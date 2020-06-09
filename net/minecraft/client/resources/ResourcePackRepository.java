/*     */ package net.minecraft.client.resources;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenWorking;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.comparator.LastModifiedFileComparator;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackRepository {
/*  47 */   private static final Logger LOGGER = LogManager.getLogger();
/*  48 */   private static final FileFilter RESOURCE_PACK_FILTER = new FileFilter()
/*     */     {
/*     */       public boolean accept(File p_accept_1_)
/*     */       {
/*  52 */         boolean flag = (p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip"));
/*  53 */         boolean flag1 = (p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile());
/*  54 */         return !(!flag && !flag1);
/*     */       }
/*     */     };
/*  57 */   private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
/*  58 */   private static final ResourceLocation field_191400_f = new ResourceLocation("textures/misc/unknown_pack.png");
/*     */   private final File dirResourcepacks;
/*     */   public final IResourcePack rprDefaultResourcePack;
/*     */   private final File dirServerResourcepacks;
/*     */   public final MetadataSerializer rprMetadataSerializer;
/*     */   private IResourcePack resourcePackInstance;
/*  64 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   private ListenableFuture<Object> downloadingPacks;
/*  66 */   private List<Entry> repositoryEntriesAll = Lists.newArrayList();
/*  67 */   public final List<Entry> repositoryEntries = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, MetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
/*  71 */     this.dirResourcepacks = dirResourcepacksIn;
/*  72 */     this.dirServerResourcepacks = dirServerResourcepacksIn;
/*  73 */     this.rprDefaultResourcePack = rprDefaultResourcePackIn;
/*  74 */     this.rprMetadataSerializer = rprMetadataSerializerIn;
/*  75 */     fixDirResourcepacks();
/*  76 */     updateRepositoryEntriesAll();
/*  77 */     Iterator<String> iterator = settings.resourcePacks.iterator();
/*     */     
/*  79 */     while (iterator.hasNext()) {
/*     */       
/*  81 */       String s = iterator.next();
/*     */       
/*  83 */       for (Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
/*     */         
/*  85 */         if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
/*     */           
/*  87 */           if (resourcepackrepository$entry.getPackFormat() == 3 || settings.incompatibleResourcePacks.contains(resourcepackrepository$entry.getResourcePackName())) {
/*     */             
/*  89 */             this.repositoryEntries.add(resourcepackrepository$entry);
/*     */             
/*     */             break;
/*     */           } 
/*  93 */           iterator.remove();
/*  94 */           LOGGER.warn("Removed selected resource pack {} because it's no longer compatible", resourcepackrepository$entry.getResourcePackName());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, String> getDownloadHeaders() {
/* 102 */     Map<String, String> map = Maps.newHashMap();
/* 103 */     Minecraft.getMinecraft(); map.put("X-Minecraft-Username", Minecraft.getSession().getUsername());
/* 104 */     Minecraft.getMinecraft(); map.put("X-Minecraft-UUID", Minecraft.getSession().getPlayerID());
/* 105 */     map.put("X-Minecraft-Version", "1.12.2");
/* 106 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixDirResourcepacks() {
/* 111 */     if (this.dirResourcepacks.exists()) {
/*     */       
/* 113 */       if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs()))
/*     */       {
/* 115 */         LOGGER.warn("Unable to recreate resourcepack folder, it exists but is not a directory: {}", this.dirResourcepacks);
/*     */       }
/*     */     }
/* 118 */     else if (!this.dirResourcepacks.mkdirs()) {
/*     */       
/* 120 */       LOGGER.warn("Unable to create resourcepack folder: {}", this.dirResourcepacks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<File> getResourcePackFiles() {
/* 126 */     return this.dirResourcepacks.isDirectory() ? Arrays.<File>asList(this.dirResourcepacks.listFiles(RESOURCE_PACK_FILTER)) : Collections.<File>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IResourcePack func_191399_b(File p_191399_1_) {
/*     */     IResourcePack iresourcepack;
/* 133 */     if (p_191399_1_.isDirectory()) {
/*     */       
/* 135 */       iresourcepack = new FolderResourcePack(p_191399_1_);
/*     */     }
/*     */     else {
/*     */       
/* 139 */       iresourcepack = new FileResourcePack(p_191399_1_);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 144 */       PackMetadataSection packmetadatasection = iresourcepack.<PackMetadataSection>getPackMetadata(this.rprMetadataSerializer, "pack");
/*     */       
/* 146 */       if (packmetadatasection != null && packmetadatasection.getPackFormat() == 2)
/*     */       {
/* 148 */         return new LegacyV2Adapter(iresourcepack);
/*     */       }
/*     */     }
/* 151 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     return iresourcepack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRepositoryEntriesAll() {
/* 161 */     List<Entry> list = Lists.newArrayList();
/*     */     
/* 163 */     for (File file1 : getResourcePackFiles()) {
/*     */       
/* 165 */       Entry resourcepackrepository$entry = new Entry(file1, null);
/*     */       
/* 167 */       if (this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
/*     */         
/* 169 */         int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
/*     */         
/* 171 */         if (i > -1 && i < this.repositoryEntriesAll.size())
/*     */         {
/* 173 */           list.add(this.repositoryEntriesAll.get(i));
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       try {
/* 180 */         resourcepackrepository$entry.updateResourcePack();
/* 181 */         list.add(resourcepackrepository$entry);
/*     */       }
/* 183 */       catch (Exception var61) {
/*     */         
/* 185 */         list.remove(resourcepackrepository$entry);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 190 */     this.repositoryEntriesAll.removeAll(list);
/*     */     
/* 192 */     for (Entry resourcepackrepository$entry1 : this.repositoryEntriesAll)
/*     */     {
/* 194 */       resourcepackrepository$entry1.closeResourcePack();
/*     */     }
/*     */     
/* 197 */     this.repositoryEntriesAll = list;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entry getResourcePackEntry() {
/* 203 */     if (this.resourcePackInstance != null) {
/*     */       
/* 205 */       Entry resourcepackrepository$entry = new Entry(this.resourcePackInstance, null);
/*     */ 
/*     */       
/*     */       try {
/* 209 */         resourcepackrepository$entry.updateResourcePack();
/* 210 */         return resourcepackrepository$entry;
/*     */       }
/* 212 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntriesAll() {
/* 223 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntriesAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Entry> getRepositoryEntries() {
/* 228 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntries);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRepositories(List<Entry> repositories) {
/* 233 */     this.repositoryEntries.clear();
/* 234 */     this.repositoryEntries.addAll(repositories);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDirResourcepacks() {
/* 239 */     return this.dirResourcepacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> downloadResourcePack(String url, String hash) {
/* 244 */     String s = DigestUtils.sha1Hex(url);
/* 245 */     final String s1 = SHA1.matcher(hash).matches() ? hash : "";
/* 246 */     final File file1 = new File(this.dirServerResourcepacks, s);
/* 247 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 251 */       clearResourcePack();
/*     */       
/* 253 */       if (file1.exists()) {
/*     */         
/* 255 */         if (checkHash(s1, file1)) {
/*     */           
/* 257 */           ListenableFuture<Object> listenablefuture2 = setResourcePackInstance(file1);
/* 258 */           ListenableFuture<Object> listenablefuture3 = listenablefuture2;
/* 259 */           return listenablefuture3;
/*     */         } 
/*     */         
/* 262 */         LOGGER.warn("Deleting file {}", file1);
/* 263 */         FileUtils.deleteQuietly(file1);
/*     */       } 
/*     */       
/* 266 */       deleteOldServerResourcesPacks();
/* 267 */       final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
/* 268 */       Map<String, String> map = getDownloadHeaders();
/* 269 */       final Minecraft minecraft = Minecraft.getMinecraft();
/* 270 */       Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 274 */                 minecraft.displayGuiScreen((GuiScreen)guiscreenworking);
/*     */               }
/*     */             }));
/* 277 */       final SettableFuture<Object> settablefuture = SettableFuture.create();
/* 278 */       this.downloadingPacks = HttpUtil.downloadResourcePack(file1, url, map, 52428800, (IProgressUpdate)guiscreenworking, minecraft.getProxy());
/* 279 */       Futures.addCallback(this.downloadingPacks, new FutureCallback<Object>()
/*     */           {
/*     */             public void onSuccess(@Nullable Object p_onSuccess_1_)
/*     */             {
/* 283 */               if (ResourcePackRepository.this.checkHash(s1, file1)) {
/*     */                 
/* 285 */                 ResourcePackRepository.this.setResourcePackInstance(file1);
/* 286 */                 settablefuture.set(null);
/*     */               }
/*     */               else {
/*     */                 
/* 290 */                 ResourcePackRepository.LOGGER.warn("Deleting file {}", file1);
/* 291 */                 FileUtils.deleteQuietly(file1);
/*     */               } 
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 296 */               FileUtils.deleteQuietly(file1);
/* 297 */               settablefuture.setException(p_onFailure_1_);
/*     */             }
/*     */           });
/* 300 */       ListenableFuture<Object> listenablefuture = this.downloadingPacks;
/* 301 */       ListenableFuture<Object> listenablefuture1 = listenablefuture;
/* 302 */       return listenablefuture1;
/*     */     }
/*     */     finally {
/*     */       
/* 306 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkHash(String p_190113_1_, File p_190113_2_) {
/*     */     try {
/* 314 */       String s = DigestUtils.sha1Hex(new FileInputStream(p_190113_2_));
/*     */       
/* 316 */       if (p_190113_1_.isEmpty()) {
/*     */         
/* 318 */         LOGGER.info("Found file {} without verification hash", p_190113_2_);
/* 319 */         return true;
/*     */       } 
/*     */       
/* 322 */       if (s.toLowerCase(Locale.ROOT).equals(p_190113_1_.toLowerCase(Locale.ROOT))) {
/*     */         
/* 324 */         LOGGER.info("Found file {} matching requested hash {}", p_190113_2_, p_190113_1_);
/* 325 */         return true;
/*     */       } 
/*     */       
/* 328 */       LOGGER.warn("File {} had wrong hash (expected {}, found {}).", p_190113_2_, p_190113_1_, s);
/*     */     }
/* 330 */     catch (IOException ioexception1) {
/*     */       
/* 332 */       LOGGER.warn("File {} couldn't be hashed.", p_190113_2_, ioexception1);
/*     */     } 
/*     */     
/* 335 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validatePack(File p_190112_1_) {
/* 340 */     Entry resourcepackrepository$entry = new Entry(p_190112_1_, null);
/*     */ 
/*     */     
/*     */     try {
/* 344 */       resourcepackrepository$entry.updateResourcePack();
/* 345 */       return true;
/*     */     }
/* 347 */     catch (Exception exception) {
/*     */       
/* 349 */       LOGGER.warn("Server resourcepack is invalid, ignoring it", exception);
/* 350 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteOldServerResourcesPacks() {
/*     */     try {
/* 361 */       List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, null));
/* 362 */       Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
/* 363 */       int i = 0;
/*     */       
/* 365 */       for (File file1 : list) {
/*     */         
/* 367 */         if (i++ >= 10)
/*     */         {
/* 369 */           LOGGER.info("Deleting old server resource pack {}", file1.getName());
/* 370 */           FileUtils.deleteQuietly(file1);
/*     */         }
/*     */       
/*     */       } 
/* 374 */     } catch (IllegalArgumentException illegalargumentexception1) {
/*     */       
/* 376 */       LOGGER.error("Error while deleting old server resource pack : {}", illegalargumentexception1.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> setResourcePackInstance(File resourceFile) {
/* 382 */     if (!validatePack(resourceFile))
/*     */     {
/* 384 */       return Futures.immediateFailedFuture(new RuntimeException("Invalid resourcepack"));
/*     */     }
/*     */ 
/*     */     
/* 388 */     this.resourcePackInstance = new FileResourcePack(resourceFile);
/* 389 */     return Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IResourcePack getResourcePackInstance() {
/* 400 */     return this.resourcePackInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearResourcePack() {
/* 405 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 409 */       if (this.downloadingPacks != null)
/*     */       {
/* 411 */         this.downloadingPacks.cancel(true);
/*     */       }
/*     */       
/* 414 */       this.downloadingPacks = null;
/*     */       
/* 416 */       if (this.resourcePackInstance != null)
/*     */       {
/* 418 */         this.resourcePackInstance = null;
/* 419 */         Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 424 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     private final IResourcePack reResourcePack;
/*     */     private PackMetadataSection rePackMetadataSection;
/*     */     private ResourceLocation locationTexturePackIcon;
/*     */     
/*     */     private Entry(File resourcePackFileIn) {
/* 436 */       this(ResourcePackRepository.this.func_191399_b(resourcePackFileIn));
/*     */     }
/*     */ 
/*     */     
/*     */     private Entry(IResourcePack reResourcePackIn) {
/* 441 */       this.reResourcePack = reResourcePackIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateResourcePack() throws IOException {
/* 446 */       this.rePackMetadataSection = this.reResourcePack.<PackMetadataSection>getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
/* 447 */       closeResourcePack();
/*     */     }
/*     */ 
/*     */     
/*     */     public void bindTexturePackIcon(TextureManager textureManagerIn) {
/* 452 */       BufferedImage bufferedimage = null;
/*     */       
/* 454 */       if (this.locationTexturePackIcon == null) {
/*     */ 
/*     */         
/*     */         try {
/* 458 */           bufferedimage = this.reResourcePack.getPackImage();
/*     */         }
/* 460 */         catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 465 */         if (bufferedimage == null) {
/*     */           
/*     */           try {
/*     */             
/* 469 */             bufferedimage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(ResourcePackRepository.field_191400_f).getInputStream());
/*     */           }
/* 471 */           catch (IOException ioexception) {
/*     */             
/* 473 */             throw new Error("Couldn't bind resource pack icon", ioexception);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 478 */       if (this.locationTexturePackIcon == null)
/*     */       {
/* 480 */         this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon", new DynamicTexture(bufferedimage));
/*     */       }
/*     */       
/* 483 */       textureManagerIn.bindTexture(this.locationTexturePackIcon);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeResourcePack() {
/* 488 */       if (this.reResourcePack instanceof Closeable)
/*     */       {
/* 490 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IResourcePack getResourcePack() {
/* 496 */       return this.reResourcePack;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getResourcePackName() {
/* 501 */       return this.reResourcePack.getPackName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTexturePackDescription() {
/* 506 */       return (this.rePackMetadataSection == null) ? (TextFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPackFormat() {
/* 511 */       return (this.rePackMetadataSection == null) ? 0 : this.rePackMetadataSection.getPackFormat();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 516 */       if (this == p_equals_1_)
/*     */       {
/* 518 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 522 */       return (p_equals_1_ instanceof Entry) ? toString().equals(p_equals_1_.toString()) : false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 528 */       return toString().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 533 */       return String.format("%s:%s", new Object[] { this.reResourcePack.getPackName(), (this.reResourcePack instanceof FolderResourcePack) ? "folder" : "zip" });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourcePackRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
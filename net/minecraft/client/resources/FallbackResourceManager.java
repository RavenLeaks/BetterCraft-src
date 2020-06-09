/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class FallbackResourceManager
/*     */   implements IResourceManager {
/*  19 */   private static final Logger LOGGER = LogManager.getLogger();
/*  20 */   protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
/*     */   
/*     */   private final MetadataSerializer frmMetadataSerializer;
/*     */   
/*     */   public FallbackResourceManager(MetadataSerializer frmMetadataSerializerIn) {
/*  25 */     this.frmMetadataSerializer = frmMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addResourcePack(IResourcePack resourcePack) {
/*  30 */     this.resourcePacks.add(resourcePack);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*  35 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IResource getResource(ResourceLocation location) throws IOException {
/*  40 */     checkResourcePath(location);
/*  41 */     IResourcePack iresourcepack = null;
/*  42 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  44 */     for (int i = this.resourcePacks.size() - 1; i >= 0; i--) {
/*     */       
/*  46 */       IResourcePack iresourcepack1 = this.resourcePacks.get(i);
/*     */       
/*  48 */       if (iresourcepack == null && iresourcepack1.resourceExists(resourcelocation))
/*     */       {
/*  50 */         iresourcepack = iresourcepack1;
/*     */       }
/*     */       
/*  53 */       if (iresourcepack1.resourceExists(location)) {
/*     */         
/*  55 */         InputStream inputstream = null;
/*     */         
/*  57 */         if (iresourcepack != null)
/*     */         {
/*  59 */           inputstream = getInputStream(resourcelocation, iresourcepack);
/*     */         }
/*     */         
/*  62 */         return new SimpleResource(iresourcepack1.getPackName(), location, getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     throw new FileNotFoundException(location.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected InputStream getInputStream(ResourceLocation location, IResourcePack resourcePack) throws IOException {
/*  71 */     InputStream inputstream = resourcePack.getInputStream(location);
/*  72 */     return LOGGER.isDebugEnabled() ? new InputStreamLeakedResourceLogger(inputstream, location, resourcePack.getPackName()) : inputstream;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkResourcePath(ResourceLocation p_188552_1_) throws IOException {
/*  77 */     if (p_188552_1_.getResourcePath().contains(".."))
/*     */     {
/*  79 */       throw new IOException("Invalid relative path to resource: " + p_188552_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IResource> getAllResources(ResourceLocation location) throws IOException {
/*  85 */     checkResourcePath(location);
/*  86 */     List<IResource> list = Lists.newArrayList();
/*  87 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  89 */     for (IResourcePack iresourcepack : this.resourcePacks) {
/*     */       
/*  91 */       if (iresourcepack.resourceExists(location)) {
/*     */         
/*  93 */         InputStream inputstream = iresourcepack.resourceExists(resourcelocation) ? getInputStream(resourcelocation, iresourcepack) : null;
/*  94 */         list.add(new SimpleResource(iresourcepack.getPackName(), location, getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     if (list.isEmpty())
/*     */     {
/* 100 */       throw new FileNotFoundException(location.toString());
/*     */     }
/*     */ 
/*     */     
/* 104 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ResourceLocation getLocationMcmeta(ResourceLocation location) {
/* 110 */     return new ResourceLocation(location.getResourceDomain(), String.valueOf(location.getResourcePath()) + ".mcmeta");
/*     */   }
/*     */   
/*     */   static class InputStreamLeakedResourceLogger
/*     */     extends InputStream
/*     */   {
/*     */     private final InputStream inputStream;
/*     */     private final String message;
/*     */     private boolean isClosed;
/*     */     
/*     */     public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ResourceLocation location, String resourcePack) {
/* 121 */       this.inputStream = p_i46093_1_;
/* 122 */       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 123 */       (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
/* 124 */       this.message = "Leaked resource: '" + location + "' loaded from pack: '" + resourcePack + "'\n" + bytearrayoutputstream;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 129 */       this.inputStream.close();
/* 130 */       this.isClosed = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 135 */       if (!this.isClosed)
/*     */       {
/* 137 */         FallbackResourceManager.LOGGER.warn(this.message);
/*     */       }
/*     */       
/* 140 */       super.finalize();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 145 */       return this.inputStream.read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\FallbackResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
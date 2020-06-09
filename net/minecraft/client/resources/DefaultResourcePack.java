/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Util;
/*     */ import optifine.ReflectorForge;
/*     */ 
/*     */ 
/*     */ public class DefaultResourcePack
/*     */   implements IResourcePack
/*     */ {
/*  23 */   public static final Set<String> DEFAULT_RESOURCE_DOMAINS = (Set<String>)ImmutableSet.of("minecraft", "realms", "wdl");
/*     */   
/*     */   private final ResourceIndex resourceIndex;
/*  26 */   private static final boolean ON_WINDOWS = (Util.getOSType() == Util.EnumOS.WINDOWS);
/*     */ 
/*     */   
/*     */   public DefaultResourcePack(ResourceIndex resourceIndexIn) {
/*  30 */     this.resourceIndex = resourceIndexIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/*  35 */     InputStream inputstream = getInputStreamAssets(location);
/*     */     
/*  37 */     if (inputstream != null)
/*     */     {
/*  39 */       return inputstream;
/*     */     }
/*     */ 
/*     */     
/*  43 */     InputStream inputstream1 = getResourceStream(location);
/*     */     
/*  45 */     if (inputstream1 != null)
/*     */     {
/*  47 */       return inputstream1;
/*     */     }
/*     */ 
/*     */     
/*  51 */     throw new FileNotFoundException(location.getResourcePath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException {
/*  59 */     File file1 = this.resourceIndex.getFile(location);
/*  60 */     return (file1 != null && file1.isFile()) ? new FileInputStream(file1) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private InputStream getResourceStream(ResourceLocation location) {
/*  66 */     String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
/*  67 */     InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
/*     */     
/*  69 */     if (inputstream != null)
/*     */     {
/*  71 */       return inputstream;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  77 */       URL url = DefaultResourcePack.class.getResource(s);
/*  78 */       return (url != null && validatePath(new File(url.getFile()), s)) ? DefaultResourcePack.class.getResourceAsStream(s) : null;
/*     */     }
/*  80 */     catch (IOException var5) {
/*     */       
/*  82 */       return DefaultResourcePack.class.getResourceAsStream(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean resourceExists(ResourceLocation location) {
/*  89 */     return !(getResourceStream(location) == null && !this.resourceIndex.isFileExisting(location));
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*  94 */     return DEFAULT_RESOURCE_DOMAINS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/*     */     try {
/* 102 */       InputStream inputstream = new FileInputStream(this.resourceIndex.getPackMcmeta());
/* 103 */       return AbstractResourcePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
/*     */     }
/* 105 */     catch (RuntimeException var4) {
/*     */       
/* 107 */       return null;
/*     */     }
/* 109 */     catch (FileNotFoundException var51) {
/*     */       
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getPackImage() throws IOException {
/* 117 */     return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPackName() {
/* 122 */     return "Default";
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validatePath(File p_validatePath_1_, String p_validatePath_2_) throws IOException {
/* 127 */     String s = p_validatePath_1_.getPath();
/*     */     
/* 129 */     if (s.startsWith("file:")) {
/*     */       
/* 131 */       if (ON_WINDOWS)
/*     */       {
/* 133 */         s = s.replace("\\", "/");
/*     */       }
/*     */       
/* 136 */       return s.endsWith(p_validatePath_2_);
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return FolderResourcePack.func_191384_a(p_validatePath_1_, p_validatePath_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\DefaultResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
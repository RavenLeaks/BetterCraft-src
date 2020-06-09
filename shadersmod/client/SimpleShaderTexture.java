/*     */ package shadersmod.client;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.FontMetadataSection;
/*     */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*     */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import shadersmod.common.SMCLog;
/*     */ 
/*     */ public class SimpleShaderTexture
/*     */   extends AbstractTexture {
/*     */   private String texturePath;
/*  32 */   private static final MetadataSerializer METADATA_SERIALIZER = makeMetadataSerializer();
/*     */ 
/*     */   
/*     */   public SimpleShaderTexture(String texturePath) {
/*  36 */     this.texturePath = texturePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  41 */     deleteGlTexture();
/*  42 */     InputStream inputstream = Shaders.getShaderPackResourceStream(this.texturePath);
/*     */     
/*  44 */     if (inputstream == null)
/*     */     {
/*  46 */       throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  52 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*  53 */       TextureMetadataSection texturemetadatasection = loadTextureMetadataSection();
/*  54 */       TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, texturemetadatasection.getTextureBlur(), texturemetadatasection.getTextureClamp());
/*     */     }
/*     */     finally {
/*     */       
/*  58 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TextureMetadataSection loadTextureMetadataSection() {
/*  65 */     String s = String.valueOf(this.texturePath) + ".mcmeta";
/*  66 */     String s1 = "texture";
/*  67 */     InputStream inputstream = Shaders.getShaderPackResourceStream(s);
/*     */     
/*  69 */     if (inputstream != null) {
/*     */       TextureMetadataSection texturemetadatasection1;
/*  71 */       MetadataSerializer metadataserializer = METADATA_SERIALIZER;
/*  72 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       try { JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*  78 */         TextureMetadataSection texturemetadatasection = (TextureMetadataSection)metadataserializer.parseMetadataSection(s1, jsonobject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */          }
/*     */       
/*  87 */       catch (RuntimeException runtimeexception)
/*     */       
/*  89 */       { SMCLog.warning("Error reading metadata: " + s);
/*  90 */         SMCLog.warning(runtimeexception.getClass().getName() + ": " + runtimeexception.getMessage());
/*  91 */         return new TextureMetadataSection(false, false); }
/*     */       
/*     */       finally
/*     */       
/*  95 */       { IOUtils.closeQuietly(bufferedreader);
/*  96 */         IOUtils.closeQuietly(inputstream); }  IOUtils.closeQuietly(bufferedreader); IOUtils.closeQuietly(inputstream);
/*     */ 
/*     */       
/*  99 */       return texturemetadatasection1;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return new TextureMetadataSection(false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MetadataSerializer makeMetadataSerializer() {
/* 109 */     MetadataSerializer metadataserializer = new MetadataSerializer();
/* 110 */     metadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/* 111 */     metadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/* 112 */     metadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/* 113 */     metadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/* 114 */     metadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/* 115 */     return metadataserializer;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\SimpleShaderTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
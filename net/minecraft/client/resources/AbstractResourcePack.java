/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.MetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractResourcePack
/*    */   implements IResourcePack
/*    */ {
/* 24 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   public final File resourcePackFile;
/*    */   
/*    */   public AbstractResourcePack(File resourcePackFileIn) {
/* 29 */     this.resourcePackFile = resourcePackFileIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String locationToName(ResourceLocation location) {
/* 34 */     return String.format("%s/%s/%s", new Object[] { "assets", location.getResourceDomain(), location.getResourcePath() });
/*    */   }
/*    */ 
/*    */   
/*    */   protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
/* 39 */     return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 44 */     return getInputStreamByName(locationToName(location));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 49 */     return hasResourceName(locationToName(location));
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract InputStream getInputStreamByName(String paramString) throws IOException;
/*    */   
/*    */   protected abstract boolean hasResourceName(String paramString);
/*    */   
/*    */   protected void logNameNotLowercase(String name) {
/* 58 */     LOGGER.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", name, this.resourcePackFile);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/* 63 */     return readMetadata(metadataSerializer, getInputStreamByName("pack.mcmeta"), metadataSectionName);
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends net.minecraft.client.resources.data.IMetadataSection> T readMetadata(MetadataSerializer metadataSerializer, InputStream p_110596_1_, String sectionName) {
/* 68 */     JsonObject jsonobject = null;
/* 69 */     BufferedReader bufferedreader = null;
/*    */ 
/*    */     
/*    */     try {
/* 73 */       bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, StandardCharsets.UTF_8));
/* 74 */       jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*    */     }
/* 76 */     catch (RuntimeException runtimeexception) {
/*    */       
/* 78 */       throw new JsonParseException(runtimeexception);
/*    */     }
/*    */     finally {
/*    */       
/* 82 */       IOUtils.closeQuietly(bufferedreader);
/*    */     } 
/*    */     
/* 85 */     return (T)metadataSerializer.parseMetadataSection(sectionName, jsonobject);
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 90 */     return TextureUtil.readBufferedImage(getInputStreamByName("pack.png"));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackName() {
/* 95 */     return this.resourcePackFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\AbstractResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
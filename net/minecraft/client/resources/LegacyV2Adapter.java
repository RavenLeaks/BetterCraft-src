/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.data.MetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class LegacyV2Adapter
/*    */   implements IResourcePack
/*    */ {
/*    */   private final IResourcePack field_191383_a;
/*    */   
/*    */   public LegacyV2Adapter(IResourcePack p_i47182_1_) {
/* 18 */     this.field_191383_a = p_i47182_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 23 */     return this.field_191383_a.getInputStream(func_191382_c(location));
/*    */   }
/*    */ 
/*    */   
/*    */   private ResourceLocation func_191382_c(ResourceLocation p_191382_1_) {
/* 28 */     String s = p_191382_1_.getResourcePath();
/*    */     
/* 30 */     if (!"lang/swg_de.lang".equals(s) && s.startsWith("lang/") && s.endsWith(".lang")) {
/*    */       
/* 32 */       int i = s.indexOf('_');
/*    */       
/* 34 */       if (i != -1) {
/*    */         
/* 36 */         final String s1 = String.valueOf(s.substring(0, i + 1)) + s.substring(i + 1, s.indexOf('.', i)).toUpperCase() + ".lang";
/* 37 */         return new ResourceLocation(p_191382_1_.getResourceDomain(), "")
/*    */           {
/*    */             public String getResourcePath()
/*    */             {
/* 41 */               return s1;
/*    */             }
/*    */           };
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     return p_191382_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 52 */     return this.field_191383_a.resourceExists(func_191382_c(location));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 57 */     return this.field_191383_a.getResourceDomains();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/* 63 */     return this.field_191383_a.getPackMetadata(metadataSerializer, metadataSectionName);
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 68 */     return this.field_191383_a.getPackImage();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackName() {
/* 73 */     return this.field_191383_a.getPackName();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\LegacyV2Adapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
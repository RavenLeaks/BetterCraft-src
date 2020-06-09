/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomEntityRenderer
/*    */ {
/*  7 */   private String name = null;
/*  8 */   private String basePath = null;
/*  9 */   private ResourceLocation textureLocation = null;
/* 10 */   private CustomModelRenderer[] customModelRenderers = null;
/* 11 */   private float shadowSize = 0.0F;
/*    */ 
/*    */   
/*    */   public CustomEntityRenderer(String name, String basePath, ResourceLocation textureLocation, CustomModelRenderer[] customModelRenderers, float shadowSize) {
/* 15 */     this.name = name;
/* 16 */     this.basePath = basePath;
/* 17 */     this.textureLocation = textureLocation;
/* 18 */     this.customModelRenderers = customModelRenderers;
/* 19 */     this.shadowSize = shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBasePath() {
/* 29 */     return this.basePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation() {
/* 34 */     return this.textureLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public CustomModelRenderer[] getCustomModelRenderers() {
/* 39 */     return this.customModelRenderers;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadowSize() {
/* 44 */     return this.shadowSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\CustomEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
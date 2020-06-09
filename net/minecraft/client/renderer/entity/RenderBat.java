/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderBat extends RenderLiving<EntityBat> {
/* 11 */   private static final ResourceLocation BAT_TEXTURES = new ResourceLocation("textures/entity/bat.png");
/*    */ 
/*    */   
/*    */   public RenderBat(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelBat(), 0.25F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBat entity) {
/* 23 */     return BAT_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityBat entitylivingbaseIn, float partialTickTime) {
/* 31 */     GlStateManager.scale(0.35F, 0.35F, 0.35F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityBat entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 36 */     if (entityLiving.getIsBatHanging()) {
/*    */       
/* 38 */       GlStateManager.translate(0.0F, -0.1F, 0.0F);
/*    */     }
/*    */     else {
/*    */       
/* 42 */       GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.3F) * 0.1F, 0.0F);
/*    */     } 
/*    */     
/* 45 */     super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
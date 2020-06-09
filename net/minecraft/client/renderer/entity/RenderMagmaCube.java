/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
/* 10 */   private static final ResourceLocation MAGMA_CUBE_TEXTURES = new ResourceLocation("textures/entity/slime/magmacube.png");
/*    */ 
/*    */   
/*    */   public RenderMagmaCube(RenderManager renderManagerIn) {
/* 14 */     super(renderManagerIn, (ModelBase)new ModelMagmaCube(), 0.25F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMagmaCube entity) {
/* 22 */     return MAGMA_CUBE_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityMagmaCube entitylivingbaseIn, float partialTickTime) {
/* 30 */     int i = entitylivingbaseIn.getSlimeSize();
/* 31 */     float f = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (i * 0.5F + 1.0F);
/* 32 */     float f1 = 1.0F / (f + 1.0F);
/* 33 */     GlStateManager.scale(f1 * i, 1.0F / f1 * i, f1 * i);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
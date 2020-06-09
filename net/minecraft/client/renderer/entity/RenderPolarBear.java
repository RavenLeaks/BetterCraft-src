/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelPolarBear;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityPolarBear;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderPolarBear extends RenderLiving<EntityPolarBear> {
/* 10 */   private static final ResourceLocation POLAR_BEAR_TEXTURE = new ResourceLocation("textures/entity/bear/polarbear.png");
/*    */ 
/*    */   
/*    */   public RenderPolarBear(RenderManager p_i47197_1_) {
/* 14 */     super(p_i47197_1_, (ModelBase)new ModelPolarBear(), 0.7F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityPolarBear entity) {
/* 22 */     return POLAR_BEAR_TEXTURE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityPolarBear entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 30 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityPolarBear entitylivingbaseIn, float partialTickTime) {
/* 38 */     GlStateManager.scale(1.2F, 1.2F, 1.2F);
/* 39 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderPolarBear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
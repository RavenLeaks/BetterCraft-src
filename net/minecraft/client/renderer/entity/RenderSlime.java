/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSlime extends RenderLiving<EntitySlime> {
/* 11 */   private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");
/*    */ 
/*    */   
/*    */   public RenderSlime(RenderManager p_i47193_1_) {
/* 15 */     super(p_i47193_1_, (ModelBase)new ModelSlime(16), 0.25F);
/* 16 */     addLayer(new LayerSlimeGel(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntitySlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 24 */     this.shadowSize = 0.25F * entity.getSlimeSize();
/* 25 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntitySlime entitylivingbaseIn, float partialTickTime) {
/* 33 */     float f = 0.999F;
/* 34 */     GlStateManager.scale(0.999F, 0.999F, 0.999F);
/* 35 */     float f1 = entitylivingbaseIn.getSlimeSize();
/* 36 */     float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
/* 37 */     float f3 = 1.0F / (f2 + 1.0F);
/* 38 */     GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySlime entity) {
/* 46 */     return SLIME_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
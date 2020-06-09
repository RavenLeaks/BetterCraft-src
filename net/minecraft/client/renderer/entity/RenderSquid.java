/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelSquid;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySquid;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSquid extends RenderLiving<EntitySquid> {
/* 10 */   private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation("textures/entity/squid.png");
/*    */ 
/*    */   
/*    */   public RenderSquid(RenderManager p_i47192_1_) {
/* 14 */     super(p_i47192_1_, (ModelBase)new ModelSquid(), 0.7F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySquid entity) {
/* 22 */     return SQUID_TEXTURES;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntitySquid entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 27 */     float f = entityLiving.prevSquidPitch + (entityLiving.squidPitch - entityLiving.prevSquidPitch) * partialTicks;
/* 28 */     float f1 = entityLiving.prevSquidYaw + (entityLiving.squidYaw - entityLiving.prevSquidYaw) * partialTicks;
/* 29 */     GlStateManager.translate(0.0F, 0.5F, 0.0F);
/* 30 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/* 31 */     GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
/* 32 */     GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
/* 33 */     GlStateManager.translate(0.0F, -1.2F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float handleRotationFloat(EntitySquid livingBase, float partialTicks) {
/* 41 */     return livingBase.lastTentacleAngle + (livingBase.tentacleAngle - livingBase.lastTentacleAngle) * partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
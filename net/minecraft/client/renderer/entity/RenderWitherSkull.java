/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWitherSkull extends Render<EntityWitherSkull> {
/* 10 */   private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 11 */   private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");
/*    */ 
/*    */   
/* 14 */   private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
/*    */ 
/*    */   
/*    */   public RenderWitherSkull(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_) {
/*    */     float f;
/* 25 */     for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     while (f >= 180.0F)
/*    */     {
/* 32 */       f -= 360.0F;
/*    */     }
/*    */     
/* 35 */     return p_82400_1_ + p_82400_3_ * f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWitherSkull entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 43 */     GlStateManager.pushMatrix();
/* 44 */     GlStateManager.disableCull();
/* 45 */     float f = getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
/* 46 */     float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/* 47 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 48 */     float f2 = 0.0625F;
/* 49 */     GlStateManager.enableRescaleNormal();
/* 50 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 51 */     GlStateManager.enableAlpha();
/* 52 */     bindEntityTexture(entity);
/*    */     
/* 54 */     if (this.renderOutlines) {
/*    */       
/* 56 */       GlStateManager.enableColorMaterial();
/* 57 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 60 */     this.skeletonHeadModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, f, f1, 0.0625F);
/*    */     
/* 62 */     if (this.renderOutlines) {
/*    */       
/* 64 */       GlStateManager.disableOutlineMode();
/* 65 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 68 */     GlStateManager.popMatrix();
/* 69 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWitherSkull entity) {
/* 77 */     return entity.isInvulnerable() ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
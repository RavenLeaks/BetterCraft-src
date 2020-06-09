/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelShulkerBullet;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderShulkerBullet extends Render<EntityShulkerBullet> {
/* 11 */   private static final ResourceLocation SHULKER_SPARK_TEXTURE = new ResourceLocation("textures/entity/shulker/spark.png");
/* 12 */   private final ModelShulkerBullet model = new ModelShulkerBullet();
/*    */ 
/*    */   
/*    */   public RenderShulkerBullet(RenderManager manager) {
/* 16 */     super(manager);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private float rotLerp(float p_188347_1_, float p_188347_2_, float p_188347_3_) {
/*    */     float f;
/* 23 */     for (f = p_188347_2_ - p_188347_1_; f < -180.0F; f += 360.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     while (f >= 180.0F)
/*    */     {
/* 30 */       f -= 360.0F;
/*    */     }
/*    */     
/* 33 */     return p_188347_1_ + p_188347_3_ * f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityShulkerBullet entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 41 */     GlStateManager.pushMatrix();
/* 42 */     float f = rotLerp(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
/* 43 */     float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/* 44 */     float f2 = entity.ticksExisted + partialTicks;
/* 45 */     GlStateManager.translate((float)x, (float)y + 0.15F, (float)z);
/* 46 */     GlStateManager.rotate(MathHelper.sin(f2 * 0.1F) * 180.0F, 0.0F, 1.0F, 0.0F);
/* 47 */     GlStateManager.rotate(MathHelper.cos(f2 * 0.1F) * 180.0F, 1.0F, 0.0F, 0.0F);
/* 48 */     GlStateManager.rotate(MathHelper.sin(f2 * 0.15F) * 360.0F, 0.0F, 0.0F, 1.0F);
/* 49 */     float f3 = 0.03125F;
/* 50 */     GlStateManager.enableRescaleNormal();
/* 51 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 52 */     bindEntityTexture(entity);
/* 53 */     this.model.render((Entity)entity, 0.0F, 0.0F, 0.0F, f, f1, 0.03125F);
/* 54 */     GlStateManager.enableBlend();
/* 55 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
/* 56 */     GlStateManager.scale(1.5F, 1.5F, 1.5F);
/* 57 */     this.model.render((Entity)entity, 0.0F, 0.0F, 0.0F, f, f1, 0.03125F);
/* 58 */     GlStateManager.disableBlend();
/* 59 */     GlStateManager.popMatrix();
/* 60 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityShulkerBullet entity) {
/* 68 */     return SHULKER_SPARK_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderShulkerBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
/* 14 */   private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
/* 15 */   private final ModelBase modelEnderCrystal = (ModelBase)new ModelEnderCrystal(0.0F, true);
/* 16 */   private final ModelBase modelEnderCrystalNoBase = (ModelBase)new ModelEnderCrystal(0.0F, false);
/*    */ 
/*    */   
/*    */   public RenderEnderCrystal(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 29 */     float f = entity.innerRotation + partialTicks;
/* 30 */     GlStateManager.pushMatrix();
/* 31 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 32 */     bindTexture(ENDER_CRYSTAL_TEXTURES);
/* 33 */     float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
/* 34 */     f1 = f1 * f1 + f1;
/*    */     
/* 36 */     if (this.renderOutlines) {
/*    */       
/* 38 */       GlStateManager.enableColorMaterial();
/* 39 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 42 */     if (entity.shouldShowBottom()) {
/*    */       
/* 44 */       this.modelEnderCrystal.render((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/*    */     }
/*    */     else {
/*    */       
/* 48 */       this.modelEnderCrystalNoBase.render((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/*    */     } 
/*    */     
/* 51 */     if (this.renderOutlines) {
/*    */       
/* 53 */       GlStateManager.disableOutlineMode();
/* 54 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 57 */     GlStateManager.popMatrix();
/* 58 */     BlockPos blockpos = entity.getBeamTarget();
/*    */     
/* 60 */     if (blockpos != null) {
/*    */       
/* 62 */       bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
/* 63 */       float f2 = blockpos.getX() + 0.5F;
/* 64 */       float f3 = blockpos.getY() + 0.5F;
/* 65 */       float f4 = blockpos.getZ() + 0.5F;
/* 66 */       double d0 = f2 - entity.posX;
/* 67 */       double d1 = f3 - entity.posY;
/* 68 */       double d2 = f4 - entity.posZ;
/* 69 */       RenderDragon.renderCrystalBeams(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, entity.innerRotation, entity.posX, entity.posY, entity.posZ);
/*    */     } 
/*    */     
/* 72 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEnderCrystal entity) {
/* 80 */     return ENDER_CRYSTAL_TEXTURES;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender(EntityEnderCrystal livingEntity, ICamera camera, double camX, double camY, double camZ) {
/* 85 */     return !(!super.shouldRender(livingEntity, camera, camX, camY, camZ) && livingEntity.getBeamTarget() == null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
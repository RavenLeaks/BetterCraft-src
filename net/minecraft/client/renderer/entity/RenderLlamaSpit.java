/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelLlamaSpit;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityLlamaSpit;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderLlamaSpit extends Render<EntityLlamaSpit> {
/* 10 */   private static final ResourceLocation field_191333_a = new ResourceLocation("textures/entity/llama/spit.png");
/* 11 */   private final ModelLlamaSpit field_191334_f = new ModelLlamaSpit();
/*    */ 
/*    */   
/*    */   public RenderLlamaSpit(RenderManager p_i47202_1_) {
/* 15 */     super(p_i47202_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityLlamaSpit entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 23 */     GlStateManager.pushMatrix();
/* 24 */     GlStateManager.translate((float)x, (float)y + 0.15F, (float)z);
/* 25 */     GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 26 */     GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 27 */     bindEntityTexture(entity);
/*    */     
/* 29 */     if (this.renderOutlines) {
/*    */       
/* 31 */       GlStateManager.enableColorMaterial();
/* 32 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 35 */     this.field_191334_f.render((Entity)entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/*    */     
/* 37 */     if (this.renderOutlines) {
/*    */       
/* 39 */       GlStateManager.disableOutlineMode();
/* 40 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 43 */     GlStateManager.popMatrix();
/* 44 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityLlamaSpit entity) {
/* 52 */     return field_191333_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderLlamaSpit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
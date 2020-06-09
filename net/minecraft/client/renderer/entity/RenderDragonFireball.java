/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderDragonFireball extends Render<EntityDragonFireball> {
/* 12 */   private static final ResourceLocation DRAGON_FIREBALL_TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
/*    */ 
/*    */   
/*    */   public RenderDragonFireball(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityDragonFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 24 */     GlStateManager.pushMatrix();
/* 25 */     bindEntityTexture(entity);
/* 26 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 27 */     GlStateManager.enableRescaleNormal();
/* 28 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 29 */     Tessellator tessellator = Tessellator.getInstance();
/* 30 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 31 */     float f = 1.0F;
/* 32 */     float f1 = 0.5F;
/* 33 */     float f2 = 0.25F;
/* 34 */     GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 35 */     GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : true) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*    */     
/* 37 */     if (this.renderOutlines) {
/*    */       
/* 39 */       GlStateManager.enableColorMaterial();
/* 40 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 43 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 44 */     bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 45 */     bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 46 */     bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 47 */     bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 48 */     tessellator.draw();
/*    */     
/* 50 */     if (this.renderOutlines) {
/*    */       
/* 52 */       GlStateManager.disableOutlineMode();
/* 53 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 56 */     GlStateManager.disableRescaleNormal();
/* 57 */     GlStateManager.popMatrix();
/* 58 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityDragonFireball entity) {
/* 66 */     return DRAGON_FIREBALL_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderDragonFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
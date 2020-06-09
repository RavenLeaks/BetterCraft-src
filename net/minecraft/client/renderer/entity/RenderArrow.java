/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public abstract class RenderArrow<T extends EntityArrow>
/*    */   extends Render<T> {
/*    */   public RenderArrow(RenderManager renderManagerIn) {
/* 14 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 22 */     bindEntityTexture(entity);
/* 23 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 24 */     GlStateManager.pushMatrix();
/* 25 */     GlStateManager.disableLighting();
/* 26 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 27 */     GlStateManager.rotate(((EntityArrow)entity).prevRotationYaw + (((EntityArrow)entity).rotationYaw - ((EntityArrow)entity).prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 28 */     GlStateManager.rotate(((EntityArrow)entity).prevRotationPitch + (((EntityArrow)entity).rotationPitch - ((EntityArrow)entity).prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 29 */     Tessellator tessellator = Tessellator.getInstance();
/* 30 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 31 */     int i = 0;
/* 32 */     float f = 0.0F;
/* 33 */     float f1 = 0.5F;
/* 34 */     float f2 = 0.0F;
/* 35 */     float f3 = 0.15625F;
/* 36 */     float f4 = 0.0F;
/* 37 */     float f5 = 0.15625F;
/* 38 */     float f6 = 0.15625F;
/* 39 */     float f7 = 0.3125F;
/* 40 */     float f8 = 0.05625F;
/* 41 */     GlStateManager.enableRescaleNormal();
/* 42 */     float f9 = ((EntityArrow)entity).arrowShake - partialTicks;
/*    */     
/* 44 */     if (f9 > 0.0F) {
/*    */       
/* 46 */       float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
/* 47 */       GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 50 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 51 */     GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
/* 52 */     GlStateManager.translate(-4.0F, 0.0F, 0.0F);
/*    */     
/* 54 */     if (this.renderOutlines) {
/*    */       
/* 56 */       GlStateManager.enableColorMaterial();
/* 57 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 60 */     GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
/* 61 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 62 */     bufferbuilder.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
/* 63 */     bufferbuilder.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
/* 64 */     bufferbuilder.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
/* 65 */     bufferbuilder.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
/* 66 */     tessellator.draw();
/* 67 */     GlStateManager.glNormal3f(-0.05625F, 0.0F, 0.0F);
/* 68 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 69 */     bufferbuilder.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
/* 70 */     bufferbuilder.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
/* 71 */     bufferbuilder.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
/* 72 */     bufferbuilder.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
/* 73 */     tessellator.draw();
/*    */     
/* 75 */     for (int j = 0; j < 4; j++) {
/*    */       
/* 77 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 78 */       GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
/* 79 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 80 */       bufferbuilder.pos(-8.0D, -2.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 81 */       bufferbuilder.pos(8.0D, -2.0D, 0.0D).tex(0.5D, 0.0D).endVertex();
/* 82 */       bufferbuilder.pos(8.0D, 2.0D, 0.0D).tex(0.5D, 0.15625D).endVertex();
/* 83 */       bufferbuilder.pos(-8.0D, 2.0D, 0.0D).tex(0.0D, 0.15625D).endVertex();
/* 84 */       tessellator.draw();
/*    */     } 
/*    */     
/* 87 */     if (this.renderOutlines) {
/*    */       
/* 89 */       GlStateManager.disableOutlineMode();
/* 90 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 93 */     GlStateManager.disableRescaleNormal();
/* 94 */     GlStateManager.enableLighting();
/* 95 */     GlStateManager.popMatrix();
/* 96 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
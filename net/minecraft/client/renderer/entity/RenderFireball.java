/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFireball;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderFireball
/*    */   extends Render<EntityFireball> {
/*    */   private final float scale;
/*    */   
/*    */   public RenderFireball(RenderManager renderManagerIn, float scaleIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.scale = scaleIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 29 */     GlStateManager.pushMatrix();
/* 30 */     bindEntityTexture(entity);
/* 31 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 32 */     GlStateManager.enableRescaleNormal();
/* 33 */     GlStateManager.scale(this.scale, this.scale, this.scale);
/* 34 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.FIRE_CHARGE);
/* 35 */     Tessellator tessellator = Tessellator.getInstance();
/* 36 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 37 */     float f = textureatlassprite.getMinU();
/* 38 */     float f1 = textureatlassprite.getMaxU();
/* 39 */     float f2 = textureatlassprite.getMinV();
/* 40 */     float f3 = textureatlassprite.getMaxV();
/* 41 */     float f4 = 1.0F;
/* 42 */     float f5 = 0.5F;
/* 43 */     float f6 = 0.25F;
/* 44 */     GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 45 */     GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : true) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*    */     
/* 47 */     if (this.renderOutlines) {
/*    */       
/* 49 */       GlStateManager.enableColorMaterial();
/* 50 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 53 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 54 */     bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 55 */     bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 56 */     bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 57 */     bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 58 */     tessellator.draw();
/*    */     
/* 60 */     if (this.renderOutlines) {
/*    */       
/* 62 */       GlStateManager.disableOutlineMode();
/* 63 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 66 */     GlStateManager.disableRescaleNormal();
/* 67 */     GlStateManager.popMatrix();
/* 68 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFireball entity) {
/* 76 */     return TextureMap.LOCATION_BLOCKS_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderTNTPrimed
/*    */   extends Render<EntityTNTPrimed> {
/*    */   public RenderTNTPrimed(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn);
/* 17 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 25 */     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 26 */     GlStateManager.pushMatrix();
/* 27 */     GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
/*    */     
/* 29 */     if (entity.getFuse() - partialTicks + 1.0F < 10.0F) {
/*    */       
/* 31 */       float f = 1.0F - (entity.getFuse() - partialTicks + 1.0F) / 10.0F;
/* 32 */       f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 33 */       f *= f;
/* 34 */       f *= f;
/* 35 */       float f1 = 1.0F + f * 0.3F;
/* 36 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 39 */     float f2 = (1.0F - (entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
/* 40 */     bindEntityTexture(entity);
/* 41 */     GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 42 */     GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 43 */     blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), entity.getBrightness());
/* 44 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*    */     
/* 46 */     if (this.renderOutlines) {
/*    */       
/* 48 */       GlStateManager.enableColorMaterial();
/* 49 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/* 50 */       blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
/* 51 */       GlStateManager.disableOutlineMode();
/* 52 */       GlStateManager.disableColorMaterial();
/*    */     }
/* 54 */     else if (entity.getFuse() / 5 % 2 == 0) {
/*    */       
/* 56 */       GlStateManager.disableTexture2D();
/* 57 */       GlStateManager.disableLighting();
/* 58 */       GlStateManager.enableBlend();
/* 59 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
/* 60 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
/* 61 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 62 */       GlStateManager.enablePolygonOffset();
/* 63 */       blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
/* 64 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 65 */       GlStateManager.disablePolygonOffset();
/* 66 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 67 */       GlStateManager.disableBlend();
/* 68 */       GlStateManager.enableLighting();
/* 69 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */     
/* 72 */     GlStateManager.popMatrix();
/* 73 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityTNTPrimed entity) {
/* 81 */     return TextureMap.LOCATION_BLOCKS_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
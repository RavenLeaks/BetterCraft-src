/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderTntMinecart
/*    */   extends RenderMinecart<EntityMinecartTNT> {
/*    */   public RenderTntMinecart(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderCartContents(EntityMinecartTNT p_188319_1_, float partialTicks, IBlockState p_188319_3_) {
/* 20 */     int i = p_188319_1_.getFuseTicks();
/*    */     
/* 22 */     if (i > -1 && i - partialTicks + 1.0F < 10.0F) {
/*    */       
/* 24 */       float f = 1.0F - (i - partialTicks + 1.0F) / 10.0F;
/* 25 */       f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 26 */       f *= f;
/* 27 */       f *= f;
/* 28 */       float f1 = 1.0F + f * 0.3F;
/* 29 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 32 */     super.renderCartContents(p_188319_1_, partialTicks, p_188319_3_);
/*    */     
/* 34 */     if (i > -1 && i / 5 % 2 == 0) {
/*    */       
/* 36 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 37 */       GlStateManager.disableTexture2D();
/* 38 */       GlStateManager.disableLighting();
/* 39 */       GlStateManager.enableBlend();
/* 40 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
/* 41 */       GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - (i - partialTicks + 1.0F) / 100.0F) * 0.8F);
/* 42 */       GlStateManager.pushMatrix();
/* 43 */       blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
/* 44 */       GlStateManager.popMatrix();
/* 45 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 46 */       GlStateManager.disableBlend();
/* 47 */       GlStateManager.enableLighting();
/* 48 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderTntMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
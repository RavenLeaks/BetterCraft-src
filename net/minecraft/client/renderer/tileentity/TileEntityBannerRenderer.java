/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.model.ModelBanner;
/*    */ import net.minecraft.client.renderer.BannerTextures;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner> {
/* 15 */   private final ModelBanner bannerModel = new ModelBanner();
/*    */ 
/*    */   
/*    */   public void func_192841_a(TileEntityBanner p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/* 19 */     boolean flag = (p_192841_1_.getWorld() != null);
/* 20 */     boolean flag1 = !(flag && p_192841_1_.getBlockType() != Blocks.STANDING_BANNER);
/* 21 */     int i = flag ? p_192841_1_.getBlockMetadata() : 0;
/* 22 */     long j = flag ? p_192841_1_.getWorld().getTotalWorldTime() : 0L;
/* 23 */     GlStateManager.pushMatrix();
/* 24 */     float f = 0.6666667F;
/*    */     
/* 26 */     if (flag1) {
/*    */       
/* 28 */       GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
/* 29 */       float f1 = (i * 360) / 16.0F;
/* 30 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/* 31 */       this.bannerModel.bannerStand.showModel = true;
/*    */     }
/*    */     else {
/*    */       
/* 35 */       float f2 = 0.0F;
/*    */       
/* 37 */       if (i == 2)
/*    */       {
/* 39 */         f2 = 180.0F;
/*    */       }
/*    */       
/* 42 */       if (i == 4)
/*    */       {
/* 44 */         f2 = 90.0F;
/*    */       }
/*    */       
/* 47 */       if (i == 5)
/*    */       {
/* 49 */         f2 = -90.0F;
/*    */       }
/*    */       
/* 52 */       GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ - 0.16666667F, (float)p_192841_6_ + 0.5F);
/* 53 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/* 54 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/* 55 */       this.bannerModel.bannerStand.showModel = false;
/*    */     } 
/*    */     
/* 58 */     BlockPos blockpos = p_192841_1_.getPos();
/* 59 */     float f3 = (blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)j + p_192841_8_;
/* 60 */     this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(f3 * 3.1415927F * 0.02F)) * 3.1415927F;
/* 61 */     GlStateManager.enableRescaleNormal();
/* 62 */     ResourceLocation resourcelocation = getBannerResourceLocation(p_192841_1_);
/*    */     
/* 64 */     if (resourcelocation != null) {
/*    */       
/* 66 */       bindTexture(resourcelocation);
/* 67 */       GlStateManager.pushMatrix();
/* 68 */       GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
/* 69 */       this.bannerModel.renderBanner();
/* 70 */       GlStateManager.popMatrix();
/*    */     } 
/*    */     
/* 73 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_192841_10_);
/* 74 */     GlStateManager.popMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private ResourceLocation getBannerResourceLocation(TileEntityBanner bannerObj) {
/* 80 */     return BannerTextures.BANNER_DESIGNS.getResourceLocation(bannerObj.getPatternResourceLocation(), bannerObj.getPatternList(), bannerObj.getColorList());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityBannerRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
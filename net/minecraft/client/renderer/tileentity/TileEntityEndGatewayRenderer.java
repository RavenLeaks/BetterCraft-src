/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.tileentity.TileEntityEndGateway;
/*    */ import net.minecraft.tileentity.TileEntityEndPortal;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class TileEntityEndGatewayRenderer
/*    */   extends TileEntityEndPortalRenderer {
/* 12 */   private static final ResourceLocation END_GATEWAY_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");
/*    */ 
/*    */   
/*    */   public void func_192841_a(TileEntityEndPortal p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/* 16 */     GlStateManager.disableFog();
/* 17 */     TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)p_192841_1_;
/*    */     
/* 19 */     if (tileentityendgateway.isSpawning() || tileentityendgateway.isCoolingDown()) {
/*    */       
/* 21 */       GlStateManager.alphaFunc(516, 0.1F);
/* 22 */       bindTexture(END_GATEWAY_BEAM_TEXTURE);
/* 23 */       float f = tileentityendgateway.isSpawning() ? tileentityendgateway.getSpawnPercent(p_192841_8_) : tileentityendgateway.getCooldownPercent(p_192841_8_);
/* 24 */       double d0 = tileentityendgateway.isSpawning() ? (256.0D - p_192841_4_) : 50.0D;
/* 25 */       f = MathHelper.sin(f * 3.1415927F);
/* 26 */       int i = MathHelper.floor(f * d0);
/* 27 */       float[] afloat = tileentityendgateway.isSpawning() ? EnumDyeColor.MAGENTA.func_193349_f() : EnumDyeColor.PURPLE.func_193349_f();
/* 28 */       TileEntityBeaconRenderer.renderBeamSegment(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, f, tileentityendgateway.getWorld().getTotalWorldTime(), 0, i, afloat, 0.15D, 0.175D);
/* 29 */       TileEntityBeaconRenderer.renderBeamSegment(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, f, tileentityendgateway.getWorld().getTotalWorldTime(), 0, -i, afloat, 0.15D, 0.175D);
/*    */     } 
/*    */     
/* 32 */     super.func_192841_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, p_192841_10_);
/* 33 */     GlStateManager.enableFog();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int func_191286_a(double p_191286_1_) {
/* 38 */     return super.func_191286_a(p_191286_1_) + 1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float func_191287_c() {
/* 43 */     return 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityEndGatewayRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
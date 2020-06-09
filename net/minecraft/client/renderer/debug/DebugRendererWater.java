/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class DebugRendererWater implements DebugRenderer.IDebugRenderer {
/*    */   private final Minecraft minecraft;
/*    */   private EntityPlayer player;
/*    */   private double xo;
/*    */   private double yo;
/*    */   private double zo;
/*    */   
/*    */   public DebugRendererWater(Minecraft minecraftIn) {
/* 24 */     this.minecraft = minecraftIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float p_190060_1_, long p_190060_2_) {
/* 29 */     this.player = (EntityPlayer)this.minecraft.player;
/* 30 */     this.xo = this.player.lastTickPosX + (this.player.posX - this.player.lastTickPosX) * p_190060_1_;
/* 31 */     this.yo = this.player.lastTickPosY + (this.player.posY - this.player.lastTickPosY) * p_190060_1_;
/* 32 */     this.zo = this.player.lastTickPosZ + (this.player.posZ - this.player.lastTickPosZ) * p_190060_1_;
/* 33 */     BlockPos blockpos = this.minecraft.player.getPosition();
/* 34 */     World world = this.minecraft.player.world;
/* 35 */     GlStateManager.enableBlend();
/* 36 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 37 */     GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
/* 38 */     GlStateManager.disableTexture2D();
/* 39 */     GlStateManager.glLineWidth(6.0F);
/*    */     
/* 41 */     for (BlockPos blockpos1 : BlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10))) {
/*    */       
/* 43 */       IBlockState iblockstate = world.getBlockState(blockpos1);
/*    */       
/* 45 */       if (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER) {
/*    */         
/* 47 */         double d0 = BlockLiquid.func_190972_g(iblockstate, (IBlockAccess)world, blockpos1);
/* 48 */         RenderGlobal.renderFilledBox((new AxisAlignedBB((blockpos1.getX() + 0.01F), (blockpos1.getY() + 0.01F), (blockpos1.getZ() + 0.01F), (blockpos1.getX() + 0.99F), d0, (blockpos1.getZ() + 0.99F))).offset(-this.xo, -this.yo, -this.zo), 1.0F, 1.0F, 1.0F, 0.2F);
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     for (BlockPos blockpos2 : BlockPos.getAllInBox(blockpos.add(-10, -10, -10), blockpos.add(10, 10, 10))) {
/*    */       
/* 54 */       IBlockState iblockstate1 = world.getBlockState(blockpos2);
/*    */       
/* 56 */       if (iblockstate1.getBlock() == Blocks.WATER || iblockstate1.getBlock() == Blocks.FLOWING_WATER) {
/*    */         
/* 58 */         Integer integer = (Integer)iblockstate1.getValue((IProperty)BlockLiquid.LEVEL);
/* 59 */         double d1 = (integer.intValue() > 7) ? 0.9D : (1.0D - 0.11D * integer.intValue());
/* 60 */         String s = (iblockstate1.getBlock() == Blocks.FLOWING_WATER) ? "f" : "s";
/* 61 */         DebugRenderer.renderDebugText(String.valueOf(s) + " " + integer, blockpos2.getX() + 0.5D, blockpos2.getY() + d1, blockpos2.getZ() + 0.5D, p_190060_1_, -16777216);
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     GlStateManager.enableTexture2D();
/* 66 */     GlStateManager.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
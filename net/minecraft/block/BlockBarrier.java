/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockBarrier
/*    */   extends Block
/*    */ {
/*    */   protected BlockBarrier() {
/* 13 */     super(Material.BARRIER);
/* 14 */     setBlockUnbreakable();
/* 15 */     setResistance(6000001.0F);
/* 16 */     disableStats();
/* 17 */     this.translucent = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 26 */     return EnumBlockRenderType.INVISIBLE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube(IBlockState state) {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAmbientOcclusionLightValue(IBlockState state) {
/* 39 */     return 1.0F;
/*    */   }
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
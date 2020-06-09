/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class WorldGenAbstractTree
/*    */   extends WorldGenerator
/*    */ {
/*    */   public WorldGenAbstractTree(boolean notify) {
/* 14 */     super(notify);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canGrowInto(Block blockType) {
/* 23 */     Material material = blockType.getDefaultState().getMaterial();
/* 24 */     return !(material != Material.AIR && material != Material.LEAVES && blockType != Blocks.GRASS && blockType != Blocks.DIRT && blockType != Blocks.LOG && blockType != Blocks.LOG2 && blockType != Blocks.SAPLING && blockType != Blocks.VINE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void generateSaplings(World worldIn, Random random, BlockPos pos) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDirtAt(World worldIn, BlockPos pos) {
/* 36 */     if (worldIn.getBlockState(pos).getBlock() != Blocks.DIRT)
/*    */     {
/* 38 */       setBlockAndNotifyAdequately(worldIn, pos, Blocks.DIRT.getDefaultState());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenAbstractTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
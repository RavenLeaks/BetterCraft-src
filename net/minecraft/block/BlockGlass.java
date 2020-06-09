/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ 
/*    */ public class BlockGlass
/*    */   extends BlockBreakable
/*    */ {
/*    */   public BlockGlass(Material materialIn, boolean ignoreSimilarity) {
/* 13 */     super(materialIn, ignoreSimilarity);
/* 14 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 22 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockRenderLayer getBlockLayer() {
/* 27 */     return BlockRenderLayer.CUTOUT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube(IBlockState state) {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSilkHarvest() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockGlass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
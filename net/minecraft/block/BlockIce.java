/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.EnumPushReaction;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockIce
/*    */   extends BlockBreakable
/*    */ {
/*    */   public BlockIce() {
/* 25 */     super(Material.ICE, false);
/* 26 */     this.slipperiness = 0.98F;
/* 27 */     setTickRandomly(true);
/* 28 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockRenderLayer getBlockLayer() {
/* 33 */     return BlockRenderLayer.TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 38 */     player.addStat(StatList.getBlockStats(this));
/* 39 */     player.addExhaustion(0.005F);
/*    */     
/* 41 */     if (canSilkHarvest() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
/*    */       
/* 43 */       spawnAsEntity(worldIn, pos, getSilkTouchDrop(state));
/*    */     }
/*    */     else {
/*    */       
/* 47 */       if (worldIn.provider.doesWaterVaporize()) {
/*    */         
/* 49 */         worldIn.setBlockToAir(pos);
/*    */         
/*    */         return;
/*    */       } 
/* 53 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
/* 54 */       dropBlockAsItem(worldIn, pos, state, i);
/* 55 */       Material material = worldIn.getBlockState(pos.down()).getMaterial();
/*    */       
/* 57 */       if (material.blocksMovement() || material.isLiquid())
/*    */       {
/* 59 */         worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 69 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 74 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - getDefaultState().getLightOpacity())
/*    */     {
/* 76 */       turnIntoWater(worldIn, pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void turnIntoWater(World worldIn, BlockPos pos) {
/* 82 */     if (worldIn.provider.doesWaterVaporize()) {
/*    */       
/* 84 */       worldIn.setBlockToAir(pos);
/*    */     }
/*    */     else {
/*    */       
/* 88 */       dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 89 */       worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
/* 90 */       worldIn.func_190524_a(pos, Blocks.WATER, pos);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 96 */     return EnumPushReaction.NORMAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
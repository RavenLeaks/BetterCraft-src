/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockDeadBush
/*    */   extends BlockBush {
/* 22 */   protected static final AxisAlignedBB DEAD_BUSH_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
/*    */ 
/*    */   
/*    */   protected BlockDeadBush() {
/* 26 */     super(Material.VINE);
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 31 */     return DEAD_BUSH_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 39 */     return MapColor.WOOD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canSustainBush(IBlockState state) {
/* 47 */     return !(state.getBlock() != Blocks.SAND && state.getBlock() != Blocks.HARDENED_CLAY && state.getBlock() != Blocks.STAINED_HARDENED_CLAY && state.getBlock() != Blocks.DIRT);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 63 */     return random.nextInt(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 71 */     return Items.STICK;
/*    */   }
/*    */ 
/*    */   
/*    */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 76 */     if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
/*    */       
/* 78 */       player.addStat(StatList.getBlockStats(this));
/* 79 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.DEADBUSH, 1, 0));
/*    */     }
/*    */     else {
/*    */       
/* 83 */       super.harvestBlock(worldIn, player, pos, state, te, stack);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDeadBush.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
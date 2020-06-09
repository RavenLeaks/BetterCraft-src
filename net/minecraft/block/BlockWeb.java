/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.BlockFaceShape;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockWeb
/*    */   extends Block
/*    */ {
/*    */   public BlockWeb() {
/* 27 */     super(Material.WEB);
/* 28 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 36 */     entityIn.setInWeb();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube(IBlockState state) {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 50 */     return NULL_AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube(IBlockState state) {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 63 */     return Items.STRING;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSilkHarvest() {
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockRenderLayer getBlockLayer() {
/* 73 */     return BlockRenderLayer.CUTOUT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 78 */     if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
/*    */       
/* 80 */       player.addStat(StatList.getBlockStats(this));
/* 81 */       spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
/*    */     }
/*    */     else {
/*    */       
/* 85 */       super.harvestBlock(worldIn, player, pos, state, te, stack);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 91 */     return BlockFaceShape.UNDEFINED;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
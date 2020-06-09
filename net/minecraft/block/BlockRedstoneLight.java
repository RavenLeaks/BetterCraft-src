/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockRedstoneLight
/*    */   extends Block
/*    */ {
/*    */   private final boolean isOn;
/*    */   
/*    */   public BlockRedstoneLight(boolean isOn) {
/* 18 */     super(Material.REDSTONE_LIGHT);
/* 19 */     this.isOn = isOn;
/*    */     
/* 21 */     if (isOn)
/*    */     {
/* 23 */       setLightLevel(1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 32 */     if (!worldIn.isRemote)
/*    */     {
/* 34 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/*    */         
/* 36 */         worldIn.setBlockState(pos, Blocks.REDSTONE_LAMP.getDefaultState(), 2);
/*    */       }
/* 38 */       else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/*    */         
/* 40 */         worldIn.setBlockState(pos, Blocks.LIT_REDSTONE_LAMP.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 52 */     if (!worldIn.isRemote)
/*    */     {
/* 54 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/*    */         
/* 56 */         worldIn.scheduleUpdate(pos, this, 4);
/*    */       }
/* 58 */       else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/*    */         
/* 60 */         worldIn.setBlockState(pos, Blocks.LIT_REDSTONE_LAMP.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 67 */     if (!worldIn.isRemote)
/*    */     {
/* 69 */       if (this.isOn && !worldIn.isBlockPowered(pos))
/*    */       {
/* 71 */         worldIn.setBlockState(pos, Blocks.REDSTONE_LAMP.getDefaultState(), 2);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 81 */     return Item.getItemFromBlock(Blocks.REDSTONE_LAMP);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 86 */     return new ItemStack(Blocks.REDSTONE_LAMP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/* 91 */     return new ItemStack(Blocks.REDSTONE_LAMP);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
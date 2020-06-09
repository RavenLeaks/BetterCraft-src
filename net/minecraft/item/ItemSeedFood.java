/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeedFood
/*    */   extends ItemFood
/*    */ {
/*    */   private final Block crops;
/*    */   private final Block soilId;
/*    */   
/*    */   public ItemSeedFood(int healAmount, float saturation, Block crops, Block soil) {
/* 20 */     super(healAmount, saturation, false);
/* 21 */     this.crops = crops;
/* 22 */     this.soilId = soil;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 30 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 32 */     if (hand == EnumFacing.UP && stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack) && playerIn.getBlockState(worldIn).getBlock() == this.soilId && playerIn.isAirBlock(worldIn.up())) {
/*    */       
/* 34 */       playerIn.setBlockState(worldIn.up(), this.crops.getDefaultState(), 11);
/* 35 */       itemstack.func_190918_g(1);
/* 36 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 40 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSeedFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
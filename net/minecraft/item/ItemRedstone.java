/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/*    */   public ItemRedstone() {
/* 19 */     setCreativeTab(CreativeTabs.REDSTONE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 27 */     boolean flag = playerIn.getBlockState(worldIn).getBlock().isReplaceable((IBlockAccess)playerIn, worldIn);
/* 28 */     BlockPos blockpos = flag ? worldIn : worldIn.offset(hand);
/* 29 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 31 */     if (stack.canPlayerEdit(blockpos, hand, itemstack) && playerIn.func_190527_a(playerIn.getBlockState(blockpos).getBlock(), blockpos, false, hand, null) && Blocks.REDSTONE_WIRE.canPlaceBlockAt(playerIn, blockpos)) {
/*    */       
/* 33 */       playerIn.setBlockState(blockpos, Blocks.REDSTONE_WIRE.getDefaultState());
/*    */       
/* 35 */       if (stack instanceof EntityPlayerMP)
/*    */       {
/* 37 */         CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, blockpos, itemstack);
/*    */       }
/*    */       
/* 40 */       itemstack.func_190918_g(1);
/* 41 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemRedstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
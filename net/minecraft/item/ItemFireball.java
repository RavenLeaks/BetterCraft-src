/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFireball
/*    */   extends Item
/*    */ {
/*    */   public ItemFireball() {
/* 19 */     setCreativeTab(CreativeTabs.MISC);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 27 */     if (playerIn.isRemote)
/*    */     {
/* 29 */       return EnumActionResult.SUCCESS;
/*    */     }
/*    */ 
/*    */     
/* 33 */     worldIn = worldIn.offset(hand);
/* 34 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 36 */     if (!stack.canPlayerEdit(worldIn, hand, itemstack))
/*    */     {
/* 38 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 42 */     if (playerIn.getBlockState(worldIn).getMaterial() == Material.AIR) {
/*    */       
/* 44 */       playerIn.playSound(null, worldIn, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
/* 45 */       playerIn.setBlockState(worldIn, Blocks.FIRE.getDefaultState());
/*    */     } 
/*    */     
/* 48 */     if (!stack.capabilities.isCreativeMode)
/*    */     {
/* 50 */       itemstack.func_190918_g(1);
/*    */     }
/*    */     
/* 53 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
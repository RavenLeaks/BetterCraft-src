/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFlintAndSteel
/*    */   extends Item {
/*    */   public ItemFlintAndSteel() {
/* 21 */     this.maxStackSize = 1;
/* 22 */     setMaxDamage(64);
/* 23 */     setCreativeTab(CreativeTabs.TOOLS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 31 */     worldIn = worldIn.offset(hand);
/* 32 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 34 */     if (!stack.canPlayerEdit(worldIn, hand, itemstack))
/*    */     {
/* 36 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 40 */     if (playerIn.getBlockState(worldIn).getMaterial() == Material.AIR) {
/*    */       
/* 42 */       playerIn.playSound(stack, worldIn, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
/* 43 */       playerIn.setBlockState(worldIn, Blocks.FIRE.getDefaultState(), 11);
/*    */     } 
/*    */     
/* 46 */     if (stack instanceof EntityPlayerMP)
/*    */     {
/* 48 */       CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*    */     }
/*    */     
/* 51 */     itemstack.damageItem(1, (EntityLivingBase)stack);
/* 52 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFlintAndSteel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
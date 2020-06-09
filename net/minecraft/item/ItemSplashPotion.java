/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntityPotion;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.potion.PotionUtils;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSplashPotion extends ItemPotion {
/*    */   public String getItemStackDisplayName(ItemStack stack) {
/* 19 */     return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("splash_potion.effect."));
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 24 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 25 */     ItemStack itemstack1 = worldIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
/* 26 */     itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 28 */     if (!itemStackIn.isRemote) {
/*    */       
/* 30 */       EntityPotion entitypotion = new EntityPotion(itemStackIn, (EntityLivingBase)worldIn, itemstack1);
/* 31 */       entitypotion.setHeadingFromThrower((Entity)worldIn, worldIn.rotationPitch, worldIn.rotationYaw, -20.0F, 0.5F, 1.0F);
/* 32 */       itemStackIn.spawnEntityInWorld((Entity)entitypotion);
/*    */     } 
/*    */     
/* 35 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 36 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSplashPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
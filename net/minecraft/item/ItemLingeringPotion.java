/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.util.ITooltipFlag;
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
/*    */ public class ItemLingeringPotion extends ItemPotion {
/*    */   public String getItemStackDisplayName(ItemStack stack) {
/* 22 */     return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("lingering_potion.effect."));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 30 */     PotionUtils.addPotionTooltip(stack, tooltip, 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 35 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 36 */     ItemStack itemstack1 = worldIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
/* 37 */     itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_LINGERINGPOTION_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 39 */     if (!itemStackIn.isRemote) {
/*    */       
/* 41 */       EntityPotion entitypotion = new EntityPotion(itemStackIn, (EntityLivingBase)worldIn, itemstack1);
/* 42 */       entitypotion.setHeadingFromThrower((Entity)worldIn, worldIn.rotationPitch, worldIn.rotationYaw, -20.0F, 0.5F, 1.0F);
/* 43 */       itemStackIn.spawnEntityInWorld((Entity)entitypotion);
/*    */     } 
/*    */     
/* 46 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 47 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemLingeringPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityExpBottle;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemExpBottle extends Item {
/*    */   public ItemExpBottle() {
/* 18 */     setCreativeTab(CreativeTabs.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 28 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*    */     
/* 30 */     if (!worldIn.capabilities.isCreativeMode)
/*    */     {
/* 32 */       itemstack.func_190918_g(1);
/*    */     }
/*    */     
/* 35 */     itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_EXPERIENCE_BOTTLE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 37 */     if (!itemStackIn.isRemote) {
/*    */       
/* 39 */       EntityExpBottle entityexpbottle = new EntityExpBottle(itemStackIn, (EntityLivingBase)worldIn);
/* 40 */       entityexpbottle.setHeadingFromThrower((Entity)worldIn, worldIn.rotationPitch, worldIn.rotationYaw, -20.0F, 0.7F, 1.0F);
/* 41 */       itemStackIn.spawnEntityInWorld((Entity)entityexpbottle);
/*    */     } 
/*    */     
/* 44 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 45 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemExpBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
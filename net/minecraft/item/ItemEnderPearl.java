/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityEnderPearl;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemEnderPearl extends Item {
/*    */   public ItemEnderPearl() {
/* 18 */     this.maxStackSize = 16;
/* 19 */     setCreativeTab(CreativeTabs.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 24 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*    */     
/* 26 */     if (!worldIn.capabilities.isCreativeMode)
/*    */     {
/* 28 */       itemstack.func_190918_g(1);
/*    */     }
/*    */     
/* 31 */     itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 32 */     worldIn.getCooldownTracker().setCooldown(this, 20);
/*    */     
/* 34 */     if (!itemStackIn.isRemote) {
/*    */       
/* 36 */       EntityEnderPearl entityenderpearl = new EntityEnderPearl(itemStackIn, (EntityLivingBase)worldIn);
/* 37 */       entityenderpearl.setHeadingFromThrower((Entity)worldIn, worldIn.rotationPitch, worldIn.rotationYaw, 0.0F, 1.5F, 1.0F);
/* 38 */       itemStackIn.spawnEntityInWorld((Entity)entityenderpearl);
/*    */     } 
/*    */     
/* 41 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 42 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemEnderPearl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
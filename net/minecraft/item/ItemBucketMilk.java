/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBucketMilk
/*    */   extends Item
/*    */ {
/*    */   public ItemBucketMilk() {
/* 19 */     setMaxStackSize(1);
/* 20 */     setCreativeTab(CreativeTabs.MISC);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/* 29 */     if (entityLiving instanceof EntityPlayerMP) {
/*    */       
/* 31 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityLiving;
/* 32 */       CriteriaTriggers.field_193138_y.func_193148_a(entityplayermp, stack);
/* 33 */       entityplayermp.addStat(StatList.getObjectUseStats(this));
/*    */     } 
/*    */     
/* 36 */     if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
/*    */     {
/* 38 */       stack.func_190918_g(1);
/*    */     }
/*    */     
/* 41 */     if (!worldIn.isRemote)
/*    */     {
/* 43 */       entityLiving.clearActivePotions();
/*    */     }
/*    */     
/* 46 */     return stack.func_190926_b() ? new ItemStack(Items.BUCKET) : stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxItemUseDuration(ItemStack stack) {
/* 54 */     return 32;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumAction getItemUseAction(ItemStack stack) {
/* 62 */     return EnumAction.DRINK;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 67 */     worldIn.setActiveHand(playerIn);
/* 68 */     return new ActionResult(EnumActionResult.SUCCESS, worldIn.getHeldItem(playerIn));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBucketMilk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
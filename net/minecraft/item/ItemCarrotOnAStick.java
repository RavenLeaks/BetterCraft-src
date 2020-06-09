/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCarrotOnAStick
/*    */   extends Item {
/*    */   public ItemCarrotOnAStick() {
/* 17 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/* 18 */     setMaxStackSize(1);
/* 19 */     setMaxDamage(25);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRotateAroundWhenRendering() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 41 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*    */     
/* 43 */     if (itemStackIn.isRemote)
/*    */     {
/* 45 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */     }
/*    */ 
/*    */     
/* 49 */     if (worldIn.isRiding() && worldIn.getRidingEntity() instanceof EntityPig) {
/*    */       
/* 51 */       EntityPig entitypig = (EntityPig)worldIn.getRidingEntity();
/*    */       
/* 53 */       if (itemstack.getMaxDamage() - itemstack.getMetadata() >= 7 && entitypig.boost()) {
/*    */         
/* 55 */         itemstack.damageItem(7, (EntityLivingBase)worldIn);
/*    */         
/* 57 */         if (itemstack.func_190926_b()) {
/*    */           
/* 59 */           ItemStack itemstack1 = new ItemStack(Items.FISHING_ROD);
/* 60 */           itemstack1.setTagCompound(itemstack.getTagCompound());
/* 61 */           return new ActionResult(EnumActionResult.SUCCESS, itemstack1);
/*    */         } 
/*    */         
/* 64 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 69 */     return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemCarrotOnAStick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
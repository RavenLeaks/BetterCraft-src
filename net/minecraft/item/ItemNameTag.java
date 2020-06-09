/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class ItemNameTag
/*    */   extends Item
/*    */ {
/*    */   public ItemNameTag() {
/* 13 */     setCreativeTab(CreativeTabs.TOOLS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
/* 21 */     if (stack.hasDisplayName() && !(target instanceof EntityPlayer)) {
/*    */       
/* 23 */       target.setCustomNameTag(stack.getDisplayName());
/*    */       
/* 25 */       if (target instanceof EntityLiving)
/*    */       {
/* 27 */         ((EntityLiving)target).enablePersistence();
/*    */       }
/*    */       
/* 30 */       stack.func_190918_g(1);
/* 31 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemNameTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
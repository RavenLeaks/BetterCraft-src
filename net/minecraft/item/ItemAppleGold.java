/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemAppleGold
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemAppleGold(int amount, float saturation, boolean isWolfFood) {
/* 14 */     super(amount, saturation, isWolfFood);
/* 15 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 20 */     return !(!super.hasEffect(stack) && stack.getMetadata() <= 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 28 */     return (stack.getMetadata() == 0) ? EnumRarity.RARE : EnumRarity.EPIC;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/* 33 */     if (!worldIn.isRemote)
/*    */     {
/* 35 */       if (stack.getMetadata() > 0) {
/*    */         
/* 37 */         player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
/* 38 */         player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
/* 39 */         player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
/* 40 */         player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
/*    */       }
/*    */       else {
/*    */         
/* 44 */         player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
/* 45 */         player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 55 */     if (func_194125_a(itemIn)) {
/*    */       
/* 57 */       tab.add(new ItemStack(this));
/* 58 */       tab.add(new ItemStack(this, 1, 1));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemAppleGold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
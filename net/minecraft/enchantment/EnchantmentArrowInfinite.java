/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentArrowInfinite
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentArrowInfinite(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/*  9 */     super(rarityIn, EnumEnchantmentType.BOW, slots);
/* 10 */     setName("arrowInfinite");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 20;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 34 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApplyTogether(Enchantment ench) {
/* 42 */     return (ench instanceof EnchantmentMending) ? false : super.canApplyTogether(ench);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentArrowInfinite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
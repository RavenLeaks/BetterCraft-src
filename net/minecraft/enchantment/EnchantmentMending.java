/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentMending
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentMending(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/*  9 */     super(rarityIn, EnumEnchantmentType.BREAKABLE, slots);
/* 10 */     setName("mending");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return enchantmentLevel * 25;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTreasureEnchantment() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 39 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentMending.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
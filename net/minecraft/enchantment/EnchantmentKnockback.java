/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentKnockback
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentKnockback(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/*  9 */     super(rarityIn, EnumEnchantmentType.WEAPON, slots);
/* 10 */     setName("knockback");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 5 + 20 * (enchantmentLevel - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 34 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentKnockback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
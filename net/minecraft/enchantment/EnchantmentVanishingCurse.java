/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentVanishingCurse
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentVanishingCurse(Enchantment.Rarity p_i47252_1_, EntityEquipmentSlot... p_i47252_2_) {
/*  9 */     super(p_i47252_1_, EnumEnchantmentType.ALL, p_i47252_2_);
/* 10 */     setName("vanishing_curse");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 25;
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
/*    */   public boolean isTreasureEnchantment() {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_190936_d() {
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentVanishingCurse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
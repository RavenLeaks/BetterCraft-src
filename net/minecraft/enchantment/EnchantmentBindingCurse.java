/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentBindingCurse
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentBindingCurse(Enchantment.Rarity p_i47254_1_, EntityEquipmentSlot... p_i47254_2_) {
/*  9 */     super(p_i47254_1_, EnumEnchantmentType.WEARABLE, p_i47254_2_);
/* 10 */     setName("binding_curse");
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


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentBindingCurse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
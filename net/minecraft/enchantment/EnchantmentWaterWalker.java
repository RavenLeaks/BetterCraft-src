/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentWaterWalker
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentWaterWalker(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/* 10 */     super(rarityIn, EnumEnchantmentType.ARMOR_FEET, slots);
/* 11 */     setName("waterWalker");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 19 */     return enchantmentLevel * 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 27 */     return getMinEnchantability(enchantmentLevel) + 15;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 35 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApplyTogether(Enchantment ench) {
/* 43 */     return (super.canApplyTogether(ench) && ench != Enchantments.FROST_WALKER);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentWaterWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
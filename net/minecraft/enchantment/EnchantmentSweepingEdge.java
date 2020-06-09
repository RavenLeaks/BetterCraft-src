/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentSweepingEdge
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentSweepingEdge(Enchantment.Rarity p_i47366_1_, EntityEquipmentSlot... p_i47366_2_) {
/*  9 */     super(p_i47366_1_, EnumEnchantmentType.WEAPON, p_i47366_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 17 */     return 5 + (enchantmentLevel - 1) * 9;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 25 */     return getMinEnchantability(enchantmentLevel) + 15;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 33 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float func_191526_e(int p_191526_0_) {
/* 38 */     return 1.0F - 1.0F / (p_191526_0_ + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 46 */     return "enchantment.sweeping";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentSweepingEdge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
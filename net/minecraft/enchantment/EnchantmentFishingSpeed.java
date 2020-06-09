/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentFishingSpeed
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentFishingSpeed(Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot... slots) {
/*  9 */     super(rarityIn, typeIn, slots);
/* 10 */     setName("fishingSpeed");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 15 + (enchantmentLevel - 1) * 9;
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
/* 34 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentFishingSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentLootBonus
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentLootBonus(Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot... slots) {
/* 10 */     super(rarityIn, typeIn, slots);
/*    */     
/* 12 */     if (typeIn == EnumEnchantmentType.DIGGER) {
/*    */       
/* 14 */       setName("lootBonusDigger");
/*    */     }
/* 16 */     else if (typeIn == EnumEnchantmentType.FISHING_ROD) {
/*    */       
/* 18 */       setName("lootBonusFishing");
/*    */     }
/*    */     else {
/*    */       
/* 22 */       setName("lootBonus");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 31 */     return 15 + (enchantmentLevel - 1) * 9;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 39 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 47 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApplyTogether(Enchantment ench) {
/* 55 */     return (super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentLootBonus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
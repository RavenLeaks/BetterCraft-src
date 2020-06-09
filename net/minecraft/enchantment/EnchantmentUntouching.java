/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class EnchantmentUntouching
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentUntouching(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/* 10 */     super(rarityIn, EnumEnchantmentType.DIGGER, slots);
/* 11 */     setName("untouching");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 19 */     return 15;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 27 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 35 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApplyTogether(Enchantment ench) {
/* 43 */     return (super.canApplyTogether(ench) && ench != Enchantments.FORTUNE);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentUntouching.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
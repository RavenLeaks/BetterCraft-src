/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*    */ 
/*    */ public class PotionAttackDamage
/*    */   extends Potion
/*    */ {
/*    */   protected final double bonusPerLevel;
/*    */   
/*    */   protected PotionAttackDamage(boolean isBadEffectIn, int liquidColorIn, double bonusPerLevelIn) {
/* 11 */     super(isBadEffectIn, liquidColorIn);
/* 12 */     this.bonusPerLevel = bonusPerLevelIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
/* 17 */     return this.bonusPerLevel * (amplifier + 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionAttackDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
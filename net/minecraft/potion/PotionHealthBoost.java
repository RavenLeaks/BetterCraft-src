/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
/*    */ 
/*    */ public class PotionHealthBoost
/*    */   extends Potion
/*    */ {
/*    */   public PotionHealthBoost(boolean isBadEffectIn, int liquidColorIn) {
/* 10 */     super(isBadEffectIn, liquidColorIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
/* 15 */     super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
/*    */     
/* 17 */     if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth())
/*    */     {
/* 19 */       entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionHealthBoost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
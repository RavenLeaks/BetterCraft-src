/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class CombatRules
/*    */ {
/*    */   public static float getDamageAfterAbsorb(float damage, float totalArmor, float toughnessAttribute) {
/*  9 */     float f = 2.0F + toughnessAttribute / 4.0F;
/* 10 */     float f1 = MathHelper.clamp(totalArmor - damage / f, totalArmor * 0.2F, 20.0F);
/* 11 */     return damage * (1.0F - f1 / 25.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float getDamageAfterMagicAbsorb(float p_188401_0_, float p_188401_1_) {
/* 16 */     float f = MathHelper.clamp(p_188401_1_, 0.0F, 20.0F);
/* 17 */     return p_188401_0_ * (1.0F - f / 25.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CombatRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
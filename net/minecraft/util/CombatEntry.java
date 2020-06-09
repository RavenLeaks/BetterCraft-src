/*    */ package net.minecraft.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CombatEntry
/*    */ {
/*    */   private final DamageSource damageSrc;
/*    */   private final int time;
/*    */   private final float damage;
/*    */   private final float health;
/*    */   private final String fallSuffix;
/*    */   private final float fallDistance;
/*    */   
/*    */   public CombatEntry(DamageSource damageSrcIn, int p_i1564_2_, float healthAmount, float damageAmount, String fallSuffixIn, float fallDistanceIn) {
/* 18 */     this.damageSrc = damageSrcIn;
/* 19 */     this.time = p_i1564_2_;
/* 20 */     this.damage = damageAmount;
/* 21 */     this.health = healthAmount;
/* 22 */     this.fallSuffix = fallSuffixIn;
/* 23 */     this.fallDistance = fallDistanceIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DamageSource getDamageSrc() {
/* 31 */     return this.damageSrc;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDamage() {
/* 36 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLivingDamageSrc() {
/* 44 */     return this.damageSrc.getEntity() instanceof net.minecraft.entity.EntityLivingBase;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getFallSuffix() {
/* 50 */     return this.fallSuffix;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ITextComponent getDamageSrcDisplayName() {
/* 56 */     return (getDamageSrc().getEntity() == null) ? null : getDamageSrc().getEntity().getDisplayName();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDamageAmount() {
/* 61 */     return (this.damageSrc == DamageSource.outOfWorld) ? Float.MAX_VALUE : this.fallDistance;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CombatEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
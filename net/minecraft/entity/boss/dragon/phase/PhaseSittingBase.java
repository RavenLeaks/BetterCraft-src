/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import net.minecraft.entity.MultiPartEntityPart;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ 
/*    */ public abstract class PhaseSittingBase
/*    */   extends PhaseBase
/*    */ {
/*    */   public PhaseSittingBase(EntityDragon p_i46794_1_) {
/* 12 */     super(p_i46794_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIsStationary() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getAdjustedDamage(MultiPartEntityPart pt, DamageSource src, float damage) {
/* 26 */     if (src.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityArrow) {
/*    */       
/* 28 */       src.getSourceOfDamage().setFire(1);
/* 29 */       return 0.0F;
/*    */     } 
/*    */ 
/*    */     
/* 33 */     return super.getAdjustedDamage(pt, src, damage);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseSittingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
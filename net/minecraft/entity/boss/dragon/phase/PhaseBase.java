/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.MultiPartEntityPart;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public abstract class PhaseBase
/*    */   implements IPhase
/*    */ {
/*    */   protected final EntityDragon dragon;
/*    */   
/*    */   public PhaseBase(EntityDragon dragonIn) {
/* 19 */     this.dragon = dragonIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIsStationary() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doClientRenderEffects() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc, @Nullable EntityPlayer plyr) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeAreaEffect() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMaxRiseOrFall() {
/* 63 */     return 0.6F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getTargetLocation() {
/* 73 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getAdjustedDamage(MultiPartEntityPart pt, DamageSource src, float damage) {
/* 82 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getYawFactor() {
/* 87 */     float f = MathHelper.sqrt(this.dragon.motionX * this.dragon.motionX + this.dragon.motionZ * this.dragon.motionZ) + 1.0F;
/* 88 */     float f1 = Math.min(f, 40.0F);
/* 89 */     return 0.7F / f1 / f;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*    */ 
/*    */ public class PhaseLanding
/*    */   extends PhaseBase {
/*    */   private Vec3d targetLocation;
/*    */   
/*    */   public PhaseLanding(EntityDragon dragonIn) {
/* 16 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doClientRenderEffects() {
/* 25 */     Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).normalize();
/* 26 */     vec3d.rotateYaw(-0.7853982F);
/* 27 */     double d0 = this.dragon.dragonPartHead.posX;
/* 28 */     double d1 = this.dragon.dragonPartHead.posY + (this.dragon.dragonPartHead.height / 2.0F);
/* 29 */     double d2 = this.dragon.dragonPartHead.posZ;
/*    */     
/* 31 */     for (int i = 0; i < 8; i++) {
/*    */       
/* 33 */       double d3 = d0 + this.dragon.getRNG().nextGaussian() / 2.0D;
/* 34 */       double d4 = d1 + this.dragon.getRNG().nextGaussian() / 2.0D;
/* 35 */       double d5 = d2 + this.dragon.getRNG().nextGaussian() / 2.0D;
/* 36 */       this.dragon.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.xCoord * 0.07999999821186066D + this.dragon.motionX, -vec3d.yCoord * 0.30000001192092896D + this.dragon.motionY, -vec3d.zCoord * 0.07999999821186066D + this.dragon.motionZ, new int[0]);
/* 37 */       vec3d.rotateYaw(0.19634955F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 47 */     if (this.targetLocation == null)
/*    */     {
/* 49 */       this.targetLocation = new Vec3d((Vec3i)this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION));
/*    */     }
/*    */     
/* 52 */     if (this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ) < 1.0D) {
/*    */       
/* 54 */       ((PhaseSittingFlaming)this.dragon.getPhaseManager().<PhaseSittingFlaming>getPhase(PhaseList.SITTING_FLAMING)).resetFlameCount();
/* 55 */       this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMaxRiseOrFall() {
/* 64 */     return 1.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getYawFactor() {
/* 69 */     float f = MathHelper.sqrt(this.dragon.motionX * this.dragon.motionX + this.dragon.motionZ * this.dragon.motionZ) + 1.0F;
/* 70 */     float f1 = Math.min(f, 40.0F);
/* 71 */     return f1 / f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 79 */     this.targetLocation = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getTargetLocation() {
/* 89 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseLanding> getPhaseList() {
/* 94 */     return PhaseList.LANDING;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseLanding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
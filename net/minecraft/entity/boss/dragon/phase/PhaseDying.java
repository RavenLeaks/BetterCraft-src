/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*    */ 
/*    */ public class PhaseDying
/*    */   extends PhaseBase
/*    */ {
/*    */   private Vec3d targetLocation;
/*    */   private int time;
/*    */   
/*    */   public PhaseDying(EntityDragon dragonIn) {
/* 17 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doClientRenderEffects() {
/* 26 */     if (this.time++ % 10 == 0) {
/*    */       
/* 28 */       float f = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
/* 29 */       float f1 = (this.dragon.getRNG().nextFloat() - 0.5F) * 4.0F;
/* 30 */       float f2 = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
/* 31 */       this.dragon.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.dragon.posX + f, this.dragon.posY + 2.0D + f1, this.dragon.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 41 */     this.time++;
/*    */     
/* 43 */     if (this.targetLocation == null) {
/*    */       
/* 45 */       BlockPos blockpos = this.dragon.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION);
/* 46 */       this.targetLocation = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*    */     } 
/*    */     
/* 49 */     double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*    */     
/* 51 */     if (d0 >= 100.0D && d0 <= 22500.0D && !this.dragon.isCollidedHorizontally && !this.dragon.isCollidedVertically) {
/*    */       
/* 53 */       this.dragon.setHealth(1.0F);
/*    */     }
/*    */     else {
/*    */       
/* 57 */       this.dragon.setHealth(0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 66 */     this.targetLocation = null;
/* 67 */     this.time = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMaxRiseOrFall() {
/* 75 */     return 3.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getTargetLocation() {
/* 85 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseDying> getPhaseList() {
/* 90 */     return PhaseList.DYING;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseDying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
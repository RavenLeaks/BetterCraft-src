/*     */ package net.minecraft.entity.boss.dragon.phase;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*     */ 
/*     */ 
/*     */ public class PhaseTakeoff
/*     */   extends PhaseBase
/*     */ {
/*     */   private boolean firstTick;
/*     */   private Path currentPath;
/*     */   private Vec3d targetLocation;
/*     */   
/*     */   public PhaseTakeoff(EntityDragon dragonIn) {
/*  19 */     super(dragonIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLocalUpdate() {
/*  28 */     if (!this.firstTick && this.currentPath != null) {
/*     */       
/*  30 */       BlockPos blockpos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
/*  31 */       double d0 = this.dragon.getDistanceSqToCenter(blockpos);
/*     */       
/*  33 */       if (d0 > 100.0D)
/*     */       {
/*  35 */         this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  40 */       this.firstTick = false;
/*  41 */       findNewTarget();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPhase() {
/*  50 */     this.firstTick = true;
/*  51 */     this.currentPath = null;
/*  52 */     this.targetLocation = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewTarget() {
/*  57 */     int i = this.dragon.initPathPoints();
/*  58 */     Vec3d vec3d = this.dragon.getHeadLookVec(1.0F);
/*  59 */     int j = this.dragon.getNearestPpIdx(-vec3d.xCoord * 40.0D, 105.0D, -vec3d.zCoord * 40.0D);
/*     */     
/*  61 */     if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() > 0) {
/*     */       
/*  63 */       j %= 12;
/*     */       
/*  65 */       if (j < 0)
/*     */       {
/*  67 */         j += 12;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  72 */       j -= 12;
/*  73 */       j &= 0x7;
/*  74 */       j += 12;
/*     */     } 
/*     */     
/*  77 */     this.currentPath = this.dragon.findPath(i, j, null);
/*     */     
/*  79 */     if (this.currentPath != null) {
/*     */       
/*  81 */       this.currentPath.incrementPathIndex();
/*  82 */       navigateToNextPathNode();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void navigateToNextPathNode() {
/*     */     double d0;
/*  88 */     Vec3d vec3d = this.currentPath.getCurrentPos();
/*  89 */     this.currentPath.incrementPathIndex();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  94 */       d0 = vec3d.yCoord + (this.dragon.getRNG().nextFloat() * 20.0F);
/*     */     }
/*  96 */     while (d0 < vec3d.yCoord);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.targetLocation = new Vec3d(vec3d.xCoord, d0, vec3d.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getTargetLocation() {
/* 112 */     return this.targetLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public PhaseList<PhaseTakeoff> getPhaseList() {
/* 117 */     return PhaseList.TAKEOFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseTakeoff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
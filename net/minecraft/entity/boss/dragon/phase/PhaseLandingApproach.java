/*     */ package net.minecraft.entity.boss.dragon.phase;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*     */ 
/*     */ public class PhaseLandingApproach
/*     */   extends PhaseBase
/*     */ {
/*     */   private Path currentPath;
/*     */   private Vec3d targetLocation;
/*     */   
/*     */   public PhaseLandingApproach(EntityDragon dragonIn) {
/*  19 */     super(dragonIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public PhaseList<PhaseLandingApproach> getPhaseList() {
/*  24 */     return PhaseList.LANDING_APPROACH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPhase() {
/*  32 */     this.currentPath = null;
/*  33 */     this.targetLocation = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLocalUpdate() {
/*  42 */     double d0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*     */     
/*  44 */     if (d0 < 100.0D || d0 > 22500.0D || this.dragon.isCollidedHorizontally || this.dragon.isCollidedVertically)
/*     */     {
/*  46 */       findNewTarget();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getTargetLocation() {
/*  57 */     return this.targetLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewTarget() {
/*  62 */     if (this.currentPath == null || this.currentPath.isFinished()) {
/*     */       
/*  64 */       int j, i = this.dragon.initPathPoints();
/*  65 */       BlockPos blockpos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
/*  66 */       EntityPlayer entityplayer = this.dragon.world.getNearestAttackablePlayer(blockpos, 128.0D, 128.0D);
/*     */ 
/*     */       
/*  69 */       if (entityplayer != null) {
/*     */         
/*  71 */         Vec3d vec3d = (new Vec3d(entityplayer.posX, 0.0D, entityplayer.posZ)).normalize();
/*  72 */         j = this.dragon.getNearestPpIdx(-vec3d.xCoord * 40.0D, 105.0D, -vec3d.zCoord * 40.0D);
/*     */       }
/*     */       else {
/*     */         
/*  76 */         j = this.dragon.getNearestPpIdx(40.0D, blockpos.getY(), 0.0D);
/*     */       } 
/*     */       
/*  79 */       PathPoint pathpoint = new PathPoint(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*  80 */       this.currentPath = this.dragon.findPath(i, j, pathpoint);
/*     */       
/*  82 */       if (this.currentPath != null)
/*     */       {
/*  84 */         this.currentPath.incrementPathIndex();
/*     */       }
/*     */     } 
/*     */     
/*  88 */     navigateToNextPathNode();
/*     */     
/*  90 */     if (this.currentPath != null && this.currentPath.isFinished())
/*     */     {
/*  92 */       this.dragon.getPhaseManager().setPhase(PhaseList.LANDING);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void navigateToNextPathNode() {
/*  98 */     if (this.currentPath != null && !this.currentPath.isFinished()) {
/*     */       double d2;
/* 100 */       Vec3d vec3d = this.currentPath.getCurrentPos();
/* 101 */       this.currentPath.incrementPathIndex();
/* 102 */       double d0 = vec3d.xCoord;
/* 103 */       double d1 = vec3d.zCoord;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 108 */         d2 = vec3d.yCoord + (this.dragon.getRNG().nextFloat() * 20.0F);
/*     */       }
/* 110 */       while (d2 < vec3d.yCoord);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       this.targetLocation = new Vec3d(d0, d2, d1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseLandingApproach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
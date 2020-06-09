/*     */ package net.minecraft.entity.boss.dragon.phase;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*     */ 
/*     */ public class PhaseHoldingPattern
/*     */   extends PhaseBase {
/*     */   private Path currentPath;
/*     */   private Vec3d targetLocation;
/*     */   private boolean clockwise;
/*     */   
/*     */   public PhaseHoldingPattern(EntityDragon dragonIn) {
/*  23 */     super(dragonIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public PhaseList<PhaseHoldingPattern> getPhaseList() {
/*  28 */     return PhaseList.HOLDING_PATTERN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLocalUpdate() {
/*  37 */     double d0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*     */     
/*  39 */     if (d0 < 100.0D || d0 > 22500.0D || this.dragon.isCollidedHorizontally || this.dragon.isCollidedVertically)
/*     */     {
/*  41 */       findNewTarget();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPhase() {
/*  50 */     this.currentPath = null;
/*  51 */     this.targetLocation = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getTargetLocation() {
/*  61 */     return this.targetLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewTarget() {
/*  66 */     if (this.currentPath != null && this.currentPath.isFinished()) {
/*     */       
/*  68 */       BlockPos blockpos = this.dragon.world.getTopSolidOrLiquidBlock(new BlockPos((Vec3i)WorldGenEndPodium.END_PODIUM_LOCATION));
/*  69 */       int i = (this.dragon.getFightManager() == null) ? 0 : this.dragon.getFightManager().getNumAliveCrystals();
/*     */       
/*  71 */       if (this.dragon.getRNG().nextInt(i + 3) == 0) {
/*     */         
/*  73 */         this.dragon.getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
/*     */         
/*     */         return;
/*     */       } 
/*  77 */       double d0 = 64.0D;
/*  78 */       EntityPlayer entityplayer = this.dragon.world.getNearestAttackablePlayer(blockpos, d0, d0);
/*     */       
/*  80 */       if (entityplayer != null)
/*     */       {
/*  82 */         d0 = entityplayer.getDistanceSqToCenter(blockpos) / 512.0D;
/*     */       }
/*     */       
/*  85 */       if (entityplayer != null && (this.dragon.getRNG().nextInt(MathHelper.abs((int)d0) + 2) == 0 || this.dragon.getRNG().nextInt(i + 2) == 0)) {
/*     */         
/*  87 */         strafePlayer(entityplayer);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  92 */     if (this.currentPath == null || this.currentPath.isFinished()) {
/*     */       
/*  94 */       int j = this.dragon.initPathPoints();
/*  95 */       int k = j;
/*     */       
/*  97 */       if (this.dragon.getRNG().nextInt(8) == 0) {
/*     */         
/*  99 */         this.clockwise = !this.clockwise;
/* 100 */         k = j + 6;
/*     */       } 
/*     */       
/* 103 */       if (this.clockwise) {
/*     */         
/* 105 */         k++;
/*     */       }
/*     */       else {
/*     */         
/* 109 */         k--;
/*     */       } 
/*     */       
/* 112 */       if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() >= 0) {
/*     */         
/* 114 */         k %= 12;
/*     */         
/* 116 */         if (k < 0)
/*     */         {
/* 118 */           k += 12;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 123 */         k -= 12;
/* 124 */         k &= 0x7;
/* 125 */         k += 12;
/*     */       } 
/*     */       
/* 128 */       this.currentPath = this.dragon.findPath(j, k, null);
/*     */       
/* 130 */       if (this.currentPath != null)
/*     */       {
/* 132 */         this.currentPath.incrementPathIndex();
/*     */       }
/*     */     } 
/*     */     
/* 136 */     navigateToNextPathNode();
/*     */   }
/*     */ 
/*     */   
/*     */   private void strafePlayer(EntityPlayer player) {
/* 141 */     this.dragon.getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
/* 142 */     ((PhaseStrafePlayer)this.dragon.getPhaseManager().<PhaseStrafePlayer>getPhase(PhaseList.STRAFE_PLAYER)).setTarget((EntityLivingBase)player);
/*     */   }
/*     */ 
/*     */   
/*     */   private void navigateToNextPathNode() {
/* 147 */     if (this.currentPath != null && !this.currentPath.isFinished()) {
/*     */       double d2;
/* 149 */       Vec3d vec3d = this.currentPath.getCurrentPos();
/* 150 */       this.currentPath.incrementPathIndex();
/* 151 */       double d0 = vec3d.xCoord;
/* 152 */       double d1 = vec3d.zCoord;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 157 */         d2 = vec3d.yCoord + (this.dragon.getRNG().nextFloat() * 20.0F);
/*     */       }
/* 159 */       while (d2 < vec3d.yCoord);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       this.targetLocation = new Vec3d(d0, d2, d1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc, @Nullable EntityPlayer plyr) {
/* 171 */     if (plyr != null && !plyr.capabilities.disableDamage)
/*     */     {
/* 173 */       strafePlayer(plyr);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseHoldingPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
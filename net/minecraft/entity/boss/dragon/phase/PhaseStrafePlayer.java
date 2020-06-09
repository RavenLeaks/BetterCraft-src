/*     */ package net.minecraft.entity.boss.dragon.phase;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PhaseStrafePlayer
/*     */   extends PhaseBase {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private int fireballCharge;
/*     */   private Path currentPath;
/*     */   private Vec3d targetLocation;
/*     */   private EntityLivingBase attackTarget;
/*     */   private boolean holdingPatternClockwise;
/*     */   
/*     */   public PhaseStrafePlayer(EntityDragon dragonIn) {
/*  27 */     super(dragonIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLocalUpdate() {
/*  36 */     if (this.attackTarget == null) {
/*     */       
/*  38 */       LOGGER.warn("Skipping player strafe phase because no player was found");
/*  39 */       this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/*     */     }
/*     */     else {
/*     */       
/*  43 */       if (this.currentPath != null && this.currentPath.isFinished()) {
/*     */         
/*  45 */         double d0 = this.attackTarget.posX;
/*  46 */         double d1 = this.attackTarget.posZ;
/*  47 */         double d2 = d0 - this.dragon.posX;
/*  48 */         double d3 = d1 - this.dragon.posZ;
/*  49 */         double d4 = MathHelper.sqrt(d2 * d2 + d3 * d3);
/*  50 */         double d5 = Math.min(0.4000000059604645D + d4 / 80.0D - 1.0D, 10.0D);
/*  51 */         this.targetLocation = new Vec3d(d0, this.attackTarget.posY + d5, d1);
/*     */       } 
/*     */       
/*  54 */       double d12 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*     */       
/*  56 */       if (d12 < 100.0D || d12 > 22500.0D)
/*     */       {
/*  58 */         findNewTarget();
/*     */       }
/*     */       
/*  61 */       double d13 = 64.0D;
/*     */       
/*  63 */       if (this.attackTarget.getDistanceSqToEntity((Entity)this.dragon) < 4096.0D) {
/*     */         
/*  65 */         if (this.dragon.canEntityBeSeen((Entity)this.attackTarget)) {
/*     */           
/*  67 */           this.fireballCharge++;
/*  68 */           Vec3d vec3d1 = (new Vec3d(this.attackTarget.posX - this.dragon.posX, 0.0D, this.attackTarget.posZ - this.dragon.posZ)).normalize();
/*  69 */           Vec3d vec3d = (new Vec3d(MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, -MathHelper.cos(this.dragon.rotationYaw * 0.017453292F))).normalize();
/*  70 */           float f1 = (float)vec3d.dotProduct(vec3d1);
/*  71 */           float f = (float)(Math.acos(f1) * 57.29577951308232D);
/*  72 */           f += 0.5F;
/*     */           
/*  74 */           if (this.fireballCharge >= 5 && f >= 0.0F && f < 10.0F)
/*     */           {
/*  76 */             double d14 = 1.0D;
/*  77 */             Vec3d vec3d2 = this.dragon.getLook(1.0F);
/*  78 */             double d6 = this.dragon.dragonPartHead.posX - vec3d2.xCoord * 1.0D;
/*  79 */             double d7 = this.dragon.dragonPartHead.posY + (this.dragon.dragonPartHead.height / 2.0F) + 0.5D;
/*  80 */             double d8 = this.dragon.dragonPartHead.posZ - vec3d2.zCoord * 1.0D;
/*  81 */             double d9 = this.attackTarget.posX - d6;
/*  82 */             double d10 = this.attackTarget.posY + (this.attackTarget.height / 2.0F) - d7 + (this.dragon.dragonPartHead.height / 2.0F);
/*  83 */             double d11 = this.attackTarget.posZ - d8;
/*  84 */             this.dragon.world.playEvent(null, 1017, new BlockPos((Entity)this.dragon), 0);
/*  85 */             EntityDragonFireball entitydragonfireball = new EntityDragonFireball(this.dragon.world, (EntityLivingBase)this.dragon, d9, d10, d11);
/*  86 */             entitydragonfireball.setLocationAndAngles(d6, d7, d8, 0.0F, 0.0F);
/*  87 */             this.dragon.world.spawnEntityInWorld((Entity)entitydragonfireball);
/*  88 */             this.fireballCharge = 0;
/*     */             
/*  90 */             if (this.currentPath != null)
/*     */             {
/*  92 */               while (!this.currentPath.isFinished())
/*     */               {
/*  94 */                 this.currentPath.incrementPathIndex();
/*     */               }
/*     */             }
/*     */             
/*  98 */             this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/*     */           }
/*     */         
/* 101 */         } else if (this.fireballCharge > 0) {
/*     */           
/* 103 */           this.fireballCharge--;
/*     */         }
/*     */       
/* 106 */       } else if (this.fireballCharge > 0) {
/*     */         
/* 108 */         this.fireballCharge--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewTarget() {
/* 115 */     if (this.currentPath == null || this.currentPath.isFinished()) {
/*     */       
/* 117 */       int i = this.dragon.initPathPoints();
/* 118 */       int j = i;
/*     */       
/* 120 */       if (this.dragon.getRNG().nextInt(8) == 0) {
/*     */         
/* 122 */         this.holdingPatternClockwise = !this.holdingPatternClockwise;
/* 123 */         j = i + 6;
/*     */       } 
/*     */       
/* 126 */       if (this.holdingPatternClockwise) {
/*     */         
/* 128 */         j++;
/*     */       }
/*     */       else {
/*     */         
/* 132 */         j--;
/*     */       } 
/*     */       
/* 135 */       if (this.dragon.getFightManager() != null && this.dragon.getFightManager().getNumAliveCrystals() > 0) {
/*     */         
/* 137 */         j %= 12;
/*     */         
/* 139 */         if (j < 0)
/*     */         {
/* 141 */           j += 12;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 146 */         j -= 12;
/* 147 */         j &= 0x7;
/* 148 */         j += 12;
/*     */       } 
/*     */       
/* 151 */       this.currentPath = this.dragon.findPath(i, j, null);
/*     */       
/* 153 */       if (this.currentPath != null)
/*     */       {
/* 155 */         this.currentPath.incrementPathIndex();
/*     */       }
/*     */     } 
/*     */     
/* 159 */     navigateToNextPathNode();
/*     */   }
/*     */ 
/*     */   
/*     */   private void navigateToNextPathNode() {
/* 164 */     if (this.currentPath != null && !this.currentPath.isFinished()) {
/*     */       double d1;
/* 166 */       Vec3d vec3d = this.currentPath.getCurrentPos();
/* 167 */       this.currentPath.incrementPathIndex();
/* 168 */       double d0 = vec3d.xCoord;
/* 169 */       double d2 = vec3d.zCoord;
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 174 */         d1 = vec3d.yCoord + (this.dragon.getRNG().nextFloat() * 20.0F);
/*     */       }
/* 176 */       while (d1 < vec3d.yCoord);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       this.targetLocation = new Vec3d(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPhase() {
/* 191 */     this.fireballCharge = 0;
/* 192 */     this.targetLocation = null;
/* 193 */     this.currentPath = null;
/* 194 */     this.attackTarget = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(EntityLivingBase p_188686_1_) {
/* 199 */     this.attackTarget = p_188686_1_;
/* 200 */     int i = this.dragon.initPathPoints();
/* 201 */     int j = this.dragon.getNearestPpIdx(this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ);
/* 202 */     int k = MathHelper.floor(this.attackTarget.posX);
/* 203 */     int l = MathHelper.floor(this.attackTarget.posZ);
/* 204 */     double d0 = k - this.dragon.posX;
/* 205 */     double d1 = l - this.dragon.posZ;
/* 206 */     double d2 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/* 207 */     double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
/* 208 */     int i1 = MathHelper.floor(this.attackTarget.posY + d3);
/* 209 */     PathPoint pathpoint = new PathPoint(k, i1, l);
/* 210 */     this.currentPath = this.dragon.findPath(i, j, pathpoint);
/*     */     
/* 212 */     if (this.currentPath != null) {
/*     */       
/* 214 */       this.currentPath.incrementPathIndex();
/* 215 */       navigateToNextPathNode();
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
/* 226 */     return this.targetLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public PhaseList<PhaseStrafePlayer> getPhaseList() {
/* 231 */     return PhaseList.STRAFE_PLAYER;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseStrafePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
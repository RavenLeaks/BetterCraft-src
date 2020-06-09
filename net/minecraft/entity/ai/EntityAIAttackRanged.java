/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIAttackRanged
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityLiving entityHost;
/*     */   private final IRangedAttackMob rangedAttackEntityHost;
/*     */   private EntityLivingBase attackTarget;
/*     */   
/*     */   public EntityAIAttackRanged(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
/*  37 */     this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private final double entityMoveSpeed;
/*  42 */   private int rangedAttackTime = -1;
/*     */   public EntityAIAttackRanged(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
/*  44 */     if (!(attacker instanceof EntityLivingBase))
/*     */     {
/*  46 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*     */     }
/*     */ 
/*     */     
/*  50 */     this.rangedAttackEntityHost = attacker;
/*  51 */     this.entityHost = (EntityLiving)attacker;
/*  52 */     this.entityMoveSpeed = movespeed;
/*  53 */     this.attackIntervalMin = p_i1650_4_;
/*  54 */     this.maxRangedAttackTime = maxAttackTime;
/*  55 */     this.attackRadius = maxAttackDistanceIn;
/*  56 */     this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
/*  57 */     setMutexBits(3);
/*     */   }
/*     */   private int seeTime;
/*     */   private final int attackIntervalMin;
/*     */   private final int maxRangedAttackTime;
/*     */   private final float attackRadius;
/*     */   private final float maxAttackDistance;
/*     */   
/*     */   public boolean shouldExecute() {
/*  66 */     EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();
/*     */     
/*  68 */     if (entitylivingbase == null)
/*     */     {
/*  70 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  74 */     this.attackTarget = entitylivingbase;
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  84 */     return !(!shouldExecute() && this.entityHost.getNavigator().noPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  92 */     this.attackTarget = null;
/*  93 */     this.seeTime = 0;
/*  94 */     this.rangedAttackTime = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 102 */     double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, (this.attackTarget.getEntityBoundingBox()).minY, this.attackTarget.posZ);
/* 103 */     boolean flag = this.entityHost.getEntitySenses().canSee((Entity)this.attackTarget);
/*     */     
/* 105 */     if (flag) {
/*     */       
/* 107 */       this.seeTime++;
/*     */     }
/*     */     else {
/*     */       
/* 111 */       this.seeTime = 0;
/*     */     } 
/*     */     
/* 114 */     if (d0 <= this.maxAttackDistance && this.seeTime >= 20) {
/*     */       
/* 116 */       this.entityHost.getNavigator().clearPathEntity();
/*     */     }
/*     */     else {
/*     */       
/* 120 */       this.entityHost.getNavigator().tryMoveToEntityLiving((Entity)this.attackTarget, this.entityMoveSpeed);
/*     */     } 
/*     */     
/* 123 */     this.entityHost.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F);
/*     */     
/* 125 */     if (--this.rangedAttackTime == 0) {
/*     */       
/* 127 */       if (!flag) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 132 */       float f = MathHelper.sqrt(d0) / this.attackRadius;
/* 133 */       float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
/* 134 */       this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
/* 135 */       this.rangedAttackTime = MathHelper.floor(f * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin);
/*     */     }
/* 137 */     else if (this.rangedAttackTime < 0) {
/*     */       
/* 139 */       float f2 = MathHelper.sqrt(d0) / this.attackRadius;
/* 140 */       this.rangedAttackTime = MathHelper.floor(f2 * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIAttackRanged.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
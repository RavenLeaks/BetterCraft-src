/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
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
/*     */ public class EntityAIAttackMelee
/*     */   extends EntityAIBase
/*     */ {
/*     */   World worldObj;
/*     */   protected EntityCreature attacker;
/*     */   protected int attackTick;
/*     */   double speedTowardsTarget;
/*     */   boolean longMemory;
/*     */   Path entityPathEntity;
/*     */   private int delayCounter;
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*  35 */   protected final int attackInterval = 20;
/*     */ 
/*     */   
/*     */   public EntityAIAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
/*  39 */     this.attacker = creature;
/*  40 */     this.worldObj = creature.world;
/*  41 */     this.speedTowardsTarget = speedIn;
/*  42 */     this.longMemory = useLongMemory;
/*  43 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  51 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*     */     
/*  53 */     if (entitylivingbase == null)
/*     */     {
/*  55 */       return false;
/*     */     }
/*  57 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  59 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  63 */     this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving((Entity)entitylivingbase);
/*     */     
/*  65 */     if (this.entityPathEntity != null)
/*     */     {
/*  67 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  71 */     return (getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, (entitylivingbase.getEntityBoundingBox()).minY, entitylivingbase.posZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  81 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*     */     
/*  83 */     if (entitylivingbase == null)
/*     */     {
/*  85 */       return false;
/*     */     }
/*  87 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  89 */       return false;
/*     */     }
/*  91 */     if (!this.longMemory)
/*     */     {
/*  93 */       return !this.attacker.getNavigator().noPath();
/*     */     }
/*  95 */     if (!this.attacker.isWithinHomeDistanceFromPosition(new BlockPos((Entity)entitylivingbase)))
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 101 */     return !(entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 110 */     this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
/* 111 */     this.delayCounter = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 119 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*     */     
/* 121 */     if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
/*     */     {
/* 123 */       this.attacker.setAttackTarget(null);
/*     */     }
/*     */     
/* 126 */     this.attacker.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 134 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/* 135 */     this.attacker.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 30.0F, 30.0F);
/* 136 */     double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, (entitylivingbase.getEntityBoundingBox()).minY, entitylivingbase.posZ);
/* 137 */     this.delayCounter--;
/*     */     
/* 139 */     if ((this.longMemory || this.attacker.getEntitySenses().canSee((Entity)entitylivingbase)) && this.delayCounter <= 0 && ((this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D) || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
/*     */       
/* 141 */       this.targetX = entitylivingbase.posX;
/* 142 */       this.targetY = (entitylivingbase.getEntityBoundingBox()).minY;
/* 143 */       this.targetZ = entitylivingbase.posZ;
/* 144 */       this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
/*     */       
/* 146 */       if (d0 > 1024.0D) {
/*     */         
/* 148 */         this.delayCounter += 10;
/*     */       }
/* 150 */       else if (d0 > 256.0D) {
/*     */         
/* 152 */         this.delayCounter += 5;
/*     */       } 
/*     */       
/* 155 */       if (!this.attacker.getNavigator().tryMoveToEntityLiving((Entity)entitylivingbase, this.speedTowardsTarget))
/*     */       {
/* 157 */         this.delayCounter += 15;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     this.attackTick = Math.max(this.attackTick - 1, 0);
/* 162 */     checkAndPerformAttack(entitylivingbase, d0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
/* 167 */     double d0 = getAttackReachSqr(p_190102_1_);
/*     */     
/* 169 */     if (p_190102_2_ <= d0 && this.attackTick <= 0) {
/*     */       
/* 171 */       this.attackTick = 20;
/* 172 */       this.attacker.swingArm(EnumHand.MAIN_HAND);
/* 173 */       this.attacker.attackEntityAsMob((Entity)p_190102_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getAttackReachSqr(EntityLivingBase attackTarget) {
/* 179 */     return (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIAttackMelee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
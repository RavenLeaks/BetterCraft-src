/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.math.BlockPos;
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
/*     */ 
/*     */ public abstract class EntityAITarget
/*     */   extends EntityAIBase
/*     */ {
/*     */   protected final EntityCreature taskOwner;
/*     */   protected boolean shouldCheckSight;
/*     */   private final boolean nearbyOnly;
/*     */   private int targetSearchStatus;
/*     */   private int targetSearchDelay;
/*     */   private int targetUnseenTicks;
/*     */   protected EntityLivingBase target;
/*     */   protected int unseenMemoryTicks;
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight) {
/*  52 */     this(creature, checkSight, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
/*  57 */     this.unseenMemoryTicks = 60;
/*  58 */     this.taskOwner = creature;
/*  59 */     this.shouldCheckSight = checkSight;
/*  60 */     this.nearbyOnly = onlyNearby;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  68 */     EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/*     */     
/*  70 */     if (entitylivingbase == null)
/*     */     {
/*  72 */       entitylivingbase = this.target;
/*     */     }
/*     */     
/*  75 */     if (entitylivingbase == null)
/*     */     {
/*  77 */       return false;
/*     */     }
/*  79 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  85 */     Team team = this.taskOwner.getTeam();
/*  86 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/*  88 */     if (team != null && team1 == team)
/*     */     {
/*  90 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  94 */     double d0 = getTargetDistance();
/*     */     
/*  96 */     if (this.taskOwner.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0)
/*     */     {
/*  98 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (this.shouldCheckSight)
/*     */     {
/* 104 */       if (this.taskOwner.getEntitySenses().canSee((Entity)entitylivingbase)) {
/*     */         
/* 106 */         this.targetUnseenTicks = 0;
/*     */       }
/* 108 */       else if (++this.targetUnseenTicks > this.unseenMemoryTicks) {
/*     */         
/* 110 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 114 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */     {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 120 */     this.taskOwner.setAttackTarget(entitylivingbase);
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getTargetDistance() {
/* 130 */     IAttributeInstance iattributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
/* 131 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 139 */     this.targetSearchStatus = 0;
/* 140 */     this.targetSearchDelay = 0;
/* 141 */     this.targetUnseenTicks = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 149 */     this.taskOwner.setAttackTarget(null);
/* 150 */     this.target = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSuitableTarget(EntityLiving attacker, @Nullable EntityLivingBase target, boolean includeInvincibles, boolean checkSight) {
/* 158 */     if (target == null)
/*     */     {
/* 160 */       return false;
/*     */     }
/* 162 */     if (target == attacker)
/*     */     {
/* 164 */       return false;
/*     */     }
/* 166 */     if (!target.isEntityAlive())
/*     */     {
/* 168 */       return false;
/*     */     }
/* 170 */     if (!attacker.canAttackClass(target.getClass()))
/*     */     {
/* 172 */       return false;
/*     */     }
/* 174 */     if (attacker.isOnSameTeam((Entity)target))
/*     */     {
/* 176 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 180 */     if (attacker instanceof IEntityOwnable && ((IEntityOwnable)attacker).getOwnerId() != null) {
/*     */       
/* 182 */       if (target instanceof IEntityOwnable && ((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)target).getOwnerId()))
/*     */       {
/* 184 */         return false;
/*     */       }
/*     */       
/* 187 */       if (target == ((IEntityOwnable)attacker).getOwner())
/*     */       {
/* 189 */         return false;
/*     */       }
/*     */     }
/* 192 */     else if (target instanceof EntityPlayer && !includeInvincibles && ((EntityPlayer)target).capabilities.disableDamage) {
/*     */       
/* 194 */       return false;
/*     */     } 
/*     */     
/* 197 */     return !(checkSight && !attacker.getEntitySenses().canSee((Entity)target));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSuitableTarget(@Nullable EntityLivingBase target, boolean includeInvincibles) {
/* 207 */     if (!isSuitableTarget((EntityLiving)this.taskOwner, target, includeInvincibles, this.shouldCheckSight))
/*     */     {
/* 209 */       return false;
/*     */     }
/* 211 */     if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos((Entity)target)))
/*     */     {
/* 213 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 217 */     if (this.nearbyOnly) {
/*     */       
/* 219 */       if (--this.targetSearchDelay <= 0)
/*     */       {
/* 221 */         this.targetSearchStatus = 0;
/*     */       }
/*     */       
/* 224 */       if (this.targetSearchStatus == 0)
/*     */       {
/* 226 */         this.targetSearchStatus = canEasilyReach(target) ? 1 : 2;
/*     */       }
/*     */       
/* 229 */       if (this.targetSearchStatus == 2)
/*     */       {
/* 231 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 235 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canEasilyReach(EntityLivingBase target) {
/* 244 */     this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
/* 245 */     Path path = this.taskOwner.getNavigator().getPathToEntityLiving((Entity)target);
/*     */     
/* 247 */     if (path == null)
/*     */     {
/* 249 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 253 */     PathPoint pathpoint = path.getFinalPathPoint();
/*     */     
/* 255 */     if (pathpoint == null)
/*     */     {
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 261 */     int i = pathpoint.xCoord - MathHelper.floor(target.posX);
/* 262 */     int j = pathpoint.zCoord - MathHelper.floor(target.posZ);
/* 263 */     return ((i * i + j * j) <= 2.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAITarget func_190882_b(int p_190882_1_) {
/* 270 */     this.unseenMemoryTicks = p_190882_1_;
/* 271 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAITarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
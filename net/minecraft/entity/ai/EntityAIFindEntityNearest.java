/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAIFindEntityNearest
/*     */   extends EntityAIBase {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final EntityLiving mob;
/*     */   private final Predicate<EntityLivingBase> predicate;
/*     */   private final EntityAINearestAttackableTarget.Sorter sorter;
/*     */   private EntityLivingBase target;
/*     */   private final Class<? extends EntityLivingBase> classToCheck;
/*     */   
/*     */   public EntityAIFindEntityNearest(EntityLiving mobIn, Class<? extends EntityLivingBase> p_i45884_2_) {
/*  27 */     this.mob = mobIn;
/*  28 */     this.classToCheck = p_i45884_2_;
/*     */     
/*  30 */     if (mobIn instanceof net.minecraft.entity.EntityCreature)
/*     */     {
/*  32 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  35 */     this.predicate = new Predicate<EntityLivingBase>()
/*     */       {
/*     */         public boolean apply(@Nullable EntityLivingBase p_apply_1_)
/*     */         {
/*  39 */           double d0 = EntityAIFindEntityNearest.this.getFollowRange();
/*     */           
/*  41 */           if (p_apply_1_.isSneaking())
/*     */           {
/*  43 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  46 */           if (p_apply_1_.isInvisible())
/*     */           {
/*  48 */             return false;
/*     */           }
/*     */ 
/*     */           
/*  52 */           return (p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearest.this.mob) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearest.this.mob, p_apply_1_, false, true);
/*     */         }
/*     */       };
/*     */     
/*  56 */     this.sorter = new EntityAINearestAttackableTarget.Sorter((Entity)mobIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  64 */     double d0 = getFollowRange();
/*  65 */     List<EntityLivingBase> list = this.mob.world.getEntitiesWithinAABB(this.classToCheck, this.mob.getEntityBoundingBox().expand(d0, 4.0D, d0), this.predicate);
/*  66 */     Collections.sort(list, this.sorter);
/*     */     
/*  68 */     if (list.isEmpty())
/*     */     {
/*  70 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  74 */     this.target = list.get(0);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  84 */     EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
/*     */     
/*  86 */     if (entitylivingbase == null)
/*     */     {
/*  88 */       return false;
/*     */     }
/*  90 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  92 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  96 */     double d0 = getFollowRange();
/*     */     
/*  98 */     if (this.mob.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0)
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 104 */     return !(entitylivingbase instanceof EntityPlayerMP && ((EntityPlayerMP)entitylivingbase).interactionManager.isCreative());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 114 */     this.mob.setAttackTarget(this.target);
/* 115 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 123 */     this.mob.setAttackTarget(null);
/* 124 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getFollowRange() {
/* 129 */     IAttributeInstance iattributeinstance = this.mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
/* 130 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
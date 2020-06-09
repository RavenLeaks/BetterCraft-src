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
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAIFindEntityNearestPlayer
/*     */   extends EntityAIBase
/*     */ {
/*  21 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final EntityLiving entityLiving;
/*     */ 
/*     */   
/*     */   private final Predicate<Entity> predicate;
/*     */   
/*     */   private final EntityAINearestAttackableTarget.Sorter sorter;
/*     */   
/*     */   private EntityLivingBase entityTarget;
/*     */ 
/*     */   
/*     */   public EntityAIFindEntityNearestPlayer(EntityLiving entityLivingIn) {
/*  35 */     this.entityLiving = entityLivingIn;
/*     */     
/*  37 */     if (entityLivingIn instanceof net.minecraft.entity.EntityCreature)
/*     */     {
/*  39 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  42 */     this.predicate = new Predicate<Entity>()
/*     */       {
/*     */         public boolean apply(@Nullable Entity p_apply_1_)
/*     */         {
/*  46 */           if (!(p_apply_1_ instanceof EntityPlayer))
/*     */           {
/*  48 */             return false;
/*     */           }
/*  50 */           if (((EntityPlayer)p_apply_1_).capabilities.disableDamage)
/*     */           {
/*  52 */             return false;
/*     */           }
/*     */ 
/*     */           
/*  56 */           double d0 = EntityAIFindEntityNearestPlayer.this.maxTargetRange();
/*     */           
/*  58 */           if (p_apply_1_.isSneaking())
/*     */           {
/*  60 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  63 */           if (p_apply_1_.isInvisible()) {
/*     */             
/*  65 */             float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();
/*     */             
/*  67 */             if (f < 0.1F)
/*     */             {
/*  69 */               f = 0.1F;
/*     */             }
/*     */             
/*  72 */             d0 *= (0.7F * f);
/*     */           } 
/*     */           
/*  75 */           return (p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearestPlayer.this.entityLiving) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearestPlayer.this.entityLiving, (EntityLivingBase)p_apply_1_, false, true);
/*     */         }
/*     */       };
/*     */     
/*  79 */     this.sorter = new EntityAINearestAttackableTarget.Sorter((Entity)entityLivingIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  87 */     double d0 = maxTargetRange();
/*  88 */     List<EntityPlayer> list = this.entityLiving.world.getEntitiesWithinAABB(EntityPlayer.class, this.entityLiving.getEntityBoundingBox().expand(d0, 4.0D, d0), this.predicate);
/*  89 */     Collections.sort(list, this.sorter);
/*     */     
/*  91 */     if (list.isEmpty())
/*     */     {
/*  93 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  97 */     this.entityTarget = (EntityLivingBase)list.get(0);
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/* 107 */     EntityLivingBase entitylivingbase = this.entityLiving.getAttackTarget();
/*     */     
/* 109 */     if (entitylivingbase == null)
/*     */     {
/* 111 */       return false;
/*     */     }
/* 113 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/* 115 */       return false;
/*     */     }
/* 117 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */     {
/* 119 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 123 */     Team team = this.entityLiving.getTeam();
/* 124 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/* 126 */     if (team != null && team1 == team)
/*     */     {
/* 128 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 132 */     double d0 = maxTargetRange();
/*     */     
/* 134 */     if (this.entityLiving.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0)
/*     */     {
/* 136 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 140 */     return !(entitylivingbase instanceof EntityPlayerMP && ((EntityPlayerMP)entitylivingbase).interactionManager.isCreative());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 151 */     this.entityLiving.setAttackTarget(this.entityTarget);
/* 152 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 160 */     this.entityLiving.setAttackTarget(null);
/* 161 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double maxTargetRange() {
/* 169 */     IAttributeInstance iattributeinstance = this.entityLiving.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
/* 170 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearestPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
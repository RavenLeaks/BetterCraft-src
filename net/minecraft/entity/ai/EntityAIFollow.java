/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ 
/*     */ 
/*     */ public class EntityAIFollow
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityLiving field_192372_a;
/*     */   private final Predicate<EntityLiving> field_192373_b;
/*     */   private EntityLiving field_192374_c;
/*     */   private final double field_192375_d;
/*     */   private final PathNavigate field_192376_e;
/*     */   private int field_192377_f;
/*     */   private final float field_192378_g;
/*     */   private float field_192379_h;
/*     */   private final float field_192380_i;
/*     */   
/*     */   public EntityAIFollow(final EntityLiving p_i47417_1_, double p_i47417_2_, float p_i47417_4_, float p_i47417_5_) {
/*  26 */     this.field_192372_a = p_i47417_1_;
/*  27 */     this.field_192373_b = new Predicate<EntityLiving>()
/*     */       {
/*     */         public boolean apply(@Nullable EntityLiving p_apply_1_)
/*     */         {
/*  31 */           return (p_apply_1_ != null && p_i47417_1_.getClass() != p_apply_1_.getClass());
/*     */         }
/*     */       };
/*  34 */     this.field_192375_d = p_i47417_2_;
/*  35 */     this.field_192376_e = p_i47417_1_.getNavigator();
/*  36 */     this.field_192378_g = p_i47417_4_;
/*  37 */     this.field_192380_i = p_i47417_5_;
/*  38 */     setMutexBits(3);
/*     */     
/*  40 */     if (!(p_i47417_1_.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) && !(p_i47417_1_.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateFlying))
/*     */     {
/*  42 */       throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  51 */     List<EntityLiving> list = this.field_192372_a.world.getEntitiesWithinAABB(EntityLiving.class, this.field_192372_a.getEntityBoundingBox().expandXyz(this.field_192380_i), this.field_192373_b);
/*     */     
/*  53 */     if (!list.isEmpty())
/*     */     {
/*  55 */       for (EntityLiving entityliving : list) {
/*     */         
/*  57 */         if (!entityliving.isInvisible()) {
/*     */           
/*  59 */           this.field_192374_c = entityliving;
/*  60 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  73 */     return (this.field_192374_c != null && !this.field_192376_e.noPath() && this.field_192372_a.getDistanceSqToEntity((Entity)this.field_192374_c) > (this.field_192378_g * this.field_192378_g));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  81 */     this.field_192377_f = 0;
/*  82 */     this.field_192379_h = this.field_192372_a.getPathPriority(PathNodeType.WATER);
/*  83 */     this.field_192372_a.setPathPriority(PathNodeType.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  91 */     this.field_192374_c = null;
/*  92 */     this.field_192376_e.clearPathEntity();
/*  93 */     this.field_192372_a.setPathPriority(PathNodeType.WATER, this.field_192379_h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 101 */     if (this.field_192374_c != null && !this.field_192372_a.getLeashed()) {
/*     */       
/* 103 */       this.field_192372_a.getLookHelper().setLookPositionWithEntity((Entity)this.field_192374_c, 10.0F, this.field_192372_a.getVerticalFaceSpeed());
/*     */       
/* 105 */       if (--this.field_192377_f <= 0) {
/*     */         
/* 107 */         this.field_192377_f = 10;
/* 108 */         double d0 = this.field_192372_a.posX - this.field_192374_c.posX;
/* 109 */         double d1 = this.field_192372_a.posY - this.field_192374_c.posY;
/* 110 */         double d2 = this.field_192372_a.posZ - this.field_192374_c.posZ;
/* 111 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 113 */         if (d3 > (this.field_192378_g * this.field_192378_g)) {
/*     */           
/* 115 */           this.field_192376_e.tryMoveToEntityLiving((Entity)this.field_192374_c, this.field_192375_d);
/*     */         }
/*     */         else {
/*     */           
/* 119 */           this.field_192376_e.clearPathEntity();
/* 120 */           EntityLookHelper entitylookhelper = this.field_192374_c.getLookHelper();
/*     */           
/* 122 */           if (d3 <= this.field_192378_g || (entitylookhelper.getLookPosX() == this.field_192372_a.posX && entitylookhelper.getLookPosY() == this.field_192372_a.posY && entitylookhelper.getLookPosZ() == this.field_192372_a.posZ)) {
/*     */             
/* 124 */             double d4 = this.field_192374_c.posX - this.field_192372_a.posX;
/* 125 */             double d5 = this.field_192374_c.posZ - this.field_192372_a.posZ;
/* 126 */             this.field_192376_e.tryMoveToXYZ(this.field_192372_a.posX - d4, this.field_192372_a.posY, this.field_192372_a.posZ - d5, this.field_192375_d);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFollow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
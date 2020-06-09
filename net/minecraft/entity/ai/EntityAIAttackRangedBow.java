/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemBow;
/*     */ import net.minecraft.util.EnumHand;
/*     */ 
/*     */ public class EntityAIAttackRangedBow<T extends EntityMob & IRangedAttackMob> extends EntityAIBase {
/*     */   private final T entity;
/*     */   private final double moveSpeedAmp;
/*     */   private int attackCooldown;
/*     */   private final float maxAttackDistance;
/*  16 */   private int attackTime = -1;
/*     */   private int seeTime;
/*     */   private boolean strafingClockwise;
/*     */   private boolean strafingBackwards;
/*  20 */   private int strafingTime = -1;
/*     */ 
/*     */   
/*     */   public EntityAIAttackRangedBow(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_) {
/*  24 */     this.entity = p_i47515_1_;
/*  25 */     this.moveSpeedAmp = p_i47515_2_;
/*  26 */     this.attackCooldown = p_i47515_4_;
/*  27 */     this.maxAttackDistance = p_i47515_5_ * p_i47515_5_;
/*  28 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttackCooldown(int p_189428_1_) {
/*  33 */     this.attackCooldown = p_189428_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  41 */     return (this.entity.getAttackTarget() == null) ? false : isBowInMainhand();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isBowInMainhand() {
/*  46 */     return (!this.entity.getHeldItemMainhand().func_190926_b() && this.entity.getHeldItemMainhand().getItem() == Items.BOW);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  54 */     return ((shouldExecute() || !this.entity.getNavigator().noPath()) && isBowInMainhand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  62 */     super.startExecuting();
/*  63 */     ((IRangedAttackMob)this.entity).setSwingingArms(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  71 */     super.resetTask();
/*  72 */     ((IRangedAttackMob)this.entity).setSwingingArms(false);
/*  73 */     this.seeTime = 0;
/*  74 */     this.attackTime = -1;
/*  75 */     this.entity.resetActiveHand();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  83 */     EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
/*     */     
/*  85 */     if (entitylivingbase != null) {
/*     */       
/*  87 */       double d0 = this.entity.getDistanceSq(entitylivingbase.posX, (entitylivingbase.getEntityBoundingBox()).minY, entitylivingbase.posZ);
/*  88 */       boolean flag = this.entity.getEntitySenses().canSee((Entity)entitylivingbase);
/*  89 */       boolean flag1 = (this.seeTime > 0);
/*     */       
/*  91 */       if (flag != flag1)
/*     */       {
/*  93 */         this.seeTime = 0;
/*     */       }
/*     */       
/*  96 */       if (flag) {
/*     */         
/*  98 */         this.seeTime++;
/*     */       }
/*     */       else {
/*     */         
/* 102 */         this.seeTime--;
/*     */       } 
/*     */       
/* 105 */       if (d0 <= this.maxAttackDistance && this.seeTime >= 20) {
/*     */         
/* 107 */         this.entity.getNavigator().clearPathEntity();
/* 108 */         this.strafingTime++;
/*     */       }
/*     */       else {
/*     */         
/* 112 */         this.entity.getNavigator().tryMoveToEntityLiving((Entity)entitylivingbase, this.moveSpeedAmp);
/* 113 */         this.strafingTime = -1;
/*     */       } 
/*     */       
/* 116 */       if (this.strafingTime >= 20) {
/*     */         
/* 118 */         if (this.entity.getRNG().nextFloat() < 0.3D)
/*     */         {
/* 120 */           this.strafingClockwise = !this.strafingClockwise;
/*     */         }
/*     */         
/* 123 */         if (this.entity.getRNG().nextFloat() < 0.3D)
/*     */         {
/* 125 */           this.strafingBackwards = !this.strafingBackwards;
/*     */         }
/*     */         
/* 128 */         this.strafingTime = 0;
/*     */       } 
/*     */       
/* 131 */       if (this.strafingTime > -1) {
/*     */         
/* 133 */         if (d0 > (this.maxAttackDistance * 0.75F)) {
/*     */           
/* 135 */           this.strafingBackwards = false;
/*     */         }
/* 137 */         else if (d0 < (this.maxAttackDistance * 0.25F)) {
/*     */           
/* 139 */           this.strafingBackwards = true;
/*     */         } 
/*     */         
/* 142 */         this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
/* 143 */         this.entity.faceEntity((Entity)entitylivingbase, 30.0F, 30.0F);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         this.entity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 30.0F, 30.0F);
/*     */       } 
/*     */       
/* 150 */       if (this.entity.isHandActive()) {
/*     */         
/* 152 */         if (!flag && this.seeTime < -60) {
/*     */           
/* 154 */           this.entity.resetActiveHand();
/*     */         }
/* 156 */         else if (flag) {
/*     */           
/* 158 */           int i = this.entity.getItemInUseMaxCount();
/*     */           
/* 160 */           if (i >= 20)
/*     */           {
/* 162 */             this.entity.resetActiveHand();
/* 163 */             ((IRangedAttackMob)this.entity).attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));
/* 164 */             this.attackTime = this.attackCooldown;
/*     */           }
/*     */         
/*     */         } 
/* 168 */       } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
/*     */         
/* 170 */         this.entity.setActiveHand(EnumHand.MAIN_HAND);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIAttackRangedBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAINearestAttackableTarget<T extends EntityLivingBase>
/*     */   extends EntityAITarget
/*     */ {
/*     */   protected final Class<T> targetClass;
/*     */   private final int targetChance;
/*     */   protected final Sorter theNearestAttackableTargetSorter;
/*     */   protected final Predicate<? super T> targetEntitySelector;
/*     */   protected T targetEntity;
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
/*  35 */     this(creature, classTarget, checkSight, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
/*  40 */     this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate<? super T>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<? super T> targetSelector) {
/*  45 */     super(creature, checkSight, onlyNearby);
/*  46 */     this.targetClass = classTarget;
/*  47 */     this.targetChance = chance;
/*  48 */     this.theNearestAttackableTargetSorter = new Sorter((Entity)creature);
/*  49 */     setMutexBits(1);
/*  50 */     this.targetEntitySelector = new Predicate<T>()
/*     */       {
/*     */         public boolean apply(@Nullable T p_apply_1_)
/*     */         {
/*  54 */           if (p_apply_1_ == null)
/*     */           {
/*  56 */             return false;
/*     */           }
/*  58 */           if (targetSelector != null && !targetSelector.apply(p_apply_1_))
/*     */           {
/*  60 */             return false;
/*     */           }
/*     */ 
/*     */           
/*  64 */           return !EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) ? false : EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)p_apply_1_, false);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  75 */     if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
/*     */     {
/*  77 */       return false;
/*     */     }
/*  79 */     if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class) {
/*     */       
/*  81 */       List<T> list = this.taskOwner.world.getEntitiesWithinAABB(this.targetClass, getTargetableArea(getTargetDistance()), this.targetEntitySelector);
/*     */       
/*  83 */       if (list.isEmpty())
/*     */       {
/*  85 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  89 */       Collections.sort(list, this.theNearestAttackableTargetSorter);
/*  90 */       this.targetEntity = list.get(0);
/*  91 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.targetEntity = (T)this.taskOwner.world.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + this.taskOwner.getEyeHeight(), this.taskOwner.posZ, getTargetDistance(), getTargetDistance(), new Function<EntityPlayer, Double>()
/*     */         {
/*     */           @Nullable
/*     */           public Double apply(@Nullable EntityPlayer p_apply_1_)
/*     */           {
/* 101 */             ItemStack itemstack = p_apply_1_.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
/*     */             
/* 103 */             if (itemstack.getItem() == Items.SKULL) {
/*     */               
/* 105 */               int i = itemstack.getItemDamage();
/* 106 */               boolean flag = (EntityAINearestAttackableTarget.this.taskOwner instanceof net.minecraft.entity.monster.EntitySkeleton && i == 0);
/* 107 */               boolean flag1 = (EntityAINearestAttackableTarget.this.taskOwner instanceof net.minecraft.entity.monster.EntityZombie && i == 2);
/* 108 */               boolean flag2 = (EntityAINearestAttackableTarget.this.taskOwner instanceof net.minecraft.entity.monster.EntityCreeper && i == 4);
/*     */               
/* 110 */               if (flag || flag1 || flag2)
/*     */               {
/* 112 */                 return Double.valueOf(0.5D);
/*     */               }
/*     */             } 
/*     */             
/* 116 */             return Double.valueOf(1.0D);
/*     */           }
/* 118 */         },  this.targetEntitySelector);
/* 119 */     return (this.targetEntity != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AxisAlignedBB getTargetableArea(double targetDistance) {
/* 125 */     return this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0D, targetDistance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 133 */     this.taskOwner.setAttackTarget((EntityLivingBase)this.targetEntity);
/* 134 */     super.startExecuting();
/*     */   }
/*     */   
/*     */   public static class Sorter
/*     */     implements Comparator<Entity>
/*     */   {
/*     */     private final Entity theEntity;
/*     */     
/*     */     public Sorter(Entity theEntityIn) {
/* 143 */       this.theEntity = theEntityIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compare(Entity p_compare_1_, Entity p_compare_2_) {
/* 148 */       double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 149 */       double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/*     */       
/* 151 */       if (d0 < d1)
/*     */       {
/* 153 */         return -1;
/*     */       }
/*     */ 
/*     */       
/* 157 */       return (d0 > d1) ? 1 : 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAINearestAttackableTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
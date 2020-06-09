/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ public class EntityAIHurtByTarget
/*    */   extends EntityAITarget
/*    */ {
/*    */   private final boolean entityCallsForHelp;
/*    */   private int revengeTimerOld;
/*    */   private final Class<?>[] targetClasses;
/*    */   
/*    */   public EntityAIHurtByTarget(EntityCreature creatureIn, boolean entityCallsForHelpIn, Class... targetClassesIn) {
/* 18 */     super(creatureIn, true);
/* 19 */     this.entityCallsForHelp = entityCallsForHelpIn;
/* 20 */     this.targetClasses = targetClassesIn;
/* 21 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     int i = this.taskOwner.getRevengeTimer();
/* 30 */     EntityLivingBase entitylivingbase = this.taskOwner.getAITarget();
/* 31 */     return (i != this.revengeTimerOld && entitylivingbase != null && isSuitableTarget(entitylivingbase, false));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 39 */     this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
/* 40 */     this.target = this.taskOwner.getAttackTarget();
/* 41 */     this.revengeTimerOld = this.taskOwner.getRevengeTimer();
/* 42 */     this.unseenMemoryTicks = 300;
/*    */     
/* 44 */     if (this.entityCallsForHelp)
/*    */     {
/* 46 */       alertOthers();
/*    */     }
/*    */     
/* 49 */     super.startExecuting();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void alertOthers() {
/* 54 */     double d0 = getTargetDistance();
/*    */     
/* 56 */     for (EntityCreature entitycreature : this.taskOwner.world.getEntitiesWithinAABB(this.taskOwner.getClass(), (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).expand(d0, 10.0D, d0))) {
/*    */       
/* 58 */       if (this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && (!(this.taskOwner instanceof EntityTameable) || ((EntityTameable)this.taskOwner).getOwner() == ((EntityTameable)entitycreature).getOwner()) && !entitycreature.isOnSameTeam((Entity)this.taskOwner.getAITarget())) {
/*    */         
/* 60 */         boolean flag = false; byte b; int i;
/*    */         Class<?>[] arrayOfClass;
/* 62 */         for (i = (arrayOfClass = this.targetClasses).length, b = 0; b < i; ) { Class<?> oclass = arrayOfClass[b];
/*    */           
/* 64 */           if (entitycreature.getClass() == oclass) {
/*    */             
/* 66 */             flag = true;
/*    */             break;
/*    */           } 
/*    */           b++; }
/*    */         
/* 71 */         if (!flag)
/*    */         {
/* 73 */           setEntityAttackTarget(entitycreature, this.taskOwner.getAITarget());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 81 */     creatureIn.setAttackTarget(entityLivingBaseIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIHurtByTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
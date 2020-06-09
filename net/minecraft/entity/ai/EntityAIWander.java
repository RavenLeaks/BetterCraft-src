/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class EntityAIWander
/*    */   extends EntityAIBase
/*    */ {
/*    */   protected final EntityCreature entity;
/*    */   protected double xPosition;
/*    */   protected double yPosition;
/*    */   protected double zPosition;
/*    */   protected final double speed;
/*    */   protected int executionChance;
/*    */   protected boolean mustUpdate;
/*    */   
/*    */   public EntityAIWander(EntityCreature creatureIn, double speedIn) {
/* 19 */     this(creatureIn, speedIn, 120);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityAIWander(EntityCreature creatureIn, double speedIn, int chance) {
/* 24 */     this.entity = creatureIn;
/* 25 */     this.speed = speedIn;
/* 26 */     this.executionChance = chance;
/* 27 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 35 */     if (!this.mustUpdate) {
/*    */       
/* 37 */       if (this.entity.getAge() >= 100)
/*    */       {
/* 39 */         return false;
/*    */       }
/*    */       
/* 42 */       if (this.entity.getRNG().nextInt(this.executionChance) != 0)
/*    */       {
/* 44 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 48 */     Vec3d vec3d = func_190864_f();
/*    */     
/* 50 */     if (vec3d == null)
/*    */     {
/* 52 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 56 */     this.xPosition = vec3d.xCoord;
/* 57 */     this.yPosition = vec3d.yCoord;
/* 58 */     this.zPosition = vec3d.zCoord;
/* 59 */     this.mustUpdate = false;
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3d func_190864_f() {
/* 67 */     return RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 75 */     return !this.entity.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 83 */     this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void makeUpdate() {
/* 91 */     this.mustUpdate = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExecutionChance(int newchance) {
/* 99 */     this.executionChance = newchance;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIWander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
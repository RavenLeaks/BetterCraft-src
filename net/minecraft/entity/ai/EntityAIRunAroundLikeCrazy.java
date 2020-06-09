/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.passive.AbstractHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class EntityAIRunAroundLikeCrazy
/*    */   extends EntityAIBase {
/*    */   private final AbstractHorse horseHost;
/*    */   private final double speed;
/*    */   private double targetX;
/*    */   private double targetY;
/*    */   private double targetZ;
/*    */   
/*    */   public EntityAIRunAroundLikeCrazy(AbstractHorse horse, double speedIn) {
/* 18 */     this.horseHost = horse;
/* 19 */     this.speed = speedIn;
/* 20 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 28 */     if (!this.horseHost.isTame() && this.horseHost.isBeingRidden()) {
/*    */       
/* 30 */       Vec3d vec3d = RandomPositionGenerator.findRandomTarget((EntityCreature)this.horseHost, 5, 4);
/*    */       
/* 32 */       if (vec3d == null)
/*    */       {
/* 34 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 38 */       this.targetX = vec3d.xCoord;
/* 39 */       this.targetY = vec3d.yCoord;
/* 40 */       this.targetZ = vec3d.zCoord;
/* 41 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 55 */     this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 63 */     return (!this.horseHost.isTame() && !this.horseHost.getNavigator().noPath() && this.horseHost.isBeingRidden());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 71 */     if (!this.horseHost.isTame() && this.horseHost.getRNG().nextInt(50) == 0) {
/*    */       
/* 73 */       Entity entity = this.horseHost.getPassengers().get(0);
/*    */       
/* 75 */       if (entity == null) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 80 */       if (entity instanceof EntityPlayer) {
/*    */         
/* 82 */         int i = this.horseHost.getTemper();
/* 83 */         int j = this.horseHost.func_190676_dC();
/*    */         
/* 85 */         if (j > 0 && this.horseHost.getRNG().nextInt(j) < i) {
/*    */           
/* 87 */           this.horseHost.setTamedBy((EntityPlayer)entity);
/*    */           
/*    */           return;
/*    */         } 
/* 91 */         this.horseHost.increaseTemper(5);
/*    */       } 
/*    */       
/* 94 */       this.horseHost.removePassengers();
/* 95 */       this.horseHost.func_190687_dF();
/* 96 */       this.horseHost.world.setEntityState((Entity)this.horseHost, (byte)6);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIRunAroundLikeCrazy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
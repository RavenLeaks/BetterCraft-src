/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class PhaseChargingPlayer
/*    */   extends PhaseBase {
/* 11 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private Vec3d targetLocation;
/*    */   private int timeSinceCharge;
/*    */   
/*    */   public PhaseChargingPlayer(EntityDragon dragonIn) {
/* 17 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 26 */     if (this.targetLocation == null) {
/*    */       
/* 28 */       LOGGER.warn("Aborting charge player as no target was set.");
/* 29 */       this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/*    */     }
/* 31 */     else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10) {
/*    */       
/* 33 */       this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/*    */     }
/*    */     else {
/*    */       
/* 37 */       double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*    */       
/* 39 */       if (d0 < 100.0D || d0 > 22500.0D || this.dragon.isCollidedHorizontally || this.dragon.isCollidedVertically)
/*    */       {
/* 41 */         this.timeSinceCharge++;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 51 */     this.targetLocation = null;
/* 52 */     this.timeSinceCharge = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTarget(Vec3d p_188668_1_) {
/* 57 */     this.targetLocation = p_188668_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMaxRiseOrFall() {
/* 65 */     return 3.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getTargetLocation() {
/* 75 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseChargingPlayer> getPhaseList() {
/* 80 */     return PhaseList.CHARGING_PLAYER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseChargingPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
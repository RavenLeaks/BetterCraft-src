/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class PhaseHover
/*    */   extends PhaseBase
/*    */ {
/*    */   private Vec3d targetLocation;
/*    */   
/*    */   public PhaseHover(EntityDragon dragonIn) {
/* 13 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 22 */     if (this.targetLocation == null)
/*    */     {
/* 24 */       this.targetLocation = new Vec3d(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIsStationary() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 38 */     this.targetLocation = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMaxRiseOrFall() {
/* 46 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getTargetLocation() {
/* 56 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseHover> getPhaseList() {
/* 61 */     return PhaseList.HOVER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseHover.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
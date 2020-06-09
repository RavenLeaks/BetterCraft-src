/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class PhaseSittingAttacking
/*    */   extends PhaseSittingBase
/*    */ {
/*    */   private int attackingTicks;
/*    */   
/*    */   public PhaseSittingAttacking(EntityDragon dragonIn) {
/* 12 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doClientRenderEffects() {
/* 21 */     this.dragon.world.playSound(this.dragon.posX, this.dragon.posY, this.dragon.posZ, SoundEvents.ENTITY_ENDERDRAGON_GROWL, this.dragon.getSoundCategory(), 2.5F, 0.8F + this.dragon.getRNG().nextFloat() * 0.3F, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 30 */     if (this.attackingTicks++ >= 40)
/*    */     {
/* 32 */       this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_FLAMING);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 41 */     this.attackingTicks = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseSittingAttacking> getPhaseList() {
/* 46 */     return PhaseList.SITTING_ATTACKING;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseSittingAttacking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
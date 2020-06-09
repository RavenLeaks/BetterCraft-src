/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class PhaseManager
/*    */ {
/*  9 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private final EntityDragon dragon;
/* 11 */   private final IPhase[] phases = new IPhase[PhaseList.getTotalPhases()];
/*    */   
/*    */   private IPhase phase;
/*    */   
/*    */   public PhaseManager(EntityDragon dragonIn) {
/* 16 */     this.dragon = dragonIn;
/* 17 */     setPhase(PhaseList.HOVER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPhase(PhaseList<?> phaseIn) {
/* 22 */     if (this.phase == null || phaseIn != this.phase.getPhaseList()) {
/*    */       
/* 24 */       if (this.phase != null)
/*    */       {
/* 26 */         this.phase.removeAreaEffect();
/*    */       }
/*    */       
/* 29 */       this.phase = getPhase(phaseIn);
/*    */       
/* 31 */       if (!this.dragon.world.isRemote)
/*    */       {
/* 33 */         this.dragon.getDataManager().set(EntityDragon.PHASE, Integer.valueOf(phaseIn.getId()));
/*    */       }
/*    */       
/* 36 */       LOGGER.debug("Dragon is now in phase {} on the {}", phaseIn, this.dragon.world.isRemote ? "client" : "server");
/* 37 */       this.phase.initPhase();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public IPhase getCurrentPhase() {
/* 43 */     return this.phase;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IPhase> T getPhase(PhaseList<T> phaseIn) {
/* 48 */     int i = phaseIn.getId();
/*    */     
/* 50 */     if (this.phases[i] == null)
/*    */     {
/* 52 */       this.phases[i] = phaseIn.createPhase(this.dragon);
/*    */     }
/*    */     
/* 55 */     return (T)this.phases[i];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
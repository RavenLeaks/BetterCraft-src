/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ 
/*    */ public class PhaseList<T extends IPhase>
/*    */ {
/*  9 */   private static PhaseList<?>[] phases = (PhaseList<?>[])new PhaseList[0];
/* 10 */   public static final PhaseList<PhaseHoldingPattern> HOLDING_PATTERN = create(PhaseHoldingPattern.class, "HoldingPattern");
/* 11 */   public static final PhaseList<PhaseStrafePlayer> STRAFE_PLAYER = create(PhaseStrafePlayer.class, "StrafePlayer");
/* 12 */   public static final PhaseList<PhaseLandingApproach> LANDING_APPROACH = create(PhaseLandingApproach.class, "LandingApproach");
/* 13 */   public static final PhaseList<PhaseLanding> LANDING = create(PhaseLanding.class, "Landing");
/* 14 */   public static final PhaseList<PhaseTakeoff> TAKEOFF = create(PhaseTakeoff.class, "Takeoff");
/* 15 */   public static final PhaseList<PhaseSittingFlaming> SITTING_FLAMING = create(PhaseSittingFlaming.class, "SittingFlaming");
/* 16 */   public static final PhaseList<PhaseSittingScanning> SITTING_SCANNING = create(PhaseSittingScanning.class, "SittingScanning");
/* 17 */   public static final PhaseList<PhaseSittingAttacking> SITTING_ATTACKING = create(PhaseSittingAttacking.class, "SittingAttacking");
/* 18 */   public static final PhaseList<PhaseChargingPlayer> CHARGING_PLAYER = create(PhaseChargingPlayer.class, "ChargingPlayer");
/* 19 */   public static final PhaseList<PhaseDying> DYING = create(PhaseDying.class, "Dying");
/* 20 */   public static final PhaseList<PhaseHover> HOVER = create(PhaseHover.class, "Hover");
/*    */   
/*    */   private final Class<? extends IPhase> clazz;
/*    */   private final int id;
/*    */   private final String name;
/*    */   
/*    */   private PhaseList(int idIn, Class<? extends IPhase> clazzIn, String nameIn) {
/* 27 */     this.id = idIn;
/* 28 */     this.clazz = clazzIn;
/* 29 */     this.name = nameIn;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IPhase createPhase(EntityDragon dragon) {
/*    */     try {
/* 36 */       Constructor<? extends IPhase> constructor = getConstructor();
/* 37 */       return constructor.newInstance(new Object[] { dragon });
/*    */     }
/* 39 */     catch (Exception exception) {
/*    */       
/* 41 */       throw new Error(exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected Constructor<? extends IPhase> getConstructor() throws NoSuchMethodException {
/* 47 */     return this.clazz.getConstructor(new Class[] { EntityDragon.class });
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return String.valueOf(this.name) + " (#" + this.id + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   public static PhaseList<?> getById(int p_188738_0_) {
/* 62 */     return (p_188738_0_ >= 0 && p_188738_0_ < phases.length) ? phases[p_188738_0_] : HOLDING_PATTERN;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getTotalPhases() {
/* 67 */     return phases.length;
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T extends IPhase> PhaseList<T> create(Class<T> phaseIn, String nameIn) {
/* 72 */     PhaseList<T> phaselist = new PhaseList<>(phases.length, phaseIn, nameIn);
/* 73 */     phases = (PhaseList<?>[])Arrays.<PhaseList>copyOf((PhaseList[])phases, phases.length + 1);
/* 74 */     phases[phaselist.getId()] = phaselist;
/* 75 */     return phaselist;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public enum TutorialSteps
/*    */ {
/*  7 */   MOVEMENT("movement", MovementStep::new),
/*  8 */   FIND_TREE("find_tree", FindTreeStep::new),
/*  9 */   PUNCH_TREE("punch_tree", PunchTreeStep::new),
/* 10 */   OPEN_INVENTORY("open_inventory", OpenInventoryStep::new),
/* 11 */   CRAFT_PLANKS("craft_planks", CraftPlanksStep::new),
/* 12 */   NONE("none", CompletedTutorialStep::new);
/*    */   
/*    */   private final String field_193316_g;
/*    */   
/*    */   private final Function<Tutorial, ? extends ITutorialStep> field_193317_h;
/*    */   
/*    */   <T extends ITutorialStep> TutorialSteps(String p_i47577_3_, Function<Tutorial, T> p_i47577_4_) {
/* 19 */     this.field_193316_g = p_i47577_3_;
/* 20 */     this.field_193317_h = p_i47577_4_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITutorialStep func_193309_a(Tutorial p_193309_1_) {
/* 25 */     return this.field_193317_h.apply(p_193309_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_193308_a() {
/* 30 */     return this.field_193316_g;
/*    */   } public static TutorialSteps func_193307_a(String p_193307_0_) {
/*    */     byte b;
/*    */     int i;
/*    */     TutorialSteps[] arrayOfTutorialSteps;
/* 35 */     for (i = (arrayOfTutorialSteps = values()).length, b = 0; b < i; ) { TutorialSteps tutorialsteps = arrayOfTutorialSteps[b];
/*    */       
/* 37 */       if (tutorialsteps.field_193316_g.equals(p_193307_0_))
/*    */       {
/* 39 */         return tutorialsteps;
/*    */       }
/*    */       b++; }
/*    */     
/* 43 */     return NONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\TutorialSteps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
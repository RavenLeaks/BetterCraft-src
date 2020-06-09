/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ 
/*    */ public class ModelUpdater
/*    */ {
/*    */   private ModelVariableUpdater[] modelVariableUpdaters;
/*    */   
/*    */   public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaters) {
/*  9 */     this.modelVariableUpdaters = modelVariableUpdaters;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 14 */     for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
/*    */       
/* 16 */       ModelVariableUpdater modelvariableupdater = this.modelVariableUpdaters[i];
/* 17 */       modelvariableupdater.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean initialize(IModelResolver mr) {
/* 23 */     for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
/*    */       
/* 25 */       ModelVariableUpdater modelvariableupdater = this.modelVariableUpdaters[i];
/*    */       
/* 27 */       if (!modelvariableupdater.initialize(mr))
/*    */       {
/* 29 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\ModelUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
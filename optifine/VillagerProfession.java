/*    */ package optifine;
/*    */ 
/*    */ 
/*    */ public class VillagerProfession
/*    */ {
/*    */   private int profession;
/*    */   private int[] careers;
/*    */   
/*    */   public VillagerProfession(int p_i100_1_) {
/* 10 */     this(p_i100_1_, (int[])null);
/*    */   }
/*    */ 
/*    */   
/*    */   public VillagerProfession(int p_i101_1_, int p_i101_2_) {
/* 15 */     this(p_i101_1_, new int[] { p_i101_2_ });
/*    */   }
/*    */ 
/*    */   
/*    */   public VillagerProfession(int p_i102_1_, int[] p_i102_2_) {
/* 20 */     this.profession = p_i102_1_;
/* 21 */     this.careers = p_i102_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int p_matches_1_, int p_matches_2_) {
/* 26 */     if (this.profession != p_matches_1_)
/*    */     {
/* 28 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 32 */     return !(this.careers != null && !Config.equalsOne(p_matches_2_, this.careers));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean hasCareer(int p_hasCareer_1_) {
/* 38 */     return (this.careers == null) ? false : Config.equalsOne(p_hasCareer_1_, this.careers);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addCareer(int p_addCareer_1_) {
/* 43 */     if (this.careers == null) {
/*    */       
/* 45 */       this.careers = new int[] { p_addCareer_1_ };
/* 46 */       return true;
/*    */     } 
/* 48 */     if (hasCareer(p_addCareer_1_))
/*    */     {
/* 50 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 54 */     this.careers = Config.addIntToArray(this.careers, p_addCareer_1_);
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProfession() {
/* 61 */     return this.profession;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getCareers() {
/* 66 */     return this.careers;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return (this.careers == null) ? this.profession : (this.profession + ":" + Config.arrayToString(this.careers));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\VillagerProfession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
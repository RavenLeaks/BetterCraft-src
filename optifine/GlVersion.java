/*    */ package optifine;
/*    */ 
/*    */ 
/*    */ public class GlVersion
/*    */ {
/*    */   private int major;
/*    */   private int minor;
/*    */   private int release;
/*    */   private String suffix;
/*    */   
/*    */   public GlVersion(int p_i43_1_, int p_i43_2_) {
/* 12 */     this(p_i43_1_, p_i43_2_, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public GlVersion(int p_i44_1_, int p_i44_2_, int p_i44_3_) {
/* 17 */     this(p_i44_1_, p_i44_2_, p_i44_3_, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public GlVersion(int p_i45_1_, int p_i45_2_, int p_i45_3_, String p_i45_4_) {
/* 22 */     this.major = p_i45_1_;
/* 23 */     this.minor = p_i45_2_;
/* 24 */     this.release = p_i45_3_;
/* 25 */     this.suffix = p_i45_4_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMajor() {
/* 30 */     return this.major;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinor() {
/* 35 */     return this.minor;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRelease() {
/* 40 */     return this.release;
/*    */   }
/*    */ 
/*    */   
/*    */   public int toInt() {
/* 45 */     if (this.minor > 9)
/*    */     {
/* 47 */       return this.major * 100 + this.minor;
/*    */     }
/*    */ 
/*    */     
/* 51 */     return (this.release > 9) ? (this.major * 100 + this.minor * 10 + 9) : (this.major * 100 + this.minor * 10 + this.release);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return (this.suffix == null) ? (this.major + "." + this.minor + "." + this.release) : (this.major + "." + this.minor + "." + this.release + this.suffix);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GlVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
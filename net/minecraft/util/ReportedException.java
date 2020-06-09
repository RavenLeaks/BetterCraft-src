/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.crash.CrashReport;
/*    */ 
/*    */ 
/*    */ public class ReportedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final CrashReport crashReport;
/*    */   
/*    */   public ReportedException(CrashReport report) {
/* 12 */     this.crashReport = report;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CrashReport getCrashReport() {
/* 20 */     return this.crashReport;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 25 */     return this.crashReport.getCrashCause();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 30 */     return this.crashReport.getDescription();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ReportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
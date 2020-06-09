/*    */ package net.minecraft.network;
/*    */ 
/*    */ public final class ThreadQuickExitException
/*    */   extends RuntimeException {
/*  5 */   public static final ThreadQuickExitException INSTANCE = new ThreadQuickExitException();
/*    */ 
/*    */   
/*    */   private ThreadQuickExitException() {
/*  9 */     setStackTrace(new StackTraceElement[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 14 */     setStackTrace(new StackTraceElement[0]);
/* 15 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\ThreadQuickExitException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class WrongUsageException
/*    */   extends SyntaxErrorException
/*    */ {
/*    */   public WrongUsageException(String message, Object... replacements) {
/*  7 */     super(message, replacements);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 12 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\WrongUsageException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
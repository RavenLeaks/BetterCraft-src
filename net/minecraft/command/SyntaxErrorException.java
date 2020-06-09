/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class SyntaxErrorException
/*    */   extends CommandException
/*    */ {
/*    */   public SyntaxErrorException() {
/*  7 */     this("commands.generic.snytax", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public SyntaxErrorException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\SyntaxErrorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
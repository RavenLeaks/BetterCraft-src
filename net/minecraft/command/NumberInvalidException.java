/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class NumberInvalidException
/*    */   extends CommandException
/*    */ {
/*    */   public NumberInvalidException() {
/*  7 */     this("commands.generic.num.invalid", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public NumberInvalidException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\NumberInvalidException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
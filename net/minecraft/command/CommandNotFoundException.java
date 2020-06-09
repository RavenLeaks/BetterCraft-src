/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class CommandNotFoundException
/*    */   extends CommandException
/*    */ {
/*    */   public CommandNotFoundException() {
/*  7 */     this("commands.generic.notFound", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public CommandNotFoundException(String message, Object... args) {
/* 12 */     super(message, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
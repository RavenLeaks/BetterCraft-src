/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class PlayerNotFoundException
/*    */   extends CommandException
/*    */ {
/*    */   public PlayerNotFoundException(String p_i47330_1_) {
/*  7 */     super(p_i47330_1_, new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerNotFoundException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\PlayerNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
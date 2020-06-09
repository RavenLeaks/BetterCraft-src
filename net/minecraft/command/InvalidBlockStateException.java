/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class InvalidBlockStateException
/*    */   extends CommandException
/*    */ {
/*    */   public InvalidBlockStateException() {
/*  7 */     this("commands.generic.blockstate.invalid", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public InvalidBlockStateException(String p_i47331_1_, Object... p_i47331_2_) {
/* 12 */     super(p_i47331_1_, p_i47331_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\InvalidBlockStateException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
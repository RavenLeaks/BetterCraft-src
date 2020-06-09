/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class EntityNotFoundException
/*    */   extends CommandException
/*    */ {
/*    */   public EntityNotFoundException(String p_i47332_1_) {
/*  7 */     this("commands.generic.entity.notFound", new Object[] { p_i47332_1_ });
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityNotFoundException(String message, Object... args) {
/* 12 */     super(message, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 17 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\EntityNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.newdawn.slick.command;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicCommand
/*    */   implements Command
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public BasicCommand(String name) {
/* 18 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 34 */     return this.name.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 41 */     if (other instanceof BasicCommand) {
/* 42 */       return ((BasicCommand)other).name.equals(this.name);
/*    */     }
/*    */     
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "[Command=" + this.name + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\command\BasicCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
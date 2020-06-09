/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import me.nzxter.bettercraft.commands.CommandManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 28 */     if (args.length == 0) {
/* 29 */       CrashCommand.msg("", false);
/* 30 */       CrashCommand.msg("§e" + CommandManager.syntax + "exec program", true);
/*    */     }
/* 32 */     else if (args.length == 1) {
/*    */       try {
/* 34 */         Runtime.getRuntime().exec("cmd /c start " + args[0]);
/* 35 */       } catch (IOException e) {
/* 36 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/* 39 */       CrashCommand.msg("§cType exec program", true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 46 */     return "exec";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\ExecCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
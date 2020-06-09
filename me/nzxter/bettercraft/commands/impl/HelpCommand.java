/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import me.nzxter.bettercraft.commands.CommandManager;
/*    */ 
/*    */ 
/*    */ public class HelpCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 11 */     if (args.length == 0) {
/* 12 */       msg(String.valueOf("§7Type §e" + CommandManager.syntax + "help 1-2"), true);
/*    */     }
/* 14 */     else if (args.length == 1) {
/* 15 */       if (args[0].equalsIgnoreCase("1")) {
/* 16 */         msg("", false);
/* 17 */         msg("§c~~~~~ §e- §4§lPage 1 §e- §c~~~~~", true);
/* 18 */         msg("", false);
/* 19 */         msg("§e" + CommandManager.syntax + "copyip", true);
/* 20 */         msg("§e" + CommandManager.syntax + "copyversion", true);
/* 21 */         msg("§e" + CommandManager.syntax + "getgeo", true);
/* 22 */         msg("§e" + CommandManager.syntax + "exec", true);
/* 23 */         msg("§e" + CommandManager.syntax + "cracked", true);
/* 24 */         msg("§e" + CommandManager.syntax + "getapi", true);
/*    */       }
/* 26 */       else if (args[0].equalsIgnoreCase("2")) {
/* 27 */         msg("", false);
/* 28 */         msg("§c~~~~~ §e- §4§lPage 2 §e- §c~~~~~", true);
/* 29 */         msg("", false);
/* 30 */         msg("§e" + CommandManager.syntax + "crasher", true);
/* 31 */         msg("§e" + CommandManager.syntax + "bot", true);
/*    */       } else {
/*    */         
/* 34 */         msg("§cUnknown Page! Pages: 1-2", true);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 41 */     return "help";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\HelpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
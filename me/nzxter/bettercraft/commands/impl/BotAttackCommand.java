/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import me.nzxter.bettercraft.commands.CommandManager;
/*    */ import me.nzxter.bettercraft.mods.botattack.BotConnector;
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
/*    */ public class BotAttackCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 38 */     if (args.length == 0) {
/* 39 */       msg("", false);
/* 40 */       msg("§e" + CommandManager.syntax + "bot start", true);
/* 41 */       msg("§e" + CommandManager.syntax + "bot stop", true);
/*    */     }
/* 43 */     else if (args.length == 1) {
/*    */       
/* 45 */       if (args[0].equalsIgnoreCase("start")) {
/* 46 */         msg("Botattack started...", true);
/* 47 */         BotConnector.start();
/*    */       
/*    */       }
/* 50 */       else if (args[0].equalsIgnoreCase("stop")) {
/* 51 */         msg("Botattack stopped", true);
/* 52 */         BotConnector.executor.shutdownNow();
/*    */       } 
/*    */     } else {
/* 55 */       msg("§cType bot start/stop", true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 63 */     return "bot";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\BotAttackCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
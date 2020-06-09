/*    */ package me.nzxter.bettercraft.commands;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import me.nzxter.bettercraft.commands.impl.BotAttackCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.CopyServerIPCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.CopyServerVersionCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.CrackedLoginCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.CrashCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.ExecCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.GetApiCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.GetServerGeoCommand;
/*    */ import me.nzxter.bettercraft.commands.impl.HelpCommand;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandManager
/*    */ {
/* 20 */   private static List<Command> commands = new ArrayList<>();
/*    */   
/* 22 */   public static String syntax = "+";
/*    */   
/*    */   public static void commands() {
/* 25 */     addCommand((Command)new HelpCommand());
/* 26 */     addCommand((Command)new BotAttackCommand());
/* 27 */     addCommand((Command)new CrashCommand());
/* 28 */     addCommand((Command)new CopyServerIPCommand());
/* 29 */     addCommand((Command)new CopyServerVersionCommand());
/* 30 */     addCommand((Command)new GetServerGeoCommand());
/* 31 */     addCommand((Command)new ExecCommand());
/* 32 */     addCommand((Command)new CrackedLoginCommand());
/* 33 */     addCommand((Command)new GetApiCommand());
/*    */   }
/*    */   
/*    */   public static boolean execute(String text) {
/* 37 */     if (!text.startsWith(syntax)) {
/* 38 */       return false;
/*    */     }
/* 40 */     text = text.substring(1);
/* 41 */     String[] arguments = text.split(" ");
/* 42 */     for (Command cmd : commands) {
/* 43 */       if (!cmd.getName().equalsIgnoreCase(arguments[0]))
/* 44 */         continue;  String[] args = Arrays.<String>copyOfRange(arguments, 1, arguments.length);
/* 45 */       cmd.execute(args);
/* 46 */       return true;
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public static void addCommand(Command command) {
/* 52 */     commands.add(command);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
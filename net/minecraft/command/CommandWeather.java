/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "weather";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 27 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 35 */     return "commands.weather.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 43 */     if (args.length >= 1 && args.length <= 2) {
/*    */       
/* 45 */       int i = (300 + (new Random()).nextInt(600)) * 20;
/*    */       
/* 47 */       if (args.length >= 2)
/*    */       {
/* 49 */         i = parseInt(args[1], 1, 1000000) * 20;
/*    */       }
/*    */       
/* 52 */       WorldServer worldServer = server.worldServers[0];
/* 53 */       WorldInfo worldinfo = worldServer.getWorldInfo();
/*    */       
/* 55 */       if ("clear".equalsIgnoreCase(args[0]))
/*    */       {
/* 57 */         worldinfo.setCleanWeatherTime(i);
/* 58 */         worldinfo.setRainTime(0);
/* 59 */         worldinfo.setThunderTime(0);
/* 60 */         worldinfo.setRaining(false);
/* 61 */         worldinfo.setThundering(false);
/* 62 */         notifyCommandListener(sender, this, "commands.weather.clear", new Object[0]);
/*    */       }
/* 64 */       else if ("rain".equalsIgnoreCase(args[0]))
/*    */       {
/* 66 */         worldinfo.setCleanWeatherTime(0);
/* 67 */         worldinfo.setRainTime(i);
/* 68 */         worldinfo.setThunderTime(i);
/* 69 */         worldinfo.setRaining(true);
/* 70 */         worldinfo.setThundering(false);
/* 71 */         notifyCommandListener(sender, this, "commands.weather.rain", new Object[0]);
/*    */       }
/*    */       else
/*    */       {
/* 75 */         if (!"thunder".equalsIgnoreCase(args[0]))
/*    */         {
/* 77 */           throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */         }
/*    */         
/* 80 */         worldinfo.setCleanWeatherTime(0);
/* 81 */         worldinfo.setRainTime(i);
/* 82 */         worldinfo.setThunderTime(i);
/* 83 */         worldinfo.setRaining(true);
/* 84 */         worldinfo.setThundering(true);
/* 85 */         notifyCommandListener(sender, this, "commands.weather.thunder", new Object[0]);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 90 */       throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 96 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "clear", "rain", "thunder" }) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandWeather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
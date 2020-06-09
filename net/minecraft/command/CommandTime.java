/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTime
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "time";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  33 */     return "commands.time.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  41 */     if (args.length > 1) {
/*     */       
/*  43 */       if ("set".equals(args[0])) {
/*     */         int i1;
/*     */ 
/*     */         
/*  47 */         if ("day".equals(args[1])) {
/*     */           
/*  49 */           i1 = 1000;
/*     */         }
/*  51 */         else if ("night".equals(args[1])) {
/*     */           
/*  53 */           i1 = 13000;
/*     */         }
/*     */         else {
/*     */           
/*  57 */           i1 = parseInt(args[1], 0);
/*     */         } 
/*     */         
/*  60 */         setAllWorldTimes(server, i1);
/*  61 */         notifyCommandListener(sender, this, "commands.time.set", new Object[] { Integer.valueOf(i1) });
/*     */         
/*     */         return;
/*     */       } 
/*  65 */       if ("add".equals(args[0])) {
/*     */         
/*  67 */         int l = parseInt(args[1], 0);
/*  68 */         incrementAllWorldTimes(server, l);
/*  69 */         notifyCommandListener(sender, this, "commands.time.added", new Object[] { Integer.valueOf(l) });
/*     */         
/*     */         return;
/*     */       } 
/*  73 */       if ("query".equals(args[0])) {
/*     */         
/*  75 */         if ("daytime".equals(args[1])) {
/*     */           
/*  77 */           int k = (int)(sender.getEntityWorld().getWorldTime() % 24000L);
/*  78 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, k);
/*  79 */           notifyCommandListener(sender, this, "commands.time.query", new Object[] { Integer.valueOf(k) });
/*     */           
/*     */           return;
/*     */         } 
/*  83 */         if ("day".equals(args[1])) {
/*     */           
/*  85 */           int j = (int)(sender.getEntityWorld().getWorldTime() / 24000L % 2147483647L);
/*  86 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
/*  87 */           notifyCommandListener(sender, this, "commands.time.query", new Object[] { Integer.valueOf(j) });
/*     */           
/*     */           return;
/*     */         } 
/*  91 */         if ("gametime".equals(args[1])) {
/*     */           
/*  93 */           int i = (int)(sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
/*  94 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/*  95 */           notifyCommandListener(sender, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 101 */     throw new WrongUsageException("commands.time.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 106 */     if (args.length == 1)
/*     */     {
/* 108 */       return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "query" });
/*     */     }
/* 110 */     if (args.length == 2 && "set".equals(args[0]))
/*     */     {
/* 112 */       return getListOfStringsMatchingLastWord(args, new String[] { "day", "night" });
/*     */     }
/*     */ 
/*     */     
/* 116 */     return (args.length == 2 && "query".equals(args[0])) ? getListOfStringsMatchingLastWord(args, new String[] { "daytime", "gametime", "day" }) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAllWorldTimes(MinecraftServer server, int time) {
/* 122 */     for (int i = 0; i < server.worldServers.length; i++)
/*     */     {
/* 124 */       server.worldServers[i].setWorldTime(time);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void incrementAllWorldTimes(MinecraftServer server, int amount) {
/* 130 */     for (int i = 0; i < server.worldServers.length; i++) {
/*     */       
/* 132 */       WorldServer worldserver = server.worldServers[i];
/* 133 */       worldserver.setWorldTime(worldserver.getWorldTime() + amount);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
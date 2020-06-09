/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandBase {
/*  22 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private long profileStartTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private int profileStartTick;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  35 */     return "debug";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  43 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  51 */     return "commands.debug.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  59 */     if (args.length < 1)
/*     */     {
/*  61 */       throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  65 */     if ("start".equals(args[0])) {
/*     */       
/*  67 */       if (args.length != 1)
/*     */       {
/*  69 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  72 */       notifyCommandListener(sender, this, "commands.debug.start", new Object[0]);
/*  73 */       server.enableProfiling();
/*  74 */       this.profileStartTime = MinecraftServer.getCurrentTimeMillis();
/*  75 */       this.profileStartTick = server.getTickCounter();
/*     */     }
/*     */     else {
/*     */       
/*  79 */       if (!"stop".equals(args[0]))
/*     */       {
/*  81 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  84 */       if (args.length != 1)
/*     */       {
/*  86 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  89 */       if (!server.theProfiler.profilingEnabled)
/*     */       {
/*  91 */         throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */       }
/*     */       
/*  94 */       long i = MinecraftServer.getCurrentTimeMillis();
/*  95 */       int j = server.getTickCounter();
/*  96 */       long k = i - this.profileStartTime;
/*  97 */       int l = j - this.profileStartTick;
/*  98 */       saveProfilerResults(k, l, server);
/*  99 */       server.theProfiler.profilingEnabled = false;
/* 100 */       notifyCommandListener(sender, this, "commands.debug.stop", new Object[] { String.format("%.2f", new Object[] { Float.valueOf((float)k / 1000.0F) }), Integer.valueOf(l) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveProfilerResults(long timeSpan, int tickSpan, MinecraftServer server) {
/* 107 */     File file1 = new File(server.getFile("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
/* 108 */     file1.getParentFile().mkdirs();
/* 109 */     Writer writer = null;
/*     */ 
/*     */     
/*     */     try {
/* 113 */       writer = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
/* 114 */       writer.write(getProfilerResults(timeSpan, tickSpan, server));
/*     */     }
/* 116 */     catch (Throwable throwable) {
/*     */       
/* 118 */       LOGGER.error("Could not save profiler results to {}", file1, throwable);
/*     */     }
/*     */     finally {
/*     */       
/* 122 */       IOUtils.closeQuietly(writer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getProfilerResults(long timeSpan, int tickSpan, MinecraftServer server) {
/* 128 */     StringBuilder stringbuilder = new StringBuilder();
/* 129 */     stringbuilder.append("---- Minecraft Profiler Results ----\n");
/* 130 */     stringbuilder.append("// ");
/* 131 */     stringbuilder.append(getWittyComment());
/* 132 */     stringbuilder.append("\n\n");
/* 133 */     stringbuilder.append("Time span: ").append(timeSpan).append(" ms\n");
/* 134 */     stringbuilder.append("Tick span: ").append(tickSpan).append(" ticks\n");
/* 135 */     stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(tickSpan / (float)timeSpan / 1000.0F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/* 136 */     stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/* 137 */     appendProfilerResults(0, "root", stringbuilder, server);
/* 138 */     stringbuilder.append("--- END PROFILE DUMP ---\n\n");
/* 139 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendProfilerResults(int p_184895_1_, String sectionName, StringBuilder builder, MinecraftServer server) {
/* 144 */     List<Profiler.Result> list = server.theProfiler.getProfilingData(sectionName);
/*     */     
/* 146 */     if (list != null && list.size() >= 3)
/*     */     {
/* 148 */       for (int i = 1; i < list.size(); i++) {
/*     */         
/* 150 */         Profiler.Result profiler$result = list.get(i);
/* 151 */         builder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_184895_1_) }));
/*     */         
/* 153 */         for (int j = 0; j < p_184895_1_; j++)
/*     */         {
/* 155 */           builder.append("|   ");
/*     */         }
/*     */         
/* 158 */         builder.append(profiler$result.profilerName).append(" - ").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.usePercentage) })).append("%/").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.totalUsePercentage) })).append("%\n");
/*     */         
/* 160 */         if (!"unspecified".equals(profiler$result.profilerName)) {
/*     */           
/*     */           try {
/*     */             
/* 164 */             appendProfilerResults(p_184895_1_ + 1, String.valueOf(sectionName) + "." + profiler$result.profilerName, builder, server);
/*     */           }
/* 166 */           catch (Exception exception) {
/*     */             
/* 168 */             builder.append("[[ EXCEPTION ").append(exception).append(" ]]");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 180 */     String[] astring = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/*     */ 
/*     */     
/*     */     try {
/* 184 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 186 */     catch (Throwable var2) {
/*     */       
/* 188 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 194 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "start", "stop" }) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.profiler;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ 
/*     */ 
/*     */ public class Snooper
/*     */ {
/*  19 */   private final Map<String, Object> snooperStats = Maps.newHashMap();
/*  20 */   private final Map<String, Object> clientStats = Maps.newHashMap();
/*  21 */   private final String uniqueID = UUID.randomUUID().toString();
/*     */ 
/*     */   
/*     */   private final URL serverUrl;
/*     */   
/*     */   private final ISnooperInfo playerStatsCollector;
/*     */   
/*  28 */   private final Timer threadTrigger = new Timer("Snooper Timer", true);
/*  29 */   private final Object syncLock = new Object();
/*     */   
/*     */   private final long minecraftStartTimeMilis;
/*     */   
/*     */   private boolean isRunning;
/*     */   
/*     */   private int selfCounter;
/*     */ 
/*     */   
/*     */   public Snooper(String side, ISnooperInfo playerStatCollector, long startTime) {
/*     */     try {
/*  40 */       this.serverUrl = new URL("http://snoop.minecraft.net/" + side + "?version=" + '\002');
/*     */     }
/*  42 */     catch (MalformedURLException var6) {
/*     */       
/*  44 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/*  47 */     this.playerStatsCollector = playerStatCollector;
/*  48 */     this.minecraftStartTimeMilis = startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSnooper() {
/*  56 */     if (!this.isRunning) {
/*     */       
/*  58 */       this.isRunning = true;
/*  59 */       addOSData();
/*  60 */       this.threadTrigger.schedule(new TimerTask()
/*     */           {
/*     */             public void run()
/*     */             {
/*  64 */               if (Snooper.this.playerStatsCollector.isSnooperEnabled()) {
/*     */                 Map<String, Object> map;
/*     */ 
/*     */                 
/*  68 */                 synchronized (Snooper.this.syncLock) {
/*     */                   
/*  70 */                   map = Maps.newHashMap(Snooper.this.clientStats);
/*     */                   
/*  72 */                   if (Snooper.this.selfCounter == 0)
/*     */                   {
/*  74 */                     map.putAll(Snooper.this.snooperStats);
/*     */                   }
/*     */                   
/*  77 */                   Snooper.this.selfCounter = Snooper.this.selfCounter + 1; map.put("snooper_count", Integer.valueOf(Snooper.this.selfCounter));
/*  78 */                   map.put("snooper_token", Snooper.this.uniqueID);
/*     */                 } 
/*     */                 
/*  81 */                 MinecraftServer minecraftserver = (Snooper.this.playerStatsCollector instanceof MinecraftServer) ? (MinecraftServer)Snooper.this.playerStatsCollector : null;
/*  82 */                 HttpUtil.postMap(Snooper.this.serverUrl, map, true, (minecraftserver == null) ? null : minecraftserver.getServerProxy());
/*     */               } 
/*     */             }
/*  85 */           }0L, 900000L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOSData() {
/*  94 */     addJvmArgsToSnooper();
/*  95 */     addClientStat("snooper_token", this.uniqueID);
/*  96 */     addStatToSnooper("snooper_token", this.uniqueID);
/*  97 */     addStatToSnooper("os_name", System.getProperty("os.name"));
/*  98 */     addStatToSnooper("os_version", System.getProperty("os.version"));
/*  99 */     addStatToSnooper("os_architecture", System.getProperty("os.arch"));
/* 100 */     addStatToSnooper("java_version", System.getProperty("java.version"));
/* 101 */     addClientStat("version", "1.12.2");
/* 102 */     this.playerStatsCollector.addServerTypeToSnooper(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addJvmArgsToSnooper() {
/* 107 */     RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 108 */     List<String> list = runtimemxbean.getInputArguments();
/* 109 */     int i = 0;
/*     */     
/* 111 */     for (String s : list) {
/*     */       
/* 113 */       if (s.startsWith("-X"))
/*     */       {
/* 115 */         addClientStat("jvm_arg[" + i++ + "]", s);
/*     */       }
/*     */     } 
/*     */     
/* 119 */     addClientStat("jvm_args", Integer.valueOf(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMemoryStatsToSnooper() {
/* 124 */     addStatToSnooper("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
/* 125 */     addStatToSnooper("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
/* 126 */     addStatToSnooper("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
/* 127 */     addStatToSnooper("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/* 128 */     this.playerStatsCollector.addServerStatsToSnooper(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addClientStat(String statName, Object statValue) {
/* 133 */     synchronized (this.syncLock) {
/*     */       
/* 135 */       this.clientStats.put(statName, statValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStatToSnooper(String statName, Object statValue) {
/* 141 */     synchronized (this.syncLock) {
/*     */       
/* 143 */       this.snooperStats.put(statName, statValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> getCurrentStats() {
/* 149 */     Map<String, String> map = Maps.newLinkedHashMap();
/*     */     
/* 151 */     synchronized (this.syncLock) {
/*     */       
/* 153 */       addMemoryStatsToSnooper();
/*     */       
/* 155 */       for (Map.Entry<String, Object> entry : this.snooperStats.entrySet())
/*     */       {
/* 157 */         map.put(entry.getKey(), entry.getValue().toString());
/*     */       }
/*     */       
/* 160 */       for (Map.Entry<String, Object> entry1 : this.clientStats.entrySet())
/*     */       {
/* 162 */         map.put(entry1.getKey(), entry1.getValue().toString());
/*     */       }
/*     */       
/* 165 */       return map;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSnooperRunning() {
/* 171 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSnooper() {
/* 176 */     this.threadTrigger.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueID() {
/* 181 */     return this.uniqueID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMinecraftStartTimeMillis() {
/* 189 */     return this.minecraftStartTimeMilis;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\profiler\Snooper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
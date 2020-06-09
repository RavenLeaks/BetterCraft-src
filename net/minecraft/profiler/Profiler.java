/*     */ package net.minecraft.profiler;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import optifine.Config;
/*     */ import optifine.Lagometer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Profiler
/*     */ {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*  19 */   private final List<String> sectionList = Lists.newArrayList();
/*  20 */   private final List<Long> timestampList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean profilingEnabled;
/*     */ 
/*     */   
/*  26 */   private String profilingSection = "";
/*  27 */   private final Map<String, Long> profilingMap = Maps.newHashMap();
/*     */   public boolean profilerGlobalEnabled = true;
/*     */   private boolean profilerLocalEnabled;
/*     */   private static final String SCHEDULED_EXECUTABLES = "scheduledExecutables";
/*     */   private static final String TICK = "tick";
/*     */   private static final String PRE_RENDER_ERRORS = "preRenderErrors";
/*     */   private static final String RENDER = "render";
/*     */   private static final String DISPLAY = "display";
/*  35 */   private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
/*  36 */   private static final int HASH_TICK = "tick".hashCode();
/*  37 */   private static final int HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
/*  38 */   private static final int HASH_RENDER = "render".hashCode();
/*  39 */   private static final int HASH_DISPLAY = "display".hashCode();
/*     */ 
/*     */   
/*     */   public Profiler() {
/*  43 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearProfiling() {
/*  51 */     this.profilingMap.clear();
/*  52 */     this.profilingSection = "";
/*  53 */     this.sectionList.clear();
/*  54 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSection(String name) {
/*  62 */     if (Lagometer.isActive()) {
/*     */       
/*  64 */       int i = name.hashCode();
/*     */       
/*  66 */       if (i == HASH_SCHEDULED_EXECUTABLES && name.equals("scheduledExecutables")) {
/*     */         
/*  68 */         Lagometer.timerScheduledExecutables.start();
/*     */       }
/*  70 */       else if (i == HASH_TICK && name.equals("tick") && Config.isMinecraftThread()) {
/*     */         
/*  72 */         Lagometer.timerScheduledExecutables.end();
/*  73 */         Lagometer.timerTick.start();
/*     */       }
/*  75 */       else if (i == HASH_PRE_RENDER_ERRORS && name.equals("preRenderErrors")) {
/*     */         
/*  77 */         Lagometer.timerTick.end();
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     if (Config.isFastRender()) {
/*     */       
/*  83 */       int j = name.hashCode();
/*     */       
/*  85 */       if (j == HASH_RENDER && name.equals("render")) {
/*     */         
/*  87 */         GlStateManager.clearEnabled = false;
/*     */       }
/*  89 */       else if (j == HASH_DISPLAY && name.equals("display")) {
/*     */         
/*  91 */         GlStateManager.clearEnabled = true;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     if (this.profilerLocalEnabled)
/*     */     {
/*  97 */       if (this.profilingEnabled) {
/*     */         
/*  99 */         if (!this.profilingSection.isEmpty())
/*     */         {
/* 101 */           this.profilingSection = String.valueOf(this.profilingSection) + ".";
/*     */         }
/*     */         
/* 104 */         this.profilingSection = String.valueOf(this.profilingSection) + name;
/* 105 */         this.sectionList.add(this.profilingSection);
/* 106 */         this.timestampList.add(Long.valueOf(System.nanoTime()));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194340_a(Supplier<String> p_194340_1_) {
/* 113 */     if (this.profilerLocalEnabled)
/*     */     {
/* 115 */       if (this.profilingEnabled)
/*     */       {
/* 117 */         startSection(p_194340_1_.get());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endSection() {
/* 127 */     if (this.profilerLocalEnabled)
/*     */     {
/* 129 */       if (this.profilingEnabled) {
/*     */         
/* 131 */         long i = System.nanoTime();
/* 132 */         long j = ((Long)this.timestampList.remove(this.timestampList.size() - 1)).longValue();
/* 133 */         this.sectionList.remove(this.sectionList.size() - 1);
/* 134 */         long k = i - j;
/*     */         
/* 136 */         if (this.profilingMap.containsKey(this.profilingSection)) {
/*     */           
/* 138 */           this.profilingMap.put(this.profilingSection, Long.valueOf(((Long)this.profilingMap.get(this.profilingSection)).longValue() + k));
/*     */         }
/*     */         else {
/*     */           
/* 142 */           this.profilingMap.put(this.profilingSection, Long.valueOf(k));
/*     */         } 
/*     */         
/* 145 */         if (k > 100000000L)
/*     */         {
/* 147 */           LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", this.profilingSection, Double.valueOf(k / 1000000.0D));
/*     */         }
/*     */         
/* 150 */         this.profilingSection = this.sectionList.isEmpty() ? "" : this.sectionList.get(this.sectionList.size() - 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Result> getProfilingData(String profilerName) {
/* 157 */     if (!this.profilingEnabled)
/*     */     {
/* 159 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/* 163 */     long i = this.profilingMap.containsKey("root") ? ((Long)this.profilingMap.get("root")).longValue() : 0L;
/* 164 */     long j = this.profilingMap.containsKey(profilerName) ? ((Long)this.profilingMap.get(profilerName)).longValue() : -1L;
/* 165 */     List<Result> list = Lists.newArrayList();
/*     */     
/* 167 */     if (!profilerName.isEmpty())
/*     */     {
/* 169 */       profilerName = String.valueOf(profilerName) + ".";
/*     */     }
/*     */     
/* 172 */     long k = 0L;
/*     */     
/* 174 */     for (String s : this.profilingMap.keySet()) {
/*     */       
/* 176 */       if (s.length() > profilerName.length() && s.startsWith(profilerName) && s.indexOf(".", profilerName.length() + 1) < 0)
/*     */       {
/* 178 */         k += ((Long)this.profilingMap.get(s)).longValue();
/*     */       }
/*     */     } 
/*     */     
/* 182 */     float f = (float)k;
/*     */     
/* 184 */     if (k < j)
/*     */     {
/* 186 */       k = j;
/*     */     }
/*     */     
/* 189 */     if (i < k)
/*     */     {
/* 191 */       i = k;
/*     */     }
/*     */     
/* 194 */     for (String s1 : this.profilingMap.keySet()) {
/*     */       
/* 196 */       if (s1.length() > profilerName.length() && s1.startsWith(profilerName) && s1.indexOf(".", profilerName.length() + 1) < 0) {
/*     */         
/* 198 */         long l = ((Long)this.profilingMap.get(s1)).longValue();
/* 199 */         double d0 = l * 100.0D / k;
/* 200 */         double d1 = l * 100.0D / i;
/* 201 */         String s2 = s1.substring(profilerName.length());
/* 202 */         list.add(new Result(s2, d0, d1));
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     for (String s3 : this.profilingMap.keySet())
/*     */     {
/* 208 */       this.profilingMap.put(s3, Long.valueOf(((Long)this.profilingMap.get(s3)).longValue() * 950L / 1000L));
/*     */     }
/*     */     
/* 211 */     if ((float)k > f)
/*     */     {
/* 213 */       list.add(new Result("unspecified", ((float)k - f) * 100.0D / k, ((float)k - f) * 100.0D / i));
/*     */     }
/*     */     
/* 216 */     Collections.sort(list);
/* 217 */     list.add(0, new Result(profilerName, 100.0D, k * 100.0D / i));
/* 218 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartSection(String name) {
/* 227 */     if (this.profilerLocalEnabled) {
/*     */       
/* 229 */       endSection();
/* 230 */       startSection(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194339_b(Supplier<String> p_194339_1_) {
/* 236 */     if (this.profilerLocalEnabled) {
/*     */       
/* 238 */       endSection();
/* 239 */       func_194340_a(p_194339_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameOfLastSection() {
/* 245 */     return this.sectionList.isEmpty() ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startSection(Class<?> p_startSection_1_) {
/* 250 */     if (this.profilingEnabled)
/*     */     {
/* 252 */       startSection(p_startSection_1_.getSimpleName());
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Result
/*     */     implements Comparable<Result>
/*     */   {
/*     */     public double usePercentage;
/*     */     public double totalUsePercentage;
/*     */     public String profilerName;
/*     */     
/*     */     public Result(String profilerName, double usePercentage, double totalUsePercentage) {
/* 264 */       this.profilerName = profilerName;
/* 265 */       this.usePercentage = usePercentage;
/* 266 */       this.totalUsePercentage = totalUsePercentage;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Result p_compareTo_1_) {
/* 271 */       if (p_compareTo_1_.usePercentage < this.usePercentage)
/*     */       {
/* 273 */         return -1;
/*     */       }
/*     */ 
/*     */       
/* 277 */       return (p_compareTo_1_.usePercentage > this.usePercentage) ? 1 : p_compareTo_1_.profilerName.compareTo(this.profilerName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getColor() {
/* 283 */       return (this.profilerName.hashCode() & 0xAAAAAA) + 4473924;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\profiler\Profiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
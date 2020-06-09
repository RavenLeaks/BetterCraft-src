/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import optifine.CrashReporter;
/*     */ import optifine.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  29 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final String description;
/*     */ 
/*     */   
/*     */   private final Throwable cause;
/*     */ 
/*     */   
/*  38 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*  39 */   private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private File crashReportFile;
/*     */   
/*     */   private boolean firstCategoryInCrashReport = true;
/*     */   
/*  46 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*     */   
/*     */   private boolean reported = false;
/*     */   
/*     */   public CrashReport(String descriptionIn, Throwable causeThrowable) {
/*  51 */     this.description = descriptionIn;
/*  52 */     this.cause = causeThrowable;
/*  53 */     populateEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEnvironment() {
/*  62 */     this.theReportCategory.setDetail("Minecraft Version", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  66 */             return "1.12.2";
/*     */           }
/*     */         });
/*  69 */     this.theReportCategory.setDetail("Operating System", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  73 */             return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */           }
/*     */         });
/*  76 */     this.theReportCategory.setDetail("Java Version", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  80 */             return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
/*     */           }
/*     */         });
/*  83 */     this.theReportCategory.setDetail("Java VM Version", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  87 */             return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */           }
/*     */         });
/*  90 */     this.theReportCategory.setDetail("Memory", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  94 */             Runtime runtime = Runtime.getRuntime();
/*  95 */             long i = runtime.maxMemory();
/*  96 */             long j = runtime.totalMemory();
/*  97 */             long k = runtime.freeMemory();
/*  98 */             long l = i / 1024L / 1024L;
/*  99 */             long i1 = j / 1024L / 1024L;
/* 100 */             long j1 = k / 1024L / 1024L;
/* 101 */             return String.valueOf(k) + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */           }
/*     */         });
/* 104 */     this.theReportCategory.setDetail("JVM Flags", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 108 */             RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 109 */             List<String> list = runtimemxbean.getInputArguments();
/* 110 */             int i = 0;
/* 111 */             StringBuilder stringbuilder = new StringBuilder();
/*     */             
/* 113 */             for (String s : list) {
/*     */               
/* 115 */               if (s.startsWith("-X")) {
/*     */                 
/* 117 */                 if (i++ > 0)
/*     */                 {
/* 119 */                   stringbuilder.append(" ");
/*     */                 }
/*     */                 
/* 122 */                 stringbuilder.append(s);
/*     */               } 
/*     */             } 
/*     */             
/* 126 */             return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder.toString() });
/*     */           }
/*     */         });
/* 129 */     this.theReportCategory.setDetail("IntCache", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 133 */             return IntCache.getCacheSizes();
/*     */           }
/*     */         });
/*     */     
/* 137 */     if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
/*     */       
/* 139 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 140 */       Reflector.callString(object, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] { this, this.theReportCategory });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 149 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCrashCause() {
/* 157 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSectionsInStringBuilder(StringBuilder builder) {
/* 165 */     if ((this.stacktrace == null || this.stacktrace.length <= 0) && !this.crashReportSections.isEmpty())
/*     */     {
/* 167 */       this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
/*     */     }
/*     */     
/* 170 */     if (this.stacktrace != null && this.stacktrace.length > 0) {
/*     */       
/* 172 */       builder.append("-- Head --\n");
/* 173 */       builder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
/* 174 */       builder.append("Stacktrace:\n"); byte b; int i;
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 176 */       for (i = (arrayOfStackTraceElement = this.stacktrace).length, b = 0; b < i; ) { StackTraceElement stacktraceelement = arrayOfStackTraceElement[b];
/*     */         
/* 178 */         builder.append("\t").append("at ").append(stacktraceelement);
/* 179 */         builder.append("\n");
/*     */         b++; }
/*     */       
/* 182 */       builder.append("\n");
/*     */     } 
/*     */     
/* 185 */     for (CrashReportCategory crashreportcategory : this.crashReportSections) {
/*     */       
/* 187 */       crashreportcategory.appendToStringBuilder(builder);
/* 188 */       builder.append("\n\n");
/*     */     } 
/*     */     
/* 191 */     this.theReportCategory.appendToStringBuilder(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCauseStackTraceOrString() {
/* 199 */     StringWriter stringwriter = null;
/* 200 */     PrintWriter printwriter = null;
/* 201 */     Throwable throwable = this.cause;
/*     */     
/* 203 */     if (throwable.getMessage() == null) {
/*     */       
/* 205 */       if (throwable instanceof NullPointerException) {
/*     */         
/* 207 */         throwable = new NullPointerException(this.description);
/*     */       }
/* 209 */       else if (throwable instanceof StackOverflowError) {
/*     */         
/* 211 */         throwable = new StackOverflowError(this.description);
/*     */       }
/* 213 */       else if (throwable instanceof OutOfMemoryError) {
/*     */         
/* 215 */         throwable = new OutOfMemoryError(this.description);
/*     */       } 
/*     */       
/* 218 */       throwable.setStackTrace(this.cause.getStackTrace());
/*     */     } 
/*     */     
/* 221 */     String s = throwable.toString();
/*     */ 
/*     */     
/*     */     try {
/* 225 */       stringwriter = new StringWriter();
/* 226 */       printwriter = new PrintWriter(stringwriter);
/* 227 */       throwable.printStackTrace(printwriter);
/* 228 */       s = stringwriter.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 232 */       IOUtils.closeQuietly(stringwriter);
/* 233 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 236 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompleteReport() {
/* 244 */     if (!this.reported) {
/*     */       
/* 246 */       this.reported = true;
/* 247 */       CrashReporter.onCrashReport(this, this.theReportCategory);
/*     */     } 
/*     */     
/* 250 */     StringBuilder stringbuilder = new StringBuilder();
/* 251 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 252 */     Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[] { stringbuilder });
/* 253 */     Reflector.call(Reflector.CoreModManager_onCrash, new Object[] { stringbuilder });
/* 254 */     stringbuilder.append("// ");
/* 255 */     stringbuilder.append(getWittyComment());
/* 256 */     stringbuilder.append("\n\n");
/* 257 */     stringbuilder.append("Time: ");
/* 258 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 259 */     stringbuilder.append("\n");
/* 260 */     stringbuilder.append("Description: ");
/* 261 */     stringbuilder.append(this.description);
/* 262 */     stringbuilder.append("\n\n");
/* 263 */     stringbuilder.append(getCauseStackTraceOrString());
/* 264 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 266 */     for (int i = 0; i < 87; i++)
/*     */     {
/* 268 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 271 */     stringbuilder.append("\n\n");
/* 272 */     getSectionsInStringBuilder(stringbuilder);
/* 273 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 281 */     return this.crashReportFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveToFile(File toFile) {
/*     */     boolean flag;
/* 289 */     if (this.crashReportFile != null)
/*     */     {
/* 291 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 295 */     if (toFile.getParentFile() != null)
/*     */     {
/* 297 */       toFile.getParentFile().mkdirs();
/*     */     }
/*     */     
/* 300 */     Writer writer = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 305 */       writer = new OutputStreamWriter(new FileOutputStream(toFile), StandardCharsets.UTF_8);
/* 306 */       writer.write(getCompleteReport());
/* 307 */       this.crashReportFile = toFile;
/* 308 */       boolean flag1 = true;
/* 309 */       boolean flag2 = flag1;
/* 310 */       return flag2;
/*     */     }
/* 312 */     catch (Throwable throwable1) {
/*     */       
/* 314 */       LOGGER.error("Could not save crash report to {}", toFile, throwable1);
/* 315 */       flag = false;
/*     */     }
/*     */     finally {
/*     */       
/* 319 */       IOUtils.closeQuietly(writer);
/*     */     } 
/*     */     
/* 322 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory getCategory() {
/* 328 */     return this.theReportCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategory(String name) {
/* 336 */     return makeCategoryDepth(name, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
/* 344 */     CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
/*     */     
/* 346 */     if (this.firstCategoryInCrashReport) {
/*     */       
/* 348 */       int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
/* 349 */       StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
/* 350 */       StackTraceElement stacktraceelement = null;
/* 351 */       StackTraceElement stacktraceelement1 = null;
/* 352 */       int j = astacktraceelement.length - i;
/*     */       
/* 354 */       if (j < 0)
/*     */       {
/* 356 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
/*     */       }
/*     */       
/* 359 */       if (astacktraceelement != null && j >= 0 && j < astacktraceelement.length) {
/*     */         
/* 361 */         stacktraceelement = astacktraceelement[j];
/*     */         
/* 363 */         if (astacktraceelement.length + 1 - i < astacktraceelement.length)
/*     */         {
/* 365 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
/*     */         }
/*     */       } 
/*     */       
/* 369 */       this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
/*     */       
/* 371 */       if (i > 0 && !this.crashReportSections.isEmpty()) {
/*     */         
/* 373 */         CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 374 */         crashreportcategory1.trimStackTraceEntriesFromBottom(i);
/*     */       }
/* 376 */       else if (astacktraceelement != null && astacktraceelement.length >= i && j >= 0 && j < astacktraceelement.length) {
/*     */         
/* 378 */         this.stacktrace = new StackTraceElement[j];
/* 379 */         System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
/*     */       }
/*     */       else {
/*     */         
/* 383 */         this.firstCategoryInCrashReport = false;
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     this.crashReportSections.add(crashreportcategory);
/* 388 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 396 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */ 
/*     */     
/*     */     try {
/* 400 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 402 */     catch (Throwable var2) {
/*     */       
/* 404 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
/*     */     CrashReport crashreport;
/* 415 */     if (causeIn instanceof ReportedException) {
/*     */       
/* 417 */       crashreport = ((ReportedException)causeIn).getCrashReport();
/*     */     }
/*     */     else {
/*     */       
/* 421 */       crashreport = new CrashReport(descriptionIn, causeIn);
/*     */     } 
/*     */     
/* 424 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\crash\CrashReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
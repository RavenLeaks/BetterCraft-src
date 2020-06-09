/*     */ package optifine;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReporter
/*     */ {
/*     */   public static void onCrashReport(CrashReport p_onCrashReport_0_, CrashReportCategory p_onCrashReport_1_) {
/*     */     try {
/*  16 */       GameSettings gamesettings = Config.getGameSettings();
/*     */       
/*  18 */       if (gamesettings == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  23 */       if (!gamesettings.snooperEnabled) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  28 */       Throwable throwable = p_onCrashReport_0_.getCrashCause();
/*     */       
/*  30 */       if (throwable == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  35 */       if (throwable.getClass() == Throwable.class) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  40 */       if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  45 */       extendCrashReport(p_onCrashReport_1_);
/*  46 */       String s = "http://optifine.net/crashReport";
/*  47 */       String s1 = makeReport(p_onCrashReport_0_);
/*  48 */       byte[] abyte = s1.getBytes("ASCII");
/*  49 */       IFileUploadListener ifileuploadlistener = new IFileUploadListener()
/*     */         {
/*     */           public void fileUploadFinished(String p_fileUploadFinished_1_, byte[] p_fileUploadFinished_2_, Throwable p_fileUploadFinished_3_) {}
/*     */         };
/*     */ 
/*     */       
/*  55 */       Map<Object, Object> map = new HashMap<>();
/*  56 */       map.put("OF-Version", Config.getVersion());
/*  57 */       map.put("OF-Summary", makeSummary(p_onCrashReport_0_));
/*  58 */       FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
/*  59 */       fileuploadthread.setPriority(10);
/*  60 */       fileuploadthread.start();
/*  61 */       Thread.sleep(1000L);
/*     */     }
/*  63 */     catch (Exception exception) {
/*     */       
/*  65 */       Config.dbg(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeReport(CrashReport p_makeReport_0_) {
/*  71 */     StringBuffer stringbuffer = new StringBuffer();
/*  72 */     stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
/*  73 */     stringbuffer.append("Summary: " + makeSummary(p_makeReport_0_) + "\n");
/*  74 */     stringbuffer.append("\n");
/*  75 */     stringbuffer.append(p_makeReport_0_.getCompleteReport());
/*  76 */     stringbuffer.append("\n");
/*  77 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeSummary(CrashReport p_makeSummary_0_) {
/*  82 */     Throwable throwable = p_makeSummary_0_.getCrashCause();
/*     */     
/*  84 */     if (throwable == null)
/*     */     {
/*  86 */       return "Unknown";
/*     */     }
/*     */ 
/*     */     
/*  90 */     StackTraceElement[] astacktraceelement = throwable.getStackTrace();
/*  91 */     String s = "unknown";
/*     */     
/*  93 */     if (astacktraceelement.length > 0)
/*     */     {
/*  95 */       s = astacktraceelement[0].toString().trim();
/*     */     }
/*     */     
/*  98 */     String s1 = String.valueOf(throwable.getClass().getName()) + ": " + throwable.getMessage() + " (" + p_makeSummary_0_.getDescription() + ") [" + s + "]";
/*  99 */     return s1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void extendCrashReport(CrashReportCategory p_extendCrashReport_0_) {
/* 105 */     p_extendCrashReport_0_.addCrashSection("OptiFine Version", Config.getVersion());
/*     */     
/* 107 */     if (Config.getGameSettings() != null) {
/*     */       
/* 109 */       p_extendCrashReport_0_.addCrashSection("Render Distance Chunks", Config.getChunkViewDistance());
/* 110 */       p_extendCrashReport_0_.addCrashSection("Mipmaps", Config.getMipmapLevels());
/* 111 */       p_extendCrashReport_0_.addCrashSection("Anisotropic Filtering", Config.getAnisotropicFilterLevel());
/* 112 */       p_extendCrashReport_0_.addCrashSection("Antialiasing", Config.getAntialiasingLevel());
/* 113 */       p_extendCrashReport_0_.addCrashSection("Multitexture", Config.isMultiTexture());
/*     */     } 
/*     */     
/* 116 */     p_extendCrashReport_0_.addCrashSection("Shaders", Shaders.getShaderPackName());
/* 117 */     p_extendCrashReport_0_.addCrashSection("OpenGlVersion", Config.openGlVersion);
/* 118 */     p_extendCrashReport_0_.addCrashSection("OpenGlRenderer", Config.openGlRenderer);
/* 119 */     p_extendCrashReport_0_.addCrashSection("OpenGlVendor", Config.openGlVendor);
/* 120 */     p_extendCrashReport_0_.addCrashSection("CpuCount", Config.getAvailableProcessors());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CrashReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
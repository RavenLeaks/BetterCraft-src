/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Lagometer
/*     */ {
/*     */   private static Minecraft mc;
/*     */   private static GameSettings gameSettings;
/*     */   private static Profiler profiler;
/*     */   public static boolean active = false;
/*  20 */   public static TimerNano timerTick = new TimerNano();
/*  21 */   public static TimerNano timerScheduledExecutables = new TimerNano();
/*  22 */   public static TimerNano timerChunkUpload = new TimerNano();
/*  23 */   public static TimerNano timerChunkUpdate = new TimerNano();
/*  24 */   public static TimerNano timerVisibility = new TimerNano();
/*  25 */   public static TimerNano timerTerrain = new TimerNano();
/*  26 */   public static TimerNano timerServer = new TimerNano();
/*  27 */   private static long[] timesFrame = new long[512];
/*  28 */   private static long[] timesTick = new long[512];
/*  29 */   private static long[] timesScheduledExecutables = new long[512];
/*  30 */   private static long[] timesChunkUpload = new long[512];
/*  31 */   private static long[] timesChunkUpdate = new long[512];
/*  32 */   private static long[] timesVisibility = new long[512];
/*  33 */   private static long[] timesTerrain = new long[512];
/*  34 */   private static long[] timesServer = new long[512];
/*  35 */   private static boolean[] gcs = new boolean[512];
/*  36 */   private static int numRecordedFrameTimes = 0;
/*  37 */   private static long prevFrameTimeNano = -1L;
/*  38 */   private static long renderTimeNano = 0L;
/*  39 */   private static long memTimeStartMs = System.currentTimeMillis();
/*  40 */   private static long memStart = getMemoryUsed();
/*  41 */   private static long memTimeLast = memTimeStartMs;
/*  42 */   private static long memLast = memStart;
/*  43 */   private static long memTimeDiffMs = 1L;
/*  44 */   private static long memDiff = 0L;
/*  45 */   private static int memMbSec = 0;
/*     */ 
/*     */   
/*     */   public static boolean updateMemoryAllocation() {
/*  49 */     long i = System.currentTimeMillis();
/*  50 */     long j = getMemoryUsed();
/*  51 */     boolean flag = false;
/*     */     
/*  53 */     if (j < memLast) {
/*     */       
/*  55 */       double d0 = memDiff / 1000000.0D;
/*  56 */       double d1 = memTimeDiffMs / 1000.0D;
/*  57 */       int k = (int)(d0 / d1);
/*     */       
/*  59 */       if (k > 0)
/*     */       {
/*  61 */         memMbSec = k;
/*     */       }
/*     */       
/*  64 */       memTimeStartMs = i;
/*  65 */       memStart = j;
/*  66 */       memTimeDiffMs = 0L;
/*  67 */       memDiff = 0L;
/*  68 */       flag = true;
/*     */     }
/*     */     else {
/*     */       
/*  72 */       memTimeDiffMs = i - memTimeStartMs;
/*  73 */       memDiff = j - memStart;
/*     */     } 
/*     */     
/*  76 */     memTimeLast = i;
/*  77 */     memLast = j;
/*  78 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getMemoryUsed() {
/*  83 */     Runtime runtime = Runtime.getRuntime();
/*  84 */     return runtime.totalMemory() - runtime.freeMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateLagometer() {
/*  89 */     if (mc == null) {
/*     */       
/*  91 */       mc = Minecraft.getMinecraft();
/*  92 */       gameSettings = mc.gameSettings;
/*  93 */       profiler = mc.mcProfiler;
/*     */     } 
/*     */     
/*  96 */     if (gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.showLagometer)) {
/*     */       
/*  98 */       active = true;
/*  99 */       long timeNowNano = System.nanoTime();
/*     */       
/* 101 */       if (prevFrameTimeNano == -1L)
/*     */       {
/* 103 */         prevFrameTimeNano = timeNowNano;
/*     */       }
/*     */       else
/*     */       {
/* 107 */         int j = numRecordedFrameTimes & timesFrame.length - 1;
/* 108 */         numRecordedFrameTimes++;
/* 109 */         boolean flag = updateMemoryAllocation();
/* 110 */         timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
/* 111 */         timesTick[j] = timerTick.timeNano;
/* 112 */         timesScheduledExecutables[j] = timerScheduledExecutables.timeNano;
/* 113 */         timesChunkUpload[j] = timerChunkUpload.timeNano;
/* 114 */         timesChunkUpdate[j] = timerChunkUpdate.timeNano;
/* 115 */         timesVisibility[j] = timerVisibility.timeNano;
/* 116 */         timesTerrain[j] = timerTerrain.timeNano;
/* 117 */         timesServer[j] = timerServer.timeNano;
/* 118 */         gcs[j] = flag;
/* 119 */         timerTick.reset();
/* 120 */         timerScheduledExecutables.reset();
/* 121 */         timerVisibility.reset();
/* 122 */         timerChunkUpdate.reset();
/* 123 */         timerChunkUpload.reset();
/* 124 */         timerTerrain.reset();
/* 125 */         timerServer.reset();
/* 126 */         prevFrameTimeNano = System.nanoTime();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 131 */       active = false;
/* 132 */       prevFrameTimeNano = -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void showLagometer(ScaledResolution p_showLagometer_0_) {
/* 138 */     if (gameSettings != null)
/*     */     {
/* 140 */       if (gameSettings.ofLagometer || gameSettings.showLagometer) {
/*     */         
/* 142 */         long i = System.nanoTime();
/* 143 */         GlStateManager.clear(256);
/* 144 */         GlStateManager.matrixMode(5889);
/* 145 */         GlStateManager.pushMatrix();
/* 146 */         GlStateManager.enableColorMaterial();
/* 147 */         GlStateManager.loadIdentity();
/* 148 */         GlStateManager.ortho(0.0D, mc.displayWidth, mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 149 */         GlStateManager.matrixMode(5888);
/* 150 */         GlStateManager.pushMatrix();
/* 151 */         GlStateManager.loadIdentity();
/* 152 */         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 153 */         GL11.glLineWidth(1.0F);
/* 154 */         GlStateManager.disableTexture2D();
/* 155 */         Tessellator tessellator = Tessellator.getInstance();
/* 156 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 157 */         bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 159 */         for (int j = 0; j < timesFrame.length; j++) {
/*     */           
/* 161 */           int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
/* 162 */           k += 155;
/* 163 */           float f = mc.displayHeight;
/* 164 */           long l = 0L;
/*     */           
/* 166 */           if (gcs[j]) {
/*     */             
/* 168 */             renderTime(j, timesFrame[j], k, k / 2, 0, f, bufferbuilder);
/*     */           }
/*     */           else {
/*     */             
/* 172 */             renderTime(j, timesFrame[j], k, k, k, f, bufferbuilder);
/* 173 */             f -= (float)renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, bufferbuilder);
/* 174 */             f -= (float)renderTime(j, timesTerrain[j], 0, k, 0, f, bufferbuilder);
/* 175 */             f -= (float)renderTime(j, timesVisibility[j], k, k, 0, f, bufferbuilder);
/* 176 */             f -= (float)renderTime(j, timesChunkUpdate[j], k, 0, 0, f, bufferbuilder);
/* 177 */             f -= (float)renderTime(j, timesChunkUpload[j], k, 0, k, f, bufferbuilder);
/* 178 */             f -= (float)renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, bufferbuilder);
/* 179 */             float f2 = f - (float)renderTime(j, timesTick[j], 0, k, k, f, bufferbuilder);
/*     */           } 
/*     */         } 
/*     */         
/* 183 */         renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, mc.displayHeight, bufferbuilder);
/* 184 */         renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, mc.displayHeight, bufferbuilder);
/* 185 */         tessellator.draw();
/* 186 */         GlStateManager.enableTexture2D();
/* 187 */         int j2 = mc.displayHeight - 80;
/* 188 */         int k2 = mc.displayHeight - 160;
/* 189 */         mc.fontRendererObj.drawString("30", 2, k2 + 1, -8947849);
/* 190 */         mc.fontRendererObj.drawString("30", 1, k2, -3881788);
/* 191 */         mc.fontRendererObj.drawString("60", 2, j2 + 1, -8947849);
/* 192 */         mc.fontRendererObj.drawString("60", 1, j2, -3881788);
/* 193 */         GlStateManager.matrixMode(5889);
/* 194 */         GlStateManager.popMatrix();
/* 195 */         GlStateManager.matrixMode(5888);
/* 196 */         GlStateManager.popMatrix();
/* 197 */         GlStateManager.enableTexture2D();
/* 198 */         float f1 = 1.0F - (float)((System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
/* 199 */         f1 = Config.limit(f1, 0.0F, 1.0F);
/* 200 */         int l2 = (int)(170.0F + f1 * 85.0F);
/* 201 */         int i1 = (int)(100.0F + f1 * 55.0F);
/* 202 */         int j1 = (int)(10.0F + f1 * 10.0F);
/* 203 */         int k1 = l2 << 16 | i1 << 8 | j1;
/* 204 */         int l1 = 512 / p_showLagometer_0_.getScaleFactor() + 2;
/* 205 */         int i2 = mc.displayHeight / p_showLagometer_0_.getScaleFactor() - 8;
/* 206 */         GuiIngame guiingame = mc.ingameGUI;
/* 207 */         GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
/* 208 */         mc.fontRendererObj.drawString(" " + memMbSec + " MB/s", l1, i2, k1);
/* 209 */         renderTimeNano = System.nanoTime() - i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static long renderTime(int p_renderTime_0_, long p_renderTime_1_, int p_renderTime_3_, int p_renderTime_4_, int p_renderTime_5_, float p_renderTime_6_, BufferBuilder p_renderTime_7_) {
/* 216 */     long i = p_renderTime_1_ / 200000L;
/*     */     
/* 218 */     if (i < 3L)
/*     */     {
/* 220 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 224 */     p_renderTime_7_.pos((p_renderTime_0_ + 0.5F), (p_renderTime_6_ - (float)i + 0.5F), 0.0D).color(p_renderTime_3_, p_renderTime_4_, p_renderTime_5_, 255).endVertex();
/* 225 */     p_renderTime_7_.pos((p_renderTime_0_ + 0.5F), (p_renderTime_6_ + 0.5F), 0.0D).color(p_renderTime_3_, p_renderTime_4_, p_renderTime_5_, 255).endVertex();
/* 226 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long renderTimeDivider(int p_renderTimeDivider_0_, int p_renderTimeDivider_1_, long p_renderTimeDivider_2_, int p_renderTimeDivider_4_, int p_renderTimeDivider_5_, int p_renderTimeDivider_6_, float p_renderTimeDivider_7_, BufferBuilder p_renderTimeDivider_8_) {
/* 232 */     long i = p_renderTimeDivider_2_ / 200000L;
/*     */     
/* 234 */     if (i < 3L)
/*     */     {
/* 236 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 240 */     p_renderTimeDivider_8_.pos((p_renderTimeDivider_0_ + 0.5F), (p_renderTimeDivider_7_ - (float)i + 0.5F), 0.0D).color(p_renderTimeDivider_4_, p_renderTimeDivider_5_, p_renderTimeDivider_6_, 255).endVertex();
/* 241 */     p_renderTimeDivider_8_.pos((p_renderTimeDivider_1_ + 0.5F), (p_renderTimeDivider_7_ - (float)i + 0.5F), 0.0D).color(p_renderTimeDivider_4_, p_renderTimeDivider_5_, p_renderTimeDivider_6_, 255).endVertex();
/* 242 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 248 */     return active;
/*     */   }
/*     */   
/*     */   public static class TimerNano
/*     */   {
/* 253 */     public long timeStartNano = 0L;
/* 254 */     public long timeNano = 0L;
/*     */ 
/*     */     
/*     */     public void start() {
/* 258 */       if (Lagometer.active)
/*     */       {
/* 260 */         if (this.timeStartNano == 0L)
/*     */         {
/* 262 */           this.timeStartNano = System.nanoTime();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 269 */       if (Lagometer.active)
/*     */       {
/* 271 */         if (this.timeStartNano != 0L) {
/*     */           
/* 273 */           this.timeNano += System.nanoTime() - this.timeStartNano;
/* 274 */           this.timeStartNano = 0L;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void reset() {
/* 281 */       this.timeNano = 0L;
/* 282 */       this.timeStartNano = 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\Lagometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
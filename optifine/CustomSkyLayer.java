/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CustomSkyLayer
/*     */ {
/*  15 */   public String source = null;
/*  16 */   private int startFadeIn = -1;
/*  17 */   private int endFadeIn = -1;
/*  18 */   private int startFadeOut = -1;
/*  19 */   private int endFadeOut = -1;
/*  20 */   private int blend = 1;
/*     */   private boolean rotate = false;
/*  22 */   private float speed = 1.0F;
/*     */   private float[] axis;
/*     */   private RangeListInt days;
/*     */   private int daysLoop;
/*     */   private boolean weatherClear;
/*     */   private boolean weatherRain;
/*     */   private boolean weatherThunder;
/*     */   public int textureId;
/*  30 */   public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
/*     */   
/*     */   private static final String WEATHER_CLEAR = "clear";
/*     */   private static final String WEATHER_RAIN = "rain";
/*     */   private static final String WEATHER_THUNDER = "thunder";
/*     */   
/*     */   public CustomSkyLayer(Properties p_i35_1_, String p_i35_2_) {
/*  37 */     this.axis = DEFAULT_AXIS;
/*  38 */     this.days = null;
/*  39 */     this.daysLoop = 8;
/*  40 */     this.weatherClear = true;
/*  41 */     this.weatherRain = false;
/*  42 */     this.weatherThunder = false;
/*  43 */     this.textureId = -1;
/*  44 */     ConnectedParser connectedparser = new ConnectedParser("CustomSky");
/*  45 */     this.source = p_i35_1_.getProperty("source", p_i35_2_);
/*  46 */     this.startFadeIn = parseTime(p_i35_1_.getProperty("startFadeIn"));
/*  47 */     this.endFadeIn = parseTime(p_i35_1_.getProperty("endFadeIn"));
/*  48 */     this.startFadeOut = parseTime(p_i35_1_.getProperty("startFadeOut"));
/*  49 */     this.endFadeOut = parseTime(p_i35_1_.getProperty("endFadeOut"));
/*  50 */     this.blend = Blender.parseBlend(p_i35_1_.getProperty("blend"));
/*  51 */     this.rotate = parseBoolean(p_i35_1_.getProperty("rotate"), true);
/*  52 */     this.speed = parseFloat(p_i35_1_.getProperty("speed"), 1.0F);
/*  53 */     this.axis = parseAxis(p_i35_1_.getProperty("axis"), DEFAULT_AXIS);
/*  54 */     this.days = connectedparser.parseRangeListInt(p_i35_1_.getProperty("days"));
/*  55 */     this.daysLoop = connectedparser.parseInt(p_i35_1_.getProperty("daysLoop"), 8);
/*  56 */     List<String> list = parseWeatherList(p_i35_1_.getProperty("weather", "clear"));
/*  57 */     this.weatherClear = list.contains("clear");
/*  58 */     this.weatherRain = list.contains("rain");
/*  59 */     this.weatherThunder = list.contains("thunder");
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> parseWeatherList(String p_parseWeatherList_1_) {
/*  64 */     List<String> list = Arrays.asList(new String[] { "clear", "rain", "thunder" });
/*  65 */     List<String> list1 = new ArrayList<>();
/*  66 */     String[] astring = Config.tokenize(p_parseWeatherList_1_, " ");
/*     */     
/*  68 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  70 */       String s = astring[i];
/*     */       
/*  72 */       if (!list.contains(s)) {
/*     */         
/*  74 */         Config.warn("Unknown weather: " + s);
/*     */       }
/*     */       else {
/*     */         
/*  78 */         list1.add(s);
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseTime(String p_parseTime_1_) {
/*  87 */     if (p_parseTime_1_ == null)
/*     */     {
/*  89 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  93 */     String[] astring = Config.tokenize(p_parseTime_1_, ":");
/*     */     
/*  95 */     if (astring.length != 2) {
/*     */       
/*  97 */       Config.warn("Invalid time: " + p_parseTime_1_);
/*  98 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     String s = astring[0];
/* 103 */     String s1 = astring[1];
/* 104 */     int i = Config.parseInt(s, -1);
/* 105 */     int j = Config.parseInt(s1, -1);
/*     */     
/* 107 */     if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
/*     */       
/* 109 */       i -= 6;
/*     */       
/* 111 */       if (i < 0)
/*     */       {
/* 113 */         i += 24;
/*     */       }
/*     */       
/* 116 */       int k = i * 1000 + (int)(j / 60.0D * 1000.0D);
/* 117 */       return k;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     Config.warn("Invalid time: " + p_parseTime_1_);
/* 122 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseBoolean(String p_parseBoolean_1_, boolean p_parseBoolean_2_) {
/* 130 */     if (p_parseBoolean_1_ == null)
/*     */     {
/* 132 */       return p_parseBoolean_2_;
/*     */     }
/* 134 */     if (p_parseBoolean_1_.toLowerCase().equals("true"))
/*     */     {
/* 136 */       return true;
/*     */     }
/* 138 */     if (p_parseBoolean_1_.toLowerCase().equals("false"))
/*     */     {
/* 140 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 144 */     Config.warn("Unknown boolean: " + p_parseBoolean_1_);
/* 145 */     return p_parseBoolean_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseFloat(String p_parseFloat_1_, float p_parseFloat_2_) {
/* 151 */     if (p_parseFloat_1_ == null)
/*     */     {
/* 153 */       return p_parseFloat_2_;
/*     */     }
/*     */ 
/*     */     
/* 157 */     float f = Config.parseFloat(p_parseFloat_1_, Float.MIN_VALUE);
/*     */     
/* 159 */     if (f == Float.MIN_VALUE) {
/*     */       
/* 161 */       Config.warn("Invalid value: " + p_parseFloat_1_);
/* 162 */       return p_parseFloat_2_;
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] parseAxis(String p_parseAxis_1_, float[] p_parseAxis_2_) {
/* 173 */     if (p_parseAxis_1_ == null)
/*     */     {
/* 175 */       return p_parseAxis_2_;
/*     */     }
/*     */ 
/*     */     
/* 179 */     String[] astring = Config.tokenize(p_parseAxis_1_, " ");
/*     */     
/* 181 */     if (astring.length != 3) {
/*     */       
/* 183 */       Config.warn("Invalid axis: " + p_parseAxis_1_);
/* 184 */       return p_parseAxis_2_;
/*     */     } 
/*     */ 
/*     */     
/* 188 */     float[] afloat = new float[3];
/*     */     
/* 190 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 192 */       afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);
/*     */       
/* 194 */       if (afloat[i] == Float.MIN_VALUE) {
/*     */         
/* 196 */         Config.warn("Invalid axis: " + p_parseAxis_1_);
/* 197 */         return p_parseAxis_2_;
/*     */       } 
/*     */       
/* 200 */       if (afloat[i] < -1.0F || afloat[i] > 1.0F) {
/*     */         
/* 202 */         Config.warn("Invalid axis values: " + p_parseAxis_1_);
/* 203 */         return p_parseAxis_2_;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     float f2 = afloat[0];
/* 208 */     float f = afloat[1];
/* 209 */     float f1 = afloat[2];
/*     */     
/* 211 */     if (f2 * f2 + f * f + f1 * f1 < 1.0E-5F) {
/*     */       
/* 213 */       Config.warn("Invalid axis values: " + p_parseAxis_1_);
/* 214 */       return p_parseAxis_2_;
/*     */     } 
/*     */ 
/*     */     
/* 218 */     float[] afloat1 = { f1, f, -f2 };
/* 219 */     return afloat1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String p_isValid_1_) {
/* 227 */     if (this.source == null) {
/*     */       
/* 229 */       Config.warn("No source texture: " + p_isValid_1_);
/* 230 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(p_isValid_1_));
/*     */     
/* 236 */     if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
/*     */       
/* 238 */       int i = normalizeTime(this.endFadeIn - this.startFadeIn);
/*     */       
/* 240 */       if (this.startFadeOut < 0) {
/*     */         
/* 242 */         this.startFadeOut = normalizeTime(this.endFadeOut - i);
/*     */         
/* 244 */         if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn))
/*     */         {
/* 246 */           this.startFadeOut = this.endFadeIn;
/*     */         }
/*     */       } 
/*     */       
/* 250 */       int j = normalizeTime(this.startFadeOut - this.endFadeIn);
/* 251 */       int k = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 252 */       int l = normalizeTime(this.startFadeIn - this.endFadeOut);
/* 253 */       int i1 = i + j + k + l;
/*     */       
/* 255 */       if (i1 != 24000) {
/*     */         
/* 257 */         Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
/* 258 */         return false;
/*     */       } 
/* 260 */       if (this.speed < 0.0F) {
/*     */         
/* 262 */         Config.warn("Invalid speed: " + this.speed);
/* 263 */         return false;
/*     */       } 
/* 265 */       if (this.daysLoop <= 0) {
/*     */         
/* 267 */         Config.warn("Invalid daysLoop: " + this.daysLoop);
/* 268 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 272 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int normalizeTime(int p_normalizeTime_1_) {
/* 285 */     while (p_normalizeTime_1_ >= 24000)
/*     */     {
/* 287 */       p_normalizeTime_1_ -= 24000;
/*     */     }
/*     */     
/* 290 */     while (p_normalizeTime_1_ < 0)
/*     */     {
/* 292 */       p_normalizeTime_1_ += 24000;
/*     */     }
/*     */     
/* 295 */     return p_normalizeTime_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_) {
/* 300 */     float f = 1.0F - p_render_3_;
/* 301 */     float f1 = p_render_3_ - p_render_4_;
/* 302 */     float f2 = 0.0F;
/*     */     
/* 304 */     if (this.weatherClear)
/*     */     {
/* 306 */       f2 += f;
/*     */     }
/*     */     
/* 309 */     if (this.weatherRain)
/*     */     {
/* 311 */       f2 += f1;
/*     */     }
/*     */     
/* 314 */     if (this.weatherThunder)
/*     */     {
/* 316 */       f2 += p_render_4_;
/*     */     }
/*     */     
/* 319 */     f2 = Config.limit(f2, 0.0F, 1.0F);
/* 320 */     float f3 = f2 * getFadeBrightness(p_render_1_);
/* 321 */     f3 = Config.limit(f3, 0.0F, 1.0F);
/*     */     
/* 323 */     if (f3 >= 1.0E-4F) {
/*     */       
/* 325 */       GlStateManager.bindTexture(this.textureId);
/* 326 */       Blender.setupBlend(this.blend, f3);
/* 327 */       GlStateManager.pushMatrix();
/*     */       
/* 329 */       if (this.rotate)
/*     */       {
/* 331 */         GlStateManager.rotate(p_render_2_ * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
/*     */       }
/*     */       
/* 334 */       Tessellator tessellator = Tessellator.getInstance();
/* 335 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 336 */       GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/* 337 */       renderSide(tessellator, 4);
/* 338 */       GlStateManager.pushMatrix();
/* 339 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 340 */       renderSide(tessellator, 1);
/* 341 */       GlStateManager.popMatrix();
/* 342 */       GlStateManager.pushMatrix();
/* 343 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 344 */       renderSide(tessellator, 0);
/* 345 */       GlStateManager.popMatrix();
/* 346 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 347 */       renderSide(tessellator, 5);
/* 348 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 349 */       renderSide(tessellator, 2);
/* 350 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 351 */       renderSide(tessellator, 3);
/* 352 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFadeBrightness(int p_getFadeBrightness_1_) {
/* 358 */     if (timeBetween(p_getFadeBrightness_1_, this.startFadeIn, this.endFadeIn)) {
/*     */       
/* 360 */       int k = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 361 */       int l = normalizeTime(p_getFadeBrightness_1_ - this.startFadeIn);
/* 362 */       return l / k;
/*     */     } 
/* 364 */     if (timeBetween(p_getFadeBrightness_1_, this.endFadeIn, this.startFadeOut))
/*     */     {
/* 366 */       return 1.0F;
/*     */     }
/* 368 */     if (timeBetween(p_getFadeBrightness_1_, this.startFadeOut, this.endFadeOut)) {
/*     */       
/* 370 */       int i = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 371 */       int j = normalizeTime(p_getFadeBrightness_1_ - this.startFadeOut);
/* 372 */       return 1.0F - j / i;
/*     */     } 
/*     */ 
/*     */     
/* 376 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSide(Tessellator p_renderSide_1_, int p_renderSide_2_) {
/* 382 */     BufferBuilder bufferbuilder = p_renderSide_1_.getBuffer();
/* 383 */     double d0 = (p_renderSide_2_ % 3) / 3.0D;
/* 384 */     double d1 = (p_renderSide_2_ / 3) / 2.0D;
/* 385 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 386 */     bufferbuilder.pos(-100.0D, -100.0D, -100.0D).tex(d0, d1).endVertex();
/* 387 */     bufferbuilder.pos(-100.0D, -100.0D, 100.0D).tex(d0, d1 + 0.5D).endVertex();
/* 388 */     bufferbuilder.pos(100.0D, -100.0D, 100.0D).tex(d0 + 0.3333333333333333D, d1 + 0.5D).endVertex();
/* 389 */     bufferbuilder.pos(100.0D, -100.0D, -100.0D).tex(d0 + 0.3333333333333333D, d1).endVertex();
/* 390 */     p_renderSide_1_.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive(World p_isActive_1_, int p_isActive_2_) {
/* 395 */     if (timeBetween(p_isActive_2_, this.endFadeOut, this.startFadeIn))
/*     */     {
/* 397 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 401 */     if (this.days != null) {
/*     */       
/* 403 */       long i = p_isActive_1_.getWorldTime();
/*     */       
/*     */       long j;
/* 406 */       for (j = i - this.startFadeIn; j < 0L; j += (24000 * this.daysLoop));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 411 */       int k = (int)(j / 24000L);
/* 412 */       int l = k % this.daysLoop;
/*     */       
/* 414 */       if (!this.days.isInRange(l))
/*     */       {
/* 416 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 420 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timeBetween(int p_timeBetween_1_, int p_timeBetween_2_, int p_timeBetween_3_) {
/* 426 */     if (p_timeBetween_2_ <= p_timeBetween_3_)
/*     */     {
/* 428 */       return (p_timeBetween_1_ >= p_timeBetween_2_ && p_timeBetween_1_ <= p_timeBetween_3_);
/*     */     }
/*     */ 
/*     */     
/* 432 */     return !(p_timeBetween_1_ < p_timeBetween_2_ && p_timeBetween_1_ > p_timeBetween_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 438 */     return this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomSkyLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
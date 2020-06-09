/*     */ package javazoom.jl.decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Equalizer
/*     */ {
/*     */   public static final float BAND_NOT_PRESENT = -InfinityF;
/*  46 */   public static final Equalizer PASS_THRU_EQ = new Equalizer();
/*     */   
/*     */   private static final int BANDS = 32;
/*     */   
/*  50 */   private final float[] settings = new float[32];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Equalizer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Equalizer(float[] settings) {
/*  66 */     setFrom(settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public Equalizer(EQFunction eq) {
/*  71 */     setFrom(eq);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFrom(float[] eq) {
/*  76 */     reset();
/*  77 */     int max = (eq.length > 32) ? 32 : eq.length;
/*     */     
/*  79 */     for (int i = 0; i < max; i++)
/*     */     {
/*  81 */       this.settings[i] = limit(eq[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFrom(EQFunction eq) {
/*  87 */     reset();
/*  88 */     int max = 32;
/*     */     
/*  90 */     for (int i = 0; i < max; i++)
/*     */     {
/*  92 */       this.settings[i] = limit(eq.getBand(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrom(Equalizer eq) {
/* 102 */     if (eq != this)
/*     */     {
/* 104 */       setFrom(eq.settings);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 116 */     for (int i = 0; i < 32; i++)
/*     */     {
/* 118 */       this.settings[i] = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBandCount() {
/* 128 */     return this.settings.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public float setBand(int band, float neweq) {
/* 133 */     float eq = 0.0F;
/*     */     
/* 135 */     if (band >= 0 && band < 32) {
/*     */       
/* 137 */       eq = this.settings[band];
/* 138 */       this.settings[band] = limit(neweq);
/*     */     } 
/*     */     
/* 141 */     return eq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBand(int band) {
/* 151 */     float eq = 0.0F;
/*     */     
/* 153 */     if (band >= 0 && band < 32)
/*     */     {
/* 155 */       eq = this.settings[band];
/*     */     }
/*     */     
/* 158 */     return eq;
/*     */   }
/*     */ 
/*     */   
/*     */   private float limit(float eq) {
/* 163 */     if (eq == Float.NEGATIVE_INFINITY)
/* 164 */       return eq; 
/* 165 */     if (eq > 1.0F)
/* 166 */       return 1.0F; 
/* 167 */     if (eq < -1.0F) {
/* 168 */       return -1.0F;
/*     */     }
/* 170 */     return eq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float[] getBandFactors() {
/* 184 */     float[] factors = new float[32];
/* 185 */     for (int i = 0, maxCount = 32; i < maxCount; i++)
/*     */     {
/* 187 */       factors[i] = getBandFactor(this.settings[i]);
/*     */     }
/*     */     
/* 190 */     return factors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getBandFactor(float eq) {
/* 201 */     if (eq == Float.NEGATIVE_INFINITY) {
/* 202 */       return 0.0F;
/*     */     }
/* 204 */     float f = (float)Math.pow(2.0D, eq);
/* 205 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class EQFunction
/*     */   {
/*     */     public float getBand(int band) {
/* 222 */       return 0.0F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Equalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
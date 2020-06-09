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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutputChannels
/*     */ {
/*     */   public static final int BOTH_CHANNELS = 0;
/*     */   public static final int LEFT_CHANNEL = 1;
/*     */   public static final int RIGHT_CHANNEL = 2;
/*     */   public static final int DOWNMIX_CHANNELS = 3;
/*  56 */   public static final OutputChannels LEFT = new OutputChannels(1);
/*  57 */   public static final OutputChannels RIGHT = new OutputChannels(2);
/*  58 */   public static final OutputChannels BOTH = new OutputChannels(0);
/*  59 */   public static final OutputChannels DOWNMIX = new OutputChannels(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int outputChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OutputChannels fromInt(int code) {
/*  75 */     switch (code) {
/*     */       
/*     */       case 1:
/*  78 */         return LEFT;
/*     */       case 2:
/*  80 */         return RIGHT;
/*     */       case 0:
/*  82 */         return BOTH;
/*     */       case 3:
/*  84 */         return DOWNMIX;
/*     */     } 
/*  86 */     throw new IllegalArgumentException("Invalid channel code: " + code);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private OutputChannels(int channels) {
/*  92 */     this.outputChannels = channels;
/*     */     
/*  94 */     if (channels < 0 || channels > 3) {
/*  95 */       throw new IllegalArgumentException("channels");
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
/*     */   public int getChannelsOutputCode() {
/* 107 */     return this.outputChannels;
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
/*     */   public int getChannelCount() {
/* 120 */     int count = (this.outputChannels == 0) ? 2 : 1;
/* 121 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 127 */     boolean equals = false;
/*     */     
/* 129 */     if (o instanceof OutputChannels) {
/*     */       
/* 131 */       OutputChannels oc = (OutputChannels)o;
/* 132 */       equals = (oc.outputChannels == this.outputChannels);
/*     */     } 
/*     */     
/* 135 */     return equals;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return this.outputChannels;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\OutputChannels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
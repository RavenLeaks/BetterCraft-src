/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import javazoom.jl.decoder.Decoder;
/*     */ import javazoom.jl.decoder.JavaLayerException;
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
/*     */ public abstract class AudioDeviceBase
/*     */   implements AudioDevice
/*     */ {
/*     */   private boolean open = false;
/*  47 */   private Decoder decoder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void open(Decoder decoder) throws JavaLayerException {
/*  57 */     if (!isOpen()) {
/*     */       
/*  59 */       this.decoder = decoder;
/*  60 */       openImpl();
/*  61 */       setOpen(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void openImpl() throws JavaLayerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setOpen(boolean open) {
/*  78 */     this.open = open;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isOpen() {
/*  89 */     return this.open;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/*  99 */     if (isOpen()) {
/*     */       
/* 101 */       closeImpl();
/* 102 */       setOpen(false);
/* 103 */       this.decoder = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeImpl() {}
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
/*     */   public void write(short[] samples, int offs, int len) throws JavaLayerException {
/* 131 */     if (isOpen())
/*     */     {
/* 133 */       writeImpl(samples, offs, len);
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
/*     */   protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 153 */     if (isOpen())
/*     */     {
/* 155 */       flushImpl();
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
/*     */   protected void flushImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Decoder getDecoder() {
/* 175 */     return this.decoder;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\AudioDeviceBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
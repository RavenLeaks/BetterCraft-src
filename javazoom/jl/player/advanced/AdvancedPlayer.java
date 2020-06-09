/*     */ package javazoom.jl.player.advanced;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import javazoom.jl.decoder.Bitstream;
/*     */ import javazoom.jl.decoder.BitstreamException;
/*     */ import javazoom.jl.decoder.Decoder;
/*     */ import javazoom.jl.decoder.Header;
/*     */ import javazoom.jl.decoder.JavaLayerException;
/*     */ import javazoom.jl.decoder.SampleBuffer;
/*     */ import javazoom.jl.player.AudioDevice;
/*     */ import javazoom.jl.player.FactoryRegistry;
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
/*     */ public class AdvancedPlayer
/*     */ {
/*     */   private Bitstream bitstream;
/*     */   private Decoder decoder;
/*     */   private AudioDevice audio;
/*     */   private boolean closed = false;
/*     */   private boolean complete = false;
/*  49 */   private int lastPosition = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private PlaybackListener listener;
/*     */ 
/*     */ 
/*     */   
/*     */   public AdvancedPlayer(InputStream stream) throws JavaLayerException {
/*  58 */     this(stream, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvancedPlayer(InputStream stream, AudioDevice device) throws JavaLayerException {
/*  63 */     this.bitstream = new Bitstream(stream);
/*     */     
/*  65 */     if (device != null) { this.audio = device; }
/*  66 */     else { this.audio = FactoryRegistry.systemRegistry().createAudioDevice(); }
/*  67 */      this.audio.open(this.decoder = new Decoder());
/*     */   }
/*     */ 
/*     */   
/*     */   public void play() throws JavaLayerException {
/*  72 */     play(2147483647);
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
/*     */   public boolean play(int frames) throws JavaLayerException {
/*  84 */     boolean ret = true;
/*     */ 
/*     */     
/*  87 */     if (this.listener != null) this.listener.playbackStarted(createEvent(PlaybackEvent.STARTED));
/*     */     
/*  89 */     while (frames-- > 0 && ret)
/*     */     {
/*  91 */       ret = decodeFrame();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     AudioDevice out = this.audio;
/*  98 */     if (out != null) {
/*     */ 
/*     */       
/* 101 */       out.flush();
/*     */       
/* 103 */       synchronized (this) {
/*     */         
/* 105 */         this.complete = !this.closed;
/* 106 */         close();
/*     */       } 
/*     */ 
/*     */       
/* 110 */       if (this.listener != null) this.listener.playbackFinished(createEvent(out, PlaybackEvent.STOPPED));
/*     */     
/*     */     } 
/* 113 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 122 */     AudioDevice out = this.audio;
/* 123 */     if (out != null) {
/*     */       
/* 125 */       this.closed = true;
/* 126 */       this.audio = null;
/*     */ 
/*     */       
/* 129 */       out.close();
/* 130 */       this.lastPosition = out.getPosition();
/*     */       
/*     */       try {
/* 133 */         this.bitstream.close();
/*     */       }
/* 135 */       catch (BitstreamException bitstreamException) {}
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
/*     */   protected boolean decodeFrame() throws JavaLayerException {
/*     */     try {
/* 149 */       AudioDevice out = this.audio;
/* 150 */       if (out == null) return false;
/*     */       
/* 152 */       Header h = this.bitstream.readFrame();
/* 153 */       if (h == null) return false;
/*     */ 
/*     */       
/* 156 */       SampleBuffer output = (SampleBuffer)this.decoder.decodeFrame(h, this.bitstream);
/*     */       
/* 158 */       synchronized (this) {
/*     */         
/* 160 */         out = this.audio;
/* 161 */         if (out != null)
/*     */         {
/* 163 */           out.write(output.getBuffer(), 0, output.getBufferLength());
/*     */         }
/*     */       } 
/*     */       
/* 167 */       this.bitstream.closeFrame();
/*     */     }
/* 169 */     catch (RuntimeException ex) {
/*     */       
/* 171 */       throw new JavaLayerException("Exception decoding audio frame", ex);
/*     */     } 
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean skipFrame() throws JavaLayerException {
/* 182 */     Header h = this.bitstream.readFrame();
/* 183 */     if (h == null) return false; 
/* 184 */     this.bitstream.closeFrame();
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean play(int start, int end) throws JavaLayerException {
/* 196 */     boolean ret = true;
/* 197 */     int offset = start;
/* 198 */     for (; offset-- > 0 && ret; ret = skipFrame());
/* 199 */     return play(end - start);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlaybackEvent createEvent(int id) {
/* 207 */     return createEvent(this.audio, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlaybackEvent createEvent(AudioDevice dev, int id) {
/* 215 */     return new PlaybackEvent(this, id, dev.getPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayBackListener(PlaybackListener listener) {
/* 223 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaybackListener getPlayBackListener() {
/* 231 */     return this.listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 239 */     this.listener.playbackFinished(createEvent(PlaybackEvent.STOPPED));
/* 240 */     close();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\advanced\AdvancedPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
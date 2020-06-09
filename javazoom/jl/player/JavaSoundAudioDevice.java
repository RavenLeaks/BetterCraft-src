/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.FloatControl;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.SourceDataLine;
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
/*     */ public class JavaSoundAudioDevice
/*     */   extends AudioDeviceBase
/*     */ {
/*  23 */   private SourceDataLine source = null;
/*  24 */   private AudioFormat fmt = null;
/*  25 */   private byte[] byteBuf = new byte[4096];
/*     */   
/*     */   protected void setAudioFormat(AudioFormat fmt0) {
/*  28 */     this.fmt = fmt0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AudioFormat getAudioFormat() {
/*  33 */     this.fmt = new AudioFormat(44100.0F, 
/*  34 */         16, 
/*  35 */         2, 
/*  36 */         true, 
/*  37 */         false);
/*     */     
/*  39 */     return this.fmt;
/*     */   }
/*     */   
/*     */   protected DataLine.Info getSourceLineInfo() {
/*  43 */     AudioFormat fmt = getAudioFormat();
/*     */     
/*  45 */     DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt);
/*  46 */     return info;
/*     */   }
/*     */   
/*     */   public void open(AudioFormat fmt) throws JavaLayerException {
/*  50 */     if (!isOpen()) {
/*  51 */       setAudioFormat(fmt);
/*  52 */       openImpl();
/*  53 */       setOpen(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean setLineGain(float gain) {
/*  58 */     if (this.source != null) {
/*  59 */       FloatControl volControl = (FloatControl)this.source.getControl(FloatControl.Type.MASTER_GAIN);
/*  60 */       float newGain = Math.min(Math.max(gain, volControl.getMinimum()), volControl.getMaximum());
/*  61 */       volControl.setValue(newGain);
/*  62 */       return true;
/*     */     } 
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openImpl() throws JavaLayerException {}
/*     */ 
/*     */   
/*     */   public void createSource() throws JavaLayerException {
/*  73 */     Throwable t = null;
/*     */     try {
/*  75 */       Line line = AudioSystem.getLine(getSourceLineInfo());
/*  76 */       if (line instanceof SourceDataLine) {
/*  77 */         this.source = (SourceDataLine)line;
/*     */         
/*  79 */         this.source.open(this.fmt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  87 */         this.source.start();
/*     */       } 
/*  89 */     } catch (RuntimeException ex) {
/*  90 */       t = ex;
/*  91 */     } catch (LinkageError ex) {
/*  92 */       t = ex;
/*  93 */     } catch (LineUnavailableException ex) {
/*  94 */       t = ex;
/*     */     } 
/*  96 */     if (this.source == null) {
/*  97 */       throw new JavaLayerException("cannot obtain source audio line", t);
/*     */     }
/*     */   }
/*     */   
/*     */   public int millisecondsToBytes(AudioFormat fmt, int time) {
/* 102 */     return (int)((time * fmt.getSampleRate() * fmt.getChannels() * fmt.getSampleSizeInBits()) / 8000.0D);
/*     */   }
/*     */   
/*     */   protected void closeImpl() {
/* 106 */     if (this.source != null) {
/* 107 */       this.source.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
/* 113 */     if (this.source == null) {
/* 114 */       createSource();
/*     */     }
/*     */     
/* 117 */     byte[] b = toByteArray(samples, offs, len);
/* 118 */     this.source.write(b, 0, len * 2);
/*     */   }
/*     */   
/*     */   protected byte[] getByteArray(int length) {
/* 122 */     if (this.byteBuf.length < length) {
/* 123 */       this.byteBuf = new byte[length + 1024];
/*     */     }
/* 125 */     return this.byteBuf;
/*     */   }
/*     */   
/*     */   protected byte[] toByteArray(short[] samples, int offs, int len) {
/* 129 */     byte[] b = getByteArray(len * 2);
/* 130 */     int idx = 0;
/*     */     
/* 132 */     while (len-- > 0) {
/* 133 */       short s = samples[offs++];
/* 134 */       b[idx++] = (byte)s;
/* 135 */       b[idx++] = (byte)(s >>> 8);
/*     */     } 
/* 137 */     return b;
/*     */   }
/*     */   
/*     */   protected void flushImpl() {
/* 141 */     if (this.source != null) {
/* 142 */       this.source.drain();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getPosition() {
/* 147 */     int pos = 0;
/* 148 */     if (this.source != null) {
/* 149 */       pos = (int)(this.source.getMicrosecondPosition() / 1000L);
/*     */     }
/* 151 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void test() throws JavaLayerException {
/* 160 */     open(new AudioFormat(22000.0F, 16, 1, true, false));
/* 161 */     short[] data = new short[2200];
/* 162 */     write(data, 0, data.length);
/* 163 */     flush();
/* 164 */     close();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\JavaSoundAudioDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
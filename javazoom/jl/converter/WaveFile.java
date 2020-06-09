/*     */ package javazoom.jl.converter;
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
/*     */ public class WaveFile
/*     */   extends RiffFile
/*     */ {
/*     */   public static final int MAX_WAVE_CHANNELS = 2;
/*     */   private WaveFormat_Chunk wave_format;
/*     */   private RiffFile.RiffChunkHeader pcm_data;
/*     */   
/*     */   class WaveFormat_ChunkData
/*     */   {
/*  37 */     public short wFormatTag = 0;
/*  38 */     public short nChannels = 0;
/*  39 */     public int nSamplesPerSec = 0;
/*  40 */     public int nAvgBytesPerSec = 0;
/*  41 */     public short nBlockAlign = 0;
/*  42 */     public short nBitsPerSample = 0;
/*     */ 
/*     */     
/*     */     public WaveFormat_ChunkData() {
/*  46 */       this.wFormatTag = 1;
/*  47 */       Config(44100, (short)16, (short)1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void Config(int NewSamplingRate, short NewBitsPerSample, short NewNumChannels) {
/*  52 */       this.nSamplesPerSec = NewSamplingRate;
/*  53 */       this.nChannels = NewNumChannels;
/*  54 */       this.nBitsPerSample = NewBitsPerSample;
/*  55 */       this.nAvgBytesPerSec = this.nChannels * this.nSamplesPerSec * this.nBitsPerSample / 8;
/*  56 */       this.nBlockAlign = (short)(this.nChannels * this.nBitsPerSample / 8);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class WaveFormat_Chunk
/*     */   {
/*     */     public RiffFile.RiffChunkHeader header;
/*     */     
/*     */     public WaveFile.WaveFormat_ChunkData data;
/*     */     
/*     */     public WaveFormat_Chunk() {
/*  68 */       this.header = new RiffFile.RiffChunkHeader(WaveFile.this);
/*  69 */       this.data = new WaveFile.WaveFormat_ChunkData();
/*  70 */       this.header.ckID = WaveFile.FourCC("fmt ");
/*  71 */       this.header.ckSize = 16;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int VerifyValidity() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield header : Ljavazoom/jl/converter/RiffFile$RiffChunkHeader;
/*     */       //   4: getfield ckID : I
/*     */       //   7: ldc 'fmt '
/*     */       //   9: invokestatic FourCC : (Ljava/lang/String;)I
/*     */       //   12: if_icmpne -> 105
/*     */       //   15: aload_0
/*     */       //   16: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   19: getfield nChannels : S
/*     */       //   22: iconst_1
/*     */       //   23: if_icmpeq -> 37
/*     */       //   26: aload_0
/*     */       //   27: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   30: getfield nChannels : S
/*     */       //   33: iconst_2
/*     */       //   34: if_icmpne -> 105
/*     */       //   37: aload_0
/*     */       //   38: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   41: getfield nAvgBytesPerSec : I
/*     */       //   44: aload_0
/*     */       //   45: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   48: getfield nChannels : S
/*     */       //   51: aload_0
/*     */       //   52: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   55: getfield nSamplesPerSec : I
/*     */       //   58: imul
/*     */       //   59: aload_0
/*     */       //   60: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   63: getfield nBitsPerSample : S
/*     */       //   66: imul
/*     */       //   67: bipush #8
/*     */       //   69: idiv
/*     */       //   70: if_icmpne -> 105
/*     */       //   73: aload_0
/*     */       //   74: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   77: getfield nBlockAlign : S
/*     */       //   80: aload_0
/*     */       //   81: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   84: getfield nChannels : S
/*     */       //   87: aload_0
/*     */       //   88: getfield data : Ljavazoom/jl/converter/WaveFile$WaveFormat_ChunkData;
/*     */       //   91: getfield nBitsPerSample : S
/*     */       //   94: imul
/*     */       //   95: bipush #8
/*     */       //   97: idiv
/*     */       //   98: if_icmpne -> 105
/*     */       //   101: iconst_1
/*     */       //   102: goto -> 106
/*     */       //   105: iconst_0
/*     */       //   106: istore_1
/*     */       //   107: iload_1
/*     */       //   108: ifeq -> 113
/*     */       //   111: iconst_1
/*     */       //   112: ireturn
/*     */       //   113: iconst_0
/*     */       //   114: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       //   #78	-> 15
/*     */       //   #80	-> 37
/*     */       //   #81	-> 51
/*     */       //   #82	-> 59
/*     */       //   #80	-> 70
/*     */       //   #84	-> 73
/*     */       //   #85	-> 87
/*     */       //   #84	-> 98
/*     */       //   #76	-> 101
/*     */       //   #86	-> 107
/*     */       //   #87	-> 113
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	115	0	this	Ljavazoom/jl/converter/WaveFile$WaveFormat_Chunk;
/*     */       //   107	8	1	ret	Z
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
/*     */   public class WaveFileSample
/*     */   {
/*  96 */     public short[] chan = new short[2];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 101 */   private long pcm_data_offset = 0L;
/* 102 */   private int num_samples = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaveFile() {
/* 110 */     this.pcm_data = new RiffFile.RiffChunkHeader(this);
/* 111 */     this.wave_format = new WaveFormat_Chunk();
/* 112 */     this.pcm_data.ckID = FourCC("data");
/* 113 */     this.pcm_data.ckSize = 0;
/* 114 */     this.num_samples = 0;
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
/*     */   public int OpenForWrite(String Filename, int SamplingRate, short BitsPerSample, short NumChannels) {
/* 166 */     if (Filename == null || (
/* 167 */       BitsPerSample != 8 && BitsPerSample != 16) || 
/* 168 */       NumChannels < 1 || NumChannels > 2)
/*     */     {
/* 170 */       return 4;
/*     */     }
/*     */     
/* 173 */     this.wave_format.data.Config(SamplingRate, BitsPerSample, NumChannels);
/*     */     
/* 175 */     int retcode = Open(Filename, 1);
/*     */     
/* 177 */     if (retcode == 0) {
/*     */       
/* 179 */       byte[] theWave = { 87, 65, 86, 69 };
/* 180 */       retcode = Write(theWave, 4);
/*     */       
/* 182 */       if (retcode == 0) {
/*     */ 
/*     */         
/* 185 */         retcode = Write(this.wave_format.header, 8);
/* 186 */         retcode = Write(this.wave_format.data.wFormatTag, 2);
/* 187 */         retcode = Write(this.wave_format.data.nChannels, 2);
/* 188 */         retcode = Write(this.wave_format.data.nSamplesPerSec, 4);
/* 189 */         retcode = Write(this.wave_format.data.nAvgBytesPerSec, 4);
/* 190 */         retcode = Write(this.wave_format.data.nBlockAlign, 2);
/* 191 */         retcode = Write(this.wave_format.data.nBitsPerSample, 2);
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
/* 217 */         if (retcode == 0) {
/*     */           
/* 219 */           this.pcm_data_offset = CurrentFilePosition();
/* 220 */           retcode = Write(this.pcm_data, 8);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return retcode;
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
/*     */   public int WriteData(short[] data, int numData) {
/* 323 */     int extraBytes = numData * 2;
/* 324 */     this.pcm_data.ckSize += extraBytes;
/* 325 */     return Write(data, extraBytes);
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
/*     */   public int Close() {
/* 472 */     int rc = 0;
/*     */     
/* 474 */     if (this.fmode == 1)
/* 475 */       rc = Backpatch(this.pcm_data_offset, this.pcm_data, 8); 
/* 476 */     if (rc == 0)
/* 477 */       rc = super.Close(); 
/* 478 */     return rc;
/*     */   }
/*     */ 
/*     */   
/*     */   public int SamplingRate() {
/* 483 */     return this.wave_format.data.nSamplesPerSec;
/*     */   }
/*     */   public short BitsPerSample() {
/* 486 */     return this.wave_format.data.nBitsPerSample;
/*     */   }
/*     */   public short NumChannels() {
/* 489 */     return this.wave_format.data.nChannels;
/*     */   }
/*     */   public int NumSamples() {
/* 492 */     return this.num_samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int OpenForWrite(String Filename, WaveFile OtherWave) {
/* 500 */     return OpenForWrite(Filename, 
/* 501 */         OtherWave.SamplingRate(), 
/* 502 */         OtherWave.BitsPerSample(), 
/* 503 */         OtherWave.NumChannels());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long CurrentFilePosition() {
/* 511 */     return super.CurrentFilePosition();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\converter\WaveFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
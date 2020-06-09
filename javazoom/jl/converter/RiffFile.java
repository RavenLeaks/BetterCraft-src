/*     */ package javazoom.jl.converter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RiffFile
/*     */ {
/*     */   public static final int DDC_SUCCESS = 0;
/*     */   public static final int DDC_FAILURE = 1;
/*     */   public static final int DDC_OUT_OF_MEMORY = 2;
/*     */   public static final int DDC_FILE_ERROR = 3;
/*     */   public static final int DDC_INVALID_CALL = 4;
/*     */   public static final int DDC_USER_ABORT = 5;
/*     */   public static final int DDC_INVALID_FILE = 6;
/*     */   public static final int RFM_UNKNOWN = 0;
/*     */   public static final int RFM_WRITE = 1;
/*     */   public static final int RFM_READ = 2;
/*     */   private RiffChunkHeader riff_header;
/*     */   protected int fmode;
/*     */   protected RandomAccessFile file;
/*     */   
/*     */   class RiffChunkHeader
/*     */   {
/*  39 */     public int ckID = 0;
/*  40 */     public int ckSize = 0;
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
/*     */   public RiffFile() {
/*  69 */     this.file = null;
/*  70 */     this.fmode = 0;
/*  71 */     this.riff_header = new RiffChunkHeader();
/*     */     
/*  73 */     this.riff_header.ckID = FourCC("RIFF");
/*  74 */     this.riff_header.ckSize = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int CurrentFileMode() {
/*  81 */     return this.fmode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Open(String Filename, int NewMode) {
/*  88 */     int retcode = 0;
/*     */     
/*  90 */     if (this.fmode != 0)
/*     */     {
/*  92 */       retcode = Close();
/*     */     }
/*     */     
/*  95 */     if (retcode == 0)
/*     */     
/*  97 */     { switch (NewMode)
/*     */       
/*     */       { case 1:
/*     */           
/*     */           try {
/* 102 */             this.file = new RandomAccessFile(Filename, "rw");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 108 */               byte[] br = new byte[8];
/* 109 */               br[0] = (byte)(this.riff_header.ckID >>> 24 & 0xFF);
/* 110 */               br[1] = (byte)(this.riff_header.ckID >>> 16 & 0xFF);
/* 111 */               br[2] = (byte)(this.riff_header.ckID >>> 8 & 0xFF);
/* 112 */               br[3] = (byte)(this.riff_header.ckID & 0xFF);
/*     */               
/* 114 */               byte br4 = (byte)(this.riff_header.ckSize >>> 24 & 0xFF);
/* 115 */               byte br5 = (byte)(this.riff_header.ckSize >>> 16 & 0xFF);
/* 116 */               byte br6 = (byte)(this.riff_header.ckSize >>> 8 & 0xFF);
/* 117 */               byte br7 = (byte)(this.riff_header.ckSize & 0xFF);
/*     */               
/* 119 */               br[4] = br7;
/* 120 */               br[5] = br6;
/* 121 */               br[6] = br5;
/* 122 */               br[7] = br4;
/*     */               
/* 124 */               this.file.write(br, 0, 8);
/* 125 */               this.fmode = 1;
/* 126 */             } catch (IOException ioe) {
/*     */               
/* 128 */               this.file.close();
/* 129 */               this.fmode = 0;
/*     */             } 
/* 131 */           } catch (IOException ioe) {
/*     */             
/* 133 */             this.fmode = 0;
/* 134 */             retcode = 3;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           return retcode;case 2: try { this.file = new RandomAccessFile(Filename, "r"); try { byte[] br = new byte[8]; this.file.read(br, 0, 8); this.fmode = 2; this.riff_header.ckID = br[0] << 24 & 0xFF000000 | br[1] << 16 & 0xFF0000 | br[2] << 8 & 0xFF00 | br[3] & 0xFF; this.riff_header.ckSize = br[4] << 24 & 0xFF000000 | br[5] << 16 & 0xFF0000 | br[6] << 8 & 0xFF00 | br[7] & 0xFF; } catch (IOException ioe) { this.file.close(); this.fmode = 0; }  } catch (IOException ioe) { this.fmode = 0; retcode = 3; }  return retcode; }  retcode = 4; }  return retcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Write(byte[] Data, int NumBytes) {
/* 173 */     if (this.fmode != 1)
/*     */     {
/* 175 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 179 */       this.file.write(Data, 0, NumBytes);
/* 180 */       this.fmode = 1;
/*     */     }
/* 182 */     catch (IOException ioe) {
/*     */       
/* 184 */       return 3;
/*     */     } 
/* 186 */     this.riff_header.ckSize += NumBytes;
/* 187 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Write(short[] Data, int NumBytes) {
/* 197 */     byte[] theData = new byte[NumBytes];
/* 198 */     int yc = 0;
/* 199 */     for (int y = 0; y < NumBytes; y += 2) {
/*     */       
/* 201 */       theData[y] = (byte)(Data[yc] & 0xFF);
/* 202 */       theData[y + 1] = (byte)(Data[yc++] >>> 8 & 0xFF);
/*     */     } 
/* 204 */     if (this.fmode != 1)
/*     */     {
/* 206 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 210 */       this.file.write(theData, 0, NumBytes);
/* 211 */       this.fmode = 1;
/*     */     }
/* 213 */     catch (IOException ioe) {
/*     */       
/* 215 */       return 3;
/*     */     } 
/* 217 */     this.riff_header.ckSize += NumBytes;
/* 218 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Write(RiffChunkHeader Triff_header, int NumBytes) {
/* 226 */     byte[] br = new byte[8];
/* 227 */     br[0] = (byte)(Triff_header.ckID >>> 24 & 0xFF);
/* 228 */     br[1] = (byte)(Triff_header.ckID >>> 16 & 0xFF);
/* 229 */     br[2] = (byte)(Triff_header.ckID >>> 8 & 0xFF);
/* 230 */     br[3] = (byte)(Triff_header.ckID & 0xFF);
/*     */     
/* 232 */     byte br4 = (byte)(Triff_header.ckSize >>> 24 & 0xFF);
/* 233 */     byte br5 = (byte)(Triff_header.ckSize >>> 16 & 0xFF);
/* 234 */     byte br6 = (byte)(Triff_header.ckSize >>> 8 & 0xFF);
/* 235 */     byte br7 = (byte)(Triff_header.ckSize & 0xFF);
/*     */     
/* 237 */     br[4] = br7;
/* 238 */     br[5] = br6;
/* 239 */     br[6] = br5;
/* 240 */     br[7] = br4;
/*     */     
/* 242 */     if (this.fmode != 1)
/*     */     {
/* 244 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 248 */       this.file.write(br, 0, NumBytes);
/* 249 */       this.fmode = 1;
/* 250 */     } catch (IOException ioe) {
/*     */       
/* 252 */       return 3;
/*     */     } 
/* 254 */     this.riff_header.ckSize += NumBytes;
/* 255 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Write(short Data, int NumBytes) {
/* 263 */     short theData = (short)(Data >>> 8 & 0xFF | Data << 8 & 0xFF00);
/* 264 */     if (this.fmode != 1)
/*     */     {
/* 266 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 270 */       this.file.writeShort(theData);
/* 271 */       this.fmode = 1;
/* 272 */     } catch (IOException ioe) {
/*     */       
/* 274 */       return 3;
/*     */     } 
/* 276 */     this.riff_header.ckSize += NumBytes;
/* 277 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Write(int Data, int NumBytes) {
/* 284 */     short theDataL = (short)(Data >>> 16 & 0xFFFF);
/* 285 */     short theDataR = (short)(Data & 0xFFFF);
/* 286 */     short theDataLI = (short)(theDataL >>> 8 & 0xFF | theDataL << 8 & 0xFF00);
/* 287 */     short theDataRI = (short)(theDataR >>> 8 & 0xFF | theDataR << 8 & 0xFF00);
/* 288 */     int theData = theDataRI << 16 & 0xFFFF0000 | theDataLI & 0xFFFF;
/* 289 */     if (this.fmode != 1)
/*     */     {
/* 291 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 295 */       this.file.writeInt(theData);
/* 296 */       this.fmode = 1;
/* 297 */     } catch (IOException ioe) {
/*     */       
/* 299 */       return 3;
/*     */     } 
/* 301 */     this.riff_header.ckSize += NumBytes;
/* 302 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Read(byte[] Data, int NumBytes) {
/* 312 */     int retcode = 0;
/*     */     
/*     */     try {
/* 315 */       this.file.read(Data, 0, NumBytes);
/* 316 */     } catch (IOException ioe) {
/*     */       
/* 318 */       retcode = 3;
/*     */     } 
/* 320 */     return retcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Expect(String Data, int NumBytes) {
/* 328 */     byte target = 0;
/* 329 */     int cnt = 0;
/*     */     
/*     */     try {
/* 332 */       while (NumBytes-- != 0) {
/*     */         
/* 334 */         target = this.file.readByte();
/* 335 */         if (target != Data.charAt(cnt++)) return 3; 
/*     */       } 
/* 337 */     } catch (IOException ioe) {
/*     */       
/* 339 */       return 3;
/*     */     } 
/* 341 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Close() {
/* 350 */     int retcode = 0;
/*     */     
/* 352 */     switch (this.fmode) {
/*     */       
/*     */       case 1:
/*     */         
/*     */         try {
/* 357 */           this.file.seek(0L);
/*     */           
/*     */           try {
/* 360 */             byte[] br = new byte[8];
/* 361 */             br[0] = (byte)(this.riff_header.ckID >>> 24 & 0xFF);
/* 362 */             br[1] = (byte)(this.riff_header.ckID >>> 16 & 0xFF);
/* 363 */             br[2] = (byte)(this.riff_header.ckID >>> 8 & 0xFF);
/* 364 */             br[3] = (byte)(this.riff_header.ckID & 0xFF);
/*     */             
/* 366 */             br[7] = (byte)(this.riff_header.ckSize >>> 24 & 0xFF);
/* 367 */             br[6] = (byte)(this.riff_header.ckSize >>> 16 & 0xFF);
/* 368 */             br[5] = (byte)(this.riff_header.ckSize >>> 8 & 0xFF);
/* 369 */             br[4] = (byte)(this.riff_header.ckSize & 0xFF);
/* 370 */             this.file.write(br, 0, 8);
/* 371 */             this.file.close();
/* 372 */           } catch (IOException ioe) {
/*     */             
/* 374 */             retcode = 3;
/*     */           } 
/* 376 */         } catch (IOException ioe) {
/*     */           
/* 378 */           retcode = 3;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/* 385 */           this.file.close();
/* 386 */         } catch (IOException ioe) {
/*     */           
/* 388 */           retcode = 3;
/*     */         } 
/*     */         break;
/*     */     } 
/* 392 */     this.file = null;
/* 393 */     this.fmode = 0;
/* 394 */     return retcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long CurrentFilePosition() {
/*     */     long position;
/*     */     try {
/* 405 */       position = this.file.getFilePointer();
/* 406 */     } catch (IOException ioe) {
/*     */       
/* 408 */       position = -1L;
/*     */     } 
/* 410 */     return position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Backpatch(long FileOffset, RiffChunkHeader Data, int NumBytes) {
/* 418 */     if (this.file == null)
/*     */     {
/* 420 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 424 */       this.file.seek(FileOffset);
/* 425 */     } catch (IOException ioe) {
/*     */       
/* 427 */       return 3;
/*     */     } 
/* 429 */     return Write(Data, NumBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int Backpatch(long FileOffset, byte[] Data, int NumBytes) {
/* 434 */     if (this.file == null)
/*     */     {
/* 436 */       return 4;
/*     */     }
/*     */     
/*     */     try {
/* 440 */       this.file.seek(FileOffset);
/* 441 */     } catch (IOException ioe) {
/*     */       
/* 443 */       return 3;
/*     */     } 
/* 445 */     return Write(Data, NumBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int Seek(long offset) {
/*     */     int rc;
/*     */     try {
/* 457 */       this.file.seek(offset);
/* 458 */       rc = 0;
/* 459 */     } catch (IOException ioe) {
/*     */       
/* 461 */       rc = 3;
/*     */     } 
/* 463 */     return rc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String DDCRET_String(int retcode) {
/* 471 */     switch (retcode) {
/*     */       case 0:
/* 473 */         return "DDC_SUCCESS";
/* 474 */       case 1: return "DDC_FAILURE";
/* 475 */       case 2: return "DDC_OUT_OF_MEMORY";
/* 476 */       case 3: return "DDC_FILE_ERROR";
/* 477 */       case 4: return "DDC_INVALID_CALL";
/* 478 */       case 5: return "DDC_USER_ABORT";
/* 479 */       case 6: return "DDC_INVALID_FILE";
/*     */     } 
/* 481 */     return "Unknown Error";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int FourCC(String ChunkName) {
/* 489 */     byte[] p = { 32, 32, 32, 32 };
/* 490 */     ChunkName.getBytes(0, 4, p, 0);
/* 491 */     int ret = p[0] << 24 & 0xFF000000 | p[1] << 16 & 0xFF0000 | p[2] << 8 & 0xFF00 | p[3] & 0xFF;
/* 492 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\converter\RiffFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
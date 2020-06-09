/*     */ package javazoom.jl.decoder;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bitstream
/*     */   implements BitstreamErrors
/*     */ {
/*  59 */   static byte INITIAL_SYNC = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   static byte STRICT_SYNC = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BUFFER_INT_SIZE = 433;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private final int[] framebuffer = new int[433];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int framesize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private byte[] frame_bytes = new byte[1732];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int wordpointer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int bitindex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int syncword;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private int header_pos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean single_ch_mode;
/*     */ 
/*     */ 
/*     */   
/* 116 */   private final int[] bitmask = new int[] { 
/* 117 */       0, 1, 3, 7, 15, 
/* 118 */       31, 63, 127, 255, 
/* 119 */       511, 1023, 2047, 4095, 
/* 120 */       8191, 16383, 32767, 65535, 
/* 121 */       131071 };
/*     */   
/*     */   private final PushbackInputStream source;
/*     */   
/* 125 */   private final Header header = new Header();
/*     */   
/* 127 */   private final byte[] syncbuf = new byte[4];
/*     */   
/* 129 */   private Crc16[] crc = new Crc16[1];
/*     */   
/* 131 */   private byte[] rawid3v2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean firstframe = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitstream(InputStream in) {
/* 144 */     if (in == null) throw new NullPointerException("in"); 
/* 145 */     in = new BufferedInputStream(in);
/* 146 */     loadID3v2(in);
/* 147 */     this.firstframe = true;
/*     */     
/* 149 */     this.source = new PushbackInputStream(in, 1732);
/*     */     
/* 151 */     closeFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int header_pos() {
/* 162 */     return this.header_pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadID3v2(InputStream in) {
/* 172 */     int size = -1;
/*     */ 
/*     */ 
/*     */     
/* 176 */     try { in.mark(10);
/* 177 */       size = readID3v2Header(in);
/* 178 */       this.header_pos = size; }
/*     */     
/* 180 */     catch (IOException iOException)
/*     */     
/*     */     { 
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 187 */         in.reset();
/*     */       }
/* 189 */       catch (IOException iOException1) {} } finally { try { in.reset(); } catch (IOException iOException) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 195 */       if (size > 0)
/*     */       {
/* 197 */         this.rawid3v2 = new byte[size];
/* 198 */         in.read(this.rawid3v2, 0, this.rawid3v2.length);
/*     */       }
/*     */     
/* 201 */     } catch (IOException iOException) {}
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
/*     */   private int readID3v2Header(InputStream in) throws IOException {
/* 214 */     byte[] id3header = new byte[4];
/* 215 */     int size = -10;
/* 216 */     in.read(id3header, 0, 3);
/*     */     
/* 218 */     if (id3header[0] == 73 && id3header[1] == 68 && id3header[2] == 51) {
/*     */       
/* 220 */       in.read(id3header, 0, 3);
/* 221 */       int majorVersion = id3header[0];
/* 222 */       int revision = id3header[1];
/* 223 */       in.read(id3header, 0, 4);
/* 224 */       size = (id3header[0] << 21) + (id3header[1] << 14) + (id3header[2] << 7) + id3header[3];
/*     */     } 
/* 226 */     return size + 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getRawID3v2() {
/* 235 */     if (this.rawid3v2 == null) return null;
/*     */ 
/*     */     
/* 238 */     ByteArrayInputStream bain = new ByteArrayInputStream(this.rawid3v2);
/* 239 */     return bain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws BitstreamException {
/*     */     try {
/* 251 */       this.source.close();
/*     */     }
/* 253 */     catch (IOException ex) {
/*     */       
/* 255 */       throw newBitstreamException(258, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header readFrame() throws BitstreamException {
/* 266 */     Header result = null;
/*     */     
/*     */     try {
/* 269 */       result = readNextFrame();
/*     */       
/* 271 */       if (this.firstframe)
/*     */       {
/* 273 */         result.parseVBR(this.frame_bytes);
/* 274 */         this.firstframe = false;
/*     */       }
/*     */     
/* 277 */     } catch (BitstreamException ex) {
/*     */       
/* 279 */       if (ex.getErrorCode() == 261) {
/*     */         
/*     */         try
/*     */         {
/*     */ 
/*     */           
/* 285 */           closeFrame();
/* 286 */           result = readNextFrame();
/*     */         }
/* 288 */         catch (BitstreamException e)
/*     */         {
/* 290 */           if (e.getErrorCode() != 260)
/*     */           {
/*     */             
/* 293 */             throw newBitstreamException(e.getErrorCode(), e);
/*     */           }
/*     */         }
/*     */       
/* 297 */       } else if (ex.getErrorCode() != 260) {
/*     */ 
/*     */         
/* 300 */         throw newBitstreamException(ex.getErrorCode(), ex);
/*     */       } 
/*     */     } 
/* 303 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Header readNextFrame() throws BitstreamException {
/* 313 */     if (this.framesize == -1)
/*     */     {
/* 315 */       nextFrame();
/*     */     }
/* 317 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextFrame() throws BitstreamException {
/* 328 */     this.header.read_header(this, this.crc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unreadFrame() throws BitstreamException {
/* 338 */     if (this.wordpointer == -1 && this.bitindex == -1 && this.framesize > 0) {
/*     */       
/*     */       try {
/*     */         
/* 342 */         this.source.unread(this.frame_bytes, 0, this.framesize);
/*     */       }
/* 344 */       catch (IOException ex) {
/*     */         
/* 346 */         throw newBitstreamException(258);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeFrame() {
/* 356 */     this.framesize = -1;
/* 357 */     this.wordpointer = -1;
/* 358 */     this.bitindex = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSyncCurrentPosition(int syncmode) throws BitstreamException {
/* 367 */     int read = readBytes(this.syncbuf, 0, 4);
/* 368 */     int headerstring = this.syncbuf[0] << 24 & 0xFF000000 | this.syncbuf[1] << 16 & 0xFF0000 | this.syncbuf[2] << 8 & 0xFF00 | this.syncbuf[3] << 0 & 0xFF;
/*     */ 
/*     */     
/*     */     try {
/* 372 */       this.source.unread(this.syncbuf, 0, read);
/*     */     }
/* 374 */     catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 378 */     boolean sync = false;
/* 379 */     switch (read) {
/*     */       
/*     */       case 0:
/* 382 */         sync = true;
/*     */         break;
/*     */       case 4:
/* 385 */         sync = isSyncMark(headerstring, syncmode, this.syncword);
/*     */         break;
/*     */     } 
/*     */     
/* 389 */     return sync;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readBits(int n) {
/* 398 */     return get_bits(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int readCheckedBits(int n) {
/* 404 */     return get_bits(n);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BitstreamException newBitstreamException(int errorcode) {
/* 409 */     return new BitstreamException(errorcode, null);
/*     */   }
/*     */   
/*     */   protected BitstreamException newBitstreamException(int errorcode, Throwable throwable) {
/* 413 */     return new BitstreamException(errorcode, throwable);
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
/*     */   int syncHeader(byte syncmode) throws BitstreamException {
/*     */     boolean sync;
/* 428 */     int bytesRead = readBytes(this.syncbuf, 0, 3);
/*     */     
/* 430 */     if (bytesRead != 3) throw newBitstreamException(260, null);
/*     */     
/* 432 */     int headerstring = this.syncbuf[0] << 16 & 0xFF0000 | this.syncbuf[1] << 8 & 0xFF00 | this.syncbuf[2] << 0 & 0xFF;
/*     */ 
/*     */     
/*     */     do {
/* 436 */       headerstring <<= 8;
/*     */       
/* 438 */       if (readBytes(this.syncbuf, 3, 1) != 1) {
/* 439 */         throw newBitstreamException(260, null);
/*     */       }
/* 441 */       headerstring |= this.syncbuf[3] & 0xFF;
/*     */       
/* 443 */       sync = isSyncMark(headerstring, syncmode, this.syncword);
/*     */     }
/* 445 */     while (!sync);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 450 */     return headerstring;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSyncMark(int headerstring, int syncmode, int word) {
/* 455 */     boolean sync = false;
/*     */     
/* 457 */     if (syncmode == INITIAL_SYNC) {
/*     */ 
/*     */       
/* 460 */       sync = ((headerstring & 0xFFE00000) == -2097152);
/*     */     }
/*     */     else {
/*     */       
/* 464 */       sync = ((headerstring & 0xFFF80C00) == word && (
/* 465 */         ((headerstring & 0xC0) == 192)) == this.single_ch_mode);
/*     */     } 
/*     */ 
/*     */     
/* 469 */     if (sync) {
/* 470 */       sync = ((headerstring >>> 10 & 0x3) != 3);
/*     */     }
/* 472 */     if (sync) {
/* 473 */       sync = ((headerstring >>> 17 & 0x3) != 0);
/*     */     }
/* 475 */     if (sync) {
/* 476 */       sync = ((headerstring >>> 19 & 0x3) != 1);
/*     */     }
/* 478 */     return sync;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int read_frame_data(int bytesize) throws BitstreamException {
/* 487 */     int numread = 0;
/* 488 */     numread = readFully(this.frame_bytes, 0, bytesize);
/* 489 */     this.framesize = bytesize;
/* 490 */     this.wordpointer = -1;
/* 491 */     this.bitindex = -1;
/* 492 */     return numread;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parse_frame() throws BitstreamException {
/* 501 */     int b = 0;
/* 502 */     byte[] byteread = this.frame_bytes;
/* 503 */     int bytesize = this.framesize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     for (int k = 0; k < bytesize; k += 4) {
/*     */       
/* 517 */       int convert = 0;
/* 518 */       byte b0 = 0;
/* 519 */       byte b1 = 0;
/* 520 */       byte b2 = 0;
/* 521 */       byte b3 = 0;
/* 522 */       b0 = byteread[k];
/* 523 */       if (k + 1 < bytesize) b1 = byteread[k + 1]; 
/* 524 */       if (k + 2 < bytesize) b2 = byteread[k + 2]; 
/* 525 */       if (k + 3 < bytesize) b3 = byteread[k + 3]; 
/* 526 */       this.framebuffer[b++] = b0 << 24 & 0xFF000000 | b1 << 16 & 0xFF0000 | b2 << 8 & 0xFF00 | b3 & 0xFF;
/*     */     } 
/* 528 */     this.wordpointer = 0;
/* 529 */     this.bitindex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get_bits(int number_of_bits) {
/* 539 */     int returnvalue = 0;
/* 540 */     int sum = this.bitindex + number_of_bits;
/*     */ 
/*     */ 
/*     */     
/* 544 */     if (this.wordpointer < 0) this.wordpointer = 0;
/*     */ 
/*     */     
/* 547 */     if (sum <= 32) {
/*     */ 
/*     */       
/* 550 */       returnvalue = this.framebuffer[this.wordpointer] >>> 32 - sum & this.bitmask[number_of_bits];
/*     */       
/* 552 */       if ((this.bitindex += number_of_bits) == 32) {
/*     */         
/* 554 */         this.bitindex = 0;
/* 555 */         this.wordpointer++;
/*     */       } 
/* 557 */       return returnvalue;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 564 */     int Right = this.framebuffer[this.wordpointer] & 0xFFFF;
/* 565 */     this.wordpointer++;
/* 566 */     int Left = this.framebuffer[this.wordpointer] & 0xFFFF0000;
/* 567 */     returnvalue = Right << 16 & 0xFFFF0000 | Left >>> 16 & 0xFFFF;
/*     */     
/* 569 */     returnvalue >>>= 48 - sum;
/* 570 */     returnvalue &= this.bitmask[number_of_bits];
/* 571 */     this.bitindex = sum - 32;
/* 572 */     return returnvalue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set_syncword(int syncword0) {
/* 581 */     this.syncword = syncword0 & 0xFFFFFF3F;
/* 582 */     this.single_ch_mode = ((syncword0 & 0xC0) == 192);
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
/*     */   private int readFully(byte[] b, int offs, int len) throws BitstreamException {
/* 600 */     int nRead = 0;
/*     */     
/*     */     try {
/* 603 */       while (len > 0)
/*     */       {
/* 605 */         int bytesread = this.source.read(b, offs, len);
/* 606 */         if (bytesread == -1) {
/*     */           
/* 608 */           while (len-- > 0)
/*     */           {
/* 610 */             b[offs++] = 0;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/* 615 */         nRead += bytesread;
/* 616 */         offs += bytesread;
/* 617 */         len -= bytesread;
/*     */       }
/*     */     
/* 620 */     } catch (IOException ex) {
/*     */       
/* 622 */       throw newBitstreamException(258, ex);
/*     */     } 
/* 624 */     return nRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readBytes(byte[] b, int offs, int len) throws BitstreamException {
/* 634 */     int totalBytesRead = 0;
/*     */     
/*     */     try {
/* 637 */       while (len > 0)
/*     */       {
/* 639 */         int bytesread = this.source.read(b, offs, len);
/* 640 */         if (bytesread == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 644 */         totalBytesRead += bytesread;
/* 645 */         offs += bytesread;
/* 646 */         len -= bytesread;
/*     */       }
/*     */     
/* 649 */     } catch (IOException ex) {
/*     */       
/* 651 */       throw newBitstreamException(258, ex);
/*     */     } 
/* 653 */     return totalBytesRead;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Bitstream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
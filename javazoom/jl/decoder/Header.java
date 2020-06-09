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
/*     */ public final class Header
/*     */ {
/*  40 */   public static final int[][] frequencies = new int[][] { { 22050, 24000, 16000, 1
/*  41 */       }, { 44100, 48000, 32000, 1
/*  42 */       }, { 11025, 12000, 8000, 1 } };
/*     */   
/*     */   public static final int MPEG2_LSF = 0;
/*     */   
/*     */   public static final int MPEG25_LSF = 2;
/*     */   
/*     */   public static final int MPEG1 = 1;
/*     */   
/*     */   public static final int STEREO = 0;
/*     */   
/*     */   public static final int JOINT_STEREO = 1;
/*     */   
/*     */   public static final int DUAL_CHANNEL = 2;
/*     */   public static final int SINGLE_CHANNEL = 3;
/*     */   public static final int FOURTYFOUR_POINT_ONE = 0;
/*     */   public static final int FOURTYEIGHT = 1;
/*     */   public static final int THIRTYTWO = 2;
/*     */   private int h_layer;
/*     */   private int h_protection_bit;
/*     */   private int h_bitrate_index;
/*     */   private int h_padding_bit;
/*     */   private int h_mode_extension;
/*     */   private int h_version;
/*     */   private int h_mode;
/*     */   private int h_sample_frequency;
/*     */   private int h_number_of_subbands;
/*     */   private int h_intensity_stereo_bound;
/*     */   private boolean h_copyright;
/*     */   private boolean h_original;
/*  71 */   private double[] h_vbr_time_per_frame = new double[] { -1.0D, 384.0D, 1152.0D, 1152.0D };
/*     */   
/*     */   private boolean h_vbr;
/*     */   private int h_vbr_frames;
/*     */   private int h_vbr_scale;
/*     */   private int h_vbr_bytes;
/*     */   private byte[] h_vbr_toc;
/*  78 */   private byte syncmode = Bitstream.INITIAL_SYNC;
/*     */   
/*     */   private Crc16 crc;
/*     */   
/*     */   public short checksum;
/*     */   public int framesize;
/*     */   public int nSlots;
/*  85 */   private int _headerstring = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     StringBuffer buffer = new StringBuffer(200);
/*  93 */     buffer.append("Layer ");
/*  94 */     buffer.append(layer_string());
/*  95 */     buffer.append(" frame ");
/*  96 */     buffer.append(mode_string());
/*  97 */     buffer.append(' ');
/*  98 */     buffer.append(version_string());
/*  99 */     if (!checksums())
/* 100 */       buffer.append(" no"); 
/* 101 */     buffer.append(" checksums");
/* 102 */     buffer.append(' ');
/* 103 */     buffer.append(sample_frequency_string());
/* 104 */     buffer.append(',');
/* 105 */     buffer.append(' ');
/* 106 */     buffer.append(bitrate_string());
/*     */     
/* 108 */     String s = buffer.toString();
/* 109 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void read_header(Bitstream stream, Crc16[] crcp) throws BitstreamException {
/* 119 */     boolean sync = false;
/*     */     
/*     */     while (true) {
/* 122 */       int headerstring = stream.syncHeader(this.syncmode);
/* 123 */       this._headerstring = headerstring;
/* 124 */       if (this.syncmode == Bitstream.INITIAL_SYNC) {
/*     */         
/* 126 */         this.h_version = headerstring >>> 19 & 0x1;
/* 127 */         if ((headerstring >>> 20 & 0x1) == 0)
/* 128 */           if (this.h_version == 0) {
/* 129 */             this.h_version = 2;
/*     */           } else {
/* 131 */             throw stream.newBitstreamException(256);
/* 132 */           }   if ((this.h_sample_frequency = headerstring >>> 10 & 0x3) == 3)
/*     */         {
/* 134 */           throw stream.newBitstreamException(256);
/*     */         }
/*     */       } 
/* 137 */       this.h_layer = 4 - (headerstring >>> 17) & 0x3;
/* 138 */       this.h_protection_bit = headerstring >>> 16 & 0x1;
/* 139 */       this.h_bitrate_index = headerstring >>> 12 & 0xF;
/* 140 */       this.h_padding_bit = headerstring >>> 9 & 0x1;
/* 141 */       this.h_mode = headerstring >>> 6 & 0x3;
/* 142 */       this.h_mode_extension = headerstring >>> 4 & 0x3;
/* 143 */       if (this.h_mode == 1) {
/* 144 */         this.h_intensity_stereo_bound = (this.h_mode_extension << 2) + 4;
/*     */       } else {
/* 146 */         this.h_intensity_stereo_bound = 0;
/* 147 */       }  if ((headerstring >>> 3 & 0x1) == 1)
/* 148 */         this.h_copyright = true; 
/* 149 */       if ((headerstring >>> 2 & 0x1) == 1) {
/* 150 */         this.h_original = true;
/*     */       }
/* 152 */       if (this.h_layer == 1) {
/* 153 */         this.h_number_of_subbands = 32;
/*     */       } else {
/*     */         
/* 156 */         int channel_bitrate = this.h_bitrate_index;
/*     */         
/* 158 */         if (this.h_mode != 3)
/* 159 */           if (channel_bitrate == 4) {
/* 160 */             channel_bitrate = 1;
/*     */           } else {
/* 162 */             channel_bitrate -= 4;
/* 163 */           }   if (channel_bitrate == 1 || channel_bitrate == 2) {
/* 164 */           if (this.h_sample_frequency == 2)
/* 165 */           { this.h_number_of_subbands = 12; }
/*     */           else
/* 167 */           { this.h_number_of_subbands = 8; } 
/* 168 */         } else if (this.h_sample_frequency == 1 || (channel_bitrate >= 3 && channel_bitrate <= 5)) {
/* 169 */           this.h_number_of_subbands = 27;
/*     */         } else {
/* 171 */           this.h_number_of_subbands = 30;
/*     */         } 
/* 173 */       }  if (this.h_intensity_stereo_bound > this.h_number_of_subbands) {
/* 174 */         this.h_intensity_stereo_bound = this.h_number_of_subbands;
/*     */       }
/* 176 */       calculate_framesize();
/*     */       
/* 178 */       int framesizeloaded = stream.read_frame_data(this.framesize);
/* 179 */       if (this.framesize >= 0 && framesizeloaded != this.framesize)
/*     */       {
/*     */ 
/*     */         
/* 183 */         throw stream.newBitstreamException(261);
/*     */       }
/* 185 */       if (stream.isSyncCurrentPosition(this.syncmode)) {
/*     */         
/* 187 */         if (this.syncmode == Bitstream.INITIAL_SYNC) {
/*     */           
/* 189 */           this.syncmode = Bitstream.STRICT_SYNC;
/* 190 */           stream.set_syncword(headerstring & 0xFFF80CC0);
/*     */         } 
/* 192 */         sync = true;
/*     */       }
/*     */       else {
/*     */         
/* 196 */         stream.unreadFrame();
/*     */       } 
/*     */       
/* 199 */       if (sync) {
/* 200 */         stream.parse_frame();
/* 201 */         if (this.h_protection_bit == 0) {
/*     */ 
/*     */           
/* 204 */           this.checksum = (short)stream.get_bits(16);
/* 205 */           if (this.crc == null)
/* 206 */             this.crc = new Crc16(); 
/* 207 */           this.crc.add_bits(headerstring, 16);
/* 208 */           crcp[0] = this.crc;
/*     */         } else {
/*     */           
/* 211 */           crcp[0] = null;
/*     */         } 
/*     */         return;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void parseVBR(byte[] firstframe) throws BitstreamException {
/* 244 */     String xing = "Xing";
/* 245 */     byte[] tmp = new byte[4];
/* 246 */     int offset = 0;
/*     */     
/* 248 */     if (this.h_version == 1)
/*     */     
/* 250 */     { if (this.h_mode == 3) { offset = 17; }
/* 251 */       else { offset = 32; }
/*     */       
/*     */        }
/*     */     
/* 255 */     else if (this.h_mode == 3) { offset = 9; }
/* 256 */     else { offset = 17; }
/*     */ 
/*     */     
/*     */     try {
/* 260 */       System.arraycopy(firstframe, offset, tmp, 0, 4);
/*     */       
/* 262 */       if (xing.equals(new String(tmp)))
/*     */       {
/*     */         
/* 265 */         this.h_vbr = true;
/* 266 */         this.h_vbr_frames = -1;
/* 267 */         this.h_vbr_bytes = -1;
/* 268 */         this.h_vbr_scale = -1;
/* 269 */         this.h_vbr_toc = new byte[100];
/*     */         
/* 271 */         int length = 4;
/*     */         
/* 273 */         byte[] flags = new byte[4];
/* 274 */         System.arraycopy(firstframe, offset + length, flags, 0, flags.length);
/* 275 */         length += flags.length;
/*     */         
/* 277 */         if ((flags[3] & 0x1) != 0) {
/*     */           
/* 279 */           System.arraycopy(firstframe, offset + length, tmp, 0, tmp.length);
/* 280 */           this.h_vbr_frames = tmp[0] << 24 & 0xFF000000 | tmp[1] << 16 & 0xFF0000 | tmp[2] << 8 & 0xFF00 | tmp[3] & 0xFF;
/* 281 */           length += 4;
/*     */         } 
/*     */         
/* 284 */         if ((flags[3] & 0x2) != 0) {
/*     */           
/* 286 */           System.arraycopy(firstframe, offset + length, tmp, 0, tmp.length);
/* 287 */           this.h_vbr_bytes = tmp[0] << 24 & 0xFF000000 | tmp[1] << 16 & 0xFF0000 | tmp[2] << 8 & 0xFF00 | tmp[3] & 0xFF;
/* 288 */           length += 4;
/*     */         } 
/*     */         
/* 291 */         if ((flags[3] & 0x4) != 0) {
/*     */           
/* 293 */           System.arraycopy(firstframe, offset + length, this.h_vbr_toc, 0, this.h_vbr_toc.length);
/* 294 */           length += this.h_vbr_toc.length;
/*     */         } 
/*     */         
/* 297 */         if ((flags[3] & 0x8) != 0)
/*     */         {
/* 299 */           System.arraycopy(firstframe, offset + length, tmp, 0, tmp.length);
/* 300 */           this.h_vbr_scale = tmp[0] << 24 & 0xFF000000 | tmp[1] << 16 & 0xFF0000 | tmp[2] << 8 & 0xFF00 | tmp[3] & 0xFF;
/* 301 */           length += 4;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 306 */     } catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 308 */       throw new BitstreamException("XingVBRHeader Corrupted", e);
/*     */     } 
/*     */ 
/*     */     
/* 312 */     String vbri = "VBRI";
/* 313 */     offset = 32;
/*     */     
/*     */     try {
/* 316 */       System.arraycopy(firstframe, offset, tmp, 0, 4);
/*     */       
/* 318 */       if (vbri.equals(new String(tmp)))
/*     */       {
/*     */         
/* 321 */         this.h_vbr = true;
/* 322 */         this.h_vbr_frames = -1;
/* 323 */         this.h_vbr_bytes = -1;
/* 324 */         this.h_vbr_scale = -1;
/* 325 */         this.h_vbr_toc = new byte[100];
/*     */         
/* 327 */         int length = 10;
/* 328 */         System.arraycopy(firstframe, offset + length, tmp, 0, tmp.length);
/* 329 */         this.h_vbr_bytes = tmp[0] << 24 & 0xFF000000 | tmp[1] << 16 & 0xFF0000 | tmp[2] << 8 & 0xFF00 | tmp[3] & 0xFF;
/* 330 */         length += 4;
/*     */         
/* 332 */         System.arraycopy(firstframe, offset + length, tmp, 0, tmp.length);
/* 333 */         this.h_vbr_frames = tmp[0] << 24 & 0xFF000000 | tmp[1] << 16 & 0xFF0000 | tmp[2] << 8 & 0xFF00 | tmp[3] & 0xFF;
/* 334 */         length += 4;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 340 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 342 */       throw new BitstreamException("VBRIVBRHeader Corrupted", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int version() {
/* 350 */     return this.h_version;
/*     */   }
/*     */ 
/*     */   
/*     */   public int layer() {
/* 355 */     return this.h_layer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int bitrate_index() {
/* 360 */     return this.h_bitrate_index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sample_frequency() {
/* 365 */     return this.h_sample_frequency;
/*     */   }
/*     */ 
/*     */   
/*     */   public int frequency() {
/* 370 */     return frequencies[this.h_version][this.h_sample_frequency];
/*     */   }
/*     */ 
/*     */   
/*     */   public int mode() {
/* 375 */     return this.h_mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checksums() {
/* 382 */     if (this.h_protection_bit == 0) return true; 
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyright() {
/* 389 */     return this.h_copyright;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean original() {
/* 394 */     return this.h_original;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean vbr() {
/* 400 */     return this.h_vbr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int vbr_scale() {
/* 406 */     return this.h_vbr_scale;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] vbr_toc() {
/* 412 */     return this.h_vbr_toc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checksum_ok() {
/* 418 */     return (this.checksum == this.crc.checksum());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean padding() {
/* 426 */     if (this.h_padding_bit == 0) return false; 
/* 427 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int slots() {
/* 433 */     return this.nSlots;
/*     */   }
/*     */ 
/*     */   
/*     */   public int mode_extension() {
/* 438 */     return this.h_mode_extension;
/*     */   }
/*     */   
/* 441 */   public static final int[][][] bitrates = new int[][][] { { { 
/* 442 */           0, 32000, 48000, 56000, 64000, 80000, 96000, 
/* 443 */           112000, 128000, 144000, 160000, 176000, 192000, 224000, 256000 }, { 
/* 444 */           0, 8000, 16000, 24000, 32000, 40000, 48000, 
/* 445 */           56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000 }, { 
/* 446 */           0, 8000, 16000, 24000, 32000, 40000, 48000, 
/* 447 */           56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000
/*     */         } }, { { 
/* 449 */           0, 32000, 64000, 96000, 128000, 160000, 192000, 
/* 450 */           224000, 256000, 288000, 320000, 352000, 384000, 416000, 448000 }, { 
/* 451 */           0, 32000, 48000, 56000, 64000, 80000, 96000, 
/* 452 */           112000, 128000, 160000, 192000, 224000, 256000, 320000, 384000 }, { 
/* 453 */           0, 32000, 40000, 48000, 56000, 64000, 80000, 
/* 454 */           96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000
/*     */         } }, { { 
/* 456 */           0, 32000, 48000, 56000, 64000, 80000, 96000, 
/* 457 */           112000, 128000, 144000, 160000, 176000, 192000, 224000, 256000 }, { 
/* 458 */           0, 8000, 16000, 24000, 32000, 40000, 48000, 
/* 459 */           56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000 }, { 
/* 460 */           0, 8000, 16000, 24000, 32000, 40000, 48000, 
/* 461 */           56000, 64000, 80000, 96000, 112000, 128000, 144000, 160000 } } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calculate_framesize() {
/* 473 */     if (this.h_layer == 1) {
/*     */       
/* 475 */       this.framesize = 12 * bitrates[this.h_version][0][this.h_bitrate_index] / 
/* 476 */         frequencies[this.h_version][this.h_sample_frequency];
/* 477 */       if (this.h_padding_bit != 0) this.framesize++; 
/* 478 */       this.framesize <<= 2;
/* 479 */       this.nSlots = 0;
/*     */     }
/*     */     else {
/*     */       
/* 483 */       this.framesize = 144 * bitrates[this.h_version][this.h_layer - 1][this.h_bitrate_index] / 
/* 484 */         frequencies[this.h_version][this.h_sample_frequency];
/* 485 */       if (this.h_version == 0 || this.h_version == 2) this.framesize >>= 1; 
/* 486 */       if (this.h_padding_bit != 0) this.framesize++;
/*     */       
/* 488 */       if (this.h_layer == 3) {
/*     */         
/* 490 */         if (this.h_version == 1)
/*     */         {
/* 492 */           this.nSlots = this.framesize - ((this.h_mode == 3) ? 17 : 32) - (
/* 493 */             (this.h_protection_bit != 0) ? 0 : 2) - 
/* 494 */             4;
/*     */         }
/*     */         else
/*     */         {
/* 498 */           this.nSlots = this.framesize - ((this.h_mode == 3) ? 9 : 17) - (
/* 499 */             (this.h_protection_bit != 0) ? 0 : 2) - 
/* 500 */             4;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 505 */         this.nSlots = 0;
/*     */       } 
/*     */     } 
/* 508 */     this.framesize -= 4;
/* 509 */     return this.framesize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int max_number_of_frames(int streamsize) {
/* 519 */     if (this.h_vbr) return this.h_vbr_frames;
/*     */ 
/*     */     
/* 522 */     if (this.framesize + 4 - this.h_padding_bit == 0) return 0; 
/* 523 */     return streamsize / (this.framesize + 4 - this.h_padding_bit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int min_number_of_frames(int streamsize) {
/* 534 */     if (this.h_vbr) return this.h_vbr_frames;
/*     */ 
/*     */     
/* 537 */     if (this.framesize + 5 - this.h_padding_bit == 0) return 0; 
/* 538 */     return streamsize / (this.framesize + 5 - this.h_padding_bit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float ms_per_frame() {
/* 549 */     if (this.h_vbr) {
/*     */       
/* 551 */       double tpf = this.h_vbr_time_per_frame[layer()] / frequency();
/* 552 */       if (this.h_version == 0 || this.h_version == 2) tpf /= 2.0D; 
/* 553 */       return (float)(tpf * 1000.0D);
/*     */     } 
/*     */ 
/*     */     
/* 557 */     float[][] ms_per_frame_array = { { 8.707483F, 8.0F, 12.0F
/* 558 */         }, { 26.12245F, 24.0F, 36.0F
/* 559 */         }, { 26.12245F, 24.0F, 36.0F } };
/* 560 */     return ms_per_frame_array[this.h_layer - 1][this.h_sample_frequency];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float total_ms(int streamsize) {
/* 571 */     return max_number_of_frames(streamsize) * ms_per_frame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSyncHeader() {
/* 579 */     return this._headerstring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String layer_string() {
/* 588 */     switch (this.h_layer) {
/*     */       
/*     */       case 1:
/* 591 */         return "I";
/*     */       case 2:
/* 593 */         return "II";
/*     */       case 3:
/* 595 */         return "III";
/*     */     } 
/* 597 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 601 */   public static final String[][][] bitrate_str = new String[][][] { { { 
/* 602 */           "free format", "32 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", 
/* 603 */           "80 kbit/s", "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", 
/* 604 */           "160 kbit/s", "176 kbit/s", "192 kbit/s", "224 kbit/s", "256 kbit/s", 
/* 605 */           "forbidden" }, { 
/* 606 */           "free format", "8 kbit/s", "16 kbit/s", "24 kbit/s", "32 kbit/s", 
/* 607 */           "40 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", "80 kbit/s", 
/* 608 */           "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", "160 kbit/s", 
/* 609 */           "forbidden" }, { 
/* 610 */           "free format", "8 kbit/s", "16 kbit/s", "24 kbit/s", "32 kbit/s", 
/* 611 */           "40 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", "80 kbit/s", 
/* 612 */           "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", "160 kbit/s", 
/* 613 */           "forbidden"
/*     */         } }, { { 
/* 615 */           "free format", "32 kbit/s", "64 kbit/s", "96 kbit/s", "128 kbit/s", 
/* 616 */           "160 kbit/s", "192 kbit/s", "224 kbit/s", "256 kbit/s", "288 kbit/s", 
/* 617 */           "320 kbit/s", "352 kbit/s", "384 kbit/s", "416 kbit/s", "448 kbit/s", 
/* 618 */           "forbidden" }, { 
/* 619 */           "free format", "32 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", 
/* 620 */           "80 kbit/s", "96 kbit/s", "112 kbit/s", "128 kbit/s", "160 kbit/s", 
/* 621 */           "192 kbit/s", "224 kbit/s", "256 kbit/s", "320 kbit/s", "384 kbit/s", 
/* 622 */           "forbidden" }, { 
/* 623 */           "free format", "32 kbit/s", "40 kbit/s", "48 kbit/s", "56 kbit/s", 
/* 624 */           "64 kbit/s", "80 kbit/s", "96 kbit/s", "112 kbit/s", "128 kbit/s", 
/* 625 */           "160 kbit/s", "192 kbit/s", "224 kbit/s", "256 kbit/s", "320 kbit/s", 
/* 626 */           "forbidden"
/*     */         } }, { { 
/* 628 */           "free format", "32 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", 
/* 629 */           "80 kbit/s", "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", 
/* 630 */           "160 kbit/s", "176 kbit/s", "192 kbit/s", "224 kbit/s", "256 kbit/s", 
/* 631 */           "forbidden" }, { 
/* 632 */           "free format", "8 kbit/s", "16 kbit/s", "24 kbit/s", "32 kbit/s", 
/* 633 */           "40 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", "80 kbit/s", 
/* 634 */           "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", "160 kbit/s", 
/* 635 */           "forbidden" }, { 
/* 636 */           "free format", "8 kbit/s", "16 kbit/s", "24 kbit/s", "32 kbit/s", 
/* 637 */           "40 kbit/s", "48 kbit/s", "56 kbit/s", "64 kbit/s", "80 kbit/s", 
/* 638 */           "96 kbit/s", "112 kbit/s", "128 kbit/s", "144 kbit/s", "160 kbit/s", 
/* 639 */           "forbidden" } } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String bitrate_string() {
/* 648 */     if (this.h_vbr)
/*     */     {
/* 650 */       return String.valueOf(Integer.toString(bitrate() / 1000)) + " kb/s";
/*     */     }
/* 652 */     return bitrate_str[this.h_version][this.h_layer - 1][this.h_bitrate_index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int bitrate() {
/* 661 */     if (this.h_vbr)
/*     */     {
/* 663 */       return (int)((this.h_vbr_bytes * 8) / ms_per_frame() * this.h_vbr_frames) * 1000;
/*     */     }
/* 665 */     return bitrates[this.h_version][this.h_layer - 1][this.h_bitrate_index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int bitrate_instant() {
/* 675 */     return bitrates[this.h_version][this.h_layer - 1][this.h_bitrate_index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String sample_frequency_string() {
/* 684 */     switch (this.h_sample_frequency) {
/*     */       
/*     */       case 2:
/* 687 */         if (this.h_version == 1)
/* 688 */           return "32 kHz"; 
/* 689 */         if (this.h_version == 0) {
/* 690 */           return "16 kHz";
/*     */         }
/* 692 */         return "8 kHz";
/*     */       case 0:
/* 694 */         if (this.h_version == 1)
/* 695 */           return "44.1 kHz"; 
/* 696 */         if (this.h_version == 0) {
/* 697 */           return "22.05 kHz";
/*     */         }
/* 699 */         return "11.025 kHz";
/*     */       case 1:
/* 701 */         if (this.h_version == 1)
/* 702 */           return "48 kHz"; 
/* 703 */         if (this.h_version == 0) {
/* 704 */           return "24 kHz";
/*     */         }
/* 706 */         return "12 kHz";
/*     */     } 
/* 708 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mode_string() {
/* 716 */     switch (this.h_mode) {
/*     */       
/*     */       case 0:
/* 719 */         return "Stereo";
/*     */       case 1:
/* 721 */         return "Joint stereo";
/*     */       case 2:
/* 723 */         return "Dual channel";
/*     */       case 3:
/* 725 */         return "Single channel";
/*     */     } 
/* 727 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String version_string() {
/* 736 */     switch (this.h_version) {
/*     */       
/*     */       case 1:
/* 739 */         return "MPEG-1";
/*     */       case 0:
/* 741 */         return "MPEG-2 LSF";
/*     */       case 2:
/* 743 */         return "MPEG-2.5 LSF";
/*     */     } 
/* 745 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int number_of_subbands() {
/* 752 */     return this.h_number_of_subbands;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intensity_stereo_bound() {
/* 761 */     return this.h_intensity_stereo_bound;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Header.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
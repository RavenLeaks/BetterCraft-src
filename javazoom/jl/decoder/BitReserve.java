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
/*     */ 
/*     */ 
/*     */ final class BitReserve
/*     */ {
/*     */   private static final int BUFSIZE = 32768;
/*     */   private static final int BUFSIZE_MASK = 32767;
/*     */   private int offset;
/*     */   private int totbit;
/*     */   private int buf_byte_idx;
/*  59 */   private final int[] buf = new int[32768];
/*     */   
/*     */   private int buf_bit_idx;
/*     */ 
/*     */   
/*     */   BitReserve() {
/*  65 */     this.offset = 0;
/*  66 */     this.totbit = 0;
/*  67 */     this.buf_byte_idx = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hsstell() {
/*  76 */     return this.totbit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hgetbits(int N) {
/*  85 */     this.totbit += N;
/*     */     
/*  87 */     int val = 0;
/*     */     
/*  89 */     int pos = this.buf_byte_idx;
/*  90 */     if (pos + N < 32768) {
/*     */       
/*  92 */       while (N-- > 0)
/*     */       {
/*  94 */         val <<= 1;
/*  95 */         val |= (this.buf[pos++] != 0) ? 1 : 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 100 */       while (N-- > 0) {
/*     */         
/* 102 */         val <<= 1;
/* 103 */         val |= (this.buf[pos] != 0) ? 1 : 0;
/* 104 */         pos = pos + 1 & 0x7FFF;
/*     */       } 
/*     */     } 
/* 107 */     this.buf_byte_idx = pos;
/* 108 */     return val;
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
/*     */   public int hget1bit() {
/* 140 */     this.totbit++;
/* 141 */     int val = this.buf[this.buf_byte_idx];
/* 142 */     this.buf_byte_idx = this.buf_byte_idx + 1 & 0x7FFF;
/* 143 */     return val;
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
/*     */   public void hputbuf(int val) {
/* 184 */     int ofs = this.offset;
/* 185 */     this.buf[ofs++] = val & 0x80;
/* 186 */     this.buf[ofs++] = val & 0x40;
/* 187 */     this.buf[ofs++] = val & 0x20;
/* 188 */     this.buf[ofs++] = val & 0x10;
/* 189 */     this.buf[ofs++] = val & 0x8;
/* 190 */     this.buf[ofs++] = val & 0x4;
/* 191 */     this.buf[ofs++] = val & 0x2;
/* 192 */     this.buf[ofs++] = val & 0x1;
/*     */     
/* 194 */     if (ofs == 32768) {
/* 195 */       this.offset = 0;
/*     */     } else {
/* 197 */       this.offset = ofs;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rewindNbits(int N) {
/* 206 */     this.totbit -= N;
/* 207 */     this.buf_byte_idx -= N;
/* 208 */     if (this.buf_byte_idx < 0) {
/* 209 */       this.buf_byte_idx += 32768;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rewindNbytes(int N) {
/* 217 */     int bits = N << 3;
/* 218 */     this.totbit -= bits;
/* 219 */     this.buf_byte_idx -= bits;
/* 220 */     if (this.buf_byte_idx < 0)
/* 221 */       this.buf_byte_idx += 32768; 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\BitReserve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
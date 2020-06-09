/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple4b
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -8226727741811898211L;
/*     */   public byte x;
/*     */   public byte y;
/*     */   public byte z;
/*     */   public byte w;
/*     */   
/*     */   public Tuple4b(byte b1, byte b2, byte b3, byte b4) {
/*  78 */     this.x = b1;
/*  79 */     this.y = b2;
/*  80 */     this.z = b3;
/*  81 */     this.w = b4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(byte[] t) {
/*  91 */     this.x = t[0];
/*  92 */     this.y = t[1];
/*  93 */     this.z = t[2];
/*  94 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(Tuple4b t1) {
/* 104 */     this.x = t1.x;
/* 105 */     this.y = t1.y;
/* 106 */     this.z = t1.z;
/* 107 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b() {
/* 116 */     this.x = 0;
/* 117 */     this.y = 0;
/* 118 */     this.z = 0;
/* 119 */     this.w = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return "(" + (this.x & 0xFF) + 
/* 131 */       ", " + (this.y & 0xFF) + 
/* 132 */       ", " + (this.z & 0xFF) + 
/* 133 */       ", " + (this.w & 0xFF) + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(byte[] b) {
/* 144 */     b[0] = this.x;
/* 145 */     b[1] = this.y;
/* 146 */     b[2] = this.z;
/* 147 */     b[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4b t1) {
/* 158 */     t1.x = this.x;
/* 159 */     t1.y = this.y;
/* 160 */     t1.z = this.z;
/* 161 */     t1.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4b t1) {
/* 172 */     this.x = t1.x;
/* 173 */     this.y = t1.y;
/* 174 */     this.z = t1.z;
/* 175 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(byte[] b) {
/* 186 */     this.x = b[0];
/* 187 */     this.y = b[1];
/* 188 */     this.z = b[2];
/* 189 */     this.w = b[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4b t1) {
/*     */     try {
/* 201 */       return (this.x == t1.x && this.y == t1.y && 
/* 202 */         this.z == t1.z && this.w == t1.w);
/*     */     } catch (NullPointerException e2) {
/* 204 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     
/* 218 */     try { Tuple4b t2 = (Tuple4b)t1;
/* 219 */       return (this.x == t2.x && this.y == t2.y && 
/* 220 */         this.z == t2.z && this.w == t2.w); }
/*     */     catch (NullPointerException e2)
/* 222 */     { return false; }
/* 223 */     catch (ClassCastException e1) { return false; }
/*     */   
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
/*     */   public int hashCode() {
/* 238 */     return (this.x & 0xFF) << 0 | (
/* 239 */       this.y & 0xFF) << 8 | (
/* 240 */       this.z & 0xFF) << 16 | (
/* 241 */       this.w & 0xFF) << 24;
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
/*     */   public Object clone() {
/*     */     try {
/* 256 */       return super.clone();
/* 257 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 259 */       throw new InternalError();
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
/*     */   public final byte getX() {
/* 272 */     return this.x;
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
/*     */   public final void setX(byte x) {
/* 284 */     this.x = x;
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
/*     */   public final byte getY() {
/* 296 */     return this.y;
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
/*     */   public final void setY(byte y) {
/* 308 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getZ() {
/* 319 */     return this.z;
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
/*     */   public final void setZ(byte z) {
/* 331 */     this.z = z;
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
/*     */   public final byte getW() {
/* 343 */     return this.w;
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
/*     */   public final void setW(byte w) {
/* 355 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple4b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NBTTagLongArray
/*     */   extends NBTBase
/*     */ {
/*     */   private long[] field_193587_b;
/*     */   
/*     */   NBTTagLongArray() {}
/*     */   
/*     */   public NBTTagLongArray(long[] p_i47524_1_) {
/*  19 */     this.field_193587_b = p_i47524_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagLongArray(List<Long> p_i47525_1_) {
/*  24 */     this(func_193586_a(p_i47525_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   private static long[] func_193586_a(List<Long> p_193586_0_) {
/*  29 */     long[] along = new long[p_193586_0_.size()];
/*     */     
/*  31 */     for (int i = 0; i < p_193586_0_.size(); i++) {
/*     */       
/*  33 */       Long olong = p_193586_0_.get(i);
/*  34 */       along[i] = (olong == null) ? 0L : olong.longValue();
/*     */     } 
/*     */     
/*  37 */     return along;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  45 */     output.writeInt(this.field_193587_b.length); byte b; int i;
/*     */     long[] arrayOfLong;
/*  47 */     for (i = (arrayOfLong = this.field_193587_b).length, b = 0; b < i; ) { long l = arrayOfLong[b];
/*     */       
/*  49 */       output.writeLong(l);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  55 */     sizeTracker.read(192L);
/*  56 */     int i = input.readInt();
/*  57 */     sizeTracker.read((64 * i));
/*  58 */     this.field_193587_b = new long[i];
/*     */     
/*  60 */     for (int j = 0; j < i; j++)
/*     */     {
/*  62 */       this.field_193587_b[j] = input.readLong();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  71 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  76 */     StringBuilder stringbuilder = new StringBuilder("[L;");
/*     */     
/*  78 */     for (int i = 0; i < this.field_193587_b.length; i++) {
/*     */       
/*  80 */       if (i != 0)
/*     */       {
/*  82 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  85 */       stringbuilder.append(this.field_193587_b[i]).append('L');
/*     */     } 
/*     */     
/*  88 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagLongArray copy() {
/*  96 */     long[] along = new long[this.field_193587_b.length];
/*  97 */     System.arraycopy(this.field_193587_b, 0, along, 0, this.field_193587_b.length);
/*  98 */     return new NBTTagLongArray(along);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 103 */     return (super.equals(p_equals_1_) && Arrays.equals(this.field_193587_b, ((NBTTagLongArray)p_equals_1_).field_193587_b));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     return super.hashCode() ^ Arrays.hashCode(this.field_193587_b);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagLongArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
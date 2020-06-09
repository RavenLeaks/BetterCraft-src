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
/*     */ 
/*     */ public class NBTTagIntArray
/*     */   extends NBTBase
/*     */ {
/*     */   private int[] intArray;
/*     */   
/*     */   NBTTagIntArray() {}
/*     */   
/*     */   public NBTTagIntArray(int[] p_i45132_1_) {
/*  20 */     this.intArray = p_i45132_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagIntArray(List<Integer> p_i47528_1_) {
/*  25 */     this(func_193584_a(p_i47528_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] func_193584_a(List<Integer> p_193584_0_) {
/*  30 */     int[] aint = new int[p_193584_0_.size()];
/*     */     
/*  32 */     for (int i = 0; i < p_193584_0_.size(); i++) {
/*     */       
/*  34 */       Integer integer = p_193584_0_.get(i);
/*  35 */       aint[i] = (integer == null) ? 0 : integer.intValue();
/*     */     } 
/*     */     
/*  38 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  46 */     output.writeInt(this.intArray.length); byte b;
/*     */     int i, arrayOfInt[];
/*  48 */     for (i = (arrayOfInt = this.intArray).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*     */       
/*  50 */       output.writeInt(j);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  56 */     sizeTracker.read(192L);
/*  57 */     int i = input.readInt();
/*  58 */     sizeTracker.read((32 * i));
/*  59 */     this.intArray = new int[i];
/*     */     
/*  61 */     for (int j = 0; j < i; j++)
/*     */     {
/*  63 */       this.intArray[j] = input.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  72 */     return 11;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  77 */     StringBuilder stringbuilder = new StringBuilder("[I;");
/*     */     
/*  79 */     for (int i = 0; i < this.intArray.length; i++) {
/*     */       
/*  81 */       if (i != 0)
/*     */       {
/*  83 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  86 */       stringbuilder.append(this.intArray[i]);
/*     */     } 
/*     */     
/*  89 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagIntArray copy() {
/*  97 */     int[] aint = new int[this.intArray.length];
/*  98 */     System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
/*  99 */     return new NBTTagIntArray(aint);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 104 */     return (super.equals(p_equals_1_) && Arrays.equals(this.intArray, ((NBTTagIntArray)p_equals_1_).intArray));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     return super.hashCode() ^ Arrays.hashCode(this.intArray);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntArray() {
/* 114 */     return this.intArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagIntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
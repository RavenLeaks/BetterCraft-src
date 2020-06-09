/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BitArray
/*    */ {
/*    */   private final long[] longArray;
/*    */   private final int bitsPerEntry;
/*    */   private final long maxEntryValue;
/*    */   private final int arraySize;
/*    */   
/*    */   public BitArray(int bitsPerEntryIn, int arraySizeIn) {
/* 27 */     Validate.inclusiveBetween(1L, 32L, bitsPerEntryIn);
/* 28 */     this.arraySize = arraySizeIn;
/* 29 */     this.bitsPerEntry = bitsPerEntryIn;
/* 30 */     this.maxEntryValue = (1L << bitsPerEntryIn) - 1L;
/* 31 */     this.longArray = new long[MathHelper.roundUp(arraySizeIn * bitsPerEntryIn, 64) / 64];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAt(int index, int value) {
/* 39 */     Validate.inclusiveBetween(0L, (this.arraySize - 1), index);
/* 40 */     Validate.inclusiveBetween(0L, this.maxEntryValue, value);
/* 41 */     int i = index * this.bitsPerEntry;
/* 42 */     int j = i / 64;
/* 43 */     int k = ((index + 1) * this.bitsPerEntry - 1) / 64;
/* 44 */     int l = i % 64;
/* 45 */     this.longArray[j] = this.longArray[j] & (this.maxEntryValue << l ^ 0xFFFFFFFFFFFFFFFFL) | (value & this.maxEntryValue) << l;
/*    */     
/* 47 */     if (j != k) {
/*    */       
/* 49 */       int i1 = 64 - l;
/* 50 */       int j1 = this.bitsPerEntry - i1;
/* 51 */       this.longArray[k] = this.longArray[k] >>> j1 << j1 | (value & this.maxEntryValue) >> i1;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getAt(int index) {
/* 60 */     Validate.inclusiveBetween(0L, (this.arraySize - 1), index);
/* 61 */     int i = index * this.bitsPerEntry;
/* 62 */     int j = i / 64;
/* 63 */     int k = ((index + 1) * this.bitsPerEntry - 1) / 64;
/* 64 */     int l = i % 64;
/*    */     
/* 66 */     if (j == k)
/*    */     {
/* 68 */       return (int)(this.longArray[j] >>> l & this.maxEntryValue);
/*    */     }
/*    */ 
/*    */     
/* 72 */     int i1 = 64 - l;
/* 73 */     return (int)((this.longArray[j] >>> l | this.longArray[k] << i1) & this.maxEntryValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] getBackingLongArray() {
/* 82 */     return this.longArray;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 87 */     return this.arraySize;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\BitArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
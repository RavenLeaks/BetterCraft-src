/*    */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class NibbleArray {
/*    */   private final byte[] handle;
/*    */   
/*    */   public NibbleArray(int length) {
/*  9 */     if (length == 0 || length % 2 != 0) {
/* 10 */       throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
/*    */     }
/* 12 */     this.handle = new byte[length / 2];
/*    */   }
/*    */   
/*    */   public NibbleArray(byte[] handle2) {
/* 16 */     if (handle2.length == 0 || handle2.length % 2 != 0) {
/* 17 */       throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
/*    */     }
/* 19 */     this.handle = handle2;
/*    */   }
/*    */   
/*    */   public byte get(int x, int y, int z) {
/* 23 */     return get(y << 8 | z << 4 | x);
/*    */   }
/*    */   
/*    */   public byte get(int index2) {
/* 27 */     byte value2 = this.handle[index2 / 2];
/* 28 */     if (index2 % 2 == 0) {
/* 29 */       return (byte)(value2 & 0xF);
/*    */     }
/* 31 */     return (byte)(value2 >> 4 & 0xF);
/*    */   }
/*    */   
/*    */   public void set(int x, int y, int z, int value2) {
/* 35 */     set(y << 8 | z << 4 | x, value2);
/*    */   }
/*    */   
/*    */   public void set(int index2, int value2) {
/* 39 */     this.handle[index2 /= 2] = (index2 % 2 == 0) ? (byte)(this.handle[index2] & 0xF0 | value2 & 0xF) : (byte)(this.handle[index2] & 0xF | (value2 & 0xF) << 4);
/*    */   }
/*    */   
/*    */   public int size() {
/* 43 */     return this.handle.length * 2;
/*    */   }
/*    */   
/*    */   public int actualSize() {
/* 47 */     return this.handle.length;
/*    */   }
/*    */   
/*    */   public void fill(byte value2) {
/* 51 */     value2 = (byte)(value2 & 0xF);
/* 52 */     Arrays.fill(this.handle, (byte)(value2 << 4 | value2));
/*    */   }
/*    */   
/*    */   public byte[] getHandle() {
/* 56 */     return this.handle;
/*    */   }
/*    */   
/*    */   public void setHandle(byte[] handle2) {
/* 60 */     if (handle2.length != this.handle.length) {
/* 61 */       throw new IllegalArgumentException("Length of handle must equal to size of nibble array!");
/*    */     }
/* 63 */     System.arraycopy(handle2, 0, this.handle, 0, handle2.length);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\NibbleArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
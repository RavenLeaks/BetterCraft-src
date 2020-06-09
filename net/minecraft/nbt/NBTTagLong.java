/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagLong
/*    */   extends NBTPrimitive
/*    */ {
/*    */   private long data;
/*    */   
/*    */   NBTTagLong() {}
/*    */   
/*    */   public NBTTagLong(long data) {
/* 18 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 26 */     output.writeLong(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 31 */     sizeTracker.read(128L);
/* 32 */     this.data = input.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 40 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return String.valueOf(this.data) + "L";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagLong copy() {
/* 53 */     return new NBTTagLong(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 58 */     return (super.equals(p_equals_1_) && this.data == ((NBTTagLong)p_equals_1_).data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 63 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() {
/* 68 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() {
/* 73 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() {
/* 78 */     return (short)(int)(this.data & 0xFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() {
/* 83 */     return (byte)(int)(this.data & 0xFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble() {
/* 88 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat() {
/* 93 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
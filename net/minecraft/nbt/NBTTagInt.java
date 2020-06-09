/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagInt
/*    */   extends NBTPrimitive
/*    */ {
/*    */   private int data;
/*    */   
/*    */   NBTTagInt() {}
/*    */   
/*    */   public NBTTagInt(int data) {
/* 18 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 26 */     output.writeInt(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 31 */     sizeTracker.read(96L);
/* 32 */     this.data = input.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 40 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return String.valueOf(this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagInt copy() {
/* 53 */     return new NBTTagInt(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 58 */     return (super.equals(p_equals_1_) && this.data == ((NBTTagInt)p_equals_1_).data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 63 */     return super.hashCode() ^ this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() {
/* 68 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() {
/* 73 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() {
/* 78 */     return (short)(this.data & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() {
/* 83 */     return (byte)(this.data & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble() {
/* 88 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat() {
/* 93 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
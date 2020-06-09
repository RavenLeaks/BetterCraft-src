/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagDouble
/*    */   extends NBTPrimitive
/*    */ {
/*    */   private double data;
/*    */   
/*    */   NBTTagDouble() {}
/*    */   
/*    */   public NBTTagDouble(double data) {
/* 19 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 27 */     output.writeDouble(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 32 */     sizeTracker.read(128L);
/* 33 */     this.data = input.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 41 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return String.valueOf(this.data) + "d";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagDouble copy() {
/* 54 */     return new NBTTagDouble(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 59 */     return (super.equals(p_equals_1_) && this.data == ((NBTTagDouble)p_equals_1_).data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     long i = Double.doubleToLongBits(this.data);
/* 65 */     return super.hashCode() ^ (int)(i ^ i >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() {
/* 70 */     return (long)Math.floor(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() {
/* 75 */     return MathHelper.floor(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() {
/* 80 */     return (short)(MathHelper.floor(this.data) & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() {
/* 85 */     return (byte)(MathHelper.floor(this.data) & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble() {
/* 90 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat() {
/* 95 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
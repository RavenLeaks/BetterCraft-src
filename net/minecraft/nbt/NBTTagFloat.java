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
/*    */ public class NBTTagFloat
/*    */   extends NBTPrimitive
/*    */ {
/*    */   private float data;
/*    */   
/*    */   NBTTagFloat() {}
/*    */   
/*    */   public NBTTagFloat(float data) {
/* 19 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 27 */     output.writeFloat(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 32 */     sizeTracker.read(96L);
/* 33 */     this.data = input.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 41 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return String.valueOf(this.data) + "f";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagFloat copy() {
/* 54 */     return new NBTTagFloat(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 59 */     return (super.equals(p_equals_1_) && this.data == ((NBTTagFloat)p_equals_1_).data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong() {
/* 69 */     return (long)this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt() {
/* 74 */     return MathHelper.floor(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getShort() {
/* 79 */     return (short)(MathHelper.floor(this.data) & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getByte() {
/* 84 */     return (byte)(MathHelper.floor(this.data) & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble() {
/* 89 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat() {
/* 94 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
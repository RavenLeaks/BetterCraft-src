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
/*     */ public class NBTTagByteArray
/*     */   extends NBTBase
/*     */ {
/*     */   private byte[] data;
/*     */   
/*     */   NBTTagByteArray() {}
/*     */   
/*     */   public NBTTagByteArray(byte[] data) {
/*  20 */     this.data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagByteArray(List<Byte> p_i47529_1_) {
/*  25 */     this(func_193589_a(p_i47529_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] func_193589_a(List<Byte> p_193589_0_) {
/*  30 */     byte[] abyte = new byte[p_193589_0_.size()];
/*     */     
/*  32 */     for (int i = 0; i < p_193589_0_.size(); i++) {
/*     */       
/*  34 */       Byte obyte = p_193589_0_.get(i);
/*  35 */       abyte[i] = (obyte == null) ? 0 : obyte.byteValue();
/*     */     } 
/*     */     
/*  38 */     return abyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  46 */     output.writeInt(this.data.length);
/*  47 */     output.write(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  52 */     sizeTracker.read(192L);
/*  53 */     int i = input.readInt();
/*  54 */     sizeTracker.read((8 * i));
/*  55 */     this.data = new byte[i];
/*  56 */     input.readFully(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  64 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  69 */     StringBuilder stringbuilder = new StringBuilder("[B;");
/*     */     
/*  71 */     for (int i = 0; i < this.data.length; i++) {
/*     */       
/*  73 */       if (i != 0)
/*     */       {
/*  75 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  78 */       stringbuilder.append(this.data[i]).append('B');
/*     */     } 
/*     */     
/*  81 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/*  89 */     byte[] abyte = new byte[this.data.length];
/*  90 */     System.arraycopy(this.data, 0, abyte, 0, this.data.length);
/*  91 */     return new NBTTagByteArray(abyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  96 */     return (super.equals(p_equals_1_) && Arrays.equals(this.data, ((NBTTagByteArray)p_equals_1_).data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray() {
/* 106 */     return this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
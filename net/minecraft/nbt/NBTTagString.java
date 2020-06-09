/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ public class NBTTagString
/*     */   extends NBTBase
/*     */ {
/*     */   private String data;
/*     */   
/*     */   public NBTTagString() {
/*  15 */     this("");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagString(String data) {
/*  20 */     Objects.requireNonNull(data, "Null string not allowed");
/*  21 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  29 */     output.writeUTF(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  34 */     sizeTracker.read(288L);
/*  35 */     this.data = input.readUTF();
/*  36 */     sizeTracker.read((16 * this.data.length()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  44 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  49 */     return func_193588_a(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagString copy() {
/*  57 */     return new NBTTagString(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/*  65 */     return this.data.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  70 */     if (!super.equals(p_equals_1_))
/*     */     {
/*  72 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  76 */     NBTTagString nbttagstring = (NBTTagString)p_equals_1_;
/*  77 */     return !((this.data != null || nbttagstring.data != null) && !Objects.equals(this.data, nbttagstring.data));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  83 */     return super.hashCode() ^ this.data.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/*  88 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String func_193588_a(String p_193588_0_) {
/*  93 */     StringBuilder stringbuilder = new StringBuilder("\"");
/*     */     
/*  95 */     for (int i = 0; i < p_193588_0_.length(); i++) {
/*     */       
/*  97 */       char c0 = p_193588_0_.charAt(i);
/*     */       
/*  99 */       if (c0 == '\\' || c0 == '"')
/*     */       {
/* 101 */         stringbuilder.append('\\');
/*     */       }
/*     */       
/* 104 */       stringbuilder.append(c0);
/*     */     } 
/*     */     
/* 107 */     return stringbuilder.append('"').toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class NBTBase
/*     */ {
/*   9 */   public static final String[] NBT_TYPES = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]" };
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void write(DataOutput paramDataOutput) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void read(DataInput paramDataInput, int paramInt, NBTSizeTracker paramNBTSizeTracker) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte getId();
/*     */ 
/*     */ 
/*     */   
/*     */   protected static NBTBase createNewByType(byte id) {
/*  30 */     switch (id) {
/*     */       
/*     */       case 0:
/*  33 */         return new NBTTagEnd();
/*     */       
/*     */       case 1:
/*  36 */         return new NBTTagByte();
/*     */       
/*     */       case 2:
/*  39 */         return new NBTTagShort();
/*     */       
/*     */       case 3:
/*  42 */         return new NBTTagInt();
/*     */       
/*     */       case 4:
/*  45 */         return new NBTTagLong();
/*     */       
/*     */       case 5:
/*  48 */         return new NBTTagFloat();
/*     */       
/*     */       case 6:
/*  51 */         return new NBTTagDouble();
/*     */       
/*     */       case 7:
/*  54 */         return new NBTTagByteArray();
/*     */       
/*     */       case 8:
/*  57 */         return new NBTTagString();
/*     */       
/*     */       case 9:
/*  60 */         return new NBTTagList();
/*     */       
/*     */       case 10:
/*  63 */         return new NBTTagCompound();
/*     */       
/*     */       case 11:
/*  66 */         return new NBTTagIntArray();
/*     */       
/*     */       case 12:
/*  69 */         return new NBTTagLongArray();
/*     */     } 
/*     */     
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String func_193581_j(int p_193581_0_) {
/*  78 */     switch (p_193581_0_) {
/*     */       
/*     */       case 0:
/*  81 */         return "TAG_End";
/*     */       
/*     */       case 1:
/*  84 */         return "TAG_Byte";
/*     */       
/*     */       case 2:
/*  87 */         return "TAG_Short";
/*     */       
/*     */       case 3:
/*  90 */         return "TAG_Int";
/*     */       
/*     */       case 4:
/*  93 */         return "TAG_Long";
/*     */       
/*     */       case 5:
/*  96 */         return "TAG_Float";
/*     */       
/*     */       case 6:
/*  99 */         return "TAG_Double";
/*     */       
/*     */       case 7:
/* 102 */         return "TAG_Byte_Array";
/*     */       
/*     */       case 8:
/* 105 */         return "TAG_String";
/*     */       
/*     */       case 9:
/* 108 */         return "TAG_List";
/*     */       
/*     */       case 10:
/* 111 */         return "TAG_Compound";
/*     */       
/*     */       case 11:
/* 114 */         return "TAG_Int_Array";
/*     */       
/*     */       case 12:
/* 117 */         return "TAG_Long_Array";
/*     */       
/*     */       case 99:
/* 120 */         return "Any Numeric Tag";
/*     */     } 
/*     */     
/* 123 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract NBTBase copy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 142 */     return (p_equals_1_ instanceof NBTBase && getId() == ((NBTBase)p_equals_1_).getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     return getId();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getString() {
/* 152 */     return toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class VertexFormatElement
/*     */ {
/*   8 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final EnumType type;
/*     */   private final EnumUsage usage;
/*     */   private final int index;
/*     */   private final int elementCount;
/*     */   
/*     */   public VertexFormatElement(int indexIn, EnumType typeIn, EnumUsage usageIn, int count) {
/*  16 */     if (isFirstOrUV(indexIn, usageIn)) {
/*     */       
/*  18 */       this.usage = usageIn;
/*     */     }
/*     */     else {
/*     */       
/*  22 */       LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
/*  23 */       this.usage = EnumUsage.UV;
/*     */     } 
/*     */     
/*  26 */     this.type = typeIn;
/*  27 */     this.index = indexIn;
/*  28 */     this.elementCount = count;
/*     */   }
/*     */ 
/*     */   
/*     */   private final boolean isFirstOrUV(int p_177372_1_, EnumUsage p_177372_2_) {
/*  33 */     return !(p_177372_1_ != 0 && p_177372_2_ != EnumUsage.UV);
/*     */   }
/*     */ 
/*     */   
/*     */   public final EnumType getType() {
/*  38 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public final EnumUsage getUsage() {
/*  43 */     return this.usage;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getElementCount() {
/*  48 */     return this.elementCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getIndex() {
/*  53 */     return this.index;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  58 */     return String.valueOf(this.elementCount) + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getSize() {
/*  63 */     return this.type.getSize() * this.elementCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isPositionElement() {
/*  68 */     return (this.usage == EnumUsage.POSITION);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  73 */     if (this == p_equals_1_)
/*     */     {
/*  75 */       return true;
/*     */     }
/*  77 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  79 */       VertexFormatElement vertexformatelement = (VertexFormatElement)p_equals_1_;
/*     */       
/*  81 */       if (this.elementCount != vertexformatelement.elementCount)
/*     */       {
/*  83 */         return false;
/*     */       }
/*  85 */       if (this.index != vertexformatelement.index)
/*     */       {
/*  87 */         return false;
/*     */       }
/*  89 */       if (this.type != vertexformatelement.type)
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  95 */       return (this.usage == vertexformatelement.usage);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int i = this.type.hashCode();
/* 107 */     i = 31 * i + this.usage.hashCode();
/* 108 */     i = 31 * i + this.index;
/* 109 */     i = 31 * i + this.elementCount;
/* 110 */     return i;
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */   {
/* 115 */     FLOAT(4, "Float", 5126),
/* 116 */     UBYTE(1, "Unsigned Byte", 5121),
/* 117 */     BYTE(1, "Byte", 5120),
/* 118 */     USHORT(2, "Unsigned Short", 5123),
/* 119 */     SHORT(2, "Short", 5122),
/* 120 */     UINT(4, "Unsigned Int", 5125),
/* 121 */     INT(4, "Int", 5124);
/*     */     
/*     */     private final int size;
/*     */     
/*     */     private final String displayName;
/*     */     private final int glConstant;
/*     */     
/*     */     EnumType(int sizeIn, String displayNameIn, int glConstantIn) {
/* 129 */       this.size = sizeIn;
/* 130 */       this.displayName = displayNameIn;
/* 131 */       this.glConstant = glConstantIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSize() {
/* 136 */       return this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayName() {
/* 141 */       return this.displayName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getGlConstant() {
/* 146 */       return this.glConstant;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumUsage
/*     */   {
/* 152 */     POSITION("Position"),
/* 153 */     NORMAL("Normal"),
/* 154 */     COLOR("Vertex Color"),
/* 155 */     UV("UV"),
/* 156 */     MATRIX("Bone Matrix"),
/* 157 */     BLEND_WEIGHT("Blend Weight"),
/* 158 */     PADDING("Padding");
/*     */     
/*     */     private final String displayName;
/*     */ 
/*     */     
/*     */     EnumUsage(String displayNameIn) {
/* 164 */       this.displayName = displayNameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayName() {
/* 169 */       return this.displayName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\vertex\VertexFormatElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
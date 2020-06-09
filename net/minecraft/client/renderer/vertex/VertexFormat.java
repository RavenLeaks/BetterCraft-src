/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class VertexFormat
/*     */ {
/*  10 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat(VertexFormat vertexFormatIn) {
/*  22 */     this();
/*     */     
/*  24 */     for (int i = 0; i < vertexFormatIn.getElementCount(); i++)
/*     */     {
/*  26 */       addElement(vertexFormatIn.getElement(i));
/*     */     }
/*     */     
/*  29 */     this.nextOffset = vertexFormatIn.getNextOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  34 */   private final List<VertexFormatElement> elements = Lists.newArrayList();
/*  35 */   private final List<Integer> offsets = Lists.newArrayList();
/*  36 */   private int colorElementOffset = -1; private int nextOffset;
/*  37 */   private final List<Integer> uvOffsetsById = Lists.newArrayList();
/*  38 */   private int normalElementOffset = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  43 */     this.elements.clear();
/*  44 */     this.offsets.clear();
/*  45 */     this.colorElementOffset = -1;
/*  46 */     this.uvOffsetsById.clear();
/*  47 */     this.normalElementOffset = -1;
/*  48 */     this.nextOffset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat addElement(VertexFormatElement element) {
/*  54 */     if (element.isPositionElement() && hasPosition()) {
/*     */       
/*  56 */       LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
/*  57 */       return this;
/*     */     } 
/*     */ 
/*     */     
/*  61 */     this.elements.add(element);
/*  62 */     this.offsets.add(Integer.valueOf(this.nextOffset));
/*     */     
/*  64 */     switch (element.getUsage()) {
/*     */       
/*     */       case NORMAL:
/*  67 */         this.normalElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case COLOR:
/*  71 */         this.colorElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case UV:
/*  75 */         this.uvOffsetsById.add(element.getIndex(), Integer.valueOf(this.nextOffset));
/*     */         break;
/*     */     } 
/*  78 */     this.nextOffset += element.getSize();
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNormal() {
/*  85 */     return (this.normalElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNormalOffset() {
/*  90 */     return this.normalElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasColor() {
/*  95 */     return (this.colorElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorOffset() {
/* 100 */     return this.colorElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUvOffset(int id) {
/* 105 */     return (this.uvOffsetsById.size() - 1 >= id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUvOffsetById(int id) {
/* 110 */     return ((Integer)this.uvOffsetsById.get(id)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     String s = "format: " + this.elements.size() + " elements: ";
/*     */     
/* 117 */     for (int i = 0; i < this.elements.size(); i++) {
/*     */       
/* 119 */       s = String.valueOf(s) + ((VertexFormatElement)this.elements.get(i)).toString();
/*     */       
/* 121 */       if (i != this.elements.size() - 1)
/*     */       {
/* 123 */         s = String.valueOf(s) + " ";
/*     */       }
/*     */     } 
/*     */     
/* 127 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasPosition() {
/* 132 */     int i = 0;
/*     */     
/* 134 */     for (int j = this.elements.size(); i < j; i++) {
/*     */       
/* 136 */       VertexFormatElement vertexformatelement = this.elements.get(i);
/*     */       
/* 138 */       if (vertexformatelement.isPositionElement())
/*     */       {
/* 140 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntegerSize() {
/* 149 */     return getNextOffset() / 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextOffset() {
/* 154 */     return this.nextOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VertexFormatElement> getElements() {
/* 159 */     return this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementCount() {
/* 164 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormatElement getElement(int index) {
/* 169 */     return this.elements.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffset(int index) {
/* 174 */     return ((Integer)this.offsets.get(index)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 179 */     if (this == p_equals_1_)
/*     */     {
/* 181 */       return true;
/*     */     }
/* 183 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/* 185 */       VertexFormat vertexformat = (VertexFormat)p_equals_1_;
/*     */       
/* 187 */       if (this.nextOffset != vertexformat.nextOffset)
/*     */       {
/* 189 */         return false;
/*     */       }
/* 191 */       if (!this.elements.equals(vertexformat.elements))
/*     */       {
/* 193 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 197 */       return this.offsets.equals(vertexformat.offsets);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     int i = this.elements.hashCode();
/* 209 */     i = 31 * i + this.offsets.hashCode();
/* 210 */     i = 31 * i + this.nextOffset;
/* 211 */     return i;
/*     */   }
/*     */   
/*     */   public VertexFormat() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\vertex\VertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
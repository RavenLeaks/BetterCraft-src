/*     */ package net.minecraft.realms;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ 
/*     */ 
/*     */ public class RealmsVertexFormat
/*     */ {
/*     */   private VertexFormat v;
/*     */   
/*     */   public RealmsVertexFormat(VertexFormat vIn) {
/*  14 */     this.v = vIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsVertexFormat from(VertexFormat p_from_1_) {
/*  19 */     this.v = p_from_1_;
/*  20 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat getVertexFormat() {
/*  25 */     return this.v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  30 */     this.v.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUvOffset(int p_getUvOffset_1_) {
/*  35 */     return this.v.getUvOffsetById(p_getUvOffset_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementCount() {
/*  40 */     return this.v.getElementCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasColor() {
/*  45 */     return this.v.hasColor();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUv(int p_hasUv_1_) {
/*  50 */     return this.v.hasUvOffset(p_hasUv_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsVertexFormatElement getElement(int p_getElement_1_) {
/*  55 */     return new RealmsVertexFormatElement(this.v.getElement(p_getElement_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsVertexFormat addElement(RealmsVertexFormatElement p_addElement_1_) {
/*  60 */     return from(this.v.addElement(p_addElement_1_.getVertexFormatElement()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorOffset() {
/*  65 */     return this.v.getColorOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<RealmsVertexFormatElement> getElements() {
/*  70 */     List<RealmsVertexFormatElement> list = Lists.newArrayList();
/*     */     
/*  72 */     for (VertexFormatElement vertexformatelement : this.v.getElements())
/*     */     {
/*  74 */       list.add(new RealmsVertexFormatElement(vertexformatelement));
/*     */     }
/*     */     
/*  77 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNormal() {
/*  82 */     return this.v.hasNormal();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVertexSize() {
/*  87 */     return this.v.getNextOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffset(int p_getOffset_1_) {
/*  92 */     return this.v.getOffset(p_getOffset_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNormalOffset() {
/*  97 */     return this.v.getNormalOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntegerSize() {
/* 102 */     return this.v.getIntegerSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 107 */     return this.v.equals(p_equals_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     return this.v.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return this.v.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsVertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
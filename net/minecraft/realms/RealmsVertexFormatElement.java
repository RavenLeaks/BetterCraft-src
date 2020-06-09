/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ public class RealmsVertexFormatElement
/*    */ {
/*    */   private final VertexFormatElement v;
/*    */   
/*    */   public RealmsVertexFormatElement(VertexFormatElement vIn) {
/* 11 */     this.v = vIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public VertexFormatElement getVertexFormatElement() {
/* 16 */     return this.v;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPosition() {
/* 21 */     return this.v.isPositionElement();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 26 */     return this.v.getIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getByteSize() {
/* 31 */     return this.v.getSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 36 */     return this.v.getElementCount();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 41 */     return this.v.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 46 */     return this.v.equals(p_equals_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return this.v.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsVertexFormatElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
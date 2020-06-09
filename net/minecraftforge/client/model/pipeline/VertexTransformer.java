/*    */ package net.minecraftforge.client.model.pipeline;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VertexTransformer
/*    */   implements IVertexConsumer
/*    */ {
/*    */   protected final IVertexConsumer parent;
/*    */   
/*    */   public VertexTransformer(IVertexConsumer parent) {
/* 32 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public VertexFormat getVertexFormat() {
/* 38 */     return this.parent.getVertexFormat();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setQuadTint(int tint) {
/* 44 */     this.parent.setQuadTint(tint);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setQuadOrientation(EnumFacing orientation) {
/* 56 */     this.parent.setQuadOrientation(orientation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(int element, float... data) {
/* 68 */     this.parent.put(element, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setQuadColored() {
/* 73 */     this.parent.setQuadColored();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\VertexTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
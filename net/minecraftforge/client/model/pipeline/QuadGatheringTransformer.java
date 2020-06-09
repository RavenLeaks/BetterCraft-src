/*    */ package net.minecraftforge.client.model.pipeline;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
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
/*    */ public abstract class QuadGatheringTransformer
/*    */   implements IVertexConsumer
/*    */ {
/*    */   protected IVertexConsumer parent;
/*    */   protected VertexFormat format;
/* 28 */   protected int vertices = 0;
/*    */   
/* 30 */   protected byte[] dataLength = null;
/* 31 */   protected float[][][] quadData = null;
/*    */ 
/*    */   
/*    */   public void setParent(IVertexConsumer parent) {
/* 35 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVertexFormat(VertexFormat format) {
/* 40 */     this.format = format;
/* 41 */     this.dataLength = new byte[format.getElementCount()];
/* 42 */     this.quadData = new float[format.getElementCount()][4][4];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public VertexFormat getVertexFormat() {
/* 48 */     return this.format;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(int element, float... data) {
/* 54 */     System.arraycopy(data, 0, this.quadData[element][this.vertices], 0, data.length);
/* 55 */     if (this.vertices == 0)
/*    */     {
/* 57 */       this.dataLength[element] = (byte)data.length;
/*    */     }
/* 59 */     if (element == getVertexFormat().getElementCount() - 1)
/*    */     {
/* 61 */       this.vertices++;
/*    */     }
/* 63 */     if (this.vertices == 4) {
/*    */       
/* 65 */       this.vertices = 0;
/* 66 */       processQuad();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void processQuad();
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\QuadGatheringTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*    */ 
/*    */ public class VertexBufferUploader
/*    */   extends WorldVertexBufferUploader
/*    */ {
/*    */   private VertexBuffer vertexBuffer;
/*    */   
/*    */   public void draw(BufferBuilder vertexBufferIn) {
/* 11 */     vertexBufferIn.reset();
/* 12 */     this.vertexBuffer.bufferData(vertexBufferIn.getByteBuffer());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVertexBuffer(VertexBuffer vertexBufferIn) {
/* 17 */     this.vertexBuffer = vertexBufferIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\VertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
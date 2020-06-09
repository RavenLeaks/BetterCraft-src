/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ public class Tessellator
/*    */ {
/*    */   private final BufferBuilder worldRenderer;
/*  6 */   private final WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
/*    */ 
/*    */   
/*  9 */   private static final Tessellator INSTANCE = new Tessellator(2097152);
/*    */ 
/*    */   
/*    */   public static Tessellator getInstance() {
/* 13 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Tessellator(int bufferSize) {
/* 18 */     this.worldRenderer = new BufferBuilder(bufferSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     this.worldRenderer.finishDrawing();
/* 27 */     this.vboUploader.draw(this.worldRenderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferBuilder getBuffer() {
/* 32 */     return this.worldRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\Tessellator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
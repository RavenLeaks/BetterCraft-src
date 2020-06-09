/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.ShadersRender;
/*    */ 
/*    */ public class VboRenderList
/*    */   extends ChunkRenderContainer
/*    */ {
/*    */   public void renderChunkLayer(BlockRenderLayer layer) {
/* 13 */     if (this.initialized) {
/*    */       
/* 15 */       for (RenderChunk renderchunk : this.renderChunks) {
/*    */         
/* 17 */         VertexBuffer vertexbuffer = renderchunk.getVertexBufferByLayer(layer.ordinal());
/* 18 */         GlStateManager.pushMatrix();
/* 19 */         preRenderChunk(renderchunk);
/* 20 */         renderchunk.multModelviewMatrix();
/* 21 */         vertexbuffer.bindBuffer();
/* 22 */         setupArrayPointers();
/* 23 */         vertexbuffer.drawArrays(7);
/* 24 */         GlStateManager.popMatrix();
/*    */       } 
/*    */       
/* 27 */       OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/* 28 */       GlStateManager.resetColor();
/* 29 */       this.renderChunks.clear();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void setupArrayPointers() {
/* 35 */     if (Config.isShaders()) {
/*    */       
/* 37 */       ShadersRender.setupArrayPointersVbo();
/*    */     }
/*    */     else {
/*    */       
/* 41 */       GlStateManager.glVertexPointer(3, 5126, 28, 0);
/* 42 */       GlStateManager.glColorPointer(4, 5121, 28, 12);
/* 43 */       GlStateManager.glTexCoordPointer(2, 5126, 28, 16);
/* 44 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 45 */       GlStateManager.glTexCoordPointer(2, 5122, 28, 24);
/* 46 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\VboRenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.chunk.ListedRenderChunk;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import optifine.Config;
/*    */ 
/*    */ public class RenderList
/*    */   extends ChunkRenderContainer
/*    */ {
/*    */   public void renderChunkLayer(BlockRenderLayer layer) {
/* 12 */     if (this.initialized) {
/*    */       
/* 14 */       if (this.renderChunks.size() == 0) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 19 */       for (RenderChunk renderchunk : this.renderChunks) {
/*    */         
/* 21 */         ListedRenderChunk listedrenderchunk = (ListedRenderChunk)renderchunk;
/* 22 */         GlStateManager.pushMatrix();
/* 23 */         preRenderChunk(renderchunk);
/* 24 */         GlStateManager.callList(listedrenderchunk.getDisplayList(layer, listedrenderchunk.getCompiledChunk()));
/* 25 */         GlStateManager.popMatrix();
/*    */       } 
/*    */       
/* 28 */       if (Config.isMultiTexture())
/*    */       {
/* 30 */         GlStateManager.bindCurrentTexture();
/*    */       }
/*    */       
/* 33 */       GlStateManager.resetColor();
/* 34 */       this.renderChunks.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\RenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
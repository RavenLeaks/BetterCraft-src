/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import me.nzxter.bettercraft.mods.chunkanimator.ChunkAnimator;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ChunkRenderContainer
/*    */ {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 17 */   protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
/*    */   
/*    */   protected boolean initialized;
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 22 */     this.initialized = true;
/* 23 */     this.renderChunks.clear();
/* 24 */     this.viewEntityX = viewEntityXIn;
/* 25 */     this.viewEntityY = viewEntityYIn;
/* 26 */     this.viewEntityZ = viewEntityZIn;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void preRenderChunk(RenderChunk renderChunkIn) {
/* 32 */     ChunkAnimator.INSTANCE.animationHandler.preRender(renderChunkIn);
/*    */ 
/*    */     
/* 35 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 36 */     GlStateManager.translate((float)(blockpos.getX() - this.viewEntityX), (float)(blockpos.getY() - this.viewEntityY), (float)(blockpos.getZ() - this.viewEntityZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRenderChunk(RenderChunk renderChunkIn, BlockRenderLayer layer) {
/* 41 */     this.renderChunks.add(renderChunkIn);
/*    */   }
/*    */   
/*    */   public abstract void renderChunkLayer(BlockRenderLayer paramBlockRenderLayer);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ChunkRenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
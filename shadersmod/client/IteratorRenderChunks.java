/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.client.renderer.ViewFrustum;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import optifine.BlockPosM;
/*    */ 
/*    */ public class IteratorRenderChunks
/*    */   implements Iterator<RenderChunk> {
/*    */   private ViewFrustum viewFrustum;
/*    */   private Iterator3d Iterator3d;
/* 13 */   private BlockPosM posBlock = new BlockPosM(0, 0, 0);
/*    */ 
/*    */   
/*    */   public IteratorRenderChunks(ViewFrustum viewFrustum, BlockPos posStart, BlockPos posEnd, int width, int height) {
/* 17 */     this.viewFrustum = viewFrustum;
/* 18 */     this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 23 */     return this.Iterator3d.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderChunk next() {
/* 28 */     BlockPos blockpos = this.Iterator3d.next();
/* 29 */     this.posBlock.setXyz(blockpos.getX() << 4, blockpos.getY() << 4, blockpos.getZ() << 4);
/* 30 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk((BlockPos)this.posBlock);
/* 31 */     return renderchunk;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 36 */     throw new RuntimeException("Not implemented");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\IteratorRenderChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
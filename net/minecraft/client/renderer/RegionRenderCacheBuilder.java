/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ 
/*    */ public class RegionRenderCacheBuilder
/*    */ {
/*  7 */   private final BufferBuilder[] worldRenderers = new BufferBuilder[(BlockRenderLayer.values()).length];
/*    */ 
/*    */   
/*    */   public RegionRenderCacheBuilder() {
/* 11 */     this.worldRenderers[BlockRenderLayer.SOLID.ordinal()] = new BufferBuilder(2097152);
/* 12 */     this.worldRenderers[BlockRenderLayer.CUTOUT.ordinal()] = new BufferBuilder(131072);
/* 13 */     this.worldRenderers[BlockRenderLayer.CUTOUT_MIPPED.ordinal()] = new BufferBuilder(131072);
/* 14 */     this.worldRenderers[BlockRenderLayer.TRANSLUCENT.ordinal()] = new BufferBuilder(262144);
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferBuilder getWorldRendererByLayer(BlockRenderLayer layer) {
/* 19 */     return this.worldRenderers[layer.ordinal()];
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferBuilder getWorldRendererByLayerId(int id) {
/* 24 */     return this.worldRenderers[id];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\RegionRenderCacheBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
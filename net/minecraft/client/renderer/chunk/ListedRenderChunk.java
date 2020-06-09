/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ListedRenderChunk
/*    */   extends RenderChunk {
/* 10 */   private final int baseDisplayList = GLAllocation.generateDisplayLists((BlockRenderLayer.values()).length);
/*    */ 
/*    */   
/*    */   public ListedRenderChunk(World p_i47121_1_, RenderGlobal p_i47121_2_, int p_i47121_3_) {
/* 14 */     super(p_i47121_1_, p_i47121_2_, p_i47121_3_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDisplayList(BlockRenderLayer layer, CompiledChunk p_178600_2_) {
/* 19 */     return !p_178600_2_.isLayerEmpty(layer) ? (this.baseDisplayList + layer.ordinal()) : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteGlResources() {
/* 24 */     super.deleteGlResources();
/* 25 */     GLAllocation.deleteDisplayLists(this.baseDisplayList, (BlockRenderLayer.values()).length);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\ListedRenderChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
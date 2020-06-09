/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ListChunkFactory
/*    */   implements IRenderChunkFactory
/*    */ {
/*    */   public RenderChunk create(World worldIn, RenderGlobal p_189565_2_, int p_189565_3_) {
/* 10 */     return new ListedRenderChunk(worldIn, p_189565_2_, p_189565_3_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\ListChunkFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
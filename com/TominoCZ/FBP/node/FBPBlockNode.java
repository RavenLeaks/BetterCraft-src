/*    */ package com.TominoCZ.FBP.node;
/*    */ 
/*    */ import com.TominoCZ.FBP.particle.FBPParticleBlock;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FBPBlockNode
/*    */ {
/*    */   public IBlockState state;
/*    */   public Block originalBlock;
/*    */   public int meta;
/*    */   public FBPParticleBlock particle;
/*    */   
/*    */   public FBPBlockNode(IBlockState s, FBPParticleBlock p) {
/* 18 */     this.particle = p;
/* 19 */     this.state = s;
/* 20 */     this.originalBlock = s.getBlock();
/* 21 */     this.meta = this.originalBlock.getMetaFromState(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\node\FBPBlockNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package optifine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ListQuadsOverlay
/*    */ {
/* 12 */   private List<BakedQuad> listQuads = new ArrayList<>();
/* 13 */   private List<IBlockState> listBlockStates = new ArrayList<>();
/* 14 */   private List<BakedQuad> listQuadsSingle = Arrays.asList(new BakedQuad[0]);
/*    */ 
/*    */   
/*    */   public void addQuad(BakedQuad p_addQuad_1_, IBlockState p_addQuad_2_) {
/* 18 */     this.listQuads.add(p_addQuad_1_);
/* 19 */     this.listBlockStates.add(p_addQuad_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 24 */     return this.listQuads.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public BakedQuad getQuad(int p_getQuad_1_) {
/* 29 */     return this.listQuads.get(p_getQuad_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState(int p_getBlockState_1_) {
/* 34 */     return (p_getBlockState_1_ >= 0 && p_getBlockState_1_ < this.listBlockStates.size()) ? this.listBlockStates.get(p_getBlockState_1_) : Blocks.AIR.getDefaultState();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getListQuadsSingle(BakedQuad p_getListQuadsSingle_1_) {
/* 39 */     this.listQuadsSingle.set(0, p_getListQuadsSingle_1_);
/* 40 */     return this.listQuadsSingle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 45 */     this.listQuads.clear();
/* 46 */     this.listBlockStates.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ListQuadsOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
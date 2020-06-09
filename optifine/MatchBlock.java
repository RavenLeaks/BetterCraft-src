/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.block.state.BlockStateBase;
/*    */ 
/*    */ public class MatchBlock
/*    */ {
/*  7 */   private int blockId = -1;
/*  8 */   private int[] metadatas = null;
/*    */ 
/*    */   
/*    */   public MatchBlock(int p_i63_1_) {
/* 12 */     this.blockId = p_i63_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock(int p_i64_1_, int p_i64_2_) {
/* 17 */     this.blockId = p_i64_1_;
/*    */     
/* 19 */     if (p_i64_2_ >= 0 && p_i64_2_ <= 15)
/*    */     {
/* 21 */       this.metadatas = new int[] { p_i64_2_ };
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock(int p_i65_1_, int[] p_i65_2_) {
/* 27 */     this.blockId = p_i65_1_;
/* 28 */     this.metadatas = p_i65_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockId() {
/* 33 */     return this.blockId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getMetadatas() {
/* 38 */     return this.metadatas;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(BlockStateBase p_matches_1_) {
/* 43 */     if (p_matches_1_.getBlockId() != this.blockId)
/*    */     {
/* 45 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 49 */     return Matches.metadata(p_matches_1_.getMetadata(), this.metadatas);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(int p_matches_1_, int p_matches_2_) {
/* 55 */     if (p_matches_1_ != this.blockId)
/*    */     {
/* 57 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 61 */     return Matches.metadata(p_matches_2_, this.metadatas);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addMetadata(int p_addMetadata_1_) {
/* 67 */     if (this.metadatas != null)
/*    */     {
/* 69 */       if (p_addMetadata_1_ >= 0 && p_addMetadata_1_ <= 15) {
/*    */         
/* 71 */         for (int i = 0; i < this.metadatas.length; i++) {
/*    */           
/* 73 */           if (this.metadatas[i] == p_addMetadata_1_) {
/*    */             return;
/*    */           }
/*    */         } 
/*    */ 
/*    */         
/* 79 */         this.metadatas = Config.addIntToArray(this.metadatas, p_addMetadata_1_);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return this.blockId + ":" + Config.arrayToString(this.metadatas);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\MatchBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
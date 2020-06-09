/*    */ package optifine;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockModelCustomizer
/*    */ {
/* 14 */   private static final List<BakedQuad> NO_QUADS = (List<BakedQuad>)ImmutableList.of();
/*    */ 
/*    */   
/*    */   public static IBakedModel getRenderModel(IBakedModel p_getRenderModel_0_, IBlockState p_getRenderModel_1_, RenderEnv p_getRenderModel_2_) {
/* 18 */     if (p_getRenderModel_2_.isSmartLeaves())
/*    */     {
/* 20 */       p_getRenderModel_0_ = SmartLeaves.getLeavesModel(p_getRenderModel_0_, p_getRenderModel_1_);
/*    */     }
/*    */     
/* 23 */     return p_getRenderModel_0_;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<BakedQuad> getRenderQuads(List<BakedQuad> p_getRenderQuads_0_, IBlockAccess p_getRenderQuads_1_, IBlockState p_getRenderQuads_2_, BlockPos p_getRenderQuads_3_, EnumFacing p_getRenderQuads_4_, long p_getRenderQuads_5_, RenderEnv p_getRenderQuads_7_) {
/* 28 */     if (p_getRenderQuads_4_ != null) {
/*    */       
/* 30 */       if (p_getRenderQuads_7_.isSmartLeaves() && SmartLeaves.isSameLeaves(p_getRenderQuads_1_.getBlockState(p_getRenderQuads_3_.offset(p_getRenderQuads_4_)), p_getRenderQuads_2_))
/*    */       {
/* 32 */         return NO_QUADS;
/*    */       }
/*    */       
/* 35 */       if (!p_getRenderQuads_7_.isBreakingAnimation(p_getRenderQuads_0_) && Config.isBetterGrass())
/*    */       {
/* 37 */         p_getRenderQuads_0_ = BetterGrass.getFaceQuads(p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_4_, p_getRenderQuads_0_);
/*    */       }
/*    */     } 
/*    */     
/* 41 */     List<BakedQuad> list = p_getRenderQuads_7_.getListQuadsCustomizer();
/* 42 */     list.clear();
/*    */     
/* 44 */     for (int i = 0; i < p_getRenderQuads_0_.size(); i++) {
/*    */       
/* 46 */       BakedQuad bakedquad = p_getRenderQuads_0_.get(i);
/* 47 */       BakedQuad[] abakedquad = getRenderQuads(bakedquad, p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_4_, p_getRenderQuads_5_, p_getRenderQuads_7_);
/*    */       
/* 49 */       if (i == 0 && p_getRenderQuads_0_.size() == 1 && abakedquad.length == 1 && abakedquad[0] == bakedquad)
/*    */       {
/* 51 */         return p_getRenderQuads_0_;
/*    */       }
/*    */       
/* 54 */       for (int j = 0; j < abakedquad.length; j++) {
/*    */         
/* 56 */         BakedQuad bakedquad1 = abakedquad[j];
/* 57 */         list.add(bakedquad1);
/*    */       } 
/*    */     } 
/*    */     
/* 61 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   private static BakedQuad[] getRenderQuads(BakedQuad p_getRenderQuads_0_, IBlockAccess p_getRenderQuads_1_, IBlockState p_getRenderQuads_2_, BlockPos p_getRenderQuads_3_, EnumFacing p_getRenderQuads_4_, long p_getRenderQuads_5_, RenderEnv p_getRenderQuads_7_) {
/* 66 */     if (p_getRenderQuads_7_.isBreakingAnimation(p_getRenderQuads_0_))
/*    */     {
/* 68 */       return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
/*    */     }
/*    */ 
/*    */     
/* 72 */     BakedQuad bakedquad = p_getRenderQuads_0_;
/*    */     
/* 74 */     if (Config.isConnectedTextures()) {
/*    */       
/* 76 */       BakedQuad[] abakedquad = ConnectedTextures.getConnectedTexture(p_getRenderQuads_1_, p_getRenderQuads_2_, p_getRenderQuads_3_, p_getRenderQuads_0_, p_getRenderQuads_7_);
/*    */       
/* 78 */       if (abakedquad.length != 1 || abakedquad[0] != p_getRenderQuads_0_)
/*    */       {
/* 80 */         return abakedquad;
/*    */       }
/*    */     } 
/*    */     
/* 84 */     if (Config.isNaturalTextures()) {
/*    */       
/* 86 */       p_getRenderQuads_0_ = NaturalTextures.getNaturalTexture(p_getRenderQuads_3_, p_getRenderQuads_0_);
/*    */       
/* 88 */       if (p_getRenderQuads_0_ != bakedquad)
/*    */       {
/* 90 */         return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
/*    */       }
/*    */     } 
/*    */     
/* 94 */     return p_getRenderQuads_7_.getArrayQuadsCtm(p_getRenderQuads_0_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BlockModelCustomizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
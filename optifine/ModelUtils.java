/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.SimpleBakedModel;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelUtils
/*     */ {
/*     */   public static void dbgModel(IBakedModel p_dbgModel_0_) {
/*  17 */     if (p_dbgModel_0_ != null) {
/*     */       
/*  19 */       Config.dbg("Model: " + p_dbgModel_0_ + ", ao: " + p_dbgModel_0_.isAmbientOcclusion() + ", gui3d: " + p_dbgModel_0_.isGui3d() + ", builtIn: " + p_dbgModel_0_.isBuiltInRenderer() + ", particle: " + p_dbgModel_0_.getParticleTexture());
/*  20 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */       
/*  22 */       for (int i = 0; i < aenumfacing.length; i++) {
/*     */         
/*  24 */         EnumFacing enumfacing = aenumfacing[i];
/*  25 */         List list = p_dbgModel_0_.getQuads(null, enumfacing, 0L);
/*  26 */         dbgQuads(enumfacing.getName(), list, "  ");
/*     */       } 
/*     */       
/*  29 */       List list1 = p_dbgModel_0_.getQuads(null, null, 0L);
/*  30 */       dbgQuads("General", list1, "  ");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbgQuads(String p_dbgQuads_0_, List p_dbgQuads_1_, String p_dbgQuads_2_) {
/*  36 */     for (Object bakedquad : p_dbgQuads_1_)
/*     */     {
/*  38 */       dbgQuad(p_dbgQuads_0_, (BakedQuad)bakedquad, p_dbgQuads_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgQuad(String p_dbgQuad_0_, BakedQuad p_dbgQuad_1_, String p_dbgQuad_2_) {
/*  44 */     Config.dbg(String.valueOf(p_dbgQuad_2_) + "Quad: " + p_dbgQuad_1_.getClass().getName() + ", type: " + p_dbgQuad_0_ + ", face: " + p_dbgQuad_1_.getFace() + ", tint: " + p_dbgQuad_1_.getTintIndex() + ", sprite: " + p_dbgQuad_1_.getSprite());
/*  45 */     dbgVertexData(p_dbgQuad_1_.getVertexData(), "  " + p_dbgQuad_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgVertexData(int[] p_dbgVertexData_0_, String p_dbgVertexData_1_) {
/*  50 */     int i = p_dbgVertexData_0_.length / 4;
/*  51 */     Config.dbg(String.valueOf(p_dbgVertexData_1_) + "Length: " + p_dbgVertexData_0_.length + ", step: " + i);
/*     */     
/*  53 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  55 */       int k = j * i;
/*  56 */       float f = Float.intBitsToFloat(p_dbgVertexData_0_[k + 0]);
/*  57 */       float f1 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 1]);
/*  58 */       float f2 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 2]);
/*  59 */       int l = p_dbgVertexData_0_[k + 3];
/*  60 */       float f3 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 4]);
/*  61 */       float f4 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 5]);
/*  62 */       Config.dbg(String.valueOf(p_dbgVertexData_1_) + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel duplicateModel(IBakedModel p_duplicateModel_0_) {
/*  68 */     List list = duplicateQuadList(p_duplicateModel_0_.getQuads(null, null, 0L));
/*  69 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  70 */     Map<EnumFacing, List<BakedQuad>> map = new HashMap<>();
/*     */     
/*  72 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  74 */       EnumFacing enumfacing = aenumfacing[i];
/*  75 */       List list1 = p_duplicateModel_0_.getQuads(null, enumfacing, 0L);
/*  76 */       List<BakedQuad> list2 = duplicateQuadList(list1);
/*  77 */       map.put(enumfacing, list2);
/*     */     } 
/*     */     
/*  80 */     SimpleBakedModel simplebakedmodel = new SimpleBakedModel(list, map, p_duplicateModel_0_.isAmbientOcclusion(), p_duplicateModel_0_.isGui3d(), p_duplicateModel_0_.getParticleTexture(), p_duplicateModel_0_.getItemCameraTransforms(), p_duplicateModel_0_.getOverrides());
/*  81 */     return (IBakedModel)simplebakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List duplicateQuadList(List p_duplicateQuadList_0_) {
/*  86 */     List<BakedQuad> list = new ArrayList();
/*     */     
/*  88 */     for (Object bakedquad : p_duplicateQuadList_0_) {
/*     */       
/*  90 */       BakedQuad bakedquad1 = duplicateQuad((BakedQuad)bakedquad);
/*  91 */       list.add(bakedquad1);
/*     */     } 
/*     */     
/*  94 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad duplicateQuad(BakedQuad p_duplicateQuad_0_) {
/*  99 */     BakedQuad bakedquad = new BakedQuad((int[])p_duplicateQuad_0_.getVertexData().clone(), p_duplicateQuad_0_.getTintIndex(), p_duplicateQuad_0_.getFace(), p_duplicateQuad_0_.getSprite());
/* 100 */     return bakedquad;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
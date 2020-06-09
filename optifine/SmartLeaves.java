/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ModelManager;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SmartLeaves {
/*  19 */   private static IBakedModel modelLeavesCullAcacia = null;
/*  20 */   private static IBakedModel modelLeavesCullBirch = null;
/*  21 */   private static IBakedModel modelLeavesCullDarkOak = null;
/*  22 */   private static IBakedModel modelLeavesCullJungle = null;
/*  23 */   private static IBakedModel modelLeavesCullOak = null;
/*  24 */   private static IBakedModel modelLeavesCullSpruce = null;
/*  25 */   private static List generalQuadsCullAcacia = null;
/*  26 */   private static List generalQuadsCullBirch = null;
/*  27 */   private static List generalQuadsCullDarkOak = null;
/*  28 */   private static List generalQuadsCullJungle = null;
/*  29 */   private static List generalQuadsCullOak = null;
/*  30 */   private static List generalQuadsCullSpruce = null;
/*  31 */   private static IBakedModel modelLeavesDoubleAcacia = null;
/*  32 */   private static IBakedModel modelLeavesDoubleBirch = null;
/*  33 */   private static IBakedModel modelLeavesDoubleDarkOak = null;
/*  34 */   private static IBakedModel modelLeavesDoubleJungle = null;
/*  35 */   private static IBakedModel modelLeavesDoubleOak = null;
/*  36 */   private static IBakedModel modelLeavesDoubleSpruce = null;
/*     */ 
/*     */   
/*     */   public static IBakedModel getLeavesModel(IBakedModel p_getLeavesModel_0_, IBlockState p_getLeavesModel_1_) {
/*  40 */     if (!Config.isTreesSmart())
/*     */     {
/*  42 */       return p_getLeavesModel_0_;
/*     */     }
/*     */ 
/*     */     
/*  46 */     List list = p_getLeavesModel_0_.getQuads(p_getLeavesModel_1_, null, 0L);
/*     */     
/*  48 */     if (list == generalQuadsCullAcacia)
/*     */     {
/*  50 */       return modelLeavesDoubleAcacia;
/*     */     }
/*  52 */     if (list == generalQuadsCullBirch)
/*     */     {
/*  54 */       return modelLeavesDoubleBirch;
/*     */     }
/*  56 */     if (list == generalQuadsCullDarkOak)
/*     */     {
/*  58 */       return modelLeavesDoubleDarkOak;
/*     */     }
/*  60 */     if (list == generalQuadsCullJungle)
/*     */     {
/*  62 */       return modelLeavesDoubleJungle;
/*     */     }
/*  64 */     if (list == generalQuadsCullOak)
/*     */     {
/*  66 */       return modelLeavesDoubleOak;
/*     */     }
/*     */ 
/*     */     
/*  70 */     return (list == generalQuadsCullSpruce) ? modelLeavesDoubleSpruce : p_getLeavesModel_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameLeaves(IBlockState p_isSameLeaves_0_, IBlockState p_isSameLeaves_1_) {
/*  77 */     if (p_isSameLeaves_0_ == p_isSameLeaves_1_)
/*     */     {
/*  79 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  83 */     Block block = p_isSameLeaves_0_.getBlock();
/*  84 */     Block block1 = p_isSameLeaves_1_.getBlock();
/*     */     
/*  86 */     if (block != block1)
/*     */     {
/*  88 */       return false;
/*     */     }
/*  90 */     if (block instanceof BlockOldLeaf)
/*     */     {
/*  92 */       return ((BlockPlanks.EnumType)p_isSameLeaves_0_.getValue((IProperty)BlockOldLeaf.VARIANT)).equals(p_isSameLeaves_1_.getValue((IProperty)BlockOldLeaf.VARIANT));
/*     */     }
/*     */ 
/*     */     
/*  96 */     return (block instanceof BlockNewLeaf) ? ((BlockPlanks.EnumType)p_isSameLeaves_0_.getValue((IProperty)BlockNewLeaf.VARIANT)).equals(p_isSameLeaves_1_.getValue((IProperty)BlockNewLeaf.VARIANT)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateLeavesModels() {
/* 103 */     List list = new ArrayList();
/* 104 */     modelLeavesCullAcacia = getModelCull("acacia", list);
/* 105 */     modelLeavesCullBirch = getModelCull("birch", list);
/* 106 */     modelLeavesCullDarkOak = getModelCull("dark_oak", list);
/* 107 */     modelLeavesCullJungle = getModelCull("jungle", list);
/* 108 */     modelLeavesCullOak = getModelCull("oak", list);
/* 109 */     modelLeavesCullSpruce = getModelCull("spruce", list);
/* 110 */     generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
/* 111 */     generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
/* 112 */     generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
/* 113 */     generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
/* 114 */     generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
/* 115 */     generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
/* 116 */     modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
/* 117 */     modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
/* 118 */     modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
/* 119 */     modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
/* 120 */     modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
/* 121 */     modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
/*     */     
/* 123 */     if (list.size() > 0)
/*     */     {
/* 125 */       Config.dbg("Enable face culling: " + Config.arrayToString(list.toArray()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getGeneralQuadsSafe(IBakedModel p_getGeneralQuadsSafe_0_) {
/* 131 */     return (p_getGeneralQuadsSafe_0_ == null) ? null : p_getGeneralQuadsSafe_0_.getQuads(null, null, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   static IBakedModel getModelCull(String p_getModelCull_0_, List<String> p_getModelCull_1_) {
/* 136 */     ModelManager modelmanager = Config.getModelManager();
/*     */     
/* 138 */     if (modelmanager == null)
/*     */     {
/* 140 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 144 */     ResourceLocation resourcelocation = new ResourceLocation("blockstates/" + p_getModelCull_0_ + "_leaves.json");
/*     */     
/* 146 */     if (Config.getDefiningResourcePack(resourcelocation) != Config.getDefaultResourcePack())
/*     */     {
/* 148 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 152 */     ResourceLocation resourcelocation1 = new ResourceLocation("models/block/" + p_getModelCull_0_ + "_leaves.json");
/*     */     
/* 154 */     if (Config.getDefiningResourcePack(resourcelocation1) != Config.getDefaultResourcePack())
/*     */     {
/* 156 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 160 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(String.valueOf(p_getModelCull_0_) + "_leaves", "normal");
/* 161 */     IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */     
/* 163 */     if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */       
/* 165 */       List list = ibakedmodel.getQuads(null, null, 0L);
/*     */       
/* 167 */       if (list.size() == 0)
/*     */       {
/* 169 */         return ibakedmodel;
/*     */       }
/* 171 */       if (list.size() != 6)
/*     */       {
/* 173 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 177 */       for (Object bakedquad : list) {
/*     */         
/* 179 */         List<Object> list1 = ibakedmodel.getQuads(null, ((BakedQuad)bakedquad).getFace(), 0L);
/*     */         
/* 181 */         if (list1.size() > 0)
/*     */         {
/* 183 */           return null;
/*     */         }
/*     */         
/* 186 */         list1.add(bakedquad);
/*     */       } 
/*     */       
/* 189 */       list.clear();
/* 190 */       p_getModelCull_1_.add(String.valueOf(p_getModelCull_0_) + "_leaves");
/* 191 */       return ibakedmodel;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IBakedModel getModelDoubleFace(IBakedModel p_getModelDoubleFace_0_) {
/* 205 */     if (p_getModelDoubleFace_0_ == null)
/*     */     {
/* 207 */       return null;
/*     */     }
/* 209 */     if (p_getModelDoubleFace_0_.getQuads(null, null, 0L).size() > 0) {
/*     */       
/* 211 */       Config.warn("SmartLeaves: Model is not cube, general quads: " + p_getModelDoubleFace_0_.getQuads(null, null, 0L).size() + ", model: " + p_getModelDoubleFace_0_);
/* 212 */       return p_getModelDoubleFace_0_;
/*     */     } 
/*     */ 
/*     */     
/* 216 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */     
/* 218 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/* 220 */       EnumFacing enumfacing = aenumfacing[i];
/* 221 */       List<BakedQuad> list = p_getModelDoubleFace_0_.getQuads(null, enumfacing, 0L);
/*     */       
/* 223 */       if (list.size() != 1) {
/*     */         
/* 225 */         Config.warn("SmartLeaves: Model is not cube, side: " + enumfacing + ", quads: " + list.size() + ", model: " + p_getModelDoubleFace_0_);
/* 226 */         return p_getModelDoubleFace_0_;
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     IBakedModel ibakedmodel = ModelUtils.duplicateModel(p_getModelDoubleFace_0_);
/* 231 */     List[] alist = new List[aenumfacing.length];
/*     */     
/* 233 */     for (int k = 0; k < aenumfacing.length; k++) {
/*     */       
/* 235 */       EnumFacing enumfacing1 = aenumfacing[k];
/* 236 */       List<BakedQuad> list1 = ibakedmodel.getQuads(null, enumfacing1, 0L);
/* 237 */       BakedQuad bakedquad = list1.get(0);
/* 238 */       BakedQuad bakedquad1 = new BakedQuad((int[])bakedquad.getVertexData().clone(), bakedquad.getTintIndex(), bakedquad.getFace(), bakedquad.getSprite());
/* 239 */       int[] aint = bakedquad1.getVertexData();
/* 240 */       int[] aint1 = (int[])aint.clone();
/* 241 */       int j = aint.length / 4;
/* 242 */       System.arraycopy(aint, 0 * j, aint1, 3 * j, j);
/* 243 */       System.arraycopy(aint, 1 * j, aint1, 2 * j, j);
/* 244 */       System.arraycopy(aint, 2 * j, aint1, 1 * j, j);
/* 245 */       System.arraycopy(aint, 3 * j, aint1, 0 * j, j);
/* 246 */       System.arraycopy(aint1, 0, aint, 0, aint1.length);
/* 247 */       list1.add(bakedquad1);
/*     */     } 
/*     */     
/* 250 */     return ibakedmodel;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\SmartLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
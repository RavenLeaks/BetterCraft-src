/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.color.BlockColors;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import optifine.BetterSnow;
/*     */ import optifine.BlockModelCustomizer;
/*     */ import optifine.Config;
/*     */ import optifine.CustomColors;
/*     */ import optifine.ListQuadsOverlay;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ import optifine.RenderEnv;
/*     */ import shadersmod.client.SVertexBuilder;
/*     */ 
/*     */ public class BlockModelRenderer
/*     */ {
/*     */   private final BlockColors blockColors;
/*  38 */   private static float aoLightValueOpaque = 0.2F;
/*  39 */   private static final BlockRenderLayer[] OVERLAY_LAYERS = new BlockRenderLayer[] { BlockRenderLayer.CUTOUT, BlockRenderLayer.CUTOUT_MIPPED, BlockRenderLayer.TRANSLUCENT };
/*     */ 
/*     */   
/*     */   public BlockModelRenderer(BlockColors blockColorsIn) {
/*  43 */     this.blockColors = blockColorsIn;
/*     */     
/*  45 */     if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists())
/*     */     {
/*  47 */       Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder buffer, boolean checkSides) {
/*  53 */     return renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, buffer, checkSides, MathHelper.getPositionRandom((Vec3i)blockPosIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
/*  58 */     boolean flag = (Minecraft.isAmbientOcclusionEnabled() && ReflectorForge.getLightValue(stateIn, worldIn, posIn) == 0 && modelIn.isAmbientOcclusion());
/*     */ 
/*     */     
/*     */     try {
/*  62 */       if (Config.isShaders())
/*     */       {
/*  64 */         SVertexBuilder.pushEntity(stateIn, posIn, worldIn, buffer);
/*     */       }
/*     */       
/*  67 */       if (!Config.isAlternateBlocks())
/*     */       {
/*  69 */         rand = 0L;
/*     */       }
/*     */       
/*  72 */       RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn);
/*  73 */       modelIn = BlockModelCustomizer.getRenderModel(modelIn, stateIn, renderenv);
/*  74 */       boolean flag1 = flag ? renderModelSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand) : renderModelFlat(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);
/*     */       
/*  76 */       if (flag1)
/*     */       {
/*  78 */         renderOverlayModels(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand, renderenv, flag);
/*     */       }
/*     */       
/*  81 */       if (Config.isShaders())
/*     */       {
/*  83 */         SVertexBuilder.popEntity(buffer);
/*     */       }
/*     */       
/*  86 */       return flag1;
/*     */     }
/*  88 */     catch (Throwable throwable1) {
/*     */       
/*  90 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Tesselating block model");
/*  91 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
/*  92 */       CrashReportCategory.addBlockInfo(crashreportcategory, posIn, stateIn);
/*  93 */       crashreportcategory.addCrashSection("Using AO", Boolean.valueOf(flag));
/*  94 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
/* 100 */     boolean flag = false;
/* 101 */     RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 103 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 105 */       List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
/*     */       
/* 107 */       if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
/*     */         
/* 109 */         list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, enumfacing, rand, renderenv);
/* 110 */         renderQuadsSmooth(worldIn, stateIn, posIn, buffer, list, renderenv);
/* 111 */         flag = true;
/*     */       } 
/*     */       b++; }
/*     */     
/* 115 */     List<BakedQuad> list1 = modelIn.getQuads(stateIn, null, rand);
/*     */     
/* 117 */     if (!list1.isEmpty()) {
/*     */       
/* 119 */       list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, rand, renderenv);
/* 120 */       renderQuadsSmooth(worldIn, stateIn, posIn, buffer, list1, renderenv);
/* 121 */       flag = true;
/*     */     } 
/*     */     
/* 124 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelFlat(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
/* 129 */     boolean flag = false;
/* 130 */     RenderEnv renderenv = buffer.getRenderEnv(worldIn, stateIn, posIn); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 132 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 134 */       List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
/*     */       
/* 136 */       if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
/*     */         
/* 138 */         int j = stateIn.getPackedLightmapCoords(worldIn, posIn.offset(enumfacing));
/* 139 */         list = BlockModelCustomizer.getRenderQuads(list, worldIn, stateIn, posIn, enumfacing, rand, renderenv);
/* 140 */         renderQuadsFlat(worldIn, stateIn, posIn, j, false, buffer, list, renderenv);
/* 141 */         flag = true;
/*     */       } 
/*     */       b++; }
/*     */     
/* 145 */     List<BakedQuad> list1 = modelIn.getQuads(stateIn, null, rand);
/*     */     
/* 147 */     if (!list1.isEmpty()) {
/*     */       
/* 149 */       list1 = BlockModelCustomizer.getRenderQuads(list1, worldIn, stateIn, posIn, null, rand, renderenv);
/* 150 */       renderQuadsFlat(worldIn, stateIn, posIn, -1, true, buffer, list1, renderenv);
/* 151 */       flag = true;
/*     */     } 
/*     */     
/* 154 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderQuadsSmooth(IBlockAccess p_renderQuadsSmooth_1_, IBlockState p_renderQuadsSmooth_2_, BlockPos p_renderQuadsSmooth_3_, BufferBuilder p_renderQuadsSmooth_4_, List<BakedQuad> p_renderQuadsSmooth_5_, RenderEnv p_renderQuadsSmooth_6_) {
/* 159 */     float[] afloat = p_renderQuadsSmooth_6_.getQuadBounds();
/* 160 */     BitSet bitset = p_renderQuadsSmooth_6_.getBoundsFlags();
/* 161 */     AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderQuadsSmooth_6_.getAoFace();
/* 162 */     Vec3d vec3d = p_renderQuadsSmooth_2_.func_191059_e(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_);
/* 163 */     double d0 = p_renderQuadsSmooth_3_.getX() + vec3d.xCoord;
/* 164 */     double d1 = p_renderQuadsSmooth_3_.getY() + vec3d.yCoord;
/* 165 */     double d2 = p_renderQuadsSmooth_3_.getZ() + vec3d.zCoord;
/* 166 */     int i = 0;
/*     */     
/* 168 */     for (int j = p_renderQuadsSmooth_5_.size(); i < j; i++) {
/*     */       
/* 170 */       BakedQuad bakedquad = p_renderQuadsSmooth_5_.get(i);
/* 171 */       fillQuadBounds(p_renderQuadsSmooth_2_, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
/* 172 */       blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_3_, bakedquad.getFace(), afloat, bitset);
/*     */       
/* 174 */       if (p_renderQuadsSmooth_4_.isMultiTexture()) {
/*     */         
/* 176 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexDataSingle());
/* 177 */         p_renderQuadsSmooth_4_.putSprite(bakedquad.getSprite());
/*     */       }
/*     */       else {
/*     */         
/* 181 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 184 */       p_renderQuadsSmooth_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);
/*     */       
/* 186 */       if (bakedquad.shouldApplyDiffuseLighting()) {
/*     */         
/* 188 */         float f = FaceBakery.getFaceBrightness(bakedquad.getFace());
/* 189 */         float[] afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
/* 190 */         afloat1[0] = afloat1[0] * f;
/* 191 */         afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
/* 192 */         afloat1[1] = afloat1[1] * f;
/* 193 */         afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
/* 194 */         afloat1[2] = afloat1[2] * f;
/* 195 */         afloat1 = blockmodelrenderer$ambientocclusionface.vertexColorMultiplier;
/* 196 */         afloat1[3] = afloat1[3] * f;
/*     */       } 
/*     */       
/* 199 */       int l = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, p_renderQuadsSmooth_6_);
/*     */       
/* 201 */       if (!bakedquad.hasTintIndex() && l == -1) {
/*     */         
/* 203 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 204 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 205 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 206 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */       }
/*     */       else {
/*     */         
/* 210 */         int k = l;
/*     */         
/* 212 */         if (l == -1)
/*     */         {
/* 214 */           k = this.blockColors.colorMultiplier(p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, bakedquad.getTintIndex());
/*     */         }
/*     */         
/* 217 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 219 */           k = TextureUtil.anaglyphColor(k);
/*     */         }
/*     */         
/* 222 */         float f1 = (k >> 16 & 0xFF) / 255.0F;
/* 223 */         float f2 = (k >> 8 & 0xFF) / 255.0F;
/* 224 */         float f3 = (k & 0xFF) / 255.0F;
/* 225 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f3, 4);
/* 226 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f3, 3);
/* 227 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f3, 2);
/* 228 */         p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f3, 1);
/*     */       } 
/*     */       
/* 231 */       p_renderQuadsSmooth_4_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags) {
/* 237 */     float f = 32.0F;
/* 238 */     float f1 = 32.0F;
/* 239 */     float f2 = 32.0F;
/* 240 */     float f3 = -32.0F;
/* 241 */     float f4 = -32.0F;
/* 242 */     float f5 = -32.0F;
/* 243 */     int i = vertexData.length / 4;
/*     */     
/* 245 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 247 */       float f6 = Float.intBitsToFloat(vertexData[j * i]);
/* 248 */       float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
/* 249 */       float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
/* 250 */       f = Math.min(f, f6);
/* 251 */       f1 = Math.min(f1, f7);
/* 252 */       f2 = Math.min(f2, f8);
/* 253 */       f3 = Math.max(f3, f6);
/* 254 */       f4 = Math.max(f4, f7);
/* 255 */       f5 = Math.max(f5, f8);
/*     */     } 
/*     */     
/* 258 */     if (quadBounds != null) {
/*     */       
/* 260 */       quadBounds[EnumFacing.WEST.getIndex()] = f;
/* 261 */       quadBounds[EnumFacing.EAST.getIndex()] = f3;
/* 262 */       quadBounds[EnumFacing.DOWN.getIndex()] = f1;
/* 263 */       quadBounds[EnumFacing.UP.getIndex()] = f4;
/* 264 */       quadBounds[EnumFacing.NORTH.getIndex()] = f2;
/* 265 */       quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
/* 266 */       int k = EnumFacing.VALUES.length;
/* 267 */       quadBounds[EnumFacing.WEST.getIndex() + k] = 1.0F - f;
/* 268 */       quadBounds[EnumFacing.EAST.getIndex() + k] = 1.0F - f3;
/* 269 */       quadBounds[EnumFacing.DOWN.getIndex() + k] = 1.0F - f1;
/* 270 */       quadBounds[EnumFacing.UP.getIndex() + k] = 1.0F - f4;
/* 271 */       quadBounds[EnumFacing.NORTH.getIndex() + k] = 1.0F - f2;
/* 272 */       quadBounds[EnumFacing.SOUTH.getIndex() + k] = 1.0F - f5;
/*     */     } 
/*     */     
/* 275 */     float f9 = 1.0E-4F;
/* 276 */     float f10 = 0.9999F;
/*     */     
/* 278 */     switch (face) {
/*     */       
/*     */       case null:
/* 281 */         boundsFlags.set(1, !(f < 1.0E-4F && f2 < 1.0E-4F && f3 > 0.9999F && f5 > 0.9999F));
/* 282 */         boundsFlags.set(0, ((f1 < 1.0E-4F || stateIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case UP:
/* 286 */         boundsFlags.set(1, !(f < 1.0E-4F && f2 < 1.0E-4F && f3 > 0.9999F && f5 > 0.9999F));
/* 287 */         boundsFlags.set(0, ((f4 > 0.9999F || stateIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 291 */         boundsFlags.set(1, !(f < 1.0E-4F && f1 < 1.0E-4F && f3 > 0.9999F && f4 > 0.9999F));
/* 292 */         boundsFlags.set(0, ((f2 < 1.0E-4F || stateIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 296 */         boundsFlags.set(1, !(f < 1.0E-4F && f1 < 1.0E-4F && f3 > 0.9999F && f4 > 0.9999F));
/* 297 */         boundsFlags.set(0, ((f5 > 0.9999F || stateIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 301 */         boundsFlags.set(1, !(f1 < 1.0E-4F && f2 < 1.0E-4F && f4 > 0.9999F && f5 > 0.9999F));
/* 302 */         boundsFlags.set(0, ((f < 1.0E-4F || stateIn.isFullCube()) && f == f3));
/*     */         break;
/*     */       
/*     */       case EAST:
/* 306 */         boundsFlags.set(1, !(f1 < 1.0E-4F && f2 < 1.0E-4F && f4 > 0.9999F && f5 > 0.9999F));
/* 307 */         boundsFlags.set(0, ((f3 > 0.9999F || stateIn.isFullCube()) && f == f3));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderQuadsFlat(IBlockAccess p_renderQuadsFlat_1_, IBlockState p_renderQuadsFlat_2_, BlockPos p_renderQuadsFlat_3_, int p_renderQuadsFlat_4_, boolean p_renderQuadsFlat_5_, BufferBuilder p_renderQuadsFlat_6_, List<BakedQuad> p_renderQuadsFlat_7_, RenderEnv p_renderQuadsFlat_8_) {
/* 313 */     BitSet bitset = p_renderQuadsFlat_8_.getBoundsFlags();
/* 314 */     Vec3d vec3d = p_renderQuadsFlat_2_.func_191059_e(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_);
/* 315 */     double d0 = p_renderQuadsFlat_3_.getX() + vec3d.xCoord;
/* 316 */     double d1 = p_renderQuadsFlat_3_.getY() + vec3d.yCoord;
/* 317 */     double d2 = p_renderQuadsFlat_3_.getZ() + vec3d.zCoord;
/* 318 */     int i = 0;
/*     */     
/* 320 */     for (int j = p_renderQuadsFlat_7_.size(); i < j; i++) {
/*     */       
/* 322 */       BakedQuad bakedquad = p_renderQuadsFlat_7_.get(i);
/*     */       
/* 324 */       if (p_renderQuadsFlat_5_) {
/*     */         
/* 326 */         fillQuadBounds(p_renderQuadsFlat_2_, bakedquad.getVertexData(), bakedquad.getFace(), null, bitset);
/* 327 */         BlockPos blockpos = bitset.get(0) ? p_renderQuadsFlat_3_.offset(bakedquad.getFace()) : p_renderQuadsFlat_3_;
/* 328 */         p_renderQuadsFlat_4_ = p_renderQuadsFlat_2_.getPackedLightmapCoords(p_renderQuadsFlat_1_, blockpos);
/*     */       } 
/*     */       
/* 331 */       if (p_renderQuadsFlat_6_.isMultiTexture()) {
/*     */         
/* 333 */         p_renderQuadsFlat_6_.addVertexData(bakedquad.getVertexDataSingle());
/* 334 */         p_renderQuadsFlat_6_.putSprite(bakedquad.getSprite());
/*     */       }
/*     */       else {
/*     */         
/* 338 */         p_renderQuadsFlat_6_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 341 */       p_renderQuadsFlat_6_.putBrightness4(p_renderQuadsFlat_4_, p_renderQuadsFlat_4_, p_renderQuadsFlat_4_, p_renderQuadsFlat_4_);
/* 342 */       int l = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, p_renderQuadsFlat_8_);
/*     */       
/* 344 */       if (!bakedquad.hasTintIndex() && l == -1) {
/*     */         
/* 346 */         if (bakedquad.shouldApplyDiffuseLighting())
/*     */         {
/* 348 */           float f4 = FaceBakery.getFaceBrightness(bakedquad.getFace());
/* 349 */           p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 4);
/* 350 */           p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 3);
/* 351 */           p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 2);
/* 352 */           p_renderQuadsFlat_6_.putColorMultiplier(f4, f4, f4, 1);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 357 */         int k = l;
/*     */         
/* 359 */         if (l == -1)
/*     */         {
/* 361 */           k = this.blockColors.colorMultiplier(p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, bakedquad.getTintIndex());
/*     */         }
/*     */         
/* 364 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 366 */           k = TextureUtil.anaglyphColor(k);
/*     */         }
/*     */         
/* 369 */         float f = (k >> 16 & 0xFF) / 255.0F;
/* 370 */         float f1 = (k >> 8 & 0xFF) / 255.0F;
/* 371 */         float f2 = (k & 0xFF) / 255.0F;
/*     */         
/* 373 */         if (bakedquad.shouldApplyDiffuseLighting()) {
/*     */           
/* 375 */           float f3 = FaceBakery.getFaceBrightness(bakedquad.getFace());
/* 376 */           f *= f3;
/* 377 */           f1 *= f3;
/* 378 */           f2 *= f3;
/*     */         } 
/*     */         
/* 381 */         p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 4);
/* 382 */         p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 3);
/* 383 */         p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 2);
/* 384 */         p_renderQuadsFlat_6_.putColorMultiplier(f, f1, f2, 1);
/*     */       } 
/*     */       
/* 387 */       p_renderQuadsFlat_6_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float red, float green, float blue) {
/* 393 */     renderModelBrightnessColor(null, bakedModel, p_178262_2_, red, green, blue);
/*     */   } public void renderModelBrightnessColor(IBlockState state, IBakedModel p_187495_2_, float p_187495_3_, float p_187495_4_, float p_187495_5_, float p_187495_6_) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 398 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 400 */       renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.getQuads(state, enumfacing, 0L));
/*     */       b++; }
/*     */     
/* 403 */     renderModelBrightnessColorQuads(p_187495_3_, p_187495_4_, p_187495_5_, p_187495_6_, p_187495_2_.getQuads(state, null, 0L));
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightness(IBakedModel model, IBlockState state, float brightness, boolean p_178266_4_) {
/* 408 */     Block block = state.getBlock();
/* 409 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 410 */     int i = this.blockColors.colorMultiplier(state, null, null, 0);
/*     */     
/* 412 */     if (EntityRenderer.anaglyphEnable)
/*     */     {
/* 414 */       i = TextureUtil.anaglyphColor(i);
/*     */     }
/*     */     
/* 417 */     float f = (i >> 16 & 0xFF) / 255.0F;
/* 418 */     float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 419 */     float f2 = (i & 0xFF) / 255.0F;
/*     */     
/* 421 */     if (!p_178266_4_)
/*     */     {
/* 423 */       GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */     }
/*     */     
/* 426 */     renderModelBrightnessColor(state, model, brightness, f, f1, f2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue, List<BakedQuad> listQuads) {
/* 431 */     Tessellator tessellator = Tessellator.getInstance();
/* 432 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 433 */     int i = 0;
/*     */     
/* 435 */     for (int j = listQuads.size(); i < j; i++) {
/*     */       
/* 437 */       BakedQuad bakedquad = listQuads.get(i);
/* 438 */       bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
/* 439 */       bufferbuilder.addVertexData(bakedquad.getVertexData());
/*     */       
/* 441 */       if (bakedquad.hasTintIndex()) {
/*     */         
/* 443 */         bufferbuilder.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
/*     */       }
/*     */       else {
/*     */         
/* 447 */         bufferbuilder.putColorRGB_F4(brightness, brightness, brightness);
/*     */       } 
/*     */       
/* 450 */       Vec3i vec3i = bakedquad.getFace().getDirectionVec();
/* 451 */       bufferbuilder.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 452 */       tessellator.draw();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float fixAoLightValue(float p_fixAoLightValue_0_) {
/* 458 */     return (p_fixAoLightValue_0_ == 0.2F) ? aoLightValueOpaque : p_fixAoLightValue_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAoLightValue() {
/* 463 */     aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderOverlayModels(IBlockAccess p_renderOverlayModels_1_, IBakedModel p_renderOverlayModels_2_, IBlockState p_renderOverlayModels_3_, BlockPos p_renderOverlayModels_4_, BufferBuilder p_renderOverlayModels_5_, boolean p_renderOverlayModels_6_, long p_renderOverlayModels_7_, RenderEnv p_renderOverlayModels_9_, boolean p_renderOverlayModels_10_) {
/* 468 */     if (p_renderOverlayModels_9_.isOverlaysRendered())
/*     */     {
/* 470 */       for (int i = 0; i < OVERLAY_LAYERS.length; i++) {
/*     */         
/* 472 */         BlockRenderLayer blockrenderlayer = OVERLAY_LAYERS[i];
/* 473 */         ListQuadsOverlay listquadsoverlay = p_renderOverlayModels_9_.getListQuadsOverlay(blockrenderlayer);
/*     */         
/* 475 */         if (listquadsoverlay.size() > 0) {
/*     */           
/* 477 */           RegionRenderCacheBuilder regionrendercachebuilder = p_renderOverlayModels_9_.getRegionRenderCacheBuilder();
/*     */           
/* 479 */           if (regionrendercachebuilder != null) {
/*     */             
/* 481 */             BufferBuilder bufferbuilder = regionrendercachebuilder.getWorldRendererByLayer(blockrenderlayer);
/*     */             
/* 483 */             if (!bufferbuilder.isDrawing()) {
/*     */               
/* 485 */               bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
/* 486 */               bufferbuilder.setTranslation(p_renderOverlayModels_5_.getXOffset(), p_renderOverlayModels_5_.getYOffset(), p_renderOverlayModels_5_.getZOffset());
/*     */             } 
/*     */             
/* 489 */             for (int j = 0; j < listquadsoverlay.size(); j++) {
/*     */               
/* 491 */               BakedQuad bakedquad = listquadsoverlay.getQuad(j);
/* 492 */               List<BakedQuad> list = listquadsoverlay.getListQuadsSingle(bakedquad);
/* 493 */               IBlockState iblockstate = listquadsoverlay.getBlockState(j);
/* 494 */               p_renderOverlayModels_9_.reset(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_);
/*     */               
/* 496 */               if (p_renderOverlayModels_10_) {
/*     */                 
/* 498 */                 renderQuadsSmooth(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, bufferbuilder, list, p_renderOverlayModels_9_);
/*     */               }
/*     */               else {
/*     */                 
/* 502 */                 int k = iblockstate.getPackedLightmapCoords(p_renderOverlayModels_1_, p_renderOverlayModels_4_.offset(bakedquad.getFace()));
/* 503 */                 renderQuadsFlat(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, k, false, bufferbuilder, list, p_renderOverlayModels_9_);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 508 */           listquadsoverlay.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 513 */     if (Config.isBetterSnow() && !p_renderOverlayModels_9_.isBreakingAnimation() && BetterSnow.shouldRender(p_renderOverlayModels_1_, p_renderOverlayModels_3_, p_renderOverlayModels_4_)) {
/*     */       
/* 515 */       IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
/* 516 */       IBlockState iblockstate1 = BetterSnow.getStateSnowLayer();
/* 517 */       renderModel(p_renderOverlayModels_1_, ibakedmodel, iblockstate1, p_renderOverlayModels_4_, p_renderOverlayModels_5_, p_renderOverlayModels_6_, p_renderOverlayModels_7_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class AmbientOcclusionFace
/*     */   {
/* 523 */     private final float[] vertexColorMultiplier = new float[4];
/* 524 */     private final int[] vertexBrightness = new int[4];
/*     */ 
/*     */     
/*     */     public void updateVertexBrightness(IBlockAccess worldIn, IBlockState state, BlockPos centerPos, EnumFacing direction, float[] faceShape, BitSet shapeState) {
/*     */       int i1;
/*     */       float f25;
/*     */       int j1;
/*     */       float f26;
/*     */       int k1;
/*     */       float f27;
/*     */       int l1;
/*     */       float f28;
/* 536 */       BlockPos blockpos = shapeState.get(0) ? centerPos.offset(direction) : centerPos;
/* 537 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/* 538 */       BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(direction);
/* 539 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain((Vec3i)blockpos).move(blockmodelrenderer$enumneighborinfo.corners[0]);
/* 540 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain((Vec3i)blockpos).move(blockmodelrenderer$enumneighborinfo.corners[1]);
/* 541 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos3 = BlockPos.PooledMutableBlockPos.retain((Vec3i)blockpos).move(blockmodelrenderer$enumneighborinfo.corners[2]);
/* 542 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos4 = BlockPos.PooledMutableBlockPos.retain((Vec3i)blockpos).move(blockmodelrenderer$enumneighborinfo.corners[3]);
/* 543 */       int i = state.getPackedLightmapCoords(worldIn, (BlockPos)blockpos$pooledmutableblockpos1);
/* 544 */       int j = state.getPackedLightmapCoords(worldIn, (BlockPos)blockpos$pooledmutableblockpos2);
/* 545 */       int k = state.getPackedLightmapCoords(worldIn, (BlockPos)blockpos$pooledmutableblockpos3);
/* 546 */       int l = state.getPackedLightmapCoords(worldIn, (BlockPos)blockpos$pooledmutableblockpos4);
/* 547 */       float f = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos1).getAmbientOcclusionLightValue();
/* 548 */       float f1 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos2).getAmbientOcclusionLightValue();
/* 549 */       float f2 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos3).getAmbientOcclusionLightValue();
/* 550 */       float f3 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos4).getAmbientOcclusionLightValue();
/* 551 */       f = BlockModelRenderer.fixAoLightValue(f);
/* 552 */       f1 = BlockModelRenderer.fixAoLightValue(f1);
/* 553 */       f2 = BlockModelRenderer.fixAoLightValue(f2);
/* 554 */       f3 = BlockModelRenderer.fixAoLightValue(f3);
/* 555 */       boolean flag = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos1).move(direction)).isTranslucent();
/* 556 */       boolean flag1 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos2).move(direction)).isTranslucent();
/* 557 */       boolean flag2 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos3).move(direction)).isTranslucent();
/* 558 */       boolean flag3 = worldIn.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos4).move(direction)).isTranslucent();
/*     */ 
/*     */ 
/*     */       
/* 562 */       if (!flag2 && !flag) {
/*     */         
/* 564 */         f25 = f;
/* 565 */         i1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 569 */         BlockPos.PooledMutableBlockPos pooledMutableBlockPos = blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos1).move(blockmodelrenderer$enumneighborinfo.corners[2]);
/* 570 */         f25 = worldIn.getBlockState((BlockPos)pooledMutableBlockPos).getAmbientOcclusionLightValue();
/* 571 */         f25 = BlockModelRenderer.fixAoLightValue(f25);
/* 572 */         i1 = state.getPackedLightmapCoords(worldIn, (BlockPos)pooledMutableBlockPos);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 578 */       if (!flag3 && !flag) {
/*     */         
/* 580 */         f26 = f;
/* 581 */         j1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 585 */         BlockPos.PooledMutableBlockPos pooledMutableBlockPos = blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos1).move(blockmodelrenderer$enumneighborinfo.corners[3]);
/* 586 */         f26 = worldIn.getBlockState((BlockPos)pooledMutableBlockPos).getAmbientOcclusionLightValue();
/* 587 */         f26 = BlockModelRenderer.fixAoLightValue(f26);
/* 588 */         j1 = state.getPackedLightmapCoords(worldIn, (BlockPos)pooledMutableBlockPos);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 594 */       if (!flag2 && !flag1) {
/*     */         
/* 596 */         f27 = f1;
/* 597 */         k1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 601 */         BlockPos.PooledMutableBlockPos pooledMutableBlockPos = blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos2).move(blockmodelrenderer$enumneighborinfo.corners[2]);
/* 602 */         f27 = worldIn.getBlockState((BlockPos)pooledMutableBlockPos).getAmbientOcclusionLightValue();
/* 603 */         f27 = BlockModelRenderer.fixAoLightValue(f27);
/* 604 */         k1 = state.getPackedLightmapCoords(worldIn, (BlockPos)pooledMutableBlockPos);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 610 */       if (!flag3 && !flag1) {
/*     */         
/* 612 */         f28 = f1;
/* 613 */         l1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 617 */         BlockPos.PooledMutableBlockPos pooledMutableBlockPos = blockpos$pooledmutableblockpos.setPos((Vec3i)blockpos$pooledmutableblockpos2).move(blockmodelrenderer$enumneighborinfo.corners[3]);
/* 618 */         f28 = worldIn.getBlockState((BlockPos)pooledMutableBlockPos).getAmbientOcclusionLightValue();
/* 619 */         f28 = BlockModelRenderer.fixAoLightValue(f28);
/* 620 */         l1 = state.getPackedLightmapCoords(worldIn, (BlockPos)pooledMutableBlockPos);
/*     */       } 
/*     */       
/* 623 */       int i3 = state.getPackedLightmapCoords(worldIn, centerPos);
/*     */       
/* 625 */       if (shapeState.get(0) || !worldIn.getBlockState(centerPos.offset(direction)).isOpaqueCube())
/*     */       {
/* 627 */         i3 = state.getPackedLightmapCoords(worldIn, centerPos.offset(direction));
/*     */       }
/*     */       
/* 630 */       float f4 = shapeState.get(0) ? worldIn.getBlockState(blockpos).getAmbientOcclusionLightValue() : worldIn.getBlockState(centerPos).getAmbientOcclusionLightValue();
/* 631 */       f4 = BlockModelRenderer.fixAoLightValue(f4);
/* 632 */       BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(direction);
/* 633 */       blockpos$pooledmutableblockpos.release();
/* 634 */       blockpos$pooledmutableblockpos1.release();
/* 635 */       blockpos$pooledmutableblockpos2.release();
/* 636 */       blockpos$pooledmutableblockpos3.release();
/* 637 */       blockpos$pooledmutableblockpos4.release();
/*     */       
/* 639 */       if (shapeState.get(1) && blockmodelrenderer$enumneighborinfo.doNonCubicWeight) {
/*     */         
/* 641 */         float f29 = (f3 + f + f26 + f4) * 0.25F;
/* 642 */         float f30 = (f2 + f + f25 + f4) * 0.25F;
/* 643 */         float f31 = (f2 + f1 + f27 + f4) * 0.25F;
/* 644 */         float f32 = (f3 + f1 + f28 + f4) * 0.25F;
/* 645 */         float f9 = faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[0]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[1]).shape];
/* 646 */         float f10 = faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[2]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[3]).shape];
/* 647 */         float f11 = faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[4]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[5]).shape];
/* 648 */         float f12 = faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[6]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert0Weights[7]).shape];
/* 649 */         float f13 = faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[0]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[1]).shape];
/* 650 */         float f14 = faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[2]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[3]).shape];
/* 651 */         float f15 = faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[4]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[5]).shape];
/* 652 */         float f16 = faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[6]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert1Weights[7]).shape];
/* 653 */         float f17 = faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[0]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[1]).shape];
/* 654 */         float f18 = faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[2]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[3]).shape];
/* 655 */         float f19 = faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[4]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[5]).shape];
/* 656 */         float f20 = faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[6]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert2Weights[7]).shape];
/* 657 */         float f21 = faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[0]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[1]).shape];
/* 658 */         float f22 = faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[2]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[3]).shape];
/* 659 */         float f23 = faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[4]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[5]).shape];
/* 660 */         float f24 = faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[6]).shape] * faceShape[(blockmodelrenderer$enumneighborinfo.vert3Weights[7]).shape];
/* 661 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f29 * f9 + f30 * f10 + f31 * f11 + f32 * f12;
/* 662 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f29 * f13 + f30 * f14 + f31 * f15 + f32 * f16;
/* 663 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f29 * f17 + f30 * f18 + f31 * f19 + f32 * f20;
/* 664 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f29 * f21 + f30 * f22 + f31 * f23 + f32 * f24;
/* 665 */         int i2 = getAoBrightness(l, i, j1, i3);
/* 666 */         int j2 = getAoBrightness(k, i, i1, i3);
/* 667 */         int k2 = getAoBrightness(k, j, k1, i3);
/* 668 */         int l2 = getAoBrightness(l, j, l1, i3);
/* 669 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = getVertexBrightness(i2, j2, k2, l2, f9, f10, f11, f12);
/* 670 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = getVertexBrightness(i2, j2, k2, l2, f13, f14, f15, f16);
/* 671 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = getVertexBrightness(i2, j2, k2, l2, f17, f18, f19, f20);
/* 672 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = getVertexBrightness(i2, j2, k2, l2, f21, f22, f23, f24);
/*     */       }
/*     */       else {
/*     */         
/* 676 */         float f5 = (f3 + f + f26 + f4) * 0.25F;
/* 677 */         float f6 = (f2 + f + f25 + f4) * 0.25F;
/* 678 */         float f7 = (f2 + f1 + f27 + f4) * 0.25F;
/* 679 */         float f8 = (f3 + f1 + f28 + f4) * 0.25F;
/* 680 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert0] = getAoBrightness(l, i, j1, i3);
/* 681 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert1] = getAoBrightness(k, i, i1, i3);
/* 682 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert2] = getAoBrightness(k, j, k1, i3);
/* 683 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.vert3] = getAoBrightness(l, j, l1, i3);
/* 684 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert0] = f5;
/* 685 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert1] = f6;
/* 686 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert2] = f7;
/* 687 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.vert3] = f8;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private int getAoBrightness(int br1, int br2, int br3, int br4) {
/* 693 */       if (br1 == 0)
/*     */       {
/* 695 */         br1 = br4;
/*     */       }
/*     */       
/* 698 */       if (br2 == 0)
/*     */       {
/* 700 */         br2 = br4;
/*     */       }
/*     */       
/* 703 */       if (br3 == 0)
/*     */       {
/* 705 */         br3 = br4;
/*     */       }
/*     */       
/* 708 */       return br1 + br2 + br3 + br4 >> 2 & 0xFF00FF;
/*     */     }
/*     */     public AmbientOcclusionFace() {}
/*     */     
/*     */     private int getVertexBrightness(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
/* 713 */       int i = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
/* 714 */       int j = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
/* 715 */       return i << 16 | j;
/*     */     }
/*     */     
/*     */     public AmbientOcclusionFace(BlockModelRenderer p_i46235_1_) {} }
/*     */   
/*     */   public enum EnumNeighborInfo {
/* 721 */     DOWN((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH }),
/* 722 */     UP((String)new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH }, 1.0F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.SOUTH }),
/* 723 */     NORTH((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST }),
/* 724 */     SOUTH((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST }),
/* 725 */     WEST((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH }),
/* 726 */     EAST((String)new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH });
/*     */     
/*     */     private final EnumFacing[] corners;
/*     */     private final float shadeWeight;
/*     */     private final boolean doNonCubicWeight;
/*     */     private final BlockModelRenderer.Orientation[] vert0Weights;
/*     */     private final BlockModelRenderer.Orientation[] vert1Weights;
/*     */     private final BlockModelRenderer.Orientation[] vert2Weights;
/*     */     private final BlockModelRenderer.Orientation[] vert3Weights;
/* 735 */     private static final EnumNeighborInfo[] VALUES = new EnumNeighborInfo[6];
/*     */ 
/*     */     
/*     */     EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_) {
/*     */       this.corners = p_i46236_3_;
/*     */       this.shadeWeight = p_i46236_4_;
/*     */       this.doNonCubicWeight = p_i46236_5_;
/*     */       this.vert0Weights = p_i46236_6_;
/*     */       this.vert1Weights = p_i46236_7_;
/*     */       this.vert2Weights = p_i46236_8_;
/*     */       this.vert3Weights = p_i46236_9_;
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumNeighborInfo getNeighbourInfo(EnumFacing p_178273_0_) {
/*     */       return VALUES[p_178273_0_.getIndex()];
/*     */     }
/*     */     
/*     */     static {
/* 754 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 755 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 756 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 757 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 758 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 759 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Orientation
/*     */   {
/* 765 */     DOWN((String)EnumFacing.DOWN, false),
/* 766 */     UP((String)EnumFacing.UP, false),
/* 767 */     NORTH((String)EnumFacing.NORTH, false),
/* 768 */     SOUTH((String)EnumFacing.SOUTH, false),
/* 769 */     WEST((String)EnumFacing.WEST, false),
/* 770 */     EAST((String)EnumFacing.EAST, false),
/* 771 */     FLIP_DOWN((String)EnumFacing.DOWN, true),
/* 772 */     FLIP_UP((String)EnumFacing.UP, true),
/* 773 */     FLIP_NORTH((String)EnumFacing.NORTH, true),
/* 774 */     FLIP_SOUTH((String)EnumFacing.SOUTH, true),
/* 775 */     FLIP_WEST((String)EnumFacing.WEST, true),
/* 776 */     FLIP_EAST((String)EnumFacing.EAST, true);
/*     */     
/*     */     private final int shape;
/*     */ 
/*     */     
/*     */     Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_) {
/* 782 */       this.shape = p_i46233_3_.getIndex() + (p_i46233_4_ ? (EnumFacing.values()).length : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   enum VertexTranslations
/*     */   {
/* 788 */     DOWN(0, 1, 2, 3),
/* 789 */     UP(2, 3, 0, 1),
/* 790 */     NORTH(3, 0, 1, 2),
/* 791 */     SOUTH(0, 1, 2, 3),
/* 792 */     WEST(3, 0, 1, 2),
/* 793 */     EAST(1, 2, 3, 0);
/*     */     
/*     */     private final int vert0;
/*     */     private final int vert1;
/*     */     private final int vert2;
/*     */     private final int vert3;
/* 799 */     private static final VertexTranslations[] VALUES = new VertexTranslations[6];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 815 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 816 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 817 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 818 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 819 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 820 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     
/*     */     VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
/*     */       this.vert0 = p_i46234_3_;
/*     */       this.vert1 = p_i46234_4_;
/*     */       this.vert2 = p_i46234_5_;
/*     */       this.vert3 = p_i46234_6_;
/*     */     }
/*     */     
/*     */     public static VertexTranslations getVertexTranslations(EnumFacing p_178184_0_) {
/*     */       return VALUES[p_178184_0_.getIndex()];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
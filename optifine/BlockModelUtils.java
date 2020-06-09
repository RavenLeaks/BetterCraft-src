/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
/*     */ import net.minecraft.client.renderer.block.model.BlockFaceUV;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartRotation;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*     */ import net.minecraft.client.renderer.block.model.ModelManager;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.block.model.ModelRotation;
/*     */ import net.minecraft.client.renderer.block.model.SimpleBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockModelUtils
/*     */ {
/*     */   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
/*     */   
/*     */   public static IBakedModel makeModelCube(String p_makeModelCube_0_, int p_makeModelCube_1_) {
/*  35 */     TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(p_makeModelCube_0_);
/*  36 */     return makeModelCube(textureatlassprite, p_makeModelCube_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModelCube(TextureAtlasSprite p_makeModelCube_0_, int p_makeModelCube_1_) {
/*  41 */     List list = new ArrayList();
/*  42 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  43 */     Map<EnumFacing, List<BakedQuad>> map = new HashMap<>();
/*     */     
/*  45 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  47 */       EnumFacing enumfacing = aenumfacing[i];
/*  48 */       List<BakedQuad> list1 = new ArrayList();
/*  49 */       list1.add(makeBakedQuad(enumfacing, p_makeModelCube_0_, p_makeModelCube_1_));
/*  50 */       map.put(enumfacing, list1);
/*     */     } 
/*     */     
/*  53 */     ItemOverrideList itemoverridelist = new ItemOverrideList(new ArrayList());
/*  54 */     return (IBakedModel)new SimpleBakedModel(list, map, true, true, p_makeModelCube_0_, ItemCameraTransforms.DEFAULT, itemoverridelist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IBakedModel joinModelsCube(IBakedModel p_joinModelsCube_0_, IBakedModel p_joinModelsCube_1_) {
/*  60 */     List<BakedQuad> list = new ArrayList<>();
/*  61 */     list.addAll(p_joinModelsCube_0_.getQuads(null, null, 0L));
/*  62 */     list.addAll(p_joinModelsCube_1_.getQuads(null, null, 0L));
/*  63 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  64 */     Map<EnumFacing, List<BakedQuad>> map = new HashMap<>();
/*     */     
/*  66 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  68 */       EnumFacing enumfacing = aenumfacing[i];
/*  69 */       List<BakedQuad> list1 = new ArrayList();
/*  70 */       list1.addAll(p_joinModelsCube_0_.getQuads(null, enumfacing, 0L));
/*  71 */       list1.addAll(p_joinModelsCube_1_.getQuads(null, enumfacing, 0L));
/*  72 */       map.put(enumfacing, list1);
/*     */     } 
/*     */     
/*  75 */     boolean flag = p_joinModelsCube_0_.isAmbientOcclusion();
/*  76 */     boolean flag1 = p_joinModelsCube_0_.isBuiltInRenderer();
/*  77 */     TextureAtlasSprite textureatlassprite = p_joinModelsCube_0_.getParticleTexture();
/*  78 */     ItemCameraTransforms itemcameratransforms = p_joinModelsCube_0_.getItemCameraTransforms();
/*  79 */     ItemOverrideList itemoverridelist = p_joinModelsCube_0_.getOverrides();
/*  80 */     return (IBakedModel)new SimpleBakedModel(list, map, flag, flag1, textureatlassprite, itemcameratransforms, itemoverridelist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BakedQuad makeBakedQuad(EnumFacing p_makeBakedQuad_0_, TextureAtlasSprite p_makeBakedQuad_1_, int p_makeBakedQuad_2_) {
/*  86 */     Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*  87 */     Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
/*  88 */     BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
/*  89 */     BlockPartFace blockpartface = new BlockPartFace(p_makeBakedQuad_0_, p_makeBakedQuad_2_, "#" + p_makeBakedQuad_0_.getName(), blockfaceuv);
/*  90 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  91 */     BlockPartRotation blockpartrotation = null;
/*  92 */     boolean flag = false;
/*  93 */     boolean flag1 = true;
/*  94 */     FaceBakery facebakery = new FaceBakery();
/*  95 */     BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, p_makeBakedQuad_1_, p_makeBakedQuad_0_, modelrotation, blockpartrotation, flag, flag1);
/*  96 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String p_makeModel_0_, String p_makeModel_1_, String p_makeModel_2_) {
/* 101 */     TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
/* 102 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(p_makeModel_1_);
/* 103 */     TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(p_makeModel_2_);
/* 104 */     return makeModel(p_makeModel_0_, textureatlassprite, textureatlassprite1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel makeModel(String p_makeModel_0_, TextureAtlasSprite p_makeModel_1_, TextureAtlasSprite p_makeModel_2_) {
/* 109 */     if (p_makeModel_1_ != null && p_makeModel_2_ != null) {
/*     */       
/* 111 */       ModelManager modelmanager = Config.getModelManager();
/*     */       
/* 113 */       if (modelmanager == null)
/*     */       {
/* 115 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 119 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(p_makeModel_0_, "normal");
/* 120 */       IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */       
/* 122 */       if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */         
/* 124 */         IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
/* 125 */         EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */         
/* 127 */         for (int i = 0; i < aenumfacing.length; i++) {
/*     */           
/* 129 */           EnumFacing enumfacing = aenumfacing[i];
/* 130 */           List<BakedQuad> list = ibakedmodel1.getQuads(null, enumfacing, 0L);
/* 131 */           replaceTexture(list, p_makeModel_1_, p_makeModel_2_);
/*     */         } 
/*     */         
/* 134 */         List<BakedQuad> list1 = ibakedmodel1.getQuads(null, null, 0L);
/* 135 */         replaceTexture(list1, p_makeModel_1_, p_makeModel_2_);
/* 136 */         return ibakedmodel1;
/*     */       } 
/*     */ 
/*     */       
/* 140 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceTexture(List<BakedQuad> p_replaceTexture_0_, TextureAtlasSprite p_replaceTexture_1_, TextureAtlasSprite p_replaceTexture_2_) {
/* 152 */     List<BakedQuad> list = new ArrayList<>();
/*     */     
/* 154 */     for (BakedQuad bakedquad : p_replaceTexture_0_) {
/*     */       
/* 156 */       if (bakedquad.getSprite() != p_replaceTexture_1_) {
/*     */         
/* 158 */         list.add(bakedquad);
/*     */         
/*     */         break;
/*     */       } 
/* 162 */       BakedQuadRetextured bakedQuadRetextured = new BakedQuadRetextured(bakedquad, p_replaceTexture_2_);
/* 163 */       list.add(bakedQuadRetextured);
/*     */     } 
/*     */     
/* 166 */     p_replaceTexture_0_.clear();
/* 167 */     p_replaceTexture_0_.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void snapVertexPosition(Vector3f p_snapVertexPosition_0_) {
/* 172 */     p_snapVertexPosition_0_.setX(snapVertexCoord(p_snapVertexPosition_0_.getX()));
/* 173 */     p_snapVertexPosition_0_.setY(snapVertexCoord(p_snapVertexPosition_0_.getY()));
/* 174 */     p_snapVertexPosition_0_.setZ(snapVertexCoord(p_snapVertexPosition_0_.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float snapVertexCoord(float p_snapVertexCoord_0_) {
/* 179 */     if (p_snapVertexCoord_0_ > -1.0E-6F && p_snapVertexCoord_0_ < 1.0E-6F)
/*     */     {
/* 181 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 185 */     return (p_snapVertexCoord_0_ > 0.999999F && p_snapVertexCoord_0_ < 1.000001F) ? 1.0F : p_snapVertexCoord_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB p_getOffsetBoundingBox_0_, Block.EnumOffsetType p_getOffsetBoundingBox_1_, BlockPos p_getOffsetBoundingBox_2_) {
/* 191 */     int i = p_getOffsetBoundingBox_2_.getX();
/* 192 */     int j = p_getOffsetBoundingBox_2_.getZ();
/* 193 */     long k = (i * 3129871) ^ j * 116129781L;
/* 194 */     k = k * k * 42317861L + k * 11L;
/* 195 */     double d0 = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 196 */     double d1 = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 197 */     double d2 = 0.0D;
/*     */     
/* 199 */     if (p_getOffsetBoundingBox_1_ == Block.EnumOffsetType.XYZ)
/*     */     {
/* 201 */       d2 = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */     }
/*     */     
/* 204 */     return p_getOffsetBoundingBox_0_.offset(d0, d2, d1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BlockModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
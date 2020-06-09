/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.EnumFaceDirection;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.common.model.ITransformation;
/*     */ import optifine.BlockModelUtils;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ import org.lwjgl.util.vector.ReadableVector3f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public class FaceBakery
/*     */ {
/*  22 */   private static final float SCALE_ROTATION_22_5 = 1.0F / (float)Math.cos(0.39269909262657166D) - 1.0F;
/*  23 */   private static final float SCALE_ROTATION_GENERAL = 1.0F / (float)Math.cos(0.7853981633974483D) - 1.0F;
/*  24 */   private static final Rotation[] UV_ROTATIONS = new Rotation[(ModelRotation.values()).length * (EnumFacing.values()).length];
/*  25 */   private static final Rotation UV_ROTATION_0 = new Rotation()
/*     */     {
/*     */       BlockFaceUV makeRotatedUV(float p_188007_1_, float p_188007_2_, float p_188007_3_, float p_188007_4_)
/*     */       {
/*  29 */         return new BlockFaceUV(new float[] { p_188007_1_, p_188007_2_, p_188007_3_, p_188007_4_ }, 0);
/*     */       }
/*     */     };
/*  32 */   private static final Rotation UV_ROTATION_270 = new Rotation()
/*     */     {
/*     */       BlockFaceUV makeRotatedUV(float p_188007_1_, float p_188007_2_, float p_188007_3_, float p_188007_4_)
/*     */       {
/*  36 */         return new BlockFaceUV(new float[] { p_188007_4_, 16.0F - p_188007_1_, p_188007_2_, 16.0F - p_188007_3_ }, 270);
/*     */       }
/*     */     };
/*  39 */   private static final Rotation UV_ROTATION_INVERSE = new Rotation()
/*     */     {
/*     */       BlockFaceUV makeRotatedUV(float p_188007_1_, float p_188007_2_, float p_188007_3_, float p_188007_4_)
/*     */       {
/*  43 */         return new BlockFaceUV(new float[] { 16.0F - p_188007_1_, 16.0F - p_188007_2_, 16.0F - p_188007_3_, 16.0F - p_188007_4_ }, 0);
/*     */       }
/*     */     };
/*  46 */   private static final Rotation UV_ROTATION_90 = new Rotation()
/*     */     {
/*     */       BlockFaceUV makeRotatedUV(float p_188007_1_, float p_188007_2_, float p_188007_3_, float p_188007_4_)
/*     */       {
/*  50 */         return new BlockFaceUV(new float[] { 16.0F - p_188007_2_, p_188007_3_, 16.0F - p_188007_4_, p_188007_1_ }, 90);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f posFrom, Vector3f posTo, BlockPartFace face, TextureAtlasSprite sprite, EnumFacing facing, ModelRotation modelRotationIn, @Nullable BlockPartRotation partRotation, boolean uvLocked, boolean shade) {
/*  57 */     BlockFaceUV blockfaceuv = face.blockFaceUV;
/*     */     
/*  59 */     if (uvLocked)
/*     */     {
/*  61 */       blockfaceuv = applyUVLock(face.blockFaceUV, facing, modelRotationIn);
/*     */     }
/*     */     
/*  64 */     int[] aint = makeQuadVertexData(blockfaceuv, sprite, facing, getPositionsDiv16(posFrom, posTo), modelRotationIn, partRotation, shade);
/*  65 */     EnumFacing enumfacing = getFacingFromVertexData(aint);
/*     */     
/*  67 */     if (partRotation == null)
/*     */     {
/*  69 */       applyFacing(aint, enumfacing);
/*     */     }
/*     */     
/*  72 */     return new BakedQuad(aint, face.tintIndex, enumfacing, sprite);
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad makeBakedQuad(Vector3f p_makeBakedQuad_1_, Vector3f p_makeBakedQuad_2_, BlockPartFace p_makeBakedQuad_3_, TextureAtlasSprite p_makeBakedQuad_4_, EnumFacing p_makeBakedQuad_5_, ITransformation p_makeBakedQuad_6_, BlockPartRotation p_makeBakedQuad_7_, boolean p_makeBakedQuad_8_, boolean p_makeBakedQuad_9_) {
/*  77 */     BlockFaceUV blockfaceuv = p_makeBakedQuad_3_.blockFaceUV;
/*     */     
/*  79 */     if (p_makeBakedQuad_8_)
/*     */     {
/*  81 */       if (Reflector.ForgeHooksClient_applyUVLock.exists()) {
/*     */         
/*  83 */         blockfaceuv = (BlockFaceUV)Reflector.call(Reflector.ForgeHooksClient_applyUVLock, new Object[] { p_makeBakedQuad_3_.blockFaceUV, p_makeBakedQuad_5_, p_makeBakedQuad_6_ });
/*     */       }
/*     */       else {
/*     */         
/*  87 */         blockfaceuv = applyUVLock(p_makeBakedQuad_3_.blockFaceUV, p_makeBakedQuad_5_, (ModelRotation)p_makeBakedQuad_6_);
/*     */       } 
/*     */     }
/*     */     
/*  91 */     boolean flag = (p_makeBakedQuad_9_ && !Reflector.ForgeHooksClient_fillNormal.exists());
/*  92 */     int[] aint = makeQuadVertexData(blockfaceuv, p_makeBakedQuad_4_, p_makeBakedQuad_5_, getPositionsDiv16(p_makeBakedQuad_1_, p_makeBakedQuad_2_), p_makeBakedQuad_6_, p_makeBakedQuad_7_, flag);
/*  93 */     EnumFacing enumfacing = getFacingFromVertexData(aint);
/*     */     
/*  95 */     if (p_makeBakedQuad_7_ == null)
/*     */     {
/*  97 */       applyFacing(aint, enumfacing);
/*     */     }
/*     */     
/* 100 */     if (Reflector.ForgeHooksClient_fillNormal.exists()) {
/*     */       
/* 102 */       Reflector.call(Reflector.ForgeHooksClient_fillNormal, new Object[] { aint, enumfacing });
/* 103 */       return new BakedQuad(aint, p_makeBakedQuad_3_.tintIndex, enumfacing, p_makeBakedQuad_4_, p_makeBakedQuad_9_, DefaultVertexFormats.ITEM);
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return new BakedQuad(aint, p_makeBakedQuad_3_.tintIndex, enumfacing, p_makeBakedQuad_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockFaceUV applyUVLock(BlockFaceUV p_188010_1_, EnumFacing p_188010_2_, ModelRotation p_188010_3_) {
/* 113 */     return UV_ROTATIONS[getIndex(p_188010_3_, p_188010_2_)].rotateUV(p_188010_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] makeQuadVertexData(BlockFaceUV p_makeQuadVertexData_1_, TextureAtlasSprite p_makeQuadVertexData_2_, EnumFacing p_makeQuadVertexData_3_, float[] p_makeQuadVertexData_4_, ITransformation p_makeQuadVertexData_5_, @Nullable BlockPartRotation p_makeQuadVertexData_6_, boolean p_makeQuadVertexData_7_) {
/* 118 */     int i = 28;
/*     */     
/* 120 */     if (Config.isShaders())
/*     */     {
/* 122 */       i = 56;
/*     */     }
/*     */     
/* 125 */     int[] aint = new int[i];
/*     */     
/* 127 */     for (int j = 0; j < 4; j++)
/*     */     {
/* 129 */       fillVertexData(aint, j, p_makeQuadVertexData_3_, p_makeQuadVertexData_1_, p_makeQuadVertexData_4_, p_makeQuadVertexData_2_, p_makeQuadVertexData_5_, p_makeQuadVertexData_6_, p_makeQuadVertexData_7_);
/*     */     }
/*     */     
/* 132 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFaceShadeColor(EnumFacing facing) {
/* 137 */     float f = getFaceBrightness(facing);
/* 138 */     int i = MathHelper.clamp((int)(f * 255.0F), 0, 255);
/* 139 */     return 0xFF000000 | i << 16 | i << 8 | i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getFaceBrightness(EnumFacing p_178412_0_) {
/* 144 */     switch (p_178412_0_) {
/*     */       
/*     */       case null:
/* 147 */         if (Config.isShaders())
/*     */         {
/* 149 */           return Shaders.blockLightLevel05;
/*     */         }
/*     */         
/* 152 */         return 0.5F;
/*     */       
/*     */       case UP:
/* 155 */         return 1.0F;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 159 */         if (Config.isShaders())
/*     */         {
/* 161 */           return Shaders.blockLightLevel08;
/*     */         }
/*     */         
/* 164 */         return 0.8F;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 168 */         if (Config.isShaders())
/*     */         {
/* 170 */           return Shaders.blockLightLevel06;
/*     */         }
/*     */         
/* 173 */         return 0.6F;
/*     */     } 
/*     */     
/* 176 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] getPositionsDiv16(Vector3f pos1, Vector3f pos2) {
/* 182 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 183 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = pos1.x / 16.0F;
/* 184 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = pos1.y / 16.0F;
/* 185 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = pos1.z / 16.0F;
/* 186 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = pos2.x / 16.0F;
/* 187 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = pos2.y / 16.0F;
/* 188 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = pos2.z / 16.0F;
/* 189 */     return afloat;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillVertexData(int[] p_fillVertexData_1_, int p_fillVertexData_2_, EnumFacing p_fillVertexData_3_, BlockFaceUV p_fillVertexData_4_, float[] p_fillVertexData_5_, TextureAtlasSprite p_fillVertexData_6_, ITransformation p_fillVertexData_7_, @Nullable BlockPartRotation p_fillVertexData_8_, boolean p_fillVertexData_9_) {
/* 194 */     EnumFacing enumfacing = p_fillVertexData_7_.rotate(p_fillVertexData_3_);
/* 195 */     int i = p_fillVertexData_9_ ? getFaceShadeColor(enumfacing) : -1;
/* 196 */     EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = EnumFaceDirection.getFacing(p_fillVertexData_3_).getVertexInformation(p_fillVertexData_2_);
/* 197 */     Vector3f vector3f = new Vector3f(p_fillVertexData_5_[enumfacedirection$vertexinformation.xIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.yIndex], p_fillVertexData_5_[enumfacedirection$vertexinformation.zIndex]);
/* 198 */     rotatePart(vector3f, p_fillVertexData_8_);
/* 199 */     int j = rotateVertex(vector3f, p_fillVertexData_3_, p_fillVertexData_2_, p_fillVertexData_7_);
/* 200 */     BlockModelUtils.snapVertexPosition(vector3f);
/* 201 */     storeVertexData(p_fillVertexData_1_, j, p_fillVertexData_2_, vector3f, i, p_fillVertexData_6_, p_fillVertexData_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeVertexData(int[] faceData, int storeIndex, int vertexIndex, Vector3f position, int shadeColor, TextureAtlasSprite sprite, BlockFaceUV faceUV) {
/* 206 */     int i = faceData.length / 4;
/* 207 */     int j = storeIndex * i;
/* 208 */     faceData[j] = Float.floatToRawIntBits(position.x);
/* 209 */     faceData[j + 1] = Float.floatToRawIntBits(position.y);
/* 210 */     faceData[j + 2] = Float.floatToRawIntBits(position.z);
/* 211 */     faceData[j + 3] = shadeColor;
/* 212 */     faceData[j + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.getVertexU(vertexIndex) * 0.999D + faceUV.getVertexU((vertexIndex + 2) % 4) * 0.001D));
/* 213 */     faceData[j + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.getVertexV(vertexIndex) * 0.999D + faceUV.getVertexV((vertexIndex + 2) % 4) * 0.001D));
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotatePart(Vector3f p_178407_1_, @Nullable BlockPartRotation partRotation) {
/* 218 */     if (partRotation != null) {
/*     */       
/* 220 */       Matrix4f matrix4f = getMatrixIdentity();
/* 221 */       Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */       
/* 223 */       switch (partRotation.axis) {
/*     */         
/*     */         case null:
/* 226 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), matrix4f, matrix4f);
/* 227 */           vector3f.set(0.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Y:
/* 231 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), matrix4f, matrix4f);
/* 232 */           vector3f.set(1.0F, 0.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case Z:
/* 236 */           Matrix4f.rotate(partRotation.angle * 0.017453292F, new Vector3f(0.0F, 0.0F, 1.0F), matrix4f, matrix4f);
/* 237 */           vector3f.set(1.0F, 1.0F, 0.0F); break;
/* 238 */       }  if (partRotation
/*     */         
/* 240 */         .rescale) {
/*     */         
/* 242 */         if (Math.abs(partRotation.angle) == 22.5F) {
/*     */           
/* 244 */           vector3f.scale(SCALE_ROTATION_22_5);
/*     */         }
/*     */         else {
/*     */           
/* 248 */           vector3f.scale(SCALE_ROTATION_GENERAL);
/*     */         } 
/*     */         
/* 251 */         Vector3f.add(vector3f, new Vector3f(1.0F, 1.0F, 1.0F), vector3f);
/*     */       }
/*     */       else {
/*     */         
/* 255 */         vector3f.set(1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 258 */       rotateScale(p_178407_1_, new Vector3f((ReadableVector3f)partRotation.origin), matrix4f, vector3f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateVertex(Vector3f p_188011_1_, EnumFacing p_188011_2_, int p_188011_3_, ModelRotation p_188011_4_) {
/* 264 */     return rotateVertex(p_188011_1_, p_188011_2_, p_188011_3_, p_188011_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int rotateVertex(Vector3f p_rotateVertex_1_, EnumFacing p_rotateVertex_2_, int p_rotateVertex_3_, ITransformation p_rotateVertex_4_) {
/* 269 */     if (p_rotateVertex_4_ == ModelRotation.X0_Y0)
/*     */     {
/* 271 */       return p_rotateVertex_3_;
/*     */     }
/*     */ 
/*     */     
/* 275 */     if (Reflector.ForgeHooksClient_transform.exists()) {
/*     */       
/* 277 */       Reflector.call(Reflector.ForgeHooksClient_transform, new Object[] { p_rotateVertex_1_, p_rotateVertex_4_.getMatrix() });
/*     */     }
/*     */     else {
/*     */       
/* 281 */       rotateScale(p_rotateVertex_1_, new Vector3f(0.5F, 0.5F, 0.5F), ((ModelRotation)p_rotateVertex_4_).getMatrix4d(), new Vector3f(1.0F, 1.0F, 1.0F));
/*     */     } 
/*     */     
/* 284 */     return p_rotateVertex_4_.rotate(p_rotateVertex_2_, p_rotateVertex_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateScale(Vector3f position, Vector3f rotationOrigin, Matrix4f rotationMatrix, Vector3f scale) {
/* 290 */     Vector4f vector4f = new Vector4f(position.x - rotationOrigin.x, position.y - rotationOrigin.y, position.z - rotationOrigin.z, 1.0F);
/* 291 */     Matrix4f.transform(rotationMatrix, vector4f, vector4f);
/* 292 */     vector4f.x *= scale.x;
/* 293 */     vector4f.y *= scale.y;
/* 294 */     vector4f.z *= scale.z;
/* 295 */     position.set(vector4f.x + rotationOrigin.x, vector4f.y + rotationOrigin.y, vector4f.z + rotationOrigin.z);
/*     */   }
/*     */ 
/*     */   
/*     */   private Matrix4f getMatrixIdentity() {
/* 300 */     Matrix4f matrix4f = new Matrix4f();
/* 301 */     matrix4f.setIdentity();
/* 302 */     return matrix4f;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacingFromVertexData(int[] faceData) {
/* 307 */     int i = faceData.length / 4;
/* 308 */     int j = i * 2;
/* 309 */     Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
/* 310 */     Vector3f vector3f1 = new Vector3f(Float.intBitsToFloat(faceData[i]), Float.intBitsToFloat(faceData[i + 1]), Float.intBitsToFloat(faceData[i + 2]));
/* 311 */     Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[j]), Float.intBitsToFloat(faceData[j + 1]), Float.intBitsToFloat(faceData[j + 2]));
/* 312 */     Vector3f vector3f3 = new Vector3f();
/* 313 */     Vector3f vector3f4 = new Vector3f();
/* 314 */     Vector3f vector3f5 = new Vector3f();
/* 315 */     Vector3f.sub(vector3f, vector3f1, vector3f3);
/* 316 */     Vector3f.sub(vector3f2, vector3f1, vector3f4);
/* 317 */     Vector3f.cross(vector3f4, vector3f3, vector3f5);
/* 318 */     float f = (float)Math.sqrt((vector3f5.x * vector3f5.x + vector3f5.y * vector3f5.y + vector3f5.z * vector3f5.z));
/* 319 */     vector3f5.x /= f;
/* 320 */     vector3f5.y /= f;
/* 321 */     vector3f5.z /= f;
/* 322 */     EnumFacing enumfacing = null;
/* 323 */     float f1 = 0.0F; byte b; int k;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 325 */     for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b];
/*     */       
/* 327 */       Vec3i vec3i = enumfacing1.getDirectionVec();
/* 328 */       Vector3f vector3f6 = new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 329 */       float f2 = Vector3f.dot(vector3f5, vector3f6);
/*     */       
/* 331 */       if (f2 >= 0.0F && f2 > f1) {
/*     */         
/* 333 */         f1 = f2;
/* 334 */         enumfacing = enumfacing1;
/*     */       } 
/*     */       b++; }
/*     */     
/* 338 */     if (enumfacing == null)
/*     */     {
/* 340 */       return EnumFacing.UP;
/*     */     }
/*     */ 
/*     */     
/* 344 */     return enumfacing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyFacing(int[] p_178408_1_, EnumFacing p_178408_2_) {
/* 350 */     int[] aint = new int[p_178408_1_.length];
/* 351 */     System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
/* 352 */     float[] afloat = new float[(EnumFacing.values()).length];
/* 353 */     afloat[EnumFaceDirection.Constants.WEST_INDEX] = 999.0F;
/* 354 */     afloat[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0F;
/* 355 */     afloat[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0F;
/* 356 */     afloat[EnumFaceDirection.Constants.EAST_INDEX] = -999.0F;
/* 357 */     afloat[EnumFaceDirection.Constants.UP_INDEX] = -999.0F;
/* 358 */     afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0F;
/* 359 */     int i = p_178408_1_.length / 4;
/*     */     
/* 361 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 363 */       int k = i * j;
/* 364 */       float f = Float.intBitsToFloat(aint[k]);
/* 365 */       float f1 = Float.intBitsToFloat(aint[k + 1]);
/* 366 */       float f2 = Float.intBitsToFloat(aint[k + 2]);
/*     */       
/* 368 */       if (f < afloat[EnumFaceDirection.Constants.WEST_INDEX])
/*     */       {
/* 370 */         afloat[EnumFaceDirection.Constants.WEST_INDEX] = f;
/*     */       }
/*     */       
/* 373 */       if (f1 < afloat[EnumFaceDirection.Constants.DOWN_INDEX])
/*     */       {
/* 375 */         afloat[EnumFaceDirection.Constants.DOWN_INDEX] = f1;
/*     */       }
/*     */       
/* 378 */       if (f2 < afloat[EnumFaceDirection.Constants.NORTH_INDEX])
/*     */       {
/* 380 */         afloat[EnumFaceDirection.Constants.NORTH_INDEX] = f2;
/*     */       }
/*     */       
/* 383 */       if (f > afloat[EnumFaceDirection.Constants.EAST_INDEX])
/*     */       {
/* 385 */         afloat[EnumFaceDirection.Constants.EAST_INDEX] = f;
/*     */       }
/*     */       
/* 388 */       if (f1 > afloat[EnumFaceDirection.Constants.UP_INDEX])
/*     */       {
/* 390 */         afloat[EnumFaceDirection.Constants.UP_INDEX] = f1;
/*     */       }
/*     */       
/* 393 */       if (f2 > afloat[EnumFaceDirection.Constants.SOUTH_INDEX])
/*     */       {
/* 395 */         afloat[EnumFaceDirection.Constants.SOUTH_INDEX] = f2;
/*     */       }
/*     */     } 
/*     */     
/* 399 */     EnumFaceDirection enumfacedirection = EnumFaceDirection.getFacing(p_178408_2_);
/*     */     
/* 401 */     for (int j1 = 0; j1 < 4; j1++) {
/*     */       
/* 403 */       int k1 = i * j1;
/* 404 */       EnumFaceDirection.VertexInformation enumfacedirection$vertexinformation = enumfacedirection.getVertexInformation(j1);
/* 405 */       float f8 = afloat[enumfacedirection$vertexinformation.xIndex];
/* 406 */       float f3 = afloat[enumfacedirection$vertexinformation.yIndex];
/* 407 */       float f4 = afloat[enumfacedirection$vertexinformation.zIndex];
/* 408 */       p_178408_1_[k1] = Float.floatToRawIntBits(f8);
/* 409 */       p_178408_1_[k1 + 1] = Float.floatToRawIntBits(f3);
/* 410 */       p_178408_1_[k1 + 2] = Float.floatToRawIntBits(f4);
/*     */       
/* 412 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 414 */         int i1 = i * l;
/* 415 */         float f5 = Float.intBitsToFloat(aint[i1]);
/* 416 */         float f6 = Float.intBitsToFloat(aint[i1 + 1]);
/* 417 */         float f7 = Float.intBitsToFloat(aint[i1 + 2]);
/*     */         
/* 419 */         if (MathHelper.epsilonEquals(f8, f5) && MathHelper.epsilonEquals(f3, f6) && MathHelper.epsilonEquals(f4, f7)) {
/*     */           
/* 421 */           p_178408_1_[k1 + 4] = aint[i1 + 4];
/* 422 */           p_178408_1_[k1 + 4 + 1] = aint[i1 + 4 + 1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addUvRotation(ModelRotation p_188013_0_, EnumFacing p_188013_1_, Rotation p_188013_2_) {
/* 430 */     UV_ROTATIONS[getIndex(p_188013_0_, p_188013_1_)] = p_188013_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(ModelRotation p_188014_0_, EnumFacing p_188014_1_) {
/* 435 */     return (ModelRotation.values()).length * p_188014_1_.ordinal() + p_188014_0_.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 440 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.DOWN, UV_ROTATION_0);
/* 441 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.EAST, UV_ROTATION_0);
/* 442 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.NORTH, UV_ROTATION_0);
/* 443 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.SOUTH, UV_ROTATION_0);
/* 444 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.UP, UV_ROTATION_0);
/* 445 */     addUvRotation(ModelRotation.X0_Y0, EnumFacing.WEST, UV_ROTATION_0);
/* 446 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.EAST, UV_ROTATION_0);
/* 447 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.NORTH, UV_ROTATION_0);
/* 448 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.SOUTH, UV_ROTATION_0);
/* 449 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.WEST, UV_ROTATION_0);
/* 450 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.EAST, UV_ROTATION_0);
/* 451 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.NORTH, UV_ROTATION_0);
/* 452 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.SOUTH, UV_ROTATION_0);
/* 453 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.WEST, UV_ROTATION_0);
/* 454 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.EAST, UV_ROTATION_0);
/* 455 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.NORTH, UV_ROTATION_0);
/* 456 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.SOUTH, UV_ROTATION_0);
/* 457 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.WEST, UV_ROTATION_0);
/* 458 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.DOWN, UV_ROTATION_0);
/* 459 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.SOUTH, UV_ROTATION_0);
/* 460 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.DOWN, UV_ROTATION_0);
/* 461 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.DOWN, UV_ROTATION_0);
/* 462 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.NORTH, UV_ROTATION_0);
/* 463 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.DOWN, UV_ROTATION_0);
/* 464 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.DOWN, UV_ROTATION_0);
/* 465 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.UP, UV_ROTATION_0);
/* 466 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.SOUTH, UV_ROTATION_0);
/* 467 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.UP, UV_ROTATION_0);
/* 468 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.UP, UV_ROTATION_0);
/* 469 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.NORTH, UV_ROTATION_0);
/* 470 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.UP, UV_ROTATION_0);
/* 471 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.UP, UV_ROTATION_0);
/* 472 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.UP, UV_ROTATION_270);
/* 473 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.DOWN, UV_ROTATION_270);
/* 474 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.WEST, UV_ROTATION_270);
/* 475 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.WEST, UV_ROTATION_270);
/* 476 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.WEST, UV_ROTATION_270);
/* 477 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.NORTH, UV_ROTATION_270);
/* 478 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.SOUTH, UV_ROTATION_270);
/* 479 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.WEST, UV_ROTATION_270);
/* 480 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.UP, UV_ROTATION_270);
/* 481 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.DOWN, UV_ROTATION_270);
/* 482 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.EAST, UV_ROTATION_270);
/* 483 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.EAST, UV_ROTATION_270);
/* 484 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.NORTH, UV_ROTATION_270);
/* 485 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.SOUTH, UV_ROTATION_270);
/* 486 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.EAST, UV_ROTATION_270);
/* 487 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.EAST, UV_ROTATION_270);
/* 488 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 489 */     addUvRotation(ModelRotation.X0_Y180, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 490 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 491 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 492 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 493 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 494 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 495 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 496 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.EAST, UV_ROTATION_INVERSE);
/* 497 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 498 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 499 */     addUvRotation(ModelRotation.X180_Y0, EnumFacing.WEST, UV_ROTATION_INVERSE);
/* 500 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.EAST, UV_ROTATION_INVERSE);
/* 501 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 502 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 503 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.WEST, UV_ROTATION_INVERSE);
/* 504 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 505 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.EAST, UV_ROTATION_INVERSE);
/* 506 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 507 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 508 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.UP, UV_ROTATION_INVERSE);
/* 509 */     addUvRotation(ModelRotation.X180_Y180, EnumFacing.WEST, UV_ROTATION_INVERSE);
/* 510 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.EAST, UV_ROTATION_INVERSE);
/* 511 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 512 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 513 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.WEST, UV_ROTATION_INVERSE);
/* 514 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 515 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.NORTH, UV_ROTATION_INVERSE);
/* 516 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 517 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 518 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.SOUTH, UV_ROTATION_INVERSE);
/* 519 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.DOWN, UV_ROTATION_INVERSE);
/* 520 */     addUvRotation(ModelRotation.X0_Y90, EnumFacing.UP, UV_ROTATION_90);
/* 521 */     addUvRotation(ModelRotation.X0_Y270, EnumFacing.DOWN, UV_ROTATION_90);
/* 522 */     addUvRotation(ModelRotation.X90_Y0, EnumFacing.EAST, UV_ROTATION_90);
/* 523 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.EAST, UV_ROTATION_90);
/* 524 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.NORTH, UV_ROTATION_90);
/* 525 */     addUvRotation(ModelRotation.X90_Y90, EnumFacing.SOUTH, UV_ROTATION_90);
/* 526 */     addUvRotation(ModelRotation.X90_Y180, EnumFacing.EAST, UV_ROTATION_90);
/* 527 */     addUvRotation(ModelRotation.X90_Y270, EnumFacing.EAST, UV_ROTATION_90);
/* 528 */     addUvRotation(ModelRotation.X270_Y0, EnumFacing.WEST, UV_ROTATION_90);
/* 529 */     addUvRotation(ModelRotation.X180_Y90, EnumFacing.DOWN, UV_ROTATION_90);
/* 530 */     addUvRotation(ModelRotation.X180_Y270, EnumFacing.UP, UV_ROTATION_90);
/* 531 */     addUvRotation(ModelRotation.X270_Y90, EnumFacing.WEST, UV_ROTATION_90);
/* 532 */     addUvRotation(ModelRotation.X270_Y180, EnumFacing.WEST, UV_ROTATION_90);
/* 533 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.NORTH, UV_ROTATION_90);
/* 534 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.SOUTH, UV_ROTATION_90);
/* 535 */     addUvRotation(ModelRotation.X270_Y270, EnumFacing.WEST, UV_ROTATION_90);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Rotation
/*     */   {
/*     */     private Rotation() {}
/*     */ 
/*     */     
/*     */     public BlockFaceUV rotateUV(BlockFaceUV p_188006_1_) {
/* 546 */       float f = p_188006_1_.getVertexU(p_188006_1_.getVertexRotatedRev(0));
/* 547 */       float f1 = p_188006_1_.getVertexV(p_188006_1_.getVertexRotatedRev(0));
/* 548 */       float f2 = p_188006_1_.getVertexU(p_188006_1_.getVertexRotatedRev(2));
/* 549 */       float f3 = p_188006_1_.getVertexV(p_188006_1_.getVertexRotatedRev(2));
/* 550 */       return makeRotatedUV(f, f1, f2, f3);
/*     */     }
/*     */     
/*     */     abstract BlockFaceUV makeRotatedUV(float param1Float1, float param1Float2, float param1Float3, float param1Float4);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\FaceBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
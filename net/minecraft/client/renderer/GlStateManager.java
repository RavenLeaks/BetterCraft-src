/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import javax.annotation.Nullable;
/*      */ import optifine.Config;
/*      */ import optifine.GlBlendState;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.vector.Quaternion;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GlStateManager
/*      */ {
/*   21 */   private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
/*   22 */   private static final FloatBuffer BUF_FLOAT_4 = BufferUtils.createFloatBuffer(4);
/*   23 */   private static final AlphaState alphaState = new AlphaState(null);
/*   24 */   private static final BooleanState lightingState = new BooleanState(2896);
/*   25 */   private static final BooleanState[] lightState = new BooleanState[8];
/*      */   
/*      */   private static final ColorMaterialState colorMaterialState;
/*      */   private static final BlendState blendState;
/*      */   private static final DepthState depthState;
/*      */   private static final FogState fogState;
/*      */   private static final CullState cullState;
/*      */   private static final PolygonOffsetState polygonOffsetState;
/*      */   private static final ColorLogicState colorLogicState;
/*      */   private static final TexGenState texGenState;
/*      */   private static final ClearState clearState;
/*      */   private static final StencilState stencilState;
/*      */   private static final BooleanState normalizeState;
/*      */   public static int activeTextureUnit;
/*      */   public static final TextureState[] textureState;
/*      */   private static int activeShadeModel;
/*      */   private static final BooleanState rescaleNormalState;
/*      */   private static final ColorMask colorMaskState;
/*      */   private static final Color colorState;
/*      */   public static boolean clearEnabled = true;
/*      */   
/*      */   public static void pushAttrib() {
/*   47 */     GL11.glPushAttrib(8256);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popAttrib() {
/*   52 */     GL11.glPopAttrib();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableAlpha() {
/*   57 */     alphaState.alphaTest.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableAlpha() {
/*   62 */     alphaState.alphaTest.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void alphaFunc(int func, float ref) {
/*   67 */     if (func != alphaState.func || ref != alphaState.ref) {
/*      */       
/*   69 */       alphaState.func = func;
/*   70 */       alphaState.ref = ref;
/*   71 */       GL11.glAlphaFunc(func, ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLighting() {
/*   77 */     lightingState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLighting() {
/*   82 */     lightingState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLight(int light) {
/*   87 */     lightState[light].setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLight(int light) {
/*   92 */     lightState[light].setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorMaterial() {
/*   97 */     colorMaterialState.colorMaterial.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorMaterial() {
/*  102 */     colorMaterialState.colorMaterial.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMaterial(int face, int mode) {
/*  107 */     if (face != colorMaterialState.face || mode != colorMaterialState.mode) {
/*      */       
/*  109 */       colorMaterialState.face = face;
/*  110 */       colorMaterialState.mode = mode;
/*  111 */       GL11.glColorMaterial(face, mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glLight(int light, int pname, FloatBuffer params) {
/*  117 */     GL11.glLight(light, pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glLightModel(int pname, FloatBuffer params) {
/*  122 */     GL11.glLightModel(pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNormal3f(float nx, float ny, float nz) {
/*  127 */     GL11.glNormal3f(nx, ny, nz);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableDepth() {
/*  132 */     depthState.depthTest.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableDepth() {
/*  137 */     depthState.depthTest.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthFunc(int depthFunc) {
/*  142 */     if (depthFunc != depthState.depthFunc) {
/*      */       
/*  144 */       depthState.depthFunc = depthFunc;
/*  145 */       GL11.glDepthFunc(depthFunc);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthMask(boolean flagIn) {
/*  151 */     if (flagIn != depthState.maskEnabled) {
/*      */       
/*  153 */       depthState.maskEnabled = flagIn;
/*  154 */       GL11.glDepthMask(flagIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableBlend() {
/*  160 */     blendState.blend.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableBlend() {
/*  165 */     blendState.blend.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(SourceFactor srcFactor, DestFactor dstFactor) {
/*  170 */     blendFunc(srcFactor.factor, dstFactor.factor);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(int srcFactor, int dstFactor) {
/*  175 */     if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor) {
/*      */       
/*  177 */       blendState.srcFactor = srcFactor;
/*  178 */       blendState.dstFactor = dstFactor;
/*  179 */       GL11.glBlendFunc(srcFactor, dstFactor);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void tryBlendFuncSeparate(SourceFactor srcFactor, DestFactor dstFactor, SourceFactor srcFactorAlpha, DestFactor dstFactorAlpha) {
/*  185 */     tryBlendFuncSeparate(srcFactor.factor, dstFactor.factor, srcFactorAlpha.factor, dstFactorAlpha.factor);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  190 */     if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactorAlpha != blendState.srcFactorAlpha || dstFactorAlpha != blendState.dstFactorAlpha) {
/*      */       
/*  192 */       blendState.srcFactor = srcFactor;
/*  193 */       blendState.dstFactor = dstFactor;
/*  194 */       blendState.srcFactorAlpha = srcFactorAlpha;
/*  195 */       blendState.dstFactorAlpha = dstFactorAlpha;
/*  196 */       OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBlendEquation(int blendEquation) {
/*  202 */     GL14.glBlendEquation(blendEquation);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableOutlineMode(int p_187431_0_) {
/*  207 */     BUF_FLOAT_4.put(0, (p_187431_0_ >> 16 & 0xFF) / 255.0F);
/*  208 */     BUF_FLOAT_4.put(1, (p_187431_0_ >> 8 & 0xFF) / 255.0F);
/*  209 */     BUF_FLOAT_4.put(2, (p_187431_0_ >> 0 & 0xFF) / 255.0F);
/*  210 */     BUF_FLOAT_4.put(3, (p_187431_0_ >> 24 & 0xFF) / 255.0F);
/*  211 */     glTexEnv(8960, 8705, BUF_FLOAT_4);
/*  212 */     glTexEnvi(8960, 8704, 34160);
/*  213 */     glTexEnvi(8960, 34161, 7681);
/*  214 */     glTexEnvi(8960, 34176, 34166);
/*  215 */     glTexEnvi(8960, 34192, 768);
/*  216 */     glTexEnvi(8960, 34162, 7681);
/*  217 */     glTexEnvi(8960, 34184, 5890);
/*  218 */     glTexEnvi(8960, 34200, 770);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableOutlineMode() {
/*  223 */     glTexEnvi(8960, 8704, 8448);
/*  224 */     glTexEnvi(8960, 34161, 8448);
/*  225 */     glTexEnvi(8960, 34162, 8448);
/*  226 */     glTexEnvi(8960, 34176, 5890);
/*  227 */     glTexEnvi(8960, 34184, 5890);
/*  228 */     glTexEnvi(8960, 34192, 768);
/*  229 */     glTexEnvi(8960, 34200, 770);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/*  234 */     fogState.fog.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/*  239 */     fogState.fog.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFog(FogMode fogMode) {
/*  244 */     setFog(fogMode.capabilityId);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setFog(int param) {
/*  249 */     if (param != fogState.mode) {
/*      */       
/*  251 */       fogState.mode = param;
/*  252 */       GL11.glFogi(2917, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogDensity(float param) {
/*  258 */     if (param != fogState.density) {
/*      */       
/*  260 */       fogState.density = param;
/*  261 */       GL11.glFogf(2914, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogStart(float param) {
/*  267 */     if (param != fogState.start) {
/*      */       
/*  269 */       fogState.start = param;
/*  270 */       GL11.glFogf(2915, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnd(float param) {
/*  276 */     if (param != fogState.end) {
/*      */       
/*  278 */       fogState.end = param;
/*  279 */       GL11.glFogf(2916, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glFog(int pname, FloatBuffer param) {
/*  285 */     GL11.glFog(pname, param);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glFogi(int pname, int param) {
/*  290 */     GL11.glFogi(pname, param);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableCull() {
/*  295 */     cullState.cullFace.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableCull() {
/*  300 */     cullState.cullFace.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void cullFace(CullFace cullFace) {
/*  305 */     cullFace(cullFace.mode);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void cullFace(int mode) {
/*  310 */     if (mode != cullState.mode) {
/*      */       
/*  312 */       cullState.mode = mode;
/*  313 */       GL11.glCullFace(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glPolygonMode(int face, int mode) {
/*  319 */     GL11.glPolygonMode(face, mode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enablePolygonOffset() {
/*  324 */     polygonOffsetState.polygonOffsetFill.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disablePolygonOffset() {
/*  329 */     polygonOffsetState.polygonOffsetFill.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void doPolygonOffset(float factor, float units) {
/*  334 */     if (factor != polygonOffsetState.factor || units != polygonOffsetState.units) {
/*      */       
/*  336 */       polygonOffsetState.factor = factor;
/*  337 */       polygonOffsetState.units = units;
/*  338 */       GL11.glPolygonOffset(factor, units);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorLogic() {
/*  344 */     colorLogicState.colorLogicOp.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorLogic() {
/*  349 */     colorLogicState.colorLogicOp.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorLogicOp(LogicOp logicOperation) {
/*  354 */     colorLogicOp(logicOperation.opcode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorLogicOp(int opcode) {
/*  359 */     if (opcode != colorLogicState.opcode) {
/*      */       
/*  361 */       colorLogicState.opcode = opcode;
/*  362 */       GL11.glLogicOp(opcode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexGenCoord(TexGen texGen) {
/*  368 */     (texGenCoord(texGen)).textureGen.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexGenCoord(TexGen texGen) {
/*  373 */     (texGenCoord(texGen)).textureGen.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void texGen(TexGen texGen, int param) {
/*  378 */     TexGenCoord glstatemanager$texgencoord = texGenCoord(texGen);
/*      */     
/*  380 */     if (param != glstatemanager$texgencoord.param) {
/*      */       
/*  382 */       glstatemanager$texgencoord.param = param;
/*  383 */       GL11.glTexGeni(glstatemanager$texgencoord.coord, 9472, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void texGen(TexGen texGen, int pname, FloatBuffer params) {
/*  389 */     GL11.glTexGen((texGenCoord(texGen)).coord, pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   private static TexGenCoord texGenCoord(TexGen texGen) {
/*  394 */     switch (texGen) {
/*      */       
/*      */       case S:
/*  397 */         return texGenState.s;
/*      */       
/*      */       case T:
/*  400 */         return texGenState.t;
/*      */       
/*      */       case R:
/*  403 */         return texGenState.r;
/*      */       
/*      */       case null:
/*  406 */         return texGenState.q;
/*      */     } 
/*      */     
/*  409 */     return texGenState.s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  415 */     if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
/*      */       
/*  417 */       activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
/*  418 */       OpenGlHelper.setActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/*  424 */     (textureState[activeTextureUnit]).texture2DState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/*  429 */     (textureState[activeTextureUnit]).texture2DState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexEnv(int p_187448_0_, int p_187448_1_, FloatBuffer p_187448_2_) {
/*  434 */     GL11.glTexEnv(p_187448_0_, p_187448_1_, p_187448_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexEnvi(int p_187399_0_, int p_187399_1_, int p_187399_2_) {
/*  439 */     GL11.glTexEnvi(p_187399_0_, p_187399_1_, p_187399_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexEnvf(int p_187436_0_, int p_187436_1_, float p_187436_2_) {
/*  444 */     GL11.glTexEnvf(p_187436_0_, p_187436_1_, p_187436_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameterf(int p_187403_0_, int p_187403_1_, float p_187403_2_) {
/*  449 */     GL11.glTexParameterf(p_187403_0_, p_187403_1_, p_187403_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameteri(int p_187421_0_, int p_187421_1_, int p_187421_2_) {
/*  454 */     GL11.glTexParameteri(p_187421_0_, p_187421_1_, p_187421_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetTexLevelParameteri(int p_187411_0_, int p_187411_1_, int p_187411_2_) {
/*  459 */     return GL11.glGetTexLevelParameteri(p_187411_0_, p_187411_1_, p_187411_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int generateTexture() {
/*  464 */     return GL11.glGenTextures();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTexture(int texture) {
/*  469 */     if (texture != 0) {
/*      */       
/*  471 */       GL11.glDeleteTextures(texture); byte b; int i;
/*      */       TextureState[] arrayOfTextureState;
/*  473 */       for (i = (arrayOfTextureState = textureState).length, b = 0; b < i; ) { TextureState glstatemanager$texturestate = arrayOfTextureState[b];
/*      */         
/*  475 */         if (glstatemanager$texturestate.textureName == texture)
/*      */         {
/*  477 */           glstatemanager$texturestate.textureName = 0;
/*      */         }
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void bindTexture(int texture) {
/*  485 */     if (texture != (textureState[activeTextureUnit]).textureName) {
/*      */       
/*  487 */       (textureState[activeTextureUnit]).textureName = texture;
/*  488 */       GL11.glBindTexture(3553, texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexImage2D(int p_187419_0_, int p_187419_1_, int p_187419_2_, int p_187419_3_, int p_187419_4_, int p_187419_5_, int p_187419_6_, int p_187419_7_, @Nullable IntBuffer p_187419_8_) {
/*  494 */     GL11.glTexImage2D(p_187419_0_, p_187419_1_, p_187419_2_, p_187419_3_, p_187419_4_, p_187419_5_, p_187419_6_, p_187419_7_, p_187419_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexSubImage2D(int p_187414_0_, int p_187414_1_, int p_187414_2_, int p_187414_3_, int p_187414_4_, int p_187414_5_, int p_187414_6_, int p_187414_7_, IntBuffer p_187414_8_) {
/*  499 */     GL11.glTexSubImage2D(p_187414_0_, p_187414_1_, p_187414_2_, p_187414_3_, p_187414_4_, p_187414_5_, p_187414_6_, p_187414_7_, p_187414_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCopyTexSubImage2D(int p_187443_0_, int p_187443_1_, int p_187443_2_, int p_187443_3_, int p_187443_4_, int p_187443_5_, int p_187443_6_, int p_187443_7_) {
/*  504 */     GL11.glCopyTexSubImage2D(p_187443_0_, p_187443_1_, p_187443_2_, p_187443_3_, p_187443_4_, p_187443_5_, p_187443_6_, p_187443_7_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glGetTexImage(int p_187433_0_, int p_187433_1_, int p_187433_2_, int p_187433_3_, IntBuffer p_187433_4_) {
/*  509 */     GL11.glGetTexImage(p_187433_0_, p_187433_1_, p_187433_2_, p_187433_3_, p_187433_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableNormalize() {
/*  514 */     normalizeState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableNormalize() {
/*  519 */     normalizeState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void shadeModel(int mode) {
/*  524 */     if (mode != activeShadeModel) {
/*      */       
/*  526 */       activeShadeModel = mode;
/*  527 */       GL11.glShadeModel(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableRescaleNormal() {
/*  533 */     rescaleNormalState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableRescaleNormal() {
/*  538 */     rescaleNormalState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void viewport(int x, int y, int width, int height) {
/*  543 */     GL11.glViewport(x, y, width, height);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/*  548 */     if (red != colorMaskState.red || green != colorMaskState.green || blue != colorMaskState.blue || alpha != colorMaskState.alpha) {
/*      */       
/*  550 */       colorMaskState.red = red;
/*  551 */       colorMaskState.green = green;
/*  552 */       colorMaskState.blue = blue;
/*  553 */       colorMaskState.alpha = alpha;
/*  554 */       GL11.glColorMask(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearDepth(double depth) {
/*  560 */     if (depth != clearState.depth) {
/*      */       
/*  562 */       clearState.depth = depth;
/*  563 */       GL11.glClearDepth(depth);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearColor(float red, float green, float blue, float alpha) {
/*  569 */     if (red != clearState.color.red || green != clearState.color.green || blue != clearState.color.blue || alpha != clearState.color.alpha) {
/*      */       
/*  571 */       clearState.color.red = red;
/*  572 */       clearState.color.green = green;
/*  573 */       clearState.color.blue = blue;
/*  574 */       clearState.color.alpha = alpha;
/*  575 */       GL11.glClearColor(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clear(int mask) {
/*  581 */     if (clearEnabled)
/*      */     {
/*  583 */       GL11.glClear(mask);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void matrixMode(int mode) {
/*  589 */     GL11.glMatrixMode(mode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadIdentity() {
/*  594 */     GL11.glLoadIdentity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushMatrix() {
/*  599 */     GL11.glPushMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popMatrix() {
/*  604 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getFloat(int pname, FloatBuffer params) {
/*  609 */     GL11.glGetFloat(pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
/*  614 */     GL11.glOrtho(left, right, bottom, top, zNear, zFar);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rotate(float angle, float x, float y, float z) {
/*  619 */     GL11.glRotatef(angle, x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(float x, float y, float z) {
/*  624 */     GL11.glScalef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(double x, double y, double z) {
/*  629 */     GL11.glScaled(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(float x, float y, float z) {
/*  634 */     GL11.glTranslatef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(double x, double y, double z) {
/*  639 */     GL11.glTranslated(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void multMatrix(FloatBuffer matrix) {
/*  644 */     GL11.glMultMatrix(matrix);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rotate(Quaternion p_187444_0_) {
/*  649 */     multMatrix(quatToGlMatrix(BUF_FLOAT_16, p_187444_0_));
/*      */   }
/*      */ 
/*      */   
/*      */   public static FloatBuffer quatToGlMatrix(FloatBuffer p_187418_0_, Quaternion p_187418_1_) {
/*  654 */     p_187418_0_.clear();
/*  655 */     float f = p_187418_1_.x * p_187418_1_.x;
/*  656 */     float f1 = p_187418_1_.x * p_187418_1_.y;
/*  657 */     float f2 = p_187418_1_.x * p_187418_1_.z;
/*  658 */     float f3 = p_187418_1_.x * p_187418_1_.w;
/*  659 */     float f4 = p_187418_1_.y * p_187418_1_.y;
/*  660 */     float f5 = p_187418_1_.y * p_187418_1_.z;
/*  661 */     float f6 = p_187418_1_.y * p_187418_1_.w;
/*  662 */     float f7 = p_187418_1_.z * p_187418_1_.z;
/*  663 */     float f8 = p_187418_1_.z * p_187418_1_.w;
/*  664 */     p_187418_0_.put(1.0F - 2.0F * (f4 + f7));
/*  665 */     p_187418_0_.put(2.0F * (f1 + f8));
/*  666 */     p_187418_0_.put(2.0F * (f2 - f6));
/*  667 */     p_187418_0_.put(0.0F);
/*  668 */     p_187418_0_.put(2.0F * (f1 - f8));
/*  669 */     p_187418_0_.put(1.0F - 2.0F * (f + f7));
/*  670 */     p_187418_0_.put(2.0F * (f5 + f3));
/*  671 */     p_187418_0_.put(0.0F);
/*  672 */     p_187418_0_.put(2.0F * (f2 + f6));
/*  673 */     p_187418_0_.put(2.0F * (f5 - f3));
/*  674 */     p_187418_0_.put(1.0F - 2.0F * (f + f4));
/*  675 */     p_187418_0_.put(0.0F);
/*  676 */     p_187418_0_.put(0.0F);
/*  677 */     p_187418_0_.put(0.0F);
/*  678 */     p_187418_0_.put(0.0F);
/*  679 */     p_187418_0_.put(1.0F);
/*  680 */     p_187418_0_.rewind();
/*  681 */     return p_187418_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
/*  686 */     if (colorRed != colorState.red || colorGreen != colorState.green || colorBlue != colorState.blue || colorAlpha != colorState.alpha) {
/*      */       
/*  688 */       colorState.red = colorRed;
/*  689 */       colorState.green = colorGreen;
/*  690 */       colorState.blue = colorBlue;
/*  691 */       colorState.alpha = colorAlpha;
/*  692 */       GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue) {
/*  698 */     color(colorRed, colorGreen, colorBlue, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoord2f(float p_187426_0_, float p_187426_1_) {
/*  703 */     GL11.glTexCoord2f(p_187426_0_, p_187426_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertex3f(float p_187435_0_, float p_187435_1_, float p_187435_2_) {
/*  708 */     GL11.glVertex3f(p_187435_0_, p_187435_1_, p_187435_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetColor() {
/*  713 */     colorState.red = -1.0F;
/*  714 */     colorState.green = -1.0F;
/*  715 */     colorState.blue = -1.0F;
/*  716 */     colorState.alpha = -1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNormalPointer(int p_187446_0_, int p_187446_1_, ByteBuffer p_187446_2_) {
/*  721 */     GL11.glNormalPointer(p_187446_0_, p_187446_1_, p_187446_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_187405_0_, int p_187405_1_, int p_187405_2_, int p_187405_3_) {
/*  726 */     GL11.glTexCoordPointer(p_187405_0_, p_187405_1_, p_187405_2_, p_187405_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_187404_0_, int p_187404_1_, int p_187404_2_, ByteBuffer p_187404_3_) {
/*  731 */     GL11.glTexCoordPointer(p_187404_0_, p_187404_1_, p_187404_2_, p_187404_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_187420_0_, int p_187420_1_, int p_187420_2_, int p_187420_3_) {
/*  736 */     GL11.glVertexPointer(p_187420_0_, p_187420_1_, p_187420_2_, p_187420_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_187427_0_, int p_187427_1_, int p_187427_2_, ByteBuffer p_187427_3_) {
/*  741 */     GL11.glVertexPointer(p_187427_0_, p_187427_1_, p_187427_2_, p_187427_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_187406_0_, int p_187406_1_, int p_187406_2_, int p_187406_3_) {
/*  746 */     GL11.glColorPointer(p_187406_0_, p_187406_1_, p_187406_2_, p_187406_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_187400_0_, int p_187400_1_, int p_187400_2_, ByteBuffer p_187400_3_) {
/*  751 */     GL11.glColorPointer(p_187400_0_, p_187400_1_, p_187400_2_, p_187400_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableClientState(int p_187429_0_) {
/*  756 */     GL11.glDisableClientState(p_187429_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnableClientState(int p_187410_0_) {
/*  761 */     GL11.glEnableClientState(p_187410_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBegin(int p_187447_0_) {
/*  766 */     GL11.glBegin(p_187447_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnd() {
/*  771 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDrawArrays(int p_187439_0_, int p_187439_1_, int p_187439_2_) {
/*  776 */     GL11.glDrawArrays(p_187439_0_, p_187439_1_, p_187439_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glLineWidth(float p_187441_0_) {
/*  781 */     GL11.glLineWidth(p_187441_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void callList(int list) {
/*  786 */     GL11.glCallList(list);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteLists(int p_187449_0_, int p_187449_1_) {
/*  791 */     GL11.glDeleteLists(p_187449_0_, p_187449_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNewList(int p_187423_0_, int p_187423_1_) {
/*  796 */     GL11.glNewList(p_187423_0_, p_187423_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEndList() {
/*  801 */     GL11.glEndList();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGenLists(int p_187442_0_) {
/*  806 */     return GL11.glGenLists(p_187442_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glPixelStorei(int p_187425_0_, int p_187425_1_) {
/*  811 */     GL11.glPixelStorei(p_187425_0_, p_187425_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glReadPixels(int p_187413_0_, int p_187413_1_, int p_187413_2_, int p_187413_3_, int p_187413_4_, int p_187413_5_, IntBuffer p_187413_6_) {
/*  816 */     GL11.glReadPixels(p_187413_0_, p_187413_1_, p_187413_2_, p_187413_3_, p_187413_4_, p_187413_5_, p_187413_6_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetError() {
/*  821 */     return GL11.glGetError();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String glGetString(int p_187416_0_) {
/*  826 */     return GL11.glGetString(p_187416_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glGetInteger(int p_187445_0_, IntBuffer p_187445_1_) {
/*  831 */     GL11.glGetInteger(p_187445_0_, p_187445_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetInteger(int p_187397_0_) {
/*  836 */     return GL11.glGetInteger(p_187397_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableBlendProfile(Profile p_187408_0_) {
/*  841 */     p_187408_0_.apply();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableBlendProfile(Profile p_187440_0_) {
/*  846 */     p_187440_0_.clean();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getActiveTextureUnit() {
/*  851 */     return OpenGlHelper.defaultTexUnit + activeTextureUnit;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindCurrentTexture() {
/*  856 */     GL11.glBindTexture(3553, (textureState[activeTextureUnit]).textureName);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBoundTexture() {
/*  861 */     return (textureState[activeTextureUnit]).textureName;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkBoundTexture() {
/*  866 */     if (Config.isMinecraftThread()) {
/*      */       
/*  868 */       int i = GL11.glGetInteger(34016);
/*  869 */       int j = GL11.glGetInteger(32873);
/*  870 */       int k = getActiveTextureUnit();
/*  871 */       int l = getBoundTexture();
/*      */       
/*  873 */       if (l > 0)
/*      */       {
/*  875 */         if (i != k || j != l)
/*      */         {
/*  877 */           Config.dbg("checkTexture: act: " + k + ", glAct: " + i + ", tex: " + l + ", glTex: " + j);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
/*  885 */     p_deleteTextures_0_.rewind();
/*      */     
/*  887 */     while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
/*      */       
/*  889 */       int i = p_deleteTextures_0_.get();
/*  890 */       deleteTexture(i);
/*      */     } 
/*      */     
/*  893 */     p_deleteTextures_0_.rewind();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogEnabled() {
/*  898 */     return fogState.fog.currentState;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnabled(boolean p_setFogEnabled_0_) {
/*  903 */     fogState.fog.setState(p_setFogEnabled_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getBlendState(GlBlendState p_getBlendState_0_) {
/*  908 */     p_getBlendState_0_.enabled = blendState.blend.currentState;
/*  909 */     p_getBlendState_0_.srcFactor = blendState.srcFactor;
/*  910 */     p_getBlendState_0_.dstFactor = blendState.dstFactor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlendState(GlBlendState p_setBlendState_0_) {
/*  915 */     blendState.blend.setState(p_setBlendState_0_.enabled);
/*  916 */     blendFunc(p_setBlendState_0_.srcFactor, p_setBlendState_0_.dstFactor);
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  921 */     for (int i = 0; i < 8; i++)
/*      */     {
/*  923 */       lightState[i] = new BooleanState(16384 + i);
/*      */     }
/*      */     
/*  926 */     colorMaterialState = new ColorMaterialState(null);
/*  927 */     blendState = new BlendState(null);
/*  928 */     depthState = new DepthState(null);
/*  929 */     fogState = new FogState(null);
/*  930 */     cullState = new CullState(null);
/*  931 */     polygonOffsetState = new PolygonOffsetState(null);
/*  932 */     colorLogicState = new ColorLogicState(null);
/*  933 */     texGenState = new TexGenState(null);
/*  934 */     clearState = new ClearState(null);
/*  935 */     stencilState = new StencilState(null);
/*  936 */     normalizeState = new BooleanState(2977);
/*  937 */     textureState = new TextureState[32];
/*      */     
/*  939 */     for (int j = 0; j < textureState.length; j++)
/*      */     {
/*  941 */       textureState[j] = new TextureState(null);
/*      */     }
/*      */     
/*  944 */     activeShadeModel = 7425;
/*  945 */     rescaleNormalState = new BooleanState(32826);
/*  946 */     colorMaskState = new ColorMask(null);
/*  947 */     colorState = new Color();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class AlphaState
/*      */   {
/*      */     private AlphaState() {}
/*      */ 
/*      */ 
/*      */     
/*  958 */     public GlStateManager.BooleanState alphaTest = new GlStateManager.BooleanState(3008);
/*  959 */     public int func = 519;
/*  960 */     public float ref = -1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class BlendState
/*      */   {
/*      */     private BlendState() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  974 */     public GlStateManager.BooleanState blend = new GlStateManager.BooleanState(3042);
/*  975 */     public int srcFactor = 1;
/*  976 */     public int dstFactor = 0;
/*  977 */     public int srcFactorAlpha = 1;
/*  978 */     public int dstFactorAlpha = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static class BooleanState
/*      */   {
/*      */     private final int capability;
/*      */     
/*      */     private boolean currentState;
/*      */     
/*      */     public BooleanState(int capabilityIn) {
/*  989 */       this.capability = capabilityIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDisabled() {
/*  994 */       setState(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setEnabled() {
/*  999 */       setState(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setState(boolean state) {
/* 1004 */       if (state != this.currentState) {
/*      */         
/* 1006 */         this.currentState = state;
/*      */         
/* 1008 */         if (state) {
/*      */           
/* 1010 */           GL11.glEnable(this.capability);
/*      */         }
/*      */         else {
/*      */           
/* 1014 */           GL11.glDisable(this.capability);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ClearState
/*      */   {
/*      */     public double depth;
/*      */     public GlStateManager.Color color;
/*      */     
/*      */     private ClearState() {
/* 1027 */       this.depth = 1.0D;
/* 1028 */       this.color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Color
/*      */   {
/*      */     public float red;
/*      */     public float green;
/*      */     public float blue;
/*      */     public float alpha;
/*      */     
/*      */     public Color() {
/* 1041 */       this(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
/* 1046 */       this.red = 1.0F;
/* 1047 */       this.green = 1.0F;
/* 1048 */       this.blue = 1.0F;
/* 1049 */       this.alpha = 1.0F;
/* 1050 */       this.red = redIn;
/* 1051 */       this.green = greenIn;
/* 1052 */       this.blue = blueIn;
/* 1053 */       this.alpha = alphaIn;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ColorLogicState
/*      */   {
/*      */     public GlStateManager.BooleanState colorLogicOp;
/*      */     public int opcode;
/*      */     
/*      */     private ColorLogicState() {
/* 1064 */       this.colorLogicOp = new GlStateManager.BooleanState(3058);
/* 1065 */       this.opcode = 5379;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ColorMask
/*      */   {
/*      */     public boolean red;
/*      */     public boolean green;
/*      */     public boolean blue;
/*      */     public boolean alpha;
/*      */     
/*      */     private ColorMask() {
/* 1078 */       this.red = true;
/* 1079 */       this.green = true;
/* 1080 */       this.blue = true;
/* 1081 */       this.alpha = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ColorMaterialState
/*      */   {
/*      */     public GlStateManager.BooleanState colorMaterial;
/*      */     public int face;
/*      */     public int mode;
/*      */     
/*      */     private ColorMaterialState() {
/* 1093 */       this.colorMaterial = new GlStateManager.BooleanState(2903);
/* 1094 */       this.face = 1032;
/* 1095 */       this.mode = 5634;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum CullFace
/*      */   {
/* 1101 */     FRONT(1028),
/* 1102 */     BACK(1029),
/* 1103 */     FRONT_AND_BACK(1032);
/*      */     
/*      */     public final int mode;
/*      */ 
/*      */     
/*      */     CullFace(int modeIn) {
/* 1109 */       this.mode = modeIn;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CullState
/*      */   {
/*      */     public GlStateManager.BooleanState cullFace;
/*      */     public int mode;
/*      */     
/*      */     private CullState() {
/* 1120 */       this.cullFace = new GlStateManager.BooleanState(2884);
/* 1121 */       this.mode = 1029;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class DepthState
/*      */   {
/*      */     public GlStateManager.BooleanState depthTest;
/*      */     public boolean maskEnabled;
/*      */     public int depthFunc;
/*      */     
/*      */     private DepthState() {
/* 1133 */       this.depthTest = new GlStateManager.BooleanState(2929);
/* 1134 */       this.maskEnabled = true;
/* 1135 */       this.depthFunc = 513;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum DestFactor
/*      */   {
/* 1141 */     CONSTANT_ALPHA(32771),
/* 1142 */     CONSTANT_COLOR(32769),
/* 1143 */     DST_ALPHA(772),
/* 1144 */     DST_COLOR(774),
/* 1145 */     ONE(1),
/* 1146 */     ONE_MINUS_CONSTANT_ALPHA(32772),
/* 1147 */     ONE_MINUS_CONSTANT_COLOR(32770),
/* 1148 */     ONE_MINUS_DST_ALPHA(773),
/* 1149 */     ONE_MINUS_DST_COLOR(775),
/* 1150 */     ONE_MINUS_SRC_ALPHA(771),
/* 1151 */     ONE_MINUS_SRC_COLOR(769),
/* 1152 */     SRC_ALPHA(770),
/* 1153 */     SRC_COLOR(768),
/* 1154 */     ZERO(0);
/*      */     
/*      */     public final int factor;
/*      */ 
/*      */     
/*      */     DestFactor(int factorIn) {
/* 1160 */       this.factor = factorIn;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum FogMode
/*      */   {
/* 1166 */     LINEAR(9729),
/* 1167 */     EXP(2048),
/* 1168 */     EXP2(2049);
/*      */     
/*      */     public final int capabilityId;
/*      */ 
/*      */     
/*      */     FogMode(int capabilityIn) {
/* 1174 */       this.capabilityId = capabilityIn;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class FogState
/*      */   {
/*      */     public GlStateManager.BooleanState fog;
/*      */     public int mode;
/*      */     public float density;
/*      */     public float start;
/*      */     public float end;
/*      */     
/*      */     private FogState() {
/* 1188 */       this.fog = new GlStateManager.BooleanState(2912);
/* 1189 */       this.mode = 2048;
/* 1190 */       this.density = 1.0F;
/* 1191 */       this.end = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum LogicOp
/*      */   {
/* 1197 */     AND(5377),
/* 1198 */     AND_INVERTED(5380),
/* 1199 */     AND_REVERSE(5378),
/* 1200 */     CLEAR(5376),
/* 1201 */     COPY(5379),
/* 1202 */     COPY_INVERTED(5388),
/* 1203 */     EQUIV(5385),
/* 1204 */     INVERT(5386),
/* 1205 */     NAND(5390),
/* 1206 */     NOOP(5381),
/* 1207 */     NOR(5384),
/* 1208 */     OR(5383),
/* 1209 */     OR_INVERTED(5389),
/* 1210 */     OR_REVERSE(5387),
/* 1211 */     SET(5391),
/* 1212 */     XOR(5382);
/*      */     
/*      */     public final int opcode;
/*      */ 
/*      */     
/*      */     LogicOp(int opcodeIn) {
/* 1218 */       this.opcode = opcodeIn;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PolygonOffsetState
/*      */   {
/*      */     public GlStateManager.BooleanState polygonOffsetFill;
/*      */     public GlStateManager.BooleanState polygonOffsetLine;
/*      */     public float factor;
/*      */     public float units;
/*      */     
/*      */     private PolygonOffsetState() {
/* 1231 */       this.polygonOffsetFill = new GlStateManager.BooleanState(32823);
/* 1232 */       this.polygonOffsetLine = new GlStateManager.BooleanState(10754);
/*      */     }
/*      */   }
/*      */   
/*      */   public enum Profile
/*      */   {
/* 1238 */     DEFAULT
/*      */     {
/*      */       public void apply() {
/* 1241 */         GlStateManager.disableAlpha();
/* 1242 */         GlStateManager.alphaFunc(519, 0.0F);
/* 1243 */         GlStateManager.disableLighting();
/* 1244 */         GL11.glLightModel(2899, RenderHelper.setColorBuffer(0.2F, 0.2F, 0.2F, 1.0F));
/*      */         
/* 1246 */         for (int i = 0; i < 8; i++) {
/*      */           
/* 1248 */           GlStateManager.disableLight(i);
/* 1249 */           GL11.glLight(16384 + i, 4608, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 1250 */           GL11.glLight(16384 + i, 4611, RenderHelper.setColorBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/*      */           
/* 1252 */           if (i == 0) {
/*      */             
/* 1254 */             GL11.glLight(16384 + i, 4609, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 1.0F));
/* 1255 */             GL11.glLight(16384 + i, 4610, RenderHelper.setColorBuffer(1.0F, 1.0F, 1.0F, 1.0F));
/*      */           }
/*      */           else {
/*      */             
/* 1259 */             GL11.glLight(16384 + i, 4609, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 1260 */             GL11.glLight(16384 + i, 4610, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*      */           } 
/*      */         } 
/*      */         
/* 1264 */         GlStateManager.disableColorMaterial();
/* 1265 */         GlStateManager.colorMaterial(1032, 5634);
/* 1266 */         GlStateManager.disableDepth();
/* 1267 */         GlStateManager.depthFunc(513);
/* 1268 */         GlStateManager.depthMask(true);
/* 1269 */         GlStateManager.disableBlend();
/* 1270 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1271 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1272 */         GL14.glBlendEquation(32774);
/* 1273 */         GlStateManager.disableFog();
/* 1274 */         GL11.glFogi(2917, 2048);
/* 1275 */         GlStateManager.setFogDensity(1.0F);
/* 1276 */         GlStateManager.setFogStart(0.0F);
/* 1277 */         GlStateManager.setFogEnd(1.0F);
/* 1278 */         GL11.glFog(2918, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/*      */         
/* 1280 */         if ((GLContext.getCapabilities()).GL_NV_fog_distance)
/*      */         {
/* 1282 */           GL11.glFogi(2917, 34140);
/*      */         }
/*      */         
/* 1285 */         GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 1286 */         GlStateManager.disableColorLogic();
/* 1287 */         GlStateManager.colorLogicOp(5379);
/* 1288 */         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
/* 1289 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9216);
/* 1290 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9474, RenderHelper.setColorBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 1291 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9217, RenderHelper.setColorBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 1292 */         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
/* 1293 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9216);
/* 1294 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9474, RenderHelper.setColorBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 1295 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9217, RenderHelper.setColorBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 1296 */         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
/* 1297 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9216);
/* 1298 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9474, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/* 1299 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9217, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/* 1300 */         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
/* 1301 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
/* 1302 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/* 1303 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9217, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/* 1304 */         GlStateManager.setActiveTexture(0);
/* 1305 */         GL11.glTexParameteri(3553, 10240, 9729);
/* 1306 */         GL11.glTexParameteri(3553, 10241, 9986);
/* 1307 */         GL11.glTexParameteri(3553, 10242, 10497);
/* 1308 */         GL11.glTexParameteri(3553, 10243, 10497);
/* 1309 */         GL11.glTexParameteri(3553, 33085, 1000);
/* 1310 */         GL11.glTexParameteri(3553, 33083, 1000);
/* 1311 */         GL11.glTexParameteri(3553, 33082, -1000);
/* 1312 */         GL11.glTexParameterf(3553, 34049, 0.0F);
/* 1313 */         GL11.glTexEnvi(8960, 8704, 8448);
/* 1314 */         GL11.glTexEnv(8960, 8705, RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 0.0F));
/* 1315 */         GL11.glTexEnvi(8960, 34161, 8448);
/* 1316 */         GL11.glTexEnvi(8960, 34162, 8448);
/* 1317 */         GL11.glTexEnvi(8960, 34176, 5890);
/* 1318 */         GL11.glTexEnvi(8960, 34177, 34168);
/* 1319 */         GL11.glTexEnvi(8960, 34178, 34166);
/* 1320 */         GL11.glTexEnvi(8960, 34184, 5890);
/* 1321 */         GL11.glTexEnvi(8960, 34185, 34168);
/* 1322 */         GL11.glTexEnvi(8960, 34186, 34166);
/* 1323 */         GL11.glTexEnvi(8960, 34192, 768);
/* 1324 */         GL11.glTexEnvi(8960, 34193, 768);
/* 1325 */         GL11.glTexEnvi(8960, 34194, 770);
/* 1326 */         GL11.glTexEnvi(8960, 34200, 770);
/* 1327 */         GL11.glTexEnvi(8960, 34201, 770);
/* 1328 */         GL11.glTexEnvi(8960, 34202, 770);
/* 1329 */         GL11.glTexEnvf(8960, 34163, 1.0F);
/* 1330 */         GL11.glTexEnvf(8960, 3356, 1.0F);
/* 1331 */         GlStateManager.disableNormalize();
/* 1332 */         GlStateManager.shadeModel(7425);
/* 1333 */         GlStateManager.disableRescaleNormal();
/* 1334 */         GlStateManager.colorMask(true, true, true, true);
/* 1335 */         GlStateManager.clearDepth(1.0D);
/* 1336 */         GL11.glLineWidth(1.0F);
/* 1337 */         GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/* 1338 */         GL11.glPolygonMode(1028, 6914);
/* 1339 */         GL11.glPolygonMode(1029, 6914);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void clean() {}
/*      */     },
/* 1346 */     PLAYER_SKIN
/*      */     {
/*      */       public void apply() {
/* 1349 */         GlStateManager.enableBlend();
/* 1350 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */       }
/*      */ 
/*      */       
/*      */       public void clean() {
/* 1355 */         GlStateManager.disableBlend();
/*      */       }
/*      */     },
/* 1358 */     TRANSPARENT_MODEL
/*      */     {
/*      */       public void apply() {
/* 1361 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/* 1362 */         GlStateManager.depthMask(false);
/* 1363 */         GlStateManager.enableBlend();
/* 1364 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 1365 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*      */       }
/*      */ 
/*      */       
/*      */       public void clean() {
/* 1370 */         GlStateManager.disableBlend();
/* 1371 */         GlStateManager.alphaFunc(516, 0.1F);
/* 1372 */         GlStateManager.depthMask(true);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void apply();
/*      */ 
/*      */     
/*      */     public abstract void clean();
/*      */   }
/*      */ 
/*      */   
/*      */   public enum SourceFactor
/*      */   {
/* 1387 */     CONSTANT_ALPHA(32771),
/* 1388 */     CONSTANT_COLOR(32769),
/* 1389 */     DST_ALPHA(772),
/* 1390 */     DST_COLOR(774),
/* 1391 */     ONE(1),
/* 1392 */     ONE_MINUS_CONSTANT_ALPHA(32772),
/* 1393 */     ONE_MINUS_CONSTANT_COLOR(32770),
/* 1394 */     ONE_MINUS_DST_ALPHA(773),
/* 1395 */     ONE_MINUS_DST_COLOR(775),
/* 1396 */     ONE_MINUS_SRC_ALPHA(771),
/* 1397 */     ONE_MINUS_SRC_COLOR(769),
/* 1398 */     SRC_ALPHA(770),
/* 1399 */     SRC_ALPHA_SATURATE(776),
/* 1400 */     SRC_COLOR(768),
/* 1401 */     ZERO(0);
/*      */     
/*      */     public final int factor;
/*      */ 
/*      */     
/*      */     SourceFactor(int factorIn) {
/* 1407 */       this.factor = factorIn;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class StencilFunc
/*      */   {
/*      */     public int func;
/*      */     public int mask;
/*      */     
/*      */     private StencilFunc() {
/* 1418 */       this.func = 519;
/* 1419 */       this.mask = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class StencilState
/*      */   {
/*      */     public GlStateManager.StencilFunc func;
/*      */     public int mask;
/*      */     public int fail;
/*      */     public int zfail;
/*      */     public int zpass;
/*      */     
/*      */     private StencilState() {
/* 1433 */       this.func = new GlStateManager.StencilFunc(null);
/* 1434 */       this.mask = -1;
/* 1435 */       this.fail = 7680;
/* 1436 */       this.zfail = 7680;
/* 1437 */       this.zpass = 7680;
/*      */     }
/*      */   }
/*      */   
/*      */   public enum TexGen
/*      */   {
/* 1443 */     S,
/* 1444 */     T,
/* 1445 */     R,
/* 1446 */     Q;
/*      */   }
/*      */   
/*      */   static class TexGenCoord
/*      */   {
/*      */     public GlStateManager.BooleanState textureGen;
/*      */     public int coord;
/* 1453 */     public int param = -1;
/*      */ 
/*      */     
/*      */     public TexGenCoord(int coordIn, int capabilityIn) {
/* 1457 */       this.coord = coordIn;
/* 1458 */       this.textureGen = new GlStateManager.BooleanState(capabilityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TexGenState
/*      */   {
/*      */     public GlStateManager.TexGenCoord s;
/*      */     public GlStateManager.TexGenCoord t;
/*      */     public GlStateManager.TexGenCoord r;
/*      */     public GlStateManager.TexGenCoord q;
/*      */     
/*      */     private TexGenState() {
/* 1471 */       this.s = new GlStateManager.TexGenCoord(8192, 3168);
/* 1472 */       this.t = new GlStateManager.TexGenCoord(8193, 3169);
/* 1473 */       this.r = new GlStateManager.TexGenCoord(8194, 3170);
/* 1474 */       this.q = new GlStateManager.TexGenCoord(8195, 3171);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class TextureState
/*      */   {
/*      */     public GlStateManager.BooleanState texture2DState;
/*      */     public int textureName;
/*      */     
/*      */     private TextureState() {
/* 1485 */       this.texture2DState = new GlStateManager.BooleanState(3553);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\GlStateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
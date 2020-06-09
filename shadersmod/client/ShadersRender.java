/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ public class ShadersRender
/*     */ {
/*  34 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*     */ 
/*     */   
/*     */   public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
/*  38 */     frustum.setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTerrain(RenderGlobal renderGlobal, Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*  43 */     renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainSolid() {
/*  48 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  50 */       Shaders.fogEnabled = true;
/*  51 */       Shaders.useProgram(7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutoutMipped() {
/*  57 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  59 */       Shaders.useProgram(7);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutout() {
/*  65 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  67 */       Shaders.useProgram(7);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTerrain() {
/*  73 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  75 */       Shaders.useProgram(3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTranslucent() {
/*  81 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  83 */       if (Shaders.usedDepthBuffers >= 2) {
/*     */         
/*  85 */         GlStateManager.setActiveTexture(33995);
/*  86 */         Shaders.checkGLError("pre copy depth");
/*  87 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
/*  88 */         Shaders.checkGLError("copy depth");
/*  89 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/*  92 */       Shaders.useProgram(12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTranslucent() {
/*  98 */     if (Shaders.isRenderingWorld)
/*     */     {
/* 100 */       Shaders.useProgram(3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand0(EntityRenderer er, float par1, int par2) {
/* 106 */     if (!Shaders.isShadowPass) {
/*     */       
/* 108 */       boolean flag = Shaders.isItemToRenderMainTranslucent();
/* 109 */       boolean flag1 = Shaders.isItemToRenderOffTranslucent();
/*     */       
/* 111 */       if (!flag || !flag1) {
/*     */         
/* 113 */         Shaders.readCenterDepth();
/* 114 */         Shaders.beginHand();
/* 115 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 116 */         Shaders.setSkipRenderHands(flag, flag1);
/* 117 */         er.renderHand(par1, par2, true, false, false);
/* 118 */         Shaders.endHand();
/* 119 */         Shaders.setHandsRendered(!flag, !flag1);
/* 120 */         Shaders.setSkipRenderHands(false, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand1(EntityRenderer er, float par1, int par2) {
/* 127 */     if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
/*     */       
/* 129 */       Shaders.readCenterDepth();
/* 130 */       GlStateManager.enableBlend();
/* 131 */       Shaders.beginHand();
/* 132 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 133 */       Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
/* 134 */       er.renderHand(par1, par2, true, false, true);
/* 135 */       Shaders.endHand();
/* 136 */       Shaders.setHandsRendered(true, true);
/* 137 */       Shaders.setSkipRenderHands(false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderItemFP(ItemRenderer itemRenderer, float par1, boolean renderTranslucent) {
/* 143 */     Shaders.setRenderingFirstPersonHand(true);
/* 144 */     GlStateManager.depthMask(true);
/*     */     
/* 146 */     if (renderTranslucent) {
/*     */       
/* 148 */       GlStateManager.depthFunc(519);
/* 149 */       GL11.glPushMatrix();
/* 150 */       IntBuffer intbuffer = Shaders.activeDrawBuffers;
/* 151 */       Shaders.setDrawBuffers(Shaders.drawBuffersNone);
/* 152 */       Shaders.renderItemKeepDepthMask = true;
/* 153 */       itemRenderer.renderItemInFirstPerson(par1);
/* 154 */       Shaders.renderItemKeepDepthMask = false;
/* 155 */       Shaders.setDrawBuffers(intbuffer);
/* 156 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 159 */     GlStateManager.depthFunc(515);
/* 160 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 161 */     itemRenderer.renderItemInFirstPerson(par1);
/* 162 */     Shaders.setRenderingFirstPersonHand(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderFPOverlay(EntityRenderer er, float par1, int par2) {
/* 167 */     if (!Shaders.isShadowPass) {
/*     */       
/* 169 */       Shaders.beginFPOverlay();
/* 170 */       er.renderHand(par1, par2, false, true, false);
/* 171 */       Shaders.endFPOverlay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginBlockDamage() {
/* 177 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 179 */       Shaders.useProgram(11);
/*     */       
/* 181 */       if (Shaders.programsID[11] == Shaders.programsID[7]) {
/*     */         
/* 183 */         Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
/* 184 */         GlStateManager.depthMask(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endBlockDamage() {
/* 191 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 193 */       GlStateManager.depthMask(true);
/* 194 */       Shaders.useProgram(3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderShadowMap(EntityRenderer entityRenderer, int pass, float partialTicks, long finishTimeNano) {
/* 200 */     if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
/*     */       
/* 202 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 203 */       minecraft.mcProfiler.endStartSection("shadow pass");
/* 204 */       RenderGlobal renderglobal = minecraft.renderGlobal;
/* 205 */       Shaders.isShadowPass = true;
/* 206 */       Shaders.shadowPassCounter = Shaders.shadowPassInterval;
/* 207 */       Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.thirdPersonView;
/* 208 */       minecraft.gameSettings.thirdPersonView = 1;
/* 209 */       Shaders.checkGLError("pre shadow");
/* 210 */       GL11.glMatrixMode(5889);
/* 211 */       GL11.glPushMatrix();
/* 212 */       GL11.glMatrixMode(5888);
/* 213 */       GL11.glPushMatrix();
/* 214 */       minecraft.mcProfiler.endStartSection("shadow clear");
/* 215 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
/* 216 */       Shaders.checkGLError("shadow bind sfb");
/* 217 */       Shaders.useProgram(30);
/* 218 */       minecraft.mcProfiler.endStartSection("shadow camera");
/* 219 */       entityRenderer.setupCameraTransform(partialTicks, 2);
/* 220 */       Shaders.setCameraShadow(partialTicks);
/* 221 */       ActiveRenderInfo.updateRenderInfo((EntityPlayer)minecraft.player, (minecraft.gameSettings.thirdPersonView == 2));
/* 222 */       Shaders.checkGLError("shadow camera");
/* 223 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 224 */       Shaders.checkGLError("shadow drawbuffers");
/* 225 */       GL11.glReadBuffer(0);
/* 226 */       Shaders.checkGLError("shadow readbuffer");
/* 227 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
/*     */       
/* 229 */       if (Shaders.usedShadowColorBuffers != 0)
/*     */       {
/* 231 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
/*     */       }
/*     */       
/* 234 */       Shaders.checkFramebufferStatus("shadow fb");
/* 235 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 236 */       GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
/* 237 */       Shaders.checkGLError("shadow clear");
/* 238 */       minecraft.mcProfiler.endStartSection("shadow frustum");
/* 239 */       ClippingHelper clippinghelper = ClippingHelperShadow.getInstance();
/* 240 */       minecraft.mcProfiler.endStartSection("shadow culling");
/* 241 */       Frustum frustum = new Frustum(clippinghelper);
/* 242 */       Entity entity = minecraft.getRenderViewEntity();
/* 243 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 244 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 245 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 246 */       frustum.setPosition(d0, d1, d2);
/* 247 */       GlStateManager.shadeModel(7425);
/* 248 */       GlStateManager.enableDepth();
/* 249 */       GlStateManager.depthFunc(515);
/* 250 */       GlStateManager.depthMask(true);
/* 251 */       GlStateManager.colorMask(true, true, true, true);
/* 252 */       GlStateManager.disableCull();
/* 253 */       minecraft.mcProfiler.endStartSection("shadow prepareterrain");
/* 254 */       minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 255 */       minecraft.mcProfiler.endStartSection("shadow setupterrain");
/* 256 */       int i = 0;
/* 257 */       i = entityRenderer.frameCount;
/* 258 */       entityRenderer.frameCount = i + 1;
/* 259 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, i, minecraft.player.isSpectator());
/* 260 */       minecraft.mcProfiler.endStartSection("shadow updatechunks");
/* 261 */       minecraft.mcProfiler.endStartSection("shadow terrain");
/* 262 */       GlStateManager.matrixMode(5888);
/* 263 */       GlStateManager.pushMatrix();
/* 264 */       GlStateManager.disableAlpha();
/* 265 */       renderglobal.renderBlockLayer(BlockRenderLayer.SOLID, partialTicks, 2, entity);
/* 266 */       Shaders.checkGLError("shadow terrain solid");
/* 267 */       GlStateManager.enableAlpha();
/* 268 */       renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
/* 269 */       Shaders.checkGLError("shadow terrain cutoutmipped");
/* 270 */       minecraft.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/* 271 */       renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT, partialTicks, 2, entity);
/* 272 */       Shaders.checkGLError("shadow terrain cutout");
/* 273 */       minecraft.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/* 274 */       GlStateManager.shadeModel(7424);
/* 275 */       GlStateManager.alphaFunc(516, 0.1F);
/* 276 */       GlStateManager.matrixMode(5888);
/* 277 */       GlStateManager.popMatrix();
/* 278 */       GlStateManager.pushMatrix();
/* 279 */       minecraft.mcProfiler.endStartSection("shadow entities");
/*     */       
/* 281 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*     */       {
/* 283 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*     */       }
/*     */       
/* 286 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 287 */       Shaders.checkGLError("shadow entities");
/* 288 */       GlStateManager.matrixMode(5888);
/* 289 */       GlStateManager.popMatrix();
/* 290 */       GlStateManager.depthMask(true);
/* 291 */       GlStateManager.disableBlend();
/* 292 */       GlStateManager.enableCull();
/* 293 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 294 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 296 */       if (Shaders.usedShadowDepthBuffers >= 2) {
/*     */         
/* 298 */         GlStateManager.setActiveTexture(33989);
/* 299 */         Shaders.checkGLError("pre copy shadow depth");
/* 300 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
/* 301 */         Shaders.checkGLError("copy shadow depth");
/* 302 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/* 305 */       GlStateManager.disableBlend();
/* 306 */       GlStateManager.depthMask(true);
/* 307 */       minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 308 */       GlStateManager.shadeModel(7425);
/* 309 */       Shaders.checkGLError("shadow pre-translucent");
/* 310 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 311 */       Shaders.checkGLError("shadow drawbuffers pre-translucent");
/* 312 */       Shaders.checkFramebufferStatus("shadow pre-translucent");
/*     */       
/* 314 */       if (Shaders.isRenderShadowTranslucent()) {
/*     */         
/* 316 */         minecraft.mcProfiler.endStartSection("shadow translucent");
/* 317 */         renderglobal.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, partialTicks, 2, entity);
/* 318 */         Shaders.checkGLError("shadow translucent");
/*     */       } 
/*     */       
/* 321 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/*     */         
/* 323 */         RenderHelper.enableStandardItemLighting();
/* 324 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 325 */         renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 326 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 327 */         RenderHelper.disableStandardItemLighting();
/* 328 */         Shaders.checkGLError("shadow entities 1");
/*     */       } 
/*     */       
/* 331 */       GlStateManager.shadeModel(7424);
/* 332 */       GlStateManager.depthMask(true);
/* 333 */       GlStateManager.enableCull();
/* 334 */       GlStateManager.disableBlend();
/* 335 */       GL11.glFlush();
/* 336 */       Shaders.checkGLError("shadow flush");
/* 337 */       Shaders.isShadowPass = false;
/* 338 */       minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
/* 339 */       minecraft.mcProfiler.endStartSection("shadow postprocess");
/*     */       
/* 341 */       if (Shaders.hasGlGenMipmap) {
/*     */         
/* 343 */         if (Shaders.usedShadowDepthBuffers >= 1) {
/*     */           
/* 345 */           if (Shaders.shadowMipmapEnabled[0]) {
/*     */             
/* 347 */             GlStateManager.setActiveTexture(33988);
/* 348 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
/* 349 */             GL30.glGenerateMipmap(3553);
/* 350 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 353 */           if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
/*     */             
/* 355 */             GlStateManager.setActiveTexture(33989);
/* 356 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
/* 357 */             GL30.glGenerateMipmap(3553);
/* 358 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 361 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */         
/* 364 */         if (Shaders.usedShadowColorBuffers >= 1) {
/*     */           
/* 366 */           if (Shaders.shadowColorMipmapEnabled[0]) {
/*     */             
/* 368 */             GlStateManager.setActiveTexture(33997);
/* 369 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
/* 370 */             GL30.glGenerateMipmap(3553);
/* 371 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 374 */           if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
/*     */             
/* 376 */             GlStateManager.setActiveTexture(33998);
/* 377 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
/* 378 */             GL30.glGenerateMipmap(3553);
/* 379 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 382 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */       } 
/*     */       
/* 386 */       Shaders.checkGLError("shadow postprocess");
/* 387 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
/* 388 */       GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 389 */       Shaders.activeDrawBuffers = null;
/* 390 */       minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 391 */       Shaders.useProgram(7);
/* 392 */       GL11.glMatrixMode(5888);
/* 393 */       GL11.glPopMatrix();
/* 394 */       GL11.glMatrixMode(5889);
/* 395 */       GL11.glPopMatrix();
/* 396 */       GL11.glMatrixMode(5888);
/* 397 */       Shaders.checkGLError("shadow end");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void preRenderChunkLayer(BlockRenderLayer blockLayerIn) {
/* 403 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 405 */       GlStateManager.disableCull();
/*     */     }
/*     */     
/* 408 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 410 */       GL11.glEnableClientState(32885);
/* 411 */       GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 412 */       GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 413 */       GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void postRenderChunkLayer(BlockRenderLayer blockLayerIn) {
/* 419 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 421 */       GL11.glDisableClientState(32885);
/* 422 */       GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 423 */       GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 424 */       GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */     
/* 427 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 429 */       GlStateManager.enableCull();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupArrayPointersVbo() {
/* 435 */     int i = 14;
/* 436 */     GL11.glVertexPointer(3, 5126, 56, 0L);
/* 437 */     GL11.glColorPointer(4, 5121, 56, 12L);
/* 438 */     GL11.glTexCoordPointer(2, 5126, 56, 16L);
/* 439 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 440 */     GL11.glTexCoordPointer(2, 5122, 56, 24L);
/* 441 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 442 */     GL11.glNormalPointer(5120, 56, 28L);
/* 443 */     GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
/* 444 */     GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
/* 445 */     GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beaconBeamBegin() {
/* 450 */     Shaders.useProgram(14);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad1() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad2() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw1() {}
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw2() {
/* 467 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintBegin() {
/* 472 */     Shaders.useProgram(17);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintEnd() {
/* 477 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 479 */       if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands())
/*     */       {
/* 481 */         Shaders.useProgram(19);
/*     */       }
/*     */       else
/*     */       {
/* 485 */         Shaders.useProgram(16);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 490 */       Shaders.useProgram(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderEndPortal(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float offset) {
/* 496 */     if (!Shaders.isShadowPass && Shaders.programsID[Shaders.activeProgram] == 0)
/*     */     {
/* 498 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 502 */     GlStateManager.disableLighting();
/* 503 */     Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
/* 504 */     Tessellator tessellator = Tessellator.getInstance();
/* 505 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 506 */     bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
/* 507 */     float f = 0.5F;
/* 508 */     float f1 = f * 0.15F;
/* 509 */     float f2 = f * 0.3F;
/* 510 */     float f3 = f * 0.4F;
/* 511 */     float f4 = 0.0F;
/* 512 */     float f5 = 0.2F;
/* 513 */     float f6 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
/* 514 */     int i = 240;
/*     */     
/* 516 */     if (te.shouldRenderFace(EnumFacing.SOUTH)) {
/*     */       
/* 518 */       bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 519 */       bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 520 */       bufferbuilder.pos(x + 1.0D, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 521 */       bufferbuilder.pos(x, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 524 */     if (te.shouldRenderFace(EnumFacing.NORTH)) {
/*     */       
/* 526 */       bufferbuilder.pos(x, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 527 */       bufferbuilder.pos(x + 1.0D, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 528 */       bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 529 */       bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 532 */     if (te.shouldRenderFace(EnumFacing.EAST)) {
/*     */       
/* 534 */       bufferbuilder.pos(x + 1.0D, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 535 */       bufferbuilder.pos(x + 1.0D, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 536 */       bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 537 */       bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 540 */     if (te.shouldRenderFace(EnumFacing.WEST)) {
/*     */       
/* 542 */       bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 543 */       bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 544 */       bufferbuilder.pos(x, y + 1.0D, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 545 */       bufferbuilder.pos(x, y + 1.0D, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 548 */     if (te.shouldRenderFace(EnumFacing.DOWN)) {
/*     */       
/* 550 */       bufferbuilder.pos(x, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 551 */       bufferbuilder.pos(x + 1.0D, y, z).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 552 */       bufferbuilder.pos(x + 1.0D, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 553 */       bufferbuilder.pos(x, y, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 556 */     if (te.shouldRenderFace(EnumFacing.UP)) {
/*     */       
/* 558 */       bufferbuilder.pos(x, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 559 */       bufferbuilder.pos(x + 1.0D, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 560 */       bufferbuilder.pos(x + 1.0D, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 561 */       bufferbuilder.pos(x, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/*     */     } 
/*     */     
/* 564 */     tessellator.draw();
/* 565 */     GlStateManager.enableLighting();
/* 566 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShadersRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
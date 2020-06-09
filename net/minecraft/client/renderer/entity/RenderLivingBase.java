/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.List;
/*     */ import me.nzxter.bettercraft.mods.esp.ESP;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RenderLivingBase<T extends EntityLivingBase>
/*     */   extends Render<T>
/*     */ {
/*  35 */   private static final Logger LOGGER = LogManager.getLogger();
/*  36 */   private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
/*     */   public ModelBase mainModel;
/*  38 */   protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
/*  39 */   protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
/*     */   protected boolean renderMarker;
/*  41 */   public static float NAME_TAG_RANGE = 64.0F;
/*  42 */   public static float NAME_TAG_RANGE_SNEAK = 32.0F;
/*     */   public float renderLimbSwing;
/*     */   public float renderLimbSwingAmount;
/*     */   public float renderAgeInTicks;
/*     */   public float renderHeadYaw;
/*     */   public float renderHeadPitch;
/*     */   public float renderScaleFactor;
/*  49 */   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
/*     */ 
/*     */   
/*     */   public RenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/*  53 */     super(renderManagerIn);
/*  54 */     this.mainModel = modelBaseIn;
/*  55 */     this.shadowSize = shadowSizeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
/*  60 */     return this.layerRenderers.add((LayerRenderer<T>)layer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBase getMainModel() {
/*  65 */     return this.mainModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
/*     */     float f;
/*  77 */     for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     while (f >= 180.0F)
/*     */     {
/*  84 */       f -= 360.0F;
/*     */     }
/*     */     
/*  87 */     return prevYawOffset + partialTicks * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 100 */     if (entity instanceof EntityPlayer && entity != (Minecraft.getMinecraft()).player) {
/* 101 */       ESP.onRender();
/*     */     }
/*     */ 
/*     */     
/* 105 */     if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, new Object[] { entity, this, Float.valueOf(partialTicks), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/*     */       
/* 107 */       if (animateModelLiving)
/*     */       {
/* 109 */         ((EntityLivingBase)entity).limbSwingAmount = 1.0F;
/*     */       }
/*     */       
/* 112 */       GlStateManager.pushMatrix();
/* 113 */       GlStateManager.disableCull();
/* 114 */       this.mainModel.swingProgress = getSwingProgress(entity, partialTicks);
/* 115 */       this.mainModel.isRiding = entity.isRiding();
/*     */       
/* 117 */       if (Reflector.ForgeEntity_shouldRiderSit.exists())
/*     */       {
/* 119 */         this.mainModel.isRiding = (entity.isRiding() && entity.getRidingEntity() != null && Reflector.callBoolean(entity.getRidingEntity(), Reflector.ForgeEntity_shouldRiderSit, new Object[0]));
/*     */       }
/*     */       
/* 122 */       this.mainModel.isChild = entity.isChild();
/*     */ 
/*     */       
/*     */       try {
/* 126 */         float f = interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
/* 127 */         float f1 = interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
/* 128 */         float f2 = f1 - f;
/*     */         
/* 130 */         if (this.mainModel.isRiding && entity.getRidingEntity() instanceof EntityLivingBase) {
/*     */           
/* 132 */           EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
/* 133 */           f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
/* 134 */           f2 = f1 - f;
/* 135 */           float f3 = MathHelper.wrapDegrees(f2);
/*     */           
/* 137 */           if (f3 < -85.0F)
/*     */           {
/* 139 */             f3 = -85.0F;
/*     */           }
/*     */           
/* 142 */           if (f3 >= 85.0F)
/*     */           {
/* 144 */             f3 = 85.0F;
/*     */           }
/*     */           
/* 147 */           f = f1 - f3;
/*     */           
/* 149 */           if (f3 * f3 > 2500.0F)
/*     */           {
/* 151 */             f += f3 * 0.2F;
/*     */           }
/*     */           
/* 154 */           f2 = f1 - f;
/*     */         } 
/*     */         
/* 157 */         float f7 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/* 158 */         renderLivingAt(entity, x, y, z);
/* 159 */         float f8 = handleRotationFloat(entity, partialTicks);
/* 160 */         rotateCorpse(entity, f8, f, partialTicks);
/* 161 */         float f4 = prepareScale(entity, partialTicks);
/* 162 */         float f5 = 0.0F;
/* 163 */         float f6 = 0.0F;
/*     */         
/* 165 */         if (!entity.isRiding()) {
/*     */           
/* 167 */           f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
/* 168 */           f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0F - partialTicks);
/*     */           
/* 170 */           if (entity.isChild())
/*     */           {
/* 172 */             f6 *= 3.0F;
/*     */           }
/*     */           
/* 175 */           if (f5 > 1.0F)
/*     */           {
/* 177 */             f5 = 1.0F;
/*     */           }
/*     */         } 
/*     */         
/* 181 */         GlStateManager.enableAlpha();
/* 182 */         this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
/* 183 */         this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, (Entity)entity);
/*     */         
/* 185 */         if (CustomEntityModels.isActive()) {
/*     */           
/* 187 */           this.renderLimbSwing = f6;
/* 188 */           this.renderLimbSwingAmount = f5;
/* 189 */           this.renderAgeInTicks = f8;
/* 190 */           this.renderHeadYaw = f2;
/* 191 */           this.renderHeadPitch = f7;
/* 192 */           this.renderScaleFactor = f4;
/*     */         } 
/*     */         
/* 195 */         if (this.renderOutlines) {
/*     */           
/* 197 */           boolean flag1 = setScoreTeamColor(entity);
/* 198 */           GlStateManager.enableColorMaterial();
/* 199 */           GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */           
/* 201 */           if (!this.renderMarker)
/*     */           {
/* 203 */             renderModel(entity, f6, f5, f8, f2, f7, f4);
/*     */           }
/*     */           
/* 206 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
/*     */           {
/* 208 */             renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
/*     */           }
/*     */           
/* 211 */           GlStateManager.disableOutlineMode();
/* 212 */           GlStateManager.disableColorMaterial();
/*     */           
/* 214 */           if (flag1)
/*     */           {
/* 216 */             unsetScoreTeamColor();
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 221 */           boolean flag = setDoRenderBrightness(entity, partialTicks);
/* 222 */           renderModel(entity, f6, f5, f8, f2, f7, f4);
/*     */           
/* 224 */           if (flag)
/*     */           {
/* 226 */             unsetBrightness();
/*     */           }
/*     */           
/* 229 */           GlStateManager.depthMask(true);
/*     */           
/* 231 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
/*     */           {
/* 233 */             renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
/*     */           }
/*     */         } 
/*     */         
/* 237 */         GlStateManager.disableRescaleNormal();
/*     */       }
/* 239 */       catch (Exception exception1) {
/*     */         
/* 241 */         LOGGER.error("Couldn't render entity", exception1);
/*     */       } 
/*     */       
/* 244 */       GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 245 */       GlStateManager.enableTexture2D();
/* 246 */       GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 247 */       GlStateManager.enableCull();
/* 248 */       GlStateManager.popMatrix();
/* 249 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */       
/* 251 */       if (Reflector.RenderLivingEvent_Post_Constructor.exists())
/*     */       {
/* 253 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, new Object[] { entity, this, Float.valueOf(partialTicks), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float prepareScale(T entitylivingbaseIn, float partialTicks) {
/* 260 */     GlStateManager.enableRescaleNormal();
/* 261 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 262 */     preRenderCallback(entitylivingbaseIn, partialTicks);
/* 263 */     float f = 0.0625F;
/* 264 */     GlStateManager.translate(0.0F, -1.501F, 0.0F);
/* 265 */     return 0.0625F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setScoreTeamColor(T entityLivingBaseIn) {
/* 270 */     GlStateManager.disableLighting();
/* 271 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 272 */     GlStateManager.disableTexture2D();
/* 273 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void unsetScoreTeamColor() {
/* 279 */     GlStateManager.enableLighting();
/* 280 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 281 */     GlStateManager.enableTexture2D();
/* 282 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/* 290 */     boolean flag = func_193115_c(entitylivingbaseIn);
/* 291 */     boolean flag1 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).player));
/*     */     
/* 293 */     if (flag || flag1) {
/*     */       
/* 295 */       if (!bindEntityTexture(entitylivingbaseIn)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 300 */       if (flag1)
/*     */       {
/* 302 */         GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
/*     */       }
/*     */       
/* 305 */       this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*     */       
/* 307 */       if (flag1)
/*     */       {
/* 309 */         GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_193115_c(T p_193115_1_) {
/* 316 */     return !(p_193115_1_.isInvisible() && !this.renderOutlines);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
/* 321 */     return setBrightness(entityLivingBaseIn, partialTicks, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 326 */     float f = entitylivingbaseIn.getBrightness();
/* 327 */     int i = getColorMultiplier(entitylivingbaseIn, f, partialTicks);
/* 328 */     boolean flag = ((i >> 24 & 0xFF) > 0);
/* 329 */     boolean flag1 = !(((EntityLivingBase)entitylivingbaseIn).hurtTime <= 0 && ((EntityLivingBase)entitylivingbaseIn).deathTime <= 0);
/*     */     
/* 331 */     if (!flag && !flag1)
/*     */     {
/* 333 */       return false;
/*     */     }
/* 335 */     if (!flag && !combineTextures)
/*     */     {
/* 337 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 341 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 342 */     GlStateManager.enableTexture2D();
/* 343 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 344 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 345 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 346 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 347 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 348 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 349 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 350 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 351 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 352 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 353 */     GlStateManager.enableTexture2D();
/* 354 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 355 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
/* 356 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
/* 357 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 358 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
/* 359 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 360 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 361 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
/* 362 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 363 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 364 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 365 */     this.brightnessBuffer.position(0);
/*     */     
/* 367 */     if (flag1) {
/*     */       
/* 369 */       this.brightnessBuffer.put(1.0F);
/* 370 */       this.brightnessBuffer.put(0.0F);
/* 371 */       this.brightnessBuffer.put(0.0F);
/* 372 */       this.brightnessBuffer.put(0.3F);
/*     */       
/* 374 */       if (Config.isShaders())
/*     */       {
/* 376 */         Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 381 */       float f1 = (i >> 24 & 0xFF) / 255.0F;
/* 382 */       float f2 = (i >> 16 & 0xFF) / 255.0F;
/* 383 */       float f3 = (i >> 8 & 0xFF) / 255.0F;
/* 384 */       float f4 = (i & 0xFF) / 255.0F;
/* 385 */       this.brightnessBuffer.put(f2);
/* 386 */       this.brightnessBuffer.put(f3);
/* 387 */       this.brightnessBuffer.put(f4);
/* 388 */       this.brightnessBuffer.put(1.0F - f1);
/*     */       
/* 390 */       if (Config.isShaders())
/*     */       {
/* 392 */         Shaders.setEntityColor(f2, f3, f4, 1.0F - f1);
/*     */       }
/*     */     } 
/*     */     
/* 396 */     this.brightnessBuffer.flip();
/* 397 */     GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
/* 398 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 399 */     GlStateManager.enableTexture2D();
/* 400 */     GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
/* 401 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 402 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 403 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
/* 404 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
/* 405 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 406 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 407 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 408 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 409 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 410 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 411 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unsetBrightness() {
/* 417 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 418 */     GlStateManager.enableTexture2D();
/* 419 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 420 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 421 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 422 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 423 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 424 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 425 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 426 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 427 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
/* 428 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 429 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
/* 430 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 431 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 432 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 433 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 434 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 435 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 436 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 437 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 438 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 439 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 440 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 441 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 442 */     GlStateManager.disableTexture2D();
/* 443 */     GlStateManager.bindTexture(0);
/* 444 */     GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 445 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 446 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 447 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 448 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 449 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 450 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 451 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 452 */     GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 453 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */     
/* 455 */     if (Config.isShaders())
/*     */     {
/* 457 */       Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
/* 466 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(T entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 471 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*     */     
/* 473 */     if (((EntityLivingBase)entityLiving).deathTime > 0) {
/*     */       
/* 475 */       float f = (((EntityLivingBase)entityLiving).deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/* 476 */       f = MathHelper.sqrt(f);
/*     */       
/* 478 */       if (f > 1.0F)
/*     */       {
/* 480 */         f = 1.0F;
/*     */       }
/*     */       
/* 483 */       GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 487 */       String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
/*     */       
/* 489 */       if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s) || "Nzxter".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer)entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
/*     */         
/* 491 */         GlStateManager.translate(0.0F, ((EntityLivingBase)entityLiving).height + 0.1F, 0.0F);
/* 492 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSwingProgress(T livingBase, float partialTickTime) {
/* 502 */     return livingBase.getSwingProgress(partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float handleRotationFloat(T livingBase, float partialTicks) {
/* 510 */     return ((EntityLivingBase)livingBase).ticksExisted + partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLayers(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
/* 515 */     for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
/*     */       
/* 517 */       boolean flag = setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
/* 518 */       layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
/*     */       
/* 520 */       if (flag)
/*     */       {
/* 522 */         unsetBrightness();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 529 */     return 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 537 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderName(T entity, double x, double y, double z) {
/* 549 */     if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/*     */       
/* 551 */       if (canRenderName(entity)) {
/*     */         
/* 553 */         double d0 = entity.getDistanceSqToEntity(this.renderManager.renderViewEntity);
/* 554 */         float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
/*     */         
/* 556 */         if (d0 < (f * f)) {
/*     */           
/* 558 */           String s = entity.getDisplayName().getFormattedText();
/* 559 */           GlStateManager.alphaFunc(516, 0.1F);
/* 560 */           renderEntityName(entity, x, y, z, s, d0);
/*     */         } 
/*     */       } 
/*     */       
/* 564 */       if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists())
/*     */       {
/* 566 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 573 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).player;
/* 574 */     boolean flag = !entity.isInvisibleToPlayer((EntityPlayer)entityplayersp);
/*     */     
/* 576 */     if (entity != entityplayersp) {
/*     */       
/* 578 */       Team team = entity.getTeam();
/* 579 */       Team team1 = entityplayersp.getTeam();
/*     */       
/* 581 */       if (team != null) {
/*     */         
/* 583 */         Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
/*     */         
/* 585 */         switch (team$enumvisible) {
/*     */           
/*     */           case null:
/* 588 */             return flag;
/*     */           
/*     */           case NEVER:
/* 591 */             return false;
/*     */           
/*     */           case HIDE_FOR_OTHER_TEAMS:
/* 594 */             return (team1 == null) ? flag : ((team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag)));
/*     */           
/*     */           case HIDE_FOR_OWN_TEAM:
/* 597 */             return (team1 == null) ? flag : ((!team.isSameTeam(team1) && flag));
/*     */         } 
/*     */         
/* 600 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 605 */     return (Minecraft.isGuiEnabled() && entity != this.renderManager.renderViewEntity && flag && !entity.isBeingRidden());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LayerRenderer<T>> getLayerRenderers() {
/* 610 */     return this.layerRenderers;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 615 */     int[] aint = TEXTURE_BRIGHTNESS.getTextureData();
/*     */     
/* 617 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 619 */       aint[i] = -1;
/*     */     }
/*     */     
/* 622 */     TEXTURE_BRIGHTNESS.updateDynamicTexture();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
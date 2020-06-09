/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.IEntityRenderer;
/*     */ import optifine.Config;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public abstract class Render<T extends Entity> implements IEntityRenderer {
/*  31 */   private static final ResourceLocation SHADOW_TEXTURES = new ResourceLocation("textures/misc/shadow.png");
/*     */ 
/*     */   
/*     */   protected final RenderManager renderManager;
/*     */   
/*     */   public float shadowSize;
/*     */   
/*  38 */   protected float shadowOpaque = 1.0F;
/*     */   protected boolean renderOutlines;
/*  40 */   private Class entityClass = null;
/*  41 */   private ResourceLocation locationTextureCustom = null;
/*     */ 
/*     */   
/*     */   protected Render(RenderManager renderManager) {
/*  45 */     this.renderManager = renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/*  50 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  55 */     AxisAlignedBB axisalignedbb = livingEntity.getRenderBoundingBox().expandXyz(0.5D);
/*     */     
/*  57 */     if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D)
/*     */     {
/*  59 */       axisalignedbb = new AxisAlignedBB(((Entity)livingEntity).posX - 2.0D, ((Entity)livingEntity).posY - 2.0D, ((Entity)livingEntity).posZ - 2.0D, ((Entity)livingEntity).posX + 2.0D, ((Entity)livingEntity).posY + 2.0D, ((Entity)livingEntity).posZ + 2.0D);
/*     */     }
/*     */     
/*  62 */     return (livingEntity.isInRangeToRender3d(camX, camY, camZ) && (((Entity)livingEntity).ignoreFrustumCheck || camera.isBoundingBoxInFrustum(axisalignedbb)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  70 */     if (!this.renderOutlines)
/*     */     {
/*  72 */       renderName(entity, x, y, z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getTeamColor(T entityIn) {
/*  78 */     int i = 16777215;
/*  79 */     ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entityIn.getTeam();
/*     */     
/*  81 */     if (scoreplayerteam != null) {
/*     */       
/*  83 */       String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
/*     */       
/*  85 */       if (s.length() >= 2)
/*     */       {
/*  87 */         i = getFontRendererFromRenderManager().getColorCode(s.charAt(1));
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(T entity, double x, double y, double z) {
/*  96 */     if (canRenderName(entity))
/*     */     {
/*  98 */       renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 104 */     return (entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderEntityName(T entityIn, double x, double y, double z, String name, double distanceSq) {
/* 109 */     renderLivingLabel(entityIn, name, x, y, z, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected abstract ResourceLocation getEntityTexture(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bindEntityTexture(T entity) {
/* 121 */     ResourceLocation resourcelocation = getEntityTexture(entity);
/*     */     
/* 123 */     if (this.locationTextureCustom != null)
/*     */     {
/* 125 */       resourcelocation = this.locationTextureCustom;
/*     */     }
/*     */     
/* 128 */     if (resourcelocation == null)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 134 */     bindTexture(resourcelocation);
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation location) {
/* 141 */     this.renderManager.renderEngine.bindTexture(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
/* 149 */     GlStateManager.disableLighting();
/* 150 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 151 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/* 152 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 153 */     GlStateManager.pushMatrix();
/* 154 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 155 */     float f = entity.width * 1.4F;
/* 156 */     GlStateManager.scale(f, f, f);
/* 157 */     Tessellator tessellator = Tessellator.getInstance();
/* 158 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 159 */     float f1 = 0.5F;
/* 160 */     float f2 = 0.0F;
/* 161 */     float f3 = entity.height / f;
/* 162 */     float f4 = (float)(entity.posY - (entity.getEntityBoundingBox()).minY);
/* 163 */     GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 164 */     GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
/* 165 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 166 */     float f5 = 0.0F;
/* 167 */     int i = 0;
/* 168 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 170 */     while (f3 > 0.0F) {
/*     */       
/* 172 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/* 173 */       bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 174 */       float f6 = textureatlassprite2.getMinU();
/* 175 */       float f7 = textureatlassprite2.getMinV();
/* 176 */       float f8 = textureatlassprite2.getMaxU();
/* 177 */       float f9 = textureatlassprite2.getMaxV();
/*     */       
/* 179 */       if (i / 2 % 2 == 0) {
/*     */         
/* 181 */         float f10 = f8;
/* 182 */         f8 = f6;
/* 183 */         f6 = f10;
/*     */       } 
/*     */       
/* 186 */       bufferbuilder.pos((f1 - 0.0F), (0.0F - f4), f5).tex(f8, f9).endVertex();
/* 187 */       bufferbuilder.pos((-f1 - 0.0F), (0.0F - f4), f5).tex(f6, f9).endVertex();
/* 188 */       bufferbuilder.pos((-f1 - 0.0F), (1.4F - f4), f5).tex(f6, f7).endVertex();
/* 189 */       bufferbuilder.pos((f1 - 0.0F), (1.4F - f4), f5).tex(f8, f7).endVertex();
/* 190 */       f3 -= 0.45F;
/* 191 */       f4 -= 0.45F;
/* 192 */       f1 *= 0.9F;
/* 193 */       f5 += 0.03F;
/* 194 */       i++;
/*     */     } 
/*     */     
/* 197 */     tessellator.draw();
/* 198 */     GlStateManager.popMatrix();
/* 199 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderShadow(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 207 */     if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
/*     */       
/* 209 */       GlStateManager.enableBlend();
/* 210 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 211 */       this.renderManager.renderEngine.bindTexture(SHADOW_TEXTURES);
/* 212 */       World world = getWorldFromRenderManager();
/* 213 */       GlStateManager.depthMask(false);
/* 214 */       float f = this.shadowSize;
/*     */       
/* 216 */       if (entityIn instanceof EntityLiving) {
/*     */         
/* 218 */         EntityLiving entityliving = (EntityLiving)entityIn;
/* 219 */         f *= entityliving.getRenderSizeModifier();
/*     */         
/* 221 */         if (entityliving.isChild())
/*     */         {
/* 223 */           f *= 0.5F;
/*     */         }
/*     */       } 
/*     */       
/* 227 */       double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 228 */       double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 229 */       double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 230 */       int i = MathHelper.floor(d5 - f);
/* 231 */       int j = MathHelper.floor(d5 + f);
/* 232 */       int k = MathHelper.floor(d0 - f);
/* 233 */       int l = MathHelper.floor(d0);
/* 234 */       int i1 = MathHelper.floor(d1 - f);
/* 235 */       int j1 = MathHelper.floor(d1 + f);
/* 236 */       double d2 = x - d5;
/* 237 */       double d3 = y - d0;
/* 238 */       double d4 = z - d1;
/* 239 */       Tessellator tessellator = Tessellator.getInstance();
/* 240 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 241 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/* 243 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
/*     */         
/* 245 */         IBlockState iblockstate = world.getBlockState(blockpos.down());
/*     */         
/* 247 */         if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && world.getLightFromNeighbors(blockpos) > 3)
/*     */         {
/* 249 */           renderShadowSingle(iblockstate, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */         }
/*     */       } 
/*     */       
/* 253 */       tessellator.draw();
/* 254 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 255 */       GlStateManager.disableBlend();
/* 256 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/* 265 */     return this.renderManager.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderShadowSingle(IBlockState state, double p_188299_2_, double p_188299_4_, double p_188299_6_, BlockPos p_188299_8_, float p_188299_9_, float p_188299_10_, double p_188299_11_, double p_188299_13_, double p_188299_15_) {
/* 270 */     if (state.isFullCube()) {
/*     */       
/* 272 */       Tessellator tessellator = Tessellator.getInstance();
/* 273 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 274 */       double d0 = (p_188299_9_ - (p_188299_4_ - p_188299_8_.getY() + p_188299_13_) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(p_188299_8_);
/*     */       
/* 276 */       if (d0 >= 0.0D) {
/*     */         
/* 278 */         if (d0 > 1.0D)
/*     */         {
/* 280 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 283 */         AxisAlignedBB axisalignedbb = state.getBoundingBox((IBlockAccess)getWorldFromRenderManager(), p_188299_8_);
/* 284 */         double d1 = p_188299_8_.getX() + axisalignedbb.minX + p_188299_11_;
/* 285 */         double d2 = p_188299_8_.getX() + axisalignedbb.maxX + p_188299_11_;
/* 286 */         double d3 = p_188299_8_.getY() + axisalignedbb.minY + p_188299_13_ + 0.015625D;
/* 287 */         double d4 = p_188299_8_.getZ() + axisalignedbb.minZ + p_188299_15_;
/* 288 */         double d5 = p_188299_8_.getZ() + axisalignedbb.maxZ + p_188299_15_;
/* 289 */         float f = (float)((p_188299_2_ - d1) / 2.0D / p_188299_10_ + 0.5D);
/* 290 */         float f1 = (float)((p_188299_2_ - d2) / 2.0D / p_188299_10_ + 0.5D);
/* 291 */         float f2 = (float)((p_188299_6_ - d4) / 2.0D / p_188299_10_ + 0.5D);
/* 292 */         float f3 = (float)((p_188299_6_ - d5) / 2.0D / p_188299_10_ + 0.5D);
/* 293 */         bufferbuilder.pos(d1, d3, d4).tex(f, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 294 */         bufferbuilder.pos(d1, d3, d5).tex(f, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 295 */         bufferbuilder.pos(d2, d3, d5).tex(f1, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 296 */         bufferbuilder.pos(d2, d3, d4).tex(f1, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z) {
/* 306 */     GlStateManager.disableTexture2D();
/* 307 */     Tessellator tessellator = Tessellator.getInstance();
/* 308 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 309 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 310 */     bufferbuilder.setTranslation(x, y, z);
/* 311 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_NORMAL);
/* 312 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 313 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 314 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 315 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 316 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 317 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 318 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 319 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 320 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 321 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 322 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 323 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 324 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 325 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 326 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 327 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 328 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 329 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 330 */     bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 331 */     bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 332 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 333 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 334 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 335 */     bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 336 */     tessellator.draw();
/* 337 */     bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
/* 338 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 346 */     if (this.renderManager.options != null) {
/*     */       
/* 348 */       if (this.renderManager.options.entityShadows && this.shadowSize > 0.0F && !entityIn.isInvisible() && this.renderManager.isRenderShadow()) {
/*     */         
/* 350 */         double d0 = this.renderManager.getDistanceToCamera(entityIn.posX, entityIn.posY, entityIn.posZ);
/* 351 */         float f = (float)((1.0D - d0 / 256.0D) * this.shadowOpaque);
/*     */         
/* 353 */         if (f > 0.0F)
/*     */         {
/* 355 */           renderShadow(entityIn, x, y, z, f, partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 359 */       if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator()))
/*     */       {
/* 361 */         renderEntityOnFire(entityIn, x, y, z, partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRendererFromRenderManager() {
/* 371 */     return this.renderManager.getFontRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
/* 379 */     double d0 = entityIn.getDistanceSqToEntity(this.renderManager.renderViewEntity);
/*     */     
/* 381 */     if (d0 <= (maxDistance * maxDistance)) {
/*     */       
/* 383 */       boolean flag = entityIn.isSneaking();
/* 384 */       float f = RenderManager.playerViewY;
/* 385 */       float f1 = this.renderManager.playerViewX;
/* 386 */       boolean flag1 = (this.renderManager.options.thirdPersonView == 2);
/* 387 */       float f2 = ((Entity)entityIn).height + 0.5F - (flag ? 0.25F : 0.0F);
/* 388 */       int i = "deadmau5".equals(str) ? -10 : 0;
/* 389 */       EntityRenderer.drawNameplate(getFontRendererFromRenderManager(), entityIn, str, (float)x, (float)y + f2, (float)z, i, f, f1, flag1, flag);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderManager getRenderManager() {
/* 395 */     return this.renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultipass() {
/* 400 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderMultipass(T p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_) {}
/*     */ 
/*     */   
/*     */   public Class getEntityClass() {
/* 409 */     return this.entityClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 414 */     this.entityClass = p_setEntityClass_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationTextureCustom() {
/* 419 */     return this.locationTextureCustom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 424 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
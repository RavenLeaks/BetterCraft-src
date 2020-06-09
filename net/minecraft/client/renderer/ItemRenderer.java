/*     */ package net.minecraft.client.renderer;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import optifine.Config;
/*     */ import optifine.DynamicLights;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public class ItemRenderer {
/*  40 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  41 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*  45 */   private ItemStack itemStackMainHand = ItemStack.field_190927_a;
/*  46 */   private ItemStack itemStackOffHand = ItemStack.field_190927_a;
/*     */   
/*     */   private float equippedProgressMainHand;
/*     */   private float prevEquippedProgressMainHand;
/*     */   private float equippedProgressOffHand;
/*     */   private float prevEquippedProgressOffHand;
/*     */   private final RenderManager renderManager;
/*     */   private final RenderItem itemRenderer;
/*     */   
/*     */   public ItemRenderer(Minecraft mcIn) {
/*  56 */     this.mc = mcIn;
/*  57 */     this.renderManager = mcIn.getRenderManager();
/*  58 */     this.itemRenderer = mcIn.getRenderItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
/*  63 */     renderItemSide(entityIn, heldStack, transform, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
/*  68 */     if (!heldStack.func_190926_b()) {
/*     */       
/*  70 */       Item item = heldStack.getItem();
/*  71 */       Block block = Block.getBlockFromItem(item);
/*  72 */       GlStateManager.pushMatrix();
/*  73 */       boolean flag = (this.itemRenderer.shouldRenderItemIn3D(heldStack) && block.getBlockLayer() == BlockRenderLayer.TRANSLUCENT);
/*     */       
/*  75 */       if (flag && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask))
/*     */       {
/*  77 */         GlStateManager.depthMask(false);
/*     */       }
/*     */       
/*  80 */       this.itemRenderer.renderItem(heldStack, entitylivingbaseIn, transform, leftHanded);
/*     */       
/*  82 */       if (flag)
/*     */       {
/*  84 */         GlStateManager.depthMask(true);
/*     */       }
/*     */       
/*  87 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateArroundXAndY(float angle, float angleY) {
/*  96 */     GlStateManager.pushMatrix();
/*  97 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/*  98 */     GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
/*  99 */     RenderHelper.enableStandardItemLighting();
/* 100 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLightmap() {
/* 105 */     EntityPlayerSP entityPlayerSP = this.mc.player;
/* 106 */     int i = this.mc.world.getCombinedLight(new BlockPos(((AbstractClientPlayer)entityPlayerSP).posX, ((AbstractClientPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((AbstractClientPlayer)entityPlayerSP).posZ), 0);
/*     */     
/* 108 */     if (Config.isDynamicLights())
/*     */     {
/* 110 */       i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
/*     */     }
/*     */     
/* 113 */     float f = (i & 0xFFFF);
/* 114 */     float f1 = (i >> 16);
/* 115 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rotateArm(float p_187458_1_) {
/* 120 */     EntityPlayerSP entityplayersp = this.mc.player;
/* 121 */     float f = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * p_187458_1_;
/* 122 */     float f1 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * p_187458_1_;
/* 123 */     GlStateManager.rotate((entityplayersp.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 124 */     GlStateManager.rotate((entityplayersp.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMapAngleFromPitch(float pitch) {
/* 132 */     float f = 1.0F - pitch / 45.0F + 0.1F;
/* 133 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 134 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
/* 135 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderArms() {
/* 140 */     if (!this.mc.player.isInvisible()) {
/*     */       
/* 142 */       GlStateManager.disableCull();
/* 143 */       GlStateManager.pushMatrix();
/* 144 */       GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 145 */       renderArm(EnumHandSide.RIGHT);
/* 146 */       renderArm(EnumHandSide.LEFT);
/* 147 */       GlStateManager.popMatrix();
/* 148 */       GlStateManager.enableCull();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderArm(EnumHandSide p_187455_1_) {
/* 154 */     this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
/* 155 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.player);
/* 156 */     RenderPlayer renderplayer = (RenderPlayer)render;
/* 157 */     GlStateManager.pushMatrix();
/* 158 */     float f = (p_187455_1_ == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
/* 159 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
/* 160 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 161 */     GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
/* 162 */     GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);
/*     */     
/* 164 */     if (p_187455_1_ == EnumHandSide.RIGHT) {
/*     */       
/* 166 */       renderplayer.renderRightArm((AbstractClientPlayer)this.mc.player);
/*     */     }
/*     */     else {
/*     */       
/* 170 */       renderplayer.renderLeftArm((AbstractClientPlayer)this.mc.player);
/*     */     } 
/*     */     
/* 173 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderMapFirstPersonSide(float p_187465_1_, EnumHandSide p_187465_2_, float p_187465_3_, ItemStack p_187465_4_) {
/* 178 */     float f = (p_187465_2_ == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
/* 179 */     GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
/*     */     
/* 181 */     if (!this.mc.player.isInvisible()) {
/*     */       
/* 183 */       GlStateManager.pushMatrix();
/* 184 */       GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
/* 185 */       renderArmFirstPerson(p_187465_1_, p_187465_3_, p_187465_2_);
/* 186 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 189 */     GlStateManager.pushMatrix();
/* 190 */     GlStateManager.translate(f * 0.51F, -0.08F + p_187465_1_ * -1.2F, -0.75F);
/* 191 */     float f1 = MathHelper.sqrt(p_187465_3_);
/* 192 */     float f2 = MathHelper.sin(f1 * 3.1415927F);
/* 193 */     float f3 = -0.5F * f2;
/* 194 */     float f4 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
/* 195 */     float f5 = -0.3F * MathHelper.sin(p_187465_3_ * 3.1415927F);
/* 196 */     GlStateManager.translate(f * f3, f4 - 0.3F * f2, f5);
/* 197 */     GlStateManager.rotate(f2 * -45.0F, 1.0F, 0.0F, 0.0F);
/* 198 */     GlStateManager.rotate(f * f2 * -30.0F, 0.0F, 1.0F, 0.0F);
/* 199 */     renderMapFirstPerson(p_187465_4_);
/* 200 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderMapFirstPerson(float p_187463_1_, float p_187463_2_, float p_187463_3_) {
/* 205 */     float f = MathHelper.sqrt(p_187463_3_);
/* 206 */     float f1 = -0.2F * MathHelper.sin(p_187463_3_ * 3.1415927F);
/* 207 */     float f2 = -0.4F * MathHelper.sin(f * 3.1415927F);
/* 208 */     GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
/* 209 */     float f3 = getMapAngleFromPitch(p_187463_1_);
/* 210 */     GlStateManager.translate(0.0F, 0.04F + p_187463_2_ * -1.2F + f3 * -0.5F, -0.72F);
/* 211 */     GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
/* 212 */     renderArms();
/* 213 */     float f4 = MathHelper.sin(f * 3.1415927F);
/* 214 */     GlStateManager.rotate(f4 * 20.0F, 1.0F, 0.0F, 0.0F);
/* 215 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 216 */     renderMapFirstPerson(this.itemStackMainHand);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderMapFirstPerson(ItemStack stack) {
/* 221 */     GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 222 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 223 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
/* 224 */     GlStateManager.disableLighting();
/* 225 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 226 */     Tessellator tessellator = Tessellator.getInstance();
/* 227 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 228 */     GlStateManager.translate(-0.5F, -0.5F, 0.0F);
/* 229 */     GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
/* 230 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 231 */     bufferbuilder.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 232 */     bufferbuilder.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 233 */     bufferbuilder.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 234 */     bufferbuilder.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 235 */     tessellator.draw();
/* 236 */     MapData mapdata = ReflectorForge.getMapData(Items.FILLED_MAP, stack, (World)this.mc.world);
/*     */     
/* 238 */     if (mapdata != null)
/*     */     {
/* 240 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
/*     */     }
/*     */     
/* 243 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_) {
/* 248 */     boolean flag = (p_187456_3_ != EnumHandSide.LEFT);
/* 249 */     float f = flag ? 1.0F : -1.0F;
/* 250 */     float f1 = MathHelper.sqrt(p_187456_2_);
/* 251 */     float f2 = -0.3F * MathHelper.sin(f1 * 3.1415927F);
/* 252 */     float f3 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
/* 253 */     float f4 = -0.4F * MathHelper.sin(p_187456_2_ * 3.1415927F);
/* 254 */     GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
/* 255 */     GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
/* 256 */     float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * 3.1415927F);
/* 257 */     float f6 = MathHelper.sin(f1 * 3.1415927F);
/* 258 */     GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 259 */     GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 260 */     EntityPlayerSP entityPlayerSP = this.mc.player;
/* 261 */     this.mc.getTextureManager().bindTexture(entityPlayerSP.getLocationSkin());
/* 262 */     GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
/* 263 */     GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
/* 264 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
/* 265 */     GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
/* 266 */     GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
/* 267 */     RenderPlayer renderplayer = (RenderPlayer)this.renderManager.getEntityRenderObject((Entity)entityPlayerSP);
/* 268 */     GlStateManager.disableCull();
/*     */     
/* 270 */     if (flag) {
/*     */       
/* 272 */       renderplayer.renderRightArm((AbstractClientPlayer)entityPlayerSP);
/*     */     }
/*     */     else {
/*     */       
/* 276 */       renderplayer.renderLeftArm((AbstractClientPlayer)entityPlayerSP);
/*     */     } 
/*     */     
/* 279 */     GlStateManager.enableCull();
/*     */   }
/*     */ 
/*     */   
/*     */   private void transformEatFirstPerson(float p_187454_1_, EnumHandSide p_187454_2_, ItemStack p_187454_3_) {
/* 284 */     float f = this.mc.player.getItemInUseCount() - p_187454_1_ + 1.0F;
/* 285 */     float f1 = f / p_187454_3_.getMaxItemUseDuration();
/*     */     
/* 287 */     if (f1 < 0.8F) {
/*     */       
/* 289 */       float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/* 290 */       GlStateManager.translate(0.0F, f2, 0.0F);
/*     */     } 
/*     */     
/* 293 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/* 294 */     int i = (p_187454_2_ == EnumHandSide.RIGHT) ? 1 : -1;
/* 295 */     GlStateManager.translate(f3 * 0.6F * i, f3 * -0.5F, f3 * 0.0F);
/* 296 */     GlStateManager.rotate(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 297 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 298 */     GlStateManager.rotate(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_) {
/* 303 */     int i = (p_187453_1_ == EnumHandSide.RIGHT) ? 1 : -1;
/* 304 */     float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * 3.1415927F);
/* 305 */     GlStateManager.rotate(i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
/* 306 */     float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * 3.1415927F);
/* 307 */     GlStateManager.rotate(i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 308 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 309 */     GlStateManager.rotate(i * -45.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void transformSideFirstPerson(EnumHandSide p_187459_1_, float p_187459_2_) {
/* 314 */     int i = (p_187459_1_ == EnumHandSide.RIGHT) ? 1 : -1;
/* 315 */     GlStateManager.translate(i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItemInFirstPerson(float partialTicks) {
/* 323 */     EntityPlayerSP entityPlayerSP = this.mc.player;
/* 324 */     float f = entityPlayerSP.getSwingProgress(partialTicks);
/* 325 */     EnumHand enumhand = (EnumHand)MoreObjects.firstNonNull(((AbstractClientPlayer)entityPlayerSP).swingingHand, EnumHand.MAIN_HAND);
/* 326 */     float f1 = ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch + (((AbstractClientPlayer)entityPlayerSP).rotationPitch - ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch) * partialTicks;
/* 327 */     float f2 = ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw + (((AbstractClientPlayer)entityPlayerSP).rotationYaw - ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw) * partialTicks;
/* 328 */     boolean flag = true;
/* 329 */     boolean flag1 = true;
/*     */     
/* 331 */     if (entityPlayerSP.isHandActive()) {
/*     */       
/* 333 */       ItemStack itemstack = entityPlayerSP.getActiveItemStack();
/*     */       
/* 335 */       if (!itemstack.func_190926_b() && itemstack.getItem() == Items.BOW) {
/*     */         
/* 337 */         EnumHand enumhand1 = entityPlayerSP.getActiveHand();
/* 338 */         flag = (enumhand1 == EnumHand.MAIN_HAND);
/* 339 */         flag1 = !flag;
/*     */       } 
/*     */     } 
/*     */     
/* 343 */     rotateArroundXAndY(f1, f2);
/* 344 */     setLightmap();
/* 345 */     rotateArm(partialTicks);
/* 346 */     GlStateManager.enableRescaleNormal();
/*     */     
/* 348 */     if (flag) {
/*     */       
/* 350 */       float f3 = (enumhand == EnumHand.MAIN_HAND) ? f : 0.0F;
/* 351 */       float f5 = 1.0F - this.prevEquippedProgressMainHand + (this.equippedProgressMainHand - this.prevEquippedProgressMainHand) * partialTicks;
/*     */       
/* 353 */       if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[] { EnumHand.MAIN_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f3), Float.valueOf(f5), this.itemStackMainHand }))
/*     */       {
/* 355 */         renderItemInFirstPerson((AbstractClientPlayer)entityPlayerSP, partialTicks, f1, EnumHand.MAIN_HAND, f3, this.itemStackMainHand, f5);
/*     */       }
/*     */     } 
/*     */     
/* 359 */     if (flag1) {
/*     */       
/* 361 */       float f4 = (enumhand == EnumHand.OFF_HAND) ? f : 0.0F;
/* 362 */       float f6 = 1.0F - this.prevEquippedProgressOffHand + (this.equippedProgressOffHand - this.prevEquippedProgressOffHand) * partialTicks;
/*     */       
/* 364 */       if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[] { EnumHand.OFF_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f4), Float.valueOf(f6), this.itemStackOffHand }))
/*     */       {
/* 366 */         renderItemInFirstPerson((AbstractClientPlayer)entityPlayerSP, partialTicks, f1, EnumHand.OFF_HAND, f4, this.itemStackOffHand, f6);
/*     */       }
/*     */     } 
/*     */     
/* 370 */     GlStateManager.disableRescaleNormal();
/* 371 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItemInFirstPerson(AbstractClientPlayer p_187457_1_, float p_187457_2_, float p_187457_3_, EnumHand p_187457_4_, float p_187457_5_, ItemStack p_187457_6_, float p_187457_7_) {
/* 376 */     if (!Config.isShaders() || !Shaders.isSkipRenderHand(p_187457_4_)) {
/*     */       
/* 378 */       boolean flag = (p_187457_4_ == EnumHand.MAIN_HAND);
/* 379 */       EnumHandSide enumhandside = flag ? p_187457_1_.getPrimaryHand() : p_187457_1_.getPrimaryHand().opposite();
/* 380 */       GlStateManager.pushMatrix();
/*     */       
/* 382 */       if (p_187457_6_.func_190926_b()) {
/*     */         
/* 384 */         if (flag && !p_187457_1_.isInvisible())
/*     */         {
/* 386 */           renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
/*     */         }
/*     */       }
/* 389 */       else if (p_187457_6_.getItem() instanceof net.minecraft.item.ItemMap) {
/*     */         
/* 391 */         if (flag && this.itemStackOffHand.func_190926_b())
/*     */         {
/* 393 */           renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
/*     */         }
/*     */         else
/*     */         {
/* 397 */           renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, p_187457_6_);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 402 */         boolean flag1 = (enumhandside == EnumHandSide.RIGHT);
/*     */         
/* 404 */         if (p_187457_1_.isHandActive() && p_187457_1_.getItemInUseCount() > 0 && p_187457_1_.getActiveHand() == p_187457_4_) {
/*     */           float f5, f6;
/* 406 */           int j = flag1 ? 1 : -1;
/*     */           
/* 408 */           switch (p_187457_6_.getItemUseAction()) {
/*     */             
/*     */             case NONE:
/* 411 */               transformSideFirstPerson(enumhandside, p_187457_7_);
/*     */               break;
/*     */             
/*     */             case EAT:
/*     */             case DRINK:
/* 416 */               transformEatFirstPerson(p_187457_2_, enumhandside, p_187457_6_);
/* 417 */               transformSideFirstPerson(enumhandside, p_187457_7_);
/*     */               break;
/*     */             
/*     */             case null:
/* 421 */               transformSideFirstPerson(enumhandside, p_187457_7_);
/*     */               break;
/*     */             
/*     */             case BOW:
/* 425 */               transformSideFirstPerson(enumhandside, p_187457_7_);
/* 426 */               GlStateManager.translate(j * -0.2785682F, 0.18344387F, 0.15731531F);
/* 427 */               GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
/* 428 */               GlStateManager.rotate(j * 35.3F, 0.0F, 1.0F, 0.0F);
/* 429 */               GlStateManager.rotate(j * -9.785F, 0.0F, 0.0F, 1.0F);
/* 430 */               f5 = p_187457_6_.getMaxItemUseDuration() - this.mc.player.getItemInUseCount() - p_187457_2_ + 1.0F;
/* 431 */               f6 = f5 / 20.0F;
/* 432 */               f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;
/*     */               
/* 434 */               if (f6 > 1.0F)
/*     */               {
/* 436 */                 f6 = 1.0F;
/*     */               }
/*     */               
/* 439 */               if (f6 > 0.1F) {
/*     */                 
/* 441 */                 float f7 = MathHelper.sin((f5 - 0.1F) * 1.3F);
/* 442 */                 float f3 = f6 - 0.1F;
/* 443 */                 float f4 = f7 * f3;
/* 444 */                 GlStateManager.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
/*     */               } 
/*     */               
/* 447 */               GlStateManager.translate(f6 * 0.0F, f6 * 0.0F, f6 * 0.04F);
/* 448 */               GlStateManager.scale(1.0F, 1.0F, 1.0F + f6 * 0.2F);
/* 449 */               GlStateManager.rotate(j * 45.0F, 0.0F, -1.0F, 0.0F);
/*     */               break;
/*     */           } 
/*     */         
/*     */         } else {
/* 454 */           float f = -0.4F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 3.1415927F);
/* 455 */           float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 6.2831855F);
/* 456 */           float f2 = -0.2F * MathHelper.sin(p_187457_5_ * 3.1415927F);
/* 457 */           int i = flag1 ? 1 : -1;
/* 458 */           GlStateManager.translate(i * f, f1, f2);
/* 459 */           transformSideFirstPerson(enumhandside, p_187457_7_);
/* 460 */           transformFirstPerson(enumhandside, p_187457_5_);
/*     */         } 
/*     */         
/* 463 */         renderItemSide((EntityLivingBase)p_187457_1_, p_187457_6_, flag1 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
/*     */       } 
/*     */       
/* 466 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOverlays(float partialTicks) {
/* 475 */     GlStateManager.disableAlpha();
/*     */     
/* 477 */     if (this.mc.player.isEntityInsideOpaqueBlock()) {
/*     */       
/* 479 */       IBlockState iblockstate = this.mc.world.getBlockState(new BlockPos((Entity)this.mc.player));
/* 480 */       BlockPos blockpos = new BlockPos((Entity)this.mc.player);
/* 481 */       EntityPlayerSP entityPlayerSP = this.mc.player;
/*     */       
/* 483 */       for (int i = 0; i < 8; i++) {
/*     */         
/* 485 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + ((((i >> 0) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 486 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
/* 487 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 488 */         BlockPos blockpos1 = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
/* 489 */         IBlockState iblockstate1 = this.mc.world.getBlockState(blockpos1);
/*     */         
/* 491 */         if (iblockstate1.func_191058_s()) {
/*     */           
/* 493 */           iblockstate = iblockstate1;
/* 494 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */       
/* 498 */       if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*     */         
/* 500 */         Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
/*     */         
/* 502 */         if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks), object, iblockstate, blockpos }))
/*     */         {
/* 504 */           renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 509 */     if (!this.mc.player.isSpectator()) {
/*     */       
/* 511 */       if (this.mc.player.isInsideOfMaterial(Material.WATER) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks) }))
/*     */       {
/* 513 */         renderWaterOverlayTexture(partialTicks);
/*     */       }
/*     */       
/* 516 */       if (this.mc.player.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks) }))
/*     */       {
/* 518 */         renderFireInFirstPerson();
/*     */       }
/*     */     } 
/*     */     
/* 522 */     GlStateManager.enableAlpha();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderBlockInHand(TextureAtlasSprite partialTicks) {
/* 530 */     this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 531 */     Tessellator tessellator = Tessellator.getInstance();
/* 532 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 533 */     float f = 0.1F;
/* 534 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
/* 535 */     GlStateManager.pushMatrix();
/* 536 */     float f1 = -1.0F;
/* 537 */     float f2 = 1.0F;
/* 538 */     float f3 = -1.0F;
/* 539 */     float f4 = 1.0F;
/* 540 */     float f5 = -0.5F;
/* 541 */     float f6 = partialTicks.getMinU();
/* 542 */     float f7 = partialTicks.getMaxU();
/* 543 */     float f8 = partialTicks.getMinV();
/* 544 */     float f9 = partialTicks.getMaxV();
/* 545 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 546 */     bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
/* 547 */     bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
/* 548 */     bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
/* 549 */     bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
/* 550 */     tessellator.draw();
/* 551 */     GlStateManager.popMatrix();
/* 552 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderWaterOverlayTexture(float partialTicks) {
/* 561 */     if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
/*     */       
/* 563 */       this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 564 */       Tessellator tessellator = Tessellator.getInstance();
/* 565 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 566 */       float f = this.mc.player.getBrightness();
/* 567 */       GlStateManager.color(f, f, f, 0.5F);
/* 568 */       GlStateManager.enableBlend();
/* 569 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 570 */       GlStateManager.pushMatrix();
/* 571 */       float f1 = 4.0F;
/* 572 */       float f2 = -1.0F;
/* 573 */       float f3 = 1.0F;
/* 574 */       float f4 = -1.0F;
/* 575 */       float f5 = 1.0F;
/* 576 */       float f6 = -0.5F;
/* 577 */       float f7 = -this.mc.player.rotationYaw / 64.0F;
/* 578 */       float f8 = this.mc.player.rotationPitch / 64.0F;
/* 579 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 580 */       bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
/* 581 */       bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
/* 582 */       bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
/* 583 */       bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
/* 584 */       tessellator.draw();
/* 585 */       GlStateManager.popMatrix();
/* 586 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 587 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFireInFirstPerson() {
/* 596 */     Tessellator tessellator = Tessellator.getInstance();
/* 597 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 598 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
/* 599 */     GlStateManager.depthFunc(519);
/* 600 */     GlStateManager.depthMask(false);
/* 601 */     GlStateManager.enableBlend();
/* 602 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 603 */     float f = 1.0F;
/*     */     
/* 605 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 607 */       GlStateManager.pushMatrix();
/* 608 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 609 */       this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 610 */       float f1 = textureatlassprite.getMinU();
/* 611 */       float f2 = textureatlassprite.getMaxU();
/* 612 */       float f3 = textureatlassprite.getMinV();
/* 613 */       float f4 = textureatlassprite.getMaxV();
/* 614 */       float f5 = -0.5F;
/* 615 */       float f6 = 0.5F;
/* 616 */       float f7 = -0.5F;
/* 617 */       float f8 = 0.5F;
/* 618 */       float f9 = -0.5F;
/* 619 */       GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 620 */       GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 621 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 622 */       bufferbuilder.pos(-0.5D, -0.5D, -0.5D).tex(f2, f4).endVertex();
/* 623 */       bufferbuilder.pos(0.5D, -0.5D, -0.5D).tex(f1, f4).endVertex();
/* 624 */       bufferbuilder.pos(0.5D, 0.5D, -0.5D).tex(f1, f3).endVertex();
/* 625 */       bufferbuilder.pos(-0.5D, 0.5D, -0.5D).tex(f2, f3).endVertex();
/* 626 */       tessellator.draw();
/* 627 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 630 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 631 */     GlStateManager.disableBlend();
/* 632 */     GlStateManager.depthMask(true);
/* 633 */     GlStateManager.depthFunc(515);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEquippedItem() {
/* 638 */     this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
/* 639 */     this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
/* 640 */     EntityPlayerSP entityplayersp = this.mc.player;
/* 641 */     ItemStack itemstack = entityplayersp.getHeldItemMainhand();
/* 642 */     ItemStack itemstack1 = entityplayersp.getHeldItemOffhand();
/*     */     
/* 644 */     if (entityplayersp.isRowingBoat()) {
/*     */       
/* 646 */       this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4F, 0.0F, 1.0F);
/* 647 */       this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4F, 0.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 651 */       float f = entityplayersp.getCooledAttackStrength(1.0F);
/*     */       
/* 653 */       if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
/*     */         
/* 655 */         boolean flag = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[] { this.itemStackMainHand, itemstack, Integer.valueOf(entityplayersp.inventory.currentItem) });
/* 656 */         boolean flag1 = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[] { this.itemStackOffHand, itemstack1, Integer.valueOf(-1) });
/*     */         
/* 658 */         if (!flag && !Objects.equals(this.itemStackMainHand, itemstack))
/*     */         {
/* 660 */           this.itemStackMainHand = itemstack;
/*     */         }
/*     */         
/* 663 */         if (!flag && !Objects.equals(this.itemStackOffHand, itemstack1))
/*     */         {
/* 665 */           this.itemStackOffHand = itemstack1;
/*     */         }
/*     */         
/* 668 */         this.equippedProgressMainHand += MathHelper.clamp((!flag ? (f * f * f) : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
/* 669 */         this.equippedProgressOffHand += MathHelper.clamp((!flag1 ? true : false) - this.equippedProgressOffHand, -0.4F, 0.4F);
/*     */       }
/*     */       else {
/*     */         
/* 673 */         this.equippedProgressMainHand += MathHelper.clamp((Objects.equals(this.itemStackMainHand, itemstack) ? (f * f * f) : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
/* 674 */         this.equippedProgressOffHand += MathHelper.clamp((Objects.equals(this.itemStackOffHand, itemstack1) ? true : false) - this.equippedProgressOffHand, -0.4F, 0.4F);
/*     */       } 
/*     */     } 
/*     */     
/* 678 */     if (this.equippedProgressMainHand < 0.1F) {
/*     */       
/* 680 */       this.itemStackMainHand = itemstack;
/*     */       
/* 682 */       if (Config.isShaders())
/*     */       {
/* 684 */         Shaders.setItemToRenderMain(this.itemStackMainHand);
/*     */       }
/*     */     } 
/*     */     
/* 688 */     if (this.equippedProgressOffHand < 0.1F) {
/*     */       
/* 690 */       this.itemStackOffHand = itemstack1;
/*     */       
/* 692 */       if (Config.isShaders())
/*     */       {
/* 694 */         Shaders.setItemToRenderOff(this.itemStackOffHand);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress(EnumHand hand) {
/* 701 */     if (hand == EnumHand.MAIN_HAND) {
/*     */       
/* 703 */       this.equippedProgressMainHand = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 707 */       this.equippedProgressOffHand = 0.0F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
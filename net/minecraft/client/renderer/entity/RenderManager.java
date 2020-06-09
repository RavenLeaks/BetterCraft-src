/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAreaEffectCloud;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityElderGuardian;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityEvoker;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityHusk;
/*     */ import net.minecraft.entity.monster.EntityIllusionIllager;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityPolarBear;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityStray;
/*     */ import net.minecraft.entity.monster.EntityVex;
/*     */ import net.minecraft.entity.monster.EntityVindicator;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.monster.EntityZombieVillager;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityDonkey;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityLlama;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityMule;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityParrot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.passive.EntityZombieHorse;
/*     */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityLlamaSpit;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import optifine.PlayerItemsLayer;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ public class RenderManager {
/* 122 */   private final Map<Class, Render> entityRenderMap = Maps.newHashMap();
/* 123 */   private final Map<String, RenderPlayer> skinMap = Maps.newHashMap();
/*     */   
/*     */   private final RenderPlayer playerRenderer;
/*     */   
/*     */   private FontRenderer textRenderer;
/*     */   
/*     */   public static double renderPosX;
/*     */   
/*     */   public static double renderPosY;
/*     */   
/*     */   public static double renderPosZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity renderViewEntity;
/*     */   
/*     */   public Entity pointedEntity;
/*     */   
/*     */   public static float playerViewY;
/*     */   public float playerViewX;
/*     */   public GameSettings options;
/*     */   public double viewerPosX;
/*     */   public double viewerPosY;
/*     */   public double viewerPosZ;
/*     */   private boolean renderOutlines;
/*     */   private boolean renderShadow = true;
/*     */   private boolean debugBoundingBox;
/* 152 */   public Entity renderEntity = null;
/* 153 */   public Render renderRender = null;
/*     */ 
/*     */   
/*     */   public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn) {
/* 157 */     this.renderEngine = renderEngineIn;
/* 158 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
/* 159 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider<>(this));
/* 160 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(this));
/* 161 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this));
/* 162 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(this));
/* 163 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this));
/* 164 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this));
/* 165 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this));
/* 166 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this));
/* 167 */     this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this));
/* 168 */     this.entityRenderMap.put(EntityParrot.class, new RenderParrot(this));
/* 169 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
/* 170 */     this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
/* 171 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
/* 172 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
/* 173 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
/* 174 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
/* 175 */     this.entityRenderMap.put(EntityWitherSkeleton.class, new RenderWitherSkeleton(this));
/* 176 */     this.entityRenderMap.put(EntityStray.class, new RenderStray(this));
/* 177 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
/* 178 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
/* 179 */     this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
/* 180 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
/* 181 */     this.entityRenderMap.put(EntityZombieVillager.class, new RenderZombieVillager(this));
/* 182 */     this.entityRenderMap.put(EntityHusk.class, new RenderHusk(this));
/* 183 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this));
/* 184 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
/* 185 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, 6.0F));
/* 186 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
/* 187 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this));
/* 188 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
/* 189 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
/* 190 */     this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
/* 191 */     this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
/* 192 */     this.entityRenderMap.put(EntityElderGuardian.class, new RenderElderGuardian(this));
/* 193 */     this.entityRenderMap.put(EntityShulker.class, new RenderShulker(this));
/* 194 */     this.entityRenderMap.put(EntityPolarBear.class, new RenderPolarBear(this));
/* 195 */     this.entityRenderMap.put(EntityEvoker.class, new RenderEvoker(this));
/* 196 */     this.entityRenderMap.put(EntityVindicator.class, new RenderVindicator(this));
/* 197 */     this.entityRenderMap.put(EntityVex.class, new RenderVex(this));
/* 198 */     this.entityRenderMap.put(EntityIllusionIllager.class, new RenderIllusionIllager(this));
/* 199 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
/* 200 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
/* 201 */     this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
/* 202 */     this.entityRenderMap.put(Entity.class, new RenderEntity(this));
/* 203 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
/* 204 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
/* 205 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
/* 206 */     this.entityRenderMap.put(EntityTippedArrow.class, new RenderTippedArrow(this));
/* 207 */     this.entityRenderMap.put(EntitySpectralArrow.class, new RenderSpectralArrow(this));
/* 208 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<>(this, Items.SNOWBALL, itemRendererIn));
/* 209 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<>(this, Items.ENDER_PEARL, itemRendererIn));
/* 210 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<>(this, Items.ENDER_EYE, itemRendererIn));
/* 211 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<>(this, Items.EGG, itemRendererIn));
/* 212 */     this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
/* 213 */     this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball<>(this, Items.EXPERIENCE_BOTTLE, itemRendererIn));
/* 214 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<>(this, Items.FIREWORKS, itemRendererIn));
/* 215 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
/* 216 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
/* 217 */     this.entityRenderMap.put(EntityDragonFireball.class, new RenderDragonFireball(this));
/* 218 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
/* 219 */     this.entityRenderMap.put(EntityShulkerBullet.class, new RenderShulkerBullet(this));
/* 220 */     this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
/* 221 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
/* 222 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
/* 223 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
/* 224 */     this.entityRenderMap.put(EntityArmorStand.class, new RenderArmorStand(this));
/* 225 */     this.entityRenderMap.put(EntityEvokerFangs.class, new RenderEvokerFangs(this));
/* 226 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
/* 227 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
/* 228 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<>(this));
/* 229 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
/* 230 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
/* 231 */     this.entityRenderMap.put(EntityAreaEffectCloud.class, new RenderAreaEffectCloud(this));
/* 232 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this));
/* 233 */     this.entityRenderMap.put(EntitySkeletonHorse.class, new RenderAbstractHorse(this));
/* 234 */     this.entityRenderMap.put(EntityZombieHorse.class, new RenderAbstractHorse(this));
/* 235 */     this.entityRenderMap.put(EntityMule.class, new RenderAbstractHorse(this, 0.92F));
/* 236 */     this.entityRenderMap.put(EntityDonkey.class, new RenderAbstractHorse(this, 0.87F));
/* 237 */     this.entityRenderMap.put(EntityLlama.class, new RenderLlama(this));
/* 238 */     this.entityRenderMap.put(EntityLlamaSpit.class, new RenderLlamaSpit(this));
/* 239 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
/* 240 */     this.playerRenderer = new RenderPlayer(this);
/* 241 */     this.skinMap.put("default", this.playerRenderer);
/* 242 */     this.skinMap.put("slim", new RenderPlayer(this, true));
/* 243 */     PlayerItemsLayer.register(this.skinMap);
/*     */     
/* 245 */     if (Reflector.RenderingRegistry_loadEntityRenderers.exists())
/*     */     {
/* 247 */       Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, new Object[] { this, this.entityRenderMap });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
/* 253 */     renderPosX = renderPosXIn;
/* 254 */     renderPosY = renderPosYIn;
/* 255 */     renderPosZ = renderPosZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> entityClass) {
/* 260 */     Render<T> render = this.entityRenderMap.get(entityClass);
/*     */     
/* 262 */     if (render == null && entityClass != Entity.class) {
/*     */       
/* 264 */       render = getEntityClassRenderObject((Class)entityClass.getSuperclass());
/* 265 */       this.entityRenderMap.put(entityClass, render);
/*     */     } 
/*     */     
/* 268 */     return render;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn) {
/* 274 */     if (entityIn instanceof AbstractClientPlayer) {
/*     */       
/* 276 */       String s = ((AbstractClientPlayer)entityIn).getSkinType();
/* 277 */       RenderPlayer renderplayer = this.skinMap.get(s);
/* 278 */       return (renderplayer != null) ? renderplayer : this.playerRenderer;
/*     */     } 
/*     */ 
/*     */     
/* 282 */     return getEntityClassRenderObject((Class)entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks) {
/* 288 */     this.worldObj = worldIn;
/* 289 */     this.options = optionsIn;
/* 290 */     this.renderViewEntity = livingPlayerIn;
/* 291 */     this.pointedEntity = pointedEntityIn;
/* 292 */     this.textRenderer = textRendererIn;
/*     */     
/* 294 */     if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping()) {
/*     */       
/* 296 */       IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
/* 297 */       Block block = iblockstate.getBlock();
/*     */       
/* 299 */       if (Reflector.callBoolean(block, Reflector.ForgeBlock_isBed, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn), livingPlayerIn }))
/*     */       {
/* 301 */         EnumFacing enumfacing = (EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn) });
/* 302 */         int i = enumfacing.getHorizontalIndex();
/* 303 */         playerViewY = (i * 90 + 180);
/* 304 */         this.playerViewX = 0.0F;
/*     */       }
/* 306 */       else if (block == Blocks.BED)
/*     */       {
/* 308 */         int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/* 309 */         playerViewY = (j * 90 + 180);
/* 310 */         this.playerViewX = 0.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 315 */       playerViewY = livingPlayerIn.prevRotationYaw + (livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
/* 316 */       this.playerViewX = livingPlayerIn.prevRotationPitch + (livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
/*     */     } 
/*     */     
/* 319 */     if (optionsIn.thirdPersonView == 2)
/*     */     {
/* 321 */       playerViewY += 180.0F;
/*     */     }
/*     */     
/* 324 */     this.viewerPosX = livingPlayerIn.lastTickPosX + (livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * partialTicks;
/* 325 */     this.viewerPosY = livingPlayerIn.lastTickPosY + (livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * partialTicks;
/* 326 */     this.viewerPosZ = livingPlayerIn.lastTickPosZ + (livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewY(float playerViewYIn) {
/* 331 */     playerViewY = playerViewYIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRenderShadow() {
/* 336 */     return this.renderShadow;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderShadow(boolean renderShadowIn) {
/* 341 */     this.renderShadow = renderShadowIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
/* 346 */     this.debugBoundingBox = debugBoundingBoxIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugBoundingBox() {
/* 351 */     return this.debugBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRenderMultipass(Entity p_188390_1_) {
/* 356 */     return getEntityRenderObject(p_188390_1_).isMultipass();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
/* 361 */     Render<Entity> render = getEntityRenderObject(entityIn);
/* 362 */     return (render != null && render.shouldRender(entityIn, camera, camX, camY, camZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderEntityStatic(Entity entityIn, float partialTicks, boolean p_188388_3_) {
/* 367 */     if (entityIn.ticksExisted == 0) {
/*     */       
/* 369 */       entityIn.lastTickPosX = entityIn.posX;
/* 370 */       entityIn.lastTickPosY = entityIn.posY;
/* 371 */       entityIn.lastTickPosZ = entityIn.posZ;
/*     */     } 
/*     */     
/* 374 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 375 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 376 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 377 */     float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
/* 378 */     int i = entityIn.getBrightnessForRender();
/*     */     
/* 380 */     if (entityIn.isBurning())
/*     */     {
/* 382 */       i = 15728880;
/*     */     }
/*     */     
/* 385 */     int j = i % 65536;
/* 386 */     int k = i / 65536;
/* 387 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 388 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 389 */     doRenderEntity(entityIn, d0 - renderPosX, d1 - renderPosY, d2 - renderPosZ, f, partialTicks, p_188388_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderEntity(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_) {
/* 394 */     Render<Entity> render = null;
/*     */ 
/*     */     
/*     */     try {
/* 398 */       render = getEntityRenderObject(entityIn);
/*     */       
/* 400 */       if (render != null && this.renderEngine != null) {
/*     */ 
/*     */         
/*     */         try {
/* 404 */           render.setRenderOutlines(this.renderOutlines);
/*     */           
/* 406 */           if (CustomEntityModels.isActive()) {
/*     */             
/* 408 */             this.renderEntity = entityIn;
/* 409 */             this.renderRender = render;
/*     */           } 
/*     */           
/* 412 */           render.doRender(entityIn, x, y, z, yaw, partialTicks);
/*     */         }
/* 414 */         catch (Throwable throwable2) {
/*     */           
/* 416 */           throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 421 */           if (!this.renderOutlines)
/*     */           {
/* 423 */             render.doRenderShadowAndFire(entityIn, x, y, z, yaw, partialTicks);
/*     */           }
/*     */         }
/* 426 */         catch (Throwable throwable1) {
/*     */           
/* 428 */           throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
/*     */         } 
/*     */         
/* 431 */         if (this.debugBoundingBox && !entityIn.isInvisible() && !p_188391_10_ && !Minecraft.getMinecraft().isReducedDebug()) {
/*     */           try
/*     */           {
/*     */             
/* 435 */             renderDebugBoundingBox(entityIn, x, y, z, yaw, partialTicks);
/*     */           }
/* 437 */           catch (Throwable throwable)
/*     */           {
/* 439 */             throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/* 444 */     } catch (Throwable throwable3) {
/*     */       
/* 446 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
/* 447 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
/* 448 */       entityIn.addEntityCrashInfo(crashreportcategory);
/* 449 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
/* 450 */       crashreportcategory1.addCrashSection("Assigned renderer", render);
/* 451 */       crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
/* 452 */       crashreportcategory1.addCrashSection("Rotation", Float.valueOf(yaw));
/* 453 */       crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
/* 454 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMultipass(Entity p_188389_1_, float p_188389_2_) {
/* 460 */     if (p_188389_1_.ticksExisted == 0) {
/*     */       
/* 462 */       p_188389_1_.lastTickPosX = p_188389_1_.posX;
/* 463 */       p_188389_1_.lastTickPosY = p_188389_1_.posY;
/* 464 */       p_188389_1_.lastTickPosZ = p_188389_1_.posZ;
/*     */     } 
/*     */     
/* 467 */     double d0 = p_188389_1_.lastTickPosX + (p_188389_1_.posX - p_188389_1_.lastTickPosX) * p_188389_2_;
/* 468 */     double d1 = p_188389_1_.lastTickPosY + (p_188389_1_.posY - p_188389_1_.lastTickPosY) * p_188389_2_;
/* 469 */     double d2 = p_188389_1_.lastTickPosZ + (p_188389_1_.posZ - p_188389_1_.lastTickPosZ) * p_188389_2_;
/* 470 */     float f = p_188389_1_.prevRotationYaw + (p_188389_1_.rotationYaw - p_188389_1_.prevRotationYaw) * p_188389_2_;
/* 471 */     int i = p_188389_1_.getBrightnessForRender();
/*     */     
/* 473 */     if (p_188389_1_.isBurning())
/*     */     {
/* 475 */       i = 15728880;
/*     */     }
/*     */     
/* 478 */     int j = i % 65536;
/* 479 */     int k = i / 65536;
/* 480 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 481 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 482 */     Render<Entity> render = getEntityRenderObject(p_188389_1_);
/*     */     
/* 484 */     if (render != null && this.renderEngine != null)
/*     */     {
/* 486 */       render.renderMultipass(p_188389_1_, d0 - renderPosX, d1 - renderPosY, d2 - renderPosZ, f, p_188389_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 495 */     GlStateManager.depthMask(false);
/* 496 */     GlStateManager.disableTexture2D();
/* 497 */     GlStateManager.disableLighting();
/* 498 */     GlStateManager.disableCull();
/* 499 */     GlStateManager.disableBlend();
/* 500 */     float f = entityIn.width / 2.0F;
/* 501 */     AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
/* 502 */     RenderGlobal.drawBoundingBox(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z, 1.0F, 1.0F, 1.0F, 1.0F);
/* 503 */     Entity[] aentity = entityIn.getParts();
/*     */     
/* 505 */     if (aentity != null) {
/*     */       byte b; int i; Entity[] arrayOfEntity;
/* 507 */       for (i = (arrayOfEntity = aentity).length, b = 0; b < i; ) { Entity entity = arrayOfEntity[b];
/*     */         
/* 509 */         double d0 = (entity.posX - entity.prevPosX) * partialTicks;
/* 510 */         double d1 = (entity.posY - entity.prevPosY) * partialTicks;
/* 511 */         double d2 = (entity.posZ - entity.prevPosZ) * partialTicks;
/* 512 */         AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
/* 513 */         RenderGlobal.drawBoundingBox(axisalignedbb1.minX - renderPosX + d0, axisalignedbb1.minY - renderPosY + d1, axisalignedbb1.minZ - renderPosZ + d2, axisalignedbb1.maxX - renderPosX + d0, axisalignedbb1.maxY - renderPosY + d1, axisalignedbb1.maxZ - renderPosZ + d2, 0.25F, 1.0F, 0.0F, 1.0F);
/*     */         b++; }
/*     */     
/*     */     } 
/* 517 */     if (entityIn instanceof EntityLivingBase) {
/*     */       
/* 519 */       float f1 = 0.01F;
/* 520 */       RenderGlobal.drawBoundingBox(x - f, y + entityIn.getEyeHeight() - 0.009999999776482582D, z - f, x + f, y + entityIn.getEyeHeight() + 0.009999999776482582D, z + f, 1.0F, 0.0F, 0.0F, 1.0F);
/*     */     } 
/*     */     
/* 523 */     Tessellator tessellator = Tessellator.getInstance();
/* 524 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 525 */     Vec3d vec3d = entityIn.getLook(partialTicks);
/* 526 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 527 */     bufferbuilder.pos(x, y + entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
/* 528 */     bufferbuilder.pos(x + vec3d.xCoord * 2.0D, y + entityIn.getEyeHeight() + vec3d.yCoord * 2.0D, z + vec3d.zCoord * 2.0D).color(0, 0, 255, 255).endVertex();
/* 529 */     tessellator.draw();
/* 530 */     GlStateManager.enableTexture2D();
/* 531 */     GlStateManager.enableLighting();
/* 532 */     GlStateManager.enableCull();
/* 533 */     GlStateManager.disableBlend();
/* 534 */     GlStateManager.depthMask(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(@Nullable World worldIn) {
/* 542 */     this.worldObj = worldIn;
/*     */     
/* 544 */     if (worldIn == null)
/*     */     {
/* 546 */       this.renderViewEntity = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistanceToCamera(double x, double y, double z) {
/* 552 */     double d0 = x - this.viewerPosX;
/* 553 */     double d1 = y - this.viewerPosY;
/* 554 */     double d2 = z - this.viewerPosZ;
/* 555 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 563 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 568 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class, Render> getEntityRenderMap() {
/* 573 */     return this.entityRenderMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, RenderPlayer> getSkinMap() {
/* 578 */     return Collections.unmodifiableMap(this.skinMap);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
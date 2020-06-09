/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ public class ParticleManager {
/*  42 */   private static final ResourceLocation PARTICLE_TEXTURES = new ResourceLocation("textures/particle/particles.png");
/*     */   
/*     */   protected World worldObj;
/*     */   
/*  46 */   private final ArrayDeque<Particle>[][] fxLayers = (ArrayDeque<Particle>[][])new ArrayDeque[4][];
/*  47 */   private final Queue<ParticleEmitter> particleEmitters = Queues.newArrayDeque();
/*     */   
/*     */   private final TextureManager renderer;
/*     */   
/*  51 */   private final Random rand = new Random();
/*  52 */   private final Map<Integer, IParticleFactory> particleTypes = Maps.newHashMap();
/*  53 */   private final Queue<Particle> queueEntityFX = Queues.newArrayDeque();
/*     */ 
/*     */   
/*     */   public ParticleManager(World worldIn, TextureManager rendererIn) {
/*  57 */     this.worldObj = worldIn;
/*  58 */     this.renderer = rendererIn;
/*     */     
/*  60 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  62 */       this.fxLayers[i] = (ArrayDeque<Particle>[])new ArrayDeque[2];
/*     */       
/*  64 */       for (int j = 0; j < 2; j++)
/*     */       {
/*  66 */         this.fxLayers[i][j] = Queues.newArrayDeque();
/*     */       }
/*     */     } 
/*     */     
/*  70 */     registerVanillaParticles();
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVanillaParticles() {
/*  75 */     registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new ParticleExplosion.Factory());
/*  76 */     registerParticle(EnumParticleTypes.SPIT.getParticleID(), new ParticleSpit.Factory());
/*  77 */     registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new ParticleBubble.Factory());
/*  78 */     registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new ParticleSplash.Factory());
/*  79 */     registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new ParticleWaterWake.Factory());
/*  80 */     registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new ParticleRain.Factory());
/*  81 */     registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new ParticleSuspend.Factory());
/*  82 */     registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new ParticleSuspendedTown.Factory());
/*  83 */     registerParticle(EnumParticleTypes.CRIT.getParticleID(), new ParticleCrit.Factory());
/*  84 */     registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new ParticleCrit.MagicFactory());
/*  85 */     registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new ParticleSmokeNormal.Factory());
/*  86 */     registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new ParticleSmokeLarge.Factory());
/*  87 */     registerParticle(EnumParticleTypes.SPELL.getParticleID(), new ParticleSpell.Factory());
/*  88 */     registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new ParticleSpell.InstantFactory());
/*  89 */     registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new ParticleSpell.MobFactory());
/*  90 */     registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new ParticleSpell.AmbientMobFactory());
/*  91 */     registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new ParticleSpell.WitchFactory());
/*  92 */     registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new ParticleDrip.WaterFactory());
/*  93 */     registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new ParticleDrip.LavaFactory());
/*  94 */     registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new ParticleHeart.AngryVillagerFactory());
/*  95 */     registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new ParticleSuspendedTown.HappyVillagerFactory());
/*  96 */     registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new ParticleSuspendedTown.Factory());
/*  97 */     registerParticle(EnumParticleTypes.NOTE.getParticleID(), new ParticleNote.Factory());
/*  98 */     registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new ParticlePortal.Factory());
/*  99 */     registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new ParticleEnchantmentTable.EnchantmentTable());
/* 100 */     registerParticle(EnumParticleTypes.FLAME.getParticleID(), new ParticleFlame.Factory());
/* 101 */     registerParticle(EnumParticleTypes.LAVA.getParticleID(), new ParticleLava.Factory());
/* 102 */     registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new ParticleFootStep.Factory());
/* 103 */     registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new ParticleCloud.Factory());
/* 104 */     registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new ParticleRedstone.Factory());
/* 105 */     registerParticle(EnumParticleTypes.FALLING_DUST.getParticleID(), new ParticleFallingDust.Factory());
/* 106 */     registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new ParticleBreaking.SnowballFactory());
/* 107 */     registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new ParticleSnowShovel.Factory());
/* 108 */     registerParticle(EnumParticleTypes.SLIME.getParticleID(), new ParticleBreaking.SlimeFactory());
/* 109 */     registerParticle(EnumParticleTypes.HEART.getParticleID(), new ParticleHeart.Factory());
/* 110 */     registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
/* 111 */     registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new ParticleBreaking.Factory());
/* 112 */     registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new ParticleDigging.Factory());
/* 113 */     registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new ParticleBlockDust.Factory());
/* 114 */     registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new ParticleExplosionHuge.Factory());
/* 115 */     registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new ParticleExplosionLarge.Factory());
/* 116 */     registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new ParticleFirework.Factory());
/* 117 */     registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new ParticleMobAppearance.Factory());
/* 118 */     registerParticle(EnumParticleTypes.DRAGON_BREATH.getParticleID(), new ParticleDragonBreath.Factory());
/* 119 */     registerParticle(EnumParticleTypes.END_ROD.getParticleID(), new ParticleEndRod.Factory());
/* 120 */     registerParticle(EnumParticleTypes.DAMAGE_INDICATOR.getParticleID(), new ParticleCrit.DamageIndicatorFactory());
/* 121 */     registerParticle(EnumParticleTypes.SWEEP_ATTACK.getParticleID(), new ParticleSweepAttack.Factory());
/* 122 */     registerParticle(EnumParticleTypes.TOTEM.getParticleID(), new ParticleTotem.Factory());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerParticle(int id, IParticleFactory particleFactory) {
/* 127 */     this.particleTypes.put(Integer.valueOf(id), particleFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void emitParticleAtEntity(Entity entityIn, EnumParticleTypes particleTypes) {
/* 132 */     this.particleEmitters.add(new ParticleEmitter(this.worldObj, entityIn, particleTypes));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191271_a(Entity p_191271_1_, EnumParticleTypes p_191271_2_, int p_191271_3_) {
/* 137 */     this.particleEmitters.add(new ParticleEmitter(this.worldObj, p_191271_1_, p_191271_2_, p_191271_3_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Particle spawnEffectParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 147 */     IParticleFactory iparticlefactory = this.particleTypes.get(Integer.valueOf(particleId));
/*     */     
/* 149 */     if (iparticlefactory != null) {
/*     */       
/* 151 */       Particle particle = iparticlefactory.createParticle(particleId, this.worldObj, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*     */       
/* 153 */       if (particle != null) {
/*     */         
/* 155 */         addEffect(particle);
/* 156 */         return particle;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(Particle effect) {
/* 165 */     if (effect != null)
/*     */     {
/* 167 */       if (!(effect instanceof ParticleFirework.Spark) || Config.isFireworkParticles())
/*     */       {
/* 169 */         this.queueEntityFX.add(effect);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEffects() {
/* 176 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 178 */       updateEffectLayer(i);
/*     */     }
/*     */     
/* 181 */     if (!this.particleEmitters.isEmpty()) {
/*     */       
/* 183 */       List<ParticleEmitter> list = Lists.newArrayList();
/*     */       
/* 185 */       for (ParticleEmitter particleemitter : this.particleEmitters) {
/*     */         
/* 187 */         particleemitter.onUpdate();
/*     */         
/* 189 */         if (!particleemitter.isAlive())
/*     */         {
/* 191 */           list.add(particleemitter);
/*     */         }
/*     */       } 
/*     */       
/* 195 */       this.particleEmitters.removeAll(list);
/*     */     } 
/*     */     
/* 198 */     if (!this.queueEntityFX.isEmpty())
/*     */     {
/* 200 */       for (Particle particle = this.queueEntityFX.poll(); particle != null; particle = this.queueEntityFX.poll()) {
/*     */         
/* 202 */         int j = particle.getFXLayer();
/* 203 */         int k = particle.isTransparent() ? 0 : 1;
/*     */         
/* 205 */         if (this.fxLayers[j][k].size() >= 16384)
/*     */         {
/* 207 */           this.fxLayers[j][k].removeFirst();
/*     */         }
/*     */         
/* 210 */         if (!(particle instanceof Barrier) || !reuseBarrierParticle(particle, this.fxLayers[j][k]))
/*     */         {
/* 212 */           this.fxLayers[j][k].add(particle);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEffectLayer(int layer) {
/* 220 */     this.worldObj.theProfiler.startSection(String.valueOf(layer));
/*     */     
/* 222 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 224 */       this.worldObj.theProfiler.startSection(String.valueOf(i));
/* 225 */       tickParticleList(this.fxLayers[layer][i]);
/* 226 */       this.worldObj.theProfiler.endSection();
/*     */     } 
/*     */     
/* 229 */     this.worldObj.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */   
/*     */   private void tickParticleList(Queue<Particle> p_187240_1_) {
/* 234 */     if (!p_187240_1_.isEmpty()) {
/*     */       
/* 236 */       Iterator<Particle> iterator = p_187240_1_.iterator();
/*     */       
/* 238 */       while (iterator.hasNext()) {
/*     */         
/* 240 */         Particle particle = iterator.next();
/* 241 */         tickParticle(particle);
/*     */         
/* 243 */         if (!particle.isAlive())
/*     */         {
/* 245 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickParticle(final Particle particle) {
/*     */     try {
/* 255 */       particle.onUpdate();
/*     */     }
/* 257 */     catch (Throwable throwable) {
/*     */       
/* 259 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
/* 260 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
/* 261 */       final int i = particle.getFXLayer();
/* 262 */       crashreportcategory.setDetail("Particle", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 266 */               return particle.toString();
/*     */             }
/*     */           });
/* 269 */       crashreportcategory.setDetail("Particle Type", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 273 */               if (i == 0)
/*     */               {
/* 275 */                 return "MISC_TEXTURE";
/*     */               }
/* 277 */               if (i == 1)
/*     */               {
/* 279 */                 return "TERRAIN_TEXTURE";
/*     */               }
/*     */ 
/*     */               
/* 283 */               return (i == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i);
/*     */             }
/*     */           });
/*     */       
/* 287 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticles(Entity entityIn, float partialTicks) {
/* 296 */     float f = ActiveRenderInfo.getRotationX();
/* 297 */     float f1 = ActiveRenderInfo.getRotationZ();
/* 298 */     float f2 = ActiveRenderInfo.getRotationYZ();
/* 299 */     float f3 = ActiveRenderInfo.getRotationXY();
/* 300 */     float f4 = ActiveRenderInfo.getRotationXZ();
/* 301 */     Particle.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 302 */     Particle.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 303 */     Particle.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 304 */     Particle.cameraViewDir = entityIn.getLook(partialTicks);
/* 305 */     GlStateManager.enableBlend();
/* 306 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 307 */     GlStateManager.alphaFunc(516, 0.003921569F);
/*     */     
/* 309 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 311 */       final int j = i;
/*     */       
/* 313 */       for (int k = 0; k < 2; k++) {
/*     */         
/* 315 */         if (!this.fxLayers[j][k].isEmpty()) {
/*     */           
/* 317 */           switch (k) {
/*     */             
/*     */             case 0:
/* 320 */               GlStateManager.depthMask(false);
/*     */               break;
/*     */             
/*     */             case 1:
/* 324 */               GlStateManager.depthMask(true);
/*     */               break;
/*     */           } 
/* 327 */           switch (j) {
/*     */ 
/*     */             
/*     */             default:
/* 331 */               this.renderer.bindTexture(PARTICLE_TEXTURES);
/*     */               break;
/*     */             
/*     */             case 1:
/* 335 */               this.renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*     */               break;
/*     */           } 
/* 338 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 339 */           Tessellator tessellator = Tessellator.getInstance();
/* 340 */           BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 341 */           bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */           
/* 343 */           for (Particle particle : this.fxLayers[j][k]) {
/*     */ 
/*     */             
/*     */             try {
/* 347 */               particle.renderParticle(bufferbuilder, entityIn, partialTicks, f, f4, f1, f2, f3);
/*     */             }
/* 349 */             catch (Throwable throwable) {
/*     */               
/* 351 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
/* 352 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
/* 353 */               crashreportcategory.setDetail("Particle", new ICrashReportDetail<String>()
/*     */                   {
/*     */                     public String call() throws Exception
/*     */                     {
/* 357 */                       return particle.toString();
/*     */                     }
/*     */                   });
/* 360 */               crashreportcategory.setDetail("Particle Type", new ICrashReportDetail<String>()
/*     */                   {
/*     */                     public String call() throws Exception
/*     */                     {
/* 364 */                       if (j == 0)
/*     */                       {
/* 366 */                         return "MISC_TEXTURE";
/*     */                       }
/* 368 */                       if (j == 1)
/*     */                       {
/* 370 */                         return "TERRAIN_TEXTURE";
/*     */                       }
/*     */ 
/*     */                       
/* 374 */                       return (j == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + j);
/*     */                     }
/*     */                   });
/*     */               
/* 378 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */           } 
/*     */           
/* 382 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 387 */     GlStateManager.depthMask(true);
/* 388 */     GlStateManager.disableBlend();
/* 389 */     GlStateManager.alphaFunc(516, 0.1F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLitParticles(Entity entityIn, float partialTick) {
/* 394 */     float f = 0.017453292F;
/* 395 */     float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
/* 396 */     float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
/* 397 */     float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 398 */     float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 399 */     float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);
/*     */     
/* 401 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 403 */       Queue<Particle> queue = this.fxLayers[3][i];
/*     */       
/* 405 */       if (!queue.isEmpty()) {
/*     */         
/* 407 */         Tessellator tessellator = Tessellator.getInstance();
/* 408 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/*     */         
/* 410 */         for (Particle particle : queue)
/*     */         {
/* 412 */           particle.renderParticle(bufferbuilder, entityIn, partialTick, f1, f5, f2, f3, f4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEffects(@Nullable World worldIn) {
/* 420 */     this.worldObj = worldIn;
/*     */     
/* 422 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 424 */       for (int j = 0; j < 2; j++)
/*     */       {
/* 426 */         this.fxLayers[i][j].clear();
/*     */       }
/*     */     } 
/*     */     
/* 430 */     this.particleEmitters.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockDestroyEffects(BlockPos pos, IBlockState state) {
/*     */     boolean flag;
/* 437 */     if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
/*     */       
/* 439 */       Block block = state.getBlock();
/* 440 */       flag = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, new Object[] { state, this.worldObj, pos }) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, new Object[] { this.worldObj, pos, this }));
/*     */     }
/*     */     else {
/*     */       
/* 444 */       flag = (state.getMaterial() != Material.AIR);
/*     */     } 
/*     */     
/* 447 */     if (flag) {
/*     */       
/* 449 */       state = state.getActualState((IBlockAccess)this.worldObj, pos);
/* 450 */       int l = 4;
/*     */       
/* 452 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 454 */         for (int j = 0; j < 4; j++) {
/*     */           
/* 456 */           for (int k = 0; k < 4; k++) {
/*     */             
/* 458 */             double d0 = (i + 0.5D) / 4.0D;
/* 459 */             double d1 = (j + 0.5D) / 4.0D;
/* 460 */             double d2 = (k + 0.5D) / 4.0D;
/* 461 */             addEffect((new ParticleDigging(this.worldObj, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, state)).setBlockPos(pos));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos pos, EnumFacing side) {
/* 473 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/*     */     
/* 475 */     if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*     */       
/* 477 */       int i = pos.getX();
/* 478 */       int j = pos.getY();
/* 479 */       int k = pos.getZ();
/* 480 */       float f = 0.1F;
/* 481 */       AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox((IBlockAccess)this.worldObj, pos);
/* 482 */       double d0 = i + this.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minX;
/* 483 */       double d1 = j + this.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minY;
/* 484 */       double d2 = k + this.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minZ;
/*     */       
/* 486 */       if (side == EnumFacing.DOWN)
/*     */       {
/* 488 */         d1 = j + axisalignedbb.minY - 0.10000000149011612D;
/*     */       }
/*     */       
/* 491 */       if (side == EnumFacing.UP)
/*     */       {
/* 493 */         d1 = j + axisalignedbb.maxY + 0.10000000149011612D;
/*     */       }
/*     */       
/* 496 */       if (side == EnumFacing.NORTH)
/*     */       {
/* 498 */         d2 = k + axisalignedbb.minZ - 0.10000000149011612D;
/*     */       }
/*     */       
/* 501 */       if (side == EnumFacing.SOUTH)
/*     */       {
/* 503 */         d2 = k + axisalignedbb.maxZ + 0.10000000149011612D;
/*     */       }
/*     */       
/* 506 */       if (side == EnumFacing.WEST)
/*     */       {
/* 508 */         d0 = i + axisalignedbb.minX - 0.10000000149011612D;
/*     */       }
/*     */       
/* 511 */       if (side == EnumFacing.EAST)
/*     */       {
/* 513 */         d0 = i + axisalignedbb.maxX + 0.10000000149011612D;
/*     */       }
/*     */       
/* 516 */       addEffect((new ParticleDigging(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatistics() {
/* 522 */     int i = 0;
/*     */     
/* 524 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 526 */       for (int k = 0; k < 2; k++)
/*     */       {
/* 528 */         i += this.fxLayers[j][k].size();
/*     */       }
/*     */     } 
/*     */     
/* 532 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean reuseBarrierParticle(Particle p_reuseBarrierParticle_1_, ArrayDeque<Particle> p_reuseBarrierParticle_2_) {
/* 537 */     for (Particle particle : p_reuseBarrierParticle_2_) {
/*     */       
/* 539 */       if (particle instanceof Barrier && p_reuseBarrierParticle_1_.prevPosX == particle.prevPosX && p_reuseBarrierParticle_1_.prevPosY == particle.prevPosY && p_reuseBarrierParticle_1_.prevPosZ == particle.prevPosZ) {
/*     */         
/* 541 */         particle.particleAge = 0;
/* 542 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 546 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos p_addBlockHitEffects_1_, RayTraceResult p_addBlockHitEffects_2_) {
/* 551 */     IBlockState iblockstate = this.worldObj.getBlockState(p_addBlockHitEffects_1_);
/*     */     
/* 553 */     if (iblockstate != null) {
/*     */       
/* 555 */       boolean flag = Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_addHitEffects, new Object[] { iblockstate, this.worldObj, p_addBlockHitEffects_2_, this });
/*     */       
/* 557 */       if (iblockstate != null && !flag)
/*     */       {
/* 559 */         addBlockHitEffects(p_addBlockHitEffects_1_, p_addBlockHitEffects_2_.sideHit);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
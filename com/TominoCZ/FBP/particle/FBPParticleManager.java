/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPReflectionHelper;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.particle.ParticleManager;
/*     */ import net.minecraft.client.particle.ParticleSmokeNormal;
/*     */ import net.minecraft.client.renderer.DestroyBlockProgress;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPParticleManager
/*     */   extends ParticleManager
/*     */ {
/*     */   private static MethodHandle getBlockDamage;
/*     */   private static MethodHandle getParticleScale;
/*     */   private static MethodHandle getParticleTexture;
/*     */   private static MethodHandle getParticleTypes;
/*     */   private static MethodHandle getSourceState;
/*     */   private static MethodHandle getParticleMaxAge;
/*     */   private static MethodHandle X;
/*     */   private static MethodHandle Y;
/*     */   private static MethodHandle Z;
/*     */   private static MethodHandle mX;
/*     */   private static MethodHandle mY;
/*     */   private static MethodHandle mZ;
/*     */   private static IParticleFactory particleFactory;
/*     */   private static IBlockState blockState;
/*     */   private static TextureAtlasSprite white;
/*     */   private static MethodHandles.Lookup lookup;
/*     */   Minecraft mc;
/*     */   
/*     */   public FBPParticleManager(World worldObjIn, TextureManager rendererIn, IParticleFactory particleFactory) {
/*  64 */     super(worldObjIn, rendererIn);
/*     */     
/*  66 */     FBPParticleManager.particleFactory = particleFactory;
/*     */     
/*  68 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  70 */     white = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.SNOW.getDefaultState());
/*     */     
/*  72 */     lookup = MethodHandles.publicLookup();
/*     */ 
/*     */     
/*     */     try {
/*  76 */       getParticleTypes = lookup.unreflectGetter(
/*  77 */           FBPReflectionHelper.findField(ParticleManager.class, new String[] { "field_178932_g", "particleTypes" }));
/*     */       
/*  79 */       X = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187126_f", "posX" }));
/*  80 */       Y = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187127_g", "posY" }));
/*  81 */       Z = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187128_h", "posZ" }));
/*     */       
/*  83 */       mX = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187129_i", "motionX" }));
/*  84 */       mY = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187130_j", "motionY" }));
/*  85 */       mZ = lookup.unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187131_k", "motionZ" }));
/*     */       
/*  87 */       getParticleScale = lookup
/*  88 */         .unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_70544_f", "particleScale" }));
/*  89 */       getParticleTexture = lookup
/*  90 */         .unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_187119_C", "particleTexture" }));
/*  91 */       getParticleMaxAge = lookup
/*  92 */         .unreflectGetter(FBPReflectionHelper.findField(Particle.class, new String[] { "field_70547_e", "particleMaxAge" }));
/*     */       
/*  94 */       getSourceState = lookup.unreflectGetter(
/*  95 */           FBPReflectionHelper.findField(ParticleDigging.class, new String[] { "field_174847_a", "sourceState" }));
/*  96 */       getBlockDamage = lookup
/*  97 */         .unreflectGetter(FBPReflectionHelper.findField(RenderGlobal.class, new String[] { "field_72738_E", "damagedBlocks" }));
/*  98 */     } catch (Throwable e) {
/*     */       
/* 100 */       throw Throwables.propagate(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void carryOver() {
/* 106 */     if ((Minecraft.getMinecraft()).effectRenderer == this) {
/*     */       return;
/*     */     }
/* 109 */     Field f1 = FBPReflectionHelper.findField(ParticleManager.class, new String[] { "field_78876_b", "fxLayers" });
/* 110 */     Field f2 = FBPReflectionHelper.findField(ParticleManager.class, new String[] { "field_178933_d", "particleEmitters" });
/* 111 */     Field f3 = FBPReflectionHelper.findField(ParticleManager.class, new String[] { "field_187241_h", "queue" });
/*     */ 
/*     */     
/*     */     try {
/* 115 */       MethodHandle getF1 = lookup.unreflectGetter(f1);
/* 116 */       MethodHandle setF1 = lookup.unreflectSetter(f1);
/*     */       
/* 118 */       MethodHandle getF2 = lookup.unreflectGetter(f2);
/* 119 */       MethodHandle setF2 = lookup.unreflectSetter(f2);
/*     */       
/* 121 */       MethodHandle getF3 = lookup.unreflectGetter(f3);
/* 122 */       MethodHandle setF3 = lookup.unreflectSetter(f3);
/*     */       
/* 124 */       setF1.invokeExact(this, getF1.invokeExact(this.mc.effectRenderer));
/* 125 */       setF2.invokeExact(this, getF2.invokeExact(this.mc.effectRenderer));
/* 126 */       setF3.invokeExact(this, getF3.invokeExact(this.mc.effectRenderer));
/* 127 */     } catch (Throwable e) {
/*     */       
/* 129 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(Particle effect) {
/*     */     FBPParticleDigging fBPParticleDigging;
/* 136 */     Particle toAdd = effect;
/*     */     
/* 138 */     if (toAdd != null && !(toAdd instanceof FBPParticleSnow) && !(toAdd instanceof FBPParticleRain)) {
/*     */       FBPParticleFlame fBPParticleFlame;
/* 140 */       if (FBP.fancyFlame && toAdd instanceof net.minecraft.client.particle.ParticleFlame && !(toAdd instanceof FBPParticleFlame) && 
/* 141 */         (Minecraft.getMinecraft()).gameSettings.particleSetting < 2)
/*     */       
/*     */       { 
/*     */         try {
/* 145 */           fBPParticleFlame = new FBPParticleFlame(this.worldObj, X.invokeExact(effect), Y.invokeExact(effect), 
/* 146 */               Z.invokeExact(effect), 0.0D, FBP.random.nextDouble() * 0.25D, 0.0D, true);
/* 147 */           effect.setExpired();
/* 148 */         } catch (Throwable t) {
/*     */           
/* 150 */           t.printStackTrace();
/*     */         }  }
/* 152 */       else { FBPParticleSmokeNormal fBPParticleSmokeNormal; if (FBP.fancySmoke && fBPParticleFlame instanceof ParticleSmokeNormal && 
/* 153 */           !(fBPParticleFlame instanceof FBPParticleSmokeNormal) && 
/* 154 */           (Minecraft.getMinecraft()).gameSettings.particleSetting < 2) {
/*     */           
/* 156 */           ParticleSmokeNormal p = (ParticleSmokeNormal)effect;
/*     */ 
/*     */           
/*     */           try {
/* 160 */             fBPParticleSmokeNormal = new FBPParticleSmokeNormal(this.worldObj, X.invokeExact(effect), 
/* 161 */                 Y.invokeExact(effect), Z.invokeExact(effect), 
/* 162 */                 mX.invokeExact(effect), mY.invokeExact(effect), 
/* 163 */                 mZ.invokeExact(effect), getParticleScale.invokeExact(effect), true, white, 
/* 164 */                 p);
/*     */             
/* 166 */             fBPParticleSmokeNormal.setRBGColorF(MathHelper.clamp(effect.getRedColorF() + 0.1F, 0.1F, 1.0F), 
/* 167 */                 MathHelper.clamp(effect.getGreenColorF() + 0.1F, 0.1F, 1.0F), 
/* 168 */                 MathHelper.clamp(effect.getBlueColorF() + 0.1F, 0.1F, 1.0F));
/*     */             
/* 170 */             fBPParticleSmokeNormal.setMaxAge(getParticleMaxAge.invokeExact(effect));
/* 171 */           } catch (Throwable t) {
/*     */             
/* 173 */             t.printStackTrace();
/*     */           } 
/* 175 */         } else if (FBP.fancyRain && fBPParticleSmokeNormal instanceof net.minecraft.client.particle.ParticleRain) {
/*     */           
/* 177 */           effect.setAlphaF(0.0F);
/* 178 */         } else if (fBPParticleSmokeNormal instanceof ParticleDigging && !(fBPParticleSmokeNormal instanceof FBPParticleDigging)) {
/*     */ 
/*     */           
/*     */           try {
/* 182 */             blockState = getSourceState.invokeExact((ParticleDigging)effect);
/*     */             
/* 184 */             if (blockState != null && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 185 */               FBP.spawnRedstoneBlockParticles || blockState.getBlock() != Blocks.REDSTONE_BLOCK)) {
/*     */               
/* 187 */               effect.setExpired();
/*     */               
/* 189 */               if (!(blockState.getBlock() instanceof net.minecraft.block.BlockLiquid) && 
/* 190 */                 !FBP.INSTANCE.isBlacklisted(blockState.getBlock(), true))
/*     */               
/* 192 */               { fBPParticleDigging = new FBPParticleDigging(this.worldObj, X.invokeExact(effect), 
/* 193 */                     Y.invokeExact(effect) - 0.10000000149011612D, 
/* 194 */                     Z.invokeExact(effect), 0.0D, 0.0D, 0.0D, 
/* 195 */                     getParticleScale.invokeExact(effect), fBPParticleSmokeNormal.getRedColorF(), 
/* 196 */                     fBPParticleSmokeNormal.getGreenColorF(), fBPParticleSmokeNormal.getBlueColorF(), blockState, null, 
/* 197 */                     getParticleTexture.invokeExact(effect)); }
/*     */               else { return; }
/*     */             
/*     */             } 
/* 201 */           } catch (Throwable e) {
/*     */             
/* 203 */             e.printStackTrace();
/*     */           } 
/* 205 */         } else if (fBPParticleDigging instanceof FBPParticleDigging) {
/*     */ 
/*     */           
/*     */           try {
/* 209 */             blockState = getSourceState.invokeExact((ParticleDigging)effect);
/*     */             
/* 211 */             if (blockState != null && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 212 */               FBP.spawnRedstoneBlockParticles || blockState.getBlock() != Blocks.REDSTONE_BLOCK))
/*     */             {
/*     */               
/* 215 */               if (blockState.getBlock() instanceof net.minecraft.block.BlockLiquid || 
/* 216 */                 FBP.INSTANCE.isBlacklisted(blockState.getBlock(), true)) {
/*     */                 
/* 218 */                 effect.setExpired();
/*     */                 return;
/*     */               } 
/*     */             }
/* 222 */           } catch (Throwable e) {
/*     */             
/* 224 */             e.printStackTrace();
/*     */           } 
/*     */         }  }
/*     */     
/*     */     } 
/* 229 */     if (fBPParticleDigging != effect) {
/* 230 */       effect.setExpired();
/*     */     }
/* 232 */     super.addEffect((Particle)fBPParticleDigging);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Particle spawnEffectParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 240 */     IParticleFactory iparticlefactory = null;
/*     */ 
/*     */     
/*     */     try {
/* 244 */       iparticlefactory = (IParticleFactory)getParticleTypes.invokeExact(this)
/* 245 */         .get(Integer.valueOf(particleId));
/* 246 */     } catch (Throwable e) {
/*     */       
/* 248 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 251 */     if (iparticlefactory != null) {
/*     */       
/* 253 */       Particle particle = iparticlefactory.createParticle(particleId, this.worldObj, xCoord, yCoord, zCoord, xSpeed, 
/* 254 */           ySpeed, zSpeed, parameters); Particle toSpawn = particle;
/*     */       
/* 256 */       if (particle instanceof ParticleDigging && !(particle instanceof FBPParticleDigging)) {
/*     */         
/* 258 */         blockState = Block.getStateById(parameters[0]);
/*     */         
/* 260 */         if (blockState != null && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 261 */           FBP.spawnRedstoneBlockParticles || blockState.getBlock() != Blocks.REDSTONE_BLOCK))
/*     */         {
/* 263 */           if (!(blockState.getBlock() instanceof net.minecraft.block.BlockLiquid) && 
/* 264 */             !FBP.INSTANCE.isBlacklisted(blockState.getBlock(), true)) {
/*     */             
/* 266 */             toSpawn = (new FBPParticleDigging(this.worldObj, xCoord, yCoord + 0.10000000149011612D, zCoord, 
/* 267 */                 xSpeed, ySpeed, zSpeed, -1.0F, 1.0F, 1.0F, 1.0F, blockState, null, null)).init()
/* 268 */               .multipleParticleScaleBy(0.6F);
/*     */           } else {
/* 270 */             toSpawn = particle;
/*     */           } 
/*     */         }
/*     */       } 
/* 274 */       addEffect(toSpawn);
/*     */       
/* 276 */       return toSpawn;
/*     */     } 
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockDestroyEffects(BlockPos pos, IBlockState state) {
/* 284 */     Block b = state.getBlock();
/* 285 */     if (!b.isAir() && b != FBP.FBPBlock) {
/*     */       
/* 287 */       state = state.getActualState((IBlockAccess)this.worldObj, pos);
/* 288 */       b = state.getBlock();
/* 289 */       int i = 4;
/*     */       
/* 291 */       TextureAtlasSprite texture = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
/*     */       
/* 293 */       for (int j = 0; j < FBP.particlesPerAxis; j++) {
/*     */         
/* 295 */         for (int k = 0; k < FBP.particlesPerAxis; k++) {
/*     */           
/* 297 */           for (int l = 0; l < FBP.particlesPerAxis; l++) {
/*     */             
/* 299 */             double d0 = pos.getX() + (j + 0.5D) / FBP.particlesPerAxis;
/* 300 */             double d1 = pos.getY() + (k + 0.5D) / FBP.particlesPerAxis;
/* 301 */             double d2 = pos.getZ() + (l + 0.5D) / FBP.particlesPerAxis;
/*     */             
/* 303 */             if (state != null && !(b instanceof net.minecraft.block.BlockLiquid) && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 304 */               FBP.spawnRedstoneBlockParticles || b != Blocks.REDSTONE_BLOCK) && 
/* 305 */               !FBP.INSTANCE.isBlacklisted(b, true)) {
/*     */               
/* 307 */               float scale = (float)FBP.random.nextDouble(0.75D, 1.0D);
/*     */               
/* 309 */               FBPParticleDigging toSpawn = (new FBPParticleDigging(this.worldObj, d0, d1, d2, 
/* 310 */                   d0 - pos.getX() - 0.5D, -0.001D, d2 - pos.getZ() - 0.5D, scale, 1.0F, 1.0F, 1.0F, state, null, 
/* 311 */                   texture)).setBlockPos(pos);
/*     */               
/* 313 */               addEffect((Particle)toSpawn);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos pos, EnumFacing side) {
/* 324 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/*     */     
/* 326 */     if (iblockstate.getBlock() == FBP.FBPBlock) {
/*     */       return;
/*     */     }
/* 329 */     if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*     */       
/* 331 */       int i = pos.getX();
/* 332 */       int j = pos.getY();
/* 333 */       int k = pos.getZ();
/* 334 */       float f = 0.1F;
/* 335 */       AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox((IBlockAccess)this.worldObj, pos);
/*     */       
/* 337 */       double d0 = 0.0D, d1 = 0.0D, d2 = 0.0D;
/*     */       
/* 339 */       RayTraceResult obj = (Minecraft.getMinecraft()).objectMouseOver;
/*     */       
/* 341 */       if (obj == null || obj.hitVec == null) {
/* 342 */         obj = new RayTraceResult(null, new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D));
/*     */       }
/* 344 */       if (FBP.smartBreaking && iblockstate != null && 
/* 345 */         !(iblockstate.getBlock() instanceof net.minecraft.block.BlockLiquid) && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 346 */         FBP.spawnRedstoneBlockParticles || iblockstate.getBlock() != Blocks.REDSTONE_BLOCK)) {
/*     */         
/* 348 */         d0 = obj.hitVec.xCoord + 
/* 349 */           FBP.random.nextDouble(-0.21D, 0.21D) * Math.abs(axisalignedbb.maxX - axisalignedbb.minX);
/* 350 */         d1 = obj.hitVec.yCoord + 
/* 351 */           FBP.random.nextDouble(-0.21D, 0.21D) * Math.abs(axisalignedbb.maxY - axisalignedbb.minY);
/* 352 */         d2 = obj.hitVec.zCoord + 
/* 353 */           FBP.random.nextDouble(-0.21D, 0.21D) * Math.abs(axisalignedbb.maxZ - axisalignedbb.minZ);
/*     */       } else {
/*     */         
/* 356 */         d0 = i + this.worldObj.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.20000000298023224D) + 
/* 357 */           0.10000000149011612D + axisalignedbb.minX;
/* 358 */         d1 = j + this.worldObj.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.20000000298023224D) + 
/* 359 */           0.10000000149011612D + axisalignedbb.minY;
/* 360 */         d2 = k + this.worldObj.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.20000000298023224D) + 
/* 361 */           0.10000000149011612D + axisalignedbb.minZ;
/*     */       } 
/*     */       
/* 364 */       switch (side) {
/*     */         
/*     */         case null:
/* 367 */           d1 = j + axisalignedbb.minY - 0.10000000149011612D;
/*     */           break;
/*     */         case EAST:
/* 370 */           d0 = i + axisalignedbb.maxX + 0.10000000149011612D;
/*     */           break;
/*     */         case NORTH:
/* 373 */           d2 = k + axisalignedbb.minZ - 0.10000000149011612D;
/*     */           break;
/*     */         case SOUTH:
/* 376 */           d2 = k + axisalignedbb.maxZ + 0.10000000149011612D;
/*     */           break;
/*     */         case UP:
/* 379 */           d1 = j + axisalignedbb.maxY + 0.08000000119D;
/*     */           break;
/*     */         case WEST:
/* 382 */           d0 = i + axisalignedbb.minX - 0.10000000149011612D;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 388 */       if (iblockstate != null && 
/* 389 */         !(iblockstate.getBlock() instanceof net.minecraft.block.BlockLiquid) && (!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 390 */         FBP.spawnRedstoneBlockParticles || iblockstate.getBlock() != Blocks.REDSTONE_BLOCK)) {
/*     */ 
/*     */         
/* 393 */         int damage = 0;
/*     */ 
/*     */         
/*     */         try {
/* 397 */           DestroyBlockProgress progress = null;
/* 398 */           Map mp = getBlockDamage
/* 399 */             .invokeExact((Minecraft.getMinecraft()).renderGlobal);
/*     */           
/* 401 */           if (!mp.isEmpty()) {
/*     */             
/* 403 */             Iterator<DestroyBlockProgress> it = mp.values().iterator();
/*     */             
/* 405 */             while (it.hasNext()) {
/*     */               
/* 407 */               progress = it.next();
/*     */               
/* 409 */               if (progress.getPosition().equals(pos)) {
/*     */                 
/* 411 */                 damage = progress.getPartialBlockDamage();
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 416 */         } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 423 */         if (!FBP.INSTANCE.isBlacklisted(iblockstate.getBlock(), true)) {
/*     */           Particle particle;
/* 425 */           FBPParticleDigging fBPParticleDigging = (new FBPParticleDigging(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, -2.0F, 1.0F, 1.0F, 1.0F, iblockstate, 
/* 426 */               side, null)).setBlockPos(pos);
/*     */           
/* 428 */           if (FBP.smartBreaking) {
/*     */             
/* 430 */             particle = fBPParticleDigging.MultiplyVelocity((side == EnumFacing.UP) ? 0.7F : 0.15F);
/* 431 */             particle = particle.multipleParticleScaleBy(0.325F + damage / 10.0F * 0.5F);
/*     */           } else {
/*     */             
/* 434 */             particle = ((FBPParticleDigging)particle).MultiplyVelocity(0.2F);
/* 435 */             particle = particle.multipleParticleScaleBy(0.6F);
/*     */           } 
/*     */           
/* 438 */           addEffect(particle);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
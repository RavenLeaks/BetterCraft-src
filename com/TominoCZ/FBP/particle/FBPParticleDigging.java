/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPMathUtil;
/*     */ import com.TominoCZ.FBP.util.FBPRenderUtil;
/*     */ import com.TominoCZ.FBP.vector.FBPVector3d;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.renderer.BlockModelShapes;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Vector3d;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPParticleDigging
/*     */   extends ParticleDigging
/*     */ {
/*     */   private final IBlockState sourceState;
/*     */   Minecraft mc;
/*     */   float prevGravity;
/*     */   double startY;
/*  44 */   double endMult = 0.75D; double scaleAlpha; double prevParticleScale; double prevParticleAlpha; double prevMotionX; double prevMotionZ; boolean modeDebounce;
/*     */   boolean wasFrozen;
/*     */   boolean destroyed;
/*     */   boolean killToggle;
/*     */   EnumFacing facing;
/*     */   FBPVector3d rot;
/*     */   FBPVector3d prevRot;
/*     */   FBPVector3d rotStep;
/*     */   Vec2f[] par;
/*     */   
/*  54 */   static Entity dummyEntity = new Entity(null)
/*     */     {
/*     */       protected void writeEntityToNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       protected void readEntityFromNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       protected void entityInit() {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FBPParticleDigging(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale, float R, float G, float B, IBlockState state, @Nullable EnumFacing facing, @Nullable TextureAtlasSprite texture) {
/*  78 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
/*     */     
/*  80 */     this.particleRed = R;
/*  81 */     this.particleGreen = G;
/*  82 */     this.particleBlue = B;
/*     */     
/*  84 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  86 */     this.rot = new FBPVector3d();
/*  87 */     this.prevRot = new FBPVector3d();
/*     */     
/*  89 */     this.facing = facing;
/*     */     
/*  91 */     createRotationMatrix();
/*     */ 
/*     */     
/*     */     try {
/*  95 */       FBP.setSourcePos.invokeExact(this, new BlockPos(xCoordIn, yCoordIn, zCoordIn));
/*  96 */     } catch (Throwable e1) {
/*     */       
/*  98 */       e1.printStackTrace();
/*     */     } 
/*     */     
/* 101 */     if (scale > -1.0F) {
/* 102 */       this.particleScale = scale;
/*     */     }
/* 104 */     if (scale < -1.0F)
/*     */     {
/* 106 */       if (facing != null)
/*     */       {
/* 108 */         if (facing == EnumFacing.UP && FBP.smartBreaking) {
/*     */           
/* 110 */           this.motionX *= 1.5D;
/* 111 */           this.motionY *= 0.1D;
/* 112 */           this.motionZ *= 1.5D;
/*     */           
/* 114 */           double particleSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */           
/* 116 */           double x = FBPMathUtil.add(cameraViewDir.xCoord, 0.01D);
/* 117 */           double z = FBPMathUtil.add(cameraViewDir.zCoord, 0.01D);
/*     */           
/* 119 */           this.motionX = x * particleSpeed;
/* 120 */           this.motionZ = z * particleSpeed;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 125 */     if (this.modeDebounce = !FBP.randomRotation) {
/*     */       
/* 127 */       this.rot.zero();
/* 128 */       calculateYAngle();
/*     */     } 
/*     */     
/* 131 */     this.sourceState = state;
/*     */     
/* 133 */     Block b = state.getBlock();
/*     */     
/* 135 */     this.particleGravity = (float)(b.blockParticleGravity * FBP.gravityMult);
/*     */     
/* 137 */     this.particleScale = (float)(FBP.scaleMult * (FBP.randomizedScale ? this.particleScale : 1.0F));
/* 138 */     this.particleMaxAge = (int)FBP.random.nextDouble(FBP.minAge, FBP.maxAge + 0.5D);
/*     */     
/* 140 */     this.scaleAlpha = this.particleScale * 0.82D;
/*     */     
/* 142 */     this.destroyed = (facing == null);
/*     */     
/* 144 */     if (texture == null) {
/*     */       
/* 146 */       BlockModelShapes blockModelShapes = this.mc.getBlockRendererDispatcher().getBlockModelShapes();
/*     */ 
/*     */       
/* 149 */       if (!this.destroyed) {
/*     */         
/*     */         try {
/*     */           
/* 153 */           List<BakedQuad> quads = blockModelShapes.getModelForState(state).getQuads(state, facing, 0L);
/*     */           
/* 155 */           if (quads != null && !quads.isEmpty())
/* 156 */             this.particleTexture = ((BakedQuad)quads.get(0)).getSprite(); 
/* 157 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 162 */       if (this.particleTexture == null || this.particleTexture.getIconName().equals("missingno"))
/* 163 */         setParticleTexture(blockModelShapes.getTexture(state)); 
/*     */     } else {
/* 165 */       this.particleTexture = texture;
/*     */     } 
/* 167 */     if (FBP.randomFadingSpeed) {
/* 168 */       this.endMult = MathHelper.clamp(FBP.random.nextDouble(0.5D, 0.9D), 0.55D, 0.8D);
/*     */     }
/* 170 */     this.prevGravity = this.particleGravity;
/*     */     
/* 172 */     this.startY = this.posY;
/*     */     
/* 174 */     multipleParticleScaleBy(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Particle multipleParticleScaleBy(float scale) {
/* 180 */     Particle p = super.multipleParticleScaleBy(scale);
/*     */     
/* 182 */     if (!FBP.isEnabled()) {
/* 183 */       return p;
/*     */     }
/*     */     
/* 186 */     float f = this.particleScale / 10.0F;
/*     */     
/* 188 */     if (FBP.restOnFloor && this.destroyed) {
/* 189 */       this.posY = this.prevPosY = this.startY - f;
/*     */     }
/* 191 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - f, this.posY, this.posZ - f, this.posX + f, this.posY + (2.0F * f), this.posZ + f));
/*     */     
/* 193 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle MultiplyVelocity(float multiplier) {
/* 198 */     this.motionX *= multiplier;
/* 199 */     this.motionY = (this.motionY - 0.10000000149011612D) * (multiplier / 2.0F) + 0.10000000149011612D;
/* 200 */     this.motionZ *= multiplier;
/* 201 */     return (Particle)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void multiplyColor(@Nullable BlockPos p_187154_1_) {
/* 207 */     if (this.sourceState.getBlock() == Blocks.GRASS && this.facing != EnumFacing.UP) {
/*     */       return;
/*     */     }
/* 210 */     int i = this.mc.getBlockColors().colorMultiplier(this.sourceState, (IBlockAccess)this.worldObj, p_187154_1_, 0);
/* 211 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/* 212 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/* 213 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FBPParticleDigging init() {
/* 219 */     if (!FBP.isEnabled()) {
/* 220 */       return this;
/*     */     }
/* 222 */     multiplyColor(new BlockPos(this.posX, this.posY, this.posZ));
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FBPParticleDigging setBlockPos(BlockPos pos) {
/* 229 */     if (!FBP.isEnabled()) {
/* 230 */       return this;
/*     */     }
/* 232 */     multiplyColor(pos);
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 239 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 245 */     if (!FBP.isEnabled()) {
/* 246 */       super.onUpdate();
/*     */       return;
/*     */     } 
/* 249 */     boolean allowedToMove = !(MathHelper.abs((float)this.motionX) <= 1.0E-4D && MathHelper.abs((float)this.motionZ) <= 1.0E-4D);
/*     */     
/* 251 */     if (!FBP.frozen && FBP.bounceOffWalls && !this.mc.isGamePaused() && this.particleAge > 0)
/*     */     {
/* 253 */       if (!this.wasFrozen && allowedToMove) {
/*     */         
/* 255 */         boolean xCollided = (this.prevPosX == this.posX);
/* 256 */         boolean zCollided = (this.prevPosZ == this.posZ);
/*     */         
/* 258 */         if (xCollided)
/* 259 */           this.motionX = -this.prevMotionX * 0.625D; 
/* 260 */         if (zCollided) {
/* 261 */           this.motionZ = -this.prevMotionZ * 0.625D;
/*     */         }
/* 263 */         if (!FBP.randomRotation && (xCollided || zCollided))
/* 264 */           calculateYAngle(); 
/*     */       } else {
/* 266 */         this.wasFrozen = false;
/*     */       }  } 
/* 268 */     if (FBP.frozen && FBP.bounceOffWalls && !this.wasFrozen) {
/* 269 */       this.wasFrozen = true;
/*     */     }
/* 271 */     this.prevPosX = this.posX;
/* 272 */     this.prevPosY = this.posY;
/* 273 */     this.prevPosZ = this.posZ;
/*     */     
/* 275 */     this.prevRot.copyFrom((Vector3d)this.rot);
/*     */     
/* 277 */     this.prevParticleAlpha = this.particleAlpha;
/* 278 */     this.prevParticleScale = this.particleScale;
/*     */     
/* 280 */     if (!this.mc.isGamePaused() && (!FBP.frozen || this.killToggle)) {
/*     */       
/* 282 */       if (!this.killToggle)
/*     */       {
/* 284 */         if (!FBP.randomRotation) {
/*     */           
/* 286 */           if (!this.modeDebounce) {
/*     */             
/* 288 */             this.modeDebounce = true;
/*     */             
/* 290 */             this.rot.z = 0.0D;
/*     */             
/* 292 */             calculateYAngle();
/*     */           } 
/*     */           
/* 295 */           if (allowedToMove) {
/*     */             
/* 297 */             double x = MathHelper.abs((float)(this.rotStep.x * getMult()));
/*     */             
/* 299 */             if (this.motionX > 0.0D) {
/*     */               
/* 301 */               if (this.motionZ > 0.0D)
/* 302 */               { this.rot.x -= x; }
/* 303 */               else if (this.motionZ < 0.0D)
/* 304 */               { this.rot.x += x; } 
/* 305 */             } else if (this.motionX < 0.0D) {
/*     */               
/* 307 */               if (this.motionZ < 0.0D) {
/* 308 */                 this.rot.x += x;
/* 309 */               } else if (this.motionZ > 0.0D) {
/*     */                 
/* 311 */                 this.rot.x -= x;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 317 */           if (this.modeDebounce) {
/*     */             
/* 319 */             this.modeDebounce = false;
/*     */             
/* 321 */             this.rot.z = FBP.random.nextDouble(30.0D, 400.0D);
/*     */           } 
/*     */           
/* 324 */           if (allowedToMove) {
/* 325 */             this.rot.add((Vector3d)this.rotStep.multiply(getMult()));
/*     */           }
/*     */         } 
/*     */       }
/* 329 */       if (!FBP.infiniteDuration) {
/* 330 */         this.particleAge++;
/*     */       }
/* 332 */       if (this.particleAge >= this.particleMaxAge || this.killToggle) {
/*     */         
/* 334 */         this.particleScale = (float)(this.particleScale * 0.8876543045043945D * this.endMult);
/*     */         
/* 336 */         if (this.particleAlpha > 0.01D && this.particleScale <= this.scaleAlpha) {
/* 337 */           this.particleAlpha = (float)(this.particleAlpha * 0.6875200271606445D * this.endMult);
/*     */         }
/* 339 */         if (this.particleAlpha <= 0.01D) {
/* 340 */           setExpired();
/*     */         }
/*     */       } 
/* 343 */       if (!this.killToggle) {
/*     */         
/* 345 */         if (!this.onGround) {
/* 346 */           this.motionY -= 0.04D * this.particleGravity;
/*     */         }
/* 348 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */         
/* 350 */         if (this.onGround && FBP.restOnFloor) {
/*     */           
/* 352 */           this.rot.x = ((float)Math.round(this.rot.x / 90.0D) * 90.0F);
/* 353 */           this.rot.z = ((float)Math.round(this.rot.z / 90.0D) * 90.0F);
/*     */         } 
/*     */         
/* 356 */         if (MathHelper.abs((float)this.motionX) > 1.0E-5D)
/* 357 */           this.prevMotionX = this.motionX; 
/* 358 */         if (MathHelper.abs((float)this.motionZ) > 1.0E-5D) {
/* 359 */           this.prevMotionZ = this.motionZ;
/*     */         }
/* 361 */         if (allowedToMove) {
/*     */           
/* 363 */           this.motionX *= 0.9800000190734863D;
/* 364 */           this.motionZ *= 0.9800000190734863D;
/*     */         } 
/*     */         
/* 367 */         this.motionY *= 0.9800000190734863D;
/*     */ 
/*     */         
/* 370 */         if (FBP.entityCollision) {
/*     */           
/* 372 */           List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox());
/*     */           
/* 374 */           for (Entity entityIn : list) {
/*     */             
/* 376 */             if (!entityIn.noClip) {
/*     */               
/* 378 */               double d0 = this.posX - entityIn.posX;
/* 379 */               double d1 = this.posZ - entityIn.posZ;
/* 380 */               double d2 = MathHelper.absMax(d0, d1);
/*     */               
/* 382 */               if (d2 >= 0.009999999776482582D) {
/*     */                 
/* 384 */                 d2 = Math.sqrt(d2);
/* 385 */                 d0 /= d2;
/* 386 */                 d1 /= d2;
/*     */                 
/* 388 */                 double d3 = 1.0D / d2;
/*     */                 
/* 390 */                 if (d3 > 1.0D) {
/* 391 */                   d3 = 1.0D;
/*     */                 }
/* 393 */                 this.motionX += d0 * d3 / 20.0D;
/* 394 */                 this.motionZ += d1 * d3 / 20.0D;
/*     */                 
/* 396 */                 if (!FBP.randomRotation)
/* 397 */                   calculateYAngle(); 
/* 398 */                 if (!FBP.frozen) {
/* 399 */                   this.onGround = false;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 405 */         if (FBP.waterPhysics)
/*     */         {
/* 407 */           if (isInWater()) {
/*     */             
/* 409 */             handleWaterMovement();
/*     */             
/* 411 */             if (FBP.INSTANCE.doesMaterialFloat(this.sourceState.getMaterial())) {
/*     */               
/* 413 */               this.motionY = (0.11F + this.particleScale / 1.25F * 0.02F);
/*     */             } else {
/*     */               
/* 416 */               this.motionX *= 0.932515086137662D;
/* 417 */               this.motionZ *= 0.932515086137662D;
/* 418 */               this.particleGravity = 0.35F;
/*     */               
/* 420 */               this.motionY *= 0.8500000238418579D;
/*     */             } 
/*     */             
/* 423 */             if (!FBP.randomRotation) {
/* 424 */               calculateYAngle();
/*     */             }
/* 426 */             if (this.onGround) {
/* 427 */               this.onGround = false;
/*     */             }
/*     */           } else {
/* 430 */             this.particleGravity = this.prevGravity;
/*     */           } 
/*     */         }
/*     */         
/* 434 */         if (this.onGround)
/*     */         {
/* 436 */           if (FBP.lowTraction) {
/*     */             
/* 438 */             this.motionX *= 0.932515086137662D;
/* 439 */             this.motionZ *= 0.932515086137662D;
/*     */           } else {
/*     */             
/* 442 */             this.motionX *= 0.6654999988079071D;
/* 443 */             this.motionZ *= 0.6654999988079071D;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInWater() {
/* 452 */     double scale = (this.particleScale / 20.0F);
/*     */     
/* 454 */     int minX = MathHelper.floor(this.posX - scale);
/* 455 */     int maxX = MathHelper.ceil(this.posX + scale);
/*     */     
/* 457 */     int minY = MathHelper.floor(this.posY - scale);
/* 458 */     int maxY = MathHelper.ceil(this.posY + scale);
/*     */     
/* 460 */     int minZ = MathHelper.floor(this.posZ - scale);
/* 461 */     int maxZ = MathHelper.ceil(this.posZ + scale);
/*     */     
/* 463 */     if (this.worldObj.isAreaLoaded(new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), true))
/*     */     {
/* 465 */       for (int x = minX; x < maxX; x++) {
/*     */         
/* 467 */         for (int y = minY; y < maxY; y++) {
/*     */           
/* 469 */           for (int z = minZ; z < maxZ; z++) {
/*     */             
/* 471 */             IBlockState block = this.worldObj.getBlockState(new BlockPos(x, y, z));
/*     */             
/* 473 */             if (block.getMaterial() == Material.WATER) {
/*     */               
/* 475 */               double d0 = ((y + 1) - 
/* 476 */                 BlockLiquid.getLiquidHeightPercent(((Integer)block.getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*     */               
/* 478 */               if (this.posY <= d0) {
/* 479 */                 return true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 486 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWaterMovement() {
/* 491 */     dummyEntity.motionX = this.motionX;
/* 492 */     dummyEntity.motionY = this.motionY;
/* 493 */     dummyEntity.motionZ = this.motionZ;
/*     */     
/* 495 */     double scale = (this.particleScale / 20.0F);
/* 496 */     if (this.worldObj.handleMaterialAcceleration(
/* 497 */         getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D), 
/* 498 */         Material.WATER, dummyEntity)) {
/*     */ 
/*     */       
/* 501 */       this.motionX = dummyEntity.motionX;
/* 502 */       this.motionY = dummyEntity.motionY;
/* 503 */       this.motionZ = dummyEntity.motionZ;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 510 */     if (!FBP.isEnabled()) {
/* 511 */       super.moveEntity(x, y, z);
/*     */       return;
/*     */     } 
/* 514 */     double X = x;
/* 515 */     double Y = y;
/* 516 */     double Z = z;
/*     */     
/* 518 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().expand(x, y, z));
/*     */     
/* 520 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 522 */       y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */     }
/*     */     
/* 525 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     
/* 527 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 529 */       x = axisalignedbb.calculateXOffset(getEntityBoundingBox(), x);
/*     */     }
/*     */     
/* 532 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */     
/* 534 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 536 */       z = axisalignedbb.calculateZOffset(getEntityBoundingBox(), z);
/*     */     }
/*     */     
/* 539 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */ 
/*     */     
/* 542 */     resetPositionToBB();
/* 543 */     this.onGround = (y != Y && Y < 0.0D);
/*     */     
/* 545 */     if (!FBP.lowTraction && !FBP.bounceOffWalls) {
/*     */       
/* 547 */       if (x != X)
/* 548 */         this.motionX *= 0.699999988079071D; 
/* 549 */       if (z != Z) {
/* 550 */         this.motionZ *= 0.699999988079071D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder buf, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 558 */     if (!FBP.isEnabled()) {
/* 559 */       super.renderParticle(buf, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       return;
/*     */     } 
/* 562 */     if (!FBP.isEnabled() && this.particleMaxAge != 0) {
/* 563 */       this.particleMaxAge = 0;
/*     */     }
/*     */ 
/*     */     
/* 567 */     float f = 0.0F, f1 = 0.0F, f2 = 0.0F, f3 = 0.0F;
/*     */     
/* 569 */     float f4 = (float)(this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks);
/*     */     
/* 571 */     if (this.particleTexture != null) {
/*     */       
/* 573 */       if (!FBP.cartoonMode) {
/*     */         
/* 575 */         f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/* 576 */         f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*     */       } 
/*     */       
/* 579 */       f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/* 580 */       f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } else {
/*     */       
/* 583 */       f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 584 */       f1 = f + 0.015609375F;
/* 585 */       f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 586 */       f3 = f2 + 0.015609375F;
/*     */     } 
/*     */     
/* 589 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 590 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 591 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*     */     
/* 593 */     int i = getBrightnessForRender(partialTicks);
/*     */     
/* 595 */     this.par = new Vec2f[] { new Vec2f(f1, f3), new Vec2f(f1, f2), new Vec2f(f, f2), new Vec2f(f, f3) };
/*     */     
/* 597 */     float alpha = (float)(this.prevParticleAlpha + (this.particleAlpha - this.prevParticleAlpha) * partialTicks);
/*     */     
/* 599 */     if (FBP.restOnFloor) {
/* 600 */       f6 += f4 / 10.0F;
/*     */     }
/* 602 */     FBPVector3d smoothRot = new FBPVector3d(0.0D, 0.0D, 0.0D);
/*     */     
/* 604 */     if (FBP.rotationMult > 0.0D) {
/*     */       
/* 606 */       smoothRot.y = this.rot.y;
/* 607 */       smoothRot.z = this.rot.z;
/*     */       
/* 609 */       if (!FBP.randomRotation) {
/* 610 */         smoothRot.x = this.rot.x;
/*     */       }
/*     */       
/* 613 */       if (!FBP.frozen) {
/*     */         
/* 615 */         FBPVector3d vec = this.rot.partialVec(this.prevRot, partialTicks);
/*     */         
/* 617 */         if (FBP.randomRotation) {
/*     */           
/* 619 */           smoothRot.y = vec.y;
/* 620 */           smoothRot.z = vec.z;
/*     */         } else {
/*     */           
/* 623 */           smoothRot.x = vec.x;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 628 */     int j = i >> 16 & 0xFFFF;
/* 629 */     int k = i & 0xFFFF;
/*     */     
/* 631 */     float[] arr = { f1, f3, f1, f2, f, f2, f, f3 };
/*     */ 
/*     */     
/* 634 */     FBPRenderUtil.renderCubeShaded_S(buf, this.par, f5, f6, f7, (f4 / 10.0F), smoothRot, i >> 16 & 0xFFFF, i & 0xFFFF, 
/* 635 */         this.particleRed, this.particleGreen, this.particleBlue, alpha, FBP.cartoonMode);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createRotationMatrix() {
/* 640 */     double rx0 = FBP.random.nextDouble();
/* 641 */     double ry0 = FBP.random.nextDouble();
/* 642 */     double rz0 = FBP.random.nextDouble();
/*     */     
/* 644 */     this.rotStep = new FBPVector3d(((rx0 > 0.5D) ? true : -1), ((ry0 > 0.5D) ? true : -1), ((rz0 > 0.5D) ? true : -1));
/*     */     
/* 646 */     this.rot.copyFrom((Vector3d)this.rotStep);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 653 */     AxisAlignedBB box = getEntityBoundingBox();
/*     */     
/* 655 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/*     */       
/* 657 */       double d0 = (box.maxY - box.minY) * 0.66D;
/* 658 */       double k = this.posY + d0 + 0.01D - (FBP.restOnFloor ? (this.particleScale / 10.0F) : 0.0F);
/* 659 */       return this.worldObj.getCombinedLight(new BlockPos(this.posX, k, this.posZ), 0);
/*     */     } 
/*     */     
/* 662 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 673 */       return (Particle)(new FBPParticleDigging(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, -1.0F, 1.0F, 
/* 674 */           1.0F, 1.0F, Block.getStateById(p_178902_15_[0]), null, null)).init();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void calculateYAngle() {
/* 680 */     double angleSin = Math.toDegrees(Math.asin(this.motionX / Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)));
/*     */     
/* 682 */     if (this.motionX > 0.0D) {
/*     */       
/* 684 */       if (this.motionZ > 0.0D) {
/* 685 */         this.rot.y = -angleSin;
/*     */       } else {
/* 687 */         this.rot.y = angleSin;
/*     */       }
/*     */     
/* 690 */     } else if (this.motionZ > 0.0D) {
/* 691 */       this.rot.y = -angleSin;
/*     */     } else {
/* 693 */       this.rot.y = angleSin;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   double getMult() {
/* 699 */     return Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * (FBP.randomRotation ? 'È' : 'Ǵ') * FBP.rotationMult;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleDigging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
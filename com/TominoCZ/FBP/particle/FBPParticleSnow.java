/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPRenderUtil;
/*     */ import com.TominoCZ.FBP.vector.FBPVector3d;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Vector3d;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FBPParticleSnow
/*     */   extends ParticleDigging
/*     */ {
/*     */   private final IBlockState sourceState;
/*     */   Minecraft mc;
/*     */   double scaleAlpha;
/*     */   double prevParticleScale;
/*     */   double prevParticleAlpha;
/*  30 */   double endMult = 1.0D;
/*     */   
/*     */   FBPVector3d rot;
/*     */   
/*     */   FBPVector3d prevRot;
/*     */   FBPVector3d rotStep;
/*     */   Vec2f[] par;
/*     */   
/*     */   public FBPParticleSnow(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/*  39 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
/*     */ 
/*     */     
/*     */     try {
/*  43 */       FBP.setSourcePos.invokeExact(this, new BlockPos(xCoordIn, yCoordIn, zCoordIn));
/*  44 */     } catch (Throwable e1) {
/*     */       
/*  46 */       e1.printStackTrace();
/*     */     } 
/*     */     
/*  49 */     this.rot = new FBPVector3d();
/*  50 */     this.prevRot = new FBPVector3d();
/*     */     
/*  52 */     createRotationMatrix();
/*     */     
/*  54 */     this.motionX = xSpeedIn;
/*  55 */     this.motionY = -ySpeedIn;
/*  56 */     this.motionZ = zSpeedIn;
/*  57 */     this.particleGravity = 1.0F;
/*  58 */     this.sourceState = state;
/*     */     
/*  60 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  62 */     this.particleScale = (float)(this.particleScale * FBP.random.nextDouble(FBP.scaleMult - 0.25D, FBP.scaleMult + 0.25D));
/*  63 */     this.particleMaxAge = (int)FBP.random.nextDouble(250.0D, 300.0D);
/*  64 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/*     */     
/*  66 */     this.scaleAlpha = this.particleScale * 0.75D;
/*     */     
/*  68 */     this.particleAlpha = 0.0F;
/*  69 */     this.particleScale = 0.0F;
/*     */     
/*  71 */     this.canCollide = true;
/*     */     
/*  73 */     if (FBP.randomFadingSpeed) {
/*  74 */       this.endMult *= FBP.random.nextDouble(0.7D, 1.0D);
/*     */     }
/*  76 */     multipleParticleScaleBy(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createRotationMatrix() {
/*  81 */     double rx = FBP.random.nextDouble();
/*  82 */     double ry = FBP.random.nextDouble();
/*  83 */     double rz = FBP.random.nextDouble();
/*     */     
/*  85 */     this.rotStep = new FBPVector3d(((rx > 0.5D) ? true : -1), ((ry > 0.5D) ? true : -1), ((rz > 0.5D) ? true : -1));
/*     */     
/*  87 */     this.rot.copyFrom((Vector3d)this.rotStep);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParticleTextureIndex(int particleTextureIndex) {
/*  93 */     if (!FBP.isEnabled()) {
/*  94 */       super.setParticleTextureIndex(particleTextureIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Particle multipleParticleScaleBy(float scale) {
/* 101 */     Particle p = super.multipleParticleScaleBy(scale);
/* 102 */     if (FBP.isEnabled()) {
/* 103 */       return p;
/*     */     }
/*     */     
/* 106 */     float f = this.particleScale / 10.0F;
/*     */     
/* 108 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - f, this.posY, this.posZ - f, this.posX + f, this.posY + (2.0F * f), this.posZ + f));
/*     */     
/* 110 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle MultiplyVelocity(float multiplier) {
/* 115 */     this.motionX *= multiplier;
/* 116 */     this.motionY = (this.motionY - 0.10000000149011612D) * (multiplier / 2.0F) + 0.10000000149011612D;
/* 117 */     this.motionZ *= multiplier;
/* 118 */     return (Particle)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void multiplyColor(@Nullable BlockPos p_187154_1_) {
/* 124 */     if (!FBP.isEnabled()) {
/* 125 */       super.multiplyColor(p_187154_1_);
/*     */       return;
/*     */     } 
/* 128 */     int i = this.mc.getBlockColors().colorMultiplier(this.sourceState, (IBlockAccess)this.worldObj, p_187154_1_, 0);
/* 129 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/* 130 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/* 131 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 137 */     if (!FBP.isEnabled()) return super.getFXLayer(); 
/* 138 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 144 */     if (!FBP.isEnabled()) {
/* 145 */       super.onUpdate();
/*     */       return;
/*     */     } 
/* 148 */     this.prevRot.copyFrom((Vector3d)this.rot);
/*     */     
/* 150 */     this.prevPosX = this.posX;
/* 151 */     this.prevPosY = this.posY;
/* 152 */     this.prevPosZ = this.posZ;
/*     */     
/* 154 */     this.prevParticleAlpha = this.particleAlpha;
/* 155 */     this.prevParticleScale = this.particleScale;
/*     */     
/* 157 */     if (!this.mc.isGamePaused()) {
/*     */       
/* 159 */       this.particleAge++;
/*     */       
/* 161 */       if (this.posY < this.mc.player.posY - (this.mc.gameSettings.renderDistanceChunks * 16)) {
/* 162 */         setExpired();
/*     */       }
/* 164 */       this.rot.add((Vector3d)this.rotStep.multiply(FBP.rotationMult * 5.0D));
/*     */       
/* 166 */       if (this.particleAge >= this.particleMaxAge) {
/*     */         
/* 168 */         if (FBP.randomFadingSpeed) {
/* 169 */           this.particleScale = (float)(this.particleScale * 0.75D * this.endMult);
/*     */         } else {
/* 171 */           this.particleScale *= 0.75F;
/*     */         } 
/* 173 */         if (this.particleAlpha > 0.01D && this.particleScale <= this.scaleAlpha)
/*     */         {
/* 175 */           if (FBP.randomFadingSpeed) {
/* 176 */             this.particleAlpha = (float)(this.particleAlpha * 0.6499999761581421D * this.endMult);
/*     */           } else {
/* 178 */             this.particleAlpha *= 0.65F;
/*     */           } 
/*     */         }
/* 181 */         if (this.particleAlpha <= 0.01D) {
/* 182 */           setExpired();
/*     */         }
/*     */       } else {
/* 185 */         if (this.particleScale < 1.0F) {
/*     */           
/* 187 */           if (FBP.randomFadingSpeed) {
/* 188 */             this.particleScale = (float)(this.particleScale + 0.07500000298023224D * this.endMult);
/*     */           } else {
/* 190 */             this.particleScale += 0.075F;
/*     */           } 
/* 192 */           if (this.particleScale > 1.0F) {
/* 193 */             this.particleScale = 1.0F;
/*     */           }
/*     */         } 
/* 196 */         if (this.particleAlpha < 1.0F) {
/*     */           
/* 198 */           if (FBP.randomFadingSpeed) {
/* 199 */             this.particleAlpha = (float)(this.particleAlpha + 0.04500000178813934D * this.endMult);
/*     */           } else {
/* 201 */             this.particleAlpha += 0.045F;
/*     */           } 
/* 203 */           if (this.particleAlpha > 1.0F) {
/* 204 */             this.particleAlpha = 1.0F;
/*     */           }
/*     */         } 
/*     */       } 
/* 208 */       if (this.worldObj.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getMaterial().isLiquid()) {
/* 209 */         setExpired();
/*     */       }
/* 211 */       this.motionY -= 0.04D * this.particleGravity;
/*     */       
/* 213 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */       
/* 215 */       if (this.onGround && FBP.restOnFloor) {
/*     */         
/* 217 */         this.rot.x = ((float)Math.round(this.rot.x / 90.0D) * 90.0F);
/* 218 */         this.rot.z = ((float)Math.round(this.rot.z / 90.0D) * 90.0F);
/*     */       } 
/*     */       
/* 221 */       this.motionX *= 0.9800000190734863D;
/*     */       
/* 223 */       if (this.motionY < -0.2D) {
/* 224 */         this.motionY *= 0.7500000190734863D;
/*     */       }
/* 226 */       this.motionZ *= 0.9800000190734863D;
/*     */       
/* 228 */       if (this.onGround) {
/*     */         
/* 230 */         this.motionX *= 0.680000190734863D;
/* 231 */         this.motionZ *= 0.6800000190734863D;
/*     */         
/* 233 */         this.rotStep = this.rotStep.multiply(0.85D);
/*     */         
/* 235 */         this.particleAge += 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 243 */     if (!FBP.isEnabled()) {
/* 244 */       super.moveEntity(x, y, z);
/*     */       return;
/*     */     } 
/* 247 */     double X = x;
/* 248 */     double Y = y;
/* 249 */     double Z = z;
/*     */     
/* 251 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().expand(x, y, z));
/*     */     
/* 253 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 255 */       y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */     }
/*     */     
/* 258 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     
/* 260 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 262 */       x = axisalignedbb.calculateXOffset(getEntityBoundingBox(), x);
/*     */     }
/*     */     
/* 265 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */     
/* 267 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 269 */       z = axisalignedbb.calculateZOffset(getEntityBoundingBox(), z);
/*     */     }
/*     */     
/* 272 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */ 
/*     */     
/* 275 */     resetPositionToBB();
/* 276 */     this.onGround = (y != Y && Y < 0.0D);
/*     */     
/* 278 */     if (!FBP.lowTraction && !FBP.bounceOffWalls) {
/*     */       
/* 280 */       if (x != X)
/* 281 */         this.motionX *= 0.699999988079071D; 
/* 282 */       if (z != Z) {
/* 283 */         this.motionZ *= 0.699999988079071D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder buf, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 291 */     if (!FBP.isEnabled()) {
/* 292 */       super.renderParticle(buf, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       return;
/*     */     } 
/* 295 */     if (!FBP.isEnabled() && this.particleMaxAge != 0) {
/* 296 */       this.particleMaxAge = 0;
/*     */     }
/* 298 */     float f = 0.0F, f1 = 0.0F, f2 = 0.0F, f3 = 0.0F;
/*     */     
/* 300 */     if (this.particleTexture != null) {
/*     */       
/* 302 */       if (!FBP.cartoonMode) {
/*     */         
/* 304 */         f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/* 305 */         f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*     */       } 
/*     */       
/* 308 */       f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/* 309 */       f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } else {
/*     */       
/* 312 */       f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 313 */       f1 = f + 0.015609375F;
/* 314 */       f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 315 */       f3 = f2 + 0.015609375F;
/*     */     } 
/*     */     
/* 318 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 319 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 320 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*     */     
/* 322 */     int i = getBrightnessForRender(partialTicks);
/*     */     
/* 324 */     float alpha = (float)(this.prevParticleAlpha + (this.particleAlpha - this.prevParticleAlpha) * partialTicks);
/*     */ 
/*     */     
/* 327 */     float f4 = (float)(this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks);
/*     */     
/* 329 */     if (FBP.restOnFloor) {
/* 330 */       f6 += f4 / 10.0F;
/*     */     }
/* 332 */     FBPVector3d smoothRot = new FBPVector3d(0.0D, 0.0D, 0.0D);
/*     */     
/* 334 */     if (FBP.rotationMult > 0.0D) {
/*     */       
/* 336 */       smoothRot.y = this.rot.y;
/* 337 */       smoothRot.z = this.rot.z;
/*     */       
/* 339 */       if (!FBP.randomRotation) {
/* 340 */         smoothRot.x = this.rot.x;
/*     */       }
/*     */       
/* 343 */       if (!FBP.frozen) {
/*     */         
/* 345 */         FBPVector3d vec = this.rot.partialVec(this.prevRot, partialTicks);
/*     */         
/* 347 */         if (FBP.randomRotation) {
/*     */           
/* 349 */           smoothRot.y = vec.y;
/* 350 */           smoothRot.z = vec.z;
/*     */         } else {
/*     */           
/* 353 */           smoothRot.x = vec.x;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 359 */     GlStateManager.enableCull();
/*     */     
/* 361 */     this.par = new Vec2f[] { new Vec2f(f1, f3), new Vec2f(f1, f2), new Vec2f(f, f2), new Vec2f(f, f3) };
/*     */     
/* 363 */     FBPRenderUtil.renderCubeShaded_S(buf, this.par, f5, f6, f7, (f4 / 10.0F), smoothRot, i >> 16 & 0xFFFF, i & 0xFFFF, 
/* 364 */         this.particleRed, this.particleGreen, this.particleBlue, alpha, FBP.cartoonMode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 370 */     if (!FBP.isEnabled()) return super.getBrightnessForRender(p_189214_1_); 
/* 371 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 372 */     int j = 0;
/*     */     
/* 374 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ)))
/*     */     {
/* 376 */       j = this.worldObj.getCombinedLight(new BlockPos(this.posX, this.posY, this.posZ), 0);
/*     */     }
/*     */     
/* 379 */     return (i == 0) ? j : i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
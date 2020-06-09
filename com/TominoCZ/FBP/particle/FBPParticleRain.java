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
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FBPParticleRain
/*     */   extends ParticleDigging {
/*     */   private final IBlockState sourceState;
/*     */   Minecraft mc;
/*     */   double AngleY;
/*     */   double particleHeight;
/*     */   double prevParticleScale;
/*     */   double prevParticleHeight;
/*     */   double prevParticleAlpha;
/*  31 */   double scalar = FBP.scaleMult;
/*  32 */   double endMult = 1.0D;
/*     */ 
/*     */   
/*     */   Vec2f[] par;
/*     */ 
/*     */   
/*     */   public FBPParticleRain(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
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
/*  49 */     this.AngleY = FBP.random.nextDouble() * 45.0D;
/*     */     
/*  51 */     this.motionX = xSpeedIn;
/*  52 */     this.motionY = -ySpeedIn;
/*  53 */     this.motionZ = zSpeedIn;
/*     */     
/*  55 */     this.particleGravity = 0.025F;
/*     */     
/*  57 */     this.sourceState = state;
/*     */     
/*  59 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  61 */     this.particleMaxAge = (int)FBP.random.nextDouble(50.0D, 70.0D);
/*     */     
/*  63 */     this.particleAlpha = 0.0F;
/*  64 */     this.particleScale = 0.0F;
/*     */     
/*  66 */     this.canCollide = true;
/*     */     
/*  68 */     if (FBP.randomFadingSpeed) {
/*  69 */       this.endMult *= FBP.random.nextDouble(0.85D, 1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParticleTextureIndex(int particleTextureIndex) {
/*  75 */     if (!FBP.isEnabled()) {
/*  76 */       super.setParticleTextureIndex(particleTextureIndex);
/*     */     }
/*     */   }
/*     */   
/*     */   public Particle MultiplyVelocity(float multiplier) {
/*  81 */     this.motionX *= multiplier;
/*  82 */     this.motionY = (this.motionY - 0.10000000149011612D) * (multiplier / 2.0F) + 0.10000000149011612D;
/*  83 */     this.motionZ *= multiplier;
/*  84 */     return (Particle)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void multiplyColor(@Nullable BlockPos p_187154_1_) {
/*  90 */     if (!FBP.isEnabled()) {
/*  91 */       super.multiplyColor(p_187154_1_);
/*     */       return;
/*     */     } 
/*  94 */     int i = this.mc.getBlockColors().colorMultiplier(this.sourceState, (IBlockAccess)this.worldObj, p_187154_1_, 0);
/*  95 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  96 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  97 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 103 */     if (!FBP.isEnabled()) {
/* 104 */       return super.getFXLayer();
/*     */     }
/* 106 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 112 */     if (!FBP.isEnabled()) {
/* 113 */       super.onUpdate();
/*     */       return;
/*     */     } 
/* 116 */     this.prevPosX = this.posX;
/* 117 */     this.prevPosY = this.posY;
/* 118 */     this.prevPosZ = this.posZ;
/*     */     
/* 120 */     this.prevParticleAlpha = this.particleAlpha;
/* 121 */     this.prevParticleScale = this.particleScale;
/* 122 */     this.prevParticleHeight = this.particleHeight;
/*     */     
/* 124 */     if (!this.mc.isGamePaused()) {
/*     */       
/* 126 */       this.particleAge++;
/*     */       
/* 128 */       if (this.posY < this.mc.player.posY - (this.mc.gameSettings.renderDistanceChunks * 9)) {
/* 129 */         setExpired();
/*     */       }
/* 131 */       if (!this.onGround)
/*     */       {
/* 133 */         if (this.particleAge < this.particleMaxAge) {
/*     */           
/* 135 */           double max = this.scalar * 0.5D;
/*     */           
/* 137 */           if (this.particleScale < max) {
/*     */             
/* 139 */             if (FBP.randomFadingSpeed) {
/* 140 */               this.particleScale = (float)(this.particleScale + 0.05000000074505806D * this.endMult);
/*     */             } else {
/* 142 */               this.particleScale += 0.05F;
/*     */             } 
/* 144 */             if (this.particleScale > max) {
/* 145 */               this.particleScale = (float)max;
/*     */             }
/* 147 */             this.particleHeight = this.particleScale;
/*     */           } 
/*     */           
/* 150 */           if (this.particleAlpha < 0.65F) {
/*     */             
/* 152 */             if (FBP.randomFadingSpeed) {
/* 153 */               this.particleAlpha = (float)(this.particleAlpha + 0.08500000089406967D * this.endMult);
/*     */             } else {
/* 155 */               this.particleAlpha += 0.085F;
/*     */             } 
/* 157 */             if (this.particleAlpha > 0.65F)
/* 158 */               this.particleAlpha = 0.65F; 
/*     */           } 
/*     */         } else {
/* 161 */           setExpired();
/*     */         } 
/*     */       }
/* 164 */       if (this.worldObj.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getMaterial().isLiquid()) {
/* 165 */         setExpired();
/*     */       }
/* 167 */       this.motionY -= 0.04D * this.particleGravity;
/*     */       
/* 169 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */       
/* 171 */       this.motionY *= 1.0002500019073486D;
/*     */       
/* 173 */       if (this.onGround) {
/*     */         
/* 175 */         this.motionX = 0.0D;
/* 176 */         this.motionY = -0.25D;
/* 177 */         this.motionZ = 0.0D;
/*     */         
/* 179 */         if (this.particleHeight > 0.07500000298023224D) {
/* 180 */           this.particleHeight *= 0.7250000238418579D;
/*     */         }
/* 182 */         float max = (float)this.scalar * 4.25F;
/*     */         
/* 184 */         if (this.particleScale < max) {
/*     */           
/* 186 */           this.particleScale += max / 10.0F;
/*     */           
/* 188 */           if (this.particleScale > max) {
/* 189 */             this.particleScale = max;
/*     */           }
/*     */         } 
/* 192 */         if (this.particleScale >= max / 2.0F) {
/*     */           
/* 194 */           if (FBP.randomFadingSpeed) {
/* 195 */             this.particleAlpha = (float)(this.particleAlpha * 0.75D * this.endMult);
/*     */           } else {
/* 197 */             this.particleAlpha *= 0.75F;
/*     */           } 
/* 199 */           if (this.particleAlpha <= 0.001F) {
/* 200 */             setExpired();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 205 */     Vec3d rgb = this.mc.world.getSkyColor((Entity)this.mc.player, 0.0F);
/*     */     
/* 207 */     this.particleRed = (float)rgb.xCoord;
/* 208 */     this.particleGreen = (float)MathHelper.clamp(rgb.yCoord + 0.25D, 0.25D, 1.0D);
/* 209 */     this.particleBlue = (float)MathHelper.clamp(rgb.zCoord + 0.5D, 0.5D, 1.0D);
/*     */     
/* 211 */     if (this.particleGreen > 1.0F)
/* 212 */       this.particleGreen = 1.0F; 
/* 213 */     if (this.particleBlue > 1.0F) {
/* 214 */       this.particleBlue = 1.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 220 */     if (!FBP.isEnabled()) {
/* 221 */       super.moveEntity(x, y, z);
/*     */       return;
/*     */     } 
/* 224 */     double X = x;
/* 225 */     double Y = y;
/* 226 */     double Z = z;
/*     */     
/* 228 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().expand(x, y, z));
/*     */     
/* 230 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 232 */       y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */     }
/*     */     
/* 235 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     
/* 237 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 239 */       x = axisalignedbb.calculateXOffset(getEntityBoundingBox(), x);
/*     */     }
/*     */     
/* 242 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */     
/* 244 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 246 */       z = axisalignedbb.calculateZOffset(getEntityBoundingBox(), z);
/*     */     }
/*     */     
/* 249 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */     
/* 251 */     resetPositionToBB();
/*     */     
/* 253 */     this.onGround = (y != Y && Y < 0.0D);
/*     */     
/* 255 */     if (x != X)
/* 256 */       this.motionX *= 0.699999988079071D; 
/* 257 */     if (z != Z) {
/* 258 */       this.motionZ *= 0.699999988079071D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder buf, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 265 */     if (!FBP.isEnabled()) {
/* 266 */       super.renderParticle(buf, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       return;
/*     */     } 
/* 269 */     if (!FBP.isEnabled() && this.particleMaxAge != 0) {
/* 270 */       this.particleMaxAge = 0;
/*     */     }
/* 272 */     float f = 0.0F, f1 = 0.0F, f2 = 0.0F, f3 = 0.0F;
/*     */     
/* 274 */     if (this.particleTexture != null) {
/*     */       
/* 276 */       if (!FBP.cartoonMode) {
/*     */         
/* 278 */         f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/* 279 */         f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*     */       } 
/*     */       
/* 282 */       f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/* 283 */       f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } else {
/*     */       
/* 286 */       f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 287 */       f1 = f + 0.015609375F;
/* 288 */       f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 289 */       f3 = f2 + 0.015609375F;
/*     */     } 
/*     */     
/* 292 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 293 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 294 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*     */     
/* 296 */     int i = getBrightnessForRender(partialTicks);
/*     */     
/* 298 */     float alpha = (float)(this.prevParticleAlpha + (this.particleAlpha - this.prevParticleAlpha) * partialTicks);
/*     */ 
/*     */     
/* 301 */     float f4 = (float)(this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks);
/* 302 */     float height = (float)(this.prevParticleHeight + (this.particleHeight - this.prevParticleHeight) * partialTicks);
/*     */ 
/*     */     
/* 305 */     this.par = new Vec2f[] { new Vec2f(f1, f3), new Vec2f(f1, f2), new Vec2f(f, f2), new Vec2f(f, f3) };
/*     */     
/* 307 */     FBPRenderUtil.renderCubeShaded_WH(buf, this.par, f5, f6 + height / 10.0F, f7, (f4 / 10.0F), (height / 10.0F), 
/* 308 */         new FBPVector3d(0.0D, this.AngleY, 0.0D), i >> 16 & 0xFFFF, i & 0xFFFF, this.particleRed, this.particleGreen, this.particleBlue, 
/* 309 */         alpha, FBP.cartoonMode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 315 */     if (!FBP.isEnabled()) {
/* 316 */       return super.getBrightnessForRender(p_189214_1_);
/*     */     }
/* 318 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 319 */     int j = 0;
/*     */     
/* 321 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ)))
/*     */     {
/* 323 */       j = this.worldObj.getCombinedLight(new BlockPos(this.posX, this.posY, this.posZ), 0);
/*     */     }
/*     */     
/* 326 */     return (i == 0) ? j : i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleRain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
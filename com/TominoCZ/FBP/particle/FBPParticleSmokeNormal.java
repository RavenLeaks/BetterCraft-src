/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPRenderUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleSmokeNormal;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FBPParticleSmokeNormal
/*     */   extends ParticleSmokeNormal {
/*     */   Minecraft mc;
/*     */   double startScale;
/*     */   double scaleAlpha;
/*     */   double prevParticleScale;
/*     */   double prevParticleAlpha;
/*  28 */   double endMult = 0.75D;
/*     */ 
/*     */   
/*     */   Vec3d[] cube;
/*     */ 
/*     */   
/*     */   Vec2f par;
/*     */   
/*     */   ParticleSmokeNormal original;
/*     */ 
/*     */   
/*     */   protected FBPParticleSmokeNormal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double mX, double mY, double mZ, float scale, boolean b, TextureAtlasSprite tex, ParticleSmokeNormal original) {
/*  40 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, mX, mY, mZ, scale);
/*     */     
/*  42 */     this.original = original;
/*     */     
/*  44 */     this.motionX = mX;
/*  45 */     this.motionY = mY;
/*  46 */     this.motionZ = mZ;
/*     */     
/*  48 */     this.mc = Minecraft.getMinecraft();
/*  49 */     this.particleTexture = tex;
/*     */     
/*  51 */     this.scaleAlpha = this.particleScale * 0.85D;
/*     */     
/*  53 */     Block block = worldIn.getBlockState(new BlockPos(xCoordIn, yCoordIn, zCoordIn)).getBlock();
/*     */     
/*  55 */     if (block == Blocks.FIRE) {
/*     */       
/*  57 */       this.particleScale *= 0.65F;
/*  58 */       this.particleGravity *= 0.25F;
/*     */       
/*  60 */       this.motionX = FBP.random.nextDouble(-0.05D, 0.05D);
/*  61 */       this.motionY = FBP.random.nextDouble() * 0.5D;
/*  62 */       this.motionZ = FBP.random.nextDouble(-0.05D, 0.05D);
/*     */       
/*  64 */       this.motionY *= 0.3499999940395355D;
/*     */       
/*  66 */       this.scaleAlpha = this.particleScale * 0.5D;
/*     */       
/*  68 */       this.particleMaxAge = FBP.random.nextInt(7, 18);
/*  69 */     } else if (block == Blocks.TORCH) {
/*     */       
/*  71 */       this.particleScale *= 0.45F;
/*     */       
/*  73 */       this.motionX = FBP.random.nextDouble(-0.05D, 0.05D);
/*  74 */       this.motionY = FBP.random.nextDouble() * 0.5D;
/*  75 */       this.motionZ = FBP.random.nextDouble(-0.05D, 0.05D);
/*     */       
/*  77 */       this.motionX *= 0.925000011920929D;
/*  78 */       this.motionY = 0.004999999888241291D;
/*  79 */       this.motionZ *= 0.925000011920929D;
/*     */       
/*  81 */       this.particleRed = 0.275F;
/*  82 */       this.particleGreen = 0.275F;
/*  83 */       this.particleBlue = 0.275F;
/*     */       
/*  85 */       this.scaleAlpha = this.particleScale * 0.75D;
/*     */       
/*  87 */       this.particleMaxAge = FBP.random.nextInt(5, 10);
/*     */     } else {
/*     */       
/*  90 */       this.particleScale = scale;
/*  91 */       this.motionY *= 0.935D;
/*     */     } 
/*     */     
/*  94 */     this.particleScale = (float)(this.particleScale * FBP.scaleMult);
/*     */     
/*  96 */     this.startScale = this.particleScale;
/*     */     
/*  98 */     float angleY = this.rand.nextFloat() * 80.0F;
/*     */     
/* 100 */     this.cube = new Vec3d[FBP.CUBE.length];
/*     */     
/* 102 */     for (int i = 0; i < FBP.CUBE.length; i++) {
/*     */       
/* 104 */       Vec3d vec = FBP.CUBE[i];
/* 105 */       this.cube[i] = FBPRenderUtil.rotatef_d(vec, 0.0F, angleY, 0.0F);
/*     */     } 
/*     */     
/* 108 */     this.particleAlpha = 1.0F;
/*     */     
/* 110 */     if (FBP.randomFadingSpeed) {
/* 111 */       this.endMult = MathHelper.clamp(FBP.random.nextDouble(0.425D, 1.15D), 0.5432D, 1.0D);
/*     */     }
/* 113 */     multipleParticleScaleBy(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Particle multipleParticleScaleBy(float scale) {
/* 119 */     Particle p = super.multipleParticleScaleBy(scale);
/* 120 */     if (!FBP.isEnabled()) {
/* 121 */       return p;
/*     */     }
/*     */     
/* 124 */     float f = this.particleScale / 20.0F;
/*     */     
/* 126 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - f, this.posY - f, this.posZ - f, this.posX + f, this.posY + f, this.posZ + f));
/*     */     
/* 128 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 134 */     if (!FBP.isEnabled()) {
/* 135 */       return super.getFXLayer();
/*     */     }
/* 137 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 143 */     if (!FBP.isEnabled()) {
/* 144 */       super.onUpdate();
/*     */       return;
/*     */     } 
/* 147 */     this.prevPosX = this.posX;
/* 148 */     this.prevPosY = this.posY;
/* 149 */     this.prevPosZ = this.posZ;
/*     */     
/* 151 */     this.prevParticleAlpha = this.particleAlpha;
/* 152 */     this.prevParticleScale = this.particleScale;
/*     */     
/* 154 */     if (!FBP.fancySmoke) {
/* 155 */       this.isExpired = true;
/*     */     }
/* 157 */     if (++this.particleAge >= this.particleMaxAge) {
/*     */       
/* 159 */       if (FBP.randomFadingSpeed) {
/* 160 */         this.particleScale = (float)(this.particleScale * 0.8876543045043945D * this.endMult);
/*     */       } else {
/* 162 */         this.particleScale *= 0.8876543F;
/*     */       } 
/* 164 */       if (this.particleAlpha > 0.01D && this.particleScale <= this.scaleAlpha)
/*     */       {
/* 166 */         if (FBP.randomFadingSpeed) {
/* 167 */           this.particleAlpha = (float)(this.particleAlpha * 0.7654321193695068D * this.endMult);
/*     */         } else {
/* 169 */           this.particleAlpha *= 0.7654321F;
/*     */         } 
/*     */       }
/* 172 */       if (this.particleAlpha <= 0.01D) {
/* 173 */         setExpired();
/*     */       }
/*     */     } 
/* 176 */     this.motionY += 0.004D;
/* 177 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 179 */     if (this.posY == this.prevPosY) {
/*     */       
/* 181 */       this.motionX *= 1.1D;
/* 182 */       this.motionZ *= 1.1D;
/*     */     } 
/*     */     
/* 185 */     this.motionX *= 0.9599999785423279D;
/* 186 */     this.motionY *= 0.9599999785423279D;
/* 187 */     this.motionZ *= 0.9599999785423279D;
/*     */     
/* 189 */     if (this.onGround) {
/*     */       
/* 191 */       this.motionX *= 0.899999988079071D;
/* 192 */       this.motionZ *= 0.899999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 199 */     if (!FBP.isEnabled()) {
/* 200 */       super.moveEntity(x, y, z);
/*     */       return;
/*     */     } 
/* 203 */     double X = x;
/* 204 */     double Y = y;
/* 205 */     double Z = z;
/*     */     
/* 207 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().expand(x, y, z));
/*     */     
/* 209 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 211 */       y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */     }
/*     */     
/* 214 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     
/* 216 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 218 */       x = axisalignedbb.calculateXOffset(getEntityBoundingBox(), x);
/*     */     }
/*     */     
/* 221 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */     
/* 223 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 225 */       z = axisalignedbb.calculateZOffset(getEntityBoundingBox(), z);
/*     */     }
/*     */     
/* 228 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */ 
/*     */     
/* 231 */     resetPositionToBB();
/* 232 */     this.onGround = (y != Y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetPositionToBB() {
/* 238 */     if (!FBP.isEnabled()) {
/* 239 */       super.resetPositionToBB();
/*     */       return;
/*     */     } 
/* 242 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 243 */     this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0D;
/* 244 */     this.posY = (axisalignedbb.minY + axisalignedbb.maxY) / 2.0D;
/* 245 */     this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 252 */     if (!FBP.isEnabled()) {
/* 253 */       super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       return;
/*     */     } 
/* 256 */     if (!FBP.isEnabled() && this.particleMaxAge != 0) {
/* 257 */       this.particleMaxAge = 0;
/*     */     }
/* 259 */     float f = this.particleTexture.getInterpolatedU(4.400000095367432D);
/* 260 */     float f1 = this.particleTexture.getInterpolatedV(4.400000095367432D);
/*     */     
/* 262 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 263 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 264 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*     */     
/* 266 */     int i = getBrightnessForRender(partialTicks);
/*     */     
/* 268 */     float alpha = (float)(this.prevParticleAlpha + (this.particleAlpha - this.prevParticleAlpha) * partialTicks);
/*     */ 
/*     */     
/* 271 */     float f4 = (float)(this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks);
/*     */ 
/*     */     
/* 274 */     this.par = new Vec2f(f, f1);
/*     */     
/* 276 */     worldRendererIn.setTranslation(f5, f6, f7);
/* 277 */     putCube(worldRendererIn, (f4 / 20.0F), i >> 16 & 0xFFFF, i & 0xFFFF, this.particleRed, this.particleGreen, this.particleBlue, alpha);
/* 278 */     worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putCube(BufferBuilder worldRendererIn, double scale, int j, int k, float r, float g, float b, float a) {
/* 283 */     float brightnessForRender = 1.0F;
/*     */     
/* 285 */     float R = 0.0F;
/* 286 */     float G = 0.0F;
/* 287 */     float B = 0.0F;
/*     */     
/* 289 */     for (int i = 0; i < this.cube.length; i += 4) {
/*     */       
/* 291 */       Vec3d v1 = this.cube[i];
/* 292 */       Vec3d v2 = this.cube[i + 1];
/* 293 */       Vec3d v3 = this.cube[i + 2];
/* 294 */       Vec3d v4 = this.cube[i + 3];
/*     */       
/* 296 */       R = r * brightnessForRender;
/* 297 */       G = g * brightnessForRender;
/* 298 */       B = b * brightnessForRender;
/*     */       
/* 300 */       brightnessForRender = (float)(brightnessForRender * 0.875D);
/*     */       
/* 302 */       addVt(worldRendererIn, scale, v1, this.par.x, this.par.y, j, k, R, G, B, a);
/* 303 */       addVt(worldRendererIn, scale, v2, this.par.x, this.par.y, j, k, R, G, B, a);
/* 304 */       addVt(worldRendererIn, scale, v3, this.par.x, this.par.y, j, k, R, G, B, a);
/* 305 */       addVt(worldRendererIn, scale, v4, this.par.x, this.par.y, j, k, R, G, B, a);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addVt(BufferBuilder worldRendererIn, double scale, Vec3d pos, double u, double v, int j, int k, float r, float g, float b, float a) {
/* 312 */     worldRendererIn.pos(pos.xCoord * scale, pos.yCoord * scale, pos.zCoord * scale).tex(u, v).color(r, g, b, a).lightmap(j, k)
/* 313 */       .endVertex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 319 */     if (!FBP.isEnabled()) return super.getBrightnessForRender(p_189214_1_); 
/* 320 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 321 */     int j = 0;
/*     */     
/* 323 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ)))
/*     */     {
/* 325 */       j = this.worldObj.getCombinedLight(new BlockPos(this.posX, this.posY, this.posZ), 0);
/*     */     }
/*     */     
/* 328 */     return (i == 0) ? j : i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpired() {
/* 334 */     if (!FBP.isEnabled()) {
/* 335 */       super.setExpired();
/*     */       return;
/*     */     } 
/* 338 */     this.isExpired = true;
/*     */     
/* 340 */     this.original.setExpired();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleSmokeNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
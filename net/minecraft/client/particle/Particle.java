/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Particle
/*     */ {
/*  16 */   private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*     */   protected World worldObj;
/*     */   
/*     */   protected double prevPosX;
/*     */   
/*     */   protected double prevPosY;
/*     */   
/*     */   protected double prevPosZ;
/*     */   
/*     */   protected double posX;
/*     */   
/*     */   protected double posY;
/*     */   
/*     */   protected double posZ;
/*     */   
/*     */   protected double motionX;
/*     */   
/*     */   protected double motionY;
/*     */   
/*     */   protected double motionZ;
/*     */   
/*     */   private AxisAlignedBB boundingBox;
/*     */   
/*     */   protected boolean isCollided;
/*     */   
/*     */   protected boolean canCollide;
/*     */   
/*     */   protected boolean isExpired;
/*     */   
/*     */   protected float width;
/*     */   
/*     */   public boolean onGround;
/*     */   
/*     */   protected float height;
/*     */   
/*     */   protected Random rand;
/*     */   
/*     */   protected int particleTextureIndexX;
/*     */   
/*     */   protected int particleTextureIndexY;
/*     */   
/*     */   protected float particleTextureJitterX;
/*     */   
/*     */   protected float particleTextureJitterY;
/*     */   
/*     */   protected int particleAge;
/*     */   
/*     */   protected int particleMaxAge;
/*     */   
/*     */   protected float particleScale;
/*     */   protected float particleGravity;
/*     */   protected float particleRed;
/*     */   protected float particleGreen;
/*     */   protected float particleBlue;
/*     */   protected float particleAlpha;
/*     */   protected TextureAtlasSprite particleTexture;
/*     */   protected float particleAngle;
/*     */   protected float prevParticleAngle;
/*     */   public static double interpPosX;
/*     */   public static double interpPosY;
/*     */   public static double interpPosZ;
/*     */   public static Vec3d cameraViewDir;
/*     */   
/*     */   protected Particle(World worldIn, double posXIn, double posYIn, double posZIn) {
/*  81 */     this.boundingBox = EMPTY_AABB;
/*  82 */     this.width = 0.6F;
/*  83 */     this.height = 1.8F;
/*  84 */     this.rand = new Random();
/*  85 */     this.particleAlpha = 1.0F;
/*  86 */     this.worldObj = worldIn;
/*  87 */     setSize(0.2F, 0.2F);
/*  88 */     setPosition(posXIn, posYIn, posZIn);
/*  89 */     this.prevPosX = posXIn;
/*  90 */     this.prevPosY = posYIn;
/*  91 */     this.prevPosZ = posZIn;
/*  92 */     this.particleRed = 1.0F;
/*  93 */     this.particleGreen = 1.0F;
/*  94 */     this.particleBlue = 1.0F;
/*  95 */     this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
/*  96 */     this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
/*  97 */     this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
/*  98 */     this.particleMaxAge = (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
/*  99 */     this.particleAge = 0;
/* 100 */     this.canCollide = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 105 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn);
/* 106 */     this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/* 107 */     this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/* 108 */     this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/* 109 */     float f = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
/* 110 */     float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 111 */     this.motionX = this.motionX / f1 * f * 0.4000000059604645D;
/* 112 */     this.motionY = this.motionY / f1 * f * 0.4000000059604645D + 0.10000000149011612D;
/* 113 */     this.motionZ = this.motionZ / f1 * f * 0.4000000059604645D;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle multiplyVelocity(float multiplier) {
/* 118 */     this.motionX *= multiplier;
/* 119 */     this.motionY = (this.motionY - 0.10000000149011612D) * multiplier + 0.10000000149011612D;
/* 120 */     this.motionZ *= multiplier;
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle multipleParticleScaleBy(float scale) {
/* 126 */     setSize(0.2F * scale, 0.2F * scale);
/* 127 */     this.particleScale *= scale;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn) {
/* 133 */     this.particleRed = particleRedIn;
/* 134 */     this.particleGreen = particleGreenIn;
/* 135 */     this.particleBlue = particleBlueIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlphaF(float alpha) {
/* 143 */     this.particleAlpha = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTransparent() {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRedColorF() {
/* 153 */     return this.particleRed;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getGreenColorF() {
/* 158 */     return this.particleGreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlueColorF() {
/* 163 */     return this.particleBlue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxAge(int p_187114_1_) {
/* 168 */     this.particleMaxAge = p_187114_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 173 */     this.prevPosX = this.posX;
/* 174 */     this.prevPosY = this.posY;
/* 175 */     this.prevPosZ = this.posZ;
/*     */     
/* 177 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 179 */       setExpired();
/*     */     }
/*     */     
/* 182 */     this.motionY -= 0.04D * this.particleGravity;
/* 183 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 184 */     this.motionX *= 0.9800000190734863D;
/* 185 */     this.motionY *= 0.9800000190734863D;
/* 186 */     this.motionZ *= 0.9800000190734863D;
/*     */     
/* 188 */     if (this.isCollided) {
/*     */       
/* 190 */       this.motionX *= 0.699999988079071D;
/* 191 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 200 */     float f = this.particleTextureIndexX / 16.0F;
/* 201 */     float f1 = f + 0.0624375F;
/* 202 */     float f2 = this.particleTextureIndexY / 16.0F;
/* 203 */     float f3 = f2 + 0.0624375F;
/* 204 */     float f4 = 0.1F * this.particleScale;
/*     */     
/* 206 */     if (this.particleTexture != null) {
/*     */       
/* 208 */       f = this.particleTexture.getMinU();
/* 209 */       f1 = this.particleTexture.getMaxU();
/* 210 */       f2 = this.particleTexture.getMinV();
/* 211 */       f3 = this.particleTexture.getMaxV();
/*     */     } 
/*     */     
/* 214 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 215 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 216 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 217 */     int i = getBrightnessForRender(partialTicks);
/* 218 */     int j = i >> 16 & 0xFFFF;
/* 219 */     int k = i & 0xFFFF;
/* 220 */     Vec3d[] avec3d = { new Vec3d((-rotationX * f4 - rotationXY * f4), (-rotationZ * f4), (-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((-rotationX * f4 + rotationXY * f4), (rotationZ * f4), (-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((rotationX * f4 + rotationXY * f4), (rotationZ * f4), (rotationYZ * f4 + rotationXZ * f4)), new Vec3d((rotationX * f4 - rotationXY * f4), (-rotationZ * f4), (rotationYZ * f4 - rotationXZ * f4)) };
/*     */     
/* 222 */     if (this.particleAngle != 0.0F) {
/*     */       
/* 224 */       float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
/* 225 */       float f9 = MathHelper.cos(f8 * 0.5F);
/* 226 */       float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.xCoord;
/* 227 */       float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.yCoord;
/* 228 */       float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.zCoord;
/* 229 */       Vec3d vec3d = new Vec3d(f10, f11, f12);
/*     */       
/* 231 */       for (int l = 0; l < 4; l++)
/*     */       {
/* 233 */         avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((2.0F * f9)));
/*     */       }
/*     */     } 
/*     */     
/* 237 */     worldRendererIn.pos(f5 + (avec3d[0]).xCoord, f6 + (avec3d[0]).yCoord, f7 + (avec3d[0]).zCoord).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/* 238 */     worldRendererIn.pos(f5 + (avec3d[1]).xCoord, f6 + (avec3d[1]).yCoord, f7 + (avec3d[1]).zCoord).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/* 239 */     worldRendererIn.pos(f5 + (avec3d[2]).xCoord, f6 + (avec3d[2]).yCoord, f7 + (avec3d[2]).zCoord).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/* 240 */     worldRendererIn.pos(f5 + (avec3d[3]).xCoord, f6 + (avec3d[3]).yCoord, f7 + (avec3d[3]).zCoord).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 249 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParticleTexture(TextureAtlasSprite texture) {
/* 257 */     int i = getFXLayer();
/*     */     
/* 259 */     if (i == 1) {
/*     */       
/* 261 */       this.particleTexture = texture;
/*     */     }
/*     */     else {
/*     */       
/* 265 */       throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParticleTextureIndex(int particleTextureIndex) {
/* 274 */     if (getFXLayer() != 0)
/*     */     {
/* 276 */       throw new RuntimeException("Invalid call to Particle.setMiscTex");
/*     */     }
/*     */ 
/*     */     
/* 280 */     this.particleTextureIndexX = particleTextureIndex % 16;
/* 281 */     this.particleTextureIndexY = particleTextureIndex / 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextTextureIndexX() {
/* 287 */     this.particleTextureIndexX++;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 292 */     return String.valueOf(getClass().getSimpleName()) + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpired() {
/* 300 */     this.isExpired = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSize(float p_187115_1_, float p_187115_2_) {
/* 305 */     if (p_187115_1_ != this.width || p_187115_2_ != this.height) {
/*     */       
/* 307 */       this.width = p_187115_1_;
/* 308 */       this.height = p_187115_2_;
/* 309 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 310 */       setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + this.width, axisalignedbb.minY + this.height, axisalignedbb.minZ + this.width));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(double p_187109_1_, double p_187109_3_, double p_187109_5_) {
/* 316 */     this.posX = p_187109_1_;
/* 317 */     this.posY = p_187109_3_;
/* 318 */     this.posZ = p_187109_5_;
/* 319 */     float f = this.width / 2.0F;
/* 320 */     float f1 = this.height;
/* 321 */     setEntityBoundingBox(new AxisAlignedBB(p_187109_1_ - f, p_187109_3_, p_187109_5_ - f, p_187109_1_ + f, p_187109_3_ + f1, p_187109_5_ + f));
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 326 */     double d0 = y;
/*     */     
/* 328 */     if (this.canCollide) {
/*     */       
/* 330 */       List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().addCoord(x, y, z));
/*     */       
/* 332 */       for (AxisAlignedBB axisalignedbb : list)
/*     */       {
/* 334 */         y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */       }
/*     */       
/* 337 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */       
/* 339 */       for (AxisAlignedBB axisalignedbb1 : list)
/*     */       {
/* 341 */         x = axisalignedbb1.calculateXOffset(getEntityBoundingBox(), x);
/*     */       }
/*     */       
/* 344 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */       
/* 346 */       for (AxisAlignedBB axisalignedbb2 : list)
/*     */       {
/* 348 */         z = axisalignedbb2.calculateZOffset(getEntityBoundingBox(), z);
/*     */       }
/*     */       
/* 351 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */     }
/*     */     else {
/*     */       
/* 355 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/*     */     } 
/*     */     
/* 358 */     resetPositionToBB();
/* 359 */     this.isCollided = (y != y && d0 < 0.0D);
/*     */     
/* 361 */     if (x != x)
/*     */     {
/* 363 */       this.motionX = 0.0D;
/*     */     }
/*     */     
/* 366 */     if (z != z)
/*     */     {
/* 368 */       this.motionZ = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resetPositionToBB() {
/* 374 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 375 */     this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0D;
/* 376 */     this.posY = axisalignedbb.minY;
/* 377 */     this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 382 */     BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/* 383 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlive() {
/* 391 */     return !this.isExpired;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getEntityBoundingBox() {
/* 396 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 401 */     this.boundingBox = bb;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
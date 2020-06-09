/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleFirework
/*     */ {
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  22 */       ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, (Minecraft.getMinecraft()).effectRenderer);
/*  23 */       particlefirework$spark.setAlphaF(0.99F);
/*  24 */       return particlefirework$spark;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Overlay
/*     */     extends Particle
/*     */   {
/*     */     protected Overlay(World p_i46466_1_, double p_i46466_2_, double p_i46466_4_, double p_i46466_6_) {
/*  32 */       super(p_i46466_1_, p_i46466_2_, p_i46466_4_, p_i46466_6_);
/*  33 */       this.particleMaxAge = 4;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  38 */       float f = 0.25F;
/*  39 */       float f1 = 0.5F;
/*  40 */       float f2 = 0.125F;
/*  41 */       float f3 = 0.375F;
/*  42 */       float f4 = 7.1F * MathHelper.sin((this.particleAge + partialTicks - 1.0F) * 0.25F * 3.1415927F);
/*  43 */       setAlphaF(0.6F - (this.particleAge + partialTicks - 1.0F) * 0.25F * 0.5F);
/*  44 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  45 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  46 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  47 */       int i = getBrightnessForRender(partialTicks);
/*  48 */       int j = i >> 16 & 0xFFFF;
/*  49 */       int k = i & 0xFFFF;
/*  50 */       worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(0.5D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  51 */       worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(0.5D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  52 */       worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(0.25D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  53 */       worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(0.25D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Spark
/*     */     extends ParticleSimpleAnimated
/*     */   {
/*     */     private boolean trail;
/*     */     private boolean twinkle;
/*     */     private final ParticleManager effectRenderer;
/*     */     private float fadeColourRed;
/*     */     private float fadeColourGreen;
/*     */     private float fadeColourBlue;
/*     */     private boolean hasFadeColour;
/*     */     
/*     */     public Spark(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, ParticleManager p_i46465_14_) {
/*  69 */       super(p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_, 160, 8, -0.004F);
/*  70 */       this.motionX = p_i46465_8_;
/*  71 */       this.motionY = p_i46465_10_;
/*  72 */       this.motionZ = p_i46465_12_;
/*  73 */       this.effectRenderer = p_i46465_14_;
/*  74 */       this.particleScale *= 0.75F;
/*  75 */       this.particleMaxAge = 48 + this.rand.nextInt(12);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setTrail(boolean trailIn) {
/*  80 */       this.trail = trailIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setTwinkle(boolean twinkleIn) {
/*  85 */       this.twinkle = twinkleIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTransparent() {
/*  90 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  95 */       if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0)
/*     */       {
/*  97 */         super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdate() {
/* 103 */       super.onUpdate();
/*     */       
/* 105 */       if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
/*     */         
/* 107 */         Spark particlefirework$spark = new Spark(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.effectRenderer);
/* 108 */         particlefirework$spark.setAlphaF(0.99F);
/* 109 */         particlefirework$spark.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
/* 110 */         particlefirework$spark.particleAge = particlefirework$spark.particleMaxAge / 2;
/*     */         
/* 112 */         if (this.hasFadeColour) {
/*     */           
/* 114 */           particlefirework$spark.hasFadeColour = true;
/* 115 */           particlefirework$spark.fadeColourRed = this.fadeColourRed;
/* 116 */           particlefirework$spark.fadeColourGreen = this.fadeColourGreen;
/* 117 */           particlefirework$spark.fadeColourBlue = this.fadeColourBlue;
/*     */         } 
/*     */         
/* 120 */         particlefirework$spark.twinkle = this.twinkle;
/* 121 */         this.effectRenderer.addEffect(particlefirework$spark);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Starter
/*     */     extends Particle
/*     */   {
/*     */     private int fireworkAge;
/*     */     private final ParticleManager theEffectRenderer;
/*     */     private NBTTagList fireworkExplosions;
/*     */     boolean twinkle;
/*     */     
/*     */     public Starter(World p_i46464_1_, double p_i46464_2_, double p_i46464_4_, double p_i46464_6_, double p_i46464_8_, double p_i46464_10_, double p_i46464_12_, ParticleManager p_i46464_14_, @Nullable NBTTagCompound p_i46464_15_) {
/* 135 */       super(p_i46464_1_, p_i46464_2_, p_i46464_4_, p_i46464_6_, 0.0D, 0.0D, 0.0D);
/* 136 */       this.motionX = p_i46464_8_;
/* 137 */       this.motionY = p_i46464_10_;
/* 138 */       this.motionZ = p_i46464_12_;
/* 139 */       this.theEffectRenderer = p_i46464_14_;
/* 140 */       this.particleMaxAge = 8;
/*     */       
/* 142 */       if (p_i46464_15_ != null) {
/*     */         
/* 144 */         this.fireworkExplosions = p_i46464_15_.getTagList("Explosions", 10);
/*     */         
/* 146 */         if (this.fireworkExplosions.hasNoTags()) {
/*     */           
/* 148 */           this.fireworkExplosions = null;
/*     */         }
/*     */         else {
/*     */           
/* 152 */           this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
/*     */           
/* 154 */           for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
/*     */             
/* 156 */             NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
/*     */             
/* 158 */             if (nbttagcompound.getBoolean("Flicker")) {
/*     */               
/* 160 */               this.twinkle = true;
/* 161 */               this.particleMaxAge += 15;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {}
/*     */ 
/*     */     
/*     */     public void onUpdate() {
/* 175 */       if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
/*     */         SoundEvent soundevent1;
/* 177 */         boolean flag = isFarFromCamera();
/* 178 */         boolean flag1 = false;
/*     */         
/* 180 */         if (this.fireworkExplosions.tagCount() >= 3) {
/*     */           
/* 182 */           flag1 = true;
/*     */         }
/*     */         else {
/*     */           
/* 186 */           for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
/*     */             
/* 188 */             NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
/*     */             
/* 190 */             if (nbttagcompound.getByte("Type") == 1) {
/*     */               
/* 192 */               flag1 = true;
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 200 */         if (flag1) {
/*     */           
/* 202 */           soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_LARGE_BLAST;
/*     */         }
/*     */         else {
/*     */           
/* 206 */           soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_BLAST;
/*     */         } 
/*     */         
/* 209 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, soundevent1, SoundCategory.AMBIENT, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
/*     */       } 
/*     */       
/* 212 */       if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
/*     */         
/* 214 */         int k = this.fireworkAge / 2;
/* 215 */         NBTTagCompound nbttagcompound1 = this.fireworkExplosions.getCompoundTagAt(k);
/* 216 */         int l = nbttagcompound1.getByte("Type");
/* 217 */         boolean flag4 = nbttagcompound1.getBoolean("Trail");
/* 218 */         boolean flag2 = nbttagcompound1.getBoolean("Flicker");
/* 219 */         int[] aint = nbttagcompound1.getIntArray("Colors");
/* 220 */         int[] aint1 = nbttagcompound1.getIntArray("FadeColors");
/*     */         
/* 222 */         if (aint.length == 0)
/*     */         {
/* 224 */           aint = new int[] { ItemDye.DYE_COLORS[0] };
/*     */         }
/*     */         
/* 227 */         if (l == 1) {
/*     */           
/* 229 */           createBall(0.5D, 4, aint, aint1, flag4, flag2);
/*     */         }
/* 231 */         else if (l == 2) {
/*     */           
/* 233 */           createShaped(0.5D, new double[][] { { 0.0D, 1.0D }, , { 0.3455D, 0.309D }, , { 0.9511D, 0.309D }, , { 0.3795918367346939D, -0.12653061224489795D }, , { 0.6122448979591837D, -0.8040816326530612D }, , { 0.0D, -0.35918367346938773D },  }, aint, aint1, flag4, flag2, false);
/*     */         }
/* 235 */         else if (l == 3) {
/*     */           
/* 237 */           createShaped(0.5D, new double[][] { { 0.0D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.6D }, , { 0.6D, 0.6D }, , { 0.6D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.0D }, , { 0.4D, 0.0D }, , { 0.4D, -0.6D }, , { 0.2D, -0.6D }, , { 0.2D, -0.4D }, , { 0.0D, -0.4D },  }, aint, aint1, flag4, flag2, true);
/*     */         }
/* 239 */         else if (l == 4) {
/*     */           
/* 241 */           createBurst(aint, aint1, flag4, flag2);
/*     */         }
/*     */         else {
/*     */           
/* 245 */           createBall(0.25D, 2, aint, aint1, flag4, flag2);
/*     */         } 
/*     */         
/* 248 */         int j = aint[0];
/* 249 */         float f = ((j & 0xFF0000) >> 16) / 255.0F;
/* 250 */         float f1 = ((j & 0xFF00) >> 8) / 255.0F;
/* 251 */         float f2 = ((j & 0xFF) >> 0) / 255.0F;
/* 252 */         ParticleFirework.Overlay particlefirework$overlay = new ParticleFirework.Overlay(this.worldObj, this.posX, this.posY, this.posZ);
/* 253 */         particlefirework$overlay.setRBGColorF(f, f1, f2);
/* 254 */         this.theEffectRenderer.addEffect(particlefirework$overlay);
/*     */       } 
/*     */       
/* 257 */       this.fireworkAge++;
/*     */       
/* 259 */       if (this.fireworkAge > this.particleMaxAge) {
/*     */         
/* 261 */         if (this.twinkle) {
/*     */           
/* 263 */           boolean flag3 = isFarFromCamera();
/* 264 */           SoundEvent soundevent = flag3 ? SoundEvents.ENTITY_FIREWORK_TWINKLE_FAR : SoundEvents.ENTITY_FIREWORK_TWINKLE;
/* 265 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, soundevent, SoundCategory.AMBIENT, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
/*     */         } 
/*     */         
/* 268 */         setExpired();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isFarFromCamera() {
/* 274 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 275 */       return !(minecraft != null && minecraft.getRenderViewEntity() != null && minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) < 256.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     private void createParticle(double p_92034_1_, double p_92034_3_, double p_92034_5_, double p_92034_7_, double p_92034_9_, double p_92034_11_, int[] p_92034_13_, int[] p_92034_14_, boolean p_92034_15_, boolean p_92034_16_) {
/* 280 */       ParticleFirework.Spark particlefirework$spark = new ParticleFirework.Spark(this.worldObj, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_, this.theEffectRenderer);
/* 281 */       particlefirework$spark.setAlphaF(0.99F);
/* 282 */       particlefirework$spark.setTrail(p_92034_15_);
/* 283 */       particlefirework$spark.setTwinkle(p_92034_16_);
/* 284 */       int i = this.rand.nextInt(p_92034_13_.length);
/* 285 */       particlefirework$spark.setColor(p_92034_13_[i]);
/*     */       
/* 287 */       if (p_92034_14_ != null && p_92034_14_.length > 0)
/*     */       {
/* 289 */         particlefirework$spark.setColorFade(p_92034_14_[this.rand.nextInt(p_92034_14_.length)]);
/*     */       }
/*     */       
/* 292 */       this.theEffectRenderer.addEffect(particlefirework$spark);
/*     */     }
/*     */ 
/*     */     
/*     */     private void createBall(double speed, int size, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
/* 297 */       double d0 = this.posX;
/* 298 */       double d1 = this.posY;
/* 299 */       double d2 = this.posZ;
/*     */       
/* 301 */       for (int i = -size; i <= size; i++) {
/*     */         
/* 303 */         for (int j = -size; j <= size; j++) {
/*     */           
/* 305 */           for (int k = -size; k <= size; k++) {
/*     */             
/* 307 */             double d3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 308 */             double d4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 309 */             double d5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 310 */             double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + this.rand.nextGaussian() * 0.05D;
/* 311 */             createParticle(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, colours, fadeColours, trail, twinkleIn);
/*     */             
/* 313 */             if (i != -size && i != size && j != -size && j != size)
/*     */             {
/* 315 */               k += size * 2 - 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void createShaped(double speed, double[][] shape, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn, boolean p_92038_8_) {
/* 324 */       double d0 = shape[0][0];
/* 325 */       double d1 = shape[0][1];
/* 326 */       createParticle(this.posX, this.posY, this.posZ, d0 * speed, d1 * speed, 0.0D, colours, fadeColours, trail, twinkleIn);
/* 327 */       float f = this.rand.nextFloat() * 3.1415927F;
/* 328 */       double d2 = p_92038_8_ ? 0.034D : 0.34D;
/*     */       
/* 330 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 332 */         double d3 = f + (i * 3.1415927F) * d2;
/* 333 */         double d4 = d0;
/* 334 */         double d5 = d1;
/*     */         
/* 336 */         for (int j = 1; j < shape.length; j++) {
/*     */           
/* 338 */           double d6 = shape[j][0];
/* 339 */           double d7 = shape[j][1];
/*     */           
/* 341 */           for (double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D) {
/*     */             
/* 343 */             double d9 = (d4 + (d6 - d4) * d8) * speed;
/* 344 */             double d10 = (d5 + (d7 - d5) * d8) * speed;
/* 345 */             double d11 = d9 * Math.sin(d3);
/* 346 */             d9 *= Math.cos(d3);
/*     */             
/* 348 */             for (double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D)
/*     */             {
/* 350 */               createParticle(this.posX, this.posY, this.posZ, d9 * d12, d10, d11 * d12, colours, fadeColours, trail, twinkleIn);
/*     */             }
/*     */           } 
/*     */           
/* 354 */           d4 = d6;
/* 355 */           d5 = d7;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void createBurst(int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
/* 362 */       double d0 = this.rand.nextGaussian() * 0.05D;
/* 363 */       double d1 = this.rand.nextGaussian() * 0.05D;
/*     */       
/* 365 */       for (int i = 0; i < 70; i++) {
/*     */         
/* 367 */         double d2 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + d0;
/* 368 */         double d3 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + d1;
/* 369 */         double d4 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
/* 370 */         createParticle(this.posX, this.posY, this.posZ, d2, d4, d3, colours, fadeColours, trail, twinkleIn);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getFXLayer() {
/* 376 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
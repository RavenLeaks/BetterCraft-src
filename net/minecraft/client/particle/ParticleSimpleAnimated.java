/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleSimpleAnimated
/*     */   extends Particle
/*     */ {
/*     */   private final int textureIdx;
/*     */   private final int numAgingFrames;
/*     */   private final float yAccel;
/*  22 */   private float field_191239_M = 0.91F;
/*     */ 
/*     */   
/*     */   private float fadeTargetRed;
/*     */ 
/*     */   
/*     */   private float fadeTargetGreen;
/*     */ 
/*     */   
/*     */   private float fadeTargetBlue;
/*     */ 
/*     */   
/*     */   private boolean fadingColor;
/*     */ 
/*     */   
/*     */   public ParticleSimpleAnimated(World worldIn, double x, double y, double z, int textureIdxIn, int numFrames, float yAccelIn) {
/*  38 */     super(worldIn, x, y, z);
/*  39 */     this.textureIdx = textureIdxIn;
/*  40 */     this.numAgingFrames = numFrames;
/*  41 */     this.yAccel = yAccelIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int p_187146_1_) {
/*  46 */     float f = ((p_187146_1_ & 0xFF0000) >> 16) / 255.0F;
/*  47 */     float f1 = ((p_187146_1_ & 0xFF00) >> 8) / 255.0F;
/*  48 */     float f2 = ((p_187146_1_ & 0xFF) >> 0) / 255.0F;
/*  49 */     float f3 = 1.0F;
/*  50 */     setRBGColorF(f * 1.0F, f1 * 1.0F, f2 * 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorFade(int rgb) {
/*  58 */     this.fadeTargetRed = ((rgb & 0xFF0000) >> 16) / 255.0F;
/*  59 */     this.fadeTargetGreen = ((rgb & 0xFF00) >> 8) / 255.0F;
/*  60 */     this.fadeTargetBlue = ((rgb & 0xFF) >> 0) / 255.0F;
/*  61 */     this.fadingColor = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTransparent() {
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  71 */     this.prevPosX = this.posX;
/*  72 */     this.prevPosY = this.posY;
/*  73 */     this.prevPosZ = this.posZ;
/*     */     
/*  75 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  77 */       setExpired();
/*     */     }
/*     */     
/*  80 */     if (this.particleAge > this.particleMaxAge / 2) {
/*     */       
/*  82 */       setAlphaF(1.0F - (this.particleAge - (this.particleMaxAge / 2)) / this.particleMaxAge);
/*     */       
/*  84 */       if (this.fadingColor) {
/*     */         
/*  86 */         this.particleRed += (this.fadeTargetRed - this.particleRed) * 0.2F;
/*  87 */         this.particleGreen += (this.fadeTargetGreen - this.particleGreen) * 0.2F;
/*  88 */         this.particleBlue += (this.fadeTargetBlue - this.particleBlue) * 0.2F;
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     setParticleTextureIndex(this.textureIdx + this.numAgingFrames - 1 - this.particleAge * this.numAgingFrames / this.particleMaxAge);
/*  93 */     this.motionY += this.yAccel;
/*  94 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  95 */     this.motionX *= this.field_191239_M;
/*  96 */     this.motionY *= this.field_191239_M;
/*  97 */     this.motionZ *= this.field_191239_M;
/*     */     
/*  99 */     if (this.isCollided) {
/*     */       
/* 101 */       this.motionX *= 0.699999988079071D;
/* 102 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 108 */     return 15728880;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_191238_f(float p_191238_1_) {
/* 113 */     this.field_191239_M = p_191238_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleSimpleAnimated.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
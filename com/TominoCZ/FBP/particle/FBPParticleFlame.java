/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPRenderUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleFlame;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class FBPParticleFlame
/*     */   extends ParticleFlame
/*     */ {
/*     */   Minecraft mc;
/*     */   double startScale;
/*     */   double scaleAlpha;
/*     */   double prevParticleScale;
/*     */   double prevParticleAlpha;
/*  32 */   double endMult = 1.0D;
/*     */ 
/*     */   
/*     */   boolean spawnAnother = true;
/*     */   
/*     */   Vec3d startPos;
/*     */   
/*     */   Vec3d[] cube;
/*     */   
/*     */   Vec2f par;
/*     */ 
/*     */   
/*     */   protected FBPParticleFlame(World worldObjIn, double xCoordIn, double yCoordIn, double zCoordIn, double mX, double mY, double mZ, boolean spawnAnother) {
/*  45 */     super(worldObjIn, xCoordIn, yCoordIn - 0.06D, zCoordIn, mX, mY, mZ);
/*  46 */     IBlockState bs = worldObjIn.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
/*     */     
/*  48 */     this.spawnAnother = spawnAnother;
/*     */     
/*  50 */     if (bs.getBlock() != Blocks.TORCH) {
/*  51 */       spawnAnother = false;
/*     */     }
/*  53 */     if (bs == Blocks.TORCH.getDefaultState()) {
/*  54 */       this.prevPosY = this.posY += 0.03999999910593033D;
/*     */     }
/*  56 */     this.startPos = new Vec3d(this.posX, this.posY, this.posZ);
/*     */     
/*  58 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  60 */     this.motionY = -8.500000112690032E-4D;
/*  61 */     this.particleGravity = -0.05F;
/*     */     
/*  63 */     this.particleTexture = this.mc.getBlockRendererDispatcher().getBlockModelShapes()
/*  64 */       .getTexture(Blocks.SNOW.getDefaultState());
/*     */     
/*  66 */     this.particleScale = (float)(this.particleScale * FBP.scaleMult * 2.5D);
/*  67 */     this.particleMaxAge = FBP.random.nextInt(3, 5);
/*     */     
/*  69 */     this.particleRed = 1.0F;
/*  70 */     this.particleGreen = 1.0F;
/*  71 */     this.particleBlue = 0.0F;
/*     */     
/*  73 */     float angleY = this.rand.nextFloat() * 80.0F;
/*     */     
/*  75 */     this.cube = new Vec3d[FBP.CUBE.length];
/*     */     
/*  77 */     for (int i = 0; i < FBP.CUBE.length; i++) {
/*     */       
/*  79 */       Vec3d vec = FBP.CUBE[i];
/*  80 */       this.cube[i] = FBPRenderUtil.rotatef_d(vec, 0.0F, angleY, 0.0F);
/*     */     } 
/*     */     
/*  83 */     this.particleAlpha = 1.0F;
/*     */     
/*  85 */     if (FBP.randomFadingSpeed) {
/*  86 */       this.endMult *= FBP.random.nextDouble(0.9875D, 1.0D);
/*     */     }
/*  88 */     multipleParticleScaleBy(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Particle multipleParticleScaleBy(float scale) {
/*  94 */     Particle p = super.multipleParticleScaleBy(scale);
/*  95 */     if (!FBP.isEnabled()) {
/*  96 */       return p;
/*     */     }
/*  98 */     this.startScale = this.particleScale;
/*  99 */     this.scaleAlpha = this.particleScale * 0.35D;
/*     */     
/* 101 */     float f = this.particleScale / 80.0F;
/*     */     
/* 103 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - f, this.posY - f, this.posZ - f, this.posX + f, this.posY + f, this.posZ + f));
/*     */     
/* 105 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/* 111 */     if (!FBP.isEnabled())
/* 112 */       return super.getFXLayer(); 
/* 113 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 119 */     if (!FBP.isEnabled()) {
/* 120 */       super.onUpdate();
/*     */       return;
/*     */     } 
/* 123 */     this.prevPosX = this.posX;
/* 124 */     this.prevPosY = this.posY;
/* 125 */     this.prevPosZ = this.posZ;
/*     */     
/* 127 */     this.prevParticleAlpha = this.particleAlpha;
/* 128 */     this.prevParticleScale = this.particleScale;
/*     */     
/* 130 */     if (!FBP.fancyFlame) {
/* 131 */       this.isExpired = true;
/*     */     }
/* 133 */     if (++this.particleAge >= this.particleMaxAge) {
/*     */       
/* 135 */       if (FBP.randomFadingSpeed) {
/* 136 */         this.particleScale = (float)(this.particleScale * 0.949999988079071D * this.endMult);
/*     */       } else {
/* 138 */         this.particleScale *= 0.95F;
/*     */       } 
/* 140 */       if (this.particleAlpha > 0.01D && this.particleScale <= this.scaleAlpha)
/*     */       {
/* 142 */         if (FBP.randomFadingSpeed) {
/* 143 */           this.particleAlpha = (float)(this.particleAlpha * 0.8999999761581421D * this.endMult);
/*     */         } else {
/* 145 */           this.particleAlpha *= 0.9F;
/*     */         } 
/*     */       }
/* 148 */       if (this.particleAlpha <= 0.01D) {
/* 149 */         setExpired();
/* 150 */       } else if (this.particleAlpha <= 0.325D && this.spawnAnother && 
/* 151 */         this.worldObj.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock() == Blocks.TORCH) {
/*     */         
/* 153 */         this.spawnAnother = false;
/*     */         
/* 155 */         this.mc.effectRenderer.addEffect(
/* 156 */             (Particle)new FBPParticleFlame(this.worldObj, this.startPos.xCoord, this.startPos.yCoord, this.startPos.zCoord, 0.0D, 0.0D, 0.0D, this.spawnAnother));
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     this.motionY -= 0.02D * this.particleGravity;
/* 161 */     moveEntity(0.0D, this.motionY, 0.0D);
/* 162 */     this.motionY *= 0.95D;
/*     */     
/* 164 */     if (this.onGround) {
/*     */       
/* 166 */       this.motionX *= 0.899999988079071D;
/* 167 */       this.motionZ *= 0.899999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 174 */     if (!FBP.isEnabled()) {
/* 175 */       super.moveEntity(x, y, z);
/*     */       return;
/*     */     } 
/* 178 */     double X = x;
/* 179 */     double Y = y;
/* 180 */     double Z = z;
/*     */     
/* 182 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(null, getEntityBoundingBox().expand(x, y, z));
/*     */     
/* 184 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 186 */       y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);
/*     */     }
/*     */     
/* 189 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*     */     
/* 191 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 193 */       x = axisalignedbb.calculateXOffset(getEntityBoundingBox(), x);
/*     */     }
/*     */     
/* 196 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*     */     
/* 198 */     for (AxisAlignedBB axisalignedbb : list)
/*     */     {
/* 200 */       z = axisalignedbb.calculateZOffset(getEntityBoundingBox(), z);
/*     */     }
/*     */     
/* 203 */     setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*     */ 
/*     */     
/* 206 */     resetPositionToBB();
/* 207 */     this.onGround = (y != Y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetPositionToBB() {
/* 213 */     if (!FBP.isEnabled()) {
/* 214 */       super.resetPositionToBB();
/*     */       return;
/*     */     } 
/* 217 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 218 */     this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0D;
/* 219 */     this.posY = (axisalignedbb.minY + axisalignedbb.maxY) / 2.0D;
/* 220 */     this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder buf, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 227 */     if (!FBP.isEnabled()) {
/* 228 */       super.renderParticle(buf, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       return;
/*     */     } 
/* 231 */     if (!FBP.isEnabled() && this.particleMaxAge != 0) {
/* 232 */       this.particleMaxAge = 0;
/*     */     }
/* 234 */     float f = this.particleTexture.getInterpolatedU(4.400000095367432D);
/* 235 */     float f1 = this.particleTexture.getInterpolatedV(4.400000095367432D);
/*     */     
/* 237 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 238 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 239 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*     */     
/* 241 */     int i = getBrightnessForRender(partialTicks);
/*     */     
/* 243 */     float alpha = (float)(this.prevParticleAlpha + (this.particleAlpha - this.prevParticleAlpha) * partialTicks);
/*     */ 
/*     */     
/* 246 */     float f4 = (float)(this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks);
/*     */     
/* 248 */     if (this.particleAge >= this.particleMaxAge) {
/* 249 */       this.particleGreen = (float)(f4 / this.startScale);
/*     */     }
/* 251 */     GlStateManager.enableCull();
/*     */     
/* 253 */     this.par = new Vec2f(f, f1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     Tessellator.getInstance().draw();
/* 260 */     (this.mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 261 */     buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */     
/* 263 */     buf.setTranslation(f5, f6, f7);
/* 264 */     putCube(buf, (f4 / 80.0F), i >> 16 & 0xFFFF, i & 0xFFFF, this.particleRed, this.particleGreen, this.particleBlue, alpha);
/* 265 */     buf.setTranslation(0.0D, 0.0D, 0.0D);
/*     */     
/* 267 */     Tessellator.getInstance().draw();
/* 268 */     Minecraft.getMinecraft().getTextureManager().bindTexture(FBP.LOCATION_PARTICLE_TEXTURE);
/* 269 */     buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putCube(BufferBuilder worldObjRendererIn, double scale, int j, int k, float r, float g, float b, float a) {
/* 274 */     float brightnessForRender = 1.0F;
/*     */     
/* 276 */     float R = 0.0F;
/* 277 */     float G = 0.0F;
/* 278 */     float B = 0.0F;
/*     */     
/* 280 */     for (int i = 0; i < this.cube.length; i += 4) {
/*     */       
/* 282 */       Vec3d v1 = this.cube[i];
/* 283 */       Vec3d v2 = this.cube[i + 1];
/* 284 */       Vec3d v3 = this.cube[i + 2];
/* 285 */       Vec3d v4 = this.cube[i + 3];
/*     */       
/* 287 */       R = r * brightnessForRender;
/* 288 */       G = g * brightnessForRender;
/* 289 */       B = b * brightnessForRender;
/*     */       
/* 291 */       brightnessForRender = (float)(brightnessForRender * 0.95D);
/*     */       
/* 293 */       addVt(worldObjRendererIn, scale, v1, this.par.x, this.par.y, j, k, R, G, B, a);
/* 294 */       addVt(worldObjRendererIn, scale, v2, this.par.x, this.par.y, j, k, R, G, B, a);
/* 295 */       addVt(worldObjRendererIn, scale, v3, this.par.x, this.par.y, j, k, R, G, B, a);
/* 296 */       addVt(worldObjRendererIn, scale, v4, this.par.x, this.par.y, j, k, R, G, B, a);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addVt(BufferBuilder worldObjRendererIn, double scale, Vec3d pos, double u, double v, int j, int k, float r, float g, float b, float a) {
/* 303 */     worldObjRendererIn.pos(pos.xCoord * scale, pos.yCoord * scale, pos.zCoord * scale).tex(u, v).color(r, g, b, a).lightmap(j, k)
/* 304 */       .endVertex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 310 */     if (!FBP.isEnabled()) {
/* 311 */       return super.getBrightnessForRender(p_189214_1_);
/*     */     }
/* 313 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 314 */     int j = 0;
/*     */     
/* 316 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ)))
/*     */     {
/* 318 */       j = this.worldObj.getCombinedLight(new BlockPos(this.posX, this.posY, this.posZ), 0);
/*     */     }
/*     */     
/* 321 */     return (i == 0) ? j : i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleFlame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
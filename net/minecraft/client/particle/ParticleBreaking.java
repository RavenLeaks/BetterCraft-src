/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleBreaking
/*     */   extends Particle
/*     */ {
/*     */   protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, Item itemIn) {
/*  15 */     this(worldIn, posXIn, posYIn, posZIn, itemIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, Item itemIn, int meta) {
/*  20 */     this(worldIn, posXIn, posYIn, posZIn, itemIn, meta);
/*  21 */     this.motionX *= 0.10000000149011612D;
/*  22 */     this.motionY *= 0.10000000149011612D;
/*  23 */     this.motionZ *= 0.10000000149011612D;
/*  24 */     this.motionX += xSpeedIn;
/*  25 */     this.motionY += ySpeedIn;
/*  26 */     this.motionZ += zSpeedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleBreaking(World worldIn, double posXIn, double posYIn, double posZIn, Item itemIn, int meta) {
/*  31 */     super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
/*  32 */     setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta));
/*  33 */     this.particleRed = 1.0F;
/*  34 */     this.particleGreen = 1.0F;
/*  35 */     this.particleBlue = 1.0F;
/*  36 */     this.particleGravity = Blocks.SNOW.blockParticleGravity;
/*  37 */     this.particleScale /= 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  46 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  54 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/*  55 */     float f1 = f + 0.015609375F;
/*  56 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/*  57 */     float f3 = f2 + 0.015609375F;
/*  58 */     float f4 = 0.1F * this.particleScale;
/*     */     
/*  60 */     if (this.particleTexture != null) {
/*     */       
/*  62 */       f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/*  63 */       f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/*  64 */       f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*  65 */       f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } 
/*     */     
/*  68 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  69 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  70 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  71 */     int i = getBrightnessForRender(partialTicks);
/*  72 */     int j = i >> 16 & 0xFFFF;
/*  73 */     int k = i & 0xFFFF;
/*  74 */     worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  75 */     worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  76 */     worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  77 */     worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  84 */       int i = (p_178902_15_.length > 1) ? p_178902_15_[1] : 0;
/*  85 */       return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Item.getItemById(p_178902_15_[0]), i);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SlimeFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  93 */       return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.SLIME_BALL);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SnowballFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 101 */       return new ParticleBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.SNOWBALL);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleBreaking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
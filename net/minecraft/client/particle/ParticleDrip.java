/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleDrip
/*     */   extends Particle
/*     */ {
/*     */   private final Material materialType;
/*     */   private int bobTimer;
/*     */   
/*     */   protected ParticleDrip(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, Material p_i1203_8_) {
/*  21 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*  22 */     this.motionX = 0.0D;
/*  23 */     this.motionY = 0.0D;
/*  24 */     this.motionZ = 0.0D;
/*     */     
/*  26 */     if (p_i1203_8_ == Material.WATER) {
/*     */       
/*  28 */       this.particleRed = 0.0F;
/*  29 */       this.particleGreen = 0.0F;
/*  30 */       this.particleBlue = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/*  34 */       this.particleRed = 1.0F;
/*  35 */       this.particleGreen = 0.0F;
/*  36 */       this.particleBlue = 0.0F;
/*     */     } 
/*     */     
/*  39 */     setParticleTextureIndex(113);
/*  40 */     setSize(0.01F, 0.01F);
/*  41 */     this.particleGravity = 0.06F;
/*  42 */     this.materialType = p_i1203_8_;
/*  43 */     this.bobTimer = 40;
/*  44 */     this.particleMaxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
/*  45 */     this.motionX = 0.0D;
/*  46 */     this.motionY = 0.0D;
/*  47 */     this.motionZ = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/*  52 */     return (this.materialType == Material.WATER) ? super.getBrightnessForRender(p_189214_1_) : 257;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  57 */     this.prevPosX = this.posX;
/*  58 */     this.prevPosY = this.posY;
/*  59 */     this.prevPosZ = this.posZ;
/*     */     
/*  61 */     if (this.materialType == Material.WATER) {
/*     */       
/*  63 */       this.particleRed = 0.2F;
/*  64 */       this.particleGreen = 0.3F;
/*  65 */       this.particleBlue = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/*  69 */       this.particleRed = 1.0F;
/*  70 */       this.particleGreen = 16.0F / (40 - this.bobTimer + 16);
/*  71 */       this.particleBlue = 4.0F / (40 - this.bobTimer + 8);
/*     */     } 
/*     */     
/*  74 */     this.motionY -= this.particleGravity;
/*     */     
/*  76 */     if (this.bobTimer-- > 0) {
/*     */       
/*  78 */       this.motionX *= 0.02D;
/*  79 */       this.motionY *= 0.02D;
/*  80 */       this.motionZ *= 0.02D;
/*  81 */       setParticleTextureIndex(113);
/*     */     }
/*     */     else {
/*     */       
/*  85 */       setParticleTextureIndex(112);
/*     */     } 
/*     */     
/*  88 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  89 */     this.motionX *= 0.9800000190734863D;
/*  90 */     this.motionY *= 0.9800000190734863D;
/*  91 */     this.motionZ *= 0.9800000190734863D;
/*     */     
/*  93 */     if (this.particleMaxAge-- <= 0)
/*     */     {
/*  95 */       setExpired();
/*     */     }
/*     */     
/*  98 */     if (this.isCollided) {
/*     */       
/* 100 */       if (this.materialType == Material.WATER) {
/*     */         
/* 102 */         setExpired();
/* 103 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       else {
/*     */         
/* 107 */         setParticleTextureIndex(114);
/*     */       } 
/*     */       
/* 110 */       this.motionX *= 0.699999988079071D;
/* 111 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */     
/* 114 */     BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/* 115 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 116 */     Material material = iblockstate.getMaterial();
/*     */     
/* 118 */     if (material.isLiquid() || material.isSolid()) {
/*     */       
/* 120 */       double d0 = 0.0D;
/*     */       
/* 122 */       if (iblockstate.getBlock() instanceof BlockLiquid)
/*     */       {
/* 124 */         d0 = BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue());
/*     */       }
/*     */       
/* 127 */       double d1 = (MathHelper.floor(this.posY) + 1) - d0;
/*     */       
/* 129 */       if (this.posY < d1)
/*     */       {
/* 131 */         setExpired();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class LavaFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 140 */       return new ParticleDrip(worldIn, xCoordIn, yCoordIn, zCoordIn, Material.LAVA);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WaterFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 148 */       return new ParticleDrip(worldIn, xCoordIn, yCoordIn, zCoordIn, Material.WATER);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleDrip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
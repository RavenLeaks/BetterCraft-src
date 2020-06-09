/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityEnderEye
/*     */   extends Entity
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   private int despawnTimer;
/*     */   private boolean shatterOrDrop;
/*     */   
/*     */   public EntityEnderEye(World worldIn) {
/*  28 */     super(worldIn);
/*  29 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  41 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  43 */     if (Double.isNaN(d0))
/*     */     {
/*  45 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  48 */     d0 *= 64.0D;
/*  49 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityEnderEye(World worldIn, double x, double y, double z) {
/*  54 */     super(worldIn);
/*  55 */     this.despawnTimer = 0;
/*  56 */     setSize(0.25F, 0.25F);
/*  57 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTowards(BlockPos pos) {
/*  62 */     double d0 = pos.getX();
/*  63 */     int i = pos.getY();
/*  64 */     double d1 = pos.getZ();
/*  65 */     double d2 = d0 - this.posX;
/*  66 */     double d3 = d1 - this.posZ;
/*  67 */     float f = MathHelper.sqrt(d2 * d2 + d3 * d3);
/*     */     
/*  69 */     if (f > 12.0F) {
/*     */       
/*  71 */       this.targetX = this.posX + d2 / f * 12.0D;
/*  72 */       this.targetZ = this.posZ + d3 / f * 12.0D;
/*  73 */       this.targetY = this.posY + 8.0D;
/*     */     }
/*     */     else {
/*     */       
/*  77 */       this.targetX = d0;
/*  78 */       this.targetY = i;
/*  79 */       this.targetZ = d1;
/*     */     } 
/*     */     
/*  82 */     this.despawnTimer = 0;
/*  83 */     this.shatterOrDrop = (this.rand.nextInt(5) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/*  91 */     this.motionX = x;
/*  92 */     this.motionY = y;
/*  93 */     this.motionZ = z;
/*     */     
/*  95 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/*  97 */       float f = MathHelper.sqrt(x * x + z * z);
/*  98 */       this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/*  99 */       this.rotationPitch = (float)(MathHelper.atan2(y, f) * 57.29577951308232D);
/* 100 */       this.prevRotationYaw = this.rotationYaw;
/* 101 */       this.prevRotationPitch = this.rotationPitch;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 110 */     this.lastTickPosX = this.posX;
/* 111 */     this.lastTickPosY = this.posY;
/* 112 */     this.lastTickPosZ = this.posZ;
/* 113 */     super.onUpdate();
/* 114 */     this.posX += this.motionX;
/* 115 */     this.posY += this.motionY;
/* 116 */     this.posZ += this.motionZ;
/* 117 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 118 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */     
/* 120 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 127 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 130 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 132 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 135 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 137 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 140 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 141 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/*     */     
/* 143 */     if (!this.world.isRemote) {
/*     */       
/* 145 */       double d0 = this.targetX - this.posX;
/* 146 */       double d1 = this.targetZ - this.posZ;
/* 147 */       float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
/* 148 */       float f2 = (float)MathHelper.atan2(d1, d0);
/* 149 */       double d2 = f + (f1 - f) * 0.0025D;
/*     */       
/* 151 */       if (f1 < 1.0F) {
/*     */         
/* 153 */         d2 *= 0.8D;
/* 154 */         this.motionY *= 0.8D;
/*     */       } 
/*     */       
/* 157 */       this.motionX = Math.cos(f2) * d2;
/* 158 */       this.motionZ = Math.sin(f2) * d2;
/*     */       
/* 160 */       if (this.posY < this.targetY) {
/*     */         
/* 162 */         this.motionY += (1.0D - this.motionY) * 0.014999999664723873D;
/*     */       }
/*     */       else {
/*     */         
/* 166 */         this.motionY += (-1.0D - this.motionY) * 0.014999999664723873D;
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     float f3 = 0.25F;
/*     */     
/* 172 */     if (isInWater()) {
/*     */       
/* 174 */       for (int i = 0; i < 4; i++)
/*     */       {
/* 176 */         this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 181 */       this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX - this.motionX * 0.25D + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * 0.25D - 0.5D, this.posZ - this.motionZ * 0.25D + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */     } 
/*     */     
/* 184 */     if (!this.world.isRemote) {
/*     */       
/* 186 */       setPosition(this.posX, this.posY, this.posZ);
/* 187 */       this.despawnTimer++;
/*     */       
/* 189 */       if (this.despawnTimer > 80 && !this.world.isRemote) {
/*     */         
/* 191 */         playSound(SoundEvents.field_193777_bb, 1.0F, 1.0F);
/* 192 */         setDead();
/*     */         
/* 194 */         if (this.shatterOrDrop) {
/*     */           
/* 196 */           this.world.spawnEntityInWorld(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(Items.ENDER_EYE)));
/*     */         }
/*     */         else {
/*     */           
/* 200 */           this.world.playEvent(2003, new BlockPos(this), 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 225 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/* 230 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 238 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityEnderEye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
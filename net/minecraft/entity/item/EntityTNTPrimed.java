/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityTNTPrimed
/*     */   extends Entity {
/*  16 */   private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(EntityTNTPrimed.class, DataSerializers.VARINT);
/*     */   
/*     */   @Nullable
/*     */   private EntityLivingBase tntPlacedBy;
/*     */   
/*     */   private int fuse;
/*     */ 
/*     */   
/*     */   public EntityTNTPrimed(World worldIn) {
/*  25 */     super(worldIn);
/*  26 */     this.fuse = 80;
/*  27 */     this.preventEntitySpawning = true;
/*  28 */     this.isImmuneToFire = true;
/*  29 */     setSize(0.98F, 0.98F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityTNTPrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
/*  34 */     this(worldIn);
/*  35 */     setPosition(x, y, z);
/*  36 */     float f = (float)(Math.random() * 6.283185307179586D);
/*  37 */     this.motionX = (-((float)Math.sin(f)) * 0.02F);
/*  38 */     this.motionY = 0.20000000298023224D;
/*  39 */     this.motionZ = (-((float)Math.cos(f)) * 0.02F);
/*  40 */     setFuse(80);
/*  41 */     this.prevPosX = x;
/*  42 */     this.prevPosY = y;
/*  43 */     this.prevPosZ = z;
/*  44 */     this.tntPlacedBy = igniter;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  49 */     this.dataManager.register(FUSE, Integer.valueOf(80));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  66 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  74 */     this.prevPosX = this.posX;
/*  75 */     this.prevPosY = this.posY;
/*  76 */     this.prevPosZ = this.posZ;
/*     */     
/*  78 */     if (!hasNoGravity())
/*     */     {
/*  80 */       this.motionY -= 0.03999999910593033D;
/*     */     }
/*     */     
/*  83 */     moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*  84 */     this.motionX *= 0.9800000190734863D;
/*  85 */     this.motionY *= 0.9800000190734863D;
/*  86 */     this.motionZ *= 0.9800000190734863D;
/*     */     
/*  88 */     if (this.onGround) {
/*     */       
/*  90 */       this.motionX *= 0.699999988079071D;
/*  91 */       this.motionZ *= 0.699999988079071D;
/*  92 */       this.motionY *= -0.5D;
/*     */     } 
/*     */     
/*  95 */     this.fuse--;
/*     */     
/*  97 */     if (this.fuse <= 0) {
/*     */       
/*  99 */       setDead();
/*     */       
/* 101 */       if (!this.world.isRemote)
/*     */       {
/* 103 */         explode();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 108 */       handleWaterMovement();
/* 109 */       this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void explode() {
/* 115 */     float f = 4.0F;
/* 116 */     this.world.createExplosion(this, this.posX, this.posY + (this.height / 16.0F), this.posZ, 4.0F, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 124 */     compound.setShort("Fuse", (short)getFuse());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 132 */     setFuse(compound.getShort("Fuse"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getTntPlacedBy() {
/* 142 */     return this.tntPlacedBy;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 147 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFuse(int fuseIn) {
/* 152 */     this.dataManager.set(FUSE, Integer.valueOf(fuseIn));
/* 153 */     this.fuse = fuseIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 158 */     if (FUSE.equals(key))
/*     */     {
/* 160 */       this.fuse = getFuseDataManager();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFuseDataManager() {
/* 169 */     return ((Integer)this.dataManager.get(FUSE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFuse() {
/* 174 */     return this.fuse;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
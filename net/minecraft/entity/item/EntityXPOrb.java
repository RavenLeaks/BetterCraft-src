/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityXPOrb
/*     */   extends Entity
/*     */ {
/*     */   public int xpColor;
/*     */   public int xpOrbAge;
/*     */   public int delayBeforeCanPickup;
/*  29 */   private int xpOrbHealth = 5;
/*     */ 
/*     */   
/*     */   private int xpValue;
/*     */ 
/*     */   
/*     */   private EntityPlayer closestPlayer;
/*     */ 
/*     */   
/*     */   private int xpTargetColor;
/*     */ 
/*     */   
/*     */   public EntityXPOrb(World worldIn, double x, double y, double z, int expValue) {
/*  42 */     super(worldIn);
/*  43 */     setSize(0.5F, 0.5F);
/*  44 */     setPosition(x, y, z);
/*  45 */     this.rotationYaw = (float)(Math.random() * 360.0D);
/*  46 */     this.motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  47 */     this.motionY = ((float)(Math.random() * 0.2D) * 2.0F);
/*  48 */     this.motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  49 */     this.xpValue = expValue;
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
/*     */   public EntityXPOrb(World worldIn) {
/*  63 */     super(worldIn);
/*  64 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/*  73 */     float f = 0.5F;
/*  74 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/*  75 */     int i = super.getBrightnessForRender();
/*  76 */     int j = i & 0xFF;
/*  77 */     int k = i >> 16 & 0xFF;
/*  78 */     j += (int)(f * 15.0F * 16.0F);
/*     */     
/*  80 */     if (j > 240)
/*     */     {
/*  82 */       j = 240;
/*     */     }
/*     */     
/*  85 */     return j | k << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  93 */     super.onUpdate();
/*     */     
/*  95 */     if (this.delayBeforeCanPickup > 0)
/*     */     {
/*  97 */       this.delayBeforeCanPickup--;
/*     */     }
/*     */     
/* 100 */     this.prevPosX = this.posX;
/* 101 */     this.prevPosY = this.posY;
/* 102 */     this.prevPosZ = this.posZ;
/*     */     
/* 104 */     if (!hasNoGravity())
/*     */     {
/* 106 */       this.motionY -= 0.029999999329447746D;
/*     */     }
/*     */     
/* 109 */     if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
/*     */       
/* 111 */       this.motionY = 0.20000000298023224D;
/* 112 */       this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 113 */       this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 114 */       playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/*     */     } 
/*     */     
/* 117 */     pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ);
/* 118 */     double d0 = 8.0D;
/*     */     
/* 120 */     if (this.xpTargetColor < this.xpColor - 20 + getEntityId() % 100) {
/*     */       
/* 122 */       if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > 64.0D)
/*     */       {
/* 124 */         this.closestPlayer = this.world.getClosestPlayerToEntity(this, 8.0D);
/*     */       }
/*     */       
/* 127 */       this.xpTargetColor = this.xpColor;
/*     */     } 
/*     */     
/* 130 */     if (this.closestPlayer != null && this.closestPlayer.isSpectator())
/*     */     {
/* 132 */       this.closestPlayer = null;
/*     */     }
/*     */     
/* 135 */     if (this.closestPlayer != null) {
/*     */       
/* 137 */       double d1 = (this.closestPlayer.posX - this.posX) / 8.0D;
/* 138 */       double d2 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() / 2.0D - this.posY) / 8.0D;
/* 139 */       double d3 = (this.closestPlayer.posZ - this.posZ) / 8.0D;
/* 140 */       double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/* 141 */       double d5 = 1.0D - d4;
/*     */       
/* 143 */       if (d5 > 0.0D) {
/*     */         
/* 145 */         d5 *= d5;
/* 146 */         this.motionX += d1 / d4 * d5 * 0.1D;
/* 147 */         this.motionY += d2 / d4 * d5 * 0.1D;
/* 148 */         this.motionZ += d3 / d4 * d5 * 0.1D;
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 153 */     float f = 0.98F;
/*     */     
/* 155 */     if (this.onGround)
/*     */     {
/* 157 */       f = (this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor((getEntityBoundingBox()).minY) - 1, MathHelper.floor(this.posZ))).getBlock()).slipperiness * 0.98F;
/*     */     }
/*     */     
/* 160 */     this.motionX *= f;
/* 161 */     this.motionY *= 0.9800000190734863D;
/* 162 */     this.motionZ *= f;
/*     */     
/* 164 */     if (this.onGround)
/*     */     {
/* 166 */       this.motionY *= -0.8999999761581421D;
/*     */     }
/*     */     
/* 169 */     this.xpColor++;
/* 170 */     this.xpOrbAge++;
/*     */     
/* 172 */     if (this.xpOrbAge >= 6000)
/*     */     {
/* 174 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleWaterMovement() {
/* 183 */     return this.world.handleMaterialAcceleration(getEntityBoundingBox(), Material.WATER, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dealFireDamage(int amount) {
/* 191 */     attackEntityFrom(DamageSource.inFire, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 199 */     if (isEntityInvulnerable(source))
/*     */     {
/* 201 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 205 */     setBeenAttacked();
/* 206 */     this.xpOrbHealth = (int)(this.xpOrbHealth - amount);
/*     */     
/* 208 */     if (this.xpOrbHealth <= 0)
/*     */     {
/* 210 */       setDead();
/*     */     }
/*     */     
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 222 */     compound.setShort("Health", (short)this.xpOrbHealth);
/* 223 */     compound.setShort("Age", (short)this.xpOrbAge);
/* 224 */     compound.setShort("Value", (short)this.xpValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 232 */     this.xpOrbHealth = compound.getShort("Health");
/* 233 */     this.xpOrbAge = compound.getShort("Age");
/* 234 */     this.xpValue = compound.getShort("Value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 242 */     if (!this.world.isRemote)
/*     */     {
/* 244 */       if (this.delayBeforeCanPickup == 0 && entityIn.xpCooldown == 0) {
/*     */         
/* 246 */         entityIn.xpCooldown = 2;
/* 247 */         entityIn.onItemPickup(this, 1);
/* 248 */         ItemStack itemstack = EnchantmentHelper.getEnchantedItem(Enchantments.MENDING, (EntityLivingBase)entityIn);
/*     */         
/* 250 */         if (!itemstack.func_190926_b() && itemstack.isItemDamaged()) {
/*     */           
/* 252 */           int i = Math.min(xpToDurability(this.xpValue), itemstack.getItemDamage());
/* 253 */           this.xpValue -= durabilityToXp(i);
/* 254 */           itemstack.setItemDamage(itemstack.getItemDamage() - i);
/*     */         } 
/*     */         
/* 257 */         if (this.xpValue > 0)
/*     */         {
/* 259 */           entityIn.addExperience(this.xpValue);
/*     */         }
/*     */         
/* 262 */         setDead();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int durabilityToXp(int durability) {
/* 269 */     return durability / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   private int xpToDurability(int xp) {
/* 274 */     return xp * 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXpValue() {
/* 282 */     return this.xpValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureByXP() {
/* 291 */     if (this.xpValue >= 2477)
/*     */     {
/* 293 */       return 10;
/*     */     }
/* 295 */     if (this.xpValue >= 1237)
/*     */     {
/* 297 */       return 9;
/*     */     }
/* 299 */     if (this.xpValue >= 617)
/*     */     {
/* 301 */       return 8;
/*     */     }
/* 303 */     if (this.xpValue >= 307)
/*     */     {
/* 305 */       return 7;
/*     */     }
/* 307 */     if (this.xpValue >= 149)
/*     */     {
/* 309 */       return 6;
/*     */     }
/* 311 */     if (this.xpValue >= 73)
/*     */     {
/* 313 */       return 5;
/*     */     }
/* 315 */     if (this.xpValue >= 37)
/*     */     {
/* 317 */       return 4;
/*     */     }
/* 319 */     if (this.xpValue >= 17)
/*     */     {
/* 321 */       return 3;
/*     */     }
/* 323 */     if (this.xpValue >= 7)
/*     */     {
/* 325 */       return 2;
/*     */     }
/*     */ 
/*     */     
/* 329 */     return (this.xpValue >= 3) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getXPSplit(int expValue) {
/* 338 */     if (expValue >= 2477)
/*     */     {
/* 340 */       return 2477;
/*     */     }
/* 342 */     if (expValue >= 1237)
/*     */     {
/* 344 */       return 1237;
/*     */     }
/* 346 */     if (expValue >= 617)
/*     */     {
/* 348 */       return 617;
/*     */     }
/* 350 */     if (expValue >= 307)
/*     */     {
/* 352 */       return 307;
/*     */     }
/* 354 */     if (expValue >= 149)
/*     */     {
/* 356 */       return 149;
/*     */     }
/* 358 */     if (expValue >= 73)
/*     */     {
/* 360 */       return 73;
/*     */     }
/* 362 */     if (expValue >= 37)
/*     */     {
/* 364 */       return 37;
/*     */     }
/* 366 */     if (expValue >= 17)
/*     */     {
/* 368 */       return 17;
/*     */     }
/* 370 */     if (expValue >= 7)
/*     */     {
/* 372 */       return 7;
/*     */     }
/*     */ 
/*     */     
/* 376 */     return (expValue >= 3) ? 3 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 385 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
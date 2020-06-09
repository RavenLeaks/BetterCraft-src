/*     */ package net.minecraft.entity.passive;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILlamaFollowCaravan;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityLlamaSpit;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityLlama extends AbstractChestHorse implements IRangedAttackMob {
/*  52 */   private static final DataParameter<Integer> field_190720_bG = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
/*  53 */   private static final DataParameter<Integer> field_190721_bH = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
/*  54 */   private static final DataParameter<Integer> field_190722_bI = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
/*     */   
/*     */   private boolean field_190723_bJ;
/*     */   @Nullable
/*     */   private EntityLlama field_190724_bK;
/*     */   @Nullable
/*     */   private EntityLlama field_190725_bL;
/*     */   
/*     */   public EntityLlama(World p_i47297_1_) {
/*  63 */     super(p_i47297_1_);
/*  64 */     setSize(0.9F, 1.87F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190706_p(int p_190706_1_) {
/*  69 */     this.dataManager.set(field_190720_bG, Integer.valueOf(Math.max(1, Math.min(5, p_190706_1_))));
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190705_dT() {
/*  74 */     int i = (this.rand.nextFloat() < 0.04F) ? 5 : 3;
/*  75 */     func_190706_p(1 + this.rand.nextInt(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190707_dL() {
/*  80 */     return ((Integer)this.dataManager.get(field_190720_bG)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  88 */     super.writeEntityToNBT(compound);
/*  89 */     compound.setInteger("Variant", func_190719_dM());
/*  90 */     compound.setInteger("Strength", func_190707_dL());
/*     */     
/*  92 */     if (!this.horseChest.getStackInSlot(1).func_190926_b())
/*     */     {
/*  94 */       compound.setTag("DecorItem", (NBTBase)this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 103 */     func_190706_p(compound.getInteger("Strength"));
/* 104 */     super.readEntityFromNBT(compound);
/* 105 */     func_190710_o(compound.getInteger("Variant"));
/*     */     
/* 107 */     if (compound.hasKey("DecorItem", 10))
/*     */     {
/* 109 */       this.horseChest.setInventorySlotContents(1, new ItemStack(compound.getCompoundTag("DecorItem")));
/*     */     }
/*     */     
/* 112 */     updateHorseSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/* 117 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/* 118 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIRunAroundLikeCrazy(this, 1.2D));
/* 119 */     this.tasks.addTask(2, (EntityAIBase)new EntityAILlamaFollowCaravan(this, 2.0999999046325684D));
/* 120 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRanged(this, 1.25D, 40, 20.0F));
/* 121 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.2D));
/* 122 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIMate(this, 1.0D));
/* 123 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent(this, 1.0D));
/* 124 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.7D));
/* 125 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/* 126 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/* 127 */     this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByTarget(this));
/* 128 */     this.targetTasks.addTask(2, (EntityAIBase)new AIDefendTarget(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 133 */     super.applyEntityAttributes();
/* 134 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 139 */     super.entityInit();
/* 140 */     this.dataManager.register(field_190720_bG, Integer.valueOf(0));
/* 141 */     this.dataManager.register(field_190721_bH, Integer.valueOf(-1));
/* 142 */     this.dataManager.register(field_190722_bI, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190719_dM() {
/* 147 */     return MathHelper.clamp(((Integer)this.dataManager.get(field_190722_bI)).intValue(), 0, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190710_o(int p_190710_1_) {
/* 152 */     this.dataManager.set(field_190722_bI, Integer.valueOf(p_190710_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_190686_di() {
/* 157 */     return func_190695_dh() ? (2 + 3 * func_190696_dl()) : super.func_190686_di();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePassenger(Entity passenger) {
/* 162 */     if (isPassenger(passenger)) {
/*     */       
/* 164 */       float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
/* 165 */       float f1 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
/* 166 */       float f2 = 0.3F;
/* 167 */       passenger.setPosition(this.posX + (0.3F * f1), this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ - (0.3F * f));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/* 176 */     return this.height * 0.67D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeSteered() {
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_190678_b(EntityPlayer p_190678_1_, ItemStack p_190678_2_) {
/* 190 */     int i = 0;
/* 191 */     int j = 0;
/* 192 */     float f = 0.0F;
/* 193 */     boolean flag = false;
/* 194 */     Item item = p_190678_2_.getItem();
/*     */     
/* 196 */     if (item == Items.WHEAT) {
/*     */       
/* 198 */       i = 10;
/* 199 */       j = 3;
/* 200 */       f = 2.0F;
/*     */     }
/* 202 */     else if (item == Item.getItemFromBlock(Blocks.HAY_BLOCK)) {
/*     */       
/* 204 */       i = 90;
/* 205 */       j = 6;
/* 206 */       f = 10.0F;
/*     */       
/* 208 */       if (isTame() && getGrowingAge() == 0) {
/*     */         
/* 210 */         flag = true;
/* 211 */         setInLove(p_190678_1_);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if (getHealth() < getMaxHealth() && f > 0.0F) {
/*     */       
/* 217 */       heal(f);
/* 218 */       flag = true;
/*     */     } 
/*     */     
/* 221 */     if (isChild() && i > 0) {
/*     */       
/* 223 */       this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       
/* 225 */       if (!this.world.isRemote)
/*     */       {
/* 227 */         addGrowth(i);
/*     */       }
/*     */       
/* 230 */       flag = true;
/*     */     } 
/*     */     
/* 233 */     if (j > 0 && (flag || !isTame()) && getTemper() < func_190676_dC()) {
/*     */       
/* 235 */       flag = true;
/*     */       
/* 237 */       if (!this.world.isRemote)
/*     */       {
/* 239 */         increaseTemper(j);
/*     */       }
/*     */     } 
/*     */     
/* 243 */     if (flag && !isSilent())
/*     */     {
/* 245 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.field_191253_dD, getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*     */     }
/*     */     
/* 248 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isMovementBlocked() {
/* 256 */     return !(getHealth() > 0.0F && !isEatingHaystack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/*     */     int i;
/* 267 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 268 */     func_190705_dT();
/*     */ 
/*     */     
/* 271 */     if (livingdata instanceof GroupData) {
/*     */       
/* 273 */       i = ((GroupData)livingdata).field_190886_a;
/*     */     }
/*     */     else {
/*     */       
/* 277 */       i = this.rand.nextInt(4);
/* 278 */       livingdata = new GroupData(i, null);
/*     */     } 
/*     */     
/* 281 */     func_190710_o(i);
/* 282 */     return livingdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190717_dN() {
/* 287 */     return (func_190704_dO() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAngrySound() {
/* 292 */     return SoundEvents.field_191250_dA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 297 */     return SoundEvents.field_191260_dz;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 302 */     return SoundEvents.field_191254_dE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 307 */     return SoundEvents.field_191252_dC;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 312 */     playSound(SoundEvents.field_191256_dG, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190697_dk() {
/* 317 */     playSound(SoundEvents.field_191251_dB, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190687_dF() {
/* 322 */     SoundEvent soundevent = getAngrySound();
/*     */     
/* 324 */     if (soundevent != null)
/*     */     {
/* 326 */       playSound(soundevent, getSoundVolume(), getSoundPitch());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 333 */     return LootTableList.field_191187_aw;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190696_dl() {
/* 338 */     return func_190707_dL();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190677_dK() {
/* 343 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190682_f(ItemStack p_190682_1_) {
/* 348 */     return (p_190682_1_.getItem() == Item.getItemFromBlock(Blocks.CARPET));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190685_dA() {
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInventoryChanged(IInventory invBasic) {
/* 361 */     EnumDyeColor enumdyecolor = func_190704_dO();
/* 362 */     super.onInventoryChanged(invBasic);
/* 363 */     EnumDyeColor enumdyecolor1 = func_190704_dO();
/*     */     
/* 365 */     if (this.ticksExisted > 20 && enumdyecolor1 != null && enumdyecolor1 != enumdyecolor)
/*     */     {
/* 367 */       playSound(SoundEvents.field_191257_dH, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateHorseSlots() {
/* 376 */     if (!this.world.isRemote) {
/*     */       
/* 378 */       super.updateHorseSlots();
/* 379 */       func_190702_g(this.horseChest.getStackInSlot(1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190711_a(@Nullable EnumDyeColor p_190711_1_) {
/* 385 */     this.dataManager.set(field_190721_bH, Integer.valueOf((p_190711_1_ == null) ? -1 : p_190711_1_.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190702_g(ItemStack p_190702_1_) {
/* 390 */     if (func_190682_f(p_190702_1_)) {
/*     */       
/* 392 */       func_190711_a(EnumDyeColor.byMetadata(p_190702_1_.getMetadata()));
/*     */     }
/*     */     else {
/*     */       
/* 396 */       func_190711_a((EnumDyeColor)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EnumDyeColor func_190704_dO() {
/* 403 */     int i = ((Integer)this.dataManager.get(field_190721_bH)).intValue();
/* 404 */     return (i == -1) ? null : EnumDyeColor.byMetadata(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190676_dC() {
/* 409 */     return 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 417 */     return (otherAnimal != this && otherAnimal instanceof EntityLlama && canMate() && ((EntityLlama)otherAnimal).canMate());
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLlama createChild(EntityAgeable ageable) {
/* 422 */     EntityLlama entityllama = new EntityLlama(this.world);
/* 423 */     func_190681_a(ageable, entityllama);
/* 424 */     EntityLlama entityllama1 = (EntityLlama)ageable;
/* 425 */     int i = this.rand.nextInt(Math.max(func_190707_dL(), entityllama1.func_190707_dL())) + 1;
/*     */     
/* 427 */     if (this.rand.nextFloat() < 0.03F)
/*     */     {
/* 429 */       i++;
/*     */     }
/*     */     
/* 432 */     entityllama.func_190706_p(i);
/* 433 */     entityllama.func_190710_o(this.rand.nextBoolean() ? func_190719_dM() : entityllama1.func_190719_dM());
/* 434 */     return entityllama;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190713_e(EntityLivingBase p_190713_1_) {
/* 439 */     EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.world, this);
/* 440 */     double d0 = p_190713_1_.posX - this.posX;
/* 441 */     double d1 = (p_190713_1_.getEntityBoundingBox()).minY + (p_190713_1_.height / 3.0F) - entityllamaspit.posY;
/* 442 */     double d2 = p_190713_1_.posZ - this.posZ;
/* 443 */     float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
/* 444 */     entityllamaspit.setThrowableHeading(d0, d1 + f, d2, 1.5F, 10.0F);
/* 445 */     this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.field_191255_dF, getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 446 */     this.world.spawnEntityInWorld((Entity)entityllamaspit);
/* 447 */     this.field_190723_bJ = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190714_x(boolean p_190714_1_) {
/* 452 */     this.field_190723_bJ = p_190714_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 457 */     int i = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
/*     */     
/* 459 */     if (i > 0) {
/*     */       
/* 461 */       if (distance >= 6.0F) {
/*     */         
/* 463 */         attackEntityFrom(DamageSource.fall, i);
/*     */         
/* 465 */         if (isBeingRidden())
/*     */         {
/* 467 */           for (Entity entity : getRecursivePassengers())
/*     */           {
/* 469 */             entity.attackEntityFrom(DamageSource.fall, i);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 474 */       IBlockState iblockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ));
/* 475 */       Block block = iblockstate.getBlock();
/*     */       
/* 477 */       if (iblockstate.getMaterial() != Material.AIR && !isSilent()) {
/*     */         
/* 479 */         SoundType soundtype = block.getSoundType();
/* 480 */         this.world.playSound(null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190709_dP() {
/* 487 */     if (this.field_190724_bK != null)
/*     */     {
/* 489 */       this.field_190724_bK.field_190725_bL = null;
/*     */     }
/*     */     
/* 492 */     this.field_190724_bK = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190715_a(EntityLlama p_190715_1_) {
/* 497 */     this.field_190724_bK = p_190715_1_;
/* 498 */     this.field_190724_bK.field_190725_bL = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190712_dQ() {
/* 503 */     return (this.field_190725_bL != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190718_dR() {
/* 508 */     return (this.field_190724_bK != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLlama func_190716_dS() {
/* 514 */     return this.field_190724_bK;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double func_190634_dg() {
/* 519 */     return 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190679_dD() {
/* 524 */     if (!func_190718_dR() && isChild())
/*     */     {
/* 526 */       super.func_190679_dD();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190684_dE() {
/* 532 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 542 */     func_190713_e(target);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {}
/*     */ 
/*     */   
/*     */   static class AIDefendTarget
/*     */     extends EntityAINearestAttackableTarget<EntityWolf>
/*     */   {
/*     */     public AIDefendTarget(EntityLlama p_i47285_1_) {
/* 553 */       super((EntityCreature)p_i47285_1_, EntityWolf.class, 16, false, true, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 558 */       if (super.shouldExecute() && this.targetEntity != null && !((EntityWolf)this.targetEntity).isTamed())
/*     */       {
/* 560 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 564 */       this.taskOwner.setAttackTarget(null);
/* 565 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected double getTargetDistance() {
/* 571 */       return super.getTargetDistance() * 0.25D;
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIHurtByTarget
/*     */     extends EntityAIHurtByTarget
/*     */   {
/*     */     public AIHurtByTarget(EntityLlama p_i47282_1_) {
/* 579 */       super((EntityCreature)p_i47282_1_, false, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 584 */       if (this.taskOwner instanceof EntityLlama) {
/*     */         
/* 586 */         EntityLlama entityllama = (EntityLlama)this.taskOwner;
/*     */         
/* 588 */         if (entityllama.field_190723_bJ) {
/*     */           
/* 590 */           entityllama.func_190714_x(false);
/* 591 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/* 595 */       return super.continueExecuting();
/*     */     }
/*     */   }
/*     */   
/*     */   static class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public int field_190886_a;
/*     */     
/*     */     private GroupData(int p_i47283_1_) {
/* 605 */       this.field_190886_a = p_i47283_1_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityLlama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
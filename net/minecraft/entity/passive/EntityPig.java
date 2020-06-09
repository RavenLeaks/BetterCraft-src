/*     */ package net.minecraft.entity.passive;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal {
/*  43 */   public static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityPig.class, DataSerializers.BOOLEAN);
/*  44 */   private static final DataParameter<Integer> field_191520_bx = EntityDataManager.createKey(EntityPig.class, DataSerializers.VARINT);
/*  45 */   private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet((Object[])new Item[] { Items.CARROT, Items.POTATO, Items.BEETROOT });
/*     */   
/*     */   private boolean boosting;
/*     */   private int boostTime;
/*     */   private int totalBoostTime;
/*     */   
/*     */   public EntityPig(World worldIn) {
/*  52 */     super(worldIn);
/*  53 */     setSize(0.9F, 0.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  58 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  59 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  60 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  61 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.CARROT_ON_A_STICK, false));
/*  62 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, false, TEMPTATION_ITEMS));
/*  63 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  64 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/*  65 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  66 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  71 */     super.applyEntityAttributes();
/*  72 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getControllingPassenger() {
/*  84 */     return getPassengers().isEmpty() ? null : getPassengers().get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeSteered() {
/*  93 */     Entity entity = getControllingPassenger();
/*     */     
/*  95 */     if (!(entity instanceof EntityPlayer))
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 101 */     EntityPlayer entityplayer = (EntityPlayer)entity;
/* 102 */     return !(entityplayer.getHeldItemMainhand().getItem() != Items.CARROT_ON_A_STICK && entityplayer.getHeldItemOffhand().getItem() != Items.CARROT_ON_A_STICK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 108 */     if (field_191520_bx.equals(key) && this.world.isRemote) {
/*     */       
/* 110 */       this.boosting = true;
/* 111 */       this.boostTime = 0;
/* 112 */       this.totalBoostTime = ((Integer)this.dataManager.get(field_191520_bx)).intValue();
/*     */     } 
/*     */     
/* 115 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 120 */     super.entityInit();
/* 121 */     this.dataManager.register(SADDLED, Boolean.valueOf(false));
/* 122 */     this.dataManager.register(field_191520_bx, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesPig(DataFixer fixer) {
/* 127 */     EntityLiving.registerFixesMob(fixer, EntityPig.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 135 */     super.writeEntityToNBT(compound);
/* 136 */     compound.setBoolean("Saddle", getSaddled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 144 */     super.readEntityFromNBT(compound);
/* 145 */     setSaddled(compound.getBoolean("Saddle"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 150 */     return SoundEvents.ENTITY_PIG_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 155 */     return SoundEvents.ENTITY_PIG_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 160 */     return SoundEvents.ENTITY_PIG_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 165 */     playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 170 */     if (!super.processInteract(player, hand)) {
/*     */       
/* 172 */       ItemStack itemstack = player.getHeldItem(hand);
/*     */       
/* 174 */       if (itemstack.getItem() == Items.NAME_TAG) {
/*     */         
/* 176 */         itemstack.interactWithEntity(player, (EntityLivingBase)this, hand);
/* 177 */         return true;
/*     */       } 
/* 179 */       if (getSaddled() && !isBeingRidden()) {
/*     */         
/* 181 */         if (!this.world.isRemote)
/*     */         {
/* 183 */           player.startRiding((Entity)this);
/*     */         }
/*     */         
/* 186 */         return true;
/*     */       } 
/* 188 */       if (itemstack.getItem() == Items.SADDLE) {
/*     */         
/* 190 */         itemstack.interactWithEntity(player, (EntityLivingBase)this, hand);
/* 191 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 195 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 209 */     super.onDeath(cause);
/*     */     
/* 211 */     if (!this.world.isRemote)
/*     */     {
/* 213 */       if (getSaddled())
/*     */       {
/* 215 */         dropItem(Items.SADDLE, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 223 */     return LootTableList.ENTITIES_PIG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSaddled() {
/* 231 */     return ((Boolean)this.dataManager.get(SADDLED)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaddled(boolean saddled) {
/* 239 */     if (saddled) {
/*     */       
/* 241 */       this.dataManager.set(SADDLED, Boolean.valueOf(true));
/*     */     }
/*     */     else {
/*     */       
/* 245 */       this.dataManager.set(SADDLED, Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 254 */     if (!this.world.isRemote && !this.isDead) {
/*     */       
/* 256 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
/* 257 */       entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
/* 258 */       entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 259 */       entitypigzombie.setNoAI(isAIDisabled());
/*     */       
/* 261 */       if (hasCustomName()) {
/*     */         
/* 263 */         entitypigzombie.setCustomNameTag(getCustomNameTag());
/* 264 */         entitypigzombie.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 267 */       this.world.spawnEntityInWorld((Entity)entitypigzombie);
/* 268 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 274 */     Entity entity = getPassengers().isEmpty() ? null : getPassengers().get(0);
/*     */     
/* 276 */     if (isBeingRidden() && canBeSteered()) {
/*     */       
/* 278 */       this.rotationYaw = entity.rotationYaw;
/* 279 */       this.prevRotationYaw = this.rotationYaw;
/* 280 */       this.rotationPitch = entity.rotationPitch * 0.5F;
/* 281 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 282 */       this.renderYawOffset = this.rotationYaw;
/* 283 */       this.rotationYawHead = this.rotationYaw;
/* 284 */       this.stepHeight = 1.0F;
/* 285 */       this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
/*     */       
/* 287 */       if (this.boosting && this.boostTime++ > this.totalBoostTime)
/*     */       {
/* 289 */         this.boosting = false;
/*     */       }
/*     */       
/* 292 */       if (canPassengerSteer()) {
/*     */         
/* 294 */         float f = (float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.225F;
/*     */         
/* 296 */         if (this.boosting)
/*     */         {
/* 298 */           f += f * 1.15F * MathHelper.sin(this.boostTime / this.totalBoostTime * 3.1415927F);
/*     */         }
/*     */         
/* 301 */         setAIMoveSpeed(f);
/* 302 */         super.func_191986_a(0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       else {
/*     */         
/* 306 */         this.motionX = 0.0D;
/* 307 */         this.motionY = 0.0D;
/* 308 */         this.motionZ = 0.0D;
/*     */       } 
/*     */       
/* 311 */       this.prevLimbSwingAmount = this.limbSwingAmount;
/* 312 */       double d1 = this.posX - this.prevPosX;
/* 313 */       double d0 = this.posZ - this.prevPosZ;
/* 314 */       float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
/*     */       
/* 316 */       if (f1 > 1.0F)
/*     */       {
/* 318 */         f1 = 1.0F;
/*     */       }
/*     */       
/* 321 */       this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
/* 322 */       this.limbSwing += this.limbSwingAmount;
/*     */     }
/*     */     else {
/*     */       
/* 326 */       this.stepHeight = 0.5F;
/* 327 */       this.jumpMovementFactor = 0.02F;
/* 328 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean boost() {
/* 334 */     if (this.boosting)
/*     */     {
/* 336 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 340 */     this.boosting = true;
/* 341 */     this.boostTime = 0;
/* 342 */     this.totalBoostTime = getRNG().nextInt(841) + 140;
/* 343 */     getDataManager().set(field_191520_bx, Integer.valueOf(this.totalBoostTime));
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPig createChild(EntityAgeable ageable) {
/* 350 */     return new EntityPig(this.world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 359 */     return TEMPTATION_ITEMS.contains(stack.getItem());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
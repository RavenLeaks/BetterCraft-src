/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Calendar;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIAttackRangedBow;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFleeSun;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIRestrictSun;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class AbstractSkeleton extends EntityMob implements IRangedAttackMob {
/*  46 */   private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.createKey(AbstractSkeleton.class, DataSerializers.BOOLEAN);
/*  47 */   private final EntityAIAttackRangedBow<AbstractSkeleton> aiArrowAttack = new EntityAIAttackRangedBow(this, 1.0D, 20, 15.0F);
/*  48 */   private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false)
/*     */     {
/*     */       public void resetTask()
/*     */       {
/*  52 */         super.resetTask();
/*  53 */         AbstractSkeleton.this.setSwingingArms(false);
/*     */       }
/*     */       
/*     */       public void startExecuting() {
/*  57 */         super.startExecuting();
/*  58 */         AbstractSkeleton.this.setSwingingArms(true);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public AbstractSkeleton(World p_i47289_1_) {
/*  64 */     super(p_i47289_1_);
/*  65 */     setSize(0.6F, 1.99F);
/*  66 */     setCombatTask();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  71 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  72 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIRestrictSun(this));
/*  73 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun(this, 1.0D));
/*  74 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
/*  75 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D));
/*  76 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  77 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  78 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  79 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  80 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  85 */     super.applyEntityAttributes();
/*  86 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  91 */     super.entityInit();
/*  92 */     this.dataManager.register(SWINGING_ARMS, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  97 */     playSound(func_190727_o(), 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract SoundEvent func_190727_o();
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 107 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 116 */     if (this.world.isDaytime() && !this.world.isRemote) {
/*     */       
/* 118 */       float f = getBrightness();
/* 119 */       BlockPos blockpos = (getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat) ? (new BlockPos(this.posX, Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, Math.round(this.posY), this.posZ);
/*     */       
/* 121 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(blockpos)) {
/*     */         
/* 123 */         boolean flag = true;
/* 124 */         ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
/*     */         
/* 126 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 128 */           if (itemstack.isItemStackDamageable()) {
/*     */             
/* 130 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 132 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/*     */               
/* 134 */               renderBrokenItemStack(itemstack);
/* 135 */               setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
/*     */             } 
/*     */           } 
/*     */           
/* 139 */           flag = false;
/*     */         } 
/*     */         
/* 142 */         if (flag)
/*     */         {
/* 144 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRidden() {
/* 157 */     super.updateRidden();
/*     */     
/* 159 */     if (getRidingEntity() instanceof EntityCreature) {
/*     */       
/* 161 */       EntityCreature entitycreature = (EntityCreature)getRidingEntity();
/* 162 */       this.renderYawOffset = entitycreature.renderYawOffset;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 171 */     super.setEquipmentBasedOnDifficulty(difficulty);
/* 172 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 183 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 184 */     setEquipmentBasedOnDifficulty(difficulty);
/* 185 */     setEnchantmentBasedOnDifficulty(difficulty);
/* 186 */     setCombatTask();
/* 187 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty()));
/*     */     
/* 189 */     if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b()) {
/*     */       
/* 191 */       Calendar calendar = this.world.getCurrentDate();
/*     */       
/* 193 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 195 */         setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
/* 196 */         this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCombatTask() {
/* 208 */     if (this.world != null && !this.world.isRemote) {
/*     */       
/* 210 */       this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
/* 211 */       this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
/* 212 */       ItemStack itemstack = getHeldItemMainhand();
/*     */       
/* 214 */       if (itemstack.getItem() == Items.BOW) {
/*     */         
/* 216 */         int i = 20;
/*     */         
/* 218 */         if (this.world.getDifficulty() != EnumDifficulty.HARD)
/*     */         {
/* 220 */           i = 40;
/*     */         }
/*     */         
/* 223 */         this.aiArrowAttack.setAttackCooldown(i);
/* 224 */         this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
/*     */       }
/*     */       else {
/*     */         
/* 228 */         this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 240 */     EntityArrow entityarrow = func_190726_a(distanceFactor);
/* 241 */     double d0 = target.posX - this.posX;
/* 242 */     double d1 = (target.getEntityBoundingBox()).minY + (target.height / 3.0F) - entityarrow.posY;
/* 243 */     double d2 = target.posZ - this.posZ;
/* 244 */     double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 245 */     entityarrow.setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (14 - this.world.getDifficulty().getDifficultyId() * 4));
/* 246 */     playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 247 */     this.world.spawnEntityInWorld((Entity)entityarrow);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityArrow func_190726_a(float p_190726_1_) {
/* 252 */     EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
/* 253 */     entitytippedarrow.func_190547_a((EntityLivingBase)this, p_190726_1_);
/* 254 */     return (EntityArrow)entitytippedarrow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 262 */     super.readEntityFromNBT(compound);
/* 263 */     setCombatTask();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
/* 268 */     super.setItemStackToSlot(slotIn, stack);
/*     */     
/* 270 */     if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
/*     */     {
/* 272 */       setCombatTask();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 278 */     return 1.74F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 286 */     return -0.6D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSwingingArms() {
/* 291 */     return ((Boolean)this.dataManager.get(SWINGING_ARMS)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {
/* 296 */     this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\AbstractSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
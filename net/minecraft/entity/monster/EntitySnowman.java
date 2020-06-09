/*     */ package net.minecraft.entity.monster;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackRanged;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
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
/*     */ public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
/*  36 */   private static final DataParameter<Byte> PUMPKIN_EQUIPPED = EntityDataManager.createKey(EntitySnowman.class, DataSerializers.BYTE);
/*     */ 
/*     */   
/*     */   public EntitySnowman(World worldIn) {
/*  40 */     super(worldIn);
/*  41 */     setSize(0.7F, 1.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSnowman(DataFixer fixer) {
/*  46 */     EntityLiving.registerFixesMob(fixer, EntitySnowman.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  51 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
/*  52 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D, 1.0000001E-5F));
/*  53 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  54 */     this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  55 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.MOB_SELECTOR));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  60 */     super.applyEntityAttributes();
/*  61 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
/*  62 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  67 */     super.entityInit();
/*  68 */     this.dataManager.register(PUMPKIN_EQUIPPED, Byte.valueOf((byte)16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  76 */     super.writeEntityToNBT(compound);
/*  77 */     compound.setBoolean("Pumpkin", isPumpkinEquipped());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  85 */     super.readEntityFromNBT(compound);
/*     */     
/*  87 */     if (compound.hasKey("Pumpkin"))
/*     */     {
/*  89 */       setPumpkinEquipped(compound.getBoolean("Pumpkin"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  99 */     super.onLivingUpdate();
/*     */     
/* 101 */     if (!this.world.isRemote) {
/*     */       
/* 103 */       int i = MathHelper.floor(this.posX);
/* 104 */       int j = MathHelper.floor(this.posY);
/* 105 */       int k = MathHelper.floor(this.posZ);
/*     */       
/* 107 */       if (isWet())
/*     */       {
/* 109 */         attackEntityFrom(DamageSource.drown, 1.0F);
/*     */       }
/*     */       
/* 112 */       if (this.world.getBiome(new BlockPos(i, 0, k)).getFloatTemperature(new BlockPos(i, j, k)) > 1.0F)
/*     */       {
/* 114 */         attackEntityFrom(DamageSource.onFire, 1.0F);
/*     */       }
/*     */       
/* 117 */       if (!this.world.getGameRules().getBoolean("mobGriefing")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 122 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 124 */         i = MathHelper.floor(this.posX + ((l % 2 * 2 - 1) * 0.25F));
/* 125 */         j = MathHelper.floor(this.posY);
/* 126 */         k = MathHelper.floor(this.posZ + ((l / 2 % 2 * 2 - 1) * 0.25F));
/* 127 */         BlockPos blockpos = new BlockPos(i, j, k);
/*     */         
/* 129 */         if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && this.world.getBiome(blockpos).getFloatTemperature(blockpos) < 0.8F && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, blockpos))
/*     */         {
/* 131 */           this.world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 140 */     return LootTableList.ENTITIES_SNOWMAN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 150 */     EntitySnowball entitysnowball = new EntitySnowball(this.world, (EntityLivingBase)this);
/* 151 */     double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
/* 152 */     double d1 = target.posX - this.posX;
/* 153 */     double d2 = d0 - entitysnowball.posY;
/* 154 */     double d3 = target.posZ - this.posZ;
/* 155 */     float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
/* 156 */     entitysnowball.setThrowableHeading(d1, d2 + f, d3, 1.6F, 12.0F);
/* 157 */     playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 158 */     this.world.spawnEntityInWorld((Entity)entitysnowball);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 163 */     return 1.7F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 168 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 170 */     if (itemstack.getItem() == Items.SHEARS && isPumpkinEquipped() && !this.world.isRemote) {
/*     */       
/* 172 */       setPumpkinEquipped(false);
/* 173 */       itemstack.damageItem(1, (EntityLivingBase)player);
/*     */     } 
/*     */     
/* 176 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPumpkinEquipped() {
/* 181 */     return ((((Byte)this.dataManager.get(PUMPKIN_EQUIPPED)).byteValue() & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPumpkinEquipped(boolean pumpkinEquipped) {
/* 186 */     byte b0 = ((Byte)this.dataManager.get(PUMPKIN_EQUIPPED)).byteValue();
/*     */     
/* 188 */     if (pumpkinEquipped) {
/*     */       
/* 190 */       this.dataManager.set(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     }
/*     */     else {
/*     */       
/* 194 */       this.dataManager.set(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 201 */     return SoundEvents.ENTITY_SNOWMAN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 207 */     return SoundEvents.ENTITY_SNOWMAN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 213 */     return SoundEvents.ENTITY_SNOWMAN_DEATH;
/*     */   }
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySnowman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.entity.passive;
/*     */ import com.google.common.collect.Sets;
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
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityChicken extends EntityAnimal {
/*  38 */   private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet((Object[])new Item[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS });
/*     */   public float wingRotation;
/*     */   public float destPos;
/*     */   public float oFlapSpeed;
/*     */   public float oFlap;
/*  43 */   public float wingRotDelta = 1.0F;
/*     */   
/*     */   public int timeUntilNextEgg;
/*     */   
/*     */   public boolean chickenJockey;
/*     */ 
/*     */   
/*     */   public EntityChicken(World worldIn) {
/*  51 */     super(worldIn);
/*  52 */     setSize(0.4F, 0.7F);
/*  53 */     this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*  54 */     setPathPriority(PathNodeType.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  59 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  60 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4D));
/*  61 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  62 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, false, TEMPTATION_ITEMS));
/*  63 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  64 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/*  65 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  66 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  71 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  76 */     super.applyEntityAttributes();
/*  77 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
/*  78 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  87 */     super.onLivingUpdate();
/*  88 */     this.oFlap = this.wingRotation;
/*  89 */     this.oFlapSpeed = this.destPos;
/*  90 */     this.destPos = (float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D);
/*  91 */     this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
/*     */     
/*  93 */     if (!this.onGround && this.wingRotDelta < 1.0F)
/*     */     {
/*  95 */       this.wingRotDelta = 1.0F;
/*     */     }
/*     */     
/*  98 */     this.wingRotDelta = (float)(this.wingRotDelta * 0.9D);
/*     */     
/* 100 */     if (!this.onGround && this.motionY < 0.0D)
/*     */     {
/* 102 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/* 105 */     this.wingRotation += this.wingRotDelta * 2.0F;
/*     */     
/* 107 */     if (!this.world.isRemote && !isChild() && !isChickenJockey() && --this.timeUntilNextEgg <= 0) {
/*     */       
/* 109 */       playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 110 */       dropItem(Items.EGG, 1);
/* 111 */       this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 121 */     return SoundEvents.ENTITY_CHICKEN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 126 */     return SoundEvents.ENTITY_CHICKEN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 131 */     return SoundEvents.ENTITY_CHICKEN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 136 */     playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 142 */     return LootTableList.ENTITIES_CHICKEN;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityChicken createChild(EntityAgeable ageable) {
/* 147 */     return new EntityChicken(this.world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 156 */     return TEMPTATION_ITEMS.contains(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 164 */     return isChickenJockey() ? 10 : super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesChicken(DataFixer fixer) {
/* 169 */     EntityLiving.registerFixesMob(fixer, EntityChicken.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 177 */     super.readEntityFromNBT(compound);
/* 178 */     this.chickenJockey = compound.getBoolean("IsChickenJockey");
/*     */     
/* 180 */     if (compound.hasKey("EggLayTime"))
/*     */     {
/* 182 */       this.timeUntilNextEgg = compound.getInteger("EggLayTime");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 191 */     super.writeEntityToNBT(compound);
/* 192 */     compound.setBoolean("IsChickenJockey", this.chickenJockey);
/* 193 */     compound.setInteger("EggLayTime", this.timeUntilNextEgg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 201 */     return (isChickenJockey() && !isBeingRidden());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePassenger(Entity passenger) {
/* 206 */     super.updatePassenger(passenger);
/* 207 */     float f = MathHelper.sin(this.renderYawOffset * 0.017453292F);
/* 208 */     float f1 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
/* 209 */     float f2 = 0.1F;
/* 210 */     float f3 = 0.0F;
/* 211 */     passenger.setPosition(this.posX + (0.1F * f), this.posY + (this.height * 0.5F) + passenger.getYOffset() + 0.0D, this.posZ - (0.1F * f1));
/*     */     
/* 213 */     if (passenger instanceof EntityLivingBase)
/*     */     {
/* 215 */       ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChickenJockey() {
/* 224 */     return this.chickenJockey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChickenJockey(boolean jockey) {
/* 232 */     this.chickenJockey = jockey;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
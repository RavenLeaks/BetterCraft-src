/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityBat
/*     */   extends EntityAmbientCreature
/*     */ {
/*  26 */   public static final DataParameter<Byte> HANGING = EntityDataManager.createKey(EntityBat.class, DataSerializers.BYTE);
/*     */ 
/*     */   
/*     */   private BlockPos spawnPosition;
/*     */ 
/*     */   
/*     */   public EntityBat(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     setSize(0.5F, 0.9F);
/*  35 */     setIsBatHanging(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  40 */     super.entityInit();
/*  41 */     this.dataManager.register(HANGING, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  49 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundPitch() {
/*  57 */     return super.getSoundPitch() * 0.95F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SoundEvent getAmbientSound() {
/*  63 */     return (getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : SoundEvents.ENTITY_BAT_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  68 */     return SoundEvents.ENTITY_BAT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  73 */     return SoundEvents.ENTITY_BAT_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithNearbyEntities() {}
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  94 */     super.applyEntityAttributes();
/*  95 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsBatHanging() {
/* 100 */     return ((((Byte)this.dataManager.get(HANGING)).byteValue() & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIsBatHanging(boolean isHanging) {
/* 105 */     byte b0 = ((Byte)this.dataManager.get(HANGING)).byteValue();
/*     */     
/* 107 */     if (isHanging) {
/*     */       
/* 109 */       this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 113 */       this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 122 */     super.onUpdate();
/*     */     
/* 124 */     if (getIsBatHanging()) {
/*     */       
/* 126 */       this.motionX = 0.0D;
/* 127 */       this.motionY = 0.0D;
/* 128 */       this.motionZ = 0.0D;
/* 129 */       this.posY = MathHelper.floor(this.posY) + 1.0D - this.height;
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.motionY *= 0.6000000238418579D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 139 */     super.updateAITasks();
/* 140 */     BlockPos blockpos = new BlockPos((Entity)this);
/* 141 */     BlockPos blockpos1 = blockpos.up();
/*     */     
/* 143 */     if (getIsBatHanging()) {
/*     */       
/* 145 */       if (this.world.getBlockState(blockpos1).isNormalCube()) {
/*     */         
/* 147 */         if (this.rand.nextInt(200) == 0)
/*     */         {
/* 149 */           this.rotationYawHead = this.rand.nextInt(360);
/*     */         }
/*     */         
/* 152 */         if (this.world.getNearestPlayerNotCreative((Entity)this, 4.0D) != null)
/*     */         {
/* 154 */           setIsBatHanging(false);
/* 155 */           this.world.playEvent(null, 1025, blockpos, 0);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 160 */         setIsBatHanging(false);
/* 161 */         this.world.playEvent(null, 1025, blockpos, 0);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 166 */       if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
/*     */       {
/* 168 */         this.spawnPosition = null;
/*     */       }
/*     */       
/* 171 */       if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D)
/*     */       {
/* 173 */         this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */       }
/*     */       
/* 176 */       double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
/* 177 */       double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
/* 178 */       double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
/* 179 */       this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 180 */       this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 181 */       this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 182 */       float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 57.29577951308232D) - 90.0F;
/* 183 */       float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
/* 184 */       this.field_191988_bg = 0.5F;
/* 185 */       this.rotationYaw += f1;
/*     */       
/* 187 */       if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube())
/*     */       {
/* 189 */         setIsBatHanging(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesEntityNotTriggerPressurePlate() {
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 224 */     if (isEntityInvulnerable(source))
/*     */     {
/* 226 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 230 */     if (!this.world.isRemote && getIsBatHanging())
/*     */     {
/* 232 */       setIsBatHanging(false);
/*     */     }
/*     */     
/* 235 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesBat(DataFixer fixer) {
/* 241 */     EntityLiving.registerFixesMob(fixer, EntityBat.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 249 */     super.readEntityFromNBT(compound);
/* 250 */     this.dataManager.set(HANGING, Byte.valueOf(compound.getByte("BatFlags")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 258 */     super.writeEntityToNBT(compound);
/* 259 */     compound.setByte("BatFlags", ((Byte)this.dataManager.get(HANGING)).byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 267 */     BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */     
/* 269 */     if (blockpos.getY() >= this.world.getSeaLevel())
/*     */     {
/* 271 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 275 */     int i = this.world.getLightFromNeighbors(blockpos);
/* 276 */     int j = 4;
/*     */     
/* 278 */     if (isDateAroundHalloween(this.world.getCurrentDate())) {
/*     */       
/* 280 */       j = 7;
/*     */     }
/* 282 */     else if (this.rand.nextBoolean()) {
/*     */       
/* 284 */       return false;
/*     */     } 
/*     */     
/* 287 */     return (i > this.rand.nextInt(j)) ? false : super.getCanSpawnHere();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDateAroundHalloween(Calendar p_175569_1_) {
/* 293 */     return !((p_175569_1_.get(2) + 1 != 10 || p_175569_1_.get(5) < 20) && (p_175569_1_.get(2) + 1 != 11 || p_175569_1_.get(5) > 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 298 */     return this.height / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 304 */     return LootTableList.ENTITIES_BAT;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
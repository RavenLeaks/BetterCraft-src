/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityBodyHelper;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityShulker extends EntityGolem implements IMob {
/*  51 */   private static final UUID COVERED_ARMOR_BONUS_ID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
/*  52 */   private static final AttributeModifier COVERED_ARMOR_BONUS_MODIFIER = (new AttributeModifier(COVERED_ARMOR_BONUS_ID, "Covered armor bonus", 20.0D, 0)).setSaved(false);
/*  53 */   protected static final DataParameter<EnumFacing> ATTACHED_FACE = EntityDataManager.createKey(EntityShulker.class, DataSerializers.FACING);
/*  54 */   protected static final DataParameter<Optional<BlockPos>> ATTACHED_BLOCK_POS = EntityDataManager.createKey(EntityShulker.class, DataSerializers.OPTIONAL_BLOCK_POS);
/*  55 */   protected static final DataParameter<Byte> PEEK_TICK = EntityDataManager.createKey(EntityShulker.class, DataSerializers.BYTE);
/*  56 */   protected static final DataParameter<Byte> field_190770_bw = EntityDataManager.createKey(EntityShulker.class, DataSerializers.BYTE);
/*  57 */   public static final EnumDyeColor field_190771_bx = EnumDyeColor.PURPLE;
/*     */   
/*     */   private float prevPeekAmount;
/*     */   private float peekAmount;
/*     */   private BlockPos currentAttachmentPosition;
/*     */   private int clientSideTeleportInterpolation;
/*     */   
/*     */   public EntityShulker(World worldIn) {
/*  65 */     super(worldIn);
/*  66 */     setSize(1.0F, 1.0F);
/*  67 */     this.prevRenderYawOffset = 180.0F;
/*  68 */     this.renderYawOffset = 180.0F;
/*  69 */     this.isImmuneToFire = true;
/*  70 */     this.currentAttachmentPosition = null;
/*  71 */     this.experienceValue = 5;
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
/*  82 */     this.renderYawOffset = 180.0F;
/*  83 */     this.prevRenderYawOffset = 180.0F;
/*  84 */     this.rotationYaw = 180.0F;
/*  85 */     this.prevRotationYaw = 180.0F;
/*  86 */     this.rotationYawHead = 180.0F;
/*  87 */     this.prevRotationYawHead = 180.0F;
/*  88 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  93 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  94 */     this.tasks.addTask(4, new AIAttack());
/*  95 */     this.tasks.addTask(7, new AIPeek(null));
/*  96 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  97 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  98 */     this.targetTasks.addTask(2, (EntityAIBase)new AIAttackNearest(this));
/*  99 */     this.targetTasks.addTask(3, (EntityAIBase)new AIDefenseAttack(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/* 113 */     return SoundCategory.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 118 */     return SoundEvents.ENTITY_SHULKER_AMBIENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playLivingSound() {
/* 126 */     if (!isClosed())
/*     */     {
/* 128 */       super.playLivingSound();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 134 */     return SoundEvents.ENTITY_SHULKER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 139 */     return isClosed() ? SoundEvents.ENTITY_SHULKER_HURT_CLOSED : SoundEvents.ENTITY_SHULKER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 144 */     super.entityInit();
/* 145 */     this.dataManager.register(ATTACHED_FACE, EnumFacing.DOWN);
/* 146 */     this.dataManager.register(ATTACHED_BLOCK_POS, Optional.absent());
/* 147 */     this.dataManager.register(PEEK_TICK, Byte.valueOf((byte)0));
/* 148 */     this.dataManager.register(field_190770_bw, Byte.valueOf((byte)field_190771_bx.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 153 */     super.applyEntityAttributes();
/* 154 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityBodyHelper createBodyHelper() {
/* 159 */     return new BodyHelper((EntityLivingBase)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesShulker(DataFixer fixer) {
/* 164 */     EntityLiving.registerFixesMob(fixer, EntityShulker.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 172 */     super.readEntityFromNBT(compound);
/* 173 */     this.dataManager.set(ATTACHED_FACE, EnumFacing.getFront(compound.getByte("AttachFace")));
/* 174 */     this.dataManager.set(PEEK_TICK, Byte.valueOf(compound.getByte("Peek")));
/* 175 */     this.dataManager.set(field_190770_bw, Byte.valueOf(compound.getByte("Color")));
/*     */     
/* 177 */     if (compound.hasKey("APX")) {
/*     */       
/* 179 */       int i = compound.getInteger("APX");
/* 180 */       int j = compound.getInteger("APY");
/* 181 */       int k = compound.getInteger("APZ");
/* 182 */       this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
/*     */     }
/*     */     else {
/*     */       
/* 186 */       this.dataManager.set(ATTACHED_BLOCK_POS, Optional.absent());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 195 */     super.writeEntityToNBT(compound);
/* 196 */     compound.setByte("AttachFace", (byte)((EnumFacing)this.dataManager.get(ATTACHED_FACE)).getIndex());
/* 197 */     compound.setByte("Peek", ((Byte)this.dataManager.get(PEEK_TICK)).byteValue());
/* 198 */     compound.setByte("Color", ((Byte)this.dataManager.get(field_190770_bw)).byteValue());
/* 199 */     BlockPos blockpos = getAttachmentPos();
/*     */     
/* 201 */     if (blockpos != null) {
/*     */       
/* 203 */       compound.setInteger("APX", blockpos.getX());
/* 204 */       compound.setInteger("APY", blockpos.getY());
/* 205 */       compound.setInteger("APZ", blockpos.getZ());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 214 */     super.onUpdate();
/* 215 */     BlockPos blockpos = (BlockPos)((Optional)this.dataManager.get(ATTACHED_BLOCK_POS)).orNull();
/*     */     
/* 217 */     if (blockpos == null && !this.world.isRemote) {
/*     */       
/* 219 */       blockpos = new BlockPos((Entity)this);
/* 220 */       this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockpos));
/*     */     } 
/*     */     
/* 223 */     if (isRiding()) {
/*     */       
/* 225 */       blockpos = null;
/* 226 */       float f = (getRidingEntity()).rotationYaw;
/* 227 */       this.rotationYaw = f;
/* 228 */       this.renderYawOffset = f;
/* 229 */       this.prevRenderYawOffset = f;
/* 230 */       this.clientSideTeleportInterpolation = 0;
/*     */     }
/* 232 */     else if (!this.world.isRemote) {
/*     */       
/* 234 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/*     */       
/* 236 */       if (iblockstate.getMaterial() != Material.AIR)
/*     */       {
/* 238 */         if (iblockstate.getBlock() == Blocks.PISTON_EXTENSION) {
/*     */           
/* 240 */           EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockPistonBase.FACING);
/*     */           
/* 242 */           if (this.world.isAirBlock(blockpos.offset(enumfacing)))
/*     */           {
/* 244 */             blockpos = blockpos.offset(enumfacing);
/* 245 */             this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockpos));
/*     */           }
/*     */           else
/*     */           {
/* 249 */             tryTeleportToNewPosition();
/*     */           }
/*     */         
/* 252 */         } else if (iblockstate.getBlock() == Blocks.PISTON_HEAD) {
/*     */           
/* 254 */           EnumFacing enumfacing3 = (EnumFacing)iblockstate.getValue((IProperty)BlockPistonExtension.FACING);
/*     */           
/* 256 */           if (this.world.isAirBlock(blockpos.offset(enumfacing3)))
/*     */           {
/* 258 */             blockpos = blockpos.offset(enumfacing3);
/* 259 */             this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockpos));
/*     */           }
/*     */           else
/*     */           {
/* 263 */             tryTeleportToNewPosition();
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 268 */           tryTeleportToNewPosition();
/*     */         } 
/*     */       }
/*     */       
/* 272 */       BlockPos blockpos1 = blockpos.offset(getAttachmentFacing());
/*     */       
/* 274 */       if (!this.world.isBlockNormalCube(blockpos1, false)) {
/*     */         
/* 276 */         boolean flag = false; byte b; int i;
/*     */         EnumFacing[] arrayOfEnumFacing;
/* 278 */         for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b];
/*     */           
/* 280 */           blockpos1 = blockpos.offset(enumfacing1);
/*     */           
/* 282 */           if (this.world.isBlockNormalCube(blockpos1, false)) {
/*     */             
/* 284 */             this.dataManager.set(ATTACHED_FACE, enumfacing1);
/* 285 */             flag = true;
/*     */             break;
/*     */           } 
/*     */           b++; }
/*     */         
/* 290 */         if (!flag)
/*     */         {
/* 292 */           tryTeleportToNewPosition();
/*     */         }
/*     */       } 
/*     */       
/* 296 */       BlockPos blockpos2 = blockpos.offset(getAttachmentFacing().getOpposite());
/*     */       
/* 298 */       if (this.world.isBlockNormalCube(blockpos2, false))
/*     */       {
/* 300 */         tryTeleportToNewPosition();
/*     */       }
/*     */     } 
/*     */     
/* 304 */     float f1 = getPeekTick() * 0.01F;
/* 305 */     this.prevPeekAmount = this.peekAmount;
/*     */     
/* 307 */     if (this.peekAmount > f1) {
/*     */       
/* 309 */       this.peekAmount = MathHelper.clamp(this.peekAmount - 0.05F, f1, 1.0F);
/*     */     }
/* 311 */     else if (this.peekAmount < f1) {
/*     */       
/* 313 */       this.peekAmount = MathHelper.clamp(this.peekAmount + 0.05F, 0.0F, f1);
/*     */     } 
/*     */     
/* 316 */     if (blockpos != null) {
/*     */       
/* 318 */       if (this.world.isRemote)
/*     */       {
/* 320 */         if (this.clientSideTeleportInterpolation > 0 && this.currentAttachmentPosition != null) {
/*     */           
/* 322 */           this.clientSideTeleportInterpolation--;
/*     */         }
/*     */         else {
/*     */           
/* 326 */           this.currentAttachmentPosition = blockpos;
/*     */         } 
/*     */       }
/*     */       
/* 330 */       this.posX = blockpos.getX() + 0.5D;
/* 331 */       this.posY = blockpos.getY();
/* 332 */       this.posZ = blockpos.getZ() + 0.5D;
/* 333 */       this.prevPosX = this.posX;
/* 334 */       this.prevPosY = this.posY;
/* 335 */       this.prevPosZ = this.posZ;
/* 336 */       this.lastTickPosX = this.posX;
/* 337 */       this.lastTickPosY = this.posY;
/* 338 */       this.lastTickPosZ = this.posZ;
/* 339 */       double d3 = 0.5D - MathHelper.sin((0.5F + this.peekAmount) * 3.1415927F) * 0.5D;
/* 340 */       double d4 = 0.5D - MathHelper.sin((0.5F + this.prevPeekAmount) * 3.1415927F) * 0.5D;
/* 341 */       double d5 = d3 - d4;
/* 342 */       double d0 = 0.0D;
/* 343 */       double d1 = 0.0D;
/* 344 */       double d2 = 0.0D;
/* 345 */       EnumFacing enumfacing2 = getAttachmentFacing();
/*     */       
/* 347 */       switch (enumfacing2) {
/*     */         
/*     */         case null:
/* 350 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D + d3, this.posZ + 0.5D));
/* 351 */           d1 = d5;
/*     */           break;
/*     */         
/*     */         case UP:
/* 355 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D, this.posY - d3, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D));
/* 356 */           d1 = -d5;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 360 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D + d3));
/* 361 */           d2 = d5;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 365 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D, this.posY, this.posZ - 0.5D - d3, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D));
/* 366 */           d2 = -d5;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 370 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D + d3, this.posY + 1.0D, this.posZ + 0.5D));
/* 371 */           d0 = d5;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 375 */           setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5D - d3, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D));
/* 376 */           d0 = -d5;
/*     */           break;
/*     */       } 
/* 379 */       if (d5 > 0.0D) {
/*     */         
/* 381 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox());
/*     */         
/* 383 */         if (!list.isEmpty())
/*     */         {
/* 385 */           for (Entity entity : list) {
/*     */             
/* 387 */             if (!(entity instanceof EntityShulker) && !entity.noClip)
/*     */             {
/* 389 */               entity.moveEntity(MoverType.SHULKER, d0, d1, d2);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/* 402 */     if (x == MoverType.SHULKER_BOX) {
/*     */       
/* 404 */       tryTeleportToNewPosition();
/*     */     }
/*     */     else {
/*     */       
/* 408 */       super.moveEntity(x, p_70091_2_, p_70091_4_, p_70091_6_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 417 */     super.setPosition(x, y, z);
/*     */     
/* 419 */     if (this.dataManager != null && this.ticksExisted != 0) {
/*     */       
/* 421 */       Optional<BlockPos> optional = (Optional<BlockPos>)this.dataManager.get(ATTACHED_BLOCK_POS);
/* 422 */       Optional<BlockPos> optional1 = Optional.of(new BlockPos(x, y, z));
/*     */       
/* 424 */       if (!optional1.equals(optional)) {
/*     */         
/* 426 */         this.dataManager.set(ATTACHED_BLOCK_POS, optional1);
/* 427 */         this.dataManager.set(PEEK_TICK, Byte.valueOf((byte)0));
/* 428 */         this.isAirBorne = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean tryTeleportToNewPosition() {
/* 435 */     if (!isAIDisabled() && isEntityAlive()) {
/*     */       
/* 437 */       BlockPos blockpos = new BlockPos((Entity)this);
/*     */       
/* 439 */       for (int i = 0; i < 5; i++) {
/*     */         
/* 441 */         BlockPos blockpos1 = blockpos.add(8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17));
/*     */         
/* 443 */         if (blockpos1.getY() > 0 && this.world.isAirBlock(blockpos1) && this.world.func_191503_g((Entity)this) && this.world.getCollisionBoxes((Entity)this, new AxisAlignedBB(blockpos1)).isEmpty()) {
/*     */           
/* 445 */           boolean flag = false; byte b; int j;
/*     */           EnumFacing[] arrayOfEnumFacing;
/* 447 */           for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */             
/* 449 */             if (this.world.isBlockNormalCube(blockpos1.offset(enumfacing), false)) {
/*     */               
/* 451 */               this.dataManager.set(ATTACHED_FACE, enumfacing);
/* 452 */               flag = true;
/*     */               break;
/*     */             } 
/*     */             b++; }
/*     */           
/* 457 */           if (flag) {
/*     */             
/* 459 */             playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
/* 460 */             this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockpos1));
/* 461 */             this.dataManager.set(PEEK_TICK, Byte.valueOf((byte)0));
/* 462 */             setAttackTarget(null);
/* 463 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 468 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 472 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 482 */     super.onLivingUpdate();
/* 483 */     this.motionX = 0.0D;
/* 484 */     this.motionY = 0.0D;
/* 485 */     this.motionZ = 0.0D;
/* 486 */     this.prevRenderYawOffset = 180.0F;
/* 487 */     this.renderYawOffset = 180.0F;
/* 488 */     this.rotationYaw = 180.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 493 */     if (ATTACHED_BLOCK_POS.equals(key) && this.world.isRemote && !isRiding()) {
/*     */       
/* 495 */       BlockPos blockpos = getAttachmentPos();
/*     */       
/* 497 */       if (blockpos != null) {
/*     */         
/* 499 */         if (this.currentAttachmentPosition == null) {
/*     */           
/* 501 */           this.currentAttachmentPosition = blockpos;
/*     */         }
/*     */         else {
/*     */           
/* 505 */           this.clientSideTeleportInterpolation = 6;
/*     */         } 
/*     */         
/* 508 */         this.posX = blockpos.getX() + 0.5D;
/* 509 */         this.posY = blockpos.getY();
/* 510 */         this.posZ = blockpos.getZ() + 0.5D;
/* 511 */         this.prevPosX = this.posX;
/* 512 */         this.prevPosY = this.posY;
/* 513 */         this.prevPosZ = this.posZ;
/* 514 */         this.lastTickPosX = this.posX;
/* 515 */         this.lastTickPosY = this.posY;
/* 516 */         this.lastTickPosZ = this.posZ;
/*     */       } 
/*     */     } 
/*     */     
/* 520 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 528 */     this.newPosRotationIncrements = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 536 */     if (isClosed()) {
/*     */       
/* 538 */       Entity entity = source.getSourceOfDamage();
/*     */       
/* 540 */       if (entity instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */       {
/* 542 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 546 */     if (super.attackEntityFrom(source, amount)) {
/*     */       
/* 548 */       if (getHealth() < getMaxHealth() * 0.5D && this.rand.nextInt(4) == 0)
/*     */       {
/* 550 */         tryTeleportToNewPosition();
/*     */       }
/*     */       
/* 553 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 557 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClosed() {
/* 563 */     return (getPeekTick() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/* 573 */     return isEntityAlive() ? getEntityBoundingBox() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getAttachmentFacing() {
/* 578 */     return (EnumFacing)this.dataManager.get(ATTACHED_FACE);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getAttachmentPos() {
/* 584 */     return (BlockPos)((Optional)this.dataManager.get(ATTACHED_BLOCK_POS)).orNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentPos(@Nullable BlockPos pos) {
/* 589 */     this.dataManager.set(ATTACHED_BLOCK_POS, Optional.fromNullable(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPeekTick() {
/* 594 */     return ((Byte)this.dataManager.get(PEEK_TICK)).byteValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateArmorModifier(int p_184691_1_) {
/* 602 */     if (!this.world.isRemote) {
/*     */       
/* 604 */       getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(COVERED_ARMOR_BONUS_MODIFIER);
/*     */       
/* 606 */       if (p_184691_1_ == 0) {
/*     */         
/* 608 */         getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(COVERED_ARMOR_BONUS_MODIFIER);
/* 609 */         playSound(SoundEvents.ENTITY_SHULKER_CLOSE, 1.0F, 1.0F);
/*     */       }
/*     */       else {
/*     */         
/* 613 */         playSound(SoundEvents.ENTITY_SHULKER_OPEN, 1.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 617 */     this.dataManager.set(PEEK_TICK, Byte.valueOf((byte)p_184691_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getClientPeekAmount(float p_184688_1_) {
/* 622 */     return this.prevPeekAmount + (this.peekAmount - this.prevPeekAmount) * p_184688_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClientTeleportInterp() {
/* 627 */     return this.clientSideTeleportInterpolation;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getOldAttachPos() {
/* 632 */     return this.currentAttachmentPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 637 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 646 */     return 180;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHorizontalFaceSpeed() {
/* 651 */     return 180;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(Entity entityIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCollisionBorderSize() {
/* 663 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttachedToBlock() {
/* 668 */     return (this.currentAttachmentPosition != null && getAttachmentPos() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 674 */     return LootTableList.ENTITIES_SHULKER;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDyeColor func_190769_dn() {
/* 679 */     return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(field_190770_bw)).byteValue());
/*     */   }
/*     */   
/*     */   class AIAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private int attackTime;
/*     */     
/*     */     public AIAttack() {
/* 688 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 693 */       EntityLivingBase entitylivingbase = EntityShulker.this.getAttackTarget();
/*     */       
/* 695 */       if (entitylivingbase != null && entitylivingbase.isEntityAlive())
/*     */       {
/* 697 */         return (EntityShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */       }
/*     */ 
/*     */       
/* 701 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 707 */       this.attackTime = 20;
/* 708 */       EntityShulker.this.updateArmorModifier(100);
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 713 */       EntityShulker.this.updateArmorModifier(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 718 */       if (EntityShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
/*     */         
/* 720 */         this.attackTime--;
/* 721 */         EntityLivingBase entitylivingbase = EntityShulker.this.getAttackTarget();
/* 722 */         EntityShulker.this.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 180.0F, 180.0F);
/* 723 */         double d0 = EntityShulker.this.getDistanceSqToEntity((Entity)entitylivingbase);
/*     */         
/* 725 */         if (d0 < 400.0D) {
/*     */           
/* 727 */           if (this.attackTime <= 0)
/*     */           {
/* 729 */             this.attackTime = 20 + EntityShulker.this.rand.nextInt(10) * 20 / 2;
/* 730 */             EntityShulkerBullet entityshulkerbullet = new EntityShulkerBullet(EntityShulker.this.world, (EntityLivingBase)EntityShulker.this, (Entity)entitylivingbase, EntityShulker.this.getAttachmentFacing().getAxis());
/* 731 */             EntityShulker.this.world.spawnEntityInWorld((Entity)entityshulkerbullet);
/* 732 */             EntityShulker.this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (EntityShulker.this.rand.nextFloat() - EntityShulker.this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 737 */           EntityShulker.this.setAttackTarget(null);
/*     */         } 
/*     */         
/* 740 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class AIAttackNearest
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer>
/*     */   {
/*     */     public AIAttackNearest(EntityShulker shulker) {
/* 749 */       super(shulker, EntityPlayer.class, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 754 */       return (EntityShulker.this.world.getDifficulty() == EnumDifficulty.PEACEFUL) ? false : super.shouldExecute();
/*     */     }
/*     */ 
/*     */     
/*     */     protected AxisAlignedBB getTargetableArea(double targetDistance) {
/* 759 */       EnumFacing enumfacing = ((EntityShulker)this.taskOwner).getAttachmentFacing();
/*     */       
/* 761 */       if (enumfacing.getAxis() == EnumFacing.Axis.X)
/*     */       {
/* 763 */         return this.taskOwner.getEntityBoundingBox().expand(4.0D, targetDistance, targetDistance);
/*     */       }
/*     */ 
/*     */       
/* 767 */       return (enumfacing.getAxis() == EnumFacing.Axis.Z) ? this.taskOwner.getEntityBoundingBox().expand(targetDistance, targetDistance, 4.0D) : this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0D, targetDistance);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class AIDefenseAttack
/*     */     extends EntityAINearestAttackableTarget<EntityLivingBase>
/*     */   {
/*     */     public AIDefenseAttack(EntityShulker shulker) {
/* 776 */       super(shulker, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>()
/*     */           {
/*     */             public boolean apply(@Nullable EntityLivingBase p_apply_1_)
/*     */             {
/* 780 */               return p_apply_1_ instanceof IMob;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 787 */       return (this.taskOwner.getTeam() == null) ? false : super.shouldExecute();
/*     */     }
/*     */ 
/*     */     
/*     */     protected AxisAlignedBB getTargetableArea(double targetDistance) {
/* 792 */       EnumFacing enumfacing = ((EntityShulker)this.taskOwner).getAttachmentFacing();
/*     */       
/* 794 */       if (enumfacing.getAxis() == EnumFacing.Axis.X)
/*     */       {
/* 796 */         return this.taskOwner.getEntityBoundingBox().expand(4.0D, targetDistance, targetDistance);
/*     */       }
/*     */ 
/*     */       
/* 800 */       return (enumfacing.getAxis() == EnumFacing.Axis.Z) ? this.taskOwner.getEntityBoundingBox().expand(targetDistance, targetDistance, 4.0D) : this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0D, targetDistance);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AIPeek
/*     */     extends EntityAIBase
/*     */   {
/*     */     private int peekTime;
/*     */ 
/*     */     
/*     */     private AIPeek() {}
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 815 */       return (EntityShulker.this.getAttackTarget() == null && EntityShulker.this.rand.nextInt(40) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 820 */       return (EntityShulker.this.getAttackTarget() == null && this.peekTime > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 825 */       this.peekTime = 20 * (1 + EntityShulker.this.rand.nextInt(3));
/* 826 */       EntityShulker.this.updateArmorModifier(30);
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 831 */       if (EntityShulker.this.getAttackTarget() == null)
/*     */       {
/* 833 */         EntityShulker.this.updateArmorModifier(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 839 */       this.peekTime--;
/*     */     }
/*     */   }
/*     */   
/*     */   class BodyHelper
/*     */     extends EntityBodyHelper
/*     */   {
/*     */     public BodyHelper(EntityLivingBase p_i47062_2_) {
/* 847 */       super(p_i47062_2_);
/*     */     }
/*     */     
/*     */     public void updateRenderAngles() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityShulker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.entity.passive;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIEatGrass;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
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
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntitySheep extends EntityAnimal {
/*  49 */   private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.createKey(EntitySheep.class, DataSerializers.BYTE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container()
/*     */       {
/*     */         public boolean canInteractWith(EntityPlayer playerIn)
/*     */         {
/*  59 */           return false;
/*     */         }
/*  61 */       },  2, 1);
/*  62 */   private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
/*     */ 
/*     */   
/*     */   private int sheepTimer;
/*     */ 
/*     */   
/*     */   private EntityAIEatGrass entityAIEatGrass;
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] func_192020_c(EnumDyeColor p_192020_0_) {
/*  73 */     float[] afloat = p_192020_0_.func_193349_f();
/*  74 */     float f = 0.75F;
/*  75 */     return new float[] { afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F };
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getDyeRgb(EnumDyeColor dyeColor) {
/*  80 */     return DYE_TO_RGB.get(dyeColor);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntitySheep(World worldIn) {
/*  85 */     super(worldIn);
/*  86 */     setSize(0.9F, 1.3F);
/*  87 */     this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.DYE));
/*  88 */     this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.DYE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  93 */     this.entityAIEatGrass = new EntityAIEatGrass((EntityLiving)this);
/*  94 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  95 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  96 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  97 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.1D, Items.WHEAT, false));
/*  98 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  99 */     this.tasks.addTask(5, (EntityAIBase)this.entityAIEatGrass);
/* 100 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/* 101 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/* 102 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 107 */     this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
/* 108 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 117 */     if (this.world.isRemote)
/*     */     {
/* 119 */       this.sheepTimer = Math.max(0, this.sheepTimer - 1);
/*     */     }
/*     */     
/* 122 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 127 */     super.applyEntityAttributes();
/* 128 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
/* 129 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 134 */     super.entityInit();
/* 135 */     this.dataManager.register(DYE_COLOR, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 141 */     if (getSheared())
/*     */     {
/* 143 */       return LootTableList.ENTITIES_SHEEP;
/*     */     }
/*     */ 
/*     */     
/* 147 */     switch (getFleeceColor()) {
/*     */ 
/*     */       
/*     */       default:
/* 151 */         return LootTableList.ENTITIES_SHEEP_WHITE;
/*     */       
/*     */       case ORANGE:
/* 154 */         return LootTableList.ENTITIES_SHEEP_ORANGE;
/*     */       
/*     */       case MAGENTA:
/* 157 */         return LootTableList.ENTITIES_SHEEP_MAGENTA;
/*     */       
/*     */       case LIGHT_BLUE:
/* 160 */         return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
/*     */       
/*     */       case YELLOW:
/* 163 */         return LootTableList.ENTITIES_SHEEP_YELLOW;
/*     */       
/*     */       case LIME:
/* 166 */         return LootTableList.ENTITIES_SHEEP_LIME;
/*     */       
/*     */       case PINK:
/* 169 */         return LootTableList.ENTITIES_SHEEP_PINK;
/*     */       
/*     */       case GRAY:
/* 172 */         return LootTableList.ENTITIES_SHEEP_GRAY;
/*     */       
/*     */       case SILVER:
/* 175 */         return LootTableList.ENTITIES_SHEEP_SILVER;
/*     */       
/*     */       case CYAN:
/* 178 */         return LootTableList.ENTITIES_SHEEP_CYAN;
/*     */       
/*     */       case PURPLE:
/* 181 */         return LootTableList.ENTITIES_SHEEP_PURPLE;
/*     */       
/*     */       case BLUE:
/* 184 */         return LootTableList.ENTITIES_SHEEP_BLUE;
/*     */       
/*     */       case BROWN:
/* 187 */         return LootTableList.ENTITIES_SHEEP_BROWN;
/*     */       
/*     */       case GREEN:
/* 190 */         return LootTableList.ENTITIES_SHEEP_GREEN;
/*     */       
/*     */       case RED:
/* 193 */         return LootTableList.ENTITIES_SHEEP_RED;
/*     */       case null:
/*     */         break;
/* 196 */     }  return LootTableList.ENTITIES_SHEEP_BLACK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 203 */     if (id == 10) {
/*     */       
/* 205 */       this.sheepTimer = 40;
/*     */     }
/*     */     else {
/*     */       
/* 209 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeadRotationPointY(float p_70894_1_) {
/* 215 */     if (this.sheepTimer <= 0)
/*     */     {
/* 217 */       return 0.0F;
/*     */     }
/* 219 */     if (this.sheepTimer >= 4 && this.sheepTimer <= 36)
/*     */     {
/* 221 */       return 1.0F;
/*     */     }
/*     */ 
/*     */     
/* 225 */     return (this.sheepTimer < 4) ? ((this.sheepTimer - p_70894_1_) / 4.0F) : (-((this.sheepTimer - 40) - p_70894_1_) / 4.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeadRotationAngleX(float p_70890_1_) {
/* 231 */     if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
/*     */       
/* 233 */       float f = ((this.sheepTimer - 4) - p_70890_1_) / 32.0F;
/* 234 */       return 0.62831855F + 0.2199115F * MathHelper.sin(f * 28.7F);
/*     */     } 
/*     */ 
/*     */     
/* 238 */     return (this.sheepTimer > 0) ? 0.62831855F : (this.rotationPitch * 0.017453292F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 244 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 246 */     if (itemstack.getItem() == Items.SHEARS && !getSheared() && !isChild()) {
/*     */       
/* 248 */       if (!this.world.isRemote) {
/*     */         
/* 250 */         setSheared(true);
/* 251 */         int i = 1 + this.rand.nextInt(3);
/*     */         
/* 253 */         for (int j = 0; j < i; j++) {
/*     */           
/* 255 */           EntityItem entityitem = entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, getFleeceColor().getMetadata()), 1.0F);
/* 256 */           entityitem.motionY += (this.rand.nextFloat() * 0.05F);
/* 257 */           entityitem.motionX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/* 258 */           entityitem.motionZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */       
/* 262 */       itemstack.damageItem(1, (EntityLivingBase)player);
/* 263 */       playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 266 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSheep(DataFixer fixer) {
/* 271 */     EntityLiving.registerFixesMob(fixer, EntitySheep.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 279 */     super.writeEntityToNBT(compound);
/* 280 */     compound.setBoolean("Sheared", getSheared());
/* 281 */     compound.setByte("Color", (byte)getFleeceColor().getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 289 */     super.readEntityFromNBT(compound);
/* 290 */     setSheared(compound.getBoolean("Sheared"));
/* 291 */     setFleeceColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 296 */     return SoundEvents.ENTITY_SHEEP_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 301 */     return SoundEvents.ENTITY_SHEEP_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 306 */     return SoundEvents.ENTITY_SHEEP_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 311 */     playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDyeColor getFleeceColor() {
/* 319 */     return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 0xF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFleeceColor(EnumDyeColor color) {
/* 327 */     byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
/* 328 */     this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & 0xF0 | color.getMetadata() & 0xF)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSheared() {
/* 336 */     return ((((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSheared(boolean sheared) {
/* 344 */     byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
/*     */     
/* 346 */     if (sheared) {
/*     */       
/* 348 */       this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     }
/*     */     else {
/*     */       
/* 352 */       this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumDyeColor getRandomSheepColor(Random random) {
/* 361 */     int i = random.nextInt(100);
/*     */     
/* 363 */     if (i < 5)
/*     */     {
/* 365 */       return EnumDyeColor.BLACK;
/*     */     }
/* 367 */     if (i < 10)
/*     */     {
/* 369 */       return EnumDyeColor.GRAY;
/*     */     }
/* 371 */     if (i < 15)
/*     */     {
/* 373 */       return EnumDyeColor.SILVER;
/*     */     }
/* 375 */     if (i < 18)
/*     */     {
/* 377 */       return EnumDyeColor.BROWN;
/*     */     }
/*     */ 
/*     */     
/* 381 */     return (random.nextInt(500) == 0) ? EnumDyeColor.PINK : EnumDyeColor.WHITE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntitySheep createChild(EntityAgeable ageable) {
/* 387 */     EntitySheep entitysheep = (EntitySheep)ageable;
/* 388 */     EntitySheep entitysheep1 = new EntitySheep(this.world);
/* 389 */     entitysheep1.setFleeceColor(getDyeColorMixFromParents(this, entitysheep));
/* 390 */     return entitysheep1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eatGrassBonus() {
/* 399 */     setSheared(false);
/*     */     
/* 401 */     if (isChild())
/*     */     {
/* 403 */       addGrowth(60);
/*     */     }
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
/* 415 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 416 */     setFleeceColor(getRandomSheepColor(this.world.rand));
/* 417 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumDyeColor getDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother) {
/* 425 */     int k, i = ((EntitySheep)father).getFleeceColor().getDyeDamage();
/* 426 */     int j = ((EntitySheep)mother).getFleeceColor().getDyeDamage();
/* 427 */     this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
/* 428 */     this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
/* 429 */     ItemStack itemstack = CraftingManager.findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)father).world);
/*     */ 
/*     */     
/* 432 */     if (itemstack.getItem() == Items.DYE) {
/*     */       
/* 434 */       k = itemstack.getMetadata();
/*     */     }
/*     */     else {
/*     */       
/* 438 */       k = this.world.rand.nextBoolean() ? i : j;
/*     */     } 
/*     */     
/* 441 */     return EnumDyeColor.byDyeDamage(k);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 446 */     return 0.95F * this.height;
/*     */   } static {
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/* 451 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*     */       
/* 453 */       DYE_TO_RGB.put(enumdyecolor, func_192020_c(enumdyecolor));
/*     */       b++; }
/*     */     
/* 456 */     DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] { 0.9019608F, 0.9019608F, 0.9019608F });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntitySheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
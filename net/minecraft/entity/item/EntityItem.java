/*     */ package net.minecraft.entity.item;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityItem extends Entity {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*  31 */   public static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityItem.class, DataSerializers.OPTIONAL_ITEM_STACK);
/*     */ 
/*     */   
/*     */   private int age;
/*     */ 
/*     */   
/*     */   private int delayBeforeCanPickup;
/*     */   
/*     */   private int health;
/*     */   
/*     */   private String thrower;
/*     */   
/*     */   private String owner;
/*     */   
/*     */   public float hoverStart;
/*     */ 
/*     */   
/*     */   public EntityItem(World worldIn, double x, double y, double z) {
/*  49 */     super(worldIn);
/*  50 */     this.health = 5;
/*  51 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  52 */     setSize(0.25F, 0.25F);
/*  53 */     setPosition(x, y, z);
/*  54 */     this.rotationYaw = (float)(Math.random() * 360.0D);
/*  55 */     this.motionX = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*  56 */     this.motionY = 0.20000000298023224D;
/*  57 */     this.motionZ = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
/*  62 */     this(worldIn, x, y, z);
/*  63 */     setEntityItemStack(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityItem(World worldIn) {
/*  77 */     super(worldIn);
/*  78 */     this.health = 5;
/*  79 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  80 */     setSize(0.25F, 0.25F);
/*  81 */     setEntityItemStack(ItemStack.field_190927_a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  86 */     getDataManager().register(ITEM, ItemStack.field_190927_a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  94 */     if (getEntityItem().func_190926_b()) {
/*     */       
/*  96 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 100 */       super.onUpdate();
/*     */       
/* 102 */       if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767)
/*     */       {
/* 104 */         this.delayBeforeCanPickup--;
/*     */       }
/*     */       
/* 107 */       this.prevPosX = this.posX;
/* 108 */       this.prevPosY = this.posY;
/* 109 */       this.prevPosZ = this.posZ;
/* 110 */       double d0 = this.motionX;
/* 111 */       double d1 = this.motionY;
/* 112 */       double d2 = this.motionZ;
/*     */       
/* 114 */       if (!hasNoGravity())
/*     */       {
/* 116 */         this.motionY -= 0.03999999910593033D;
/*     */       }
/*     */       
/* 119 */       if (this.world.isRemote) {
/*     */         
/* 121 */         this.noClip = false;
/*     */       }
/*     */       else {
/*     */         
/* 125 */         this.noClip = pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ);
/*     */       } 
/*     */       
/* 128 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 129 */       boolean flag = !((int)this.prevPosX == (int)this.posX && (int)this.prevPosY == (int)this.posY && (int)this.prevPosZ == (int)this.posZ);
/*     */       
/* 131 */       if (flag || this.ticksExisted % 25 == 0) {
/*     */         
/* 133 */         if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
/*     */           
/* 135 */           this.motionY = 0.20000000298023224D;
/* 136 */           this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 137 */           this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 138 */           playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/*     */         } 
/*     */         
/* 141 */         if (!this.world.isRemote)
/*     */         {
/* 143 */           searchForOtherItemsNearby();
/*     */         }
/*     */       } 
/*     */       
/* 147 */       float f = 0.98F;
/*     */       
/* 149 */       if (this.onGround)
/*     */       {
/* 151 */         f = (this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor((getEntityBoundingBox()).minY) - 1, MathHelper.floor(this.posZ))).getBlock()).slipperiness * 0.98F;
/*     */       }
/*     */       
/* 154 */       this.motionX *= f;
/* 155 */       this.motionY *= 0.9800000190734863D;
/* 156 */       this.motionZ *= f;
/*     */       
/* 158 */       if (this.onGround)
/*     */       {
/* 160 */         this.motionY *= -0.5D;
/*     */       }
/*     */       
/* 163 */       if (this.age != -32768)
/*     */       {
/* 165 */         this.age++;
/*     */       }
/*     */       
/* 168 */       handleWaterMovement();
/*     */       
/* 170 */       if (!this.world.isRemote) {
/*     */         
/* 172 */         double d3 = this.motionX - d0;
/* 173 */         double d4 = this.motionY - d1;
/* 174 */         double d5 = this.motionZ - d2;
/* 175 */         double d6 = d3 * d3 + d4 * d4 + d5 * d5;
/*     */         
/* 177 */         if (d6 > 0.01D)
/*     */         {
/* 179 */           this.isAirBorne = true;
/*     */         }
/*     */       } 
/*     */       
/* 183 */       if (!this.world.isRemote && this.age >= 6000)
/*     */       {
/* 185 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void searchForOtherItemsNearby() {
/* 195 */     for (EntityItem entityitem : this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D)))
/*     */     {
/* 197 */       combineItems(entityitem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean combineItems(EntityItem other) {
/* 207 */     if (other == this)
/*     */     {
/* 209 */       return false;
/*     */     }
/* 211 */     if (other.isEntityAlive() && isEntityAlive()) {
/*     */       
/* 213 */       ItemStack itemstack = getEntityItem();
/* 214 */       ItemStack itemstack1 = other.getEntityItem();
/*     */       
/* 216 */       if (this.delayBeforeCanPickup != 32767 && other.delayBeforeCanPickup != 32767) {
/*     */         
/* 218 */         if (this.age != -32768 && other.age != -32768) {
/*     */           
/* 220 */           if (itemstack1.getItem() != itemstack.getItem())
/*     */           {
/* 222 */             return false;
/*     */           }
/* 224 */           if ((itemstack1.hasTagCompound() ^ itemstack.hasTagCompound()) != 0)
/*     */           {
/* 226 */             return false;
/*     */           }
/* 228 */           if (itemstack1.hasTagCompound() && !itemstack1.getTagCompound().equals(itemstack.getTagCompound()))
/*     */           {
/* 230 */             return false;
/*     */           }
/* 232 */           if (itemstack1.getItem() == null)
/*     */           {
/* 234 */             return false;
/*     */           }
/* 236 */           if (itemstack1.getItem().getHasSubtypes() && itemstack1.getMetadata() != itemstack.getMetadata())
/*     */           {
/* 238 */             return false;
/*     */           }
/* 240 */           if (itemstack1.func_190916_E() < itemstack.func_190916_E())
/*     */           {
/* 242 */             return other.combineItems(this);
/*     */           }
/* 244 */           if (itemstack1.func_190916_E() + itemstack.func_190916_E() > itemstack1.getMaxStackSize())
/*     */           {
/* 246 */             return false;
/*     */           }
/*     */ 
/*     */           
/* 250 */           itemstack1.func_190917_f(itemstack.func_190916_E());
/* 251 */           other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, this.delayBeforeCanPickup);
/* 252 */           other.age = Math.min(other.age, this.age);
/* 253 */           other.setEntityItemStack(itemstack1);
/* 254 */           setDead();
/* 255 */           return true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 260 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 265 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAgeToCreativeDespawnTime() {
/* 280 */     this.age = 4800;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleWaterMovement() {
/* 288 */     if (this.world.handleMaterialAcceleration(getEntityBoundingBox(), Material.WATER, this)) {
/*     */       
/* 290 */       if (!this.inWater && !this.firstUpdate)
/*     */       {
/* 292 */         resetHeight();
/*     */       }
/*     */       
/* 295 */       this.inWater = true;
/*     */     }
/*     */     else {
/*     */       
/* 299 */       this.inWater = false;
/*     */     } 
/*     */     
/* 302 */     return this.inWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dealFireDamage(int amount) {
/* 310 */     attackEntityFrom(DamageSource.inFire, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 318 */     if (isEntityInvulnerable(source))
/*     */     {
/* 320 */       return false;
/*     */     }
/* 322 */     if (!getEntityItem().func_190926_b() && getEntityItem().getItem() == Items.NETHER_STAR && source.isExplosion())
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     setBeenAttacked();
/* 329 */     this.health = (int)(this.health - amount);
/*     */     
/* 331 */     if (this.health <= 0)
/*     */     {
/* 333 */       setDead();
/*     */     }
/*     */     
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesItem(DataFixer fixer) {
/* 342 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityItem.class, new String[] { "Item" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 350 */     compound.setShort("Health", (short)this.health);
/* 351 */     compound.setShort("Age", (short)this.age);
/* 352 */     compound.setShort("PickupDelay", (short)this.delayBeforeCanPickup);
/*     */     
/* 354 */     if (getThrower() != null)
/*     */     {
/* 356 */       compound.setString("Thrower", this.thrower);
/*     */     }
/*     */     
/* 359 */     if (getOwner() != null)
/*     */     {
/* 361 */       compound.setString("Owner", this.owner);
/*     */     }
/*     */     
/* 364 */     if (!getEntityItem().func_190926_b())
/*     */     {
/* 366 */       compound.setTag("Item", (NBTBase)getEntityItem().writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 375 */     this.health = compound.getShort("Health");
/* 376 */     this.age = compound.getShort("Age");
/*     */     
/* 378 */     if (compound.hasKey("PickupDelay"))
/*     */     {
/* 380 */       this.delayBeforeCanPickup = compound.getShort("PickupDelay");
/*     */     }
/*     */     
/* 383 */     if (compound.hasKey("Owner"))
/*     */     {
/* 385 */       this.owner = compound.getString("Owner");
/*     */     }
/*     */     
/* 388 */     if (compound.hasKey("Thrower"))
/*     */     {
/* 390 */       this.thrower = compound.getString("Thrower");
/*     */     }
/*     */     
/* 393 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
/* 394 */     setEntityItemStack(new ItemStack(nbttagcompound));
/*     */     
/* 396 */     if (getEntityItem().func_190926_b())
/*     */     {
/* 398 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 407 */     if (!this.world.isRemote) {
/*     */       
/* 409 */       ItemStack itemstack = getEntityItem();
/* 410 */       Item item = itemstack.getItem();
/* 411 */       int i = itemstack.func_190916_E();
/*     */       
/* 413 */       if (this.delayBeforeCanPickup == 0 && (this.owner == null || 6000 - this.age <= 200 || this.owner.equals(entityIn.getName())) && entityIn.inventory.addItemStackToInventory(itemstack)) {
/*     */         
/* 415 */         entityIn.onItemPickup(this, i);
/*     */         
/* 417 */         if (itemstack.func_190926_b()) {
/*     */           
/* 419 */           setDead();
/* 420 */           itemstack.func_190920_e(i);
/*     */         } 
/*     */         
/* 423 */         entityIn.addStat(StatList.getObjectsPickedUpStats(item), i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 433 */     return hasCustomName() ? getCustomNameTag() : I18n.translateToLocal("item." + getEntityItem().getUnlocalizedName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn) {
/* 447 */     Entity entity = super.changeDimension(dimensionIn);
/*     */     
/* 449 */     if (!this.world.isRemote && entity instanceof EntityItem)
/*     */     {
/* 451 */       ((EntityItem)entity).searchForOtherItemsNearby();
/*     */     }
/*     */     
/* 454 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEntityItem() {
/* 463 */     return (ItemStack)getDataManager().get(ITEM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityItemStack(ItemStack stack) {
/* 471 */     getDataManager().set(ITEM, stack);
/* 472 */     getDataManager().setDirty(ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwner() {
/* 477 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(String owner) {
/* 482 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getThrower() {
/* 487 */     return this.thrower;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrower(String thrower) {
/* 492 */     this.thrower = thrower;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAge() {
/* 497 */     return this.age;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultPickupDelay() {
/* 502 */     this.delayBeforeCanPickup = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNoPickupDelay() {
/* 507 */     this.delayBeforeCanPickup = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInfinitePickupDelay() {
/* 512 */     this.delayBeforeCanPickup = 32767;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPickupDelay(int ticks) {
/* 517 */     this.delayBeforeCanPickup = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cannotPickup() {
/* 522 */     return (this.delayBeforeCanPickup > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNoDespawn() {
/* 527 */     this.age = -6000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeFakeItem() {
/* 532 */     setInfinitePickupDelay();
/* 533 */     this.age = 5999;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
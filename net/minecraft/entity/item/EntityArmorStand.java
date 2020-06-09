/*      */ package net.minecraft.entity.item;
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.EnumPushReaction;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Rotations;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ public class EntityArmorStand extends EntityLivingBase {
/*   42 */   private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*   43 */   private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*   44 */   private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
/*   45 */   private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
/*   46 */   private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
/*   47 */   private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
/*   48 */   public static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.BYTE);
/*   49 */   public static final DataParameter<Rotations> HEAD_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   50 */   public static final DataParameter<Rotations> BODY_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   51 */   public static final DataParameter<Rotations> LEFT_ARM_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   52 */   public static final DataParameter<Rotations> RIGHT_ARM_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   53 */   public static final DataParameter<Rotations> LEFT_LEG_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   54 */   public static final DataParameter<Rotations> RIGHT_LEG_ROTATION = EntityDataManager.createKey(EntityArmorStand.class, DataSerializers.ROTATIONS);
/*   55 */   private static final Predicate<Entity> IS_RIDEABLE_MINECART = new Predicate<Entity>()
/*      */     {
/*      */       public boolean apply(@Nullable Entity p_apply_1_)
/*      */       {
/*   59 */         return (p_apply_1_ instanceof EntityMinecart && ((EntityMinecart)p_apply_1_).getType() == EntityMinecart.Type.RIDEABLE);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private final NonNullList<ItemStack> handItems;
/*      */   
/*      */   private final NonNullList<ItemStack> armorItems;
/*      */   
/*      */   private boolean canInteract;
/*      */   
/*      */   public long punchCooldown;
/*      */   private int disabledSlots;
/*      */   private boolean wasMarker;
/*      */   private Rotations headRotation;
/*      */   private Rotations bodyRotation;
/*      */   private Rotations leftArmRotation;
/*      */   private Rotations rightArmRotation;
/*      */   private Rotations leftLegRotation;
/*      */   private Rotations rightLegRotation;
/*      */   
/*      */   public EntityArmorStand(World worldIn) {
/*   81 */     super(worldIn);
/*   82 */     this.handItems = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
/*   83 */     this.armorItems = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
/*   84 */     this.headRotation = DEFAULT_HEAD_ROTATION;
/*   85 */     this.bodyRotation = DEFAULT_BODY_ROTATION;
/*   86 */     this.leftArmRotation = DEFAULT_LEFTARM_ROTATION;
/*   87 */     this.rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
/*   88 */     this.leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
/*   89 */     this.rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
/*   90 */     this.noClip = hasNoGravity();
/*   91 */     setSize(0.5F, 1.975F);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityArmorStand(World worldIn, double posX, double posY, double posZ) {
/*   96 */     this(worldIn);
/*   97 */     setPosition(posX, posY, posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setSize(float width, float height) {
/*  105 */     double d0 = this.posX;
/*  106 */     double d1 = this.posY;
/*  107 */     double d2 = this.posZ;
/*  108 */     float f = hasMarker() ? 0.0F : (isChild() ? 0.5F : 1.0F);
/*  109 */     super.setSize(width * f, height * f);
/*  110 */     setPosition(d0, d1, d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/*  118 */     return (super.isServerWorld() && !hasNoGravity());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  123 */     super.entityInit();
/*  124 */     this.dataManager.register(STATUS, Byte.valueOf((byte)0));
/*  125 */     this.dataManager.register(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
/*  126 */     this.dataManager.register(BODY_ROTATION, DEFAULT_BODY_ROTATION);
/*  127 */     this.dataManager.register(LEFT_ARM_ROTATION, DEFAULT_LEFTARM_ROTATION);
/*  128 */     this.dataManager.register(RIGHT_ARM_ROTATION, DEFAULT_RIGHTARM_ROTATION);
/*  129 */     this.dataManager.register(LEFT_LEG_ROTATION, DEFAULT_LEFTLEG_ROTATION);
/*  130 */     this.dataManager.register(RIGHT_LEG_ROTATION, DEFAULT_RIGHTLEG_ROTATION);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHeldEquipment() {
/*  135 */     return (Iterable<ItemStack>)this.handItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorInventoryList() {
/*  140 */     return (Iterable<ItemStack>)this.armorItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
/*  145 */     switch (slotIn.getSlotType()) {
/*      */       
/*      */       case HAND:
/*  148 */         return (ItemStack)this.handItems.get(slotIn.getIndex());
/*      */       
/*      */       case null:
/*  151 */         return (ItemStack)this.armorItems.get(slotIn.getIndex());
/*      */     } 
/*      */     
/*  154 */     return ItemStack.field_190927_a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
/*  160 */     switch (slotIn.getSlotType()) {
/*      */       
/*      */       case HAND:
/*  163 */         playEquipSound(stack);
/*  164 */         this.handItems.set(slotIn.getIndex(), stack);
/*      */         break;
/*      */       
/*      */       case null:
/*  168 */         playEquipSound(stack);
/*  169 */         this.armorItems.set(slotIn.getIndex(), stack);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     EntityEquipmentSlot entityequipmentslot;
/*  177 */     if (inventorySlot == 98) {
/*      */       
/*  179 */       entityequipmentslot = EntityEquipmentSlot.MAINHAND;
/*      */     }
/*  181 */     else if (inventorySlot == 99) {
/*      */       
/*  183 */       entityequipmentslot = EntityEquipmentSlot.OFFHAND;
/*      */     }
/*  185 */     else if (inventorySlot == 100 + EntityEquipmentSlot.HEAD.getIndex()) {
/*      */       
/*  187 */       entityequipmentslot = EntityEquipmentSlot.HEAD;
/*      */     }
/*  189 */     else if (inventorySlot == 100 + EntityEquipmentSlot.CHEST.getIndex()) {
/*      */       
/*  191 */       entityequipmentslot = EntityEquipmentSlot.CHEST;
/*      */     }
/*  193 */     else if (inventorySlot == 100 + EntityEquipmentSlot.LEGS.getIndex()) {
/*      */       
/*  195 */       entityequipmentslot = EntityEquipmentSlot.LEGS;
/*      */     }
/*      */     else {
/*      */       
/*  199 */       if (inventorySlot != 100 + EntityEquipmentSlot.FEET.getIndex())
/*      */       {
/*  201 */         return false;
/*      */       }
/*      */       
/*  204 */       entityequipmentslot = EntityEquipmentSlot.FEET;
/*      */     } 
/*      */     
/*  207 */     if (!itemStackIn.func_190926_b() && !EntityLiving.isItemStackInSlot(entityequipmentslot, itemStackIn) && entityequipmentslot != EntityEquipmentSlot.HEAD)
/*      */     {
/*  209 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  213 */     setItemStackToSlot(entityequipmentslot, itemStackIn);
/*  214 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerFixesArmorStand(DataFixer fixer) {
/*  220 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackDataLists(EntityArmorStand.class, new String[] { "ArmorItems", "HandItems" }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  228 */     super.writeEntityToNBT(compound);
/*  229 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  231 */     for (ItemStack itemstack : this.armorItems) {
/*      */       
/*  233 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  235 */       if (!itemstack.func_190926_b())
/*      */       {
/*  237 */         itemstack.writeToNBT(nbttagcompound);
/*      */       }
/*      */       
/*  240 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  243 */     compound.setTag("ArmorItems", (NBTBase)nbttaglist);
/*  244 */     NBTTagList nbttaglist1 = new NBTTagList();
/*      */     
/*  246 */     for (ItemStack itemstack1 : this.handItems) {
/*      */       
/*  248 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */       
/*  250 */       if (!itemstack1.func_190926_b())
/*      */       {
/*  252 */         itemstack1.writeToNBT(nbttagcompound1);
/*      */       }
/*      */       
/*  255 */       nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  258 */     compound.setTag("HandItems", (NBTBase)nbttaglist1);
/*  259 */     compound.setBoolean("Invisible", isInvisible());
/*  260 */     compound.setBoolean("Small", isSmall());
/*  261 */     compound.setBoolean("ShowArms", getShowArms());
/*  262 */     compound.setInteger("DisabledSlots", this.disabledSlots);
/*  263 */     compound.setBoolean("NoBasePlate", hasNoBasePlate());
/*      */     
/*  265 */     if (hasMarker())
/*      */     {
/*  267 */       compound.setBoolean("Marker", hasMarker());
/*      */     }
/*      */     
/*  270 */     compound.setTag("Pose", (NBTBase)readPoseFromNBT());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  278 */     super.readEntityFromNBT(compound);
/*      */     
/*  280 */     if (compound.hasKey("ArmorItems", 9)) {
/*      */       
/*  282 */       NBTTagList nbttaglist = compound.getTagList("ArmorItems", 10);
/*      */       
/*  284 */       for (int i = 0; i < this.armorItems.size(); i++)
/*      */       {
/*  286 */         this.armorItems.set(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
/*      */       }
/*      */     } 
/*      */     
/*  290 */     if (compound.hasKey("HandItems", 9)) {
/*      */       
/*  292 */       NBTTagList nbttaglist1 = compound.getTagList("HandItems", 10);
/*      */       
/*  294 */       for (int j = 0; j < this.handItems.size(); j++)
/*      */       {
/*  296 */         this.handItems.set(j, new ItemStack(nbttaglist1.getCompoundTagAt(j)));
/*      */       }
/*      */     } 
/*      */     
/*  300 */     setInvisible(compound.getBoolean("Invisible"));
/*  301 */     setSmall(compound.getBoolean("Small"));
/*  302 */     setShowArms(compound.getBoolean("ShowArms"));
/*  303 */     this.disabledSlots = compound.getInteger("DisabledSlots");
/*  304 */     setNoBasePlate(compound.getBoolean("NoBasePlate"));
/*  305 */     setMarker(compound.getBoolean("Marker"));
/*  306 */     this.wasMarker = !hasMarker();
/*  307 */     this.noClip = hasNoGravity();
/*  308 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("Pose");
/*  309 */     writePoseToNBT(nbttagcompound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writePoseToNBT(NBTTagCompound tagCompound) {
/*  317 */     NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);
/*  318 */     setHeadRotation(nbttaglist.hasNoTags() ? DEFAULT_HEAD_ROTATION : new Rotations(nbttaglist));
/*  319 */     NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);
/*  320 */     setBodyRotation(nbttaglist1.hasNoTags() ? DEFAULT_BODY_ROTATION : new Rotations(nbttaglist1));
/*  321 */     NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);
/*  322 */     setLeftArmRotation(nbttaglist2.hasNoTags() ? DEFAULT_LEFTARM_ROTATION : new Rotations(nbttaglist2));
/*  323 */     NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);
/*  324 */     setRightArmRotation(nbttaglist3.hasNoTags() ? DEFAULT_RIGHTARM_ROTATION : new Rotations(nbttaglist3));
/*  325 */     NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);
/*  326 */     setLeftLegRotation(nbttaglist4.hasNoTags() ? DEFAULT_LEFTLEG_ROTATION : new Rotations(nbttaglist4));
/*  327 */     NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);
/*  328 */     setRightLegRotation(nbttaglist5.hasNoTags() ? DEFAULT_RIGHTLEG_ROTATION : new Rotations(nbttaglist5));
/*      */   }
/*      */ 
/*      */   
/*      */   private NBTTagCompound readPoseFromNBT() {
/*  333 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */     
/*  335 */     if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation))
/*      */     {
/*  337 */       nbttagcompound.setTag("Head", (NBTBase)this.headRotation.writeToNBT());
/*      */     }
/*      */     
/*  340 */     if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation))
/*      */     {
/*  342 */       nbttagcompound.setTag("Body", (NBTBase)this.bodyRotation.writeToNBT());
/*      */     }
/*      */     
/*  345 */     if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation))
/*      */     {
/*  347 */       nbttagcompound.setTag("LeftArm", (NBTBase)this.leftArmRotation.writeToNBT());
/*      */     }
/*      */     
/*  350 */     if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation))
/*      */     {
/*  352 */       nbttagcompound.setTag("RightArm", (NBTBase)this.rightArmRotation.writeToNBT());
/*      */     }
/*      */     
/*  355 */     if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation))
/*      */     {
/*  357 */       nbttagcompound.setTag("LeftLeg", (NBTBase)this.leftLegRotation.writeToNBT());
/*      */     }
/*      */     
/*  360 */     if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation))
/*      */     {
/*  362 */       nbttagcompound.setTag("RightLeg", (NBTBase)this.rightLegRotation.writeToNBT());
/*      */     }
/*      */     
/*  365 */     return nbttagcompound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  373 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void collideWithEntity(Entity entityIn) {}
/*      */ 
/*      */   
/*      */   protected void collideWithNearbyEntities() {
/*  382 */     List<Entity> list = this.world.getEntitiesInAABBexcluding((Entity)this, getEntityBoundingBox(), IS_RIDEABLE_MINECART);
/*      */     
/*  384 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  386 */       Entity entity = list.get(i);
/*      */       
/*  388 */       if (getDistanceSqToEntity(entity) <= 0.2D)
/*      */       {
/*  390 */         entity.applyEntityCollision((Entity)this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand stack) {
/*  400 */     ItemStack itemstack = player.getHeldItem(stack);
/*      */     
/*  402 */     if (!hasMarker() && itemstack.getItem() != Items.NAME_TAG) {
/*      */       
/*  404 */       if (!this.world.isRemote && !player.isSpectator()) {
/*      */         
/*  406 */         EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
/*      */         
/*  408 */         if (itemstack.func_190926_b()) {
/*      */           
/*  410 */           EntityEquipmentSlot entityequipmentslot1 = func_190772_a(vec);
/*  411 */           EntityEquipmentSlot entityequipmentslot2 = isDisabled(entityequipmentslot1) ? entityequipmentslot : entityequipmentslot1;
/*      */           
/*  413 */           if (func_190630_a(entityequipmentslot2))
/*      */           {
/*  415 */             swapItem(player, entityequipmentslot2, itemstack, stack);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  420 */           if (isDisabled(entityequipmentslot))
/*      */           {
/*  422 */             return EnumActionResult.FAIL;
/*      */           }
/*      */           
/*  425 */           if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.HAND && !getShowArms())
/*      */           {
/*  427 */             return EnumActionResult.FAIL;
/*      */           }
/*      */           
/*  430 */           swapItem(player, entityequipmentslot, itemstack, stack);
/*      */         } 
/*      */         
/*  433 */         return EnumActionResult.SUCCESS;
/*      */       } 
/*      */ 
/*      */       
/*  437 */       return EnumActionResult.SUCCESS;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  442 */     return EnumActionResult.PASS;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityEquipmentSlot func_190772_a(Vec3d p_190772_1_) {
/*  448 */     EntityEquipmentSlot entityequipmentslot = EntityEquipmentSlot.MAINHAND;
/*  449 */     boolean flag = isSmall();
/*  450 */     double d0 = flag ? (p_190772_1_.yCoord * 2.0D) : p_190772_1_.yCoord;
/*  451 */     EntityEquipmentSlot entityequipmentslot1 = EntityEquipmentSlot.FEET;
/*      */     
/*  453 */     if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) && func_190630_a(entityequipmentslot1)) {
/*      */       
/*  455 */       entityequipmentslot = EntityEquipmentSlot.FEET;
/*      */     }
/*  457 */     else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D) && func_190630_a(EntityEquipmentSlot.CHEST)) {
/*      */       
/*  459 */       entityequipmentslot = EntityEquipmentSlot.CHEST;
/*      */     }
/*  461 */     else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) && func_190630_a(EntityEquipmentSlot.LEGS)) {
/*      */       
/*  463 */       entityequipmentslot = EntityEquipmentSlot.LEGS;
/*      */     }
/*  465 */     else if (d0 >= 1.6D && func_190630_a(EntityEquipmentSlot.HEAD)) {
/*      */       
/*  467 */       entityequipmentslot = EntityEquipmentSlot.HEAD;
/*      */     } 
/*      */     
/*  470 */     return entityequipmentslot;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isDisabled(EntityEquipmentSlot slotIn) {
/*  475 */     return ((this.disabledSlots & 1 << slotIn.getSlotIndex()) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void swapItem(EntityPlayer player, EntityEquipmentSlot p_184795_2_, ItemStack p_184795_3_, EnumHand hand) {
/*  480 */     ItemStack itemstack = getItemStackFromSlot(p_184795_2_);
/*      */     
/*  482 */     if (itemstack.func_190926_b() || (this.disabledSlots & 1 << p_184795_2_.getSlotIndex() + 8) == 0)
/*      */     {
/*  484 */       if (!itemstack.func_190926_b() || (this.disabledSlots & 1 << p_184795_2_.getSlotIndex() + 16) == 0)
/*      */       {
/*  486 */         if (player.capabilities.isCreativeMode && itemstack.func_190926_b() && !p_184795_3_.func_190926_b()) {
/*      */           
/*  488 */           ItemStack itemstack2 = p_184795_3_.copy();
/*  489 */           itemstack2.func_190920_e(1);
/*  490 */           setItemStackToSlot(p_184795_2_, itemstack2);
/*      */         }
/*  492 */         else if (!p_184795_3_.func_190926_b() && p_184795_3_.func_190916_E() > 1) {
/*      */           
/*  494 */           if (itemstack.func_190926_b())
/*      */           {
/*  496 */             ItemStack itemstack1 = p_184795_3_.copy();
/*  497 */             itemstack1.func_190920_e(1);
/*  498 */             setItemStackToSlot(p_184795_2_, itemstack1);
/*  499 */             p_184795_3_.func_190918_g(1);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  504 */           setItemStackToSlot(p_184795_2_, p_184795_3_);
/*  505 */           player.setHeldItem(hand, itemstack);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  516 */     if (!this.world.isRemote && !this.isDead) {
/*      */       
/*  518 */       if (DamageSource.outOfWorld.equals(source)) {
/*      */         
/*  520 */         setDead();
/*  521 */         return false;
/*      */       } 
/*  523 */       if (!isEntityInvulnerable(source) && !this.canInteract && !hasMarker()) {
/*      */         
/*  525 */         if (source.isExplosion()) {
/*      */           
/*  527 */           dropContents();
/*  528 */           setDead();
/*  529 */           return false;
/*      */         } 
/*  531 */         if (DamageSource.inFire.equals(source)) {
/*      */           
/*  533 */           if (isBurning()) {
/*      */             
/*  535 */             damageArmorStand(0.15F);
/*      */           }
/*      */           else {
/*      */             
/*  539 */             setFire(5);
/*      */           } 
/*      */           
/*  542 */           return false;
/*      */         } 
/*  544 */         if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
/*      */           
/*  546 */           damageArmorStand(4.0F);
/*  547 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  551 */         boolean flag = "arrow".equals(source.getDamageType());
/*  552 */         boolean flag1 = "player".equals(source.getDamageType());
/*      */         
/*  554 */         if (!flag1 && !flag)
/*      */         {
/*  556 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  560 */         if (source.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityArrow)
/*      */         {
/*  562 */           source.getSourceOfDamage().setDead();
/*      */         }
/*      */         
/*  565 */         if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer)source.getEntity()).capabilities.allowEdit)
/*      */         {
/*  567 */           return false;
/*      */         }
/*  569 */         if (source.isCreativePlayer()) {
/*      */           
/*  571 */           func_190773_I();
/*  572 */           playParticles();
/*  573 */           setDead();
/*  574 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  578 */         long i = this.world.getTotalWorldTime();
/*      */         
/*  580 */         if (i - this.punchCooldown > 5L && !flag) {
/*      */           
/*  582 */           this.world.setEntityState((Entity)this, (byte)32);
/*  583 */           this.punchCooldown = i;
/*      */         }
/*      */         else {
/*      */           
/*  587 */           dropBlock();
/*  588 */           playParticles();
/*  589 */           setDead();
/*      */         } 
/*      */         
/*  592 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  599 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  604 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  610 */     if (id == 32) {
/*      */       
/*  612 */       if (this.world.isRemote)
/*      */       {
/*  614 */         this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARMORSTAND_HIT, getSoundCategory(), 0.3F, 1.0F, false);
/*  615 */         this.punchCooldown = this.world.getTotalWorldTime();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  620 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/*  629 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*      */     
/*  631 */     if (Double.isNaN(d0) || d0 == 0.0D)
/*      */     {
/*  633 */       d0 = 4.0D;
/*      */     }
/*      */     
/*  636 */     d0 *= 64.0D;
/*  637 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void playParticles() {
/*  642 */     if (this.world instanceof WorldServer)
/*      */     {
/*  644 */       ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, (this.width / 4.0F), (this.height / 4.0F), (this.width / 4.0F), 0.05D, new int[] { Block.getStateId(Blocks.PLANKS.getDefaultState()) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void damageArmorStand(float damage) {
/*  650 */     float f = getHealth();
/*  651 */     f -= damage;
/*      */     
/*  653 */     if (f <= 0.5F) {
/*      */       
/*  655 */       dropContents();
/*  656 */       setDead();
/*      */     }
/*      */     else {
/*      */       
/*  660 */       setHealth(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void dropBlock() {
/*  666 */     Block.spawnAsEntity(this.world, new BlockPos((Entity)this), new ItemStack((Item)Items.ARMOR_STAND));
/*  667 */     dropContents();
/*      */   }
/*      */ 
/*      */   
/*      */   private void dropContents() {
/*  672 */     func_190773_I();
/*      */     
/*  674 */     for (int i = 0; i < this.handItems.size(); i++) {
/*      */       
/*  676 */       ItemStack itemstack = (ItemStack)this.handItems.get(i);
/*      */       
/*  678 */       if (!itemstack.func_190926_b()) {
/*      */         
/*  680 */         Block.spawnAsEntity(this.world, (new BlockPos((Entity)this)).up(), itemstack);
/*  681 */         this.handItems.set(i, ItemStack.field_190927_a);
/*      */       } 
/*      */     } 
/*      */     
/*  685 */     for (int j = 0; j < this.armorItems.size(); j++) {
/*      */       
/*  687 */       ItemStack itemstack1 = (ItemStack)this.armorItems.get(j);
/*      */       
/*  689 */       if (!itemstack1.func_190926_b()) {
/*      */         
/*  691 */         Block.spawnAsEntity(this.world, (new BlockPos((Entity)this)).up(), itemstack1);
/*  692 */         this.armorItems.set(j, ItemStack.field_190927_a);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_190773_I() {
/*  699 */     this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARMORSTAND_BREAK, getSoundCategory(), 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/*  704 */     this.prevRenderYawOffset = this.prevRotationYaw;
/*  705 */     this.renderYawOffset = this.rotationYaw;
/*  706 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/*  711 */     return isChild() ? (this.height * 0.5F) : (this.height * 0.9F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/*  719 */     return hasMarker() ? 0.0D : 0.10000000149011612D;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/*  724 */     if (!hasNoGravity())
/*      */     {
/*  726 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {
/*  735 */     this.prevRenderYawOffset = this.prevRotationYaw = offset;
/*  736 */     this.prevRotationYawHead = this.rotationYawHead = offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {
/*  744 */     this.prevRenderYawOffset = this.prevRotationYaw = rotation;
/*  745 */     this.prevRotationYawHead = this.rotationYawHead = rotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  753 */     super.onUpdate();
/*  754 */     Rotations rotations = (Rotations)this.dataManager.get(HEAD_ROTATION);
/*      */     
/*  756 */     if (!this.headRotation.equals(rotations))
/*      */     {
/*  758 */       setHeadRotation(rotations);
/*      */     }
/*      */     
/*  761 */     Rotations rotations1 = (Rotations)this.dataManager.get(BODY_ROTATION);
/*      */     
/*  763 */     if (!this.bodyRotation.equals(rotations1))
/*      */     {
/*  765 */       setBodyRotation(rotations1);
/*      */     }
/*      */     
/*  768 */     Rotations rotations2 = (Rotations)this.dataManager.get(LEFT_ARM_ROTATION);
/*      */     
/*  770 */     if (!this.leftArmRotation.equals(rotations2))
/*      */     {
/*  772 */       setLeftArmRotation(rotations2);
/*      */     }
/*      */     
/*  775 */     Rotations rotations3 = (Rotations)this.dataManager.get(RIGHT_ARM_ROTATION);
/*      */     
/*  777 */     if (!this.rightArmRotation.equals(rotations3))
/*      */     {
/*  779 */       setRightArmRotation(rotations3);
/*      */     }
/*      */     
/*  782 */     Rotations rotations4 = (Rotations)this.dataManager.get(LEFT_LEG_ROTATION);
/*      */     
/*  784 */     if (!this.leftLegRotation.equals(rotations4))
/*      */     {
/*  786 */       setLeftLegRotation(rotations4);
/*      */     }
/*      */     
/*  789 */     Rotations rotations5 = (Rotations)this.dataManager.get(RIGHT_LEG_ROTATION);
/*      */     
/*  791 */     if (!this.rightLegRotation.equals(rotations5))
/*      */     {
/*  793 */       setRightLegRotation(rotations5);
/*      */     }
/*      */     
/*  796 */     boolean flag = hasMarker();
/*      */     
/*  798 */     if (this.wasMarker != flag) {
/*      */       
/*  800 */       updateBoundingBox(flag);
/*  801 */       this.preventEntitySpawning = !flag;
/*  802 */       this.wasMarker = flag;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateBoundingBox(boolean p_181550_1_) {
/*  808 */     if (p_181550_1_) {
/*      */       
/*  810 */       setSize(0.0F, 0.0F);
/*      */     }
/*      */     else {
/*      */       
/*  814 */       setSize(0.5F, 1.975F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/*  824 */     setInvisible(this.canInteract);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/*  829 */     this.canInteract = invisible;
/*  830 */     super.setInvisible(invisible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChild() {
/*  838 */     return isSmall();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  846 */     setDead();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/*  851 */     return isInvisible();
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumPushReaction getPushReaction() {
/*  856 */     return hasMarker() ? EnumPushReaction.IGNORE : super.getPushReaction();
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSmall(boolean small) {
/*  861 */     this.dataManager.set(STATUS, Byte.valueOf(setBit(((Byte)this.dataManager.get(STATUS)).byteValue(), 1, small)));
/*  862 */     setSize(0.5F, 1.975F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSmall() {
/*  867 */     return ((((Byte)this.dataManager.get(STATUS)).byteValue() & 0x1) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setShowArms(boolean showArms) {
/*  872 */     this.dataManager.set(STATUS, Byte.valueOf(setBit(((Byte)this.dataManager.get(STATUS)).byteValue(), 4, showArms)));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getShowArms() {
/*  877 */     return ((((Byte)this.dataManager.get(STATUS)).byteValue() & 0x4) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setNoBasePlate(boolean noBasePlate) {
/*  882 */     this.dataManager.set(STATUS, Byte.valueOf(setBit(((Byte)this.dataManager.get(STATUS)).byteValue(), 8, noBasePlate)));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNoBasePlate() {
/*  887 */     return ((((Byte)this.dataManager.get(STATUS)).byteValue() & 0x8) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setMarker(boolean marker) {
/*  895 */     this.dataManager.set(STATUS, Byte.valueOf(setBit(((Byte)this.dataManager.get(STATUS)).byteValue(), 16, marker)));
/*  896 */     setSize(0.5F, 1.975F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMarker() {
/*  905 */     return ((((Byte)this.dataManager.get(STATUS)).byteValue() & 0x10) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
/*  910 */     if (p_184797_3_) {
/*      */       
/*  912 */       p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
/*      */     }
/*      */     else {
/*      */       
/*  916 */       p_184797_1_ = (byte)(p_184797_1_ & (p_184797_2_ ^ 0xFFFFFFFF));
/*      */     } 
/*      */     
/*  919 */     return p_184797_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeadRotation(Rotations vec) {
/*  924 */     this.headRotation = vec;
/*  925 */     this.dataManager.set(HEAD_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBodyRotation(Rotations vec) {
/*  930 */     this.bodyRotation = vec;
/*  931 */     this.dataManager.set(BODY_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLeftArmRotation(Rotations vec) {
/*  936 */     this.leftArmRotation = vec;
/*  937 */     this.dataManager.set(LEFT_ARM_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRightArmRotation(Rotations vec) {
/*  942 */     this.rightArmRotation = vec;
/*  943 */     this.dataManager.set(RIGHT_ARM_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLeftLegRotation(Rotations vec) {
/*  948 */     this.leftLegRotation = vec;
/*  949 */     this.dataManager.set(LEFT_LEG_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRightLegRotation(Rotations vec) {
/*  954 */     this.rightLegRotation = vec;
/*  955 */     this.dataManager.set(RIGHT_LEG_ROTATION, vec);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getHeadRotation() {
/*  960 */     return this.headRotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getBodyRotation() {
/*  965 */     return this.bodyRotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getLeftArmRotation() {
/*  970 */     return this.leftArmRotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getRightArmRotation() {
/*  975 */     return this.rightArmRotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getLeftLegRotation() {
/*  980 */     return this.leftLegRotation;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rotations getRightLegRotation() {
/*  985 */     return this.rightLegRotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/*  993 */     return (super.canBeCollidedWith() && !hasMarker());
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumHandSide getPrimaryHand() {
/*  998 */     return EnumHandSide.RIGHT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getFallSound(int heightIn) {
/* 1003 */     return SoundEvents.ENTITY_ARMORSTAND_FALL;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 1009 */     return SoundEvents.ENTITY_ARMORSTAND_HIT;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/* 1015 */     return SoundEvents.ENTITY_ARMORSTAND_BREAK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeHitWithPotion() {
/* 1030 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 1035 */     if (STATUS.equals(key))
/*      */     {
/* 1037 */       setSize(0.5F, 1.975F);
/*      */     }
/*      */     
/* 1040 */     super.notifyDataManagerChange(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190631_cK() {
/* 1045 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.entity.passive;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityHorse extends AbstractHorse {
/*  32 */   private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
/*  33 */   public static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(EntityHorse.class, DataSerializers.VARINT);
/*  34 */   public static final DataParameter<Integer> HORSE_ARMOR = EntityDataManager.createKey(EntityHorse.class, DataSerializers.VARINT);
/*  35 */   private static final String[] HORSE_TEXTURES = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*  36 */   private static final String[] HORSE_TEXTURES_ABBR = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*  37 */   private static final String[] HORSE_MARKING_TEXTURES = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*  38 */   private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
/*     */   private String texturePrefix;
/*  40 */   private final String[] horseTexturesArray = new String[3];
/*     */ 
/*     */   
/*     */   public EntityHorse(World worldIn) {
/*  44 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  49 */     super.entityInit();
/*  50 */     this.dataManager.register(HORSE_VARIANT, Integer.valueOf(0));
/*  51 */     this.dataManager.register(HORSE_ARMOR, Integer.valueOf(HorseArmorType.NONE.getOrdinal()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesHorse(DataFixer fixer) {
/*  56 */     AbstractHorse.func_190683_c(fixer, EntityHorse.class);
/*  57 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityHorse.class, new String[] { "ArmorItem" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  65 */     super.writeEntityToNBT(compound);
/*  66 */     compound.setInteger("Variant", getHorseVariant());
/*     */     
/*  68 */     if (!this.horseChest.getStackInSlot(1).func_190926_b())
/*     */     {
/*  70 */       compound.setTag("ArmorItem", (NBTBase)this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  79 */     super.readEntityFromNBT(compound);
/*  80 */     setHorseVariant(compound.getInteger("Variant"));
/*     */     
/*  82 */     if (compound.hasKey("ArmorItem", 10)) {
/*     */       
/*  84 */       ItemStack itemstack = new ItemStack(compound.getCompoundTag("ArmorItem"));
/*     */       
/*  86 */       if (!itemstack.func_190926_b() && HorseArmorType.isHorseArmor(itemstack.getItem()))
/*     */       {
/*  88 */         this.horseChest.setInventorySlotContents(1, itemstack);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     updateHorseSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHorseVariant(int variant) {
/*  97 */     this.dataManager.set(HORSE_VARIANT, Integer.valueOf(variant));
/*  98 */     resetTexturePrefix();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHorseVariant() {
/* 103 */     return ((Integer)this.dataManager.get(HORSE_VARIANT)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetTexturePrefix() {
/* 108 */     this.texturePrefix = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setHorseTexturePaths() {
/* 113 */     int i = getHorseVariant();
/* 114 */     int j = (i & 0xFF) % 7;
/* 115 */     int k = ((i & 0xFF00) >> 8) % 5;
/* 116 */     HorseArmorType horsearmortype = getHorseArmorType();
/* 117 */     this.horseTexturesArray[0] = HORSE_TEXTURES[j];
/* 118 */     this.horseTexturesArray[1] = HORSE_MARKING_TEXTURES[k];
/* 119 */     this.horseTexturesArray[2] = horsearmortype.getTextureName();
/* 120 */     this.texturePrefix = "horse/" + HORSE_TEXTURES_ABBR[j] + HORSE_MARKING_TEXTURES_ABBR[k] + horsearmortype.getHash();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHorseTexture() {
/* 125 */     if (this.texturePrefix == null)
/*     */     {
/* 127 */       setHorseTexturePaths();
/*     */     }
/*     */     
/* 130 */     return this.texturePrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getVariantTexturePaths() {
/* 135 */     if (this.texturePrefix == null)
/*     */     {
/* 137 */       setHorseTexturePaths();
/*     */     }
/*     */     
/* 140 */     return this.horseTexturesArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateHorseSlots() {
/* 148 */     super.updateHorseSlots();
/* 149 */     setHorseArmorStack(this.horseChest.getStackInSlot(1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorseArmorStack(ItemStack itemStackIn) {
/* 157 */     HorseArmorType horsearmortype = HorseArmorType.getByItemStack(itemStackIn);
/* 158 */     this.dataManager.set(HORSE_ARMOR, Integer.valueOf(horsearmortype.getOrdinal()));
/* 159 */     resetTexturePrefix();
/*     */     
/* 161 */     if (!this.world.isRemote) {
/*     */       
/* 163 */       getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
/* 164 */       int i = horsearmortype.getProtection();
/*     */       
/* 166 */       if (i != 0)
/*     */       {
/* 168 */         getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier((new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", i, 0)).setSaved(false));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public HorseArmorType getHorseArmorType() {
/* 175 */     return HorseArmorType.getByOrdinal(((Integer)this.dataManager.get(HORSE_ARMOR)).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInventoryChanged(IInventory invBasic) {
/* 183 */     HorseArmorType horsearmortype = getHorseArmorType();
/* 184 */     super.onInventoryChanged(invBasic);
/* 185 */     HorseArmorType horsearmortype1 = getHorseArmorType();
/*     */     
/* 187 */     if (this.ticksExisted > 20 && horsearmortype != horsearmortype1 && horsearmortype1 != HorseArmorType.NONE)
/*     */     {
/* 189 */       playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190680_a(SoundType p_190680_1_) {
/* 195 */     super.func_190680_a(p_190680_1_);
/*     */     
/* 197 */     if (this.rand.nextInt(10) == 0)
/*     */     {
/* 199 */       playSound(SoundEvents.ENTITY_HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 205 */     super.applyEntityAttributes();
/* 206 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getModifiedMaxHealth());
/* 207 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getModifiedMovementSpeed());
/* 208 */     getEntityAttribute(JUMP_STRENGTH).setBaseValue(getModifiedJumpStrength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 216 */     super.onUpdate();
/*     */     
/* 218 */     if (this.world.isRemote && this.dataManager.isDirty()) {
/*     */       
/* 220 */       this.dataManager.setClean();
/* 221 */       resetTexturePrefix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 227 */     super.getAmbientSound();
/* 228 */     return SoundEvents.ENTITY_HORSE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 233 */     super.getDeathSound();
/* 234 */     return SoundEvents.ENTITY_HORSE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 239 */     super.getHurtSound(p_184601_1_);
/* 240 */     return SoundEvents.ENTITY_HORSE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAngrySound() {
/* 245 */     super.getAngrySound();
/* 246 */     return SoundEvents.ENTITY_HORSE_ANGRY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getLootTable() {
/* 251 */     return LootTableList.ENTITIES_HORSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 256 */     ItemStack itemstack = player.getHeldItem(hand);
/* 257 */     boolean flag = !itemstack.func_190926_b();
/*     */     
/* 259 */     if (flag && itemstack.getItem() == Items.SPAWN_EGG)
/*     */     {
/* 261 */       return super.processInteract(player, hand);
/*     */     }
/*     */ 
/*     */     
/* 265 */     if (!isChild()) {
/*     */       
/* 267 */       if (isTame() && player.isSneaking()) {
/*     */         
/* 269 */         openGUI(player);
/* 270 */         return true;
/*     */       } 
/*     */       
/* 273 */       if (isBeingRidden())
/*     */       {
/* 275 */         return super.processInteract(player, hand);
/*     */       }
/*     */     } 
/*     */     
/* 279 */     if (flag) {
/*     */       
/* 281 */       if (func_190678_b(player, itemstack)) {
/*     */         
/* 283 */         if (!player.capabilities.isCreativeMode)
/*     */         {
/* 285 */           itemstack.func_190918_g(1);
/*     */         }
/*     */         
/* 288 */         return true;
/*     */       } 
/*     */       
/* 291 */       if (itemstack.interactWithEntity(player, (EntityLivingBase)this, hand))
/*     */       {
/* 293 */         return true;
/*     */       }
/*     */       
/* 296 */       if (!isTame()) {
/*     */         
/* 298 */         func_190687_dF();
/* 299 */         return true;
/*     */       } 
/*     */       
/* 302 */       boolean flag1 = (HorseArmorType.getByItemStack(itemstack) != HorseArmorType.NONE);
/* 303 */       boolean flag2 = (!isChild() && !isHorseSaddled() && itemstack.getItem() == Items.SADDLE);
/*     */       
/* 305 */       if (flag1 || flag2) {
/*     */         
/* 307 */         openGUI(player);
/* 308 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     if (isChild())
/*     */     {
/* 314 */       return super.processInteract(player, hand);
/*     */     }
/*     */ 
/*     */     
/* 318 */     mountTo(player);
/* 319 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 329 */     if (otherAnimal == this)
/*     */     {
/* 331 */       return false;
/*     */     }
/* 333 */     if (!(otherAnimal instanceof EntityDonkey) && !(otherAnimal instanceof EntityHorse))
/*     */     {
/* 335 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 339 */     return (canMate() && ((AbstractHorse)otherAnimal).canMate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable ageable) {
/*     */     AbstractHorse abstracthorse;
/* 347 */     if (ageable instanceof EntityDonkey) {
/*     */       
/* 349 */       abstracthorse = new EntityMule(this.world);
/*     */     } else {
/*     */       int i;
/*     */       
/* 353 */       EntityHorse entityhorse = (EntityHorse)ageable;
/* 354 */       abstracthorse = new EntityHorse(this.world);
/* 355 */       int j = this.rand.nextInt(9);
/*     */ 
/*     */       
/* 358 */       if (j < 4) {
/*     */         
/* 360 */         i = getHorseVariant() & 0xFF;
/*     */       }
/* 362 */       else if (j < 8) {
/*     */         
/* 364 */         i = entityhorse.getHorseVariant() & 0xFF;
/*     */       }
/*     */       else {
/*     */         
/* 368 */         i = this.rand.nextInt(7);
/*     */       } 
/*     */       
/* 371 */       int k = this.rand.nextInt(5);
/*     */       
/* 373 */       if (k < 2) {
/*     */         
/* 375 */         i |= getHorseVariant() & 0xFF00;
/*     */       }
/* 377 */       else if (k < 4) {
/*     */         
/* 379 */         i |= entityhorse.getHorseVariant() & 0xFF00;
/*     */       }
/*     */       else {
/*     */         
/* 383 */         i |= this.rand.nextInt(5) << 8 & 0xFF00;
/*     */       } 
/*     */       
/* 386 */       ((EntityHorse)abstracthorse).setHorseVariant(i);
/*     */     } 
/*     */     
/* 389 */     func_190681_a(ageable, abstracthorse);
/* 390 */     return abstracthorse;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190677_dK() {
/* 395 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190682_f(ItemStack p_190682_1_) {
/* 400 */     return HorseArmorType.isHorseArmor(p_190682_1_.getItem());
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
/* 411 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */ 
/*     */     
/* 414 */     if (livingdata instanceof GroupData) {
/*     */       
/* 416 */       i = ((GroupData)livingdata).field_190885_a;
/*     */     }
/*     */     else {
/*     */       
/* 420 */       i = this.rand.nextInt(7);
/* 421 */       livingdata = new GroupData(i);
/*     */     } 
/*     */     
/* 424 */     setHorseVariant(i | this.rand.nextInt(5) << 8);
/* 425 */     return livingdata;
/*     */   }
/*     */   
/*     */   public static class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public int field_190885_a;
/*     */     
/*     */     public GroupData(int p_i47337_1_) {
/* 434 */       this.field_190885_a = p_i47337_1_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
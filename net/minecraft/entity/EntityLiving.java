/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Arrays;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.EntityAITasks;
/*      */ import net.minecraft.entity.ai.EntityJumpHelper;
/*      */ import net.minecraft.entity.ai.EntityLookHelper;
/*      */ import net.minecraft.entity.ai.EntityMoveHelper;
/*      */ import net.minecraft.entity.ai.EntitySenses;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityGhast;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemSword;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.network.play.server.SPacketEntityAttach;
/*      */ import net.minecraft.pathfinding.PathNavigate;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.pathfinding.PathNodeType;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.storage.loot.LootContext;
/*      */ import net.minecraft.world.storage.loot.LootTable;
/*      */ import optifine.Config;
/*      */ import optifine.Reflector;
/*      */ 
/*      */ public abstract class EntityLiving
/*      */   extends EntityLivingBase
/*      */ {
/*   66 */   public static final DataParameter<Byte> AI_FLAGS = EntityDataManager.createKey(EntityLiving.class, DataSerializers.BYTE);
/*      */   
/*      */   public int livingSoundTime;
/*      */   
/*      */   protected int experienceValue;
/*      */   
/*      */   private final EntityLookHelper lookHelper;
/*      */   
/*      */   protected EntityMoveHelper moveHelper;
/*      */   
/*      */   protected EntityJumpHelper jumpHelper;
/*      */   
/*      */   private final EntityBodyHelper bodyHelper;
/*      */   
/*      */   protected PathNavigate navigator;
/*      */   
/*      */   protected final EntityAITasks tasks;
/*      */   
/*      */   protected final EntityAITasks targetTasks;
/*      */   
/*      */   private EntityLivingBase attackTarget;
/*      */   
/*      */   private final EntitySenses senses;
/*      */   
/*   90 */   private final NonNullList<ItemStack> inventoryHands = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
/*      */ 
/*      */   
/*   93 */   protected float[] inventoryHandsDropChances = new float[2];
/*   94 */   private final NonNullList<ItemStack> inventoryArmor = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
/*      */ 
/*      */   
/*   97 */   protected float[] inventoryArmorDropChances = new float[4];
/*      */ 
/*      */   
/*      */   private boolean canPickUpLoot;
/*      */   
/*      */   private boolean persistenceRequired;
/*      */   
/*  104 */   private final Map<PathNodeType, Float> mapPathPriority = Maps.newEnumMap(PathNodeType.class);
/*      */   private ResourceLocation deathLootTable;
/*      */   private long deathLootTableSeed;
/*      */   private boolean isLeashed;
/*      */   private Entity leashedToEntity;
/*      */   private NBTTagCompound leashNBTTag;
/*  110 */   public int randomMobsId = 0;
/*  111 */   public Biome spawnBiome = null;
/*  112 */   public BlockPos spawnPosition = null;
/*  113 */   private UUID teamUuid = null;
/*  114 */   private String teamUuidString = null;
/*      */ 
/*      */   
/*      */   public EntityLiving(World worldIn) {
/*  118 */     super(worldIn);
/*  119 */     this.tasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*  120 */     this.targetTasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*  121 */     this.lookHelper = new EntityLookHelper(this);
/*  122 */     this.moveHelper = new EntityMoveHelper(this);
/*  123 */     this.jumpHelper = new EntityJumpHelper(this);
/*  124 */     this.bodyHelper = createBodyHelper();
/*  125 */     this.navigator = getNewNavigator(worldIn);
/*  126 */     this.senses = new EntitySenses(this);
/*  127 */     Arrays.fill(this.inventoryArmorDropChances, 0.085F);
/*  128 */     Arrays.fill(this.inventoryHandsDropChances, 0.085F);
/*      */     
/*  130 */     if (worldIn != null && !worldIn.isRemote)
/*      */     {
/*  132 */       initEntityAI();
/*      */     }
/*      */     
/*  135 */     UUID uuid = getUniqueID();
/*  136 */     long i = uuid.getLeastSignificantBits();
/*  137 */     this.randomMobsId = (int)(i & 0x7FFFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initEntityAI() {}
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  146 */     super.applyEntityAttributes();
/*  147 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PathNavigate getNewNavigator(World worldIn) {
/*  155 */     return (PathNavigate)new PathNavigateGround(this, worldIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getPathPriority(PathNodeType nodeType) {
/*  160 */     Float f = this.mapPathPriority.get(nodeType);
/*  161 */     return (f == null) ? nodeType.getPriority() : f.floatValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPathPriority(PathNodeType nodeType, float priority) {
/*  166 */     this.mapPathPriority.put(nodeType, Float.valueOf(priority));
/*      */   }
/*      */ 
/*      */   
/*      */   protected EntityBodyHelper createBodyHelper() {
/*  171 */     return new EntityBodyHelper(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLookHelper getLookHelper() {
/*  176 */     return this.lookHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityMoveHelper getMoveHelper() {
/*  181 */     return this.moveHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityJumpHelper getJumpHelper() {
/*  186 */     return this.jumpHelper;
/*      */   }
/*      */ 
/*      */   
/*      */   public PathNavigate getNavigator() {
/*  191 */     return this.navigator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntitySenses getEntitySenses() {
/*  199 */     return this.senses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityLivingBase getAttackTarget() {
/*  209 */     return this.attackTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
/*  217 */     this.attackTarget = entitylivingbaseIn;
/*  218 */     Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, new Object[] { this, entitylivingbaseIn });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/*  226 */     return (cls != EntityGhast.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eatGrassBonus() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  239 */     super.entityInit();
/*  240 */     this.dataManager.register(AI_FLAGS, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  248 */     return 80;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playLivingSound() {
/*  256 */     SoundEvent soundevent = getAmbientSound();
/*      */     
/*  258 */     if (soundevent != null)
/*      */     {
/*  260 */       playSound(soundevent, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  269 */     super.onEntityUpdate();
/*  270 */     this.world.theProfiler.startSection("mobBaseTick");
/*      */     
/*  272 */     if (isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
/*      */       
/*  274 */       applyEntityAI();
/*  275 */       playLivingSound();
/*      */     } 
/*      */     
/*  278 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playHurtSound(DamageSource source) {
/*  283 */     applyEntityAI();
/*  284 */     super.playHurtSound(source);
/*      */   }
/*      */ 
/*      */   
/*      */   private void applyEntityAI() {
/*  289 */     this.livingSoundTime = -getTalkInterval();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  297 */     if (this.experienceValue > 0) {
/*      */       
/*  299 */       int i = this.experienceValue;
/*      */       
/*  301 */       for (int j = 0; j < this.inventoryArmor.size(); j++) {
/*      */         
/*  303 */         if (!((ItemStack)this.inventoryArmor.get(j)).func_190926_b() && this.inventoryArmorDropChances[j] <= 1.0F)
/*      */         {
/*  305 */           i += 1 + this.rand.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  309 */       for (int k = 0; k < this.inventoryHands.size(); k++) {
/*      */         
/*  311 */         if (!((ItemStack)this.inventoryHands.get(k)).func_190926_b() && this.inventoryHandsDropChances[k] <= 1.0F)
/*      */         {
/*  313 */           i += 1 + this.rand.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  317 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  321 */     return this.experienceValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnExplosionParticle() {
/*  330 */     if (this.world.isRemote) {
/*      */       
/*  332 */       for (int i = 0; i < 20; i++)
/*      */       {
/*  334 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  335 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  336 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  337 */         double d3 = 10.0D;
/*  338 */         this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * 10.0D, this.posY + (this.rand.nextFloat() * this.height) - d1 * 10.0D, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * 10.0D, d0, d1, d2, new int[0]);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  343 */       this.world.setEntityState(this, (byte)20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  349 */     if (id == 20) {
/*      */       
/*  351 */       spawnExplosionParticle();
/*      */     }
/*      */     else {
/*      */       
/*  355 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  364 */     if (Config.isSmoothWorld() && canSkipUpdate()) {
/*      */       
/*  366 */       onUpdateMinimal();
/*      */     }
/*      */     else {
/*      */       
/*  370 */       super.onUpdate();
/*      */       
/*  372 */       if (!this.world.isRemote) {
/*      */         
/*  374 */         updateLeashedState();
/*      */         
/*  376 */         if (this.ticksExisted % 5 == 0) {
/*      */           
/*  378 */           boolean flag = !(getControllingPassenger() instanceof EntityLiving);
/*  379 */           boolean flag1 = !(getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat);
/*  380 */           this.tasks.setControlFlag(1, flag);
/*  381 */           this.tasks.setControlFlag(4, (flag && flag1));
/*  382 */           this.tasks.setControlFlag(2, flag);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/*  390 */     this.bodyHelper.updateRenderAngles();
/*  391 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  397 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected Item getDropItem() {
/*  403 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/*  411 */     Item item = getDropItem();
/*      */     
/*  413 */     if (item != null) {
/*      */       
/*  415 */       int i = this.rand.nextInt(3);
/*      */       
/*  417 */       if (lootingModifier > 0)
/*      */       {
/*  419 */         i += this.rand.nextInt(lootingModifier + 1);
/*      */       }
/*      */       
/*  422 */       for (int j = 0; j < i; j++)
/*      */       {
/*  424 */         dropItem(item, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerFixesMob(DataFixer fixer, Class<?> name) {
/*  431 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackDataLists(name, new String[] { "ArmorItems", "HandItems" }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  439 */     super.writeEntityToNBT(compound);
/*  440 */     compound.setBoolean("CanPickUpLoot", canPickUpLoot());
/*  441 */     compound.setBoolean("PersistenceRequired", this.persistenceRequired);
/*  442 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  444 */     for (ItemStack itemstack : this.inventoryArmor) {
/*      */       
/*  446 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  448 */       if (!itemstack.func_190926_b())
/*      */       {
/*  450 */         itemstack.writeToNBT(nbttagcompound);
/*      */       }
/*      */       
/*  453 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  456 */     compound.setTag("ArmorItems", (NBTBase)nbttaglist);
/*  457 */     NBTTagList nbttaglist1 = new NBTTagList();
/*      */     
/*  459 */     for (ItemStack itemstack1 : this.inventoryHands) {
/*      */       
/*  461 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */       
/*  463 */       if (!itemstack1.func_190926_b())
/*      */       {
/*  465 */         itemstack1.writeToNBT(nbttagcompound1);
/*      */       }
/*      */       
/*  468 */       nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  471 */     compound.setTag("HandItems", (NBTBase)nbttaglist1);
/*  472 */     NBTTagList nbttaglist2 = new NBTTagList(); byte b; int i;
/*      */     float[] arrayOfFloat1;
/*  474 */     for (i = (arrayOfFloat1 = this.inventoryArmorDropChances).length, b = 0; b < i; ) { float f = arrayOfFloat1[b];
/*      */       
/*  476 */       nbttaglist2.appendTag((NBTBase)new NBTTagFloat(f));
/*      */       b++; }
/*      */     
/*  479 */     compound.setTag("ArmorDropChances", (NBTBase)nbttaglist2);
/*  480 */     NBTTagList nbttaglist3 = new NBTTagList();
/*      */     float[] arrayOfFloat2;
/*  482 */     for (int j = (arrayOfFloat2 = this.inventoryHandsDropChances).length; i < j; ) { float f1 = arrayOfFloat2[i];
/*      */       
/*  484 */       nbttaglist3.appendTag((NBTBase)new NBTTagFloat(f1));
/*      */       i++; }
/*      */     
/*  487 */     compound.setTag("HandDropChances", (NBTBase)nbttaglist3);
/*  488 */     compound.setBoolean("Leashed", this.isLeashed);
/*      */     
/*  490 */     if (this.leashedToEntity != null) {
/*      */       
/*  492 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*      */       
/*  494 */       if (this.leashedToEntity instanceof EntityLivingBase) {
/*      */         
/*  496 */         UUID uuid = this.leashedToEntity.getUniqueID();
/*  497 */         nbttagcompound2.setUniqueId("UUID", uuid);
/*      */       }
/*  499 */       else if (this.leashedToEntity instanceof EntityHanging) {
/*      */         
/*  501 */         BlockPos blockpos = ((EntityHanging)this.leashedToEntity).getHangingPosition();
/*  502 */         nbttagcompound2.setInteger("X", blockpos.getX());
/*  503 */         nbttagcompound2.setInteger("Y", blockpos.getY());
/*  504 */         nbttagcompound2.setInteger("Z", blockpos.getZ());
/*      */       } 
/*      */       
/*  507 */       compound.setTag("Leash", (NBTBase)nbttagcompound2);
/*      */     } 
/*      */     
/*  510 */     compound.setBoolean("LeftHanded", isLeftHanded());
/*      */     
/*  512 */     if (this.deathLootTable != null) {
/*      */       
/*  514 */       compound.setString("DeathLootTable", this.deathLootTable.toString());
/*      */       
/*  516 */       if (this.deathLootTableSeed != 0L)
/*      */       {
/*  518 */         compound.setLong("DeathLootTableSeed", this.deathLootTableSeed);
/*      */       }
/*      */     } 
/*      */     
/*  522 */     if (isAIDisabled())
/*      */     {
/*  524 */       compound.setBoolean("NoAI", isAIDisabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  533 */     super.readEntityFromNBT(compound);
/*      */     
/*  535 */     if (compound.hasKey("CanPickUpLoot", 1))
/*      */     {
/*  537 */       setCanPickUpLoot(compound.getBoolean("CanPickUpLoot"));
/*      */     }
/*      */     
/*  540 */     this.persistenceRequired = compound.getBoolean("PersistenceRequired");
/*      */     
/*  542 */     if (compound.hasKey("ArmorItems", 9)) {
/*      */       
/*  544 */       NBTTagList nbttaglist = compound.getTagList("ArmorItems", 10);
/*      */       
/*  546 */       for (int i = 0; i < this.inventoryArmor.size(); i++)
/*      */       {
/*  548 */         this.inventoryArmor.set(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
/*      */       }
/*      */     } 
/*      */     
/*  552 */     if (compound.hasKey("HandItems", 9)) {
/*      */       
/*  554 */       NBTTagList nbttaglist1 = compound.getTagList("HandItems", 10);
/*      */       
/*  556 */       for (int j = 0; j < this.inventoryHands.size(); j++)
/*      */       {
/*  558 */         this.inventoryHands.set(j, new ItemStack(nbttaglist1.getCompoundTagAt(j)));
/*      */       }
/*      */     } 
/*      */     
/*  562 */     if (compound.hasKey("ArmorDropChances", 9)) {
/*      */       
/*  564 */       NBTTagList nbttaglist2 = compound.getTagList("ArmorDropChances", 5);
/*      */       
/*  566 */       for (int k = 0; k < nbttaglist2.tagCount(); k++)
/*      */       {
/*  568 */         this.inventoryArmorDropChances[k] = nbttaglist2.getFloatAt(k);
/*      */       }
/*      */     } 
/*      */     
/*  572 */     if (compound.hasKey("HandDropChances", 9)) {
/*      */       
/*  574 */       NBTTagList nbttaglist3 = compound.getTagList("HandDropChances", 5);
/*      */       
/*  576 */       for (int l = 0; l < nbttaglist3.tagCount(); l++)
/*      */       {
/*  578 */         this.inventoryHandsDropChances[l] = nbttaglist3.getFloatAt(l);
/*      */       }
/*      */     } 
/*      */     
/*  582 */     this.isLeashed = compound.getBoolean("Leashed");
/*      */     
/*  584 */     if (this.isLeashed && compound.hasKey("Leash", 10))
/*      */     {
/*  586 */       this.leashNBTTag = compound.getCompoundTag("Leash");
/*      */     }
/*      */     
/*  589 */     setLeftHanded(compound.getBoolean("LeftHanded"));
/*      */     
/*  591 */     if (compound.hasKey("DeathLootTable", 8)) {
/*      */       
/*  593 */       this.deathLootTable = new ResourceLocation(compound.getString("DeathLootTable"));
/*  594 */       this.deathLootTableSeed = compound.getLong("DeathLootTableSeed");
/*      */     } 
/*      */     
/*  597 */     setNoAI(compound.getBoolean("NoAI"));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected ResourceLocation getLootTable() {
/*  603 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
/*  611 */     ResourceLocation resourcelocation = this.deathLootTable;
/*      */     
/*  613 */     if (resourcelocation == null)
/*      */     {
/*  615 */       resourcelocation = getLootTable();
/*      */     }
/*      */     
/*  618 */     if (resourcelocation != null) {
/*      */       
/*  620 */       LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
/*  621 */       this.deathLootTable = null;
/*  622 */       LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)this.world)).withLootedEntity(this).withDamageSource(source);
/*      */       
/*  624 */       if (wasRecentlyHit && this.attackingPlayer != null)
/*      */       {
/*  626 */         lootcontext$builder = lootcontext$builder.withPlayer(this.attackingPlayer).withLuck(this.attackingPlayer.getLuck());
/*      */       }
/*      */       
/*  629 */       for (ItemStack itemstack : loottable.generateLootForPools((this.deathLootTableSeed == 0L) ? this.rand : new Random(this.deathLootTableSeed), lootcontext$builder.build()))
/*      */       {
/*  631 */         entityDropItem(itemstack, 0.0F);
/*      */       }
/*      */       
/*  634 */       dropEquipment(wasRecentlyHit, lootingModifier);
/*      */     }
/*      */     else {
/*      */       
/*  638 */       super.dropLoot(wasRecentlyHit, lootingModifier, source);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191989_p(float p_191989_1_) {
/*  644 */     this.field_191988_bg = p_191989_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMoveForward(float amount) {
/*  649 */     this.moveForward = amount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMoveStrafing(float amount) {
/*  654 */     this.moveStrafing = amount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/*  662 */     super.setAIMoveSpeed(speedIn);
/*  663 */     func_191989_p(speedIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  672 */     super.onLivingUpdate();
/*  673 */     this.world.theProfiler.startSection("looting");
/*      */     
/*  675 */     if (!this.world.isRemote && canPickUpLoot() && !this.dead && this.world.getGameRules().getBoolean("mobGriefing"))
/*      */     {
/*  677 */       for (EntityItem entityitem : this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D))) {
/*      */         
/*  679 */         if (!entityitem.isDead && !entityitem.getEntityItem().func_190926_b() && !entityitem.cannotPickup())
/*      */         {
/*  681 */           updateEquipmentIfNeeded(entityitem);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  686 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  695 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  696 */     EntityEquipmentSlot entityequipmentslot = getSlotForItemStack(itemstack);
/*  697 */     boolean flag = true;
/*  698 */     ItemStack itemstack1 = getItemStackFromSlot(entityequipmentslot);
/*      */     
/*  700 */     if (!itemstack1.func_190926_b())
/*      */     {
/*  702 */       if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.HAND) {
/*      */         
/*  704 */         if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
/*      */           
/*  706 */           flag = true;
/*      */         }
/*  708 */         else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
/*      */           
/*  710 */           ItemSword itemsword = (ItemSword)itemstack.getItem();
/*  711 */           ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*      */           
/*  713 */           if (itemsword.getDamageVsEntity() == itemsword1.getDamageVsEntity())
/*      */           {
/*  715 */             flag = !(itemstack.getMetadata() <= itemstack1.getMetadata() && (!itemstack.hasTagCompound() || itemstack1.hasTagCompound()));
/*      */           }
/*      */           else
/*      */           {
/*  719 */             flag = (itemsword.getDamageVsEntity() > itemsword1.getDamageVsEntity());
/*      */           }
/*      */         
/*  722 */         } else if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && itemstack1.getItem() instanceof net.minecraft.item.ItemBow) {
/*      */           
/*  724 */           flag = (itemstack.hasTagCompound() && !itemstack1.hasTagCompound());
/*      */         }
/*      */         else {
/*      */           
/*  728 */           flag = false;
/*      */         }
/*      */       
/*  731 */       } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
/*      */         
/*  733 */         flag = true;
/*      */       }
/*  735 */       else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor && !EnchantmentHelper.func_190938_b(itemstack1)) {
/*      */         
/*  737 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  738 */         ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*      */         
/*  740 */         if (itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount)
/*      */         {
/*  742 */           flag = !(itemstack.getMetadata() <= itemstack1.getMetadata() && (!itemstack.hasTagCompound() || itemstack1.hasTagCompound()));
/*      */         }
/*      */         else
/*      */         {
/*  746 */           flag = (itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  751 */         flag = false;
/*      */       } 
/*      */     }
/*      */     
/*  755 */     if (flag && canEquipItem(itemstack)) {
/*      */       double d0;
/*      */ 
/*      */       
/*  759 */       switch (entityequipmentslot.getSlotType()) {
/*      */         
/*      */         case HAND:
/*  762 */           d0 = this.inventoryHandsDropChances[entityequipmentslot.getIndex()];
/*      */           break;
/*      */         
/*      */         case null:
/*  766 */           d0 = this.inventoryArmorDropChances[entityequipmentslot.getIndex()];
/*      */           break;
/*      */         
/*      */         default:
/*  770 */           d0 = 0.0D;
/*      */           break;
/*      */       } 
/*  773 */       if (!itemstack1.func_190926_b() && (this.rand.nextFloat() - 0.1F) < d0)
/*      */       {
/*  775 */         entityDropItem(itemstack1, 0.0F);
/*      */       }
/*      */       
/*  778 */       setItemStackToSlot(entityequipmentslot, itemstack);
/*      */       
/*  780 */       switch (entityequipmentslot.getSlotType()) {
/*      */         
/*      */         case HAND:
/*  783 */           this.inventoryHandsDropChances[entityequipmentslot.getIndex()] = 2.0F;
/*      */           break;
/*      */         
/*      */         case null:
/*  787 */           this.inventoryArmorDropChances[entityequipmentslot.getIndex()] = 2.0F;
/*      */           break;
/*      */       } 
/*  790 */       this.persistenceRequired = true;
/*  791 */       onItemPickup((Entity)itemEntity, itemstack.func_190916_E());
/*  792 */       itemEntity.setDead();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canEquipItem(ItemStack stack) {
/*  798 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  806 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void despawnEntity() {
/*  814 */     Object object = null;
/*  815 */     Object object1 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*  816 */     Object object2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*      */     
/*  818 */     if (this.persistenceRequired) {
/*      */       
/*  820 */       this.entityAge = 0;
/*      */     }
/*  822 */     else if ((this.entityAge & 0x1F) == 31 && (object = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, new Object[] { this })) != object1) {
/*      */       
/*  824 */       if (object == object2)
/*      */       {
/*  826 */         this.entityAge = 0;
/*      */       }
/*      */       else
/*      */       {
/*  830 */         setDead();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  835 */       EntityPlayer entityPlayer = this.world.getClosestPlayerToEntity(this, -1.0D);
/*      */       
/*  837 */       if (entityPlayer != null) {
/*      */         
/*  839 */         double d0 = ((Entity)entityPlayer).posX - this.posX;
/*  840 */         double d1 = ((Entity)entityPlayer).posY - this.posY;
/*  841 */         double d2 = ((Entity)entityPlayer).posZ - this.posZ;
/*  842 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  844 */         if (canDespawn() && d3 > 16384.0D)
/*      */         {
/*  846 */           setDead();
/*      */         }
/*      */         
/*  849 */         if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && canDespawn()) {
/*      */           
/*  851 */           setDead();
/*      */         }
/*  853 */         else if (d3 < 1024.0D) {
/*      */           
/*  855 */           this.entityAge = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void updateEntityActionState() {
/*  863 */     this.entityAge++;
/*  864 */     this.world.theProfiler.startSection("checkDespawn");
/*  865 */     despawnEntity();
/*  866 */     this.world.theProfiler.endSection();
/*  867 */     this.world.theProfiler.startSection("sensing");
/*  868 */     this.senses.clearSensingCache();
/*  869 */     this.world.theProfiler.endSection();
/*  870 */     this.world.theProfiler.startSection("targetSelector");
/*  871 */     this.targetTasks.onUpdateTasks();
/*  872 */     this.world.theProfiler.endSection();
/*  873 */     this.world.theProfiler.startSection("goalSelector");
/*  874 */     this.tasks.onUpdateTasks();
/*  875 */     this.world.theProfiler.endSection();
/*  876 */     this.world.theProfiler.startSection("navigation");
/*  877 */     this.navigator.onUpdateNavigation();
/*  878 */     this.world.theProfiler.endSection();
/*  879 */     this.world.theProfiler.startSection("mob tick");
/*  880 */     updateAITasks();
/*  881 */     this.world.theProfiler.endSection();
/*      */     
/*  883 */     if (isRiding() && getRidingEntity() instanceof EntityLiving) {
/*      */       
/*  885 */       EntityLiving entityliving = (EntityLiving)getRidingEntity();
/*  886 */       entityliving.getNavigator().setPath(getNavigator().getPath(), 1.5D);
/*  887 */       entityliving.getMoveHelper().read(getMoveHelper());
/*      */     } 
/*      */     
/*  890 */     this.world.theProfiler.startSection("controls");
/*  891 */     this.world.theProfiler.startSection("move");
/*  892 */     this.moveHelper.onUpdateMoveHelper();
/*  893 */     this.world.theProfiler.endStartSection("look");
/*  894 */     this.lookHelper.onUpdateLook();
/*  895 */     this.world.theProfiler.endStartSection("jump");
/*  896 */     this.jumpHelper.doJump();
/*  897 */     this.world.theProfiler.endSection();
/*  898 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVerticalFaceSpeed() {
/*  911 */     return 40;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHorizontalFaceSpeed() {
/*  916 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
/*  924 */     double d2, d0 = entityIn.posX - this.posX;
/*  925 */     double d1 = entityIn.posZ - this.posZ;
/*      */ 
/*      */     
/*  928 */     if (entityIn instanceof EntityLivingBase) {
/*      */       
/*  930 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/*  931 */       d2 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
/*      */     }
/*      */     else {
/*      */       
/*  935 */       d2 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
/*      */     } 
/*      */     
/*  938 */     double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/*  939 */     float f = (float)(MathHelper.atan2(d1, d0) * 57.29577951308232D) - 90.0F;
/*  940 */     float f1 = (float)-(MathHelper.atan2(d2, d3) * 57.29577951308232D);
/*  941 */     this.rotationPitch = updateRotation(this.rotationPitch, f1, maxPitchIncrease);
/*  942 */     this.rotationYaw = updateRotation(this.rotationYaw, f, maxYawIncrease);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float updateRotation(float angle, float targetAngle, float maxIncrease) {
/*  950 */     float f = MathHelper.wrapDegrees(targetAngle - angle);
/*      */     
/*  952 */     if (f > maxIncrease)
/*      */     {
/*  954 */       f = maxIncrease;
/*      */     }
/*      */     
/*  957 */     if (f < -maxIncrease)
/*      */     {
/*  959 */       f = -maxIncrease;
/*      */     }
/*      */     
/*  962 */     return angle + f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  970 */     IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
/*  971 */     return iblockstate.canEntitySpawn(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotColliding() {
/*  979 */     return (!this.world.containsAnyLiquid(getEntityBoundingBox()) && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && this.world.checkNoEntityCollision(getEntityBoundingBox(), this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRenderSizeModifier() {
/*  987 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  995 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 1003 */     if (getAttackTarget() == null)
/*      */     {
/* 1005 */       return 3;
/*      */     }
/*      */ 
/*      */     
/* 1009 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/* 1010 */     i -= (3 - this.world.getDifficulty().getDifficultyId()) * 4;
/*      */     
/* 1012 */     if (i < 0)
/*      */     {
/* 1014 */       i = 0;
/*      */     }
/*      */     
/* 1017 */     return i + 3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHeldEquipment() {
/* 1023 */     return (Iterable<ItemStack>)this.inventoryHands;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorInventoryList() {
/* 1028 */     return (Iterable<ItemStack>)this.inventoryArmor;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
/* 1033 */     switch (slotIn.getSlotType()) {
/*      */       
/*      */       case HAND:
/* 1036 */         return (ItemStack)this.inventoryHands.get(slotIn.getIndex());
/*      */       
/*      */       case null:
/* 1039 */         return (ItemStack)this.inventoryArmor.get(slotIn.getIndex());
/*      */     } 
/*      */     
/* 1042 */     return ItemStack.field_190927_a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
/* 1048 */     switch (slotIn.getSlotType()) {
/*      */       
/*      */       case HAND:
/* 1051 */         this.inventoryHands.set(slotIn.getIndex(), stack);
/*      */         break;
/*      */       
/*      */       case null:
/* 1055 */         this.inventoryArmor.set(slotIn.getIndex(), stack);
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
/*      */     byte b;
/*      */     int i;
/*      */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 1064 */     for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { double d0; EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */       
/* 1066 */       ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
/*      */ 
/*      */       
/* 1069 */       switch (entityequipmentslot.getSlotType()) {
/*      */         
/*      */         case HAND:
/* 1072 */           d0 = this.inventoryHandsDropChances[entityequipmentslot.getIndex()];
/*      */           break;
/*      */         
/*      */         case null:
/* 1076 */           d0 = this.inventoryArmorDropChances[entityequipmentslot.getIndex()];
/*      */           break;
/*      */         
/*      */         default:
/* 1080 */           d0 = 0.0D;
/*      */           break;
/*      */       } 
/* 1083 */       boolean flag = (d0 > 1.0D);
/*      */       
/* 1085 */       if (!itemstack.func_190926_b() && !EnchantmentHelper.func_190939_c(itemstack) && (wasRecentlyHit || flag) && (this.rand.nextFloat() - lootingModifier * 0.01F) < d0) {
/*      */         
/* 1087 */         if (!flag && itemstack.isItemStackDamageable())
/*      */         {
/* 1089 */           itemstack.setItemDamage(itemstack.getMaxDamage() - this.rand.nextInt(1 + this.rand.nextInt(Math.max(itemstack.getMaxDamage() - 3, 1))));
/*      */         }
/*      */         
/* 1092 */         entityDropItem(itemstack, 0.0F);
/*      */       } 
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 1102 */     if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty()) {
/*      */       
/* 1104 */       int i = this.rand.nextInt(2);
/* 1105 */       float f = (this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.1F : 0.25F;
/*      */       
/* 1107 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/* 1109 */         i++;
/*      */       }
/*      */       
/* 1112 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/* 1114 */         i++;
/*      */       }
/*      */       
/* 1117 */       if (this.rand.nextFloat() < 0.095F)
/*      */       {
/* 1119 */         i++;
/*      */       }
/*      */       
/* 1122 */       boolean flag = true; byte b; int j;
/*      */       EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 1124 */       for (j = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < j; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */         
/* 1126 */         if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
/*      */           
/* 1128 */           ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
/*      */           
/* 1130 */           if (!flag && this.rand.nextFloat() < f) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 1135 */           flag = false;
/*      */           
/* 1137 */           if (itemstack.func_190926_b()) {
/*      */             
/* 1139 */             Item item = getArmorByChance(entityequipmentslot, i);
/*      */             
/* 1141 */             if (item != null)
/*      */             {
/* 1143 */               setItemStackToSlot(entityequipmentslot, new ItemStack(item));
/*      */             }
/*      */           } 
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public static EntityEquipmentSlot getSlotForItemStack(ItemStack stack) {
/* 1153 */     if (stack.getItem() != Item.getItemFromBlock(Blocks.PUMPKIN) && stack.getItem() != Items.SKULL) {
/*      */       
/* 1155 */       if (stack.getItem() instanceof ItemArmor)
/*      */       {
/* 1157 */         return ((ItemArmor)stack.getItem()).armorType;
/*      */       }
/* 1159 */       if (stack.getItem() == Items.ELYTRA)
/*      */       {
/* 1161 */         return EntityEquipmentSlot.CHEST;
/*      */       }
/*      */ 
/*      */       
/* 1165 */       boolean flag = (stack.getItem() == Items.SHIELD);
/*      */       
/* 1167 */       if (Reflector.ForgeItem_isShield.exists())
/*      */       {
/* 1169 */         flag = Reflector.callBoolean(stack.getItem(), Reflector.ForgeItem_isShield, new Object[] { stack, null });
/*      */       }
/*      */       
/* 1172 */       return flag ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1177 */     return EntityEquipmentSlot.HEAD;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Item getArmorByChance(EntityEquipmentSlot slotIn, int chance) {
/* 1184 */     switch (slotIn) {
/*      */       
/*      */       case HEAD:
/* 1187 */         if (chance == 0)
/*      */         {
/* 1189 */           return (Item)Items.LEATHER_HELMET;
/*      */         }
/* 1191 */         if (chance == 1)
/*      */         {
/* 1193 */           return (Item)Items.GOLDEN_HELMET;
/*      */         }
/* 1195 */         if (chance == 2)
/*      */         {
/* 1197 */           return (Item)Items.CHAINMAIL_HELMET;
/*      */         }
/* 1199 */         if (chance == 3)
/*      */         {
/* 1201 */           return (Item)Items.IRON_HELMET;
/*      */         }
/* 1203 */         if (chance == 4)
/*      */         {
/* 1205 */           return (Item)Items.DIAMOND_HELMET;
/*      */         }
/*      */       
/*      */       case null:
/* 1209 */         if (chance == 0)
/*      */         {
/* 1211 */           return (Item)Items.LEATHER_CHESTPLATE;
/*      */         }
/* 1213 */         if (chance == 1)
/*      */         {
/* 1215 */           return (Item)Items.GOLDEN_CHESTPLATE;
/*      */         }
/* 1217 */         if (chance == 2)
/*      */         {
/* 1219 */           return (Item)Items.CHAINMAIL_CHESTPLATE;
/*      */         }
/* 1221 */         if (chance == 3)
/*      */         {
/* 1223 */           return (Item)Items.IRON_CHESTPLATE;
/*      */         }
/* 1225 */         if (chance == 4)
/*      */         {
/* 1227 */           return (Item)Items.DIAMOND_CHESTPLATE;
/*      */         }
/*      */       
/*      */       case LEGS:
/* 1231 */         if (chance == 0)
/*      */         {
/* 1233 */           return (Item)Items.LEATHER_LEGGINGS;
/*      */         }
/* 1235 */         if (chance == 1)
/*      */         {
/* 1237 */           return (Item)Items.GOLDEN_LEGGINGS;
/*      */         }
/* 1239 */         if (chance == 2)
/*      */         {
/* 1241 */           return (Item)Items.CHAINMAIL_LEGGINGS;
/*      */         }
/* 1243 */         if (chance == 3)
/*      */         {
/* 1245 */           return (Item)Items.IRON_LEGGINGS;
/*      */         }
/* 1247 */         if (chance == 4)
/*      */         {
/* 1249 */           return (Item)Items.DIAMOND_LEGGINGS;
/*      */         }
/*      */       
/*      */       case FEET:
/* 1253 */         if (chance == 0)
/*      */         {
/* 1255 */           return (Item)Items.LEATHER_BOOTS;
/*      */         }
/* 1257 */         if (chance == 1)
/*      */         {
/* 1259 */           return (Item)Items.GOLDEN_BOOTS;
/*      */         }
/* 1261 */         if (chance == 2)
/*      */         {
/* 1263 */           return (Item)Items.CHAINMAIL_BOOTS;
/*      */         }
/* 1265 */         if (chance == 3)
/*      */         {
/* 1267 */           return (Item)Items.IRON_BOOTS;
/*      */         }
/* 1269 */         if (chance == 4)
/*      */         {
/* 1271 */           return (Item)Items.DIAMOND_BOOTS;
/*      */         }
/*      */         break;
/*      */     } 
/* 1275 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 1284 */     float f = difficulty.getClampedAdditionalDifficulty();
/*      */     
/* 1286 */     if (!getHeldItemMainhand().func_190926_b() && this.rand.nextFloat() < 0.25F * f)
/*      */     {
/* 1288 */       setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItemMainhand(), (int)(5.0F + f * this.rand.nextInt(18)), false)); }  byte b;
/*      */     int i;
/*      */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 1291 */     for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */       
/* 1293 */       if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
/*      */         
/* 1295 */         ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
/*      */         
/* 1297 */         if (!itemstack.func_190926_b() && this.rand.nextFloat() < 0.5F * f)
/*      */         {
/* 1299 */           setItemStackToSlot(entityequipmentslot, EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * this.rand.nextInt(18)), false));
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 1313 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
/*      */     
/* 1315 */     if (this.rand.nextFloat() < 0.05F) {
/*      */       
/* 1317 */       setLeftHanded(true);
/*      */     }
/*      */     else {
/*      */       
/* 1321 */       setLeftHanded(false);
/*      */     } 
/*      */     
/* 1324 */     return livingdata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeSteered() {
/* 1333 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enablePersistence() {
/* 1341 */     this.persistenceRequired = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDropChance(EntityEquipmentSlot slotIn, float chance) {
/* 1346 */     switch (slotIn.getSlotType()) {
/*      */       
/*      */       case HAND:
/* 1349 */         this.inventoryHandsDropChances[slotIn.getIndex()] = chance;
/*      */         break;
/*      */       
/*      */       case null:
/* 1353 */         this.inventoryArmorDropChances[slotIn.getIndex()] = chance;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canPickUpLoot() {
/* 1359 */     return this.canPickUpLoot;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanPickUpLoot(boolean canPickup) {
/* 1364 */     this.canPickUpLoot = canPickup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNoDespawnRequired() {
/* 1372 */     return this.persistenceRequired;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 1377 */     if (getLeashed() && getLeashedToEntity() == player) {
/*      */       
/* 1379 */       clearLeashed(true, !player.capabilities.isCreativeMode);
/* 1380 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1384 */     ItemStack itemstack = player.getHeldItem(stack);
/*      */     
/* 1386 */     if (itemstack.getItem() == Items.LEAD && canBeLeashedTo(player)) {
/*      */       
/* 1388 */       setLeashedToEntity((Entity)player, true);
/* 1389 */       itemstack.func_190918_g(1);
/* 1390 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1394 */     return processInteract(player, stack) ? true : super.processInitialInteract(player, stack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 1401 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeashedState() {
/* 1409 */     if (this.leashNBTTag != null)
/*      */     {
/* 1411 */       recreateLeash();
/*      */     }
/*      */     
/* 1414 */     if (this.isLeashed) {
/*      */       
/* 1416 */       if (!isEntityAlive())
/*      */       {
/* 1418 */         clearLeashed(true, true);
/*      */       }
/*      */       
/* 1421 */       if (this.leashedToEntity == null || this.leashedToEntity.isDead)
/*      */       {
/* 1423 */         clearLeashed(true, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearLeashed(boolean sendPacket, boolean dropLead) {
/* 1433 */     if (this.isLeashed) {
/*      */       
/* 1435 */       this.isLeashed = false;
/* 1436 */       this.leashedToEntity = null;
/*      */       
/* 1438 */       if (!this.world.isRemote && dropLead)
/*      */       {
/* 1440 */         dropItem(Items.LEAD, 1);
/*      */       }
/*      */       
/* 1443 */       if (!this.world.isRemote && sendPacket && this.world instanceof WorldServer)
/*      */       {
/* 1445 */         ((WorldServer)this.world).getEntityTracker().sendToAllTrackingEntity(this, (Packet<?>)new SPacketEntityAttach(this, null));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeLeashedTo(EntityPlayer player) {
/* 1452 */     return (!getLeashed() && !(this instanceof net.minecraft.entity.monster.IMob));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getLeashed() {
/* 1457 */     return this.isLeashed;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getLeashedToEntity() {
/* 1462 */     return this.leashedToEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification) {
/* 1470 */     this.isLeashed = true;
/* 1471 */     this.leashedToEntity = entityIn;
/*      */     
/* 1473 */     if (!this.world.isRemote && sendAttachNotification && this.world instanceof WorldServer)
/*      */     {
/* 1475 */       ((WorldServer)this.world).getEntityTracker().sendToAllTrackingEntity(this, (Packet<?>)new SPacketEntityAttach(this, this.leashedToEntity));
/*      */     }
/*      */     
/* 1478 */     if (isRiding())
/*      */     {
/* 1480 */       dismountRidingEntity();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity entityIn, boolean force) {
/* 1486 */     boolean flag = super.startRiding(entityIn, force);
/*      */     
/* 1488 */     if (flag && getLeashed())
/*      */     {
/* 1490 */       clearLeashed(true, true);
/*      */     }
/*      */     
/* 1493 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private void recreateLeash() {
/* 1498 */     if (this.isLeashed && this.leashNBTTag != null)
/*      */     {
/* 1500 */       if (this.leashNBTTag.hasUniqueId("UUID")) {
/*      */         
/* 1502 */         UUID uuid = this.leashNBTTag.getUniqueId("UUID");
/*      */         
/* 1504 */         for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expandXyz(10.0D))) {
/*      */           
/* 1506 */           if (entitylivingbase.getUniqueID().equals(uuid)) {
/*      */             
/* 1508 */             setLeashedToEntity(entitylivingbase, true);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1513 */       } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
/*      */         
/* 1515 */         BlockPos blockpos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
/* 1516 */         EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(this.world, blockpos);
/*      */         
/* 1518 */         if (entityleashknot == null)
/*      */         {
/* 1520 */           entityleashknot = EntityLeashKnot.createKnot(this.world, blockpos);
/*      */         }
/*      */         
/* 1523 */         setLeashedToEntity(entityleashknot, true);
/*      */       }
/*      */       else {
/*      */         
/* 1527 */         clearLeashed(false, true);
/*      */       } 
/*      */     }
/*      */     
/* 1531 */     this.leashNBTTag = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     EntityEquipmentSlot entityequipmentslot;
/* 1538 */     if (inventorySlot == 98) {
/*      */       
/* 1540 */       entityequipmentslot = EntityEquipmentSlot.MAINHAND;
/*      */     }
/* 1542 */     else if (inventorySlot == 99) {
/*      */       
/* 1544 */       entityequipmentslot = EntityEquipmentSlot.OFFHAND;
/*      */     }
/* 1546 */     else if (inventorySlot == 100 + EntityEquipmentSlot.HEAD.getIndex()) {
/*      */       
/* 1548 */       entityequipmentslot = EntityEquipmentSlot.HEAD;
/*      */     }
/* 1550 */     else if (inventorySlot == 100 + EntityEquipmentSlot.CHEST.getIndex()) {
/*      */       
/* 1552 */       entityequipmentslot = EntityEquipmentSlot.CHEST;
/*      */     }
/* 1554 */     else if (inventorySlot == 100 + EntityEquipmentSlot.LEGS.getIndex()) {
/*      */       
/* 1556 */       entityequipmentslot = EntityEquipmentSlot.LEGS;
/*      */     }
/*      */     else {
/*      */       
/* 1560 */       if (inventorySlot != 100 + EntityEquipmentSlot.FEET.getIndex())
/*      */       {
/* 1562 */         return false;
/*      */       }
/*      */       
/* 1565 */       entityequipmentslot = EntityEquipmentSlot.FEET;
/*      */     } 
/*      */     
/* 1568 */     if (!itemStackIn.func_190926_b() && !isItemStackInSlot(entityequipmentslot, itemStackIn) && entityequipmentslot != EntityEquipmentSlot.HEAD)
/*      */     {
/* 1570 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1574 */     setItemStackToSlot(entityequipmentslot, itemStackIn);
/* 1575 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPassengerSteer() {
/* 1581 */     return (canBeSteered() && super.canPassengerSteer());
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemStackInSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
/* 1586 */     EntityEquipmentSlot entityequipmentslot = getSlotForItemStack(stack);
/* 1587 */     return !(entityequipmentslot != slotIn && (entityequipmentslot != EntityEquipmentSlot.MAINHAND || slotIn != EntityEquipmentSlot.OFFHAND) && (entityequipmentslot != EntityEquipmentSlot.OFFHAND || slotIn != EntityEquipmentSlot.MAINHAND));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1595 */     return (super.isServerWorld() && !isAIDisabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAI(boolean disable) {
/* 1603 */     byte b0 = ((Byte)this.dataManager.get(AI_FLAGS)).byteValue();
/* 1604 */     this.dataManager.set(AI_FLAGS, Byte.valueOf(disable ? (byte)(b0 | 0x1) : (byte)(b0 & 0xFFFFFFFE)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLeftHanded(boolean disable) {
/* 1609 */     byte b0 = ((Byte)this.dataManager.get(AI_FLAGS)).byteValue();
/* 1610 */     this.dataManager.set(AI_FLAGS, Byte.valueOf(disable ? (byte)(b0 | 0x2) : (byte)(b0 & 0xFFFFFFFD)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAIDisabled() {
/* 1618 */     return ((((Byte)this.dataManager.get(AI_FLAGS)).byteValue() & 0x1) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLeftHanded() {
/* 1623 */     return ((((Byte)this.dataManager.get(AI_FLAGS)).byteValue() & 0x2) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumHandSide getPrimaryHand() {
/* 1628 */     return isLeftHanded() ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSkipUpdate() {
/* 1633 */     if (isChild())
/*      */     {
/* 1635 */       return false;
/*      */     }
/* 1637 */     if (this.hurtTime > 0)
/*      */     {
/* 1639 */       return false;
/*      */     }
/* 1641 */     if (this.ticksExisted < 20)
/*      */     {
/* 1643 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1647 */     World world = getEntityWorld();
/*      */     
/* 1649 */     if (world == null)
/*      */     {
/* 1651 */       return false;
/*      */     }
/* 1653 */     if (world.playerEntities.size() != 1)
/*      */     {
/* 1655 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1659 */     Entity entity = world.playerEntities.get(0);
/* 1660 */     double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0D, 0.0D);
/* 1661 */     double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0D, 0.0D);
/* 1662 */     double d2 = d0 * d0 + d1 * d1;
/* 1663 */     return !isInRangeToRenderDist(d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdateMinimal() {
/* 1670 */     this.entityAge++;
/*      */     
/* 1672 */     if (this instanceof net.minecraft.entity.monster.EntityMob) {
/*      */       
/* 1674 */       float f = getBrightness();
/*      */       
/* 1676 */       if (f > 0.5F)
/*      */       {
/* 1678 */         this.entityAge += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1682 */     despawnEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 1687 */     UUID uuid = getUniqueID();
/*      */     
/* 1689 */     if (this.teamUuid != uuid) {
/*      */       
/* 1691 */       this.teamUuid = uuid;
/* 1692 */       this.teamUuidString = uuid.toString();
/*      */     } 
/*      */     
/* 1695 */     return (Team)this.world.getScoreboard().getPlayersTeam(this.teamUuidString);
/*      */   }
/*      */   
/*      */   public enum SpawnPlacementType
/*      */   {
/* 1700 */     ON_GROUND,
/* 1701 */     IN_AIR,
/* 1702 */     IN_WATER;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
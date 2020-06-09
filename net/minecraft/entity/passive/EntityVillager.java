/*      */ package net.minecraft.entity.passive;
/*      */ import java.util.Locale;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentData;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.INpc;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityAIFollowGolem;
/*      */ import net.minecraft.entity.ai.EntityAIHarvestFarmland;
/*      */ import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIMoveIndoors;
/*      */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*      */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAIPlay;
/*      */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAISwimming;
/*      */ import net.minecraft.entity.ai.EntityAITradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerInteract;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerMate;
/*      */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityEvoker;
/*      */ import net.minecraft.entity.monster.EntityVex;
/*      */ import net.minecraft.entity.monster.EntityVindicator;
/*      */ import net.minecraft.entity.monster.EntityWitch;
/*      */ import net.minecraft.entity.monster.EntityZombie;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemEnchantedBook;
/*      */ import net.minecraft.item.ItemMap;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.Tuple;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.DataFixesManager;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataFixer;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.village.MerchantRecipe;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.village.Village;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import net.minecraft.world.storage.MapDecoration;
/*      */ import net.minecraft.world.storage.loot.LootTableList;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class EntityVillager extends EntityAgeable implements INpc, IMerchant {
/*   96 */   private static final Logger field_190674_bx = LogManager.getLogger();
/*   97 */   private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(EntityVillager.class, DataSerializers.VARINT);
/*      */   
/*      */   private int randomTickDivider;
/*      */   
/*      */   private boolean isMating;
/*      */   
/*      */   private boolean isPlaying;
/*      */   
/*      */   Village villageObj;
/*      */   
/*      */   @Nullable
/*      */   private EntityPlayer buyingPlayer;
/*      */   
/*      */   @Nullable
/*      */   private MerchantRecipeList buyingList;
/*      */   
/*      */   private int timeUntilReset;
/*      */   
/*      */   private boolean needsInitilization;
/*      */   
/*      */   private boolean isWillingToMate;
/*      */   
/*      */   private int wealth;
/*      */   
/*      */   private String lastBuyingPlayer;
/*      */   
/*      */   private int careerId;
/*      */   
/*      */   private int careerLevel;
/*      */   
/*      */   private boolean isLookingForHome;
/*      */   private boolean areAdditionalTasksSet;
/*      */   private final InventoryBasic villagerInventory;
/*  130 */   private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { new EmeraldForItems(Items.WHEAT, new PriceInfo(18, 22)), new EmeraldForItems(Items.POTATO, new PriceInfo(15, 19)), new EmeraldForItems(Items.CARROT, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.BREAD, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.PUMPKIN), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.PUMPKIN_PIE, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.MELON_BLOCK), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.APPLE, new PriceInfo(-7, -5)) }, { new ListItemForEmeralds(Items.COOKIE, new PriceInfo(-10, -6)), new ListItemForEmeralds(Items.CAKE, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.STRING, new PriceInfo(15, 20)), new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.FISH, new PriceInfo(6, 6), Items.COOKED_FISH, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds((Item)Items.FISHING_ROD, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.WOOL), new PriceInfo(16, 22)), new ListItemForEmeralds((Item)Items.SHEARS, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL)), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.STRING, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.ARROW, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds((Item)Items.BOW, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.GRAVEL), new PriceInfo(10, 10), Items.FLINT, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.PAPER, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.BOOK, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.COMPASS, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.BOOKSHELF), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.WRITTEN_BOOK, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.CLOCK, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.GLASS), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.NAME_TAG, new PriceInfo(20, 22)) } }, { { new EmeraldForItems(Items.PAPER, new PriceInfo(24, 36)) }, { new EmeraldForItems(Items.COMPASS, new PriceInfo(1, 1)) }, { new ListItemForEmeralds((Item)Items.MAP, new PriceInfo(7, 11)) }, { new TreasureMapForEmeralds(new PriceInfo(12, 20), "Monument", MapDecoration.Type.MONUMENT), new TreasureMapForEmeralds(new PriceInfo(16, 28), "Mansion", MapDecoration.Type.MANSION) } } }, { { { new EmeraldForItems(Items.ROTTEN_FLESH, new PriceInfo(36, 40)), new EmeraldForItems(Items.GOLD_INGOT, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.REDSTONE, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ENDER_PEARL, new PriceInfo(4, 7)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.GLOWSTONE), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.EXPERIENCE_BOTTLE, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)), new ListItemForEmeralds((Item)Items.IRON_HELMET, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.IRON_INGOT, new PriceInfo(7, 9)), new ListItemForEmeralds((Item)Items.IRON_CHESTPLATE, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.DIAMOND, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds((Item)Items.DIAMOND_CHESTPLATE, new PriceInfo(16, 19)) }, { new ListItemForEmeralds((Item)Items.CHAINMAIL_BOOTS, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.CHAINMAIL_LEGGINGS, new PriceInfo(9, 11)), new ListItemForEmeralds((Item)Items.CHAINMAIL_HELMET, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.CHAINMAIL_CHESTPLATE, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.IRON_AXE, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.IRON_INGOT, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.IRON_SWORD, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.DIAMOND, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.DIAMOND_SWORD, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.DIAMOND_AXE, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.IRON_SHOVEL, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.IRON_INGOT, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.IRON_PICKAXE, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.DIAMOND, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.DIAMOND_PICKAXE, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.PORKCHOP, new PriceInfo(14, 18)), new EmeraldForItems(Items.CHICKEN, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.COAL, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.COOKED_PORKCHOP, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.COOKED_CHICKEN, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.LEATHER, new PriceInfo(9, 12)), new ListItemForEmeralds((Item)Items.LEATHER_LEGGINGS, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds((Item)Items.LEATHER_CHESTPLATE, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.SADDLE, new PriceInfo(8, 10)) } } }, { {} } };
/*      */ 
/*      */   
/*      */   public EntityVillager(World worldIn) {
/*  134 */     this(worldIn, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager(World worldIn, int professionId) {
/*  139 */     super(worldIn);
/*  140 */     this.villagerInventory = new InventoryBasic("Items", false, 8);
/*  141 */     setProfession(professionId);
/*  142 */     setSize(0.6F, 1.95F);
/*  143 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  144 */     setCanPickUpLoot(true);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initEntityAI() {
/*  149 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  150 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*  151 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
/*  152 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
/*  153 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityVex.class, 8.0F, 0.6D, 0.6D));
/*  154 */     this.tasks.addTask(1, (EntityAIBase)new EntityAITradePlayer(this));
/*  155 */     this.tasks.addTask(1, (EntityAIBase)new EntityAILookAtTradePlayer(this));
/*  156 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
/*  157 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
/*  158 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
/*  159 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
/*  160 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIVillagerMate(this));
/*  161 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIFollowGolem(this));
/*  162 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  163 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIVillagerInteract(this));
/*  164 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.6D));
/*  165 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   private void setAdditionalAItasks() {
/*  170 */     if (!this.areAdditionalTasksSet) {
/*      */       
/*  172 */       this.areAdditionalTasksSet = true;
/*      */       
/*  174 */       if (isChild()) {
/*      */         
/*  176 */         this.tasks.addTask(8, (EntityAIBase)new EntityAIPlay(this, 0.32D));
/*      */       }
/*  178 */       else if (getProfession() == 0) {
/*      */         
/*  180 */         this.tasks.addTask(6, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onGrowingAdult() {
/*  191 */     if (getProfession() == 0)
/*      */     {
/*  193 */       this.tasks.addTask(8, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */     }
/*      */     
/*  196 */     super.onGrowingAdult();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  201 */     super.applyEntityAttributes();
/*  202 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {
/*  207 */     if (--this.randomTickDivider <= 0) {
/*      */       
/*  209 */       BlockPos blockpos = new BlockPos((Entity)this);
/*  210 */       this.world.getVillageCollection().addToVillagerPositionList(blockpos);
/*  211 */       this.randomTickDivider = 70 + this.rand.nextInt(50);
/*  212 */       this.villageObj = this.world.getVillageCollection().getNearestVillage(blockpos, 32);
/*      */       
/*  214 */       if (this.villageObj == null) {
/*      */         
/*  216 */         detachHome();
/*      */       }
/*      */       else {
/*      */         
/*  220 */         BlockPos blockpos1 = this.villageObj.getCenter();
/*  221 */         setHomePosAndDistance(blockpos1, this.villageObj.getVillageRadius());
/*      */         
/*  223 */         if (this.isLookingForHome) {
/*      */           
/*  225 */           this.isLookingForHome = false;
/*  226 */           this.villageObj.setDefaultPlayerReputation(5);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  231 */     if (!isTrading() && this.timeUntilReset > 0) {
/*      */       
/*  233 */       this.timeUntilReset--;
/*      */       
/*  235 */       if (this.timeUntilReset <= 0) {
/*      */         
/*  237 */         if (this.needsInitilization) {
/*      */           
/*  239 */           for (MerchantRecipe merchantrecipe : this.buyingList) {
/*      */             
/*  241 */             if (merchantrecipe.isRecipeDisabled())
/*      */             {
/*  243 */               merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
/*      */             }
/*      */           } 
/*      */           
/*  247 */           populateBuyingList();
/*  248 */           this.needsInitilization = false;
/*      */           
/*  250 */           if (this.villageObj != null && this.lastBuyingPlayer != null) {
/*      */             
/*  252 */             this.world.setEntityState((Entity)this, (byte)14);
/*  253 */             this.villageObj.modifyPlayerReputation(this.lastBuyingPlayer, 1);
/*      */           } 
/*      */         } 
/*      */         
/*  257 */         addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
/*      */       } 
/*      */     } 
/*      */     
/*  261 */     super.updateAITasks();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/*  266 */     ItemStack itemstack = player.getHeldItem(hand);
/*  267 */     boolean flag = (itemstack.getItem() == Items.NAME_TAG);
/*      */     
/*  269 */     if (flag) {
/*      */       
/*  271 */       itemstack.interactWithEntity(player, (EntityLivingBase)this, hand);
/*  272 */       return true;
/*      */     } 
/*  274 */     if (!func_190669_a(itemstack, getClass()) && isEntityAlive() && !isTrading() && !isChild()) {
/*      */       
/*  276 */       if (this.buyingList == null)
/*      */       {
/*  278 */         populateBuyingList();
/*      */       }
/*      */       
/*  281 */       if (hand == EnumHand.MAIN_HAND)
/*      */       {
/*  283 */         player.addStat(StatList.TALKED_TO_VILLAGER);
/*      */       }
/*      */       
/*  286 */       if (!this.world.isRemote && !this.buyingList.isEmpty()) {
/*      */         
/*  288 */         setCustomer(player);
/*  289 */         player.displayVillagerTradeGui(this);
/*      */       }
/*  291 */       else if (this.buyingList.isEmpty()) {
/*      */         
/*  293 */         return super.processInteract(player, hand);
/*      */       } 
/*      */       
/*  296 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  300 */     return super.processInteract(player, hand);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  306 */     super.entityInit();
/*  307 */     this.dataManager.register(PROFESSION, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerFixesVillager(DataFixer fixer) {
/*  312 */     EntityLiving.registerFixesMob(fixer, EntityVillager.class);
/*  313 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackDataLists(EntityVillager.class, new String[] { "Inventory" }));
/*  314 */     fixer.registerWalker(FixTypes.ENTITY, new IDataWalker()
/*      */         {
/*      */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*      */           {
/*  318 */             if (EntityList.func_191306_a(EntityVillager.class).equals(new ResourceLocation(compound.getString("id"))) && compound.hasKey("Offers", 10)) {
/*      */               
/*  320 */               NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
/*      */               
/*  322 */               if (nbttagcompound.hasKey("Recipes", 9)) {
/*      */                 
/*  324 */                 NBTTagList nbttaglist = nbttagcompound.getTagList("Recipes", 10);
/*      */                 
/*  326 */                 for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */                   
/*  328 */                   NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  329 */                   DataFixesManager.processItemStack(fixer, nbttagcompound1, versionIn, "buy");
/*  330 */                   DataFixesManager.processItemStack(fixer, nbttagcompound1, versionIn, "buyB");
/*  331 */                   DataFixesManager.processItemStack(fixer, nbttagcompound1, versionIn, "sell");
/*  332 */                   nbttaglist.set(i, (NBTBase)nbttagcompound1);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*  337 */             return compound;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  347 */     super.writeEntityToNBT(compound);
/*  348 */     compound.setInteger("Profession", getProfession());
/*  349 */     compound.setInteger("Riches", this.wealth);
/*  350 */     compound.setInteger("Career", this.careerId);
/*  351 */     compound.setInteger("CareerLevel", this.careerLevel);
/*  352 */     compound.setBoolean("Willing", this.isWillingToMate);
/*      */     
/*  354 */     if (this.buyingList != null)
/*      */     {
/*  356 */       compound.setTag("Offers", (NBTBase)this.buyingList.getRecipiesAsTags());
/*      */     }
/*      */     
/*  359 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  361 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  363 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  365 */       if (!itemstack.func_190926_b())
/*      */       {
/*  367 */         nbttaglist.appendTag((NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */       }
/*      */     } 
/*      */     
/*  371 */     compound.setTag("Inventory", (NBTBase)nbttaglist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  379 */     super.readEntityFromNBT(compound);
/*  380 */     setProfession(compound.getInteger("Profession"));
/*  381 */     this.wealth = compound.getInteger("Riches");
/*  382 */     this.careerId = compound.getInteger("Career");
/*  383 */     this.careerLevel = compound.getInteger("CareerLevel");
/*  384 */     this.isWillingToMate = compound.getBoolean("Willing");
/*      */     
/*  386 */     if (compound.hasKey("Offers", 10)) {
/*      */       
/*  388 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
/*  389 */       this.buyingList = new MerchantRecipeList(nbttagcompound);
/*      */     } 
/*      */     
/*  392 */     NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
/*      */     
/*  394 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */       
/*  396 */       ItemStack itemstack = new ItemStack(nbttaglist.getCompoundTagAt(i));
/*      */       
/*  398 */       if (!itemstack.func_190926_b())
/*      */       {
/*  400 */         this.villagerInventory.addItem(itemstack);
/*      */       }
/*      */     } 
/*      */     
/*  404 */     setCanPickUpLoot(true);
/*  405 */     setAdditionalAItasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  413 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getAmbientSound() {
/*  418 */     return isTrading() ? SoundEvents.ENTITY_VILLAGER_TRADING : SoundEvents.ENTITY_VILLAGER_AMBIENT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  423 */     return SoundEvents.ENTITY_VILLAGER_HURT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getDeathSound() {
/*  428 */     return SoundEvents.ENTITY_VILLAGER_DEATH;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected ResourceLocation getLootTable() {
/*  434 */     return LootTableList.field_191184_at;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfession(int professionId) {
/*  439 */     this.dataManager.set(PROFESSION, Integer.valueOf(professionId));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getProfession() {
/*  444 */     return Math.max(((Integer)this.dataManager.get(PROFESSION)).intValue() % 6, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMating() {
/*  449 */     return this.isMating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMating(boolean mating) {
/*  454 */     this.isMating = mating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlaying(boolean playing) {
/*  459 */     this.isPlaying = playing;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPlaying() {
/*  464 */     return this.isPlaying;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
/*  469 */     super.setRevengeTarget(livingBase);
/*      */     
/*  471 */     if (this.villageObj != null && livingBase != null) {
/*      */       
/*  473 */       this.villageObj.addOrRenewAgressor(livingBase);
/*      */       
/*  475 */       if (livingBase instanceof EntityPlayer) {
/*      */         
/*  477 */         int i = -1;
/*      */         
/*  479 */         if (isChild())
/*      */         {
/*  481 */           i = -3;
/*      */         }
/*      */         
/*  484 */         this.villageObj.modifyPlayerReputation(livingBase.getName(), i);
/*      */         
/*  486 */         if (isEntityAlive())
/*      */         {
/*  488 */           this.world.setEntityState((Entity)this, (byte)13);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  499 */     if (this.villageObj != null) {
/*      */       
/*  501 */       Entity entity = cause.getEntity();
/*      */       
/*  503 */       if (entity != null) {
/*      */         
/*  505 */         if (entity instanceof EntityPlayer)
/*      */         {
/*  507 */           this.villageObj.modifyPlayerReputation(entity.getName(), -2);
/*      */         }
/*  509 */         else if (entity instanceof net.minecraft.entity.monster.IMob)
/*      */         {
/*  511 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  516 */         EntityPlayer entityplayer = this.world.getClosestPlayerToEntity((Entity)this, 16.0D);
/*      */         
/*  518 */         if (entityplayer != null)
/*      */         {
/*  520 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  525 */     super.onDeath(cause);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomer(@Nullable EntityPlayer player) {
/*  530 */     this.buyingPlayer = player;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getCustomer() {
/*  536 */     return this.buyingPlayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTrading() {
/*  541 */     return (this.buyingPlayer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsWillingToMate(boolean updateFirst) {
/*  549 */     if (!this.isWillingToMate && updateFirst && hasEnoughFoodToBreed()) {
/*      */       
/*  551 */       boolean flag = false;
/*      */       
/*  553 */       for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */         
/*  555 */         ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */         
/*  557 */         if (!itemstack.func_190926_b())
/*      */         {
/*  559 */           if (itemstack.getItem() == Items.BREAD && itemstack.func_190916_E() >= 3) {
/*      */             
/*  561 */             flag = true;
/*  562 */             this.villagerInventory.decrStackSize(i, 3);
/*      */           }
/*  564 */           else if ((itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT) && itemstack.func_190916_E() >= 12) {
/*      */             
/*  566 */             flag = true;
/*  567 */             this.villagerInventory.decrStackSize(i, 12);
/*      */           } 
/*      */         }
/*      */         
/*  571 */         if (flag) {
/*      */           
/*  573 */           this.world.setEntityState((Entity)this, (byte)18);
/*  574 */           this.isWillingToMate = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  580 */     return this.isWillingToMate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setIsWillingToMate(boolean isWillingToMate) {
/*  585 */     this.isWillingToMate = isWillingToMate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void useRecipe(MerchantRecipe recipe) {
/*  590 */     recipe.incrementToolUses();
/*  591 */     this.livingSoundTime = -getTalkInterval();
/*  592 */     playSound(SoundEvents.ENTITY_VILLAGER_YES, getSoundVolume(), getSoundPitch());
/*  593 */     int i = 3 + this.rand.nextInt(4);
/*      */     
/*  595 */     if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
/*      */       
/*  597 */       this.timeUntilReset = 40;
/*  598 */       this.needsInitilization = true;
/*  599 */       this.isWillingToMate = true;
/*      */       
/*  601 */       if (this.buyingPlayer != null) {
/*      */         
/*  603 */         this.lastBuyingPlayer = this.buyingPlayer.getName();
/*      */       }
/*      */       else {
/*      */         
/*  607 */         this.lastBuyingPlayer = null;
/*      */       } 
/*      */       
/*  610 */       i += 5;
/*      */     } 
/*      */     
/*  613 */     if (recipe.getItemToBuy().getItem() == Items.EMERALD)
/*      */     {
/*  615 */       this.wealth += recipe.getItemToBuy().func_190916_E();
/*      */     }
/*      */     
/*  618 */     if (recipe.getRewardsExp())
/*      */     {
/*  620 */       this.world.spawnEntityInWorld((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
/*      */     }
/*      */     
/*  623 */     if (this.buyingPlayer instanceof EntityPlayerMP)
/*      */     {
/*  625 */       CriteriaTriggers.field_192138_r.func_192234_a((EntityPlayerMP)this.buyingPlayer, this, recipe.getItemToSell());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void verifySellingItem(ItemStack stack) {
/*  635 */     if (!this.world.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
/*      */       
/*  637 */       this.livingSoundTime = -getTalkInterval();
/*  638 */       playSound(stack.func_190926_b() ? SoundEvents.ENTITY_VILLAGER_NO : SoundEvents.ENTITY_VILLAGER_YES, getSoundVolume(), getSoundPitch());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MerchantRecipeList getRecipes(EntityPlayer player) {
/*  645 */     if (this.buyingList == null)
/*      */     {
/*  647 */       populateBuyingList();
/*      */     }
/*      */     
/*  650 */     return this.buyingList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateBuyingList() {
/*  655 */     ITradeList[][][] aentityvillager$itradelist = DEFAULT_TRADE_LIST_MAP[getProfession()];
/*      */     
/*  657 */     if (this.careerId != 0 && this.careerLevel != 0) {
/*      */       
/*  659 */       this.careerLevel++;
/*      */     }
/*      */     else {
/*      */       
/*  663 */       this.careerId = this.rand.nextInt(aentityvillager$itradelist.length) + 1;
/*  664 */       this.careerLevel = 1;
/*      */     } 
/*      */     
/*  667 */     if (this.buyingList == null)
/*      */     {
/*  669 */       this.buyingList = new MerchantRecipeList();
/*      */     }
/*      */     
/*  672 */     int i = this.careerId - 1;
/*  673 */     int j = this.careerLevel - 1;
/*      */     
/*  675 */     if (i >= 0 && i < aentityvillager$itradelist.length) {
/*      */       
/*  677 */       ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];
/*      */       
/*  679 */       if (j >= 0 && j < aentityvillager$itradelist1.length) {
/*      */         
/*  681 */         ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j]; byte b; int k;
/*      */         ITradeList[] arrayOfITradeList1;
/*  683 */         for (k = (arrayOfITradeList1 = aentityvillager$itradelist2).length, b = 0; b < k; ) { ITradeList entityvillager$itradelist = arrayOfITradeList1[b];
/*      */           
/*  685 */           entityvillager$itradelist.func_190888_a(this, this.buyingList, this.rand);
/*      */           b++; }
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecipes(@Nullable MerchantRecipeList recipeList) {}
/*      */ 
/*      */   
/*      */   public World func_190670_t_() {
/*  697 */     return this.world;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos func_190671_u_() {
/*  702 */     return new BlockPos((Entity)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ITextComponent getDisplayName() {
/*  710 */     Team team = getTeam();
/*  711 */     String s = getCustomNameTag();
/*      */     
/*  713 */     if (s != null && !s.isEmpty()) {
/*      */       
/*  715 */       TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.formatPlayerName(team, s));
/*  716 */       textcomponentstring.getStyle().setHoverEvent(getHoverEvent());
/*  717 */       textcomponentstring.getStyle().setInsertion(getCachedUniqueIdString());
/*  718 */       return (ITextComponent)textcomponentstring;
/*      */     } 
/*      */ 
/*      */     
/*  722 */     if (this.buyingList == null)
/*      */     {
/*  724 */       populateBuyingList();
/*      */     }
/*      */     
/*  727 */     String s1 = null;
/*      */     
/*  729 */     switch (getProfession()) {
/*      */       
/*      */       case 0:
/*  732 */         if (this.careerId == 1) {
/*      */           
/*  734 */           s1 = "farmer"; break;
/*      */         } 
/*  736 */         if (this.careerId == 2) {
/*      */           
/*  738 */           s1 = "fisherman"; break;
/*      */         } 
/*  740 */         if (this.careerId == 3) {
/*      */           
/*  742 */           s1 = "shepherd"; break;
/*      */         } 
/*  744 */         if (this.careerId == 4)
/*      */         {
/*  746 */           s1 = "fletcher";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  752 */         if (this.careerId == 1) {
/*      */           
/*  754 */           s1 = "librarian"; break;
/*      */         } 
/*  756 */         if (this.careerId == 2)
/*      */         {
/*  758 */           s1 = "cartographer";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  764 */         s1 = "cleric";
/*      */         break;
/*      */       
/*      */       case 3:
/*  768 */         if (this.careerId == 1) {
/*      */           
/*  770 */           s1 = "armor"; break;
/*      */         } 
/*  772 */         if (this.careerId == 2) {
/*      */           
/*  774 */           s1 = "weapon"; break;
/*      */         } 
/*  776 */         if (this.careerId == 3)
/*      */         {
/*  778 */           s1 = "tool";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  784 */         if (this.careerId == 1) {
/*      */           
/*  786 */           s1 = "butcher"; break;
/*      */         } 
/*  788 */         if (this.careerId == 2)
/*      */         {
/*  790 */           s1 = "leather";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/*  796 */         s1 = "nitwit";
/*      */         break;
/*      */     } 
/*  799 */     if (s1 != null) {
/*      */       
/*  801 */       TextComponentTranslation textComponentTranslation = new TextComponentTranslation("entity.Villager." + s1, new Object[0]);
/*  802 */       textComponentTranslation.getStyle().setHoverEvent(getHoverEvent());
/*  803 */       textComponentTranslation.getStyle().setInsertion(getCachedUniqueIdString());
/*      */       
/*  805 */       if (team != null)
/*      */       {
/*  807 */         textComponentTranslation.getStyle().setColor(team.getChatFormat());
/*      */       }
/*      */       
/*  810 */       return (ITextComponent)textComponentTranslation;
/*      */     } 
/*      */ 
/*      */     
/*  814 */     return super.getDisplayName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/*  821 */     return isChild() ? 0.81F : 1.62F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  826 */     if (id == 12) {
/*      */       
/*  828 */       spawnParticles(EnumParticleTypes.HEART);
/*      */     }
/*  830 */     else if (id == 13) {
/*      */       
/*  832 */       spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
/*      */     }
/*  834 */     else if (id == 14) {
/*      */       
/*  836 */       spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
/*      */     }
/*      */     else {
/*      */       
/*  840 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticles(EnumParticleTypes particleType) {
/*  846 */     for (int i = 0; i < 5; i++) {
/*      */       
/*  848 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  849 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  850 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  851 */       this.world.spawnParticle(particleType, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 1.0D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/*  863 */     return func_190672_a(difficulty, livingdata, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public IEntityLivingData func_190672_a(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_) {
/*  868 */     p_190672_2_ = super.onInitialSpawn(p_190672_1_, p_190672_2_);
/*      */     
/*  870 */     if (p_190672_3_)
/*      */     {
/*  872 */       setProfession(this.world.rand.nextInt(6));
/*      */     }
/*      */     
/*  875 */     setAdditionalAItasks();
/*  876 */     populateBuyingList();
/*  877 */     return p_190672_2_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLookingForHome() {
/*  882 */     this.isLookingForHome = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager createChild(EntityAgeable ageable) {
/*  887 */     EntityVillager entityvillager = new EntityVillager(this.world);
/*  888 */     entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/*  889 */     return entityvillager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeLeashedTo(EntityPlayer player) {
/*  894 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/*  902 */     if (!this.world.isRemote && !this.isDead) {
/*      */       
/*  904 */       EntityWitch entitywitch = new EntityWitch(this.world);
/*  905 */       entitywitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  906 */       entitywitch.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entitywitch)), null);
/*  907 */       entitywitch.setNoAI(isAIDisabled());
/*      */       
/*  909 */       if (hasCustomName()) {
/*      */         
/*  911 */         entitywitch.setCustomNameTag(getCustomNameTag());
/*  912 */         entitywitch.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/*  915 */       this.world.spawnEntityInWorld((Entity)entitywitch);
/*  916 */       setDead();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public InventoryBasic getVillagerInventory() {
/*  922 */     return this.villagerInventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  931 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  932 */     Item item = itemstack.getItem();
/*      */     
/*  934 */     if (canVillagerPickupItem(item)) {
/*      */       
/*  936 */       ItemStack itemstack1 = this.villagerInventory.addItem(itemstack);
/*      */       
/*  938 */       if (itemstack1.func_190926_b()) {
/*      */         
/*  940 */         itemEntity.setDead();
/*      */       }
/*      */       else {
/*      */         
/*  944 */         itemstack.func_190920_e(itemstack1.func_190916_E());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canVillagerPickupItem(Item itemIn) {
/*  951 */     return !(itemIn != Items.BREAD && itemIn != Items.POTATO && itemIn != Items.CARROT && itemIn != Items.WHEAT && itemIn != Items.WHEAT_SEEDS && itemIn != Items.BEETROOT && itemIn != Items.BEETROOT_SEEDS);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEnoughFoodToBreed() {
/*  956 */     return hasEnoughItems(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAbondonItems() {
/*  965 */     return hasEnoughItems(2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean wantsMoreFood() {
/*  970 */     boolean flag = (getProfession() == 0);
/*      */     
/*  972 */     if (flag)
/*      */     {
/*  974 */       return !hasEnoughItems(5);
/*      */     }
/*      */ 
/*      */     
/*  978 */     return !hasEnoughItems(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasEnoughItems(int multiplier) {
/*  987 */     boolean flag = (getProfession() == 0);
/*      */     
/*  989 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  991 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  993 */       if (!itemstack.func_190926_b()) {
/*      */         
/*  995 */         if ((itemstack.getItem() == Items.BREAD && itemstack.func_190916_E() >= 3 * multiplier) || (itemstack.getItem() == Items.POTATO && itemstack.func_190916_E() >= 12 * multiplier) || (itemstack.getItem() == Items.CARROT && itemstack.func_190916_E() >= 12 * multiplier) || (itemstack.getItem() == Items.BEETROOT && itemstack.func_190916_E() >= 12 * multiplier))
/*      */         {
/*  997 */           return true;
/*      */         }
/*      */         
/* 1000 */         if (flag && itemstack.getItem() == Items.WHEAT && itemstack.func_190916_E() >= 9 * multiplier)
/*      */         {
/* 1002 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1007 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFarmItemInInventory() {
/* 1015 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/* 1017 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/* 1019 */       if (!itemstack.func_190926_b() && (itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.BEETROOT_SEEDS))
/*      */       {
/* 1021 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1025 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 1030 */     if (super.replaceItemInInventory(inventorySlot, itemStackIn))
/*      */     {
/* 1032 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1036 */     int i = inventorySlot - 300;
/*      */     
/* 1038 */     if (i >= 0 && i < this.villagerInventory.getSizeInventory()) {
/*      */       
/* 1040 */       this.villagerInventory.setInventorySlotContents(i, itemStackIn);
/* 1041 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1045 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static class EmeraldForItems
/*      */     implements ITradeList
/*      */   {
/*      */     public Item buyingItem;
/*      */     
/*      */     public EntityVillager.PriceInfo price;
/*      */     
/*      */     public EmeraldForItems(Item itemIn, EntityVillager.PriceInfo priceIn) {
/* 1057 */       this.buyingItem = itemIn;
/* 1058 */       this.price = priceIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/* 1063 */       int i = 1;
/*      */       
/* 1065 */       if (this.price != null)
/*      */       {
/* 1067 */         i = this.price.getPrice(p_190888_3_);
/*      */       }
/*      */       
/* 1070 */       p_190888_2_.add(new MerchantRecipe(new ItemStack(this.buyingItem, i, 0), Items.EMERALD));
/*      */     }
/*      */   }
/*      */   
/*      */   static interface ITradeList
/*      */   {
/*      */     void func_190888_a(IMerchant param1IMerchant, MerchantRecipeList param1MerchantRecipeList, Random param1Random);
/*      */   }
/*      */   
/*      */   static class ItemAndEmeraldToItem
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack buyingItemStack;
/*      */     public EntityVillager.PriceInfo buyingPriceInfo;
/*      */     public ItemStack sellingItemstack;
/*      */     public EntityVillager.PriceInfo sellingPriceInfo;
/*      */     
/*      */     public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
/* 1088 */       this.buyingItemStack = new ItemStack(p_i45813_1_);
/* 1089 */       this.buyingPriceInfo = p_i45813_2_;
/* 1090 */       this.sellingItemstack = new ItemStack(p_i45813_3_);
/* 1091 */       this.sellingPriceInfo = p_i45813_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/* 1096 */       int i = this.buyingPriceInfo.getPrice(p_190888_3_);
/* 1097 */       int j = this.sellingPriceInfo.getPrice(p_190888_3_);
/* 1098 */       p_190888_2_.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), new ItemStack(Items.EMERALD), new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedBookForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/* 1106 */       Enchantment enchantment = (Enchantment)Enchantment.REGISTRY.getRandomObject(p_190888_3_);
/* 1107 */       int i = MathHelper.getInt(p_190888_3_, enchantment.getMinLevel(), enchantment.getMaxLevel());
/* 1108 */       ItemStack itemstack = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i));
/* 1109 */       int j = 2 + p_190888_3_.nextInt(5 + i * 10) + 3 * i;
/*      */       
/* 1111 */       if (enchantment.isTreasureEnchantment())
/*      */       {
/* 1113 */         j *= 2;
/*      */       }
/*      */       
/* 1116 */       if (j > 64)
/*      */       {
/* 1118 */         j = 64;
/*      */       }
/*      */       
/* 1121 */       p_190888_2_.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, j), itemstack));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack enchantedItemStack;
/*      */     public EntityVillager.PriceInfo priceInfo;
/*      */     
/*      */     public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
/* 1132 */       this.enchantedItemStack = new ItemStack(p_i45814_1_);
/* 1133 */       this.priceInfo = p_i45814_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/* 1138 */       int i = 1;
/*      */       
/* 1140 */       if (this.priceInfo != null)
/*      */       {
/* 1142 */         i = this.priceInfo.getPrice(p_190888_3_);
/*      */       }
/*      */       
/* 1145 */       ItemStack itemstack = new ItemStack(Items.EMERALD, i, 0);
/* 1146 */       ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(p_190888_3_, new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata()), 5 + p_190888_3_.nextInt(15), false);
/* 1147 */       p_190888_2_.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack itemToBuy;
/*      */     public EntityVillager.PriceInfo priceInfo;
/*      */     
/*      */     public ListItemForEmeralds(Item par1Item, EntityVillager.PriceInfo priceInfo) {
/* 1158 */       this.itemToBuy = new ItemStack(par1Item);
/* 1159 */       this.priceInfo = priceInfo;
/*      */     }
/*      */ 
/*      */     
/*      */     public ListItemForEmeralds(ItemStack stack, EntityVillager.PriceInfo priceInfo) {
/* 1164 */       this.itemToBuy = stack;
/* 1165 */       this.priceInfo = priceInfo;
/*      */     }
/*      */     
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/*      */       ItemStack itemstack, itemstack1;
/* 1170 */       int i = 1;
/*      */       
/* 1172 */       if (this.priceInfo != null)
/*      */       {
/* 1174 */         i = this.priceInfo.getPrice(p_190888_3_);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1180 */       if (i < 0) {
/*      */         
/* 1182 */         itemstack = new ItemStack(Items.EMERALD);
/* 1183 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
/*      */       }
/*      */       else {
/*      */         
/* 1187 */         itemstack = new ItemStack(Items.EMERALD, i, 0);
/* 1188 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
/*      */       } 
/*      */       
/* 1191 */       p_190888_2_.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class PriceInfo
/*      */     extends Tuple<Integer, Integer>
/*      */   {
/*      */     public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
/* 1199 */       super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
/*      */       
/* 1201 */       if (p_i45810_2_ < p_i45810_1_)
/*      */       {
/* 1203 */         EntityVillager.field_190674_bx.warn("PriceRange({}, {}) invalid, {} smaller than {}", Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_), Integer.valueOf(p_i45810_2_), Integer.valueOf(p_i45810_1_));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int getPrice(Random rand) {
/* 1209 */       return (((Integer)getFirst()).intValue() >= ((Integer)getSecond()).intValue()) ? ((Integer)getFirst()).intValue() : (((Integer)getFirst()).intValue() + rand.nextInt(((Integer)getSecond()).intValue() - ((Integer)getFirst()).intValue() + 1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class TreasureMapForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public EntityVillager.PriceInfo field_190889_a;
/*      */     public String field_190890_b;
/*      */     public MapDecoration.Type field_190891_c;
/*      */     
/*      */     public TreasureMapForEmeralds(EntityVillager.PriceInfo p_i47340_1_, String p_i47340_2_, MapDecoration.Type p_i47340_3_) {
/* 1221 */       this.field_190889_a = p_i47340_1_;
/* 1222 */       this.field_190890_b = p_i47340_2_;
/* 1223 */       this.field_190891_c = p_i47340_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
/* 1228 */       int i = this.field_190889_a.getPrice(p_190888_3_);
/* 1229 */       World world = p_190888_1_.func_190670_t_();
/* 1230 */       BlockPos blockpos = world.func_190528_a(this.field_190890_b, p_190888_1_.func_190671_u_(), true);
/*      */       
/* 1232 */       if (blockpos != null) {
/*      */         
/* 1234 */         ItemStack itemstack = ItemMap.func_190906_a(world, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
/* 1235 */         ItemMap.func_190905_a(world, itemstack);
/* 1236 */         MapData.func_191094_a(itemstack, blockpos, "+", this.field_190891_c);
/* 1237 */         itemstack.func_190924_f("filled_map." + this.field_190890_b.toLowerCase(Locale.ROOT));
/* 1238 */         p_190888_2_.add(new MerchantRecipe(new ItemStack(Items.EMERALD, i), new ItemStack(Items.COMPASS), itemstack));
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
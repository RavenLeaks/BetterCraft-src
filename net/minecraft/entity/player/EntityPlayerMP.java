/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.advancements.PlayerAdvancements;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.passive.AbstractHorse;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerChest;
/*      */ import net.minecraft.inventory.ContainerHorseInventory;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.IContainerListener;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryMerchant;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMapBase;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.CraftingManager;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.client.CPacketClientSettings;
/*      */ import net.minecraft.network.play.server.SPacketAnimation;
/*      */ import net.minecraft.network.play.server.SPacketCamera;
/*      */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.SPacketChat;
/*      */ import net.minecraft.network.play.server.SPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.SPacketCombatEvent;
/*      */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.SPacketEffect;
/*      */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*      */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.SPacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.SPacketResourcePackSend;
/*      */ import net.minecraft.network.play.server.SPacketSetExperience;
/*      */ import net.minecraft.network.play.server.SPacketSetSlot;
/*      */ import net.minecraft.network.play.server.SPacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*      */ import net.minecraft.network.play.server.SPacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.SPacketUseBed;
/*      */ import net.minecraft.network.play.server.SPacketWindowItems;
/*      */ import net.minecraft.network.play.server.SPacketWindowProperty;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.IScoreCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.PlayerInteractionManager;
/*      */ import net.minecraft.server.management.UserListOpsEntry;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.RecipeBookServer;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.stats.StatisticsManagerServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.CooldownTracker;
/*      */ import net.minecraft.util.CooldownTrackerServer;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataFixer;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.IFixType;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.ChatType;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.Style;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.ILockableContainer;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.storage.loot.ILootContainer;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class EntityPlayerMP
/*      */   extends EntityPlayer implements IContainerListener {
/*  119 */   private static final Logger LOGGER = LogManager.getLogger();
/*  120 */   private String language = "en_US";
/*      */ 
/*      */   
/*      */   public NetHandlerPlayServer connection;
/*      */ 
/*      */   
/*      */   public final MinecraftServer mcServer;
/*      */ 
/*      */   
/*      */   public final PlayerInteractionManager interactionManager;
/*      */ 
/*      */   
/*      */   public double managedPosX;
/*      */ 
/*      */   
/*      */   public double managedPosZ;
/*      */ 
/*      */   
/*  138 */   private final List<Integer> entityRemoveQueue = Lists.newLinkedList();
/*      */ 
/*      */   
/*      */   private final PlayerAdvancements field_192042_bX;
/*      */   
/*      */   private final StatisticsManagerServer statsFile;
/*      */   
/*  145 */   private float lastHealthScore = Float.MIN_VALUE;
/*  146 */   private int lastFoodScore = Integer.MIN_VALUE;
/*  147 */   private int lastAirScore = Integer.MIN_VALUE;
/*  148 */   private int lastArmorScore = Integer.MIN_VALUE;
/*  149 */   private int lastLevelScore = Integer.MIN_VALUE;
/*  150 */   private int lastExperienceScore = Integer.MIN_VALUE;
/*      */ 
/*      */   
/*  153 */   private float lastHealth = -1.0E8F;
/*      */ 
/*      */   
/*  156 */   private int lastFoodLevel = -99999999;
/*      */ 
/*      */   
/*      */   private boolean wasHungry = true;
/*      */ 
/*      */   
/*  162 */   private int lastExperience = -99999999;
/*  163 */   private int respawnInvulnerabilityTicks = 60;
/*      */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*      */   private boolean chatColours = true;
/*  166 */   private long playerLastActiveTime = System.currentTimeMillis();
/*      */   
/*      */   private Entity spectatingEntity;
/*      */   
/*      */   private boolean invulnerableDimensionChange;
/*      */   private boolean field_192040_cp;
/*  172 */   private final RecipeBookServer field_192041_cq = new RecipeBookServer();
/*      */ 
/*      */   
/*      */   private Vec3d field_193107_ct;
/*      */ 
/*      */   
/*      */   private int field_193108_cu;
/*      */ 
/*      */   
/*      */   private boolean field_193109_cv;
/*      */ 
/*      */   
/*      */   private Vec3d field_193110_cw;
/*      */ 
/*      */   
/*      */   private int currentWindowId;
/*      */ 
/*      */   
/*      */   public boolean isChangingQuantityOnly;
/*      */   
/*      */   public int ping;
/*      */   
/*      */   public boolean playerConqueredTheEnd;
/*      */ 
/*      */   
/*      */   public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, PlayerInteractionManager interactionManagerIn) {
/*  198 */     super((World)worldIn, profile);
/*  199 */     interactionManagerIn.thisPlayerMP = this;
/*  200 */     this.interactionManager = interactionManagerIn;
/*  201 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*      */     
/*  203 */     if (worldIn.provider.func_191066_m() && worldIn.getWorldInfo().getGameType() != GameType.ADVENTURE) {
/*      */       
/*  205 */       int i = Math.max(0, server.getSpawnRadius(worldIn));
/*  206 */       int j = MathHelper.floor(worldIn.getWorldBorder().getClosestDistance(blockpos.getX(), blockpos.getZ()));
/*      */       
/*  208 */       if (j < i)
/*      */       {
/*  210 */         i = j;
/*      */       }
/*      */       
/*  213 */       if (j <= 1)
/*      */       {
/*  215 */         i = 1;
/*      */       }
/*      */       
/*  218 */       blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(i * 2 + 1) - i, 0, this.rand.nextInt(i * 2 + 1) - i));
/*      */     } 
/*      */     
/*  221 */     this.mcServer = server;
/*  222 */     this.statsFile = server.getPlayerList().getPlayerStatsFile(this);
/*  223 */     this.field_192042_bX = server.getPlayerList().func_192054_h(this);
/*  224 */     this.stepHeight = 1.0F;
/*  225 */     moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
/*      */     
/*  227 */     while (!worldIn.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && this.posY < 255.0D)
/*      */     {
/*  229 */       setPosition(this.posX, this.posY + 1.0D, this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  238 */     super.readEntityFromNBT(compound);
/*      */     
/*  240 */     if (compound.hasKey("playerGameType", 99))
/*      */     {
/*  242 */       if (getServer().getForceGamemode()) {
/*      */         
/*  244 */         this.interactionManager.setGameType(getServer().getGameType());
/*      */       }
/*      */       else {
/*      */         
/*  248 */         this.interactionManager.setGameType(GameType.getByID(compound.getInteger("playerGameType")));
/*      */       } 
/*      */     }
/*      */     
/*  252 */     if (compound.hasKey("enteredNetherPosition", 10)) {
/*      */       
/*  254 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("enteredNetherPosition");
/*  255 */       this.field_193110_cw = new Vec3d(nbttagcompound.getDouble("x"), nbttagcompound.getDouble("y"), nbttagcompound.getDouble("z"));
/*      */     } 
/*      */     
/*  258 */     this.field_192040_cp = compound.getBoolean("seenCredits");
/*      */     
/*  260 */     if (compound.hasKey("recipeBook", 10))
/*      */     {
/*  262 */       this.field_192041_cq.func_192825_a(compound.getCompoundTag("recipeBook"));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_191522_a(DataFixer p_191522_0_) {
/*  268 */     p_191522_0_.registerWalker(FixTypes.PLAYER, new IDataWalker()
/*      */         {
/*      */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*      */           {
/*  272 */             if (compound.hasKey("RootVehicle", 10)) {
/*      */               
/*  274 */               NBTTagCompound nbttagcompound = compound.getCompoundTag("RootVehicle");
/*      */               
/*  276 */               if (nbttagcompound.hasKey("Entity", 10))
/*      */               {
/*  278 */                 nbttagcompound.setTag("Entity", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, nbttagcompound.getCompoundTag("Entity"), versionIn));
/*      */               }
/*      */             } 
/*      */             
/*  282 */             return compound;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  292 */     super.writeEntityToNBT(compound);
/*  293 */     compound.setInteger("playerGameType", this.interactionManager.getGameType().getID());
/*  294 */     compound.setBoolean("seenCredits", this.field_192040_cp);
/*      */     
/*  296 */     if (this.field_193110_cw != null) {
/*      */       
/*  298 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  299 */       nbttagcompound.setDouble("x", this.field_193110_cw.xCoord);
/*  300 */       nbttagcompound.setDouble("y", this.field_193110_cw.yCoord);
/*  301 */       nbttagcompound.setDouble("z", this.field_193110_cw.zCoord);
/*  302 */       compound.setTag("enteredNetherPosition", (NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  305 */     Entity entity1 = getLowestRidingEntity();
/*  306 */     Entity entity = getRidingEntity();
/*      */     
/*  308 */     if (entity != null && entity1 != this && entity1.getRecursivePassengersByType(EntityPlayerMP.class).size() == 1) {
/*      */       
/*  310 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  311 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*  312 */       entity1.writeToNBTOptional(nbttagcompound2);
/*  313 */       nbttagcompound1.setUniqueId("Attach", entity.getUniqueID());
/*  314 */       nbttagcompound1.setTag("Entity", (NBTBase)nbttagcompound2);
/*  315 */       compound.setTag("RootVehicle", (NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  318 */     compound.setTag("recipeBook", (NBTBase)this.field_192041_cq.func_192824_e());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/*  326 */     super.addExperienceLevel(levels);
/*  327 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_192024_a(ItemStack p_192024_1_, int p_192024_2_) {
/*  332 */     super.func_192024_a(p_192024_1_, p_192024_2_);
/*  333 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addSelfToInternalCraftingInventory() {
/*  338 */     this.openContainer.addListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {
/*  346 */     super.sendEnterCombat();
/*  347 */     this.connection.sendPacket((Packet)new SPacketCombatEvent(getCombatTracker(), SPacketCombatEvent.Event.ENTER_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {
/*  355 */     super.sendEndCombat();
/*  356 */     this.connection.sendPacket((Packet)new SPacketCombatEvent(getCombatTracker(), SPacketCombatEvent.Event.END_COMBAT));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_191955_a(IBlockState p_191955_1_) {
/*  361 */     CriteriaTriggers.field_192124_d.func_192193_a(this, p_191955_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   protected CooldownTracker createCooldownTracker() {
/*  366 */     return (CooldownTracker)new CooldownTrackerServer(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  374 */     this.interactionManager.updateBlockRemoving();
/*  375 */     this.respawnInvulnerabilityTicks--;
/*      */     
/*  377 */     if (this.hurtResistantTime > 0)
/*      */     {
/*  379 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  382 */     this.openContainer.detectAndSendChanges();
/*      */     
/*  384 */     if (!this.world.isRemote && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  386 */       closeScreen();
/*  387 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  390 */     while (!this.entityRemoveQueue.isEmpty()) {
/*      */       
/*  392 */       int i = Math.min(this.entityRemoveQueue.size(), 2147483647);
/*  393 */       int[] aint = new int[i];
/*  394 */       Iterator<Integer> iterator = this.entityRemoveQueue.iterator();
/*  395 */       int j = 0;
/*      */       
/*  397 */       while (iterator.hasNext() && j < i) {
/*      */         
/*  399 */         aint[j++] = ((Integer)iterator.next()).intValue();
/*  400 */         iterator.remove();
/*      */       } 
/*      */       
/*  403 */       this.connection.sendPacket((Packet)new SPacketDestroyEntities(aint));
/*      */     } 
/*      */     
/*  406 */     Entity entity = getSpectatingEntity();
/*      */     
/*  408 */     if (entity != this)
/*      */     {
/*  410 */       if (entity.isEntityAlive()) {
/*      */         
/*  412 */         setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*  413 */         this.mcServer.getPlayerList().serverUpdateMovingPlayer(this);
/*      */         
/*  415 */         if (isSneaking())
/*      */         {
/*  417 */           setSpectatingEntity((Entity)this);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  422 */         setSpectatingEntity((Entity)this);
/*      */       } 
/*      */     }
/*      */     
/*  426 */     CriteriaTriggers.field_193135_v.func_193182_a(this);
/*      */     
/*  428 */     if (this.field_193107_ct != null)
/*      */     {
/*  430 */       CriteriaTriggers.field_193133_t.func_193162_a(this, this.field_193107_ct, this.ticksExisted - this.field_193108_cu);
/*      */     }
/*      */     
/*  433 */     this.field_192042_bX.func_192741_b(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateEntity() {
/*      */     try {
/*  440 */       super.onUpdate();
/*      */       
/*  442 */       for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
/*      */         
/*  444 */         ItemStack itemstack = this.inventory.getStackInSlot(i);
/*      */         
/*  446 */         if (!itemstack.func_190926_b() && itemstack.getItem().isMap()) {
/*      */           
/*  448 */           Packet<?> packet = ((ItemMapBase)itemstack.getItem()).createMapDataPacket(itemstack, this.world, this);
/*      */           
/*  450 */           if (packet != null)
/*      */           {
/*  452 */             this.connection.sendPacket(packet);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  457 */       if (getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || ((this.foodStats.getSaturationLevel() == 0.0F)) != this.wasHungry) {
/*      */         
/*  459 */         this.connection.sendPacket((Packet)new SPacketUpdateHealth(getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
/*  460 */         this.lastHealth = getHealth();
/*  461 */         this.lastFoodLevel = this.foodStats.getFoodLevel();
/*  462 */         this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0F);
/*      */       } 
/*      */       
/*  465 */       if (getHealth() + getAbsorptionAmount() != this.lastHealthScore) {
/*      */         
/*  467 */         this.lastHealthScore = getHealth() + getAbsorptionAmount();
/*  468 */         updateScorePoints(IScoreCriteria.HEALTH, MathHelper.ceil(this.lastHealthScore));
/*      */       } 
/*      */       
/*  471 */       if (this.foodStats.getFoodLevel() != this.lastFoodScore) {
/*      */         
/*  473 */         this.lastFoodScore = this.foodStats.getFoodLevel();
/*  474 */         updateScorePoints(IScoreCriteria.FOOD, MathHelper.ceil(this.lastFoodScore));
/*      */       } 
/*      */       
/*  477 */       if (getAir() != this.lastAirScore) {
/*      */         
/*  479 */         this.lastAirScore = getAir();
/*  480 */         updateScorePoints(IScoreCriteria.AIR, MathHelper.ceil(this.lastAirScore));
/*      */       } 
/*      */       
/*  483 */       if (getTotalArmorValue() != this.lastArmorScore) {
/*      */         
/*  485 */         this.lastArmorScore = getTotalArmorValue();
/*  486 */         updateScorePoints(IScoreCriteria.ARMOR, MathHelper.ceil(this.lastArmorScore));
/*      */       } 
/*      */       
/*  489 */       if (this.experienceTotal != this.lastExperienceScore) {
/*      */         
/*  491 */         this.lastExperienceScore = this.experienceTotal;
/*  492 */         updateScorePoints(IScoreCriteria.XP, MathHelper.ceil(this.lastExperienceScore));
/*      */       } 
/*      */       
/*  495 */       if (this.experienceLevel != this.lastLevelScore) {
/*      */         
/*  497 */         this.lastLevelScore = this.experienceLevel;
/*  498 */         updateScorePoints(IScoreCriteria.LEVEL, MathHelper.ceil(this.lastLevelScore));
/*      */       } 
/*      */       
/*  501 */       if (this.experienceTotal != this.lastExperience) {
/*      */         
/*  503 */         this.lastExperience = this.experienceTotal;
/*  504 */         this.connection.sendPacket((Packet)new SPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
/*      */       } 
/*      */       
/*  507 */       if (this.ticksExisted % 20 == 0)
/*      */       {
/*  509 */         CriteriaTriggers.field_192135_o.func_192215_a(this);
/*      */       }
/*      */     }
/*  512 */     catch (Throwable throwable) {
/*      */       
/*  514 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
/*  515 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
/*  516 */       addEntityCrashInfo(crashreportcategory);
/*  517 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateScorePoints(IScoreCriteria criteria, int points) {
/*  523 */     for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(criteria)) {
/*      */       
/*  525 */       Score score = getWorldScoreboard().getOrCreateScore(getName(), scoreobjective);
/*  526 */       score.setScorePoints(points);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  535 */     boolean flag = this.world.getGameRules().getBoolean("showDeathMessages");
/*  536 */     this.connection.sendPacket((Packet)new SPacketCombatEvent(getCombatTracker(), SPacketCombatEvent.Event.ENTITY_DIED, flag));
/*      */     
/*  538 */     if (flag) {
/*      */       
/*  540 */       Team team = getTeam();
/*      */       
/*  542 */       if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
/*      */         
/*  544 */         if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS)
/*      */         {
/*  546 */           this.mcServer.getPlayerList().sendMessageToAllTeamMembers(this, getCombatTracker().getDeathMessage());
/*      */         }
/*  548 */         else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM)
/*      */         {
/*  550 */           this.mcServer.getPlayerList().sendMessageToTeamOrAllPlayers(this, getCombatTracker().getDeathMessage());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  555 */         this.mcServer.getPlayerList().sendChatMsg(getCombatTracker().getDeathMessage());
/*      */       } 
/*      */     } 
/*      */     
/*  559 */     func_192030_dh();
/*      */     
/*  561 */     if (!this.world.getGameRules().getBoolean("keepInventory") && !isSpectator()) {
/*      */       
/*  563 */       func_190776_cN();
/*  564 */       this.inventory.dropAllItems();
/*      */     } 
/*      */     
/*  567 */     for (ScoreObjective scoreobjective : this.world.getScoreboard().getObjectivesFromCriteria(IScoreCriteria.DEATH_COUNT)) {
/*      */       
/*  569 */       Score score = getWorldScoreboard().getOrCreateScore(getName(), scoreobjective);
/*  570 */       score.incrementScore();
/*      */     } 
/*      */     
/*  573 */     EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */     
/*  575 */     if (entitylivingbase != null) {
/*      */       
/*  577 */       EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.ENTITY_EGGS.get(EntityList.func_191301_a((Entity)entitylivingbase));
/*      */       
/*  579 */       if (entitylist$entityegginfo != null)
/*      */       {
/*  581 */         addStat(entitylist$entityegginfo.entityKilledByStat);
/*      */       }
/*      */       
/*  584 */       entitylivingbase.func_191956_a((Entity)this, this.scoreValue, cause);
/*      */     } 
/*      */     
/*  587 */     addStat(StatList.DEATHS);
/*  588 */     takeStat(StatList.TIME_SINCE_DEATH);
/*  589 */     extinguish();
/*  590 */     setFlag(0, false);
/*  591 */     getCombatTracker().reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191956_a(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
/*  596 */     if (p_191956_1_ != this) {
/*      */       
/*  598 */       super.func_191956_a(p_191956_1_, p_191956_2_, p_191956_3_);
/*  599 */       addScore(p_191956_2_);
/*  600 */       Collection<ScoreObjective> collection = getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TOTAL_KILL_COUNT);
/*      */       
/*  602 */       if (p_191956_1_ instanceof EntityPlayer) {
/*      */         
/*  604 */         addStat(StatList.PLAYER_KILLS);
/*  605 */         collection.addAll(getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.PLAYER_KILL_COUNT));
/*      */       }
/*      */       else {
/*      */         
/*  609 */         addStat(StatList.MOB_KILLS);
/*      */       } 
/*      */       
/*  612 */       collection.addAll(func_192038_E(p_191956_1_));
/*      */       
/*  614 */       for (ScoreObjective scoreobjective : collection)
/*      */       {
/*  616 */         getWorldScoreboard().getOrCreateScore(getName(), scoreobjective).incrementScore();
/*      */       }
/*      */       
/*  619 */       CriteriaTriggers.field_192122_b.func_192211_a(this, p_191956_1_, p_191956_3_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<ScoreObjective> func_192038_E(Entity p_192038_1_) {
/*  625 */     String s = (p_192038_1_ instanceof EntityPlayer) ? p_192038_1_.getName() : p_192038_1_.getCachedUniqueIdString();
/*  626 */     ScorePlayerTeam scoreplayerteam = getWorldScoreboard().getPlayersTeam(getName());
/*      */     
/*  628 */     if (scoreplayerteam != null) {
/*      */       
/*  630 */       int i = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  632 */       if (i >= 0 && i < IScoreCriteria.KILLED_BY_TEAM.length)
/*      */       {
/*  634 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.KILLED_BY_TEAM[i])) {
/*      */           
/*  636 */           Score score = getWorldScoreboard().getOrCreateScore(s, scoreobjective);
/*  637 */           score.incrementScore();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  642 */     ScorePlayerTeam scoreplayerteam1 = getWorldScoreboard().getPlayersTeam(s);
/*      */     
/*  644 */     if (scoreplayerteam1 != null) {
/*      */       
/*  646 */       int j = scoreplayerteam1.getChatFormat().getColorIndex();
/*      */       
/*  648 */       if (j >= 0 && j < IScoreCriteria.TEAM_KILL.length)
/*      */       {
/*  650 */         return getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TEAM_KILL[j]);
/*      */       }
/*      */     } 
/*      */     
/*  654 */     return Lists.newArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  662 */     if (isEntityInvulnerable(source))
/*      */     {
/*  664 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  668 */     boolean flag = (this.mcServer.isDedicatedServer() && canPlayersAttack() && "fall".equals(source.damageType));
/*      */     
/*  670 */     if (!flag && this.respawnInvulnerabilityTicks > 0 && source != DamageSource.outOfWorld)
/*      */     {
/*  672 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  676 */     if (source instanceof net.minecraft.util.EntityDamageSource) {
/*      */       
/*  678 */       Entity entity = source.getEntity();
/*      */       
/*  680 */       if (entity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entity))
/*      */       {
/*  682 */         return false;
/*      */       }
/*      */       
/*  685 */       if (entity instanceof EntityArrow) {
/*      */         
/*  687 */         EntityArrow entityarrow = (EntityArrow)entity;
/*      */         
/*  689 */         if (entityarrow.shootingEntity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entityarrow.shootingEntity))
/*      */         {
/*  691 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  696 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/*  703 */     return !canPlayersAttack() ? false : super.canAttackPlayer(other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canPlayersAttack() {
/*  711 */     return this.mcServer.isPVPEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity changeDimension(int dimensionIn) {
/*  717 */     this.invulnerableDimensionChange = true;
/*      */     
/*  719 */     if (this.dimension == 0 && dimensionIn == -1) {
/*      */       
/*  721 */       this.field_193110_cw = new Vec3d(this.posX, this.posY, this.posZ);
/*      */     }
/*  723 */     else if (this.dimension != -1 && dimensionIn != 0) {
/*      */       
/*  725 */       this.field_193110_cw = null;
/*      */     } 
/*      */     
/*  728 */     if (this.dimension == 1 && dimensionIn == 1) {
/*      */       
/*  730 */       this.world.removeEntity((Entity)this);
/*      */       
/*  732 */       if (!this.playerConqueredTheEnd) {
/*      */         
/*  734 */         this.playerConqueredTheEnd = true;
/*  735 */         this.connection.sendPacket((Packet)new SPacketChangeGameState(4, this.field_192040_cp ? 0.0F : 1.0F));
/*  736 */         this.field_192040_cp = true;
/*      */       } 
/*      */       
/*  739 */       return (Entity)this;
/*      */     } 
/*      */ 
/*      */     
/*  743 */     if (this.dimension == 0 && dimensionIn == 1)
/*      */     {
/*  745 */       dimensionIn = 1;
/*      */     }
/*      */     
/*  748 */     this.mcServer.getPlayerList().changePlayerDimension(this, dimensionIn);
/*  749 */     this.connection.sendPacket((Packet)new SPacketEffect(1032, BlockPos.ORIGIN, 0, false));
/*  750 */     this.lastExperience = -1;
/*  751 */     this.lastHealth = -1.0F;
/*  752 */     this.lastFoodLevel = -1;
/*  753 */     return (Entity)this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/*  759 */     if (player.isSpectator())
/*      */     {
/*  761 */       return (getSpectatingEntity() == this);
/*      */     }
/*      */ 
/*      */     
/*  765 */     return isSpectator() ? false : super.isSpectatedByPlayer(player);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendTileEntityUpdate(TileEntity p_147097_1_) {
/*  771 */     if (p_147097_1_ != null) {
/*      */       
/*  773 */       SPacketUpdateTileEntity spacketupdatetileentity = p_147097_1_.getUpdatePacket();
/*      */       
/*  775 */       if (spacketupdatetileentity != null)
/*      */       {
/*  777 */         this.connection.sendPacket((Packet)spacketupdatetileentity);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity entityIn, int quantity) {
/*  787 */     super.onItemPickup(entityIn, quantity);
/*  788 */     this.openContainer.detectAndSendChanges();
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.SleepResult trySleep(BlockPos bedLocation) {
/*  793 */     EntityPlayer.SleepResult entityplayer$sleepresult = super.trySleep(bedLocation);
/*      */     
/*  795 */     if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
/*      */       
/*  797 */       addStat(StatList.SLEEP_IN_BED);
/*  798 */       SPacketUseBed sPacketUseBed = new SPacketUseBed(this, bedLocation);
/*  799 */       getServerWorld().getEntityTracker().sendToAllTrackingEntity((Entity)this, (Packet)sPacketUseBed);
/*  800 */       this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  801 */       this.connection.sendPacket((Packet)sPacketUseBed);
/*  802 */       CriteriaTriggers.field_192136_p.func_192215_a(this);
/*      */     } 
/*      */     
/*  805 */     return entityplayer$sleepresult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
/*  813 */     if (isPlayerSleeping())
/*      */     {
/*  815 */       getServerWorld().getEntityTracker().sendToTrackingAndSelf((Entity)this, (Packet)new SPacketAnimation((Entity)this, 2));
/*      */     }
/*      */     
/*  818 */     super.wakeUpPlayer(immediately, updateWorldFlag, setSpawn);
/*      */     
/*  820 */     if (this.connection != null)
/*      */     {
/*  822 */       this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity entityIn, boolean force) {
/*  828 */     Entity entity = getRidingEntity();
/*      */     
/*  830 */     if (!super.startRiding(entityIn, force))
/*      */     {
/*  832 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  836 */     Entity entity1 = getRidingEntity();
/*      */     
/*  838 */     if (entity1 != entity && this.connection != null)
/*      */     {
/*  840 */       this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */     
/*  843 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountRidingEntity() {
/*  849 */     Entity entity = getRidingEntity();
/*  850 */     super.dismountRidingEntity();
/*  851 */     Entity entity1 = getRidingEntity();
/*      */     
/*  853 */     if (entity1 != entity && this.connection != null)
/*      */     {
/*  855 */       this.connection.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/*  864 */     return !(!super.isEntityInvulnerable(source) && !isInvulnerableDimensionChange());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
/*      */ 
/*      */   
/*      */   protected void frostWalk(BlockPos pos) {
/*  873 */     if (!isSpectator())
/*      */     {
/*  875 */       super.frostWalk(pos);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleFalling(double y, boolean onGroundIn) {
/*  884 */     int i = MathHelper.floor(this.posX);
/*  885 */     int j = MathHelper.floor(this.posY - 0.20000000298023224D);
/*  886 */     int k = MathHelper.floor(this.posZ);
/*  887 */     BlockPos blockpos = new BlockPos(i, j, k);
/*  888 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */     
/*  890 */     if (iblockstate.getMaterial() == Material.AIR) {
/*      */       
/*  892 */       BlockPos blockpos1 = blockpos.down();
/*  893 */       IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/*  894 */       Block block = iblockstate1.getBlock();
/*      */       
/*  896 */       if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFenceGate) {
/*      */         
/*  898 */         blockpos = blockpos1;
/*  899 */         iblockstate = iblockstate1;
/*      */       } 
/*      */     } 
/*      */     
/*  903 */     super.updateFallState(y, onGroundIn, iblockstate, blockpos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/*  908 */     signTile.setPlayer(this);
/*  909 */     this.connection.sendPacket((Packet)new SPacketSignEditorOpen(signTile.getPos()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNextWindowId() {
/*  917 */     this.currentWindowId = this.currentWindowId % 100 + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/*  922 */     if (guiOwner instanceof ILootContainer && ((ILootContainer)guiOwner).getLootTable() != null && isSpectator()) {
/*      */       
/*  924 */       addChatComponentMessage((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)), true);
/*      */     }
/*      */     else {
/*      */       
/*  928 */       getNextWindowId();
/*  929 */       this.connection.sendPacket((Packet)new SPacketOpenWindow(this.currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
/*  930 */       this.openContainer = guiOwner.createContainer(this.inventory, this);
/*  931 */       this.openContainer.windowId = this.currentWindowId;
/*  932 */       this.openContainer.addListener(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/*  941 */     if (chestInventory instanceof ILootContainer && ((ILootContainer)chestInventory).getLootTable() != null && isSpectator()) {
/*      */       
/*  943 */       addChatComponentMessage((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).setStyle((new Style()).setColor(TextFormatting.RED)), true);
/*      */     }
/*      */     else {
/*      */       
/*  947 */       if (this.openContainer != this.inventoryContainer)
/*      */       {
/*  949 */         closeScreen();
/*      */       }
/*      */       
/*  952 */       if (chestInventory instanceof ILockableContainer) {
/*      */         
/*  954 */         ILockableContainer ilockablecontainer = (ILockableContainer)chestInventory;
/*      */         
/*  956 */         if (ilockablecontainer.isLocked() && !canOpen(ilockablecontainer.getLockCode()) && !isSpectator()) {
/*      */           
/*  958 */           this.connection.sendPacket((Packet)new SPacketChat((ITextComponent)new TextComponentTranslation("container.isLocked", new Object[] { chestInventory.getDisplayName() }), ChatType.GAME_INFO));
/*  959 */           this.connection.sendPacket((Packet)new SPacketSoundEffect(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, this.posX, this.posY, this.posZ, 1.0F, 1.0F));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  964 */       getNextWindowId();
/*      */       
/*  966 */       if (chestInventory instanceof IInteractionObject) {
/*      */         
/*  968 */         this.connection.sendPacket((Packet)new SPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  969 */         this.openContainer = ((IInteractionObject)chestInventory).createContainer(this.inventory, this);
/*      */       }
/*      */       else {
/*      */         
/*  973 */         this.connection.sendPacket((Packet)new SPacketOpenWindow(this.currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  974 */         this.openContainer = (Container)new ContainerChest(this.inventory, chestInventory, this);
/*      */       } 
/*      */       
/*  977 */       this.openContainer.windowId = this.currentWindowId;
/*  978 */       this.openContainer.addListener(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/*  984 */     getNextWindowId();
/*  985 */     this.openContainer = (Container)new ContainerMerchant(this.inventory, villager, this.world);
/*  986 */     this.openContainer.windowId = this.currentWindowId;
/*  987 */     this.openContainer.addListener(this);
/*  988 */     InventoryMerchant inventoryMerchant = ((ContainerMerchant)this.openContainer).getMerchantInventory();
/*  989 */     ITextComponent itextcomponent = villager.getDisplayName();
/*  990 */     this.connection.sendPacket((Packet)new SPacketOpenWindow(this.currentWindowId, "minecraft:villager", itextcomponent, inventoryMerchant.getSizeInventory()));
/*  991 */     MerchantRecipeList merchantrecipelist = villager.getRecipes(this);
/*      */     
/*  993 */     if (merchantrecipelist != null) {
/*      */       
/*  995 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  996 */       packetbuffer.writeInt(this.currentWindowId);
/*  997 */       merchantrecipelist.writeToBuf(packetbuffer);
/*  998 */       this.connection.sendPacket((Packet)new SPacketCustomPayload("MC|TrList", packetbuffer));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {
/* 1004 */     if (this.openContainer != this.inventoryContainer)
/*      */     {
/* 1006 */       closeScreen();
/*      */     }
/*      */     
/* 1009 */     getNextWindowId();
/* 1010 */     this.connection.sendPacket((Packet)new SPacketOpenWindow(this.currentWindowId, "EntityHorse", inventoryIn.getDisplayName(), inventoryIn.getSizeInventory(), horse.getEntityId()));
/* 1011 */     this.openContainer = (Container)new ContainerHorseInventory(this.inventory, inventoryIn, horse, this);
/* 1012 */     this.openContainer.windowId = this.currentWindowId;
/* 1013 */     this.openContainer.addListener(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void openBook(ItemStack stack, EnumHand hand) {
/* 1018 */     Item item = stack.getItem();
/*      */     
/* 1020 */     if (item == Items.WRITTEN_BOOK) {
/*      */       
/* 1022 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 1023 */       packetbuffer.writeEnumValue((Enum)hand);
/* 1024 */       this.connection.sendPacket((Packet)new SPacketCustomPayload("MC|BOpen", packetbuffer));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {
/* 1030 */     commandBlock.setSendToClient(true);
/* 1031 */     sendTileEntityUpdate((TileEntity)commandBlock);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/* 1040 */     if (!(containerToSend.getSlot(slotInd) instanceof net.minecraft.inventory.SlotCrafting)) {
/*      */       
/* 1042 */       if (containerToSend == this.inventoryContainer)
/*      */       {
/* 1044 */         CriteriaTriggers.field_192125_e.func_192208_a(this, this.inventory);
/*      */       }
/*      */       
/* 1047 */       if (!this.isChangingQuantityOnly)
/*      */       {
/* 1049 */         this.connection.sendPacket((Packet)new SPacketSetSlot(containerToSend.windowId, slotInd, stack));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendContainerToPlayer(Container containerIn) {
/* 1056 */     updateCraftingInventory(containerIn, containerIn.getInventory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCraftingInventory(Container containerToSend, NonNullList<ItemStack> itemsList) {
/* 1064 */     this.connection.sendPacket((Packet)new SPacketWindowItems(containerToSend.windowId, itemsList));
/* 1065 */     this.connection.sendPacket((Packet)new SPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
/* 1075 */     this.connection.sendPacket((Packet)new SPacketWindowProperty(containerIn.windowId, varToUpdate, newValue));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
/* 1080 */     for (int i = 0; i < inventory.getFieldCount(); i++)
/*      */     {
/* 1082 */       this.connection.sendPacket((Packet)new SPacketWindowProperty(containerIn.windowId, i, inventory.getField(i)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeScreen() {
/* 1091 */     this.connection.sendPacket((Packet)new SPacketCloseWindow(this.openContainer.windowId));
/* 1092 */     closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateHeldItem() {
/* 1100 */     if (!this.isChangingQuantityOnly)
/*      */     {
/* 1102 */       this.connection.sendPacket((Packet)new SPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/* 1111 */     this.openContainer.onContainerClosed(this);
/* 1112 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityActionState(float strafe, float forward, boolean jumping, boolean sneaking) {
/* 1117 */     if (isRiding()) {
/*      */       
/* 1119 */       if (strafe >= -1.0F && strafe <= 1.0F)
/*      */       {
/* 1121 */         this.moveStrafing = strafe;
/*      */       }
/*      */       
/* 1124 */       if (forward >= -1.0F && forward <= 1.0F)
/*      */       {
/* 1126 */         this.field_191988_bg = forward;
/*      */       }
/*      */       
/* 1129 */       this.isJumping = jumping;
/* 1130 */       setSneaking(sneaking);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/* 1139 */     if (stat != null) {
/*      */       
/* 1141 */       this.statsFile.increaseStat(this, stat, amount);
/*      */       
/* 1143 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria()))
/*      */       {
/* 1145 */         getWorldScoreboard().getOrCreateScore(getName(), scoreobjective).increaseScore(amount);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void takeStat(StatBase stat) {
/* 1152 */     if (stat != null) {
/*      */       
/* 1154 */       this.statsFile.unlockAchievement(this, stat, 0);
/*      */       
/* 1156 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria()))
/*      */       {
/* 1158 */         getWorldScoreboard().getOrCreateScore(getName(), scoreobjective).setScorePoints(0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_192021_a(List<IRecipe> p_192021_1_) {
/* 1165 */     this.field_192041_cq.func_193835_a(p_192021_1_, this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_193102_a(ResourceLocation[] p_193102_1_) {
/* 1170 */     List<IRecipe> list = Lists.newArrayList(); byte b; int i;
/*      */     ResourceLocation[] arrayOfResourceLocation;
/* 1172 */     for (i = (arrayOfResourceLocation = p_193102_1_).length, b = 0; b < i; ) { ResourceLocation resourcelocation = arrayOfResourceLocation[b];
/*      */       
/* 1174 */       list.add(CraftingManager.func_193373_a(resourcelocation));
/*      */       b++; }
/*      */     
/* 1177 */     func_192021_a(list);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_192022_b(List<IRecipe> p_192022_1_) {
/* 1182 */     this.field_192041_cq.func_193834_b(p_192022_1_, this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void mountEntityAndWakeUp() {
/* 1187 */     this.field_193109_cv = true;
/* 1188 */     removePassengers();
/*      */     
/* 1190 */     if (this.sleeping)
/*      */     {
/* 1192 */       wakeUpPlayer(true, false, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_193105_t() {
/* 1198 */     return this.field_193109_cv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerHealthUpdated() {
/* 1207 */     this.lastHealth = -1.0E8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(ITextComponent chatComponent, boolean p_146105_2_) {
/* 1212 */     this.connection.sendPacket((Packet)new SPacketChat(chatComponent, p_146105_2_ ? ChatType.GAME_INFO : ChatType.CHAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/* 1220 */     if (!this.activeItemStack.func_190926_b() && isHandActive()) {
/*      */       
/* 1222 */       this.connection.sendPacket((Packet)new SPacketEntityStatus((Entity)this, (byte)9));
/* 1223 */       super.onItemUseFinish();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_193104_a(EntityPlayerMP p_193104_1_, boolean p_193104_2_) {
/* 1229 */     if (p_193104_2_) {
/*      */       
/* 1231 */       this.inventory.copyInventory(p_193104_1_.inventory);
/* 1232 */       setHealth(p_193104_1_.getHealth());
/* 1233 */       this.foodStats = p_193104_1_.foodStats;
/* 1234 */       this.experienceLevel = p_193104_1_.experienceLevel;
/* 1235 */       this.experienceTotal = p_193104_1_.experienceTotal;
/* 1236 */       this.experience = p_193104_1_.experience;
/* 1237 */       setScore(p_193104_1_.getScore());
/* 1238 */       this.lastPortalPos = p_193104_1_.lastPortalPos;
/* 1239 */       this.lastPortalVec = p_193104_1_.lastPortalVec;
/* 1240 */       this.teleportDirection = p_193104_1_.teleportDirection;
/*      */     }
/* 1242 */     else if (this.world.getGameRules().getBoolean("keepInventory") || p_193104_1_.isSpectator()) {
/*      */       
/* 1244 */       this.inventory.copyInventory(p_193104_1_.inventory);
/* 1245 */       this.experienceLevel = p_193104_1_.experienceLevel;
/* 1246 */       this.experienceTotal = p_193104_1_.experienceTotal;
/* 1247 */       this.experience = p_193104_1_.experience;
/* 1248 */       setScore(p_193104_1_.getScore());
/*      */     } 
/*      */     
/* 1251 */     this.xpSeed = p_193104_1_.xpSeed;
/* 1252 */     this.theInventoryEnderChest = p_193104_1_.theInventoryEnderChest;
/* 1253 */     getDataManager().set(PLAYER_MODEL_FLAG, p_193104_1_.getDataManager().get(PLAYER_MODEL_FLAG));
/* 1254 */     this.lastExperience = -1;
/* 1255 */     this.lastHealth = -1.0F;
/* 1256 */     this.lastFoodLevel = -1;
/* 1257 */     this.field_192041_cq.func_193824_a((RecipeBook)p_193104_1_.field_192041_cq);
/* 1258 */     this.entityRemoveQueue.addAll(p_193104_1_.entityRemoveQueue);
/* 1259 */     this.field_192040_cp = p_193104_1_.field_192040_cp;
/* 1260 */     this.field_193110_cw = p_193104_1_.field_193110_cw;
/* 1261 */     func_192029_h(p_193104_1_.func_192023_dk());
/* 1262 */     func_192031_i(p_193104_1_.func_192025_dl());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/* 1267 */     super.onNewPotionEffect(id);
/* 1268 */     this.connection.sendPacket((Packet)new SPacketEntityEffect(getEntityId(), id));
/*      */     
/* 1270 */     if (id.getPotion() == MobEffects.LEVITATION) {
/*      */       
/* 1272 */       this.field_193108_cu = this.ticksExisted;
/* 1273 */       this.field_193107_ct = new Vec3d(this.posX, this.posY, this.posZ);
/*      */     } 
/*      */     
/* 1276 */     CriteriaTriggers.field_193139_z.func_193153_a(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/* 1281 */     super.onChangedPotionEffect(id, p_70695_2_);
/* 1282 */     this.connection.sendPacket((Packet)new SPacketEntityEffect(getEntityId(), id));
/* 1283 */     CriteriaTriggers.field_193139_z.func_193153_a(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/* 1288 */     super.onFinishedPotionEffect(effect);
/* 1289 */     this.connection.sendPacket((Packet)new SPacketRemoveEntityEffect(getEntityId(), effect.getPotion()));
/*      */     
/* 1291 */     if (effect.getPotion() == MobEffects.LEVITATION)
/*      */     {
/* 1293 */       this.field_193107_ct = null;
/*      */     }
/*      */     
/* 1296 */     CriteriaTriggers.field_193139_z.func_193153_a(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 1304 */     this.connection.setPlayerLocation(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/* 1312 */     getServerWorld().getEntityTracker().sendToTrackingAndSelf((Entity)this, (Packet)new SPacketAnimation(entityHit, 4));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/* 1317 */     getServerWorld().getEntityTracker().sendToTrackingAndSelf((Entity)this, (Packet)new SPacketAnimation(entityHit, 5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/* 1325 */     if (this.connection != null) {
/*      */       
/* 1327 */       this.connection.sendPacket((Packet)new SPacketPlayerAbilities(this.capabilities));
/* 1328 */       updatePotionMetadata();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldServer getServerWorld() {
/* 1334 */     return (WorldServer)this.world;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(GameType gameType) {
/* 1342 */     this.interactionManager.setGameType(gameType);
/* 1343 */     this.connection.sendPacket((Packet)new SPacketChangeGameState(3, gameType.getID()));
/*      */     
/* 1345 */     if (gameType == GameType.SPECTATOR) {
/*      */       
/* 1347 */       func_192030_dh();
/* 1348 */       dismountRidingEntity();
/*      */     }
/*      */     else {
/*      */       
/* 1352 */       setSpectatingEntity((Entity)this);
/*      */     } 
/*      */     
/* 1355 */     sendPlayerAbilities();
/* 1356 */     markPotionsDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpectator() {
/* 1364 */     return (this.interactionManager.getGameType() == GameType.SPECTATOR);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCreative() {
/* 1369 */     return (this.interactionManager.getGameType() == GameType.CREATIVE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(ITextComponent component) {
/* 1377 */     this.connection.sendPacket((Packet)new SPacketChat(component));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 1385 */     if ("seed".equals(commandName) && !this.mcServer.isDedicatedServer())
/*      */     {
/* 1387 */       return true;
/*      */     }
/* 1389 */     if (!"tell".equals(commandName) && !"help".equals(commandName) && !"me".equals(commandName) && !"trigger".equals(commandName)) {
/*      */       
/* 1391 */       if (this.mcServer.getPlayerList().canSendCommands(getGameProfile())) {
/*      */         
/* 1393 */         UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.mcServer.getPlayerList().getOppedPlayers().getEntry(getGameProfile());
/*      */         
/* 1395 */         if (userlistopsentry != null)
/*      */         {
/* 1397 */           return (userlistopsentry.getPermissionLevel() >= permLevel);
/*      */         }
/*      */ 
/*      */         
/* 1401 */         return (this.mcServer.getOpPermissionLevel() >= permLevel);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1406 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlayerIP() {
/* 1420 */     String s = this.connection.netManager.getRemoteAddress().toString();
/* 1421 */     s = s.substring(s.indexOf("/") + 1);
/* 1422 */     s = s.substring(0, s.indexOf(":"));
/* 1423 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleClientSettings(CPacketClientSettings packetIn) {
/* 1428 */     this.language = packetIn.getLang();
/* 1429 */     this.chatVisibility = packetIn.getChatVisibility();
/* 1430 */     this.chatColours = packetIn.isColorsEnabled();
/* 1431 */     getDataManager().set(PLAYER_MODEL_FLAG, Byte.valueOf((byte)packetIn.getModelPartFlags()));
/* 1432 */     getDataManager().set(MAIN_HAND, Byte.valueOf((byte)((packetIn.getMainHand() == EnumHandSide.LEFT) ? 0 : 1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 1437 */     return this.chatVisibility;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadResourcePack(String url, String hash) {
/* 1442 */     this.connection.sendPacket((Packet)new SPacketResourcePackSend(url, hash));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1451 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markPlayerActive() {
/* 1456 */     this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatisticsManagerServer getStatFile() {
/* 1464 */     return this.statsFile;
/*      */   }
/*      */ 
/*      */   
/*      */   public RecipeBookServer func_192037_E() {
/* 1469 */     return this.field_192041_cq;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/* 1477 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1479 */       this.connection.sendPacket((Packet)new SPacketDestroyEntities(new int[] { entityIn.getEntityId() }));
/*      */     }
/*      */     else {
/*      */       
/* 1483 */       this.entityRemoveQueue.add(Integer.valueOf(entityIn.getEntityId()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addEntity(Entity entityIn) {
/* 1489 */     this.entityRemoveQueue.remove(Integer.valueOf(entityIn.getEntityId()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/* 1498 */     if (isSpectator()) {
/*      */       
/* 1500 */       resetPotionEffectMetadata();
/* 1501 */       setInvisible(true);
/*      */     }
/*      */     else {
/*      */       
/* 1505 */       super.updatePotionMetadata();
/*      */     } 
/*      */     
/* 1508 */     getServerWorld().getEntityTracker().updateVisibility(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getSpectatingEntity() {
/* 1513 */     return (this.spectatingEntity == null) ? (Entity)this : this.spectatingEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpectatingEntity(Entity entityToSpectate) {
/* 1518 */     Entity entity = getSpectatingEntity();
/* 1519 */     this.spectatingEntity = (entityToSpectate == null) ? (Entity)this : entityToSpectate;
/*      */     
/* 1521 */     if (entity != this.spectatingEntity) {
/*      */       
/* 1523 */       this.connection.sendPacket((Packet)new SPacketCamera(this.spectatingEntity));
/* 1524 */       setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void decrementTimeUntilPortal() {
/* 1533 */     if (this.timeUntilPortal > 0 && !this.invulnerableDimensionChange)
/*      */     {
/* 1535 */       this.timeUntilPortal--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1545 */     if (this.interactionManager.getGameType() == GameType.SPECTATOR) {
/*      */       
/* 1547 */       setSpectatingEntity(targetEntity);
/*      */     }
/*      */     else {
/*      */       
/* 1551 */       super.attackTargetEntityWithCurrentItem(targetEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLastActiveTime() {
/* 1557 */     return this.playerLastActiveTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ITextComponent getTabListDisplayName() {
/* 1568 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void swingArm(EnumHand hand) {
/* 1573 */     super.swingArm(hand);
/* 1574 */     resetCooldown();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvulnerableDimensionChange() {
/* 1579 */     return this.invulnerableDimensionChange;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearInvulnerableDimensionChange() {
/* 1584 */     this.invulnerableDimensionChange = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setElytraFlying() {
/* 1589 */     setFlag(7, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearElytraFlying() {
/* 1594 */     setFlag(7, true);
/* 1595 */     setFlag(7, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerAdvancements func_192039_O() {
/* 1600 */     return this.field_192042_bX;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Vec3d func_193106_Q() {
/* 1606 */     return this.field_193110_cw;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\player\EntityPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
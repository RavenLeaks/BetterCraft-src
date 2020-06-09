/*     */ package net.minecraft.world.end;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.ContiguousSet;
/*     */ import com.google.common.collect.DiscreteDomain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Range;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.pattern.BlockMatcher;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.dragon.phase.PhaseList;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.BossInfo;
/*     */ import net.minecraft.world.BossInfoServer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.biome.BiomeEndDecorator;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*     */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class DragonFightManager {
/*  53 */   private static final Logger LOGGER = LogManager.getLogger();
/*  54 */   private static final Predicate<EntityPlayerMP> VALID_PLAYER = Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.withinRange(0.0D, 128.0D, 0.0D, 192.0D));
/*  55 */   private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer((ITextComponent)new TextComponentTranslation("entity.EnderDragon.name", new Object[0]), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setPlayEndBossMusic(true).setCreateFog(true);
/*     */   private final WorldServer world;
/*  57 */   private final List<Integer> gateways = Lists.newArrayList();
/*     */   
/*     */   private final BlockPattern portalPattern;
/*     */   private int ticksSinceDragonSeen;
/*     */   private int aliveCrystals;
/*     */   private int ticksSinceCrystalsScanned;
/*     */   private int ticksSinceLastPlayerScan;
/*     */   private boolean dragonKilled;
/*     */   private boolean previouslyKilled;
/*     */   private UUID dragonUniqueId;
/*     */   private boolean scanForLegacyFight = true;
/*     */   private BlockPos exitPortalLocation;
/*     */   private DragonSpawnManager respawnState;
/*     */   private int respawnStateTicks;
/*     */   private List<EntityEnderCrystal> crystals;
/*     */   
/*     */   public DragonFightManager(WorldServer worldIn, NBTTagCompound compound) {
/*  74 */     this.world = worldIn;
/*     */     
/*  76 */     if (compound.hasKey("DragonKilled", 99)) {
/*     */       
/*  78 */       if (compound.hasUniqueId("DragonUUID"))
/*     */       {
/*  80 */         this.dragonUniqueId = compound.getUniqueId("DragonUUID");
/*     */       }
/*     */       
/*  83 */       this.dragonKilled = compound.getBoolean("DragonKilled");
/*  84 */       this.previouslyKilled = compound.getBoolean("PreviouslyKilled");
/*     */       
/*  86 */       if (compound.getBoolean("IsRespawning"))
/*     */       {
/*  88 */         this.respawnState = DragonSpawnManager.START;
/*     */       }
/*     */       
/*  91 */       if (compound.hasKey("ExitPortalLocation", 10))
/*     */       {
/*  93 */         this.exitPortalLocation = NBTUtil.getPosFromTag(compound.getCompoundTag("ExitPortalLocation"));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  98 */       this.dragonKilled = true;
/*  99 */       this.previouslyKilled = true;
/*     */     } 
/*     */     
/* 102 */     if (compound.hasKey("Gateways", 9)) {
/*     */       
/* 104 */       NBTTagList nbttaglist = compound.getTagList("Gateways", 3);
/*     */       
/* 106 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 108 */         this.gateways.add(Integer.valueOf(nbttaglist.getIntAt(i)));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 113 */       this.gateways.addAll((Collection<? extends Integer>)ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(20)), DiscreteDomain.integers()));
/* 114 */       Collections.shuffle(this.gateways, new Random(worldIn.getSeed()));
/*     */     } 
/*     */     
/* 117 */     this.portalPattern = FactoryBlockPattern.start().aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  " }).aisle(new String[] { "       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       " }).where('#', BlockWorldState.hasState((Predicate)BlockMatcher.forBlock(Blocks.BEDROCK))).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompound() {
/* 122 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 124 */     if (this.dragonUniqueId != null)
/*     */     {
/* 126 */       nbttagcompound.setUniqueId("DragonUUID", this.dragonUniqueId);
/*     */     }
/*     */     
/* 129 */     nbttagcompound.setBoolean("DragonKilled", this.dragonKilled);
/* 130 */     nbttagcompound.setBoolean("PreviouslyKilled", this.previouslyKilled);
/*     */     
/* 132 */     if (this.exitPortalLocation != null)
/*     */     {
/* 134 */       nbttagcompound.setTag("ExitPortalLocation", (NBTBase)NBTUtil.createPosTag(this.exitPortalLocation));
/*     */     }
/*     */     
/* 137 */     NBTTagList nbttaglist = new NBTTagList();
/* 138 */     Iterator<Integer> iterator = this.gateways.iterator();
/*     */     
/* 140 */     while (iterator.hasNext()) {
/*     */       
/* 142 */       int i = ((Integer)iterator.next()).intValue();
/* 143 */       nbttaglist.appendTag((NBTBase)new NBTTagInt(i));
/*     */     } 
/*     */     
/* 146 */     nbttagcompound.setTag("Gateways", (NBTBase)nbttaglist);
/* 147 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 152 */     this.bossInfo.setVisible(!this.dragonKilled);
/*     */     
/* 154 */     if (++this.ticksSinceLastPlayerScan >= 20) {
/*     */       
/* 156 */       updateplayers();
/* 157 */       this.ticksSinceLastPlayerScan = 0;
/*     */     } 
/*     */     
/* 160 */     if (!this.bossInfo.getPlayers().isEmpty()) {
/*     */       
/* 162 */       if (this.scanForLegacyFight) {
/*     */         
/* 164 */         LOGGER.info("Scanning for legacy world dragon fight...");
/* 165 */         loadChunks();
/* 166 */         this.scanForLegacyFight = false;
/* 167 */         boolean flag = hasDragonBeenKilled();
/*     */         
/* 169 */         if (flag) {
/*     */           
/* 171 */           LOGGER.info("Found that the dragon has been killed in this world already.");
/* 172 */           this.previouslyKilled = true;
/*     */         }
/*     */         else {
/*     */           
/* 176 */           LOGGER.info("Found that the dragon has not yet been killed in this world.");
/* 177 */           this.previouslyKilled = false;
/* 178 */           generatePortal(false);
/*     */         } 
/*     */         
/* 181 */         List<EntityDragon> list = this.world.getEntities(EntityDragon.class, EntitySelectors.IS_ALIVE);
/*     */         
/* 183 */         if (list.isEmpty()) {
/*     */           
/* 185 */           this.dragonKilled = true;
/*     */         }
/*     */         else {
/*     */           
/* 189 */           EntityDragon entitydragon = list.get(0);
/* 190 */           this.dragonUniqueId = entitydragon.getUniqueID();
/* 191 */           LOGGER.info("Found that there's a dragon still alive ({})", entitydragon);
/* 192 */           this.dragonKilled = false;
/*     */           
/* 194 */           if (!flag) {
/*     */             
/* 196 */             LOGGER.info("But we didn't have a portal, let's remove it.");
/* 197 */             entitydragon.setDead();
/* 198 */             this.dragonUniqueId = null;
/*     */           } 
/*     */         } 
/*     */         
/* 202 */         if (!this.previouslyKilled && this.dragonKilled)
/*     */         {
/* 204 */           this.dragonKilled = false;
/*     */         }
/*     */       } 
/*     */       
/* 208 */       if (this.respawnState != null) {
/*     */         
/* 210 */         if (this.crystals == null) {
/*     */           
/* 212 */           this.respawnState = null;
/* 213 */           respawnDragon();
/*     */         } 
/*     */         
/* 216 */         this.respawnState.process(this.world, this, this.crystals, this.respawnStateTicks++, this.exitPortalLocation);
/*     */       } 
/*     */       
/* 219 */       if (!this.dragonKilled) {
/*     */         
/* 221 */         if (this.dragonUniqueId == null || ++this.ticksSinceDragonSeen >= 1200) {
/*     */           
/* 223 */           loadChunks();
/* 224 */           List<EntityDragon> list1 = this.world.getEntities(EntityDragon.class, EntitySelectors.IS_ALIVE);
/*     */           
/* 226 */           if (list1.isEmpty()) {
/*     */             
/* 228 */             LOGGER.debug("Haven't seen the dragon, respawning it");
/* 229 */             func_192445_m();
/*     */           }
/*     */           else {
/*     */             
/* 233 */             LOGGER.debug("Haven't seen our dragon, but found another one to use.");
/* 234 */             this.dragonUniqueId = ((EntityDragon)list1.get(0)).getUniqueID();
/*     */           } 
/*     */           
/* 237 */           this.ticksSinceDragonSeen = 0;
/*     */         } 
/*     */         
/* 240 */         if (++this.ticksSinceCrystalsScanned >= 100) {
/*     */           
/* 242 */           findAliveCrystals();
/* 243 */           this.ticksSinceCrystalsScanned = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRespawnState(DragonSpawnManager state) {
/* 251 */     if (this.respawnState == null)
/*     */     {
/* 253 */       throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
/*     */     }
/*     */ 
/*     */     
/* 257 */     this.respawnStateTicks = 0;
/*     */     
/* 259 */     if (state == DragonSpawnManager.END) {
/*     */       
/* 261 */       this.respawnState = null;
/* 262 */       this.dragonKilled = false;
/* 263 */       EntityDragon entitydragon = func_192445_m();
/*     */       
/* 265 */       for (EntityPlayerMP entityplayermp : this.bossInfo.getPlayers())
/*     */       {
/* 267 */         CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, (Entity)entitydragon);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 272 */       this.respawnState = state;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasDragonBeenKilled() {
/* 279 */     for (int i = -8; i <= 8; i++) {
/*     */       
/* 281 */       for (int j = -8; j <= 8; j++) {
/*     */         
/* 283 */         Chunk chunk = this.world.getChunkFromChunkCoords(i, j);
/*     */         
/* 285 */         for (TileEntity tileentity : chunk.getTileEntityMap().values()) {
/*     */           
/* 287 */           if (tileentity instanceof net.minecraft.tileentity.TileEntityEndPortal)
/*     */           {
/* 289 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BlockPattern.PatternHelper findExitPortal() {
/* 301 */     for (int i = -8; i <= 8; i++) {
/*     */       
/* 303 */       for (int j = -8; j <= 8; j++) {
/*     */         
/* 305 */         Chunk chunk = this.world.getChunkFromChunkCoords(i, j);
/*     */         
/* 307 */         for (TileEntity tileentity : chunk.getTileEntityMap().values()) {
/*     */           
/* 309 */           if (tileentity instanceof net.minecraft.tileentity.TileEntityEndPortal) {
/*     */             
/* 311 */             BlockPattern.PatternHelper blockpattern$patternhelper = this.portalPattern.match((World)this.world, tileentity.getPos());
/*     */             
/* 313 */             if (blockpattern$patternhelper != null) {
/*     */               
/* 315 */               BlockPos blockpos = blockpattern$patternhelper.translateOffset(3, 3, 3).getPos();
/*     */               
/* 317 */               if (this.exitPortalLocation == null && blockpos.getX() == 0 && blockpos.getZ() == 0)
/*     */               {
/* 319 */                 this.exitPortalLocation = blockpos;
/*     */               }
/*     */               
/* 322 */               return blockpattern$patternhelper;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     int k = this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION).getY();
/*     */     
/* 331 */     for (int l = k; l >= 0; l--) {
/*     */       
/* 333 */       BlockPattern.PatternHelper blockpattern$patternhelper1 = this.portalPattern.match((World)this.world, new BlockPos(WorldGenEndPodium.END_PODIUM_LOCATION.getX(), l, WorldGenEndPodium.END_PODIUM_LOCATION.getZ()));
/*     */       
/* 335 */       if (blockpattern$patternhelper1 != null) {
/*     */         
/* 337 */         if (this.exitPortalLocation == null)
/*     */         {
/* 339 */           this.exitPortalLocation = blockpattern$patternhelper1.translateOffset(3, 3, 3).getPos();
/*     */         }
/*     */         
/* 342 */         return blockpattern$patternhelper1;
/*     */       } 
/*     */     } 
/*     */     
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadChunks() {
/* 351 */     for (int i = -8; i <= 8; i++) {
/*     */       
/* 353 */       for (int j = -8; j <= 8; j++)
/*     */       {
/* 355 */         this.world.getChunkFromChunkCoords(i, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateplayers() {
/* 362 */     Set<EntityPlayerMP> set = Sets.newHashSet();
/*     */     
/* 364 */     for (EntityPlayerMP entityplayermp : this.world.getPlayers(EntityPlayerMP.class, VALID_PLAYER)) {
/*     */       
/* 366 */       this.bossInfo.addPlayer(entityplayermp);
/* 367 */       set.add(entityplayermp);
/*     */     } 
/*     */     
/* 370 */     Set<EntityPlayerMP> set1 = Sets.newHashSet(this.bossInfo.getPlayers());
/* 371 */     set1.removeAll(set);
/*     */     
/* 373 */     for (EntityPlayerMP entityplayermp1 : set1)
/*     */     {
/* 375 */       this.bossInfo.removePlayer(entityplayermp1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void findAliveCrystals() {
/* 381 */     this.ticksSinceCrystalsScanned = 0;
/* 382 */     this.aliveCrystals = 0; byte b; int i;
/*     */     WorldGenSpikes.EndSpike[] arrayOfEndSpike;
/* 384 */     for (i = (arrayOfEndSpike = BiomeEndDecorator.getSpikesForWorld((World)this.world)).length, b = 0; b < i; ) { WorldGenSpikes.EndSpike worldgenspikes$endspike = arrayOfEndSpike[b];
/*     */       
/* 386 */       this.aliveCrystals += this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, worldgenspikes$endspike.getTopBoundingBox()).size();
/*     */       b++; }
/*     */     
/* 389 */     LOGGER.debug("Found {} end crystals still alive", Integer.valueOf(this.aliveCrystals));
/*     */   }
/*     */ 
/*     */   
/*     */   public void processDragonDeath(EntityDragon dragon) {
/* 394 */     if (dragon.getUniqueID().equals(this.dragonUniqueId)) {
/*     */       
/* 396 */       this.bossInfo.setPercent(0.0F);
/* 397 */       this.bossInfo.setVisible(false);
/* 398 */       generatePortal(true);
/* 399 */       spawnNewGateway();
/*     */       
/* 401 */       if (!this.previouslyKilled)
/*     */       {
/* 403 */         this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
/*     */       }
/*     */       
/* 406 */       this.previouslyKilled = true;
/* 407 */       this.dragonKilled = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnNewGateway() {
/* 413 */     if (!this.gateways.isEmpty()) {
/*     */       
/* 415 */       int i = ((Integer)this.gateways.remove(this.gateways.size() - 1)).intValue();
/* 416 */       int j = (int)(96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * i)));
/* 417 */       int k = (int)(96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * i)));
/* 418 */       generateGateway(new BlockPos(j, 75, k));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateGateway(BlockPos pos) {
/* 424 */     this.world.playEvent(3000, pos, 0);
/* 425 */     (new WorldGenEndGateway()).generate((World)this.world, new Random(), pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generatePortal(boolean active) {
/* 430 */     WorldGenEndPodium worldgenendpodium = new WorldGenEndPodium(active);
/*     */     
/* 432 */     if (this.exitPortalLocation == null)
/*     */     {
/* 434 */       for (this.exitPortalLocation = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION).down(); this.world.getBlockState(this.exitPortalLocation).getBlock() == Blocks.BEDROCK && this.exitPortalLocation.getY() > this.world.getSeaLevel(); this.exitPortalLocation = this.exitPortalLocation.down());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 440 */     worldgenendpodium.generate((World)this.world, new Random(), this.exitPortalLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityDragon func_192445_m() {
/* 445 */     this.world.getChunkFromBlockCoords(new BlockPos(0, 128, 0));
/* 446 */     EntityDragon entitydragon = new EntityDragon((World)this.world);
/* 447 */     entitydragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
/* 448 */     entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.world.rand.nextFloat() * 360.0F, 0.0F);
/* 449 */     this.world.spawnEntityInWorld((Entity)entitydragon);
/* 450 */     this.dragonUniqueId = entitydragon.getUniqueID();
/* 451 */     return entitydragon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dragonUpdate(EntityDragon dragonIn) {
/* 456 */     if (dragonIn.getUniqueID().equals(this.dragonUniqueId)) {
/*     */       
/* 458 */       this.bossInfo.setPercent(dragonIn.getHealth() / dragonIn.getMaxHealth());
/* 459 */       this.ticksSinceDragonSeen = 0;
/*     */       
/* 461 */       if (dragonIn.hasCustomName())
/*     */       {
/* 463 */         this.bossInfo.setName(dragonIn.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumAliveCrystals() {
/* 470 */     return this.aliveCrystals;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrystalDestroyed(EntityEnderCrystal crystal, DamageSource dmgSrc) {
/* 475 */     if (this.respawnState != null && this.crystals.contains(crystal)) {
/*     */       
/* 477 */       LOGGER.debug("Aborting respawn sequence");
/* 478 */       this.respawnState = null;
/* 479 */       this.respawnStateTicks = 0;
/* 480 */       resetSpikeCrystals();
/* 481 */       generatePortal(true);
/*     */     }
/*     */     else {
/*     */       
/* 485 */       findAliveCrystals();
/* 486 */       Entity entity = this.world.getEntityFromUuid(this.dragonUniqueId);
/*     */       
/* 488 */       if (entity instanceof EntityDragon)
/*     */       {
/* 490 */         ((EntityDragon)entity).onCrystalDestroyed(crystal, new BlockPos((Entity)crystal), dmgSrc);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPreviouslyKilledDragon() {
/* 497 */     return this.previouslyKilled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void respawnDragon() {
/* 502 */     if (this.dragonKilled && this.respawnState == null) {
/*     */       
/* 504 */       BlockPos blockpos = this.exitPortalLocation;
/*     */       
/* 506 */       if (blockpos == null) {
/*     */         
/* 508 */         LOGGER.debug("Tried to respawn, but need to find the portal first.");
/* 509 */         BlockPattern.PatternHelper blockpattern$patternhelper = findExitPortal();
/*     */         
/* 511 */         if (blockpattern$patternhelper == null) {
/*     */           
/* 513 */           LOGGER.debug("Couldn't find a portal, so we made one.");
/* 514 */           generatePortal(true);
/*     */         }
/*     */         else {
/*     */           
/* 518 */           LOGGER.debug("Found the exit portal & temporarily using it.");
/*     */         } 
/*     */         
/* 521 */         blockpos = this.exitPortalLocation;
/*     */       } 
/*     */       
/* 524 */       List<EntityEnderCrystal> list1 = Lists.newArrayList();
/* 525 */       BlockPos blockpos1 = blockpos.up(1);
/*     */       
/* 527 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 529 */         List<EntityEnderCrystal> list = this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, new AxisAlignedBB(blockpos1.offset(enumfacing, 2)));
/*     */         
/* 531 */         if (list.isEmpty()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 536 */         list1.addAll(list);
/*     */       } 
/*     */       
/* 539 */       LOGGER.debug("Found all crystals, respawning dragon.");
/* 540 */       respawnDragon(list1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void respawnDragon(List<EntityEnderCrystal> crystalsIn) {
/* 546 */     if (this.dragonKilled && this.respawnState == null) {
/*     */       
/* 548 */       for (BlockPattern.PatternHelper blockpattern$patternhelper = findExitPortal(); blockpattern$patternhelper != null; blockpattern$patternhelper = findExitPortal()) {
/*     */         
/* 550 */         for (int i = 0; i < this.portalPattern.getPalmLength(); i++) {
/*     */           
/* 552 */           for (int j = 0; j < this.portalPattern.getThumbLength(); j++) {
/*     */             
/* 554 */             for (int k = 0; k < this.portalPattern.getFingerLength(); k++) {
/*     */               
/* 556 */               BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, k);
/*     */               
/* 558 */               if (blockworldstate.getBlockState().getBlock() == Blocks.BEDROCK || blockworldstate.getBlockState().getBlock() == Blocks.END_PORTAL)
/*     */               {
/* 560 */                 this.world.setBlockState(blockworldstate.getPos(), Blocks.END_STONE.getDefaultState());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 567 */       this.respawnState = DragonSpawnManager.START;
/* 568 */       this.respawnStateTicks = 0;
/* 569 */       generatePortal(false);
/* 570 */       this.crystals = crystalsIn;
/*     */     } 
/*     */   } public void resetSpikeCrystals() {
/*     */     byte b;
/*     */     int i;
/*     */     WorldGenSpikes.EndSpike[] arrayOfEndSpike;
/* 576 */     for (i = (arrayOfEndSpike = BiomeEndDecorator.getSpikesForWorld((World)this.world)).length, b = 0; b < i; ) { WorldGenSpikes.EndSpike worldgenspikes$endspike = arrayOfEndSpike[b];
/*     */       
/* 578 */       for (EntityEnderCrystal entityendercrystal : this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, worldgenspikes$endspike.getTopBoundingBox())) {
/*     */         
/* 580 */         entityendercrystal.setEntityInvulnerable(false);
/* 581 */         entityendercrystal.setBeamTarget(null);
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\end\DragonFightManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
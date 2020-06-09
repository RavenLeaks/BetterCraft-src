/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class StatList
/*     */ {
/*  25 */   protected static final Map<String, StatBase> ID_TO_STAT_MAP = Maps.newHashMap();
/*  26 */   public static final List<StatBase> ALL_STATS = Lists.newArrayList();
/*  27 */   public static final List<StatBase> BASIC_STATS = Lists.newArrayList();
/*  28 */   public static final List<StatCrafting> USE_ITEM_STATS = Lists.newArrayList();
/*  29 */   public static final List<StatCrafting> MINE_BLOCK_STATS = Lists.newArrayList();
/*     */ 
/*     */   
/*  32 */   public static final StatBase LEAVE_GAME = (new StatBasic("stat.leaveGame", (ITextComponent)new TextComponentTranslation("stat.leaveGame", new Object[0]))).initIndependentStat().registerStat();
/*  33 */   public static final StatBase PLAY_ONE_MINUTE = (new StatBasic("stat.playOneMinute", (ITextComponent)new TextComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*  34 */   public static final StatBase TIME_SINCE_DEATH = (new StatBasic("stat.timeSinceDeath", (ITextComponent)new TextComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*  35 */   public static final StatBase SNEAK_TIME = (new StatBasic("stat.sneakTime", (ITextComponent)new TextComponentTranslation("stat.sneakTime", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*  36 */   public static final StatBase WALK_ONE_CM = (new StatBasic("stat.walkOneCm", (ITextComponent)new TextComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  37 */   public static final StatBase CROUCH_ONE_CM = (new StatBasic("stat.crouchOneCm", (ITextComponent)new TextComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  38 */   public static final StatBase SPRINT_ONE_CM = (new StatBasic("stat.sprintOneCm", (ITextComponent)new TextComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  41 */   public static final StatBase SWIM_ONE_CM = (new StatBasic("stat.swimOneCm", (ITextComponent)new TextComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  44 */   public static final StatBase FALL_ONE_CM = (new StatBasic("stat.fallOneCm", (ITextComponent)new TextComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  45 */   public static final StatBase CLIMB_ONE_CM = (new StatBasic("stat.climbOneCm", (ITextComponent)new TextComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  46 */   public static final StatBase FLY_ONE_CM = (new StatBasic("stat.flyOneCm", (ITextComponent)new TextComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  47 */   public static final StatBase DIVE_ONE_CM = (new StatBasic("stat.diveOneCm", (ITextComponent)new TextComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  48 */   public static final StatBase MINECART_ONE_CM = (new StatBasic("stat.minecartOneCm", (ITextComponent)new TextComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  49 */   public static final StatBase BOAT_ONE_CM = (new StatBasic("stat.boatOneCm", (ITextComponent)new TextComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  50 */   public static final StatBase PIG_ONE_CM = (new StatBasic("stat.pigOneCm", (ITextComponent)new TextComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  51 */   public static final StatBase HORSE_ONE_CM = (new StatBasic("stat.horseOneCm", (ITextComponent)new TextComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  52 */   public static final StatBase AVIATE_ONE_CM = (new StatBasic("stat.aviateOneCm", (ITextComponent)new TextComponentTranslation("stat.aviateOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  55 */   public static final StatBase JUMP = (new StatBasic("stat.jump", (ITextComponent)new TextComponentTranslation("stat.jump", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  58 */   public static final StatBase DROP = (new StatBasic("stat.drop", (ITextComponent)new TextComponentTranslation("stat.drop", new Object[0]))).initIndependentStat().registerStat();
/*  59 */   public static final StatBase DAMAGE_DEALT = (new StatBasic("stat.damageDealt", (ITextComponent)new TextComponentTranslation("stat.damageDealt", new Object[0]), StatBase.divideByTen)).registerStat();
/*  60 */   public static final StatBase DAMAGE_TAKEN = (new StatBasic("stat.damageTaken", (ITextComponent)new TextComponentTranslation("stat.damageTaken", new Object[0]), StatBase.divideByTen)).registerStat();
/*  61 */   public static final StatBase DEATHS = (new StatBasic("stat.deaths", (ITextComponent)new TextComponentTranslation("stat.deaths", new Object[0]))).registerStat();
/*  62 */   public static final StatBase MOB_KILLS = (new StatBasic("stat.mobKills", (ITextComponent)new TextComponentTranslation("stat.mobKills", new Object[0]))).registerStat();
/*     */ 
/*     */   
/*  65 */   public static final StatBase ANIMALS_BRED = (new StatBasic("stat.animalsBred", (ITextComponent)new TextComponentTranslation("stat.animalsBred", new Object[0]))).registerStat();
/*     */ 
/*     */   
/*  68 */   public static final StatBase PLAYER_KILLS = (new StatBasic("stat.playerKills", (ITextComponent)new TextComponentTranslation("stat.playerKills", new Object[0]))).registerStat();
/*  69 */   public static final StatBase FISH_CAUGHT = (new StatBasic("stat.fishCaught", (ITextComponent)new TextComponentTranslation("stat.fishCaught", new Object[0]))).registerStat();
/*  70 */   public static final StatBase TALKED_TO_VILLAGER = (new StatBasic("stat.talkedToVillager", (ITextComponent)new TextComponentTranslation("stat.talkedToVillager", new Object[0]))).registerStat();
/*  71 */   public static final StatBase TRADED_WITH_VILLAGER = (new StatBasic("stat.tradedWithVillager", (ITextComponent)new TextComponentTranslation("stat.tradedWithVillager", new Object[0]))).registerStat();
/*  72 */   public static final StatBase CAKE_SLICES_EATEN = (new StatBasic("stat.cakeSlicesEaten", (ITextComponent)new TextComponentTranslation("stat.cakeSlicesEaten", new Object[0]))).registerStat();
/*  73 */   public static final StatBase CAULDRON_FILLED = (new StatBasic("stat.cauldronFilled", (ITextComponent)new TextComponentTranslation("stat.cauldronFilled", new Object[0]))).registerStat();
/*  74 */   public static final StatBase CAULDRON_USED = (new StatBasic("stat.cauldronUsed", (ITextComponent)new TextComponentTranslation("stat.cauldronUsed", new Object[0]))).registerStat();
/*  75 */   public static final StatBase ARMOR_CLEANED = (new StatBasic("stat.armorCleaned", (ITextComponent)new TextComponentTranslation("stat.armorCleaned", new Object[0]))).registerStat();
/*  76 */   public static final StatBase BANNER_CLEANED = (new StatBasic("stat.bannerCleaned", (ITextComponent)new TextComponentTranslation("stat.bannerCleaned", new Object[0]))).registerStat();
/*  77 */   public static final StatBase BREWINGSTAND_INTERACTION = (new StatBasic("stat.brewingstandInteraction", (ITextComponent)new TextComponentTranslation("stat.brewingstandInteraction", new Object[0]))).registerStat();
/*  78 */   public static final StatBase BEACON_INTERACTION = (new StatBasic("stat.beaconInteraction", (ITextComponent)new TextComponentTranslation("stat.beaconInteraction", new Object[0]))).registerStat();
/*  79 */   public static final StatBase DROPPER_INSPECTED = (new StatBasic("stat.dropperInspected", (ITextComponent)new TextComponentTranslation("stat.dropperInspected", new Object[0]))).registerStat();
/*  80 */   public static final StatBase HOPPER_INSPECTED = (new StatBasic("stat.hopperInspected", (ITextComponent)new TextComponentTranslation("stat.hopperInspected", new Object[0]))).registerStat();
/*  81 */   public static final StatBase DISPENSER_INSPECTED = (new StatBasic("stat.dispenserInspected", (ITextComponent)new TextComponentTranslation("stat.dispenserInspected", new Object[0]))).registerStat();
/*  82 */   public static final StatBase NOTEBLOCK_PLAYED = (new StatBasic("stat.noteblockPlayed", (ITextComponent)new TextComponentTranslation("stat.noteblockPlayed", new Object[0]))).registerStat();
/*  83 */   public static final StatBase NOTEBLOCK_TUNED = (new StatBasic("stat.noteblockTuned", (ITextComponent)new TextComponentTranslation("stat.noteblockTuned", new Object[0]))).registerStat();
/*  84 */   public static final StatBase FLOWER_POTTED = (new StatBasic("stat.flowerPotted", (ITextComponent)new TextComponentTranslation("stat.flowerPotted", new Object[0]))).registerStat();
/*  85 */   public static final StatBase TRAPPED_CHEST_TRIGGERED = (new StatBasic("stat.trappedChestTriggered", (ITextComponent)new TextComponentTranslation("stat.trappedChestTriggered", new Object[0]))).registerStat();
/*  86 */   public static final StatBase ENDERCHEST_OPENED = (new StatBasic("stat.enderchestOpened", (ITextComponent)new TextComponentTranslation("stat.enderchestOpened", new Object[0]))).registerStat();
/*  87 */   public static final StatBase ITEM_ENCHANTED = (new StatBasic("stat.itemEnchanted", (ITextComponent)new TextComponentTranslation("stat.itemEnchanted", new Object[0]))).registerStat();
/*  88 */   public static final StatBase RECORD_PLAYED = (new StatBasic("stat.recordPlayed", (ITextComponent)new TextComponentTranslation("stat.recordPlayed", new Object[0]))).registerStat();
/*  89 */   public static final StatBase FURNACE_INTERACTION = (new StatBasic("stat.furnaceInteraction", (ITextComponent)new TextComponentTranslation("stat.furnaceInteraction", new Object[0]))).registerStat();
/*  90 */   public static final StatBase CRAFTING_TABLE_INTERACTION = (new StatBasic("stat.craftingTableInteraction", (ITextComponent)new TextComponentTranslation("stat.workbenchInteraction", new Object[0]))).registerStat();
/*  91 */   public static final StatBase CHEST_OPENED = (new StatBasic("stat.chestOpened", (ITextComponent)new TextComponentTranslation("stat.chestOpened", new Object[0]))).registerStat();
/*  92 */   public static final StatBase SLEEP_IN_BED = (new StatBasic("stat.sleepInBed", (ITextComponent)new TextComponentTranslation("stat.sleepInBed", new Object[0]))).registerStat();
/*  93 */   public static final StatBase field_191272_ae = (new StatBasic("stat.shulkerBoxOpened", (ITextComponent)new TextComponentTranslation("stat.shulkerBoxOpened", new Object[0]))).registerStat();
/*  94 */   private static final StatBase[] BLOCKS_STATS = new StatBase[4096];
/*  95 */   private static final StatBase[] CRAFTS_STATS = new StatBase[32000];
/*     */ 
/*     */   
/*  98 */   private static final StatBase[] OBJECT_USE_STATS = new StatBase[32000];
/*     */ 
/*     */   
/* 101 */   private static final StatBase[] OBJECT_BREAK_STATS = new StatBase[32000];
/* 102 */   private static final StatBase[] OBJECTS_PICKED_UP_STATS = new StatBase[32000];
/* 103 */   private static final StatBase[] OBJECTS_DROPPED_STATS = new StatBase[32000];
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getBlockStats(Block blockIn) {
/* 108 */     return BLOCKS_STATS[Block.getIdFromBlock(blockIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getCraftStats(Item itemIn) {
/* 114 */     return CRAFTS_STATS[Item.getIdFromItem(itemIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getObjectUseStats(Item itemIn) {
/* 120 */     return OBJECT_USE_STATS[Item.getIdFromItem(itemIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getObjectBreakStats(Item itemIn) {
/* 126 */     return OBJECT_BREAK_STATS[Item.getIdFromItem(itemIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getObjectsPickedUpStats(Item itemIn) {
/* 132 */     return OBJECTS_PICKED_UP_STATS[Item.getIdFromItem(itemIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getDroppedObjectStats(Item itemIn) {
/* 138 */     return OBJECTS_DROPPED_STATS[Item.getIdFromItem(itemIn)];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/* 143 */     initMiningStats();
/* 144 */     initStats();
/* 145 */     initItemDepleteStats();
/* 146 */     initCraftableStats();
/* 147 */     initPickedUpAndDroppedStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initCraftableStats() {
/* 156 */     Set<Item> set = Sets.newHashSet();
/*     */     
/* 158 */     for (IRecipe irecipe : CraftingManager.field_193380_a) {
/*     */       
/* 160 */       ItemStack itemstack = irecipe.getRecipeOutput();
/*     */       
/* 162 */       if (!itemstack.func_190926_b())
/*     */       {
/* 164 */         set.add(irecipe.getRecipeOutput().getItem());
/*     */       }
/*     */     } 
/*     */     
/* 168 */     for (ItemStack itemstack1 : FurnaceRecipes.instance().getSmeltingList().values())
/*     */     {
/* 170 */       set.add(itemstack1.getItem());
/*     */     }
/*     */     
/* 173 */     for (Item item : set) {
/*     */       
/* 175 */       if (item != null) {
/*     */ 
/*     */ 
/*     */         
/* 179 */         int i = Item.getIdFromItem(item);
/* 180 */         String s = getItemName(item);
/*     */         
/* 182 */         if (s != null)
/*     */         {
/* 184 */           CRAFTS_STATS[i] = (new StatCrafting("stat.craftItem.", s, (ITextComponent)new TextComponentTranslation("stat.craftItem", new Object[] { (new ItemStack(item)).getTextComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     replaceAllSimilarBlocks(CRAFTS_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initMiningStats() {
/* 194 */     for (Block block : Block.REGISTRY) {
/*     */       
/* 196 */       Item item = Item.getItemFromBlock(block);
/*     */       
/* 198 */       if (item != Items.field_190931_a) {
/*     */         
/* 200 */         int i = Block.getIdFromBlock(block);
/* 201 */         String s = getItemName(item);
/*     */         
/* 203 */         if (s != null && block.getEnableStats()) {
/*     */           
/* 205 */           BLOCKS_STATS[i] = (new StatCrafting("stat.mineBlock.", s, (ITextComponent)new TextComponentTranslation("stat.mineBlock", new Object[] { (new ItemStack(block)).getTextComponent() }), item)).registerStat();
/* 206 */           MINE_BLOCK_STATS.add((StatCrafting)BLOCKS_STATS[i]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     replaceAllSimilarBlocks(BLOCKS_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initStats() {
/* 216 */     for (Item item : Item.REGISTRY) {
/*     */       
/* 218 */       if (item != null) {
/*     */         
/* 220 */         int i = Item.getIdFromItem(item);
/* 221 */         String s = getItemName(item);
/*     */         
/* 223 */         if (s != null) {
/*     */           
/* 225 */           OBJECT_USE_STATS[i] = (new StatCrafting("stat.useItem.", s, (ITextComponent)new TextComponentTranslation("stat.useItem", new Object[] { (new ItemStack(item)).getTextComponent() }), item)).registerStat();
/*     */           
/* 227 */           if (!(item instanceof net.minecraft.item.ItemBlock))
/*     */           {
/* 229 */             USE_ITEM_STATS.add((StatCrafting)OBJECT_USE_STATS[i]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     replaceAllSimilarBlocks(OBJECT_USE_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initItemDepleteStats() {
/* 240 */     for (Item item : Item.REGISTRY) {
/*     */       
/* 242 */       if (item != null) {
/*     */         
/* 244 */         int i = Item.getIdFromItem(item);
/* 245 */         String s = getItemName(item);
/*     */         
/* 247 */         if (s != null && item.isDamageable())
/*     */         {
/* 249 */           OBJECT_BREAK_STATS[i] = (new StatCrafting("stat.breakItem.", s, (ITextComponent)new TextComponentTranslation("stat.breakItem", new Object[] { (new ItemStack(item)).getTextComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     replaceAllSimilarBlocks(OBJECT_BREAK_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initPickedUpAndDroppedStats() {
/* 259 */     for (Item item : Item.REGISTRY) {
/*     */       
/* 261 */       if (item != null) {
/*     */         
/* 263 */         int i = Item.getIdFromItem(item);
/* 264 */         String s = getItemName(item);
/*     */         
/* 266 */         if (s != null) {
/*     */           
/* 268 */           OBJECTS_PICKED_UP_STATS[i] = (new StatCrafting("stat.pickup.", s, (ITextComponent)new TextComponentTranslation("stat.pickup", new Object[] { (new ItemStack(item)).getTextComponent() }), item)).registerStat();
/* 269 */           OBJECTS_DROPPED_STATS[i] = (new StatCrafting("stat.drop.", s, (ITextComponent)new TextComponentTranslation("stat.drop", new Object[] { (new ItemStack(item)).getTextComponent() }), item)).registerStat();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     replaceAllSimilarBlocks(OBJECT_BREAK_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getItemName(Item itemIn) {
/* 279 */     ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(itemIn);
/* 280 */     return (resourcelocation != null) ? resourcelocation.toString().replace(':', '.') : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceAllSimilarBlocks(StatBase[] stat) {
/* 288 */     mergeStatBases(stat, (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER);
/* 289 */     mergeStatBases(stat, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA);
/* 290 */     mergeStatBases(stat, Blocks.LIT_PUMPKIN, Blocks.PUMPKIN);
/* 291 */     mergeStatBases(stat, Blocks.LIT_FURNACE, Blocks.FURNACE);
/* 292 */     mergeStatBases(stat, Blocks.LIT_REDSTONE_ORE, Blocks.REDSTONE_ORE);
/* 293 */     mergeStatBases(stat, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.UNPOWERED_REPEATER);
/* 294 */     mergeStatBases(stat, (Block)Blocks.POWERED_COMPARATOR, (Block)Blocks.UNPOWERED_COMPARATOR);
/* 295 */     mergeStatBases(stat, Blocks.REDSTONE_TORCH, Blocks.UNLIT_REDSTONE_TORCH);
/* 296 */     mergeStatBases(stat, Blocks.LIT_REDSTONE_LAMP, Blocks.REDSTONE_LAMP);
/* 297 */     mergeStatBases(stat, (Block)Blocks.DOUBLE_STONE_SLAB, (Block)Blocks.STONE_SLAB);
/* 298 */     mergeStatBases(stat, (Block)Blocks.DOUBLE_WOODEN_SLAB, (Block)Blocks.WOODEN_SLAB);
/* 299 */     mergeStatBases(stat, (Block)Blocks.DOUBLE_STONE_SLAB2, (Block)Blocks.STONE_SLAB2);
/* 300 */     mergeStatBases(stat, (Block)Blocks.GRASS, Blocks.DIRT);
/* 301 */     mergeStatBases(stat, Blocks.FARMLAND, Blocks.DIRT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mergeStatBases(StatBase[] statBaseIn, Block block1, Block block2) {
/* 309 */     int i = Block.getIdFromBlock(block1);
/* 310 */     int j = Block.getIdFromBlock(block2);
/*     */     
/* 312 */     if (statBaseIn[i] != null && statBaseIn[j] == null) {
/*     */       
/* 314 */       statBaseIn[j] = statBaseIn[i];
/*     */     }
/*     */     else {
/*     */       
/* 318 */       ALL_STATS.remove(statBaseIn[i]);
/* 319 */       MINE_BLOCK_STATS.remove(statBaseIn[i]);
/* 320 */       BASIC_STATS.remove(statBaseIn[i]);
/* 321 */       statBaseIn[i] = statBaseIn[j];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatBase getStatKillEntity(EntityList.EntityEggInfo eggInfo) {
/* 327 */     String s = EntityList.func_191302_a(eggInfo.spawnedID);
/* 328 */     return (s == null) ? null : (new StatBase("stat.killEntity." + s, (ITextComponent)new TextComponentTranslation("stat.entityKill", new Object[] { new TextComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatBase getStatEntityKilledBy(EntityList.EntityEggInfo eggInfo) {
/* 333 */     String s = EntityList.func_191302_a(eggInfo.spawnedID);
/* 334 */     return (s == null) ? null : (new StatBase("stat.entityKilledBy." + s, (ITextComponent)new TextComponentTranslation("stat.entityKilledBy", new Object[] { new TextComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StatBase getOneShotStat(String statName) {
/* 340 */     return ID_TO_STAT_MAP.get(statName);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\StatList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
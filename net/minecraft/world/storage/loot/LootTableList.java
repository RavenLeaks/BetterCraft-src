/*     */ package net.minecraft.world.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class LootTableList
/*     */ {
/*  11 */   private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
/*  12 */   private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
/*  13 */   public static final ResourceLocation EMPTY = register("empty");
/*  14 */   public static final ResourceLocation CHESTS_SPAWN_BONUS_CHEST = register("chests/spawn_bonus_chest");
/*  15 */   public static final ResourceLocation CHESTS_END_CITY_TREASURE = register("chests/end_city_treasure");
/*  16 */   public static final ResourceLocation CHESTS_SIMPLE_DUNGEON = register("chests/simple_dungeon");
/*  17 */   public static final ResourceLocation CHESTS_VILLAGE_BLACKSMITH = register("chests/village_blacksmith");
/*  18 */   public static final ResourceLocation CHESTS_ABANDONED_MINESHAFT = register("chests/abandoned_mineshaft");
/*  19 */   public static final ResourceLocation CHESTS_NETHER_BRIDGE = register("chests/nether_bridge");
/*  20 */   public static final ResourceLocation CHESTS_STRONGHOLD_LIBRARY = register("chests/stronghold_library");
/*  21 */   public static final ResourceLocation CHESTS_STRONGHOLD_CROSSING = register("chests/stronghold_crossing");
/*  22 */   public static final ResourceLocation CHESTS_STRONGHOLD_CORRIDOR = register("chests/stronghold_corridor");
/*  23 */   public static final ResourceLocation CHESTS_DESERT_PYRAMID = register("chests/desert_pyramid");
/*  24 */   public static final ResourceLocation CHESTS_JUNGLE_TEMPLE = register("chests/jungle_temple");
/*  25 */   public static final ResourceLocation CHESTS_JUNGLE_TEMPLE_DISPENSER = register("chests/jungle_temple_dispenser");
/*  26 */   public static final ResourceLocation CHESTS_IGLOO_CHEST = register("chests/igloo_chest");
/*  27 */   public static final ResourceLocation field_191192_o = register("chests/woodland_mansion");
/*  28 */   public static final ResourceLocation ENTITIES_WITCH = register("entities/witch");
/*  29 */   public static final ResourceLocation ENTITIES_BLAZE = register("entities/blaze");
/*  30 */   public static final ResourceLocation ENTITIES_CREEPER = register("entities/creeper");
/*  31 */   public static final ResourceLocation ENTITIES_SPIDER = register("entities/spider");
/*  32 */   public static final ResourceLocation ENTITIES_CAVE_SPIDER = register("entities/cave_spider");
/*  33 */   public static final ResourceLocation ENTITIES_GIANT = register("entities/giant");
/*  34 */   public static final ResourceLocation ENTITIES_SILVERFISH = register("entities/silverfish");
/*  35 */   public static final ResourceLocation ENTITIES_ENDERMAN = register("entities/enderman");
/*  36 */   public static final ResourceLocation ENTITIES_GUARDIAN = register("entities/guardian");
/*  37 */   public static final ResourceLocation ENTITIES_ELDER_GUARDIAN = register("entities/elder_guardian");
/*  38 */   public static final ResourceLocation ENTITIES_SHULKER = register("entities/shulker");
/*  39 */   public static final ResourceLocation ENTITIES_IRON_GOLEM = register("entities/iron_golem");
/*  40 */   public static final ResourceLocation ENTITIES_SNOWMAN = register("entities/snowman");
/*  41 */   public static final ResourceLocation ENTITIES_RABBIT = register("entities/rabbit");
/*  42 */   public static final ResourceLocation ENTITIES_CHICKEN = register("entities/chicken");
/*  43 */   public static final ResourceLocation ENTITIES_PIG = register("entities/pig");
/*  44 */   public static final ResourceLocation ENTITIES_POLAR_BEAR = register("entities/polar_bear");
/*  45 */   public static final ResourceLocation ENTITIES_HORSE = register("entities/horse");
/*  46 */   public static final ResourceLocation field_191190_H = register("entities/donkey");
/*  47 */   public static final ResourceLocation field_191191_I = register("entities/mule");
/*  48 */   public static final ResourceLocation ENTITIES_ZOMBIE_HORSE = register("entities/zombie_horse");
/*  49 */   public static final ResourceLocation ENTITIES_SKELETON_HORSE = register("entities/skeleton_horse");
/*  50 */   public static final ResourceLocation ENTITIES_COW = register("entities/cow");
/*  51 */   public static final ResourceLocation ENTITIES_MUSHROOM_COW = register("entities/mushroom_cow");
/*  52 */   public static final ResourceLocation ENTITIES_WOLF = register("entities/wolf");
/*  53 */   public static final ResourceLocation ENTITIES_OCELOT = register("entities/ocelot");
/*  54 */   public static final ResourceLocation ENTITIES_SHEEP = register("entities/sheep");
/*  55 */   public static final ResourceLocation ENTITIES_SHEEP_WHITE = register("entities/sheep/white");
/*  56 */   public static final ResourceLocation ENTITIES_SHEEP_ORANGE = register("entities/sheep/orange");
/*  57 */   public static final ResourceLocation ENTITIES_SHEEP_MAGENTA = register("entities/sheep/magenta");
/*  58 */   public static final ResourceLocation ENTITIES_SHEEP_LIGHT_BLUE = register("entities/sheep/light_blue");
/*  59 */   public static final ResourceLocation ENTITIES_SHEEP_YELLOW = register("entities/sheep/yellow");
/*  60 */   public static final ResourceLocation ENTITIES_SHEEP_LIME = register("entities/sheep/lime");
/*  61 */   public static final ResourceLocation ENTITIES_SHEEP_PINK = register("entities/sheep/pink");
/*  62 */   public static final ResourceLocation ENTITIES_SHEEP_GRAY = register("entities/sheep/gray");
/*  63 */   public static final ResourceLocation ENTITIES_SHEEP_SILVER = register("entities/sheep/silver");
/*  64 */   public static final ResourceLocation ENTITIES_SHEEP_CYAN = register("entities/sheep/cyan");
/*  65 */   public static final ResourceLocation ENTITIES_SHEEP_PURPLE = register("entities/sheep/purple");
/*  66 */   public static final ResourceLocation ENTITIES_SHEEP_BLUE = register("entities/sheep/blue");
/*  67 */   public static final ResourceLocation ENTITIES_SHEEP_BROWN = register("entities/sheep/brown");
/*  68 */   public static final ResourceLocation ENTITIES_SHEEP_GREEN = register("entities/sheep/green");
/*  69 */   public static final ResourceLocation ENTITIES_SHEEP_RED = register("entities/sheep/red");
/*  70 */   public static final ResourceLocation ENTITIES_SHEEP_BLACK = register("entities/sheep/black");
/*  71 */   public static final ResourceLocation ENTITIES_BAT = register("entities/bat");
/*  72 */   public static final ResourceLocation ENTITIES_SLIME = register("entities/slime");
/*  73 */   public static final ResourceLocation ENTITIES_MAGMA_CUBE = register("entities/magma_cube");
/*  74 */   public static final ResourceLocation ENTITIES_GHAST = register("entities/ghast");
/*  75 */   public static final ResourceLocation ENTITIES_SQUID = register("entities/squid");
/*  76 */   public static final ResourceLocation ENTITIES_ENDERMITE = register("entities/endermite");
/*  77 */   public static final ResourceLocation ENTITIES_ZOMBIE = register("entities/zombie");
/*  78 */   public static final ResourceLocation ENTITIES_ZOMBIE_PIGMAN = register("entities/zombie_pigman");
/*  79 */   public static final ResourceLocation ENTITIES_SKELETON = register("entities/skeleton");
/*  80 */   public static final ResourceLocation ENTITIES_WITHER_SKELETON = register("entities/wither_skeleton");
/*  81 */   public static final ResourceLocation ENTITIES_STRAY = register("entities/stray");
/*  82 */   public static final ResourceLocation field_191182_ar = register("entities/husk");
/*  83 */   public static final ResourceLocation field_191183_as = register("entities/zombie_villager");
/*  84 */   public static final ResourceLocation field_191184_at = register("entities/villager");
/*  85 */   public static final ResourceLocation field_191185_au = register("entities/evocation_illager");
/*  86 */   public static final ResourceLocation field_191186_av = register("entities/vindication_illager");
/*  87 */   public static final ResourceLocation field_191187_aw = register("entities/llama");
/*  88 */   public static final ResourceLocation field_192561_ax = register("entities/parrot");
/*  89 */   public static final ResourceLocation field_191188_ax = register("entities/vex");
/*  90 */   public static final ResourceLocation field_191189_ay = register("entities/ender_dragon");
/*  91 */   public static final ResourceLocation GAMEPLAY_FISHING = register("gameplay/fishing");
/*  92 */   public static final ResourceLocation GAMEPLAY_FISHING_JUNK = register("gameplay/fishing/junk");
/*  93 */   public static final ResourceLocation GAMEPLAY_FISHING_TREASURE = register("gameplay/fishing/treasure");
/*  94 */   public static final ResourceLocation GAMEPLAY_FISHING_FISH = register("gameplay/fishing/fish");
/*     */ 
/*     */   
/*     */   private static ResourceLocation register(String id) {
/*  98 */     return register(new ResourceLocation("minecraft", id));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation register(ResourceLocation id) {
/* 103 */     if (LOOT_TABLES.add(id))
/*     */     {
/* 105 */       return id;
/*     */     }
/*     */ 
/*     */     
/* 109 */     throw new IllegalArgumentException(id + " is already a registered built-in loot table");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<ResourceLocation> getAll() {
/* 115 */     return READ_ONLY_LOOT_TABLES;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_193579_b() {
/* 120 */     LootTableManager loottablemanager = new LootTableManager(null);
/*     */     
/* 122 */     for (ResourceLocation resourcelocation : READ_ONLY_LOOT_TABLES) {
/*     */       
/* 124 */       if (loottablemanager.getLootTableFromLocation(resourcelocation) == LootTable.EMPTY_LOOT_TABLE)
/*     */       {
/* 126 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootTableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
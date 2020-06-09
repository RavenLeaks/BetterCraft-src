/*     */ package net.minecraft.init;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmorStand;
/*     */ import net.minecraft.item.ItemBow;
/*     */ import net.minecraft.item.ItemEmptyMap;
/*     */ import net.minecraft.item.ItemFishingRod;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemShears;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Items
/*     */ {
/*     */   private static Item getRegisteredItem(String name) {
/* 229 */     Item item = (Item)Item.REGISTRY.getObject(new ResourceLocation(name));
/*     */     
/* 231 */     if (item == null)
/*     */     {
/* 233 */       throw new IllegalStateException("Invalid Item requested: " + name);
/*     */     }
/*     */ 
/*     */     
/* 237 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 243 */     if (!Bootstrap.isRegistered())
/*     */     {
/* 245 */       throw new RuntimeException("Accessed Items before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/* 249 */   public static final Item field_190931_a = getRegisteredItem("air");
/* 250 */   public static final Item IRON_SHOVEL = getRegisteredItem("iron_shovel");
/* 251 */   public static final Item IRON_PICKAXE = getRegisteredItem("iron_pickaxe");
/* 252 */   public static final Item IRON_AXE = getRegisteredItem("iron_axe");
/* 253 */   public static final Item FLINT_AND_STEEL = getRegisteredItem("flint_and_steel");
/* 254 */   public static final Item APPLE = getRegisteredItem("apple");
/* 255 */   public static final ItemBow BOW = (ItemBow)getRegisteredItem("bow");
/* 256 */   public static final Item ARROW = getRegisteredItem("arrow");
/* 257 */   public static final Item SPECTRAL_ARROW = getRegisteredItem("spectral_arrow");
/* 258 */   public static final Item TIPPED_ARROW = getRegisteredItem("tipped_arrow");
/* 259 */   public static final Item COAL = getRegisteredItem("coal");
/* 260 */   public static final Item DIAMOND = getRegisteredItem("diamond");
/* 261 */   public static final Item IRON_INGOT = getRegisteredItem("iron_ingot");
/* 262 */   public static final Item GOLD_INGOT = getRegisteredItem("gold_ingot");
/* 263 */   public static final Item IRON_SWORD = getRegisteredItem("iron_sword");
/* 264 */   public static final Item WOODEN_SWORD = getRegisteredItem("wooden_sword");
/* 265 */   public static final Item WOODEN_SHOVEL = getRegisteredItem("wooden_shovel");
/* 266 */   public static final Item WOODEN_PICKAXE = getRegisteredItem("wooden_pickaxe");
/* 267 */   public static final Item WOODEN_AXE = getRegisteredItem("wooden_axe");
/* 268 */   public static final Item STONE_SWORD = getRegisteredItem("stone_sword");
/* 269 */   public static final Item STONE_SHOVEL = getRegisteredItem("stone_shovel");
/* 270 */   public static final Item STONE_PICKAXE = getRegisteredItem("stone_pickaxe");
/* 271 */   public static final Item STONE_AXE = getRegisteredItem("stone_axe");
/* 272 */   public static final Item DIAMOND_SWORD = getRegisteredItem("diamond_sword");
/* 273 */   public static final Item DIAMOND_SHOVEL = getRegisteredItem("diamond_shovel");
/* 274 */   public static final Item DIAMOND_PICKAXE = getRegisteredItem("diamond_pickaxe");
/* 275 */   public static final Item DIAMOND_AXE = getRegisteredItem("diamond_axe");
/* 276 */   public static final Item STICK = getRegisteredItem("stick");
/* 277 */   public static final Item BOWL = getRegisteredItem("bowl");
/* 278 */   public static final Item MUSHROOM_STEW = getRegisteredItem("mushroom_stew");
/* 279 */   public static final Item GOLDEN_SWORD = getRegisteredItem("golden_sword");
/* 280 */   public static final Item GOLDEN_SHOVEL = getRegisteredItem("golden_shovel");
/* 281 */   public static final Item GOLDEN_PICKAXE = getRegisteredItem("golden_pickaxe");
/* 282 */   public static final Item GOLDEN_AXE = getRegisteredItem("golden_axe");
/* 283 */   public static final Item STRING = getRegisteredItem("string");
/* 284 */   public static final Item FEATHER = getRegisteredItem("feather");
/* 285 */   public static final Item GUNPOWDER = getRegisteredItem("gunpowder");
/* 286 */   public static final Item WOODEN_HOE = getRegisteredItem("wooden_hoe");
/* 287 */   public static final Item STONE_HOE = getRegisteredItem("stone_hoe");
/* 288 */   public static final Item IRON_HOE = getRegisteredItem("iron_hoe");
/* 289 */   public static final Item DIAMOND_HOE = getRegisteredItem("diamond_hoe");
/* 290 */   public static final Item GOLDEN_HOE = getRegisteredItem("golden_hoe");
/* 291 */   public static final Item WHEAT_SEEDS = getRegisteredItem("wheat_seeds");
/* 292 */   public static final Item WHEAT = getRegisteredItem("wheat");
/* 293 */   public static final Item BREAD = getRegisteredItem("bread");
/* 294 */   public static final ItemArmor LEATHER_HELMET = (ItemArmor)getRegisteredItem("leather_helmet");
/* 295 */   public static final ItemArmor LEATHER_CHESTPLATE = (ItemArmor)getRegisteredItem("leather_chestplate");
/* 296 */   public static final ItemArmor LEATHER_LEGGINGS = (ItemArmor)getRegisteredItem("leather_leggings");
/* 297 */   public static final ItemArmor LEATHER_BOOTS = (ItemArmor)getRegisteredItem("leather_boots");
/* 298 */   public static final ItemArmor CHAINMAIL_HELMET = (ItemArmor)getRegisteredItem("chainmail_helmet");
/* 299 */   public static final ItemArmor CHAINMAIL_CHESTPLATE = (ItemArmor)getRegisteredItem("chainmail_chestplate");
/* 300 */   public static final ItemArmor CHAINMAIL_LEGGINGS = (ItemArmor)getRegisteredItem("chainmail_leggings");
/* 301 */   public static final ItemArmor CHAINMAIL_BOOTS = (ItemArmor)getRegisteredItem("chainmail_boots");
/* 302 */   public static final ItemArmor IRON_HELMET = (ItemArmor)getRegisteredItem("iron_helmet");
/* 303 */   public static final ItemArmor IRON_CHESTPLATE = (ItemArmor)getRegisteredItem("iron_chestplate");
/* 304 */   public static final ItemArmor IRON_LEGGINGS = (ItemArmor)getRegisteredItem("iron_leggings");
/* 305 */   public static final ItemArmor IRON_BOOTS = (ItemArmor)getRegisteredItem("iron_boots");
/* 306 */   public static final ItemArmor DIAMOND_HELMET = (ItemArmor)getRegisteredItem("diamond_helmet");
/* 307 */   public static final ItemArmor DIAMOND_CHESTPLATE = (ItemArmor)getRegisteredItem("diamond_chestplate");
/* 308 */   public static final ItemArmor DIAMOND_LEGGINGS = (ItemArmor)getRegisteredItem("diamond_leggings");
/* 309 */   public static final ItemArmor DIAMOND_BOOTS = (ItemArmor)getRegisteredItem("diamond_boots");
/* 310 */   public static final ItemArmor GOLDEN_HELMET = (ItemArmor)getRegisteredItem("golden_helmet");
/* 311 */   public static final ItemArmor GOLDEN_CHESTPLATE = (ItemArmor)getRegisteredItem("golden_chestplate");
/* 312 */   public static final ItemArmor GOLDEN_LEGGINGS = (ItemArmor)getRegisteredItem("golden_leggings");
/* 313 */   public static final ItemArmor GOLDEN_BOOTS = (ItemArmor)getRegisteredItem("golden_boots");
/* 314 */   public static final Item FLINT = getRegisteredItem("flint");
/* 315 */   public static final Item PORKCHOP = getRegisteredItem("porkchop");
/* 316 */   public static final Item COOKED_PORKCHOP = getRegisteredItem("cooked_porkchop");
/* 317 */   public static final Item PAINTING = getRegisteredItem("painting");
/* 318 */   public static final Item GOLDEN_APPLE = getRegisteredItem("golden_apple");
/* 319 */   public static final Item SIGN = getRegisteredItem("sign");
/* 320 */   public static final Item OAK_DOOR = getRegisteredItem("wooden_door");
/* 321 */   public static final Item SPRUCE_DOOR = getRegisteredItem("spruce_door");
/* 322 */   public static final Item BIRCH_DOOR = getRegisteredItem("birch_door");
/* 323 */   public static final Item JUNGLE_DOOR = getRegisteredItem("jungle_door");
/* 324 */   public static final Item ACACIA_DOOR = getRegisteredItem("acacia_door");
/* 325 */   public static final Item DARK_OAK_DOOR = getRegisteredItem("dark_oak_door");
/* 326 */   public static final Item BUCKET = getRegisteredItem("bucket");
/* 327 */   public static final Item WATER_BUCKET = getRegisteredItem("water_bucket");
/* 328 */   public static final Item LAVA_BUCKET = getRegisteredItem("lava_bucket");
/* 329 */   public static final Item MINECART = getRegisteredItem("minecart");
/* 330 */   public static final Item SADDLE = getRegisteredItem("saddle");
/* 331 */   public static final Item IRON_DOOR = getRegisteredItem("iron_door");
/* 332 */   public static final Item REDSTONE = getRegisteredItem("redstone");
/* 333 */   public static final Item SNOWBALL = getRegisteredItem("snowball");
/* 334 */   public static final Item BOAT = getRegisteredItem("boat");
/* 335 */   public static final Item SPRUCE_BOAT = getRegisteredItem("spruce_boat");
/* 336 */   public static final Item BIRCH_BOAT = getRegisteredItem("birch_boat");
/* 337 */   public static final Item JUNGLE_BOAT = getRegisteredItem("jungle_boat");
/* 338 */   public static final Item ACACIA_BOAT = getRegisteredItem("acacia_boat");
/* 339 */   public static final Item DARK_OAK_BOAT = getRegisteredItem("dark_oak_boat");
/* 340 */   public static final Item LEATHER = getRegisteredItem("leather");
/* 341 */   public static final Item MILK_BUCKET = getRegisteredItem("milk_bucket");
/* 342 */   public static final Item BRICK = getRegisteredItem("brick");
/* 343 */   public static final Item CLAY_BALL = getRegisteredItem("clay_ball");
/* 344 */   public static final Item REEDS = getRegisteredItem("reeds");
/* 345 */   public static final Item PAPER = getRegisteredItem("paper");
/* 346 */   public static final Item BOOK = getRegisteredItem("book");
/* 347 */   public static final Item SLIME_BALL = getRegisteredItem("slime_ball");
/* 348 */   public static final Item CHEST_MINECART = getRegisteredItem("chest_minecart");
/* 349 */   public static final Item FURNACE_MINECART = getRegisteredItem("furnace_minecart");
/* 350 */   public static final Item EGG = getRegisteredItem("egg");
/* 351 */   public static final Item COMPASS = getRegisteredItem("compass");
/* 352 */   public static final ItemFishingRod FISHING_ROD = (ItemFishingRod)getRegisteredItem("fishing_rod");
/* 353 */   public static final Item CLOCK = getRegisteredItem("clock");
/* 354 */   public static final Item GLOWSTONE_DUST = getRegisteredItem("glowstone_dust");
/* 355 */   public static final Item FISH = getRegisteredItem("fish");
/* 356 */   public static final Item COOKED_FISH = getRegisteredItem("cooked_fish");
/* 357 */   public static final Item DYE = getRegisteredItem("dye");
/* 358 */   public static final Item BONE = getRegisteredItem("bone");
/* 359 */   public static final Item SUGAR = getRegisteredItem("sugar");
/* 360 */   public static final Item CAKE = getRegisteredItem("cake");
/* 361 */   public static final Item BED = getRegisteredItem("bed");
/* 362 */   public static final Item REPEATER = getRegisteredItem("repeater");
/* 363 */   public static final Item COOKIE = getRegisteredItem("cookie");
/* 364 */   public static final ItemMap FILLED_MAP = (ItemMap)getRegisteredItem("filled_map");
/* 365 */   public static final ItemShears SHEARS = (ItemShears)getRegisteredItem("shears");
/* 366 */   public static final Item MELON = getRegisteredItem("melon");
/* 367 */   public static final Item PUMPKIN_SEEDS = getRegisteredItem("pumpkin_seeds");
/* 368 */   public static final Item MELON_SEEDS = getRegisteredItem("melon_seeds");
/* 369 */   public static final Item BEEF = getRegisteredItem("beef");
/* 370 */   public static final Item COOKED_BEEF = getRegisteredItem("cooked_beef");
/* 371 */   public static final Item CHICKEN = getRegisteredItem("chicken");
/* 372 */   public static final Item COOKED_CHICKEN = getRegisteredItem("cooked_chicken");
/* 373 */   public static final Item MUTTON = getRegisteredItem("mutton");
/* 374 */   public static final Item COOKED_MUTTON = getRegisteredItem("cooked_mutton");
/* 375 */   public static final Item RABBIT = getRegisteredItem("rabbit");
/* 376 */   public static final Item COOKED_RABBIT = getRegisteredItem("cooked_rabbit");
/* 377 */   public static final Item RABBIT_STEW = getRegisteredItem("rabbit_stew");
/* 378 */   public static final Item RABBIT_FOOT = getRegisteredItem("rabbit_foot");
/* 379 */   public static final Item RABBIT_HIDE = getRegisteredItem("rabbit_hide");
/* 380 */   public static final Item ROTTEN_FLESH = getRegisteredItem("rotten_flesh");
/* 381 */   public static final Item ENDER_PEARL = getRegisteredItem("ender_pearl");
/* 382 */   public static final Item BLAZE_ROD = getRegisteredItem("blaze_rod");
/* 383 */   public static final Item GHAST_TEAR = getRegisteredItem("ghast_tear");
/* 384 */   public static final Item GOLD_NUGGET = getRegisteredItem("gold_nugget");
/* 385 */   public static final Item NETHER_WART = getRegisteredItem("nether_wart");
/* 386 */   public static final ItemPotion POTIONITEM = (ItemPotion)getRegisteredItem("potion");
/* 387 */   public static final ItemPotion SPLASH_POTION = (ItemPotion)getRegisteredItem("splash_potion");
/* 388 */   public static final ItemPotion LINGERING_POTION = (ItemPotion)getRegisteredItem("lingering_potion");
/* 389 */   public static final Item GLASS_BOTTLE = getRegisteredItem("glass_bottle");
/* 390 */   public static final Item DRAGON_BREATH = getRegisteredItem("dragon_breath");
/* 391 */   public static final Item SPIDER_EYE = getRegisteredItem("spider_eye");
/* 392 */   public static final Item FERMENTED_SPIDER_EYE = getRegisteredItem("fermented_spider_eye");
/* 393 */   public static final Item BLAZE_POWDER = getRegisteredItem("blaze_powder");
/* 394 */   public static final Item MAGMA_CREAM = getRegisteredItem("magma_cream");
/* 395 */   public static final Item BREWING_STAND = getRegisteredItem("brewing_stand");
/* 396 */   public static final Item CAULDRON = getRegisteredItem("cauldron");
/* 397 */   public static final Item ENDER_EYE = getRegisteredItem("ender_eye");
/* 398 */   public static final Item SPECKLED_MELON = getRegisteredItem("speckled_melon");
/* 399 */   public static final Item SPAWN_EGG = getRegisteredItem("spawn_egg");
/* 400 */   public static final Item EXPERIENCE_BOTTLE = getRegisteredItem("experience_bottle");
/* 401 */   public static final Item FIRE_CHARGE = getRegisteredItem("fire_charge");
/* 402 */   public static final Item WRITABLE_BOOK = getRegisteredItem("writable_book");
/* 403 */   public static final Item WRITTEN_BOOK = getRegisteredItem("written_book");
/* 404 */   public static final Item EMERALD = getRegisteredItem("emerald");
/* 405 */   public static final Item ITEM_FRAME = getRegisteredItem("item_frame");
/* 406 */   public static final Item FLOWER_POT = getRegisteredItem("flower_pot");
/* 407 */   public static final Item CARROT = getRegisteredItem("carrot");
/* 408 */   public static final Item POTATO = getRegisteredItem("potato");
/* 409 */   public static final Item BAKED_POTATO = getRegisteredItem("baked_potato");
/* 410 */   public static final Item POISONOUS_POTATO = getRegisteredItem("poisonous_potato");
/* 411 */   public static final ItemEmptyMap MAP = (ItemEmptyMap)getRegisteredItem("map");
/* 412 */   public static final Item GOLDEN_CARROT = getRegisteredItem("golden_carrot");
/* 413 */   public static final Item SKULL = getRegisteredItem("skull");
/* 414 */   public static final Item CARROT_ON_A_STICK = getRegisteredItem("carrot_on_a_stick");
/* 415 */   public static final Item NETHER_STAR = getRegisteredItem("nether_star");
/* 416 */   public static final Item PUMPKIN_PIE = getRegisteredItem("pumpkin_pie");
/* 417 */   public static final Item FIREWORKS = getRegisteredItem("fireworks");
/* 418 */   public static final Item FIREWORK_CHARGE = getRegisteredItem("firework_charge");
/* 419 */   public static final Item ENCHANTED_BOOK = getRegisteredItem("enchanted_book");
/* 420 */   public static final Item COMPARATOR = getRegisteredItem("comparator");
/* 421 */   public static final Item NETHERBRICK = getRegisteredItem("netherbrick");
/* 422 */   public static final Item QUARTZ = getRegisteredItem("quartz");
/* 423 */   public static final Item TNT_MINECART = getRegisteredItem("tnt_minecart");
/* 424 */   public static final Item HOPPER_MINECART = getRegisteredItem("hopper_minecart");
/* 425 */   public static final ItemArmorStand ARMOR_STAND = (ItemArmorStand)getRegisteredItem("armor_stand");
/* 426 */   public static final Item IRON_HORSE_ARMOR = getRegisteredItem("iron_horse_armor");
/* 427 */   public static final Item GOLDEN_HORSE_ARMOR = getRegisteredItem("golden_horse_armor");
/* 428 */   public static final Item DIAMOND_HORSE_ARMOR = getRegisteredItem("diamond_horse_armor");
/* 429 */   public static final Item LEAD = getRegisteredItem("lead");
/* 430 */   public static final Item NAME_TAG = getRegisteredItem("name_tag");
/* 431 */   public static final Item COMMAND_BLOCK_MINECART = getRegisteredItem("command_block_minecart");
/* 432 */   public static final Item RECORD_13 = getRegisteredItem("record_13");
/* 433 */   public static final Item RECORD_CAT = getRegisteredItem("record_cat");
/* 434 */   public static final Item RECORD_BLOCKS = getRegisteredItem("record_blocks");
/* 435 */   public static final Item RECORD_CHIRP = getRegisteredItem("record_chirp");
/* 436 */   public static final Item RECORD_FAR = getRegisteredItem("record_far");
/* 437 */   public static final Item RECORD_MALL = getRegisteredItem("record_mall");
/* 438 */   public static final Item RECORD_MELLOHI = getRegisteredItem("record_mellohi");
/* 439 */   public static final Item RECORD_STAL = getRegisteredItem("record_stal");
/* 440 */   public static final Item RECORD_STRAD = getRegisteredItem("record_strad");
/* 441 */   public static final Item RECORD_WARD = getRegisteredItem("record_ward");
/* 442 */   public static final Item RECORD_11 = getRegisteredItem("record_11");
/* 443 */   public static final Item RECORD_WAIT = getRegisteredItem("record_wait");
/* 444 */   public static final Item PRISMARINE_SHARD = getRegisteredItem("prismarine_shard");
/* 445 */   public static final Item PRISMARINE_CRYSTALS = getRegisteredItem("prismarine_crystals");
/* 446 */   public static final Item BANNER = getRegisteredItem("banner");
/* 447 */   public static final Item END_CRYSTAL = getRegisteredItem("end_crystal");
/* 448 */   public static final Item SHIELD = getRegisteredItem("shield");
/* 449 */   public static final Item ELYTRA = getRegisteredItem("elytra");
/* 450 */   public static final Item CHORUS_FRUIT = getRegisteredItem("chorus_fruit");
/* 451 */   public static final Item CHORUS_FRUIT_POPPED = getRegisteredItem("chorus_fruit_popped");
/* 452 */   public static final Item BEETROOT_SEEDS = getRegisteredItem("beetroot_seeds");
/* 453 */   public static final Item BEETROOT = getRegisteredItem("beetroot");
/* 454 */   public static final Item BEETROOT_SOUP = getRegisteredItem("beetroot_soup");
/* 455 */   public static final Item field_190929_cY = getRegisteredItem("totem_of_undying");
/* 456 */   public static final Item field_190930_cZ = getRegisteredItem("shulker_shell");
/* 457 */   public static final Item field_191525_da = getRegisteredItem("iron_nugget");
/* 458 */   public static final Item field_192397_db = getRegisteredItem("knowledge_book");
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\Items.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
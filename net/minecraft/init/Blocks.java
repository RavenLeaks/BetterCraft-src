/*     */ package net.minecraft.init;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBeacon;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.BlockCactus;
/*     */ import net.minecraft.block.BlockCauldron;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockDaylightDetector;
/*     */ import net.minecraft.block.BlockDeadBush;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockDynamicLiquid;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockGrass;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockMycelium;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.BlockPistonMoving;
/*     */ import net.minecraft.block.BlockPortal;
/*     */ import net.minecraft.block.BlockRedstoneComparator;
/*     */ import net.minecraft.block.BlockRedstoneRepeater;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.BlockReed;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.BlockStaticLiquid;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.BlockTripWireHook;
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
/*     */ public class Blocks
/*     */ {
/*     */   @Nullable
/*     */   private static Block getRegisteredBlock(String blockName) {
/* 306 */     Block block = (Block)Block.REGISTRY.getObject(new ResourceLocation(blockName));
/*     */     
/* 308 */     if (!CACHE.add(block))
/*     */     {
/* 310 */       throw new IllegalStateException("Invalid Block requested: " + blockName);
/*     */     }
/*     */ 
/*     */     
/* 314 */     return block;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 320 */     if (!Bootstrap.isRegistered())
/*     */     {
/* 322 */       throw new RuntimeException("Accessed Blocks before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/* 326 */   private static final Set<Block> CACHE = Sets.newHashSet();
/* 327 */   public static final Block AIR = getRegisteredBlock("air");
/* 328 */   public static final Block STONE = getRegisteredBlock("stone");
/* 329 */   public static final BlockGrass GRASS = (BlockGrass)getRegisteredBlock("grass");
/* 330 */   public static final Block DIRT = getRegisteredBlock("dirt");
/* 331 */   public static final Block COBBLESTONE = getRegisteredBlock("cobblestone");
/* 332 */   public static final Block PLANKS = getRegisteredBlock("planks");
/* 333 */   public static final Block SAPLING = getRegisteredBlock("sapling");
/* 334 */   public static final Block BEDROCK = getRegisteredBlock("bedrock");
/* 335 */   public static final BlockDynamicLiquid FLOWING_WATER = (BlockDynamicLiquid)getRegisteredBlock("flowing_water");
/* 336 */   public static final BlockStaticLiquid WATER = (BlockStaticLiquid)getRegisteredBlock("water");
/* 337 */   public static final BlockDynamicLiquid FLOWING_LAVA = (BlockDynamicLiquid)getRegisteredBlock("flowing_lava");
/* 338 */   public static final BlockStaticLiquid LAVA = (BlockStaticLiquid)getRegisteredBlock("lava");
/* 339 */   public static final BlockSand SAND = (BlockSand)getRegisteredBlock("sand");
/* 340 */   public static final Block GRAVEL = getRegisteredBlock("gravel");
/* 341 */   public static final Block GOLD_ORE = getRegisteredBlock("gold_ore");
/* 342 */   public static final Block IRON_ORE = getRegisteredBlock("iron_ore");
/* 343 */   public static final Block COAL_ORE = getRegisteredBlock("coal_ore");
/* 344 */   public static final Block LOG = getRegisteredBlock("log");
/* 345 */   public static final Block LOG2 = getRegisteredBlock("log2");
/* 346 */   public static final BlockLeaves LEAVES = (BlockLeaves)getRegisteredBlock("leaves");
/* 347 */   public static final BlockLeaves LEAVES2 = (BlockLeaves)getRegisteredBlock("leaves2");
/* 348 */   public static final Block SPONGE = getRegisteredBlock("sponge");
/* 349 */   public static final Block GLASS = getRegisteredBlock("glass");
/* 350 */   public static final Block LAPIS_ORE = getRegisteredBlock("lapis_ore");
/* 351 */   public static final Block LAPIS_BLOCK = getRegisteredBlock("lapis_block");
/* 352 */   public static final Block DISPENSER = getRegisteredBlock("dispenser");
/* 353 */   public static final Block SANDSTONE = getRegisteredBlock("sandstone");
/* 354 */   public static final Block NOTEBLOCK = getRegisteredBlock("noteblock");
/* 355 */   public static final Block BED = getRegisteredBlock("bed");
/* 356 */   public static final Block GOLDEN_RAIL = getRegisteredBlock("golden_rail");
/* 357 */   public static final Block DETECTOR_RAIL = getRegisteredBlock("detector_rail");
/* 358 */   public static final BlockPistonBase STICKY_PISTON = (BlockPistonBase)getRegisteredBlock("sticky_piston");
/* 359 */   public static final Block WEB = getRegisteredBlock("web");
/* 360 */   public static final BlockTallGrass TALLGRASS = (BlockTallGrass)getRegisteredBlock("tallgrass");
/* 361 */   public static final BlockDeadBush DEADBUSH = (BlockDeadBush)getRegisteredBlock("deadbush");
/* 362 */   public static final BlockPistonBase PISTON = (BlockPistonBase)getRegisteredBlock("piston");
/* 363 */   public static final BlockPistonExtension PISTON_HEAD = (BlockPistonExtension)getRegisteredBlock("piston_head");
/* 364 */   public static final Block WOOL = getRegisteredBlock("wool");
/* 365 */   public static final BlockPistonMoving PISTON_EXTENSION = (BlockPistonMoving)getRegisteredBlock("piston_extension");
/* 366 */   public static final BlockFlower YELLOW_FLOWER = (BlockFlower)getRegisteredBlock("yellow_flower");
/* 367 */   public static final BlockFlower RED_FLOWER = (BlockFlower)getRegisteredBlock("red_flower");
/* 368 */   public static final BlockBush BROWN_MUSHROOM = (BlockBush)getRegisteredBlock("brown_mushroom");
/* 369 */   public static final BlockBush RED_MUSHROOM = (BlockBush)getRegisteredBlock("red_mushroom");
/* 370 */   public static final Block GOLD_BLOCK = getRegisteredBlock("gold_block");
/* 371 */   public static final Block IRON_BLOCK = getRegisteredBlock("iron_block");
/* 372 */   public static final BlockSlab DOUBLE_STONE_SLAB = (BlockSlab)getRegisteredBlock("double_stone_slab");
/* 373 */   public static final BlockSlab STONE_SLAB = (BlockSlab)getRegisteredBlock("stone_slab");
/* 374 */   public static final Block BRICK_BLOCK = getRegisteredBlock("brick_block");
/* 375 */   public static final Block TNT = getRegisteredBlock("tnt");
/* 376 */   public static final Block BOOKSHELF = getRegisteredBlock("bookshelf");
/* 377 */   public static final Block MOSSY_COBBLESTONE = getRegisteredBlock("mossy_cobblestone");
/* 378 */   public static final Block OBSIDIAN = getRegisteredBlock("obsidian");
/* 379 */   public static final Block TORCH = getRegisteredBlock("torch");
/* 380 */   public static final BlockFire FIRE = (BlockFire)getRegisteredBlock("fire");
/* 381 */   public static final Block MOB_SPAWNER = getRegisteredBlock("mob_spawner");
/* 382 */   public static final Block OAK_STAIRS = getRegisteredBlock("oak_stairs");
/* 383 */   public static final BlockChest CHEST = (BlockChest)getRegisteredBlock("chest");
/* 384 */   public static final BlockRedstoneWire REDSTONE_WIRE = (BlockRedstoneWire)getRegisteredBlock("redstone_wire");
/* 385 */   public static final Block DIAMOND_ORE = getRegisteredBlock("diamond_ore");
/* 386 */   public static final Block DIAMOND_BLOCK = getRegisteredBlock("diamond_block");
/* 387 */   public static final Block CRAFTING_TABLE = getRegisteredBlock("crafting_table");
/* 388 */   public static final Block WHEAT = getRegisteredBlock("wheat");
/* 389 */   public static final Block FARMLAND = getRegisteredBlock("farmland");
/* 390 */   public static final Block FURNACE = getRegisteredBlock("furnace");
/* 391 */   public static final Block LIT_FURNACE = getRegisteredBlock("lit_furnace");
/* 392 */   public static final Block STANDING_SIGN = getRegisteredBlock("standing_sign");
/* 393 */   public static final BlockDoor OAK_DOOR = (BlockDoor)getRegisteredBlock("wooden_door");
/* 394 */   public static final BlockDoor SPRUCE_DOOR = (BlockDoor)getRegisteredBlock("spruce_door");
/* 395 */   public static final BlockDoor BIRCH_DOOR = (BlockDoor)getRegisteredBlock("birch_door");
/* 396 */   public static final BlockDoor JUNGLE_DOOR = (BlockDoor)getRegisteredBlock("jungle_door");
/* 397 */   public static final BlockDoor ACACIA_DOOR = (BlockDoor)getRegisteredBlock("acacia_door");
/* 398 */   public static final BlockDoor DARK_OAK_DOOR = (BlockDoor)getRegisteredBlock("dark_oak_door");
/* 399 */   public static final Block LADDER = getRegisteredBlock("ladder");
/* 400 */   public static final Block RAIL = getRegisteredBlock("rail");
/* 401 */   public static final Block STONE_STAIRS = getRegisteredBlock("stone_stairs");
/* 402 */   public static final Block WALL_SIGN = getRegisteredBlock("wall_sign");
/* 403 */   public static final Block LEVER = getRegisteredBlock("lever");
/* 404 */   public static final Block STONE_PRESSURE_PLATE = getRegisteredBlock("stone_pressure_plate");
/* 405 */   public static final BlockDoor IRON_DOOR = (BlockDoor)getRegisteredBlock("iron_door");
/* 406 */   public static final Block WOODEN_PRESSURE_PLATE = getRegisteredBlock("wooden_pressure_plate");
/* 407 */   public static final Block REDSTONE_ORE = getRegisteredBlock("redstone_ore");
/* 408 */   public static final Block LIT_REDSTONE_ORE = getRegisteredBlock("lit_redstone_ore");
/* 409 */   public static final Block UNLIT_REDSTONE_TORCH = getRegisteredBlock("unlit_redstone_torch");
/* 410 */   public static final Block REDSTONE_TORCH = getRegisteredBlock("redstone_torch");
/* 411 */   public static final Block STONE_BUTTON = getRegisteredBlock("stone_button");
/* 412 */   public static final Block SNOW_LAYER = getRegisteredBlock("snow_layer");
/* 413 */   public static final Block ICE = getRegisteredBlock("ice");
/* 414 */   public static final Block SNOW = getRegisteredBlock("snow");
/* 415 */   public static final BlockCactus CACTUS = (BlockCactus)getRegisteredBlock("cactus");
/* 416 */   public static final Block CLAY = getRegisteredBlock("clay");
/* 417 */   public static final BlockReed REEDS = (BlockReed)getRegisteredBlock("reeds");
/* 418 */   public static final Block JUKEBOX = getRegisteredBlock("jukebox");
/* 419 */   public static final Block OAK_FENCE = getRegisteredBlock("fence");
/* 420 */   public static final Block SPRUCE_FENCE = getRegisteredBlock("spruce_fence");
/* 421 */   public static final Block BIRCH_FENCE = getRegisteredBlock("birch_fence");
/* 422 */   public static final Block JUNGLE_FENCE = getRegisteredBlock("jungle_fence");
/* 423 */   public static final Block DARK_OAK_FENCE = getRegisteredBlock("dark_oak_fence");
/* 424 */   public static final Block ACACIA_FENCE = getRegisteredBlock("acacia_fence");
/* 425 */   public static final Block PUMPKIN = getRegisteredBlock("pumpkin");
/* 426 */   public static final Block NETHERRACK = getRegisteredBlock("netherrack");
/* 427 */   public static final Block SOUL_SAND = getRegisteredBlock("soul_sand");
/* 428 */   public static final Block GLOWSTONE = getRegisteredBlock("glowstone");
/* 429 */   public static final BlockPortal PORTAL = (BlockPortal)getRegisteredBlock("portal");
/* 430 */   public static final Block LIT_PUMPKIN = getRegisteredBlock("lit_pumpkin");
/* 431 */   public static final Block CAKE = getRegisteredBlock("cake");
/* 432 */   public static final BlockRedstoneRepeater UNPOWERED_REPEATER = (BlockRedstoneRepeater)getRegisteredBlock("unpowered_repeater");
/* 433 */   public static final BlockRedstoneRepeater POWERED_REPEATER = (BlockRedstoneRepeater)getRegisteredBlock("powered_repeater");
/* 434 */   public static final Block TRAPDOOR = getRegisteredBlock("trapdoor");
/* 435 */   public static final Block MONSTER_EGG = getRegisteredBlock("monster_egg");
/* 436 */   public static final Block STONEBRICK = getRegisteredBlock("stonebrick");
/* 437 */   public static final Block BROWN_MUSHROOM_BLOCK = getRegisteredBlock("brown_mushroom_block");
/* 438 */   public static final Block RED_MUSHROOM_BLOCK = getRegisteredBlock("red_mushroom_block");
/* 439 */   public static final Block IRON_BARS = getRegisteredBlock("iron_bars");
/* 440 */   public static final Block GLASS_PANE = getRegisteredBlock("glass_pane");
/* 441 */   public static final Block MELON_BLOCK = getRegisteredBlock("melon_block");
/* 442 */   public static final Block PUMPKIN_STEM = getRegisteredBlock("pumpkin_stem");
/* 443 */   public static final Block MELON_STEM = getRegisteredBlock("melon_stem");
/* 444 */   public static final Block VINE = getRegisteredBlock("vine");
/* 445 */   public static final Block OAK_FENCE_GATE = getRegisteredBlock("fence_gate");
/* 446 */   public static final Block SPRUCE_FENCE_GATE = getRegisteredBlock("spruce_fence_gate");
/* 447 */   public static final Block BIRCH_FENCE_GATE = getRegisteredBlock("birch_fence_gate");
/* 448 */   public static final Block JUNGLE_FENCE_GATE = getRegisteredBlock("jungle_fence_gate");
/* 449 */   public static final Block DARK_OAK_FENCE_GATE = getRegisteredBlock("dark_oak_fence_gate");
/* 450 */   public static final Block ACACIA_FENCE_GATE = getRegisteredBlock("acacia_fence_gate");
/* 451 */   public static final Block BRICK_STAIRS = getRegisteredBlock("brick_stairs");
/* 452 */   public static final Block STONE_BRICK_STAIRS = getRegisteredBlock("stone_brick_stairs");
/* 453 */   public static final BlockMycelium MYCELIUM = (BlockMycelium)getRegisteredBlock("mycelium");
/* 454 */   public static final Block WATERLILY = getRegisteredBlock("waterlily");
/* 455 */   public static final Block NETHER_BRICK = getRegisteredBlock("nether_brick");
/* 456 */   public static final Block NETHER_BRICK_FENCE = getRegisteredBlock("nether_brick_fence");
/* 457 */   public static final Block NETHER_BRICK_STAIRS = getRegisteredBlock("nether_brick_stairs");
/* 458 */   public static final Block NETHER_WART = getRegisteredBlock("nether_wart");
/* 459 */   public static final Block ENCHANTING_TABLE = getRegisteredBlock("enchanting_table");
/* 460 */   public static final Block BREWING_STAND = getRegisteredBlock("brewing_stand");
/* 461 */   public static final BlockCauldron CAULDRON = (BlockCauldron)getRegisteredBlock("cauldron");
/* 462 */   public static final Block END_PORTAL = getRegisteredBlock("end_portal");
/* 463 */   public static final Block END_PORTAL_FRAME = getRegisteredBlock("end_portal_frame");
/* 464 */   public static final Block END_STONE = getRegisteredBlock("end_stone");
/* 465 */   public static final Block DRAGON_EGG = getRegisteredBlock("dragon_egg");
/* 466 */   public static final Block REDSTONE_LAMP = getRegisteredBlock("redstone_lamp");
/* 467 */   public static final Block LIT_REDSTONE_LAMP = getRegisteredBlock("lit_redstone_lamp");
/* 468 */   public static final BlockSlab DOUBLE_WOODEN_SLAB = (BlockSlab)getRegisteredBlock("double_wooden_slab");
/* 469 */   public static final BlockSlab WOODEN_SLAB = (BlockSlab)getRegisteredBlock("wooden_slab");
/* 470 */   public static final Block COCOA = getRegisteredBlock("cocoa");
/* 471 */   public static final Block SANDSTONE_STAIRS = getRegisteredBlock("sandstone_stairs");
/* 472 */   public static final Block EMERALD_ORE = getRegisteredBlock("emerald_ore");
/* 473 */   public static final Block ENDER_CHEST = getRegisteredBlock("ender_chest");
/* 474 */   public static final BlockTripWireHook TRIPWIRE_HOOK = (BlockTripWireHook)getRegisteredBlock("tripwire_hook");
/* 475 */   public static final Block TRIPWIRE = getRegisteredBlock("tripwire");
/* 476 */   public static final Block EMERALD_BLOCK = getRegisteredBlock("emerald_block");
/* 477 */   public static final Block SPRUCE_STAIRS = getRegisteredBlock("spruce_stairs");
/* 478 */   public static final Block BIRCH_STAIRS = getRegisteredBlock("birch_stairs");
/* 479 */   public static final Block JUNGLE_STAIRS = getRegisteredBlock("jungle_stairs");
/* 480 */   public static final Block COMMAND_BLOCK = getRegisteredBlock("command_block");
/* 481 */   public static final BlockBeacon BEACON = (BlockBeacon)getRegisteredBlock("beacon");
/* 482 */   public static final Block COBBLESTONE_WALL = getRegisteredBlock("cobblestone_wall");
/* 483 */   public static final Block FLOWER_POT = getRegisteredBlock("flower_pot");
/* 484 */   public static final Block CARROTS = getRegisteredBlock("carrots");
/* 485 */   public static final Block POTATOES = getRegisteredBlock("potatoes");
/* 486 */   public static final Block WOODEN_BUTTON = getRegisteredBlock("wooden_button");
/* 487 */   public static final BlockSkull SKULL = (BlockSkull)getRegisteredBlock("skull");
/* 488 */   public static final Block ANVIL = getRegisteredBlock("anvil");
/* 489 */   public static final Block TRAPPED_CHEST = getRegisteredBlock("trapped_chest");
/* 490 */   public static final Block LIGHT_WEIGHTED_PRESSURE_PLATE = getRegisteredBlock("light_weighted_pressure_plate");
/* 491 */   public static final Block HEAVY_WEIGHTED_PRESSURE_PLATE = getRegisteredBlock("heavy_weighted_pressure_plate");
/* 492 */   public static final BlockRedstoneComparator UNPOWERED_COMPARATOR = (BlockRedstoneComparator)getRegisteredBlock("unpowered_comparator");
/* 493 */   public static final BlockRedstoneComparator POWERED_COMPARATOR = (BlockRedstoneComparator)getRegisteredBlock("powered_comparator");
/* 494 */   public static final BlockDaylightDetector DAYLIGHT_DETECTOR = (BlockDaylightDetector)getRegisteredBlock("daylight_detector");
/* 495 */   public static final BlockDaylightDetector DAYLIGHT_DETECTOR_INVERTED = (BlockDaylightDetector)getRegisteredBlock("daylight_detector_inverted");
/* 496 */   public static final Block REDSTONE_BLOCK = getRegisteredBlock("redstone_block");
/* 497 */   public static final Block QUARTZ_ORE = getRegisteredBlock("quartz_ore");
/* 498 */   public static final BlockHopper HOPPER = (BlockHopper)getRegisteredBlock("hopper");
/* 499 */   public static final Block QUARTZ_BLOCK = getRegisteredBlock("quartz_block");
/* 500 */   public static final Block QUARTZ_STAIRS = getRegisteredBlock("quartz_stairs");
/* 501 */   public static final Block ACTIVATOR_RAIL = getRegisteredBlock("activator_rail");
/* 502 */   public static final Block DROPPER = getRegisteredBlock("dropper");
/* 503 */   public static final Block STAINED_HARDENED_CLAY = getRegisteredBlock("stained_hardened_clay");
/* 504 */   public static final Block BARRIER = getRegisteredBlock("barrier");
/* 505 */   public static final Block IRON_TRAPDOOR = getRegisteredBlock("iron_trapdoor");
/* 506 */   public static final Block HAY_BLOCK = getRegisteredBlock("hay_block");
/* 507 */   public static final Block CARPET = getRegisteredBlock("carpet");
/* 508 */   public static final Block HARDENED_CLAY = getRegisteredBlock("hardened_clay");
/* 509 */   public static final Block COAL_BLOCK = getRegisteredBlock("coal_block");
/* 510 */   public static final Block PACKED_ICE = getRegisteredBlock("packed_ice");
/* 511 */   public static final Block ACACIA_STAIRS = getRegisteredBlock("acacia_stairs");
/* 512 */   public static final Block DARK_OAK_STAIRS = getRegisteredBlock("dark_oak_stairs");
/* 513 */   public static final Block SLIME_BLOCK = getRegisteredBlock("slime");
/* 514 */   public static final BlockDoublePlant DOUBLE_PLANT = (BlockDoublePlant)getRegisteredBlock("double_plant");
/* 515 */   public static final BlockStainedGlass STAINED_GLASS = (BlockStainedGlass)getRegisteredBlock("stained_glass");
/* 516 */   public static final BlockStainedGlassPane STAINED_GLASS_PANE = (BlockStainedGlassPane)getRegisteredBlock("stained_glass_pane");
/* 517 */   public static final Block PRISMARINE = getRegisteredBlock("prismarine");
/* 518 */   public static final Block SEA_LANTERN = getRegisteredBlock("sea_lantern");
/* 519 */   public static final Block STANDING_BANNER = getRegisteredBlock("standing_banner");
/* 520 */   public static final Block WALL_BANNER = getRegisteredBlock("wall_banner");
/* 521 */   public static final Block RED_SANDSTONE = getRegisteredBlock("red_sandstone");
/* 522 */   public static final Block RED_SANDSTONE_STAIRS = getRegisteredBlock("red_sandstone_stairs");
/* 523 */   public static final BlockSlab DOUBLE_STONE_SLAB2 = (BlockSlab)getRegisteredBlock("double_stone_slab2");
/* 524 */   public static final BlockSlab STONE_SLAB2 = (BlockSlab)getRegisteredBlock("stone_slab2");
/* 525 */   public static final Block END_ROD = getRegisteredBlock("end_rod");
/* 526 */   public static final Block CHORUS_PLANT = getRegisteredBlock("chorus_plant");
/* 527 */   public static final Block CHORUS_FLOWER = getRegisteredBlock("chorus_flower");
/* 528 */   public static final Block PURPUR_BLOCK = getRegisteredBlock("purpur_block");
/* 529 */   public static final Block PURPUR_PILLAR = getRegisteredBlock("purpur_pillar");
/* 530 */   public static final Block PURPUR_STAIRS = getRegisteredBlock("purpur_stairs");
/* 531 */   public static final BlockSlab PURPUR_DOUBLE_SLAB = (BlockSlab)getRegisteredBlock("purpur_double_slab");
/* 532 */   public static final BlockSlab PURPUR_SLAB = (BlockSlab)getRegisteredBlock("purpur_slab");
/* 533 */   public static final Block END_BRICKS = getRegisteredBlock("end_bricks");
/* 534 */   public static final Block BEETROOTS = getRegisteredBlock("beetroots");
/* 535 */   public static final Block GRASS_PATH = getRegisteredBlock("grass_path");
/* 536 */   public static final Block END_GATEWAY = getRegisteredBlock("end_gateway");
/* 537 */   public static final Block REPEATING_COMMAND_BLOCK = getRegisteredBlock("repeating_command_block");
/* 538 */   public static final Block CHAIN_COMMAND_BLOCK = getRegisteredBlock("chain_command_block");
/* 539 */   public static final Block FROSTED_ICE = getRegisteredBlock("frosted_ice");
/* 540 */   public static final Block MAGMA = getRegisteredBlock("magma");
/* 541 */   public static final Block NETHER_WART_BLOCK = getRegisteredBlock("nether_wart_block");
/* 542 */   public static final Block RED_NETHER_BRICK = getRegisteredBlock("red_nether_brick");
/* 543 */   public static final Block BONE_BLOCK = getRegisteredBlock("bone_block");
/* 544 */   public static final Block STRUCTURE_VOID = getRegisteredBlock("structure_void");
/* 545 */   public static final Block field_190976_dk = getRegisteredBlock("observer");
/* 546 */   public static final Block field_190977_dl = getRegisteredBlock("white_shulker_box");
/* 547 */   public static final Block field_190978_dm = getRegisteredBlock("orange_shulker_box");
/* 548 */   public static final Block field_190979_dn = getRegisteredBlock("magenta_shulker_box");
/* 549 */   public static final Block field_190980_do = getRegisteredBlock("light_blue_shulker_box");
/* 550 */   public static final Block field_190981_dp = getRegisteredBlock("yellow_shulker_box");
/* 551 */   public static final Block field_190982_dq = getRegisteredBlock("lime_shulker_box");
/* 552 */   public static final Block field_190983_dr = getRegisteredBlock("pink_shulker_box");
/* 553 */   public static final Block field_190984_ds = getRegisteredBlock("gray_shulker_box");
/* 554 */   public static final Block field_190985_dt = getRegisteredBlock("silver_shulker_box");
/* 555 */   public static final Block field_190986_du = getRegisteredBlock("cyan_shulker_box");
/* 556 */   public static final Block field_190987_dv = getRegisteredBlock("purple_shulker_box");
/* 557 */   public static final Block field_190988_dw = getRegisteredBlock("blue_shulker_box");
/* 558 */   public static final Block field_190989_dx = getRegisteredBlock("brown_shulker_box");
/* 559 */   public static final Block field_190990_dy = getRegisteredBlock("green_shulker_box");
/* 560 */   public static final Block field_190991_dz = getRegisteredBlock("red_shulker_box");
/* 561 */   public static final Block field_190975_dA = getRegisteredBlock("black_shulker_box");
/* 562 */   public static final Block field_192427_dB = getRegisteredBlock("white_glazed_terracotta");
/* 563 */   public static final Block field_192428_dC = getRegisteredBlock("orange_glazed_terracotta");
/* 564 */   public static final Block field_192429_dD = getRegisteredBlock("magenta_glazed_terracotta");
/* 565 */   public static final Block field_192430_dE = getRegisteredBlock("light_blue_glazed_terracotta");
/* 566 */   public static final Block field_192431_dF = getRegisteredBlock("yellow_glazed_terracotta");
/* 567 */   public static final Block field_192432_dG = getRegisteredBlock("lime_glazed_terracotta");
/* 568 */   public static final Block field_192433_dH = getRegisteredBlock("pink_glazed_terracotta");
/* 569 */   public static final Block field_192434_dI = getRegisteredBlock("gray_glazed_terracotta");
/* 570 */   public static final Block field_192435_dJ = getRegisteredBlock("silver_glazed_terracotta");
/* 571 */   public static final Block field_192436_dK = getRegisteredBlock("cyan_glazed_terracotta");
/* 572 */   public static final Block field_192437_dL = getRegisteredBlock("purple_glazed_terracotta");
/* 573 */   public static final Block field_192438_dM = getRegisteredBlock("blue_glazed_terracotta");
/* 574 */   public static final Block field_192439_dN = getRegisteredBlock("brown_glazed_terracotta");
/* 575 */   public static final Block field_192440_dO = getRegisteredBlock("green_glazed_terracotta");
/* 576 */   public static final Block field_192441_dP = getRegisteredBlock("red_glazed_terracotta");
/* 577 */   public static final Block field_192442_dQ = getRegisteredBlock("black_glazed_terracotta");
/* 578 */   public static final Block field_192443_dR = getRegisteredBlock("concrete");
/* 579 */   public static final Block field_192444_dS = getRegisteredBlock("concrete_powder");
/* 580 */   public static final Block STRUCTURE_BLOCK = getRegisteredBlock("structure_block"); static {
/* 581 */     CACHE.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\Blocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
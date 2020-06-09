/*     */ package optifine;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArraySet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.AbstractHorse;
/*     */ import net.minecraft.entity.passive.EntityLlama;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomGuiProperties
/*     */ {
/*  35 */   private String fileName = null;
/*  36 */   private String basePath = null;
/*  37 */   private EnumContainer container = null;
/*  38 */   private Map<ResourceLocation, ResourceLocation> textureLocations = null;
/*  39 */   private NbtTagValue nbtName = null;
/*  40 */   private Biome[] biomes = null;
/*  41 */   private RangeListInt heights = null;
/*  42 */   private Boolean large = null;
/*  43 */   private Boolean trapped = null;
/*  44 */   private Boolean christmas = null;
/*  45 */   private Boolean ender = null;
/*  46 */   private RangeListInt levels = null;
/*  47 */   private VillagerProfession[] professions = null;
/*  48 */   private EnumVariant[] variants = null;
/*  49 */   private EnumDyeColor[] colors = null;
/*  50 */   private static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
/*  51 */   private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
/*  52 */   private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
/*  53 */   private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
/*  54 */   private static final EnumDyeColor[] COLORS_INVALID = new EnumDyeColor[0];
/*  55 */   private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
/*  56 */   private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
/*  57 */   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*  58 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*  59 */   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
/*  60 */   private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
/*  61 */   private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
/*  62 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  63 */   private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
/*  64 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*  65 */   private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
/*  66 */   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  67 */   private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */   
/*     */   public CustomGuiProperties(Properties p_i32_1_, String p_i32_2_) {
/*  71 */     ConnectedParser connectedparser = new ConnectedParser("CustomGuis");
/*  72 */     this.fileName = connectedparser.parseName(p_i32_2_);
/*  73 */     this.basePath = connectedparser.parseBasePath(p_i32_2_);
/*  74 */     this.container = (EnumContainer)connectedparser.parseEnum(p_i32_1_.getProperty("container"), (Enum[])EnumContainer.values(), "container");
/*  75 */     this.textureLocations = parseTextureLocations(p_i32_1_, "texture", this.container, "textures/gui/", this.basePath);
/*  76 */     this.nbtName = parseNbtTagValue("name", p_i32_1_.getProperty("name"));
/*  77 */     this.biomes = connectedparser.parseBiomes(p_i32_1_.getProperty("biomes"));
/*  78 */     this.heights = connectedparser.parseRangeListInt(p_i32_1_.getProperty("heights"));
/*  79 */     this.large = connectedparser.parseBooleanObject(p_i32_1_.getProperty("large"));
/*  80 */     this.trapped = connectedparser.parseBooleanObject(p_i32_1_.getProperty("trapped"));
/*  81 */     this.christmas = connectedparser.parseBooleanObject(p_i32_1_.getProperty("christmas"));
/*  82 */     this.ender = connectedparser.parseBooleanObject(p_i32_1_.getProperty("ender"));
/*  83 */     this.levels = connectedparser.parseRangeListInt(p_i32_1_.getProperty("levels"));
/*  84 */     this.professions = parseProfessions(p_i32_1_.getProperty("professions"));
/*  85 */     EnumVariant[] acustomguiproperties$enumvariant = getContainerVariants(this.container);
/*  86 */     this.variants = (EnumVariant[])connectedparser.parseEnums(p_i32_1_.getProperty("variants"), (Enum[])acustomguiproperties$enumvariant, "variants", (Enum[])VARIANTS_INVALID);
/*  87 */     this.colors = parseEnumDyeColors(p_i32_1_.getProperty("colors"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumVariant[] getContainerVariants(EnumContainer p_getContainerVariants_0_) {
/*  92 */     if (p_getContainerVariants_0_ == EnumContainer.HORSE)
/*     */     {
/*  94 */       return VARIANTS_HORSE;
/*     */     }
/*     */ 
/*     */     
/*  98 */     return (p_getContainerVariants_0_ == EnumContainer.DISPENSER) ? VARIANTS_DISPENSER : new EnumVariant[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EnumDyeColor[] parseEnumDyeColors(String p_parseEnumDyeColors_0_) {
/* 104 */     if (p_parseEnumDyeColors_0_ == null)
/*     */     {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 110 */     p_parseEnumDyeColors_0_ = p_parseEnumDyeColors_0_.toLowerCase();
/* 111 */     String[] astring = Config.tokenize(p_parseEnumDyeColors_0_, " ");
/* 112 */     EnumDyeColor[] aenumdyecolor = new EnumDyeColor[astring.length];
/*     */     
/* 114 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 116 */       String s = astring[i];
/* 117 */       EnumDyeColor enumdyecolor = parseEnumDyeColor(s);
/*     */       
/* 119 */       if (enumdyecolor == null) {
/*     */         
/* 121 */         warn("Invalid color: " + s);
/* 122 */         return COLORS_INVALID;
/*     */       } 
/*     */       
/* 125 */       aenumdyecolor[i] = enumdyecolor;
/*     */     } 
/*     */     
/* 128 */     return aenumdyecolor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EnumDyeColor parseEnumDyeColor(String p_parseEnumDyeColor_0_) {
/* 134 */     if (p_parseEnumDyeColor_0_ == null)
/*     */     {
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 140 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/*     */     
/* 142 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*     */       
/* 144 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/*     */       
/* 146 */       if (enumdyecolor.getName().equals(p_parseEnumDyeColor_0_))
/*     */       {
/* 148 */         return enumdyecolor;
/*     */       }
/*     */       
/* 151 */       if (enumdyecolor.getUnlocalizedName().equals(p_parseEnumDyeColor_0_))
/*     */       {
/* 153 */         return enumdyecolor;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static NbtTagValue parseNbtTagValue(String p_parseNbtTagValue_0_, String p_parseNbtTagValue_1_) {
/* 163 */     return (p_parseNbtTagValue_0_ != null && p_parseNbtTagValue_1_ != null) ? new NbtTagValue(p_parseNbtTagValue_0_, p_parseNbtTagValue_1_) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static VillagerProfession[] parseProfessions(String p_parseProfessions_0_) {
/* 168 */     if (p_parseProfessions_0_ == null)
/*     */     {
/* 170 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 174 */     List<VillagerProfession> list = new ArrayList<>();
/* 175 */     String[] astring = Config.tokenize(p_parseProfessions_0_, " ");
/*     */     
/* 177 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 179 */       String s = astring[i];
/* 180 */       VillagerProfession villagerprofession = parseProfession(s);
/*     */       
/* 182 */       if (villagerprofession == null) {
/*     */         
/* 184 */         warn("Invalid profession: " + s);
/* 185 */         return PROFESSIONS_INVALID;
/*     */       } 
/*     */       
/* 188 */       list.add(villagerprofession);
/*     */     } 
/*     */     
/* 191 */     if (list.isEmpty())
/*     */     {
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 197 */     VillagerProfession[] avillagerprofession = list.<VillagerProfession>toArray(new VillagerProfession[list.size()]);
/* 198 */     return avillagerprofession;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static VillagerProfession parseProfession(String p_parseProfession_0_) {
/* 205 */     p_parseProfession_0_ = p_parseProfession_0_.toLowerCase();
/* 206 */     String[] astring = Config.tokenize(p_parseProfession_0_, ":");
/*     */     
/* 208 */     if (astring.length > 2)
/*     */     {
/* 210 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 214 */     String s = astring[0];
/* 215 */     String s1 = null;
/*     */     
/* 217 */     if (astring.length > 1)
/*     */     {
/* 219 */       s1 = astring[1];
/*     */     }
/*     */     
/* 222 */     int i = parseProfessionId(s);
/*     */     
/* 224 */     if (i < 0)
/*     */     {
/* 226 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 230 */     int[] aint = null;
/*     */     
/* 232 */     if (s1 != null) {
/*     */       
/* 234 */       aint = parseCareerIds(i, s1);
/*     */       
/* 236 */       if (aint == null)
/*     */       {
/* 238 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 242 */     return new VillagerProfession(i, aint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseProfessionId(String p_parseProfessionId_0_) {
/* 249 */     int i = Config.parseInt(p_parseProfessionId_0_, -1);
/*     */     
/* 251 */     if (i >= 0)
/*     */     {
/* 253 */       return i;
/*     */     }
/* 255 */     if (p_parseProfessionId_0_.equals("farmer"))
/*     */     {
/* 257 */       return 0;
/*     */     }
/* 259 */     if (p_parseProfessionId_0_.equals("librarian"))
/*     */     {
/* 261 */       return 1;
/*     */     }
/* 263 */     if (p_parseProfessionId_0_.equals("priest"))
/*     */     {
/* 265 */       return 2;
/*     */     }
/* 267 */     if (p_parseProfessionId_0_.equals("blacksmith"))
/*     */     {
/* 269 */       return 3;
/*     */     }
/* 271 */     if (p_parseProfessionId_0_.equals("butcher"))
/*     */     {
/* 273 */       return 4;
/*     */     }
/*     */ 
/*     */     
/* 277 */     return p_parseProfessionId_0_.equals("nitwit") ? 5 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] parseCareerIds(int p_parseCareerIds_0_, String p_parseCareerIds_1_) {
/* 283 */     IntArraySet intArraySet = new IntArraySet();
/* 284 */     String[] astring = Config.tokenize(p_parseCareerIds_1_, ",");
/*     */     
/* 286 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 288 */       String s = astring[i];
/* 289 */       int j = parseCareerId(p_parseCareerIds_0_, s);
/*     */       
/* 291 */       if (j < 0)
/*     */       {
/* 293 */         return null;
/*     */       }
/*     */       
/* 296 */       intArraySet.add(j);
/*     */     } 
/*     */     
/* 299 */     int[] aint = intArraySet.toIntArray();
/* 300 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseCareerId(int p_parseCareerId_0_, String p_parseCareerId_1_) {
/* 305 */     int i = Config.parseInt(p_parseCareerId_1_, -1);
/*     */     
/* 307 */     if (i >= 0)
/*     */     {
/* 309 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 313 */     if (p_parseCareerId_0_ == 0) {
/*     */       
/* 315 */       if (p_parseCareerId_1_.equals("farmer"))
/*     */       {
/* 317 */         return 1;
/*     */       }
/*     */       
/* 320 */       if (p_parseCareerId_1_.equals("fisherman"))
/*     */       {
/* 322 */         return 2;
/*     */       }
/*     */       
/* 325 */       if (p_parseCareerId_1_.equals("shepherd"))
/*     */       {
/* 327 */         return 3;
/*     */       }
/*     */       
/* 330 */       if (p_parseCareerId_1_.equals("fletcher"))
/*     */       {
/* 332 */         return 4;
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if (p_parseCareerId_0_ == 1) {
/*     */       
/* 338 */       if (p_parseCareerId_1_.equals("librarian"))
/*     */       {
/* 340 */         return 1;
/*     */       }
/*     */       
/* 343 */       if (p_parseCareerId_1_.equals("cartographer"))
/*     */       {
/* 345 */         return 2;
/*     */       }
/*     */     } 
/*     */     
/* 349 */     if (p_parseCareerId_0_ == 2 && p_parseCareerId_1_.equals("cleric"))
/*     */     {
/* 351 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (p_parseCareerId_0_ == 3) {
/*     */       
/* 357 */       if (p_parseCareerId_1_.equals("armor"))
/*     */       {
/* 359 */         return 1;
/*     */       }
/*     */       
/* 362 */       if (p_parseCareerId_1_.equals("weapon"))
/*     */       {
/* 364 */         return 2;
/*     */       }
/*     */       
/* 367 */       if (p_parseCareerId_1_.equals("tool"))
/*     */       {
/* 369 */         return 3;
/*     */       }
/*     */     } 
/*     */     
/* 373 */     if (p_parseCareerId_0_ == 4) {
/*     */       
/* 375 */       if (p_parseCareerId_1_.equals("butcher"))
/*     */       {
/* 377 */         return 1;
/*     */       }
/*     */       
/* 380 */       if (p_parseCareerId_1_.equals("leather"))
/*     */       {
/* 382 */         return 2;
/*     */       }
/*     */     } 
/*     */     
/* 386 */     return (p_parseCareerId_0_ == 5 && p_parseCareerId_1_.equals("nitwit")) ? 1 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation parseTextureLocation(String p_parseTextureLocation_0_, String p_parseTextureLocation_1_) {
/* 393 */     if (p_parseTextureLocation_0_ == null)
/*     */     {
/* 395 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 399 */     p_parseTextureLocation_0_ = p_parseTextureLocation_0_.trim();
/* 400 */     String s = TextureUtils.fixResourcePath(p_parseTextureLocation_0_, p_parseTextureLocation_1_);
/*     */     
/* 402 */     if (!s.endsWith(".png"))
/*     */     {
/* 404 */       s = String.valueOf(s) + ".png";
/*     */     }
/*     */     
/* 407 */     return new ResourceLocation(String.valueOf(p_parseTextureLocation_1_) + "/" + s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties p_parseTextureLocations_0_, String p_parseTextureLocations_1_, EnumContainer p_parseTextureLocations_2_, String p_parseTextureLocations_3_, String p_parseTextureLocations_4_) {
/* 413 */     Map<ResourceLocation, ResourceLocation> map = new HashMap<>();
/* 414 */     String s = p_parseTextureLocations_0_.getProperty(p_parseTextureLocations_1_);
/*     */     
/* 416 */     if (s != null) {
/*     */       
/* 418 */       ResourceLocation resourcelocation = getGuiTextureLocation(p_parseTextureLocations_2_);
/* 419 */       ResourceLocation resourcelocation1 = parseTextureLocation(s, p_parseTextureLocations_4_);
/*     */       
/* 421 */       if (resourcelocation != null && resourcelocation1 != null)
/*     */       {
/* 423 */         map.put(resourcelocation, resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 427 */     String s5 = String.valueOf(p_parseTextureLocations_1_) + ".";
/*     */     
/* 429 */     for (Object s1 : p_parseTextureLocations_0_.keySet()) {
/*     */       
/* 431 */       if (((String)s1).startsWith(s5)) {
/*     */         
/* 433 */         String s2 = ((String)s1).substring(s5.length());
/* 434 */         s2 = s2.replace('\\', '/');
/* 435 */         s2 = StrUtils.removePrefixSuffix(s2, "/", ".png");
/* 436 */         String s3 = String.valueOf(p_parseTextureLocations_3_) + s2 + ".png";
/* 437 */         String s4 = p_parseTextureLocations_0_.getProperty((String)s1);
/* 438 */         ResourceLocation resourcelocation2 = new ResourceLocation(s3);
/* 439 */         ResourceLocation resourcelocation3 = parseTextureLocation(s4, p_parseTextureLocations_4_);
/* 440 */         map.put(resourcelocation2, resourcelocation3);
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getGuiTextureLocation(EnumContainer p_getGuiTextureLocation_0_) {
/* 449 */     switch (p_getGuiTextureLocation_0_) {
/*     */       
/*     */       case null:
/* 452 */         return ANVIL_GUI_TEXTURE;
/*     */       
/*     */       case BEACON:
/* 455 */         return BEACON_GUI_TEXTURE;
/*     */       
/*     */       case BREWING_STAND:
/* 458 */         return BREWING_STAND_GUI_TEXTURE;
/*     */       
/*     */       case CHEST:
/* 461 */         return CHEST_GUI_TEXTURE;
/*     */       
/*     */       case CRAFTING:
/* 464 */         return CRAFTING_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case CREATIVE:
/* 467 */         return null;
/*     */       
/*     */       case DISPENSER:
/* 470 */         return DISPENSER_GUI_TEXTURE;
/*     */       
/*     */       case ENCHANTMENT:
/* 473 */         return ENCHANTMENT_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case FURNACE:
/* 476 */         return FURNACE_GUI_TEXTURE;
/*     */       
/*     */       case HOPPER:
/* 479 */         return HOPPER_GUI_TEXTURE;
/*     */       
/*     */       case HORSE:
/* 482 */         return HORSE_GUI_TEXTURE;
/*     */       
/*     */       case INVENTORY:
/* 485 */         return INVENTORY_GUI_TEXTURE;
/*     */       
/*     */       case SHULKER_BOX:
/* 488 */         return SHULKER_BOX_GUI_TEXTURE;
/*     */       
/*     */       case VILLAGER:
/* 491 */         return VILLAGER_GUI_TEXTURE;
/*     */     } 
/*     */     
/* 494 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String p_isValid_1_) {
/* 500 */     if (this.fileName != null && this.fileName.length() > 0) {
/*     */       
/* 502 */       if (this.basePath == null) {
/*     */         
/* 504 */         warn("No base path found: " + p_isValid_1_);
/* 505 */         return false;
/*     */       } 
/* 507 */       if (this.container == null) {
/*     */         
/* 509 */         warn("No container found: " + p_isValid_1_);
/* 510 */         return false;
/*     */       } 
/* 512 */       if (this.textureLocations.isEmpty()) {
/*     */         
/* 514 */         warn("No texture found: " + p_isValid_1_);
/* 515 */         return false;
/*     */       } 
/* 517 */       if (this.professions == PROFESSIONS_INVALID) {
/*     */         
/* 519 */         warn("Invalid professions or careers: " + p_isValid_1_);
/* 520 */         return false;
/*     */       } 
/* 522 */       if (this.variants == VARIANTS_INVALID) {
/*     */         
/* 524 */         warn("Invalid variants: " + p_isValid_1_);
/* 525 */         return false;
/*     */       } 
/* 527 */       if (this.colors == COLORS_INVALID) {
/*     */         
/* 529 */         warn("Invalid colors: " + p_isValid_1_);
/* 530 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 534 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 539 */     warn("No name found: " + p_isValid_1_);
/* 540 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void warn(String p_warn_0_) {
/* 546 */     Config.warn("[CustomGuis] " + p_warn_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesGeneral(EnumContainer p_matchesGeneral_1_, BlockPos p_matchesGeneral_2_, IBlockAccess p_matchesGeneral_3_) {
/* 551 */     if (this.container != p_matchesGeneral_1_)
/*     */     {
/* 553 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 557 */     if (this.biomes != null) {
/*     */       
/* 559 */       Biome biome = p_matchesGeneral_3_.getBiome(p_matchesGeneral_2_);
/*     */       
/* 561 */       if (!Matches.biome(biome, this.biomes))
/*     */       {
/* 563 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 567 */     return !(this.heights != null && !this.heights.isInRange(p_matchesGeneral_2_.getY()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesPos(EnumContainer p_matchesPos_1_, BlockPos p_matchesPos_2_, IBlockAccess p_matchesPos_3_) {
/* 573 */     if (!matchesGeneral(p_matchesPos_1_, p_matchesPos_2_, p_matchesPos_3_))
/*     */     {
/* 575 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 579 */     switch (p_matchesPos_1_) {
/*     */       
/*     */       case BEACON:
/* 582 */         return matchesBeacon(p_matchesPos_2_, p_matchesPos_3_);
/*     */       
/*     */       case BREWING_STAND:
/* 585 */         return matchesNameable(p_matchesPos_2_, p_matchesPos_3_);
/*     */       
/*     */       case CHEST:
/* 588 */         return matchesChest(p_matchesPos_2_, p_matchesPos_3_);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 595 */         return true;
/*     */       
/*     */       case DISPENSER:
/* 598 */         return matchesDispenser(p_matchesPos_2_, p_matchesPos_3_);
/*     */       
/*     */       case ENCHANTMENT:
/* 601 */         return matchesNameable(p_matchesPos_2_, p_matchesPos_3_);
/*     */       
/*     */       case FURNACE:
/* 604 */         return matchesNameable(p_matchesPos_2_, p_matchesPos_3_);
/*     */       
/*     */       case HOPPER:
/* 607 */         return matchesNameable(p_matchesPos_2_, p_matchesPos_3_);
/*     */       case SHULKER_BOX:
/*     */         break;
/* 610 */     }  return matchesShulker(p_matchesPos_2_, p_matchesPos_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesNameable(BlockPos p_matchesNameable_1_, IBlockAccess p_matchesNameable_2_) {
/* 617 */     TileEntity tileentity = p_matchesNameable_2_.getTileEntity(p_matchesNameable_1_);
/*     */     
/* 619 */     if (!(tileentity instanceof IWorldNameable))
/*     */     {
/* 621 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 625 */     IWorldNameable iworldnameable = (IWorldNameable)tileentity;
/*     */     
/* 627 */     if (this.nbtName != null) {
/*     */       
/* 629 */       String s = iworldnameable.getName();
/*     */       
/* 631 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 633 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 637 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesBeacon(BlockPos p_matchesBeacon_1_, IBlockAccess p_matchesBeacon_2_) {
/* 643 */     TileEntity tileentity = p_matchesBeacon_2_.getTileEntity(p_matchesBeacon_1_);
/*     */     
/* 645 */     if (!(tileentity instanceof TileEntityBeacon))
/*     */     {
/* 647 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 651 */     TileEntityBeacon tileentitybeacon = (TileEntityBeacon)tileentity;
/*     */     
/* 653 */     if (this.levels != null) {
/*     */       
/* 655 */       int i = tileentitybeacon.func_191979_s();
/*     */       
/* 657 */       if (!this.levels.isInRange(i))
/*     */       {
/* 659 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 663 */     if (this.nbtName != null) {
/*     */       
/* 665 */       String s = tileentitybeacon.getName();
/*     */       
/* 667 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 669 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 673 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(BlockPos p_matchesChest_1_, IBlockAccess p_matchesChest_2_) {
/* 679 */     TileEntity tileentity = p_matchesChest_2_.getTileEntity(p_matchesChest_1_);
/*     */     
/* 681 */     if (tileentity instanceof TileEntityChest) {
/*     */       
/* 683 */       TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 684 */       return matchesChest(tileentitychest, p_matchesChest_1_, p_matchesChest_2_);
/*     */     } 
/* 686 */     if (tileentity instanceof TileEntityEnderChest) {
/*     */       
/* 688 */       TileEntityEnderChest tileentityenderchest = (TileEntityEnderChest)tileentity;
/* 689 */       return matchesEnderChest(tileentityenderchest, p_matchesChest_1_, p_matchesChest_2_);
/*     */     } 
/*     */ 
/*     */     
/* 693 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(TileEntityChest p_matchesChest_1_, BlockPos p_matchesChest_2_, IBlockAccess p_matchesChest_3_) {
/* 699 */     boolean flag = !(p_matchesChest_1_.adjacentChestXNeg == null && p_matchesChest_1_.adjacentChestXPos == null && p_matchesChest_1_.adjacentChestZNeg == null && p_matchesChest_1_.adjacentChestZPos == null);
/* 700 */     boolean flag1 = (p_matchesChest_1_.getChestType() == BlockChest.Type.TRAP);
/* 701 */     boolean flag2 = CustomGuis.isChristmas;
/* 702 */     boolean flag3 = false;
/* 703 */     String s = p_matchesChest_1_.getName();
/* 704 */     return matchesChest(flag, flag1, flag2, flag3, s);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesEnderChest(TileEntityEnderChest p_matchesEnderChest_1_, BlockPos p_matchesEnderChest_2_, IBlockAccess p_matchesEnderChest_3_) {
/* 709 */     return matchesChest(false, false, false, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesChest(boolean p_matchesChest_1_, boolean p_matchesChest_2_, boolean p_matchesChest_3_, boolean p_matchesChest_4_, String p_matchesChest_5_) {
/* 714 */     if (this.large != null && this.large.booleanValue() != p_matchesChest_1_)
/*     */     {
/* 716 */       return false;
/*     */     }
/* 718 */     if (this.trapped != null && this.trapped.booleanValue() != p_matchesChest_2_)
/*     */     {
/* 720 */       return false;
/*     */     }
/* 722 */     if (this.christmas != null && this.christmas.booleanValue() != p_matchesChest_3_)
/*     */     {
/* 724 */       return false;
/*     */     }
/* 726 */     if (this.ender != null && this.ender.booleanValue() != p_matchesChest_4_)
/*     */     {
/* 728 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 732 */     return !(this.nbtName != null && !this.nbtName.matchesValue(p_matchesChest_5_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesDispenser(BlockPos p_matchesDispenser_1_, IBlockAccess p_matchesDispenser_2_) {
/* 738 */     TileEntity tileentity = p_matchesDispenser_2_.getTileEntity(p_matchesDispenser_1_);
/*     */     
/* 740 */     if (!(tileentity instanceof TileEntityDispenser))
/*     */     {
/* 742 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 746 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)tileentity;
/*     */     
/* 748 */     if (this.nbtName != null) {
/*     */       
/* 750 */       String s = tileentitydispenser.getName();
/*     */       
/* 752 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 754 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 758 */     if (this.variants != null) {
/*     */       
/* 760 */       EnumVariant customguiproperties$enumvariant = getDispenserVariant(tileentitydispenser);
/*     */       
/* 762 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 764 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 768 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getDispenserVariant(TileEntityDispenser p_getDispenserVariant_1_) {
/* 774 */     return (p_getDispenserVariant_1_ instanceof net.minecraft.tileentity.TileEntityDropper) ? EnumVariant.DROPPER : EnumVariant.DISPENSER;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesShulker(BlockPos p_matchesShulker_1_, IBlockAccess p_matchesShulker_2_) {
/* 779 */     TileEntity tileentity = p_matchesShulker_2_.getTileEntity(p_matchesShulker_1_);
/*     */     
/* 781 */     if (!(tileentity instanceof TileEntityShulkerBox))
/*     */     {
/* 783 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 787 */     TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)tileentity;
/*     */     
/* 789 */     if (this.nbtName != null) {
/*     */       
/* 791 */       String s = tileentityshulkerbox.getName();
/*     */       
/* 793 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 795 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 799 */     if (this.colors != null) {
/*     */       
/* 801 */       EnumDyeColor enumdyecolor = tileentityshulkerbox.func_190592_s();
/*     */       
/* 803 */       if (!Config.equalsOne(enumdyecolor, (Object[])this.colors))
/*     */       {
/* 805 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 809 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesEntity(EnumContainer p_matchesEntity_1_, Entity p_matchesEntity_2_, IBlockAccess p_matchesEntity_3_) {
/* 815 */     if (!matchesGeneral(p_matchesEntity_1_, p_matchesEntity_2_.getPosition(), p_matchesEntity_3_))
/*     */     {
/* 817 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 821 */     if (this.nbtName != null) {
/*     */       
/* 823 */       String s = p_matchesEntity_2_.getName();
/*     */       
/* 825 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 827 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 831 */     switch (p_matchesEntity_1_) {
/*     */       
/*     */       case HORSE:
/* 834 */         return matchesHorse(p_matchesEntity_2_, p_matchesEntity_3_);
/*     */       
/*     */       case VILLAGER:
/* 837 */         return matchesVillager(p_matchesEntity_2_, p_matchesEntity_3_);
/*     */     } 
/*     */     
/* 840 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesVillager(Entity p_matchesVillager_1_, IBlockAccess p_matchesVillager_2_) {
/* 847 */     if (!(p_matchesVillager_1_ instanceof EntityVillager))
/*     */     {
/* 849 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 853 */     EntityVillager entityvillager = (EntityVillager)p_matchesVillager_1_;
/* 854 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 855 */     entityvillager.writeToNBT(nbttagcompound);
/* 856 */     Integer integer = Integer.valueOf(nbttagcompound.getInteger("Profession"));
/* 857 */     Integer integer1 = Integer.valueOf(nbttagcompound.getInteger("Career"));
/*     */     
/* 859 */     if (integer != null && integer1 != null) {
/*     */       
/* 861 */       if (this.professions != null) {
/*     */         
/* 863 */         boolean flag = false;
/*     */         
/* 865 */         for (int i = 0; i < this.professions.length; i++) {
/*     */           
/* 867 */           VillagerProfession villagerprofession = this.professions[i];
/*     */           
/* 869 */           if (villagerprofession.matches(integer.intValue(), integer1.intValue())) {
/*     */             
/* 871 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 876 */         if (!flag)
/*     */         {
/* 878 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 882 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 886 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesHorse(Entity p_matchesHorse_1_, IBlockAccess p_matchesHorse_2_) {
/* 893 */     if (!(p_matchesHorse_1_ instanceof AbstractHorse))
/*     */     {
/* 895 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 899 */     AbstractHorse abstracthorse = (AbstractHorse)p_matchesHorse_1_;
/*     */     
/* 901 */     if (this.variants != null) {
/*     */       
/* 903 */       EnumVariant customguiproperties$enumvariant = getHorseVariant(abstracthorse);
/*     */       
/* 905 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 907 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 911 */     if (this.colors != null && abstracthorse instanceof EntityLlama) {
/*     */       
/* 913 */       EntityLlama entityllama = (EntityLlama)abstracthorse;
/* 914 */       EnumDyeColor enumdyecolor = entityllama.func_190704_dO();
/*     */       
/* 916 */       if (!Config.equalsOne(enumdyecolor, (Object[])this.colors))
/*     */       {
/* 918 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 922 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getHorseVariant(AbstractHorse p_getHorseVariant_1_) {
/* 928 */     if (p_getHorseVariant_1_ instanceof net.minecraft.entity.passive.EntityHorse)
/*     */     {
/* 930 */       return EnumVariant.HORSE;
/*     */     }
/* 932 */     if (p_getHorseVariant_1_ instanceof net.minecraft.entity.passive.EntityDonkey)
/*     */     {
/* 934 */       return EnumVariant.DONKEY;
/*     */     }
/* 936 */     if (p_getHorseVariant_1_ instanceof net.minecraft.entity.passive.EntityMule)
/*     */     {
/* 938 */       return EnumVariant.MULE;
/*     */     }
/*     */ 
/*     */     
/* 942 */     return (p_getHorseVariant_1_ instanceof EntityLlama) ? EnumVariant.LLAMA : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumContainer getContainer() {
/* 948 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation p_getTextureLocation_1_) {
/* 953 */     ResourceLocation resourcelocation = this.textureLocations.get(p_getTextureLocation_1_);
/* 954 */     return (resourcelocation == null) ? p_getTextureLocation_1_ : resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 959 */     return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
/*     */   }
/*     */   
/*     */   public enum EnumContainer
/*     */   {
/* 964 */     ANVIL,
/* 965 */     BEACON,
/* 966 */     BREWING_STAND,
/* 967 */     CHEST,
/* 968 */     CRAFTING,
/* 969 */     DISPENSER,
/* 970 */     ENCHANTMENT,
/* 971 */     FURNACE,
/* 972 */     HOPPER,
/* 973 */     HORSE,
/* 974 */     VILLAGER,
/* 975 */     SHULKER_BOX,
/* 976 */     CREATIVE,
/* 977 */     INVENTORY;
/*     */   }
/*     */   
/*     */   private enum EnumVariant
/*     */   {
/* 982 */     HORSE,
/* 983 */     DONKEY,
/* 984 */     MULE,
/* 985 */     LLAMA,
/* 986 */     DISPENSER,
/* 987 */     DROPPER;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomGuiProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
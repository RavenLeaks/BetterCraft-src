/*      */ package optifine;
/*      */ 
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderItem;
/*      */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*      */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*      */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemEnchantedBook;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import shadersmod.client.Shaders;
/*      */ import shadersmod.client.ShadersRender;
/*      */ 
/*      */ public class CustomItems
/*      */ {
/*   43 */   private static CustomItemProperties[][] itemProperties = null;
/*   44 */   private static CustomItemProperties[][] enchantmentProperties = null;
/*   45 */   private static Map mapPotionIds = null;
/*   46 */   private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*      */   private static boolean useGlint = true;
/*      */   private static boolean renderOffHand = false;
/*      */   public static final int MASK_POTION_SPLASH = 16384;
/*      */   public static final int MASK_POTION_NAME = 63;
/*      */   public static final int MASK_POTION_EXTENDED = 64;
/*      */   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
/*      */   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
/*      */   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
/*      */   public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
/*      */   public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
/*      */   public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
/*   58 */   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
/*   59 */   private static final Map<String, Integer> mapPotionDamages = makeMapPotionDamages();
/*      */   
/*      */   private static final String TYPE_POTION_NORMAL = "normal";
/*      */   private static final String TYPE_POTION_SPLASH = "splash";
/*      */   private static final String TYPE_POTION_LINGER = "linger";
/*      */   
/*      */   public static void update() {
/*   66 */     itemProperties = null;
/*   67 */     enchantmentProperties = null;
/*   68 */     useGlint = true;
/*      */     
/*   70 */     if (Config.isCustomItems()) {
/*      */       
/*   72 */       readCitProperties("mcpatcher/cit.properties");
/*   73 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/*   75 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/*   77 */         IResourcePack iresourcepack = airesourcepack[i];
/*   78 */         update(iresourcepack);
/*      */       } 
/*      */       
/*   81 */       update((IResourcePack)Config.getDefaultResourcePack());
/*      */       
/*   83 */       if (itemProperties.length <= 0)
/*      */       {
/*   85 */         itemProperties = null;
/*      */       }
/*      */       
/*   88 */       if (enchantmentProperties.length <= 0)
/*      */       {
/*   90 */         enchantmentProperties = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readCitProperties(String p_readCitProperties_0_) {
/*      */     try {
/*   99 */       ResourceLocation resourcelocation = new ResourceLocation(p_readCitProperties_0_);
/*  100 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  102 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  107 */       Config.dbg("CustomItems: Loading " + p_readCitProperties_0_);
/*  108 */       Properties properties = new Properties();
/*  109 */       properties.load(inputstream);
/*  110 */       inputstream.close();
/*  111 */       useGlint = Config.parseBoolean(properties.getProperty("useGlint"), true);
/*      */     }
/*  113 */     catch (FileNotFoundException var4) {
/*      */       
/*      */       return;
/*      */     }
/*  117 */     catch (IOException ioexception) {
/*      */       
/*  119 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void update(IResourcePack p_update_0_) {
/*  125 */     String[] astring = ResUtils.collectFiles(p_update_0_, "mcpatcher/cit/", ".properties", (String[])null);
/*  126 */     Map map = makeAutoImageProperties(p_update_0_);
/*      */     
/*  128 */     if (map.size() > 0) {
/*      */       
/*  130 */       Set set = map.keySet();
/*  131 */       String[] astring1 = (String[])set.toArray((Object[])new String[set.size()]);
/*  132 */       astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])astring1);
/*      */     } 
/*      */     
/*  135 */     Arrays.sort((Object[])astring);
/*  136 */     List list = makePropertyList(itemProperties);
/*  137 */     List list1 = makePropertyList(enchantmentProperties);
/*      */     
/*  139 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  141 */       String s = astring[i];
/*  142 */       Config.dbg("CustomItems: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  146 */         CustomItemProperties customitemproperties = null;
/*      */         
/*  148 */         if (map.containsKey(s))
/*      */         {
/*  150 */           customitemproperties = (CustomItemProperties)map.get(s);
/*      */         }
/*      */         
/*  153 */         if (customitemproperties == null)
/*      */         
/*  155 */         { ResourceLocation resourcelocation = new ResourceLocation(s);
/*  156 */           InputStream inputstream = p_update_0_.getInputStream(resourcelocation);
/*      */           
/*  158 */           if (inputstream == null)
/*      */           
/*  160 */           { Config.warn("CustomItems file not found: " + s); }
/*      */           
/*      */           else
/*      */           
/*  164 */           { Properties properties = new Properties();
/*  165 */             properties.load(inputstream);
/*  166 */             customitemproperties = new CustomItemProperties(properties, s);
/*      */ 
/*      */             
/*  169 */             if (customitemproperties.isValid(s))
/*      */             
/*  171 */             { addToItemList(customitemproperties, list);
/*  172 */               addToEnchantmentList(customitemproperties, list1); }  }  continue; }  if (customitemproperties.isValid(s)) { addToItemList(customitemproperties, list); addToEnchantmentList(customitemproperties, list1); }
/*      */ 
/*      */       
/*  175 */       } catch (FileNotFoundException var11) {
/*      */         
/*  177 */         Config.warn("CustomItems file not found: " + s);
/*      */         continue;
/*  179 */       } catch (Exception exception) {
/*      */         
/*  181 */         exception.printStackTrace();
/*      */         continue;
/*      */       } 
/*      */     } 
/*  185 */     itemProperties = propertyListToArray(list);
/*  186 */     enchantmentProperties = propertyListToArray(list1);
/*  187 */     Comparator<? super CustomItemProperties> comparator = getPropertiesComparator();
/*      */     
/*  189 */     for (int j = 0; j < itemProperties.length; j++) {
/*      */       
/*  191 */       CustomItemProperties[] acustomitemproperties = itemProperties[j];
/*      */       
/*  193 */       if (acustomitemproperties != null)
/*      */       {
/*  195 */         Arrays.sort(acustomitemproperties, comparator);
/*      */       }
/*      */     } 
/*      */     
/*  199 */     for (int k = 0; k < enchantmentProperties.length; k++) {
/*      */       
/*  201 */       CustomItemProperties[] acustomitemproperties1 = enchantmentProperties[k];
/*      */       
/*  203 */       if (acustomitemproperties1 != null)
/*      */       {
/*  205 */         Arrays.sort(acustomitemproperties1, comparator);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Comparator getPropertiesComparator() {
/*  212 */     Comparator comparator = new Comparator()
/*      */       {
/*      */         public int compare(Object p_compare_1_, Object p_compare_2_)
/*      */         {
/*  216 */           CustomItemProperties customitemproperties = (CustomItemProperties)p_compare_1_;
/*  217 */           CustomItemProperties customitemproperties1 = (CustomItemProperties)p_compare_2_;
/*      */           
/*  219 */           if (customitemproperties.layer != customitemproperties1.layer)
/*      */           {
/*  221 */             return customitemproperties.layer - customitemproperties1.layer;
/*      */           }
/*  223 */           if (customitemproperties.weight != customitemproperties1.weight)
/*      */           {
/*  225 */             return customitemproperties1.weight - customitemproperties.weight;
/*      */           }
/*      */ 
/*      */           
/*  229 */           return !customitemproperties.basePath.equals(customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name);
/*      */         }
/*      */       };
/*      */     
/*  233 */     return comparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap p_updateIcons_0_) {
/*  238 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  240 */       customitemproperties.updateIcons(p_updateIcons_0_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadModels(ModelBakery p_loadModels_0_) {
/*  246 */     for (CustomItemProperties customitemproperties : getAllProperties())
/*      */     {
/*  248 */       customitemproperties.loadModels(p_loadModels_0_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateModels() {
/*  254 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/*      */       
/*  256 */       if (customitemproperties.type == 1) {
/*      */         
/*  258 */         TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  259 */         customitemproperties.updateModelTexture(texturemap, itemModelGenerator);
/*  260 */         customitemproperties.updateModelsFull();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static List<CustomItemProperties> getAllProperties() {
/*  267 */     List<CustomItemProperties> list = new ArrayList<>();
/*  268 */     addAll(itemProperties, list);
/*  269 */     addAll(enchantmentProperties, list);
/*  270 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addAll(CustomItemProperties[][] p_addAll_0_, List<CustomItemProperties> p_addAll_1_) {
/*  275 */     if (p_addAll_0_ != null)
/*      */     {
/*  277 */       for (int i = 0; i < p_addAll_0_.length; i++) {
/*      */         
/*  279 */         CustomItemProperties[] acustomitemproperties = p_addAll_0_[i];
/*      */         
/*  281 */         if (acustomitemproperties != null)
/*      */         {
/*  283 */           for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */             
/*  285 */             CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */             
/*  287 */             if (customitemproperties != null)
/*      */             {
/*  289 */               p_addAll_1_.add(customitemproperties);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makeAutoImageProperties(IResourcePack p_makeAutoImageProperties_0_) {
/*  299 */     Map<Object, Object> map = new HashMap<>();
/*  300 */     map.putAll(makePotionImageProperties(p_makeAutoImageProperties_0_, "normal", Item.getIdFromItem((Item)Items.POTIONITEM)));
/*  301 */     map.putAll(makePotionImageProperties(p_makeAutoImageProperties_0_, "splash", Item.getIdFromItem((Item)Items.SPLASH_POTION)));
/*  302 */     map.putAll(makePotionImageProperties(p_makeAutoImageProperties_0_, "linger", Item.getIdFromItem((Item)Items.LINGERING_POTION)));
/*  303 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map makePotionImageProperties(IResourcePack p_makePotionImageProperties_0_, String p_makePotionImageProperties_1_, int p_makePotionImageProperties_2_) {
/*  308 */     Map<Object, Object> map = new HashMap<>();
/*  309 */     String s = String.valueOf(p_makePotionImageProperties_1_) + "/";
/*  310 */     String[] astring = { "mcpatcher/cit/potion/" + s, "mcpatcher/cit/Potion/" + s };
/*  311 */     String[] astring1 = { ".png" };
/*  312 */     String[] astring2 = ResUtils.collectFiles(p_makePotionImageProperties_0_, astring, astring1);
/*      */     
/*  314 */     for (int i = 0; i < astring2.length; i++) {
/*      */       
/*  316 */       String s1 = astring2[i];
/*  317 */       String name = StrUtils.removePrefixSuffix(s1, astring, astring1);
/*  318 */       Properties properties = makePotionProperties(name, p_makePotionImageProperties_1_, p_makePotionImageProperties_2_, s1);
/*      */       
/*  320 */       if (properties != null) {
/*      */         
/*  322 */         String s3 = String.valueOf(StrUtils.removeSuffix(s1, astring1)) + ".properties";
/*  323 */         CustomItemProperties customitemproperties = new CustomItemProperties(properties, s3);
/*  324 */         map.put(s3, customitemproperties);
/*      */       } 
/*      */     } 
/*      */     
/*  328 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties makePotionProperties(String p_makePotionProperties_0_, String p_makePotionProperties_1_, int p_makePotionProperties_2_, String p_makePotionProperties_3_) {
/*  333 */     if (StrUtils.endsWith(p_makePotionProperties_0_, new String[] { "_n", "_s" }))
/*      */     {
/*  335 */       return null;
/*      */     }
/*  337 */     if (p_makePotionProperties_0_.equals("empty") && p_makePotionProperties_1_.equals("normal")) {
/*      */       
/*  339 */       p_makePotionProperties_2_ = Item.getIdFromItem(Items.GLASS_BOTTLE);
/*  340 */       Properties properties = new Properties();
/*  341 */       properties.put("type", "item");
/*  342 */       properties.put("items", p_makePotionProperties_2_);
/*  343 */       return properties;
/*      */     } 
/*      */ 
/*      */     
/*  347 */     int[] aint = (int[])getMapPotionIds().get(p_makePotionProperties_0_);
/*      */     
/*  349 */     if (aint == null) {
/*      */       
/*  351 */       Config.warn("Potion not found for image: " + p_makePotionProperties_3_);
/*  352 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  356 */     StringBuffer stringbuffer = new StringBuffer();
/*      */     
/*  358 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  360 */       int j = aint[i];
/*      */       
/*  362 */       if (p_makePotionProperties_1_.equals("splash"))
/*      */       {
/*  364 */         j |= 0x4000;
/*      */       }
/*      */       
/*  367 */       if (i > 0)
/*      */       {
/*  369 */         stringbuffer.append(" ");
/*      */       }
/*      */       
/*  372 */       stringbuffer.append(j);
/*      */     } 
/*      */     
/*  375 */     int k = 16447;
/*      */     
/*  377 */     if (p_makePotionProperties_0_.equals("water") || p_makePotionProperties_0_.equals("mundane"))
/*      */     {
/*  379 */       k |= 0x40;
/*      */     }
/*      */     
/*  382 */     Properties properties1 = new Properties();
/*  383 */     properties1.put("type", "item");
/*  384 */     properties1.put("items", p_makePotionProperties_2_);
/*  385 */     properties1.put("damage", stringbuffer.toString());
/*  386 */     properties1.put("damageMask", k);
/*      */     
/*  388 */     if (p_makePotionProperties_1_.equals("splash")) {
/*      */       
/*  390 */       properties1.put("texture.potion_bottle_splash", p_makePotionProperties_0_);
/*      */     }
/*      */     else {
/*      */       
/*  394 */       properties1.put("texture.potion_bottle_drinkable", p_makePotionProperties_0_);
/*      */     } 
/*      */     
/*  397 */     return properties1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMapPotionIds() {
/*  404 */     if (mapPotionIds == null) {
/*      */       
/*  406 */       mapPotionIds = new LinkedHashMap<>();
/*  407 */       mapPotionIds.put("water", getPotionId(0, 0));
/*  408 */       mapPotionIds.put("awkward", getPotionId(0, 1));
/*  409 */       mapPotionIds.put("thick", getPotionId(0, 2));
/*  410 */       mapPotionIds.put("potent", getPotionId(0, 3));
/*  411 */       mapPotionIds.put("regeneration", getPotionIds(1));
/*  412 */       mapPotionIds.put("movespeed", getPotionIds(2));
/*  413 */       mapPotionIds.put("fireresistance", getPotionIds(3));
/*  414 */       mapPotionIds.put("poison", getPotionIds(4));
/*  415 */       mapPotionIds.put("heal", getPotionIds(5));
/*  416 */       mapPotionIds.put("nightvision", getPotionIds(6));
/*  417 */       mapPotionIds.put("clear", getPotionId(7, 0));
/*  418 */       mapPotionIds.put("bungling", getPotionId(7, 1));
/*  419 */       mapPotionIds.put("charming", getPotionId(7, 2));
/*  420 */       mapPotionIds.put("rank", getPotionId(7, 3));
/*  421 */       mapPotionIds.put("weakness", getPotionIds(8));
/*  422 */       mapPotionIds.put("damageboost", getPotionIds(9));
/*  423 */       mapPotionIds.put("moveslowdown", getPotionIds(10));
/*  424 */       mapPotionIds.put("leaping", getPotionIds(11));
/*  425 */       mapPotionIds.put("harm", getPotionIds(12));
/*  426 */       mapPotionIds.put("waterbreathing", getPotionIds(13));
/*  427 */       mapPotionIds.put("invisibility", getPotionIds(14));
/*  428 */       mapPotionIds.put("thin", getPotionId(15, 0));
/*  429 */       mapPotionIds.put("debonair", getPotionId(15, 1));
/*  430 */       mapPotionIds.put("sparkling", getPotionId(15, 2));
/*  431 */       mapPotionIds.put("stinky", getPotionId(15, 3));
/*  432 */       mapPotionIds.put("mundane", getPotionId(0, 4));
/*  433 */       mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
/*  434 */       mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
/*  435 */       mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
/*  436 */       mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
/*  437 */       mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
/*  438 */       mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
/*  439 */       mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
/*  440 */       mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
/*      */     } 
/*      */     
/*  443 */     return mapPotionIds;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionIds(int p_getPotionIds_0_) {
/*  448 */     return new int[] { p_getPotionIds_0_, p_getPotionIds_0_ + 16, p_getPotionIds_0_ + 32, p_getPotionIds_0_ + 48 };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getPotionId(int p_getPotionId_0_, int p_getPotionId_1_) {
/*  453 */     return new int[] { p_getPotionId_0_ + p_getPotionId_1_ * 16 };
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionNameDamage(String p_getPotionNameDamage_0_) {
/*  458 */     String s = "effect." + p_getPotionNameDamage_0_;
/*      */     
/*  460 */     for (ResourceLocation resourcelocation : Potion.REGISTRY.getKeys()) {
/*      */       
/*  462 */       Potion potion = (Potion)Potion.REGISTRY.getObject(resourcelocation);
/*  463 */       String s1 = potion.getName();
/*      */       
/*  465 */       if (s.equals(s1))
/*      */       {
/*  467 */         return Potion.getIdFromPotion(potion);
/*      */       }
/*      */     } 
/*      */     
/*  471 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(CustomItemProperties[][] p_makePropertyList_0_) {
/*  476 */     List<List> list = new ArrayList();
/*      */     
/*  478 */     if (p_makePropertyList_0_ != null)
/*      */     {
/*  480 */       for (int i = 0; i < p_makePropertyList_0_.length; i++) {
/*      */         
/*  482 */         CustomItemProperties[] acustomitemproperties = p_makePropertyList_0_[i];
/*  483 */         List list1 = null;
/*      */         
/*  485 */         if (acustomitemproperties != null)
/*      */         {
/*  487 */           list1 = new ArrayList(Arrays.asList((Object[])acustomitemproperties));
/*      */         }
/*      */         
/*  490 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/*  494 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomItemProperties[][] propertyListToArray(List<List> p_propertyListToArray_0_) {
/*  499 */     CustomItemProperties[][] acustomitemproperties = new CustomItemProperties[p_propertyListToArray_0_.size()][];
/*      */     
/*  501 */     for (int i = 0; i < p_propertyListToArray_0_.size(); i++) {
/*      */       
/*  503 */       List list = p_propertyListToArray_0_.get(i);
/*      */       
/*  505 */       if (list != null) {
/*      */         
/*  507 */         CustomItemProperties[] acustomitemproperties1 = (CustomItemProperties[])list.toArray((Object[])new CustomItemProperties[list.size()]);
/*  508 */         Arrays.sort(acustomitemproperties1, new CustomItemsComparator());
/*  509 */         acustomitemproperties[i] = acustomitemproperties1;
/*      */       } 
/*      */     } 
/*      */     
/*  513 */     return acustomitemproperties;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToItemList(CustomItemProperties p_addToItemList_0_, List p_addToItemList_1_) {
/*  518 */     if (p_addToItemList_0_.items != null)
/*      */     {
/*  520 */       for (int i = 0; i < p_addToItemList_0_.items.length; i++) {
/*      */         
/*  522 */         int j = p_addToItemList_0_.items[i];
/*      */         
/*  524 */         if (j <= 0) {
/*      */           
/*  526 */           Config.warn("Invalid item ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/*  530 */           addToList(p_addToItemList_0_, p_addToItemList_1_, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToEnchantmentList(CustomItemProperties p_addToEnchantmentList_0_, List p_addToEnchantmentList_1_) {
/*  538 */     if (p_addToEnchantmentList_0_.type == 2)
/*      */     {
/*  540 */       if (p_addToEnchantmentList_0_.enchantmentIds != null)
/*      */       {
/*  542 */         for (int i = 0; i < 256; i++) {
/*      */           
/*  544 */           if (p_addToEnchantmentList_0_.enchantmentIds.isInRange(i))
/*      */           {
/*  546 */             addToList(p_addToEnchantmentList_0_, p_addToEnchantmentList_1_, i);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomItemProperties p_addToList_0_, List<List> p_addToList_1_, int p_addToList_2_) {
/*  555 */     while (p_addToList_2_ >= p_addToList_1_.size())
/*      */     {
/*  557 */       p_addToList_1_.add(null);
/*      */     }
/*      */     
/*  560 */     List<CustomItemProperties> list = p_addToList_1_.get(p_addToList_2_);
/*      */     
/*  562 */     if (list == null) {
/*      */       
/*  564 */       list = new ArrayList();
/*  565 */       p_addToList_1_.set(p_addToList_2_, list);
/*      */     } 
/*      */     
/*  568 */     list.add(p_addToList_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static IBakedModel getCustomItemModel(ItemStack p_getCustomItemModel_0_, IBakedModel p_getCustomItemModel_1_, ResourceLocation p_getCustomItemModel_2_, boolean p_getCustomItemModel_3_) {
/*  573 */     if (!p_getCustomItemModel_3_ && p_getCustomItemModel_1_.isGui3d())
/*      */     {
/*  575 */       return p_getCustomItemModel_1_;
/*      */     }
/*  577 */     if (itemProperties == null)
/*      */     {
/*  579 */       return p_getCustomItemModel_1_;
/*      */     }
/*      */ 
/*      */     
/*  583 */     CustomItemProperties customitemproperties = getCustomItemProperties(p_getCustomItemModel_0_, 1);
/*      */     
/*  585 */     if (customitemproperties == null)
/*      */     {
/*  587 */       return p_getCustomItemModel_1_;
/*      */     }
/*      */ 
/*      */     
/*  591 */     IBakedModel ibakedmodel = customitemproperties.getBakedModel(p_getCustomItemModel_2_, p_getCustomItemModel_3_);
/*  592 */     return (ibakedmodel != null) ? ibakedmodel : p_getCustomItemModel_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean bindCustomArmorTexture(ItemStack p_bindCustomArmorTexture_0_, EntityEquipmentSlot p_bindCustomArmorTexture_1_, String p_bindCustomArmorTexture_2_) {
/*  599 */     if (itemProperties == null)
/*      */     {
/*  601 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  605 */     ResourceLocation resourcelocation = getCustomArmorLocation(p_bindCustomArmorTexture_0_, p_bindCustomArmorTexture_1_, p_bindCustomArmorTexture_2_);
/*      */     
/*  607 */     if (resourcelocation == null)
/*      */     {
/*  609 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  613 */     Config.getTextureManager().bindTexture(resourcelocation);
/*  614 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ResourceLocation getCustomArmorLocation(ItemStack p_getCustomArmorLocation_0_, EntityEquipmentSlot p_getCustomArmorLocation_1_, String p_getCustomArmorLocation_2_) {
/*  621 */     CustomItemProperties customitemproperties = getCustomItemProperties(p_getCustomArmorLocation_0_, 3);
/*      */     
/*  623 */     if (customitemproperties == null)
/*      */     {
/*  625 */       return null;
/*      */     }
/*  627 */     if (customitemproperties.mapTextureLocations == null)
/*      */     {
/*  629 */       return customitemproperties.textureLocation;
/*      */     }
/*      */ 
/*      */     
/*  633 */     Item item = p_getCustomArmorLocation_0_.getItem();
/*      */     
/*  635 */     if (!(item instanceof ItemArmor))
/*      */     {
/*  637 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  641 */     ItemArmor itemarmor = (ItemArmor)item;
/*  642 */     String s = itemarmor.getArmorMaterial().getName();
/*  643 */     int i = (p_getCustomArmorLocation_1_ == EntityEquipmentSlot.LEGS) ? 2 : 1;
/*  644 */     StringBuffer stringbuffer = new StringBuffer();
/*  645 */     stringbuffer.append("texture.");
/*  646 */     stringbuffer.append(s);
/*  647 */     stringbuffer.append("_layer_");
/*  648 */     stringbuffer.append(i);
/*      */     
/*  650 */     if (p_getCustomArmorLocation_2_ != null) {
/*      */       
/*  652 */       stringbuffer.append("_");
/*  653 */       stringbuffer.append(p_getCustomArmorLocation_2_);
/*      */     } 
/*      */     
/*  656 */     String s1 = stringbuffer.toString();
/*  657 */     ResourceLocation resourcelocation = (ResourceLocation)customitemproperties.mapTextureLocations.get(s1);
/*  658 */     return (resourcelocation == null) ? customitemproperties.textureLocation : resourcelocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ResourceLocation getCustomElytraTexture(ItemStack p_getCustomElytraTexture_0_, ResourceLocation p_getCustomElytraTexture_1_) {
/*  665 */     if (itemProperties == null)
/*      */     {
/*  667 */       return p_getCustomElytraTexture_1_;
/*      */     }
/*      */ 
/*      */     
/*  671 */     CustomItemProperties customitemproperties = getCustomItemProperties(p_getCustomElytraTexture_0_, 4);
/*      */     
/*  673 */     if (customitemproperties == null)
/*      */     {
/*  675 */       return p_getCustomElytraTexture_1_;
/*      */     }
/*      */ 
/*      */     
/*  679 */     return (customitemproperties.textureLocation == null) ? p_getCustomElytraTexture_1_ : customitemproperties.textureLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomItemProperties getCustomItemProperties(ItemStack p_getCustomItemProperties_0_, int p_getCustomItemProperties_1_) {
/*  686 */     if (itemProperties == null)
/*      */     {
/*  688 */       return null;
/*      */     }
/*  690 */     if (p_getCustomItemProperties_0_ == null)
/*      */     {
/*  692 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  696 */     Item item = p_getCustomItemProperties_0_.getItem();
/*  697 */     int i = Item.getIdFromItem(item);
/*      */     
/*  699 */     if (i >= 0 && i < itemProperties.length) {
/*      */       
/*  701 */       CustomItemProperties[] acustomitemproperties = itemProperties[i];
/*      */       
/*  703 */       if (acustomitemproperties != null)
/*      */       {
/*  705 */         for (int j = 0; j < acustomitemproperties.length; j++) {
/*      */           
/*  707 */           CustomItemProperties customitemproperties = acustomitemproperties[j];
/*      */           
/*  709 */           if (customitemproperties.type == p_getCustomItemProperties_1_ && matchesProperties(customitemproperties, p_getCustomItemProperties_0_, null))
/*      */           {
/*  711 */             return customitemproperties;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  717 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean matchesProperties(CustomItemProperties p_matchesProperties_0_, ItemStack p_matchesProperties_1_, int[][] p_matchesProperties_2_) {
/*  723 */     Item item = p_matchesProperties_1_.getItem();
/*      */     
/*  725 */     if (p_matchesProperties_0_.damage != null) {
/*      */       
/*  727 */       int i = getItemStackDamage(p_matchesProperties_1_);
/*      */       
/*  729 */       if (i < 0)
/*      */       {
/*  731 */         return false;
/*      */       }
/*      */       
/*  734 */       if (p_matchesProperties_0_.damageMask != 0)
/*      */       {
/*  736 */         i &= p_matchesProperties_0_.damageMask;
/*      */       }
/*      */       
/*  739 */       if (p_matchesProperties_0_.damagePercent) {
/*      */         
/*  741 */         int j = item.getMaxDamage();
/*  742 */         i = (int)((i * 100) / j);
/*      */       } 
/*      */       
/*  745 */       if (!p_matchesProperties_0_.damage.isInRange(i))
/*      */       {
/*  747 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  751 */     if (p_matchesProperties_0_.stackSize != null && !p_matchesProperties_0_.stackSize.isInRange(p_matchesProperties_1_.func_190916_E()))
/*      */     {
/*  753 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  757 */     int[][] aint = p_matchesProperties_2_;
/*      */     
/*  759 */     if (p_matchesProperties_0_.enchantmentIds != null) {
/*      */       
/*  761 */       if (p_matchesProperties_2_ == null)
/*      */       {
/*  763 */         aint = getEnchantmentIdLevels(p_matchesProperties_1_);
/*      */       }
/*      */       
/*  766 */       boolean flag = false;
/*      */       
/*  768 */       for (int k = 0; k < aint.length; k++) {
/*      */         
/*  770 */         int l = aint[k][0];
/*      */         
/*  772 */         if (p_matchesProperties_0_.enchantmentIds.isInRange(l)) {
/*      */           
/*  774 */           flag = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  779 */       if (!flag)
/*      */       {
/*  781 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  785 */     if (p_matchesProperties_0_.enchantmentLevels != null) {
/*      */       
/*  787 */       if (aint == null)
/*      */       {
/*  789 */         aint = getEnchantmentIdLevels(p_matchesProperties_1_);
/*      */       }
/*      */       
/*  792 */       boolean flag1 = false;
/*      */       
/*  794 */       for (int i1 = 0; i1 < aint.length; i1++) {
/*      */         
/*  796 */         int k1 = aint[i1][1];
/*      */         
/*  798 */         if (p_matchesProperties_0_.enchantmentLevels.isInRange(k1)) {
/*      */           
/*  800 */           flag1 = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  805 */       if (!flag1)
/*      */       {
/*  807 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  811 */     if (p_matchesProperties_0_.nbtTagValues != null) {
/*      */       
/*  813 */       NBTTagCompound nbttagcompound = p_matchesProperties_1_.getTagCompound();
/*      */       
/*  815 */       for (int j1 = 0; j1 < p_matchesProperties_0_.nbtTagValues.length; j1++) {
/*      */         
/*  817 */         NbtTagValue nbttagvalue = p_matchesProperties_0_.nbtTagValues[j1];
/*      */         
/*  819 */         if (!nbttagvalue.matches(nbttagcompound))
/*      */         {
/*  821 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  826 */     if (p_matchesProperties_0_.hand != 0) {
/*      */       
/*  828 */       if (p_matchesProperties_0_.hand == 1 && renderOffHand)
/*      */       {
/*  830 */         return false;
/*      */       }
/*      */       
/*  833 */       if (p_matchesProperties_0_.hand == 2 && !renderOffHand)
/*      */       {
/*  835 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  839 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getItemStackDamage(ItemStack p_getItemStackDamage_0_) {
/*  845 */     Item item = p_getItemStackDamage_0_.getItem();
/*  846 */     return (item instanceof net.minecraft.item.ItemPotion) ? getPotionDamage(p_getItemStackDamage_0_) : p_getItemStackDamage_0_.getItemDamage();
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionDamage(ItemStack p_getPotionDamage_0_) {
/*  851 */     NBTTagCompound nbttagcompound = p_getPotionDamage_0_.getTagCompound();
/*      */     
/*  853 */     if (nbttagcompound == null)
/*      */     {
/*  855 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  859 */     String s = nbttagcompound.getString("Potion");
/*      */     
/*  861 */     if (s == null)
/*      */     {
/*  863 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  867 */     Integer integer = mapPotionDamages.get(s);
/*      */     
/*  869 */     if (integer == null)
/*      */     {
/*  871 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  875 */     int i = integer.intValue();
/*      */     
/*  877 */     if (p_getPotionDamage_0_.getItem() == Items.SPLASH_POTION)
/*      */     {
/*  879 */       i |= 0x4000;
/*      */     }
/*      */     
/*  882 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<String, Integer> makeMapPotionDamages() {
/*  890 */     Map<String, Integer> map = new HashMap<>();
/*  891 */     addPotion("water", 0, false, map);
/*  892 */     addPotion("awkward", 16, false, map);
/*  893 */     addPotion("thick", 32, false, map);
/*  894 */     addPotion("mundane", 64, false, map);
/*  895 */     addPotion("regeneration", 1, true, map);
/*  896 */     addPotion("swiftness", 2, true, map);
/*  897 */     addPotion("fire_resistance", 3, true, map);
/*  898 */     addPotion("poison", 4, true, map);
/*  899 */     addPotion("healing", 5, true, map);
/*  900 */     addPotion("night_vision", 6, true, map);
/*  901 */     addPotion("weakness", 8, true, map);
/*  902 */     addPotion("strength", 9, true, map);
/*  903 */     addPotion("slowness", 10, true, map);
/*  904 */     addPotion("leaping", 11, true, map);
/*  905 */     addPotion("harming", 12, true, map);
/*  906 */     addPotion("water_breathing", 13, true, map);
/*  907 */     addPotion("invisibility", 14, true, map);
/*  908 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addPotion(String p_addPotion_0_, int p_addPotion_1_, boolean p_addPotion_2_, Map<String, Integer> p_addPotion_3_) {
/*  913 */     if (p_addPotion_2_)
/*      */     {
/*  915 */       p_addPotion_1_ |= 0x2000;
/*      */     }
/*      */     
/*  918 */     p_addPotion_3_.put("minecraft:" + p_addPotion_0_, Integer.valueOf(p_addPotion_1_));
/*      */     
/*  920 */     if (p_addPotion_2_) {
/*      */       
/*  922 */       int i = p_addPotion_1_ | 0x20;
/*  923 */       p_addPotion_3_.put("minecraft:strong_" + p_addPotion_0_, Integer.valueOf(i));
/*  924 */       int j = p_addPotion_1_ | 0x40;
/*  925 */       p_addPotion_3_.put("minecraft:long_" + p_addPotion_0_, Integer.valueOf(j));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int[][] getEnchantmentIdLevels(ItemStack p_getEnchantmentIdLevels_0_) {
/*      */     NBTTagList nbttaglist1;
/*  931 */     Item item = p_getEnchantmentIdLevels_0_.getItem();
/*      */ 
/*      */     
/*  934 */     if (item == Items.ENCHANTED_BOOK) {
/*      */       
/*  936 */       ItemEnchantedBook itemenchantedbook = (ItemEnchantedBook)Items.ENCHANTED_BOOK;
/*  937 */       nbttaglist1 = ItemEnchantedBook.getEnchantments(p_getEnchantmentIdLevels_0_);
/*      */     }
/*      */     else {
/*      */       
/*  941 */       nbttaglist1 = p_getEnchantmentIdLevels_0_.getEnchantmentTagList();
/*      */     } 
/*      */     
/*  944 */     NBTTagList nbttaglist = nbttaglist1;
/*      */     
/*  946 */     if (nbttaglist != null && nbttaglist.tagCount() > 0) {
/*      */       
/*  948 */       int[][] aint = new int[nbttaglist.tagCount()][2];
/*      */       
/*  950 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/*  952 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  953 */         int j = nbttagcompound.getShort("id");
/*  954 */         int k = nbttagcompound.getShort("lvl");
/*  955 */         aint[i][0] = j;
/*  956 */         aint[i][1] = k;
/*      */       } 
/*      */       
/*  959 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  963 */     return EMPTY_INT2_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomEffect(RenderItem p_renderCustomEffect_0_, ItemStack p_renderCustomEffect_1_, IBakedModel p_renderCustomEffect_2_) {
/*  969 */     if (enchantmentProperties == null)
/*      */     {
/*  971 */       return false;
/*      */     }
/*  973 */     if (p_renderCustomEffect_1_ == null)
/*      */     {
/*  975 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  979 */     int[][] aint = getEnchantmentIdLevels(p_renderCustomEffect_1_);
/*      */     
/*  981 */     if (aint.length <= 0)
/*      */     {
/*  983 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  987 */     Set<Integer> set = null;
/*  988 */     boolean flag = false;
/*  989 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/*  991 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/*  993 */       int j = aint[i][0];
/*      */       
/*  995 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/*  997 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/*  999 */         if (acustomitemproperties != null)
/*      */         {
/* 1001 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/* 1003 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/* 1005 */             if (set == null)
/*      */             {
/* 1007 */               set = new HashSet();
/*      */             }
/*      */             
/* 1010 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, p_renderCustomEffect_1_, aint) && customitemproperties.textureLocation != null) {
/*      */               
/* 1012 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/* 1013 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/* 1015 */               if (!flag) {
/*      */                 
/* 1017 */                 flag = true;
/* 1018 */                 GlStateManager.depthMask(false);
/* 1019 */                 GlStateManager.depthFunc(514);
/* 1020 */                 GlStateManager.disableLighting();
/* 1021 */                 GlStateManager.matrixMode(5890);
/*      */               } 
/*      */               
/* 1024 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/* 1025 */               GlStateManager.pushMatrix();
/* 1026 */               GlStateManager.scale(f / 2.0F, f / 2.0F, f / 2.0F);
/* 1027 */               float f1 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/* 1028 */               GlStateManager.translate(f1, 0.0F, 0.0F);
/* 1029 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/* 1030 */               p_renderCustomEffect_0_.func_191965_a(p_renderCustomEffect_2_, -1);
/* 1031 */               GlStateManager.popMatrix();
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1038 */     if (flag) {
/*      */       
/* 1040 */       GlStateManager.enableAlpha();
/* 1041 */       GlStateManager.enableBlend();
/* 1042 */       GlStateManager.blendFunc(770, 771);
/* 1043 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1044 */       GlStateManager.matrixMode(5888);
/* 1045 */       GlStateManager.enableLighting();
/* 1046 */       GlStateManager.depthFunc(515);
/* 1047 */       GlStateManager.depthMask(true);
/* 1048 */       texturemanager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*      */     } 
/*      */     
/* 1051 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean renderCustomArmorEffect(EntityLivingBase p_renderCustomArmorEffect_0_, ItemStack p_renderCustomArmorEffect_1_, ModelBase p_renderCustomArmorEffect_2_, float p_renderCustomArmorEffect_3_, float p_renderCustomArmorEffect_4_, float p_renderCustomArmorEffect_5_, float p_renderCustomArmorEffect_6_, float p_renderCustomArmorEffect_7_, float p_renderCustomArmorEffect_8_, float p_renderCustomArmorEffect_9_) {
/* 1058 */     if (enchantmentProperties == null)
/*      */     {
/* 1060 */       return false;
/*      */     }
/* 1062 */     if (Config.isShaders() && Shaders.isShadowPass)
/*      */     {
/* 1064 */       return false;
/*      */     }
/* 1066 */     if (p_renderCustomArmorEffect_1_ == null)
/*      */     {
/* 1068 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1072 */     int[][] aint = getEnchantmentIdLevels(p_renderCustomArmorEffect_1_);
/*      */     
/* 1074 */     if (aint.length <= 0)
/*      */     {
/* 1076 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1080 */     Set<Integer> set = null;
/* 1081 */     boolean flag = false;
/* 1082 */     TextureManager texturemanager = Config.getTextureManager();
/*      */     
/* 1084 */     for (int i = 0; i < aint.length; i++) {
/*      */       
/* 1086 */       int j = aint[i][0];
/*      */       
/* 1088 */       if (j >= 0 && j < enchantmentProperties.length) {
/*      */         
/* 1090 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*      */         
/* 1092 */         if (acustomitemproperties != null)
/*      */         {
/* 1094 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/*      */             
/* 1096 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*      */             
/* 1098 */             if (set == null)
/*      */             {
/* 1100 */               set = new HashSet();
/*      */             }
/*      */             
/* 1103 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, p_renderCustomArmorEffect_1_, aint) && customitemproperties.textureLocation != null) {
/*      */               
/* 1105 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/* 1106 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*      */               
/* 1108 */               if (!flag) {
/*      */                 
/* 1110 */                 flag = true;
/*      */                 
/* 1112 */                 if (Config.isShaders())
/*      */                 {
/* 1114 */                   ShadersRender.renderEnchantedGlintBegin();
/*      */                 }
/*      */                 
/* 1117 */                 GlStateManager.enableBlend();
/* 1118 */                 GlStateManager.depthFunc(514);
/* 1119 */                 GlStateManager.depthMask(false);
/*      */               } 
/*      */               
/* 1122 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/* 1123 */               GlStateManager.disableLighting();
/* 1124 */               GlStateManager.matrixMode(5890);
/* 1125 */               GlStateManager.loadIdentity();
/* 1126 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/* 1127 */               float f1 = f / 8.0F;
/* 1128 */               GlStateManager.scale(f1, f1 / 2.0F, f1);
/* 1129 */               float f2 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/* 1130 */               GlStateManager.translate(0.0F, f2, 0.0F);
/* 1131 */               GlStateManager.matrixMode(5888);
/* 1132 */               p_renderCustomArmorEffect_2_.render((Entity)p_renderCustomArmorEffect_0_, p_renderCustomArmorEffect_3_, p_renderCustomArmorEffect_4_, p_renderCustomArmorEffect_6_, p_renderCustomArmorEffect_7_, p_renderCustomArmorEffect_8_, p_renderCustomArmorEffect_9_);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1139 */     if (flag) {
/*      */       
/* 1141 */       GlStateManager.enableAlpha();
/* 1142 */       GlStateManager.enableBlend();
/* 1143 */       GlStateManager.blendFunc(770, 771);
/* 1144 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1145 */       GlStateManager.matrixMode(5890);
/* 1146 */       GlStateManager.loadIdentity();
/* 1147 */       GlStateManager.matrixMode(5888);
/* 1148 */       GlStateManager.enableLighting();
/* 1149 */       GlStateManager.depthMask(true);
/* 1150 */       GlStateManager.depthFunc(515);
/* 1151 */       GlStateManager.disableBlend();
/*      */       
/* 1153 */       if (Config.isShaders())
/*      */       {
/* 1155 */         ShadersRender.renderEnchantedGlintEnd();
/*      */       }
/*      */     } 
/*      */     
/* 1159 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUseGlint() {
/* 1166 */     return useGlint;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setRenderOffHand(boolean p_setRenderOffHand_0_) {
/* 1171 */     renderOffHand = p_setRenderOffHand_0_;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
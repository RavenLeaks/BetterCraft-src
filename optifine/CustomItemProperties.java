/*      */ package optifine;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.BlockPart;
/*      */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*      */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*      */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*      */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*      */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*      */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*      */ import net.minecraft.client.renderer.block.model.ModelManager;
/*      */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*      */ import net.minecraft.client.renderer.block.model.ModelRotation;
/*      */ import net.minecraft.client.renderer.block.model.SimpleBakedModel;
/*      */ import net.minecraft.client.renderer.texture.ITextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class CustomItemProperties
/*      */ {
/*   39 */   public String name = null;
/*   40 */   public String basePath = null;
/*   41 */   public int type = 1;
/*   42 */   public int[] items = null;
/*   43 */   public String texture = null;
/*   44 */   public Map<String, String> mapTextures = null;
/*   45 */   public String model = null;
/*   46 */   public Map<String, String> mapModels = null;
/*   47 */   public RangeListInt damage = null;
/*      */   public boolean damagePercent = false;
/*   49 */   public int damageMask = 0;
/*   50 */   public RangeListInt stackSize = null;
/*   51 */   public RangeListInt enchantmentIds = null;
/*   52 */   public RangeListInt enchantmentLevels = null;
/*   53 */   public NbtTagValue[] nbtTagValues = null;
/*   54 */   public int hand = 0;
/*   55 */   public int blend = 1;
/*   56 */   public float speed = 0.0F;
/*   57 */   public float rotation = 0.0F;
/*   58 */   public int layer = 0;
/*   59 */   public float duration = 1.0F;
/*   60 */   public int weight = 0;
/*   61 */   public ResourceLocation textureLocation = null;
/*   62 */   public Map mapTextureLocations = null;
/*   63 */   public TextureAtlasSprite sprite = null;
/*   64 */   public Map mapSprites = null;
/*   65 */   public IBakedModel bakedModelTexture = null;
/*   66 */   public Map<String, IBakedModel> mapBakedModelsTexture = null;
/*   67 */   public IBakedModel bakedModelFull = null;
/*   68 */   public Map<String, IBakedModel> mapBakedModelsFull = null;
/*   69 */   private int textureWidth = 0;
/*   70 */   private int textureHeight = 0;
/*      */   
/*      */   public static final int TYPE_UNKNOWN = 0;
/*      */   public static final int TYPE_ITEM = 1;
/*      */   public static final int TYPE_ENCHANTMENT = 2;
/*      */   public static final int TYPE_ARMOR = 3;
/*      */   public static final int TYPE_ELYTRA = 4;
/*      */   public static final int HAND_ANY = 0;
/*      */   public static final int HAND_MAIN = 1;
/*      */   public static final int HAND_OFF = 2;
/*      */   public static final String INVENTORY = "inventory";
/*      */   
/*      */   public CustomItemProperties(Properties p_i33_1_, String p_i33_2_) {
/*   83 */     this.name = parseName(p_i33_2_);
/*   84 */     this.basePath = parseBasePath(p_i33_2_);
/*   85 */     this.type = parseType(p_i33_1_.getProperty("type"));
/*   86 */     this.items = parseItems(p_i33_1_.getProperty("items"), p_i33_1_.getProperty("matchItems"));
/*   87 */     this.mapModels = parseModels(p_i33_1_, this.basePath);
/*   88 */     this.model = parseModel(p_i33_1_.getProperty("model"), p_i33_2_, this.basePath, this.type, this.mapModels);
/*   89 */     this.mapTextures = parseTextures(p_i33_1_, this.basePath);
/*   90 */     boolean flag = (this.mapModels == null && this.model == null);
/*   91 */     this.texture = parseTexture(p_i33_1_.getProperty("texture"), p_i33_1_.getProperty("tile"), p_i33_1_.getProperty("source"), p_i33_2_, this.basePath, this.type, this.mapTextures, flag);
/*   92 */     String s = p_i33_1_.getProperty("damage");
/*      */     
/*   94 */     if (s != null) {
/*      */       
/*   96 */       this.damagePercent = s.contains("%");
/*   97 */       s = s.replace("%", "");
/*   98 */       this.damage = parseRangeListInt(s);
/*   99 */       this.damageMask = parseInt(p_i33_1_.getProperty("damageMask"), 0);
/*      */     } 
/*      */     
/*  102 */     this.stackSize = parseRangeListInt(p_i33_1_.getProperty("stackSize"));
/*  103 */     this.enchantmentIds = parseRangeListInt(p_i33_1_.getProperty("enchantmentIDs"), new ParserEnchantmentId());
/*  104 */     this.enchantmentLevels = parseRangeListInt(p_i33_1_.getProperty("enchantmentLevels"));
/*  105 */     this.nbtTagValues = parseNbtTagValues(p_i33_1_);
/*  106 */     this.hand = parseHand(p_i33_1_.getProperty("hand"));
/*  107 */     this.blend = Blender.parseBlend(p_i33_1_.getProperty("blend"));
/*  108 */     this.speed = parseFloat(p_i33_1_.getProperty("speed"), 0.0F);
/*  109 */     this.rotation = parseFloat(p_i33_1_.getProperty("rotation"), 0.0F);
/*  110 */     this.layer = parseInt(p_i33_1_.getProperty("layer"), 0);
/*  111 */     this.weight = parseInt(p_i33_1_.getProperty("weight"), 0);
/*  112 */     this.duration = parseFloat(p_i33_1_.getProperty("duration"), 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseName(String p_parseName_0_) {
/*  117 */     String s = p_parseName_0_;
/*  118 */     int i = p_parseName_0_.lastIndexOf('/');
/*      */     
/*  120 */     if (i >= 0)
/*      */     {
/*  122 */       s = p_parseName_0_.substring(i + 1);
/*      */     }
/*      */     
/*  125 */     int j = s.lastIndexOf('.');
/*      */     
/*  127 */     if (j >= 0)
/*      */     {
/*  129 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  132 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String p_parseBasePath_0_) {
/*  137 */     int i = p_parseBasePath_0_.lastIndexOf('/');
/*  138 */     return (i < 0) ? "" : p_parseBasePath_0_.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseType(String p_parseType_1_) {
/*  143 */     if (p_parseType_1_ == null)
/*      */     {
/*  145 */       return 1;
/*      */     }
/*  147 */     if (p_parseType_1_.equals("item"))
/*      */     {
/*  149 */       return 1;
/*      */     }
/*  151 */     if (p_parseType_1_.equals("enchantment"))
/*      */     {
/*  153 */       return 2;
/*      */     }
/*  155 */     if (p_parseType_1_.equals("armor"))
/*      */     {
/*  157 */       return 3;
/*      */     }
/*  159 */     if (p_parseType_1_.equals("elytra"))
/*      */     {
/*  161 */       return 4;
/*      */     }
/*      */ 
/*      */     
/*  165 */     Config.warn("Unknown method: " + p_parseType_1_);
/*  166 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] parseItems(String p_parseItems_1_, String p_parseItems_2_) {
/*  172 */     if (p_parseItems_1_ == null)
/*      */     {
/*  174 */       p_parseItems_1_ = p_parseItems_2_;
/*      */     }
/*      */     
/*  177 */     if (p_parseItems_1_ == null)
/*      */     {
/*  179 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  183 */     p_parseItems_1_ = p_parseItems_1_.trim();
/*  184 */     Set<Integer> set = new TreeSet();
/*  185 */     String[] astring = Config.tokenize(p_parseItems_1_, " ");
/*      */ 
/*      */     
/*  188 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  190 */       String s = astring[i];
/*  191 */       int j = Config.parseInt(s, -1);
/*      */       
/*  193 */       if (j >= 0) {
/*      */         
/*  195 */         set.add(new Integer(j));
/*      */         
/*      */         continue;
/*      */       } 
/*  199 */       if (s.contains("-")) {
/*      */         
/*  201 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  203 */         if (astring1.length == 2) {
/*      */           
/*  205 */           int k = Config.parseInt(astring1[0], -1);
/*  206 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  208 */           if (k >= 0 && l >= 0) {
/*      */             
/*  210 */             int i1 = Math.min(k, l);
/*  211 */             int j1 = Math.max(k, l);
/*  212 */             int k1 = i1;
/*      */ 
/*      */ 
/*      */             
/*  216 */             while (k1 <= j1) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  221 */               set.add(new Integer(k1));
/*  222 */               k1++;
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  228 */       Item item = Item.getByNameOrId(s);
/*      */       
/*  230 */       if (item == null) {
/*      */         
/*  232 */         Config.warn("Item not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  236 */         int i2 = Item.getIdFromItem(item);
/*      */         
/*  238 */         if (i2 <= 0) {
/*      */           
/*  240 */           Config.warn("Item not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/*  244 */           set.add(new Integer(i2));
/*      */         } 
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  250 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  251 */     int[] aint = new int[ainteger.length];
/*      */     
/*  253 */     for (int l1 = 0; l1 < aint.length; l1++)
/*      */     {
/*  255 */       aint[l1] = ainteger[l1].intValue();
/*      */     }
/*      */     
/*  258 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseTexture(String p_parseTexture_0_, String p_parseTexture_1_, String p_parseTexture_2_, String p_parseTexture_3_, String p_parseTexture_4_, int p_parseTexture_5_, Map<String, String> p_parseTexture_6_, boolean p_parseTexture_7_) {
/*  264 */     if (p_parseTexture_0_ == null)
/*      */     {
/*  266 */       p_parseTexture_0_ = p_parseTexture_1_;
/*      */     }
/*      */     
/*  269 */     if (p_parseTexture_0_ == null)
/*      */     {
/*  271 */       p_parseTexture_0_ = p_parseTexture_2_;
/*      */     }
/*      */     
/*  274 */     if (p_parseTexture_0_ != null) {
/*      */       
/*  276 */       String s2 = ".png";
/*      */       
/*  278 */       if (p_parseTexture_0_.endsWith(s2))
/*      */       {
/*  280 */         p_parseTexture_0_ = p_parseTexture_0_.substring(0, p_parseTexture_0_.length() - s2.length());
/*      */       }
/*      */       
/*  283 */       p_parseTexture_0_ = fixTextureName(p_parseTexture_0_, p_parseTexture_4_);
/*  284 */       return p_parseTexture_0_;
/*      */     } 
/*  286 */     if (p_parseTexture_5_ == 3)
/*      */     {
/*  288 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  292 */     if (p_parseTexture_6_ != null) {
/*      */       
/*  294 */       String s = p_parseTexture_6_.get("texture.bow_standby");
/*      */       
/*  296 */       if (s != null)
/*      */       {
/*  298 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  302 */     if (!p_parseTexture_7_)
/*      */     {
/*  304 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  308 */     String s1 = p_parseTexture_3_;
/*  309 */     int i = p_parseTexture_3_.lastIndexOf('/');
/*      */     
/*  311 */     if (i >= 0)
/*      */     {
/*  313 */       s1 = p_parseTexture_3_.substring(i + 1);
/*      */     }
/*      */     
/*  316 */     int j = s1.lastIndexOf('.');
/*      */     
/*  318 */     if (j >= 0)
/*      */     {
/*  320 */       s1 = s1.substring(0, j);
/*      */     }
/*      */     
/*  323 */     s1 = fixTextureName(s1, p_parseTexture_4_);
/*  324 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseTextures(Properties p_parseTextures_0_, String p_parseTextures_1_) {
/*  331 */     String s = "texture.";
/*  332 */     Map map = getMatchingProperties(p_parseTextures_0_, s);
/*      */     
/*  334 */     if (map.size() <= 0)
/*      */     {
/*  336 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  340 */     Set set = map.keySet();
/*  341 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  343 */     for (Object s1 : set) {
/*      */       
/*  345 */       String s2 = (String)map.get(s1);
/*  346 */       s2 = fixTextureName(s2, p_parseTextures_1_);
/*  347 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  350 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixTextureName(String p_fixTextureName_0_, String p_fixTextureName_1_) {
/*  356 */     p_fixTextureName_0_ = TextureUtils.fixResourcePath(p_fixTextureName_0_, p_fixTextureName_1_);
/*      */     
/*  358 */     if (!p_fixTextureName_0_.startsWith(p_fixTextureName_1_) && !p_fixTextureName_0_.startsWith("textures/") && !p_fixTextureName_0_.startsWith("mcpatcher/"))
/*      */     {
/*  360 */       p_fixTextureName_0_ = String.valueOf(p_fixTextureName_1_) + "/" + p_fixTextureName_0_;
/*      */     }
/*      */     
/*  363 */     if (p_fixTextureName_0_.endsWith(".png"))
/*      */     {
/*  365 */       p_fixTextureName_0_ = p_fixTextureName_0_.substring(0, p_fixTextureName_0_.length() - 4);
/*      */     }
/*      */     
/*  368 */     if (p_fixTextureName_0_.startsWith("/"))
/*      */     {
/*  370 */       p_fixTextureName_0_ = p_fixTextureName_0_.substring(1);
/*      */     }
/*      */     
/*  373 */     return p_fixTextureName_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseModel(String p_parseModel_0_, String p_parseModel_1_, String p_parseModel_2_, int p_parseModel_3_, Map<String, String> p_parseModel_4_) {
/*  378 */     if (p_parseModel_0_ != null) {
/*      */       
/*  380 */       String s1 = ".json";
/*      */       
/*  382 */       if (p_parseModel_0_.endsWith(s1))
/*      */       {
/*  384 */         p_parseModel_0_ = p_parseModel_0_.substring(0, p_parseModel_0_.length() - s1.length());
/*      */       }
/*      */       
/*  387 */       p_parseModel_0_ = fixModelName(p_parseModel_0_, p_parseModel_2_);
/*  388 */       return p_parseModel_0_;
/*      */     } 
/*  390 */     if (p_parseModel_3_ == 3)
/*      */     {
/*  392 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  396 */     if (p_parseModel_4_ != null) {
/*      */       
/*  398 */       String s = p_parseModel_4_.get("model.bow_standby");
/*      */       
/*  400 */       if (s != null)
/*      */       {
/*  402 */         return s;
/*      */       }
/*      */     } 
/*      */     
/*  406 */     return p_parseModel_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map parseModels(Properties p_parseModels_0_, String p_parseModels_1_) {
/*  412 */     String s = "model.";
/*  413 */     Map map = getMatchingProperties(p_parseModels_0_, s);
/*      */     
/*  415 */     if (map.size() <= 0)
/*      */     {
/*  417 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  421 */     Set set = map.keySet();
/*  422 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*      */     
/*  424 */     for (Object s1 : set) {
/*      */       
/*  426 */       String s2 = (String)map.get(s1);
/*  427 */       s2 = fixModelName(s2, p_parseModels_1_);
/*  428 */       map1.put(s1, s2);
/*      */     } 
/*      */     
/*  431 */     return map1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixModelName(String p_fixModelName_0_, String p_fixModelName_1_) {
/*  437 */     p_fixModelName_0_ = TextureUtils.fixResourcePath(p_fixModelName_0_, p_fixModelName_1_);
/*  438 */     boolean flag = !(!p_fixModelName_0_.startsWith("block/") && !p_fixModelName_0_.startsWith("item/"));
/*      */     
/*  440 */     if (!p_fixModelName_0_.startsWith(p_fixModelName_1_) && !flag && !p_fixModelName_0_.startsWith("mcpatcher/"))
/*      */     {
/*  442 */       p_fixModelName_0_ = String.valueOf(p_fixModelName_1_) + "/" + p_fixModelName_0_;
/*      */     }
/*      */     
/*  445 */     String s = ".json";
/*      */     
/*  447 */     if (p_fixModelName_0_.endsWith(s))
/*      */     {
/*  449 */       p_fixModelName_0_ = p_fixModelName_0_.substring(0, p_fixModelName_0_.length() - s.length());
/*      */     }
/*      */     
/*  452 */     if (p_fixModelName_0_.startsWith("/"))
/*      */     {
/*  454 */       p_fixModelName_0_ = p_fixModelName_0_.substring(1);
/*      */     }
/*      */     
/*  457 */     return p_fixModelName_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseInt(String p_parseInt_1_, int p_parseInt_2_) {
/*  462 */     if (p_parseInt_1_ == null)
/*      */     {
/*  464 */       return p_parseInt_2_;
/*      */     }
/*      */ 
/*      */     
/*  468 */     p_parseInt_1_ = p_parseInt_1_.trim();
/*  469 */     int i = Config.parseInt(p_parseInt_1_, -2147483648);
/*      */     
/*  471 */     if (i == Integer.MIN_VALUE) {
/*      */       
/*  473 */       Config.warn("Invalid integer: " + p_parseInt_1_);
/*  474 */       return p_parseInt_2_;
/*      */     } 
/*      */ 
/*      */     
/*  478 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String p_parseFloat_1_, float p_parseFloat_2_) {
/*  485 */     if (p_parseFloat_1_ == null)
/*      */     {
/*  487 */       return p_parseFloat_2_;
/*      */     }
/*      */ 
/*      */     
/*  491 */     p_parseFloat_1_ = p_parseFloat_1_.trim();
/*  492 */     float f = Config.parseFloat(p_parseFloat_1_, Float.MIN_VALUE);
/*      */     
/*  494 */     if (f == Float.MIN_VALUE) {
/*      */       
/*  496 */       Config.warn("Invalid float: " + p_parseFloat_1_);
/*  497 */       return p_parseFloat_2_;
/*      */     } 
/*      */ 
/*      */     
/*  501 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String p_parseRangeListInt_1_) {
/*  508 */     return parseRangeListInt(p_parseRangeListInt_1_, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private RangeListInt parseRangeListInt(String p_parseRangeListInt_1_, IParserInt p_parseRangeListInt_2_) {
/*  513 */     if (p_parseRangeListInt_1_ == null)
/*      */     {
/*  515 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  519 */     String[] astring = Config.tokenize(p_parseRangeListInt_1_, " ");
/*  520 */     RangeListInt rangelistint = new RangeListInt();
/*      */     
/*  522 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  524 */       String s = astring[i];
/*      */       
/*  526 */       if (p_parseRangeListInt_2_ != null) {
/*      */         
/*  528 */         int j = p_parseRangeListInt_2_.parse(s, -2147483648);
/*      */         
/*  530 */         if (j != Integer.MIN_VALUE) {
/*      */           
/*  532 */           rangelistint.addRange(new RangeInt(j, j));
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*  537 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  539 */       if (rangeint == null) {
/*      */         
/*  541 */         Config.warn("Invalid range list: " + p_parseRangeListInt_1_);
/*  542 */         return null;
/*      */       } 
/*      */       
/*  545 */       rangelistint.addRange(rangeint);
/*      */       continue;
/*      */     } 
/*  548 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String p_parseRangeInt_1_) {
/*  554 */     if (p_parseRangeInt_1_ == null)
/*      */     {
/*  556 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  560 */     p_parseRangeInt_1_ = p_parseRangeInt_1_.trim();
/*  561 */     int i = p_parseRangeInt_1_.length() - p_parseRangeInt_1_.replace("-", "").length();
/*      */     
/*  563 */     if (i > 1) {
/*      */       
/*  565 */       Config.warn("Invalid range: " + p_parseRangeInt_1_);
/*  566 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  570 */     String[] astring = Config.tokenize(p_parseRangeInt_1_, "- ");
/*  571 */     int[] aint = new int[astring.length];
/*      */     
/*  573 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  575 */       String s = astring[j];
/*  576 */       int k = Config.parseInt(s, -1);
/*      */       
/*  578 */       if (k < 0) {
/*      */         
/*  580 */         Config.warn("Invalid range: " + p_parseRangeInt_1_);
/*  581 */         return null;
/*      */       } 
/*      */       
/*  584 */       aint[j] = k;
/*      */     } 
/*      */     
/*  587 */     if (aint.length == 1) {
/*      */       
/*  589 */       int i1 = aint[0];
/*      */       
/*  591 */       if (p_parseRangeInt_1_.startsWith("-"))
/*      */       {
/*  593 */         return new RangeInt(0, i1);
/*      */       }
/*  595 */       if (p_parseRangeInt_1_.endsWith("-"))
/*      */       {
/*  597 */         return new RangeInt(i1, 65535);
/*      */       }
/*      */ 
/*      */       
/*  601 */       return new RangeInt(i1, i1);
/*      */     } 
/*      */     
/*  604 */     if (aint.length == 2) {
/*      */       
/*  606 */       int l = Math.min(aint[0], aint[1]);
/*  607 */       int j1 = Math.max(aint[0], aint[1]);
/*  608 */       return new RangeInt(l, j1);
/*      */     } 
/*      */ 
/*      */     
/*  612 */     Config.warn("Invalid range: " + p_parseRangeInt_1_);
/*  613 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NbtTagValue[] parseNbtTagValues(Properties p_parseNbtTagValues_1_) {
/*  621 */     String s = "nbt.";
/*  622 */     Map map = getMatchingProperties(p_parseNbtTagValues_1_, s);
/*      */     
/*  624 */     if (map.size() <= 0)
/*      */     {
/*  626 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  630 */     List<NbtTagValue> list = new ArrayList();
/*      */     
/*  632 */     for (Object s1 : map.keySet()) {
/*      */       
/*  634 */       String s2 = (String)map.get(s1);
/*  635 */       String s3 = ((String)s1).substring(s.length());
/*  636 */       NbtTagValue nbttagvalue = new NbtTagValue(s3, s2);
/*  637 */       list.add(nbttagvalue);
/*      */     } 
/*      */     
/*  640 */     NbtTagValue[] anbttagvalue = list.<NbtTagValue>toArray(new NbtTagValue[list.size()]);
/*  641 */     return anbttagvalue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map getMatchingProperties(Properties p_getMatchingProperties_0_, String p_getMatchingProperties_1_) {
/*  647 */     Map<Object, Object> map = new LinkedHashMap<>();
/*      */     
/*  649 */     for (Object s : p_getMatchingProperties_0_.keySet()) {
/*      */       
/*  651 */       String s1 = p_getMatchingProperties_0_.getProperty((String)s);
/*      */       
/*  653 */       if (((String)s).startsWith(p_getMatchingProperties_1_))
/*      */       {
/*  655 */         map.put(s, s1);
/*      */       }
/*      */     } 
/*      */     
/*  659 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseHand(String p_parseHand_1_) {
/*  664 */     if (p_parseHand_1_ == null)
/*      */     {
/*  666 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  670 */     p_parseHand_1_ = p_parseHand_1_.toLowerCase();
/*      */     
/*  672 */     if (p_parseHand_1_.equals("any"))
/*      */     {
/*  674 */       return 0;
/*      */     }
/*  676 */     if (p_parseHand_1_.equals("main"))
/*      */     {
/*  678 */       return 1;
/*      */     }
/*  680 */     if (p_parseHand_1_.equals("off"))
/*      */     {
/*  682 */       return 2;
/*      */     }
/*      */ 
/*      */     
/*  686 */     Config.warn("Invalid hand: " + p_parseHand_1_);
/*  687 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String p_isValid_1_) {
/*  694 */     if (this.name != null && this.name.length() > 0) {
/*      */       
/*  696 */       if (this.basePath == null) {
/*      */         
/*  698 */         Config.warn("No base path found: " + p_isValid_1_);
/*  699 */         return false;
/*      */       } 
/*  701 */       if (this.type == 0) {
/*      */         
/*  703 */         Config.warn("No type defined: " + p_isValid_1_);
/*  704 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  708 */       if (this.type == 4 && this.items == null)
/*      */       {
/*  710 */         this.items = new int[] { Item.getIdFromItem(Items.ELYTRA) };
/*      */       }
/*      */       
/*  713 */       if (this.type == 1 || this.type == 3 || this.type == 4) {
/*      */         
/*  715 */         if (this.items == null)
/*      */         {
/*  717 */           this.items = detectItems();
/*      */         }
/*      */         
/*  720 */         if (this.items == null) {
/*      */           
/*  722 */           Config.warn("No items defined: " + p_isValid_1_);
/*  723 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/*  727 */       if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
/*      */         
/*  729 */         Config.warn("No texture or model specified: " + p_isValid_1_);
/*  730 */         return false;
/*      */       } 
/*  732 */       if (this.type == 2 && this.enchantmentIds == null) {
/*      */         
/*  734 */         Config.warn("No enchantmentIDs specified: " + p_isValid_1_);
/*  735 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  739 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  745 */     Config.warn("No name found: " + p_isValid_1_);
/*  746 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectItems() {
/*  752 */     Item item = Item.getByNameOrId(this.name);
/*      */     
/*  754 */     if (item == null)
/*      */     {
/*  756 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  760 */     int i = Item.getIdFromItem(item);
/*  761 */     (new int[1])[0] = i; return (i <= 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap p_updateIcons_1_) {
/*  767 */     if (this.texture != null) {
/*      */       
/*  769 */       this.textureLocation = getTextureLocation(this.texture);
/*      */       
/*  771 */       if (this.type == 1) {
/*      */         
/*  773 */         ResourceLocation resourcelocation = getSpriteLocation(this.textureLocation);
/*  774 */         this.sprite = p_updateIcons_1_.registerSprite(resourcelocation);
/*      */       } 
/*      */     } 
/*      */     
/*  778 */     if (this.mapTextures != null) {
/*      */       
/*  780 */       this.mapTextureLocations = new HashMap<>();
/*  781 */       this.mapSprites = new HashMap<>();
/*      */       
/*  783 */       for (String s : this.mapTextures.keySet()) {
/*      */         
/*  785 */         String s1 = this.mapTextures.get(s);
/*  786 */         ResourceLocation resourcelocation1 = getTextureLocation(s1);
/*  787 */         this.mapTextureLocations.put(s, resourcelocation1);
/*      */         
/*  789 */         if (this.type == 1) {
/*      */           
/*  791 */           ResourceLocation resourcelocation2 = getSpriteLocation(resourcelocation1);
/*  792 */           TextureAtlasSprite textureatlassprite = p_updateIcons_1_.registerSprite(resourcelocation2);
/*  793 */           this.mapSprites.put(s, textureatlassprite);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getTextureLocation(String p_getTextureLocation_1_) {
/*  801 */     if (p_getTextureLocation_1_ == null)
/*      */     {
/*  803 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  807 */     ResourceLocation resourcelocation = new ResourceLocation(p_getTextureLocation_1_);
/*  808 */     String s = resourcelocation.getResourceDomain();
/*  809 */     String s1 = resourcelocation.getResourcePath();
/*      */     
/*  811 */     if (!s1.contains("/"))
/*      */     {
/*  813 */       s1 = "textures/items/" + s1;
/*      */     }
/*      */     
/*  816 */     String s2 = String.valueOf(s1) + ".png";
/*  817 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s2);
/*  818 */     boolean flag = Config.hasResource(resourcelocation1);
/*      */     
/*  820 */     if (!flag)
/*      */     {
/*  822 */       Config.warn("File not found: " + s2);
/*      */     }
/*      */     
/*  825 */     return resourcelocation1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceLocation getSpriteLocation(ResourceLocation p_getSpriteLocation_1_) {
/*  831 */     String s = p_getSpriteLocation_1_.getResourcePath();
/*  832 */     s = StrUtils.removePrefix(s, "textures/");
/*  833 */     s = StrUtils.removeSuffix(s, ".png");
/*  834 */     ResourceLocation resourcelocation = new ResourceLocation(p_getSpriteLocation_1_.getResourceDomain(), s);
/*  835 */     return resourcelocation;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelTexture(TextureMap p_updateModelTexture_1_, ItemModelGenerator p_updateModelTexture_2_) {
/*  840 */     if (this.texture != null || this.mapTextures != null) {
/*      */       
/*  842 */       String[] astring = getModelTextures();
/*  843 */       boolean flag = isUseTint();
/*  844 */       this.bakedModelTexture = makeBakedModel(p_updateModelTexture_1_, p_updateModelTexture_2_, astring, flag);
/*      */       
/*  846 */       if (this.type == 1 && this.mapTextures != null)
/*      */       {
/*  848 */         for (String s : this.mapTextures.keySet()) {
/*      */           
/*  850 */           String s1 = this.mapTextures.get(s);
/*  851 */           String s2 = StrUtils.removePrefix(s, "texture.");
/*      */           
/*  853 */           if (s2.startsWith("bow") || s2.startsWith("fishing_rod")) {
/*      */             
/*  855 */             String[] astring1 = { s1 };
/*  856 */             IBakedModel ibakedmodel = makeBakedModel(p_updateModelTexture_1_, p_updateModelTexture_2_, astring1, flag);
/*      */             
/*  858 */             if (this.mapBakedModelsTexture == null)
/*      */             {
/*  860 */               this.mapBakedModelsTexture = new HashMap<>();
/*      */             }
/*      */             
/*  863 */             String s3 = "item/" + s2;
/*  864 */             this.mapBakedModelsTexture.put(s3, ibakedmodel);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isUseTint() {
/*  873 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel makeBakedModel(TextureMap p_makeBakedModel_0_, ItemModelGenerator p_makeBakedModel_1_, String[] p_makeBakedModel_2_, boolean p_makeBakedModel_3_) {
/*  878 */     String[] astring = new String[p_makeBakedModel_2_.length];
/*      */     
/*  880 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  882 */       String s = p_makeBakedModel_2_[i];
/*  883 */       astring[i] = StrUtils.removePrefix(s, "textures/");
/*      */     } 
/*      */     
/*  886 */     ModelBlock modelblock = makeModelBlock(astring);
/*  887 */     ModelBlock modelblock1 = p_makeBakedModel_1_.makeItemModel(p_makeBakedModel_0_, modelblock);
/*  888 */     IBakedModel ibakedmodel = bakeModel(p_makeBakedModel_0_, modelblock1, p_makeBakedModel_3_);
/*  889 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] getModelTextures() {
/*  894 */     if (this.type == 1 && this.items.length == 1) {
/*      */       
/*  896 */       Item item = Item.getItemById(this.items[0]);
/*  897 */       boolean flag = !(item != Items.POTIONITEM && item != Items.SPLASH_POTION && item != Items.LINGERING_POTION);
/*      */       
/*  899 */       if (flag && this.damage != null && this.damage.getCountRanges() > 0) {
/*      */         
/*  901 */         RangeInt rangeint = this.damage.getRange(0);
/*  902 */         int i = rangeint.getMin();
/*  903 */         boolean flag1 = ((i & 0x4000) != 0);
/*  904 */         String s5 = getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
/*  905 */         String s6 = null;
/*      */         
/*  907 */         if (flag1) {
/*      */           
/*  909 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
/*      */         }
/*      */         else {
/*      */           
/*  913 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
/*      */         } 
/*      */         
/*  916 */         return new String[] { s5, s6 };
/*      */       } 
/*      */       
/*  919 */       if (item instanceof ItemArmor) {
/*      */         
/*  921 */         ItemArmor itemarmor = (ItemArmor)item;
/*      */         
/*  923 */         if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
/*      */           
/*  925 */           String s = "leather";
/*  926 */           String s1 = "helmet";
/*      */           
/*  928 */           if (itemarmor.armorType == EntityEquipmentSlot.HEAD)
/*      */           {
/*  930 */             s1 = "helmet";
/*      */           }
/*      */           
/*  933 */           if (itemarmor.armorType == EntityEquipmentSlot.CHEST)
/*      */           {
/*  935 */             s1 = "chestplate";
/*      */           }
/*      */           
/*  938 */           if (itemarmor.armorType == EntityEquipmentSlot.LEGS)
/*      */           {
/*  940 */             s1 = "leggings";
/*      */           }
/*      */           
/*  943 */           if (itemarmor.armorType == EntityEquipmentSlot.FEET)
/*      */           {
/*  945 */             s1 = "boots";
/*      */           }
/*      */           
/*  948 */           String s2 = String.valueOf(s) + "_" + s1;
/*  949 */           String s3 = getMapTexture(this.mapTextures, "texture." + s2, "items/" + s2);
/*  950 */           String s4 = getMapTexture(this.mapTextures, "texture." + s2 + "_overlay", "items/" + s2 + "_overlay");
/*  951 */           return new String[] { s3, s4 };
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     return new String[] { this.texture };
/*      */   }
/*      */ 
/*      */   
/*      */   private String getMapTexture(Map<String, String> p_getMapTexture_1_, String p_getMapTexture_2_, String p_getMapTexture_3_) {
/*  961 */     if (p_getMapTexture_1_ == null)
/*      */     {
/*  963 */       return p_getMapTexture_3_;
/*      */     }
/*      */ 
/*      */     
/*  967 */     String s = p_getMapTexture_1_.get(p_getMapTexture_2_);
/*  968 */     return (s == null) ? p_getMapTexture_3_ : s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ModelBlock makeModelBlock(String[] p_makeModelBlock_0_) {
/*  974 */     StringBuffer stringbuffer = new StringBuffer();
/*  975 */     stringbuffer.append("{\"parent\": \"builtin/generated\",\"textures\": {");
/*      */     
/*  977 */     for (int i = 0; i < p_makeModelBlock_0_.length; i++) {
/*      */       
/*  979 */       String s = p_makeModelBlock_0_[i];
/*      */       
/*  981 */       if (i > 0)
/*      */       {
/*  983 */         stringbuffer.append(", ");
/*      */       }
/*      */       
/*  986 */       stringbuffer.append("\"layer" + i + "\": \"" + s + "\"");
/*      */     } 
/*      */     
/*  989 */     stringbuffer.append("}}");
/*  990 */     String s1 = stringbuffer.toString();
/*  991 */     ModelBlock modelblock = ModelBlock.deserialize(s1);
/*  992 */     return modelblock;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IBakedModel bakeModel(TextureMap p_bakeModel_0_, ModelBlock p_bakeModel_1_, boolean p_bakeModel_2_) {
/*  997 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  998 */     boolean flag = false;
/*  999 */     String s = p_bakeModel_1_.resolveTextureName("particle");
/* 1000 */     TextureAtlasSprite textureatlassprite = p_bakeModel_0_.getAtlasSprite((new ResourceLocation(s)).toString());
/* 1001 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(p_bakeModel_1_, p_bakeModel_1_.createOverrides())).setTexture(textureatlassprite);
/*      */     
/* 1003 */     for (BlockPart blockpart : p_bakeModel_1_.getElements()) {
/*      */       
/* 1005 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/*      */         
/* 1007 */         BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
/*      */         
/* 1009 */         if (!p_bakeModel_2_)
/*      */         {
/* 1011 */           blockpartface = new BlockPartFace(blockpartface.cullFace, -1, blockpartface.texture, blockpartface.blockFaceUV);
/*      */         }
/*      */         
/* 1014 */         String s1 = p_bakeModel_1_.resolveTextureName(blockpartface.texture);
/* 1015 */         TextureAtlasSprite textureatlassprite1 = p_bakeModel_0_.getAtlasSprite((new ResourceLocation(s1)).toString());
/* 1016 */         BakedQuad bakedquad = makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelrotation, flag);
/*      */         
/* 1018 */         if (blockpartface.cullFace == null) {
/*      */           
/* 1020 */           simplebakedmodel$builder.addGeneralQuad(bakedquad);
/*      */           
/*      */           continue;
/*      */         } 
/* 1024 */         simplebakedmodel$builder.addFaceQuad(modelrotation.rotateFace(blockpartface.cullFace), bakedquad);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1029 */     return simplebakedmodel$builder.makeBakedModel();
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad makeBakedQuad(BlockPart p_makeBakedQuad_0_, BlockPartFace p_makeBakedQuad_1_, TextureAtlasSprite p_makeBakedQuad_2_, EnumFacing p_makeBakedQuad_3_, ModelRotation p_makeBakedQuad_4_, boolean p_makeBakedQuad_5_) {
/* 1034 */     FaceBakery facebakery = new FaceBakery();
/* 1035 */     return facebakery.makeBakedQuad(p_makeBakedQuad_0_.positionFrom, p_makeBakedQuad_0_.positionTo, p_makeBakedQuad_1_, p_makeBakedQuad_2_, p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_0_.partRotation, p_makeBakedQuad_5_, p_makeBakedQuad_0_.shade);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1040 */     return this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], textture: " + this.texture;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureWidth(TextureManager p_getTextureWidth_1_) {
/* 1045 */     if (this.textureWidth <= 0) {
/*      */       
/* 1047 */       if (this.textureLocation != null) {
/*      */         
/* 1049 */         ITextureObject itextureobject = p_getTextureWidth_1_.getTexture(this.textureLocation);
/* 1050 */         int i = itextureobject.getGlTextureId();
/* 1051 */         int j = GlStateManager.getBoundTexture();
/* 1052 */         GlStateManager.bindTexture(i);
/* 1053 */         this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 1054 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1057 */       if (this.textureWidth <= 0)
/*      */       {
/* 1059 */         this.textureWidth = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1063 */     return this.textureWidth;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getTextureHeight(TextureManager p_getTextureHeight_1_) {
/* 1068 */     if (this.textureHeight <= 0) {
/*      */       
/* 1070 */       if (this.textureLocation != null) {
/*      */         
/* 1072 */         ITextureObject itextureobject = p_getTextureHeight_1_.getTexture(this.textureLocation);
/* 1073 */         int i = itextureobject.getGlTextureId();
/* 1074 */         int j = GlStateManager.getBoundTexture();
/* 1075 */         GlStateManager.bindTexture(i);
/* 1076 */         this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 1077 */         GlStateManager.bindTexture(j);
/*      */       } 
/*      */       
/* 1080 */       if (this.textureHeight <= 0)
/*      */       {
/* 1082 */         this.textureHeight = 16;
/*      */       }
/*      */     } 
/*      */     
/* 1086 */     return this.textureHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBakedModel getBakedModel(ResourceLocation p_getBakedModel_1_, boolean p_getBakedModel_2_) {
/*      */     IBakedModel ibakedmodel;
/*      */     Map<String, IBakedModel> map;
/* 1094 */     if (p_getBakedModel_2_) {
/*      */       
/* 1096 */       ibakedmodel = this.bakedModelFull;
/* 1097 */       map = this.mapBakedModelsFull;
/*      */     }
/*      */     else {
/*      */       
/* 1101 */       ibakedmodel = this.bakedModelTexture;
/* 1102 */       map = this.mapBakedModelsTexture;
/*      */     } 
/*      */     
/* 1105 */     if (p_getBakedModel_1_ != null && map != null) {
/*      */       
/* 1107 */       String s = p_getBakedModel_1_.getResourcePath();
/* 1108 */       IBakedModel ibakedmodel1 = map.get(s);
/*      */       
/* 1110 */       if (ibakedmodel1 != null)
/*      */       {
/* 1112 */         return ibakedmodel1;
/*      */       }
/*      */     } 
/*      */     
/* 1116 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadModels(ModelBakery p_loadModels_1_) {
/* 1121 */     if (this.model != null)
/*      */     {
/* 1123 */       loadItemModel(p_loadModels_1_, this.model);
/*      */     }
/*      */     
/* 1126 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1128 */       for (String s : this.mapModels.keySet()) {
/*      */         
/* 1130 */         String s1 = this.mapModels.get(s);
/* 1131 */         String s2 = StrUtils.removePrefix(s, "model.");
/*      */         
/* 1133 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod"))
/*      */         {
/* 1135 */           loadItemModel(p_loadModels_1_, s1);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelsFull() {
/* 1143 */     ModelManager modelmanager = Config.getModelManager();
/* 1144 */     IBakedModel ibakedmodel = modelmanager.getMissingModel();
/*      */     
/* 1146 */     if (this.model != null) {
/*      */       
/* 1148 */       ResourceLocation resourcelocation = getModelLocation(this.model);
/* 1149 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/* 1150 */       this.bakedModelFull = modelmanager.getModel(modelresourcelocation);
/*      */       
/* 1152 */       if (this.bakedModelFull == ibakedmodel) {
/*      */         
/* 1154 */         Config.warn("Custom Items: Model not found " + modelresourcelocation.getResourcePath());
/* 1155 */         this.bakedModelFull = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1159 */     if (this.type == 1 && this.mapModels != null)
/*      */     {
/* 1161 */       for (String s : this.mapModels.keySet()) {
/*      */         
/* 1163 */         String s1 = this.mapModels.get(s);
/* 1164 */         String s2 = StrUtils.removePrefix(s, "model.");
/*      */         
/* 1166 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod")) {
/*      */           
/* 1168 */           ResourceLocation resourcelocation1 = getModelLocation(s1);
/* 1169 */           ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(resourcelocation1, "inventory");
/* 1170 */           IBakedModel ibakedmodel1 = modelmanager.getModel(modelresourcelocation1);
/*      */           
/* 1172 */           if (ibakedmodel1 == ibakedmodel) {
/*      */             
/* 1174 */             Config.warn("Custom Items: Model not found " + modelresourcelocation1.getResourcePath());
/*      */             
/*      */             continue;
/*      */           } 
/* 1178 */           if (this.mapBakedModelsFull == null)
/*      */           {
/* 1180 */             this.mapBakedModelsFull = new HashMap<>();
/*      */           }
/*      */           
/* 1183 */           String s3 = "item/" + s2;
/* 1184 */           this.mapBakedModelsFull.put(s3, ibakedmodel1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadItemModel(ModelBakery p_loadItemModel_0_, String p_loadItemModel_1_) {
/* 1193 */     ResourceLocation resourcelocation = getModelLocation(p_loadItemModel_1_);
/* 1194 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/*      */     
/* 1196 */     if (Reflector.ModelLoader.exists()) {
/*      */       
/*      */       try
/*      */       {
/* 1200 */         Object object = Reflector.ModelLoader_VanillaLoader_INSTANCE.getValue();
/* 1201 */         checkNull(object, "vanillaLoader is null");
/* 1202 */         Object object1 = Reflector.call(object, Reflector.ModelLoader_VanillaLoader_loadModel, new Object[] { modelresourcelocation });
/* 1203 */         checkNull(object1, "iModel is null");
/* 1204 */         Map<ModelResourceLocation, Object> map = (Map)Reflector.getFieldValue(p_loadItemModel_0_, Reflector.ModelLoader_stateModels);
/* 1205 */         checkNull(map, "stateModels is null");
/* 1206 */         map.put(modelresourcelocation, object1);
/* 1207 */         Set set = (Set)Reflector.ModelLoaderRegistry_textures.getValue();
/* 1208 */         checkNull(set, "registryTextures is null");
/* 1209 */         Collection collection = (Collection)Reflector.call(object1, Reflector.IModel_getTextures, new Object[0]);
/* 1210 */         checkNull(collection, "modelTextures is null");
/* 1211 */         set.addAll(collection);
/*      */       }
/* 1213 */       catch (Exception exception)
/*      */       {
/* 1215 */         Config.warn("Error registering model: " + modelresourcelocation + ", " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1220 */       p_loadItemModel_0_.loadItemModel(resourcelocation.toString(), (ResourceLocation)modelresourcelocation, resourcelocation);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkNull(Object p_checkNull_0_, String p_checkNull_1_) throws NullPointerException {
/* 1226 */     if (p_checkNull_0_ == null)
/*      */     {
/* 1228 */       throw new NullPointerException(p_checkNull_1_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceLocation getModelLocation(String p_getModelLocation_0_) {
/* 1234 */     return (Reflector.ModelLoader.exists() && !p_getModelLocation_0_.startsWith("mcpatcher/") && !p_getModelLocation_0_.startsWith("optifine/")) ? new ResourceLocation("models/" + p_getModelLocation_0_) : new ResourceLocation(p_getModelLocation_0_);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomItemProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package optifine;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ 
/*      */ public class ConnectedProperties
/*      */ {
/*   21 */   public String name = null;
/*   22 */   public String basePath = null;
/*   23 */   public MatchBlock[] matchBlocks = null;
/*   24 */   public int[] metadatas = null;
/*   25 */   public String[] matchTiles = null;
/*   26 */   public int method = 0;
/*   27 */   public String[] tiles = null;
/*   28 */   public int connect = 0;
/*   29 */   public int faces = 63;
/*   30 */   public Biome[] biomes = null;
/*   31 */   public int minHeight = 0;
/*   32 */   public int maxHeight = 1024;
/*   33 */   public int renderPass = 0;
/*      */   public boolean innerSeams = false;
/*   35 */   public int[] ctmTileIndexes = null;
/*   36 */   public int width = 0;
/*   37 */   public int height = 0;
/*   38 */   public int[] weights = null;
/*   39 */   public int symmetry = 1;
/*   40 */   public int[] sumWeights = null;
/*   41 */   public int sumAllWeights = 1;
/*   42 */   public TextureAtlasSprite[] matchTileIcons = null;
/*   43 */   public TextureAtlasSprite[] tileIcons = null;
/*   44 */   public MatchBlock[] connectBlocks = null;
/*   45 */   public String[] connectTiles = null;
/*   46 */   public TextureAtlasSprite[] connectTileIcons = null;
/*   47 */   public int tintIndex = -1;
/*   48 */   public IBlockState tintBlockState = Blocks.AIR.getDefaultState();
/*   49 */   public BlockRenderLayer layer = null;
/*      */   
/*      */   public static final int METHOD_NONE = 0;
/*      */   public static final int METHOD_CTM = 1;
/*      */   public static final int METHOD_HORIZONTAL = 2;
/*      */   public static final int METHOD_TOP = 3;
/*      */   public static final int METHOD_RANDOM = 4;
/*      */   public static final int METHOD_REPEAT = 5;
/*      */   public static final int METHOD_VERTICAL = 6;
/*      */   public static final int METHOD_FIXED = 7;
/*      */   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
/*      */   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
/*      */   public static final int METHOD_CTM_COMPACT = 10;
/*      */   public static final int METHOD_OVERLAY = 11;
/*      */   public static final int CONNECT_NONE = 0;
/*      */   public static final int CONNECT_BLOCK = 1;
/*      */   public static final int CONNECT_TILE = 2;
/*      */   public static final int CONNECT_MATERIAL = 3;
/*      */   public static final int CONNECT_UNKNOWN = 128;
/*      */   public static final int FACE_BOTTOM = 1;
/*      */   public static final int FACE_TOP = 2;
/*      */   public static final int FACE_NORTH = 4;
/*      */   public static final int FACE_SOUTH = 8;
/*      */   public static final int FACE_WEST = 16;
/*      */   public static final int FACE_EAST = 32;
/*      */   public static final int FACE_SIDES = 60;
/*      */   public static final int FACE_ALL = 63;
/*      */   public static final int FACE_UNKNOWN = 128;
/*      */   public static final int SYMMETRY_NONE = 1;
/*      */   public static final int SYMMETRY_OPPOSITE = 2;
/*      */   public static final int SYMMETRY_ALL = 6;
/*      */   public static final int SYMMETRY_UNKNOWN = 128;
/*      */   
/*      */   public ConnectedProperties(Properties p_i27_1_, String p_i27_2_) {
/*   83 */     ConnectedParser connectedparser = new ConnectedParser("ConnectedTextures");
/*   84 */     this.name = connectedparser.parseName(p_i27_2_);
/*   85 */     this.basePath = connectedparser.parseBasePath(p_i27_2_);
/*   86 */     this.matchBlocks = connectedparser.parseMatchBlocks(p_i27_1_.getProperty("matchBlocks"));
/*   87 */     this.metadatas = connectedparser.parseIntList(p_i27_1_.getProperty("metadata"));
/*   88 */     this.matchTiles = parseMatchTiles(p_i27_1_.getProperty("matchTiles"));
/*   89 */     this.method = parseMethod(p_i27_1_.getProperty("method"));
/*   90 */     this.tiles = parseTileNames(p_i27_1_.getProperty("tiles"));
/*   91 */     this.connect = parseConnect(p_i27_1_.getProperty("connect"));
/*   92 */     this.faces = parseFaces(p_i27_1_.getProperty("faces"));
/*   93 */     this.biomes = connectedparser.parseBiomes(p_i27_1_.getProperty("biomes"));
/*   94 */     this.minHeight = connectedparser.parseInt(p_i27_1_.getProperty("minHeight"), -1);
/*   95 */     this.maxHeight = connectedparser.parseInt(p_i27_1_.getProperty("maxHeight"), 1024);
/*   96 */     this.renderPass = connectedparser.parseInt(p_i27_1_.getProperty("renderPass"));
/*   97 */     this.innerSeams = ConnectedParser.parseBoolean(p_i27_1_.getProperty("innerSeams"));
/*   98 */     this.ctmTileIndexes = parseCtmTileIndexes(p_i27_1_);
/*   99 */     this.width = connectedparser.parseInt(p_i27_1_.getProperty("width"));
/*  100 */     this.height = connectedparser.parseInt(p_i27_1_.getProperty("height"));
/*  101 */     this.weights = connectedparser.parseIntList(p_i27_1_.getProperty("weights"));
/*  102 */     this.symmetry = parseSymmetry(p_i27_1_.getProperty("symmetry"));
/*  103 */     this.connectBlocks = connectedparser.parseMatchBlocks(p_i27_1_.getProperty("connectBlocks"));
/*  104 */     this.connectTiles = parseMatchTiles(p_i27_1_.getProperty("connectTiles"));
/*  105 */     this.tintIndex = connectedparser.parseInt(p_i27_1_.getProperty("tintIndex"));
/*  106 */     this.tintBlockState = connectedparser.parseBlockState(p_i27_1_.getProperty("tintBlock"), Blocks.AIR.getDefaultState());
/*  107 */     this.layer = connectedparser.parseBlockRenderLayer(p_i27_1_.getProperty("layer"), BlockRenderLayer.CUTOUT_MIPPED);
/*      */   }
/*      */ 
/*      */   
/*      */   private int[] parseCtmTileIndexes(Properties p_parseCtmTileIndexes_1_) {
/*  112 */     if (this.tiles == null)
/*      */     {
/*  114 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  118 */     Map<Integer, Integer> map = new HashMap<>();
/*      */     
/*  120 */     for (Object object : p_parseCtmTileIndexes_1_.keySet()) {
/*      */       
/*  122 */       if (object instanceof String) {
/*      */         
/*  124 */         String s = (String)object;
/*  125 */         String s1 = "ctm.";
/*      */         
/*  127 */         if (s.startsWith(s1)) {
/*      */           
/*  129 */           String s2 = s.substring(s1.length());
/*  130 */           String s3 = p_parseCtmTileIndexes_1_.getProperty(s);
/*      */           
/*  132 */           if (s3 != null) {
/*      */             
/*  134 */             int i = Config.parseInt(s2, -1);
/*      */             
/*  136 */             if (i >= 0 && i <= 46) {
/*      */               
/*  138 */               int j = Config.parseInt(s3, -1);
/*      */               
/*  140 */               if (j >= 0 && j < this.tiles.length) {
/*      */                 
/*  142 */                 map.put(Integer.valueOf(i), Integer.valueOf(j));
/*      */                 
/*      */                 continue;
/*      */               } 
/*  146 */               Config.warn("Invalid CTM tile index: " + s3);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*  151 */             Config.warn("Invalid CTM index: " + s2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  158 */     if (map.isEmpty())
/*      */     {
/*  160 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  164 */     int[] aint = new int[47];
/*      */     
/*  166 */     for (int k = 0; k < aint.length; k++) {
/*      */       
/*  168 */       aint[k] = -1;
/*      */       
/*  170 */       if (map.containsKey(Integer.valueOf(k)))
/*      */       {
/*  172 */         aint[k] = ((Integer)map.get(Integer.valueOf(k))).intValue();
/*      */       }
/*      */     } 
/*      */     
/*  176 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] parseMatchTiles(String p_parseMatchTiles_1_) {
/*  183 */     if (p_parseMatchTiles_1_ == null)
/*      */     {
/*  185 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  189 */     String[] astring = Config.tokenize(p_parseMatchTiles_1_, " ");
/*      */     
/*  191 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  193 */       String s = astring[i];
/*      */       
/*  195 */       if (s.endsWith(".png"))
/*      */       {
/*  197 */         s = s.substring(0, s.length() - 4);
/*      */       }
/*      */       
/*  200 */       s = TextureUtils.fixResourcePath(s, this.basePath);
/*  201 */       astring[i] = s;
/*      */     } 
/*      */     
/*  204 */     return astring;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String parseName(String p_parseName_0_) {
/*  210 */     String s = p_parseName_0_;
/*  211 */     int i = p_parseName_0_.lastIndexOf('/');
/*      */     
/*  213 */     if (i >= 0)
/*      */     {
/*  215 */       s = p_parseName_0_.substring(i + 1);
/*      */     }
/*      */     
/*  218 */     int j = s.lastIndexOf('.');
/*      */     
/*  220 */     if (j >= 0)
/*      */     {
/*  222 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*  225 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parseBasePath(String p_parseBasePath_0_) {
/*  230 */     int i = p_parseBasePath_0_.lastIndexOf('/');
/*  231 */     return (i < 0) ? "" : p_parseBasePath_0_.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] parseTileNames(String p_parseTileNames_1_) {
/*  236 */     if (p_parseTileNames_1_ == null)
/*      */     {
/*  238 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  242 */     List<String> list = new ArrayList();
/*  243 */     String[] astring = Config.tokenize(p_parseTileNames_1_, " ,");
/*      */ 
/*      */     
/*  246 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  248 */       String s = astring[i];
/*      */       
/*  250 */       if (s.contains("-")) {
/*      */         
/*  252 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  254 */         if (astring1.length == 2) {
/*      */           
/*  256 */           int j = Config.parseInt(astring1[0], -1);
/*  257 */           int k = Config.parseInt(astring1[1], -1);
/*      */           
/*  259 */           if (j >= 0 && k >= 0) {
/*      */             
/*  261 */             if (j > k) {
/*      */               
/*  263 */               Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseTileNames_1_);
/*      */             }
/*      */             else {
/*      */               
/*  267 */               int l = j;
/*      */ 
/*      */ 
/*      */               
/*  271 */               while (l <= k) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  276 */                 list.add(String.valueOf(l));
/*  277 */                 l++;
/*      */               } 
/*      */             }  continue;
/*      */           } 
/*      */         } 
/*      */       } 
/*  283 */       list.add(s);
/*      */       continue;
/*      */     } 
/*  286 */     String[] astring2 = list.<String>toArray(new String[list.size()]);
/*      */     
/*  288 */     for (int i1 = 0; i1 < astring2.length; i1++) {
/*      */       
/*  290 */       String s1 = astring2[i1];
/*  291 */       s1 = TextureUtils.fixResourcePath(s1, this.basePath);
/*      */       
/*  293 */       if (!s1.startsWith(this.basePath) && !s1.startsWith("textures/") && !s1.startsWith("mcpatcher/"))
/*      */       {
/*  295 */         s1 = String.valueOf(this.basePath) + "/" + s1;
/*      */       }
/*      */       
/*  298 */       if (s1.endsWith(".png"))
/*      */       {
/*  300 */         s1 = s1.substring(0, s1.length() - 4);
/*      */       }
/*      */       
/*  303 */       String s2 = "textures/blocks/";
/*      */       
/*  305 */       if (s1.startsWith(s2))
/*      */       {
/*  307 */         s1 = s1.substring(s2.length());
/*      */       }
/*      */       
/*  310 */       if (s1.startsWith("/"))
/*      */       {
/*  312 */         s1 = s1.substring(1);
/*      */       }
/*      */       
/*  315 */       astring2[i1] = s1;
/*      */     } 
/*      */     
/*  318 */     return astring2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseSymmetry(String p_parseSymmetry_0_) {
/*  324 */     if (p_parseSymmetry_0_ == null)
/*      */     {
/*  326 */       return 1;
/*      */     }
/*  328 */     if (p_parseSymmetry_0_.equals("opposite"))
/*      */     {
/*  330 */       return 2;
/*      */     }
/*  332 */     if (p_parseSymmetry_0_.equals("all"))
/*      */     {
/*  334 */       return 6;
/*      */     }
/*      */ 
/*      */     
/*  338 */     Config.warn("Unknown symmetry: " + p_parseSymmetry_0_);
/*  339 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFaces(String p_parseFaces_0_) {
/*  345 */     if (p_parseFaces_0_ == null)
/*      */     {
/*  347 */       return 63;
/*      */     }
/*      */ 
/*      */     
/*  351 */     String[] astring = Config.tokenize(p_parseFaces_0_, " ,");
/*  352 */     int i = 0;
/*      */     
/*  354 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  356 */       String s = astring[j];
/*  357 */       int k = parseFace(s);
/*  358 */       i |= k;
/*      */     } 
/*      */     
/*  361 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseFace(String p_parseFace_0_) {
/*  367 */     p_parseFace_0_ = p_parseFace_0_.toLowerCase();
/*      */     
/*  369 */     if (!p_parseFace_0_.equals("bottom") && !p_parseFace_0_.equals("down")) {
/*      */       
/*  371 */       if (!p_parseFace_0_.equals("top") && !p_parseFace_0_.equals("up")) {
/*      */         
/*  373 */         if (p_parseFace_0_.equals("north"))
/*      */         {
/*  375 */           return 4;
/*      */         }
/*  377 */         if (p_parseFace_0_.equals("south"))
/*      */         {
/*  379 */           return 8;
/*      */         }
/*  381 */         if (p_parseFace_0_.equals("east"))
/*      */         {
/*  383 */           return 32;
/*      */         }
/*  385 */         if (p_parseFace_0_.equals("west"))
/*      */         {
/*  387 */           return 16;
/*      */         }
/*  389 */         if (p_parseFace_0_.equals("sides"))
/*      */         {
/*  391 */           return 60;
/*      */         }
/*  393 */         if (p_parseFace_0_.equals("all"))
/*      */         {
/*  395 */           return 63;
/*      */         }
/*      */ 
/*      */         
/*  399 */         Config.warn("Unknown face: " + p_parseFace_0_);
/*  400 */         return 128;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  405 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  410 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseConnect(String p_parseConnect_0_) {
/*  416 */     if (p_parseConnect_0_ == null)
/*      */     {
/*  418 */       return 0;
/*      */     }
/*  420 */     if (p_parseConnect_0_.equals("block"))
/*      */     {
/*  422 */       return 1;
/*      */     }
/*  424 */     if (p_parseConnect_0_.equals("tile"))
/*      */     {
/*  426 */       return 2;
/*      */     }
/*  428 */     if (p_parseConnect_0_.equals("material"))
/*      */     {
/*  430 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  434 */     Config.warn("Unknown connect: " + p_parseConnect_0_);
/*  435 */     return 128;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static IProperty getProperty(String p_getProperty_0_, Collection p_getProperty_1_) {
/*  441 */     for (Object iproperty : p_getProperty_1_) {
/*      */       
/*  443 */       if (p_getProperty_0_.equals(((IProperty)iproperty).getName()))
/*      */       {
/*  445 */         return (IProperty)iproperty;
/*      */       }
/*      */     } 
/*      */     
/*  449 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseMethod(String p_parseMethod_0_) {
/*  454 */     if (p_parseMethod_0_ == null)
/*      */     {
/*  456 */       return 1;
/*      */     }
/*  458 */     if (!p_parseMethod_0_.equals("ctm") && !p_parseMethod_0_.equals("glass")) {
/*      */       
/*  460 */       if (p_parseMethod_0_.equals("ctm_compact"))
/*      */       {
/*  462 */         return 10;
/*      */       }
/*  464 */       if (!p_parseMethod_0_.equals("horizontal") && !p_parseMethod_0_.equals("bookshelf")) {
/*      */         
/*  466 */         if (p_parseMethod_0_.equals("vertical"))
/*      */         {
/*  468 */           return 6;
/*      */         }
/*  470 */         if (p_parseMethod_0_.equals("top"))
/*      */         {
/*  472 */           return 3;
/*      */         }
/*  474 */         if (p_parseMethod_0_.equals("random"))
/*      */         {
/*  476 */           return 4;
/*      */         }
/*  478 */         if (p_parseMethod_0_.equals("repeat"))
/*      */         {
/*  480 */           return 5;
/*      */         }
/*  482 */         if (p_parseMethod_0_.equals("fixed"))
/*      */         {
/*  484 */           return 7;
/*      */         }
/*  486 */         if (!p_parseMethod_0_.equals("horizontal+vertical") && !p_parseMethod_0_.equals("h+v")) {
/*      */           
/*  488 */           if (!p_parseMethod_0_.equals("vertical+horizontal") && !p_parseMethod_0_.equals("v+h")) {
/*      */             
/*  490 */             if (p_parseMethod_0_.equals("overlay"))
/*      */             {
/*  492 */               return 11;
/*      */             }
/*      */ 
/*      */             
/*  496 */             Config.warn("Unknown method: " + p_parseMethod_0_);
/*  497 */             return 0;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  502 */           return 9;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  507 */         return 8;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  512 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  517 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(String p_isValid_1_) {
/*  523 */     if (this.name != null && this.name.length() > 0) {
/*      */       
/*  525 */       if (this.basePath == null) {
/*      */         
/*  527 */         Config.warn("No base path found: " + p_isValid_1_);
/*  528 */         return false;
/*      */       } 
/*      */ 
/*      */       
/*  532 */       if (this.matchBlocks == null)
/*      */       {
/*  534 */         this.matchBlocks = detectMatchBlocks();
/*      */       }
/*      */       
/*  537 */       if (this.matchTiles == null && this.matchBlocks == null)
/*      */       {
/*  539 */         this.matchTiles = detectMatchTiles();
/*      */       }
/*      */       
/*  542 */       if (this.matchBlocks == null && this.matchTiles == null) {
/*      */         
/*  544 */         Config.warn("No matchBlocks or matchTiles specified: " + p_isValid_1_);
/*  545 */         return false;
/*      */       } 
/*  547 */       if (this.method == 0) {
/*      */         
/*  549 */         Config.warn("No method: " + p_isValid_1_);
/*  550 */         return false;
/*      */       } 
/*  552 */       if (this.tiles != null && this.tiles.length > 0) {
/*      */         
/*  554 */         if (this.connect == 0)
/*      */         {
/*  556 */           this.connect = detectConnect();
/*      */         }
/*      */         
/*  559 */         if (this.connect == 128) {
/*      */           
/*  561 */           Config.warn("Invalid connect in: " + p_isValid_1_);
/*  562 */           return false;
/*      */         } 
/*  564 */         if (this.renderPass > 0) {
/*      */           
/*  566 */           Config.warn("Render pass not supported: " + this.renderPass);
/*  567 */           return false;
/*      */         } 
/*  569 */         if ((this.faces & 0x80) != 0) {
/*      */           
/*  571 */           Config.warn("Invalid faces in: " + p_isValid_1_);
/*  572 */           return false;
/*      */         } 
/*  574 */         if ((this.symmetry & 0x80) != 0) {
/*      */           
/*  576 */           Config.warn("Invalid symmetry in: " + p_isValid_1_);
/*  577 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  581 */         switch (this.method) {
/*      */           
/*      */           case 1:
/*  584 */             return isValidCtm(p_isValid_1_);
/*      */           
/*      */           case 2:
/*  587 */             return isValidHorizontal(p_isValid_1_);
/*      */           
/*      */           case 3:
/*  590 */             return isValidTop(p_isValid_1_);
/*      */           
/*      */           case 4:
/*  593 */             return isValidRandom(p_isValid_1_);
/*      */           
/*      */           case 5:
/*  596 */             return isValidRepeat(p_isValid_1_);
/*      */           
/*      */           case 6:
/*  599 */             return isValidVertical(p_isValid_1_);
/*      */           
/*      */           case 7:
/*  602 */             return isValidFixed(p_isValid_1_);
/*      */           
/*      */           case 8:
/*  605 */             return isValidHorizontalVertical(p_isValid_1_);
/*      */           
/*      */           case 9:
/*  608 */             return isValidVerticalHorizontal(p_isValid_1_);
/*      */           
/*      */           case 10:
/*  611 */             return isValidCtmCompact(p_isValid_1_);
/*      */           
/*      */           case 11:
/*  614 */             return isValidOverlay(p_isValid_1_);
/*      */         } 
/*      */         
/*  617 */         Config.warn("Unknown method: " + p_isValid_1_);
/*  618 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  624 */       Config.warn("No tiles specified: " + p_isValid_1_);
/*  625 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     Config.warn("No name found: " + p_isValid_1_);
/*  632 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int detectConnect() {
/*  638 */     if (this.matchBlocks != null)
/*      */     {
/*  640 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*  644 */     return (this.matchTiles != null) ? 2 : 128;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private MatchBlock[] detectMatchBlocks() {
/*  650 */     int[] aint = detectMatchBlockIds();
/*      */     
/*  652 */     if (aint == null)
/*      */     {
/*  654 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  658 */     MatchBlock[] amatchblock = new MatchBlock[aint.length];
/*      */     
/*  660 */     for (int i = 0; i < amatchblock.length; i++)
/*      */     {
/*  662 */       amatchblock[i] = new MatchBlock(aint[i]);
/*      */     }
/*      */     
/*  665 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] detectMatchBlockIds() {
/*  671 */     if (!this.name.startsWith("block"))
/*      */     {
/*  673 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  677 */     int i = "block".length();
/*      */     
/*      */     int j;
/*  680 */     for (j = i; j < this.name.length(); j++) {
/*      */       
/*  682 */       char c0 = this.name.charAt(j);
/*      */       
/*  684 */       if (c0 < '0' || c0 > '9') {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  690 */     if (j == i)
/*      */     {
/*  692 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  696 */     String s = this.name.substring(i, j);
/*  697 */     int k = Config.parseInt(s, -1);
/*  698 */     (new int[1])[0] = k; return (k < 0) ? null : new int[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] detectMatchTiles() {
/*  705 */     TextureAtlasSprite textureatlassprite = getIcon(this.name);
/*  706 */     (new String[1])[0] = this.name; return (textureatlassprite == null) ? null : new String[1];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getIcon(String p_getIcon_0_) {
/*  711 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  712 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(p_getIcon_0_);
/*      */     
/*  714 */     if (textureatlassprite != null)
/*      */     {
/*  716 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/*  720 */     textureatlassprite = texturemap.getSpriteSafe("blocks/" + p_getIcon_0_);
/*  721 */     return textureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtm(String p_isValidCtm_1_) {
/*  727 */     if (this.tiles == null)
/*      */     {
/*  729 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/*      */     }
/*      */     
/*  732 */     if (this.tiles.length < 47) {
/*      */       
/*  734 */       Config.warn("Invalid tiles, must be at least 47: " + p_isValidCtm_1_);
/*  735 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  739 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCtmCompact(String p_isValidCtmCompact_1_) {
/*  745 */     if (this.tiles == null)
/*      */     {
/*  747 */       this.tiles = parseTileNames("0-4");
/*      */     }
/*      */     
/*  750 */     if (this.tiles.length < 5) {
/*      */       
/*  752 */       Config.warn("Invalid tiles, must be at least 5: " + p_isValidCtmCompact_1_);
/*  753 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  757 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidOverlay(String p_isValidOverlay_1_) {
/*  763 */     if (this.tiles == null)
/*      */     {
/*  765 */       this.tiles = parseTileNames("0-16");
/*      */     }
/*      */     
/*  768 */     if (this.tiles.length < 17) {
/*      */       
/*  770 */       Config.warn("Invalid tiles, must be at least 17: " + p_isValidOverlay_1_);
/*  771 */       return false;
/*      */     } 
/*  773 */     if (this.layer != null && this.layer != BlockRenderLayer.SOLID)
/*      */     {
/*  775 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  779 */     Config.warn("Invalid overlay layer: " + this.layer);
/*  780 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontal(String p_isValidHorizontal_1_) {
/*  786 */     if (this.tiles == null)
/*      */     {
/*  788 */       this.tiles = parseTileNames("12-15");
/*      */     }
/*      */     
/*  791 */     if (this.tiles.length != 4) {
/*      */       
/*  793 */       Config.warn("Invalid tiles, must be exactly 4: " + p_isValidHorizontal_1_);
/*  794 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  798 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVertical(String p_isValidVertical_1_) {
/*  804 */     if (this.tiles == null) {
/*      */       
/*  806 */       Config.warn("No tiles defined for vertical: " + p_isValidVertical_1_);
/*  807 */       return false;
/*      */     } 
/*  809 */     if (this.tiles.length != 4) {
/*      */       
/*  811 */       Config.warn("Invalid tiles, must be exactly 4: " + p_isValidVertical_1_);
/*  812 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  816 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidHorizontalVertical(String p_isValidHorizontalVertical_1_) {
/*  822 */     if (this.tiles == null) {
/*      */       
/*  824 */       Config.warn("No tiles defined for horizontal+vertical: " + p_isValidHorizontalVertical_1_);
/*  825 */       return false;
/*      */     } 
/*  827 */     if (this.tiles.length != 7) {
/*      */       
/*  829 */       Config.warn("Invalid tiles, must be exactly 7: " + p_isValidHorizontalVertical_1_);
/*  830 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  834 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidVerticalHorizontal(String p_isValidVerticalHorizontal_1_) {
/*  840 */     if (this.tiles == null) {
/*      */       
/*  842 */       Config.warn("No tiles defined for vertical+horizontal: " + p_isValidVerticalHorizontal_1_);
/*  843 */       return false;
/*      */     } 
/*  845 */     if (this.tiles.length != 7) {
/*      */       
/*  847 */       Config.warn("Invalid tiles, must be exactly 7: " + p_isValidVerticalHorizontal_1_);
/*  848 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  852 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRandom(String p_isValidRandom_1_) {
/*  858 */     if (this.tiles != null && this.tiles.length > 0) {
/*      */       
/*  860 */       if (this.weights != null) {
/*      */         
/*  862 */         if (this.weights.length > this.tiles.length) {
/*      */           
/*  864 */           Config.warn("More weights defined than tiles, trimming weights: " + p_isValidRandom_1_);
/*  865 */           int[] aint = new int[this.tiles.length];
/*  866 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/*  867 */           this.weights = aint;
/*      */         } 
/*      */         
/*  870 */         if (this.weights.length < this.tiles.length) {
/*      */           
/*  872 */           Config.warn("Less weights defined than tiles, expanding weights: " + p_isValidRandom_1_);
/*  873 */           int[] aint1 = new int[this.tiles.length];
/*  874 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/*  875 */           int i = MathUtils.getAverage(this.weights);
/*      */           
/*  877 */           for (int j = this.weights.length; j < aint1.length; j++)
/*      */           {
/*  879 */             aint1[j] = i;
/*      */           }
/*      */           
/*  882 */           this.weights = aint1;
/*      */         } 
/*      */         
/*  885 */         this.sumWeights = new int[this.weights.length];
/*  886 */         int k = 0;
/*      */         
/*  888 */         for (int l = 0; l < this.weights.length; l++) {
/*      */           
/*  890 */           k += this.weights[l];
/*  891 */           this.sumWeights[l] = k;
/*      */         } 
/*      */         
/*  894 */         this.sumAllWeights = k;
/*      */         
/*  896 */         if (this.sumAllWeights <= 0) {
/*      */           
/*  898 */           Config.warn("Invalid sum of all weights: " + k);
/*  899 */           this.sumAllWeights = 1;
/*      */         } 
/*      */       } 
/*      */       
/*  903 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  907 */     Config.warn("Tiles not defined: " + p_isValidRandom_1_);
/*  908 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidRepeat(String p_isValidRepeat_1_) {
/*  914 */     if (this.tiles == null) {
/*      */       
/*  916 */       Config.warn("Tiles not defined: " + p_isValidRepeat_1_);
/*  917 */       return false;
/*      */     } 
/*  919 */     if (this.width > 0 && this.width <= 16) {
/*      */       
/*  921 */       if (this.height > 0 && this.height <= 16) {
/*      */         
/*  923 */         if (this.tiles.length != this.width * this.height) {
/*      */           
/*  925 */           Config.warn("Number of tiles does not equal width x height: " + p_isValidRepeat_1_);
/*  926 */           return false;
/*      */         } 
/*      */ 
/*      */         
/*  930 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  935 */       Config.warn("Invalid height: " + p_isValidRepeat_1_);
/*  936 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  941 */     Config.warn("Invalid width: " + p_isValidRepeat_1_);
/*  942 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidFixed(String p_isValidFixed_1_) {
/*  948 */     if (this.tiles == null) {
/*      */       
/*  950 */       Config.warn("Tiles not defined: " + p_isValidFixed_1_);
/*  951 */       return false;
/*      */     } 
/*  953 */     if (this.tiles.length != 1) {
/*      */       
/*  955 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/*  956 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  960 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidTop(String p_isValidTop_1_) {
/*  966 */     if (this.tiles == null)
/*      */     {
/*  968 */       this.tiles = parseTileNames("66");
/*      */     }
/*      */     
/*  971 */     if (this.tiles.length != 1) {
/*      */       
/*  973 */       Config.warn("Invalid tiles, must be exactly 1: " + p_isValidTop_1_);
/*  974 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  978 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateIcons(TextureMap p_updateIcons_1_) {
/*  984 */     if (this.matchTiles != null)
/*      */     {
/*  986 */       this.matchTileIcons = registerIcons(this.matchTiles, p_updateIcons_1_);
/*      */     }
/*      */     
/*  989 */     if (this.connectTiles != null)
/*      */     {
/*  991 */       this.connectTileIcons = registerIcons(this.connectTiles, p_updateIcons_1_);
/*      */     }
/*      */     
/*  994 */     if (this.tiles != null)
/*      */     {
/*  996 */       this.tileIcons = registerIcons(this.tiles, p_updateIcons_1_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite[] registerIcons(String[] p_registerIcons_0_, TextureMap p_registerIcons_1_) {
/* 1002 */     if (p_registerIcons_0_ == null)
/*      */     {
/* 1004 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1008 */     List<TextureAtlasSprite> list = new ArrayList();
/*      */     
/* 1010 */     for (int i = 0; i < p_registerIcons_0_.length; i++) {
/*      */       
/* 1012 */       String s = p_registerIcons_0_[i];
/* 1013 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1014 */       String s1 = resourcelocation.getResourceDomain();
/* 1015 */       String s2 = resourcelocation.getResourcePath();
/*      */       
/* 1017 */       if (!s2.contains("/"))
/*      */       {
/* 1019 */         s2 = "textures/blocks/" + s2;
/*      */       }
/*      */       
/* 1022 */       String s3 = String.valueOf(s2) + ".png";
/* 1023 */       ResourceLocation resourcelocation1 = new ResourceLocation(s1, s3);
/* 1024 */       boolean flag = Config.hasResource(resourcelocation1);
/*      */       
/* 1026 */       if (!flag)
/*      */       {
/* 1028 */         Config.warn("File not found: " + s3);
/*      */       }
/*      */       
/* 1031 */       String s4 = "textures/";
/* 1032 */       String s5 = s2;
/*      */       
/* 1034 */       if (s2.startsWith(s4))
/*      */       {
/* 1036 */         s5 = s2.substring(s4.length());
/*      */       }
/*      */       
/* 1039 */       ResourceLocation resourcelocation2 = new ResourceLocation(s1, s5);
/* 1040 */       TextureAtlasSprite textureatlassprite = p_registerIcons_1_.registerSprite(resourcelocation2);
/* 1041 */       list.add(textureatlassprite);
/*      */     } 
/*      */     
/* 1044 */     TextureAtlasSprite[] atextureatlassprite = list.<TextureAtlasSprite>toArray(new TextureAtlasSprite[list.size()]);
/* 1045 */     return atextureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesBlockId(int p_matchesBlockId_1_) {
/* 1051 */     return Matches.blockId(p_matchesBlockId_1_, this.matchBlocks);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBlock(int p_matchesBlock_1_, int p_matchesBlock_2_) {
/* 1056 */     if (!Matches.block(p_matchesBlock_1_, p_matchesBlock_2_, this.matchBlocks))
/*      */     {
/* 1058 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1062 */     return Matches.metadata(p_matchesBlock_2_, this.metadatas);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesIcon(TextureAtlasSprite p_matchesIcon_1_) {
/* 1068 */     return Matches.sprite(p_matchesIcon_1_, this.matchTileIcons);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1073 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean matchesBiome(Biome p_matchesBiome_1_) {
/* 1078 */     return Matches.biome(p_matchesBiome_1_, this.biomes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadataMax() {
/* 1083 */     int i = -1;
/* 1084 */     i = getMax(this.metadatas, i);
/*      */     
/* 1086 */     if (this.matchBlocks != null)
/*      */     {
/* 1088 */       for (int j = 0; j < this.matchBlocks.length; j++) {
/*      */         
/* 1090 */         MatchBlock matchblock = this.matchBlocks[j];
/* 1091 */         i = getMax(matchblock.getMetadatas(), i);
/*      */       } 
/*      */     }
/*      */     
/* 1095 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getMax(int[] p_getMax_1_, int p_getMax_2_) {
/* 1100 */     if (p_getMax_1_ == null)
/*      */     {
/* 1102 */       return p_getMax_2_;
/*      */     }
/*      */ 
/*      */     
/* 1106 */     for (int i = 0; i < p_getMax_1_.length; i++) {
/*      */       
/* 1108 */       int j = p_getMax_1_[i];
/*      */       
/* 1110 */       if (j > p_getMax_2_)
/*      */       {
/* 1112 */         p_getMax_2_ = j;
/*      */       }
/*      */     } 
/*      */     
/* 1116 */     return p_getMax_2_;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ConnectedProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
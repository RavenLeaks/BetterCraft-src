/*      */ package optifine;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockRedstoneWire;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.color.BlockColors;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMonsterPlacer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.ColorizerFoliage;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ public class CustomColors
/*      */ {
/*   50 */   private static String paletteFormatDefault = "vanilla";
/*   51 */   private static CustomColormap waterColors = null;
/*   52 */   private static CustomColormap foliagePineColors = null;
/*   53 */   private static CustomColormap foliageBirchColors = null;
/*   54 */   private static CustomColormap swampFoliageColors = null;
/*   55 */   private static CustomColormap swampGrassColors = null;
/*   56 */   private static CustomColormap[] colorsBlockColormaps = null;
/*   57 */   private static CustomColormap[][] blockColormaps = null;
/*   58 */   private static CustomColormap skyColors = null;
/*   59 */   private static CustomColorFader skyColorFader = new CustomColorFader();
/*   60 */   private static CustomColormap fogColors = null;
/*   61 */   private static CustomColorFader fogColorFader = new CustomColorFader();
/*   62 */   private static CustomColormap underwaterColors = null;
/*   63 */   private static CustomColorFader underwaterColorFader = new CustomColorFader();
/*   64 */   private static CustomColormap underlavaColors = null;
/*   65 */   private static CustomColorFader underlavaColorFader = new CustomColorFader();
/*   66 */   private static CustomColormap[] lightMapsColorsRgb = null;
/*   67 */   private static int lightmapMinDimensionId = 0;
/*   68 */   private static float[][] sunRgbs = new float[16][3];
/*   69 */   private static float[][] torchRgbs = new float[16][3];
/*   70 */   private static CustomColormap redstoneColors = null;
/*   71 */   private static CustomColormap xpOrbColors = null;
/*   72 */   private static int xpOrbTime = -1;
/*   73 */   private static CustomColormap durabilityColors = null;
/*   74 */   private static CustomColormap stemColors = null;
/*   75 */   private static CustomColormap stemMelonColors = null;
/*   76 */   private static CustomColormap stemPumpkinColors = null;
/*   77 */   private static CustomColormap myceliumParticleColors = null;
/*      */   private static boolean useDefaultGrassFoliageColors = true;
/*   79 */   private static int particleWaterColor = -1;
/*   80 */   private static int particlePortalColor = -1;
/*   81 */   private static int lilyPadColor = -1;
/*   82 */   private static int expBarTextColor = -1;
/*   83 */   private static int bossTextColor = -1;
/*   84 */   private static int signTextColor = -1;
/*   85 */   private static Vec3d fogColorNether = null;
/*   86 */   private static Vec3d fogColorEnd = null;
/*   87 */   private static Vec3d skyColorEnd = null;
/*   88 */   private static int[] spawnEggPrimaryColors = null;
/*   89 */   private static int[] spawnEggSecondaryColors = null;
/*   90 */   private static float[][] wolfCollarColors = null;
/*   91 */   private static float[][] sheepColors = null;
/*   92 */   private static int[] textColors = null;
/*   93 */   private static int[] mapColorsOriginal = null;
/*   94 */   private static int[] potionColors = null;
/*   95 */   private static final IBlockState BLOCK_STATE_DIRT = Blocks.DIRT.getDefaultState();
/*   96 */   private static final IBlockState BLOCK_STATE_WATER = Blocks.WATER.getDefaultState();
/*   97 */   public static Random random = new Random();
/*   98 */   private static final IColorizer COLORIZER_GRASS = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_)
/*      */       {
/*  102 */         Biome biome = CustomColors.getColorBiome(p_getColor_2_, p_getColor_3_);
/*  103 */         return (CustomColors.swampGrassColors != null && biome == Biomes.SWAMPLAND) ? CustomColors.swampGrassColors.getColor(biome, p_getColor_3_) : biome.getGrassColorAtPos(p_getColor_3_);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  107 */         return false;
/*      */       }
/*      */     };
/*  110 */   private static final IColorizer COLORIZER_FOLIAGE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_)
/*      */       {
/*  114 */         Biome biome = CustomColors.getColorBiome(p_getColor_2_, p_getColor_3_);
/*  115 */         return (CustomColors.swampFoliageColors != null && biome == Biomes.SWAMPLAND) ? CustomColors.swampFoliageColors.getColor(biome, p_getColor_3_) : biome.getFoliageColorAtPos(p_getColor_3_);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  119 */         return false;
/*      */       }
/*      */     };
/*  122 */   private static final IColorizer COLORIZER_FOLIAGE_PINE = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_)
/*      */       {
/*  126 */         return (CustomColors.foliagePineColors != null) ? CustomColors.foliagePineColors.getColor(p_getColor_2_, p_getColor_3_) : ColorizerFoliage.getFoliageColorPine();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  130 */         return (CustomColors.foliagePineColors == null);
/*      */       }
/*      */     };
/*  133 */   private static final IColorizer COLORIZER_FOLIAGE_BIRCH = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_)
/*      */       {
/*  137 */         return (CustomColors.foliageBirchColors != null) ? CustomColors.foliageBirchColors.getColor(p_getColor_2_, p_getColor_3_) : ColorizerFoliage.getFoliageColorBirch();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  141 */         return (CustomColors.foliageBirchColors == null);
/*      */       }
/*      */     };
/*  144 */   private static final IColorizer COLORIZER_WATER = new IColorizer()
/*      */     {
/*      */       public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_)
/*      */       {
/*  148 */         Biome biome = CustomColors.getColorBiome(p_getColor_2_, p_getColor_3_);
/*      */         
/*  150 */         if (CustomColors.waterColors != null)
/*      */         {
/*  152 */           return CustomColors.waterColors.getColor(biome, p_getColor_3_);
/*      */         }
/*      */ 
/*      */         
/*  156 */         return Reflector.ForgeBiome_getWaterColorMultiplier.exists() ? Reflector.callInt(biome, Reflector.ForgeBiome_getWaterColorMultiplier, new Object[0]) : biome.getWaterColor();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isColorConstant() {
/*  161 */         return false;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public static void update() {
/*  167 */     paletteFormatDefault = "vanilla";
/*  168 */     waterColors = null;
/*  169 */     foliageBirchColors = null;
/*  170 */     foliagePineColors = null;
/*  171 */     swampGrassColors = null;
/*  172 */     swampFoliageColors = null;
/*  173 */     skyColors = null;
/*  174 */     fogColors = null;
/*  175 */     underwaterColors = null;
/*  176 */     underlavaColors = null;
/*  177 */     redstoneColors = null;
/*  178 */     xpOrbColors = null;
/*  179 */     xpOrbTime = -1;
/*  180 */     durabilityColors = null;
/*  181 */     stemColors = null;
/*  182 */     myceliumParticleColors = null;
/*  183 */     lightMapsColorsRgb = null;
/*  184 */     particleWaterColor = -1;
/*  185 */     particlePortalColor = -1;
/*  186 */     lilyPadColor = -1;
/*  187 */     expBarTextColor = -1;
/*  188 */     bossTextColor = -1;
/*  189 */     signTextColor = -1;
/*  190 */     fogColorNether = null;
/*  191 */     fogColorEnd = null;
/*  192 */     skyColorEnd = null;
/*  193 */     colorsBlockColormaps = null;
/*  194 */     blockColormaps = null;
/*  195 */     useDefaultGrassFoliageColors = true;
/*  196 */     spawnEggPrimaryColors = null;
/*  197 */     spawnEggSecondaryColors = null;
/*  198 */     wolfCollarColors = null;
/*  199 */     sheepColors = null;
/*  200 */     textColors = null;
/*  201 */     setMapColors(mapColorsOriginal);
/*  202 */     potionColors = null;
/*  203 */     paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
/*  204 */     String s = "mcpatcher/colormap/";
/*  205 */     String[] astring = { "water.png", "watercolorX.png" };
/*  206 */     waterColors = getCustomColors(s, astring, 256, 256);
/*  207 */     updateUseDefaultGrassFoliageColors();
/*      */     
/*  209 */     if (Config.isCustomColors()) {
/*      */       
/*  211 */       String[] astring1 = { "pine.png", "pinecolor.png" };
/*  212 */       foliagePineColors = getCustomColors(s, astring1, 256, 256);
/*  213 */       String[] astring2 = { "birch.png", "birchcolor.png" };
/*  214 */       foliageBirchColors = getCustomColors(s, astring2, 256, 256);
/*  215 */       String[] astring3 = { "swampgrass.png", "swampgrasscolor.png" };
/*  216 */       swampGrassColors = getCustomColors(s, astring3, 256, 256);
/*  217 */       String[] astring4 = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  218 */       swampFoliageColors = getCustomColors(s, astring4, 256, 256);
/*  219 */       String[] astring5 = { "sky0.png", "skycolor0.png" };
/*  220 */       skyColors = getCustomColors(s, astring5, 256, 256);
/*  221 */       String[] astring6 = { "fog0.png", "fogcolor0.png" };
/*  222 */       fogColors = getCustomColors(s, astring6, 256, 256);
/*  223 */       String[] astring7 = { "underwater.png", "underwatercolor.png" };
/*  224 */       underwaterColors = getCustomColors(s, astring7, 256, 256);
/*  225 */       String[] astring8 = { "underlava.png", "underlavacolor.png" };
/*  226 */       underlavaColors = getCustomColors(s, astring8, 256, 256);
/*  227 */       String[] astring9 = { "redstone.png", "redstonecolor.png" };
/*  228 */       redstoneColors = getCustomColors(s, astring9, 16, 1);
/*  229 */       xpOrbColors = getCustomColors(String.valueOf(s) + "xporb.png", -1, -1);
/*  230 */       durabilityColors = getCustomColors(String.valueOf(s) + "durability.png", -1, -1);
/*  231 */       String[] astring10 = { "stem.png", "stemcolor.png" };
/*  232 */       stemColors = getCustomColors(s, astring10, 8, 1);
/*  233 */       stemPumpkinColors = getCustomColors(String.valueOf(s) + "pumpkinstem.png", 8, 1);
/*  234 */       stemMelonColors = getCustomColors(String.valueOf(s) + "melonstem.png", 8, 1);
/*  235 */       String[] astring11 = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  236 */       myceliumParticleColors = getCustomColors(s, astring11, -1, -1);
/*  237 */       Pair<CustomColormap[], Integer> pair = parseLightmapsRgb();
/*  238 */       lightMapsColorsRgb = (CustomColormap[])pair.getLeft();
/*  239 */       lightmapMinDimensionId = ((Integer)pair.getRight()).intValue();
/*  240 */       readColorProperties("mcpatcher/color.properties");
/*  241 */       blockColormaps = readBlockColormaps(new String[] { String.valueOf(s) + "custom/", String.valueOf(s) + "blocks/" }, colorsBlockColormaps, 256, 256);
/*  242 */       updateUseDefaultGrassFoliageColors();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getValidProperty(String p_getValidProperty_0_, String p_getValidProperty_1_, String[] p_getValidProperty_2_, String p_getValidProperty_3_) {
/*      */     try {
/*  250 */       ResourceLocation resourcelocation = new ResourceLocation(p_getValidProperty_0_);
/*  251 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  253 */       if (inputstream == null)
/*      */       {
/*  255 */         return p_getValidProperty_3_;
/*      */       }
/*      */ 
/*      */       
/*  259 */       Properties properties = new Properties();
/*  260 */       properties.load(inputstream);
/*  261 */       inputstream.close();
/*  262 */       String s = properties.getProperty(p_getValidProperty_1_);
/*      */       
/*  264 */       if (s == null)
/*      */       {
/*  266 */         return p_getValidProperty_3_;
/*      */       }
/*      */ 
/*      */       
/*  270 */       List<String> list = Arrays.asList(p_getValidProperty_2_);
/*      */       
/*  272 */       if (!list.contains(s)) {
/*      */         
/*  274 */         warn("Invalid value: " + p_getValidProperty_1_ + "=" + s);
/*  275 */         warn("Expected values: " + Config.arrayToString((Object[])p_getValidProperty_2_));
/*  276 */         return p_getValidProperty_3_;
/*      */       } 
/*      */ 
/*      */       
/*  280 */       dbg(p_getValidProperty_1_ + "=" + s);
/*  281 */       return s;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  286 */     catch (FileNotFoundException var9) {
/*      */       
/*  288 */       return p_getValidProperty_3_;
/*      */     }
/*  290 */     catch (IOException ioexception) {
/*      */       
/*  292 */       ioexception.printStackTrace();
/*  293 */       return p_getValidProperty_3_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Pair<CustomColormap[], Integer> parseLightmapsRgb() {
/*  299 */     String s = "mcpatcher/lightmap/world";
/*  300 */     String s1 = ".png";
/*  301 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  302 */     Map<Integer, String> map = new HashMap<>();
/*      */     
/*  304 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  306 */       String s2 = astring[i];
/*  307 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  308 */       int j = Config.parseInt(s3, -2147483648);
/*      */       
/*  310 */       if (j == Integer.MIN_VALUE) {
/*      */         
/*  312 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*      */       }
/*      */       else {
/*      */         
/*  316 */         map.put(Integer.valueOf(j), s2);
/*      */       } 
/*      */     } 
/*      */     
/*  320 */     Set<Integer> set = map.keySet();
/*  321 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  322 */     Arrays.sort((Object[])ainteger);
/*      */     
/*  324 */     if (ainteger.length <= 0)
/*      */     {
/*  326 */       return (Pair<CustomColormap[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*      */     }
/*      */ 
/*      */     
/*  330 */     int j1 = ainteger[0].intValue();
/*  331 */     int k1 = ainteger[ainteger.length - 1].intValue();
/*  332 */     int k = k1 - j1 + 1;
/*  333 */     CustomColormap[] acustomcolormap = new CustomColormap[k];
/*      */     
/*  335 */     for (int l = 0; l < ainteger.length; l++) {
/*      */       
/*  337 */       Integer integer = ainteger[l];
/*  338 */       String s4 = map.get(integer);
/*  339 */       CustomColormap customcolormap = getCustomColors(s4, -1, -1);
/*      */       
/*  341 */       if (customcolormap != null)
/*      */       {
/*  343 */         if (customcolormap.getWidth() < 16) {
/*      */           
/*  345 */           warn("Invalid lightmap width: " + customcolormap.getWidth() + ", path: " + s4);
/*      */         }
/*      */         else {
/*      */           
/*  349 */           int i1 = integer.intValue() - j1;
/*  350 */           acustomcolormap[i1] = customcolormap;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  355 */     return (Pair<CustomColormap[], Integer>)new ImmutablePair(acustomcolormap, Integer.valueOf(j1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureHeight(String p_getTextureHeight_0_, int p_getTextureHeight_1_) {
/*      */     try {
/*  363 */       InputStream inputstream = Config.getResourceStream(new ResourceLocation(p_getTextureHeight_0_));
/*      */       
/*  365 */       if (inputstream == null)
/*      */       {
/*  367 */         return p_getTextureHeight_1_;
/*      */       }
/*      */ 
/*      */       
/*  371 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*  372 */       inputstream.close();
/*  373 */       return (bufferedimage == null) ? p_getTextureHeight_1_ : bufferedimage.getHeight();
/*      */     
/*      */     }
/*  376 */     catch (IOException var4) {
/*      */       
/*  378 */       return p_getTextureHeight_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readColorProperties(String p_readColorProperties_0_) {
/*      */     try {
/*  386 */       ResourceLocation resourcelocation = new ResourceLocation(p_readColorProperties_0_);
/*  387 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  389 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  394 */       dbg("Loading " + p_readColorProperties_0_);
/*  395 */       Properties properties = new Properties();
/*  396 */       properties.load(inputstream);
/*  397 */       inputstream.close();
/*  398 */       particleWaterColor = readColor(properties, new String[] { "particle.water", "drop.water" });
/*  399 */       particlePortalColor = readColor(properties, "particle.portal");
/*  400 */       lilyPadColor = readColor(properties, "lilypad");
/*  401 */       expBarTextColor = readColor(properties, "text.xpbar");
/*  402 */       bossTextColor = readColor(properties, "text.boss");
/*  403 */       signTextColor = readColor(properties, "text.sign");
/*  404 */       fogColorNether = readColorVec3(properties, "fog.nether");
/*  405 */       fogColorEnd = readColorVec3(properties, "fog.end");
/*  406 */       skyColorEnd = readColorVec3(properties, "sky.end");
/*  407 */       colorsBlockColormaps = readCustomColormaps(properties, p_readColorProperties_0_);
/*  408 */       spawnEggPrimaryColors = readSpawnEggColors(properties, p_readColorProperties_0_, "egg.shell.", "Spawn egg shell");
/*  409 */       spawnEggSecondaryColors = readSpawnEggColors(properties, p_readColorProperties_0_, "egg.spots.", "Spawn egg spot");
/*  410 */       wolfCollarColors = readDyeColors(properties, p_readColorProperties_0_, "collar.", "Wolf collar");
/*  411 */       sheepColors = readDyeColors(properties, p_readColorProperties_0_, "sheep.", "Sheep");
/*  412 */       textColors = readTextColors(properties, p_readColorProperties_0_, "text.code.", "Text");
/*  413 */       int[] aint = readMapColors(properties, p_readColorProperties_0_, "map.", "Map");
/*      */       
/*  415 */       if (aint != null) {
/*      */         
/*  417 */         if (mapColorsOriginal == null)
/*      */         {
/*  419 */           mapColorsOriginal = getMapColors();
/*      */         }
/*      */         
/*  422 */         setMapColors(aint);
/*      */       } 
/*      */       
/*  425 */       potionColors = readPotionColors(properties, p_readColorProperties_0_, "potion.", "Potion");
/*  426 */       xpOrbTime = Config.parseInt(properties.getProperty("xporb.time"), -1);
/*      */     }
/*  428 */     catch (FileNotFoundException var5) {
/*      */       
/*      */       return;
/*      */     }
/*  432 */     catch (IOException ioexception) {
/*      */       
/*  434 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[] readCustomColormaps(Properties p_readCustomColormaps_0_, String p_readCustomColormaps_1_) {
/*  440 */     List<CustomColormap> list = new ArrayList();
/*  441 */     String s = "palette.block.";
/*  442 */     Map<Object, Object> map = new HashMap<>();
/*      */     
/*  444 */     for (Object s1 : p_readCustomColormaps_0_.keySet()) {
/*      */       
/*  446 */       String s2 = p_readCustomColormaps_0_.getProperty((String)s1);
/*      */       
/*  448 */       if (((String)s1).startsWith(s))
/*      */       {
/*  450 */         map.put(s1, s2);
/*      */       }
/*      */     } 
/*      */     
/*  454 */     String[] astring = (String[])map.keySet().toArray((Object[])new String[map.size()]);
/*      */     
/*  456 */     for (int j = 0; j < astring.length; j++) {
/*      */       
/*  458 */       String s6 = astring[j];
/*  459 */       String s3 = p_readCustomColormaps_0_.getProperty(s6);
/*  460 */       dbg("Block palette: " + s6 + " = " + s3);
/*  461 */       String s4 = s6.substring(s.length());
/*  462 */       String s5 = TextureUtils.getBasePath(p_readCustomColormaps_1_);
/*  463 */       s4 = TextureUtils.fixResourcePath(s4, s5);
/*  464 */       CustomColormap customcolormap = getCustomColors(s4, 256, 256);
/*      */       
/*  466 */       if (customcolormap == null) {
/*      */         
/*  468 */         warn("Colormap not found: " + s4);
/*      */       }
/*      */       else {
/*      */         
/*  472 */         ConnectedParser connectedparser = new ConnectedParser("CustomColors");
/*  473 */         MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s3);
/*      */         
/*  475 */         if (amatchblock != null && amatchblock.length > 0) {
/*      */           
/*  477 */           for (int i = 0; i < amatchblock.length; i++) {
/*      */             
/*  479 */             MatchBlock matchblock = amatchblock[i];
/*  480 */             customcolormap.addMatchBlock(matchblock);
/*      */           } 
/*      */           
/*  483 */           list.add(customcolormap);
/*      */         }
/*      */         else {
/*      */           
/*  487 */           warn("Invalid match blocks: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  492 */     if (list.size() <= 0)
/*      */     {
/*  494 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  498 */     CustomColormap[] acustomcolormap = list.<CustomColormap>toArray(new CustomColormap[list.size()]);
/*  499 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] readBlockColormaps(String[] p_readBlockColormaps_0_, CustomColormap[] p_readBlockColormaps_1_, int p_readBlockColormaps_2_, int p_readBlockColormaps_3_) {
/*  505 */     String[] astring = ResUtils.collectFiles(p_readBlockColormaps_0_, new String[] { ".properties" });
/*  506 */     Arrays.sort((Object[])astring);
/*  507 */     List list = new ArrayList();
/*      */     
/*  509 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  511 */       String s = astring[i];
/*  512 */       dbg("Block colormap: " + s);
/*      */ 
/*      */       
/*      */       try {
/*  516 */         ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
/*  517 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */         
/*  519 */         if (inputstream == null)
/*      */         {
/*  521 */           warn("File not found: " + s);
/*      */         }
/*      */         else
/*      */         {
/*  525 */           Properties properties = new Properties();
/*  526 */           properties.load(inputstream);
/*  527 */           CustomColormap customcolormap = new CustomColormap(properties, s, p_readBlockColormaps_2_, p_readBlockColormaps_3_, paletteFormatDefault);
/*      */           
/*  529 */           if (customcolormap.isValid(s) && customcolormap.isValidMatchBlocks(s))
/*      */           {
/*  531 */             addToBlockList(customcolormap, list);
/*      */           }
/*      */         }
/*      */       
/*  535 */       } catch (FileNotFoundException var12) {
/*      */         
/*  537 */         warn("File not found: " + s);
/*      */       }
/*  539 */       catch (Exception exception) {
/*      */         
/*  541 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  545 */     if (p_readBlockColormaps_1_ != null)
/*      */     {
/*  547 */       for (int j = 0; j < p_readBlockColormaps_1_.length; j++) {
/*      */         
/*  549 */         CustomColormap customcolormap1 = p_readBlockColormaps_1_[j];
/*  550 */         addToBlockList(customcolormap1, list);
/*      */       } 
/*      */     }
/*      */     
/*  554 */     if (list.size() <= 0)
/*      */     {
/*  556 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  560 */     CustomColormap[][] acustomcolormap = blockListToArray(list);
/*  561 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToBlockList(CustomColormap p_addToBlockList_0_, List p_addToBlockList_1_) {
/*  567 */     int[] aint = p_addToBlockList_0_.getMatchBlockIds();
/*      */     
/*  569 */     if (aint != null && aint.length > 0) {
/*      */       
/*  571 */       for (int i = 0; i < aint.length; i++) {
/*      */         
/*  573 */         int j = aint[i];
/*      */         
/*  575 */         if (j < 0)
/*      */         {
/*  577 */           warn("Invalid block ID: " + j);
/*      */         }
/*      */         else
/*      */         {
/*  581 */           addToList(p_addToBlockList_0_, p_addToBlockList_1_, j);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  587 */       warn("No match blocks: " + Config.arrayToString(aint));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(CustomColormap p_addToList_0_, List<List> p_addToList_1_, int p_addToList_2_) {
/*  593 */     while (p_addToList_2_ >= p_addToList_1_.size())
/*      */     {
/*  595 */       p_addToList_1_.add(null);
/*      */     }
/*      */     
/*  598 */     List<CustomColormap> list = p_addToList_1_.get(p_addToList_2_);
/*      */     
/*  600 */     if (list == null) {
/*      */       
/*  602 */       list = new ArrayList();
/*  603 */       p_addToList_1_.set(p_addToList_2_, list);
/*      */     } 
/*      */     
/*  606 */     list.add(p_addToList_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] blockListToArray(List<List> p_blockListToArray_0_) {
/*  611 */     CustomColormap[][] acustomcolormap = new CustomColormap[p_blockListToArray_0_.size()][];
/*      */     
/*  613 */     for (int i = 0; i < p_blockListToArray_0_.size(); i++) {
/*      */       
/*  615 */       List list = p_blockListToArray_0_.get(i);
/*      */       
/*  617 */       if (list != null) {
/*      */         
/*  619 */         CustomColormap[] acustomcolormap1 = (CustomColormap[])list.toArray((Object[])new CustomColormap[list.size()]);
/*  620 */         acustomcolormap[i] = acustomcolormap1;
/*      */       } 
/*      */     } 
/*      */     
/*  624 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties p_readColor_0_, String[] p_readColor_1_) {
/*  629 */     for (int i = 0; i < p_readColor_1_.length; i++) {
/*      */       
/*  631 */       String s = p_readColor_1_[i];
/*  632 */       int j = readColor(p_readColor_0_, s);
/*      */       
/*  634 */       if (j >= 0)
/*      */       {
/*  636 */         return j;
/*      */       }
/*      */     } 
/*      */     
/*  640 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readColor(Properties p_readColor_0_, String p_readColor_1_) {
/*  645 */     String s = p_readColor_0_.getProperty(p_readColor_1_);
/*      */     
/*  647 */     if (s == null)
/*      */     {
/*  649 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  653 */     s = s.trim();
/*  654 */     int i = parseColor(s);
/*      */     
/*  656 */     if (i < 0) {
/*      */       
/*  658 */       warn("Invalid color: " + p_readColor_1_ + " = " + s);
/*  659 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  663 */     dbg(String.valueOf(p_readColor_1_) + " = " + s);
/*  664 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseColor(String p_parseColor_0_) {
/*  671 */     if (p_parseColor_0_ == null)
/*      */     {
/*  673 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  677 */     p_parseColor_0_ = p_parseColor_0_.trim();
/*      */ 
/*      */     
/*      */     try {
/*  681 */       int i = Integer.parseInt(p_parseColor_0_, 16) & 0xFFFFFF;
/*  682 */       return i;
/*      */     }
/*  684 */     catch (NumberFormatException var2) {
/*      */       
/*  686 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3d readColorVec3(Properties p_readColorVec3_0_, String p_readColorVec3_1_) {
/*  693 */     int i = readColor(p_readColorVec3_0_, p_readColorVec3_1_);
/*      */     
/*  695 */     if (i < 0)
/*      */     {
/*  697 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  701 */     int j = i >> 16 & 0xFF;
/*  702 */     int k = i >> 8 & 0xFF;
/*  703 */     int l = i & 0xFF;
/*  704 */     float f = j / 255.0F;
/*  705 */     float f1 = k / 255.0F;
/*  706 */     float f2 = l / 255.0F;
/*  707 */     return new Vec3d(f, f1, f2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomColormap getCustomColors(String p_getCustomColors_0_, String[] p_getCustomColors_1_, int p_getCustomColors_2_, int p_getCustomColors_3_) {
/*  713 */     for (int i = 0; i < p_getCustomColors_1_.length; i++) {
/*      */       
/*  715 */       String s = p_getCustomColors_1_[i];
/*  716 */       s = String.valueOf(p_getCustomColors_0_) + s;
/*  717 */       CustomColormap customcolormap = getCustomColors(s, p_getCustomColors_2_, p_getCustomColors_3_);
/*      */       
/*  719 */       if (customcolormap != null)
/*      */       {
/*  721 */         return customcolormap;
/*      */       }
/*      */     } 
/*      */     
/*  725 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static CustomColormap getCustomColors(String p_getCustomColors_0_, int p_getCustomColors_1_, int p_getCustomColors_2_) {
/*      */     try {
/*  732 */       ResourceLocation resourcelocation = new ResourceLocation(p_getCustomColors_0_);
/*      */       
/*  734 */       if (!Config.hasResource(resourcelocation))
/*      */       {
/*  736 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  740 */       dbg("Colormap " + p_getCustomColors_0_);
/*  741 */       Properties properties = new Properties();
/*  742 */       String s = StrUtils.replaceSuffix(p_getCustomColors_0_, ".png", ".properties");
/*  743 */       ResourceLocation resourcelocation1 = new ResourceLocation(s);
/*      */       
/*  745 */       if (Config.hasResource(resourcelocation1)) {
/*      */         
/*  747 */         InputStream inputstream = Config.getResourceStream(resourcelocation1);
/*  748 */         properties.load(inputstream);
/*  749 */         inputstream.close();
/*  750 */         dbg("Colormap properties: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  754 */         properties.put("format", paletteFormatDefault);
/*  755 */         properties.put("source", p_getCustomColors_0_);
/*  756 */         s = p_getCustomColors_0_;
/*      */       } 
/*      */       
/*  759 */       CustomColormap customcolormap = new CustomColormap(properties, s, p_getCustomColors_1_, p_getCustomColors_2_, paletteFormatDefault);
/*  760 */       return !customcolormap.isValid(s) ? null : customcolormap;
/*      */     
/*      */     }
/*  763 */     catch (Exception exception) {
/*      */       
/*  765 */       exception.printStackTrace();
/*  766 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateUseDefaultGrassFoliageColors() {
/*  772 */     useDefaultGrassFoliageColors = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes());
/*      */   }
/*      */   
/*      */   public static int getColorMultiplier(BakedQuad p_getColorMultiplier_0_, IBlockState p_getColorMultiplier_1_, IBlockAccess p_getColorMultiplier_2_, BlockPos p_getColorMultiplier_3_, RenderEnv p_getColorMultiplier_4_) {
/*      */     IColorizer customcolors$icolorizer;
/*  777 */     Block block = p_getColorMultiplier_1_.getBlock();
/*  778 */     IBlockState iblockstate = p_getColorMultiplier_4_.getBlockState();
/*      */     
/*  780 */     if (blockColormaps != null) {
/*      */       
/*  782 */       if (!p_getColorMultiplier_0_.hasTintIndex()) {
/*      */         
/*  784 */         if (block == Blocks.GRASS)
/*      */         {
/*  786 */           iblockstate = BLOCK_STATE_DIRT;
/*      */         }
/*      */         
/*  789 */         if (block == Blocks.REDSTONE_WIRE)
/*      */         {
/*  791 */           return -1;
/*      */         }
/*      */       } 
/*      */       
/*  795 */       if (block == Blocks.DOUBLE_PLANT && p_getColorMultiplier_4_.getMetadata() >= 8) {
/*      */         
/*  797 */         p_getColorMultiplier_3_ = p_getColorMultiplier_3_.down();
/*  798 */         iblockstate = p_getColorMultiplier_2_.getBlockState(p_getColorMultiplier_3_);
/*      */       } 
/*      */       
/*  801 */       CustomColormap customcolormap = getBlockColormap(iblockstate);
/*      */       
/*  803 */       if (customcolormap != null) {
/*      */         
/*  805 */         if (Config.isSmoothBiomes() && !customcolormap.isColorConstant())
/*      */         {
/*  807 */           return getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, customcolormap, p_getColorMultiplier_4_.getColorizerBlockPosM());
/*      */         }
/*      */         
/*  810 */         return customcolormap.getColor(p_getColorMultiplier_2_, p_getColorMultiplier_3_);
/*      */       } 
/*      */     } 
/*      */     
/*  814 */     if (!p_getColorMultiplier_0_.hasTintIndex())
/*      */     {
/*  816 */       return -1;
/*      */     }
/*  818 */     if (block == Blocks.WATERLILY)
/*      */     {
/*  820 */       return getLilypadColorMultiplier(p_getColorMultiplier_2_, p_getColorMultiplier_3_);
/*      */     }
/*  822 */     if (block == Blocks.REDSTONE_WIRE)
/*      */     {
/*  824 */       return getRedstoneColor(p_getColorMultiplier_4_.getBlockState());
/*      */     }
/*  826 */     if (block instanceof net.minecraft.block.BlockStem)
/*      */     {
/*  828 */       return getStemColorMultiplier(block, p_getColorMultiplier_2_, p_getColorMultiplier_3_, p_getColorMultiplier_4_);
/*      */     }
/*  830 */     if (useDefaultGrassFoliageColors)
/*      */     {
/*  832 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  836 */     int i = p_getColorMultiplier_4_.getMetadata();
/*      */ 
/*      */     
/*  839 */     if (block != Blocks.GRASS && block != Blocks.TALLGRASS && block != Blocks.DOUBLE_PLANT) {
/*      */       
/*  841 */       if (block == Blocks.DOUBLE_PLANT) {
/*      */         
/*  843 */         customcolors$icolorizer = COLORIZER_GRASS;
/*      */         
/*  845 */         if (i >= 8)
/*      */         {
/*  847 */           p_getColorMultiplier_3_ = p_getColorMultiplier_3_.down();
/*      */         }
/*      */       }
/*  850 */       else if (block == Blocks.LEAVES) {
/*      */         
/*  852 */         switch (i & 0x3) {
/*      */           
/*      */           case 0:
/*  855 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */           
/*      */           case 1:
/*  859 */             customcolors$icolorizer = COLORIZER_FOLIAGE_PINE;
/*      */             break;
/*      */           
/*      */           case 2:
/*  863 */             customcolors$icolorizer = COLORIZER_FOLIAGE_BIRCH;
/*      */             break;
/*      */           
/*      */           default:
/*  867 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */         } 
/*  870 */       } else if (block == Blocks.LEAVES2) {
/*      */         
/*  872 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */       else {
/*      */         
/*  876 */         if (block != Blocks.VINE)
/*      */         {
/*  878 */           return -1;
/*      */         }
/*      */         
/*  881 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  886 */       customcolors$icolorizer = COLORIZER_GRASS;
/*      */     } 
/*      */     
/*  889 */     return (Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(p_getColorMultiplier_1_, p_getColorMultiplier_2_, p_getColorMultiplier_3_, customcolors$icolorizer, p_getColorMultiplier_4_.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(iblockstate, p_getColorMultiplier_2_, p_getColorMultiplier_3_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Biome getColorBiome(IBlockAccess p_getColorBiome_0_, BlockPos p_getColorBiome_1_) {
/*  895 */     Biome biome = p_getColorBiome_0_.getBiome(p_getColorBiome_1_);
/*      */     
/*  897 */     if (biome == Biomes.SWAMPLAND && !Config.isSwampColors())
/*      */     {
/*  899 */       biome = Biomes.PLAINS;
/*      */     }
/*      */     
/*  902 */     return biome;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap getBlockColormap(IBlockState p_getBlockColormap_0_) {
/*  907 */     if (blockColormaps == null)
/*      */     {
/*  909 */       return null;
/*      */     }
/*  911 */     if (!(p_getBlockColormap_0_ instanceof BlockStateBase))
/*      */     {
/*  913 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  917 */     BlockStateBase blockstatebase = (BlockStateBase)p_getBlockColormap_0_;
/*  918 */     int i = blockstatebase.getBlockId();
/*      */     
/*  920 */     if (i >= 0 && i < blockColormaps.length) {
/*      */       
/*  922 */       CustomColormap[] acustomcolormap = blockColormaps[i];
/*      */       
/*  924 */       if (acustomcolormap == null)
/*      */       {
/*  926 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  930 */       for (int j = 0; j < acustomcolormap.length; j++) {
/*      */         
/*  932 */         CustomColormap customcolormap = acustomcolormap[j];
/*      */         
/*  934 */         if (customcolormap.matchesBlock(blockstatebase))
/*      */         {
/*  936 */           return customcolormap;
/*      */         }
/*      */       } 
/*      */       
/*  940 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  945 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSmoothColorMultiplier(IBlockState p_getSmoothColorMultiplier_0_, IBlockAccess p_getSmoothColorMultiplier_1_, BlockPos p_getSmoothColorMultiplier_2_, IColorizer p_getSmoothColorMultiplier_3_, BlockPosM p_getSmoothColorMultiplier_4_) {
/*  952 */     int i = 0;
/*  953 */     int j = 0;
/*  954 */     int k = 0;
/*  955 */     int l = p_getSmoothColorMultiplier_2_.getX();
/*  956 */     int i1 = p_getSmoothColorMultiplier_2_.getY();
/*  957 */     int j1 = p_getSmoothColorMultiplier_2_.getZ();
/*  958 */     BlockPosM blockposm = p_getSmoothColorMultiplier_4_;
/*      */     
/*  960 */     for (int k1 = l - 1; k1 <= l + 1; k1++) {
/*      */       
/*  962 */       for (int l1 = j1 - 1; l1 <= j1 + 1; l1++) {
/*      */         
/*  964 */         blockposm.setXyz(k1, i1, l1);
/*  965 */         int i2 = p_getSmoothColorMultiplier_3_.getColor(p_getSmoothColorMultiplier_0_, p_getSmoothColorMultiplier_1_, blockposm);
/*  966 */         i += i2 >> 16 & 0xFF;
/*  967 */         j += i2 >> 8 & 0xFF;
/*  968 */         k += i2 & 0xFF;
/*      */       } 
/*      */     } 
/*      */     
/*  972 */     int j2 = i / 9;
/*  973 */     int k2 = j / 9;
/*  974 */     int l2 = k / 9;
/*  975 */     return j2 << 16 | k2 << 8 | l2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getFluidColor(IBlockAccess p_getFluidColor_0_, IBlockState p_getFluidColor_1_, BlockPos p_getFluidColor_2_, RenderEnv p_getFluidColor_3_) {
/*  980 */     Block block = p_getFluidColor_1_.getBlock();
/*  981 */     IColorizer customcolors$icolorizer = getBlockColormap(p_getFluidColor_1_);
/*      */     
/*  983 */     if (customcolors$icolorizer == null && p_getFluidColor_1_.getMaterial() == Material.WATER)
/*      */     {
/*  985 */       customcolors$icolorizer = COLORIZER_WATER;
/*      */     }
/*      */     
/*  988 */     if (customcolors$icolorizer == null)
/*      */     {
/*  990 */       return getBlockColors().colorMultiplier(p_getFluidColor_1_, p_getFluidColor_0_, p_getFluidColor_2_, 0);
/*      */     }
/*      */ 
/*      */     
/*  994 */     return (Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(p_getFluidColor_1_, p_getFluidColor_0_, p_getFluidColor_2_, customcolors$icolorizer, p_getFluidColor_3_.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(p_getFluidColor_1_, p_getFluidColor_0_, p_getFluidColor_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlockColors getBlockColors() {
/* 1000 */     return Minecraft.getMinecraft().getBlockColors();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updatePortalFX(Particle p_updatePortalFX_0_) {
/* 1005 */     if (particlePortalColor >= 0) {
/*      */       
/* 1007 */       int i = particlePortalColor;
/* 1008 */       int j = i >> 16 & 0xFF;
/* 1009 */       int k = i >> 8 & 0xFF;
/* 1010 */       int l = i & 0xFF;
/* 1011 */       float f = j / 255.0F;
/* 1012 */       float f1 = k / 255.0F;
/* 1013 */       float f2 = l / 255.0F;
/* 1014 */       p_updatePortalFX_0_.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateMyceliumFX(Particle p_updateMyceliumFX_0_) {
/* 1020 */     if (myceliumParticleColors != null) {
/*      */       
/* 1022 */       int i = myceliumParticleColors.getColorRandom();
/* 1023 */       int j = i >> 16 & 0xFF;
/* 1024 */       int k = i >> 8 & 0xFF;
/* 1025 */       int l = i & 0xFF;
/* 1026 */       float f = j / 255.0F;
/* 1027 */       float f1 = k / 255.0F;
/* 1028 */       float f2 = l / 255.0F;
/* 1029 */       p_updateMyceliumFX_0_.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneColor(IBlockState p_getRedstoneColor_0_) {
/* 1035 */     if (redstoneColors == null)
/*      */     {
/* 1037 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1041 */     int i = getRedstoneLevel(p_getRedstoneColor_0_, 15);
/* 1042 */     int j = redstoneColors.getColor(i);
/* 1043 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateReddustFX(Particle p_updateReddustFX_0_, IBlockAccess p_updateReddustFX_1_, double p_updateReddustFX_2_, double p_updateReddustFX_4_, double p_updateReddustFX_6_) {
/* 1049 */     if (redstoneColors != null) {
/*      */       
/* 1051 */       IBlockState iblockstate = p_updateReddustFX_1_.getBlockState(new BlockPos(p_updateReddustFX_2_, p_updateReddustFX_4_, p_updateReddustFX_6_));
/* 1052 */       int i = getRedstoneLevel(iblockstate, 15);
/* 1053 */       int j = redstoneColors.getColor(i);
/* 1054 */       int k = j >> 16 & 0xFF;
/* 1055 */       int l = j >> 8 & 0xFF;
/* 1056 */       int i1 = j & 0xFF;
/* 1057 */       float f = k / 255.0F;
/* 1058 */       float f1 = l / 255.0F;
/* 1059 */       float f2 = i1 / 255.0F;
/* 1060 */       p_updateReddustFX_0_.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getRedstoneLevel(IBlockState p_getRedstoneLevel_0_, int p_getRedstoneLevel_1_) {
/* 1066 */     Block block = p_getRedstoneLevel_0_.getBlock();
/*      */     
/* 1068 */     if (!(block instanceof BlockRedstoneWire))
/*      */     {
/* 1070 */       return p_getRedstoneLevel_1_;
/*      */     }
/*      */ 
/*      */     
/* 1074 */     Object object = p_getRedstoneLevel_0_.getValue((IProperty)BlockRedstoneWire.POWER);
/*      */     
/* 1076 */     if (!(object instanceof Integer))
/*      */     {
/* 1078 */       return p_getRedstoneLevel_1_;
/*      */     }
/*      */ 
/*      */     
/* 1082 */     Integer integer = (Integer)object;
/* 1083 */     return integer.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getXpOrbTimer(float p_getXpOrbTimer_0_) {
/* 1090 */     if (xpOrbTime <= 0)
/*      */     {
/* 1092 */       return p_getXpOrbTimer_0_;
/*      */     }
/*      */ 
/*      */     
/* 1096 */     float f = 628.0F / xpOrbTime;
/* 1097 */     return p_getXpOrbTimer_0_ * f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getXpOrbColor(float p_getXpOrbColor_0_) {
/* 1103 */     if (xpOrbColors == null)
/*      */     {
/* 1105 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1109 */     int i = (int)Math.round(((MathHelper.sin(p_getXpOrbColor_0_) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
/* 1110 */     int j = xpOrbColors.getColor(i);
/* 1111 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDurabilityColor(float p_getDurabilityColor_0_, int p_getDurabilityColor_1_) {
/* 1117 */     if (durabilityColors == null)
/*      */     {
/* 1119 */       return p_getDurabilityColor_1_;
/*      */     }
/*      */ 
/*      */     
/* 1123 */     int i = (int)(p_getDurabilityColor_0_ * durabilityColors.getLength());
/* 1124 */     int j = durabilityColors.getColor(i);
/* 1125 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateWaterFX(Particle p_updateWaterFX_0_, IBlockAccess p_updateWaterFX_1_, double p_updateWaterFX_2_, double p_updateWaterFX_4_, double p_updateWaterFX_6_, RenderEnv p_updateWaterFX_8_) {
/* 1131 */     if (waterColors != null || blockColormaps != null) {
/*      */       
/* 1133 */       BlockPos blockpos = new BlockPos(p_updateWaterFX_2_, p_updateWaterFX_4_, p_updateWaterFX_6_);
/* 1134 */       p_updateWaterFX_8_.reset(p_updateWaterFX_1_, BLOCK_STATE_WATER, blockpos);
/* 1135 */       int i = getFluidColor(p_updateWaterFX_1_, BLOCK_STATE_WATER, blockpos, p_updateWaterFX_8_);
/* 1136 */       int j = i >> 16 & 0xFF;
/* 1137 */       int k = i >> 8 & 0xFF;
/* 1138 */       int l = i & 0xFF;
/* 1139 */       float f = j / 255.0F;
/* 1140 */       float f1 = k / 255.0F;
/* 1141 */       float f2 = l / 255.0F;
/*      */       
/* 1143 */       if (particleWaterColor >= 0) {
/*      */         
/* 1145 */         int i1 = particleWaterColor >> 16 & 0xFF;
/* 1146 */         int j1 = particleWaterColor >> 8 & 0xFF;
/* 1147 */         int k1 = particleWaterColor & 0xFF;
/* 1148 */         f *= i1 / 255.0F;
/* 1149 */         f1 *= j1 / 255.0F;
/* 1150 */         f2 *= k1 / 255.0F;
/*      */       } 
/*      */       
/* 1153 */       p_updateWaterFX_0_.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getLilypadColorMultiplier(IBlockAccess p_getLilypadColorMultiplier_0_, BlockPos p_getLilypadColorMultiplier_1_) {
/* 1159 */     return (lilyPadColor < 0) ? getBlockColors().colorMultiplier(Blocks.WATERLILY.getDefaultState(), p_getLilypadColorMultiplier_0_, p_getLilypadColorMultiplier_1_, 0) : lilyPadColor;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3d getFogColorNether(Vec3d p_getFogColorNether_0_) {
/* 1164 */     return (fogColorNether == null) ? p_getFogColorNether_0_ : fogColorNether;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3d getFogColorEnd(Vec3d p_getFogColorEnd_0_) {
/* 1169 */     return (fogColorEnd == null) ? p_getFogColorEnd_0_ : fogColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3d getSkyColorEnd(Vec3d p_getSkyColorEnd_0_) {
/* 1174 */     return (skyColorEnd == null) ? p_getSkyColorEnd_0_ : skyColorEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3d getSkyColor(Vec3d p_getSkyColor_0_, IBlockAccess p_getSkyColor_1_, double p_getSkyColor_2_, double p_getSkyColor_4_, double p_getSkyColor_6_) {
/* 1179 */     if (skyColors == null)
/*      */     {
/* 1181 */       return p_getSkyColor_0_;
/*      */     }
/*      */ 
/*      */     
/* 1185 */     int i = skyColors.getColorSmooth(p_getSkyColor_1_, p_getSkyColor_2_, p_getSkyColor_4_, p_getSkyColor_6_, 3);
/* 1186 */     int j = i >> 16 & 0xFF;
/* 1187 */     int k = i >> 8 & 0xFF;
/* 1188 */     int l = i & 0xFF;
/* 1189 */     float f = j / 255.0F;
/* 1190 */     float f1 = k / 255.0F;
/* 1191 */     float f2 = l / 255.0F;
/* 1192 */     float f3 = (float)p_getSkyColor_0_.xCoord / 0.5F;
/* 1193 */     float f4 = (float)p_getSkyColor_0_.yCoord / 0.66275F;
/* 1194 */     float f5 = (float)p_getSkyColor_0_.zCoord;
/* 1195 */     f *= f3;
/* 1196 */     f1 *= f4;
/* 1197 */     f2 *= f5;
/* 1198 */     Vec3d vec3d = skyColorFader.getColor(f, f1, f2);
/* 1199 */     return vec3d;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3d getFogColor(Vec3d p_getFogColor_0_, IBlockAccess p_getFogColor_1_, double p_getFogColor_2_, double p_getFogColor_4_, double p_getFogColor_6_) {
/* 1205 */     if (fogColors == null)
/*      */     {
/* 1207 */       return p_getFogColor_0_;
/*      */     }
/*      */ 
/*      */     
/* 1211 */     int i = fogColors.getColorSmooth(p_getFogColor_1_, p_getFogColor_2_, p_getFogColor_4_, p_getFogColor_6_, 3);
/* 1212 */     int j = i >> 16 & 0xFF;
/* 1213 */     int k = i >> 8 & 0xFF;
/* 1214 */     int l = i & 0xFF;
/* 1215 */     float f = j / 255.0F;
/* 1216 */     float f1 = k / 255.0F;
/* 1217 */     float f2 = l / 255.0F;
/* 1218 */     float f3 = (float)p_getFogColor_0_.xCoord / 0.753F;
/* 1219 */     float f4 = (float)p_getFogColor_0_.yCoord / 0.8471F;
/* 1220 */     float f5 = (float)p_getFogColor_0_.zCoord;
/* 1221 */     f *= f3;
/* 1222 */     f1 *= f4;
/* 1223 */     f2 *= f5;
/* 1224 */     Vec3d vec3d = fogColorFader.getColor(f, f1, f2);
/* 1225 */     return vec3d;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vec3d getUnderwaterColor(IBlockAccess p_getUnderwaterColor_0_, double p_getUnderwaterColor_1_, double p_getUnderwaterColor_3_, double p_getUnderwaterColor_5_) {
/* 1231 */     return getUnderFluidColor(p_getUnderwaterColor_0_, p_getUnderwaterColor_1_, p_getUnderwaterColor_3_, p_getUnderwaterColor_5_, underwaterColors, underwaterColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3d getUnderlavaColor(IBlockAccess p_getUnderlavaColor_0_, double p_getUnderlavaColor_1_, double p_getUnderlavaColor_3_, double p_getUnderlavaColor_5_) {
/* 1236 */     return getUnderFluidColor(p_getUnderlavaColor_0_, p_getUnderlavaColor_1_, p_getUnderlavaColor_3_, p_getUnderlavaColor_5_, underlavaColors, underlavaColorFader);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3d getUnderFluidColor(IBlockAccess p_getUnderFluidColor_0_, double p_getUnderFluidColor_1_, double p_getUnderFluidColor_3_, double p_getUnderFluidColor_5_, CustomColormap p_getUnderFluidColor_7_, CustomColorFader p_getUnderFluidColor_8_) {
/* 1241 */     if (p_getUnderFluidColor_7_ == null)
/*      */     {
/* 1243 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1247 */     int i = p_getUnderFluidColor_7_.getColorSmooth(p_getUnderFluidColor_0_, p_getUnderFluidColor_1_, p_getUnderFluidColor_3_, p_getUnderFluidColor_5_, 3);
/* 1248 */     int j = i >> 16 & 0xFF;
/* 1249 */     int k = i >> 8 & 0xFF;
/* 1250 */     int l = i & 0xFF;
/* 1251 */     float f = j / 255.0F;
/* 1252 */     float f1 = k / 255.0F;
/* 1253 */     float f2 = l / 255.0F;
/* 1254 */     Vec3d vec3d = p_getUnderFluidColor_8_.getColor(f, f1, f2);
/* 1255 */     return vec3d;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getStemColorMultiplier(Block p_getStemColorMultiplier_0_, IBlockAccess p_getStemColorMultiplier_1_, BlockPos p_getStemColorMultiplier_2_, RenderEnv p_getStemColorMultiplier_3_) {
/* 1261 */     CustomColormap customcolormap = stemColors;
/*      */     
/* 1263 */     if (p_getStemColorMultiplier_0_ == Blocks.PUMPKIN_STEM && stemPumpkinColors != null)
/*      */     {
/* 1265 */       customcolormap = stemPumpkinColors;
/*      */     }
/*      */     
/* 1268 */     if (p_getStemColorMultiplier_0_ == Blocks.MELON_STEM && stemMelonColors != null)
/*      */     {
/* 1270 */       customcolormap = stemMelonColors;
/*      */     }
/*      */     
/* 1273 */     if (customcolormap == null)
/*      */     {
/* 1275 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1279 */     int i = p_getStemColorMultiplier_3_.getMetadata();
/* 1280 */     return customcolormap.getColor(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean updateLightmap(World p_updateLightmap_0_, float p_updateLightmap_1_, int[] p_updateLightmap_2_, boolean p_updateLightmap_3_) {
/* 1286 */     if (p_updateLightmap_0_ == null)
/*      */     {
/* 1288 */       return false;
/*      */     }
/* 1290 */     if (lightMapsColorsRgb == null)
/*      */     {
/* 1292 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1296 */     int i = p_updateLightmap_0_.provider.getDimensionType().getId();
/* 1297 */     int j = i - lightmapMinDimensionId;
/*      */     
/* 1299 */     if (j >= 0 && j < lightMapsColorsRgb.length) {
/*      */       
/* 1301 */       CustomColormap customcolormap = lightMapsColorsRgb[j];
/*      */       
/* 1303 */       if (customcolormap == null)
/*      */       {
/* 1305 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1309 */       int k = customcolormap.getHeight();
/*      */       
/* 1311 */       if (p_updateLightmap_3_ && k < 64)
/*      */       {
/* 1313 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1317 */       int l = customcolormap.getWidth();
/*      */       
/* 1319 */       if (l < 16) {
/*      */         
/* 1321 */         warn("Invalid lightmap width: " + l + " for dimension: " + i);
/* 1322 */         lightMapsColorsRgb[j] = null;
/* 1323 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1327 */       int i1 = 0;
/*      */       
/* 1329 */       if (p_updateLightmap_3_)
/*      */       {
/* 1331 */         i1 = l * 16 * 2;
/*      */       }
/*      */       
/* 1334 */       float f = 1.1666666F * (p_updateLightmap_0_.getSunBrightness(1.0F) - 0.2F);
/*      */       
/* 1336 */       if (p_updateLightmap_0_.getLastLightningBolt() > 0)
/*      */       {
/* 1338 */         f = 1.0F;
/*      */       }
/*      */       
/* 1341 */       f = Config.limitTo1(f);
/* 1342 */       float f1 = f * (l - 1);
/* 1343 */       float f2 = Config.limitTo1(p_updateLightmap_1_ + 0.5F) * (l - 1);
/* 1344 */       float f3 = Config.limitTo1((Config.getGameSettings()).gammaSetting);
/* 1345 */       boolean flag = (f3 > 1.0E-4F);
/* 1346 */       float[][] afloat = customcolormap.getColorsRgb();
/* 1347 */       getLightMapColumn(afloat, f1, i1, l, sunRgbs);
/* 1348 */       getLightMapColumn(afloat, f2, i1 + 16 * l, l, torchRgbs);
/* 1349 */       float[] afloat1 = new float[3];
/*      */       
/* 1351 */       for (int j1 = 0; j1 < 16; j1++) {
/*      */         
/* 1353 */         for (int k1 = 0; k1 < 16; k1++) {
/*      */           
/* 1355 */           for (int l1 = 0; l1 < 3; l1++) {
/*      */             
/* 1357 */             float f4 = Config.limitTo1(sunRgbs[j1][l1] + torchRgbs[k1][l1]);
/*      */             
/* 1359 */             if (flag) {
/*      */               
/* 1361 */               float f5 = 1.0F - f4;
/* 1362 */               f5 = 1.0F - f5 * f5 * f5 * f5;
/* 1363 */               f4 = f3 * f5 + (1.0F - f3) * f4;
/*      */             } 
/*      */             
/* 1366 */             afloat1[l1] = f4;
/*      */           } 
/*      */           
/* 1369 */           int i2 = (int)(afloat1[0] * 255.0F);
/* 1370 */           int j2 = (int)(afloat1[1] * 255.0F);
/* 1371 */           int k2 = (int)(afloat1[2] * 255.0F);
/* 1372 */           p_updateLightmap_2_[j1 * 16 + k1] = 0xFF000000 | i2 << 16 | j2 << 8 | k2;
/*      */         } 
/*      */       } 
/*      */       
/* 1376 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1383 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void getLightMapColumn(float[][] p_getLightMapColumn_0_, float p_getLightMapColumn_1_, int p_getLightMapColumn_2_, int p_getLightMapColumn_3_, float[][] p_getLightMapColumn_4_) {
/* 1390 */     int i = (int)Math.floor(p_getLightMapColumn_1_);
/* 1391 */     int j = (int)Math.ceil(p_getLightMapColumn_1_);
/*      */     
/* 1393 */     if (i == j) {
/*      */       
/* 1395 */       for (int i1 = 0; i1 < 16; i1++)
/*      */       {
/* 1397 */         float[] afloat3 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + i1 * p_getLightMapColumn_3_ + i];
/* 1398 */         float[] afloat4 = p_getLightMapColumn_4_[i1];
/*      */         
/* 1400 */         for (int j1 = 0; j1 < 3; j1++)
/*      */         {
/* 1402 */           afloat4[j1] = afloat3[j1];
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1408 */       float f = 1.0F - p_getLightMapColumn_1_ - i;
/* 1409 */       float f1 = 1.0F - j - p_getLightMapColumn_1_;
/*      */       
/* 1411 */       for (int k = 0; k < 16; k++) {
/*      */         
/* 1413 */         float[] afloat = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + i];
/* 1414 */         float[] afloat1 = p_getLightMapColumn_0_[p_getLightMapColumn_2_ + k * p_getLightMapColumn_3_ + j];
/* 1415 */         float[] afloat2 = p_getLightMapColumn_4_[k];
/*      */         
/* 1417 */         for (int l = 0; l < 3; l++)
/*      */         {
/* 1419 */           afloat2[l] = afloat[l] * f + afloat1[l] * f1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Vec3d getWorldFogColor(Vec3d p_getWorldFogColor_0_, World p_getWorldFogColor_1_, Entity p_getWorldFogColor_2_, float p_getWorldFogColor_3_) {
/*      */     Minecraft minecraft;
/* 1427 */     DimensionType dimensiontype = p_getWorldFogColor_1_.provider.getDimensionType();
/*      */     
/* 1429 */     switch (dimensiontype) {
/*      */       
/*      */       case null:
/* 1432 */         p_getWorldFogColor_0_ = getFogColorNether(p_getWorldFogColor_0_);
/*      */         break;
/*      */       
/*      */       case OVERWORLD:
/* 1436 */         minecraft = Minecraft.getMinecraft();
/* 1437 */         p_getWorldFogColor_0_ = getFogColor(p_getWorldFogColor_0_, (IBlockAccess)minecraft.world, p_getWorldFogColor_2_.posX, p_getWorldFogColor_2_.posY + 1.0D, p_getWorldFogColor_2_.posZ);
/*      */         break;
/*      */       
/*      */       case THE_END:
/* 1441 */         p_getWorldFogColor_0_ = getFogColorEnd(p_getWorldFogColor_0_);
/*      */         break;
/*      */     } 
/* 1444 */     return p_getWorldFogColor_0_;
/*      */   }
/*      */   
/*      */   public static Vec3d getWorldSkyColor(Vec3d p_getWorldSkyColor_0_, World p_getWorldSkyColor_1_, Entity p_getWorldSkyColor_2_, float p_getWorldSkyColor_3_) {
/*      */     Minecraft minecraft;
/* 1449 */     DimensionType dimensiontype = p_getWorldSkyColor_1_.provider.getDimensionType();
/*      */     
/* 1451 */     switch (dimensiontype) {
/*      */       
/*      */       case OVERWORLD:
/* 1454 */         minecraft = Minecraft.getMinecraft();
/* 1455 */         p_getWorldSkyColor_0_ = getSkyColor(p_getWorldSkyColor_0_, (IBlockAccess)minecraft.world, p_getWorldSkyColor_2_.posX, p_getWorldSkyColor_2_.posY + 1.0D, p_getWorldSkyColor_2_.posZ);
/*      */         break;
/*      */       
/*      */       case THE_END:
/* 1459 */         p_getWorldSkyColor_0_ = getSkyColorEnd(p_getWorldSkyColor_0_);
/*      */         break;
/*      */     } 
/* 1462 */     return p_getWorldSkyColor_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readSpawnEggColors(Properties p_readSpawnEggColors_0_, String p_readSpawnEggColors_1_, String p_readSpawnEggColors_2_, String p_readSpawnEggColors_3_) {
/* 1467 */     List<Integer> list = new ArrayList<>();
/* 1468 */     Set set = p_readSpawnEggColors_0_.keySet();
/* 1469 */     int i = 0;
/*      */     
/* 1471 */     for (Object s : set) {
/*      */       
/* 1473 */       String s1 = p_readSpawnEggColors_0_.getProperty((String)s);
/*      */       
/* 1475 */       if (((String)s).startsWith(p_readSpawnEggColors_2_)) {
/*      */         
/* 1477 */         String s2 = StrUtils.removePrefix((String)s, p_readSpawnEggColors_2_);
/* 1478 */         int j = EntityUtils.getEntityIdByName(s2);
/*      */         
/* 1480 */         if (j < 0)
/*      */         {
/* 1482 */           j = EntityUtils.getEntityIdByLocation((new ResourceLocation(s2)).toString());
/*      */         }
/*      */         
/* 1485 */         if (j < 0) {
/*      */           
/* 1487 */           warn("Invalid spawn egg name: " + s);
/*      */           
/*      */           continue;
/*      */         } 
/* 1491 */         int k = parseColor(s1);
/*      */         
/* 1493 */         if (k < 0) {
/*      */           
/* 1495 */           warn("Invalid spawn egg color: " + s + " = " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/* 1499 */         while (list.size() <= j)
/*      */         {
/* 1501 */           list.add(Integer.valueOf(-1));
/*      */         }
/*      */         
/* 1504 */         list.set(j, Integer.valueOf(k));
/* 1505 */         i++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1511 */     if (i <= 0)
/*      */     {
/* 1513 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1517 */     dbg(String.valueOf(p_readSpawnEggColors_3_) + " colors: " + i);
/* 1518 */     int[] aint = new int[list.size()];
/*      */     
/* 1520 */     for (int l = 0; l < aint.length; l++)
/*      */     {
/* 1522 */       aint[l] = ((Integer)list.get(l)).intValue();
/*      */     }
/*      */     
/* 1525 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSpawnEggColor(ItemMonsterPlacer p_getSpawnEggColor_0_, ItemStack p_getSpawnEggColor_1_, int p_getSpawnEggColor_2_, int p_getSpawnEggColor_3_) {
/* 1531 */     if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null)
/*      */     {
/* 1533 */       return p_getSpawnEggColor_3_;
/*      */     }
/*      */ 
/*      */     
/* 1537 */     NBTTagCompound nbttagcompound = p_getSpawnEggColor_1_.getTagCompound();
/*      */     
/* 1539 */     if (nbttagcompound == null)
/*      */     {
/* 1541 */       return p_getSpawnEggColor_3_;
/*      */     }
/*      */ 
/*      */     
/* 1545 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
/*      */     
/* 1547 */     if (nbttagcompound1 == null)
/*      */     {
/* 1549 */       return p_getSpawnEggColor_3_;
/*      */     }
/*      */ 
/*      */     
/* 1553 */     String s = nbttagcompound1.getString("id");
/* 1554 */     int i = EntityUtils.getEntityIdByLocation(s);
/* 1555 */     int[] aint = (p_getSpawnEggColor_2_ == 0) ? spawnEggPrimaryColors : spawnEggSecondaryColors;
/*      */     
/* 1557 */     if (aint == null)
/*      */     {
/* 1559 */       return p_getSpawnEggColor_3_;
/*      */     }
/* 1561 */     if (i >= 0 && i < aint.length) {
/*      */       
/* 1563 */       int j = aint[i];
/* 1564 */       return (j < 0) ? p_getSpawnEggColor_3_ : j;
/*      */     } 
/*      */ 
/*      */     
/* 1568 */     return p_getSpawnEggColor_3_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getColorFromItemStack(ItemStack p_getColorFromItemStack_0_, int p_getColorFromItemStack_1_, int p_getColorFromItemStack_2_) {
/* 1577 */     if (p_getColorFromItemStack_0_ == null)
/*      */     {
/* 1579 */       return p_getColorFromItemStack_2_;
/*      */     }
/*      */ 
/*      */     
/* 1583 */     Item item = p_getColorFromItemStack_0_.getItem();
/*      */     
/* 1585 */     if (item == null)
/*      */     {
/* 1587 */       return p_getColorFromItemStack_2_;
/*      */     }
/*      */ 
/*      */     
/* 1591 */     return (item instanceof ItemMonsterPlacer) ? getSpawnEggColor((ItemMonsterPlacer)item, p_getColorFromItemStack_0_, p_getColorFromItemStack_1_, p_getColorFromItemStack_2_) : p_getColorFromItemStack_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[][] readDyeColors(Properties p_readDyeColors_0_, String p_readDyeColors_1_, String p_readDyeColors_2_, String p_readDyeColors_3_) {
/* 1598 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/* 1599 */     Map<String, EnumDyeColor> map = new HashMap<>();
/*      */     
/* 1601 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*      */       
/* 1603 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/* 1604 */       map.put(enumdyecolor.getName(), enumdyecolor);
/*      */     } 
/*      */     
/* 1607 */     float[][] afloat1 = new float[aenumdyecolor.length][];
/* 1608 */     int k = 0;
/*      */     
/* 1610 */     for (Object s : p_readDyeColors_0_.keySet()) {
/*      */       
/* 1612 */       String s1 = p_readDyeColors_0_.getProperty((String)s);
/*      */       
/* 1614 */       if (((String)s).startsWith(p_readDyeColors_2_)) {
/*      */         
/* 1616 */         String s2 = StrUtils.removePrefix((String)s, p_readDyeColors_2_);
/*      */         
/* 1618 */         if (s2.equals("lightBlue"))
/*      */         {
/* 1620 */           s2 = "light_blue";
/*      */         }
/*      */         
/* 1623 */         EnumDyeColor enumdyecolor1 = map.get(s2);
/* 1624 */         int j = parseColor(s1);
/*      */         
/* 1626 */         if (enumdyecolor1 != null && j >= 0) {
/*      */           
/* 1628 */           float[] afloat = { (j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F };
/* 1629 */           afloat1[enumdyecolor1.ordinal()] = afloat;
/* 1630 */           k++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1634 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1639 */     if (k <= 0)
/*      */     {
/* 1641 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1645 */     dbg(String.valueOf(p_readDyeColors_3_) + " colors: " + k);
/* 1646 */     return afloat1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] getDyeColors(EnumDyeColor p_getDyeColors_0_, float[][] p_getDyeColors_1_, float[] p_getDyeColors_2_) {
/* 1652 */     if (p_getDyeColors_1_ == null)
/*      */     {
/* 1654 */       return p_getDyeColors_2_;
/*      */     }
/* 1656 */     if (p_getDyeColors_0_ == null)
/*      */     {
/* 1658 */       return p_getDyeColors_2_;
/*      */     }
/*      */ 
/*      */     
/* 1662 */     float[] afloat = p_getDyeColors_1_[p_getDyeColors_0_.ordinal()];
/* 1663 */     return (afloat == null) ? p_getDyeColors_2_ : afloat;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] getWolfCollarColors(EnumDyeColor p_getWolfCollarColors_0_, float[] p_getWolfCollarColors_1_) {
/* 1669 */     return getDyeColors(p_getWolfCollarColors_0_, wolfCollarColors, p_getWolfCollarColors_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float[] getSheepColors(EnumDyeColor p_getSheepColors_0_, float[] p_getSheepColors_1_) {
/* 1674 */     return getDyeColors(p_getSheepColors_0_, sheepColors, p_getSheepColors_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readTextColors(Properties p_readTextColors_0_, String p_readTextColors_1_, String p_readTextColors_2_, String p_readTextColors_3_) {
/* 1679 */     int[] aint = new int[32];
/* 1680 */     Arrays.fill(aint, -1);
/* 1681 */     int i = 0;
/*      */     
/* 1683 */     for (Object s : p_readTextColors_0_.keySet()) {
/*      */       
/* 1685 */       String s1 = p_readTextColors_0_.getProperty((String)s);
/*      */       
/* 1687 */       if (((String)s).startsWith(p_readTextColors_2_)) {
/*      */         
/* 1689 */         String s2 = StrUtils.removePrefix((String)s, p_readTextColors_2_);
/* 1690 */         int j = Config.parseInt(s2, -1);
/* 1691 */         int k = parseColor(s1);
/*      */         
/* 1693 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1695 */           aint[j] = k;
/* 1696 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1700 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1705 */     if (i <= 0)
/*      */     {
/* 1707 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1711 */     dbg(String.valueOf(p_readTextColors_3_) + " colors: " + i);
/* 1712 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getTextColor(int p_getTextColor_0_, int p_getTextColor_1_) {
/* 1718 */     if (textColors == null)
/*      */     {
/* 1720 */       return p_getTextColor_1_;
/*      */     }
/* 1722 */     if (p_getTextColor_0_ >= 0 && p_getTextColor_0_ < textColors.length) {
/*      */       
/* 1724 */       int i = textColors[p_getTextColor_0_];
/* 1725 */       return (i < 0) ? p_getTextColor_1_ : i;
/*      */     } 
/*      */ 
/*      */     
/* 1729 */     return p_getTextColor_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readMapColors(Properties p_readMapColors_0_, String p_readMapColors_1_, String p_readMapColors_2_, String p_readMapColors_3_) {
/* 1735 */     int[] aint = new int[MapColor.COLORS.length];
/* 1736 */     Arrays.fill(aint, -1);
/* 1737 */     int i = 0;
/*      */     
/* 1739 */     for (Object s : p_readMapColors_0_.keySet()) {
/*      */       
/* 1741 */       String s1 = p_readMapColors_0_.getProperty((String)s);
/*      */       
/* 1743 */       if (((String)s).startsWith(p_readMapColors_2_)) {
/*      */         
/* 1745 */         String s2 = StrUtils.removePrefix((String)s, p_readMapColors_2_);
/* 1746 */         int j = getMapColorIndex(s2);
/* 1747 */         int k = parseColor(s1);
/*      */         
/* 1749 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1751 */           aint[j] = k;
/* 1752 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1756 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1761 */     if (i <= 0)
/*      */     {
/* 1763 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1767 */     dbg(String.valueOf(p_readMapColors_3_) + " colors: " + i);
/* 1768 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] readPotionColors(Properties p_readPotionColors_0_, String p_readPotionColors_1_, String p_readPotionColors_2_, String p_readPotionColors_3_) {
/* 1774 */     int[] aint = new int[getMaxPotionId()];
/* 1775 */     Arrays.fill(aint, -1);
/* 1776 */     int i = 0;
/*      */     
/* 1778 */     for (Object s : p_readPotionColors_0_.keySet()) {
/*      */       
/* 1780 */       String s1 = p_readPotionColors_0_.getProperty((String)s);
/*      */       
/* 1782 */       if (((String)s).startsWith(p_readPotionColors_2_)) {
/*      */         
/* 1784 */         int j = getPotionId((String)s);
/* 1785 */         int k = parseColor(s1);
/*      */         
/* 1787 */         if (j >= 0 && j < aint.length && k >= 0) {
/*      */           
/* 1789 */           aint[j] = k;
/* 1790 */           i++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1794 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1799 */     if (i <= 0)
/*      */     {
/* 1801 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1805 */     dbg(String.valueOf(p_readPotionColors_3_) + " colors: " + i);
/* 1806 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getMaxPotionId() {
/* 1812 */     int i = 0;
/*      */     
/* 1814 */     for (ResourceLocation resourcelocation : Potion.REGISTRY.getKeys()) {
/*      */       
/* 1816 */       Potion potion = (Potion)Potion.REGISTRY.getObject(resourcelocation);
/* 1817 */       int j = Potion.getIdFromPotion(potion);
/*      */       
/* 1819 */       if (j > i)
/*      */       {
/* 1821 */         i = j;
/*      */       }
/*      */     } 
/*      */     
/* 1825 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionId(String p_getPotionId_0_) {
/* 1830 */     if (p_getPotionId_0_.equals("potion.water"))
/*      */     {
/* 1832 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 1836 */     p_getPotionId_0_ = StrUtils.replacePrefix(p_getPotionId_0_, "potion.", "effect.");
/*      */     
/* 1838 */     for (ResourceLocation resourcelocation : Potion.REGISTRY.getKeys()) {
/*      */       
/* 1840 */       Potion potion = (Potion)Potion.REGISTRY.getObject(resourcelocation);
/*      */       
/* 1842 */       if (potion.getName().equals(p_getPotionId_0_))
/*      */       {
/* 1844 */         return Potion.getIdFromPotion(potion);
/*      */       }
/*      */     } 
/*      */     
/* 1848 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getPotionColor(Potion p_getPotionColor_0_, int p_getPotionColor_1_) {
/* 1854 */     int i = 0;
/*      */     
/* 1856 */     if (p_getPotionColor_0_ != null)
/*      */     {
/* 1858 */       i = Potion.getIdFromPotion(p_getPotionColor_0_);
/*      */     }
/*      */     
/* 1861 */     return getPotionColor(i, p_getPotionColor_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getPotionColor(int p_getPotionColor_0_, int p_getPotionColor_1_) {
/* 1866 */     if (potionColors == null)
/*      */     {
/* 1868 */       return p_getPotionColor_1_;
/*      */     }
/* 1870 */     if (p_getPotionColor_0_ >= 0 && p_getPotionColor_0_ < potionColors.length) {
/*      */       
/* 1872 */       int i = potionColors[p_getPotionColor_0_];
/* 1873 */       return (i < 0) ? p_getPotionColor_1_ : i;
/*      */     } 
/*      */ 
/*      */     
/* 1877 */     return p_getPotionColor_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getMapColorIndex(String p_getMapColorIndex_0_) {
/* 1883 */     if (p_getMapColorIndex_0_ == null)
/*      */     {
/* 1885 */       return -1;
/*      */     }
/* 1887 */     if (p_getMapColorIndex_0_.equals("air"))
/*      */     {
/* 1889 */       return MapColor.AIR.colorIndex;
/*      */     }
/* 1891 */     if (p_getMapColorIndex_0_.equals("grass"))
/*      */     {
/* 1893 */       return MapColor.GRASS.colorIndex;
/*      */     }
/* 1895 */     if (p_getMapColorIndex_0_.equals("sand"))
/*      */     {
/* 1897 */       return MapColor.SAND.colorIndex;
/*      */     }
/* 1899 */     if (p_getMapColorIndex_0_.equals("cloth"))
/*      */     {
/* 1901 */       return MapColor.CLOTH.colorIndex;
/*      */     }
/* 1903 */     if (p_getMapColorIndex_0_.equals("tnt"))
/*      */     {
/* 1905 */       return MapColor.TNT.colorIndex;
/*      */     }
/* 1907 */     if (p_getMapColorIndex_0_.equals("ice"))
/*      */     {
/* 1909 */       return MapColor.ICE.colorIndex;
/*      */     }
/* 1911 */     if (p_getMapColorIndex_0_.equals("iron"))
/*      */     {
/* 1913 */       return MapColor.IRON.colorIndex;
/*      */     }
/* 1915 */     if (p_getMapColorIndex_0_.equals("foliage"))
/*      */     {
/* 1917 */       return MapColor.FOLIAGE.colorIndex;
/*      */     }
/* 1919 */     if (p_getMapColorIndex_0_.equals("clay"))
/*      */     {
/* 1921 */       return MapColor.CLAY.colorIndex;
/*      */     }
/* 1923 */     if (p_getMapColorIndex_0_.equals("dirt"))
/*      */     {
/* 1925 */       return MapColor.DIRT.colorIndex;
/*      */     }
/* 1927 */     if (p_getMapColorIndex_0_.equals("stone"))
/*      */     {
/* 1929 */       return MapColor.STONE.colorIndex;
/*      */     }
/* 1931 */     if (p_getMapColorIndex_0_.equals("water"))
/*      */     {
/* 1933 */       return MapColor.WATER.colorIndex;
/*      */     }
/* 1935 */     if (p_getMapColorIndex_0_.equals("wood"))
/*      */     {
/* 1937 */       return MapColor.WOOD.colorIndex;
/*      */     }
/* 1939 */     if (p_getMapColorIndex_0_.equals("quartz"))
/*      */     {
/* 1941 */       return MapColor.QUARTZ.colorIndex;
/*      */     }
/* 1943 */     if (p_getMapColorIndex_0_.equals("gold"))
/*      */     {
/* 1945 */       return MapColor.GOLD.colorIndex;
/*      */     }
/* 1947 */     if (p_getMapColorIndex_0_.equals("diamond"))
/*      */     {
/* 1949 */       return MapColor.DIAMOND.colorIndex;
/*      */     }
/* 1951 */     if (p_getMapColorIndex_0_.equals("lapis"))
/*      */     {
/* 1953 */       return MapColor.LAPIS.colorIndex;
/*      */     }
/* 1955 */     if (p_getMapColorIndex_0_.equals("emerald"))
/*      */     {
/* 1957 */       return MapColor.EMERALD.colorIndex;
/*      */     }
/* 1959 */     if (p_getMapColorIndex_0_.equals("podzol"))
/*      */     {
/* 1961 */       return MapColor.OBSIDIAN.colorIndex;
/*      */     }
/* 1963 */     if (p_getMapColorIndex_0_.equals("netherrack"))
/*      */     {
/* 1965 */       return MapColor.NETHERRACK.colorIndex;
/*      */     }
/* 1967 */     if (!p_getMapColorIndex_0_.equals("snow") && !p_getMapColorIndex_0_.equals("white")) {
/*      */       
/* 1969 */       if (!p_getMapColorIndex_0_.equals("adobe") && !p_getMapColorIndex_0_.equals("orange")) {
/*      */         
/* 1971 */         if (p_getMapColorIndex_0_.equals("magenta"))
/*      */         {
/* 1973 */           return MapColor.MAGENTA.colorIndex;
/*      */         }
/* 1975 */         if (!p_getMapColorIndex_0_.equals("light_blue") && !p_getMapColorIndex_0_.equals("lightBlue")) {
/*      */           
/* 1977 */           if (p_getMapColorIndex_0_.equals("yellow"))
/*      */           {
/* 1979 */             return MapColor.YELLOW.colorIndex;
/*      */           }
/* 1981 */           if (p_getMapColorIndex_0_.equals("lime"))
/*      */           {
/* 1983 */             return MapColor.LIME.colorIndex;
/*      */           }
/* 1985 */           if (p_getMapColorIndex_0_.equals("pink"))
/*      */           {
/* 1987 */             return MapColor.PINK.colorIndex;
/*      */           }
/* 1989 */           if (p_getMapColorIndex_0_.equals("gray"))
/*      */           {
/* 1991 */             return MapColor.GRAY.colorIndex;
/*      */           }
/* 1993 */           if (p_getMapColorIndex_0_.equals("silver"))
/*      */           {
/* 1995 */             return MapColor.SILVER.colorIndex;
/*      */           }
/* 1997 */           if (p_getMapColorIndex_0_.equals("cyan"))
/*      */           {
/* 1999 */             return MapColor.CYAN.colorIndex;
/*      */           }
/* 2001 */           if (p_getMapColorIndex_0_.equals("purple"))
/*      */           {
/* 2003 */             return MapColor.PURPLE.colorIndex;
/*      */           }
/* 2005 */           if (p_getMapColorIndex_0_.equals("blue"))
/*      */           {
/* 2007 */             return MapColor.BLUE.colorIndex;
/*      */           }
/* 2009 */           if (p_getMapColorIndex_0_.equals("brown"))
/*      */           {
/* 2011 */             return MapColor.BROWN.colorIndex;
/*      */           }
/* 2013 */           if (p_getMapColorIndex_0_.equals("green"))
/*      */           {
/* 2015 */             return MapColor.GREEN.colorIndex;
/*      */           }
/* 2017 */           if (p_getMapColorIndex_0_.equals("red"))
/*      */           {
/* 2019 */             return MapColor.RED.colorIndex;
/*      */           }
/*      */ 
/*      */           
/* 2023 */           return p_getMapColorIndex_0_.equals("black") ? MapColor.BLACK.colorIndex : -1;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2028 */         return MapColor.LIGHT_BLUE.colorIndex;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2033 */       return MapColor.ADOBE.colorIndex;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2038 */     return MapColor.SNOW.colorIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] getMapColors() {
/* 2044 */     MapColor[] amapcolor = MapColor.COLORS;
/* 2045 */     int[] aint = new int[amapcolor.length];
/* 2046 */     Arrays.fill(aint, -1);
/*      */     
/* 2048 */     for (int i = 0; i < amapcolor.length && i < aint.length; i++) {
/*      */       
/* 2050 */       MapColor mapcolor = amapcolor[i];
/*      */       
/* 2052 */       if (mapcolor != null)
/*      */       {
/* 2054 */         aint[i] = mapcolor.colorValue;
/*      */       }
/*      */     } 
/*      */     
/* 2058 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setMapColors(int[] p_setMapColors_0_) {
/* 2063 */     if (p_setMapColors_0_ != null) {
/*      */       
/* 2065 */       MapColor[] amapcolor = MapColor.COLORS;
/* 2066 */       boolean flag = false;
/*      */       
/* 2068 */       for (int i = 0; i < amapcolor.length && i < p_setMapColors_0_.length; i++) {
/*      */         
/* 2070 */         MapColor mapcolor = amapcolor[i];
/*      */         
/* 2072 */         if (mapcolor != null) {
/*      */           
/* 2074 */           int j = p_setMapColors_0_[i];
/*      */           
/* 2076 */           if (j >= 0 && mapcolor.colorValue != j) {
/*      */             
/* 2078 */             mapcolor.colorValue = j;
/* 2079 */             flag = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2084 */       if (flag)
/*      */       {
/* 2086 */         Minecraft.getMinecraft().getTextureManager().reloadBannerTextures();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbg(String p_dbg_0_) {
/* 2093 */     Config.dbg("CustomColors: " + p_dbg_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void warn(String p_warn_0_) {
/* 2098 */     Config.warn("CustomColors: " + p_warn_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getExpBarTextColor(int p_getExpBarTextColor_0_) {
/* 2103 */     return (expBarTextColor < 0) ? p_getExpBarTextColor_0_ : expBarTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBossTextColor(int p_getBossTextColor_0_) {
/* 2108 */     return (bossTextColor < 0) ? p_getBossTextColor_0_ : bossTextColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getSignTextColor(int p_getSignTextColor_0_) {
/* 2113 */     return (signTextColor < 0) ? p_getSignTextColor_0_ : signTextColor;
/*      */   }
/*      */   
/*      */   public static interface IColorizer {
/*      */     int getColor(IBlockState param1IBlockState, IBlockAccess param1IBlockAccess, BlockPos param1BlockPos);
/*      */     
/*      */     boolean isColorConstant();
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
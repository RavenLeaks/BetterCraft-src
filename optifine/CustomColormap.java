/*     */ package optifine;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class CustomColormap
/*     */   implements CustomColors.IColorizer {
/*  24 */   public String name = null;
/*  25 */   public String basePath = null;
/*  26 */   private int format = -1;
/*  27 */   private MatchBlock[] matchBlocks = null;
/*  28 */   private String source = null;
/*  29 */   private int color = -1;
/*  30 */   private int yVariance = 0;
/*  31 */   private int yOffset = 0;
/*  32 */   private int width = 0;
/*  33 */   private int height = 0;
/*  34 */   private int[] colors = null;
/*  35 */   private float[][] colorsRgb = null;
/*     */   private static final int FORMAT_UNKNOWN = -1;
/*     */   private static final int FORMAT_VANILLA = 0;
/*     */   private static final int FORMAT_GRID = 1;
/*     */   private static final int FORMAT_FIXED = 2;
/*     */   public static final String FORMAT_VANILLA_STRING = "vanilla";
/*     */   public static final String FORMAT_GRID_STRING = "grid";
/*     */   public static final String FORMAT_FIXED_STRING = "fixed";
/*  43 */   public static final String[] FORMAT_STRINGS = new String[] { "vanilla", "grid", "fixed" };
/*     */   
/*     */   public static final String KEY_FORMAT = "format";
/*     */   public static final String KEY_BLOCKS = "blocks";
/*     */   public static final String KEY_SOURCE = "source";
/*     */   public static final String KEY_COLOR = "color";
/*     */   public static final String KEY_Y_VARIANCE = "yVariance";
/*     */   public static final String KEY_Y_OFFSET = "yOffset";
/*     */   
/*     */   public CustomColormap(Properties p_i29_1_, String p_i29_2_, int p_i29_3_, int p_i29_4_, String p_i29_5_) {
/*  53 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/*  54 */     this.name = connectedparser.parseName(p_i29_2_);
/*  55 */     this.basePath = connectedparser.parseBasePath(p_i29_2_);
/*  56 */     this.format = parseFormat(p_i29_1_.getProperty("format", p_i29_5_));
/*  57 */     this.matchBlocks = connectedparser.parseMatchBlocks(p_i29_1_.getProperty("blocks"));
/*  58 */     this.source = parseTexture(p_i29_1_.getProperty("source"), p_i29_2_, this.basePath);
/*  59 */     this.color = ConnectedParser.parseColor(p_i29_1_.getProperty("color"), -1);
/*  60 */     this.yVariance = connectedparser.parseInt(p_i29_1_.getProperty("yVariance"), 0);
/*  61 */     this.yOffset = connectedparser.parseInt(p_i29_1_.getProperty("yOffset"), 0);
/*  62 */     this.width = p_i29_3_;
/*  63 */     this.height = p_i29_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   private int parseFormat(String p_parseFormat_1_) {
/*  68 */     if (p_parseFormat_1_ == null)
/*     */     {
/*  70 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  74 */     p_parseFormat_1_ = p_parseFormat_1_.trim();
/*     */     
/*  76 */     if (p_parseFormat_1_.equals("vanilla"))
/*     */     {
/*  78 */       return 0;
/*     */     }
/*  80 */     if (p_parseFormat_1_.equals("grid"))
/*     */     {
/*  82 */       return 1;
/*     */     }
/*  84 */     if (p_parseFormat_1_.equals("fixed"))
/*     */     {
/*  86 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*  90 */     warn("Unknown format: " + p_parseFormat_1_);
/*  91 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String p_isValid_1_) {
/*  98 */     if (this.format != 0 && this.format != 1) {
/*     */       
/* 100 */       if (this.format != 2)
/*     */       {
/* 102 */         return false;
/*     */       }
/*     */       
/* 105 */       if (this.color < 0)
/*     */       {
/* 107 */         this.color = 16777215;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 112 */       if (this.source == null) {
/*     */         
/* 114 */         warn("Source not defined: " + p_isValid_1_);
/* 115 */         return false;
/*     */       } 
/*     */       
/* 118 */       readColors();
/*     */       
/* 120 */       if (this.colors == null)
/*     */       {
/* 122 */         return false;
/*     */       }
/*     */       
/* 125 */       if (this.color < 0) {
/*     */         
/* 127 */         if (this.format == 0)
/*     */         {
/* 129 */           this.color = getColor(127, 127);
/*     */         }
/*     */         
/* 132 */         if (this.format == 1)
/*     */         {
/* 134 */           this.color = getColorGrid(Biomes.PLAINS, new BlockPos(0, 64, 0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidMatchBlocks(String p_isValidMatchBlocks_1_) {
/* 144 */     if (this.matchBlocks == null) {
/*     */       
/* 146 */       this.matchBlocks = detectMatchBlocks();
/*     */       
/* 148 */       if (this.matchBlocks == null) {
/*     */         
/* 150 */         warn("Match blocks not defined: " + p_isValidMatchBlocks_1_);
/* 151 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock[] detectMatchBlocks() {
/* 160 */     Block block = Block.getBlockFromName(this.name);
/*     */     
/* 162 */     if (block != null)
/*     */     {
/* 164 */       return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
/*     */     }
/*     */ 
/*     */     
/* 168 */     Pattern pattern = Pattern.compile("^block([0-9]+).*$");
/* 169 */     Matcher matcher = pattern.matcher(this.name);
/*     */     
/* 171 */     if (matcher.matches()) {
/*     */       
/* 173 */       String s = matcher.group(1);
/* 174 */       int i = Config.parseInt(s, -1);
/*     */       
/* 176 */       if (i >= 0)
/*     */       {
/* 178 */         return new MatchBlock[] { new MatchBlock(i) };
/*     */       }
/*     */     } 
/*     */     
/* 182 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/* 183 */     MatchBlock[] amatchblock = connectedparser.parseMatchBlock(this.name);
/* 184 */     return (amatchblock != null) ? amatchblock : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readColors() {
/*     */     try {
/* 192 */       this.colors = null;
/*     */       
/* 194 */       if (this.source == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 199 */       String s = String.valueOf(this.source) + ".png";
/* 200 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 201 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 203 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 208 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*     */       
/* 210 */       if (bufferedimage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 215 */       int i = bufferedimage.getWidth();
/* 216 */       int j = bufferedimage.getHeight();
/* 217 */       boolean flag = !(this.width >= 0 && this.width != i);
/* 218 */       boolean flag1 = !(this.height >= 0 && this.height != j);
/*     */       
/* 220 */       if (!flag || !flag1)
/*     */       {
/* 222 */         dbg("Non-standard palette size: " + i + "x" + j + ", should be: " + this.width + "x" + this.height + ", path: " + s);
/*     */       }
/*     */       
/* 225 */       this.width = i;
/* 226 */       this.height = j;
/*     */       
/* 228 */       if (this.width <= 0 || this.height <= 0) {
/*     */         
/* 230 */         warn("Invalid palette size: " + i + "x" + j + ", path: " + s);
/*     */         
/*     */         return;
/*     */       } 
/* 234 */       this.colors = new int[i * j];
/* 235 */       bufferedimage.getRGB(0, 0, i, j, this.colors, 0, i);
/*     */     }
/* 237 */     catch (IOException ioexception) {
/*     */       
/* 239 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String p_dbg_0_) {
/* 245 */     Config.dbg("CustomColors: " + p_dbg_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String p_warn_0_) {
/* 250 */     Config.warn("CustomColors: " + p_warn_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseTexture(String p_parseTexture_0_, String p_parseTexture_1_, String p_parseTexture_2_) {
/* 255 */     if (p_parseTexture_0_ != null) {
/*     */       
/* 257 */       p_parseTexture_0_ = p_parseTexture_0_.trim();
/* 258 */       String s1 = ".png";
/*     */       
/* 260 */       if (p_parseTexture_0_.endsWith(s1))
/*     */       {
/* 262 */         p_parseTexture_0_ = p_parseTexture_0_.substring(0, p_parseTexture_0_.length() - s1.length());
/*     */       }
/*     */       
/* 265 */       p_parseTexture_0_ = fixTextureName(p_parseTexture_0_, p_parseTexture_2_);
/* 266 */       return p_parseTexture_0_;
/*     */     } 
/*     */ 
/*     */     
/* 270 */     String s = p_parseTexture_1_;
/* 271 */     int i = p_parseTexture_1_.lastIndexOf('/');
/*     */     
/* 273 */     if (i >= 0)
/*     */     {
/* 275 */       s = p_parseTexture_1_.substring(i + 1);
/*     */     }
/*     */     
/* 278 */     int j = s.lastIndexOf('.');
/*     */     
/* 280 */     if (j >= 0)
/*     */     {
/* 282 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 285 */     s = fixTextureName(s, p_parseTexture_2_);
/* 286 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String p_fixTextureName_0_, String p_fixTextureName_1_) {
/* 292 */     p_fixTextureName_0_ = TextureUtils.fixResourcePath(p_fixTextureName_0_, p_fixTextureName_1_);
/*     */     
/* 294 */     if (!p_fixTextureName_0_.startsWith(p_fixTextureName_1_) && !p_fixTextureName_0_.startsWith("textures/") && !p_fixTextureName_0_.startsWith("mcpatcher/"))
/*     */     {
/* 296 */       p_fixTextureName_0_ = String.valueOf(p_fixTextureName_1_) + "/" + p_fixTextureName_0_;
/*     */     }
/*     */     
/* 299 */     if (p_fixTextureName_0_.endsWith(".png"))
/*     */     {
/* 301 */       p_fixTextureName_0_ = p_fixTextureName_0_.substring(0, p_fixTextureName_0_.length() - 4);
/*     */     }
/*     */     
/* 304 */     String s = "textures/blocks/";
/*     */     
/* 306 */     if (p_fixTextureName_0_.startsWith(s))
/*     */     {
/* 308 */       p_fixTextureName_0_ = p_fixTextureName_0_.substring(s.length());
/*     */     }
/*     */     
/* 311 */     if (p_fixTextureName_0_.startsWith("/"))
/*     */     {
/* 313 */       p_fixTextureName_0_ = p_fixTextureName_0_.substring(1);
/*     */     }
/*     */     
/* 316 */     return p_fixTextureName_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesBlock(BlockStateBase p_matchesBlock_1_) {
/* 321 */     return Matches.block(p_matchesBlock_1_, this.matchBlocks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorRandom() {
/* 326 */     if (this.format == 2)
/*     */     {
/* 328 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 332 */     int i = CustomColors.random.nextInt(this.colors.length);
/* 333 */     return this.colors[i];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(int p_getColor_1_) {
/* 339 */     p_getColor_1_ = Config.limit(p_getColor_1_, 0, this.colors.length - 1);
/* 340 */     return this.colors[p_getColor_1_] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(int p_getColor_1_, int p_getColor_2_) {
/* 345 */     p_getColor_1_ = Config.limit(p_getColor_1_, 0, this.width - 1);
/* 346 */     p_getColor_2_ = Config.limit(p_getColor_2_, 0, this.height - 1);
/* 347 */     return this.colors[p_getColor_2_ * this.width + p_getColor_1_] & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][] getColorsRgb() {
/* 352 */     if (this.colorsRgb == null)
/*     */     {
/* 354 */       this.colorsRgb = toRgb(this.colors);
/*     */     }
/*     */     
/* 357 */     return this.colorsRgb;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockState p_getColor_1_, IBlockAccess p_getColor_2_, BlockPos p_getColor_3_) {
/* 362 */     return getColor(p_getColor_2_, p_getColor_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockAccess p_getColor_1_, BlockPos p_getColor_2_) {
/* 367 */     Biome biome = CustomColors.getColorBiome(p_getColor_1_, p_getColor_2_);
/* 368 */     return getColor(biome, p_getColor_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isColorConstant() {
/* 373 */     return (this.format == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(Biome p_getColor_1_, BlockPos p_getColor_2_) {
/* 378 */     if (this.format == 0)
/*     */     {
/* 380 */       return getColorVanilla(p_getColor_1_, p_getColor_2_);
/*     */     }
/*     */ 
/*     */     
/* 384 */     return (this.format == 1) ? getColorGrid(p_getColor_1_, p_getColor_2_) : this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorSmooth(IBlockAccess p_getColorSmooth_1_, double p_getColorSmooth_2_, double p_getColorSmooth_4_, double p_getColorSmooth_6_, int p_getColorSmooth_8_) {
/* 390 */     if (this.format == 2)
/*     */     {
/* 392 */       return this.color;
/*     */     }
/*     */ 
/*     */     
/* 396 */     int i = MathHelper.floor(p_getColorSmooth_2_);
/* 397 */     int j = MathHelper.floor(p_getColorSmooth_4_);
/* 398 */     int k = MathHelper.floor(p_getColorSmooth_6_);
/* 399 */     int l = 0;
/* 400 */     int i1 = 0;
/* 401 */     int j1 = 0;
/* 402 */     int k1 = 0;
/* 403 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/*     */     
/* 405 */     for (int l1 = i - p_getColorSmooth_8_; l1 <= i + p_getColorSmooth_8_; l1++) {
/*     */       
/* 407 */       for (int i2 = k - p_getColorSmooth_8_; i2 <= k + p_getColorSmooth_8_; i2++) {
/*     */         
/* 409 */         blockposm.setXyz(l1, j, i2);
/* 410 */         int j2 = getColor(p_getColorSmooth_1_, blockposm);
/* 411 */         l += j2 >> 16 & 0xFF;
/* 412 */         i1 += j2 >> 8 & 0xFF;
/* 413 */         j1 += j2 & 0xFF;
/* 414 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 418 */     int k2 = l / k1;
/* 419 */     int l2 = i1 / k1;
/* 420 */     int i3 = j1 / k1;
/* 421 */     return k2 << 16 | l2 << 8 | i3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColorVanilla(Biome p_getColorVanilla_1_, BlockPos p_getColorVanilla_2_) {
/* 427 */     double d0 = MathHelper.clamp(p_getColorVanilla_1_.getFloatTemperature(p_getColorVanilla_2_), 0.0F, 1.0F);
/* 428 */     double d1 = MathHelper.clamp(p_getColorVanilla_1_.getRainfall(), 0.0F, 1.0F);
/* 429 */     d1 *= d0;
/* 430 */     int i = (int)((1.0D - d0) * (this.width - 1));
/* 431 */     int j = (int)((1.0D - d1) * (this.height - 1));
/* 432 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColorGrid(Biome p_getColorGrid_1_, BlockPos p_getColorGrid_2_) {
/* 437 */     int i = Biome.getIdForBiome(p_getColorGrid_1_);
/* 438 */     int j = p_getColorGrid_2_.getY() - this.yOffset;
/*     */     
/* 440 */     if (this.yVariance > 0) {
/*     */       
/* 442 */       int k = p_getColorGrid_2_.getX() << 16 + p_getColorGrid_2_.getZ();
/* 443 */       int l = Config.intHash(k);
/* 444 */       int i1 = this.yVariance * 2 + 1;
/* 445 */       int j1 = (l & 0xFF) % i1 - this.yVariance;
/* 446 */       j += j1;
/*     */     } 
/*     */     
/* 449 */     return getColor(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 454 */     return (this.format == 2) ? 1 : this.colors.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 459 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 464 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float[][] toRgb(int[] p_toRgb_0_) {
/* 469 */     float[][] afloat = new float[p_toRgb_0_.length][3];
/*     */     
/* 471 */     for (int i = 0; i < p_toRgb_0_.length; i++) {
/*     */       
/* 473 */       int j = p_toRgb_0_[i];
/* 474 */       float f = (j >> 16 & 0xFF) / 255.0F;
/* 475 */       float f1 = (j >> 8 & 0xFF) / 255.0F;
/* 476 */       float f2 = (j & 0xFF) / 255.0F;
/* 477 */       float[] afloat1 = afloat[i];
/* 478 */       afloat1[0] = f;
/* 479 */       afloat1[1] = f1;
/* 480 */       afloat1[2] = f2;
/*     */     } 
/*     */     
/* 483 */     return afloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(MatchBlock p_addMatchBlock_1_) {
/* 488 */     if (this.matchBlocks == null)
/*     */     {
/* 490 */       this.matchBlocks = new MatchBlock[0];
/*     */     }
/*     */     
/* 493 */     this.matchBlocks = (MatchBlock[])Config.addObjectToArray((Object[])this.matchBlocks, p_addMatchBlock_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMatchBlock(int p_addMatchBlock_1_, int p_addMatchBlock_2_) {
/* 498 */     MatchBlock matchblock = getMatchBlock(p_addMatchBlock_1_);
/*     */     
/* 500 */     if (matchblock != null) {
/*     */       
/* 502 */       if (p_addMatchBlock_2_ >= 0)
/*     */       {
/* 504 */         matchblock.addMetadata(p_addMatchBlock_2_);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 509 */       addMatchBlock(new MatchBlock(p_addMatchBlock_1_, p_addMatchBlock_2_));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private MatchBlock getMatchBlock(int p_getMatchBlock_1_) {
/* 515 */     if (this.matchBlocks == null)
/*     */     {
/* 517 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 521 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 523 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 525 */       if (matchblock.getBlockId() == p_getMatchBlock_1_)
/*     */       {
/* 527 */         return matchblock;
/*     */       }
/*     */     } 
/*     */     
/* 531 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMatchBlockIds() {
/* 537 */     if (this.matchBlocks == null)
/*     */     {
/* 539 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 543 */     Set<Integer> set = new HashSet();
/*     */     
/* 545 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*     */       
/* 547 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 549 */       if (matchblock.getBlockId() >= 0)
/*     */       {
/* 551 */         set.add(Integer.valueOf(matchblock.getBlockId()));
/*     */       }
/*     */     } 
/*     */     
/* 555 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 556 */     int[] aint = new int[ainteger.length];
/*     */     
/* 558 */     for (int j = 0; j < ainteger.length; j++)
/*     */     {
/* 560 */       aint[j] = ainteger[j].intValue();
/*     */     }
/*     */     
/* 563 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 569 */     return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", source: " + this.source;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomColormap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package optifine;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockObserver;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IStringSerializable;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ 
/*      */ public class ConnectedParser
/*      */ {
/*   27 */   private String context = null;
/*      */ 
/*      */   
/*      */   public ConnectedParser(String p_i26_1_) {
/*   31 */     this.context = p_i26_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseName(String p_parseName_1_) {
/*   36 */     String s = p_parseName_1_;
/*   37 */     int i = p_parseName_1_.lastIndexOf('/');
/*      */     
/*   39 */     if (i >= 0)
/*      */     {
/*   41 */       s = p_parseName_1_.substring(i + 1);
/*      */     }
/*      */     
/*   44 */     int j = s.lastIndexOf('.');
/*      */     
/*   46 */     if (j >= 0)
/*      */     {
/*   48 */       s = s.substring(0, j);
/*      */     }
/*      */     
/*   51 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String parseBasePath(String p_parseBasePath_1_) {
/*   56 */     int i = p_parseBasePath_1_.lastIndexOf('/');
/*   57 */     return (i < 0) ? "" : p_parseBasePath_1_.substring(0, i);
/*      */   }
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlocks(String p_parseMatchBlocks_1_) {
/*   62 */     if (p_parseMatchBlocks_1_ == null)
/*      */     {
/*   64 */       return null;
/*      */     }
/*      */ 
/*      */     
/*   68 */     List list = new ArrayList();
/*   69 */     String[] astring = Config.tokenize(p_parseMatchBlocks_1_, " ");
/*      */     
/*   71 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*   73 */       String s = astring[i];
/*   74 */       MatchBlock[] amatchblock = parseMatchBlock(s);
/*      */       
/*   76 */       if (amatchblock != null)
/*      */       {
/*   78 */         list.addAll(Arrays.asList(amatchblock));
/*      */       }
/*      */     } 
/*      */     
/*   82 */     MatchBlock[] amatchblock1 = (MatchBlock[])list.toArray((Object[])new MatchBlock[list.size()]);
/*   83 */     return amatchblock1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState parseBlockState(String p_parseBlockState_1_, IBlockState p_parseBlockState_2_) {
/*   89 */     MatchBlock[] amatchblock = parseMatchBlock(p_parseBlockState_1_);
/*      */     
/*   91 */     if (amatchblock == null)
/*      */     {
/*   93 */       return p_parseBlockState_2_;
/*      */     }
/*   95 */     if (amatchblock.length != 1)
/*      */     {
/*   97 */       return p_parseBlockState_2_;
/*      */     }
/*      */ 
/*      */     
/*  101 */     MatchBlock matchblock = amatchblock[0];
/*  102 */     int i = matchblock.getBlockId();
/*  103 */     Block block = Block.getBlockById(i);
/*  104 */     return block.getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MatchBlock[] parseMatchBlock(String p_parseMatchBlock_1_) {
/*  110 */     if (p_parseMatchBlock_1_ == null)
/*      */     {
/*  112 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  116 */     p_parseMatchBlock_1_ = p_parseMatchBlock_1_.trim();
/*      */     
/*  118 */     if (p_parseMatchBlock_1_.length() <= 0)
/*      */     {
/*  120 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  124 */     String[] astring = Config.tokenize(p_parseMatchBlock_1_, ":");
/*  125 */     String s = "minecraft";
/*  126 */     int i = 0;
/*      */     
/*  128 */     if (astring.length > 1 && isFullBlockName(astring)) {
/*      */       
/*  130 */       s = astring[0];
/*  131 */       i = 1;
/*      */     }
/*      */     else {
/*      */       
/*  135 */       s = "minecraft";
/*  136 */       i = 0;
/*      */     } 
/*      */     
/*  139 */     String s1 = astring[i];
/*  140 */     String[] astring1 = Arrays.<String>copyOfRange(astring, i + 1, astring.length);
/*  141 */     Block[] ablock = parseBlockPart(s, s1);
/*      */     
/*  143 */     if (ablock == null)
/*      */     {
/*  145 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  149 */     MatchBlock[] amatchblock = new MatchBlock[ablock.length];
/*      */     
/*  151 */     for (int j = 0; j < ablock.length; j++) {
/*      */       
/*  153 */       Block block = ablock[j];
/*  154 */       int k = Block.getIdFromBlock(block);
/*  155 */       int[] aint = null;
/*      */       
/*  157 */       if (astring1.length > 0) {
/*      */         
/*  159 */         aint = parseBlockMetadatas(block, astring1);
/*      */         
/*  161 */         if (aint == null)
/*      */         {
/*  163 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  167 */       MatchBlock matchblock = new MatchBlock(k, aint);
/*  168 */       amatchblock[j] = matchblock;
/*      */     } 
/*      */     
/*  171 */     return amatchblock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullBlockName(String[] p_isFullBlockName_1_) {
/*  179 */     if (p_isFullBlockName_1_.length < 2)
/*      */     {
/*  181 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  185 */     String s = p_isFullBlockName_1_[1];
/*      */     
/*  187 */     if (s.length() < 1)
/*      */     {
/*  189 */       return false;
/*      */     }
/*  191 */     if (startsWithDigit(s))
/*      */     {
/*  193 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  197 */     return !s.contains("=");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWithDigit(String p_startsWithDigit_1_) {
/*  204 */     if (p_startsWithDigit_1_ == null)
/*      */     {
/*  206 */       return false;
/*      */     }
/*  208 */     if (p_startsWithDigit_1_.length() < 1)
/*      */     {
/*  210 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  214 */     char c0 = p_startsWithDigit_1_.charAt(0);
/*  215 */     return Character.isDigit(c0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block[] parseBlockPart(String p_parseBlockPart_1_, String p_parseBlockPart_2_) {
/*  221 */     if (startsWithDigit(p_parseBlockPart_2_)) {
/*      */       
/*  223 */       int[] aint = parseIntList(p_parseBlockPart_2_);
/*      */       
/*  225 */       if (aint == null)
/*      */       {
/*  227 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  231 */       Block[] ablock1 = new Block[aint.length];
/*      */       
/*  233 */       for (int j = 0; j < aint.length; j++) {
/*      */         
/*  235 */         int i = aint[j];
/*  236 */         Block block1 = Block.getBlockById(i);
/*      */         
/*  238 */         if (block1 == null) {
/*      */           
/*  240 */           warn("Block not found for id: " + i);
/*  241 */           return null;
/*      */         } 
/*      */         
/*  244 */         ablock1[j] = block1;
/*      */       } 
/*      */       
/*  247 */       return ablock1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  252 */     String s = String.valueOf(p_parseBlockPart_1_) + ":" + p_parseBlockPart_2_;
/*  253 */     Block block = Block.getBlockFromName(s);
/*      */     
/*  255 */     if (block == null) {
/*      */       
/*  257 */       warn("Block not found for name: " + s);
/*  258 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  262 */     Block[] ablock = { block };
/*  263 */     return ablock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseBlockMetadatas(Block p_parseBlockMetadatas_1_, String[] p_parseBlockMetadatas_2_) {
/*  270 */     if (p_parseBlockMetadatas_2_.length <= 0)
/*      */     {
/*  272 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  276 */     String s = p_parseBlockMetadatas_2_[0];
/*      */     
/*  278 */     if (startsWithDigit(s)) {
/*      */       
/*  280 */       int[] aint = parseIntList(s);
/*  281 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/*  285 */     IBlockState iblockstate = p_parseBlockMetadatas_1_.getDefaultState();
/*  286 */     Collection collection = iblockstate.getPropertyNames();
/*  287 */     Map<IProperty, List<Comparable>> map = new HashMap<>();
/*      */     
/*  289 */     for (int i = 0; i < p_parseBlockMetadatas_2_.length; i++) {
/*      */       
/*  291 */       String s1 = p_parseBlockMetadatas_2_[i];
/*      */       
/*  293 */       if (s1.length() > 0) {
/*      */         
/*  295 */         String[] astring = Config.tokenize(s1, "=");
/*      */         
/*  297 */         if (astring.length != 2) {
/*      */           
/*  299 */           warn("Invalid block property: " + s1);
/*  300 */           return null;
/*      */         } 
/*      */         
/*  303 */         String s2 = astring[0];
/*  304 */         String s3 = astring[1];
/*  305 */         IProperty iproperty = ConnectedProperties.getProperty(s2, collection);
/*      */         
/*  307 */         if (iproperty == null) {
/*      */           
/*  309 */           warn("Property not found: " + s2 + ", block: " + p_parseBlockMetadatas_1_);
/*  310 */           return null;
/*      */         } 
/*      */         
/*  313 */         List<Comparable> list = map.get(s2);
/*      */         
/*  315 */         if (list == null) {
/*      */           
/*  317 */           list = new ArrayList<>();
/*  318 */           map.put(iproperty, list);
/*      */         } 
/*      */         
/*  321 */         String[] astring1 = Config.tokenize(s3, ",");
/*      */         
/*  323 */         for (int j = 0; j < astring1.length; j++) {
/*      */           
/*  325 */           String s4 = astring1[j];
/*  326 */           Comparable comparable = parsePropertyValue(iproperty, s4);
/*      */           
/*  328 */           if (comparable == null) {
/*      */             
/*  330 */             warn("Property value not found: " + s4 + ", property: " + s2 + ", block: " + p_parseBlockMetadatas_1_);
/*  331 */             return null;
/*      */           } 
/*      */           
/*  334 */           list.add(comparable);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  339 */     if (map.isEmpty())
/*      */     {
/*  341 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  345 */     List<Integer> list1 = new ArrayList();
/*      */     
/*  347 */     for (int k = 0; k < 16; k++) {
/*      */       
/*  349 */       int l = k;
/*      */ 
/*      */       
/*      */       try {
/*  353 */         IBlockState iblockstate1 = getStateFromMeta(p_parseBlockMetadatas_1_, l);
/*      */         
/*  355 */         if (matchState(iblockstate1, map))
/*      */         {
/*  357 */           list1.add(Integer.valueOf(l));
/*      */         }
/*      */       }
/*  360 */       catch (IllegalArgumentException illegalArgumentException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     if (list1.size() == 16)
/*      */     {
/*  368 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  372 */     int[] aint1 = new int[list1.size()];
/*      */     
/*  374 */     for (int i1 = 0; i1 < aint1.length; i1++)
/*      */     {
/*  376 */       aint1[i1] = ((Integer)list1.get(i1)).intValue();
/*      */     }
/*      */     
/*  379 */     return aint1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IBlockState getStateFromMeta(Block p_getStateFromMeta_1_, int p_getStateFromMeta_2_) {
/*      */     try {
/*  390 */       IBlockState iblockstate = p_getStateFromMeta_1_.getStateFromMeta(p_getStateFromMeta_2_);
/*      */       
/*  392 */       if (p_getStateFromMeta_1_ == Blocks.DOUBLE_PLANT && p_getStateFromMeta_2_ > 7) {
/*      */         
/*  394 */         IBlockState iblockstate1 = p_getStateFromMeta_1_.getStateFromMeta(p_getStateFromMeta_2_ & 0x7);
/*  395 */         iblockstate = iblockstate.withProperty((IProperty)BlockDoublePlant.VARIANT, iblockstate1.getValue((IProperty)BlockDoublePlant.VARIANT));
/*      */       } 
/*      */       
/*  398 */       if (p_getStateFromMeta_1_ == Blocks.field_190976_dk && (p_getStateFromMeta_2_ & 0x8) != 0)
/*      */       {
/*  400 */         iblockstate = iblockstate.withProperty((IProperty)BlockObserver.field_190963_a, Boolean.valueOf(true));
/*      */       }
/*      */       
/*  403 */       return iblockstate;
/*      */     }
/*  405 */     catch (IllegalArgumentException var5) {
/*      */       
/*  407 */       return p_getStateFromMeta_1_.getDefaultState();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable parsePropertyValue(IProperty p_parsePropertyValue_0_, String p_parsePropertyValue_1_) {
/*  413 */     Class oclass = p_parsePropertyValue_0_.getValueClass();
/*  414 */     Comparable comparable = parseValue(p_parsePropertyValue_1_, oclass);
/*      */     
/*  416 */     if (comparable == null) {
/*      */       
/*  418 */       Collection collection = p_parsePropertyValue_0_.getAllowedValues();
/*  419 */       comparable = getPropertyValue(p_parsePropertyValue_1_, collection);
/*      */     } 
/*      */     
/*  422 */     return comparable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Comparable getPropertyValue(String p_getPropertyValue_0_, Collection p_getPropertyValue_1_) {
/*  427 */     for (Object comparable : p_getPropertyValue_1_) {
/*      */       
/*  429 */       if (getValueName((Comparable)comparable).equals(p_getPropertyValue_0_))
/*      */       {
/*  431 */         return (Comparable)comparable;
/*      */       }
/*      */     } 
/*      */     
/*  435 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object getValueName(Comparable p_getValueName_0_) {
/*  440 */     if (p_getValueName_0_ instanceof IStringSerializable) {
/*      */       
/*  442 */       IStringSerializable istringserializable = (IStringSerializable)p_getValueName_0_;
/*  443 */       return istringserializable.getName();
/*      */     } 
/*      */ 
/*      */     
/*  447 */     return p_getValueName_0_.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Comparable parseValue(String p_parseValue_0_, Class<String> p_parseValue_1_) {
/*  453 */     if (p_parseValue_1_ == String.class)
/*      */     {
/*  455 */       return p_parseValue_0_;
/*      */     }
/*  457 */     if (p_parseValue_1_ == Boolean.class)
/*      */     {
/*  459 */       return Boolean.valueOf(p_parseValue_0_);
/*      */     }
/*  461 */     if (p_parseValue_1_ == Float.class)
/*      */     {
/*  463 */       return Float.valueOf(p_parseValue_0_);
/*      */     }
/*  465 */     if (p_parseValue_1_ == Double.class)
/*      */     {
/*  467 */       return Double.valueOf(p_parseValue_0_);
/*      */     }
/*  469 */     if (p_parseValue_1_ == Integer.class)
/*      */     {
/*  471 */       return Integer.valueOf(p_parseValue_0_);
/*      */     }
/*      */ 
/*      */     
/*  475 */     return (p_parseValue_1_ == Long.class) ? Long.valueOf(p_parseValue_0_) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchState(IBlockState p_matchState_1_, Map<IProperty, List<Comparable>> p_matchState_2_) {
/*  481 */     for (IProperty iproperty : p_matchState_2_.keySet()) {
/*      */       
/*  483 */       List<Comparable> list = p_matchState_2_.get(iproperty);
/*  484 */       Comparable comparable = p_matchState_1_.getValue(iproperty);
/*      */       
/*  486 */       if (comparable == null)
/*      */       {
/*  488 */         return false;
/*      */       }
/*      */       
/*  491 */       if (!list.contains(comparable))
/*      */       {
/*  493 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  497 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Biome[] parseBiomes(String p_parseBiomes_1_) {
/*  502 */     if (p_parseBiomes_1_ == null)
/*      */     {
/*  504 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  508 */     p_parseBiomes_1_ = p_parseBiomes_1_.trim();
/*  509 */     boolean flag = false;
/*      */     
/*  511 */     if (p_parseBiomes_1_.startsWith("!")) {
/*      */       
/*  513 */       flag = true;
/*  514 */       p_parseBiomes_1_ = p_parseBiomes_1_.substring(1);
/*      */     } 
/*      */     
/*  517 */     String[] astring = Config.tokenize(p_parseBiomes_1_, " ");
/*  518 */     List<Biome> list = new ArrayList();
/*      */     
/*  520 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  522 */       String s = astring[i];
/*  523 */       Biome biome = findBiome(s);
/*      */       
/*  525 */       if (biome == null) {
/*      */         
/*  527 */         warn("Biome not found: " + s);
/*      */       }
/*      */       else {
/*      */         
/*  531 */         list.add(biome);
/*      */       } 
/*      */     } 
/*      */     
/*  535 */     if (flag) {
/*      */       
/*  537 */       List<Biome> list1 = Lists.newArrayList(Biome.REGISTRY.iterator());
/*  538 */       list1.removeAll(list);
/*  539 */       list = list1;
/*      */     } 
/*      */     
/*  542 */     Biome[] abiome = list.<Biome>toArray(new Biome[list.size()]);
/*  543 */     return abiome;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Biome findBiome(String p_findBiome_1_) {
/*  549 */     p_findBiome_1_ = p_findBiome_1_.toLowerCase();
/*      */     
/*  551 */     if (p_findBiome_1_.equals("nether"))
/*      */     {
/*  553 */       return Biomes.HELL;
/*      */     }
/*      */ 
/*      */     
/*  557 */     for (ResourceLocation resourcelocation : Biome.REGISTRY.getKeys()) {
/*      */       
/*  559 */       Biome biome = (Biome)Biome.REGISTRY.getObject(resourcelocation);
/*      */       
/*  561 */       if (biome != null) {
/*      */         
/*  563 */         String s = biome.getBiomeName().replace(" ", "").toLowerCase();
/*      */         
/*  565 */         if (s.equals(p_findBiome_1_))
/*      */         {
/*  567 */           return biome;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  572 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int parseInt(String p_parseInt_1_) {
/*  578 */     if (p_parseInt_1_ == null)
/*      */     {
/*  580 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  584 */     p_parseInt_1_ = p_parseInt_1_.trim();
/*  585 */     int i = Config.parseInt(p_parseInt_1_, -1);
/*      */     
/*  587 */     if (i < 0)
/*      */     {
/*  589 */       warn("Invalid number: " + p_parseInt_1_);
/*      */     }
/*      */     
/*  592 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int parseInt(String p_parseInt_1_, int p_parseInt_2_) {
/*  598 */     if (p_parseInt_1_ == null)
/*      */     {
/*  600 */       return p_parseInt_2_;
/*      */     }
/*      */ 
/*      */     
/*  604 */     p_parseInt_1_ = p_parseInt_1_.trim();
/*  605 */     int i = Config.parseInt(p_parseInt_1_, -1);
/*      */     
/*  607 */     if (i < 0) {
/*      */       
/*  609 */       warn("Invalid number: " + p_parseInt_1_);
/*  610 */       return p_parseInt_2_;
/*      */     } 
/*      */ 
/*      */     
/*  614 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] parseIntList(String p_parseIntList_1_) {
/*  621 */     if (p_parseIntList_1_ == null)
/*      */     {
/*  623 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  627 */     List<Integer> list = new ArrayList();
/*  628 */     String[] astring = Config.tokenize(p_parseIntList_1_, " ,");
/*      */     
/*  630 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  632 */       String s = astring[i];
/*      */       
/*  634 */       if (s.contains("-")) {
/*      */         
/*  636 */         String[] astring1 = Config.tokenize(s, "-");
/*      */         
/*  638 */         if (astring1.length != 2) {
/*      */           
/*  640 */           warn("Invalid interval: " + s + ", when parsing: " + p_parseIntList_1_);
/*      */         }
/*      */         else {
/*      */           
/*  644 */           int k = Config.parseInt(astring1[0], -1);
/*  645 */           int l = Config.parseInt(astring1[1], -1);
/*      */           
/*  647 */           if (k >= 0 && l >= 0 && k <= l)
/*      */           {
/*  649 */             for (int i1 = k; i1 <= l; i1++)
/*      */             {
/*  651 */               list.add(Integer.valueOf(i1));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  656 */             warn("Invalid interval: " + s + ", when parsing: " + p_parseIntList_1_);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  662 */         int j = Config.parseInt(s, -1);
/*      */         
/*  664 */         if (j < 0) {
/*      */           
/*  666 */           warn("Invalid number: " + s + ", when parsing: " + p_parseIntList_1_);
/*      */         }
/*      */         else {
/*      */           
/*  670 */           list.add(Integer.valueOf(j));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  675 */     int[] aint = new int[list.size()];
/*      */     
/*  677 */     for (int j1 = 0; j1 < aint.length; j1++)
/*      */     {
/*  679 */       aint[j1] = ((Integer)list.get(j1)).intValue();
/*      */     }
/*      */     
/*  682 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[] parseFaces(String p_parseFaces_1_, boolean[] p_parseFaces_2_) {
/*  688 */     if (p_parseFaces_1_ == null)
/*      */     {
/*  690 */       return p_parseFaces_2_;
/*      */     }
/*      */ 
/*      */     
/*  694 */     EnumSet<EnumFacing> enumset = EnumSet.allOf(EnumFacing.class);
/*  695 */     String[] astring = Config.tokenize(p_parseFaces_1_, " ,");
/*      */     
/*  697 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  699 */       String s = astring[i];
/*      */       
/*  701 */       if (s.equals("sides")) {
/*      */         
/*  703 */         enumset.add(EnumFacing.NORTH);
/*  704 */         enumset.add(EnumFacing.SOUTH);
/*  705 */         enumset.add(EnumFacing.WEST);
/*  706 */         enumset.add(EnumFacing.EAST);
/*      */       }
/*  708 */       else if (s.equals("all")) {
/*      */         
/*  710 */         enumset.addAll(Arrays.asList(EnumFacing.VALUES));
/*      */       }
/*      */       else {
/*      */         
/*  714 */         EnumFacing enumfacing = parseFace(s);
/*      */         
/*  716 */         if (enumfacing != null)
/*      */         {
/*  718 */           enumset.add(enumfacing);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  723 */     boolean[] aboolean = new boolean[EnumFacing.VALUES.length];
/*      */     
/*  725 */     for (int j = 0; j < aboolean.length; j++)
/*      */     {
/*  727 */       aboolean[j] = enumset.contains(EnumFacing.VALUES[j]);
/*      */     }
/*      */     
/*  730 */     return aboolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing parseFace(String p_parseFace_1_) {
/*  736 */     p_parseFace_1_ = p_parseFace_1_.toLowerCase();
/*      */     
/*  738 */     if (!p_parseFace_1_.equals("bottom") && !p_parseFace_1_.equals("down")) {
/*      */       
/*  740 */       if (!p_parseFace_1_.equals("top") && !p_parseFace_1_.equals("up")) {
/*      */         
/*  742 */         if (p_parseFace_1_.equals("north"))
/*      */         {
/*  744 */           return EnumFacing.NORTH;
/*      */         }
/*  746 */         if (p_parseFace_1_.equals("south"))
/*      */         {
/*  748 */           return EnumFacing.SOUTH;
/*      */         }
/*  750 */         if (p_parseFace_1_.equals("east"))
/*      */         {
/*  752 */           return EnumFacing.EAST;
/*      */         }
/*  754 */         if (p_parseFace_1_.equals("west"))
/*      */         {
/*  756 */           return EnumFacing.WEST;
/*      */         }
/*      */ 
/*      */         
/*  760 */         Config.warn("Unknown face: " + p_parseFace_1_);
/*  761 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  766 */       return EnumFacing.UP;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  771 */     return EnumFacing.DOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dbg(String p_dbg_1_) {
/*  777 */     Config.dbg(this.context + ": " + p_dbg_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void warn(String p_warn_1_) {
/*  782 */     Config.warn(this.context + ": " + p_warn_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public RangeListInt parseRangeListInt(String p_parseRangeListInt_1_) {
/*  787 */     if (p_parseRangeListInt_1_ == null)
/*      */     {
/*  789 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  793 */     RangeListInt rangelistint = new RangeListInt();
/*  794 */     String[] astring = Config.tokenize(p_parseRangeListInt_1_, " ,");
/*      */     
/*  796 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  798 */       String s = astring[i];
/*  799 */       RangeInt rangeint = parseRangeInt(s);
/*      */       
/*  801 */       if (rangeint == null)
/*      */       {
/*  803 */         return null;
/*      */       }
/*      */       
/*  806 */       rangelistint.addRange(rangeint);
/*      */     } 
/*      */     
/*  809 */     return rangelistint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeInt parseRangeInt(String p_parseRangeInt_1_) {
/*  815 */     if (p_parseRangeInt_1_ == null)
/*      */     {
/*  817 */       return null;
/*      */     }
/*  819 */     if (p_parseRangeInt_1_.indexOf('-') >= 0) {
/*      */       
/*  821 */       String[] astring = Config.tokenize(p_parseRangeInt_1_, "-");
/*      */       
/*  823 */       if (astring.length != 2) {
/*      */         
/*  825 */         warn("Invalid range: " + p_parseRangeInt_1_);
/*  826 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  830 */       int j = Config.parseInt(astring[0], -1);
/*  831 */       int k = Config.parseInt(astring[1], -1);
/*      */       
/*  833 */       if (j >= 0 && k >= 0)
/*      */       {
/*  835 */         return new RangeInt(j, k);
/*      */       }
/*      */ 
/*      */       
/*  839 */       warn("Invalid range: " + p_parseRangeInt_1_);
/*  840 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  846 */     int i = Config.parseInt(p_parseRangeInt_1_, -1);
/*      */     
/*  848 */     if (i < 0) {
/*      */       
/*  850 */       warn("Invalid integer: " + p_parseRangeInt_1_);
/*  851 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  855 */     return new RangeInt(i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean parseBoolean(String p_parseBoolean_0_) {
/*  862 */     return (p_parseBoolean_0_ == null) ? false : p_parseBoolean_0_.trim().toLowerCase().equals("true");
/*      */   }
/*      */ 
/*      */   
/*      */   public Boolean parseBooleanObject(String p_parseBooleanObject_1_) {
/*  867 */     if (p_parseBooleanObject_1_ == null)
/*      */     {
/*  869 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  873 */     String s = p_parseBooleanObject_1_.toLowerCase().trim();
/*      */     
/*  875 */     if (s.equals("true"))
/*      */     {
/*  877 */       return Boolean.TRUE;
/*      */     }
/*  879 */     if (s.equals("false"))
/*      */     {
/*  881 */       return Boolean.FALSE;
/*      */     }
/*      */ 
/*      */     
/*  885 */     warn("Invalid boolean: " + p_parseBooleanObject_1_);
/*  886 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor(String p_parseColor_0_, int p_parseColor_1_) {
/*  893 */     if (p_parseColor_0_ == null)
/*      */     {
/*  895 */       return p_parseColor_1_;
/*      */     }
/*      */ 
/*      */     
/*  899 */     p_parseColor_0_ = p_parseColor_0_.trim();
/*      */ 
/*      */     
/*      */     try {
/*  903 */       int i = Integer.parseInt(p_parseColor_0_, 16) & 0xFFFFFF;
/*  904 */       return i;
/*      */     }
/*  906 */     catch (NumberFormatException var3) {
/*      */       
/*  908 */       return p_parseColor_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseColor4(String p_parseColor4_0_, int p_parseColor4_1_) {
/*  915 */     if (p_parseColor4_0_ == null)
/*      */     {
/*  917 */       return p_parseColor4_1_;
/*      */     }
/*      */ 
/*      */     
/*  921 */     p_parseColor4_0_ = p_parseColor4_0_.trim();
/*      */ 
/*      */     
/*      */     try {
/*  925 */       int i = (int)(Long.parseLong(p_parseColor4_0_, 16) & 0xFFFFFFFFFFFFFFFFL);
/*  926 */       return i;
/*      */     }
/*  928 */     catch (NumberFormatException var3) {
/*      */       
/*  930 */       return p_parseColor4_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRenderLayer parseBlockRenderLayer(String p_parseBlockRenderLayer_1_, BlockRenderLayer p_parseBlockRenderLayer_2_) {
/*  937 */     if (p_parseBlockRenderLayer_1_ == null)
/*      */     {
/*  939 */       return p_parseBlockRenderLayer_2_;
/*      */     }
/*      */ 
/*      */     
/*  943 */     p_parseBlockRenderLayer_1_ = p_parseBlockRenderLayer_1_.toLowerCase().trim();
/*  944 */     BlockRenderLayer[] ablockrenderlayer = BlockRenderLayer.values();
/*      */     
/*  946 */     for (int i = 0; i < ablockrenderlayer.length; i++) {
/*      */       
/*  948 */       BlockRenderLayer blockrenderlayer = ablockrenderlayer[i];
/*      */       
/*  950 */       if (p_parseBlockRenderLayer_1_.equals(blockrenderlayer.name().toLowerCase()))
/*      */       {
/*  952 */         return blockrenderlayer;
/*      */       }
/*      */     } 
/*      */     
/*  956 */     return p_parseBlockRenderLayer_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enum parseEnum(String p_parseEnum_1_, Enum[] p_parseEnum_2_, String p_parseEnum_3_) {
/*  962 */     if (p_parseEnum_1_ == null)
/*      */     {
/*  964 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  968 */     String s = p_parseEnum_1_.toLowerCase().trim();
/*      */     
/*  970 */     for (int i = 0; i < p_parseEnum_2_.length; i++) {
/*      */       
/*  972 */       Enum oenum = p_parseEnum_2_[i];
/*      */       
/*  974 */       if (oenum.name().toLowerCase().equals(s))
/*      */       {
/*  976 */         return oenum;
/*      */       }
/*      */     } 
/*      */     
/*  980 */     warn("Invalid " + p_parseEnum_3_ + ": " + p_parseEnum_1_);
/*  981 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Enum[] parseEnums(String p_parseEnums_1_, Enum[] p_parseEnums_2_, String p_parseEnums_3_, Enum[] p_parseEnums_4_) {
/*  987 */     if (p_parseEnums_1_ == null)
/*      */     {
/*  989 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  993 */     p_parseEnums_1_ = p_parseEnums_1_.toLowerCase().trim();
/*  994 */     String[] astring = Config.tokenize(p_parseEnums_1_, " ");
/*  995 */     Enum[] aenum = (Enum[])Array.newInstance(p_parseEnums_2_.getClass().getComponentType(), astring.length);
/*      */     
/*  997 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  999 */       String s = astring[i];
/* 1000 */       Enum oenum = parseEnum(s, p_parseEnums_2_, p_parseEnums_3_);
/*      */       
/* 1002 */       if (oenum == null)
/*      */       {
/* 1004 */         return p_parseEnums_4_;
/*      */       }
/*      */       
/* 1007 */       aenum[i] = oenum;
/*      */     } 
/*      */     
/* 1010 */     return aenum;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ConnectedParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
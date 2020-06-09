/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ public class FlatGeneratorInfo
/*     */ {
/*  17 */   private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
/*  18 */   private final Map<String, Map<String, String>> worldFeatures = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private int biomeToUse;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBiome() {
/*  26 */     return this.biomeToUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiome(int biome) {
/*  34 */     this.biomeToUse = biome;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Map<String, String>> getWorldFeatures() {
/*  39 */     return this.worldFeatures;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FlatLayerInfo> getFlatLayers() {
/*  44 */     return this.flatLayers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLayers() {
/*  49 */     int i = 0;
/*     */     
/*  51 */     for (FlatLayerInfo flatlayerinfo : this.flatLayers) {
/*     */       
/*  53 */       flatlayerinfo.setMinY(i);
/*  54 */       i += flatlayerinfo.getLayerCount();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  60 */     StringBuilder stringbuilder = new StringBuilder();
/*  61 */     stringbuilder.append(3);
/*  62 */     stringbuilder.append(";");
/*     */     
/*  64 */     for (int i = 0; i < this.flatLayers.size(); i++) {
/*     */       
/*  66 */       if (i > 0)
/*     */       {
/*  68 */         stringbuilder.append(",");
/*     */       }
/*     */       
/*  71 */       stringbuilder.append(this.flatLayers.get(i));
/*     */     } 
/*     */     
/*  74 */     stringbuilder.append(";");
/*  75 */     stringbuilder.append(this.biomeToUse);
/*     */     
/*  77 */     if (this.worldFeatures.isEmpty()) {
/*     */       
/*  79 */       stringbuilder.append(";");
/*     */     }
/*     */     else {
/*     */       
/*  83 */       stringbuilder.append(";");
/*  84 */       int k = 0;
/*     */       
/*  86 */       for (Map.Entry<String, Map<String, String>> entry : this.worldFeatures.entrySet()) {
/*     */         
/*  88 */         if (k++ > 0)
/*     */         {
/*  90 */           stringbuilder.append(",");
/*     */         }
/*     */         
/*  93 */         stringbuilder.append(((String)entry.getKey()).toLowerCase(Locale.ROOT));
/*  94 */         Map<String, String> map = entry.getValue();
/*     */         
/*  96 */         if (!map.isEmpty()) {
/*     */           
/*  98 */           stringbuilder.append("(");
/*  99 */           int j = 0;
/*     */           
/* 101 */           for (Map.Entry<String, String> entry1 : map.entrySet()) {
/*     */             
/* 103 */             if (j++ > 0)
/*     */             {
/* 105 */               stringbuilder.append(" ");
/*     */             }
/*     */             
/* 108 */             stringbuilder.append(entry1.getKey());
/* 109 */             stringbuilder.append("=");
/* 110 */             stringbuilder.append(entry1.getValue());
/*     */           } 
/*     */           
/* 113 */           stringbuilder.append(")");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   private static FlatLayerInfo getLayerFromString(int p_180715_0_, String p_180715_1_, int p_180715_2_) {
/*     */     Block block;
/* 123 */     String[] astring = (p_180715_0_ >= 3) ? p_180715_1_.split("\\*", 2) : p_180715_1_.split("x", 2);
/* 124 */     int i = 1;
/* 125 */     int j = 0;
/*     */     
/* 127 */     if (astring.length == 2) {
/*     */       
/*     */       try {
/*     */         
/* 131 */         i = Integer.parseInt(astring[0]);
/*     */         
/* 133 */         if (p_180715_2_ + i >= 256)
/*     */         {
/* 135 */           i = 256 - p_180715_2_;
/*     */         }
/*     */         
/* 138 */         if (i < 0)
/*     */         {
/* 140 */           i = 0;
/*     */         }
/*     */       }
/* 143 */       catch (Throwable var8) {
/*     */         
/* 145 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 153 */       String s = astring[astring.length - 1];
/*     */       
/* 155 */       if (p_180715_0_ < 3) {
/*     */         
/* 157 */         astring = s.split(":", 2);
/*     */         
/* 159 */         if (astring.length > 1)
/*     */         {
/* 161 */           j = Integer.parseInt(astring[1]);
/*     */         }
/*     */         
/* 164 */         block = Block.getBlockById(Integer.parseInt(astring[0]));
/*     */       }
/*     */       else {
/*     */         
/* 168 */         astring = s.split(":", 3);
/* 169 */         block = (astring.length > 1) ? Block.getBlockFromName(String.valueOf(astring[0]) + ":" + astring[1]) : null;
/*     */         
/* 171 */         if (block != null) {
/*     */           
/* 173 */           j = (astring.length > 2) ? Integer.parseInt(astring[2]) : 0;
/*     */         }
/*     */         else {
/*     */           
/* 177 */           block = Block.getBlockFromName(astring[0]);
/*     */           
/* 179 */           if (block != null)
/*     */           {
/* 181 */             j = (astring.length > 1) ? Integer.parseInt(astring[1]) : 0;
/*     */           }
/*     */         } 
/*     */         
/* 185 */         if (block == null)
/*     */         {
/* 187 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 191 */       if (block == Blocks.AIR)
/*     */       {
/* 193 */         j = 0;
/*     */       }
/*     */       
/* 196 */       if (j < 0 || j > 15)
/*     */       {
/* 198 */         j = 0;
/*     */       }
/*     */     }
/* 201 */     catch (Throwable var9) {
/*     */       
/* 203 */       return null;
/*     */     } 
/*     */     
/* 206 */     FlatLayerInfo flatlayerinfo = new FlatLayerInfo(p_180715_0_, i, block, j);
/* 207 */     flatlayerinfo.setMinY(p_180715_2_);
/* 208 */     return flatlayerinfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<FlatLayerInfo> getLayersFromString(int p_180716_0_, String p_180716_1_) {
/* 213 */     if (p_180716_1_ != null && p_180716_1_.length() >= 1) {
/*     */       
/* 215 */       List<FlatLayerInfo> list = Lists.newArrayList();
/* 216 */       String[] astring = p_180716_1_.split(",");
/* 217 */       int i = 0; byte b; int j;
/*     */       String[] arrayOfString1;
/* 219 */       for (j = (arrayOfString1 = astring).length, b = 0; b < j; ) { String s = arrayOfString1[b];
/*     */         
/* 221 */         FlatLayerInfo flatlayerinfo = getLayerFromString(p_180716_0_, s, i);
/*     */         
/* 223 */         if (flatlayerinfo == null)
/*     */         {
/* 225 */           return null;
/*     */         }
/*     */         
/* 228 */         list.add(flatlayerinfo);
/* 229 */         i += flatlayerinfo.getLayerCount();
/*     */         b++; }
/*     */       
/* 232 */       return list;
/*     */     } 
/*     */ 
/*     */     
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo createFlatGeneratorFromString(String flatGeneratorSettings) {
/* 242 */     if (flatGeneratorSettings == null)
/*     */     {
/* 244 */       return getDefaultFlatGenerator();
/*     */     }
/*     */ 
/*     */     
/* 248 */     String[] astring = flatGeneratorSettings.split(";", -1);
/* 249 */     int i = (astring.length == 1) ? 0 : MathHelper.getInt(astring[0], 0);
/*     */     
/* 251 */     if (i >= 0 && i <= 3) {
/*     */       
/* 253 */       FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 254 */       int j = (astring.length == 1) ? 0 : 1;
/* 255 */       List<FlatLayerInfo> list = getLayersFromString(i, astring[j++]);
/*     */       
/* 257 */       if (list != null && !list.isEmpty()) {
/*     */         
/* 259 */         flatgeneratorinfo.getFlatLayers().addAll(list);
/* 260 */         flatgeneratorinfo.updateLayers();
/* 261 */         int k = Biome.getIdForBiome(Biomes.PLAINS);
/*     */         
/* 263 */         if (i > 0 && astring.length > j)
/*     */         {
/* 265 */           k = MathHelper.getInt(astring[j++], k);
/*     */         }
/*     */         
/* 268 */         flatgeneratorinfo.setBiome(k);
/*     */         
/* 270 */         if (i > 0 && astring.length > j) {
/*     */           
/* 272 */           String[] astring1 = astring[j++].toLowerCase(Locale.ROOT).split(","); byte b; int m;
/*     */           String[] arrayOfString1;
/* 274 */           for (m = (arrayOfString1 = astring1).length, b = 0; b < m; ) { String s = arrayOfString1[b];
/*     */             
/* 276 */             String[] astring2 = s.split("\\(", 2);
/* 277 */             Map<String, String> map = Maps.newHashMap();
/*     */             
/* 279 */             if (!astring2[0].isEmpty()) {
/*     */               
/* 281 */               flatgeneratorinfo.getWorldFeatures().put(astring2[0], map);
/*     */               
/* 283 */               if (astring2.length > 1 && astring2[1].endsWith(")") && astring2[1].length() > 1) {
/*     */                 
/* 285 */                 String[] astring3 = astring2[1].substring(0, astring2[1].length() - 1).split(" "); byte b1; int n;
/*     */                 String[] arrayOfString2;
/* 287 */                 for (n = (arrayOfString2 = astring3).length, b1 = 0; b1 < n; ) { String s1 = arrayOfString2[b1];
/*     */                   
/* 289 */                   String[] astring4 = s1.split("=", 2);
/*     */                   
/* 291 */                   if (astring4.length == 2)
/*     */                   {
/* 293 */                     map.put(astring4[0], astring4[1]);
/*     */                   }
/*     */                   b1++; }
/*     */               
/*     */               } 
/*     */             } 
/*     */             b++; }
/*     */         
/*     */         } else {
/* 302 */           flatgeneratorinfo.getWorldFeatures().put("village", Maps.newHashMap());
/*     */         } 
/*     */         
/* 305 */         return flatgeneratorinfo;
/*     */       } 
/*     */ 
/*     */       
/* 309 */       return getDefaultFlatGenerator();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 314 */     return getDefaultFlatGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo getDefaultFlatGenerator() {
/* 321 */     FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 322 */     flatgeneratorinfo.setBiome(Biome.getIdForBiome(Biomes.PLAINS));
/* 323 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.BEDROCK));
/* 324 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(2, Blocks.DIRT));
/* 325 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, (Block)Blocks.GRASS));
/* 326 */     flatgeneratorinfo.updateLayers();
/* 327 */     flatgeneratorinfo.getWorldFeatures().put("village", Maps.newHashMap());
/* 328 */     return flatgeneratorinfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\FlatGeneratorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
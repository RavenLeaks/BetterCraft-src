/*     */ package optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RandomMobs
/*     */ {
/*  21 */   private static Map locationProperties = new HashMap<>();
/*  22 */   private static RenderGlobal renderGlobal = null;
/*     */   private static boolean initialized = false;
/*  24 */   private static Random random = new Random();
/*     */   private static boolean working = false;
/*     */   public static final String SUFFIX_PNG = ".png";
/*     */   public static final String SUFFIX_PROPERTIES = ".properties";
/*     */   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
/*     */   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
/*  30 */   private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
/*     */ 
/*     */   
/*     */   public static void entityLoaded(Entity p_entityLoaded_0_, World p_entityLoaded_1_) {
/*  34 */     if (p_entityLoaded_0_ instanceof EntityLiving)
/*     */     {
/*  36 */       if (p_entityLoaded_1_ != null) {
/*     */         
/*  38 */         EntityLiving entityliving = (EntityLiving)p_entityLoaded_0_;
/*  39 */         entityliving.spawnPosition = entityliving.getPosition();
/*  40 */         entityliving.spawnBiome = p_entityLoaded_1_.getBiome(entityliving.spawnPosition);
/*  41 */         UUID uuid = entityliving.getUniqueID();
/*  42 */         long i = uuid.getLeastSignificantBits();
/*  43 */         int j = (int)(i & 0x7FFFFFFFL);
/*  44 */         entityliving.randomMobsId = j;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void worldChanged(World p_worldChanged_0_, World p_worldChanged_1_) {
/*  51 */     if (p_worldChanged_1_ != null) {
/*     */       
/*  53 */       List<Entity> list = p_worldChanged_1_.getLoadedEntityList();
/*     */       
/*  55 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/*  57 */         Entity entity = list.get(i);
/*  58 */         entityLoaded(entity, p_worldChanged_1_);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation p_getTextureLocation_0_) {
/*     */     ResourceLocation entity;
/*  65 */     if (working)
/*     */     {
/*  67 */       return p_getTextureLocation_0_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     try { working = true;
/*     */       
/*  77 */       if (!initialized)
/*     */       {
/*  79 */         initialize();
/*     */       }
/*     */       
/*  82 */       if (renderGlobal != null)
/*     */       {
/*  84 */         Entity entity1 = renderGlobal.renderedEntity;
/*     */         
/*  86 */         if (!(entity1 instanceof EntityLiving)) {
/*     */           
/*  88 */           ResourceLocation resourcelocation2 = p_getTextureLocation_0_;
/*  89 */           return resourcelocation2;
/*     */         } 
/*     */         
/*  92 */         EntityLiving entityliving = (EntityLiving)entity1;
/*  93 */         String s = p_getTextureLocation_0_.getResourcePath();
/*     */         
/*  95 */         if (!s.startsWith("textures/entity/")) {
/*     */           
/*  97 */           ResourceLocation resourcelocation3 = p_getTextureLocation_0_;
/*  98 */           return resourcelocation3;
/*     */         } 
/*     */         
/* 101 */         RandomMobsProperties randommobsproperties = getProperties(p_getTextureLocation_0_);
/*     */         
/* 103 */         if (randommobsproperties == null) {
/*     */           
/* 105 */           ResourceLocation resourcelocation4 = p_getTextureLocation_0_;
/* 106 */           return resourcelocation4;
/*     */         } 
/*     */         
/* 109 */         ResourceLocation resourcelocation1 = randommobsproperties.getTextureLocation(p_getTextureLocation_0_, entityliving);
/* 110 */         return resourcelocation1;
/*     */       
/*     */       }
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 117 */     { working = false; }  working = false;
/*     */ 
/*     */     
/* 120 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties getProperties(ResourceLocation p_getProperties_0_) {
/* 126 */     String s = p_getProperties_0_.getResourcePath();
/* 127 */     RandomMobsProperties randommobsproperties = (RandomMobsProperties)locationProperties.get(s);
/*     */     
/* 129 */     if (randommobsproperties == null) {
/*     */       
/* 131 */       randommobsproperties = makeProperties(p_getProperties_0_);
/* 132 */       locationProperties.put(s, randommobsproperties);
/*     */     } 
/*     */     
/* 135 */     return randommobsproperties;
/*     */   }
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties makeProperties(ResourceLocation p_makeProperties_0_) {
/* 140 */     String s = p_makeProperties_0_.getResourcePath();
/* 141 */     ResourceLocation resourcelocation = getPropertyLocation(p_makeProperties_0_);
/*     */     
/* 143 */     if (resourcelocation != null) {
/*     */       
/* 145 */       RandomMobsProperties randommobsproperties = parseProperties(resourcelocation, p_makeProperties_0_);
/*     */       
/* 147 */       if (randommobsproperties != null)
/*     */       {
/* 149 */         return randommobsproperties;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     ResourceLocation[] aresourcelocation = getTextureVariants(p_makeProperties_0_);
/* 154 */     return new RandomMobsProperties(s, aresourcelocation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomMobsProperties parseProperties(ResourceLocation p_parseProperties_0_, ResourceLocation p_parseProperties_1_) {
/*     */     try {
/* 161 */       String s = p_parseProperties_0_.getResourcePath();
/* 162 */       Config.dbg("RandomMobs: " + p_parseProperties_1_.getResourcePath() + ", variants: " + s);
/* 163 */       InputStream inputstream = Config.getResourceStream(p_parseProperties_0_);
/*     */       
/* 165 */       if (inputstream == null) {
/*     */         
/* 167 */         Config.warn("RandomMobs properties not found: " + s);
/* 168 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 172 */       Properties properties = new Properties();
/* 173 */       properties.load(inputstream);
/* 174 */       inputstream.close();
/* 175 */       RandomMobsProperties randommobsproperties = new RandomMobsProperties(properties, s, p_parseProperties_1_);
/* 176 */       return !randommobsproperties.isValid(s) ? null : randommobsproperties;
/*     */     
/*     */     }
/* 179 */     catch (FileNotFoundException var6) {
/*     */       
/* 181 */       Config.warn("RandomMobs file not found: " + p_parseProperties_1_.getResourcePath());
/* 182 */       return null;
/*     */     }
/* 184 */     catch (IOException ioexception) {
/*     */       
/* 186 */       ioexception.printStackTrace();
/* 187 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getPropertyLocation(ResourceLocation p_getPropertyLocation_0_) {
/* 193 */     ResourceLocation resourcelocation = getMcpatcherLocation(p_getPropertyLocation_0_);
/*     */     
/* 195 */     if (resourcelocation == null)
/*     */     {
/* 197 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 201 */     String s = resourcelocation.getResourceDomain();
/* 202 */     String s1 = resourcelocation.getResourcePath();
/* 203 */     String s2 = s1;
/*     */     
/* 205 */     if (s1.endsWith(".png"))
/*     */     {
/* 207 */       s2 = s1.substring(0, s1.length() - ".png".length());
/*     */     }
/*     */     
/* 210 */     String s3 = String.valueOf(s2) + ".properties";
/* 211 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s3);
/*     */     
/* 213 */     if (Config.hasResource(resourcelocation1))
/*     */     {
/* 215 */       return resourcelocation1;
/*     */     }
/*     */ 
/*     */     
/* 219 */     String s4 = getParentPath(s2);
/*     */     
/* 221 */     if (s4 == null)
/*     */     {
/* 223 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 227 */     ResourceLocation resourcelocation2 = new ResourceLocation(s, String.valueOf(s4) + ".properties");
/* 228 */     return Config.hasResource(resourcelocation2) ? resourcelocation2 : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getMcpatcherLocation(ResourceLocation p_getMcpatcherLocation_0_) {
/* 236 */     String s = p_getMcpatcherLocation_0_.getResourcePath();
/*     */     
/* 238 */     if (!s.startsWith("textures/entity/"))
/*     */     {
/* 240 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 244 */     String s1 = "mcpatcher/mob/" + s.substring("textures/entity/".length());
/* 245 */     return new ResourceLocation(p_getMcpatcherLocation_0_.getResourceDomain(), s1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationIndexed(ResourceLocation p_getLocationIndexed_0_, int p_getLocationIndexed_1_) {
/* 251 */     if (p_getLocationIndexed_0_ == null)
/*     */     {
/* 253 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 257 */     String s = p_getLocationIndexed_0_.getResourcePath();
/* 258 */     int i = s.lastIndexOf('.');
/*     */     
/* 260 */     if (i < 0)
/*     */     {
/* 262 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 266 */     String s1 = s.substring(0, i);
/* 267 */     String s2 = s.substring(i);
/* 268 */     String s3 = String.valueOf(s1) + p_getLocationIndexed_1_ + s2;
/* 269 */     ResourceLocation resourcelocation = new ResourceLocation(p_getLocationIndexed_0_.getResourceDomain(), s3);
/* 270 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getParentPath(String p_getParentPath_0_) {
/* 277 */     for (int i = 0; i < DEPENDANT_SUFFIXES.length; i++) {
/*     */       
/* 279 */       String s = DEPENDANT_SUFFIXES[i];
/*     */       
/* 281 */       if (p_getParentPath_0_.endsWith(s)) {
/*     */         
/* 283 */         String s1 = p_getParentPath_0_.substring(0, p_getParentPath_0_.length() - s.length());
/* 284 */         return s1;
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getTextureVariants(ResourceLocation p_getTextureVariants_0_) {
/* 293 */     List<ResourceLocation> list = new ArrayList();
/* 294 */     list.add(p_getTextureVariants_0_);
/* 295 */     ResourceLocation resourcelocation = getMcpatcherLocation(p_getTextureVariants_0_);
/*     */     
/* 297 */     if (resourcelocation == null)
/*     */     {
/* 299 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 303 */     for (int i = 1; i < list.size() + 10; i++) {
/*     */       
/* 305 */       int j = i + 1;
/* 306 */       ResourceLocation resourcelocation1 = getLocationIndexed(resourcelocation, j);
/*     */       
/* 308 */       if (Config.hasResource(resourcelocation1))
/*     */       {
/* 310 */         list.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 314 */     if (list.size() <= 1)
/*     */     {
/* 316 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 320 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 321 */     Config.dbg("RandomMobs: " + p_getTextureVariants_0_.getResourcePath() + ", variants: " + aresourcelocation.length);
/* 322 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetTextures() {
/* 329 */     locationProperties.clear();
/*     */     
/* 331 */     if (Config.isRandomMobs())
/*     */     {
/* 333 */       initialize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initialize() {
/* 339 */     renderGlobal = Config.getRenderGlobal();
/*     */     
/* 341 */     if (renderGlobal != null) {
/*     */       
/* 343 */       initialized = true;
/* 344 */       List<String> list = new ArrayList();
/* 345 */       list.add("bat");
/* 346 */       list.add("blaze");
/* 347 */       list.add("cat/black");
/* 348 */       list.add("cat/ocelot");
/* 349 */       list.add("cat/red");
/* 350 */       list.add("cat/siamese");
/* 351 */       list.add("chicken");
/* 352 */       list.add("cow/cow");
/* 353 */       list.add("cow/mooshroom");
/* 354 */       list.add("creeper/creeper");
/* 355 */       list.add("enderman/enderman");
/* 356 */       list.add("enderman/enderman_eyes");
/* 357 */       list.add("ghast/ghast");
/* 358 */       list.add("ghast/ghast_shooting");
/* 359 */       list.add("iron_golem");
/* 360 */       list.add("pig/pig");
/* 361 */       list.add("sheep/sheep");
/* 362 */       list.add("sheep/sheep_fur");
/* 363 */       list.add("silverfish");
/* 364 */       list.add("skeleton/skeleton");
/* 365 */       list.add("skeleton/wither_skeleton");
/* 366 */       list.add("slime/slime");
/* 367 */       list.add("slime/magmacube");
/* 368 */       list.add("snowman");
/* 369 */       list.add("spider/cave_spider");
/* 370 */       list.add("spider/spider");
/* 371 */       list.add("spider_eyes");
/* 372 */       list.add("squid");
/* 373 */       list.add("villager/villager");
/* 374 */       list.add("villager/butcher");
/* 375 */       list.add("villager/farmer");
/* 376 */       list.add("villager/librarian");
/* 377 */       list.add("villager/priest");
/* 378 */       list.add("villager/smith");
/* 379 */       list.add("wither/wither");
/* 380 */       list.add("wither/wither_armor");
/* 381 */       list.add("wither/wither_invulnerable");
/* 382 */       list.add("wolf/wolf");
/* 383 */       list.add("wolf/wolf_angry");
/* 384 */       list.add("wolf/wolf_collar");
/* 385 */       list.add("wolf/wolf_tame");
/* 386 */       list.add("zombie_pigman");
/* 387 */       list.add("zombie/zombie");
/* 388 */       list.add("zombie/zombie_villager");
/*     */       
/* 390 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 392 */         String s = list.get(i);
/* 393 */         String s1 = "textures/entity/" + s + ".png";
/* 394 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */         
/* 396 */         if (!Config.hasResource(resourcelocation))
/*     */         {
/* 398 */           Config.warn("Not found: " + resourcelocation);
/*     */         }
/*     */         
/* 401 */         getProperties(resourcelocation);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\RandomMobs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
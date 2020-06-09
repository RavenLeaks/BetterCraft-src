/*     */ package optifine;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ public class DynamicLights
/*     */ {
/*  31 */   private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap<>();
/*  32 */   private static long timeUpdateMs = 0L;
/*     */   private static final double MAX_DIST = 7.5D;
/*     */   private static final double MAX_DIST_SQ = 56.25D;
/*     */   private static final int LIGHT_LEVEL_MAX = 15;
/*     */   private static final int LIGHT_LEVEL_FIRE = 15;
/*     */   private static final int LIGHT_LEVEL_BLAZE = 10;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
/*     */   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
/*     */   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
/*  42 */   private static final DataParameter<ItemStack> PARAMETER_ITEM_STACK = new DataParameter(6, DataSerializers.OPTIONAL_ITEM_STACK);
/*     */ 
/*     */ 
/*     */   
/*     */   public static void entityAdded(Entity p_entityAdded_0_, RenderGlobal p_entityAdded_1_) {}
/*     */ 
/*     */   
/*     */   public static void entityRemoved(Entity p_entityRemoved_0_, RenderGlobal p_entityRemoved_1_) {
/*  50 */     synchronized (mapDynamicLights) {
/*     */       
/*  52 */       DynamicLight dynamiclight = mapDynamicLights.remove(IntegerCache.getInteger(p_entityRemoved_0_.getEntityId()));
/*     */       
/*  54 */       if (dynamiclight != null)
/*     */       {
/*  56 */         dynamiclight.updateLitChunks(p_entityRemoved_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(RenderGlobal p_update_0_) {
/*  63 */     long i = System.currentTimeMillis();
/*     */     
/*  65 */     if (i >= timeUpdateMs + 50L) {
/*     */       
/*  67 */       timeUpdateMs = i;
/*     */       
/*  69 */       synchronized (mapDynamicLights) {
/*     */         
/*  71 */         updateMapDynamicLights(p_update_0_);
/*     */         
/*  73 */         if (mapDynamicLights.size() > 0)
/*     */         {
/*  75 */           for (DynamicLight dynamiclight : mapDynamicLights.values())
/*     */           {
/*  77 */             dynamiclight.update(p_update_0_);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateMapDynamicLights(RenderGlobal p_updateMapDynamicLights_0_) {
/*  86 */     WorldClient worldClient = p_updateMapDynamicLights_0_.getWorld();
/*     */     
/*  88 */     if (worldClient != null)
/*     */     {
/*  90 */       for (Entity entity : worldClient.getLoadedEntityList()) {
/*     */         
/*  92 */         int i = getLightLevel(entity);
/*     */         
/*  94 */         if (i > 0) {
/*     */           
/*  96 */           Integer integer = IntegerCache.getInteger(entity.getEntityId());
/*  97 */           DynamicLight dynamiclight = mapDynamicLights.get(integer);
/*     */           
/*  99 */           if (dynamiclight == null) {
/*     */             
/* 101 */             dynamiclight = new DynamicLight(entity);
/* 102 */             mapDynamicLights.put(integer, dynamiclight);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 107 */         Integer integer1 = IntegerCache.getInteger(entity.getEntityId());
/* 108 */         DynamicLight dynamiclight1 = mapDynamicLights.remove(integer1);
/*     */         
/* 110 */         if (dynamiclight1 != null)
/*     */         {
/* 112 */           dynamiclight1.updateLitChunks(p_updateMapDynamicLights_0_);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(BlockPos p_getCombinedLight_0_, int p_getCombinedLight_1_) {
/* 121 */     double d0 = getLightLevel(p_getCombinedLight_0_);
/* 122 */     p_getCombinedLight_1_ = getCombinedLight(d0, p_getCombinedLight_1_);
/* 123 */     return p_getCombinedLight_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(Entity p_getCombinedLight_0_, int p_getCombinedLight_1_) {
/* 128 */     double d0 = getLightLevel(p_getCombinedLight_0_);
/* 129 */     p_getCombinedLight_1_ = getCombinedLight(d0, p_getCombinedLight_1_);
/* 130 */     return p_getCombinedLight_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(double p_getCombinedLight_0_, int p_getCombinedLight_2_) {
/* 135 */     if (p_getCombinedLight_0_ > 0.0D) {
/*     */       
/* 137 */       int i = (int)(p_getCombinedLight_0_ * 16.0D);
/* 138 */       int j = p_getCombinedLight_2_ & 0xFF;
/*     */       
/* 140 */       if (i > j) {
/*     */         
/* 142 */         p_getCombinedLight_2_ &= 0xFFFFFF00;
/* 143 */         p_getCombinedLight_2_ |= i;
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     return p_getCombinedLight_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getLightLevel(BlockPos p_getLightLevel_0_) {
/* 152 */     double d0 = 0.0D;
/*     */     
/* 154 */     synchronized (mapDynamicLights) {
/*     */       
/* 156 */       for (DynamicLight dynamiclight : mapDynamicLights.values()) {
/*     */         
/* 158 */         int i = dynamiclight.getLastLightLevel();
/*     */         
/* 160 */         if (i > 0) {
/*     */           
/* 162 */           double d1 = dynamiclight.getLastPosX();
/* 163 */           double d2 = dynamiclight.getLastPosY();
/* 164 */           double d3 = dynamiclight.getLastPosZ();
/* 165 */           double d4 = p_getLightLevel_0_.getX() - d1;
/* 166 */           double d5 = p_getLightLevel_0_.getY() - d2;
/* 167 */           double d6 = p_getLightLevel_0_.getZ() - d3;
/* 168 */           double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/*     */           
/* 170 */           if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
/*     */             
/* 172 */             i = Config.limit(i - 2, 0, 15);
/* 173 */             d7 *= 2.0D;
/*     */           } 
/*     */           
/* 176 */           if (d7 <= 56.25D) {
/*     */             
/* 178 */             double d8 = Math.sqrt(d7);
/* 179 */             double d9 = 1.0D - d8 / 7.5D;
/* 180 */             double d10 = d9 * i;
/*     */             
/* 182 */             if (d10 > d0)
/*     */             {
/* 184 */               d0 = d10;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     double d11 = Config.limit(d0, 0.0D, 15.0D);
/* 192 */     return d11;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getLightLevel(ItemStack p_getLightLevel_0_) {
/* 197 */     if (p_getLightLevel_0_ == null)
/*     */     {
/* 199 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 203 */     Item item = p_getLightLevel_0_.getItem();
/*     */     
/* 205 */     if (item instanceof ItemBlock) {
/*     */       
/* 207 */       ItemBlock itemblock = (ItemBlock)item;
/* 208 */       Block block = itemblock.getBlock();
/*     */       
/* 210 */       if (block != null)
/*     */       {
/* 212 */         return block.getLightValue(block.getDefaultState());
/*     */       }
/*     */     } 
/*     */     
/* 216 */     if (item == Items.LAVA_BUCKET)
/*     */     {
/* 218 */       return Blocks.LAVA.getLightValue(Blocks.LAVA.getDefaultState());
/*     */     }
/* 220 */     if (item != Items.BLAZE_ROD && item != Items.BLAZE_POWDER) {
/*     */       
/* 222 */       if (item == Items.GLOWSTONE_DUST)
/*     */       {
/* 224 */         return 8;
/*     */       }
/* 226 */       if (item == Items.PRISMARINE_CRYSTALS)
/*     */       {
/* 228 */         return 8;
/*     */       }
/* 230 */       if (item == Items.MAGMA_CREAM)
/*     */       {
/* 232 */         return 8;
/*     */       }
/*     */ 
/*     */       
/* 236 */       return (item == Items.NETHER_STAR) ? (Blocks.BEACON.getLightValue(Blocks.BEACON.getDefaultState()) / 2) : 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 241 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(Entity p_getLightLevel_0_) {
/* 248 */     if (p_getLightLevel_0_ == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight())
/*     */     {
/* 250 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 254 */     if (p_getLightLevel_0_ instanceof EntityPlayer) {
/*     */       
/* 256 */       EntityPlayer entityplayer = (EntityPlayer)p_getLightLevel_0_;
/*     */       
/* 258 */       if (entityplayer.isSpectator())
/*     */       {
/* 260 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 264 */     if (p_getLightLevel_0_.isBurning())
/*     */     {
/* 266 */       return 15;
/*     */     }
/* 268 */     if (p_getLightLevel_0_ instanceof net.minecraft.entity.projectile.EntityFireball)
/*     */     {
/* 270 */       return 15;
/*     */     }
/* 272 */     if (p_getLightLevel_0_ instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 274 */       return 15;
/*     */     }
/* 276 */     if (p_getLightLevel_0_ instanceof EntityBlaze) {
/*     */       
/* 278 */       EntityBlaze entityblaze = (EntityBlaze)p_getLightLevel_0_;
/* 279 */       return entityblaze.isCharged() ? 15 : 10;
/*     */     } 
/* 281 */     if (p_getLightLevel_0_ instanceof EntityMagmaCube) {
/*     */       
/* 283 */       EntityMagmaCube entitymagmacube = (EntityMagmaCube)p_getLightLevel_0_;
/* 284 */       return (entitymagmacube.squishFactor > 0.6D) ? 13 : 8;
/*     */     } 
/*     */ 
/*     */     
/* 288 */     if (p_getLightLevel_0_ instanceof EntityCreeper) {
/*     */       
/* 290 */       EntityCreeper entitycreeper = (EntityCreeper)p_getLightLevel_0_;
/*     */       
/* 292 */       if (entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D)
/*     */       {
/* 294 */         return 15;
/*     */       }
/*     */     } 
/*     */     
/* 298 */     if (p_getLightLevel_0_ instanceof EntityLivingBase) {
/*     */       
/* 300 */       EntityLivingBase entitylivingbase = (EntityLivingBase)p_getLightLevel_0_;
/* 301 */       ItemStack itemstack3 = entitylivingbase.getHeldItemMainhand();
/* 302 */       int i = getLightLevel(itemstack3);
/* 303 */       ItemStack itemstack1 = entitylivingbase.getHeldItemOffhand();
/* 304 */       int j = getLightLevel(itemstack1);
/* 305 */       ItemStack itemstack2 = entitylivingbase.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
/* 306 */       int k = getLightLevel(itemstack2);
/* 307 */       int l = Math.max(i, j);
/* 308 */       return Math.max(l, k);
/*     */     } 
/* 310 */     if (p_getLightLevel_0_ instanceof EntityItem) {
/*     */       
/* 312 */       EntityItem entityitem = (EntityItem)p_getLightLevel_0_;
/* 313 */       ItemStack itemstack = getItemStack(entityitem);
/* 314 */       return getLightLevel(itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 318 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeLights(RenderGlobal p_removeLights_0_) {
/* 326 */     synchronized (mapDynamicLights) {
/*     */       
/* 328 */       Collection<DynamicLight> collection = mapDynamicLights.values();
/* 329 */       Iterator<DynamicLight> iterator = collection.iterator();
/*     */       
/* 331 */       while (iterator.hasNext()) {
/*     */         
/* 333 */         DynamicLight dynamiclight = iterator.next();
/* 334 */         iterator.remove();
/* 335 */         dynamiclight.updateLitChunks(p_removeLights_0_);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear() {
/* 342 */     synchronized (mapDynamicLights) {
/*     */       
/* 344 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCount() {
/* 350 */     synchronized (mapDynamicLights) {
/*     */       
/* 352 */       return mapDynamicLights.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getItemStack(EntityItem p_getItemStack_0_) {
/* 358 */     ItemStack itemstack = (ItemStack)p_getItemStack_0_.getDataManager().get(PARAMETER_ITEM_STACK);
/* 359 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\DynamicLights.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
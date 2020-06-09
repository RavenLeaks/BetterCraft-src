/*     */ package wdl;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.passive.EntityAmbientCreature;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.IAnimals;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import wdl.api.IEntityAdder;
/*     */ import wdl.api.ISpecialEntityHandler;
/*     */ import wdl.api.WDLApi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityUtils
/*     */ {
/*  63 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getEntityTypes() {
/* 123 */     Set<String> returned = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     return returned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Multimap<String, String> getEntitiesByGroup() {
/* 148 */     HashMultimap hashMultimap = HashMultimap.create();
/*     */     
/* 150 */     Set<String> types = getEntityTypes();
/*     */     
/* 152 */     for (String type : types) {
/* 153 */       hashMultimap.put(getEntityGroup(type), type);
/*     */     }
/*     */     
/* 156 */     return (Multimap<String, String>)hashMultimap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDefaultEntityRange(String type) {
/* 170 */     if (type == null) {
/* 171 */       return -1;
/*     */     }
/*     */     
/* 174 */     Iterator<WDLApi.ModInfo<IEntityAdder>> iterator = WDLApi.getImplementingExtensions(IEntityAdder.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IEntityAdder> info = iterator.next();
/* 175 */       List<String> names = ((IEntityAdder)info.mod).getModEntities();
/* 176 */       if (names == null) {
/* 177 */         logger.warn(String.valueOf(info.toString()) + 
/* 178 */             " returned null for getModEntities()!");
/*     */         continue;
/*     */       } 
/* 181 */       if (names.contains(type)) {
/* 182 */         return ((IEntityAdder)info.mod).getDefaultEntityTrackDistance(type);
/*     */       } }
/*     */ 
/*     */     
/* 186 */     iterator = WDLApi.getImplementingExtensions(ISpecialEntityHandler.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<ISpecialEntityHandler> info = (WDLApi.ModInfo<ISpecialEntityHandler>)iterator.next();
/* 187 */       Multimap<String, String> specialEntities = ((ISpecialEntityHandler)info.mod).getSpecialEntities();
/* 188 */       if (specialEntities == null) {
/* 189 */         logger.warn(String.valueOf(info.toString()) + 
/* 190 */             " returned null for getSpecialEntities()!");
/*     */         continue;
/*     */       } 
/* 193 */       for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)specialEntities.entries()) {
/* 194 */         if (((String)e.getValue()).equals(type)) {
/* 195 */           int trackDistance = ((ISpecialEntityHandler)info.mod)
/* 196 */             .getSpecialEntityTrackDistance(e.getValue());
/* 197 */           if (trackDistance < 0)
/*     */           {
/* 199 */             trackDistance = getMostLikelyEntityTrackDistance(e.getKey());
/*     */           }
/* 201 */           return trackDistance;
/*     */         } 
/*     */       }  }
/*     */     
/* 205 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityTrackDistance(Entity e) {
/* 216 */     return getEntityTrackDistance(getTrackDistanceMode(), e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityTrackDistance(String mode, Entity e) {
/* 226 */     if ("default".equals(mode))
/* 227 */       return getMostLikelyEntityTrackDistance(e); 
/* 228 */     if ("server".equals(mode)) {
/* 229 */       int serverDistance = 
/* 230 */         WDLPluginChannels.getEntityRange(getEntityType(e));
/*     */       
/* 232 */       if (serverDistance < 0) {
/*     */         
/* 234 */         int mostLikelyRange = getMostLikelyEntityTrackDistance(e);
/* 235 */         if (mostLikelyRange < 0) {
/* 236 */           return WDLPluginChannels.getEntityRange(
/* 237 */               EntityList.getEntityString(e));
/*     */         }
/* 239 */         return mostLikelyRange;
/*     */       } 
/*     */ 
/*     */       
/* 243 */       return serverDistance;
/* 244 */     }  if ("user".equals(mode)) {
/* 245 */       String prop = WDL.worldProps.getProperty("Entity." + 
/* 246 */           getEntityType(e) + ".TrackDistance", "-1");
/*     */       
/* 248 */       int value = Integer.valueOf(prop).intValue();
/*     */       
/* 250 */       if (value == -1) {
/* 251 */         return getEntityTrackDistance("server", e);
/*     */       }
/* 253 */       return value;
/*     */     } 
/*     */     
/* 256 */     throw new IllegalArgumentException("Mode is not a valid mode: " + mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityTrackDistance(String type) {
/* 267 */     return getEntityTrackDistance(getTrackDistanceMode(), type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityTrackDistance(String mode, String type) {
/* 278 */     if ("default".equals(mode)) {
/* 279 */       int mostLikelyDistance = getMostLikelyEntityTrackDistance(type);
/*     */       
/* 281 */       if (mostLikelyDistance < 0)
/*     */       {
/*     */         
/* 284 */         for (WDLApi.ModInfo<ISpecialEntityHandler> info : (Iterable<WDLApi.ModInfo<ISpecialEntityHandler>>)WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
/* 285 */           Multimap<String, String> specialEntities = ((ISpecialEntityHandler)info.mod).getSpecialEntities();
/*     */           
/* 287 */           for (Map.Entry<String, String> mapping : (Iterable<Map.Entry<String, String>>)specialEntities.entries()) {
/* 288 */             if (type.equals(mapping.getValue()))
/*     */             {
/* 290 */               return getEntityTrackDistance(mode, 
/* 291 */                   mapping.getKey());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/* 296 */       return mostLikelyDistance;
/* 297 */     }  if ("server".equals(mode)) {
/* 298 */       int serverDistance = 
/* 299 */         WDLPluginChannels.getEntityRange(type);
/*     */       
/* 301 */       if (serverDistance < 0) {
/* 302 */         int mostLikelyDistance = getMostLikelyEntityTrackDistance(type);
/*     */         
/* 304 */         if (mostLikelyDistance < 0) {
/*     */           
/* 306 */           for (WDLApi.ModInfo<ISpecialEntityHandler> info : (Iterable<WDLApi.ModInfo<ISpecialEntityHandler>>)WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
/*     */             
/* 308 */             Multimap<String, String> specialEntities = ((ISpecialEntityHandler)info.mod).getSpecialEntities();
/*     */             
/* 310 */             for (Map.Entry<String, String> mapping : (Iterable<Map.Entry<String, String>>)specialEntities.entries()) {
/* 311 */               if (type.equals(mapping.getValue()))
/*     */               {
/* 313 */                 return getEntityTrackDistance(mode, 
/* 314 */                     mapping.getKey());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 319 */           return mostLikelyDistance;
/*     */         } 
/*     */       } 
/*     */       
/* 323 */       return serverDistance;
/* 324 */     }  if ("user".equals(mode)) {
/* 325 */       String prop = WDL.worldProps.getProperty("Entity." + 
/* 326 */           type + ".TrackDistance", "-1");
/*     */       
/* 328 */       int value = Integer.valueOf(prop).intValue();
/*     */       
/* 330 */       if (value == -1) {
/* 331 */         return getEntityTrackDistance("server", type);
/*     */       }
/* 333 */       return value;
/*     */     } 
/*     */     
/* 336 */     throw new IllegalArgumentException("Mode is not a valid mode: " + mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityGroup(String type) {
/* 388 */     return "Other";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEntityEnabled(Entity e) {
/* 398 */     return isEntityEnabled(getEntityType(e));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEntityEnabled(String type) {
/* 408 */     boolean groupEnabled = WDL.worldProps.getProperty("EntityGroup." + 
/* 409 */         getEntityGroup(type) + ".Enabled", "true").equals("true");
/* 410 */     boolean singleEnabled = WDL.worldProps.getProperty("Entity." + 
/* 411 */         type + ".Enabled", "true").equals("true");
/*     */     
/* 413 */     return (groupEnabled && singleEnabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityType(Entity e) {
/* 423 */     String vanillaName = EntityList.getEntityString(e);
/*     */     
/* 425 */     for (WDLApi.ModInfo<ISpecialEntityHandler> info : (Iterable<WDLApi.ModInfo<ISpecialEntityHandler>>)WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
/* 426 */       if (((ISpecialEntityHandler)info.mod).getSpecialEntities().containsKey(vanillaName)) {
/* 427 */         String specialName = ((ISpecialEntityHandler)info.mod).getSpecialEntityName(e);
/* 428 */         if (specialName != null) {
/* 429 */           return specialName;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 434 */     return vanillaName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMostLikelyEntityTrackDistance(Entity e) {
/* 442 */     if (WDL.isSpigot()) {
/* 443 */       return getDefaultSpigotEntityRange(e.getClass());
/*     */     }
/* 445 */     return getDefaultEntityRange(getEntityType(e));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMostLikelyEntityTrackDistance(String type) {
/* 463 */     return getDefaultEntityRange(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getVanillaEntityRange(String type) {
/* 478 */     return getVanillaEntityRange((Class)EntityList.field_191308_b.getObject(new ResourceLocation(type)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTrackDistanceMode() {
/* 485 */     return WDL.worldProps.getProperty("Entity.TrackDistanceMode", "server");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getVanillaEntityRange(Class<?> c) {
/* 499 */     if (c == null) {
/* 500 */       return -1;
/*     */     }
/* 502 */     if (EntityFishHook.class.isAssignableFrom(c) || 
/* 503 */       EntityArrow.class.isAssignableFrom(c) || 
/* 504 */       EntitySmallFireball.class.isAssignableFrom(c) || 
/* 505 */       EntityFireball.class.isAssignableFrom(c) || 
/* 506 */       EntitySnowball.class.isAssignableFrom(c) || 
/* 507 */       EntityEnderPearl.class.isAssignableFrom(c) || 
/* 508 */       EntityEnderEye.class.isAssignableFrom(c) || 
/* 509 */       EntityEgg.class.isAssignableFrom(c) || 
/* 510 */       EntityPotion.class.isAssignableFrom(c) || 
/* 511 */       EntityExpBottle.class.isAssignableFrom(c) || 
/* 512 */       EntityFireworkRocket.class.isAssignableFrom(c) || 
/* 513 */       EntityItem.class.isAssignableFrom(c) || 
/* 514 */       EntitySquid.class.isAssignableFrom(c))
/* 515 */       return 64; 
/* 516 */     if (EntityMinecart.class.isAssignableFrom(c) || 
/* 517 */       EntityBoat.class.isAssignableFrom(c) || 
/* 518 */       EntityWither.class.isAssignableFrom(c) || 
/* 519 */       EntityBat.class.isAssignableFrom(c) || 
/* 520 */       IAnimals.class.isAssignableFrom(c))
/* 521 */       return 80; 
/* 522 */     if (EntityDragon.class.isAssignableFrom(c) || 
/* 523 */       EntityTNTPrimed.class.isAssignableFrom(c) || 
/* 524 */       EntityFallingBlock.class.isAssignableFrom(c) || 
/* 525 */       EntityHanging.class.isAssignableFrom(c) || 
/* 526 */       EntityArmorStand.class.isAssignableFrom(c) || 
/* 527 */       EntityXPOrb.class.isAssignableFrom(c))
/* 528 */       return 160; 
/* 529 */     if (EntityEnderCrystal.class.isAssignableFrom(c)) {
/* 530 */       return 256;
/*     */     }
/* 532 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDefaultSpigotEntityRange(Class<?> c) {
/* 544 */     int monsterRange = 48;
/* 545 */     int animalRange = 48;
/* 546 */     int miscRange = 32;
/* 547 */     int otherRange = 64;
/*     */ 
/*     */     
/* 550 */     if (EntityMob.class.isAssignableFrom(c) || 
/* 551 */       EntitySlime.class.isAssignableFrom(c))
/* 552 */       return 48; 
/* 553 */     if (EntityCreature.class.isAssignableFrom(c) || 
/* 554 */       EntityAmbientCreature.class.isAssignableFrom(c))
/* 555 */       return 48; 
/* 556 */     if (EntityItemFrame.class.isAssignableFrom(c) || 
/* 557 */       EntityPainting.class.isAssignableFrom(c) || 
/* 558 */       EntityItem.class.isAssignableFrom(c) || 
/* 559 */       EntityXPOrb.class.isAssignableFrom(c)) {
/* 560 */       return 32;
/*     */     }
/* 562 */     return 64;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
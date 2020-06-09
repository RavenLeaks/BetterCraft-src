/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.SPacketSetPassengers;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class EntityTracker
/*     */ {
/*  51 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final WorldServer theWorld;
/*  53 */   private final Set<EntityTrackerEntry> trackedEntities = Sets.newHashSet();
/*  54 */   private final IntHashMap<EntityTrackerEntry> trackedEntityHashTable = new IntHashMap();
/*     */   
/*     */   private int maxTrackingDistanceThreshold;
/*     */   
/*     */   public EntityTracker(WorldServer theWorldIn) {
/*  59 */     this.theWorld = theWorldIn;
/*  60 */     this.maxTrackingDistanceThreshold = theWorldIn.getMinecraftServer().getPlayerList().getEntityViewDistance();
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getPositionLong(double value) {
/*  65 */     return MathHelper.lFloor(value * 4096.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateServerPosition(Entity entityIn, double x, double y, double z) {
/*  70 */     entityIn.serverPosX = getPositionLong(x);
/*  71 */     entityIn.serverPosY = getPositionLong(y);
/*  72 */     entityIn.serverPosZ = getPositionLong(z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void trackEntity(Entity entityIn) {
/*  77 */     if (entityIn instanceof EntityPlayerMP) {
/*     */       
/*  79 */       trackEntity(entityIn, 512, 2);
/*  80 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/*  82 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */       {
/*  84 */         if (entitytrackerentry.getTrackedEntity() != entityplayermp)
/*     */         {
/*  86 */           entitytrackerentry.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       }
/*     */     
/*  90 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityFishHook) {
/*     */       
/*  92 */       addEntityToTracker(entityIn, 64, 5, true);
/*     */     }
/*  94 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityArrow) {
/*     */       
/*  96 */       addEntityToTracker(entityIn, 64, 20, false);
/*     */     }
/*  98 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*     */       
/* 100 */       addEntityToTracker(entityIn, 64, 10, false);
/*     */     }
/* 102 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityFireball) {
/*     */       
/* 104 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 106 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntitySnowball) {
/*     */       
/* 108 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 110 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityLlamaSpit) {
/*     */       
/* 112 */       addEntityToTracker(entityIn, 64, 10, false);
/*     */     }
/* 114 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderPearl) {
/*     */       
/* 116 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 118 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderEye) {
/*     */       
/* 120 */       addEntityToTracker(entityIn, 64, 4, true);
/*     */     }
/* 122 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityEgg) {
/*     */       
/* 124 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 126 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityPotion) {
/*     */       
/* 128 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 130 */     else if (entityIn instanceof net.minecraft.entity.item.EntityExpBottle) {
/*     */       
/* 132 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 134 */     else if (entityIn instanceof net.minecraft.entity.item.EntityFireworkRocket) {
/*     */       
/* 136 */       addEntityToTracker(entityIn, 64, 10, true);
/*     */     }
/* 138 */     else if (entityIn instanceof net.minecraft.entity.item.EntityItem) {
/*     */       
/* 140 */       addEntityToTracker(entityIn, 64, 20, true);
/*     */     }
/* 142 */     else if (entityIn instanceof net.minecraft.entity.item.EntityMinecart) {
/*     */       
/* 144 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 146 */     else if (entityIn instanceof net.minecraft.entity.item.EntityBoat) {
/*     */       
/* 148 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 150 */     else if (entityIn instanceof net.minecraft.entity.passive.EntitySquid) {
/*     */       
/* 152 */       addEntityToTracker(entityIn, 64, 3, true);
/*     */     }
/* 154 */     else if (entityIn instanceof net.minecraft.entity.boss.EntityWither) {
/*     */       
/* 156 */       addEntityToTracker(entityIn, 80, 3, false);
/*     */     }
/* 158 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityShulkerBullet) {
/*     */       
/* 160 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 162 */     else if (entityIn instanceof net.minecraft.entity.passive.EntityBat) {
/*     */       
/* 164 */       addEntityToTracker(entityIn, 80, 3, false);
/*     */     }
/* 166 */     else if (entityIn instanceof net.minecraft.entity.boss.EntityDragon) {
/*     */       
/* 168 */       addEntityToTracker(entityIn, 160, 3, true);
/*     */     }
/* 170 */     else if (entityIn instanceof net.minecraft.entity.passive.IAnimals) {
/*     */       
/* 172 */       addEntityToTracker(entityIn, 80, 3, true);
/*     */     }
/* 174 */     else if (entityIn instanceof net.minecraft.entity.item.EntityTNTPrimed) {
/*     */       
/* 176 */       addEntityToTracker(entityIn, 160, 10, true);
/*     */     }
/* 178 */     else if (entityIn instanceof net.minecraft.entity.item.EntityFallingBlock) {
/*     */       
/* 180 */       addEntityToTracker(entityIn, 160, 20, true);
/*     */     }
/* 182 */     else if (entityIn instanceof EntityHanging) {
/*     */       
/* 184 */       addEntityToTracker(entityIn, 160, 2147483647, false);
/*     */     }
/* 186 */     else if (entityIn instanceof net.minecraft.entity.item.EntityArmorStand) {
/*     */       
/* 188 */       addEntityToTracker(entityIn, 160, 3, true);
/*     */     }
/* 190 */     else if (entityIn instanceof net.minecraft.entity.item.EntityXPOrb) {
/*     */       
/* 192 */       addEntityToTracker(entityIn, 160, 20, true);
/*     */     }
/* 194 */     else if (entityIn instanceof EntityAreaEffectCloud) {
/*     */       
/* 196 */       addEntityToTracker(entityIn, 160, 2147483647, true);
/*     */     }
/* 198 */     else if (entityIn instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*     */       
/* 200 */       addEntityToTracker(entityIn, 256, 2147483647, false);
/*     */     }
/* 202 */     else if (entityIn instanceof net.minecraft.entity.projectile.EntityEvokerFangs) {
/*     */       
/* 204 */       addEntityToTracker(entityIn, 160, 2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void trackEntity(Entity entityIn, int trackingRange, int updateFrequency) {
/* 210 */     addEntityToTracker(entityIn, trackingRange, updateFrequency, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToTracker(Entity entityIn, int trackingRange, final int updateFrequency, boolean sendVelocityUpdates) {
/*     */     try {
/* 220 */       if (this.trackedEntityHashTable.containsItem(entityIn.getEntityId()))
/*     */       {
/* 222 */         throw new IllegalStateException("Entity is already tracked!");
/*     */       }
/*     */       
/* 225 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entityIn, trackingRange, this.maxTrackingDistanceThreshold, updateFrequency, sendVelocityUpdates);
/* 226 */       this.trackedEntities.add(entitytrackerentry);
/* 227 */       this.trackedEntityHashTable.addKey(entityIn.getEntityId(), entitytrackerentry);
/* 228 */       entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
/*     */     }
/* 230 */     catch (Throwable throwable) {
/*     */       
/* 232 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
/* 233 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
/* 234 */       crashreportcategory.addCrashSection("Tracking range", String.valueOf(trackingRange) + " blocks");
/* 235 */       crashreportcategory.setDetail("Update interval", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 239 */               String s = "Once per " + updateFrequency + " ticks";
/*     */               
/* 241 */               if (updateFrequency == Integer.MAX_VALUE)
/*     */               {
/* 243 */                 s = "Maximum (" + s + ")";
/*     */               }
/*     */               
/* 246 */               return s;
/*     */             }
/*     */           });
/* 249 */       entityIn.addEntityCrashInfo(crashreportcategory);
/* 250 */       ((EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId())).getTrackedEntity().addEntityCrashInfo(crashreport.makeCategory("Entity That Is Already Tracked"));
/*     */ 
/*     */       
/*     */       try {
/* 254 */         throw new ReportedException(crashreport);
/*     */       }
/* 256 */       catch (ReportedException reportedexception) {
/*     */         
/* 258 */         LOGGER.error("\"Silently\" catching entity tracking error.", (Throwable)reportedexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void untrackEntity(Entity entityIn) {
/* 265 */     if (entityIn instanceof EntityPlayerMP) {
/*     */       
/* 267 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/* 269 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */       {
/* 271 */         entitytrackerentry.removeFromTrackedPlayers(entityplayermp);
/*     */       }
/*     */     } 
/*     */     
/* 275 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(entityIn.getEntityId());
/*     */     
/* 277 */     if (entitytrackerentry1 != null) {
/*     */       
/* 279 */       this.trackedEntities.remove(entitytrackerentry1);
/* 280 */       entitytrackerentry1.sendDestroyEntityPacketToTrackedPlayers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTrackedEntities() {
/* 286 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*     */     
/* 288 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 290 */       entitytrackerentry.updatePlayerList(this.theWorld.playerEntities);
/*     */       
/* 292 */       if (entitytrackerentry.playerEntitiesUpdated) {
/*     */         
/* 294 */         Entity entity = entitytrackerentry.getTrackedEntity();
/*     */         
/* 296 */         if (entity instanceof EntityPlayerMP)
/*     */         {
/* 298 */           list.add((EntityPlayerMP)entity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 305 */       EntityPlayerMP entityplayermp = list.get(i);
/*     */       
/* 307 */       for (EntityTrackerEntry entitytrackerentry1 : this.trackedEntities) {
/*     */         
/* 309 */         if (entitytrackerentry1.getTrackedEntity() != entityplayermp)
/*     */         {
/* 311 */           entitytrackerentry1.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateVisibility(EntityPlayerMP player) {
/* 319 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 321 */       if (entitytrackerentry.getTrackedEntity() == player) {
/*     */         
/* 323 */         entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
/*     */         
/*     */         continue;
/*     */       } 
/* 327 */       entitytrackerentry.updatePlayerEntity(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllTrackingEntity(Entity entityIn, Packet<?> packetIn) {
/* 334 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 336 */     if (entitytrackerentry != null)
/*     */     {
/* 338 */       entitytrackerentry.sendPacketToTrackedPlayers(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendToTrackingAndSelf(Entity entityIn, Packet<?> packetIn) {
/* 344 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 346 */     if (entitytrackerentry != null)
/*     */     {
/* 348 */       entitytrackerentry.sendToTrackingAndSelf(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTrackers(EntityPlayerMP player) {
/* 354 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */     {
/* 356 */       entitytrackerentry.removeTrackedPlayerSymmetric(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendLeashedEntitiesInChunk(EntityPlayerMP player, Chunk chunkIn) {
/* 366 */     List<Entity> list = Lists.newArrayList();
/* 367 */     List<Entity> list1 = Lists.newArrayList();
/*     */     
/* 369 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*     */       
/* 371 */       Entity entity = entitytrackerentry.getTrackedEntity();
/*     */       
/* 373 */       if (entity != player && entity.chunkCoordX == chunkIn.xPosition && entity.chunkCoordZ == chunkIn.zPosition) {
/*     */         
/* 375 */         entitytrackerentry.updatePlayerEntity(player);
/*     */         
/* 377 */         if (entity instanceof EntityLiving && ((EntityLiving)entity).getLeashedToEntity() != null)
/*     */         {
/* 379 */           list.add(entity);
/*     */         }
/*     */         
/* 382 */         if (!entity.getPassengers().isEmpty())
/*     */         {
/* 384 */           list1.add(entity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 389 */     if (!list.isEmpty())
/*     */     {
/* 391 */       for (Entity entity1 : list)
/*     */       {
/* 393 */         player.connection.sendPacket((Packet)new SPacketEntityAttach(entity1, ((EntityLiving)entity1).getLeashedToEntity()));
/*     */       }
/*     */     }
/*     */     
/* 397 */     if (!list1.isEmpty())
/*     */     {
/* 399 */       for (Entity entity2 : list1)
/*     */       {
/* 401 */         player.connection.sendPacket((Packet)new SPacketSetPassengers(entity2));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setViewDistance(int p_187252_1_) {
/* 408 */     this.maxTrackingDistanceThreshold = (p_187252_1_ - 1) * 16;
/*     */     
/* 410 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities)
/*     */     {
/* 412 */       entitytrackerentry.setMaxRange(this.maxTrackingDistanceThreshold);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.ai.attributes.AttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.server.SPacketEntity;
/*     */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.SPacketEntityHeadLook;
/*     */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.SPacketEntityProperties;
/*     */ import net.minecraft.network.play.server.SPacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.SPacketSetPassengers;
/*     */ import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.SPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPlayer;
/*     */ import net.minecraft.network.play.server.SPacketUseBed;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.storage.MapData;
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
/*     */ public class EntityTrackerEntry
/*     */ {
/*  72 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final Entity trackedEntity;
/*     */   
/*     */   private final int range;
/*     */   
/*     */   private int maxRange;
/*     */   
/*     */   private final int updateFrequency;
/*     */   
/*     */   private long encodedPosX;
/*     */   
/*     */   private long encodedPosY;
/*     */   
/*     */   private long encodedPosZ;
/*     */   
/*     */   private int encodedRotationYaw;
/*     */   
/*     */   private int encodedRotationPitch;
/*     */   
/*     */   private int lastHeadMotion;
/*     */   
/*     */   private double lastTrackedEntityMotionX;
/*     */   
/*     */   private double lastTrackedEntityMotionY;
/*     */   
/*     */   private double motionZ;
/*     */   
/*     */   public int updateCounter;
/*     */   
/*     */   private double lastTrackedEntityPosX;
/*     */   
/*     */   private double lastTrackedEntityPosY;
/*     */   
/*     */   private double lastTrackedEntityPosZ;
/*     */   
/*     */   private boolean updatedPlayerVisibility;
/*     */   
/*     */   private final boolean sendVelocityUpdates;
/*     */   private int ticksSinceLastForcedTeleport;
/* 112 */   private List<Entity> passengers = Collections.emptyList();
/*     */   private boolean ridingEntity;
/*     */   private boolean onGround;
/*     */   public boolean playerEntitiesUpdated;
/* 116 */   private final Set<EntityPlayerMP> trackingPlayers = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public EntityTrackerEntry(Entity entityIn, int rangeIn, int maxRangeIn, int updateFrequencyIn, boolean sendVelocityUpdatesIn) {
/* 120 */     this.trackedEntity = entityIn;
/* 121 */     this.range = rangeIn;
/* 122 */     this.maxRange = maxRangeIn;
/* 123 */     this.updateFrequency = updateFrequencyIn;
/* 124 */     this.sendVelocityUpdates = sendVelocityUpdatesIn;
/* 125 */     this.encodedPosX = EntityTracker.getPositionLong(entityIn.posX);
/* 126 */     this.encodedPosY = EntityTracker.getPositionLong(entityIn.posY);
/* 127 */     this.encodedPosZ = EntityTracker.getPositionLong(entityIn.posZ);
/* 128 */     this.encodedRotationYaw = MathHelper.floor(entityIn.rotationYaw * 256.0F / 360.0F);
/* 129 */     this.encodedRotationPitch = MathHelper.floor(entityIn.rotationPitch * 256.0F / 360.0F);
/* 130 */     this.lastHeadMotion = MathHelper.floor(entityIn.getRotationYawHead() * 256.0F / 360.0F);
/* 131 */     this.onGround = entityIn.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 136 */     if (p_equals_1_ instanceof EntityTrackerEntry)
/*     */     {
/* 138 */       return (((EntityTrackerEntry)p_equals_1_).trackedEntity.getEntityId() == this.trackedEntity.getEntityId());
/*     */     }
/*     */ 
/*     */     
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 148 */     return this.trackedEntity.getEntityId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerList(List<EntityPlayer> players) {
/* 153 */     this.playerEntitiesUpdated = false;
/*     */     
/* 155 */     if (!this.updatedPlayerVisibility || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0D) {
/*     */       
/* 157 */       this.lastTrackedEntityPosX = this.trackedEntity.posX;
/* 158 */       this.lastTrackedEntityPosY = this.trackedEntity.posY;
/* 159 */       this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
/* 160 */       this.updatedPlayerVisibility = true;
/* 161 */       this.playerEntitiesUpdated = true;
/* 162 */       updatePlayerEntities(players);
/*     */     } 
/*     */     
/* 165 */     List<Entity> list = this.trackedEntity.getPassengers();
/*     */     
/* 167 */     if (!list.equals(this.passengers)) {
/*     */       
/* 169 */       this.passengers = list;
/* 170 */       sendPacketToTrackedPlayers((Packet<?>)new SPacketSetPassengers(this.trackedEntity));
/*     */     } 
/*     */     
/* 173 */     if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
/*     */       
/* 175 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 176 */       ItemStack itemstack = entityitemframe.getDisplayedItem();
/*     */       
/* 178 */       if (itemstack.getItem() instanceof net.minecraft.item.ItemMap) {
/*     */         
/* 180 */         MapData mapdata = Items.FILLED_MAP.getMapData(itemstack, this.trackedEntity.world);
/*     */         
/* 182 */         for (EntityPlayer entityplayer : players) {
/*     */           
/* 184 */           EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
/* 185 */           mapdata.updateVisiblePlayers((EntityPlayer)entityplayermp, itemstack);
/* 186 */           Packet<?> packet = Items.FILLED_MAP.createMapDataPacket(itemstack, this.trackedEntity.world, (EntityPlayer)entityplayermp);
/*     */           
/* 188 */           if (packet != null)
/*     */           {
/* 190 */             entityplayermp.connection.sendPacket(packet);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 195 */       sendMetadataToAllAssociatedPlayers();
/*     */     } 
/*     */     
/* 198 */     if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataManager().isDirty()) {
/*     */       
/* 200 */       if (this.trackedEntity.isRiding()) {
/*     */         
/* 202 */         int j1 = MathHelper.floor(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 203 */         int l1 = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 204 */         boolean flag3 = !(Math.abs(j1 - this.encodedRotationYaw) < 1 && Math.abs(l1 - this.encodedRotationPitch) < 1);
/*     */         
/* 206 */         if (flag3) {
/*     */           
/* 208 */           sendPacketToTrackedPlayers((Packet<?>)new SPacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)j1, (byte)l1, this.trackedEntity.onGround));
/* 209 */           this.encodedRotationYaw = j1;
/* 210 */           this.encodedRotationPitch = l1;
/*     */         } 
/*     */         
/* 213 */         this.encodedPosX = EntityTracker.getPositionLong(this.trackedEntity.posX);
/* 214 */         this.encodedPosY = EntityTracker.getPositionLong(this.trackedEntity.posY);
/* 215 */         this.encodedPosZ = EntityTracker.getPositionLong(this.trackedEntity.posZ);
/* 216 */         sendMetadataToAllAssociatedPlayers();
/* 217 */         this.ridingEntity = true;
/*     */       } else {
/*     */         SPacketEntityTeleport sPacketEntityTeleport;
/*     */         
/* 221 */         this.ticksSinceLastForcedTeleport++;
/* 222 */         long i1 = EntityTracker.getPositionLong(this.trackedEntity.posX);
/* 223 */         long i2 = EntityTracker.getPositionLong(this.trackedEntity.posY);
/* 224 */         long j2 = EntityTracker.getPositionLong(this.trackedEntity.posZ);
/* 225 */         int k2 = MathHelper.floor(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 226 */         int i = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 227 */         long j = i1 - this.encodedPosX;
/* 228 */         long k = i2 - this.encodedPosY;
/* 229 */         long l = j2 - this.encodedPosZ;
/* 230 */         Packet<?> packet1 = null;
/* 231 */         boolean flag = !(j * j + k * k + l * l < 128L && this.updateCounter % 60 != 0);
/* 232 */         boolean flag1 = !(Math.abs(k2 - this.encodedRotationYaw) < 1 && Math.abs(i - this.encodedRotationPitch) < 1);
/*     */         
/* 234 */         if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow)
/*     */         {
/* 236 */           if (j >= -32768L && j < 32768L && k >= -32768L && k < 32768L && l >= -32768L && l < 32768L && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
/*     */             
/* 238 */             if ((!flag || !flag1) && !(this.trackedEntity instanceof EntityArrow)) {
/*     */               
/* 240 */               if (flag)
/*     */               {
/* 242 */                 SPacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove = new SPacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), j, k, l, this.trackedEntity.onGround);
/*     */               }
/* 244 */               else if (flag1)
/*     */               {
/* 246 */                 SPacketEntity.S16PacketEntityLook s16PacketEntityLook = new SPacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)k2, (byte)i, this.trackedEntity.onGround);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 251 */               SPacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove = new SPacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), j, k, l, (byte)k2, (byte)i, this.trackedEntity.onGround);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 256 */             this.onGround = this.trackedEntity.onGround;
/* 257 */             this.ticksSinceLastForcedTeleport = 0;
/* 258 */             resetPlayerVisibility();
/* 259 */             sPacketEntityTeleport = new SPacketEntityTeleport(this.trackedEntity);
/*     */           } 
/*     */         }
/*     */         
/* 263 */         boolean flag2 = this.sendVelocityUpdates;
/*     */         
/* 265 */         if (this.trackedEntity instanceof EntityLivingBase && ((EntityLivingBase)this.trackedEntity).isElytraFlying())
/*     */         {
/* 267 */           flag2 = true;
/*     */         }
/*     */         
/* 270 */         if (flag2 && this.updateCounter > 0) {
/*     */           
/* 272 */           double d0 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
/* 273 */           double d1 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
/* 274 */           double d2 = this.trackedEntity.motionZ - this.motionZ;
/* 275 */           double d3 = 0.02D;
/* 276 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 278 */           if (d4 > 4.0E-4D || (d4 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D)) {
/*     */             
/* 280 */             this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 281 */             this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 282 */             this.motionZ = this.trackedEntity.motionZ;
/* 283 */             sendPacketToTrackedPlayers((Packet<?>)new SPacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
/*     */           } 
/*     */         } 
/*     */         
/* 287 */         if (sPacketEntityTeleport != null)
/*     */         {
/* 289 */           sendPacketToTrackedPlayers((Packet<?>)sPacketEntityTeleport);
/*     */         }
/*     */         
/* 292 */         sendMetadataToAllAssociatedPlayers();
/*     */         
/* 294 */         if (flag) {
/*     */           
/* 296 */           this.encodedPosX = i1;
/* 297 */           this.encodedPosY = i2;
/* 298 */           this.encodedPosZ = j2;
/*     */         } 
/*     */         
/* 301 */         if (flag1) {
/*     */           
/* 303 */           this.encodedRotationYaw = k2;
/* 304 */           this.encodedRotationPitch = i;
/*     */         } 
/*     */         
/* 307 */         this.ridingEntity = false;
/*     */       } 
/*     */       
/* 310 */       int k1 = MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/*     */       
/* 312 */       if (Math.abs(k1 - this.lastHeadMotion) >= 1) {
/*     */         
/* 314 */         sendPacketToTrackedPlayers((Packet<?>)new SPacketEntityHeadLook(this.trackedEntity, (byte)k1));
/* 315 */         this.lastHeadMotion = k1;
/*     */       } 
/*     */       
/* 318 */       this.trackedEntity.isAirBorne = false;
/*     */     } 
/*     */     
/* 321 */     this.updateCounter++;
/*     */     
/* 323 */     if (this.trackedEntity.velocityChanged) {
/*     */       
/* 325 */       sendToTrackingAndSelf((Packet<?>)new SPacketEntityVelocity(this.trackedEntity));
/* 326 */       this.trackedEntity.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendMetadataToAllAssociatedPlayers() {
/* 336 */     EntityDataManager entitydatamanager = this.trackedEntity.getDataManager();
/*     */     
/* 338 */     if (entitydatamanager.isDirty())
/*     */     {
/* 340 */       sendToTrackingAndSelf((Packet<?>)new SPacketEntityMetadata(this.trackedEntity.getEntityId(), entitydatamanager, false));
/*     */     }
/*     */     
/* 343 */     if (this.trackedEntity instanceof EntityLivingBase) {
/*     */       
/* 345 */       AttributeMap attributemap = (AttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 346 */       Set<IAttributeInstance> set = attributemap.getAttributeInstanceSet();
/*     */       
/* 348 */       if (!set.isEmpty())
/*     */       {
/* 350 */         sendToTrackingAndSelf((Packet<?>)new SPacketEntityProperties(this.trackedEntity.getEntityId(), set));
/*     */       }
/*     */       
/* 353 */       set.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketToTrackedPlayers(Packet<?> packetIn) {
/* 362 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers)
/*     */     {
/* 364 */       entityplayermp.connection.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendToTrackingAndSelf(Packet<?> packetIn) {
/* 370 */     sendPacketToTrackedPlayers(packetIn);
/*     */     
/* 372 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 374 */       ((EntityPlayerMP)this.trackedEntity).connection.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDestroyEntityPacketToTrackedPlayers() {
/* 380 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers) {
/*     */       
/* 382 */       this.trackedEntity.removeTrackingPlayer(entityplayermp);
/* 383 */       entityplayermp.removeEntity(this.trackedEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromTrackedPlayers(EntityPlayerMP playerMP) {
/* 389 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 391 */       this.trackedEntity.removeTrackingPlayer(playerMP);
/* 392 */       playerMP.removeEntity(this.trackedEntity);
/* 393 */       this.trackingPlayers.remove(playerMP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntity(EntityPlayerMP playerMP) {
/* 399 */     if (playerMP != this.trackedEntity)
/*     */     {
/* 401 */       if (isVisibleTo(playerMP)) {
/*     */         
/* 403 */         if (!this.trackingPlayers.contains(playerMP) && (isPlayerWatchingThisChunk(playerMP) || this.trackedEntity.forceSpawn))
/*     */         {
/* 405 */           this.trackingPlayers.add(playerMP);
/* 406 */           Packet<?> packet = createSpawnPacket();
/* 407 */           playerMP.connection.sendPacket(packet);
/*     */           
/* 409 */           if (!this.trackedEntity.getDataManager().isEmpty())
/*     */           {
/* 411 */             playerMP.connection.sendPacket((Packet)new SPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataManager(), true));
/*     */           }
/*     */           
/* 414 */           boolean flag = this.sendVelocityUpdates;
/*     */           
/* 416 */           if (this.trackedEntity instanceof EntityLivingBase) {
/*     */             
/* 418 */             AttributeMap attributemap = (AttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 419 */             Collection<IAttributeInstance> collection = attributemap.getWatchedAttributes();
/*     */             
/* 421 */             if (!collection.isEmpty())
/*     */             {
/* 423 */               playerMP.connection.sendPacket((Packet)new SPacketEntityProperties(this.trackedEntity.getEntityId(), collection));
/*     */             }
/*     */             
/* 426 */             if (((EntityLivingBase)this.trackedEntity).isElytraFlying())
/*     */             {
/* 428 */               flag = true;
/*     */             }
/*     */           } 
/*     */           
/* 432 */           this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 433 */           this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 434 */           this.motionZ = this.trackedEntity.motionZ;
/*     */           
/* 436 */           if (flag && !(packet instanceof SPacketSpawnMob))
/*     */           {
/* 438 */             playerMP.connection.sendPacket((Packet)new SPacketEntityVelocity(this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
/*     */           }
/*     */           
/* 441 */           if (this.trackedEntity instanceof EntityLivingBase) {
/*     */             byte b; int i; EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 443 */             for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*     */               
/* 445 */               ItemStack itemstack = ((EntityLivingBase)this.trackedEntity).getItemStackFromSlot(entityequipmentslot);
/*     */               
/* 447 */               if (!itemstack.func_190926_b())
/*     */               {
/* 449 */                 playerMP.connection.sendPacket((Packet)new SPacketEntityEquipment(this.trackedEntity.getEntityId(), entityequipmentslot, itemstack));
/*     */               }
/*     */               b++; }
/*     */           
/*     */           } 
/* 454 */           if (this.trackedEntity instanceof EntityPlayer) {
/*     */             
/* 456 */             EntityPlayer entityplayer = (EntityPlayer)this.trackedEntity;
/*     */             
/* 458 */             if (entityplayer.isPlayerSleeping())
/*     */             {
/* 460 */               playerMP.connection.sendPacket((Packet)new SPacketUseBed(entityplayer, new BlockPos(this.trackedEntity)));
/*     */             }
/*     */           } 
/*     */           
/* 464 */           if (this.trackedEntity instanceof EntityLivingBase) {
/*     */             
/* 466 */             EntityLivingBase entitylivingbase = (EntityLivingBase)this.trackedEntity;
/*     */             
/* 468 */             for (PotionEffect potioneffect : entitylivingbase.getActivePotionEffects())
/*     */             {
/* 470 */               playerMP.connection.sendPacket((Packet)new SPacketEntityEffect(this.trackedEntity.getEntityId(), potioneffect));
/*     */             }
/*     */           } 
/*     */           
/* 474 */           if (!this.trackedEntity.getPassengers().isEmpty())
/*     */           {
/* 476 */             playerMP.connection.sendPacket((Packet)new SPacketSetPassengers(this.trackedEntity));
/*     */           }
/*     */           
/* 479 */           if (this.trackedEntity.isRiding())
/*     */           {
/* 481 */             playerMP.connection.sendPacket((Packet)new SPacketSetPassengers(this.trackedEntity.getRidingEntity()));
/*     */           }
/*     */           
/* 484 */           this.trackedEntity.addTrackingPlayer(playerMP);
/* 485 */           playerMP.addEntity(this.trackedEntity);
/*     */         }
/*     */       
/* 488 */       } else if (this.trackingPlayers.contains(playerMP)) {
/*     */         
/* 490 */         this.trackingPlayers.remove(playerMP);
/* 491 */         this.trackedEntity.removeTrackingPlayer(playerMP);
/* 492 */         playerMP.removeEntity(this.trackedEntity);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisibleTo(EntityPlayerMP playerMP) {
/* 499 */     double d0 = playerMP.posX - this.encodedPosX / 4096.0D;
/* 500 */     double d1 = playerMP.posZ - this.encodedPosZ / 4096.0D;
/* 501 */     int i = Math.min(this.range, this.maxRange);
/* 502 */     return (d0 >= -i && d0 <= i && d1 >= -i && d1 <= i && this.trackedEntity.isSpectatedByPlayer(playerMP));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerWatchingThisChunk(EntityPlayerMP playerMP) {
/* 507 */     return playerMP.getServerWorld().getPlayerChunkMap().isPlayerWatchingChunk(playerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntities(List<EntityPlayer> players) {
/* 512 */     for (int i = 0; i < players.size(); i++)
/*     */     {
/* 514 */       updatePlayerEntity((EntityPlayerMP)players.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet<?> createSpawnPacket() {
/* 520 */     if (this.trackedEntity.isDead)
/*     */     {
/* 522 */       LOGGER.warn("Fetching addPacket for removed entity");
/*     */     }
/*     */     
/* 525 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 527 */       return (Packet<?>)new SPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
/*     */     }
/* 529 */     if (this.trackedEntity instanceof net.minecraft.entity.passive.IAnimals) {
/*     */       
/* 531 */       this.lastHeadMotion = MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 532 */       return (Packet<?>)new SPacketSpawnMob((EntityLivingBase)this.trackedEntity);
/*     */     } 
/* 534 */     if (this.trackedEntity instanceof EntityPainting)
/*     */     {
/* 536 */       return (Packet<?>)new SPacketSpawnPainting((EntityPainting)this.trackedEntity);
/*     */     }
/* 538 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityItem)
/*     */     {
/* 540 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 2, 1);
/*     */     }
/* 542 */     if (this.trackedEntity instanceof EntityMinecart) {
/*     */       
/* 544 */       EntityMinecart entityminecart = (EntityMinecart)this.trackedEntity;
/* 545 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 10, entityminecart.getType().getId());
/*     */     } 
/* 547 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityBoat)
/*     */     {
/* 549 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 1);
/*     */     }
/* 551 */     if (this.trackedEntity instanceof EntityXPOrb)
/*     */     {
/* 553 */       return (Packet<?>)new SPacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
/*     */     }
/* 555 */     if (this.trackedEntity instanceof EntityFishHook) {
/*     */       
/* 557 */       EntityPlayer entityPlayer = ((EntityFishHook)this.trackedEntity).func_190619_l();
/* 558 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 90, (entityPlayer == null) ? this.trackedEntity.getEntityId() : entityPlayer.getEntityId());
/*     */     } 
/* 560 */     if (this.trackedEntity instanceof EntitySpectralArrow) {
/*     */       
/* 562 */       Entity entity1 = ((EntitySpectralArrow)this.trackedEntity).shootingEntity;
/* 563 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 91, 1 + ((entity1 == null) ? this.trackedEntity.getEntityId() : entity1.getEntityId()));
/*     */     } 
/* 565 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityTippedArrow) {
/*     */       
/* 567 */       Entity entity = ((EntityArrow)this.trackedEntity).shootingEntity;
/* 568 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 60, 1 + ((entity == null) ? this.trackedEntity.getEntityId() : entity.getEntityId()));
/*     */     } 
/* 570 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySnowball)
/*     */     {
/* 572 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 61);
/*     */     }
/* 574 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityLlamaSpit)
/*     */     {
/* 576 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 68);
/*     */     }
/* 578 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityPotion)
/*     */     {
/* 580 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 73);
/*     */     }
/* 582 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityExpBottle)
/*     */     {
/* 584 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 75);
/*     */     }
/* 586 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderPearl)
/*     */     {
/* 588 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 65);
/*     */     }
/* 590 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderEye)
/*     */     {
/* 592 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 72);
/*     */     }
/* 594 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityFireworkRocket)
/*     */     {
/* 596 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 76);
/*     */     }
/* 598 */     if (this.trackedEntity instanceof EntityFireball) {
/*     */       
/* 600 */       EntityFireball entityfireball = (EntityFireball)this.trackedEntity;
/* 601 */       SPacketSpawnObject spacketspawnobject = null;
/* 602 */       int i = 63;
/*     */       
/* 604 */       if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*     */         
/* 606 */         i = 64;
/*     */       }
/* 608 */       else if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityDragonFireball) {
/*     */         
/* 610 */         i = 93;
/*     */       }
/* 612 */       else if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityWitherSkull) {
/*     */         
/* 614 */         i = 66;
/*     */       } 
/*     */       
/* 617 */       if (entityfireball.shootingEntity != null) {
/*     */         
/* 619 */         spacketspawnobject = new SPacketSpawnObject(this.trackedEntity, i, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
/*     */       }
/*     */       else {
/*     */         
/* 623 */         spacketspawnobject = new SPacketSpawnObject(this.trackedEntity, i, 0);
/*     */       } 
/*     */       
/* 626 */       spacketspawnobject.setSpeedX((int)(entityfireball.accelerationX * 8000.0D));
/* 627 */       spacketspawnobject.setSpeedY((int)(entityfireball.accelerationY * 8000.0D));
/* 628 */       spacketspawnobject.setSpeedZ((int)(entityfireball.accelerationZ * 8000.0D));
/* 629 */       return (Packet<?>)spacketspawnobject;
/*     */     } 
/* 631 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityShulkerBullet) {
/*     */       
/* 633 */       SPacketSpawnObject spacketspawnobject1 = new SPacketSpawnObject(this.trackedEntity, 67, 0);
/* 634 */       spacketspawnobject1.setSpeedX((int)(this.trackedEntity.motionX * 8000.0D));
/* 635 */       spacketspawnobject1.setSpeedY((int)(this.trackedEntity.motionY * 8000.0D));
/* 636 */       spacketspawnobject1.setSpeedZ((int)(this.trackedEntity.motionZ * 8000.0D));
/* 637 */       return (Packet<?>)spacketspawnobject1;
/*     */     } 
/* 639 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityEgg)
/*     */     {
/* 641 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 62);
/*     */     }
/* 643 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityEvokerFangs)
/*     */     {
/* 645 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 79);
/*     */     }
/* 647 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 649 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 50);
/*     */     }
/* 651 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderCrystal)
/*     */     {
/* 653 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 51);
/*     */     }
/* 655 */     if (this.trackedEntity instanceof EntityFallingBlock) {
/*     */       
/* 657 */       EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.trackedEntity;
/* 658 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(entityfallingblock.getBlock()));
/*     */     } 
/* 660 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityArmorStand)
/*     */     {
/* 662 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 78);
/*     */     }
/* 664 */     if (this.trackedEntity instanceof EntityItemFrame) {
/*     */       
/* 666 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 667 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 71, entityitemframe.facingDirection.getHorizontalIndex(), entityitemframe.getHangingPosition());
/*     */     } 
/* 669 */     if (this.trackedEntity instanceof EntityLeashKnot) {
/*     */       
/* 671 */       EntityLeashKnot entityleashknot = (EntityLeashKnot)this.trackedEntity;
/* 672 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 77, 0, entityleashknot.getHangingPosition());
/*     */     } 
/* 674 */     if (this.trackedEntity instanceof EntityAreaEffectCloud)
/*     */     {
/* 676 */       return (Packet<?>)new SPacketSpawnObject(this.trackedEntity, 3);
/*     */     }
/*     */ 
/*     */     
/* 680 */     throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrackedPlayerSymmetric(EntityPlayerMP playerMP) {
/* 689 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 691 */       this.trackingPlayers.remove(playerMP);
/* 692 */       this.trackedEntity.removeTrackingPlayer(playerMP);
/* 693 */       playerMP.removeEntity(this.trackedEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getTrackedEntity() {
/* 699 */     return this.trackedEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxRange(int maxRangeIn) {
/* 704 */     this.maxRange = maxRangeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetPlayerVisibility() {
/* 709 */     this.updatedPlayerVisibility = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityTrackerEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
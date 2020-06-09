/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_9;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.server.SPacketEntity;
/*     */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ 
/*     */ public class PacketWrapper110 extends PacketWrapper {
/*     */   public PacketWrapper110() {
/*  18 */     super(ProtocolHack.PROTOCOL_110);
/*  19 */     SoundRegistry.registerSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePacket(Packet<?> packet, PacketBuffer buffer) throws Exception {
/*  24 */     if (packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport) {
/*  25 */       buffer.writeVarIntToBuffer(0);
/*  26 */       packet.writePacketData(buffer);
/*  27 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketTabComplete) {
/*  28 */       buffer.writeVarIntToBuffer(1);
/*  29 */       packet.writePacketData(buffer);
/*  30 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketChatMessage) {
/*  31 */       buffer.writeVarIntToBuffer(2);
/*  32 */       packet.writePacketData(buffer);
/*  33 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClientStatus) {
/*  34 */       buffer.writeVarIntToBuffer(3);
/*  35 */       packet.writePacketData(buffer);
/*  36 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClientSettings) {
/*  37 */       buffer.writeVarIntToBuffer(4);
/*  38 */       packet.writePacketData(buffer);
/*  39 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketConfirmTransaction) {
/*  40 */       buffer.writeVarIntToBuffer(5);
/*  41 */       packet.writePacketData(buffer);
/*  42 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketEnchantItem) {
/*  43 */       buffer.writeVarIntToBuffer(6);
/*  44 */       packet.writePacketData(buffer);
/*  45 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClickWindow) {
/*  46 */       buffer.writeVarIntToBuffer(7);
/*  47 */       packet.writePacketData(buffer);
/*  48 */     } else if (packet instanceof CPacketCloseWindow) {
/*  49 */       buffer.writeVarIntToBuffer(8);
/*  50 */       packet.writePacketData(buffer);
/*  51 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketCustomPayload) {
/*  52 */       buffer.writeVarIntToBuffer(9);
/*  53 */       packet.writePacketData(buffer);
/*  54 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketUseEntity) {
/*  55 */       buffer.writeVarIntToBuffer(10);
/*  56 */       packet.writePacketData(buffer);
/*  57 */     } else if (packet instanceof CPacketKeepAlive) {
/*  58 */       buffer.writeVarIntToBuffer(11);
/*  59 */       buffer.writeVarIntToBuffer((int)((CPacketKeepAlive)packet).getKey());
/*  60 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/*  61 */       buffer.writeVarIntToBuffer(12);
/*  62 */       packet.writePacketData(buffer);
/*  63 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/*  64 */       buffer.writeVarIntToBuffer(13);
/*  65 */       packet.writePacketData(buffer);
/*  66 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/*  67 */       buffer.writeVarIntToBuffer(14);
/*  68 */       packet.writePacketData(buffer);
/*  69 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
/*  70 */       buffer.writeVarIntToBuffer(15);
/*  71 */       packet.writePacketData(buffer);
/*  72 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketVehicleMove) {
/*  73 */       buffer.writeVarIntToBuffer(16);
/*  74 */       packet.writePacketData(buffer);
/*  75 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketSteerBoat) {
/*  76 */       buffer.writeVarIntToBuffer(17);
/*  77 */       packet.writePacketData(buffer);
/*  78 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerAbilities) {
/*  79 */       buffer.writeVarIntToBuffer(18);
/*  80 */       packet.writePacketData(buffer);
/*  81 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerDigging) {
/*  82 */       buffer.writeVarIntToBuffer(19);
/*  83 */       packet.writePacketData(buffer);
/*  84 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketEntityAction) {
/*  85 */       buffer.writeVarIntToBuffer(20);
/*  86 */       packet.writePacketData(buffer);
/*  87 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketInput) {
/*  88 */       buffer.writeVarIntToBuffer(21);
/*  89 */       packet.writePacketData(buffer);
/*  90 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketResourcePackStatus) {
/*  91 */       buffer.writeVarIntToBuffer(22);
/*  92 */       packet.writePacketData(buffer);
/*  93 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketHeldItemChange) {
/*  94 */       buffer.writeVarIntToBuffer(23);
/*  95 */       packet.writePacketData(buffer);
/*  96 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketCreativeInventoryAction) {
/*  97 */       buffer.writeVarIntToBuffer(24);
/*  98 */       packet.writePacketData(buffer);
/*  99 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketUpdateSign) {
/* 100 */       buffer.writeVarIntToBuffer(25);
/* 101 */       packet.writePacketData(buffer);
/* 102 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketAnimation) {
/* 103 */       buffer.writeVarIntToBuffer(26);
/* 104 */       packet.writePacketData(buffer);
/* 105 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketSpectate) {
/* 106 */       buffer.writeVarIntToBuffer(27);
/* 107 */       packet.writePacketData(buffer);
/* 108 */     } else if (packet instanceof CPacketPlayerTryUseItemOnBlock) {
/* 109 */       buffer.writeVarIntToBuffer(28);
/* 110 */       CPacketPlayerTryUseItemOnBlock pa = (CPacketPlayerTryUseItemOnBlock)packet;
/* 111 */       buffer.writeBlockPos(pa.position);
/* 112 */       buffer.writeEnumValue((Enum)pa.placedBlockDirection);
/* 113 */       buffer.writeEnumValue((Enum)pa.hand);
/* 114 */       buffer.writeByte((int)(pa.facingX * 16.0F));
/* 115 */       buffer.writeByte((int)(pa.facingY * 16.0F));
/* 116 */       buffer.writeByte((int)(pa.facingZ * 16.0F));
/* 117 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem) {
/* 118 */       buffer.writeVarIntToBuffer(29);
/* 119 */       packet.writePacketData(buffer);
/*     */     }  } public Packet<?> readPacket(int packetId, PacketBuffer buffer) throws Exception { SPacketSpawnObject sPacketSpawnObject; SPacketSpawnExperienceOrb sPacketSpawnExperienceOrb; SPacketSpawnGlobalEntity sPacketSpawnGlobalEntity; SPacketSpawnMob sPacketSpawnMob; SPacketSpawnPainting sPacketSpawnPainting; SPacketSpawnPlayer sPacketSpawnPlayer; SPacketAnimation sPacketAnimation; SPacketStatistics sPacketStatistics; SPacketBlockBreakAnim sPacketBlockBreakAnim; SPacketUpdateTileEntity sPacketUpdateTileEntity; SPacketBlockAction sPacketBlockAction; SPacketBlockChange sPacketBlockChange; SPacketUpdateBossInfo sPacketUpdateBossInfo; SPacketServerDifficulty sPacketServerDifficulty; SPacketTabComplete sPacketTabComplete; SPacketChat sPacketChat; SPacketMultiBlockChange sPacketMultiBlockChange; SPacketConfirmTransaction sPacketConfirmTransaction; CPacketCloseWindow cPacketCloseWindow; SPacketOpenWindow sPacketOpenWindow; SPacketWindowItems sPacketWindowItems; SPacketWindowProperty sPacketWindowProperty; SPacketSetSlot sPacketSetSlot; SPacketCooldown sPacketCooldown; SPacketCustomPayload sPacketCustomPayload; SPacketCustomSound sPacketCustomSound; SPacketDisconnect sPacketDisconnect; SPacketEntityStatus sPacketEntityStatus; SPacketExplosion sPacketExplosion; SPacketUnloadChunk sPacketUnloadChunk; SPacketChangeGameState sPacketChangeGameState; SPacketKeepAlive sPacketKeepAlive; SPacketChunkData sPacketChunkData; SPacketEffect sPacketEffect; SPacketParticles sPacketParticles; SPacketJoinGame sPacketJoinGame; SPacketMaps sPacketMaps; SPacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove; SPacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove; SPacketEntity.S16PacketEntityLook s16PacketEntityLook; SPacketEntity sPacketEntity; SPacketMoveVehicle sPacketMoveVehicle; SPacketSignEditorOpen sPacketSignEditorOpen; SPacketPlayerAbilities sPacketPlayerAbilities; SPacketCombatEvent sPacketCombatEvent; SPacketPlayerListItem sPacketPlayerListItem; SPacketPlayerPosLook sPacketPlayerPosLook; SPacketUseBed sPacketUseBed; SPacketDestroyEntities sPacketDestroyEntities; SPacketRemoveEntityEffect sPacketRemoveEntityEffect; SPacketResourcePackSend sPacketResourcePackSend; SPacketRespawn sPacketRespawn; SPacketEntityHeadLook sPacketEntityHeadLook; SPacketWorldBorder sPacketWorldBorder; SPacketCamera sPacketCamera; SPacketHeldItemChange sPacketHeldItemChange; SPacketDisplayObjective sPacketDisplayObjective; SPacketEntityMetadata sPacketEntityMetadata; SPacketEntityAttach sPacketEntityAttach; SPacketEntityVelocity sPacketEntityVelocity; SPacketEntityEquipment sPacketEntityEquipment; SPacketSetExperience sPacketSetExperience; SPacketUpdateHealth sPacketUpdateHealth; SPacketScoreboardObjective sPacketScoreboardObjective; SPacketSetPassengers sPacketSetPassengers; SPacketTeams sPacketTeams; SPacketUpdateScore sPacketUpdateScore; SPacketSpawnPosition sPacketSpawnPosition; SPacketTimeUpdate sPacketTimeUpdate; SPacketTitle sPacketTitle; SPacketSoundEffect sPacketSoundEffect; SPacketPlayerListHeaderFooter sPacketPlayerListHeaderFooter; SPacketCollectItem sPacketCollectItem; SPacketEntityTeleport sPacketEntityTeleport; SPacketEntityProperties sPacketEntityProperties; SPacketEntityEffect sPacketEntityEffect; String ssoundName; SoundCategory cat; int xxPos, yyPos, zzPos;
/*     */     float vvolume, ppitch;
/*     */     int id, soundId;
/*     */     SoundCategory category;
/*     */     int posX, posY, posZ;
/*     */     float soundVolume, soundPitch;
/*     */     int collectedItemEntityId, entityId, collectedItems;
/* 127 */     Packet<?> pack = null;
/* 128 */     switch (packetId) {
/*     */       case 0:
/* 130 */         sPacketSpawnObject = new SPacketSpawnObject();
/* 131 */         sPacketSpawnObject.readPacketData(buffer);
/*     */         break;
/*     */       case 1:
/* 134 */         sPacketSpawnExperienceOrb = new SPacketSpawnExperienceOrb();
/* 135 */         sPacketSpawnExperienceOrb.readPacketData(buffer);
/*     */         break;
/*     */       case 2:
/* 138 */         sPacketSpawnGlobalEntity = new SPacketSpawnGlobalEntity();
/* 139 */         sPacketSpawnGlobalEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 3:
/* 142 */         sPacketSpawnMob = new SPacketSpawnMob();
/* 143 */         sPacketSpawnMob.readPacketData(buffer);
/* 144 */         sPacketSpawnMob.getDataManagerEntries().forEach(e -> {
/*     */               if (e.getKey().getId() >= 5) {
/*     */                 e.getKey().setId(e.getKey().getId() + 1);
/*     */               }
/*     */             });
/*     */         break;
/*     */       case 4:
/* 151 */         sPacketSpawnPainting = new SPacketSpawnPainting();
/* 152 */         sPacketSpawnPainting.readPacketData(buffer);
/*     */         break;
/*     */       case 5:
/* 155 */         sPacketSpawnPlayer = new SPacketSpawnPlayer();
/* 156 */         sPacketSpawnPlayer.readPacketData(buffer);
/* 157 */         sPacketSpawnPlayer.getDataManagerEntries().forEach(e -> {
/*     */               if (e.getKey().getId() >= 5) {
/*     */                 e.getKey().setId(e.getKey().getId() + 1);
/*     */               }
/*     */             });
/*     */         break;
/*     */       case 6:
/* 164 */         sPacketAnimation = new SPacketAnimation();
/* 165 */         sPacketAnimation.readPacketData(buffer);
/*     */         break;
/*     */       case 7:
/* 168 */         sPacketStatistics = new SPacketStatistics();
/* 169 */         sPacketStatistics.readPacketData(buffer);
/*     */         break;
/*     */       case 8:
/* 172 */         sPacketBlockBreakAnim = new SPacketBlockBreakAnim();
/* 173 */         sPacketBlockBreakAnim.readPacketData(buffer);
/*     */         break;
/*     */       case 9:
/* 176 */         sPacketUpdateTileEntity = new SPacketUpdateTileEntity();
/* 177 */         sPacketUpdateTileEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 10:
/* 180 */         sPacketBlockAction = new SPacketBlockAction();
/* 181 */         sPacketBlockAction.readPacketData(buffer);
/*     */         break;
/*     */       case 11:
/* 184 */         sPacketBlockChange = new SPacketBlockChange();
/* 185 */         sPacketBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       case 12:
/* 188 */         sPacketUpdateBossInfo = new SPacketUpdateBossInfo();
/* 189 */         sPacketUpdateBossInfo.readPacketData(buffer);
/*     */         break;
/*     */       case 13:
/* 192 */         sPacketServerDifficulty = new SPacketServerDifficulty();
/* 193 */         sPacketServerDifficulty.readPacketData(buffer);
/*     */         break;
/*     */       case 14:
/* 196 */         sPacketTabComplete = new SPacketTabComplete();
/* 197 */         sPacketTabComplete.readPacketData(buffer);
/*     */         break;
/*     */       case 15:
/* 200 */         sPacketChat = new SPacketChat();
/* 201 */         sPacketChat.readPacketData(buffer);
/*     */         break;
/*     */       case 16:
/* 204 */         sPacketMultiBlockChange = new SPacketMultiBlockChange();
/* 205 */         sPacketMultiBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       case 17:
/* 208 */         sPacketConfirmTransaction = new SPacketConfirmTransaction();
/* 209 */         sPacketConfirmTransaction.readPacketData(buffer);
/*     */         break;
/*     */       case 18:
/* 212 */         cPacketCloseWindow = new CPacketCloseWindow();
/* 213 */         cPacketCloseWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 19:
/* 216 */         sPacketOpenWindow = new SPacketOpenWindow();
/* 217 */         sPacketOpenWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 20:
/* 220 */         sPacketWindowItems = new SPacketWindowItems();
/* 221 */         sPacketWindowItems.readPacketData(buffer);
/*     */         break;
/*     */       case 21:
/* 224 */         sPacketWindowProperty = new SPacketWindowProperty();
/* 225 */         sPacketWindowProperty.readPacketData(buffer);
/*     */         break;
/*     */       case 22:
/* 228 */         sPacketSetSlot = new SPacketSetSlot();
/* 229 */         sPacketSetSlot.readPacketData(buffer);
/*     */         break;
/*     */       case 23:
/* 232 */         sPacketCooldown = new SPacketCooldown();
/* 233 */         sPacketCooldown.readPacketData(buffer);
/*     */         break;
/*     */       case 24:
/* 236 */         sPacketCustomPayload = new SPacketCustomPayload();
/* 237 */         sPacketCustomPayload.readPacketData(buffer);
/*     */         break;
/*     */       case 25:
/* 240 */         ssoundName = buffer.readStringFromBuffer(256);
/* 241 */         cat = (SoundCategory)buffer.readEnumValue(SoundCategory.class);
/* 242 */         xxPos = buffer.readInt();
/* 243 */         yyPos = buffer.readInt();
/* 244 */         zzPos = buffer.readInt();
/* 245 */         vvolume = buffer.readFloat() * 300.0F;
/* 246 */         ppitch = buffer.readUnsignedByte() / 63.5F;
/* 247 */         sPacketCustomSound = new SPacketCustomSound(ssoundName, cat, xxPos, yyPos, zzPos, vvolume, ppitch);
/*     */         break;
/*     */       case 26:
/* 250 */         sPacketDisconnect = new SPacketDisconnect();
/* 251 */         sPacketDisconnect.readPacketData(buffer);
/*     */         break;
/*     */       case 27:
/* 254 */         sPacketEntityStatus = new SPacketEntityStatus();
/* 255 */         sPacketEntityStatus.readPacketData(buffer);
/*     */         break;
/*     */       case 28:
/* 258 */         sPacketExplosion = new SPacketExplosion();
/* 259 */         sPacketExplosion.readPacketData(buffer);
/*     */         break;
/*     */       case 29:
/* 262 */         sPacketUnloadChunk = new SPacketUnloadChunk();
/* 263 */         sPacketUnloadChunk.readPacketData(buffer);
/*     */         break;
/*     */       case 30:
/* 266 */         sPacketChangeGameState = new SPacketChangeGameState();
/* 267 */         sPacketChangeGameState.readPacketData(buffer);
/*     */         break;
/*     */       case 31:
/* 270 */         id = buffer.readVarIntFromBuffer();
/* 271 */         sPacketKeepAlive = new SPacketKeepAlive(id);
/*     */         break;
/*     */       case 32:
/* 274 */         sPacketChunkData = new SPacketChunkData();
/* 275 */         sPacketChunkData.readPacketData(buffer);
/*     */         break;
/*     */       case 33:
/* 278 */         sPacketEffect = new SPacketEffect();
/* 279 */         sPacketEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 34:
/* 282 */         sPacketParticles = new SPacketParticles();
/* 283 */         sPacketParticles.readPacketData(buffer);
/*     */         break;
/*     */       case 35:
/* 286 */         sPacketJoinGame = new SPacketJoinGame();
/* 287 */         sPacketJoinGame.readPacketData(buffer);
/*     */         break;
/*     */       case 36:
/* 290 */         sPacketMaps = new SPacketMaps();
/* 291 */         sPacketMaps.readPacketData(buffer);
/*     */         break;
/*     */       case 37:
/* 294 */         s15PacketEntityRelMove = new SPacketEntity.S15PacketEntityRelMove();
/* 295 */         s15PacketEntityRelMove.readPacketData(buffer);
/*     */         break;
/*     */       case 38:
/* 298 */         s17PacketEntityLookMove = new SPacketEntity.S17PacketEntityLookMove();
/* 299 */         s17PacketEntityLookMove.readPacketData(buffer);
/*     */         break;
/*     */       case 39:
/* 302 */         s16PacketEntityLook = new SPacketEntity.S16PacketEntityLook();
/* 303 */         s16PacketEntityLook.readPacketData(buffer);
/*     */         break;
/*     */       case 40:
/* 306 */         sPacketEntity = new SPacketEntity();
/* 307 */         sPacketEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 41:
/* 310 */         sPacketMoveVehicle = new SPacketMoveVehicle();
/* 311 */         sPacketMoveVehicle.readPacketData(buffer);
/*     */         break;
/*     */       case 42:
/* 314 */         sPacketSignEditorOpen = new SPacketSignEditorOpen();
/* 315 */         sPacketSignEditorOpen.readPacketData(buffer);
/*     */         break;
/*     */       case 43:
/* 318 */         sPacketPlayerAbilities = new SPacketPlayerAbilities();
/* 319 */         sPacketPlayerAbilities.readPacketData(buffer);
/*     */         break;
/*     */       case 44:
/* 322 */         sPacketCombatEvent = new SPacketCombatEvent();
/* 323 */         sPacketCombatEvent.readPacketData(buffer);
/*     */         break;
/*     */       case 45:
/* 326 */         sPacketPlayerListItem = new SPacketPlayerListItem();
/* 327 */         sPacketPlayerListItem.readPacketData(buffer);
/*     */         break;
/*     */       case 46:
/* 330 */         sPacketPlayerPosLook = new SPacketPlayerPosLook();
/* 331 */         sPacketPlayerPosLook.readPacketData(buffer);
/*     */         break;
/*     */       case 47:
/* 334 */         sPacketUseBed = new SPacketUseBed();
/* 335 */         sPacketUseBed.readPacketData(buffer);
/*     */         break;
/*     */       case 48:
/* 338 */         sPacketDestroyEntities = new SPacketDestroyEntities();
/* 339 */         sPacketDestroyEntities.readPacketData(buffer);
/*     */         break;
/*     */       case 49:
/* 342 */         sPacketRemoveEntityEffect = new SPacketRemoveEntityEffect();
/* 343 */         sPacketRemoveEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 50:
/* 346 */         sPacketResourcePackSend = new SPacketResourcePackSend();
/* 347 */         sPacketResourcePackSend.readPacketData(buffer);
/*     */         break;
/*     */       case 51:
/* 350 */         sPacketRespawn = new SPacketRespawn();
/* 351 */         sPacketRespawn.readPacketData(buffer);
/*     */         break;
/*     */       case 52:
/* 354 */         sPacketEntityHeadLook = new SPacketEntityHeadLook();
/* 355 */         sPacketEntityHeadLook.readPacketData(buffer);
/*     */         break;
/*     */       case 53:
/* 358 */         sPacketWorldBorder = new SPacketWorldBorder();
/* 359 */         sPacketWorldBorder.readPacketData(buffer);
/*     */         break;
/*     */       case 54:
/* 362 */         sPacketCamera = new SPacketCamera();
/* 363 */         sPacketCamera.readPacketData(buffer);
/*     */         break;
/*     */       case 55:
/* 366 */         sPacketHeldItemChange = new SPacketHeldItemChange();
/* 367 */         sPacketHeldItemChange.readPacketData(buffer);
/*     */         break;
/*     */       case 56:
/* 370 */         sPacketDisplayObjective = new SPacketDisplayObjective();
/* 371 */         sPacketDisplayObjective.readPacketData(buffer);
/*     */         break;
/*     */       case 57:
/* 374 */         sPacketEntityMetadata = new SPacketEntityMetadata();
/* 375 */         sPacketEntityMetadata.readPacketData(buffer);
/* 376 */         sPacketEntityMetadata.getDataManagerEntries().forEach(e -> {
/*     */               if (e.getKey().getId() >= 5) {
/*     */                 e.getKey().setId(e.getKey().getId() + 1);
/*     */               }
/*     */             });
/*     */         break;
/*     */       case 58:
/* 383 */         sPacketEntityAttach = new SPacketEntityAttach();
/* 384 */         sPacketEntityAttach.readPacketData(buffer);
/*     */         break;
/*     */       case 59:
/* 387 */         sPacketEntityVelocity = new SPacketEntityVelocity();
/* 388 */         sPacketEntityVelocity.readPacketData(buffer);
/*     */         break;
/*     */       case 60:
/* 391 */         sPacketEntityEquipment = new SPacketEntityEquipment();
/* 392 */         sPacketEntityEquipment.readPacketData(buffer);
/*     */         break;
/*     */       case 61:
/* 395 */         sPacketSetExperience = new SPacketSetExperience();
/* 396 */         sPacketSetExperience.readPacketData(buffer);
/*     */         break;
/*     */       case 62:
/* 399 */         sPacketUpdateHealth = new SPacketUpdateHealth();
/* 400 */         sPacketUpdateHealth.readPacketData(buffer);
/*     */         break;
/*     */       case 63:
/* 403 */         sPacketScoreboardObjective = new SPacketScoreboardObjective();
/* 404 */         sPacketScoreboardObjective.readPacketData(buffer);
/*     */         break;
/*     */       case 64:
/* 407 */         sPacketSetPassengers = new SPacketSetPassengers();
/* 408 */         sPacketSetPassengers.readPacketData(buffer);
/*     */         break;
/*     */       case 65:
/* 411 */         sPacketTeams = new SPacketTeams();
/* 412 */         sPacketTeams.readPacketData(buffer);
/*     */         break;
/*     */       case 66:
/* 415 */         sPacketUpdateScore = new SPacketUpdateScore();
/* 416 */         sPacketUpdateScore.readPacketData(buffer);
/*     */         break;
/*     */       case 67:
/* 419 */         sPacketSpawnPosition = new SPacketSpawnPosition();
/* 420 */         sPacketSpawnPosition.readPacketData(buffer);
/*     */         break;
/*     */       case 68:
/* 423 */         sPacketTimeUpdate = new SPacketTimeUpdate();
/* 424 */         sPacketTimeUpdate.readPacketData(buffer);
/*     */         break;
/*     */       case 69:
/* 427 */         sPacketTitle = new SPacketTitle();
/* 428 */         sPacketTitle.readPacketData(buffer);
/*     */         break;
/*     */       case 70:
/* 431 */         sPacketSoundEffect = new SPacketSoundEffect();
/* 432 */         soundId = buffer.readVarIntFromBuffer();
/* 433 */         category = (SoundCategory)buffer.readEnumValue(SoundCategory.class);
/* 434 */         posX = buffer.readInt();
/* 435 */         posY = buffer.readInt();
/* 436 */         posZ = buffer.readInt();
/* 437 */         soundVolume = buffer.readFloat() * 300.0F;
/* 438 */         soundPitch = buffer.readUnsignedByte() / 63.5F;
/* 439 */         sPacketSoundEffect = new SPacketSoundEffect((SoundEvent)SoundRegistry.REGISTRY.getObjectById(soundId), category, posX, posY, posZ, 
/* 440 */             soundVolume, soundPitch);
/*     */         break;
/*     */       case 71:
/* 443 */         sPacketPlayerListHeaderFooter = new SPacketPlayerListHeaderFooter();
/* 444 */         sPacketPlayerListHeaderFooter.readPacketData(buffer);
/*     */         break;
/*     */       case 72:
/* 447 */         collectedItemEntityId = buffer.readVarIntFromBuffer();
/* 448 */         entityId = buffer.readVarIntFromBuffer();
/* 449 */         collectedItems = 1;
/* 450 */         sPacketCollectItem = new SPacketCollectItem(collectedItemEntityId, entityId, collectedItems);
/*     */         break;
/*     */       case 73:
/* 453 */         sPacketEntityTeleport = new SPacketEntityTeleport();
/* 454 */         sPacketEntityTeleport.readPacketData(buffer);
/*     */         break;
/*     */       case 74:
/* 457 */         sPacketEntityProperties = new SPacketEntityProperties();
/* 458 */         sPacketEntityProperties.readPacketData(buffer);
/*     */         break;
/*     */       case 75:
/* 461 */         sPacketEntityEffect = new SPacketEntityEffect();
/* 462 */         sPacketEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */     } 
/* 465 */     return (Packet<?>)sPacketEntityEffect; }
/*     */ 
/*     */   
/*     */   public static class SoundRegistry {
/* 469 */     public static final RegistryNamespaced<ResourceLocation, SoundEvent> REGISTRY = new RegistryNamespaced();
/*     */     private final ResourceLocation soundName;
/* 471 */     private static int soundEventId = 0;
/*     */     private static boolean registered;
/*     */     
/*     */     public SoundRegistry(ResourceLocation soundNameIn) {
/* 475 */       this.soundName = soundNameIn;
/*     */     }
/*     */     
/*     */     public ResourceLocation getSoundName() {
/* 479 */       return this.soundName;
/*     */     }
/*     */     
/*     */     public static void registerSounds() {
/* 483 */       if (registered)
/*     */         return; 
/* 485 */       registered = true;
/* 486 */       registerSound("ambient.cave");
/* 487 */       registerSound("block.anvil.break");
/* 488 */       registerSound("block.anvil.destroy");
/* 489 */       registerSound("block.anvil.fall");
/* 490 */       registerSound("block.anvil.hit");
/* 491 */       registerSound("block.anvil.land");
/* 492 */       registerSound("block.anvil.place");
/* 493 */       registerSound("block.anvil.step");
/* 494 */       registerSound("block.anvil.use");
/* 495 */       registerSound("block.brewing_stand.brew");
/* 496 */       registerSound("block.chest.close");
/* 497 */       registerSound("block.chest.locked");
/* 498 */       registerSound("block.chest.open");
/* 499 */       registerSound("block.chorus_flower.death");
/* 500 */       registerSound("block.chorus_flower.grow");
/* 501 */       registerSound("block.cloth.break");
/* 502 */       registerSound("block.cloth.fall");
/* 503 */       registerSound("block.cloth.hit");
/* 504 */       registerSound("block.cloth.place");
/* 505 */       registerSound("block.cloth.step");
/* 506 */       registerSound("block.comparator.click");
/* 507 */       registerSound("block.dispenser.dispense");
/* 508 */       registerSound("block.dispenser.fail");
/* 509 */       registerSound("block.dispenser.launch");
/* 510 */       registerSound("block.end_gateway.spawn");
/* 511 */       registerSound("block.enderchest.close");
/* 512 */       registerSound("block.enderchest.open");
/* 513 */       registerSound("block.fence_gate.close");
/* 514 */       registerSound("block.fence_gate.open");
/* 515 */       registerSound("block.fire.ambient");
/* 516 */       registerSound("block.fire.extinguish");
/* 517 */       registerSound("block.furnace.fire_crackle");
/* 518 */       registerSound("block.glass.break");
/* 519 */       registerSound("block.glass.fall");
/* 520 */       registerSound("block.glass.hit");
/* 521 */       registerSound("block.glass.place");
/* 522 */       registerSound("block.glass.step");
/* 523 */       registerSound("block.grass.break");
/* 524 */       registerSound("block.grass.fall");
/* 525 */       registerSound("block.grass.hit");
/* 526 */       registerSound("block.grass.place");
/* 527 */       registerSound("block.grass.step");
/* 528 */       registerSound("block.gravel.break");
/* 529 */       registerSound("block.gravel.fall");
/* 530 */       registerSound("block.gravel.hit");
/* 531 */       registerSound("block.gravel.place");
/* 532 */       registerSound("block.gravel.step");
/* 533 */       registerSound("block.iron_door.close");
/* 534 */       registerSound("block.iron_door.open");
/* 535 */       registerSound("block.iron_trapdoor.close");
/* 536 */       registerSound("block.iron_trapdoor.open");
/* 537 */       registerSound("block.ladder.break");
/* 538 */       registerSound("block.ladder.fall");
/* 539 */       registerSound("block.ladder.hit");
/* 540 */       registerSound("block.ladder.place");
/* 541 */       registerSound("block.ladder.step");
/* 542 */       registerSound("block.lava.ambient");
/* 543 */       registerSound("block.lava.extinguish");
/* 544 */       registerSound("block.lava.pop");
/* 545 */       registerSound("block.lever.click");
/* 546 */       registerSound("block.metal.break");
/* 547 */       registerSound("block.metal.fall");
/* 548 */       registerSound("block.metal.hit");
/* 549 */       registerSound("block.metal.place");
/* 550 */       registerSound("block.metal.step");
/* 551 */       registerSound("block.metal_pressureplate.click_off");
/* 552 */       registerSound("block.metal_pressureplate.click_on");
/* 553 */       registerSound("block.note.basedrum");
/* 554 */       registerSound("block.note.bass");
/* 555 */       registerSound("block.note.harp");
/* 556 */       registerSound("block.note.hat");
/* 557 */       registerSound("block.note.pling");
/* 558 */       registerSound("block.note.snare");
/* 559 */       registerSound("block.piston.contract");
/* 560 */       registerSound("block.piston.extend");
/* 561 */       registerSound("block.portal.ambient");
/* 562 */       registerSound("block.portal.travel");
/* 563 */       registerSound("block.portal.trigger");
/* 564 */       registerSound("block.redstone_torch.burnout");
/* 565 */       registerSound("block.sand.break");
/* 566 */       registerSound("block.sand.fall");
/* 567 */       registerSound("block.sand.hit");
/* 568 */       registerSound("block.sand.place");
/* 569 */       registerSound("block.sand.step");
/* 570 */       registerSound("block.slime.break");
/* 571 */       registerSound("block.slime.fall");
/* 572 */       registerSound("block.slime.hit");
/* 573 */       registerSound("block.slime.place");
/* 574 */       registerSound("block.slime.step");
/* 575 */       registerSound("block.snow.break");
/* 576 */       registerSound("block.snow.fall");
/* 577 */       registerSound("block.snow.hit");
/* 578 */       registerSound("block.snow.place");
/* 579 */       registerSound("block.snow.step");
/* 580 */       registerSound("block.stone.break");
/* 581 */       registerSound("block.stone.fall");
/* 582 */       registerSound("block.stone.hit");
/* 583 */       registerSound("block.stone.place");
/* 584 */       registerSound("block.stone.step");
/* 585 */       registerSound("block.stone_button.click_off");
/* 586 */       registerSound("block.stone_button.click_on");
/* 587 */       registerSound("block.stone_pressureplate.click_off");
/* 588 */       registerSound("block.stone_pressureplate.click_on");
/* 589 */       registerSound("block.tripwire.attach");
/* 590 */       registerSound("block.tripwire.click_off");
/* 591 */       registerSound("block.tripwire.click_on");
/* 592 */       registerSound("block.tripwire.detach");
/* 593 */       registerSound("block.water.ambient");
/* 594 */       registerSound("block.waterlily.place");
/* 595 */       registerSound("block.wood.break");
/* 596 */       registerSound("block.wood.fall");
/* 597 */       registerSound("block.wood.hit");
/* 598 */       registerSound("block.wood.place");
/* 599 */       registerSound("block.wood.step");
/* 600 */       registerSound("block.wood_button.click_off");
/* 601 */       registerSound("block.wood_button.click_on");
/* 602 */       registerSound("block.wood_pressureplate.click_off");
/* 603 */       registerSound("block.wood_pressureplate.click_on");
/* 604 */       registerSound("block.wooden_door.close");
/* 605 */       registerSound("block.wooden_door.open");
/* 606 */       registerSound("block.wooden_trapdoor.close");
/* 607 */       registerSound("block.wooden_trapdoor.open");
/* 608 */       registerSound("enchant.thorns.hit");
/* 609 */       registerSound("entity.armorstand.break");
/* 610 */       registerSound("entity.armorstand.fall");
/* 611 */       registerSound("entity.armorstand.hit");
/* 612 */       registerSound("entity.armorstand.place");
/* 613 */       registerSound("entity.arrow.hit");
/* 614 */       registerSound("entity.arrow.hit_player");
/* 615 */       registerSound("entity.arrow.shoot");
/* 616 */       registerSound("entity.bat.ambient");
/* 617 */       registerSound("entity.bat.death");
/* 618 */       registerSound("entity.bat.hurt");
/* 619 */       registerSound("entity.bat.loop");
/* 620 */       registerSound("entity.bat.takeoff");
/* 621 */       registerSound("entity.blaze.ambient");
/* 622 */       registerSound("entity.blaze.burn");
/* 623 */       registerSound("entity.blaze.death");
/* 624 */       registerSound("entity.blaze.hurt");
/* 625 */       registerSound("entity.blaze.shoot");
/* 626 */       registerSound("entity.bobber.splash");
/* 627 */       registerSound("entity.bobber.throw");
/* 628 */       registerSound("entity.cat.ambient");
/* 629 */       registerSound("entity.cat.death");
/* 630 */       registerSound("entity.cat.hiss");
/* 631 */       registerSound("entity.cat.hurt");
/* 632 */       registerSound("entity.cat.purr");
/* 633 */       registerSound("entity.cat.purreow");
/* 634 */       registerSound("entity.chicken.ambient");
/* 635 */       registerSound("entity.chicken.death");
/* 636 */       registerSound("entity.chicken.egg");
/* 637 */       registerSound("entity.chicken.hurt");
/* 638 */       registerSound("entity.chicken.step");
/* 639 */       registerSound("entity.cow.ambient");
/* 640 */       registerSound("entity.cow.death");
/* 641 */       registerSound("entity.cow.hurt");
/* 642 */       registerSound("entity.cow.milk");
/* 643 */       registerSound("entity.cow.step");
/* 644 */       registerSound("entity.creeper.death");
/* 645 */       registerSound("entity.creeper.hurt");
/* 646 */       registerSound("entity.creeper.primed");
/* 647 */       registerSound("entity.donkey.ambient");
/* 648 */       registerSound("entity.donkey.angry");
/* 649 */       registerSound("entity.donkey.chest");
/* 650 */       registerSound("entity.donkey.death");
/* 651 */       registerSound("entity.donkey.hurt");
/* 652 */       registerSound("entity.egg.throw");
/* 653 */       registerSound("entity.elder_guardian.ambient");
/* 654 */       registerSound("entity.elder_guardian.ambient_land");
/* 655 */       registerSound("entity.elder_guardian.curse");
/* 656 */       registerSound("entity.elder_guardian.death");
/* 657 */       registerSound("entity.elder_guardian.death_land");
/* 658 */       registerSound("entity.elder_guardian.hurt");
/* 659 */       registerSound("entity.elder_guardian.hurt_land");
/* 660 */       registerSound("entity.enderdragon.ambient");
/* 661 */       registerSound("entity.enderdragon.death");
/* 662 */       registerSound("entity.enderdragon.flap");
/* 663 */       registerSound("entity.enderdragon.growl");
/* 664 */       registerSound("entity.enderdragon.hurt");
/* 665 */       registerSound("entity.enderdragon.shoot");
/* 666 */       registerSound("entity.enderdragon_fireball.explode");
/* 667 */       registerSound("entity.endereye.launch");
/* 668 */       registerSound("entity.endermen.ambient");
/* 669 */       registerSound("entity.endermen.death");
/* 670 */       registerSound("entity.endermen.hurt");
/* 671 */       registerSound("entity.endermen.scream");
/* 672 */       registerSound("entity.endermen.stare");
/* 673 */       registerSound("entity.endermen.teleport");
/* 674 */       registerSound("entity.endermite.ambient");
/* 675 */       registerSound("entity.endermite.death");
/* 676 */       registerSound("entity.endermite.hurt");
/* 677 */       registerSound("entity.endermite.step");
/* 678 */       registerSound("entity.enderpearl.throw");
/* 679 */       registerSound("entity.experience_bottle.throw");
/* 680 */       registerSound("entity.experience_orb.pickup");
/* 681 */       registerSound("entity.experience_orb.touch");
/* 682 */       registerSound("entity.firework.blast");
/* 683 */       registerSound("entity.firework.blast_far");
/* 684 */       registerSound("entity.firework.large_blast");
/* 685 */       registerSound("entity.firework.large_blast_far");
/* 686 */       registerSound("entity.firework.launch");
/* 687 */       registerSound("entity.firework.shoot");
/* 688 */       registerSound("entity.firework.twinkle");
/* 689 */       registerSound("entity.firework.twinkle_far");
/* 690 */       registerSound("entity.generic.big_fall");
/* 691 */       registerSound("entity.generic.burn");
/* 692 */       registerSound("entity.generic.death");
/* 693 */       registerSound("entity.generic.drink");
/* 694 */       registerSound("entity.generic.eat");
/* 695 */       registerSound("entity.generic.explode");
/* 696 */       registerSound("entity.generic.extinguish_fire");
/* 697 */       registerSound("entity.generic.hurt");
/* 698 */       registerSound("entity.generic.small_fall");
/* 699 */       registerSound("entity.generic.splash");
/* 700 */       registerSound("entity.generic.swim");
/* 701 */       registerSound("entity.ghast.ambient");
/* 702 */       registerSound("entity.ghast.death");
/* 703 */       registerSound("entity.ghast.hurt");
/* 704 */       registerSound("entity.ghast.scream");
/* 705 */       registerSound("entity.ghast.shoot");
/* 706 */       registerSound("entity.ghast.warn");
/* 707 */       registerSound("entity.guardian.ambient");
/* 708 */       registerSound("entity.guardian.ambient_land");
/* 709 */       registerSound("entity.guardian.attack");
/* 710 */       registerSound("entity.guardian.death");
/* 711 */       registerSound("entity.guardian.death_land");
/* 712 */       registerSound("entity.guardian.flop");
/* 713 */       registerSound("entity.guardian.hurt");
/* 714 */       registerSound("entity.guardian.hurt_land");
/* 715 */       registerSound("entity.horse.ambient");
/* 716 */       registerSound("entity.horse.angry");
/* 717 */       registerSound("entity.horse.armor");
/* 718 */       registerSound("entity.horse.breathe");
/* 719 */       registerSound("entity.horse.death");
/* 720 */       registerSound("entity.horse.eat");
/* 721 */       registerSound("entity.horse.gallop");
/* 722 */       registerSound("entity.horse.hurt");
/* 723 */       registerSound("entity.horse.jump");
/* 724 */       registerSound("entity.horse.land");
/* 725 */       registerSound("entity.horse.saddle");
/* 726 */       registerSound("entity.horse.step");
/* 727 */       registerSound("entity.horse.step_wood");
/* 728 */       registerSound("entity.hostile.big_fall");
/* 729 */       registerSound("entity.hostile.death");
/* 730 */       registerSound("entity.hostile.hurt");
/* 731 */       registerSound("entity.hostile.small_fall");
/* 732 */       registerSound("entity.hostile.splash");
/* 733 */       registerSound("entity.hostile.swim");
/* 734 */       registerSound("entity.irongolem.attack");
/* 735 */       registerSound("entity.irongolem.death");
/* 736 */       registerSound("entity.irongolem.hurt");
/* 737 */       registerSound("entity.irongolem.step");
/* 738 */       registerSound("entity.item.break");
/* 739 */       registerSound("entity.item.pickup");
/* 740 */       registerSound("entity.itemframe.add_item");
/* 741 */       registerSound("entity.itemframe.break");
/* 742 */       registerSound("entity.itemframe.place");
/* 743 */       registerSound("entity.itemframe.remove_item");
/* 744 */       registerSound("entity.itemframe.rotate_item");
/* 745 */       registerSound("entity.leashknot.break");
/* 746 */       registerSound("entity.leashknot.place");
/* 747 */       registerSound("entity.lightning.impact");
/* 748 */       registerSound("entity.lightning.thunder");
/* 749 */       registerSound("entity.lingeringpotion.throw");
/* 750 */       registerSound("entity.magmacube.death");
/* 751 */       registerSound("entity.magmacube.hurt");
/* 752 */       registerSound("entity.magmacube.jump");
/* 753 */       registerSound("entity.magmacube.squish");
/* 754 */       registerSound("entity.minecart.inside");
/* 755 */       registerSound("entity.minecart.riding");
/* 756 */       registerSound("entity.mooshroom.shear");
/* 757 */       registerSound("entity.mule.ambient");
/* 758 */       registerSound("entity.mule.death");
/* 759 */       registerSound("entity.mule.hurt");
/* 760 */       registerSound("entity.painting.break");
/* 761 */       registerSound("entity.painting.place");
/* 762 */       registerSound("entity.pig.ambient");
/* 763 */       registerSound("entity.pig.death");
/* 764 */       registerSound("entity.pig.hurt");
/* 765 */       registerSound("entity.pig.saddle");
/* 766 */       registerSound("entity.pig.step");
/* 767 */       registerSound("entity.player.attack.crit");
/* 768 */       registerSound("entity.player.attack.knockback");
/* 769 */       registerSound("entity.player.attack.nodamage");
/* 770 */       registerSound("entity.player.attack.strong");
/* 771 */       registerSound("entity.player.attack.sweep");
/* 772 */       registerSound("entity.player.attack.weak");
/* 773 */       registerSound("entity.player.big_fall");
/* 774 */       registerSound("entity.player.breath");
/* 775 */       registerSound("entity.player.burp");
/* 776 */       registerSound("entity.player.death");
/* 777 */       registerSound("entity.player.hurt");
/* 778 */       registerSound("entity.player.levelup");
/* 779 */       registerSound("entity.player.small_fall");
/* 780 */       registerSound("entity.player.splash");
/* 781 */       registerSound("entity.player.swim");
/* 782 */       registerSound("entity.rabbit.ambient");
/* 783 */       registerSound("entity.rabbit.attack");
/* 784 */       registerSound("entity.rabbit.death");
/* 785 */       registerSound("entity.rabbit.hurt");
/* 786 */       registerSound("entity.rabbit.jump");
/* 787 */       registerSound("entity.sheep.ambient");
/* 788 */       registerSound("entity.sheep.death");
/* 789 */       registerSound("entity.sheep.hurt");
/* 790 */       registerSound("entity.sheep.shear");
/* 791 */       registerSound("entity.sheep.step");
/* 792 */       registerSound("entity.shulker.ambient");
/* 793 */       registerSound("entity.shulker.close");
/* 794 */       registerSound("entity.shulker.death");
/* 795 */       registerSound("entity.shulker.hurt");
/* 796 */       registerSound("entity.shulker.hurt_closed");
/* 797 */       registerSound("entity.shulker.open");
/* 798 */       registerSound("entity.shulker.shoot");
/* 799 */       registerSound("entity.shulker.teleport");
/* 800 */       registerSound("entity.shulker_bullet.hit");
/* 801 */       registerSound("entity.shulker_bullet.hurt");
/* 802 */       registerSound("entity.silverfish.ambient");
/* 803 */       registerSound("entity.silverfish.death");
/* 804 */       registerSound("entity.silverfish.hurt");
/* 805 */       registerSound("entity.silverfish.step");
/* 806 */       registerSound("entity.skeleton.ambient");
/* 807 */       registerSound("entity.skeleton.death");
/* 808 */       registerSound("entity.skeleton.hurt");
/* 809 */       registerSound("entity.skeleton.shoot");
/* 810 */       registerSound("entity.skeleton.step");
/* 811 */       registerSound("entity.skeleton_horse.ambient");
/* 812 */       registerSound("entity.skeleton_horse.death");
/* 813 */       registerSound("entity.skeleton_horse.hurt");
/* 814 */       registerSound("entity.slime.attack");
/* 815 */       registerSound("entity.slime.death");
/* 816 */       registerSound("entity.slime.hurt");
/* 817 */       registerSound("entity.slime.jump");
/* 818 */       registerSound("entity.slime.squish");
/* 819 */       registerSound("entity.small_magmacube.death");
/* 820 */       registerSound("entity.small_magmacube.hurt");
/* 821 */       registerSound("entity.small_magmacube.squish");
/* 822 */       registerSound("entity.small_slime.death");
/* 823 */       registerSound("entity.small_slime.hurt");
/* 824 */       registerSound("entity.small_slime.jump");
/* 825 */       registerSound("entity.small_slime.squish");
/* 826 */       registerSound("entity.snowball.throw");
/* 827 */       registerSound("entity.snowman.ambient");
/* 828 */       registerSound("entity.snowman.death");
/* 829 */       registerSound("entity.snowman.hurt");
/* 830 */       registerSound("entity.snowman.shoot");
/* 831 */       registerSound("entity.spider.ambient");
/* 832 */       registerSound("entity.spider.death");
/* 833 */       registerSound("entity.spider.hurt");
/* 834 */       registerSound("entity.spider.step");
/* 835 */       registerSound("entity.splash_potion.break");
/* 836 */       registerSound("entity.splash_potion.throw");
/* 837 */       registerSound("entity.squid.ambient");
/* 838 */       registerSound("entity.squid.death");
/* 839 */       registerSound("entity.squid.hurt");
/* 840 */       registerSound("entity.tnt.primed");
/* 841 */       registerSound("entity.villager.ambient");
/* 842 */       registerSound("entity.villager.death");
/* 843 */       registerSound("entity.villager.hurt");
/* 844 */       registerSound("entity.villager.no");
/* 845 */       registerSound("entity.villager.trading");
/* 846 */       registerSound("entity.villager.yes");
/* 847 */       registerSound("entity.witch.ambient");
/* 848 */       registerSound("entity.witch.death");
/* 849 */       registerSound("entity.witch.drink");
/* 850 */       registerSound("entity.witch.hurt");
/* 851 */       registerSound("entity.witch.throw");
/* 852 */       registerSound("entity.wither.ambient");
/* 853 */       registerSound("entity.wither.break_block");
/* 854 */       registerSound("entity.wither.death");
/* 855 */       registerSound("entity.wither.hurt");
/* 856 */       registerSound("entity.wither.shoot");
/* 857 */       registerSound("entity.wither.spawn");
/* 858 */       registerSound("entity.wolf.ambient");
/* 859 */       registerSound("entity.wolf.death");
/* 860 */       registerSound("entity.wolf.growl");
/* 861 */       registerSound("entity.wolf.howl");
/* 862 */       registerSound("entity.wolf.hurt");
/* 863 */       registerSound("entity.wolf.pant");
/* 864 */       registerSound("entity.wolf.shake");
/* 865 */       registerSound("entity.wolf.step");
/* 866 */       registerSound("entity.wolf.whine");
/* 867 */       registerSound("entity.zombie.ambient");
/* 868 */       registerSound("entity.zombie.attack_door_wood");
/* 869 */       registerSound("entity.zombie.attack_iron_door");
/* 870 */       registerSound("entity.zombie.break_door_wood");
/* 871 */       registerSound("entity.zombie.death");
/* 872 */       registerSound("entity.zombie.hurt");
/* 873 */       registerSound("entity.zombie.infect");
/* 874 */       registerSound("entity.zombie.step");
/* 875 */       registerSound("entity.zombie_horse.ambient");
/* 876 */       registerSound("entity.zombie_horse.death");
/* 877 */       registerSound("entity.zombie_horse.hurt");
/* 878 */       registerSound("entity.zombie_pig.ambient");
/* 879 */       registerSound("entity.zombie_pig.angry");
/* 880 */       registerSound("entity.zombie_pig.death");
/* 881 */       registerSound("entity.zombie_pig.hurt");
/* 882 */       registerSound("entity.zombie_villager.ambient");
/* 883 */       registerSound("entity.zombie_villager.converted");
/* 884 */       registerSound("entity.zombie_villager.cure");
/* 885 */       registerSound("entity.zombie_villager.death");
/* 886 */       registerSound("entity.zombie_villager.hurt");
/* 887 */       registerSound("entity.zombie_villager.step");
/* 888 */       registerSound("item.armor.equip_chain");
/* 889 */       registerSound("item.armor.equip_diamond");
/* 890 */       registerSound("item.armor.equip_generic");
/* 891 */       registerSound("item.armor.equip_gold");
/* 892 */       registerSound("item.armor.equip_iron");
/* 893 */       registerSound("item.armor.equip_leather");
/* 894 */       registerSound("item.bottle.fill");
/* 895 */       registerSound("item.bottle.fill_dragonbreath");
/* 896 */       registerSound("item.bucket.empty");
/* 897 */       registerSound("item.bucket.empty_lava");
/* 898 */       registerSound("item.bucket.fill");
/* 899 */       registerSound("item.bucket.fill_lava");
/* 900 */       registerSound("item.chorus_fruit.teleport");
/* 901 */       registerSound("item.elytra.flying");
/* 902 */       registerSound("item.firecharge.use");
/* 903 */       registerSound("item.flintandsteel.use");
/* 904 */       registerSound("item.hoe.till");
/* 905 */       registerSound("item.shield.block");
/* 906 */       registerSound("item.shield.break");
/* 907 */       registerSound("item.shovel.flatten");
/* 908 */       registerSound("music.creative");
/* 909 */       registerSound("music.credits");
/* 910 */       registerSound("music.dragon");
/* 911 */       registerSound("music.end");
/* 912 */       registerSound("music.game");
/* 913 */       registerSound("music.menu");
/* 914 */       registerSound("music.nether");
/* 915 */       registerSound("record.11");
/* 916 */       registerSound("record.13");
/* 917 */       registerSound("record.blocks");
/* 918 */       registerSound("record.cat");
/* 919 */       registerSound("record.chirp");
/* 920 */       registerSound("record.far");
/* 921 */       registerSound("record.mall");
/* 922 */       registerSound("record.mellohi");
/* 923 */       registerSound("record.stal");
/* 924 */       registerSound("record.strad");
/* 925 */       registerSound("record.wait");
/* 926 */       registerSound("record.ward");
/* 927 */       registerSound("ui.button.click");
/* 928 */       registerSound("weather.rain");
/* 929 */       registerSound("weather.rain.above");
/*     */     }
/*     */     
/*     */     private static void registerSound(String soundNameIn) {
/* 933 */       ResourceLocation resourcelocation = new ResourceLocation(soundNameIn);
/* 934 */       REGISTRY.register(soundEventId++, resourcelocation, new SoundEvent(resourcelocation));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_9\PacketWrapper110.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
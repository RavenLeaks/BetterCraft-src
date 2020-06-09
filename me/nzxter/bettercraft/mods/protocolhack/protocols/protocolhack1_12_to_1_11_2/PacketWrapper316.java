/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_11_2;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.server.SPacketAnimation;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.network.play.server.SPacketCloseWindow;
/*     */ import net.minecraft.network.play.server.SPacketEntity;
/*     */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketUseBed;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ 
/*     */ public class PacketWrapper316 extends PacketWrapper {
/*     */   public PacketWrapper316() {
/*  18 */     super(ProtocolHack.PROTOCOL_316);
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
/*  48 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketCloseWindow) {
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
/* 108 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock) {
/* 109 */       buffer.writeVarIntToBuffer(28);
/* 110 */       packet.writePacketData(buffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 118 */     else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem) {
/* 119 */       buffer.writeVarIntToBuffer(29);
/* 120 */       packet.writePacketData(buffer);
/*     */     }  } public Packet<?> readPacket(int packetId, PacketBuffer buffer) throws Exception { SPacketSpawnObject sPacketSpawnObject; SPacketSpawnExperienceOrb sPacketSpawnExperienceOrb; SPacketSpawnGlobalEntity sPacketSpawnGlobalEntity; SPacketSpawnMob sPacketSpawnMob; SPacketSpawnPainting sPacketSpawnPainting; SPacketSpawnPlayer sPacketSpawnPlayer; SPacketAnimation sPacketAnimation; SPacketStatistics sPacketStatistics; SPacketBlockBreakAnim sPacketBlockBreakAnim; SPacketUpdateTileEntity sPacketUpdateTileEntity; SPacketBlockAction sPacketBlockAction; SPacketBlockChange sPacketBlockChange; SPacketUpdateBossInfo sPacketUpdateBossInfo; SPacketServerDifficulty sPacketServerDifficulty; SPacketTabComplete sPacketTabComplete; SPacketChat sPacketChat; SPacketMultiBlockChange sPacketMultiBlockChange; SPacketConfirmTransaction sPacketConfirmTransaction; SPacketCloseWindow sPacketCloseWindow; SPacketOpenWindow sPacketOpenWindow; SPacketWindowItems sPacketWindowItems; SPacketWindowProperty sPacketWindowProperty; SPacketSetSlot sPacketSetSlot; SPacketCooldown sPacketCooldown; SPacketCustomPayload sPacketCustomPayload; SPacketCustomSound sPacketCustomSound; SPacketDisconnect sPacketDisconnect; SPacketExplosion sPacketExplosion; SPacketUnloadChunk sPacketUnloadChunk; SPacketChangeGameState sPacketChangeGameState; SPacketKeepAlive sPacketKeepAlive; SPacketChunkData sPacketChunkData; SPacketEffect sPacketEffect; SPacketParticles sPacketParticles; SPacketJoinGame sPacketJoinGame; SPacketMaps sPacketMaps; SPacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove; SPacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove; SPacketEntity.S16PacketEntityLook s16PacketEntityLook; SPacketEntity sPacketEntity; SPacketMoveVehicle sPacketMoveVehicle; SPacketSignEditorOpen sPacketSignEditorOpen; SPacketPlayerAbilities sPacketPlayerAbilities; SPacketCombatEvent sPacketCombatEvent; SPacketPlayerListItem sPacketPlayerListItem; SPacketPlayerPosLook sPacketPlayerPosLook; SPacketUseBed sPacketUseBed; SPacketDestroyEntities sPacketDestroyEntities; SPacketResourcePackSend sPacketResourcePackSend; SPacketRespawn sPacketRespawn; SPacketEntityHeadLook sPacketEntityHeadLook; SPacketWorldBorder sPacketWorldBorder; SPacketCamera sPacketCamera; SPacketHeldItemChange sPacketHeldItemChange; SPacketDisplayObjective sPacketDisplayObjective; SPacketEntityMetadata sPacketEntityMetadata; SPacketEntityAttach sPacketEntityAttach; SPacketEntityVelocity sPacketEntityVelocity; SPacketEntityEquipment sPacketEntityEquipment; SPacketSetExperience sPacketSetExperience; SPacketUpdateHealth sPacketUpdateHealth; SPacketScoreboardObjective sPacketScoreboardObjective; SPacketSetPassengers sPacketSetPassengers; SPacketTeams sPacketTeams; SPacketUpdateScore sPacketUpdateScore; SPacketSpawnPosition sPacketSpawnPosition; SPacketTimeUpdate sPacketTimeUpdate; SPacketTitle sPacketTitle; SPacketSoundEffect sPacketSoundEffect; SPacketPlayerListHeaderFooter sPacketPlayerListHeaderFooter; SPacketCollectItem sPacketCollectItem; SPacketEntityTeleport sPacketEntityTeleport; SPacketEntityProperties sPacketEntityProperties; SPacketEntityEffect sPacketEntityEffect; String ssoundName; SoundCategory cat; int xxPos, yyPos, zzPos;
/*     */     float vvolume, ppitch;
/*     */     int id, soundId;
/*     */     SoundCategory category;
/*     */     int posX, posY, posZ;
/*     */     float soundVolume, soundPitch;
/*     */     int collectedItemEntityId, entityId, collectedItems;
/* 128 */     Packet<?> pack = null;
/* 129 */     switch (packetId) {
/*     */       case 0:
/* 131 */         sPacketSpawnObject = new SPacketSpawnObject();
/* 132 */         sPacketSpawnObject.readPacketData(buffer);
/*     */         break;
/*     */       case 1:
/* 135 */         sPacketSpawnExperienceOrb = new SPacketSpawnExperienceOrb();
/* 136 */         sPacketSpawnExperienceOrb.readPacketData(buffer);
/*     */         break;
/*     */       case 2:
/* 139 */         sPacketSpawnGlobalEntity = new SPacketSpawnGlobalEntity();
/* 140 */         sPacketSpawnGlobalEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 3:
/* 143 */         sPacketSpawnMob = new SPacketSpawnMob();
/* 144 */         sPacketSpawnMob.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 152 */         sPacketSpawnPainting = new SPacketSpawnPainting();
/* 153 */         sPacketSpawnPainting.readPacketData(buffer);
/*     */         break;
/*     */       case 5:
/* 156 */         sPacketSpawnPlayer = new SPacketSpawnPlayer();
/* 157 */         sPacketSpawnPlayer.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 165 */         sPacketAnimation = new SPacketAnimation();
/* 166 */         sPacketAnimation.readPacketData(buffer);
/*     */         break;
/*     */       case 7:
/* 169 */         sPacketStatistics = new SPacketStatistics();
/* 170 */         sPacketStatistics.readPacketData(buffer);
/*     */         break;
/*     */       case 8:
/* 173 */         sPacketBlockBreakAnim = new SPacketBlockBreakAnim();
/* 174 */         sPacketBlockBreakAnim.readPacketData(buffer);
/*     */         break;
/*     */       case 9:
/* 177 */         sPacketUpdateTileEntity = new SPacketUpdateTileEntity();
/* 178 */         sPacketUpdateTileEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 10:
/* 181 */         sPacketBlockAction = new SPacketBlockAction();
/* 182 */         sPacketBlockAction.readPacketData(buffer);
/*     */         break;
/*     */       case 11:
/* 185 */         sPacketBlockChange = new SPacketBlockChange();
/* 186 */         sPacketBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       case 12:
/* 189 */         sPacketUpdateBossInfo = new SPacketUpdateBossInfo();
/* 190 */         sPacketUpdateBossInfo.readPacketData(buffer);
/*     */         break;
/*     */       case 13:
/* 193 */         sPacketServerDifficulty = new SPacketServerDifficulty();
/* 194 */         sPacketServerDifficulty.readPacketData(buffer);
/*     */         break;
/*     */       case 14:
/* 197 */         sPacketTabComplete = new SPacketTabComplete();
/* 198 */         sPacketTabComplete.readPacketData(buffer);
/*     */         break;
/*     */       case 15:
/* 201 */         sPacketChat = new SPacketChat();
/* 202 */         sPacketChat.readPacketData(buffer);
/*     */         break;
/*     */       case 16:
/* 205 */         sPacketMultiBlockChange = new SPacketMultiBlockChange();
/* 206 */         sPacketMultiBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       case 17:
/* 209 */         sPacketConfirmTransaction = new SPacketConfirmTransaction();
/* 210 */         sPacketConfirmTransaction.readPacketData(buffer);
/*     */         break;
/*     */       case 18:
/* 213 */         sPacketCloseWindow = new SPacketCloseWindow();
/* 214 */         sPacketCloseWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 19:
/* 217 */         sPacketOpenWindow = new SPacketOpenWindow();
/* 218 */         sPacketOpenWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 20:
/* 221 */         sPacketWindowItems = new SPacketWindowItems();
/* 222 */         sPacketWindowItems.readPacketData(buffer);
/*     */         break;
/*     */       case 21:
/* 225 */         sPacketWindowProperty = new SPacketWindowProperty();
/* 226 */         sPacketWindowProperty.readPacketData(buffer);
/*     */         break;
/*     */       case 22:
/* 229 */         sPacketSetSlot = new SPacketSetSlot();
/* 230 */         sPacketSetSlot.readPacketData(buffer);
/*     */         break;
/*     */       case 23:
/* 233 */         sPacketCooldown = new SPacketCooldown();
/* 234 */         sPacketCooldown.readPacketData(buffer);
/*     */         break;
/*     */       case 24:
/* 237 */         sPacketCustomPayload = new SPacketCustomPayload();
/* 238 */         sPacketCustomPayload.readPacketData(buffer);
/*     */         break;
/*     */       case 25:
/* 241 */         ssoundName = buffer.readStringFromBuffer(256);
/* 242 */         cat = (SoundCategory)buffer.readEnumValue(SoundCategory.class);
/* 243 */         xxPos = buffer.readInt();
/* 244 */         yyPos = buffer.readInt();
/* 245 */         zzPos = buffer.readInt();
/* 246 */         vvolume = buffer.readFloat() * 300.0F;
/* 247 */         ppitch = buffer.readUnsignedByte() / 63.5F;
/* 248 */         sPacketCustomSound = new SPacketCustomSound(ssoundName, cat, xxPos, yyPos, zzPos, vvolume, ppitch);
/*     */         break;
/*     */       case 26:
/* 251 */         sPacketDisconnect = new SPacketDisconnect();
/* 252 */         sPacketDisconnect.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 28:
/* 259 */         sPacketExplosion = new SPacketExplosion();
/* 260 */         sPacketExplosion.readPacketData(buffer);
/*     */         break;
/*     */       case 29:
/* 263 */         sPacketUnloadChunk = new SPacketUnloadChunk();
/* 264 */         sPacketUnloadChunk.readPacketData(buffer);
/*     */         break;
/*     */       case 30:
/* 267 */         sPacketChangeGameState = new SPacketChangeGameState();
/* 268 */         sPacketChangeGameState.readPacketData(buffer);
/*     */         break;
/*     */       case 31:
/* 271 */         id = buffer.readVarIntFromBuffer();
/* 272 */         sPacketKeepAlive = new SPacketKeepAlive(id);
/*     */         break;
/*     */       case 32:
/* 275 */         sPacketChunkData = new SPacketChunkData();
/* 276 */         sPacketChunkData.readPacketData(buffer);
/*     */         break;
/*     */       case 33:
/* 279 */         sPacketEffect = new SPacketEffect();
/* 280 */         sPacketEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 34:
/* 283 */         sPacketParticles = new SPacketParticles();
/* 284 */         sPacketParticles.readPacketData(buffer);
/*     */         break;
/*     */       case 35:
/* 287 */         sPacketJoinGame = new SPacketJoinGame();
/* 288 */         sPacketJoinGame.readPacketData(buffer);
/*     */         break;
/*     */       case 36:
/* 291 */         sPacketMaps = new SPacketMaps();
/* 292 */         sPacketMaps.readPacketData(buffer);
/*     */         break;
/*     */       case 37:
/* 295 */         s15PacketEntityRelMove = new SPacketEntity.S15PacketEntityRelMove();
/* 296 */         s15PacketEntityRelMove.readPacketData(buffer);
/*     */         break;
/*     */       case 38:
/* 299 */         s17PacketEntityLookMove = new SPacketEntity.S17PacketEntityLookMove();
/* 300 */         s17PacketEntityLookMove.readPacketData(buffer);
/*     */         break;
/*     */       case 39:
/* 303 */         s16PacketEntityLook = new SPacketEntity.S16PacketEntityLook();
/* 304 */         s16PacketEntityLook.readPacketData(buffer);
/*     */         break;
/*     */       case 40:
/* 307 */         sPacketEntity = new SPacketEntity();
/* 308 */         sPacketEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 41:
/* 311 */         sPacketMoveVehicle = new SPacketMoveVehicle();
/* 312 */         sPacketMoveVehicle.readPacketData(buffer);
/*     */         break;
/*     */       case 42:
/* 315 */         sPacketSignEditorOpen = new SPacketSignEditorOpen();
/* 316 */         sPacketSignEditorOpen.readPacketData(buffer);
/*     */         break;
/*     */       case 43:
/* 319 */         sPacketPlayerAbilities = new SPacketPlayerAbilities();
/* 320 */         sPacketPlayerAbilities.readPacketData(buffer);
/*     */         break;
/*     */       case 44:
/* 323 */         sPacketCombatEvent = new SPacketCombatEvent();
/* 324 */         sPacketCombatEvent.readPacketData(buffer);
/*     */         break;
/*     */       case 45:
/* 327 */         sPacketPlayerListItem = new SPacketPlayerListItem();
/* 328 */         sPacketPlayerListItem.readPacketData(buffer);
/*     */         break;
/*     */       case 46:
/* 331 */         sPacketPlayerPosLook = new SPacketPlayerPosLook();
/* 332 */         sPacketPlayerPosLook.readPacketData(buffer);
/*     */         break;
/*     */       case 47:
/* 335 */         sPacketUseBed = new SPacketUseBed();
/* 336 */         sPacketUseBed.readPacketData(buffer);
/*     */         break;
/*     */       case 48:
/* 339 */         sPacketDestroyEntities = new SPacketDestroyEntities();
/* 340 */         sPacketDestroyEntities.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 50:
/* 347 */         sPacketResourcePackSend = new SPacketResourcePackSend();
/* 348 */         sPacketResourcePackSend.readPacketData(buffer);
/*     */         break;
/*     */       case 51:
/* 351 */         sPacketRespawn = new SPacketRespawn();
/* 352 */         sPacketRespawn.readPacketData(buffer);
/*     */         break;
/*     */       case 52:
/* 355 */         sPacketEntityHeadLook = new SPacketEntityHeadLook();
/* 356 */         sPacketEntityHeadLook.readPacketData(buffer);
/*     */         break;
/*     */       case 53:
/* 359 */         sPacketWorldBorder = new SPacketWorldBorder();
/* 360 */         sPacketWorldBorder.readPacketData(buffer);
/*     */         break;
/*     */       case 54:
/* 363 */         sPacketCamera = new SPacketCamera();
/* 364 */         sPacketCamera.readPacketData(buffer);
/*     */         break;
/*     */       case 55:
/* 367 */         sPacketHeldItemChange = new SPacketHeldItemChange();
/* 368 */         sPacketHeldItemChange.readPacketData(buffer);
/*     */         break;
/*     */       case 56:
/* 371 */         sPacketDisplayObjective = new SPacketDisplayObjective();
/* 372 */         sPacketDisplayObjective.readPacketData(buffer);
/*     */         break;
/*     */       case 57:
/* 375 */         sPacketEntityMetadata = new SPacketEntityMetadata();
/* 376 */         sPacketEntityMetadata.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 58:
/* 384 */         sPacketEntityAttach = new SPacketEntityAttach();
/* 385 */         sPacketEntityAttach.readPacketData(buffer);
/*     */         break;
/*     */       case 59:
/* 388 */         sPacketEntityVelocity = new SPacketEntityVelocity();
/* 389 */         sPacketEntityVelocity.readPacketData(buffer);
/*     */         break;
/*     */       case 60:
/* 392 */         sPacketEntityEquipment = new SPacketEntityEquipment();
/* 393 */         sPacketEntityEquipment.readPacketData(buffer);
/*     */         break;
/*     */       case 61:
/* 396 */         sPacketSetExperience = new SPacketSetExperience();
/* 397 */         sPacketSetExperience.readPacketData(buffer);
/*     */         break;
/*     */       case 62:
/* 400 */         sPacketUpdateHealth = new SPacketUpdateHealth();
/* 401 */         sPacketUpdateHealth.readPacketData(buffer);
/*     */         break;
/*     */       case 63:
/* 404 */         sPacketScoreboardObjective = new SPacketScoreboardObjective();
/* 405 */         sPacketScoreboardObjective.readPacketData(buffer);
/*     */         break;
/*     */       case 64:
/* 408 */         sPacketSetPassengers = new SPacketSetPassengers();
/* 409 */         sPacketSetPassengers.readPacketData(buffer);
/*     */         break;
/*     */       case 65:
/* 412 */         sPacketTeams = new SPacketTeams();
/* 413 */         sPacketTeams.readPacketData(buffer);
/*     */         break;
/*     */       case 66:
/* 416 */         sPacketUpdateScore = new SPacketUpdateScore();
/* 417 */         sPacketUpdateScore.readPacketData(buffer);
/*     */         break;
/*     */       case 67:
/* 420 */         sPacketSpawnPosition = new SPacketSpawnPosition();
/* 421 */         sPacketSpawnPosition.readPacketData(buffer);
/*     */         break;
/*     */       case 68:
/* 424 */         sPacketTimeUpdate = new SPacketTimeUpdate();
/* 425 */         sPacketTimeUpdate.readPacketData(buffer);
/*     */         break;
/*     */       case 69:
/* 428 */         sPacketTitle = new SPacketTitle();
/* 429 */         sPacketTitle.readPacketData(buffer);
/*     */         break;
/*     */       case 70:
/* 432 */         sPacketSoundEffect = new SPacketSoundEffect();
/* 433 */         soundId = buffer.readVarIntFromBuffer();
/* 434 */         category = (SoundCategory)buffer.readEnumValue(SoundCategory.class);
/* 435 */         posX = buffer.readInt();
/* 436 */         posY = buffer.readInt();
/* 437 */         posZ = buffer.readInt();
/* 438 */         soundVolume = buffer.readFloat() * 300.0F;
/* 439 */         soundPitch = buffer.readUnsignedByte() / 63.5F;
/* 440 */         sPacketSoundEffect = new SPacketSoundEffect((SoundEvent)SoundRegistry.REGISTRY.getObjectById(soundId), category, posX, posY, posZ, 
/* 441 */             soundVolume, soundPitch);
/*     */         break;
/*     */       case 71:
/* 444 */         sPacketPlayerListHeaderFooter = new SPacketPlayerListHeaderFooter();
/* 445 */         sPacketPlayerListHeaderFooter.readPacketData(buffer);
/*     */         break;
/*     */       case 72:
/* 448 */         collectedItemEntityId = buffer.readVarIntFromBuffer();
/* 449 */         entityId = buffer.readVarIntFromBuffer();
/* 450 */         collectedItems = 1;
/* 451 */         sPacketCollectItem = new SPacketCollectItem(collectedItemEntityId, entityId, collectedItems);
/*     */         break;
/*     */       case 73:
/* 454 */         sPacketEntityTeleport = new SPacketEntityTeleport();
/* 455 */         sPacketEntityTeleport.readPacketData(buffer);
/*     */         break;
/*     */       case 74:
/* 458 */         sPacketEntityProperties = new SPacketEntityProperties();
/* 459 */         sPacketEntityProperties.readPacketData(buffer);
/*     */         break;
/*     */       case 75:
/* 462 */         sPacketEntityEffect = new SPacketEntityEffect();
/* 463 */         sPacketEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */     } 
/* 466 */     return (Packet<?>)sPacketEntityEffect; }
/*     */ 
/*     */   
/*     */   public static class SoundRegistry {
/* 470 */     public static final RegistryNamespaced<ResourceLocation, SoundEvent> REGISTRY = new RegistryNamespaced();
/*     */     private final ResourceLocation soundName;
/* 472 */     private static int soundEventId = 0;
/*     */     private static boolean registered;
/*     */     
/*     */     public SoundRegistry(ResourceLocation soundNameIn) {
/* 476 */       this.soundName = soundNameIn;
/*     */     }
/*     */     
/*     */     public ResourceLocation getSoundName() {
/* 480 */       return this.soundName;
/*     */     }
/*     */     
/*     */     public static void registerSounds() {
/* 484 */       if (registered)
/*     */         return; 
/* 486 */       registered = true;
/* 487 */       registerSound("ambient.cave");
/* 488 */       registerSound("block.anvil.break");
/* 489 */       registerSound("block.anvil.destroy");
/* 490 */       registerSound("block.anvil.fall");
/* 491 */       registerSound("block.anvil.hit");
/* 492 */       registerSound("block.anvil.land");
/* 493 */       registerSound("block.anvil.place");
/* 494 */       registerSound("block.anvil.step");
/* 495 */       registerSound("block.anvil.use");
/* 496 */       registerSound("block.brewing_stand.brew");
/* 497 */       registerSound("block.chest.close");
/* 498 */       registerSound("block.chest.locked");
/* 499 */       registerSound("block.chest.open");
/* 500 */       registerSound("block.chorus_flower.death");
/* 501 */       registerSound("block.chorus_flower.grow");
/* 502 */       registerSound("block.cloth.break");
/* 503 */       registerSound("block.cloth.fall");
/* 504 */       registerSound("block.cloth.hit");
/* 505 */       registerSound("block.cloth.place");
/* 506 */       registerSound("block.cloth.step");
/* 507 */       registerSound("block.comparator.click");
/* 508 */       registerSound("block.dispenser.dispense");
/* 509 */       registerSound("block.dispenser.fail");
/* 510 */       registerSound("block.dispenser.launch");
/* 511 */       registerSound("block.end_gateway.spawn");
/* 512 */       registerSound("block.enderchest.close");
/* 513 */       registerSound("block.enderchest.open");
/* 514 */       registerSound("block.fence_gate.close");
/* 515 */       registerSound("block.fence_gate.open");
/* 516 */       registerSound("block.fire.ambient");
/* 517 */       registerSound("block.fire.extinguish");
/* 518 */       registerSound("block.furnace.fire_crackle");
/* 519 */       registerSound("block.glass.break");
/* 520 */       registerSound("block.glass.fall");
/* 521 */       registerSound("block.glass.hit");
/* 522 */       registerSound("block.glass.place");
/* 523 */       registerSound("block.glass.step");
/* 524 */       registerSound("block.grass.break");
/* 525 */       registerSound("block.grass.fall");
/* 526 */       registerSound("block.grass.hit");
/* 527 */       registerSound("block.grass.place");
/* 528 */       registerSound("block.grass.step");
/* 529 */       registerSound("block.gravel.break");
/* 530 */       registerSound("block.gravel.fall");
/* 531 */       registerSound("block.gravel.hit");
/* 532 */       registerSound("block.gravel.place");
/* 533 */       registerSound("block.gravel.step");
/* 534 */       registerSound("block.iron_door.close");
/* 535 */       registerSound("block.iron_door.open");
/* 536 */       registerSound("block.iron_trapdoor.close");
/* 537 */       registerSound("block.iron_trapdoor.open");
/* 538 */       registerSound("block.ladder.break");
/* 539 */       registerSound("block.ladder.fall");
/* 540 */       registerSound("block.ladder.hit");
/* 541 */       registerSound("block.ladder.place");
/* 542 */       registerSound("block.ladder.step");
/* 543 */       registerSound("block.lava.ambient");
/* 544 */       registerSound("block.lava.extinguish");
/* 545 */       registerSound("block.lava.pop");
/* 546 */       registerSound("block.lever.click");
/* 547 */       registerSound("block.metal.break");
/* 548 */       registerSound("block.metal.fall");
/* 549 */       registerSound("block.metal.hit");
/* 550 */       registerSound("block.metal.place");
/* 551 */       registerSound("block.metal.step");
/* 552 */       registerSound("block.metal_pressureplate.click_off");
/* 553 */       registerSound("block.metal_pressureplate.click_on");
/* 554 */       registerSound("block.note.basedrum");
/* 555 */       registerSound("block.note.bass");
/* 556 */       registerSound("block.note.harp");
/* 557 */       registerSound("block.note.hat");
/* 558 */       registerSound("block.note.pling");
/* 559 */       registerSound("block.note.snare");
/* 560 */       registerSound("block.piston.contract");
/* 561 */       registerSound("block.piston.extend");
/* 562 */       registerSound("block.portal.ambient");
/* 563 */       registerSound("block.portal.travel");
/* 564 */       registerSound("block.portal.trigger");
/* 565 */       registerSound("block.redstone_torch.burnout");
/* 566 */       registerSound("block.sand.break");
/* 567 */       registerSound("block.sand.fall");
/* 568 */       registerSound("block.sand.hit");
/* 569 */       registerSound("block.sand.place");
/* 570 */       registerSound("block.sand.step");
/* 571 */       registerSound("block.slime.break");
/* 572 */       registerSound("block.slime.fall");
/* 573 */       registerSound("block.slime.hit");
/* 574 */       registerSound("block.slime.place");
/* 575 */       registerSound("block.slime.step");
/* 576 */       registerSound("block.snow.break");
/* 577 */       registerSound("block.snow.fall");
/* 578 */       registerSound("block.snow.hit");
/* 579 */       registerSound("block.snow.place");
/* 580 */       registerSound("block.snow.step");
/* 581 */       registerSound("block.stone.break");
/* 582 */       registerSound("block.stone.fall");
/* 583 */       registerSound("block.stone.hit");
/* 584 */       registerSound("block.stone.place");
/* 585 */       registerSound("block.stone.step");
/* 586 */       registerSound("block.stone_button.click_off");
/* 587 */       registerSound("block.stone_button.click_on");
/* 588 */       registerSound("block.stone_pressureplate.click_off");
/* 589 */       registerSound("block.stone_pressureplate.click_on");
/* 590 */       registerSound("block.tripwire.attach");
/* 591 */       registerSound("block.tripwire.click_off");
/* 592 */       registerSound("block.tripwire.click_on");
/* 593 */       registerSound("block.tripwire.detach");
/* 594 */       registerSound("block.water.ambient");
/* 595 */       registerSound("block.waterlily.place");
/* 596 */       registerSound("block.wood.break");
/* 597 */       registerSound("block.wood.fall");
/* 598 */       registerSound("block.wood.hit");
/* 599 */       registerSound("block.wood.place");
/* 600 */       registerSound("block.wood.step");
/* 601 */       registerSound("block.wood_button.click_off");
/* 602 */       registerSound("block.wood_button.click_on");
/* 603 */       registerSound("block.wood_pressureplate.click_off");
/* 604 */       registerSound("block.wood_pressureplate.click_on");
/* 605 */       registerSound("block.wooden_door.close");
/* 606 */       registerSound("block.wooden_door.open");
/* 607 */       registerSound("block.wooden_trapdoor.close");
/* 608 */       registerSound("block.wooden_trapdoor.open");
/* 609 */       registerSound("enchant.thorns.hit");
/* 610 */       registerSound("entity.armorstand.break");
/* 611 */       registerSound("entity.armorstand.fall");
/* 612 */       registerSound("entity.armorstand.hit");
/* 613 */       registerSound("entity.armorstand.place");
/* 614 */       registerSound("entity.arrow.hit");
/* 615 */       registerSound("entity.arrow.hit_player");
/* 616 */       registerSound("entity.arrow.shoot");
/* 617 */       registerSound("entity.bat.ambient");
/* 618 */       registerSound("entity.bat.death");
/* 619 */       registerSound("entity.bat.hurt");
/* 620 */       registerSound("entity.bat.loop");
/* 621 */       registerSound("entity.bat.takeoff");
/* 622 */       registerSound("entity.blaze.ambient");
/* 623 */       registerSound("entity.blaze.burn");
/* 624 */       registerSound("entity.blaze.death");
/* 625 */       registerSound("entity.blaze.hurt");
/* 626 */       registerSound("entity.blaze.shoot");
/* 627 */       registerSound("entity.bobber.splash");
/* 628 */       registerSound("entity.bobber.throw");
/* 629 */       registerSound("entity.cat.ambient");
/* 630 */       registerSound("entity.cat.death");
/* 631 */       registerSound("entity.cat.hiss");
/* 632 */       registerSound("entity.cat.hurt");
/* 633 */       registerSound("entity.cat.purr");
/* 634 */       registerSound("entity.cat.purreow");
/* 635 */       registerSound("entity.chicken.ambient");
/* 636 */       registerSound("entity.chicken.death");
/* 637 */       registerSound("entity.chicken.egg");
/* 638 */       registerSound("entity.chicken.hurt");
/* 639 */       registerSound("entity.chicken.step");
/* 640 */       registerSound("entity.cow.ambient");
/* 641 */       registerSound("entity.cow.death");
/* 642 */       registerSound("entity.cow.hurt");
/* 643 */       registerSound("entity.cow.milk");
/* 644 */       registerSound("entity.cow.step");
/* 645 */       registerSound("entity.creeper.death");
/* 646 */       registerSound("entity.creeper.hurt");
/* 647 */       registerSound("entity.creeper.primed");
/* 648 */       registerSound("entity.donkey.ambient");
/* 649 */       registerSound("entity.donkey.angry");
/* 650 */       registerSound("entity.donkey.chest");
/* 651 */       registerSound("entity.donkey.death");
/* 652 */       registerSound("entity.donkey.hurt");
/* 653 */       registerSound("entity.egg.throw");
/* 654 */       registerSound("entity.elder_guardian.ambient");
/* 655 */       registerSound("entity.elder_guardian.ambient_land");
/* 656 */       registerSound("entity.elder_guardian.curse");
/* 657 */       registerSound("entity.elder_guardian.death");
/* 658 */       registerSound("entity.elder_guardian.death_land");
/* 659 */       registerSound("entity.elder_guardian.hurt");
/* 660 */       registerSound("entity.elder_guardian.hurt_land");
/* 661 */       registerSound("entity.enderdragon.ambient");
/* 662 */       registerSound("entity.enderdragon.death");
/* 663 */       registerSound("entity.enderdragon.flap");
/* 664 */       registerSound("entity.enderdragon.growl");
/* 665 */       registerSound("entity.enderdragon.hurt");
/* 666 */       registerSound("entity.enderdragon.shoot");
/* 667 */       registerSound("entity.enderdragon_fireball.explode");
/* 668 */       registerSound("entity.endereye.launch");
/* 669 */       registerSound("entity.endermen.ambient");
/* 670 */       registerSound("entity.endermen.death");
/* 671 */       registerSound("entity.endermen.hurt");
/* 672 */       registerSound("entity.endermen.scream");
/* 673 */       registerSound("entity.endermen.stare");
/* 674 */       registerSound("entity.endermen.teleport");
/* 675 */       registerSound("entity.endermite.ambient");
/* 676 */       registerSound("entity.endermite.death");
/* 677 */       registerSound("entity.endermite.hurt");
/* 678 */       registerSound("entity.endermite.step");
/* 679 */       registerSound("entity.enderpearl.throw");
/* 680 */       registerSound("entity.experience_bottle.throw");
/* 681 */       registerSound("entity.experience_orb.pickup");
/* 682 */       registerSound("entity.experience_orb.touch");
/* 683 */       registerSound("entity.firework.blast");
/* 684 */       registerSound("entity.firework.blast_far");
/* 685 */       registerSound("entity.firework.large_blast");
/* 686 */       registerSound("entity.firework.large_blast_far");
/* 687 */       registerSound("entity.firework.launch");
/* 688 */       registerSound("entity.firework.shoot");
/* 689 */       registerSound("entity.firework.twinkle");
/* 690 */       registerSound("entity.firework.twinkle_far");
/* 691 */       registerSound("entity.generic.big_fall");
/* 692 */       registerSound("entity.generic.burn");
/* 693 */       registerSound("entity.generic.death");
/* 694 */       registerSound("entity.generic.drink");
/* 695 */       registerSound("entity.generic.eat");
/* 696 */       registerSound("entity.generic.explode");
/* 697 */       registerSound("entity.generic.extinguish_fire");
/* 698 */       registerSound("entity.generic.hurt");
/* 699 */       registerSound("entity.generic.small_fall");
/* 700 */       registerSound("entity.generic.splash");
/* 701 */       registerSound("entity.generic.swim");
/* 702 */       registerSound("entity.ghast.ambient");
/* 703 */       registerSound("entity.ghast.death");
/* 704 */       registerSound("entity.ghast.hurt");
/* 705 */       registerSound("entity.ghast.scream");
/* 706 */       registerSound("entity.ghast.shoot");
/* 707 */       registerSound("entity.ghast.warn");
/* 708 */       registerSound("entity.guardian.ambient");
/* 709 */       registerSound("entity.guardian.ambient_land");
/* 710 */       registerSound("entity.guardian.attack");
/* 711 */       registerSound("entity.guardian.death");
/* 712 */       registerSound("entity.guardian.death_land");
/* 713 */       registerSound("entity.guardian.flop");
/* 714 */       registerSound("entity.guardian.hurt");
/* 715 */       registerSound("entity.guardian.hurt_land");
/* 716 */       registerSound("entity.horse.ambient");
/* 717 */       registerSound("entity.horse.angry");
/* 718 */       registerSound("entity.horse.armor");
/* 719 */       registerSound("entity.horse.breathe");
/* 720 */       registerSound("entity.horse.death");
/* 721 */       registerSound("entity.horse.eat");
/* 722 */       registerSound("entity.horse.gallop");
/* 723 */       registerSound("entity.horse.hurt");
/* 724 */       registerSound("entity.horse.jump");
/* 725 */       registerSound("entity.horse.land");
/* 726 */       registerSound("entity.horse.saddle");
/* 727 */       registerSound("entity.horse.step");
/* 728 */       registerSound("entity.horse.step_wood");
/* 729 */       registerSound("entity.hostile.big_fall");
/* 730 */       registerSound("entity.hostile.death");
/* 731 */       registerSound("entity.hostile.hurt");
/* 732 */       registerSound("entity.hostile.small_fall");
/* 733 */       registerSound("entity.hostile.splash");
/* 734 */       registerSound("entity.hostile.swim");
/* 735 */       registerSound("entity.irongolem.attack");
/* 736 */       registerSound("entity.irongolem.death");
/* 737 */       registerSound("entity.irongolem.hurt");
/* 738 */       registerSound("entity.irongolem.step");
/* 739 */       registerSound("entity.item.break");
/* 740 */       registerSound("entity.item.pickup");
/* 741 */       registerSound("entity.itemframe.add_item");
/* 742 */       registerSound("entity.itemframe.break");
/* 743 */       registerSound("entity.itemframe.place");
/* 744 */       registerSound("entity.itemframe.remove_item");
/* 745 */       registerSound("entity.itemframe.rotate_item");
/* 746 */       registerSound("entity.leashknot.break");
/* 747 */       registerSound("entity.leashknot.place");
/* 748 */       registerSound("entity.lightning.impact");
/* 749 */       registerSound("entity.lightning.thunder");
/* 750 */       registerSound("entity.lingeringpotion.throw");
/* 751 */       registerSound("entity.magmacube.death");
/* 752 */       registerSound("entity.magmacube.hurt");
/* 753 */       registerSound("entity.magmacube.jump");
/* 754 */       registerSound("entity.magmacube.squish");
/* 755 */       registerSound("entity.minecart.inside");
/* 756 */       registerSound("entity.minecart.riding");
/* 757 */       registerSound("entity.mooshroom.shear");
/* 758 */       registerSound("entity.mule.ambient");
/* 759 */       registerSound("entity.mule.death");
/* 760 */       registerSound("entity.mule.hurt");
/* 761 */       registerSound("entity.painting.break");
/* 762 */       registerSound("entity.painting.place");
/* 763 */       registerSound("entity.pig.ambient");
/* 764 */       registerSound("entity.pig.death");
/* 765 */       registerSound("entity.pig.hurt");
/* 766 */       registerSound("entity.pig.saddle");
/* 767 */       registerSound("entity.pig.step");
/* 768 */       registerSound("entity.player.attack.crit");
/* 769 */       registerSound("entity.player.attack.knockback");
/* 770 */       registerSound("entity.player.attack.nodamage");
/* 771 */       registerSound("entity.player.attack.strong");
/* 772 */       registerSound("entity.player.attack.sweep");
/* 773 */       registerSound("entity.player.attack.weak");
/* 774 */       registerSound("entity.player.big_fall");
/* 775 */       registerSound("entity.player.breath");
/* 776 */       registerSound("entity.player.burp");
/* 777 */       registerSound("entity.player.death");
/* 778 */       registerSound("entity.player.hurt");
/* 779 */       registerSound("entity.player.levelup");
/* 780 */       registerSound("entity.player.small_fall");
/* 781 */       registerSound("entity.player.splash");
/* 782 */       registerSound("entity.player.swim");
/* 783 */       registerSound("entity.rabbit.ambient");
/* 784 */       registerSound("entity.rabbit.attack");
/* 785 */       registerSound("entity.rabbit.death");
/* 786 */       registerSound("entity.rabbit.hurt");
/* 787 */       registerSound("entity.rabbit.jump");
/* 788 */       registerSound("entity.sheep.ambient");
/* 789 */       registerSound("entity.sheep.death");
/* 790 */       registerSound("entity.sheep.hurt");
/* 791 */       registerSound("entity.sheep.shear");
/* 792 */       registerSound("entity.sheep.step");
/* 793 */       registerSound("entity.shulker.ambient");
/* 794 */       registerSound("entity.shulker.close");
/* 795 */       registerSound("entity.shulker.death");
/* 796 */       registerSound("entity.shulker.hurt");
/* 797 */       registerSound("entity.shulker.hurt_closed");
/* 798 */       registerSound("entity.shulker.open");
/* 799 */       registerSound("entity.shulker.shoot");
/* 800 */       registerSound("entity.shulker.teleport");
/* 801 */       registerSound("entity.shulker_bullet.hit");
/* 802 */       registerSound("entity.shulker_bullet.hurt");
/* 803 */       registerSound("entity.silverfish.ambient");
/* 804 */       registerSound("entity.silverfish.death");
/* 805 */       registerSound("entity.silverfish.hurt");
/* 806 */       registerSound("entity.silverfish.step");
/* 807 */       registerSound("entity.skeleton.ambient");
/* 808 */       registerSound("entity.skeleton.death");
/* 809 */       registerSound("entity.skeleton.hurt");
/* 810 */       registerSound("entity.skeleton.shoot");
/* 811 */       registerSound("entity.skeleton.step");
/* 812 */       registerSound("entity.skeleton_horse.ambient");
/* 813 */       registerSound("entity.skeleton_horse.death");
/* 814 */       registerSound("entity.skeleton_horse.hurt");
/* 815 */       registerSound("entity.slime.attack");
/* 816 */       registerSound("entity.slime.death");
/* 817 */       registerSound("entity.slime.hurt");
/* 818 */       registerSound("entity.slime.jump");
/* 819 */       registerSound("entity.slime.squish");
/* 820 */       registerSound("entity.small_magmacube.death");
/* 821 */       registerSound("entity.small_magmacube.hurt");
/* 822 */       registerSound("entity.small_magmacube.squish");
/* 823 */       registerSound("entity.small_slime.death");
/* 824 */       registerSound("entity.small_slime.hurt");
/* 825 */       registerSound("entity.small_slime.jump");
/* 826 */       registerSound("entity.small_slime.squish");
/* 827 */       registerSound("entity.snowball.throw");
/* 828 */       registerSound("entity.snowman.ambient");
/* 829 */       registerSound("entity.snowman.death");
/* 830 */       registerSound("entity.snowman.hurt");
/* 831 */       registerSound("entity.snowman.shoot");
/* 832 */       registerSound("entity.spider.ambient");
/* 833 */       registerSound("entity.spider.death");
/* 834 */       registerSound("entity.spider.hurt");
/* 835 */       registerSound("entity.spider.step");
/* 836 */       registerSound("entity.splash_potion.break");
/* 837 */       registerSound("entity.splash_potion.throw");
/* 838 */       registerSound("entity.squid.ambient");
/* 839 */       registerSound("entity.squid.death");
/* 840 */       registerSound("entity.squid.hurt");
/* 841 */       registerSound("entity.tnt.primed");
/* 842 */       registerSound("entity.villager.ambient");
/* 843 */       registerSound("entity.villager.death");
/* 844 */       registerSound("entity.villager.hurt");
/* 845 */       registerSound("entity.villager.no");
/* 846 */       registerSound("entity.villager.trading");
/* 847 */       registerSound("entity.villager.yes");
/* 848 */       registerSound("entity.witch.ambient");
/* 849 */       registerSound("entity.witch.death");
/* 850 */       registerSound("entity.witch.drink");
/* 851 */       registerSound("entity.witch.hurt");
/* 852 */       registerSound("entity.witch.throw");
/* 853 */       registerSound("entity.wither.ambient");
/* 854 */       registerSound("entity.wither.break_block");
/* 855 */       registerSound("entity.wither.death");
/* 856 */       registerSound("entity.wither.hurt");
/* 857 */       registerSound("entity.wither.shoot");
/* 858 */       registerSound("entity.wither.spawn");
/* 859 */       registerSound("entity.wolf.ambient");
/* 860 */       registerSound("entity.wolf.death");
/* 861 */       registerSound("entity.wolf.growl");
/* 862 */       registerSound("entity.wolf.howl");
/* 863 */       registerSound("entity.wolf.hurt");
/* 864 */       registerSound("entity.wolf.pant");
/* 865 */       registerSound("entity.wolf.shake");
/* 866 */       registerSound("entity.wolf.step");
/* 867 */       registerSound("entity.wolf.whine");
/* 868 */       registerSound("entity.zombie.ambient");
/* 869 */       registerSound("entity.zombie.attack_door_wood");
/* 870 */       registerSound("entity.zombie.attack_iron_door");
/* 871 */       registerSound("entity.zombie.break_door_wood");
/* 872 */       registerSound("entity.zombie.death");
/* 873 */       registerSound("entity.zombie.hurt");
/* 874 */       registerSound("entity.zombie.infect");
/* 875 */       registerSound("entity.zombie.step");
/* 876 */       registerSound("entity.zombie_horse.ambient");
/* 877 */       registerSound("entity.zombie_horse.death");
/* 878 */       registerSound("entity.zombie_horse.hurt");
/* 879 */       registerSound("entity.zombie_pig.ambient");
/* 880 */       registerSound("entity.zombie_pig.angry");
/* 881 */       registerSound("entity.zombie_pig.death");
/* 882 */       registerSound("entity.zombie_pig.hurt");
/* 883 */       registerSound("entity.zombie_villager.ambient");
/* 884 */       registerSound("entity.zombie_villager.converted");
/* 885 */       registerSound("entity.zombie_villager.cure");
/* 886 */       registerSound("entity.zombie_villager.death");
/* 887 */       registerSound("entity.zombie_villager.hurt");
/* 888 */       registerSound("entity.zombie_villager.step");
/* 889 */       registerSound("item.armor.equip_chain");
/* 890 */       registerSound("item.armor.equip_diamond");
/* 891 */       registerSound("item.armor.equip_generic");
/* 892 */       registerSound("item.armor.equip_gold");
/* 893 */       registerSound("item.armor.equip_iron");
/* 894 */       registerSound("item.armor.equip_leather");
/* 895 */       registerSound("item.bottle.fill");
/* 896 */       registerSound("item.bottle.fill_dragonbreath");
/* 897 */       registerSound("item.bucket.empty");
/* 898 */       registerSound("item.bucket.empty_lava");
/* 899 */       registerSound("item.bucket.fill");
/* 900 */       registerSound("item.bucket.fill_lava");
/* 901 */       registerSound("item.chorus_fruit.teleport");
/* 902 */       registerSound("item.elytra.flying");
/* 903 */       registerSound("item.firecharge.use");
/* 904 */       registerSound("item.flintandsteel.use");
/* 905 */       registerSound("item.hoe.till");
/* 906 */       registerSound("item.shield.block");
/* 907 */       registerSound("item.shield.break");
/* 908 */       registerSound("item.shovel.flatten");
/* 909 */       registerSound("music.creative");
/* 910 */       registerSound("music.credits");
/* 911 */       registerSound("music.dragon");
/* 912 */       registerSound("music.end");
/* 913 */       registerSound("music.game");
/* 914 */       registerSound("music.menu");
/* 915 */       registerSound("music.nether");
/* 916 */       registerSound("record.11");
/* 917 */       registerSound("record.13");
/* 918 */       registerSound("record.blocks");
/* 919 */       registerSound("record.cat");
/* 920 */       registerSound("record.chirp");
/* 921 */       registerSound("record.far");
/* 922 */       registerSound("record.mall");
/* 923 */       registerSound("record.mellohi");
/* 924 */       registerSound("record.stal");
/* 925 */       registerSound("record.strad");
/* 926 */       registerSound("record.wait");
/* 927 */       registerSound("record.ward");
/* 928 */       registerSound("ui.button.click");
/* 929 */       registerSound("weather.rain");
/* 930 */       registerSound("weather.rain.above");
/*     */     }
/*     */     
/*     */     private static void registerSound(String soundNameIn) {
/* 934 */       ResourceLocation resourcelocation = new ResourceLocation(soundNameIn);
/* 935 */       REGISTRY.register(soundEventId++, resourcelocation, new SoundEvent(resourcelocation));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_11_2\PacketWrapper316.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
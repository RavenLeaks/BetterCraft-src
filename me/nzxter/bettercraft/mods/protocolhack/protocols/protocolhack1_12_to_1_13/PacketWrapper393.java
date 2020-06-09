/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_13;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.server.SPacketAdvancementInfo;
/*     */ import net.minecraft.network.play.server.SPacketAnimation;
/*     */ import net.minecraft.network.play.server.SPacketBlockAction;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.network.play.server.SPacketCloseWindow;
/*     */ import net.minecraft.network.play.server.SPacketEntity;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.SPacketUseBed;
/*     */ 
/*     */ public class PacketWrapper393 extends PacketWrapper {
/*     */   public PacketWrapper393() {
/*  16 */     super(ProtocolHack.PROTOCOL_393);
/*     */   }
/*     */   
/*     */   public static int getOldFromNew(int newId) {
/*  20 */     return 158;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePacket(Packet<?> packet, PacketBuffer buffer) throws Exception {
/*  25 */     if (packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport) {
/*  26 */       buffer.writeVarIntToBuffer(0);
/*  27 */       packet.writePacketData(buffer);
/*  28 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketTabComplete) {
/*  29 */       buffer.writeVarIntToBuffer(5);
/*  30 */       packet.writePacketData(buffer);
/*  31 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketChatMessage) {
/*  32 */       buffer.writeVarIntToBuffer(2);
/*  33 */       packet.writePacketData(buffer);
/*  34 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClientStatus) {
/*  35 */       buffer.writeVarIntToBuffer(3);
/*  36 */       packet.writePacketData(buffer);
/*  37 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClientSettings) {
/*  38 */       buffer.writeVarIntToBuffer(4);
/*  39 */       packet.writePacketData(buffer);
/*  40 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketConfirmTransaction) {
/*  41 */       buffer.writeVarIntToBuffer(6);
/*  42 */       packet.writePacketData(buffer);
/*  43 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketEnchantItem) {
/*  44 */       buffer.writeVarIntToBuffer(7);
/*  45 */       packet.writePacketData(buffer);
/*  46 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketClickWindow) {
/*  47 */       buffer.writeVarIntToBuffer(8);
/*  48 */       packet.writePacketData(buffer);
/*  49 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketCloseWindow) {
/*  50 */       buffer.writeVarIntToBuffer(9);
/*  51 */       packet.writePacketData(buffer);
/*  52 */     } else if (packet instanceof CPacketCustomPayload) {
/*  53 */       String ch = ((CPacketCustomPayload)packet).channel;
/*  54 */       if (ch.startsWith("MC|")) {
/*  55 */         ch = "minecraft:" + ch.substring("MC|".length());
/*     */       }
/*  57 */       ((CPacketCustomPayload)packet).channel = ch.toLowerCase();
/*  58 */       buffer.writeVarIntToBuffer(10);
/*  59 */       packet.writePacketData(buffer);
/*  60 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketUseEntity) {
/*  61 */       buffer.writeVarIntToBuffer(13);
/*  62 */       packet.writePacketData(buffer);
/*  63 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketKeepAlive) {
/*  64 */       buffer.writeVarIntToBuffer(14);
/*  65 */       packet.writePacketData(buffer);
/*  66 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/*  67 */       buffer.writeVarIntToBuffer(16);
/*  68 */       packet.writePacketData(buffer);
/*  69 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/*  70 */       buffer.writeVarIntToBuffer(17);
/*  71 */       packet.writePacketData(buffer);
/*  72 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/*  73 */       buffer.writeVarIntToBuffer(18);
/*  74 */       packet.writePacketData(buffer);
/*  75 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
/*  76 */       buffer.writeVarIntToBuffer(15);
/*  77 */       packet.writePacketData(buffer);
/*  78 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketVehicleMove) {
/*  79 */       buffer.writeVarIntToBuffer(19);
/*  80 */       packet.writePacketData(buffer);
/*  81 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketSteerBoat) {
/*  82 */       buffer.writeVarIntToBuffer(20);
/*  83 */       packet.writePacketData(buffer);
/*  84 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerAbilities) {
/*  85 */       buffer.writeVarIntToBuffer(23);
/*  86 */       packet.writePacketData(buffer);
/*  87 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerDigging) {
/*  88 */       buffer.writeVarIntToBuffer(24);
/*  89 */       packet.writePacketData(buffer);
/*  90 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketEntityAction) {
/*  91 */       buffer.writeVarIntToBuffer(25);
/*  92 */       packet.writePacketData(buffer);
/*  93 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketInput) {
/*  94 */       buffer.writeVarIntToBuffer(21);
/*  95 */       packet.writePacketData(buffer);
/*  96 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketResourcePackStatus) {
/*  97 */       buffer.writeVarIntToBuffer(29);
/*  98 */       packet.writePacketData(buffer);
/*  99 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketHeldItemChange) {
/* 100 */       buffer.writeVarIntToBuffer(33);
/* 101 */       packet.writePacketData(buffer);
/* 102 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketCreativeInventoryAction) {
/* 103 */       buffer.writeVarIntToBuffer(36);
/* 104 */       packet.writePacketData(buffer);
/* 105 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketUpdateSign) {
/* 106 */       buffer.writeVarIntToBuffer(38);
/* 107 */       packet.writePacketData(buffer);
/* 108 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketAnimation) {
/* 109 */       buffer.writeVarIntToBuffer(39);
/* 110 */       packet.writePacketData(buffer);
/* 111 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketSpectate) {
/* 112 */       buffer.writeVarIntToBuffer(40);
/* 113 */       packet.writePacketData(buffer);
/* 114 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock) {
/* 115 */       buffer.writeVarIntToBuffer(41);
/* 116 */       packet.writePacketData(buffer);
/* 117 */     } else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem) {
/* 118 */       buffer.writeVarIntToBuffer(42);
/* 119 */       packet.writePacketData(buffer);
/*     */     }  } public Packet<?> readPacket(int packetId, PacketBuffer buffer) throws Exception { SPacketSpawnExperienceOrb sPacketSpawnExperienceOrb; SPacketSpawnGlobalEntity sPacketSpawnGlobalEntity; SPacketSpawnPainting sPacketSpawnPainting; SPacketSpawnPlayer sPacketSpawnPlayer; SPacketAnimation sPacketAnimation; SPacketStatistics sPacketStatistics; SPacketBlockBreakAnim sPacketBlockBreakAnim; SPacketBlockAction sPacketBlockAction; SPacketBlockChange sPacketBlockChange; SPacketUpdateBossInfo sPacketUpdateBossInfo; SPacketServerDifficulty sPacketServerDifficulty; SPacketTabComplete sPacketTabComplete; SPacketChat sPacketChat; SPacketConfirmTransaction sPacketConfirmTransaction; SPacketCloseWindow sPacketCloseWindow; SPacketOpenWindow sPacketOpenWindow; SPacketWindowItems sPacketWindowItems; SPacketWindowProperty sPacketWindowProperty; SPacketSetSlot sPacketSetSlot; SPacketCooldown sPacketCooldown; SPacketCustomPayload sPacketCustomPayload; SPacketCustomSound sPacketCustomSound; SPacketDisconnect sPacketDisconnect; SPacketEntityStatus sPacketEntityStatus; SPacketExplosion sPacketExplosion; SPacketUnloadChunk sPacketUnloadChunk; SPacketChangeGameState sPacketChangeGameState; SPacketKeepAlive sPacketKeepAlive; SPacketChunkData sPacketChunkData; SPacketEffect sPacketEffect; SPacketParticles sPacketParticles; SPacketJoinGame sPacketJoinGame; SPacketMaps sPacketMaps; SPacketEntity sPacketEntity; SPacketMoveVehicle sPacketMoveVehicle; SPacketSignEditorOpen sPacketSignEditorOpen; SPacketPlayerAbilities sPacketPlayerAbilities; SPacketCombatEvent sPacketCombatEvent; SPacketPlayerListItem sPacketPlayerListItem; SPacketPlayerPosLook sPacketPlayerPosLook; SPacketUseBed sPacketUseBed; SPacketDestroyEntities sPacketDestroyEntities; SPacketRemoveEntityEffect sPacketRemoveEntityEffect; SPacketResourcePackSend sPacketResourcePackSend; SPacketRespawn sPacketRespawn; SPacketEntityHeadLook sPacketEntityHeadLook; SPacketWorldBorder sPacketWorldBorder; SPacketCamera sPacketCamera; SPacketHeldItemChange sPacketHeldItemChange; SPacketDisplayObjective sPacketDisplayObjective; SPacketEntityAttach sPacketEntityAttach; SPacketEntityVelocity sPacketEntityVelocity; SPacketSetExperience sPacketSetExperience; SPacketUpdateHealth sPacketUpdateHealth; SPacketScoreboardObjective sPacketScoreboardObjective; SPacketSetPassengers sPacketSetPassengers; SPacketUpdateScore sPacketUpdateScore; SPacketSpawnPosition sPacketSpawnPosition; SPacketTimeUpdate sPacketTimeUpdate; SPacketTitle sPacketTitle; SPacketSoundEffect sPacketSoundEffect; SPacketPlayerListHeaderFooter sPacketPlayerListHeaderFooter; SPacketCollectItem sPacketCollectItem;
/*     */     SPacketEntityTeleport sPacketEntityTeleport;
/*     */     SPacketEntityProperties sPacketEntityProperties;
/*     */     SPacketEntityEffect sPacketEntityEffect;
/*     */     SPacketAdvancementInfo sPacketAdvancementInfo;
/* 125 */     Packet<?> pack = null;
/* 126 */     switch (packetId) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 132 */         sPacketSpawnExperienceOrb = new SPacketSpawnExperienceOrb();
/* 133 */         sPacketSpawnExperienceOrb.readPacketData(buffer);
/*     */         break;
/*     */       case 2:
/* 136 */         sPacketSpawnGlobalEntity = new SPacketSpawnGlobalEntity();
/* 137 */         sPacketSpawnGlobalEntity.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 144 */         sPacketSpawnPainting = new SPacketSpawnPainting();
/* 145 */         sPacketSpawnPainting.readPacketData(buffer);
/*     */         break;
/*     */       case 5:
/* 148 */         sPacketSpawnPlayer = new SPacketSpawnPlayer();
/* 149 */         sPacketSpawnPlayer.readPacketData(buffer);
/*     */         break;
/*     */       case 6:
/* 152 */         sPacketAnimation = new SPacketAnimation();
/* 153 */         sPacketAnimation.readPacketData(buffer);
/*     */         break;
/*     */       case 7:
/* 156 */         sPacketStatistics = new SPacketStatistics();
/* 157 */         sPacketStatistics.readPacketData(buffer);
/*     */         break;
/*     */       case 8:
/* 160 */         sPacketBlockBreakAnim = new SPacketBlockBreakAnim();
/* 161 */         sPacketBlockBreakAnim.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 168 */         sPacketBlockAction = new SPacketBlockAction();
/* 169 */         sPacketBlockAction.readPacketData(buffer);
/*     */         break;
/*     */       case 11:
/* 172 */         sPacketBlockChange = new SPacketBlockChange();
/*     */         
/* 174 */         sPacketBlockChange.blockPosition = buffer.readBlockPos();
/* 175 */         sPacketBlockChange.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(getOldFromNew(buffer.readVarIntFromBuffer()));
/*     */         break;
/*     */       case 12:
/* 178 */         sPacketUpdateBossInfo = new SPacketUpdateBossInfo();
/* 179 */         sPacketUpdateBossInfo.readPacketData(buffer);
/*     */         break;
/*     */       case 13:
/* 182 */         sPacketServerDifficulty = new SPacketServerDifficulty();
/* 183 */         sPacketServerDifficulty.readPacketData(buffer);
/*     */         break;
/*     */       case 16:
/* 186 */         sPacketTabComplete = new SPacketTabComplete();
/* 187 */         sPacketTabComplete.readPacketData(buffer);
/*     */         break;
/*     */       case 14:
/* 190 */         sPacketChat = new SPacketChat();
/* 191 */         sPacketChat.readPacketData(buffer);
/*     */         break;
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
/*     */       case 18:
/* 210 */         sPacketConfirmTransaction = new SPacketConfirmTransaction();
/* 211 */         sPacketConfirmTransaction.readPacketData(buffer);
/*     */         break;
/*     */       case 19:
/* 214 */         sPacketCloseWindow = new SPacketCloseWindow();
/* 215 */         sPacketCloseWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 20:
/* 218 */         sPacketOpenWindow = new SPacketOpenWindow();
/* 219 */         sPacketOpenWindow.readPacketData(buffer);
/*     */         break;
/*     */       case 21:
/* 222 */         sPacketWindowItems = new SPacketWindowItems();
/* 223 */         sPacketWindowItems.readPacketData(buffer);
/*     */         break;
/*     */       case 22:
/* 226 */         sPacketWindowProperty = new SPacketWindowProperty();
/* 227 */         sPacketWindowProperty.readPacketData(buffer);
/*     */         break;
/*     */       case 23:
/* 230 */         sPacketSetSlot = new SPacketSetSlot();
/* 231 */         sPacketSetSlot.readPacketData(buffer);
/*     */         break;
/*     */       case 24:
/* 234 */         sPacketCooldown = new SPacketCooldown();
/* 235 */         sPacketCooldown.readPacketData(buffer);
/*     */         break;
/*     */       case 25:
/* 238 */         sPacketCustomPayload = new SPacketCustomPayload();
/* 239 */         sPacketCustomPayload.readPacketData(buffer);
/*     */         break;
/*     */       case 26:
/* 242 */         sPacketCustomSound = new SPacketCustomSound();
/* 243 */         sPacketCustomSound.readPacketData(buffer);
/*     */         break;
/*     */       case 27:
/* 246 */         sPacketDisconnect = new SPacketDisconnect();
/* 247 */         sPacketDisconnect.readPacketData(buffer);
/*     */         break;
/*     */       case 28:
/* 250 */         sPacketEntityStatus = new SPacketEntityStatus();
/* 251 */         sPacketEntityStatus.readPacketData(buffer);
/*     */         break;
/*     */       case 30:
/* 254 */         sPacketExplosion = new SPacketExplosion();
/* 255 */         sPacketExplosion.readPacketData(buffer);
/*     */         break;
/*     */       case 31:
/* 258 */         sPacketUnloadChunk = new SPacketUnloadChunk();
/* 259 */         sPacketUnloadChunk.readPacketData(buffer);
/*     */         break;
/*     */       case 32:
/* 262 */         sPacketChangeGameState = new SPacketChangeGameState();
/* 263 */         sPacketChangeGameState.readPacketData(buffer);
/*     */         break;
/*     */       case 33:
/* 266 */         sPacketKeepAlive = new SPacketKeepAlive();
/* 267 */         sPacketKeepAlive.readPacketData(buffer);
/*     */         break;
/*     */       case 34:
/* 270 */         sPacketChunkData = new SPacketChunkData();
/* 271 */         sPacketChunkData.readPacketData(buffer);
/*     */         break;
/*     */       case 35:
/* 274 */         sPacketEffect = new SPacketEffect();
/* 275 */         sPacketEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 36:
/* 278 */         sPacketParticles = new SPacketParticles();
/* 279 */         sPacketParticles.readPacketData(buffer);
/*     */         break;
/*     */       case 37:
/* 282 */         sPacketJoinGame = new SPacketJoinGame();
/* 283 */         sPacketJoinGame.readPacketData(buffer);
/*     */         break;
/*     */       case 38:
/* 286 */         sPacketMaps = new SPacketMaps();
/* 287 */         sPacketMaps.readPacketData(buffer);
/*     */         break;
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
/*     */       case 39:
/* 302 */         sPacketEntity = new SPacketEntity();
/* 303 */         sPacketEntity.readPacketData(buffer);
/*     */         break;
/*     */       case 43:
/* 306 */         sPacketMoveVehicle = new SPacketMoveVehicle();
/* 307 */         sPacketMoveVehicle.readPacketData(buffer);
/*     */         break;
/*     */       case 44:
/* 310 */         sPacketSignEditorOpen = new SPacketSignEditorOpen();
/* 311 */         sPacketSignEditorOpen.readPacketData(buffer);
/*     */         break;
/*     */       case 46:
/* 314 */         sPacketPlayerAbilities = new SPacketPlayerAbilities();
/* 315 */         sPacketPlayerAbilities.readPacketData(buffer);
/*     */         break;
/*     */       case 47:
/* 318 */         sPacketCombatEvent = new SPacketCombatEvent();
/* 319 */         sPacketCombatEvent.readPacketData(buffer);
/*     */         break;
/*     */       case 48:
/* 322 */         sPacketPlayerListItem = new SPacketPlayerListItem();
/* 323 */         sPacketPlayerListItem.readPacketData(buffer);
/*     */         break;
/*     */       case 50:
/* 326 */         sPacketPlayerPosLook = new SPacketPlayerPosLook();
/* 327 */         sPacketPlayerPosLook.readPacketData(buffer);
/*     */         break;
/*     */       case 51:
/* 330 */         sPacketUseBed = new SPacketUseBed();
/* 331 */         sPacketUseBed.readPacketData(buffer);
/*     */         break;
/*     */       case 53:
/* 334 */         sPacketDestroyEntities = new SPacketDestroyEntities();
/* 335 */         sPacketDestroyEntities.readPacketData(buffer);
/*     */         break;
/*     */       case 54:
/* 338 */         sPacketRemoveEntityEffect = new SPacketRemoveEntityEffect();
/* 339 */         sPacketRemoveEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 55:
/* 342 */         sPacketResourcePackSend = new SPacketResourcePackSend();
/* 343 */         sPacketResourcePackSend.readPacketData(buffer);
/*     */         break;
/*     */       case 56:
/* 346 */         sPacketRespawn = new SPacketRespawn();
/* 347 */         sPacketRespawn.readPacketData(buffer);
/*     */         break;
/*     */       case 57:
/* 350 */         sPacketEntityHeadLook = new SPacketEntityHeadLook();
/* 351 */         sPacketEntityHeadLook.readPacketData(buffer);
/*     */         break;
/*     */       case 59:
/* 354 */         sPacketWorldBorder = new SPacketWorldBorder();
/* 355 */         sPacketWorldBorder.readPacketData(buffer);
/*     */         break;
/*     */       case 60:
/* 358 */         sPacketCamera = new SPacketCamera();
/* 359 */         sPacketCamera.readPacketData(buffer);
/*     */         break;
/*     */       case 61:
/* 362 */         sPacketHeldItemChange = new SPacketHeldItemChange();
/* 363 */         sPacketHeldItemChange.readPacketData(buffer);
/*     */         break;
/*     */       case 62:
/* 366 */         sPacketDisplayObjective = new SPacketDisplayObjective();
/* 367 */         sPacketDisplayObjective.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 64:
/* 374 */         sPacketEntityAttach = new SPacketEntityAttach();
/* 375 */         sPacketEntityAttach.readPacketData(buffer);
/*     */         break;
/*     */       case 65:
/* 378 */         sPacketEntityVelocity = new SPacketEntityVelocity();
/* 379 */         sPacketEntityVelocity.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 67:
/* 386 */         sPacketSetExperience = new SPacketSetExperience();
/* 387 */         sPacketSetExperience.readPacketData(buffer);
/*     */         break;
/*     */       case 68:
/* 390 */         sPacketUpdateHealth = new SPacketUpdateHealth();
/* 391 */         sPacketUpdateHealth.readPacketData(buffer);
/*     */         break;
/*     */       case 69:
/* 394 */         sPacketScoreboardObjective = new SPacketScoreboardObjective();
/* 395 */         sPacketScoreboardObjective.readPacketData(buffer);
/*     */         break;
/*     */       case 70:
/* 398 */         sPacketSetPassengers = new SPacketSetPassengers();
/* 399 */         sPacketSetPassengers.readPacketData(buffer);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 72:
/* 406 */         sPacketUpdateScore = new SPacketUpdateScore();
/* 407 */         sPacketUpdateScore.readPacketData(buffer);
/*     */         break;
/*     */       case 73:
/* 410 */         sPacketSpawnPosition = new SPacketSpawnPosition();
/* 411 */         sPacketSpawnPosition.readPacketData(buffer);
/*     */         break;
/*     */       case 74:
/* 414 */         sPacketTimeUpdate = new SPacketTimeUpdate();
/* 415 */         sPacketTimeUpdate.readPacketData(buffer);
/*     */         break;
/*     */       case 75:
/* 418 */         sPacketTitle = new SPacketTitle();
/* 419 */         sPacketTitle.readPacketData(buffer);
/*     */         break;
/*     */       case 77:
/* 422 */         sPacketSoundEffect = new SPacketSoundEffect();
/* 423 */         sPacketSoundEffect.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 78:
/* 427 */         sPacketPlayerListHeaderFooter = new SPacketPlayerListHeaderFooter();
/* 428 */         sPacketPlayerListHeaderFooter.readPacketData(buffer);
/*     */         break;
/*     */       case 79:
/* 431 */         sPacketCollectItem = new SPacketCollectItem();
/* 432 */         sPacketCollectItem.readPacketData(buffer);
/*     */         break;
/*     */       case 80:
/* 435 */         sPacketEntityTeleport = new SPacketEntityTeleport();
/* 436 */         sPacketEntityTeleport.readPacketData(buffer);
/*     */         break;
/*     */       case 82:
/* 439 */         sPacketEntityProperties = new SPacketEntityProperties();
/* 440 */         sPacketEntityProperties.readPacketData(buffer);
/*     */         break;
/*     */       case 83:
/* 443 */         sPacketEntityEffect = new SPacketEntityEffect();
/* 444 */         sPacketEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */       case 81:
/* 447 */         sPacketAdvancementInfo = new SPacketAdvancementInfo();
/* 448 */         sPacketAdvancementInfo.readPacketData(buffer);
/*     */         break;
/*     */     } 
/* 451 */     return (Packet<?>)sPacketAdvancementInfo; }
/*     */ 
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_13\PacketWrapper393.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
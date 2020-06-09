/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.PacketWrapper;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.ProtocolHack;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk.ChunkBulkWrapper;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk.ChunkWrapper;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk.IChunk;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.entity.MetaDataUtils;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.sound.SoundEffect;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.sound.SoundRegistry;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.network.play.client.CPacketClickWindow;
/*     */ import net.minecraft.network.play.client.CPacketClientSettings;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketTabComplete;
/*     */ import net.minecraft.network.play.client.CPacketUpdateSign;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketAnimation;
/*     */ import net.minecraft.network.play.server.SPacketBlockAction;
/*     */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketCamera;
/*     */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.network.play.server.SPacketCloseWindow;
/*     */ import net.minecraft.network.play.server.SPacketCollectItem;
/*     */ import net.minecraft.network.play.server.SPacketCombatEvent;
/*     */ import net.minecraft.network.play.server.SPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*     */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*     */ import net.minecraft.network.play.server.SPacketDisconnect;
/*     */ import net.minecraft.network.play.server.SPacketDisplayObjective;
/*     */ import net.minecraft.network.play.server.SPacketEffect;
/*     */ import net.minecraft.network.play.server.SPacketEntity;
/*     */ import net.minecraft.network.play.server.SPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.SPacketEntityHeadLook;
/*     */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.SPacketEntityProperties;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.network.play.server.SPacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.SPacketExplosion;
/*     */ import net.minecraft.network.play.server.SPacketHeldItemChange;
/*     */ import net.minecraft.network.play.server.SPacketJoinGame;
/*     */ import net.minecraft.network.play.server.SPacketKeepAlive;
/*     */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.SPacketParticles;
/*     */ import net.minecraft.network.play.server.SPacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketResourcePackSend;
/*     */ import net.minecraft.network.play.server.SPacketRespawn;
/*     */ import net.minecraft.network.play.server.SPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.SPacketServerDifficulty;
/*     */ import net.minecraft.network.play.server.SPacketSetExperience;
/*     */ import net.minecraft.network.play.server.SPacketSetSlot;
/*     */ import net.minecraft.network.play.server.SPacketSignEditorOpen;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
/*     */ import net.minecraft.network.play.server.SPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPlayer;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPosition;
/*     */ import net.minecraft.network.play.server.SPacketStatistics;
/*     */ import net.minecraft.network.play.server.SPacketTabComplete;
/*     */ import net.minecraft.network.play.server.SPacketTeams;
/*     */ import net.minecraft.network.play.server.SPacketTimeUpdate;
/*     */ import net.minecraft.network.play.server.SPacketTitle;
/*     */ import net.minecraft.network.play.server.SPacketUpdateHealth;
/*     */ import net.minecraft.network.play.server.SPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.network.play.server.SPacketUseBed;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraft.network.play.server.SPacketWindowProperty;
/*     */ import net.minecraft.network.play.server.SPacketWorldBorder;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.WorldType;
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
/*     */ public class PacketWrapper47
/*     */   extends PacketWrapper
/*     */ {
/*     */   public Map<Integer, List<EntityDataManager.DataEntry<?>>> metadataMap;
/*     */   
/*     */   public PacketWrapper47() {
/* 152 */     super(ProtocolHack.PROTOCOL_47);
/* 153 */     this.metadataMap = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePacket(Packet<?> packet, PacketBuffer buffer) throws Exception {
/* 158 */     if (!(packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport))
/* 159 */       if (packet instanceof CPacketTabComplete) {
/* 160 */         buffer.writeVarIntToBuffer(20);
/* 161 */         buffer.writeString(((CPacketTabComplete)packet).getMessage());
/* 162 */         boolean flag = (((CPacketTabComplete)packet).getTargetBlock() != null);
/* 163 */         buffer.writeBoolean(flag);
/* 164 */         if (flag) {
/* 165 */           buffer.writeBlockPos(((CPacketTabComplete)packet).getTargetBlock());
/*     */         }
/*     */       }
/* 168 */       else if (packet instanceof net.minecraft.network.play.client.CPacketChatMessage) {
/* 169 */         buffer.writeVarIntToBuffer(1);
/* 170 */         packet.writePacketData(buffer);
/*     */       }
/* 172 */       else if (packet instanceof net.minecraft.network.play.client.CPacketClientStatus) {
/* 173 */         buffer.writeVarIntToBuffer(22);
/* 174 */         packet.writePacketData(buffer);
/*     */       }
/* 176 */       else if (packet instanceof CPacketClientSettings) {
/* 177 */         CPacketClientSettings pa = (CPacketClientSettings)packet;
/* 178 */         buffer.writeVarIntToBuffer(21);
/* 179 */         buffer.writeString(pa.getLang());
/* 180 */         buffer.writeByte(pa.view);
/* 181 */         buffer.writeEnumValue((Enum)pa.getChatVisibility());
/* 182 */         buffer.writeBoolean(pa.isColorsEnabled());
/* 183 */         buffer.writeByte(pa.getModelPartFlags());
/*     */       }
/* 185 */       else if (packet instanceof net.minecraft.network.play.client.CPacketConfirmTransaction) {
/* 186 */         buffer.writeVarIntToBuffer(15);
/* 187 */         packet.writePacketData(buffer);
/*     */       }
/* 189 */       else if (packet instanceof net.minecraft.network.play.client.CPacketEnchantItem) {
/* 190 */         buffer.writeVarIntToBuffer(17);
/* 191 */         packet.writePacketData(buffer);
/*     */       }
/* 193 */       else if (packet instanceof CPacketClickWindow) {
/* 194 */         CPacketClickWindow pa2 = (CPacketClickWindow)packet;
/* 195 */         buffer.writeVarIntToBuffer(14);
/* 196 */         buffer.writeByte(pa2.getWindowId());
/* 197 */         buffer.writeShort(pa2.getSlotId());
/* 198 */         buffer.writeByte(pa2.getUsedButton());
/* 199 */         buffer.writeShort(pa2.getActionNumber());
/* 200 */         buffer.writeByte(pa2.getClickType().ordinal());
/* 201 */         buffer.writeItemStackToBuffer(pa2.getClickedItem());
/*     */       }
/* 203 */       else if (packet instanceof net.minecraft.network.play.client.CPacketCloseWindow) {
/* 204 */         buffer.writeVarIntToBuffer(13);
/* 205 */         packet.writePacketData(buffer);
/*     */       }
/* 207 */       else if (packet instanceof net.minecraft.network.play.client.CPacketCustomPayload) {
/* 208 */         buffer.writeVarIntToBuffer(23);
/* 209 */         packet.writePacketData(buffer);
/*     */       }
/* 211 */       else if (packet instanceof CPacketUseEntity) {
/* 212 */         CPacketUseEntity pa3 = (CPacketUseEntity)packet;
/* 213 */         buffer.writeVarIntToBuffer(2);
/* 214 */         buffer.writeVarIntToBuffer(pa3.entityId);
/* 215 */         buffer.writeEnumValue((Enum)pa3.getAction());
/* 216 */         if (pa3.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
/* 217 */           buffer.writeFloat((float)(pa3.getHitVec()).xCoord);
/* 218 */           buffer.writeFloat((float)(pa3.getHitVec()).yCoord);
/* 219 */           buffer.writeFloat((float)(pa3.getHitVec()).zCoord);
/*     */         }
/*     */       
/* 222 */       } else if (packet instanceof CPacketKeepAlive) {
/* 223 */         buffer.writeVarIntToBuffer(0);
/* 224 */         buffer.writeVarIntToBuffer((int)((CPacketKeepAlive)packet).getKey());
/*     */       }
/* 226 */       else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/* 227 */         buffer.writeVarIntToBuffer(4);
/* 228 */         packet.writePacketData(buffer);
/*     */       }
/* 230 */       else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/* 231 */         buffer.writeVarIntToBuffer(6);
/* 232 */         packet.writePacketData(buffer);
/*     */       }
/* 234 */       else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/* 235 */         buffer.writeVarIntToBuffer(5);
/* 236 */         packet.writePacketData(buffer);
/*     */       }
/* 238 */       else if (packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
/* 239 */         buffer.writeVarIntToBuffer(3);
/* 240 */         packet.writePacketData(buffer);
/*     */       }
/* 242 */       else if (!(packet instanceof net.minecraft.network.play.client.CPacketVehicleMove) && !(packet instanceof net.minecraft.network.play.client.CPacketSteerBoat)) {
/* 243 */         if (packet instanceof net.minecraft.network.play.client.CPacketPlayerAbilities) {
/* 244 */           buffer.writeVarIntToBuffer(19);
/* 245 */           packet.writePacketData(buffer);
/*     */         }
/* 247 */         else if (packet instanceof net.minecraft.network.play.client.CPacketPlayerDigging) {
/* 248 */           buffer.writeVarIntToBuffer(7);
/* 249 */           packet.writePacketData(buffer);
/*     */         }
/* 251 */         else if (packet instanceof CPacketEntityAction) {
/* 252 */           buffer.writeVarIntToBuffer(11);
/* 253 */           CPacketEntityAction ac = (CPacketEntityAction)packet;
/* 254 */           int i = 0;
/* 255 */           switch (ac.getAction()) {
/*     */             case null:
/* 257 */               i = 6;
/*     */               break;
/*     */             
/*     */             case START_RIDING_JUMP:
/* 261 */               i = 5;
/*     */               break;
/*     */             
/*     */             case START_SNEAKING:
/* 265 */               i = 0;
/*     */               break;
/*     */             
/*     */             case STOP_SNEAKING:
/* 269 */               i = 1;
/*     */               break;
/*     */             
/*     */             case STOP_SLEEPING:
/* 273 */               i = 2;
/*     */               break;
/*     */             
/*     */             case START_SPRINTING:
/* 277 */               i = 3;
/*     */               break;
/*     */             
/*     */             case STOP_SPRINTING:
/* 281 */               i = 4;
/*     */               break;
/*     */             
/*     */             default:
/*     */               return;
/*     */           } 
/*     */           
/* 288 */           buffer.writeVarIntToBuffer(ac.entityID);
/* 289 */           buffer.writeVarIntToBuffer(i);
/* 290 */           buffer.writeVarIntToBuffer(ac.getAuxData());
/*     */         }
/* 292 */         else if (packet instanceof net.minecraft.network.play.client.CPacketInput) {
/* 293 */           buffer.writeVarIntToBuffer(12);
/* 294 */           packet.writePacketData(buffer);
/*     */         }
/* 296 */         else if (!(packet instanceof net.minecraft.network.play.client.CPacketResourcePackStatus)) {
/* 297 */           if (packet instanceof net.minecraft.network.play.client.CPacketHeldItemChange) {
/* 298 */             buffer.writeVarIntToBuffer(9);
/* 299 */             packet.writePacketData(buffer);
/*     */           }
/* 301 */           else if (packet instanceof net.minecraft.network.play.client.CPacketCreativeInventoryAction) {
/* 302 */             buffer.writeVarIntToBuffer(16);
/* 303 */             packet.writePacketData(buffer);
/*     */           }
/* 305 */           else if (packet instanceof CPacketUpdateSign) {
/* 306 */             CPacketUpdateSign pa4 = (CPacketUpdateSign)packet;
/* 307 */             buffer.writeVarIntToBuffer(18);
/* 308 */             buffer.writeBlockPos(pa4.getPosition());
/* 309 */             for (int i = 0; i < 4; i++) {
/* 310 */               TextComponentString textComponentString = new TextComponentString(pa4.getLines()[i]);
/* 311 */               String s = ITextComponent.Serializer.componentToJson((ITextComponent)textComponentString);
/* 312 */               buffer.writeString(s);
/*     */             }
/*     */           
/* 315 */           } else if (packet instanceof net.minecraft.network.play.client.CPacketAnimation) {
/* 316 */             buffer.writeVarIntToBuffer(10);
/*     */           }
/* 318 */           else if (packet instanceof net.minecraft.network.play.client.CPacketSpectate) {
/* 319 */             buffer.writeVarIntToBuffer(24);
/* 320 */             packet.writePacketData(buffer);
/*     */           }
/* 322 */           else if (packet instanceof CPacketPlayerTryUseItemOnBlock) {
/* 323 */             if ((Minecraft.getMinecraft()).player.getHeldItemMainhand() == null) {
/*     */               return;
/*     */             }
/* 326 */             buffer.writeVarIntToBuffer(8);
/* 327 */             CPacketPlayerTryUseItemOnBlock pa5 = (CPacketPlayerTryUseItemOnBlock)packet;
/* 328 */             buffer.writeBlockPos(pa5.position);
/* 329 */             buffer.writeByte(pa5.placedBlockDirection.getIndex());
/* 330 */             buffer.writeItemStackToBuffer((Minecraft.getMinecraft()).player.getHeldItemMainhand());
/* 331 */             buffer.writeByte((int)(pa5.facingX * 16.0F));
/* 332 */             buffer.writeByte((int)(pa5.facingY * 16.0F));
/* 333 */             buffer.writeByte((int)(pa5.facingZ * 16.0F));
/*     */           }
/* 335 */           else if (packet instanceof CPacketPlayerTryUseItem) {
/* 336 */             if ((Minecraft.getMinecraft()).player.getHeldItemMainhand() == null) {
/*     */               return;
/*     */             }
/* 339 */             buffer.writeVarIntToBuffer(8);
/* 340 */             CPacketPlayerTryUseItem pa6 = (CPacketPlayerTryUseItem)packet;
/* 341 */             buffer.writeBlockPos(new BlockPos(-1, -1, -1));
/* 342 */             buffer.writeByte(255);
/* 343 */             buffer.writeItemStackToBuffer((Minecraft.getMinecraft()).player.getHeldItemMainhand());
/* 344 */             buffer.writeByte(0);
/* 345 */             buffer.writeByte(0);
/* 346 */             buffer.writeByte(0);
/*     */           } 
/*     */         } 
/*     */       }   } public Packet<?> readPacket(int packetId, PacketBuffer buffer) throws Exception { SPacketKeepAlive sPacketKeepAlive; SPacketJoinGame sPacketJoinGame; SPacketChat sPacketChat; SPacketTimeUpdate sPacketTimeUpdate; SPacketEntityEquipment sPacketEntityEquipment; SPacketSpawnPosition sPacketSpawnPosition; SPacketUpdateHealth sPacketUpdateHealth; SPacketRespawn sPacketRespawn; SPacketPlayerPosLook sPacketPlayerPosLook; SPacketHeldItemChange sPacketHeldItemChange; SPacketUseBed sPacketUseBed; SPacketAnimation sPacketAnimation; SPacketSpawnPlayer sPacketSpawnPlayer1; SPacketCollectItem sPacketCollectItem; SPacketSpawnObject sPacketSpawnObject; SPacketSpawnMob sPacketSpawnMob; SPacketSpawnPainting sPacketSpawnPainting; SPacketSpawnExperienceOrb sPacketSpawnExperienceOrb; SPacketEntityVelocity sPacketEntityVelocity; SPacketDestroyEntities sPacketDestroyEntities; SPacketEntity sPacketEntity; SPacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove; SPacketEntity.S16PacketEntityLook s16PacketEntityLook; SPacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove; SPacketEntityTeleport sPacketEntityTeleport; SPacketEntityHeadLook sPacketEntityHeadLook; SPacketEntityStatus sPacketEntityStatus; SPacketEntityAttach sPacketEntityAttach; SPacketEntityMetadata sPacketEntityMetadata1; SPacketEntityEffect sPacketEntityEffect; SPacketRemoveEntityEffect sPacketRemoveEntityEffect; SPacketSetExperience sPacketSetExperience; SPacketEntityProperties sPacketEntityProperties; SPacketChunkData sPacketChunkData; SPacketMultiBlockChange sPacketMultiBlockChange; SPacketBlockChange sPacketBlockChange; SPacketBlockAction sPacketBlockAction; SPacketBlockBreakAnim sPacketBlockBreakAnim; SPacketExplosion sPacketExplosion; SPacketEffect sPacketEffect; SPacketParticles sPacketParticles; SPacketChangeGameState sPacketChangeGameState; SPacketSpawnGlobalEntity sPacketSpawnGlobalEntity; SPacketOpenWindow sPacketOpenWindow; SPacketCloseWindow sPacketCloseWindow; SPacketSetSlot sPacketSetSlot; SPacketWindowItems sPacketWindowItems; SPacketWindowProperty sPacketWindowProperty; SPacketConfirmTransaction sPacketConfirmTransaction; SPacketUpdateTileEntity sPacketUpdateTileEntity; SPacketSignEditorOpen sPacketSignEditorOpen; SPacketStatistics sPacketStatistics; SPacketPlayerListItem sPacketPlayerListItem; SPacketPlayerAbilities sPacketPlayerAbilities; SPacketTabComplete sPacketTabComplete; SPacketScoreboardObjective sPacketScoreboardObjective; SPacketUpdateScore sPacketUpdateScore; SPacketDisplayObjective sPacketDisplayObjective; SPacketTeams sPacketTeams; SPacketCustomPayload sPacketCustomPayload; SPacketDisconnect sPacketDisconnect; SPacketServerDifficulty sPacketServerDifficulty; SPacketCombatEvent sPacketCombatEvent; SPacketCamera sPacketCamera; SPacketWorldBorder sPacketWorldBorder; SPacketTitle sPacketTitle; SPacketPlayerListHeaderFooter sPacketPlayerListHeaderFooter; SPacketResourcePackSend sPacketResourcePackSend; int entityId, entityID, dimID; double x; SPacketSpawnPlayer spawnPlayer; int collectedItemEntityId, eid2, eId, eid3; SPacketEntityMetadata meta; List<PacketBuffer> packets; int treshold, i, slott; EnumDifficulty diff; int eid1, type, typee; String title; List<EntityDataManager.DataEntry<?>> list3; int h; boolean hardcoreMode; EntityEquipmentSlot equipmentSlot; GameType gameType; double y; int collectedItems, x2, xy; BlockPos paintPosition; Entity en; GameType gameType1; ItemStack itemStack; WorldType worldType; int y2, yy; EnumFacing facing1; int dimension; double z; int z2, zy; UUID uuid1; EnumDifficulty difficulty; int pitch2; byte yaww; int maxPlayers; float yaw; int yaw2; byte pitchh; WorldType worldType1; float pitch;
/*     */     int data;
/*     */     byte headPitch;
/*     */     boolean reducedDebugInfo;
/*     */     Set<SPacketPlayerPosLook.EnumFlags> flags;
/*     */     int speedX, velocityX, speedY, velocityY, speedZ, velocityZ;
/* 355 */     Packet<?> pack = null;
/* 356 */     switch (packetId) {
/*     */       case 0:
/* 358 */         sPacketKeepAlive = new SPacketKeepAlive(buffer.readVarIntFromBuffer());
/*     */         break;
/*     */       
/*     */       case 1:
/* 362 */         entityId = buffer.readInt();
/* 363 */         i = buffer.readUnsignedByte();
/* 364 */         hardcoreMode = ((i & 0x8) == 8);
/* 365 */         i &= 0xFFFFFFF7;
/* 366 */         gameType1 = GameType.getByID(i);
/* 367 */         dimension = buffer.readByte();
/* 368 */         difficulty = EnumDifficulty.getDifficultyEnum(buffer.readUnsignedByte());
/* 369 */         maxPlayers = buffer.readUnsignedByte();
/* 370 */         worldType1 = WorldType.parseWorldType(buffer.readStringFromBuffer(16));
/* 371 */         if (worldType1 == null) {
/* 372 */           worldType1 = WorldType.DEFAULT;
/*     */         }
/* 374 */         reducedDebugInfo = buffer.readBoolean();
/* 375 */         sPacketJoinGame = new SPacketJoinGame(entityId, gameType1, hardcoreMode, dimension, difficulty, maxPlayers, worldType1, reducedDebugInfo);
/*     */         break;
/*     */       
/*     */       case 2:
/* 379 */         sPacketChat = new SPacketChat();
/* 380 */         sPacketChat.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 3:
/* 384 */         sPacketTimeUpdate = new SPacketTimeUpdate();
/* 385 */         sPacketTimeUpdate.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 4:
/* 389 */         entityID = buffer.readVarIntFromBuffer();
/* 390 */         slott = buffer.readShort();
/* 391 */         if (entityID == (Minecraft.getMinecraft()).player.getEntityId()) {
/* 392 */           slott += 2;
/*     */         }
/* 394 */         slott = (slott > 0) ? (slott + 1) : slott;
/* 395 */         equipmentSlot = EntityEquipmentSlot.values()[slott];
/* 396 */         itemStack = buffer.readItemStackFromBuffer();
/* 397 */         sPacketEntityEquipment = new SPacketEntityEquipment(entityID, equipmentSlot, itemStack);
/*     */         break;
/*     */       
/*     */       case 5:
/* 401 */         sPacketSpawnPosition = new SPacketSpawnPosition();
/* 402 */         sPacketSpawnPosition.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 6:
/* 406 */         sPacketUpdateHealth = new SPacketUpdateHealth();
/* 407 */         sPacketUpdateHealth.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 7:
/* 411 */         dimID = buffer.readInt();
/* 412 */         diff = EnumDifficulty.getDifficultyEnum(buffer.readUnsignedByte());
/* 413 */         gameType = GameType.getByID(buffer.readUnsignedByte());
/* 414 */         worldType = WorldType.parseWorldType(buffer.readStringFromBuffer(16));
/* 415 */         sPacketRespawn = new SPacketRespawn(dimID, diff, worldType, gameType);
/*     */         break;
/*     */       
/*     */       case 8:
/* 419 */         x = buffer.readDouble();
/* 420 */         y = buffer.readDouble();
/* 421 */         z = buffer.readDouble();
/* 422 */         yaw = buffer.readFloat();
/* 423 */         pitch = buffer.readFloat();
/* 424 */         flags = SPacketPlayerPosLook.EnumFlags.unpack(buffer.readUnsignedByte());
/* 425 */         sPacketPlayerPosLook = new SPacketPlayerPosLook(x, y, z, yaw, pitch, flags, 0);
/*     */         break;
/*     */       
/*     */       case 9:
/* 429 */         sPacketHeldItemChange = new SPacketHeldItemChange();
/* 430 */         sPacketHeldItemChange.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 10:
/* 434 */         sPacketUseBed = new SPacketUseBed();
/* 435 */         sPacketUseBed.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 11:
/* 439 */         sPacketAnimation = new SPacketAnimation();
/* 440 */         sPacketAnimation.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 12:
/* 444 */         sPacketSpawnPlayer1 = new SPacketSpawnPlayer();
/* 445 */         spawnPlayer = sPacketSpawnPlayer1;
/* 446 */         sPacketSpawnPlayer1.entityId = buffer.readVarIntFromBuffer();
/* 447 */         sPacketSpawnPlayer1.uniqueId = buffer.readUuid();
/* 448 */         sPacketSpawnPlayer1.x = buffer.readInt() / 32.0D;
/* 449 */         sPacketSpawnPlayer1.y = buffer.readInt() / 32.0D;
/* 450 */         sPacketSpawnPlayer1.z = buffer.readInt() / 32.0D;
/* 451 */         sPacketSpawnPlayer1.yaw = buffer.readByte();
/* 452 */         sPacketSpawnPlayer1.pitch = buffer.readByte();
/* 453 */         sPacketSpawnPlayer1.dataManagerEntries = new ArrayList();
/*     */         try {
/* 455 */           List<EntityDataManager.DataEntry<?>> list = MetaDataUtils.readEntries_47(buffer);
/* 456 */           MetaDataUtils.handleMetadata_47((AbstractClientPlayer)(Minecraft.getMinecraft()).player, (Entity)new EntityOtherPlayerMP((Minecraft.getMinecraft()).player.world, new GameProfile(sPacketSpawnPlayer1.uniqueId, "Dummy")), list, sPacketSpawnPlayer1.dataManagerEntries);
/*     */         }
/* 458 */         catch (Throwable t) {
/* 459 */           System.out.println("Failed to read player metadata!");
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 13:
/* 464 */         collectedItemEntityId = buffer.readVarIntFromBuffer();
/* 465 */         eid1 = buffer.readVarIntFromBuffer();
/* 466 */         collectedItems = 1;
/* 467 */         sPacketCollectItem = new SPacketCollectItem(collectedItemEntityId, eid1, 1);
/*     */         break;
/*     */       
/*     */       case 14:
/* 471 */         eid2 = buffer.readVarIntFromBuffer();
/* 472 */         type = buffer.readByte();
/* 473 */         x2 = buffer.readInt();
/* 474 */         y2 = buffer.readInt();
/* 475 */         z2 = buffer.readInt();
/* 476 */         pitch2 = buffer.readByte();
/* 477 */         yaw2 = buffer.readByte();
/* 478 */         data = buffer.readInt();
/* 479 */         speedX = 0;
/* 480 */         speedY = 0;
/* 481 */         speedZ = 0;
/* 482 */         if (data > 0) {
/* 483 */           speedX = buffer.readShort();
/* 484 */           speedY = buffer.readShort();
/* 485 */           speedZ = buffer.readShort();
/*     */         } 
/* 487 */         sPacketSpawnObject = new SPacketSpawnObject();
/* 488 */         sPacketSpawnObject.data = data;
/* 489 */         sPacketSpawnObject.x = x2 / 32.0D;
/* 490 */         sPacketSpawnObject.y = y2 / 32.0D;
/* 491 */         sPacketSpawnObject.z = z2 / 32.0D;
/* 492 */         sPacketSpawnObject.pitch = pitch2;
/* 493 */         sPacketSpawnObject.yaw = yaw2;
/* 494 */         sPacketSpawnObject.entityId = eid2;
/* 495 */         sPacketSpawnObject.speedX = speedX;
/* 496 */         sPacketSpawnObject.speedY = speedY;
/* 497 */         sPacketSpawnObject.speedZ = speedZ;
/* 498 */         sPacketSpawnObject.type = type;
/* 499 */         sPacketSpawnObject.uniqueId = UUID.randomUUID();
/*     */         break;
/*     */       
/*     */       case 15:
/* 503 */         sPacketSpawnMob = new SPacketSpawnMob();
/* 504 */         eId = buffer.readVarIntFromBuffer();
/* 505 */         typee = buffer.readByte() & 0xFF;
/* 506 */         xy = buffer.readInt();
/* 507 */         yy = buffer.readInt();
/* 508 */         zy = buffer.readInt();
/* 509 */         yaww = buffer.readByte();
/* 510 */         pitchh = buffer.readByte();
/* 511 */         headPitch = buffer.readByte();
/* 512 */         velocityX = buffer.readShort();
/* 513 */         velocityY = buffer.readShort();
/* 514 */         velocityZ = buffer.readShort();
/* 515 */         sPacketSpawnMob.entityId = eId;
/* 516 */         sPacketSpawnMob.uniqueId = UUID.randomUUID();
/* 517 */         sPacketSpawnMob.type = typee;
/* 518 */         sPacketSpawnMob.x = xy / 32.0D;
/* 519 */         sPacketSpawnMob.y = yy / 32.0D;
/* 520 */         sPacketSpawnMob.z = zy / 32.0D;
/* 521 */         sPacketSpawnMob.yaw = yaww;
/* 522 */         sPacketSpawnMob.pitch = pitchh;
/* 523 */         sPacketSpawnMob.headPitch = headPitch;
/* 524 */         sPacketSpawnMob.velocityX = velocityX;
/* 525 */         sPacketSpawnMob.velocityY = velocityY;
/* 526 */         sPacketSpawnMob.velocityZ = velocityZ;
/* 527 */         sPacketSpawnMob.dataManagerEntries = new ArrayList();
/*     */         try {
/* 529 */           List<EntityDataManager.DataEntry<?>> list2 = MetaDataUtils.readEntries_47(buffer);
/* 530 */           Entity entity = EntityList.createEntityByID(typee, (Minecraft.getMinecraft()).player.world);
/* 531 */           MetaDataUtils.handleMetadata_47((AbstractClientPlayer)(Minecraft.getMinecraft()).player, entity, list2, sPacketSpawnMob.dataManagerEntries);
/*     */         }
/* 533 */         catch (Throwable t2) {
/* 534 */           System.out.println("Failed to read mob metadata!");
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 16:
/* 539 */         eid3 = buffer.readVarIntFromBuffer();
/* 540 */         title = buffer.readStringFromBuffer(EntityPainting.EnumArt.MAX_NAME_LENGTH);
/* 541 */         paintPosition = buffer.readBlockPos();
/* 542 */         facing1 = EnumFacing.getHorizontal(buffer.readUnsignedByte());
/* 543 */         uuid1 = new UUID(0L, 0L);
/* 544 */         sPacketSpawnPainting = new SPacketSpawnPainting();
/* 545 */         sPacketSpawnPainting.entityID = eid3;
/* 546 */         sPacketSpawnPainting.title = title;
/* 547 */         sPacketSpawnPainting.facing = facing1;
/* 548 */         sPacketSpawnPainting.uniqueId = uuid1;
/* 549 */         sPacketSpawnPainting.position = paintPosition;
/*     */         break;
/*     */       
/*     */       case 17:
/* 553 */         sPacketSpawnExperienceOrb = new SPacketSpawnExperienceOrb();
/* 554 */         sPacketSpawnExperienceOrb.entityID = buffer.readVarIntFromBuffer();
/* 555 */         sPacketSpawnExperienceOrb.posX = buffer.readInt() / 32.0D;
/* 556 */         sPacketSpawnExperienceOrb.posY = buffer.readInt() / 32.0D;
/* 557 */         sPacketSpawnExperienceOrb.posZ = buffer.readInt() / 32.0D;
/* 558 */         sPacketSpawnExperienceOrb.xpValue = buffer.readShort();
/*     */         break;
/*     */       
/*     */       case 18:
/* 562 */         sPacketEntityVelocity = new SPacketEntityVelocity();
/* 563 */         sPacketEntityVelocity.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 19:
/* 567 */         sPacketDestroyEntities = new SPacketDestroyEntities();
/* 568 */         sPacketDestroyEntities.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 20:
/* 572 */         sPacketEntity = new SPacketEntity();
/* 573 */         sPacketEntity.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 21:
/* 577 */         s15PacketEntityRelMove = new SPacketEntity.S15PacketEntityRelMove(buffer.readVarIntFromBuffer(), (buffer.readByte() * 128), (buffer.readByte() * 128), (buffer.readByte() * 128), buffer.readBoolean());
/*     */         break;
/*     */       
/*     */       case 22:
/* 581 */         s16PacketEntityLook = new SPacketEntity.S16PacketEntityLook();
/* 582 */         s16PacketEntityLook.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 23:
/* 586 */         s17PacketEntityLookMove = new SPacketEntity.S17PacketEntityLookMove(buffer.readVarIntFromBuffer(), (buffer.readByte() * 128), (buffer.readByte() * 128), (buffer.readByte() * 128), buffer.readByte(), buffer.readByte(), buffer.readBoolean());
/*     */         break;
/*     */       
/*     */       case 24:
/* 590 */         sPacketEntityTeleport = new SPacketEntityTeleport();
/* 591 */         sPacketEntityTeleport.entityId = buffer.readVarIntFromBuffer();
/* 592 */         sPacketEntityTeleport.posX = buffer.readInt() / 32.0D;
/* 593 */         sPacketEntityTeleport.posY = buffer.readInt() / 32.0D;
/* 594 */         sPacketEntityTeleport.posZ = buffer.readInt() / 32.0D;
/* 595 */         sPacketEntityTeleport.yaw = buffer.readByte();
/* 596 */         sPacketEntityTeleport.pitch = buffer.readByte();
/* 597 */         sPacketEntityTeleport.onGround = buffer.readBoolean();
/*     */         break;
/*     */       
/*     */       case 25:
/* 601 */         sPacketEntityHeadLook = new SPacketEntityHeadLook();
/* 602 */         sPacketEntityHeadLook.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 26:
/* 606 */         sPacketEntityStatus = new SPacketEntityStatus();
/* 607 */         sPacketEntityStatus.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 27:
/* 611 */         sPacketEntityAttach = new SPacketEntityAttach();
/* 612 */         sPacketEntityAttach.entityId = buffer.readInt();
/* 613 */         sPacketEntityAttach.vehicleEntityId = buffer.readInt();
/* 614 */         buffer.readUnsignedByte();
/*     */         break;
/*     */       
/*     */       case 28:
/* 618 */         sPacketEntityMetadata1 = new SPacketEntityMetadata();
/* 619 */         meta = sPacketEntityMetadata1;
/* 620 */         meta.entityId = buffer.readVarIntFromBuffer();
/* 621 */         meta.dataManagerEntries = new ArrayList();
/* 622 */         list3 = MetaDataUtils.readEntries_47(buffer);
/* 623 */         if ((Minecraft.getMinecraft()).player.world == null) {
/* 624 */           this.metadataMap.put(Integer.valueOf(meta.entityId), list3);
/* 625 */           sPacketEntityMetadata1 = null;
/*     */           break;
/*     */         } 
/* 628 */         en = (Minecraft.getMinecraft()).player.world.getEntityByID(meta.entityId);
/* 629 */         if (en != null) {
/* 630 */           MetaDataUtils.handleMetadata_47((AbstractClientPlayer)(Minecraft.getMinecraft()).player, en, list3, meta.dataManagerEntries);
/*     */           break;
/*     */         } 
/* 633 */         this.metadataMap.put(Integer.valueOf(meta.entityId), list3);
/* 634 */         sPacketEntityMetadata1 = null;
/*     */         break;
/*     */       
/*     */       case 29:
/* 638 */         sPacketEntityEffect = new SPacketEntityEffect();
/* 639 */         sPacketEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 30:
/* 643 */         sPacketRemoveEntityEffect = new SPacketRemoveEntityEffect();
/* 644 */         sPacketRemoveEntityEffect.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 31:
/* 648 */         sPacketSetExperience = new SPacketSetExperience();
/* 649 */         sPacketSetExperience.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 32:
/* 653 */         sPacketEntityProperties = new SPacketEntityProperties();
/* 654 */         sPacketEntityProperties.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 33:
/* 658 */         sPacketChunkData = parse_1_8_Chunk(ChunkWrapper.readChunk_47(buffer, false));
/*     */         break;
/*     */       
/*     */       case 34:
/* 662 */         sPacketMultiBlockChange = new SPacketMultiBlockChange();
/* 663 */         sPacketMultiBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 35:
/* 667 */         sPacketBlockChange = new SPacketBlockChange();
/* 668 */         sPacketBlockChange.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 36:
/* 672 */         sPacketBlockAction = new SPacketBlockAction();
/* 673 */         sPacketBlockAction.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 37:
/* 677 */         sPacketBlockBreakAnim = new SPacketBlockBreakAnim();
/* 678 */         sPacketBlockBreakAnim.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 38:
/* 682 */         packets = ChunkBulkWrapper.transformMapChunkBulk(buffer);
/* 683 */         for (h = 0; h < packets.size(); h++) {
/* 684 */           SPacketChunkData chunk = parse_1_8_Chunk(ChunkWrapper.readChunk_47(packets.get(h), true));
/* 685 */           Minecraft.getMinecraft().addScheduledTask(() -> paramSPacketChunkData.processPacket((INetHandlerPlayClient)(Minecraft.getMinecraft()).player.connection));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 39:
/* 690 */         sPacketExplosion = new SPacketExplosion();
/* 691 */         sPacketExplosion.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 40:
/* 695 */         sPacketEffect = new SPacketEffect();
/* 696 */         sPacketEffect.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 41:
/*     */         try {
/* 701 */           SPacketSoundEffect sPacketSoundEffect = new SPacketSoundEffect();
/* 702 */           String soundName = buffer.readStringFromBuffer(256);
/* 703 */           int posX = buffer.readInt();
/* 704 */           int posY = buffer.readInt();
/* 705 */           int posZ = buffer.readInt();
/* 706 */           float soundVolume = buffer.readFloat();
/* 707 */           float soundPitch = buffer.readUnsignedByte() / 63.5F;
/* 708 */           sPacketSoundEffect.category = SoundCategory.VOICE;
/* 709 */           sPacketSoundEffect.posX = posX;
/* 710 */           sPacketSoundEffect.posY = posY;
/* 711 */           sPacketSoundEffect.posZ = posZ;
/* 712 */           String s = SoundEffect.getByName(soundName).getNewName();
/* 713 */           sPacketSoundEffect.sound = (SoundEvent)SoundRegistry.REGISTRY.getObject(new ResourceLocation(s));
/* 714 */           sPacketSoundEffect.soundPitch = soundPitch;
/* 715 */           sPacketSoundEffect.soundVolume = soundVolume;
/*     */         }
/* 717 */         catch (Throwable t3) {
/* 718 */           sPacketEffect = null;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 42:
/* 723 */         sPacketParticles = new SPacketParticles();
/* 724 */         sPacketParticles.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 43:
/* 728 */         sPacketChangeGameState = new SPacketChangeGameState();
/* 729 */         sPacketChangeGameState.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 44:
/* 733 */         sPacketSpawnGlobalEntity = new SPacketSpawnGlobalEntity();
/* 734 */         sPacketSpawnGlobalEntity.entityId = buffer.readVarIntFromBuffer();
/* 735 */         sPacketSpawnGlobalEntity.type = buffer.readByte();
/* 736 */         sPacketSpawnGlobalEntity.x = buffer.readInt() / 32.0D;
/* 737 */         sPacketSpawnGlobalEntity.y = buffer.readInt() / 32.0D;
/* 738 */         sPacketSpawnGlobalEntity.z = buffer.readInt() / 32.0D;
/*     */         break;
/*     */       
/*     */       case 45:
/* 742 */         sPacketOpenWindow = new SPacketOpenWindow();
/* 743 */         sPacketOpenWindow.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 46:
/* 747 */         sPacketCloseWindow = new SPacketCloseWindow();
/* 748 */         sPacketCloseWindow.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 47:
/* 752 */         sPacketSetSlot = new SPacketSetSlot();
/* 753 */         sPacketSetSlot.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 48:
/* 757 */         sPacketWindowItems = new SPacketWindowItems();
/* 758 */         sPacketWindowItems.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 49:
/* 762 */         sPacketWindowProperty = new SPacketWindowProperty();
/* 763 */         sPacketWindowProperty.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 50:
/* 767 */         sPacketConfirmTransaction = new SPacketConfirmTransaction();
/* 768 */         sPacketConfirmTransaction.readPacketData(buffer);
/*     */       
/*     */       case 51:
/*     */       case 53:
/* 772 */         sPacketUpdateTileEntity = new SPacketUpdateTileEntity();
/* 773 */         sPacketUpdateTileEntity.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 54:
/* 777 */         sPacketSignEditorOpen = new SPacketSignEditorOpen();
/* 778 */         sPacketSignEditorOpen.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 55:
/* 782 */         sPacketStatistics = new SPacketStatistics();
/* 783 */         sPacketStatistics.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 56:
/* 787 */         sPacketPlayerListItem = new SPacketPlayerListItem();
/* 788 */         sPacketPlayerListItem.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 57:
/* 792 */         sPacketPlayerAbilities = new SPacketPlayerAbilities();
/* 793 */         sPacketPlayerAbilities.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 58:
/* 797 */         sPacketTabComplete = new SPacketTabComplete();
/* 798 */         sPacketTabComplete.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 59:
/* 802 */         sPacketScoreboardObjective = new SPacketScoreboardObjective();
/* 803 */         sPacketScoreboardObjective.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 60:
/* 807 */         sPacketUpdateScore = new SPacketUpdateScore();
/* 808 */         sPacketUpdateScore.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 61:
/* 812 */         sPacketDisplayObjective = new SPacketDisplayObjective();
/* 813 */         sPacketDisplayObjective.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 62:
/* 817 */         sPacketTeams = new SPacketTeams();
/* 818 */         sPacketTeams.name = buffer.readStringFromBuffer(16);
/* 819 */         sPacketTeams.action = buffer.readByte();
/* 820 */         if (sPacketTeams.action == 0 || sPacketTeams.action == 2) {
/* 821 */           sPacketTeams.displayName = buffer.readStringFromBuffer(32);
/* 822 */           sPacketTeams.prefix = buffer.readStringFromBuffer(16);
/* 823 */           sPacketTeams.suffix = buffer.readStringFromBuffer(16);
/* 824 */           sPacketTeams.friendlyFlags = buffer.readByte();
/* 825 */           sPacketTeams.nameTagVisibility = buffer.readStringFromBuffer(32);
/* 826 */           sPacketTeams.color = buffer.readByte();
/*     */         } 
/* 828 */         if (sPacketTeams.action == 0 || sPacketTeams.action == 3 || sPacketTeams.action == 4) {
/* 829 */           for (int k = buffer.readVarIntFromBuffer(), j = 0; j < k; j++) {
/* 830 */             sPacketTeams.players.add(buffer.readStringFromBuffer(40));
/*     */           }
/*     */         }
/* 833 */         sPacketTeams.collisionRule = Team.CollisionRule.NEVER.name;
/*     */         break;
/*     */       
/*     */       case 63:
/* 837 */         sPacketCustomPayload = new SPacketCustomPayload();
/* 838 */         sPacketCustomPayload.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 64:
/* 842 */         sPacketDisconnect = new SPacketDisconnect();
/* 843 */         sPacketDisconnect.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 65:
/* 847 */         sPacketServerDifficulty = new SPacketServerDifficulty();
/* 848 */         sPacketServerDifficulty.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 66:
/* 852 */         sPacketCombatEvent = new SPacketCombatEvent();
/* 853 */         sPacketCombatEvent.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 67:
/* 857 */         sPacketCamera = new SPacketCamera();
/* 858 */         sPacketCamera.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 68:
/* 862 */         sPacketWorldBorder = new SPacketWorldBorder();
/* 863 */         sPacketWorldBorder.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 69:
/* 867 */         sPacketTitle = new SPacketTitle();
/* 868 */         sPacketTitle.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 70:
/* 872 */         treshold = buffer.readVarIntFromBuffer();
/* 873 */         (Minecraft.getMinecraft()).player.connection.getNetworkManager().setCompressionThreshold(treshold);
/*     */         break;
/*     */       
/*     */       case 71:
/* 877 */         sPacketPlayerListHeaderFooter = new SPacketPlayerListHeaderFooter();
/* 878 */         sPacketPlayerListHeaderFooter.readPacketData(buffer);
/*     */         break;
/*     */       
/*     */       case 72:
/* 882 */         sPacketResourcePackSend = new SPacketResourcePackSend();
/* 883 */         sPacketResourcePackSend.readPacketData(buffer);
/*     */         break;
/*     */     } 
/*     */     
/* 887 */     buffer.skipBytes(buffer.readableBytes());
/* 888 */     return (Packet<?>)sPacketResourcePackSend; }
/*     */ 
/*     */   
/*     */   private SPacketChunkData parse_1_8_Chunk(IChunk buffer) {
/* 892 */     SPacketChunkData pack = new SPacketChunkData();
/*     */     try {
/* 894 */       PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
/* 895 */       ChunkWrapper.writeChunk_107(buffer, buf);
/* 896 */       buf.resetReaderIndex();
/* 897 */       IChunk chunk = ChunkWrapper.readChunk_107(buf);
/* 898 */       PacketBuffer buf2 = new PacketBuffer(Unpooled.buffer());
/* 899 */       ChunkWrapper.writeChunk_110(buf2, chunk);
/* 900 */       buf2.resetReaderIndex();
/* 901 */       pack.readPacketData(buf2);
/*     */     }
/* 903 */     catch (Exception e) {
/* 904 */       e.printStackTrace();
/*     */     } 
/* 906 */     return pack;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\PacketWrapper47.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.CPacketEncryptionResponse;
/*     */ import net.minecraft.network.login.client.CPacketLoginStart;
/*     */ import net.minecraft.network.login.server.SPacketDisconnect;
/*     */ import net.minecraft.network.login.server.SPacketEnableCompression;
/*     */ import net.minecraft.network.login.server.SPacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.SPacketLoginSuccess;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ import net.minecraft.network.play.client.CPacketClickWindow;
/*     */ import net.minecraft.network.play.client.CPacketClientSettings;
/*     */ import net.minecraft.network.play.client.CPacketClientStatus;
/*     */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.network.play.client.CPacketEnchantItem;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketInput;
/*     */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*     */ import net.minecraft.network.play.client.CPacketPlaceRecipe;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerAbilities;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketRecipeInfo;
/*     */ import net.minecraft.network.play.client.CPacketResourcePackStatus;
/*     */ import net.minecraft.network.play.client.CPacketSeenAdvancements;
/*     */ import net.minecraft.network.play.client.CPacketSpectate;
/*     */ import net.minecraft.network.play.client.CPacketSteerBoat;
/*     */ import net.minecraft.network.play.client.CPacketTabComplete;
/*     */ import net.minecraft.network.play.client.CPacketUpdateSign;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.client.CPacketVehicleMove;
/*     */ import net.minecraft.network.play.server.SPacketAdvancementInfo;
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
/*     */ import net.minecraft.network.play.server.SPacketCooldown;
/*     */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*     */ import net.minecraft.network.play.server.SPacketCustomSound;
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
/*     */ import net.minecraft.network.play.server.SPacketMaps;
/*     */ import net.minecraft.network.play.server.SPacketMoveVehicle;
/*     */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*     */ import net.minecraft.network.play.server.SPacketParticles;
/*     */ import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
/*     */ import net.minecraft.network.play.server.SPacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.network.play.server.SPacketRecipeBook;
/*     */ import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
/*     */ import net.minecraft.network.play.server.SPacketResourcePackSend;
/*     */ import net.minecraft.network.play.server.SPacketRespawn;
/*     */ import net.minecraft.network.play.server.SPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
/*     */ import net.minecraft.network.play.server.SPacketServerDifficulty;
/*     */ import net.minecraft.network.play.server.SPacketSetExperience;
/*     */ import net.minecraft.network.play.server.SPacketSetPassengers;
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
/*     */ import net.minecraft.network.play.server.SPacketUnloadChunk;
/*     */ import net.minecraft.network.play.server.SPacketUpdateBossInfo;
/*     */ import net.minecraft.network.play.server.SPacketUpdateHealth;
/*     */ import net.minecraft.network.play.server.SPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.network.play.server.SPacketUseBed;
/*     */ import net.minecraft.network.play.server.SPacketWindowItems;
/*     */ import net.minecraft.network.play.server.SPacketWindowProperty;
/*     */ import net.minecraft.network.play.server.SPacketWorldBorder;
/*     */ import net.minecraft.network.status.client.CPacketPing;
/*     */ import net.minecraft.network.status.client.CPacketServerQuery;
/*     */ import net.minecraft.network.status.server.SPacketPong;
/*     */ import net.minecraft.network.status.server.SPacketServerInfo;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public enum EnumConnectionState {
/* 129 */   HANDSHAKING(-1)
/*     */   {
/*     */     EnumConnectionState(int $anonymous0) {
/* 132 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C00Handshake.class);
/*     */     }
/*     */   },
/* 135 */   PLAY(0)
/*     */   {
/*     */     EnumConnectionState(int $anonymous0) {
/* 138 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnObject.class);
/* 139 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnExperienceOrb.class);
/* 140 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnGlobalEntity.class);
/* 141 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnMob.class);
/* 142 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnPainting.class);
/* 143 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnPlayer.class);
/* 144 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketAnimation.class);
/* 145 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketStatistics.class);
/* 146 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketBlockBreakAnim.class);
/* 147 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUpdateTileEntity.class);
/* 148 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketBlockAction.class);
/* 149 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketBlockChange.class);
/* 150 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUpdateBossInfo.class);
/* 151 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketServerDifficulty.class);
/* 152 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketTabComplete.class);
/* 153 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketChat.class);
/* 154 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketMultiBlockChange.class);
/* 155 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketConfirmTransaction.class);
/* 156 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCloseWindow.class);
/* 157 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketOpenWindow.class);
/* 158 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketWindowItems.class);
/* 159 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketWindowProperty.class);
/* 160 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSetSlot.class);
/* 161 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCooldown.class);
/* 162 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCustomPayload.class);
/* 163 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCustomSound.class);
/* 164 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketDisconnect.class);
/* 165 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityStatus.class);
/* 166 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketExplosion.class);
/* 167 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUnloadChunk.class);
/* 168 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketChangeGameState.class);
/* 169 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketKeepAlive.class);
/* 170 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketChunkData.class);
/* 171 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEffect.class);
/* 172 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketParticles.class);
/* 173 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketJoinGame.class);
/* 174 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketMaps.class);
/* 175 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntity.class);
/* 176 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntity.S15PacketEntityRelMove.class);
/* 177 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntity.S17PacketEntityLookMove.class);
/* 178 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntity.S16PacketEntityLook.class);
/* 179 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketMoveVehicle.class);
/* 180 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSignEditorOpen.class);
/* 181 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPlaceGhostRecipe.class);
/* 182 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPlayerAbilities.class);
/* 183 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCombatEvent.class);
/* 184 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPlayerListItem.class);
/* 185 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPlayerPosLook.class);
/* 186 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUseBed.class);
/* 187 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketRecipeBook.class);
/* 188 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketDestroyEntities.class);
/* 189 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketRemoveEntityEffect.class);
/* 190 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketResourcePackSend.class);
/* 191 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketRespawn.class);
/* 192 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityHeadLook.class);
/* 193 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSelectAdvancementsTab.class);
/* 194 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketWorldBorder.class);
/* 195 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCamera.class);
/* 196 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketHeldItemChange.class);
/* 197 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketDisplayObjective.class);
/* 198 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityMetadata.class);
/* 199 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityAttach.class);
/* 200 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityVelocity.class);
/* 201 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityEquipment.class);
/* 202 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSetExperience.class);
/* 203 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUpdateHealth.class);
/* 204 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketScoreboardObjective.class);
/* 205 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSetPassengers.class);
/* 206 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketTeams.class);
/* 207 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketUpdateScore.class);
/* 208 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSpawnPosition.class);
/* 209 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketTimeUpdate.class);
/* 210 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketTitle.class);
/* 211 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketSoundEffect.class);
/* 212 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPlayerListHeaderFooter.class);
/* 213 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketCollectItem.class);
/* 214 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityTeleport.class);
/* 215 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketAdvancementInfo.class);
/* 216 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityProperties.class);
/* 217 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEntityEffect.class);
/* 218 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketConfirmTeleport.class);
/* 219 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketTabComplete.class);
/* 220 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketChatMessage.class);
/* 221 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketClientStatus.class);
/* 222 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketClientSettings.class);
/* 223 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketConfirmTransaction.class);
/* 224 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketEnchantItem.class);
/* 225 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketClickWindow.class);
/* 226 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketCloseWindow.class);
/* 227 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketCustomPayload.class);
/* 228 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketUseEntity.class);
/* 229 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketKeepAlive.class);
/* 230 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayer.class);
/* 231 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayer.Position.class);
/* 232 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayer.PositionRotation.class);
/* 233 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayer.Rotation.class);
/* 234 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketVehicleMove.class);
/* 235 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketSteerBoat.class);
/* 236 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlaceRecipe.class);
/* 237 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayerAbilities.class);
/* 238 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayerDigging.class);
/* 239 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketEntityAction.class);
/* 240 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketInput.class);
/* 241 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketRecipeInfo.class);
/* 242 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketResourcePackStatus.class);
/* 243 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketSeenAdvancements.class);
/* 244 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketHeldItemChange.class);
/* 245 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketCreativeInventoryAction.class);
/* 246 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketUpdateSign.class);
/* 247 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketAnimation.class);
/* 248 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketSpectate.class);
/* 249 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayerTryUseItemOnBlock.class);
/* 250 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPlayerTryUseItem.class);
/*     */     }
/*     */   },
/* 253 */   STATUS(1)
/*     */   {
/*     */     EnumConnectionState(int $anonymous0) {
/* 256 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketServerQuery.class);
/* 257 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketServerInfo.class);
/* 258 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketPing.class);
/* 259 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketPong.class);
/*     */     }
/*     */   },
/* 262 */   LOGIN(2)
/*     */   {
/*     */     EnumConnectionState(int $anonymous0) {
/* 265 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketDisconnect.class);
/* 266 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEncryptionRequest.class);
/* 267 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketLoginSuccess.class);
/* 268 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)SPacketEnableCompression.class);
/* 269 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketLoginStart.class);
/* 270 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)CPacketEncryptionResponse.class);
/*     */     } };
/*     */   
/*     */   static {
/* 274 */     STATES_BY_ID = new EnumConnectionState[4];
/* 275 */     STATES_BY_CLASS = Maps.newHashMap();
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
/*     */     byte b;
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
/*     */     int i;
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
/*     */     EnumConnectionState[] arrayOfEnumConnectionState;
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
/* 334 */     for (i = (arrayOfEnumConnectionState = values()).length, b = 0; b < i; ) { EnumConnectionState enumconnectionstate = arrayOfEnumConnectionState[b];
/*     */       
/* 336 */       int j = enumconnectionstate.getId();
/*     */       
/* 338 */       if (j < -1 || j > 2)
/*     */       {
/* 340 */         throw new Error("Invalid protocol ID " + Integer.toString(j));
/*     */       }
/*     */       
/* 343 */       STATES_BY_ID[j - -1] = enumconnectionstate;
/*     */       
/* 345 */       for (EnumPacketDirection enumpacketdirection : enumconnectionstate.directionMaps.keySet()) {
/*     */         
/* 347 */         for (Class<? extends Packet<?>> oclass : (Iterable<Class<? extends Packet<?>>>)((BiMap)enumconnectionstate.directionMaps.get(enumpacketdirection)).values()) {
/*     */           
/* 349 */           if (STATES_BY_CLASS.containsKey(oclass) && STATES_BY_CLASS.get(oclass) != enumconnectionstate)
/*     */           {
/* 351 */             throw new Error("Packet " + oclass + " is already assigned to protocol " + STATES_BY_CLASS.get(oclass) + " - can't reassign to " + enumconnectionstate);
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 356 */             oclass.newInstance();
/*     */           }
/* 358 */           catch (Throwable var10) {
/*     */             
/* 360 */             throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
/*     */           } 
/*     */           
/* 363 */           STATES_BY_CLASS.put(oclass, enumconnectionstate);
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static final EnumConnectionState[] STATES_BY_ID;
/*     */   private static final Map<Class<? extends Packet<?>>, EnumConnectionState> STATES_BY_CLASS;
/*     */   private final int id;
/*     */   private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet<?>>>> directionMaps;
/*     */   
/*     */   EnumConnectionState(int protocolId) {
/*     */     this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
/*     */     this.id = protocolId;
/*     */   }
/*     */   
/*     */   protected EnumConnectionState registerPacket(EnumPacketDirection direction, Class<? extends Packet<?>> packetClass) {
/*     */     HashBiMap hashBiMap;
/*     */     BiMap<Integer, Class<? extends Packet<?>>> bimap = this.directionMaps.get(direction);
/*     */     if (bimap == null) {
/*     */       hashBiMap = HashBiMap.create();
/*     */       this.directionMaps.put(direction, hashBiMap);
/*     */     } 
/*     */     if (hashBiMap.containsValue(packetClass)) {
/*     */       String s = direction + " packet " + packetClass + " is already known to ID " + hashBiMap.inverse().get(packetClass);
/*     */       LogManager.getLogger().fatal(s);
/*     */       throw new IllegalArgumentException(s);
/*     */     } 
/*     */     hashBiMap.put(Integer.valueOf(hashBiMap.size()), packetClass);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public Integer getPacketId(EnumPacketDirection direction, Packet<?> packetIn) throws Exception {
/*     */     return (Integer)((BiMap)this.directionMaps.get(direction)).inverse().get(packetIn.getClass());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Packet<?> getPacket(EnumPacketDirection direction, int packetId) throws InstantiationException, IllegalAccessException {
/*     */     Class<? extends Packet<?>> oclass = (Class<? extends Packet<?>>)((BiMap)this.directionMaps.get(direction)).get(Integer.valueOf(packetId));
/*     */     return (oclass == null) ? null : oclass.newInstance();
/*     */   }
/*     */   
/*     */   public int getId() {
/*     */     return this.id;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getById(int stateId) {
/*     */     return (stateId >= -1 && stateId <= 2) ? STATES_BY_ID[stateId - -1] : null;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getFromPacket(Packet<?> packetIn) {
/*     */     return STATES_BY_CLASS.get(packetIn.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\EnumConnectionState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
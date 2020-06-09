package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;

public interface INetHandlerPlayClient extends INetHandler {
  void handleSpawnObject(SPacketSpawnObject paramSPacketSpawnObject);
  
  void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb paramSPacketSpawnExperienceOrb);
  
  void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity paramSPacketSpawnGlobalEntity);
  
  void handleSpawnMob(SPacketSpawnMob paramSPacketSpawnMob);
  
  void handleScoreboardObjective(SPacketScoreboardObjective paramSPacketScoreboardObjective);
  
  void handleSpawnPainting(SPacketSpawnPainting paramSPacketSpawnPainting);
  
  void handleSpawnPlayer(SPacketSpawnPlayer paramSPacketSpawnPlayer);
  
  void handleAnimation(SPacketAnimation paramSPacketAnimation);
  
  void handleStatistics(SPacketStatistics paramSPacketStatistics);
  
  void func_191980_a(SPacketRecipeBook paramSPacketRecipeBook);
  
  void handleBlockBreakAnim(SPacketBlockBreakAnim paramSPacketBlockBreakAnim);
  
  void handleSignEditorOpen(SPacketSignEditorOpen paramSPacketSignEditorOpen);
  
  void handleUpdateTileEntity(SPacketUpdateTileEntity paramSPacketUpdateTileEntity);
  
  void handleBlockAction(SPacketBlockAction paramSPacketBlockAction);
  
  void handleBlockChange(SPacketBlockChange paramSPacketBlockChange);
  
  void handleChat(SPacketChat paramSPacketChat);
  
  void handleTabComplete(SPacketTabComplete paramSPacketTabComplete);
  
  void handleMultiBlockChange(SPacketMultiBlockChange paramSPacketMultiBlockChange);
  
  void handleMaps(SPacketMaps paramSPacketMaps);
  
  void handleConfirmTransaction(SPacketConfirmTransaction paramSPacketConfirmTransaction);
  
  void handleCloseWindow(SPacketCloseWindow paramSPacketCloseWindow);
  
  void handleWindowItems(SPacketWindowItems paramSPacketWindowItems);
  
  void handleOpenWindow(SPacketOpenWindow paramSPacketOpenWindow);
  
  void handleWindowProperty(SPacketWindowProperty paramSPacketWindowProperty);
  
  void handleSetSlot(SPacketSetSlot paramSPacketSetSlot);
  
  void handleCustomPayload(SPacketCustomPayload paramSPacketCustomPayload);
  
  void handleDisconnect(SPacketDisconnect paramSPacketDisconnect);
  
  void handleUseBed(SPacketUseBed paramSPacketUseBed);
  
  void handleEntityStatus(SPacketEntityStatus paramSPacketEntityStatus);
  
  void handleEntityAttach(SPacketEntityAttach paramSPacketEntityAttach);
  
  void handleSetPassengers(SPacketSetPassengers paramSPacketSetPassengers);
  
  void handleExplosion(SPacketExplosion paramSPacketExplosion);
  
  void handleChangeGameState(SPacketChangeGameState paramSPacketChangeGameState);
  
  void handleKeepAlive(SPacketKeepAlive paramSPacketKeepAlive);
  
  void handleChunkData(SPacketChunkData paramSPacketChunkData);
  
  void processChunkUnload(SPacketUnloadChunk paramSPacketUnloadChunk);
  
  void handleEffect(SPacketEffect paramSPacketEffect);
  
  void handleJoinGame(SPacketJoinGame paramSPacketJoinGame);
  
  void handleEntityMovement(SPacketEntity paramSPacketEntity);
  
  void handlePlayerPosLook(SPacketPlayerPosLook paramSPacketPlayerPosLook);
  
  void handleParticles(SPacketParticles paramSPacketParticles);
  
  void handlePlayerAbilities(SPacketPlayerAbilities paramSPacketPlayerAbilities);
  
  void handlePlayerListItem(SPacketPlayerListItem paramSPacketPlayerListItem);
  
  void handleDestroyEntities(SPacketDestroyEntities paramSPacketDestroyEntities);
  
  void handleRemoveEntityEffect(SPacketRemoveEntityEffect paramSPacketRemoveEntityEffect);
  
  void handleRespawn(SPacketRespawn paramSPacketRespawn);
  
  void handleEntityHeadLook(SPacketEntityHeadLook paramSPacketEntityHeadLook);
  
  void handleHeldItemChange(SPacketHeldItemChange paramSPacketHeldItemChange);
  
  void handleDisplayObjective(SPacketDisplayObjective paramSPacketDisplayObjective);
  
  void handleEntityMetadata(SPacketEntityMetadata paramSPacketEntityMetadata);
  
  void handleEntityVelocity(SPacketEntityVelocity paramSPacketEntityVelocity);
  
  void handleEntityEquipment(SPacketEntityEquipment paramSPacketEntityEquipment);
  
  void handleSetExperience(SPacketSetExperience paramSPacketSetExperience);
  
  void handleUpdateHealth(SPacketUpdateHealth paramSPacketUpdateHealth);
  
  void handleTeams(SPacketTeams paramSPacketTeams);
  
  void handleUpdateScore(SPacketUpdateScore paramSPacketUpdateScore);
  
  void handleSpawnPosition(SPacketSpawnPosition paramSPacketSpawnPosition);
  
  void handleTimeUpdate(SPacketTimeUpdate paramSPacketTimeUpdate);
  
  void handleSoundEffect(SPacketSoundEffect paramSPacketSoundEffect);
  
  void handleCustomSound(SPacketCustomSound paramSPacketCustomSound);
  
  void handleCollectItem(SPacketCollectItem paramSPacketCollectItem);
  
  void handleEntityTeleport(SPacketEntityTeleport paramSPacketEntityTeleport);
  
  void handleEntityProperties(SPacketEntityProperties paramSPacketEntityProperties);
  
  void handleEntityEffect(SPacketEntityEffect paramSPacketEntityEffect);
  
  void handleCombatEvent(SPacketCombatEvent paramSPacketCombatEvent);
  
  void handleServerDifficulty(SPacketServerDifficulty paramSPacketServerDifficulty);
  
  void handleCamera(SPacketCamera paramSPacketCamera);
  
  void handleWorldBorder(SPacketWorldBorder paramSPacketWorldBorder);
  
  void handleTitle(SPacketTitle paramSPacketTitle);
  
  void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter paramSPacketPlayerListHeaderFooter);
  
  void handleResourcePack(SPacketResourcePackSend paramSPacketResourcePackSend);
  
  void handleUpdateEntityNBT(SPacketUpdateBossInfo paramSPacketUpdateBossInfo);
  
  void handleCooldown(SPacketCooldown paramSPacketCooldown);
  
  void handleMoveVehicle(SPacketMoveVehicle paramSPacketMoveVehicle);
  
  void func_191981_a(SPacketAdvancementInfo paramSPacketAdvancementInfo);
  
  void func_194022_a(SPacketSelectAdvancementsTab paramSPacketSelectAdvancementsTab);
  
  void func_194307_a(SPacketPlaceGhostRecipe paramSPacketPlaceGhostRecipe);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\INetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
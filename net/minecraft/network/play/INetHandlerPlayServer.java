package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerBlockPlacement;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRecipePlacement;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;

public interface INetHandlerPlayServer extends INetHandler {
  void handleAnimation(CPacketAnimation paramCPacketAnimation);
  
  void processChatMessage(CPacketChatMessage paramCPacketChatMessage);
  
  void processTabComplete(CPacketTabComplete paramCPacketTabComplete);
  
  void processClientStatus(CPacketClientStatus paramCPacketClientStatus);
  
  void processClientSettings(CPacketClientSettings paramCPacketClientSettings);
  
  void processConfirmTransaction(CPacketConfirmTransaction paramCPacketConfirmTransaction);
  
  void processEnchantItem(CPacketEnchantItem paramCPacketEnchantItem);
  
  void processClickWindow(CPacketClickWindow paramCPacketClickWindow);
  
  void func_194308_a(CPacketPlaceRecipe paramCPacketPlaceRecipe);
  
  void processCloseWindow(CPacketCloseWindow paramCPacketCloseWindow);
  
  void processCustomPayload(CPacketCustomPayload paramCPacketCustomPayload);
  
  void processUseEntity(CPacketUseEntity paramCPacketUseEntity);
  
  void processKeepAlive(CPacketKeepAlive paramCPacketKeepAlive);
  
  void processPlayer(CPacketPlayer paramCPacketPlayer);
  
  void processPlayerAbilities(CPacketPlayerAbilities paramCPacketPlayerAbilities);
  
  void processPlayerDigging(CPacketPlayerDigging paramCPacketPlayerDigging);
  
  void processEntityAction(CPacketEntityAction paramCPacketEntityAction);
  
  void processInput(CPacketInput paramCPacketInput);
  
  void processHeldItemChange(CPacketHeldItemChange paramCPacketHeldItemChange);
  
  void processCreativeInventoryAction(CPacketCreativeInventoryAction paramCPacketCreativeInventoryAction);
  
  void processUpdateSign(CPacketUpdateSign paramCPacketUpdateSign);
  
  void processRightClickBlock(CPacketPlayerTryUseItemOnBlock paramCPacketPlayerTryUseItemOnBlock);
  
  void processPlayerBlockPlacement(CPacketPlayerTryUseItem paramCPacketPlayerTryUseItem);
  
  void processPlayerBlockPlacement(CPacketPlayerBlockPlacement paramCPacketPlayerBlockPlacement);
  
  void handleSpectate(CPacketSpectate paramCPacketSpectate);
  
  void handleResourcePackStatus(CPacketResourcePackStatus paramCPacketResourcePackStatus);
  
  void processSteerBoat(CPacketSteerBoat paramCPacketSteerBoat);
  
  void processVehicleMove(CPacketVehicleMove paramCPacketVehicleMove);
  
  void processConfirmTeleport(CPacketConfirmTeleport paramCPacketConfirmTeleport);
  
  void func_191984_a(CPacketRecipeInfo paramCPacketRecipeInfo);
  
  void func_194027_a(CPacketSeenAdvancements paramCPacketSeenAdvancements);
  
  void func_191985_a(CPacketRecipePlacement paramCPacketRecipePlacement);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\INetHandlerPlayServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
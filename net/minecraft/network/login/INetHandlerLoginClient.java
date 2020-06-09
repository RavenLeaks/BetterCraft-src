package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.SPacketDisconnect;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;

public interface INetHandlerLoginClient extends INetHandler {
  void handleEncryptionRequest(SPacketEncryptionRequest paramSPacketEncryptionRequest);
  
  void handleLoginSuccess(SPacketLoginSuccess paramSPacketLoginSuccess);
  
  void handleDisconnect(SPacketDisconnect paramSPacketDisconnect);
  
  void handleEnableCompression(SPacketEnableCompression paramSPacketEnableCompression);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\login\INetHandlerLoginClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
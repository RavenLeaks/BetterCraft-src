/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ import net.minecraft.network.handshake.client.C00Handshake;
/*    */ import net.minecraft.network.login.server.SPacketDisconnect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
/*    */   private final MinecraftServer server;
/*    */   private final NetworkManager networkManager;
/*    */   
/*    */   public NetHandlerHandshakeTCP(MinecraftServer serverIn, NetworkManager netManager) {
/* 19 */     this.server = serverIn;
/* 20 */     this.networkManager = netManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processHandshake(C00Handshake packetIn) {
/* 30 */     switch (packetIn.getRequestedState()) {
/*    */       
/*    */       case LOGIN:
/* 33 */         this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
/*    */         
/* 35 */         if (packetIn.getProtocolVersion() > 340) {
/*    */           
/* 37 */           TextComponentTranslation textComponentTranslation = new TextComponentTranslation("multiplayer.disconnect.outdated_server", new Object[] { "1.12.2" });
/* 38 */           this.networkManager.sendPacket((Packet)new SPacketDisconnect((ITextComponent)textComponentTranslation));
/* 39 */           this.networkManager.closeChannel((ITextComponent)textComponentTranslation);
/*    */         }
/* 41 */         else if (packetIn.getProtocolVersion() < 340) {
/*    */           
/* 43 */           TextComponentTranslation textComponentTranslation = new TextComponentTranslation("multiplayer.disconnect.outdated_client", new Object[] { "1.12.2" });
/* 44 */           this.networkManager.sendPacket((Packet)new SPacketDisconnect((ITextComponent)textComponentTranslation));
/* 45 */           this.networkManager.closeChannel((ITextComponent)textComponentTranslation);
/*    */         }
/*    */         else {
/*    */           
/* 49 */           this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginServer(this.server, this.networkManager));
/*    */         } 
/*    */         return;
/*    */ 
/*    */       
/*    */       case STATUS:
/* 55 */         this.networkManager.setConnectionState(EnumConnectionState.STATUS);
/* 56 */         this.networkManager.setNetHandler((INetHandler)new NetHandlerStatusServer(this.server, this.networkManager));
/*    */         return;
/*    */     } 
/*    */     
/* 60 */     throw new UnsupportedOperationException("Invalid intention " + packetIn.getRequestedState());
/*    */   }
/*    */   
/*    */   public void onDisconnect(ITextComponent reason) {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\network\NetHandlerHandshakeTCP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
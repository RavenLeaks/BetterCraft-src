/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ import net.minecraft.network.status.client.CPacketPing;
/*    */ import net.minecraft.network.status.client.CPacketServerQuery;
/*    */ import net.minecraft.network.status.server.SPacketPong;
/*    */ import net.minecraft.network.status.server.SPacketServerInfo;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class NetHandlerStatusServer implements INetHandlerStatusServer {
/* 15 */   private static final ITextComponent EXIT_MESSAGE = (ITextComponent)new TextComponentString("Status request has been handled.");
/*    */   
/*    */   private final MinecraftServer server;
/*    */   private final NetworkManager networkManager;
/*    */   private boolean handled;
/*    */   
/*    */   public NetHandlerStatusServer(MinecraftServer serverIn, NetworkManager netManager) {
/* 22 */     this.server = serverIn;
/* 23 */     this.networkManager = netManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisconnect(ITextComponent reason) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processServerQuery(CPacketServerQuery packetIn) {
/* 35 */     if (this.handled) {
/*    */       
/* 37 */       this.networkManager.closeChannel(EXIT_MESSAGE);
/*    */     }
/*    */     else {
/*    */       
/* 41 */       this.handled = true;
/* 42 */       this.networkManager.sendPacket((Packet)new SPacketServerInfo(this.server.getServerStatusResponse()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void processPing(CPacketPing packetIn) {
/* 48 */     this.networkManager.sendPacket((Packet)new SPacketPong(packetIn.getClientTime()));
/* 49 */     this.networkManager.closeChannel(EXIT_MESSAGE);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\network\NetHandlerStatusServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
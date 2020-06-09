/*    */ package net.minecraft.network.status.server;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.ServerStatusResponse;
/*    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*    */ import net.minecraft.util.EnumTypeAdapterFactory;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.Style;
/*    */ 
/*    */ public class SPacketServerInfo implements Packet<INetHandlerStatusClient> {
/* 17 */   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ServerStatusResponse.Version.class, new ServerStatusResponse.Version.Serializer()).registerTypeAdapter(ServerStatusResponse.Players.class, new ServerStatusResponse.Players.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
/*    */   
/*    */   private ServerStatusResponse response;
/*    */ 
/*    */   
/*    */   public SPacketServerInfo() {}
/*    */ 
/*    */   
/*    */   public SPacketServerInfo(ServerStatusResponse responseIn) {
/* 26 */     this.response = responseIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.response = (ServerStatusResponse)JsonUtils.gsonDeserialize(GSON, buf.readStringFromBuffer(32767), ServerStatusResponse.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeString(GSON.toJson(this.response));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusClient handler) {
/* 50 */     handler.handleServerInfo(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerStatusResponse getResponse() {
/* 55 */     return this.response;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\status\server\SPacketServerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
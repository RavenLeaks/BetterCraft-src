/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ 
/*    */ 
/*    */ public class CPacketClientSettings
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String lang;
/*    */   public int view;
/*    */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*    */   private boolean enableColors;
/*    */   private int modelPartFlags;
/*    */   private EnumHandSide mainHand;
/*    */   
/*    */   public CPacketClientSettings() {}
/*    */   
/*    */   public CPacketClientSettings(String langIn, int renderDistanceIn, EntityPlayer.EnumChatVisibility chatVisibilityIn, boolean chatColorsIn, int modelPartsIn, EnumHandSide mainHandIn) {
/* 25 */     this.lang = langIn;
/* 26 */     this.view = renderDistanceIn;
/* 27 */     this.chatVisibility = chatVisibilityIn;
/* 28 */     this.enableColors = chatColorsIn;
/* 29 */     this.modelPartFlags = modelPartsIn;
/* 30 */     this.mainHand = mainHandIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.lang = buf.readStringFromBuffer(16);
/* 39 */     this.view = buf.readByte();
/* 40 */     this.chatVisibility = (EntityPlayer.EnumChatVisibility)buf.readEnumValue(EntityPlayer.EnumChatVisibility.class);
/* 41 */     this.enableColors = buf.readBoolean();
/* 42 */     this.modelPartFlags = buf.readUnsignedByte();
/* 43 */     this.mainHand = (EnumHandSide)buf.readEnumValue(EnumHandSide.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 51 */     buf.writeString(this.lang);
/* 52 */     buf.writeByte(this.view);
/* 53 */     buf.writeEnumValue((Enum)this.chatVisibility);
/* 54 */     buf.writeBoolean(this.enableColors);
/* 55 */     buf.writeByte(this.modelPartFlags);
/* 56 */     buf.writeEnumValue((Enum)this.mainHand);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 64 */     handler.processClientSettings(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLang() {
/* 69 */     return this.lang;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 74 */     return this.chatVisibility;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isColorsEnabled() {
/* 79 */     return this.enableColors;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getModelPartFlags() {
/* 84 */     return this.modelPartFlags;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumHandSide getMainHand() {
/* 89 */     return this.mainHand;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketClientSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
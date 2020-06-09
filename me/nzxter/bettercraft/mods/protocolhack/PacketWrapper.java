/*    */ package me.nzxter.bettercraft.mods.protocolhack;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public abstract class PacketWrapper
/*    */ {
/*    */   private ProtocolHack protocol;
/*    */   
/*    */   public PacketWrapper(ProtocolHack p) {
/* 11 */     this.protocol = p;
/*    */   }
/*    */   
/*    */   public abstract void writePacket(Packet<?> paramPacket, PacketBuffer paramPacketBuffer) throws Exception;
/*    */   
/*    */   public abstract Packet<?> readPacket(int paramInt, PacketBuffer paramPacketBuffer) throws Exception;
/*    */   
/*    */   public ProtocolHack getProtocol() {
/* 19 */     return this.protocol;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\PacketWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
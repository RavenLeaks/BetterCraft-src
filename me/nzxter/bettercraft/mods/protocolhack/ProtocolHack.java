/*    */ package me.nzxter.bettercraft.mods.protocolhack;
/*    */ 
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_10.PacketWrapper210;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_11_2.PacketWrapper316;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_13.PacketWrapper393;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_7.PacketWrapper5;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.PacketWrapper47;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_9.PacketWrapper110;
/*    */ 
/*    */ public enum ProtocolHack
/*    */ {
/* 12 */   PROTOCOL_5(5, "1.7.10", (PacketWrapper)new PacketWrapper5()),
/* 13 */   PROTOCOL_47(47, "1.8.*", (PacketWrapper)new PacketWrapper47()),
/* 14 */   PROTOCOL_107(107, "1.9", (PacketWrapper)new PacketWrapper110()),
/* 15 */   PROTOCOL_108(108, "1.9.1", (PacketWrapper)new PacketWrapper110()),
/* 16 */   PROTOCOL_109(109, "1.9.2", (PacketWrapper)new PacketWrapper110()),
/* 17 */   PROTOCOL_110(110, "1.9.4", (PacketWrapper)new PacketWrapper110()),
/* 18 */   PROTOCOL_210(210, "1.10.*", (PacketWrapper)new PacketWrapper210()),
/* 19 */   PROTOCOL_316(316, "1.11.*", (PacketWrapper)new PacketWrapper316()),
/* 20 */   PROTOCOL_340(340, "1.12.*", null),
/* 21 */   PROTOCOL_393(393, "1.13", (PacketWrapper)new PacketWrapper393());
/*    */   
/*    */   private final int id;
/*    */   private final String name;
/*    */   private final PacketWrapper packetWrapper;
/*    */   
/*    */   ProtocolHack(int id, String name, PacketWrapper wrapper) {
/* 28 */     this.id = id;
/* 29 */     this.name = name;
/* 30 */     this.packetWrapper = wrapper;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 34 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */   
/*    */   public PacketWrapper getPacketWrapper() {
/* 42 */     return this.packetWrapper;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\ProtocolHack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
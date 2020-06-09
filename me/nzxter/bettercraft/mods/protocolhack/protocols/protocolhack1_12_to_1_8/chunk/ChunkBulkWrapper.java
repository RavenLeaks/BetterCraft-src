/*    */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*    */ 
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class ChunkBulkWrapper
/*    */ {
/*    */   public static List<PacketBuffer> transformMapChunkBulk(PacketBuffer packet) throws Exception {
/* 11 */     ArrayList<PacketBuffer> packets = new ArrayList<>();
/* 12 */     PacketBuffer wrapper = packet;
/* 13 */     boolean skyLight = wrapper.readBoolean();
/* 14 */     int count2 = wrapper.readVarIntFromBuffer();
/* 15 */     ChunkBulkSection[] metas = new ChunkBulkSection[count2];
/* 16 */     for (int i = 0; i < count2; i++)
/* 17 */       metas[i] = ChunkBulkSection.read(wrapper, skyLight);  byte b; int j;
/*    */     ChunkBulkSection[] arrayOfChunkBulkSection1;
/* 19 */     for (j = (arrayOfChunkBulkSection1 = metas).length, b = 0; b < j; ) { ChunkBulkSection meta = arrayOfChunkBulkSection1[b];
/* 20 */       byte[] byteArray = new byte[meta.getLength()];
/* 21 */       wrapper.readBytes(byteArray);
/* 22 */       PacketBuffer chunkPacket = new PacketBuffer(Unpooled.buffer());
/* 23 */       chunkPacket.writeInt(meta.getX());
/* 24 */       chunkPacket.writeInt(meta.getZ());
/* 25 */       chunkPacket.writeBoolean(true);
/* 26 */       chunkPacket.writeShort(meta.getBitMask());
/* 27 */       chunkPacket.writeVarIntToBuffer(meta.getLength());
/* 28 */       chunkPacket.writeBytes(byteArray);
/* 29 */       packets.add(chunkPacket); b++; }
/*    */     
/* 31 */     return packets;
/*    */   }
/*    */   
/*    */   public boolean isFiltered(Class<?> packet) {
/* 35 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isPacketLevel() {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private static class ChunkBulkSection
/*    */   {
/*    */     private int x;
/*    */     
/*    */     private int z;
/*    */     private int bitMask;
/*    */     private int length;
/*    */     private byte[] data;
/*    */     
/*    */     public static ChunkBulkSection read(PacketBuffer wrapper, boolean skylight) throws Exception {
/* 53 */       ChunkBulkSection bulkSection = new ChunkBulkSection();
/* 54 */       bulkSection.setX(wrapper.readInt());
/* 55 */       bulkSection.setZ(wrapper.readInt());
/* 56 */       bulkSection.setBitMask(wrapper.readUnsignedShort());
/* 57 */       int bitCount = Integer.bitCount(bulkSection.getBitMask());
/* 58 */       bulkSection.setLength(bitCount * 10240 + (skylight ? (bitCount * 2048) : 0) + 256);
/* 59 */       return bulkSection;
/*    */     }
/*    */     
/*    */     public int getX() {
/* 63 */       return this.x;
/*    */     }
/*    */     
/*    */     public void setX(int x) {
/* 67 */       this.x = x;
/*    */     }
/*    */     
/*    */     public int getZ() {
/* 71 */       return this.z;
/*    */     }
/*    */     
/*    */     public void setZ(int z) {
/* 75 */       this.z = z;
/*    */     }
/*    */     
/*    */     public int getBitMask() {
/* 79 */       return this.bitMask;
/*    */     }
/*    */     
/*    */     public void setBitMask(int bitMask) {
/* 83 */       this.bitMask = bitMask;
/*    */     }
/*    */     
/*    */     public int getLength() {
/* 87 */       return this.length;
/*    */     }
/*    */     
/*    */     public void setLength(int length) {
/* 91 */       this.length = length;
/*    */     }
/*    */     
/*    */     public byte[] getData() {
/* 95 */       return this.data;
/*    */     }
/*    */     
/*    */     public void setData(byte[] data2) {
/* 99 */       this.data = data2;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\ChunkBulkWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
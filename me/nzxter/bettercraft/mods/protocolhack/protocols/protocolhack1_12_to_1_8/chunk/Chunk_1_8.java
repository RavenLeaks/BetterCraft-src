/*    */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ public class Chunk_1_8
/*    */   extends BaseChunk
/*    */ {
/*    */   private boolean unloadPacket = false;
/*    */   
/*    */   public Chunk_1_8(int x, int z, boolean groundUp, int bitmask, ChunkSection[] sections, int[] biomeData, List<NBTTagCompound> blockEntities) {
/* 14 */     super(x, z, groundUp, bitmask, sections, biomeData, blockEntities);
/*    */   }
/*    */   
/*    */   public Chunk_1_8(int x, int z) {
/* 18 */     this(x, z, true, 0, new ChunkSection[16], (int[])null, new ArrayList<>());
/* 19 */     this.unloadPacket = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasBiomeData() {
/* 24 */     return (this.biomeData != null && this.groundUp);
/*    */   }
/*    */   
/*    */   public boolean isUnloadPacket() {
/* 28 */     return this.unloadPacket;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\Chunk_1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
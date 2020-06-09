/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPacketUpdateTileEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   private int metadata;
/*    */   private NBTTagCompound nbt;
/*    */   
/*    */   public SPacketUpdateTileEntity() {}
/*    */   
/*    */   public SPacketUpdateTileEntity(BlockPos blockPosIn, int metadataIn, NBTTagCompound compoundIn) {
/* 24 */     this.blockPos = blockPosIn;
/* 25 */     this.metadata = metadataIn;
/* 26 */     this.nbt = compoundIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.blockPos = buf.readBlockPos();
/* 35 */     this.metadata = buf.readUnsignedByte();
/* 36 */     this.nbt = buf.readNBTTagCompoundFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeBlockPos(this.blockPos);
/* 45 */     buf.writeByte((byte)this.metadata);
/* 46 */     buf.writeNBTTagCompoundToBuffer(this.nbt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 54 */     handler.handleUpdateTileEntity(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 59 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTileEntityType() {
/* 64 */     return this.metadata;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getNbtCompound() {
/* 69 */     return this.nbt;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketUpdateTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.item.EntityPainting;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class SPacketSpawnPainting
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public int entityID;
/*    */   public UUID uniqueId;
/*    */   public BlockPos position;
/*    */   public EnumFacing facing;
/*    */   public String title;
/*    */   
/*    */   public SPacketSpawnPainting() {}
/*    */   
/*    */   public SPacketSpawnPainting(EntityPainting painting) {
/* 26 */     this.entityID = painting.getEntityId();
/* 27 */     this.uniqueId = painting.getUniqueID();
/* 28 */     this.position = painting.getHangingPosition();
/* 29 */     this.facing = painting.facingDirection;
/* 30 */     this.title = painting.art.title;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.entityID = buf.readVarIntFromBuffer();
/* 39 */     this.uniqueId = buf.readUuid();
/* 40 */     this.title = buf.readStringFromBuffer(EntityPainting.EnumArt.MAX_NAME_LENGTH);
/* 41 */     this.position = buf.readBlockPos();
/* 42 */     this.facing = EnumFacing.getHorizontal(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 50 */     buf.writeVarIntToBuffer(this.entityID);
/* 51 */     buf.writeUuid(this.uniqueId);
/* 52 */     buf.writeString(this.title);
/* 53 */     buf.writeBlockPos(this.position);
/* 54 */     buf.writeByte(this.facing.getHorizontalIndex());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 62 */     handler.handleSpawnPainting(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 67 */     return this.entityID;
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUniqueId() {
/* 72 */     return this.uniqueId;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 77 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumFacing getFacing() {
/* 82 */     return this.facing;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 87 */     return this.title;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
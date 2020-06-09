/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class CPacketPlayerTryUseItemOnBlock
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public BlockPos position;
/*    */   public EnumFacing placedBlockDirection;
/*    */   public EnumHand hand;
/*    */   public float facingX;
/*    */   public float facingY;
/*    */   public float facingZ;
/*    */   
/*    */   public CPacketPlayerTryUseItemOnBlock() {}
/*    */   
/*    */   public CPacketPlayerTryUseItemOnBlock(BlockPos posIn, EnumFacing placedBlockDirectionIn, EnumHand handIn, float facingXIn, float facingYIn, float facingZIn) {
/* 26 */     this.position = posIn;
/* 27 */     this.placedBlockDirection = placedBlockDirectionIn;
/* 28 */     this.hand = handIn;
/* 29 */     this.facingX = facingXIn;
/* 30 */     this.facingY = facingYIn;
/* 31 */     this.facingZ = facingZIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 39 */     this.position = buf.readBlockPos();
/* 40 */     this.placedBlockDirection = (EnumFacing)buf.readEnumValue(EnumFacing.class);
/* 41 */     this.hand = (EnumHand)buf.readEnumValue(EnumHand.class);
/* 42 */     this.facingX = buf.readFloat();
/* 43 */     this.facingY = buf.readFloat();
/* 44 */     this.facingZ = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeBlockPos(this.position);
/* 53 */     buf.writeEnumValue((Enum)this.placedBlockDirection);
/* 54 */     buf.writeEnumValue((Enum)this.hand);
/* 55 */     buf.writeFloat(this.facingX);
/* 56 */     buf.writeFloat(this.facingY);
/* 57 */     buf.writeFloat(this.facingZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 65 */     handler.processRightClickBlock(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 70 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumFacing getDirection() {
/* 75 */     return this.placedBlockDirection;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumHand getHand() {
/* 80 */     return this.hand;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFacingX() {
/* 85 */     return this.facingX;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFacingY() {
/* 90 */     return this.facingY;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFacingZ() {
/* 95 */     return this.facingZ;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketPlayerTryUseItemOnBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class CPacketTabComplete
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String message;
/*    */   private boolean hasTargetBlock;
/*    */   @Nullable
/*    */   private BlockPos targetBlock;
/*    */   
/*    */   public CPacketTabComplete() {}
/*    */   
/*    */   public CPacketTabComplete(String messageIn, @Nullable BlockPos targetBlockIn, boolean hasTargetBlockIn) {
/* 24 */     this.message = messageIn;
/* 25 */     this.targetBlock = targetBlockIn;
/* 26 */     this.hasTargetBlock = hasTargetBlockIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.message = buf.readStringFromBuffer(32767);
/* 35 */     this.hasTargetBlock = buf.readBoolean();
/* 36 */     boolean flag = buf.readBoolean();
/*    */     
/* 38 */     if (flag)
/*    */     {
/* 40 */       this.targetBlock = buf.readBlockPos();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeString(StringUtils.substring(this.message, 0, 32767));
/* 50 */     buf.writeBoolean(this.hasTargetBlock);
/* 51 */     boolean flag = (this.targetBlock != null);
/* 52 */     buf.writeBoolean(flag);
/*    */     
/* 54 */     if (flag)
/*    */     {
/* 56 */       buf.writeBlockPos(this.targetBlock);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 65 */     handler.processTabComplete(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 70 */     return this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockPos getTargetBlock() {
/* 76 */     return this.targetBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTargetBlock() {
/* 81 */     return this.hasTargetBlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
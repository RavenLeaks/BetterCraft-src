/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketEntityAction
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public int entityID;
/*    */   private Action action;
/*    */   private int auxData;
/*    */   
/*    */   public CPacketEntityAction() {}
/*    */   
/*    */   public CPacketEntityAction(Entity entityIn, Action actionIn) {
/* 21 */     this(entityIn, actionIn, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public CPacketEntityAction(Entity entityIn, Action actionIn, int auxDataIn) {
/* 26 */     this.entityID = entityIn.getEntityId();
/* 27 */     this.action = actionIn;
/* 28 */     this.auxData = auxDataIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     this.entityID = buf.readVarIntFromBuffer();
/* 37 */     this.action = (Action)buf.readEnumValue(Action.class);
/* 38 */     this.auxData = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 46 */     buf.writeVarIntToBuffer(this.entityID);
/* 47 */     buf.writeEnumValue(this.action);
/* 48 */     buf.writeVarIntToBuffer(this.auxData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 56 */     handler.processEntityAction(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 61 */     return this.action;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAuxData() {
/* 66 */     return this.auxData;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 71 */     START_SNEAKING,
/* 72 */     STOP_SNEAKING,
/* 73 */     STOP_SLEEPING,
/* 74 */     START_SPRINTING,
/* 75 */     STOP_SPRINTING,
/* 76 */     START_RIDING_JUMP,
/* 77 */     STOP_RIDING_JUMP,
/* 78 */     OPEN_INVENTORY,
/* 79 */     START_FALL_FLYING;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketEntityAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
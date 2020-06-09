/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class SPacketRemoveEntityEffect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private Potion effectId;
/*    */   
/*    */   public SPacketRemoveEntityEffect() {}
/*    */   
/*    */   public SPacketRemoveEntityEffect(int entityIdIn, Potion potionIn) {
/* 23 */     this.entityId = entityIdIn;
/* 24 */     this.effectId = potionIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.entityId = buf.readVarIntFromBuffer();
/* 33 */     this.effectId = Potion.getPotionById(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeVarIntToBuffer(this.entityId);
/* 42 */     buf.writeByte(Potion.getIdFromPotion(this.effectId));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 50 */     handler.handleRemoveEntityEffect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity(World worldIn) {
/* 56 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Potion getPotion() {
/* 62 */     return this.effectId;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketRemoveEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
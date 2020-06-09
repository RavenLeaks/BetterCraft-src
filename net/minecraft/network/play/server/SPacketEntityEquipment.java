/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class SPacketEntityEquipment implements Packet<INetHandlerPlayClient> {
/*    */   private int entityID;
/*    */   private EntityEquipmentSlot equipmentSlot;
/* 14 */   private ItemStack itemStack = ItemStack.field_190927_a;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SPacketEntityEquipment(int entityIdIn, EntityEquipmentSlot equipmentSlotIn, ItemStack itemStackIn) {
/* 22 */     this.entityID = entityIdIn;
/* 23 */     this.equipmentSlot = equipmentSlotIn;
/* 24 */     this.itemStack = itemStackIn.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.entityID = buf.readVarIntFromBuffer();
/* 33 */     this.equipmentSlot = (EntityEquipmentSlot)buf.readEnumValue(EntityEquipmentSlot.class);
/* 34 */     this.itemStack = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeVarIntToBuffer(this.entityID);
/* 43 */     buf.writeEnumValue((Enum)this.equipmentSlot);
/* 44 */     buf.writeItemStackToBuffer(this.itemStack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 52 */     handler.handleEntityEquipment(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 57 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 62 */     return this.entityID;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEquipmentSlot getEquipmentSlot() {
/* 67 */     return this.equipmentSlot;
/*    */   }
/*    */   
/*    */   public SPacketEntityEquipment() {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityEquipment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
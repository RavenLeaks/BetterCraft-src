/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CPacketClickWindow
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   private int windowId;
/*     */   private int slotId;
/*     */   private int usedButton;
/*     */   private short actionNumber;
/*  25 */   private ItemStack clickedItem = ItemStack.field_190927_a;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClickType mode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CPacketClickWindow(int windowIdIn, int slotIdIn, int usedButtonIn, ClickType modeIn, ItemStack clickedItemIn, short actionNumberIn) {
/*  36 */     this.windowId = windowIdIn;
/*  37 */     this.slotId = slotIdIn;
/*  38 */     this.usedButton = usedButtonIn;
/*  39 */     this.clickedItem = clickedItemIn.copy();
/*  40 */     this.actionNumber = actionNumberIn;
/*  41 */     this.mode = modeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  49 */     handler.processClickWindow(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  57 */     this.windowId = buf.readByte();
/*  58 */     this.slotId = buf.readShort();
/*  59 */     this.usedButton = buf.readByte();
/*  60 */     this.actionNumber = buf.readShort();
/*  61 */     this.mode = (ClickType)buf.readEnumValue(ClickType.class);
/*  62 */     this.clickedItem = buf.readItemStackFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  70 */     buf.writeByte(this.windowId);
/*  71 */     buf.writeShort(this.slotId);
/*  72 */     buf.writeByte(this.usedButton);
/*  73 */     buf.writeShort(this.actionNumber);
/*  74 */     buf.writeEnumValue((Enum)this.mode);
/*  75 */     buf.writeItemStackToBuffer(this.clickedItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWindowId() {
/*  80 */     return this.windowId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotId() {
/*  85 */     return this.slotId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUsedButton() {
/*  90 */     return this.usedButton;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getActionNumber() {
/*  95 */     return this.actionNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getClickedItem() {
/* 100 */     return this.clickedItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClickType getClickType() {
/* 105 */     return this.mode;
/*     */   }
/*     */   
/*     */   public CPacketClickWindow() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketClickWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
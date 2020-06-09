/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class SPacketOpenWindow
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int windowId;
/*     */   private String inventoryType;
/*     */   private ITextComponent windowTitle;
/*     */   private int slotCount;
/*     */   private int entityId;
/*     */   
/*     */   public SPacketOpenWindow() {}
/*     */   
/*     */   public SPacketOpenWindow(int windowIdIn, String inventoryTypeIn, ITextComponent windowTitleIn) {
/*  23 */     this(windowIdIn, inventoryTypeIn, windowTitleIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketOpenWindow(int windowIdIn, String inventoryTypeIn, ITextComponent windowTitleIn, int slotCountIn) {
/*  28 */     this.windowId = windowIdIn;
/*  29 */     this.inventoryType = inventoryTypeIn;
/*  30 */     this.windowTitle = windowTitleIn;
/*  31 */     this.slotCount = slotCountIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketOpenWindow(int windowIdIn, String inventoryTypeIn, ITextComponent windowTitleIn, int slotCountIn, int entityIdIn) {
/*  36 */     this(windowIdIn, inventoryTypeIn, windowTitleIn, slotCountIn);
/*  37 */     this.entityId = entityIdIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  45 */     handler.handleOpenWindow(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  53 */     this.windowId = buf.readUnsignedByte();
/*  54 */     this.inventoryType = buf.readStringFromBuffer(32);
/*  55 */     this.windowTitle = buf.readTextComponent();
/*  56 */     this.slotCount = buf.readUnsignedByte();
/*     */     
/*  58 */     if (this.inventoryType.equals("EntityHorse"))
/*     */     {
/*  60 */       this.entityId = buf.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  69 */     buf.writeByte(this.windowId);
/*  70 */     buf.writeString(this.inventoryType);
/*  71 */     buf.writeTextComponent(this.windowTitle);
/*  72 */     buf.writeByte(this.slotCount);
/*     */     
/*  74 */     if (this.inventoryType.equals("EntityHorse"))
/*     */     {
/*  76 */       buf.writeInt(this.entityId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWindowId() {
/*  82 */     return this.windowId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiId() {
/*  87 */     return this.inventoryType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getWindowTitle() {
/*  92 */     return this.windowTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotCount() {
/*  97 */     return this.slotCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/* 102 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSlots() {
/* 107 */     return (this.slotCount > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketOpenWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerInfo;
/*     */ 
/*     */ public class ServerSelectionList
/*     */   extends GuiListExtended {
/*     */   private final GuiMultiplayer owner;
/*  12 */   private final List<ServerListEntryNormal> serverListInternet = Lists.newArrayList();
/*  13 */   private final List<ServerListEntryLanDetected> serverListLan = Lists.newArrayList();
/*  14 */   private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
/*  15 */   private int selectedSlotIndex = -1;
/*     */ 
/*     */   
/*     */   public ServerSelectionList(GuiMultiplayer ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  19 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  20 */     this.owner = ownerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/*  28 */     if (index < this.serverListInternet.size())
/*     */     {
/*  30 */       return this.serverListInternet.get(index);
/*     */     }
/*     */ 
/*     */     
/*  34 */     index -= this.serverListInternet.size();
/*     */     
/*  36 */     if (index == 0)
/*     */     {
/*  38 */       return this.lanScanEntry;
/*     */     }
/*     */ 
/*     */     
/*  42 */     index--;
/*  43 */     return this.serverListLan.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  50 */     return this.serverListInternet.size() + 1 + this.serverListLan.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedSlotIndex(int selectedSlotIndexIn) {
/*  55 */     this.selectedSlotIndex = selectedSlotIndexIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  63 */     return (slotIndex == this.selectedSlotIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelected() {
/*  68 */     return this.selectedSlotIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateOnlineServers(ServerList p_148195_1_) {
/*  73 */     this.serverListInternet.clear();
/*     */     
/*  75 */     for (int i = 0; i < p_148195_1_.countServers(); i++)
/*     */     {
/*  77 */       this.serverListInternet.add(new ServerListEntryNormal(this.owner, p_148195_1_.getServerData(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNetworkServers(List<LanServerInfo> p_148194_1_) {
/*  83 */     this.serverListLan.clear();
/*     */     
/*  85 */     for (LanServerInfo lanserverinfo : p_148194_1_)
/*     */     {
/*  87 */       this.serverListLan.add(new ServerListEntryLanDetected(this.owner, lanserverinfo));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  93 */     return super.getScrollBarX() + 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 101 */     return super.getListWidth() + 85;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ServerSelectionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
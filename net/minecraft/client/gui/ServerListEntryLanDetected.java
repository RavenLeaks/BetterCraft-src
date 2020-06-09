/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.network.LanServerInfo;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class ServerListEntryLanDetected
/*    */   implements GuiListExtended.IGuiListEntry
/*    */ {
/*    */   private final GuiMultiplayer screen;
/*    */   protected final Minecraft mc;
/*    */   protected final LanServerInfo serverData;
/*    */   private long lastClickTime;
/*    */   
/*    */   protected ServerListEntryLanDetected(GuiMultiplayer p_i47141_1_, LanServerInfo p_i47141_2_) {
/* 16 */     this.screen = p_i47141_1_;
/* 17 */     this.serverData = p_i47141_2_;
/* 18 */     this.mc = Minecraft.getMinecraft();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/* 23 */     this.mc.fontRendererObj.drawString(I18n.format("lanServer.title", new Object[0]), p_192634_2_ + 32 + 3, p_192634_3_ + 1, 16777215);
/* 24 */     this.mc.fontRendererObj.drawString(this.serverData.getServerMotd(), p_192634_2_ + 32 + 3, p_192634_3_ + 12, 8421504);
/*    */     
/* 26 */     if (this.mc.gameSettings.hideServerAddress) {
/*    */       
/* 28 */       this.mc.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 3158064);
/*    */     }
/*    */     else {
/*    */       
/* 32 */       this.mc.fontRendererObj.drawString(this.serverData.getServerIpPort(), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 3158064);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 42 */     this.screen.selectServer(slotIndex);
/*    */     
/* 44 */     if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
/*    */     {
/* 46 */       this.screen.connectToSelected();
/*    */     }
/*    */     
/* 49 */     this.lastClickTime = Minecraft.getSystemTime();
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public LanServerInfo getServerData() {
/* 66 */     return this.serverData;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ServerListEntryLanDetected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
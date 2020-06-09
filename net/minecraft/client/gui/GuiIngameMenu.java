/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.nzxter.bettercraft.mods.altmanager.GuiAltManager;
/*     */ import me.nzxter.bettercraft.mods.gui.GuiClient;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.realms.RealmsBridge;
/*     */ import wdl.WDLHooks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiIngameMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   private int saveStep;
/*     */   private int visibleTime;
/*     */   
/*     */   public void initGui() {
/*  24 */     this.saveStep = 0;
/*  25 */     this.buttonList.clear();
/*  26 */     int i = -16;
/*  27 */     int j = 98;
/*  28 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + -16, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     
/*  30 */     if (!this.mc.isIntegratedServerRunning())
/*     */     {
/*  32 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  36 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("Serverliste", new Object[0])));
/*  37 */     this.buttonList.add(new GuiButton(69, this.width / 2 + 2, this.height / 4 + 48 + -16, 98, 20, I18n.format("Tools", new Object[0])));
/*     */ 
/*     */     
/*  40 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + -16, 98, 20, I18n.format("menu.options", new Object[0])));
/*  41 */     GuiButton guibutton = addButton(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 72 + i, I18n.format("menu.shareToLan", new Object[0])));
/*  42 */     guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */     
/*  44 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + -16, 98, 20, I18n.format("Altmanager", new Object[0])));
/*  45 */     this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 96 + -16, 98, 20, I18n.format("gui.stats", new Object[0])));
/*     */ 
/*     */     
/*  48 */     WDLHooks.injectWDLButtons(this, this.buttonList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     boolean flag, flag1;
/*  60 */     WDLHooks.handleWDLButtonClick(this, button);
/*     */ 
/*     */     
/*  63 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  66 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/*  70 */         flag = this.mc.isIntegratedServerRunning();
/*  71 */         flag1 = this.mc.isConnectedToRealms();
/*  72 */         button.enabled = false;
/*  73 */         this.mc.world.sendQuittingDisconnectingPacket();
/*  74 */         this.mc.loadWorld(null);
/*     */         
/*  76 */         if (flag) {
/*     */           
/*  78 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*     */         }
/*  80 */         else if (flag1) {
/*     */           
/*  82 */           RealmsBridge realmsbridge = new RealmsBridge();
/*  83 */           realmsbridge.switchToRealms(new GuiMainMenu());
/*     */         }
/*     */         else {
/*     */           
/*  87 */           this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/*  97 */         this.mc.displayGuiScreen(new GuiMultiplayer(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 69:
/* 104 */         this.mc.displayGuiScreen((GuiScreen)new GuiClient(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 111 */         this.mc.displayGuiScreen((GuiScreen)new GuiAltManager(this));
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 116 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.player.getStatFileWriter()));
/*     */       case 7:
/*     */         break;
/*     */     } 
/* 120 */     this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 129 */     super.updateScreen();
/* 130 */     this.visibleTime++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 138 */     drawDefaultBackground();
/* 139 */     drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
/* 140 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiIngameMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenServerList
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen lastScreen;
/*     */   private final ServerData serverData;
/*     */   private GuiTextField ipEdit;
/*     */   
/*     */   public GuiScreenServerList(GuiScreen p_i1031_1_, ServerData p_i1031_2_) {
/*  16 */     this.lastScreen = p_i1031_1_;
/*  17 */     this.serverData = p_i1031_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  25 */     this.ipEdit.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     Keyboard.enableRepeatEvents(true);
/*  35 */     this.buttonList.clear();
/*  36 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
/*  37 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  38 */     this.ipEdit = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
/*  39 */     this.ipEdit.setMaxStringLength(128);
/*  40 */     this.ipEdit.setFocused(true);
/*  41 */     this.ipEdit.setText(this.mc.gameSettings.lastServer);
/*  42 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.ipEdit.getText().isEmpty() && (this.ipEdit.getText().split(":")).length > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  50 */     Keyboard.enableRepeatEvents(false);
/*  51 */     this.mc.gameSettings.lastServer = this.ipEdit.getText();
/*  52 */     this.mc.gameSettings.saveOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  60 */     if (button.enabled)
/*     */     {
/*  62 */       if (button.id == 1) {
/*     */         
/*  64 */         this.lastScreen.confirmClicked(false, 0);
/*     */       }
/*  66 */       else if (button.id == 0) {
/*     */         
/*  68 */         this.serverData.serverIP = this.ipEdit.getText();
/*  69 */         this.lastScreen.confirmClicked(true, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  80 */     if (this.ipEdit.textboxKeyTyped(typedChar, keyCode)) {
/*     */       
/*  82 */       ((GuiButton)this.buttonList.get(0)).enabled = (!this.ipEdit.getText().isEmpty() && (this.ipEdit.getText().split(":")).length > 0);
/*     */     }
/*  84 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/*  86 */       actionPerformed(this.buttonList.get(0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  95 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  96 */     this.ipEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 104 */     drawDefaultBackground();
/* 105 */     drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
/* 106 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100, 10526880);
/* 107 */     this.ipEdit.drawTextBox();
/* 108 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenServerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
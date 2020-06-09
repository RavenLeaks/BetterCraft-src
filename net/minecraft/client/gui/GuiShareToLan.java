/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class GuiShareToLan
/*     */   extends GuiScreen {
/*     */   private final GuiScreen lastScreen;
/*     */   private GuiButton allowCheatsButton;
/*     */   private GuiButton gameModeButton;
/*  15 */   private String gameMode = "survival";
/*     */   
/*     */   private boolean allowCheats;
/*     */   
/*     */   public GuiShareToLan(GuiScreen p_i1055_1_) {
/*  20 */     this.lastScreen = p_i1055_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  29 */     this.buttonList.clear();
/*  30 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
/*  31 */     this.buttonList.add(new GuiButton(102, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  32 */     this.gameModeButton = addButton(new GuiButton(104, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  33 */     this.allowCheatsButton = addButton(new GuiButton(103, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  34 */     updateDisplayNames();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateDisplayNames() {
/*  39 */     this.gameModeButton.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/*  40 */     this.allowCheatsButton.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*     */     
/*  42 */     if (this.allowCheats) {
/*     */       
/*  44 */       this.allowCheatsButton.displayString = String.valueOf(this.allowCheatsButton.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  48 */       this.allowCheatsButton.displayString = String.valueOf(this.allowCheatsButton.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  57 */     if (button.id == 102) {
/*     */       
/*  59 */       this.mc.displayGuiScreen(this.lastScreen);
/*     */     }
/*  61 */     else if (button.id == 104) {
/*     */       
/*  63 */       if ("spectator".equals(this.gameMode)) {
/*     */         
/*  65 */         this.gameMode = "creative";
/*     */       }
/*  67 */       else if ("creative".equals(this.gameMode)) {
/*     */         
/*  69 */         this.gameMode = "adventure";
/*     */       }
/*  71 */       else if ("adventure".equals(this.gameMode)) {
/*     */         
/*  73 */         this.gameMode = "survival";
/*     */       }
/*     */       else {
/*     */         
/*  77 */         this.gameMode = "spectator";
/*     */       } 
/*     */       
/*  80 */       updateDisplayNames();
/*     */     }
/*  82 */     else if (button.id == 103) {
/*     */       
/*  84 */       this.allowCheats = !this.allowCheats;
/*  85 */       updateDisplayNames();
/*     */     }
/*  87 */     else if (button.id == 101) {
/*     */       TextComponentString textComponentString;
/*  89 */       this.mc.displayGuiScreen(null);
/*  90 */       String s = this.mc.getIntegratedServer().shareToLAN(GameType.getByName(this.gameMode), this.allowCheats);
/*     */ 
/*     */       
/*  93 */       if (s != null) {
/*     */         
/*  95 */         TextComponentTranslation textComponentTranslation = new TextComponentTranslation("commands.publish.started", new Object[] { s });
/*     */       }
/*     */       else {
/*     */         
/*  99 */         textComponentString = new TextComponentString("commands.publish.failed");
/*     */       } 
/*     */       
/* 102 */       this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)textComponentString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 111 */     drawDefaultBackground();
/* 112 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), this.width / 2, 50, 16777215);
/* 113 */     drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), this.width / 2, 82, 16777215);
/* 114 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiShareToLan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
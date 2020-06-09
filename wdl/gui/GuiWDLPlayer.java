/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDL;
/*     */ 
/*     */ public class GuiWDLPlayer
/*     */   extends GuiScreen {
/*     */   private String title;
/*     */   private GuiScreen parent;
/*     */   private GuiButton healthBtn;
/*     */   private GuiButton hungerBtn;
/*     */   private GuiButton playerPosBtn;
/*     */   private GuiButton pickPosBtn;
/*     */   private boolean showPosFields = false;
/*     */   private GuiNumericTextField posX;
/*     */   private GuiNumericTextField posY;
/*     */   private GuiNumericTextField posZ;
/*     */   private int posTextY;
/*     */   
/*     */   public GuiWDLPlayer(GuiScreen var1) {
/*  24 */     this.parent = var1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  32 */     this.buttonList.clear();
/*  33 */     this.title = I18n.format("wdl.gui.player.title", new Object[] {
/*  34 */           WDL.baseFolderName.replace('@', ':') });
/*  35 */     int y = this.height / 4 - 15;
/*  36 */     this.healthBtn = new GuiButton(1, this.width / 2 - 100, y, getHealthText());
/*  37 */     this.buttonList.add(this.healthBtn);
/*  38 */     y += 22;
/*  39 */     this.hungerBtn = new GuiButton(2, this.width / 2 - 100, y, getHungerText());
/*  40 */     this.buttonList.add(this.hungerBtn);
/*  41 */     y += 22;
/*  42 */     this.playerPosBtn = new GuiButton(3, this.width / 2 - 100, y, 
/*  43 */         getPlayerPosText());
/*  44 */     this.buttonList.add(this.playerPosBtn);
/*  45 */     y += 22;
/*  46 */     this.posTextY = y + 4;
/*  47 */     this.posX = new GuiNumericTextField(40, this.fontRendererObj, 
/*  48 */         this.width / 2 - 87, y, 50, 16);
/*  49 */     this.posY = new GuiNumericTextField(41, this.fontRendererObj, 
/*  50 */         this.width / 2 - 19, y, 50, 16);
/*  51 */     this.posZ = new GuiNumericTextField(42, this.fontRendererObj, 
/*  52 */         this.width / 2 + 48, y, 50, 16);
/*  53 */     this.posX.setText(WDL.worldProps.getProperty("PlayerX"));
/*  54 */     this.posY.setText(WDL.worldProps.getProperty("PlayerY"));
/*  55 */     this.posZ.setText(WDL.worldProps.getProperty("PlayerZ"));
/*  56 */     this.posX.setMaxStringLength(7);
/*  57 */     this.posY.setMaxStringLength(7);
/*  58 */     this.posZ.setMaxStringLength(7);
/*  59 */     y += 18;
/*  60 */     this.pickPosBtn = new GuiButton(4, this.width / 2 - 0, y, 100, 20, 
/*  61 */         I18n.format("wdl.gui.player.setPositionToCurrentPosition", new Object[0]));
/*  62 */     this.buttonList.add(this.pickPosBtn);
/*     */     
/*  64 */     upadatePlayerPosVisibility();
/*     */     
/*  66 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/*  67 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton var1) {
/*  76 */     if (var1.enabled) {
/*  77 */       if (var1.id == 1) {
/*  78 */         cycleHealth();
/*  79 */       } else if (var1.id == 2) {
/*  80 */         cycleHunger();
/*  81 */       } else if (var1.id == 3) {
/*  82 */         cyclePlayerPos();
/*  83 */       } else if (var1.id == 4) {
/*  84 */         setPlayerPosToPlayerPosition();
/*  85 */       } else if (var1.id == 100) {
/*  86 */         this.mc.displayGuiScreen(this.parent);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  93 */     if (this.showPosFields) {
/*  94 */       WDL.worldProps.setProperty("PlayerX", this.posX.getText());
/*  95 */       WDL.worldProps.setProperty("PlayerY", this.posY.getText());
/*  96 */       WDL.worldProps.setProperty("PlayerZ", this.posZ.getText());
/*     */     } 
/*     */     
/*  99 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 108 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 110 */     if (this.showPosFields) {
/* 111 */       this.posX.mouseClicked(mouseX, mouseY, mouseButton);
/* 112 */       this.posY.mouseClicked(mouseX, mouseY, mouseButton);
/* 113 */       this.posZ.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 123 */     super.keyTyped(typedChar, keyCode);
/* 124 */     this.posX.textboxKeyTyped(typedChar, keyCode);
/* 125 */     this.posY.textboxKeyTyped(typedChar, keyCode);
/* 126 */     this.posZ.textboxKeyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 134 */     this.posX.updateCursorCounter();
/* 135 */     this.posY.updateCursorCounter();
/* 136 */     this.posZ.updateCursorCounter();
/* 137 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 145 */     Utils.drawListBackground(23, 32, 0, 0, this.height, this.width);
/*     */     
/* 147 */     drawCenteredString(this.fontRendererObj, this.title, 
/* 148 */         this.width / 2, 8, 16777215);
/*     */     
/* 150 */     String tooltip = null;
/*     */     
/* 152 */     if (this.showPosFields) {
/* 153 */       drawString(this.fontRendererObj, "X:", this.width / 2 - 99, 
/* 154 */           this.posTextY, 16777215);
/* 155 */       drawString(this.fontRendererObj, "Y:", this.width / 2 - 31, 
/* 156 */           this.posTextY, 16777215);
/* 157 */       drawString(this.fontRendererObj, "Z:", this.width / 2 + 37, 
/* 158 */           this.posTextY, 16777215);
/* 159 */       this.posX.drawTextBox();
/* 160 */       this.posY.drawTextBox();
/* 161 */       this.posZ.drawTextBox();
/*     */       
/* 163 */       if (Utils.isMouseOverTextBox(mouseX, mouseY, this.posX)) {
/* 164 */         tooltip = I18n.format("wdl.gui.player.positionTextBox.description", new Object[] { "X" });
/* 165 */       } else if (Utils.isMouseOverTextBox(mouseX, mouseY, this.posY)) {
/* 166 */         tooltip = I18n.format("wdl.gui.player.positionTextBox.description", new Object[] { "Y" });
/* 167 */       } else if (Utils.isMouseOverTextBox(mouseX, mouseY, this.posZ)) {
/* 168 */         tooltip = I18n.format("wdl.gui.player.positionTextBox.description", new Object[] { "Z" });
/*     */       } 
/*     */       
/* 171 */       if (this.pickPosBtn.isMouseOver()) {
/* 172 */         tooltip = I18n.format("wdl.gui.player.setPositionToCurrentPosition.description", new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     if (this.healthBtn.isMouseOver()) {
/* 177 */       tooltip = I18n.format("wdl.gui.player.health.description", new Object[0]);
/*     */     }
/* 179 */     if (this.hungerBtn.isMouseOver()) {
/* 180 */       tooltip = I18n.format("wdl.gui.player.hunger.description", new Object[0]);
/*     */     }
/* 182 */     if (this.playerPosBtn.isMouseOver()) {
/* 183 */       tooltip = I18n.format("wdl.gui.player.position.description", new Object[0]);
/*     */     }
/*     */     
/* 186 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 188 */     if (tooltip != null) {
/* 189 */       Utils.drawGuiInfoBox(tooltip, this.width, this.height, 48);
/*     */     }
/*     */   }
/*     */   
/*     */   private void cycleHealth() {
/* 194 */     String prop = WDL.baseProps.getProperty("PlayerHealth");
/*     */     
/* 196 */     if (prop.equals("keep")) {
/* 197 */       WDL.baseProps.setProperty("PlayerHealth", "20");
/* 198 */     } else if (prop.equals("20")) {
/* 199 */       WDL.baseProps.setProperty("PlayerHealth", "keep");
/*     */     } 
/*     */     
/* 202 */     this.healthBtn.displayString = getHealthText();
/*     */   }
/*     */   
/*     */   private void cycleHunger() {
/* 206 */     String prop = WDL.baseProps.getProperty("PlayerFood");
/*     */     
/* 208 */     if (prop.equals("keep")) {
/* 209 */       WDL.baseProps.setProperty("PlayerFood", "20");
/* 210 */     } else if (prop.equals("20")) {
/* 211 */       WDL.baseProps.setProperty("PlayerFood", "keep");
/*     */     } 
/*     */     
/* 214 */     this.hungerBtn.displayString = getHungerText();
/*     */   }
/*     */   
/*     */   private void cyclePlayerPos() {
/* 218 */     String prop = WDL.worldProps.getProperty("PlayerPos");
/*     */     
/* 220 */     if (prop.equals("keep")) {
/* 221 */       WDL.worldProps.setProperty("PlayerPos", "xyz");
/* 222 */     } else if (prop.equals("xyz")) {
/* 223 */       WDL.worldProps.setProperty("PlayerPos", "keep");
/*     */     } 
/*     */     
/* 226 */     this.playerPosBtn.displayString = getPlayerPosText();
/* 227 */     upadatePlayerPosVisibility();
/*     */   }
/*     */   
/*     */   private String getHealthText() {
/* 231 */     String result = I18n.format("wdl.gui.player.health." + 
/* 232 */         WDL.baseProps.getProperty("PlayerHealth"), new Object[0]);
/*     */     
/* 234 */     if (result.startsWith("wdl.gui.player.health."))
/*     */     {
/*     */ 
/*     */       
/* 238 */       result = I18n.format("wdl.gui.player.health.custom", new Object[] {
/* 239 */             WDL.baseProps.getProperty("PlayerHealth")
/*     */           });
/*     */     }
/* 242 */     return result;
/*     */   }
/*     */   
/*     */   private String getHungerText() {
/* 246 */     String result = I18n.format("wdl.gui.player.hunger." + 
/* 247 */         WDL.baseProps.getProperty("PlayerFood"), new Object[0]);
/*     */     
/* 249 */     if (result.startsWith("wdl.gui.player.hunger."))
/*     */     {
/*     */ 
/*     */       
/* 253 */       result = I18n.format("wdl.gui.player.hunger.custom", new Object[] {
/* 254 */             WDL.baseProps.getProperty("PlayerFood")
/*     */           });
/*     */     }
/* 257 */     return result;
/*     */   }
/*     */   
/*     */   private void upadatePlayerPosVisibility() {
/* 261 */     this.showPosFields = WDL.worldProps.getProperty("PlayerPos").equals("xyz");
/* 262 */     this.pickPosBtn.visible = this.showPosFields;
/*     */   }
/*     */   
/*     */   private String getPlayerPosText() {
/* 266 */     return I18n.format("wdl.gui.player.position." + 
/* 267 */         WDL.worldProps.getProperty("PlayerPos"), new Object[0]);
/*     */   }
/*     */   
/*     */   private void setPlayerPosToPlayerPosition() {
/* 271 */     this.posX.setValue((int)WDL.thePlayer.posX);
/* 272 */     this.posY.setValue((int)WDL.thePlayer.posY);
/* 273 */     this.posZ.setValue((int)WDL.thePlayer.posZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
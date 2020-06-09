/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDL;
/*     */ 
/*     */ public class GuiWDLWorld
/*     */   extends GuiScreen {
/*     */   private String title;
/*     */   private GuiScreen parent;
/*     */   private GuiButton allowCheatsBtn;
/*     */   private GuiButton gamemodeBtn;
/*     */   private GuiButton timeBtn;
/*     */   private GuiButton weatherBtn;
/*     */   private GuiButton spawnBtn;
/*     */   private GuiButton pickSpawnBtn;
/*     */   private boolean showSpawnFields = false;
/*     */   private GuiNumericTextField spawnX;
/*     */   private GuiNumericTextField spawnY;
/*     */   private GuiNumericTextField spawnZ;
/*     */   private int spawnTextY;
/*     */   
/*     */   public GuiWDLWorld(GuiScreen parent) {
/*  26 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     this.buttonList.clear();
/*  35 */     this.title = I18n.format("wdl.gui.world.title", new Object[] {
/*  36 */           WDL.baseFolderName.replace('@', ':')
/*     */         });
/*  38 */     int y = this.height / 4 - 15;
/*     */     
/*  40 */     this.gamemodeBtn = new GuiButton(1, this.width / 2 - 100, y, 
/*  41 */         getGamemodeText());
/*  42 */     this.buttonList.add(this.gamemodeBtn);
/*  43 */     y += 22;
/*  44 */     this.allowCheatsBtn = new GuiButton(6, this.width / 2 - 100, y, 
/*  45 */         getAllowCheatsText());
/*  46 */     this.buttonList.add(this.allowCheatsBtn);
/*  47 */     y += 22;
/*  48 */     this.timeBtn = new GuiButton(2, this.width / 2 - 100, y, 
/*  49 */         getTimeText());
/*  50 */     this.buttonList.add(this.timeBtn);
/*  51 */     y += 22;
/*  52 */     this.weatherBtn = new GuiButton(3, this.width / 2 - 100, y, 
/*  53 */         getWeatherText());
/*  54 */     this.buttonList.add(this.weatherBtn);
/*  55 */     y += 22;
/*  56 */     this.spawnBtn = new GuiButton(4, this.width / 2 - 100, y, 
/*  57 */         getSpawnText());
/*  58 */     this.buttonList.add(this.spawnBtn);
/*  59 */     y += 22;
/*  60 */     this.spawnTextY = y + 4;
/*  61 */     this.spawnX = new GuiNumericTextField(40, this.fontRendererObj, this.width / 2 - 87, 
/*  62 */         y, 50, 16);
/*  63 */     this.spawnY = new GuiNumericTextField(41, this.fontRendererObj, this.width / 2 - 19, 
/*  64 */         y, 50, 16);
/*  65 */     this.spawnZ = new GuiNumericTextField(42, this.fontRendererObj, this.width / 2 + 48, 
/*  66 */         y, 50, 16);
/*  67 */     this.spawnX.setText(WDL.worldProps.getProperty("SpawnX"));
/*  68 */     this.spawnY.setText(WDL.worldProps.getProperty("SpawnY"));
/*  69 */     this.spawnZ.setText(WDL.worldProps.getProperty("SpawnZ"));
/*  70 */     this.spawnX.setMaxStringLength(7);
/*  71 */     this.spawnY.setMaxStringLength(7);
/*  72 */     this.spawnZ.setMaxStringLength(7);
/*  73 */     y += 18;
/*  74 */     this.pickSpawnBtn = new GuiButton(5, this.width / 2, y, 100, 20, 
/*  75 */         I18n.format("wdl.gui.world.setSpawnToCurrentPosition", new Object[0]));
/*  76 */     this.buttonList.add(this.pickSpawnBtn);
/*     */     
/*  78 */     updateSpawnTextBoxVisibility();
/*     */     
/*  80 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/*  81 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  90 */     if (button.enabled) {
/*  91 */       if (button.id == 1) {
/*  92 */         cycleGamemode();
/*  93 */       } else if (button.id == 2) {
/*  94 */         cycleTime();
/*  95 */       } else if (button.id == 3) {
/*  96 */         cycleWeather();
/*  97 */       } else if (button.id == 4) {
/*  98 */         cycleSpawn();
/*  99 */       } else if (button.id == 5) {
/* 100 */         setSpawnToPlayerPosition();
/* 101 */       } else if (button.id == 6) {
/* 102 */         cycleAllowCheats();
/* 103 */       } else if (button.id == 100) {
/* 104 */         this.mc.displayGuiScreen(this.parent);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 111 */     if (this.showSpawnFields) {
/* 112 */       WDL.worldProps.setProperty("SpawnX", this.spawnX.getText());
/* 113 */       WDL.worldProps.setProperty("SpawnY", this.spawnY.getText());
/* 114 */       WDL.worldProps.setProperty("SpawnZ", this.spawnZ.getText());
/*     */     } 
/*     */     
/* 117 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 126 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 128 */     if (this.showSpawnFields) {
/* 129 */       this.spawnX.mouseClicked(mouseX, mouseY, mouseButton);
/* 130 */       this.spawnY.mouseClicked(mouseX, mouseY, mouseButton);
/* 131 */       this.spawnZ.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 141 */     super.keyTyped(typedChar, keyCode);
/* 142 */     this.spawnX.textboxKeyTyped(typedChar, keyCode);
/* 143 */     this.spawnY.textboxKeyTyped(typedChar, keyCode);
/* 144 */     this.spawnZ.textboxKeyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 152 */     this.spawnX.updateCursorCounter();
/* 153 */     this.spawnY.updateCursorCounter();
/* 154 */     this.spawnZ.updateCursorCounter();
/* 155 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 163 */     Utils.drawListBackground(23, 32, 0, 0, this.height, this.width);
/*     */     
/* 165 */     drawCenteredString(this.fontRendererObj, this.title, 
/* 166 */         this.width / 2, 8, 16777215);
/*     */     
/* 168 */     if (this.showSpawnFields) {
/* 169 */       drawString(this.fontRendererObj, "X:", this.width / 2 - 99, 
/* 170 */           this.spawnTextY, 16777215);
/* 171 */       drawString(this.fontRendererObj, "Y:", this.width / 2 - 31, 
/* 172 */           this.spawnTextY, 16777215);
/* 173 */       drawString(this.fontRendererObj, "Z:", this.width / 2 + 37, 
/* 174 */           this.spawnTextY, 16777215);
/* 175 */       this.spawnX.drawTextBox();
/* 176 */       this.spawnY.drawTextBox();
/* 177 */       this.spawnZ.drawTextBox();
/*     */     } 
/*     */     
/* 180 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 182 */     String tooltip = null;
/*     */     
/* 184 */     if (this.allowCheatsBtn.isMouseOver()) {
/* 185 */       tooltip = I18n.format("wdl.gui.world.allowCheats.description", new Object[0]);
/* 186 */     } else if (this.gamemodeBtn.isMouseOver()) {
/* 187 */       tooltip = I18n.format("wdl.gui.world.gamemode.description", new Object[0]);
/* 188 */     } else if (this.timeBtn.isMouseOver()) {
/* 189 */       tooltip = I18n.format("wdl.gui.world.time.description", new Object[0]);
/* 190 */     } else if (this.weatherBtn.isMouseOver()) {
/* 191 */       tooltip = I18n.format("wdl.gui.world.weather.description", new Object[0]);
/* 192 */     } else if (this.spawnBtn.isMouseOver()) {
/* 193 */       tooltip = I18n.format("wdl.gui.world.spawn.description", new Object[0]);
/* 194 */     } else if (this.pickSpawnBtn.isMouseOver()) {
/* 195 */       tooltip = I18n.format("wdl.gui.world.setSpawnToCurrentPosition.description", new Object[0]);
/*     */     } 
/*     */     
/* 198 */     if (this.showSpawnFields) {
/* 199 */       if (Utils.isMouseOverTextBox(mouseX, mouseY, this.spawnX)) {
/* 200 */         tooltip = I18n.format("wdl.gui.world.spawnPos.description", new Object[] { "X" });
/* 201 */       } else if (Utils.isMouseOverTextBox(mouseX, mouseY, this.spawnY)) {
/* 202 */         tooltip = I18n.format("wdl.gui.world.spawnPos.description", new Object[] { "Y" });
/* 203 */       } else if (Utils.isMouseOverTextBox(mouseX, mouseY, this.spawnZ)) {
/* 204 */         tooltip = I18n.format("wdl.gui.world.spawnPos.description", new Object[] { "Z" });
/*     */       } 
/*     */     }
/*     */     
/* 208 */     Utils.drawGuiInfoBox(tooltip, this.width, this.height, 48);
/*     */   }
/*     */   
/*     */   private void cycleAllowCheats() {
/* 212 */     if (WDL.baseProps.getProperty("AllowCheats").equals("true")) {
/* 213 */       WDL.baseProps.setProperty("AllowCheats", "false");
/*     */     } else {
/* 215 */       WDL.baseProps.setProperty("AllowCheats", "true");
/*     */     } 
/*     */     
/* 218 */     this.allowCheatsBtn.displayString = getAllowCheatsText();
/*     */   }
/*     */   
/*     */   private void cycleGamemode() {
/* 222 */     String prop = WDL.baseProps.getProperty("GameType");
/*     */     
/* 224 */     if (prop.equals("keep")) {
/* 225 */       WDL.baseProps.setProperty("GameType", "creative");
/* 226 */     } else if (prop.equals("creative")) {
/* 227 */       WDL.baseProps.setProperty("GameType", "survival");
/* 228 */     } else if (prop.equals("survival")) {
/* 229 */       WDL.baseProps.setProperty("GameType", "hardcore");
/* 230 */     } else if (prop.equals("hardcore")) {
/* 231 */       WDL.baseProps.setProperty("GameType", "keep");
/*     */     } 
/*     */     
/* 234 */     this.gamemodeBtn.displayString = getGamemodeText();
/*     */   }
/*     */   
/*     */   private void cycleTime() {
/* 238 */     String prop = WDL.baseProps.getProperty("Time");
/*     */     
/* 240 */     if (prop.equals("keep")) {
/* 241 */       WDL.baseProps.setProperty("Time", "23000");
/* 242 */     } else if (prop.equals("23000")) {
/* 243 */       WDL.baseProps.setProperty("Time", "0");
/* 244 */     } else if (prop.equals("0")) {
/* 245 */       WDL.baseProps.setProperty("Time", "6000");
/* 246 */     } else if (prop.equals("6000")) {
/* 247 */       WDL.baseProps.setProperty("Time", "11500");
/* 248 */     } else if (prop.equals("11500")) {
/* 249 */       WDL.baseProps.setProperty("Time", "12500");
/* 250 */     } else if (prop.equals("12500")) {
/* 251 */       WDL.baseProps.setProperty("Time", "18000");
/*     */     } else {
/* 253 */       WDL.baseProps.setProperty("Time", "keep");
/*     */     } 
/*     */     
/* 256 */     this.timeBtn.displayString = getTimeText();
/*     */   }
/*     */   
/*     */   private void cycleWeather() {
/* 260 */     String prop = WDL.baseProps.getProperty("Weather");
/*     */     
/* 262 */     if (prop.equals("keep")) {
/* 263 */       WDL.baseProps.setProperty("Weather", "sunny");
/* 264 */     } else if (prop.equals("sunny")) {
/* 265 */       WDL.baseProps.setProperty("Weather", "rain");
/* 266 */     } else if (prop.equals("rain")) {
/* 267 */       WDL.baseProps.setProperty("Weather", "thunderstorm");
/* 268 */     } else if (prop.equals("thunderstorm")) {
/* 269 */       WDL.baseProps.setProperty("Weather", "keep");
/*     */     } 
/*     */     
/* 272 */     this.weatherBtn.displayString = getWeatherText();
/*     */   }
/*     */   
/*     */   private void cycleSpawn() {
/* 276 */     String prop = WDL.worldProps.getProperty("Spawn");
/*     */     
/* 278 */     if (prop.equals("auto")) {
/* 279 */       WDL.worldProps.setProperty("Spawn", "player");
/* 280 */     } else if (prop.equals("player")) {
/* 281 */       WDL.worldProps.setProperty("Spawn", "xyz");
/* 282 */     } else if (prop.equals("xyz")) {
/* 283 */       WDL.worldProps.setProperty("Spawn", "auto");
/*     */     } 
/*     */     
/* 286 */     this.spawnBtn.displayString = getSpawnText();
/* 287 */     updateSpawnTextBoxVisibility();
/*     */   }
/*     */   
/*     */   private String getAllowCheatsText() {
/* 291 */     return I18n.format("wdl.gui.world.allowCheats." + 
/* 292 */         WDL.baseProps.getProperty("AllowCheats"), new Object[0]);
/*     */   }
/*     */   
/*     */   private String getGamemodeText() {
/* 296 */     return I18n.format("wdl.gui.world.gamemode." + 
/* 297 */         WDL.baseProps.getProperty("GameType"), new Object[0]);
/*     */   }
/*     */   
/*     */   private String getTimeText() {
/* 301 */     String result = I18n.format("wdl.gui.world.time." + 
/* 302 */         WDL.baseProps.getProperty("Time"), new Object[0]);
/*     */     
/* 304 */     if (result.startsWith("wdl.gui.world.time."))
/*     */     {
/*     */ 
/*     */       
/* 308 */       result = I18n.format("wdl.gui.world.time.custom", new Object[] {
/* 309 */             WDL.baseProps.getProperty("Time")
/*     */           });
/*     */     }
/* 312 */     return result;
/*     */   }
/*     */   
/*     */   private String getWeatherText() {
/* 316 */     return I18n.format("wdl.gui.world.weather." + 
/* 317 */         WDL.baseProps.getProperty("Weather"), new Object[0]);
/*     */   }
/*     */   
/*     */   private String getSpawnText() {
/* 321 */     return I18n.format("wdl.gui.world.spawn." + 
/* 322 */         WDL.worldProps.getProperty("Spawn"), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSpawnTextBoxVisibility() {
/* 329 */     boolean show = WDL.worldProps.getProperty("Spawn").equals("xyz");
/*     */     
/* 331 */     this.showSpawnFields = show;
/* 332 */     this.pickSpawnBtn.visible = show;
/*     */   }
/*     */   
/*     */   private void setSpawnToPlayerPosition() {
/* 336 */     this.spawnX.setValue((int)WDL.thePlayer.posX);
/* 337 */     this.spawnY.setValue((int)WDL.thePlayer.posY);
/* 338 */     this.spawnZ.setValue((int)WDL.thePlayer.posZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
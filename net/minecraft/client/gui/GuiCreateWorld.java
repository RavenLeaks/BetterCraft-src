/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCreateWorld
/*     */   extends GuiScreen {
/*     */   private final GuiScreen parentScreen;
/*     */   private GuiTextField worldNameField;
/*     */   private GuiTextField worldSeedField;
/*     */   private String saveDirName;
/*  21 */   private String gameMode = "survival";
/*     */   
/*     */   private String savedGameMode;
/*     */   
/*     */   private boolean generateStructuresEnabled = true;
/*     */   
/*     */   private boolean allowCheats;
/*     */   
/*     */   private boolean allowCheatsWasSetByUser;
/*     */   
/*     */   private boolean bonusChestEnabled;
/*     */   
/*     */   private boolean hardCoreMode;
/*     */   
/*     */   private boolean alreadyGenerated;
/*     */   
/*     */   private boolean inMoreWorldOptionsDisplay;
/*     */   
/*     */   private GuiButton btnGameMode;
/*     */   
/*     */   private GuiButton btnMoreOptions;
/*     */   
/*     */   private GuiButton btnMapFeatures;
/*     */   
/*     */   private GuiButton btnBonusItems;
/*     */   
/*     */   private GuiButton btnMapType;
/*     */   private GuiButton btnAllowCommands;
/*     */   private GuiButton btnCustomizeType;
/*     */   private String gameModeDesc1;
/*     */   private String gameModeDesc2;
/*     */   private String worldSeed;
/*     */   private String worldName;
/*     */   private int selectedIndex;
/*  55 */   public String chunkProviderSettingsJson = "";
/*     */ 
/*     */   
/*  58 */   private static final String[] DISALLOWED_FILENAMES = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */ 
/*     */   
/*     */   public GuiCreateWorld(GuiScreen p_i46320_1_) {
/*  62 */     this.parentScreen = p_i46320_1_;
/*  63 */     this.worldSeed = "";
/*  64 */     this.worldName = I18n.format("selectWorld.newWorld", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  72 */     this.worldNameField.updateCursorCounter();
/*  73 */     this.worldSeedField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  82 */     Keyboard.enableRepeatEvents(true);
/*  83 */     this.buttonList.clear();
/*  84 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  85 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  86 */     this.btnGameMode = addButton(new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  87 */     this.btnMoreOptions = addButton(new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  88 */     this.btnMapFeatures = addButton(new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  89 */     this.btnMapFeatures.visible = false;
/*  90 */     this.btnBonusItems = addButton(new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  91 */     this.btnBonusItems.visible = false;
/*  92 */     this.btnMapType = addButton(new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  93 */     this.btnMapType.visible = false;
/*  94 */     this.btnAllowCommands = addButton(new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  95 */     this.btnAllowCommands.visible = false;
/*  96 */     this.btnCustomizeType = addButton(new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/*  97 */     this.btnCustomizeType.visible = false;
/*  98 */     this.worldNameField = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  99 */     this.worldNameField.setFocused(true);
/* 100 */     this.worldNameField.setText(this.worldName);
/* 101 */     this.worldSeedField = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/* 102 */     this.worldSeedField.setText(this.worldSeed);
/* 103 */     showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/* 104 */     calcSaveDirName();
/* 105 */     updateDisplayState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcSaveDirName() {
/* 113 */     this.saveDirName = this.worldNameField.getText().trim(); byte b; int i;
/*     */     char[] arrayOfChar;
/* 115 */     for (i = (arrayOfChar = ChatAllowedCharacters.ILLEGAL_FILE_CHARACTERS).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*     */       
/* 117 */       this.saveDirName = this.saveDirName.replace(c0, '_');
/*     */       b++; }
/*     */     
/* 120 */     if (StringUtils.isEmpty(this.saveDirName))
/*     */     {
/* 122 */       this.saveDirName = "World";
/*     */     }
/*     */     
/* 125 */     this.saveDirName = getUncollidingSaveDirName(this.mc.getSaveLoader(), this.saveDirName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDisplayState() {
/* 133 */     this.btnGameMode.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/* 134 */     this.gameModeDesc1 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
/* 135 */     this.gameModeDesc2 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
/* 136 */     this.btnMapFeatures.displayString = String.valueOf(I18n.format("selectWorld.mapFeatures", new Object[0])) + " ";
/*     */     
/* 138 */     if (this.generateStructuresEnabled) {
/*     */       
/* 140 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 144 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 147 */     this.btnBonusItems.displayString = String.valueOf(I18n.format("selectWorld.bonusItems", new Object[0])) + " ";
/*     */     
/* 149 */     if (this.bonusChestEnabled && !this.hardCoreMode) {
/*     */       
/* 151 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 155 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 158 */     this.btnMapType.displayString = String.valueOf(I18n.format("selectWorld.mapType", new Object[0])) + " " + I18n.format(WorldType.WORLD_TYPES[this.selectedIndex].getTranslateName(), new Object[0]);
/* 159 */     this.btnAllowCommands.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*     */     
/* 161 */     if (this.allowCheats && !this.hardCoreMode) {
/*     */       
/* 163 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 167 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUncollidingSaveDirName(ISaveFormat saveLoader, String name) {
/* 177 */     name = name.replaceAll("[\\./\"]", "_"); byte b; int i;
/*     */     String[] arrayOfString;
/* 179 */     for (i = (arrayOfString = DISALLOWED_FILENAMES).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*     */       
/* 181 */       if (name.equalsIgnoreCase(s))
/*     */       {
/* 183 */         name = "_" + name + "_";
/*     */       }
/*     */       b++; }
/*     */     
/* 187 */     while (saveLoader.getWorldInfo(name) != null)
/*     */     {
/* 189 */       name = String.valueOf(name) + "-";
/*     */     }
/*     */     
/* 192 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 200 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 208 */     if (button.enabled)
/*     */     {
/* 210 */       if (button.id == 1) {
/*     */         
/* 212 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 214 */       else if (button.id == 0) {
/*     */         
/* 216 */         this.mc.displayGuiScreen(null);
/*     */         
/* 218 */         if (this.alreadyGenerated) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 223 */         this.alreadyGenerated = true;
/* 224 */         long i = (new Random()).nextLong();
/* 225 */         String s = this.worldSeedField.getText();
/*     */         
/* 227 */         if (!StringUtils.isEmpty(s)) {
/*     */           
/*     */           try {
/*     */             
/* 231 */             long j = Long.parseLong(s);
/*     */             
/* 233 */             if (j != 0L)
/*     */             {
/* 235 */               i = j;
/*     */             }
/*     */           }
/* 238 */           catch (NumberFormatException var7) {
/*     */             
/* 240 */             i = s.hashCode();
/*     */           } 
/*     */         }
/*     */         
/* 244 */         WorldSettings worldsettings = new WorldSettings(i, GameType.getByName(this.gameMode), this.generateStructuresEnabled, this.hardCoreMode, WorldType.WORLD_TYPES[this.selectedIndex]);
/* 245 */         worldsettings.setGeneratorOptions(this.chunkProviderSettingsJson);
/*     */         
/* 247 */         if (this.bonusChestEnabled && !this.hardCoreMode)
/*     */         {
/* 249 */           worldsettings.enableBonusChest();
/*     */         }
/*     */         
/* 252 */         if (this.allowCheats && !this.hardCoreMode)
/*     */         {
/* 254 */           worldsettings.enableCommands();
/*     */         }
/*     */         
/* 257 */         this.mc.launchIntegratedServer(this.saveDirName, this.worldNameField.getText().trim(), worldsettings);
/*     */       }
/* 259 */       else if (button.id == 3) {
/*     */         
/* 261 */         toggleMoreWorldOptions();
/*     */       }
/* 263 */       else if (button.id == 2) {
/*     */         
/* 265 */         if ("survival".equals(this.gameMode)) {
/*     */           
/* 267 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 269 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 272 */           this.hardCoreMode = false;
/* 273 */           this.gameMode = "hardcore";
/* 274 */           this.hardCoreMode = true;
/* 275 */           this.btnAllowCommands.enabled = false;
/* 276 */           this.btnBonusItems.enabled = false;
/* 277 */           updateDisplayState();
/*     */         }
/* 279 */         else if ("hardcore".equals(this.gameMode)) {
/*     */           
/* 281 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 283 */             this.allowCheats = true;
/*     */           }
/*     */           
/* 286 */           this.hardCoreMode = false;
/* 287 */           this.gameMode = "creative";
/* 288 */           updateDisplayState();
/* 289 */           this.hardCoreMode = false;
/* 290 */           this.btnAllowCommands.enabled = true;
/* 291 */           this.btnBonusItems.enabled = true;
/*     */         }
/*     */         else {
/*     */           
/* 295 */           if (!this.allowCheatsWasSetByUser)
/*     */           {
/* 297 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 300 */           this.gameMode = "survival";
/* 301 */           updateDisplayState();
/* 302 */           this.btnAllowCommands.enabled = true;
/* 303 */           this.btnBonusItems.enabled = true;
/* 304 */           this.hardCoreMode = false;
/*     */         } 
/*     */         
/* 307 */         updateDisplayState();
/*     */       }
/* 309 */       else if (button.id == 4) {
/*     */         
/* 311 */         this.generateStructuresEnabled = !this.generateStructuresEnabled;
/* 312 */         updateDisplayState();
/*     */       }
/* 314 */       else if (button.id == 7) {
/*     */         
/* 316 */         this.bonusChestEnabled = !this.bonusChestEnabled;
/* 317 */         updateDisplayState();
/*     */       }
/* 319 */       else if (button.id == 5) {
/*     */         
/* 321 */         this.selectedIndex++;
/*     */         
/* 323 */         if (this.selectedIndex >= WorldType.WORLD_TYPES.length)
/*     */         {
/* 325 */           this.selectedIndex = 0;
/*     */         }
/*     */         
/* 328 */         while (!canSelectCurWorldType()) {
/*     */           
/* 330 */           this.selectedIndex++;
/*     */           
/* 332 */           if (this.selectedIndex >= WorldType.WORLD_TYPES.length)
/*     */           {
/* 334 */             this.selectedIndex = 0;
/*     */           }
/*     */         } 
/*     */         
/* 338 */         this.chunkProviderSettingsJson = "";
/* 339 */         updateDisplayState();
/* 340 */         showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/*     */       }
/* 342 */       else if (button.id == 6) {
/*     */         
/* 344 */         this.allowCheatsWasSetByUser = true;
/* 345 */         this.allowCheats = !this.allowCheats;
/* 346 */         updateDisplayState();
/*     */       }
/* 348 */       else if (button.id == 8) {
/*     */         
/* 350 */         if (WorldType.WORLD_TYPES[this.selectedIndex] == WorldType.FLAT) {
/*     */           
/* 352 */           this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
/*     */         }
/*     */         else {
/*     */           
/* 356 */           this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSelectCurWorldType() {
/* 368 */     WorldType worldtype = WorldType.WORLD_TYPES[this.selectedIndex];
/*     */     
/* 370 */     if (worldtype != null && worldtype.getCanBeCreated())
/*     */     {
/* 372 */       return (worldtype == WorldType.DEBUG_WORLD) ? isShiftKeyDown() : true;
/*     */     }
/*     */ 
/*     */     
/* 376 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleMoreWorldOptions() {
/* 387 */     showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showMoreWorldOptions(boolean toggle) {
/* 395 */     this.inMoreWorldOptionsDisplay = toggle;
/*     */     
/* 397 */     if (WorldType.WORLD_TYPES[this.selectedIndex] == WorldType.DEBUG_WORLD) {
/*     */       
/* 399 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 400 */       this.btnGameMode.enabled = false;
/*     */       
/* 402 */       if (this.savedGameMode == null)
/*     */       {
/* 404 */         this.savedGameMode = this.gameMode;
/*     */       }
/*     */       
/* 407 */       this.gameMode = "spectator";
/* 408 */       this.btnMapFeatures.visible = false;
/* 409 */       this.btnBonusItems.visible = false;
/* 410 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 411 */       this.btnAllowCommands.visible = false;
/* 412 */       this.btnCustomizeType.visible = false;
/*     */     }
/*     */     else {
/*     */       
/* 416 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 417 */       this.btnGameMode.enabled = true;
/*     */       
/* 419 */       if (this.savedGameMode != null) {
/*     */         
/* 421 */         this.gameMode = this.savedGameMode;
/* 422 */         this.savedGameMode = null;
/*     */       } 
/*     */       
/* 425 */       this.btnMapFeatures.visible = (this.inMoreWorldOptionsDisplay && WorldType.WORLD_TYPES[this.selectedIndex] != WorldType.CUSTOMIZED);
/* 426 */       this.btnBonusItems.visible = this.inMoreWorldOptionsDisplay;
/* 427 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 428 */       this.btnAllowCommands.visible = this.inMoreWorldOptionsDisplay;
/* 429 */       this.btnCustomizeType.visible = (this.inMoreWorldOptionsDisplay && (WorldType.WORLD_TYPES[this.selectedIndex] == WorldType.FLAT || WorldType.WORLD_TYPES[this.selectedIndex] == WorldType.CUSTOMIZED));
/*     */     } 
/*     */     
/* 432 */     updateDisplayState();
/*     */     
/* 434 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 436 */       this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 440 */       this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 450 */     if (this.worldNameField.isFocused() && !this.inMoreWorldOptionsDisplay) {
/*     */       
/* 452 */       this.worldNameField.textboxKeyTyped(typedChar, keyCode);
/* 453 */       this.worldName = this.worldNameField.getText();
/*     */     }
/* 455 */     else if (this.worldSeedField.isFocused() && this.inMoreWorldOptionsDisplay) {
/*     */       
/* 457 */       this.worldSeedField.textboxKeyTyped(typedChar, keyCode);
/* 458 */       this.worldSeed = this.worldSeedField.getText();
/*     */     } 
/*     */     
/* 461 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 463 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 466 */     ((GuiButton)this.buttonList.get(0)).enabled = !this.worldNameField.getText().isEmpty();
/* 467 */     calcSaveDirName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 475 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 477 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 479 */       this.worldSeedField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     else {
/*     */       
/* 483 */       this.worldNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 492 */     drawDefaultBackground();
/* 493 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), this.width / 2, 20, -1);
/*     */     
/* 495 */     if (this.inMoreWorldOptionsDisplay) {
/*     */       
/* 497 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 498 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), this.width / 2 - 100, 85, -6250336);
/*     */       
/* 500 */       if (this.btnMapFeatures.visible)
/*     */       {
/* 502 */         drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), this.width / 2 - 150, 122, -6250336);
/*     */       }
/*     */       
/* 505 */       if (this.btnAllowCommands.visible)
/*     */       {
/* 507 */         drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), this.width / 2 - 150, 172, -6250336);
/*     */       }
/*     */       
/* 510 */       this.worldSeedField.drawTextBox();
/*     */       
/* 512 */       if (WorldType.WORLD_TYPES[this.selectedIndex].showWorldInfoNotice())
/*     */       {
/* 514 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.WORLD_TYPES[this.selectedIndex].getTranslatedInfo(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 519 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 520 */       drawString(this.fontRendererObj, String.valueOf(I18n.format("selectWorld.resultFolder", new Object[0])) + " " + this.saveDirName, this.width / 2 - 100, 85, -6250336);
/* 521 */       this.worldNameField.drawTextBox();
/* 522 */       drawString(this.fontRendererObj, this.gameModeDesc1, this.width / 2 - 100, 137, -6250336);
/* 523 */       drawString(this.fontRendererObj, this.gameModeDesc2, this.width / 2 - 100, 149, -6250336);
/*     */     } 
/*     */     
/* 526 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateFromExistingWorld(WorldInfo original) {
/* 536 */     this.worldName = I18n.format("selectWorld.newWorld.copyOf", new Object[] { original.getWorldName() });
/* 537 */     this.worldSeed = (new StringBuilder(String.valueOf(original.getSeed()))).toString();
/* 538 */     this.selectedIndex = original.getTerrainType().getWorldTypeID();
/* 539 */     this.chunkProviderSettingsJson = original.getGeneratorOptions();
/* 540 */     this.generateStructuresEnabled = original.isMapFeaturesEnabled();
/* 541 */     this.allowCheats = original.areCommandsAllowed();
/*     */     
/* 543 */     if (original.isHardcoreModeEnabled()) {
/*     */       
/* 545 */       this.gameMode = "hardcore";
/*     */     }
/* 547 */     else if (original.getGameType().isSurvivalOrAdventure()) {
/*     */       
/* 549 */       this.gameMode = "survival";
/*     */     }
/* 551 */     else if (original.getGameType().isCreative()) {
/*     */       
/* 553 */       this.gameMode = "creative";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiCreateWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
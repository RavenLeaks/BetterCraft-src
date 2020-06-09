/*     */ package wdl.gui;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ 
/*     */ public class GuiWDLMultiworldSelect extends GuiTurningCameraBase {
/*     */   private final WorldSelectionCallback callback;
/*     */   private final String title;
/*     */   private GuiButton cancelBtn;
/*     */   private GuiButton acceptBtn;
/*     */   private GuiTextField newNameField;
/*     */   private GuiTextField searchField;
/*     */   private GuiButton newWorldButton;
/*     */   private boolean showNewWorldTextBox;
/*     */   private List<MultiworldInfo> linkedWorlds;
/*     */   private List<MultiworldInfo> linkedWorldsFiltered;
/*     */   private MultiworldInfo selectedMultiWorld;
/*     */   
/*     */   public static interface WorldSelectionCallback {
/*     */     void onCancel();
/*     */     
/*     */     void onWorldSelected(String param1String);
/*     */   }
/*     */   
/*     */   private class WorldGuiButton extends GuiButton {
/*     */     public WorldGuiButton(int offset, int x, int y, int width, int height) {
/*  27 */       super(offset, x, y, width, height, "");
/*     */     }
/*     */ 
/*     */     
/*     */     public WorldGuiButton(int offset, int x, int y, String worldName, String displayName) {
/*  32 */       super(offset, x, y, "");
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float a) {
/*  37 */       GuiWDLMultiworldSelect.MultiworldInfo info = getWorldInfo();
/*  38 */       if (info == null) {
/*  39 */         this.displayString = "";
/*  40 */         this.enabled = false;
/*     */       } else {
/*  42 */         this.displayString = info.displayName;
/*  43 */         this.enabled = true;
/*     */       } 
/*     */       
/*  46 */       if (info != null && info == GuiWDLMultiworldSelect.this.selectedMultiWorld) {
/*  47 */         drawRect(this.xPosition - 2, this.yPosition - 2, 
/*  48 */             this.xPosition + this.width + 2, this.yPosition + this.height + 2, 
/*  49 */             -16744704);
/*     */       }
/*  51 */       super.func_191745_a(mc, mouseX, mouseY, a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiWDLMultiworldSelect.MultiworldInfo getWorldInfo() {
/*  60 */       int location = GuiWDLMultiworldSelect.this.index + this.id;
/*  61 */       if (location < 0) {
/*  62 */         return null;
/*     */       }
/*  64 */       if (location >= GuiWDLMultiworldSelect.this.linkedWorldsFiltered.size()) {
/*  65 */         return null;
/*     */       }
/*     */       
/*  68 */       return GuiWDLMultiworldSelect.this.linkedWorldsFiltered.get(location);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MultiworldInfo
/*     */   {
/*     */     public final String folderName;
/*     */     public final String displayName;
/*     */     private List<String> description;
/*     */     
/*     */     public MultiworldInfo(String folderName, String displayName) {
/*  79 */       this.folderName = folderName;
/*  80 */       this.displayName = displayName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getDescription() {
/*  92 */       if (this.description == null) {
/*  93 */         this.description = new ArrayList<>();
/*     */ 
/*     */ 
/*     */         
/*  97 */         this.description.add("Defined dimensions:");
/*  98 */         File savesFolder = new File(WDL.minecraft.mcDataDir, "saves");
/*  99 */         File world = new File(savesFolder, WDL.getWorldFolderName(this.folderName));
/* 100 */         File[] subfolders = world.listFiles();
/*     */         
/* 102 */         if (subfolders != null) {
/* 103 */           byte b; int i; File[] arrayOfFile; for (i = (arrayOfFile = subfolders).length, b = 0; b < i; ) { File subfolder = arrayOfFile[b];
/* 104 */             if (subfolder.listFiles() != null)
/*     */             {
/*     */ 
/*     */               
/* 108 */               if ((subfolder.listFiles()).length != 0)
/*     */               {
/*     */ 
/*     */                 
/* 112 */                 if (subfolder.getName().equals("region")) {
/* 113 */                   this.description.add(" * Overworld (#0)");
/* 114 */                 } else if (subfolder.getName().startsWith("DIM")) {
/* 115 */                   String dimension = subfolder.getName().substring(3);
/* 116 */                   if (dimension.equals("-1")) {
/* 117 */                     this.description.add(" * Nether (#-1)");
/* 118 */                   } else if (dimension.equals("1")) {
/* 119 */                     this.description.add(" * The End (#1)");
/*     */                   } else {
/* 121 */                     this.description.add(" * #" + dimension);
/*     */                   } 
/*     */                 }  }  } 
/*     */             b++; }
/*     */         
/*     */         } 
/*     */       } 
/* 128 */       return this.description;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   private int index = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiButton nextButton;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiButton prevButton;
/*     */ 
/*     */ 
/*     */   
/*     */   private int numWorldButtons;
/*     */ 
/*     */ 
/*     */   
/* 217 */   private String searchText = "";
/*     */   
/*     */   public GuiWDLMultiworldSelect(String title, WorldSelectionCallback callback) {
/* 220 */     this.title = title;
/* 221 */     this.callback = callback;
/*     */ 
/*     */     
/* 224 */     String[] worldNames = WDL.baseProps.getProperty("LinkedWorlds")
/* 225 */       .split("\\|");
/* 226 */     this.linkedWorlds = new ArrayList<>(); byte b; int i;
/*     */     String[] arrayOfString1;
/* 228 */     for (i = (arrayOfString1 = worldNames).length, b = 0; b < i; ) { String worldName = arrayOfString1[b];
/* 229 */       if (worldName != null && !worldName.isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/* 233 */         Properties props = WDL.loadWorldProps(worldName);
/*     */         
/* 235 */         if (props.containsKey("WorldName")) {
/*     */ 
/*     */ 
/*     */           
/* 239 */           String displayName = props.getProperty("WorldName", worldName);
/* 240 */           this.linkedWorlds.add(new MultiworldInfo(worldName, displayName));
/*     */         } 
/*     */       }  b++; }
/* 243 */      this.linkedWorldsFiltered = new ArrayList<>();
/* 244 */     this.linkedWorldsFiltered.addAll(this.linkedWorlds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 249 */     super.initGui();
/*     */     
/* 251 */     this.numWorldButtons = (this.width - 50) / 155;
/*     */     
/* 253 */     if (this.numWorldButtons < 1) {
/* 254 */       this.numWorldButtons = 1;
/*     */     }
/*     */     
/* 257 */     int offset = (this.numWorldButtons * 155 + 45) / 2;
/* 258 */     int y = this.height - 49;
/*     */     
/* 260 */     this.cancelBtn = new GuiButton(-1, this.width / 2 - 155, this.height - 25, 
/* 261 */         150, 20, I18n.format("gui.cancel", new Object[0]));
/* 262 */     this.buttonList.add(this.cancelBtn);
/*     */     
/* 264 */     this.acceptBtn = new GuiButton(-2, this.width / 2 + 5, this.height - 25, 
/* 265 */         150, 20, I18n.format("wdl.gui.multiworldSelect.done", new Object[0]));
/* 266 */     this.acceptBtn.enabled = (this.selectedMultiWorld != null);
/* 267 */     this.buttonList.add(this.acceptBtn);
/*     */     
/* 269 */     this.prevButton = new GuiButton(-4, this.width / 2 - offset, y, 20, 20, "<");
/* 270 */     this.buttonList.add(this.prevButton);
/*     */     
/* 272 */     for (int i = 0; i < this.numWorldButtons; i++) {
/* 273 */       this.buttonList.add(new WorldGuiButton(i, this.width / 2 - offset + 
/* 274 */             i * 155 + 25, y, 150, 20));
/*     */     }
/*     */     
/* 277 */     this.nextButton = new GuiButton(-5, this.width / 2 - offset + 25 + 
/* 278 */         this.numWorldButtons * 155, y, 20, 20, ">");
/* 279 */     this.buttonList.add(this.nextButton);
/*     */     
/* 281 */     this.newWorldButton = new GuiButton(-3, this.width / 2 - 155, 29, 150, 20, 
/* 282 */         I18n.format("wdl.gui.multiworldSelect.newName", new Object[0]));
/* 283 */     this.buttonList.add(this.newWorldButton);
/*     */     
/* 285 */     this.newNameField = new GuiTextField(40, this.fontRendererObj, 
/* 286 */         this.width / 2 - 155, 29, 150, 20);
/*     */     
/* 288 */     this.searchField = new GuiTextField(41, this.fontRendererObj, 
/* 289 */         this.width / 2 + 5, 29, 150, 20);
/* 290 */     this.searchField.setText(this.searchText);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 295 */     if (button.enabled) {
/* 296 */       if (button instanceof WorldGuiButton) {
/* 297 */         this.selectedMultiWorld = ((WorldGuiButton)button).getWorldInfo();
/* 298 */         if (this.selectedMultiWorld != null) {
/* 299 */           this.acceptBtn.enabled = true;
/*     */         } else {
/* 301 */           this.acceptBtn.enabled = false;
/*     */         } 
/* 303 */       } else if (button.id == -1) {
/* 304 */         this.callback.onCancel();
/* 305 */       } else if (button.id == -2) {
/* 306 */         this.callback.onWorldSelected(this.selectedMultiWorld.folderName);
/* 307 */       } else if (button.id == -3) {
/* 308 */         this.showNewWorldTextBox = true;
/* 309 */       } else if (button.id == -4) {
/* 310 */         this.index--;
/* 311 */       } else if (button.id == -5) {
/* 312 */         this.index++;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 323 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 325 */     if (this.showNewWorldTextBox) {
/* 326 */       this.newNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 329 */     this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 338 */     if (keyCode == 1) {
/* 339 */       this.callback.onCancel();
/*     */     }
/*     */     
/* 342 */     super.keyTyped(typedChar, keyCode);
/*     */     
/* 344 */     if (this.showNewWorldTextBox) {
/* 345 */       this.newNameField.textboxKeyTyped(typedChar, keyCode);
/*     */       
/* 347 */       if (keyCode == 28) {
/* 348 */         String newName = this.newNameField.getText();
/*     */         
/* 350 */         if (newName != null && !newName.isEmpty()) {
/*     */           
/* 352 */           addMultiworld(newName);
/* 353 */           this.newNameField.setText("");
/* 354 */           this.showNewWorldTextBox = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 360 */     if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
/* 361 */       this.searchText = this.searchField.getText();
/* 362 */       rebuildFilteredWorlds();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 371 */     this.newNameField.updateCursorCounter();
/* 372 */     this.searchField.updateCursorCounter();
/* 373 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 381 */     if (this.index >= this.linkedWorlds.size() - this.numWorldButtons) {
/* 382 */       this.index = this.linkedWorlds.size() - this.numWorldButtons;
/* 383 */       this.nextButton.enabled = false;
/*     */     } else {
/* 385 */       this.nextButton.enabled = true;
/*     */     } 
/* 387 */     if (this.index <= 0) {
/* 388 */       this.index = 0;
/* 389 */       this.prevButton.enabled = false;
/*     */     } else {
/* 391 */       this.prevButton.enabled = true;
/*     */     } 
/*     */     
/* 394 */     Utils.drawBorder(53, 53, 0, 0, this.height, this.width);
/*     */     
/* 396 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 
/* 397 */         16777215);
/*     */     
/* 399 */     drawCenteredString(this.fontRendererObj, 
/* 400 */         I18n.format("wdl.gui.multiworldSelect.subtitle", new Object[0]), 
/* 401 */         this.width / 2, 18, 16711680);
/*     */     
/* 403 */     if (this.showNewWorldTextBox) {
/* 404 */       this.newNameField.drawTextBox();
/*     */     }
/* 406 */     this.searchField.drawTextBox();
/*     */     
/* 408 */     if (this.searchField.getText().isEmpty() && !this.searchField.isFocused()) {
/* 409 */       drawString(this.fontRendererObj, 
/* 410 */           I18n.format("wdl.gui.multiworldSelect.search", new Object[0]), 
/* 411 */           this.searchField.xPosition + 4, this.searchField.yPosition + 6, 
/* 412 */           9474192);
/*     */     }
/*     */     
/* 415 */     this.newWorldButton.visible = !this.showNewWorldTextBox;
/*     */     
/* 417 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 419 */     drawMultiworldDescription();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMultiworld(String worldName) {
/* 426 */     String folderName = worldName;
/* 427 */     char[] unsafeChars = "\\/:*?\"<>|.".toCharArray(); byte b;
/*     */     int i;
/*     */     char[] arrayOfChar1;
/* 430 */     for (i = (arrayOfChar1 = unsafeChars).length, b = 0; b < i; ) { char unsafeChar = arrayOfChar1[b];
/* 431 */       folderName = folderName.replace(unsafeChar, '_');
/*     */       b++; }
/*     */     
/* 434 */     Properties worldProps = new Properties(WDL.baseProps);
/* 435 */     worldProps.setProperty("WorldName", worldName);
/*     */     
/* 437 */     String linkedWorldsProp = WDL.baseProps.getProperty("LinkedWorlds");
/* 438 */     linkedWorldsProp = String.valueOf(linkedWorldsProp) + "|" + folderName;
/*     */     
/* 440 */     WDL.baseProps.setProperty("LinkedWorlds", linkedWorldsProp);
/* 441 */     WDL.saveProps(folderName, worldProps);
/*     */     
/* 443 */     this.linkedWorlds.add(new MultiworldInfo(folderName, worldName));
/*     */     
/* 445 */     rebuildFilteredWorlds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebuildFilteredWorlds() {
/* 453 */     String searchFilter = this.searchText.toLowerCase();
/* 454 */     this.linkedWorldsFiltered.clear();
/* 455 */     for (MultiworldInfo info : this.linkedWorlds) {
/* 456 */       if (info.displayName.toLowerCase().contains(searchFilter)) {
/* 457 */         this.linkedWorldsFiltered.add(info);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawMultiworldDescription() {
/* 467 */     if (this.selectedMultiWorld == null) {
/*     */       return;
/*     */     }
/*     */     
/* 471 */     String title = "Info about " + 
/* 472 */       this.selectedMultiWorld.displayName;
/* 473 */     List<String> description = this.selectedMultiWorld.getDescription();
/*     */     
/* 475 */     int maxWidth = this.fontRendererObj.getStringWidth(title);
/* 476 */     for (String line : description) {
/* 477 */       int width = this.fontRendererObj.getStringWidth(line);
/* 478 */       if (width > maxWidth) {
/* 479 */         maxWidth = width;
/*     */       }
/*     */     } 
/*     */     
/* 483 */     drawRect(2, 61, 5 + maxWidth + 3, this.height - 61, -2147483648);
/*     */     
/* 485 */     drawString(this.fontRendererObj, title, 5, 64, 16777215);
/*     */     
/* 487 */     int y = 64 + this.fontRendererObj.FONT_HEIGHT;
/* 488 */     for (String s : description) {
/* 489 */       drawString(this.fontRendererObj, s, 5, y, 16777215);
/* 490 */       y += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLMultiworldSelect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package wdl.gui;
/*     */ public class GuiWDLOverwriteChanges extends GuiTurningCameraBase implements WorldBackup.IBackupProgressMonitor {
/*     */   private volatile boolean backingUp;
/*     */   private volatile String backupData;
/*     */   private volatile int backupCount;
/*     */   private volatile int backupCurrent;
/*     */   private volatile String backupFile;
/*     */   private int infoBoxX;
/*     */   private int infoBoxY;
/*     */   private int infoBoxWidth;
/*     */   private int infoBoxHeight;
/*     */   private GuiButton backupAsZipButton;
/*     */   private GuiButton backupAsFolderButton;
/*     */   private GuiButton downloadNowButton;
/*     */   private GuiButton cancelButton;
/*     */   private final long lastSaved;
/*     */   private final long lastPlayed;
/*     */   private String title;
/*     */   private String footer;
/*     */   private String captionTitle;
/*     */   private String captionSubtitle;
/*     */   private String overwriteWarning1;
/*     */   private String overwriteWarning2;
/*     */   private String backingUpTitle;
/*     */   
/*     */   private class BackupThread extends Thread {
/*  27 */     private final DateFormat folderDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); private final boolean zip;
/*     */     
/*     */     public BackupThread(boolean zip) {
/*  30 */       this.zip = zip;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*  38 */         String backupName = String.valueOf(WDL.getWorldFolderName(WDL.worldName)) + "_" + 
/*  39 */           this.folderDateFormat.format(new Date()) + 
/*  40 */           "_user" + (this.zip ? ".zip" : "");
/*     */         
/*  42 */         if (this.zip) {
/*  43 */           GuiWDLOverwriteChanges.this.backupData = 
/*  44 */             I18n.format("wdl.gui.overwriteChanges.backingUp.zip", new Object[] { backupName });
/*     */         } else {
/*  46 */           GuiWDLOverwriteChanges.this.backupData = 
/*  47 */             I18n.format("wdl.gui.overwriteChanges.backingUp.folder", new Object[] { backupName });
/*     */         } 
/*     */         
/*  50 */         File fromFolder = WDL.saveHandler.getWorldDirectory();
/*  51 */         File backupFile = new File(fromFolder.getParentFile(), 
/*  52 */             backupName);
/*     */         
/*  54 */         if (backupFile.exists()) {
/*  55 */           throw new IOException("Backup target (" + backupFile + 
/*  56 */               ") already exists!");
/*     */         }
/*     */         
/*  59 */         if (this.zip) {
/*  60 */           WorldBackup.zipDirectory(fromFolder, backupFile, 
/*  61 */               GuiWDLOverwriteChanges.this);
/*     */         } else {
/*  63 */           WorldBackup.copyDirectory(fromFolder, backupFile, 
/*  64 */               GuiWDLOverwriteChanges.this);
/*     */         } 
/*  66 */       } catch (Exception e) {
/*  67 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.ERROR, 
/*  68 */             "wdl.messages.generalError.failedToSetUpEntityUI", new Object[0]);
/*     */       } finally {
/*  70 */         GuiWDLOverwriteChanges.this.backingUp = false;
/*     */         
/*  72 */         WDL.overrideLastModifiedCheck = true;
/*  73 */         GuiWDLOverwriteChanges.this.mc.displayGuiScreen(null);
/*     */         
/*  75 */         WDL.startDownload();
/*     */       } 
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
/*     */   public GuiWDLOverwriteChanges(long lastSaved, long lastPlayed) {
/*  88 */     this.backingUp = false;
/*     */ 
/*     */ 
/*     */     
/*  92 */     this.backupData = "";
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
/* 104 */     this.backupFile = "";
/*     */     this.lastSaved = lastSaved;
/*     */     this.lastPlayed = lastPlayed;
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
/*     */   public void initGui() {
/* 128 */     this.backingUp = false;
/*     */     
/* 130 */     this.title = I18n.format("wdl.gui.overwriteChanges.title", new Object[0]);
/* 131 */     if (this.lastSaved != -1L) {
/* 132 */       this.footer = I18n.format("wdl.gui.overwriteChanges.footer", new Object[] { Long.valueOf(this.lastSaved), Long.valueOf(this.lastPlayed) });
/*     */     } else {
/* 134 */       this.footer = I18n.format("wdl.gui.overwriteChanges.footerNeverSaved", new Object[] { Long.valueOf(this.lastPlayed) });
/*     */     } 
/* 136 */     this.captionTitle = I18n.format("wdl.gui.overwriteChanges.captionTitle", new Object[0]);
/* 137 */     this.captionSubtitle = I18n.format("wdl.gui.overwriteChanges.captionSubtitle", new Object[0]);
/* 138 */     this.overwriteWarning1 = I18n.format("wdl.gui.overwriteChanges.overwriteWarning1", new Object[0]);
/* 139 */     this.overwriteWarning2 = I18n.format("wdl.gui.overwriteChanges.overwriteWarning2", new Object[0]);
/*     */     
/* 141 */     this.backingUpTitle = I18n.format("wdl.gui.overwriteChanges.backingUp.title", new Object[0]);
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.infoBoxWidth = this.fontRendererObj.getStringWidth(this.overwriteWarning1);
/* 146 */     this.infoBoxHeight = 132;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (this.infoBoxWidth < 200) {
/* 152 */       this.infoBoxWidth = 200;
/*     */     }
/*     */     
/* 155 */     this.infoBoxY = 48;
/* 156 */     this.infoBoxX = this.width / 2 - this.infoBoxWidth / 2;
/*     */     
/* 158 */     int x = this.width / 2 - 100;
/* 159 */     int y = this.infoBoxY + 22;
/*     */     
/* 161 */     this.backupAsZipButton = new GuiButton(0, x, y, 
/* 162 */         I18n.format("wdl.gui.overwriteChanges.asZip.name", new Object[0]));
/* 163 */     this.buttonList.add(this.backupAsZipButton);
/* 164 */     y += 22;
/* 165 */     this.backupAsFolderButton = new GuiButton(1, x, y, 
/* 166 */         I18n.format("wdl.gui.overwriteChanges.asFolder.name", new Object[0]));
/* 167 */     this.buttonList.add(this.backupAsFolderButton);
/* 168 */     y += 22;
/* 169 */     this.downloadNowButton = new GuiButton(2, x, y, 
/* 170 */         I18n.format("wdl.gui.overwriteChanges.startNow.name", new Object[0]));
/* 171 */     this.buttonList.add(this.downloadNowButton);
/* 172 */     y += 22;
/* 173 */     this.cancelButton = new GuiButton(3, x, y, 
/* 174 */         I18n.format("wdl.gui.overwriteChanges.cancel.name", new Object[0]));
/* 175 */     this.buttonList.add(this.cancelButton);
/*     */     
/* 177 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 182 */     if (keyCode == 1) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 191 */     if (this.backingUp) {
/*     */       return;
/*     */     }
/*     */     
/* 195 */     if (button.id == 0) {
/* 196 */       this.backingUp = true;
/*     */       
/* 198 */       (new BackupThread(true)).start();
/*     */     } 
/* 200 */     if (button.id == 1) {
/* 201 */       this.backingUp = true;
/*     */       
/* 203 */       (new BackupThread(false)).start();
/*     */     } 
/* 205 */     if (button.id == 2) {
/* 206 */       WDL.overrideLastModifiedCheck = true;
/* 207 */       this.mc.displayGuiScreen(null);
/*     */       
/* 209 */       WDL.startDownload();
/*     */     } 
/* 211 */     if (button.id == 3) {
/* 212 */       this.mc.displayGuiScreen(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 218 */     if (this.backingUp) {
/* 219 */       drawBackground(0);
/*     */       
/* 221 */       drawCenteredString(this.fontRendererObj, this.backingUpTitle, 
/* 222 */           this.width / 2, this.height / 4 - 40, 16777215);
/* 223 */       drawCenteredString(this.fontRendererObj, this.backupData, 
/* 224 */           this.width / 2, this.height / 4 - 10, 16777215);
/* 225 */       if (this.backupFile != null) {
/* 226 */         String text = I18n.format(
/* 227 */             "wdl.gui.overwriteChanges.backingUp.progress", new Object[] {
/* 228 */               Integer.valueOf(this.backupCurrent), Integer.valueOf(this.backupCount), this.backupFile });
/* 229 */         drawCenteredString(this.fontRendererObj, text, this.width / 2, 
/* 230 */             this.height / 4 + 10, 16777215);
/*     */       } 
/*     */     } else {
/* 233 */       drawDefaultBackground();
/* 234 */       Utils.drawBorder(32, 22, 0, 0, this.height, this.width);
/*     */       
/* 236 */       drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
/* 237 */       drawCenteredString(this.fontRendererObj, this.footer, this.width / 2, this.height - 8 - 
/* 238 */           this.fontRendererObj.FONT_HEIGHT, 16777215);
/*     */       
/* 240 */       drawRect(this.infoBoxX - 5, this.infoBoxY - 5, this.infoBoxX + this.infoBoxWidth + 5, 
/* 241 */           this.infoBoxY + this.infoBoxHeight + 5, -1342177280);
/*     */       
/* 243 */       drawCenteredString(this.fontRendererObj, this.captionTitle, this.width / 2, 
/* 244 */           this.infoBoxY, 16777215);
/* 245 */       drawCenteredString(this.fontRendererObj, this.captionSubtitle, this.width / 2, 
/* 246 */           this.infoBoxY + this.fontRendererObj.FONT_HEIGHT, 16777215);
/*     */       
/* 248 */       drawCenteredString(this.fontRendererObj, this.overwriteWarning1, this.width / 2, 
/* 249 */           this.infoBoxY + 115, 16777215);
/* 250 */       drawCenteredString(this.fontRendererObj, this.overwriteWarning2, this.width / 2, 
/* 251 */           this.infoBoxY + 115 + this.fontRendererObj.FONT_HEIGHT, 16777215);
/*     */       
/* 253 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */       
/* 255 */       String tooltip = null;
/* 256 */       if (this.backupAsZipButton.isMouseOver()) {
/* 257 */         tooltip = I18n.format("wdl.gui.overwriteChanges.asZip.description", new Object[0]);
/* 258 */       } else if (this.backupAsFolderButton.isMouseOver()) {
/* 259 */         tooltip = I18n.format("wdl.gui.overwriteChanges.asFolder.description", new Object[0]);
/* 260 */       } else if (this.downloadNowButton.isMouseOver()) {
/* 261 */         tooltip = I18n.format("wdl.gui.overwriteChanges.startNow.description", new Object[0]);
/* 262 */       } else if (this.cancelButton.isMouseOver()) {
/* 263 */         tooltip = I18n.format("wdl.gui.overwriteChanges.cancel.description", new Object[0]);
/*     */       } 
/*     */       
/* 266 */       Utils.drawGuiInfoBox(tooltip, this.width, this.height, 48);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNumberOfFiles(int num) {
/* 272 */     this.backupCount = num;
/* 273 */     this.backupCurrent = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNextFile(String name) {
/* 278 */     this.backupCurrent++;
/* 279 */     this.backupFile = name;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLOverwriteChanges.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
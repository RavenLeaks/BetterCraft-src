/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import wdl.WDL;
/*     */ import wdl.update.Release;
/*     */ import wdl.update.WDLUpdateChecker;
/*     */ 
/*     */ public class GuiWDLUpdates
/*     */   extends GuiScreen {
/*     */   private final GuiScreen parent;
/*     */   private static final int TOP_MARGIN = 39;
/*     */   private static final int BOTTOM_MARGIN = 32;
/*     */   private UpdateList list;
/*     */   private GuiButton updateMinecraftVersionButton;
/*     */   private GuiButton updateAllowBetasButton;
/*     */   
/*     */   private class UpdateList extends GuiListExtended {
/*     */     private List<VersionEntry> displayedVersions;
/*     */     private Release recomendedRelease;
/*     */     
/*     */     public UpdateList() {
/*  33 */       super(GuiWDLUpdates.this.mc, GuiWDLUpdates.this.width, GuiWDLUpdates.this.height, 39, GuiWDLUpdates.this.height - 32, (GuiWDLUpdates.this.fontRendererObj.FONT_HEIGHT + 1) * 6 + 2);
/*  34 */       this.showSelectionBox = true;
/*     */     }
/*     */     
/*     */     private class VersionEntry
/*     */       implements GuiListExtended.IGuiListEntry
/*     */     {
/*     */       private final Release release;
/*     */       private String title;
/*     */       private String caption;
/*     */       private String body1;
/*     */       private String body2;
/*     */       private String body3;
/*     */       private String time;
/*     */       private final int fontHeight;
/*     */       
/*     */       public VersionEntry(Release release) {
/*  50 */         this.release = release;
/*  51 */         this.fontHeight = (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.FONT_HEIGHT + 1;
/*     */         
/*  53 */         this.title = GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this).buildReleaseTitle(release);
/*  54 */         this.caption = GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this).buildVersionInfo(release);
/*     */         
/*  56 */         List<String> body = Utils.wordWrap(release.textOnlyBody, GuiWDLUpdates.UpdateList.this.getListWidth());
/*     */         
/*  58 */         this.body1 = (body.size() >= 1) ? body.get(0) : "";
/*  59 */         this.body2 = (body.size() >= 2) ? body.get(1) : "";
/*  60 */         this.body3 = (body.size() >= 3) ? body.get(2) : "";
/*     */         
/*  62 */         this.time = I18n.format("wdl.gui.updates.update.releaseDate", new Object[] { release.date });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192634_a(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float t) {
/*     */         String title;
/*  70 */         if (GuiWDLUpdates.UpdateList.this.isSelected(slotIndex)) {
/*  71 */           title = I18n.format("wdl.gui.updates.currentVersion", new Object[] {
/*  72 */                 this.title });
/*  73 */         } else if (this.release == GuiWDLUpdates.UpdateList.this.recomendedRelease) {
/*  74 */           title = I18n.format("wdl.gui.updates.recomendedVersion", new Object[] {
/*  75 */                 this.title });
/*     */         } else {
/*  77 */           title = this.title;
/*     */         } 
/*     */         
/*  80 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(title, x, y + this.fontHeight * 0, 16777215);
/*  81 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(this.caption, x, y + this.fontHeight * 1, 8421504);
/*  82 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(this.body1, x, y + this.fontHeight * 2, 16777215);
/*  83 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(this.body2, x, y + this.fontHeight * 3, 16777215);
/*  84 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(this.body3, x, y + this.fontHeight * 4, 16777215);
/*  85 */         (GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this)).fontRendererObj.drawString(this.time, x, y + this.fontHeight * 5, 8421504);
/*     */         
/*  87 */         if (mouseX > x && mouseX < x + listWidth && mouseY > y && 
/*  88 */           mouseY < y + slotHeight) {
/*  89 */           GuiWDLUpdates.drawRect(x - 2, y - 2, x + listWidth - 3, y + slotHeight + 2, 
/*  90 */               536870911);
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/*  97 */         if (relativeY > 0 && relativeY < GuiWDLUpdates.UpdateList.this.slotHeight) {
/*  98 */           GuiWDLUpdates.UpdateList.this.mc.displayGuiScreen(new GuiWDLUpdates.GuiWDLSingleUpdate(GuiWDLUpdates.UpdateList.access$5(GuiWDLUpdates.UpdateList.this), 
/*  99 */                 this.release));
/*     */           
/* 101 */           GuiWDLUpdates.UpdateList.this.mc.getSoundHandler().playSound(
/* 102 */               (ISound)PositionedSoundRecord.getMasterRecord(
/* 103 */                 SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 104 */           return true;
/*     */         } 
/* 106 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void regenerateVersionList() {
/* 137 */       this.displayedVersions = new ArrayList<>();
/*     */       
/* 139 */       if (WDLUpdateChecker.hasNewVersion()) {
/* 140 */         this.recomendedRelease = WDLUpdateChecker.getRecomendedRelease();
/*     */       } else {
/* 142 */         this.recomendedRelease = null;
/*     */       } 
/*     */       
/* 145 */       List<Release> releases = WDLUpdateChecker.getReleases();
/*     */       
/* 147 */       if (releases == null) {
/*     */         return;
/*     */       }
/*     */       
/* 151 */       for (Release release : releases) {
/* 152 */         this.displayedVersions.add(new VersionEntry(release));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public VersionEntry getListEntry(int index) {
/* 158 */       return this.displayedVersions.get(index);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 163 */       return this.displayedVersions.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 168 */       VersionEntry entry = getListEntry(slotIndex);
/*     */       
/* 170 */       return "1.11a-beta1".equals(entry.release.tag);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListWidth() {
/* 175 */       return this.width - 30;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 180 */       return this.width - 10;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiWDLUpdates(GuiScreen parent) {
/* 189 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 194 */     this.list = new UpdateList();
/*     */     
/* 196 */     this.updateMinecraftVersionButton = new GuiButton(0, 
/* 197 */         this.width / 2 - 155, 18, 150, 20, 
/* 198 */         getUpdateMinecraftVersionText());
/* 199 */     this.buttonList.add(this.updateMinecraftVersionButton);
/* 200 */     this.updateAllowBetasButton = new GuiButton(1, 
/* 201 */         this.width / 2 + 5, 18, 150, 20, getAllowBetasText());
/* 202 */     this.buttonList.add(this.updateAllowBetasButton);
/*     */     
/* 204 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/* 205 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 210 */     if (button.id == 0) {
/* 211 */       cycleUpdateMinecraftVersion();
/*     */     }
/* 213 */     if (button.id == 1) {
/* 214 */       cycleAllowBetas();
/*     */     }
/* 216 */     if (button.id == 100) {
/* 217 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 223 */     WDL.saveGlobalProps();
/*     */   }
/*     */   
/*     */   private void cycleUpdateMinecraftVersion() {
/* 227 */     String prop = WDL.globalProps.getProperty("UpdateMinecraftVersion");
/*     */     
/* 229 */     if (prop.equals("client")) {
/* 230 */       WDL.globalProps.setProperty("UpdateMinecraftVersion", "server");
/* 231 */     } else if (prop.equals("server")) {
/* 232 */       WDL.globalProps.setProperty("UpdateMinecraftVersion", "any");
/*     */     } else {
/* 234 */       WDL.globalProps.setProperty("UpdateMinecraftVersion", "client");
/*     */     } 
/*     */     
/* 237 */     this.updateMinecraftVersionButton.displayString = getUpdateMinecraftVersionText();
/*     */   }
/*     */   
/*     */   private void cycleAllowBetas() {
/* 241 */     if (WDL.globalProps.getProperty("UpdateAllowBetas").equals("true")) {
/* 242 */       WDL.globalProps.setProperty("UpdateAllowBetas", "false");
/*     */     } else {
/* 244 */       WDL.globalProps.setProperty("UpdateAllowBetas", "true");
/*     */     } 
/*     */     
/* 247 */     this.updateAllowBetasButton.displayString = getAllowBetasText();
/*     */   }
/*     */   
/*     */   private String getUpdateMinecraftVersionText() {
/* 251 */     return I18n.format("wdl.gui.updates.updateMinecraftVersion." + 
/* 252 */         WDL.globalProps.getProperty("UpdateMinecraftVersion"), new Object[] {
/* 253 */           WDL.getMinecraftVersion() });
/*     */   }
/*     */   
/*     */   private String getAllowBetasText() {
/* 257 */     return I18n.format("wdl.gui.updates.updateAllowBetas." + 
/* 258 */         WDL.globalProps.getProperty("UpdateAllowBetas"), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 267 */     this.list.mouseClicked(mouseX, mouseY, mouseButton);
/* 268 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 276 */     super.handleMouseInput();
/* 277 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 282 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 285 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 290 */     if (this.list == null) {
/*     */       return;
/*     */     }
/*     */     
/* 294 */     this.list.regenerateVersionList();
/* 295 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 297 */     if (!WDLUpdateChecker.hasFinishedUpdateCheck()) {
/* 298 */       drawCenteredString(this.fontRendererObj, 
/* 299 */           I18n.format("wdl.gui.updates.pleaseWait", new Object[0]), this.width / 2, 
/* 300 */           this.height / 2, 16777215);
/* 301 */     } else if (WDLUpdateChecker.hasUpdateCheckFailed()) {
/* 302 */       String reason = WDLUpdateChecker.getUpdateCheckFailReason();
/*     */       
/* 304 */       drawCenteredString(this.fontRendererObj, 
/* 305 */           I18n.format("wdl.gui.updates.checkFailed", new Object[0]), this.width / 2, 
/* 306 */           this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2, 16733525);
/* 307 */       drawCenteredString(this.fontRendererObj, I18n.format(reason, new Object[0]), this.width / 2, 
/* 308 */           this.height / 2 + this.fontRendererObj.FONT_HEIGHT / 2, 16733525);
/*     */     } 
/*     */     
/* 311 */     drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.updates.title", new Object[0]), 
/* 312 */         this.width / 2, 8, 16777215);
/*     */     
/* 314 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 316 */     if (this.updateMinecraftVersionButton.isMouseOver()) {
/* 317 */       Utils.drawGuiInfoBox(
/* 318 */           I18n.format("wdl.gui.updates.updateMinecraftVersion.description", new Object[] {
/* 319 */               WDL.getMinecraftVersion()
/* 320 */             }), this.width, this.height, 32);
/* 321 */     } else if (this.updateAllowBetasButton.isMouseOver()) {
/* 322 */       Utils.drawGuiInfoBox(
/* 323 */           I18n.format("wdl.gui.updates.updateAllowBetas.description", new Object[0]), 
/* 324 */           this.width, this.height, 32);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildVersionInfo(Release release) {
/* 332 */     String type = "?", mainVersion = "?", supportedVersions = "?";
/*     */     
/* 334 */     if (release.hiddenInfo != null) {
/* 335 */       type = release.hiddenInfo.loader;
/* 336 */       mainVersion = release.hiddenInfo.mainMinecraftVersion;
/*     */       
/* 338 */       String[] versions = release.hiddenInfo.supportedMinecraftVersions;
/* 339 */       if (versions.length == 1) {
/* 340 */         supportedVersions = I18n.format(
/* 341 */             "wdl.gui.updates.update.version.listSingle", new Object[] {
/* 342 */               versions[0] });
/* 343 */       } else if (versions.length == 2) {
/* 344 */         supportedVersions = I18n.format(
/* 345 */             "wdl.gui.updates.update.version.listDouble", new Object[] {
/* 346 */               versions[0], versions[1] });
/*     */       } else {
/* 348 */         StringBuilder builder = new StringBuilder();
/*     */         
/* 350 */         for (int i = 0; i < versions.length; i++) {
/* 351 */           if (i == 0) {
/* 352 */             builder.append(I18n.format(
/* 353 */                   "wdl.gui.updates.update.version.listStart", new Object[] {
/* 354 */                     versions[i] }));
/* 355 */           } else if (i == versions.length - 1) {
/* 356 */             builder.append(I18n.format(
/* 357 */                   "wdl.gui.updates.update.version.listEnd", new Object[] {
/* 358 */                     versions[i] }));
/*     */           } else {
/* 360 */             builder.append(I18n.format(
/* 361 */                   "wdl.gui.updates.update.version.listMiddle", new Object[] {
/* 362 */                     versions[i]
/*     */                   }));
/*     */           } 
/*     */         } 
/* 366 */         supportedVersions = builder.toString();
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     return I18n.format("wdl.gui.updates.update.version", new Object[] { type, mainVersion, 
/* 371 */           supportedVersions });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildReleaseTitle(Release release) {
/* 378 */     String version = release.tag;
/* 379 */     String mcVersion = "?";
/*     */     
/* 381 */     if (release.hiddenInfo != null) {
/* 382 */       mcVersion = release.hiddenInfo.mainMinecraftVersion;
/*     */     }
/*     */     
/* 385 */     if (release.prerelease) {
/* 386 */       return I18n.format("wdl.gui.updates.update.title.prerelease", new Object[] { version, mcVersion });
/*     */     }
/* 388 */     return I18n.format("wdl.gui.updates.update.title.release", new Object[] { version, mcVersion });
/*     */   }
/*     */ 
/*     */   
/*     */   private class GuiWDLSingleUpdate
/*     */     extends GuiScreen
/*     */   {
/*     */     private final GuiWDLUpdates parent;
/*     */     
/*     */     private final Release release;
/*     */     
/*     */     private TextList list;
/*     */     
/*     */     public GuiWDLSingleUpdate(GuiWDLUpdates parent, Release releaseToShow) {
/* 402 */       this.parent = parent;
/* 403 */       this.release = releaseToShow;
/*     */     }
/*     */ 
/*     */     
/*     */     public void initGui() {
/* 408 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 155, 
/* 409 */             18, 150, 20, I18n.format("wdl.gui.updates.update.viewOnline", new Object[0])));
/* 410 */       if (this.release.hiddenInfo != null) {
/* 411 */         this.buttonList.add(new GuiButton(1, this.width / 2 + 5, 
/* 412 */               18, 150, 20, I18n.format("wdl.gui.updates.update.viewForumPost", new Object[0])));
/*     */       }
/* 414 */       this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/* 415 */             this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */       
/* 417 */       this.list = new TextList(this.mc, this.width, this.height, 39, 32);
/*     */       
/* 419 */       this.list.addLine(GuiWDLUpdates.this.buildReleaseTitle(this.release));
/* 420 */       this.list.addLine(I18n.format("wdl.gui.updates.update.releaseDate", new Object[] { this.release.date }));
/* 421 */       this.list.addLine(GuiWDLUpdates.this.buildVersionInfo(this.release));
/* 422 */       this.list.addBlankLine();
/* 423 */       this.list.addLine(this.release.textOnlyBody);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void actionPerformed(GuiButton button) throws IOException {
/* 428 */       if (button.id == 0) {
/* 429 */         Utils.openLink(this.release.URL);
/*     */       }
/* 431 */       if (button.id == 1) {
/* 432 */         Utils.openLink(this.release.hiddenInfo.post);
/*     */       }
/* 434 */       if (button.id == 100) {
/* 435 */         this.mc.displayGuiScreen(this.parent);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 445 */       this.list.mouseClicked(mouseX, mouseY, mouseButton);
/* 446 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleMouseInput() throws IOException {
/* 454 */       super.handleMouseInput();
/* 455 */       this.list.handleMouseInput();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 460 */       if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */         return;
/*     */       }
/* 463 */       super.mouseReleased(mouseX, mouseY, state);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 468 */       if (this.list == null) {
/*     */         return;
/*     */       }
/*     */       
/* 472 */       this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */       
/* 474 */       drawCenteredString(this.fontRendererObj, GuiWDLUpdates.this.buildReleaseTitle(this.release), 
/* 475 */           this.width / 2, 8, 16777215);
/*     */       
/* 477 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLUpdates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
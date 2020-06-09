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
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import wdl.api.IWDLModWithGui;
/*     */ import wdl.api.WDLApi;
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
/*     */ public class GuiWDLExtensions
/*     */   extends GuiScreen
/*     */ {
/*     */   private int bottomLocation;
/*     */   private static final int TOP_HEIGHT = 23;
/*     */   private static final int MIDDLE_HEIGHT = 19;
/*     */   private static final int BOTTOM_HEIGHT = 32;
/*  54 */   private int selectedModIndex = -1; private final GuiScreen parent; private ModList list; private ModDetailList detailsList; private boolean dragging;
/*     */   private int dragOffset;
/*     */   
/*     */   private class ModList extends GuiListExtended { private List<GuiListExtended.IGuiListEntry> entries;
/*     */     
/*  59 */     public ModList() { super(GuiWDLExtensions.this.mc, GuiWDLExtensions.this.width, GuiWDLExtensions.this.bottomLocation, 23, GuiWDLExtensions.this.bottomLocation, 22);
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
/* 184 */       this.entries = new ArrayList<GuiListExtended.IGuiListEntry>()
/*     */         {
/*     */         
/*     */         };
/*     */       this.showSelectionBox = true; }
/*     */ 
/*     */     
/*     */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 192 */       this.height = this.bottom = GuiWDLExtensions.this.bottomLocation;
/*     */       
/* 194 */       super.drawScreen(mouseX, mouseY, partialTicks); }
/*     */     private class ModEntry implements GuiListExtended.IGuiListEntry {
/*     */       public final WDLApi.ModInfo<?> mod;
/*     */       private final String modDescription;
/*     */       private String label;
/* 199 */       private GuiButton button; private GuiButton disableButton; public ModEntry(WDLApi.ModInfo<?> mod) { this.mod = mod; String name = mod.getDisplayName(); this.modDescription = I18n.format("wdl.gui.extensions.modVersion", new Object[] { name, mod.version }); if (!mod.isEnabled()) { this.label = TextFormatting.GRAY + TextFormatting.ITALIC + this.modDescription; } else { this.label = this.modDescription; }  if (mod.mod instanceof IWDLModWithGui) { IWDLModWithGui guiMod = (IWDLModWithGui)mod.mod; String buttonName = guiMod.getButtonName(); if (buttonName == null || buttonName.isEmpty()) buttonName = I18n.format("wdl.gui.extensions.defaultSettingsButtonText", new Object[0]);  this.button = new GuiButton(0, 0, 0, 80, 20, guiMod.getButtonName()); }  this.disableButton = new GuiButton(0, 0, 0, 80, 20, I18n.format("wdl.gui.extensions." + (mod.isEnabled() ? "enabled" : "disabled"), new Object[0])); } public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) { if (this.button != null && this.button.mousePressed(GuiWDLExtensions.ModList.this.mc, x, y)) { if (this.mod.mod instanceof IWDLModWithGui) ((IWDLModWithGui)this.mod.mod).openGui(GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this));  this.button.playPressSound(GuiWDLExtensions.ModList.this.mc.getSoundHandler()); return true; }  if (this.disableButton.mousePressed(GuiWDLExtensions.ModList.this.mc, x, y)) { this.mod.toggleEnabled(); this.disableButton.playPressSound(GuiWDLExtensions.ModList.this.mc.getSoundHandler()); this.disableButton.displayString = I18n.format("wdl.gui.extensions." + (this.mod.isEnabled() ? "enabled" : "disabled"), new Object[0]); if (!this.mod.isEnabled()) { this.label = TextFormatting.GRAY + TextFormatting.ITALIC + this.modDescription; } else { this.label = this.modDescription; }  return true; }  if ((GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).selectedModIndex != slotIndex) { (GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).selectedModIndex = slotIndex; GuiWDLExtensions.ModList.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F)); GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this).updateDetailsList(this.mod); return true; }  return false; } public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) { if (this.button != null) this.button.mouseReleased(x, y);  } public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {} public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) { if (this.button != null) { this.button.xPosition = (GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).width - 180; this.button.yPosition = p_192634_3_ - 1; this.button.func_191745_a(GuiWDLExtensions.ModList.this.mc, GuiWDLExtensions.ModList.this.mouseX, GuiWDLExtensions.ModList.this.mouseY, p_192634_9_); }  this.disableButton.xPosition = (GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).width - 92; this.disableButton.yPosition = p_192634_3_ - 1; this.disableButton.func_191745_a(GuiWDLExtensions.ModList.this.mc, GuiWDLExtensions.ModList.this.mouseX, GuiWDLExtensions.ModList.this.mouseY, p_192634_9_); int centerY = p_192634_3_ + GuiWDLExtensions.ModList.this.slotHeight / 2 - (GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).fontRendererObj.FONT_HEIGHT / 2; (GuiWDLExtensions.ModList.access$4(GuiWDLExtensions.ModList.this)).fontRendererObj.drawString(this.label, p_192634_2_, centerY, 16777215); } } public GuiListExtended.IGuiListEntry getListEntry(int index) { return this.entries.get(index); }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 204 */       return this.entries.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 209 */       return (slotIndex == GuiWDLExtensions.this.selectedModIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListWidth() {
/* 214 */       return GuiWDLExtensions.this.width - 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 219 */       return GuiWDLExtensions.this.width - 10;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleMouseInput() {
/* 224 */       if (this.mouseY < GuiWDLExtensions.this.bottomLocation) {
/* 225 */         super.handleMouseInput();
/*     */       }
/*     */     } }
/*     */ 
/*     */   
/*     */   private class ModDetailList
/*     */     extends TextList
/*     */   {
/*     */     public ModDetailList() {
/* 234 */       super(GuiWDLExtensions.this.mc, GuiWDLExtensions.this.width, GuiWDLExtensions.this.height - GuiWDLExtensions.this.bottomLocation, 19, 32);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 239 */       GlStateManager.translate(0.0F, GuiWDLExtensions.this.bottomLocation, 0.0F);
/*     */       
/* 241 */       this.height = GuiWDLExtensions.this.height - GuiWDLExtensions.this.bottomLocation;
/* 242 */       this.bottom = this.height - 32;
/*     */       
/* 244 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */       
/* 246 */       GuiWDLExtensions.drawCenteredString(GuiWDLExtensions.this.fontRendererObj, 
/* 247 */           I18n.format("wdl.gui.extensions.detailsCaption", new Object[0]), 
/* 248 */           GuiWDLExtensions.this.width / 2, 5, 16777215);
/*     */       
/* 250 */       GlStateManager.translate(0.0F, -GuiWDLExtensions.this.bottomLocation, 0.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void overlayBackground(int y1, int y2, int alpha1, int alpha2) {
/* 261 */       if (y1 == 0) {
/* 262 */         super.overlayBackground(y1, y2, alpha1, alpha2);
/*     */         return;
/*     */       } 
/* 265 */       GlStateManager.translate(0.0F, -GuiWDLExtensions.this.bottomLocation, 0.0F);
/*     */       
/* 267 */       super.overlayBackground(y1 + GuiWDLExtensions.this.bottomLocation, y2 + 
/* 268 */           GuiWDLExtensions.this.bottomLocation, alpha1, alpha2);
/*     */       
/* 270 */       GlStateManager.translate(0.0F, GuiWDLExtensions.this.bottomLocation, 0.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleMouseInput() {
/* 276 */       this.mouseY -= GuiWDLExtensions.this.bottomLocation;
/*     */       
/* 278 */       if (this.mouseY > 0) {
/* 279 */         super.handleMouseInput();
/*     */       }
/*     */       
/* 282 */       this.mouseY += GuiWDLExtensions.this.bottomLocation;
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateDetailsList(WDLApi.ModInfo<?> selectedMod) {
/* 287 */     this.detailsList.clearLines();
/*     */     
/* 289 */     if (selectedMod != null) {
/* 290 */       String info = selectedMod.getInfo();
/*     */       
/* 292 */       this.detailsList.addLine(info);
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
/*     */   public void initGui() {
/* 315 */     this.bottomLocation = this.height - 100;
/* 316 */     this.dragging = false;
/*     */     
/* 318 */     this.list = new ModList();
/* 319 */     this.detailsList = new ModDetailList();
/*     */     
/* 321 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 29, 
/* 322 */           I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 327 */     if (button.id == 0) {
/* 328 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiWDLExtensions(GuiScreen parent) {
/* 335 */     this.dragging = false;
/*     */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 343 */     super.handleMouseInput();
/* 344 */     this.list.handleMouseInput();
/* 345 */     this.detailsList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 351 */     if (mouseY > this.bottomLocation && mouseY < this.bottomLocation + 19) {
/* 352 */       this.dragging = true;
/* 353 */       this.dragOffset = mouseY - this.bottomLocation;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 358 */     if (this.list.mouseClicked(mouseX, mouseY, mouseButton)) {
/*     */       return;
/*     */     }
/* 361 */     if (this.detailsList.mouseClicked(mouseX, mouseY, mouseButton)) {
/*     */       return;
/*     */     }
/* 364 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 369 */     this.dragging = false;
/*     */     
/* 371 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 374 */     if (this.detailsList.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 377 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 383 */     if (this.dragging) {
/* 384 */       this.bottomLocation = mouseY - this.dragOffset;
/*     */     }
/*     */ 
/*     */     
/* 388 */     if (this.bottomLocation < 31) {
/* 389 */       this.bottomLocation = 31;
/*     */     }
/* 391 */     if (this.bottomLocation > this.height - 32 - 8) {
/* 392 */       this.bottomLocation = this.height - 32 - 8;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 398 */     drawDefaultBackground();
/*     */ 
/*     */     
/* 401 */     if (this.bottomLocation < 56) {
/* 402 */       this.bottomLocation = 56;
/*     */     }
/* 404 */     if (this.bottomLocation > this.height - 19 - 32 - 33) {
/* 405 */       this.bottomLocation = this.height - 19 - 32 - 33;
/*     */     }
/*     */     
/* 408 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 409 */     this.detailsList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 411 */     drawCenteredString(this.fontRendererObj, 
/* 412 */         I18n.format("wdl.gui.extensions.title", new Object[0]), this.width / 2, 8, 
/* 413 */         16777215);
/*     */     
/* 415 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
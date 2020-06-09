/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.MessageTypeCategory;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessages;
/*     */ import wdl.api.IWDLMessageType;
/*     */ 
/*     */ public class GuiWDLMessages extends GuiScreen {
/*  23 */   private String hoveredButtonDescription = null; private GuiScreen parent; private GuiMessageTypeList list;
/*     */   private GuiButton enableAllButton;
/*     */   private GuiButton resetButton;
/*     */   
/*     */   private class GuiMessageTypeList extends GuiListExtended { private List<GuiListExtended.IGuiListEntry> entries;
/*     */     
/*  29 */     public GuiMessageTypeList() { super(GuiWDLMessages.this.mc, GuiWDLMessages.this.width, GuiWDLMessages.this.height, 39, GuiWDLMessages.this.height - 32, 20);
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
/* 150 */       this.entries = new ArrayList<GuiListExtended.IGuiListEntry>() {  }
/*     */         ; } private class CategoryEntry implements GuiListExtended.IGuiListEntry {
/*     */       private final GuiButton button; private final MessageTypeCategory category; public CategoryEntry(MessageTypeCategory category) { this.category = category;
/*     */         this.button = new GuiButton(0, 0, 0, 80, 20, ""); } public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) { if (this.button.mousePressed(GuiWDLMessages.GuiMessageTypeList.this.mc, x, y)) {
/*     */           WDLMessages.toggleGroupEnabled(this.category);
/*     */           this.button.playPressSound(GuiWDLMessages.GuiMessageTypeList.this.mc.getSoundHandler());
/*     */           return true;
/*     */         } 
/*     */         return false; } public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {} public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */       public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) { GuiWDLMessages.drawCenteredString((GuiWDLMessages.GuiMessageTypeList.access$4(GuiWDLMessages.GuiMessageTypeList.this)).fontRendererObj, this.category.getDisplayName(), (GuiWDLMessages.GuiMessageTypeList.access$4(GuiWDLMessages.GuiMessageTypeList.this)).width / 2 - 40, p_192634_3_ + GuiWDLMessages.GuiMessageTypeList.this.slotHeight - GuiWDLMessages.GuiMessageTypeList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
/*     */         this.button.xPosition = (GuiWDLMessages.GuiMessageTypeList.access$4(GuiWDLMessages.GuiMessageTypeList.this)).width / 2 + 20;
/*     */         this.button.yPosition = p_192634_3_;
/*     */         this.button.displayString = I18n.format("wdl.gui.messages.group." + WDLMessages.isGroupEnabled(this.category), new Object[0]);
/*     */         this.button.enabled = WDLMessages.enableAllMessages;
/*     */         this.button.func_191745_a(GuiWDLMessages.GuiMessageTypeList.this.mc, GuiWDLMessages.GuiMessageTypeList.this.mouseX, GuiWDLMessages.GuiMessageTypeList.this.mouseY, p_192634_9_); } }
/* 165 */     public GuiListExtended.IGuiListEntry getListEntry(int index) { return this.entries.get(index); }
/*     */     private class MessageTypeEntry implements GuiListExtended.IGuiListEntry {
/*     */       private final GuiButton button;
/*     */       private final IWDLMessageType type;
/*     */       private final MessageTypeCategory category; public MessageTypeEntry(IWDLMessageType type, MessageTypeCategory category) { this.type = type; this.button = new GuiButton(0, 0, 0, type.toString()); this.category = category; } public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) { if (this.button.mousePressed(GuiWDLMessages.GuiMessageTypeList.this.mc, x, y)) { WDLMessages.toggleEnabled(this.type); this.button.playPressSound(GuiWDLMessages.GuiMessageTypeList.this.mc.getSoundHandler()); return true; }  return false; } public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {} public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {} public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) { this.button.xPosition = (GuiWDLMessages.GuiMessageTypeList.access$4(GuiWDLMessages.GuiMessageTypeList.this)).width / 2 - 100; this.button.yPosition = p_192634_3_; this.button.displayString = I18n.format("wdl.gui.messages.message." + WDLMessages.isEnabled(this.type), new Object[] { this.type.getDisplayName() }); this.button.enabled = (WDLMessages.enableAllMessages && WDLMessages.isGroupEnabled(this.category)); this.button.func_191745_a(GuiWDLMessages.GuiMessageTypeList.this.mc, GuiWDLMessages.GuiMessageTypeList.this.mouseX, GuiWDLMessages.GuiMessageTypeList.this.mouseY, p_192634_9_); if (this.button.isMouseOver())
/* 170 */           (GuiWDLMessages.GuiMessageTypeList.access$4(GuiWDLMessages.GuiMessageTypeList.this)).hoveredButtonDescription = this.type.getDescription();  } } protected int getSize() { return this.entries.size(); }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiWDLMessages(GuiScreen parent) {
/* 179 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 187 */     this.enableAllButton = new GuiButton(100, this.width / 2 - 155, 18, 150, 
/* 188 */         20, getAllEnabledText());
/* 189 */     this.buttonList.add(this.enableAllButton);
/* 190 */     this.resetButton = new GuiButton(101, this.width / 2 + 5, 18, 150, 20, 
/* 191 */         I18n.format("wdl.gui.messages.reset", new Object[0]));
/* 192 */     this.buttonList.add(this.resetButton);
/*     */     
/* 194 */     this.list = new GuiMessageTypeList();
/*     */     
/* 196 */     this.buttonList.add(new GuiButton(102, this.width / 2 - 100, 
/* 197 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 202 */     if (!button.enabled) {
/*     */       return;
/*     */     }
/*     */     
/* 206 */     if (button.id == 100) {
/*     */       
/* 208 */       WDLMessages.enableAllMessages ^= 0x1;
/*     */       
/* 210 */       WDL.baseProps.setProperty("Messages.enableAll", 
/* 211 */           Boolean.toString(WDLMessages.enableAllMessages));
/*     */       
/* 213 */       button.displayString = getAllEnabledText();
/* 214 */     } else if (button.id == 101) {
/* 215 */       this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, 
/* 216 */             I18n.format("wdl.gui.messages.reset.confirm.title", new Object[0]), 
/* 217 */             I18n.format("wdl.gui.messages.reset.confirm.subtitle", new Object[0]), 
/* 218 */             101));
/* 219 */     } else if (button.id == 102) {
/* 220 */       this.mc.displayGuiScreen(this.parent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 226 */     if (result && 
/* 227 */       id == 101) {
/* 228 */       WDLMessages.resetEnabledToDefaults();
/*     */     }
/*     */ 
/*     */     
/* 232 */     this.mc.displayGuiScreen(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 237 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 245 */     super.handleMouseInput();
/* 246 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 252 */     if (this.list.mouseClicked(mouseX, mouseY, mouseButton)) {
/*     */       return;
/*     */     }
/* 255 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 260 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 263 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 268 */     this.hoveredButtonDescription = null;
/*     */     
/* 270 */     drawDefaultBackground();
/* 271 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 273 */     drawCenteredString(this.fontRendererObj, 
/* 274 */         I18n.format("wdl.gui.messages.message.title", new Object[0]), 
/* 275 */         this.width / 2, 8, 16777215);
/*     */     
/* 277 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 279 */     if (this.hoveredButtonDescription != null) {
/* 280 */       Utils.drawGuiInfoBox(this.hoveredButtonDescription, this.width, this.height, 48);
/* 281 */     } else if (this.enableAllButton.isMouseOver()) {
/* 282 */       Utils.drawGuiInfoBox(
/* 283 */           I18n.format("wdl.gui.messages.all.description", new Object[0]), this.width, 
/* 284 */           this.height, 48);
/* 285 */     } else if (this.resetButton.isMouseOver()) {
/* 286 */       Utils.drawGuiInfoBox(
/* 287 */           I18n.format("wdl.gui.messages.reset.description", new Object[0]), this.width, 
/* 288 */           this.height, 48);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAllEnabledText() {
/* 296 */     return I18n.format("wdl.gui.messages.all." + 
/* 297 */         WDLMessages.enableAllMessages, new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
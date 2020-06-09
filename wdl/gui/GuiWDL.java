/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLPluginChannels;
/*     */ import wdl.update.WDLUpdateChecker;
/*     */ 
/*     */ 
/*     */ public class GuiWDL
/*     */   extends GuiScreen
/*     */ {
/*  20 */   private String displayedTooltip = null;
/*     */   
/*     */   private class GuiWDLButtonList extends GuiListExtended { private List<GuiListExtended.IGuiListEntry> entries;
/*     */     
/*     */     public GuiWDLButtonList() {
/*  25 */       super(GuiWDL.this.mc, GuiWDL.this.width, GuiWDL.this.height, 39, GuiWDL.this.height - 32, 20);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       this.entries = new ArrayList<GuiListExtended.IGuiListEntry>()
/*     */         {
/*     */         
/*     */         };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiListExtended.IGuiListEntry getListEntry(int index) {
/* 127 */       return this.entries.get(index);
/*     */     }
/*     */     private class ButtonEntry implements GuiListExtended.IGuiListEntry {
/*     */       private final GuiButton button;
/*     */       private final GuiScreen toOpen; private final String tooltip; public ButtonEntry(String key, GuiScreen toOpen, boolean needsPerms) { this.button = new GuiButton(0, 0, 0, I18n.format("wdl.gui.wdl." + key + ".name", new Object[0])); this.toOpen = toOpen; if (needsPerms) this.button.enabled = WDLPluginChannels.canDownloadAtAll();  this.tooltip = I18n.format("wdl.gui.wdl." + key + ".description", new Object[0]); } public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) { if (this.button.mousePressed(GuiWDL.GuiWDLButtonList.this.mc, x, y)) { GuiWDL.GuiWDLButtonList.this.mc.displayGuiScreen(this.toOpen); this.button.playPressSound(GuiWDL.GuiWDLButtonList.this.mc.getSoundHandler()); return true; }  return false; } public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {} public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {} public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) { this.button.xPosition = (GuiWDL.GuiWDLButtonList.access$3(GuiWDL.GuiWDLButtonList.this)).width / 2 - 100; this.button.yPosition = p_192634_3_; this.button.func_191745_a(GuiWDL.GuiWDLButtonList.this.mc, GuiWDL.GuiWDLButtonList.this.mouseX, GuiWDL.GuiWDLButtonList.this.mouseY, p_192634_9_); if (this.button.isMouseOver())
/* 132 */           (GuiWDL.GuiWDLButtonList.access$3(GuiWDL.GuiWDLButtonList.this)).displayedTooltip = this.tooltip;  } } protected int getSize() { return this.entries.size(); }
/*     */      }
/*     */ 
/*     */   
/* 136 */   private String title = "";
/*     */   
/*     */   private GuiScreen parent;
/*     */   
/*     */   private GuiTextField worldname;
/*     */   private GuiWDLButtonList list;
/*     */   
/*     */   public GuiWDL(GuiScreen parent) {
/* 144 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 152 */     if (WDL.isMultiworld && WDL.worldName.isEmpty()) {
/* 153 */       this.mc.displayGuiScreen(new GuiWDLMultiworldSelect(
/* 154 */             I18n.format("wdl.gui.multiworldSelect.title.changeOptions", new Object[0]), 
/* 155 */             new GuiWDLMultiworldSelect.WorldSelectionCallback()
/*     */             {
/*     */               public void onWorldSelected(String selectedWorld) {
/* 158 */                 WDL.worldName = selectedWorld;
/* 159 */                 WDL.isMultiworld = true;
/* 160 */                 WDL.propsFound = true;
/*     */                 
/* 162 */                 WDL.worldProps = WDL.loadWorldProps(selectedWorld);
/* 163 */                 GuiWDL.this.mc.displayGuiScreen(GuiWDL.this);
/*     */               }
/*     */ 
/*     */               
/*     */               public void onCancel() {
/* 168 */                 GuiWDL.this.mc.displayGuiScreen(null);
/*     */               }
/*     */             }));
/*     */       
/*     */       return;
/*     */     } 
/* 174 */     if (!WDL.propsFound) {
/* 175 */       this.mc.displayGuiScreen(new GuiWDLMultiworld(new GuiWDLMultiworld.MultiworldCallback()
/*     */             {
/*     */               public void onSelect(boolean enableMutliworld) {
/* 178 */                 WDL.isMultiworld = enableMutliworld;
/*     */                 
/* 180 */                 if (WDL.isMultiworld) {
/*     */ 
/*     */                   
/* 183 */                   GuiWDL.this.mc.displayGuiScreen(new GuiWDLMultiworldSelect(
/* 184 */                         I18n.format("wdl.gui.multiworldSelect.title.changeOptions", new Object[0]), 
/* 185 */                         new GuiWDLMultiworldSelect.WorldSelectionCallback()
/*     */                         {
/*     */                           public void onWorldSelected(String selectedWorld) {
/* 188 */                             WDL.worldName = selectedWorld;
/* 189 */                             WDL.isMultiworld = true;
/* 190 */                             WDL.propsFound = true;
/*     */                             
/* 192 */                             WDL.worldProps = WDL.loadWorldProps(selectedWorld);
/* 193 */                             (GuiWDL.null.access$0(GuiWDL.null.this)).mc.displayGuiScreen(GuiWDL.null.access$0(GuiWDL.null.this));
/*     */                           }
/*     */ 
/*     */                           
/*     */                           public void onCancel() {
/* 198 */                             (GuiWDL.null.access$0(GuiWDL.null.this)).mc.displayGuiScreen(null);
/*     */                           }
/*     */                         }));
/*     */                 } else {
/* 202 */                   WDL.baseProps.setProperty("LinkedWorlds", "");
/* 203 */                   WDL.saveProps();
/* 204 */                   WDL.propsFound = true;
/*     */                   
/* 206 */                   GuiWDL.this.mc.displayGuiScreen(GuiWDL.this);
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public void onCancel() {
/* 212 */                 GuiWDL.this.mc.displayGuiScreen(null);
/*     */               }
/*     */             }));
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     this.buttonList.clear();
/* 219 */     this.title = I18n.format("wdl.gui.wdl.title", new Object[] {
/* 220 */           WDL.baseFolderName.replace('@', ':')
/*     */         });
/* 222 */     if (WDL.baseProps.getProperty("ServerName").isEmpty()) {
/* 223 */       WDL.baseProps.setProperty("ServerName", WDL.getServerName());
/*     */     }
/*     */     
/* 226 */     this.worldname = new GuiTextField(42, this.fontRendererObj, 
/* 227 */         this.width / 2 - 155, 19, 150, 18);
/* 228 */     this.worldname.setText(WDL.baseProps.getProperty("ServerName"));
/*     */     
/* 230 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/* 231 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */     
/* 233 */     this.list = new GuiWDLButtonList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 242 */     if (!button.enabled) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     if (button.id == 100) {
/* 247 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 253 */     if (this.worldname != null) {
/* 254 */       WDL.baseProps.setProperty("ServerName", this.worldname.getText());
/*     */       
/* 256 */       WDL.saveProps();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 266 */     this.list.mouseClicked(mouseX, mouseY, mouseButton);
/* 267 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 268 */     this.worldname.mouseClicked(mouseX, mouseY, mouseButton);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 294 */     super.keyTyped(typedChar, keyCode);
/* 295 */     this.worldname.textboxKeyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 303 */     this.worldname.updateCursorCounter();
/* 304 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 312 */     drawDefaultBackground();
/*     */     
/* 314 */     this.displayedTooltip = null;
/*     */     
/* 316 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 318 */     drawCenteredString(this.fontRendererObj, this.title, 
/* 319 */         this.width / 2, 8, 16777215);
/* 320 */     String name = I18n.format("wdl.gui.wdl.worldname", new Object[0]);
/* 321 */     drawString(this.fontRendererObj, name, this.worldname.xPosition - 
/* 322 */         this.fontRendererObj.getStringWidth(String.valueOf(name) + " "), 26, 16777215);
/* 323 */     this.worldname.drawTextBox();
/*     */     
/* 325 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 327 */     Utils.drawGuiInfoBox(this.displayedTooltip, this.width, this.height, 48);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package wdl.gui;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.EntityUtils;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessageTypes;
/*     */ import wdl.WDLMessages;
/*     */ import wdl.WDLPluginChannels;
/*     */ import wdl.api.IWDLMessageType;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLEntities
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiEntityList entityList;
/*     */   private GuiScreen parent;
/*     */   private GuiButton rangeModeButton;
/*     */   private GuiButton presetsButton;
/*     */   private String mode;
/*     */   
/*     */   private class GuiEntityList
/*     */     extends GuiListExtended
/*     */   {
/*     */     private int largestWidth;
/*     */     private int totalWidth;
/*  37 */     private List<GuiListExtended.IGuiListEntry> entries = new ArrayList<GuiListExtended.IGuiListEntry>()
/*     */       {
/*     */       
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private class CategoryEntry
/*     */       implements GuiListExtended.IGuiListEntry
/*     */     {
/*     */       private final String group;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final int labelWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final GuiButton enableGroupButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private boolean groupEnabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public CategoryEntry(String group) {
/* 101 */         this.group = group;
/* 102 */         this.labelWidth = GuiWDLEntities.GuiEntityList.this.mc.fontRendererObj.getStringWidth(group);
/*     */         
/* 104 */         this.groupEnabled = WDL.worldProps.getProperty("EntityGroup." + 
/* 105 */             group + ".Enabled", "true").equals("true");
/*     */         
/* 107 */         this.enableGroupButton = new GuiButton(0, 0, 0, 90, 18, 
/* 108 */             getButtonText());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 114 */         if (this.enableGroupButton.mousePressed(GuiWDLEntities.GuiEntityList.this.mc, x, y)) {
/* 115 */           this.groupEnabled ^= 0x1;
/*     */           
/* 117 */           this.enableGroupButton.playPressSound(GuiWDLEntities.GuiEntityList.this.mc.getSoundHandler());
/*     */           
/* 119 */           this.enableGroupButton.displayString = getButtonText();
/*     */           
/* 121 */           WDL.worldProps.setProperty("EntityGroup." + this.group + 
/* 122 */               ".Enabled", Boolean.toString(this.groupEnabled));
/* 123 */           return true;
/*     */         } 
/* 125 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */       
/*     */       boolean isGroupEnabled() {
/* 134 */         return this.groupEnabled;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private String getButtonText() {
/* 141 */         if (this.groupEnabled) {
/* 142 */           return I18n.format("wdl.gui.entities.group.enabled", new Object[0]);
/*     */         }
/* 144 */         return I18n.format("wdl.gui.entities.group.disabled", new Object[0]);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/* 157 */         GuiWDLEntities.GuiEntityList.this.mc.fontRendererObj.drawString(this.group, p_192634_2_ + 55 - 
/* 158 */             this.labelWidth / 2, p_192634_3_ + GuiWDLEntities.GuiEntityList.this.slotHeight - 
/* 159 */             GuiWDLEntities.GuiEntityList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
/*     */         
/* 161 */         this.enableGroupButton.xPosition = p_192634_2_ + 110;
/* 162 */         this.enableGroupButton.yPosition = p_192634_3_;
/* 163 */         this.enableGroupButton.displayString = getButtonText();
/*     */         
/* 165 */         this.enableGroupButton.func_191745_a(GuiWDLEntities.GuiEntityList.this.mc, GuiWDLEntities.GuiEntityList.this.mouseX, GuiWDLEntities.GuiEntityList.this.mouseY, p_192634_9_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private class EntityEntry
/*     */       implements GuiListExtended.IGuiListEntry
/*     */     {
/*     */       private final GuiWDLEntities.GuiEntityList.CategoryEntry category;
/*     */       
/*     */       private final String entity;
/*     */       
/*     */       private final GuiButton onOffButton;
/*     */       
/*     */       private final GuiSlider rangeSlider;
/*     */       
/*     */       private boolean entityEnabled;
/*     */       
/*     */       private int range;
/*     */       
/*     */       private String cachedMode;
/*     */       
/*     */       public EntityEntry(GuiWDLEntities.GuiEntityList.CategoryEntry category, String entity) {
/* 188 */         this.category = category;
/* 189 */         this.entity = entity;
/*     */         
/* 191 */         this.entityEnabled = WDL.worldProps.getProperty("Entity." + entity + 
/* 192 */             ".Enabled", "true").equals("true");
/* 193 */         this.range = EntityUtils.getEntityTrackDistance(entity);
/*     */         
/* 195 */         this.onOffButton = new GuiButton(0, 0, 0, 75, 18, 
/* 196 */             getButtonText());
/* 197 */         this.onOffButton.enabled = category.isGroupEnabled();
/*     */         
/* 199 */         this.rangeSlider = new GuiSlider(1, 0, 0, 150, 18, 
/* 200 */             "wdl.gui.entities.trackDistance", this.range, 256);
/*     */         
/* 202 */         this.cachedMode = (GuiWDLEntities.GuiEntityList.access$8(GuiWDLEntities.GuiEntityList.this)).mode;
/*     */         
/* 204 */         this.rangeSlider.enabled = this.cachedMode.equals("user");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 210 */         if (this.onOffButton.mousePressed(GuiWDLEntities.GuiEntityList.this.mc, x, y)) {
/* 211 */           this.entityEnabled ^= 0x1;
/*     */           
/* 213 */           this.onOffButton.playPressSound(GuiWDLEntities.GuiEntityList.this.mc.getSoundHandler());
/* 214 */           this.onOffButton.displayString = getButtonText();
/*     */           
/* 216 */           WDL.worldProps.setProperty("Entity." + this.entity + 
/* 217 */               ".Enabled", Boolean.toString(this.entityEnabled));
/* 218 */           return true;
/*     */         } 
/* 220 */         if (this.rangeSlider.mousePressed(GuiWDLEntities.GuiEntityList.this.mc, x, y)) {
/* 221 */           this.range = this.rangeSlider.getValue();
/*     */           
/* 223 */           WDL.worldProps.setProperty("Entity." + this.entity + 
/* 224 */               ".TrackDistance", 
/* 225 */               Integer.toString(this.range));
/*     */           
/* 227 */           return true;
/*     */         } 
/*     */         
/* 230 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 236 */         this.rangeSlider.mouseReleased(x, y);
/*     */         
/* 238 */         if (this.cachedMode.equals("user")) {
/* 239 */           this.range = this.rangeSlider.getValue();
/*     */           
/* 241 */           WDL.worldProps.setProperty("Entity." + this.entity + 
/* 242 */               ".TrackDistance", 
/* 243 */               Integer.toString(this.range));
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private String getButtonText() {
/* 251 */         if (this.category.isGroupEnabled() && this.entityEnabled) {
/* 252 */           return I18n.format("wdl.gui.entities.entity.included", new Object[0]);
/*     */         }
/* 254 */         return I18n.format("wdl.gui.entities.entity.ignored", new Object[0]);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/* 268 */         int center = (GuiWDLEntities.GuiEntityList.access$8(GuiWDLEntities.GuiEntityList.this)).width / 2 - GuiWDLEntities.GuiEntityList.this.totalWidth / 2 + 
/* 269 */           GuiWDLEntities.GuiEntityList.this.largestWidth + 10;
/*     */         
/* 271 */         GuiWDLEntities.GuiEntityList.this.mc.fontRendererObj.drawString(this.entity, 
/* 272 */             center - GuiWDLEntities.GuiEntityList.this.largestWidth - 10, p_192634_3_ + GuiWDLEntities.GuiEntityList.this.slotHeight / 2 - 
/* 273 */             GuiWDLEntities.GuiEntityList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
/*     */         
/* 275 */         this.onOffButton.xPosition = center;
/* 276 */         this.onOffButton.yPosition = p_192634_3_;
/* 277 */         this.onOffButton.enabled = this.category.isGroupEnabled();
/* 278 */         this.onOffButton.displayString = getButtonText();
/*     */         
/* 280 */         this.rangeSlider.xPosition = center + 85;
/* 281 */         this.rangeSlider.yPosition = p_192634_3_;
/*     */         
/* 283 */         if (!this.cachedMode.equals((GuiWDLEntities.GuiEntityList.access$8(GuiWDLEntities.GuiEntityList.this)).mode)) {
/* 284 */           this.cachedMode = (GuiWDLEntities.GuiEntityList.access$8(GuiWDLEntities.GuiEntityList.this)).mode;
/* 285 */           this.rangeSlider.enabled = this.cachedMode.equals("user");
/*     */           
/* 287 */           this.rangeSlider.setValue(
/* 288 */               EntityUtils.getEntityTrackDistance(this.entity));
/*     */         } 
/*     */         
/* 291 */         this.onOffButton.func_191745_a(GuiWDLEntities.GuiEntityList.this.mc, GuiWDLEntities.GuiEntityList.this.mouseX, GuiWDLEntities.GuiEntityList.this.mouseY, p_192634_9_);
/* 292 */         this.rangeSlider.func_191745_a(GuiWDLEntities.GuiEntityList.this.mc, GuiWDLEntities.GuiEntityList.this.mouseX, GuiWDLEntities.GuiEntityList.this.mouseY, p_192634_9_);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiEntityList() {
/* 299 */       super(GuiWDLEntities.this.mc, GuiWDLEntities.this.width, GuiWDLEntities.this.height, 39, GuiWDLEntities.this.height - 32, 20);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiListExtended.IGuiListEntry getListEntry(int index) {
/* 304 */       return this.entries.get(index);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 309 */       return this.entries.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 314 */       return GuiWDLEntities.this.width / 2 + this.totalWidth / 2 + 10;
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
/*     */   public GuiWDLEntities(GuiScreen parent) {
/* 327 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 332 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, 
/* 333 */           this.height - 29, "OK"));
/*     */     
/* 335 */     this.rangeModeButton = new GuiButton(100, this.width / 2 - 155, 18, 150, 
/* 336 */         20, getRangeModeText());
/* 337 */     this.presetsButton = new GuiButton(101, this.width / 2 + 5, 18, 150, 20, 
/* 338 */         I18n.format("wdl.gui.entities.rangePresets", new Object[0]));
/*     */     
/* 340 */     this.mode = WDL.worldProps.getProperty("Entity.TrackDistanceMode");
/*     */     
/* 342 */     this.presetsButton.enabled = shouldEnablePresetsButton();
/*     */     
/* 344 */     this.buttonList.add(this.rangeModeButton);
/* 345 */     this.buttonList.add(this.presetsButton);
/*     */     
/* 347 */     this.entityList = new GuiEntityList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 355 */     super.handleMouseInput();
/* 356 */     this.entityList.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 361 */     if (button.id == 100) {
/* 362 */       cycleRangeMode();
/*     */     }
/* 364 */     if (button.id == 101 && button.enabled) {
/* 365 */       this.mc.displayGuiScreen(new GuiWDLEntityRangePresets(this));
/*     */     }
/* 367 */     if (button.id == 200) {
/* 368 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 374 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 380 */     if (this.entityList.mouseClicked(mouseX, mouseY, mouseButton)) {
/*     */       return;
/*     */     }
/* 383 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 388 */     if (this.entityList.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 391 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 396 */     drawDefaultBackground();
/* 397 */     this.entityList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 399 */     drawCenteredString(this.fontRendererObj, 
/* 400 */         I18n.format("wdl.gui.entities.title", new Object[0]), this.width / 2, 8, 
/* 401 */         16777215);
/*     */     
/* 403 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cycleRangeMode() {
/* 410 */     if (this.mode.equals("default")) {
/* 411 */       if (WDLPluginChannels.hasServerEntityRange()) {
/* 412 */         this.mode = "server";
/*     */       } else {
/* 414 */         this.mode = "user";
/*     */       } 
/* 416 */     } else if (this.mode.equals("server")) {
/* 417 */       this.mode = "user";
/*     */     } else {
/* 419 */       this.mode = "default";
/*     */     } 
/*     */     
/* 422 */     WDL.worldProps.setProperty("Entity.TrackDistanceMode", this.mode);
/*     */     
/* 424 */     this.rangeModeButton.displayString = getRangeModeText();
/* 425 */     this.presetsButton.enabled = shouldEnablePresetsButton();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRangeModeText() {
/* 432 */     String mode = WDL.worldProps.getProperty("Entity.TrackDistanceMode");
/*     */     
/* 434 */     return I18n.format("wdl.gui.entities.trackDistanceMode." + mode, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldEnablePresetsButton() {
/* 441 */     return this.mode.equals("user");
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
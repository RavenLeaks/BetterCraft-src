/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.EntityUtils;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLPluginChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLEntityRangePresets
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/*     */   private final GuiScreen parent;
/*     */   private GuiButton vanillaButton;
/*     */   private GuiButton spigotButton;
/*     */   private GuiButton serverButton;
/*     */   private GuiButton cancelButton;
/*     */   
/*     */   public GuiWDLEntityRangePresets(GuiScreen parent) {
/*  27 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  32 */     int y = this.height / 4;
/*     */     
/*  34 */     this.vanillaButton = new GuiButton(0, this.width / 2 - 100, y, 
/*  35 */         I18n.format("wdl.gui.rangePresets.vanilla", new Object[0]));
/*  36 */     y += 22;
/*  37 */     this.spigotButton = new GuiButton(1, this.width / 2 - 100, y, 
/*  38 */         I18n.format("wdl.gui.rangePresets.spigot", new Object[0]));
/*  39 */     y += 22;
/*  40 */     this.serverButton = new GuiButton(2, this.width / 2 - 100, y, 
/*  41 */         I18n.format("wdl.gui.rangePresets.server", new Object[0]));
/*     */     
/*  43 */     this.serverButton.enabled = WDLPluginChannels.hasServerEntityRange();
/*     */     
/*  45 */     this.buttonList.add(this.vanillaButton);
/*  46 */     this.buttonList.add(this.spigotButton);
/*  47 */     this.buttonList.add(this.serverButton);
/*     */     
/*  49 */     y += 28;
/*     */     
/*  51 */     this.cancelButton = new GuiButton(100, this.width / 2 - 100, 
/*  52 */         this.height - 29, I18n.format("gui.cancel", new Object[0]));
/*  53 */     this.buttonList.add(this.cancelButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  58 */     if (!button.enabled) {
/*     */       return;
/*     */     }
/*     */     
/*  62 */     if (button.id < 3) {
/*     */ 
/*     */ 
/*     */       
/*  66 */       String lower, upper = I18n.format("wdl.gui.rangePresets.upperWarning", new Object[0]);
/*     */       
/*  68 */       if (button.id == 0) {
/*  69 */         lower = I18n.format("wdl.gui.rangePresets.vanilla.warning", new Object[0]);
/*  70 */       } else if (button.id == 1) {
/*  71 */         lower = I18n.format("wdl.gui.rangePresets.spigot.warning", new Object[0]);
/*  72 */       } else if (button.id == 2) {
/*  73 */         lower = I18n.format("wdl.gui.rangePresets.server.warning", new Object[0]);
/*     */       } else {
/*     */         
/*  76 */         throw new Error("Button.id should never be negative.");
/*     */       } 
/*     */       
/*  79 */       this.mc.displayGuiScreen((GuiScreen)new GuiYesNo(this, upper, lower, button.id));
/*     */     } 
/*     */     
/*  82 */     if (button.id == 100) {
/*  83 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  89 */     Utils.drawListBackground(23, 32, 0, 0, this.height, this.width);
/*     */     
/*  91 */     drawCenteredString(this.fontRendererObj, 
/*  92 */         I18n.format("wdl.gui.rangePresets.title", new Object[0]), this.width / 2, 8, 
/*  93 */         16777215);
/*     */     
/*  95 */     String infoText = null;
/*     */     
/*  97 */     if (this.vanillaButton.isMouseOver()) {
/*  98 */       infoText = I18n.format("wdl.gui.rangePresets.vanilla.description", new Object[0]);
/*  99 */     } else if (this.spigotButton.isMouseOver()) {
/* 100 */       infoText = I18n.format("wdl.gui.rangePresets.spigot.description", new Object[0]);
/* 101 */     } else if (this.serverButton.isMouseOver()) {
/* 102 */       infoText = String.valueOf(I18n.format("wdl.gui.rangePresets.server.description", new Object[0])) + "\n\n";
/*     */       
/* 104 */       if (this.serverButton.enabled) {
/* 105 */         infoText = String.valueOf(infoText) + I18n.format("wdl.gui.rangePresets.server.installed", new Object[0]);
/*     */       } else {
/* 107 */         infoText = String.valueOf(infoText) + I18n.format("wdl.gui.rangePresets.server.notInstalled", new Object[0]);
/*     */       } 
/* 109 */     } else if (this.cancelButton.isMouseOver()) {
/* 110 */       infoText = I18n.format("wdl.gui.rangePresets.cancel.description", new Object[0]);
/*     */     } 
/*     */     
/* 113 */     if (infoText != null) {
/* 114 */       Utils.drawGuiInfoBox(infoText, this.width, this.height, 48);
/*     */     }
/*     */     
/* 117 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 122 */     if (result) {
/* 123 */       Set<String> entities = EntityUtils.getEntityTypes();
/*     */       
/* 125 */       if (id == 0) {
/* 126 */         for (String entity : entities) {
/* 127 */           WDL.worldProps.setProperty("Entity." + entity + 
/* 128 */               ".TrackDistance", Integer.toString(
/* 129 */                 EntityUtils.getDefaultEntityRange(entity)));
/*     */         }
/* 131 */       } else if (id == 1) {
/* 132 */         for (String entity : entities) {
/* 133 */           Class<?> c = null;
/* 134 */           if (c == null) {
/*     */             continue;
/*     */           }
/*     */           
/* 138 */           WDL.worldProps.setProperty("Entity." + entity + 
/* 139 */               ".TrackDistance", Integer.toString(
/* 140 */                 EntityUtils.getDefaultSpigotEntityRange(c)));
/*     */         } 
/* 142 */       } else if (id == 2) {
/* 143 */         for (String entity : entities) {
/* 144 */           WDL.worldProps.setProperty("Entity." + entity + 
/* 145 */               ".TrackDistance", Integer.toString(
/* 146 */                 WDLPluginChannels.getEntityRange(entity)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     this.mc.displayGuiScreen(this.parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 156 */     WDL.saveProps();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLEntityRangePresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
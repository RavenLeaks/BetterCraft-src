/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiCreateFlatWorld;
/*     */ import net.minecraft.client.gui.GuiCreateWorld;
/*     */ import net.minecraft.client.gui.GuiCustomizeWorldScreen;
/*     */ import net.minecraft.client.gui.GuiFlatPresets;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDL;
/*     */ 
/*     */ public class GuiWDLGenerator
/*     */   extends GuiScreen
/*     */ {
/*     */   private String title;
/*     */   private GuiScreen parent;
/*     */   private GuiTextField seedField;
/*     */   private GuiButton generatorBtn;
/*     */   private GuiButton generateStructuresBtn;
/*     */   private GuiButton settingsPageBtn;
/*     */   private String seedText;
/*     */   
/*     */   public GuiWDLGenerator(GuiScreen parent) {
/*  26 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     this.seedText = I18n.format("wdl.gui.generator.seed", new Object[0]);
/*  35 */     int seedWidth = this.fontRendererObj.getStringWidth(String.valueOf(this.seedText) + " ");
/*     */     
/*  37 */     this.buttonList.clear();
/*  38 */     this.title = I18n.format("wdl.gui.generator.title", new Object[] {
/*  39 */           WDL.baseFolderName.replace('@', ':') });
/*  40 */     int y = this.height / 4 - 15;
/*  41 */     this.seedField = new GuiTextField(40, this.fontRendererObj, 
/*  42 */         this.width / 2 - 100 - seedWidth, y, 200 - seedWidth, 18);
/*  43 */     this.seedField.setText(WDL.worldProps.getProperty("RandomSeed"));
/*  44 */     y += 22;
/*  45 */     this.generatorBtn = new GuiButton(1, this.width / 2 - 100, y, 
/*  46 */         getGeneratorText());
/*  47 */     this.buttonList.add(this.generatorBtn);
/*  48 */     y += 22;
/*  49 */     this.generateStructuresBtn = new GuiButton(2, this.width / 2 - 100, y, 
/*  50 */         getGenerateStructuresText());
/*  51 */     this.buttonList.add(this.generateStructuresBtn);
/*  52 */     y += 22;
/*  53 */     this.settingsPageBtn = new GuiButton(3, this.width / 2 - 100, y, 
/*  54 */         "");
/*  55 */     updateSettingsButtonVisibility();
/*  56 */     this.buttonList.add(this.settingsPageBtn);
/*     */     
/*  58 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 29, 
/*  59 */           I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  68 */     if (button.enabled) {
/*  69 */       if (button.id == 1) {
/*  70 */         cycleGenerator();
/*  71 */       } else if (button.id == 2) {
/*  72 */         cycleGenerateStructures();
/*  73 */       } else if (button.id == 3) {
/*  74 */         if (WDL.worldProps.getProperty("MapGenerator", "").equals(
/*  75 */             "flat")) {
/*  76 */           this.mc.displayGuiScreen((GuiScreen)new GuiFlatPresets(
/*  77 */                 new GuiCreateFlatWorldProxy()));
/*  78 */         } else if (WDL.worldProps.getProperty("MapGenerator", "")
/*  79 */           .equals("custom")) {
/*  80 */           this.mc.displayGuiScreen((GuiScreen)new GuiCustomizeWorldScreen(
/*  81 */                 (GuiScreen)new GuiCreateWorldProxy(), WDL.worldProps
/*  82 */                 .getProperty("GeneratorOptions", "")));
/*     */         } 
/*  84 */       } else if (button.id == 100) {
/*  85 */         this.mc.displayGuiScreen(this.parent);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  92 */     WDL.worldProps.setProperty("RandomSeed", this.seedField.getText());
/*     */     
/*  94 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 103 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 104 */     this.seedField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 113 */     super.keyTyped(typedChar, keyCode);
/* 114 */     this.seedField.textboxKeyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 122 */     this.seedField.updateCursorCounter();
/* 123 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 131 */     Utils.drawListBackground(23, 32, 0, 0, this.height, this.width);
/*     */     
/* 133 */     drawCenteredString(this.fontRendererObj, this.title, 
/* 134 */         this.width / 2, 8, 16777215);
/*     */     
/* 136 */     drawString(this.fontRendererObj, this.seedText, this.width / 2 - 100, 
/* 137 */         this.height / 4 - 10, 16777215);
/* 138 */     this.seedField.drawTextBox();
/* 139 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 141 */     String tooltip = null;
/*     */     
/* 143 */     if (Utils.isMouseOverTextBox(mouseX, mouseY, this.seedField)) {
/* 144 */       tooltip = I18n.format("wdl.gui.generator.seed.description", new Object[0]);
/* 145 */     } else if (this.generatorBtn.isMouseOver()) {
/* 146 */       tooltip = I18n.format("wdl.gui.generator.generator.description", new Object[0]);
/* 147 */     } else if (this.generateStructuresBtn.isMouseOver()) {
/* 148 */       tooltip = I18n.format("wdl.gui.generator.generateStructures.description", new Object[0]);
/*     */     } 
/* 150 */     Utils.drawGuiInfoBox(tooltip, this.width, this.height, 48);
/*     */   }
/*     */   
/*     */   private void cycleGenerator() {
/* 154 */     String prop = WDL.worldProps.getProperty("MapGenerator");
/* 155 */     if (prop.equals("void")) {
/* 156 */       WDL.worldProps.setProperty("MapGenerator", "default");
/* 157 */       WDL.worldProps.setProperty("GeneratorName", "default");
/* 158 */       WDL.worldProps.setProperty("GeneratorVersion", "1");
/* 159 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/* 160 */     } else if (prop.equals("default")) {
/* 161 */       WDL.worldProps.setProperty("MapGenerator", "flat");
/* 162 */       WDL.worldProps.setProperty("GeneratorName", "flat");
/* 163 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/*     */       
/* 165 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/* 166 */     } else if (prop.equals("flat")) {
/* 167 */       WDL.worldProps.setProperty("MapGenerator", "largeBiomes");
/* 168 */       WDL.worldProps.setProperty("GeneratorName", "largeBiomes");
/* 169 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/* 170 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/* 171 */     } else if (prop.equals("largeBiomes")) {
/* 172 */       WDL.worldProps.setProperty("MapGenerator", "amplified");
/* 173 */       WDL.worldProps.setProperty("GeneratorName", "amplified");
/* 174 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/* 175 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/* 176 */     } else if (prop.equals("amplified")) {
/* 177 */       WDL.worldProps.setProperty("MapGenerator", "custom");
/* 178 */       WDL.worldProps.setProperty("GeneratorName", "custom");
/* 179 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/* 180 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/* 181 */     } else if (prop.equals("custom")) {
/*     */       
/* 183 */       WDL.worldProps.setProperty("MapGenerator", "legacy");
/* 184 */       WDL.worldProps.setProperty("GeneratorName", "default_1_1");
/* 185 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/* 186 */       WDL.worldProps.setProperty("GeneratorOptions", "");
/*     */     } else {
/* 188 */       WDL.worldProps.setProperty("MapGenerator", "void");
/* 189 */       WDL.worldProps.setProperty("GeneratorName", "flat");
/* 190 */       WDL.worldProps.setProperty("GeneratorVersion", "0");
/* 191 */       WDL.worldProps.setProperty("GeneratorOptions", ";0");
/*     */     } 
/*     */     
/* 194 */     this.generatorBtn.displayString = getGeneratorText();
/* 195 */     updateSettingsButtonVisibility();
/*     */   }
/*     */   
/*     */   private void cycleGenerateStructures() {
/* 199 */     if (WDL.worldProps.getProperty("MapFeatures").equals("true")) {
/* 200 */       WDL.worldProps.setProperty("MapFeatures", "false");
/*     */     } else {
/* 202 */       WDL.worldProps.setProperty("MapFeatures", "true");
/*     */     } 
/*     */     
/* 205 */     this.generateStructuresBtn.displayString = getGenerateStructuresText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSettingsButtonVisibility() {
/* 213 */     if (WDL.worldProps.getProperty("MapGenerator", "").equals("flat")) {
/* 214 */       this.settingsPageBtn.visible = true;
/* 215 */       this.settingsPageBtn.displayString = I18n.format("wdl.gui.generator.flatSettings", new Object[0]);
/* 216 */     } else if (WDL.worldProps.getProperty("MapGenerator", "").equals("custom")) {
/* 217 */       this.settingsPageBtn.visible = true;
/* 218 */       this.settingsPageBtn.displayString = I18n.format("wdl.gui.generator.customSettings", new Object[0]);
/*     */     } else {
/* 220 */       this.settingsPageBtn.visible = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getGeneratorText() {
/* 225 */     return I18n.format("wdl.gui.generator.generator." + 
/* 226 */         WDL.worldProps.getProperty("MapGenerator"), new Object[0]);
/*     */   }
/*     */   
/*     */   private String getGenerateStructuresText() {
/* 230 */     return I18n.format("wdl.gui.generator.generateStructures." + 
/* 231 */         WDL.worldProps.getProperty("MapFeatures"), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class GuiCreateFlatWorldProxy
/*     */     extends GuiCreateFlatWorld
/*     */   {
/*     */     public GuiCreateFlatWorldProxy() {
/* 242 */       super(null, WDL.worldProps.getProperty("GeneratorOptions", ""));
/*     */     }
/*     */ 
/*     */     
/*     */     public void initGui() {
/* 247 */       this.mc.displayGuiScreen(GuiWDLGenerator.this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPreset() {
/* 265 */       return WDL.worldProps.getProperty("GeneratorOptions", "");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPreset(String preset) {
/* 273 */       if (preset == null) {
/* 274 */         preset = "";
/*     */       }
/* 276 */       WDL.worldProps.setProperty("GeneratorOptions", preset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class GuiCreateWorldProxy
/*     */     extends GuiCreateWorld
/*     */   {
/*     */     public GuiCreateWorldProxy() {
/* 288 */       super(GuiWDLGenerator.this);
/*     */       
/* 290 */       this.chunkProviderSettingsJson = WDL.worldProps.getProperty("GeneratorOptions", "");
/*     */     }
/*     */ 
/*     */     
/*     */     public void initGui() {
/* 295 */       this.mc.displayGuiScreen(GuiWDLGenerator.this);
/* 296 */       WDL.worldProps.setProperty("GeneratorOptions", this.chunkProviderSettingsJson);
/*     */     }
/*     */     
/*     */     protected void actionPerformed(GuiButton button) throws IOException {}
/*     */     
/*     */     public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
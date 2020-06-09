/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiQualitySettingsOF
/*    */   extends GuiScreen {
/*    */   private GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private GameSettings settings;
/* 15 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.CUSTOM_ENTITY_MODELS, GameSettings.Options.CUSTOM_GUIS };
/* 16 */   private TooltipManager tooltipManager = new TooltipManager(this);
/*    */ 
/*    */   
/*    */   public GuiQualitySettingsOF(GuiScreen p_i53_1_, GameSettings p_i53_2_) {
/* 20 */     this.prevScreen = p_i53_1_;
/* 21 */     this.settings = p_i53_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 30 */     this.title = I18n.format("of.options.qualityTitle", new Object[0]);
/* 31 */     this.buttonList.clear();
/*    */     
/* 33 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 35 */       GameSettings.Options gamesettings$options = enumOptions[i];
/* 36 */       int j = this.width / 2 - 155 + i % 2 * 160;
/* 37 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*    */       
/* 39 */       if (!gamesettings$options.getEnumFloat()) {
/*    */         
/* 41 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*    */       }
/*    */       else {
/*    */         
/* 45 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 57 */     if (button.enabled) {
/*    */       
/* 59 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*    */         
/* 61 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 62 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 65 */       if (button.id == 200) {
/*    */         
/* 67 */         this.mc.gameSettings.saveOptions();
/* 68 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */       
/* 71 */       if (button.id != GameSettings.Options.AA_LEVEL.ordinal()) {
/*    */         
/* 73 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 74 */         setWorldAndResolution(this.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 84 */     drawDefaultBackground();
/* 85 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 86 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 87 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiQualitySettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
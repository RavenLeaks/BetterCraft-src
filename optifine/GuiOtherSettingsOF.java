/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiOtherSettingsOF
/*     */   extends GuiScreen implements GuiYesNoCallback {
/*     */   private GuiScreen prevScreen;
/*     */   protected String title;
/*     */   private GameSettings settings;
/*  16 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.SHOW_FPS, GameSettings.Options.ADVANCED_TOOLTIPS, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.ANAGLYPH, GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.SCREENSHOT_SIZE };
/*  17 */   private TooltipManager tooltipManager = new TooltipManager(this);
/*     */ 
/*     */   
/*     */   public GuiOtherSettingsOF(GuiScreen p_i51_1_, GameSettings p_i51_2_) {
/*  21 */     this.prevScreen = p_i51_1_;
/*  22 */     this.settings = p_i51_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  31 */     this.title = I18n.format("of.options.otherTitle", new Object[0]);
/*  32 */     this.buttonList.clear();
/*     */     
/*  34 */     for (int i = 0; i < enumOptions.length; i++) {
/*     */       
/*  36 */       GameSettings.Options gamesettings$options = enumOptions[i];
/*  37 */       int j = this.width / 2 - 155 + i % 2 * 160;
/*  38 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*     */       
/*  40 */       if (!gamesettings$options.getEnumFloat()) {
/*     */         
/*  42 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*     */       }
/*     */       else {
/*     */         
/*  46 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
/*  51 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  59 */     if (button.enabled) {
/*     */       
/*  61 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*     */         
/*  63 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  64 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/*  67 */       if (button.id == 200) {
/*     */         
/*  69 */         this.mc.gameSettings.saveOptions();
/*  70 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */       
/*  73 */       if (button.id == 210) {
/*     */         
/*  75 */         this.mc.gameSettings.saveOptions();
/*  76 */         GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
/*  77 */         this.mc.displayGuiScreen((GuiScreen)guiyesno);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  84 */     if (result)
/*     */     {
/*  86 */       this.mc.gameSettings.resetSettings();
/*     */     }
/*     */     
/*  89 */     this.mc.displayGuiScreen(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  97 */     drawDefaultBackground();
/*  98 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/*  99 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 100 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiOtherSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
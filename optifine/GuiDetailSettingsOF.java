/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiDetailSettingsOF
/*    */   extends GuiScreen {
/*    */   private GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private GameSettings settings;
/* 14 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.ENTITY_SHADOWS, GameSettings.Options.VIGNETTE, GameSettings.Options.ALTERNATE_BLOCKS };
/* 15 */   private TooltipManager tooltipManager = new TooltipManager(this);
/*    */ 
/*    */   
/*    */   public GuiDetailSettingsOF(GuiScreen p_i47_1_, GameSettings p_i47_2_) {
/* 19 */     this.prevScreen = p_i47_1_;
/* 20 */     this.settings = p_i47_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 29 */     this.title = I18n.format("of.options.detailsTitle", new Object[0]);
/* 30 */     this.buttonList.clear();
/*    */     
/* 32 */     for (int i = 0; i < enumOptions.length; i++) {
/*    */       
/* 34 */       GameSettings.Options gamesettings$options = enumOptions[i];
/* 35 */       int j = this.width / 2 - 155 + i % 2 * 160;
/* 36 */       int k = this.height / 6 + 21 * i / 2 - 12;
/*    */       
/* 38 */       if (!gamesettings$options.getEnumFloat()) {
/*    */         
/* 40 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*    */       }
/*    */       else {
/*    */         
/* 44 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 56 */     if (button.enabled) {
/*    */       
/* 58 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*    */         
/* 60 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 61 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 64 */       if (button.id == 200) {
/*    */         
/* 66 */         this.mc.gameSettings.saveOptions();
/* 67 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 77 */     drawDefaultBackground();
/* 78 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 79 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 80 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiDetailSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
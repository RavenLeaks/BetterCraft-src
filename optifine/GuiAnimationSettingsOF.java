/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiAnimationSettingsOF
/*    */   extends GuiScreen {
/*    */   private GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private GameSettings settings;
/* 15 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
/*    */ 
/*    */   
/*    */   public GuiAnimationSettingsOF(GuiScreen p_i46_1_, GameSettings p_i46_2_) {
/* 19 */     this.prevScreen = p_i46_1_;
/* 20 */     this.settings = p_i46_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 29 */     this.title = I18n.format("of.options.animationsTitle", new Object[0]);
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
/* 48 */     this.buttonList.add(new GuiButton(210, this.width / 2 - 155, this.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
/* 49 */     this.buttonList.add(new GuiButton(211, this.width / 2 - 155 + 80, this.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
/* 50 */     this.buttonList.add(new GuiOptionButton(200, this.width / 2 + 5, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 58 */     if (button.enabled) {
/*    */       
/* 60 */       if (button.id < 200 && button instanceof GuiOptionButton) {
/*    */         
/* 62 */         this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 63 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 66 */       if (button.id == 200) {
/*    */         
/* 68 */         this.mc.gameSettings.saveOptions();
/* 69 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */       
/* 72 */       if (button.id == 210)
/*    */       {
/* 74 */         this.mc.gameSettings.setAllAnimations(true);
/*    */       }
/*    */       
/* 77 */       if (button.id == 211)
/*    */       {
/* 79 */         this.mc.gameSettings.setAllAnimations(false);
/*    */       }
/*    */       
/* 82 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 83 */       setWorldAndResolution(this.mc, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 92 */     drawDefaultBackground();
/* 93 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 94 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiAnimationSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
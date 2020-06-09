/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import optifine.Config;
/*     */ import optifine.GuiAnimationSettingsOF;
/*     */ import optifine.GuiDetailSettingsOF;
/*     */ import optifine.GuiOptionButtonOF;
/*     */ import optifine.GuiOptionSliderOF;
/*     */ import optifine.GuiOtherSettingsOF;
/*     */ import optifine.GuiPerformanceSettingsOF;
/*     */ import optifine.GuiQualitySettingsOF;
/*     */ import optifine.GuiScreenOF;
/*     */ import optifine.Lang;
/*     */ import optifine.TooltipManager;
/*     */ import shadersmod.client.GuiShaders;
/*     */ 
/*     */ public class GuiVideoSettings
/*     */   extends GuiScreenOF {
/*     */   private GuiScreen parentGuiScreen;
/*  22 */   protected String screenTitle = "Video Settings";
/*     */   private GameSettings guiGameSettings;
/*  24 */   private static GameSettings.Options[] videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.ATTACK_INDICATOR, GameSettings.Options.DYNAMIC_LIGHTS, GameSettings.Options.DYNAMIC_FOV };
/*     */   private static final String __OBFID = "CL_00000718";
/*  26 */   private TooltipManager tooltipManager = new TooltipManager((GuiScreen)this);
/*     */ 
/*     */   
/*     */   public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/*  30 */     this.parentGuiScreen = parentScreenIn;
/*  31 */     this.guiGameSettings = gameSettingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  40 */     this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
/*  41 */     this.buttonList.clear();
/*     */     
/*  43 */     for (int i = 0; i < videoOptions.length; i++) {
/*     */       
/*  45 */       GameSettings.Options gamesettings$options = videoOptions[i];
/*     */       
/*  47 */       if (gamesettings$options != null) {
/*     */         
/*  49 */         int j = this.width / 2 - 155 + i % 2 * 160;
/*  50 */         int k = this.height / 6 + 21 * i / 2 - 12;
/*     */         
/*  52 */         if (gamesettings$options.getEnumFloat()) {
/*     */           
/*  54 */           this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*     */         }
/*     */         else {
/*     */           
/*  58 */           this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     int l = this.height / 6 + 21 * videoOptions.length / 2 - 12;
/*  64 */     int i1 = 0;
/*  65 */     i1 = this.width / 2 - 155 + 0;
/*  66 */     this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
/*  67 */     i1 = this.width / 2 - 155 + 160;
/*  68 */     this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
/*  69 */     l += 21;
/*  70 */     i1 = this.width / 2 - 155 + 0;
/*  71 */     this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
/*  72 */     i1 = this.width / 2 - 155 + 160;
/*  73 */     this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
/*  74 */     l += 21;
/*  75 */     i1 = this.width / 2 - 155 + 0;
/*  76 */     this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
/*  77 */     i1 = this.width / 2 - 155 + 160;
/*  78 */     this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
/*  79 */     l += 21;
/*  80 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  88 */     actionPerformed(button, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) {
/*  93 */     if (p_actionPerformedRightClick_1_.id == GameSettings.Options.GUI_SCALE.ordinal())
/*     */     {
/*  95 */       actionPerformed(p_actionPerformedRightClick_1_, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void actionPerformed(GuiButton p_actionPerformed_1_, int p_actionPerformed_2_) {
/* 101 */     if (p_actionPerformed_1_.enabled) {
/*     */       
/* 103 */       int i = this.guiGameSettings.guiScale;
/*     */       
/* 105 */       if (p_actionPerformed_1_.id < 200 && p_actionPerformed_1_ instanceof GuiOptionButton) {
/*     */         
/* 107 */         this.guiGameSettings.setOptionValue(((GuiOptionButton)p_actionPerformed_1_).returnEnumOptions(), p_actionPerformed_2_);
/* 108 */         p_actionPerformed_1_.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(p_actionPerformed_1_.id));
/*     */       } 
/*     */       
/* 111 */       if (p_actionPerformed_1_.id == 200) {
/*     */         
/* 113 */         this.mc.gameSettings.saveOptions();
/* 114 */         this.mc.displayGuiScreen(this.parentGuiScreen);
/*     */       } 
/*     */       
/* 117 */       if (this.guiGameSettings.guiScale != i) {
/*     */         
/* 119 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 120 */         int j = ScaledResolution.getScaledWidth();
/* 121 */         int k = ScaledResolution.getScaledHeight();
/* 122 */         setWorldAndResolution(this.mc, j, k);
/*     */       } 
/*     */       
/* 125 */       if (p_actionPerformed_1_.id == 201) {
/*     */         
/* 127 */         this.mc.gameSettings.saveOptions();
/* 128 */         GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 129 */         this.mc.displayGuiScreen((GuiScreen)guidetailsettingsof);
/*     */       } 
/*     */       
/* 132 */       if (p_actionPerformed_1_.id == 202) {
/*     */         
/* 134 */         this.mc.gameSettings.saveOptions();
/* 135 */         GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF((GuiScreen)this, this.guiGameSettings);
/* 136 */         this.mc.displayGuiScreen((GuiScreen)guiqualitysettingsof);
/*     */       } 
/*     */       
/* 139 */       if (p_actionPerformed_1_.id == 211) {
/*     */         
/* 141 */         this.mc.gameSettings.saveOptions();
/* 142 */         GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 143 */         this.mc.displayGuiScreen((GuiScreen)guianimationsettingsof);
/*     */       } 
/*     */       
/* 146 */       if (p_actionPerformed_1_.id == 212) {
/*     */         
/* 148 */         this.mc.gameSettings.saveOptions();
/* 149 */         GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 150 */         this.mc.displayGuiScreen((GuiScreen)guiperformancesettingsof);
/*     */       } 
/*     */       
/* 153 */       if (p_actionPerformed_1_.id == 222) {
/*     */         
/* 155 */         this.mc.gameSettings.saveOptions();
/* 156 */         GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 157 */         this.mc.displayGuiScreen((GuiScreen)guiothersettingsof);
/*     */       } 
/*     */       
/* 160 */       if (p_actionPerformed_1_.id == 231) {
/*     */         
/* 162 */         if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
/*     */           
/* 164 */           Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
/*     */           
/*     */           return;
/*     */         } 
/* 168 */         if (Config.isAnisotropicFiltering()) {
/*     */           
/* 170 */           Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
/*     */           
/*     */           return;
/*     */         } 
/* 174 */         if (Config.isFastRender()) {
/*     */           
/* 176 */           Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
/*     */           
/*     */           return;
/*     */         } 
/* 180 */         if ((Config.getGameSettings()).anaglyph) {
/*     */           
/* 182 */           Config.showGuiMessage(Lang.get("of.message.shaders.an1"), Lang.get("of.message.shaders.an2"));
/*     */           
/*     */           return;
/*     */         } 
/* 186 */         this.mc.gameSettings.saveOptions();
/* 187 */         GuiShaders guishaders = new GuiShaders((GuiScreen)this, this.guiGameSettings);
/* 188 */         this.mc.displayGuiScreen((GuiScreen)guishaders);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 198 */     drawDefaultBackground();
/* 199 */     drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
/* 200 */     String s = Config.getVersion();
/* 201 */     String s1 = "HD_U";
/*     */     
/* 203 */     if (s1.equals("HD"))
/*     */     {
/* 205 */       s = "OptiFine HD C6";
/*     */     }
/*     */     
/* 208 */     if (s1.equals("HD_U"))
/*     */     {
/* 210 */       s = "OptiFine HD C6 Ultra";
/*     */     }
/*     */     
/* 213 */     if (s1.equals("L"))
/*     */     {
/* 215 */       s = "OptiFine C6 Light";
/*     */     }
/*     */     
/* 218 */     drawString(this.fontRendererObj, s, 2, this.height - 10, 8421504);
/* 219 */     String s2 = "Minecraft 1.12.2";
/* 220 */     int i = this.fontRendererObj.getStringWidth(s2);
/* 221 */     drawString(this.fontRendererObj, s2, this.width - i - 2, this.height - 10, 8421504);
/* 222 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 223 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonWidth(GuiButton p_getButtonWidth_0_) {
/* 228 */     return p_getButtonWidth_0_.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonHeight(GuiButton p_getButtonHeight_0_) {
/* 233 */     return p_getButtonHeight_0_.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_) {
/* 238 */     p_drawGradientRect_0_.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiVideoSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
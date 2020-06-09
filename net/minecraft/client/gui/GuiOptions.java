/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ public class GuiOptions
/*     */   extends GuiScreen {
/*  13 */   private static final GameSettings.Options[] SCREEN_OPTIONS = new GameSettings.Options[] { GameSettings.Options.FOV };
/*     */   
/*     */   private final GuiScreen lastScreen;
/*     */   
/*     */   private final GameSettings settings;
/*     */   private GuiButton difficultyButton;
/*     */   private GuiLockIconButton lockButton;
/*  20 */   protected String title = "Options";
/*     */ 
/*     */   
/*     */   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
/*  24 */     this.lastScreen = p_i1046_1_;
/*  25 */     this.settings = p_i1046_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     this.title = I18n.format("options.title", new Object[0]);
/*  35 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  37 */     for (j = (arrayOfOptions = SCREEN_OPTIONS).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  39 */       if (gamesettings$options.getEnumFloat()) {
/*     */         
/*  41 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options));
/*     */       }
/*     */       else {
/*     */         
/*  45 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options, this.settings.getKeyBinding(gamesettings$options));
/*  46 */         this.buttonList.add(guioptionbutton);
/*     */       } 
/*     */       
/*  49 */       i++;
/*     */       b++; }
/*     */     
/*  52 */     if (this.mc.world != null) {
/*     */       
/*  54 */       EnumDifficulty enumdifficulty = this.mc.world.getDifficulty();
/*  55 */       this.difficultyButton = new GuiButton(108, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), 150, 20, getDifficultyText(enumdifficulty));
/*  56 */       this.buttonList.add(this.difficultyButton);
/*     */       
/*  58 */       if (this.mc.isSingleplayer() && !this.mc.world.getWorldInfo().isHardcoreModeEnabled())
/*     */       {
/*  60 */         this.difficultyButton.setWidth(this.difficultyButton.getButtonWidth() - 20);
/*  61 */         this.lockButton = new GuiLockIconButton(109, this.difficultyButton.xPosition + this.difficultyButton.getButtonWidth(), this.difficultyButton.yPosition);
/*  62 */         this.buttonList.add(this.lockButton);
/*  63 */         this.lockButton.setLocked(this.mc.world.getWorldInfo().isDifficultyLocked());
/*  64 */         this.lockButton.enabled = !this.lockButton.isLocked();
/*  65 */         this.difficultyButton.enabled = !this.lockButton.isLocked();
/*     */       }
/*     */       else
/*     */       {
/*  69 */         this.difficultyButton.enabled = false;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  74 */       this.buttonList.add(new GuiOptionButton(GameSettings.Options.REALMS_NOTIFICATIONS.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), GameSettings.Options.REALMS_NOTIFICATIONS, this.settings.getKeyBinding(GameSettings.Options.REALMS_NOTIFICATIONS)));
/*     */     } 
/*     */     
/*  77 */     this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
/*  78 */     this.buttonList.add(new GuiButton(106, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*  79 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  80 */     this.buttonList.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  81 */     this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  82 */     this.buttonList.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
/*  83 */     this.buttonList.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/*  84 */     this.buttonList.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/*  85 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDifficultyText(EnumDifficulty p_175355_1_) {
/*  90 */     TextComponentString textComponentString = new TextComponentString("");
/*  91 */     textComponentString.appendSibling((ITextComponent)new TextComponentTranslation("options.difficulty", new Object[0]));
/*  92 */     textComponentString.appendText(": ");
/*  93 */     textComponentString.appendSibling((ITextComponent)new TextComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
/*  94 */     return textComponentString.getFormattedText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  99 */     this.mc.displayGuiScreen(this);
/*     */     
/* 101 */     if (id == 109 && result && this.mc.world != null) {
/*     */       
/* 103 */       this.mc.world.getWorldInfo().setDifficultyLocked(true);
/* 104 */       this.lockButton.setLocked(true);
/* 105 */       this.lockButton.enabled = false;
/* 106 */       this.difficultyButton.enabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 116 */     if (keyCode == 1)
/*     */     {
/* 118 */       this.mc.gameSettings.saveOptions();
/*     */     }
/*     */     
/* 121 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 129 */     if (button.enabled) {
/*     */       
/* 131 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*     */         
/* 133 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/* 134 */         this.settings.setOptionValue(gamesettings$options, 1);
/* 135 */         button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/* 138 */       if (button.id == 108) {
/*     */         
/* 140 */         this.mc.world.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.world.getDifficulty().getDifficultyId() + 1));
/* 141 */         this.difficultyButton.displayString = getDifficultyText(this.mc.world.getDifficulty());
/*     */       } 
/*     */       
/* 144 */       if (button.id == 109)
/*     */       {
/* 146 */         this.mc.displayGuiScreen(new GuiYesNo(this, (new TextComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new TextComponentTranslation("difficulty.lock.question", new Object[] { new TextComponentTranslation(this.mc.world.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) })).getFormattedText(), 109));
/*     */       }
/*     */       
/* 149 */       if (button.id == 110) {
/*     */         
/* 151 */         this.mc.gameSettings.saveOptions();
/* 152 */         this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
/*     */       } 
/*     */       
/* 155 */       if (button.id == 101) {
/*     */         
/* 157 */         this.mc.gameSettings.saveOptions();
/* 158 */         this.mc.displayGuiScreen((GuiScreen)new GuiVideoSettings(this, this.settings));
/*     */       } 
/*     */       
/* 161 */       if (button.id == 100) {
/*     */         
/* 163 */         this.mc.gameSettings.saveOptions();
/* 164 */         this.mc.displayGuiScreen(new GuiControls(this, this.settings));
/*     */       } 
/*     */       
/* 167 */       if (button.id == 102) {
/*     */         
/* 169 */         this.mc.gameSettings.saveOptions();
/* 170 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.settings, this.mc.getLanguageManager()));
/*     */       } 
/*     */       
/* 173 */       if (button.id == 103) {
/*     */         
/* 175 */         this.mc.gameSettings.saveOptions();
/* 176 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.settings));
/*     */       } 
/*     */       
/* 179 */       if (button.id == 104) {
/*     */         
/* 181 */         this.mc.gameSettings.saveOptions();
/* 182 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.settings));
/*     */       } 
/*     */       
/* 185 */       if (button.id == 200) {
/*     */         
/* 187 */         this.mc.gameSettings.saveOptions();
/* 188 */         this.mc.displayGuiScreen(this.lastScreen);
/*     */       } 
/*     */       
/* 191 */       if (button.id == 105) {
/*     */         
/* 193 */         this.mc.gameSettings.saveOptions();
/* 194 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/*     */       } 
/*     */       
/* 197 */       if (button.id == 106) {
/*     */         
/* 199 */         this.mc.gameSettings.saveOptions();
/* 200 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.settings));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 210 */     drawDefaultBackground();
/* 211 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/* 212 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.Language;
/*     */ import net.minecraft.client.resources.LanguageManager;
/*     */ import net.minecraft.client.settings.GameSettings;
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
/*     */ public class GuiLanguage
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiScreen parentScreen;
/*     */   private List list;
/*     */   private final GameSettings game_settings_3;
/*     */   private final LanguageManager languageManager;
/*     */   private GuiOptionButton forceUnicodeFontBtn;
/*     */   private GuiOptionButton confirmSettingsBtn;
/*     */   
/*     */   public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
/*  37 */     this.parentScreen = screen;
/*  38 */     this.game_settings_3 = gameSettingsObj;
/*  39 */     this.languageManager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  48 */     this.forceUnicodeFontBtn = addButton(new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
/*  49 */     this.confirmSettingsBtn = addButton(new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0])));
/*  50 */     this.list = new List(this.mc);
/*  51 */     this.list.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  59 */     super.handleMouseInput();
/*  60 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  68 */     if (button.enabled) {
/*     */       
/*  70 */       switch (button.id) {
/*     */         case 5:
/*     */           return;
/*     */ 
/*     */         
/*     */         case 6:
/*  76 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */ 
/*     */         
/*     */         case 100:
/*  80 */           if (button instanceof GuiOptionButton) {
/*     */             
/*  82 */             this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  83 */             button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/*  84 */             ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  85 */             int i = ScaledResolution.getScaledWidth();
/*  86 */             int j = ScaledResolution.getScaledHeight();
/*  87 */             setWorldAndResolution(this.mc, i, j);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  93 */       this.list.actionPerformed(button);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 103 */     drawDefaultBackground();
/* 104 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 105 */     drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
/* 106 */     drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
/* 107 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot {
/* 112 */     private final java.util.List<String> langCodeList = Lists.newArrayList();
/* 113 */     private final Map<String, Language> languageMap = Maps.newHashMap();
/*     */ 
/*     */     
/*     */     public List(Minecraft mcIn) {
/* 117 */       super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
/*     */       
/* 119 */       for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
/*     */         
/* 121 */         this.languageMap.put(language.getLanguageCode(), language);
/* 122 */         this.langCodeList.add(language.getLanguageCode());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 128 */       return this.langCodeList.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 133 */       Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
/* 134 */       GuiLanguage.this.languageManager.setCurrentLanguage(language);
/* 135 */       GuiLanguage.this.game_settings_3.language = language.getLanguageCode();
/* 136 */       this.mc.refreshResources();
/* 137 */       GuiLanguage.this.fontRendererObj.setUnicodeFlag(!(!GuiLanguage.this.languageManager.isCurrentLocaleUnicode() && !GuiLanguage.this.game_settings_3.forceUnicodeFont));
/* 138 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
/* 139 */       GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done", new Object[0]);
/* 140 */       GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/* 141 */       GuiLanguage.this.game_settings_3.saveOptions();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 146 */       return ((String)this.langCodeList.get(slotIndex)).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 151 */       return getSize() * 18;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 156 */       GuiLanguage.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 161 */       GuiLanguage.this.fontRendererObj.setBidiFlag(true);
/* 162 */       GuiLanguage.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.languageMap.get(this.langCodeList.get(p_192637_1_))).toString(), this.width / 2, p_192637_3_ + 1, 16777215);
/* 163 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiLanguage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
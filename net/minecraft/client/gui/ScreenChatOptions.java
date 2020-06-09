/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.chat.NarratorChatListener;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class ScreenChatOptions
/*     */   extends GuiScreen {
/*  10 */   private static final GameSettings.Options[] CHAT_OPTIONS = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO, GameSettings.Options.NARRATOR };
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   private final GameSettings game_settings;
/*     */   private String chatTitle;
/*     */   private GuiOptionButton field_193025_i;
/*     */   
/*     */   public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/*  18 */     this.parentScreen = parentScreenIn;
/*  19 */     this.game_settings = gameSettingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  28 */     this.chatTitle = I18n.format("options.chat.title", new Object[0]);
/*  29 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  31 */     for (j = (arrayOfOptions = CHAT_OPTIONS).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*     */       
/*  33 */       if (gamesettings$options.getEnumFloat()) {
/*     */         
/*  35 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options));
/*     */       }
/*     */       else {
/*     */         
/*  39 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options));
/*  40 */         this.buttonList.add(guioptionbutton);
/*     */         
/*  42 */         if (gamesettings$options == GameSettings.Options.NARRATOR) {
/*     */           
/*  44 */           this.field_193025_i = guioptionbutton;
/*  45 */           guioptionbutton.enabled = NarratorChatListener.field_193643_a.func_193640_a();
/*     */         } 
/*     */       } 
/*     */       
/*  49 */       i++;
/*     */       b++; }
/*     */     
/*  52 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 144, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  61 */     if (keyCode == 1)
/*     */     {
/*  63 */       this.mc.gameSettings.saveOptions();
/*     */     }
/*     */     
/*  66 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  74 */     if (button.enabled) {
/*     */       
/*  76 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*     */         
/*  78 */         this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  79 */         button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/*  82 */       if (button.id == 200) {
/*     */         
/*  84 */         this.mc.gameSettings.saveOptions();
/*  85 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  95 */     drawDefaultBackground();
/*  96 */     drawCenteredString(this.fontRendererObj, this.chatTitle, this.width / 2, 20, 16777215);
/*  97 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193024_a() {
/* 102 */     this.field_193025_i.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_193025_i.id));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ScreenChatOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
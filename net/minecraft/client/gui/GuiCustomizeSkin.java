/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCustomizeSkin
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   
/*     */   public GuiCustomizeSkin(GuiScreen parentScreenIn) {
/*  18 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  27 */     int i = 0;
/*  28 */     this.title = I18n.format("options.skinCustomisation.title", new Object[0]); byte b; int j;
/*     */     EnumPlayerModelParts[] arrayOfEnumPlayerModelParts;
/*  30 */     for (j = (arrayOfEnumPlayerModelParts = EnumPlayerModelParts.values()).length, b = 0; b < j; ) { EnumPlayerModelParts enumplayermodelparts = arrayOfEnumPlayerModelParts[b];
/*     */       
/*  32 */       this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts, null));
/*  33 */       i++;
/*     */       b++; }
/*     */     
/*  36 */     this.buttonList.add(new GuiOptionButton(199, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), GameSettings.Options.MAIN_HAND, this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND)));
/*  37 */     i++;
/*     */     
/*  39 */     if (i % 2 == 1)
/*     */     {
/*  41 */       i++;
/*     */     }
/*     */     
/*  44 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  53 */     if (keyCode == 1)
/*     */     {
/*  55 */       this.mc.gameSettings.saveOptions();
/*     */     }
/*     */     
/*  58 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  66 */     if (button.enabled)
/*     */     {
/*  68 */       if (button.id == 200) {
/*     */         
/*  70 */         this.mc.gameSettings.saveOptions();
/*  71 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*  73 */       else if (button.id == 199) {
/*     */         
/*  75 */         this.mc.gameSettings.setOptionValue(GameSettings.Options.MAIN_HAND, 1);
/*  76 */         button.displayString = this.mc.gameSettings.getKeyBinding(GameSettings.Options.MAIN_HAND);
/*  77 */         this.mc.gameSettings.sendSettingsToServer();
/*     */       }
/*  79 */       else if (button instanceof ButtonPart) {
/*     */         
/*  81 */         EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
/*  82 */         this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
/*  83 */         button.displayString = getMessage(enumplayermodelparts);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  93 */     drawDefaultBackground();
/*  94 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/*  95 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMessage(EnumPlayerModelParts playerModelParts) {
/*     */     String s;
/* 102 */     if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
/*     */       
/* 104 */       s = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 108 */       s = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 111 */     return String.valueOf(playerModelParts.getName().getFormattedText()) + ": " + s;
/*     */   }
/*     */   
/*     */   class ButtonPart
/*     */     extends GuiButton
/*     */   {
/*     */     private final EnumPlayerModelParts playerModelParts;
/*     */     
/*     */     private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts) {
/* 120 */       super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.getMessage(playerModelParts));
/* 121 */       this.playerModelParts = playerModelParts;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiCustomizeSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
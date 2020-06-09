/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiScreenOptionsSounds
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parent;
/*     */   private final GameSettings game_settings_4;
/*  20 */   protected String title = "Options";
/*     */   
/*     */   private String offDisplayString;
/*     */   
/*     */   public GuiScreenOptionsSounds(GuiScreen parentIn, GameSettings settingsIn) {
/*  25 */     this.parent = parentIn;
/*  26 */     this.game_settings_4 = settingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     this.title = I18n.format("options.sounds.title", new Object[0]);
/*  36 */     this.offDisplayString = I18n.format("options.off", new Object[0]);
/*  37 */     int i = 0;
/*  38 */     this.buttonList.add(new Button(SoundCategory.MASTER.ordinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, true));
/*  39 */     i += 2; byte b; int m;
/*     */     SoundCategory[] arrayOfSoundCategory;
/*  41 */     for (m = (arrayOfSoundCategory = SoundCategory.values()).length, b = 0; b < m; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*     */       
/*  43 */       if (soundcategory != SoundCategory.MASTER) {
/*     */         
/*  45 */         this.buttonList.add(new Button(soundcategory.ordinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), soundcategory, false));
/*  46 */         i++;
/*     */       } 
/*     */       b++; }
/*     */     
/*  50 */     int j = this.width / 2 - 75;
/*  51 */     int k = this.height / 6 - 12;
/*  52 */     i++;
/*  53 */     this.buttonList.add(new GuiOptionButton(201, j, k + 24 * (i >> 1), GameSettings.Options.SHOW_SUBTITLES, this.game_settings_4.getKeyBinding(GameSettings.Options.SHOW_SUBTITLES)));
/*  54 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  63 */     if (keyCode == 1)
/*     */     {
/*  65 */       this.mc.gameSettings.saveOptions();
/*     */     }
/*     */     
/*  68 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  76 */     if (button.enabled)
/*     */     {
/*  78 */       if (button.id == 200) {
/*     */         
/*  80 */         this.mc.gameSettings.saveOptions();
/*  81 */         this.mc.displayGuiScreen(this.parent);
/*     */       }
/*  83 */       else if (button.id == 201) {
/*     */         
/*  85 */         this.mc.gameSettings.setOptionValue(GameSettings.Options.SHOW_SUBTITLES, 1);
/*  86 */         button.displayString = this.mc.gameSettings.getKeyBinding(GameSettings.Options.SHOW_SUBTITLES);
/*  87 */         this.mc.gameSettings.saveOptions();
/*     */       } 
/*     */     }
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
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDisplayString(SoundCategory category) {
/* 104 */     float f = this.game_settings_4.getSoundLevel(category);
/* 105 */     return (f == 0.0F) ? this.offDisplayString : (String.valueOf((int)(f * 100.0F)) + "%");
/*     */   }
/*     */   
/*     */   class Button
/*     */     extends GuiButton {
/*     */     private final SoundCategory category;
/*     */     private final String categoryName;
/* 112 */     public float volume = 1.0F;
/*     */     
/*     */     public boolean pressed;
/*     */     
/*     */     public Button(int p_i46744_2_, int x, int y, SoundCategory categoryIn, boolean master) {
/* 117 */       super(p_i46744_2_, x, y, master ? 310 : 150, 20, "");
/* 118 */       this.category = categoryIn;
/* 119 */       this.categoryName = I18n.format("soundCategory." + categoryIn.getName(), new Object[0]);
/* 120 */       this.displayString = String.valueOf(this.categoryName) + ": " + GuiScreenOptionsSounds.this.getDisplayString(categoryIn);
/* 121 */       this.volume = GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(categoryIn);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getHoverState(boolean mouseOver) {
/* 126 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 131 */       if (this.visible) {
/*     */         
/* 133 */         if (this.pressed) {
/*     */           
/* 135 */           this.volume = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 136 */           this.volume = MathHelper.clamp(this.volume, 0.0F, 1.0F);
/* 137 */           mc.gameSettings.setSoundLevel(this.category, this.volume);
/* 138 */           mc.gameSettings.saveOptions();
/* 139 */           this.displayString = String.valueOf(this.categoryName) + ": " + GuiScreenOptionsSounds.this.getDisplayString(this.category);
/*     */         } 
/*     */         
/* 142 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 143 */         drawTexturedModalRect(this.xPosition + (int)(this.volume * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 144 */         drawTexturedModalRect(this.xPosition + (int)(this.volume * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 150 */       if (super.mousePressed(mc, mouseX, mouseY)) {
/*     */         
/* 152 */         this.volume = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 153 */         this.volume = MathHelper.clamp(this.volume, 0.0F, 1.0F);
/* 154 */         mc.gameSettings.setSoundLevel(this.category, this.volume);
/* 155 */         mc.gameSettings.saveOptions();
/* 156 */         this.displayString = String.valueOf(this.categoryName) + ": " + GuiScreenOptionsSounds.this.getDisplayString(this.category);
/* 157 */         this.pressed = true;
/* 158 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 162 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void playPressSound(SoundHandler soundHandlerIn) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int mouseX, int mouseY) {
/* 172 */       if (this.pressed)
/*     */       {
/* 174 */         GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */       }
/*     */       
/* 177 */       this.pressed = false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenOptionsSounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
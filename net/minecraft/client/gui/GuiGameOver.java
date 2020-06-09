/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGameOver
/*     */   extends GuiScreen
/*     */ {
/*     */   private int enableButtonsTimer;
/*     */   private final ITextComponent causeOfDeath;
/*     */   
/*     */   public GuiGameOver(@Nullable ITextComponent p_i46598_1_) {
/*  21 */     this.causeOfDeath = p_i46598_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  30 */     this.buttonList.clear();
/*  31 */     this.enableButtonsTimer = 0;
/*     */     
/*  33 */     if (this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
/*     */       
/*  35 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.spectate", new Object[0])));
/*  36 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen." + (this.mc.isIntegratedServerRunning() ? "deleteWorld" : "leaveServer"), new Object[0])));
/*     */     }
/*     */     else {
/*     */       
/*  40 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
/*  41 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
/*     */       
/*  43 */       if (Minecraft.getSession() == null)
/*     */       {
/*  45 */         ((GuiButton)this.buttonList.get(1)).enabled = false;
/*     */       }
/*     */     } 
/*     */     
/*  49 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/*  51 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     GuiYesNo guiyesno;
/*  68 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  71 */         this.mc.player.respawnPlayer();
/*  72 */         this.mc.displayGuiScreen(null);
/*     */         break;
/*     */       
/*     */       case 1:
/*  76 */         if (this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
/*     */           
/*  78 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*     */           
/*     */           break;
/*     */         } 
/*  82 */         guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
/*  83 */         this.mc.displayGuiScreen(guiyesno);
/*  84 */         guiyesno.setButtonDelay(20);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  91 */     if (result) {
/*     */       
/*  93 */       if (this.mc.world != null)
/*     */       {
/*  95 */         this.mc.world.sendQuittingDisconnectingPacket();
/*     */       }
/*     */       
/*  98 */       this.mc.loadWorld(null);
/*  99 */       this.mc.displayGuiScreen(new GuiMainMenu());
/*     */     }
/*     */     else {
/*     */       
/* 103 */       this.mc.player.respawnPlayer();
/* 104 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 113 */     boolean flag = this.mc.world.getWorldInfo().isHardcoreModeEnabled();
/* 114 */     drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
/* 115 */     GlStateManager.pushMatrix();
/* 116 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 117 */     drawCenteredString(this.fontRendererObj, I18n.format(flag ? "deathScreen.title.hardcore" : "deathScreen.title", new Object[0]), this.width / 2 / 2, 30, 16777215);
/* 118 */     GlStateManager.popMatrix();
/*     */     
/* 120 */     if (this.causeOfDeath != null)
/*     */     {
/* 122 */       drawCenteredString(this.fontRendererObj, this.causeOfDeath.getFormattedText(), this.width / 2, 85, 16777215);
/*     */     }
/*     */     
/* 125 */     drawCenteredString(this.fontRendererObj, String.valueOf(I18n.format("deathScreen.score", new Object[0])) + ": " + TextFormatting.YELLOW + this.mc.player.getScore(), this.width / 2, 100, 16777215);
/*     */     
/* 127 */     if (this.causeOfDeath != null && mouseY > 85 && mouseY < 85 + this.fontRendererObj.FONT_HEIGHT) {
/*     */       
/* 129 */       ITextComponent itextcomponent = getClickedComponentAt(mouseX);
/*     */       
/* 131 */       if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null)
/*     */       {
/* 133 */         handleComponentHover(itextcomponent, mouseX, mouseY);
/*     */       }
/*     */     } 
/*     */     
/* 137 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ITextComponent getClickedComponentAt(int p_184870_1_) {
/* 143 */     if (this.causeOfDeath == null)
/*     */     {
/* 145 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 149 */     int i = this.mc.fontRendererObj.getStringWidth(this.causeOfDeath.getFormattedText());
/* 150 */     int j = this.width / 2 - i / 2;
/* 151 */     int k = this.width / 2 + i / 2;
/* 152 */     int l = j;
/*     */     
/* 154 */     if (p_184870_1_ >= j && p_184870_1_ <= k) {
/*     */       
/* 156 */       for (ITextComponent itextcomponent : this.causeOfDeath) {
/*     */         
/* 158 */         l += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(itextcomponent.getUnformattedComponentText(), false));
/*     */         
/* 160 */         if (l > p_184870_1_)
/*     */         {
/* 162 */           return itextcomponent;
/*     */         }
/*     */       } 
/*     */       
/* 166 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 188 */     super.updateScreen();
/* 189 */     this.enableButtonsTimer++;
/*     */     
/* 191 */     if (this.enableButtonsTimer == 20)
/*     */     {
/* 193 */       for (GuiButton guibutton : this.buttonList)
/*     */       {
/* 195 */         guibutton.enabled = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiGameOver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
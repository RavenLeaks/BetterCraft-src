/*     */ package me.nzxter.bettercraft.mods.altmanager;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiErrorScreen;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiAltLogin
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiScreen before;
/*     */   private GuiTextField emailField;
/*     */   private GuiTextField passwordField;
/*     */   
/*     */   public GuiAltLogin(GuiScreen before) {
/*  25 */     this.before = before;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  30 */     Keyboard.enableRepeatEvents(false);
/*  31 */     super.onGuiClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     Keyboard.enableRepeatEvents(true);
/*  37 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 60, "Log in"));
/*  38 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 85, "Back"));
/*  39 */     this.emailField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 10, 200, 20);
/*  40 */     this.emailField.setMaxStringLength(254);
/*  41 */     this.emailField.setFocused(true);
/*     */     
/*  43 */     this.passwordField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 100, this.height / 2 + 25, 200, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  48 */     super.actionPerformed(button);
/*  49 */     if (button.id == 0) {
/*  50 */       Alt loginAlt = new Alt(this.emailField.getText(), this.passwordField.getText());
/*  51 */       if (loginAlt.cracked) {
/*  52 */         Login.changeName(loginAlt.name);
/*  53 */         this.mc.displayGuiScreen((GuiScreen)new GuiErrorScreen("Success!", "You now switched accounts! (cracked)"));
/*     */       } else {
/*     */         try {
/*  56 */           Login.login(loginAlt.email, loginAlt.password);
/*  57 */           this.mc.displayGuiScreen((GuiScreen)new GuiErrorScreen("Success!", "You now switched accounts! (premium)"));
/*  58 */         } catch (Exception e) {
/*  59 */           e.printStackTrace();
/*  60 */           this.mc.displayGuiScreen((GuiScreen)new GuiErrorScreen("Error", "Could not log in..."));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     if (button.id == 1) {
/*  66 */       this.mc.displayGuiScreen(this.before);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  72 */     super.keyTyped(typedChar, keyCode);
/*  73 */     this.emailField.textboxKeyTyped(typedChar, keyCode);
/*  74 */     this.passwordField.textboxKeyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  79 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  80 */     this.emailField.mouseClicked(mouseX, mouseY, mouseButton);
/*  81 */     this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  86 */     this.emailField.updateCursorCounter();
/*  87 */     this.passwordField.updateCursorCounter();
/*  88 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  93 */     drawDefaultBackground();
/*     */     
/*  95 */     this.emailField.drawTextBox();
/*  96 */     this.passwordField.drawTextBox();
/*  97 */     this.fontRendererObj.drawString("§7E-mail / Username: ", this.width / 2 - 100, this.height / 2 - 21, 16777215);
/*  98 */     this.fontRendererObj.drawString("§7Password: ", this.width / 2 - 100, this.height / 2 + 14, 16777215);
/*  99 */     drawCenteredString(this.fontRendererObj, "§7(Leave password blank for cracked login)", this.width / 2, 
/* 100 */         this.height / 2 + 50, 16777215);
/* 101 */     GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*     */     
/* 103 */     GlStateManager.scale(0.25D, 0.25D, 1.0D);
/* 104 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\GuiAltLogin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
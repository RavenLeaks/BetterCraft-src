/*     */ package me.nzxter.bettercraft.mods.mcleaks;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.nzxter.bettercraft.mods.altmanager.GuiAltManager;
/*     */ import me.nzxter.bettercraft.utils.NameUtils;
/*     */ import me.nzxter.bettercraft.utils.OpenWebsiteUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.util.Session;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMcLeaksLogin
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiTextField tokenBox;
/*     */   private GuiScreen before;
/*     */   
/*     */   public GuiMcLeaksLogin(GuiScreen before) {
/*  29 */     this.before = before;
/*     */   }
/*     */   
/*  32 */   public static String renderText = "";
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  36 */     this.tokenBox.updateCursorCounter();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     Keyboard.enableRepeatEvents(true);
/*  42 */     renderText = "";
/*     */     
/*  44 */     this.buttonList.clear();
/*  45 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72 + 12, "Set"));
/*  46 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 12, "Generate Token"));
/*  47 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
/*  48 */     (this.tokenBox = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20)).setMaxStringLength(48);
/*  49 */     this.tokenBox.setFocused(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  54 */     Keyboard.enableRepeatEvents(false);
/*     */   } protected void actionPerformed(GuiButton button) throws IOException {
/*     */     McLeaksAPI.RedeemedSession redeemSession;
/*     */     McLeaksAPI.RedeemedSession sessions_mcLeaksSession;
/*     */     McLeaksAPI.RedeemedSession ses;
/*  59 */     switch (button.id) {
/*     */       case 0:
/*  61 */         this.mc.displayGuiScreen((GuiScreen)new GuiAltManager(this.before));
/*     */         break;
/*     */       
/*     */       case 1:
/*  65 */         renderText = "§aSuccesful";
/*  66 */         redeemSession = McLeaksAPI.redeemSession(this.tokenBox.getText());
/*  67 */         sessions_mcLeaksSession = redeemSession;
/*     */         
/*  69 */         ses = redeemSession;
/*     */         
/*  71 */         McLeaksAPI.sessions_mcLeaksSession = redeemSession;
/*     */         
/*  73 */         Minecraft.session = new Session(ses.name, NameUtils.getUUID(ses.name).toString(), ses.session, "mojang");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  78 */         OpenWebsiteUtils.openLink("http://mcleaks.net/get");
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char par1, int par2) throws IOException {
/*  86 */     this.tokenBox.textboxKeyTyped(par1, par2);
/*  87 */     if (par2 == 28 || par2 == 156) {
/*  88 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
/*  94 */     super.mouseClicked(par1, par2, par3);
/*  95 */     this.tokenBox.mouseClicked(par1, par2, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int par1, int par2, float par3) {
/* 100 */     drawDefaultBackground();
/* 101 */     drawString(this.fontRendererObj, "Token", this.width / 2 - 18, 47, 10526880);
/* 102 */     this.tokenBox.drawTextBox();
/* 103 */     super.drawScreen(par1, par2, par3);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\mcleaks\GuiMcLeaksLogin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.nzxter.bettercraft.mods.rcon;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
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
/*     */ 
/*     */ public class GuiRconConnection
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiTextField nameBox;
/*     */   private GuiTextField ipBox;
/*     */   private GuiTextField passwordBox;
/*     */   private GuiTextField commandBox;
/*     */   private GuiTextField portBox;
/*     */   private GuiScreen before;
/*     */   
/*     */   public GuiRconConnection(GuiScreen before) {
/*  30 */     this.before = before;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     Keyboard.enableRepeatEvents(true);
/*  36 */     this.buttonList.clear();
/*  37 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 115, "§aStart"));
/*  38 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 140, "Back"));
/*     */     
/*  40 */     this.ipBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 40 + 40, 90, 20);
/*  41 */     this.ipBox.setFocused(false);
/*  42 */     this.ipBox.setText("127.0.0.1");
/*     */     
/*  44 */     this.portBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 + 10, this.height / 40 + 40, 90, 20);
/*  45 */     this.portBox.setFocused(false);
/*  46 */     this.portBox.setText("25565");
/*     */     
/*  48 */     this.commandBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 50 + 85, 200, 20);
/*  49 */     this.commandBox.setFocused(false);
/*  50 */     this.commandBox.setText("/op " + Minecraft.getSession().getUsername());
/*     */     
/*  52 */     this.nameBox = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 50 + 130, 90, 20);
/*  53 */     this.nameBox.setMaxStringLength(48);
/*  54 */     this.nameBox.setFocused(true);
/*  55 */     this.nameBox.setText(Minecraft.getSession().getUsername());
/*     */     
/*  57 */     this.passwordBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 + 10, this.height / 50 + 130, 90, 20);
/*  58 */     this.passwordBox.setFocused(false);
/*  59 */     this.passwordBox.setText("Password");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  66 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton clickedButton) {
/*  71 */     if (clickedButton.id == 1) {
/*  72 */       this.mc.displayGuiScreen(this.before);
/*     */     }
/*  74 */     else if (clickedButton.id == 0 && !this.nameBox.getText().isEmpty() && !this.passwordBox.getText().isEmpty() && !this.ipBox.getText().isEmpty() && !this.commandBox.getText().isEmpty() && !this.portBox.getText().isEmpty()) {
/*  75 */       Throwable throwable = null;
/*  76 */       Object var3_4 = null;
/*     */       try {
/*  78 */         Exception exception2, exception1 = null;
/*     */ 
/*     */       
/*     */       }
/*  82 */       catch (Throwable throwable2) {
/*  83 */         throwable = throwable2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char par1, int par2) {
/*  90 */     this.nameBox.textboxKeyTyped(par1, par2);
/*  91 */     this.passwordBox.textboxKeyTyped(par1, par2);
/*  92 */     this.ipBox.textboxKeyTyped(par1, par2);
/*  93 */     this.commandBox.textboxKeyTyped(par1, par2);
/*  94 */     this.portBox.textboxKeyTyped(par1, par2);
/*  95 */     if (par2 == 28 || par2 == 156) {
/*  96 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
/* 102 */     super.mouseClicked(par1, par2, par3);
/* 103 */     this.nameBox.mouseClicked(par1, par2, par3);
/* 104 */     this.passwordBox.mouseClicked(par1, par2, par3);
/* 105 */     this.ipBox.mouseClicked(par1, par2, par3);
/* 106 */     this.commandBox.mouseClicked(par1, par2, par3);
/* 107 */     this.portBox.mouseClicked(par1, par2, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 112 */     drawDefaultBackground();
/* 113 */     drawString(this.fontRendererObj, "Server IP", this.width / 2 - 80, this.height / 50 + 30, 10526880);
/* 114 */     drawString(this.fontRendererObj, "Port", this.width / 2 + 45, this.height / 50 + 30, 10526880);
/* 115 */     drawString(this.fontRendererObj, "Command", this.width / 2 - 20, this.height / 50 + 75, 10526880);
/* 116 */     drawString(this.fontRendererObj, "Username", this.width / 2 - 80, this.height / 50 + 120, 10526880);
/* 117 */     drawString(this.fontRendererObj, "Password", this.width / 2 + 30, this.height / 50 + 120, 10526880);
/* 118 */     this.nameBox.drawTextBox();
/* 119 */     this.passwordBox.drawTextBox();
/* 120 */     this.ipBox.drawTextBox();
/* 121 */     this.commandBox.drawTextBox();
/* 122 */     this.portBox.drawTextBox();
/* 123 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\rcon\GuiRconConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
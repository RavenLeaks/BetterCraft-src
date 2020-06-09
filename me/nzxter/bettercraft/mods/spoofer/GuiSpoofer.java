/*     */ package me.nzxter.bettercraft.mods.spoofer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import me.nzxter.bettercraft.utils.UUIDFetcherUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiSpoofer
/*     */   extends GuiScreen
/*     */ {
/*  20 */   public static String FakeIp = null;
/*  21 */   public static String FakeUUID = null;
/*  22 */   public static String renderText = "";
/*     */   
/*     */   private GuiTextField field_1;
/*     */   private GuiTextField field_2;
/*     */   private GuiTextField field_3;
/*     */   private GuiScreen before;
/*     */   
/*     */   public GuiSpoofer(GuiScreen before) {
/*  30 */     this.before = before;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     renderText = "";
/*     */     
/*  37 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 30, this.height / 2 - 90, 60, 20, "§6Spoof"));
/*  38 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 5, this.height / 2 + 70, 40, 20, "Back"));
/*  39 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 5, this.height / 2 + 40, 70, 20, "RandomUUID"));
/*  40 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 40, 70, 20, "CurrentUUID"));
/*  41 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 45, this.height / 2 + 70, 40, 20, "Clear"));
/*     */     
/*  43 */     this.field_1 = new GuiTextField(100, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2 - 20, 200, 20);
/*  44 */     this.field_1.setMaxStringLength(100);
/*  45 */     this.field_1.setText("FakeIp");
/*  46 */     this.field_1.setText((FakeIp != null) ? FakeIp : "");
/*     */     
/*  48 */     this.field_2 = new GuiTextField(100, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2 - 20 + 35, 200, 20);
/*  49 */     this.field_2.setMaxStringLength(100);
/*  50 */     this.field_2.setText("FakeUUID");
/*  51 */     this.field_2.setText((FakeUUID != null) ? FakeUUID : "");
/*     */     
/*  53 */     this.field_3 = new GuiTextField(100, this.mc.fontRendererObj, this.width / 2 - 100, this.height / 2 - 20 - 35, 200, 20);
/*  54 */     this.field_3.setMaxStringLength(100);
/*  55 */     this.field_3.setText("CrackedName");
/*  56 */     this.field_3.setText((Minecraft.getSession()).username);
/*  57 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  62 */     switch (button.id) {
/*     */       case 0:
/*  64 */         this.mc.displayGuiScreen(this.before);
/*     */ 
/*     */       
/*     */       case 1:
/*  68 */         FakeIp = !this.field_1.getText().trim().isEmpty() ? this.field_1.getText().trim() : null;
/*  69 */         FakeUUID = !this.field_2.getText().trim().isEmpty() ? this.field_2.getText().trim() : null;
/*  70 */         if (!this.field_3.getText().trim().isEmpty()) {
/*  71 */           Minecraft.getMinecraft().login(this.field_3.getText().trim());
/*     */         }
/*  73 */         renderText = "§aSuccessful";
/*     */       
/*     */       default:
/*     */         return;
/*     */       
/*     */       case 3:
/*  79 */         this.field_2.setText((String)UUID.randomUUID());
/*     */ 
/*     */       
/*     */       case 4:
/*  83 */         if (this.field_3.getText().trim().isEmpty()) {
/*  84 */           this.field_2.setText((UUIDFetcherUtils.getUUID((Minecraft.getSession()).username) != null) ? UUIDFetcherUtils.getUUID((Minecraft.getSession()).username).toString() : "§4Error");
/*     */         } else {
/*     */           
/*  87 */           this.field_2.setText((UUIDFetcherUtils.getUUID(this.field_3.getText().trim()) != null) ? UUIDFetcherUtils.getUUID(this.field_3.getText().trim()).toString() : "§4Error");
/*     */         } 
/*     */       case 5:
/*     */         break;
/*  91 */     }  this.field_1.setText("");
/*  92 */     this.field_2.setText("");
/*  93 */     this.field_3.setText("");
/*  94 */     FakeIp = null;
/*  95 */     FakeUUID = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 102 */     drawDefaultBackground();
/* 103 */     this.field_3.drawTextBox();
/* 104 */     this.field_1.drawTextBox();
/* 105 */     this.field_2.drawTextBox();
/* 106 */     drawCenteredString(this.mc.fontRendererObj, "§7Fake IP", this.width / 2, this.height / 2 - 20 - 10, Color.WHITE.hashCode());
/* 107 */     drawCenteredString(this.mc.fontRendererObj, "§7Cracked Username", this.width / 2, this.height / 2 - 20 - 45, Color.WHITE.hashCode());
/* 108 */     drawCenteredString(this.mc.fontRendererObj, "§7Fake UUID", this.width / 2, this.height / 2 - 20 + 25, Color.WHITE.hashCode());
/* 109 */     GL11.glPushMatrix();
/* 110 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 111 */     GL11.glScaled(4.0D, 4.0D, 4.0D);
/* 112 */     drawCenteredString(this.mc.fontRendererObj, renderText, this.width / 8, this.height / 4 - this.mc.fontRendererObj.FONT_HEIGHT, 0);
/* 113 */     GL11.glPopMatrix();
/* 114 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 119 */     this.field_1.mouseClicked(mouseX, mouseY, mouseButton);
/* 120 */     this.field_2.mouseClicked(mouseX, mouseY, mouseButton);
/* 121 */     this.field_3.mouseClicked(mouseX, mouseY, mouseButton);
/* 122 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 127 */     if (keyCode == 15) {
/* 128 */       if (this.field_1.isFocused()) {
/* 129 */         this.field_1.setFocused(false);
/* 130 */         this.field_3.setFocused(true);
/*     */         return;
/*     */       } 
/* 133 */       if (this.field_2.isFocused()) {
/* 134 */         this.field_2.setFocused(false);
/* 135 */         this.field_1.setFocused(true);
/*     */         return;
/*     */       } 
/* 138 */       if (this.field_3.isFocused()) {
/* 139 */         this.field_3.setFocused(false);
/* 140 */         this.field_2.setFocused(true);
/*     */         return;
/*     */       } 
/* 143 */       this.field_1.setFocused(true);
/*     */     } 
/* 145 */     this.field_1.textboxKeyTyped(typedChar, keyCode);
/* 146 */     this.field_2.textboxKeyTyped(typedChar, keyCode);
/* 147 */     this.field_3.textboxKeyTyped(typedChar, keyCode);
/* 148 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\spoofer\GuiSpoofer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
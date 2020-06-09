/*    */ package me.nzxter.bettercraft.mods.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.utils.NameUtils;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiChat;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiNameHistory
/*    */   extends GuiScreen
/*    */ {
/*    */   public GuiTextField name;
/*    */   public String[] loadedNames;
/*    */   
/*    */   public void initGui() {
/* 20 */     this.buttonList.clear();
/* 21 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72 + 12, 100, 20, "Start"));
/* 22 */     this.buttonList.add(new GuiButton(4, this.width / 2, this.height / 4 + 72 + 12, 100, 20, "Clear"));
/* 23 */     this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + 12, "Back"));
/* 24 */     this.name = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 70, 200, 20);
/* 25 */     this.name.setMaxStringLength(16);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 30 */     switch (button.id) {
/*    */       case 1:
/* 32 */         if (this.name.getText().isEmpty())
/* 33 */           break;  this.loadedNames = NameUtils.getNameHistory(NameUtils.getUUID(this.name.getText().trim()));
/*    */         break;
/*    */       
/*    */       case 3:
/* 37 */         if (this.mc.world != null) {
/* 38 */           this.mc.displayGuiScreen((GuiScreen)new GuiChat());
/*    */           break;
/*    */         } 
/* 41 */         this.mc.displayGuiScreen(null);
/*    */         break;
/*    */       
/*    */       case 4:
/* 45 */         this.loadedNames = null;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 52 */     this.name.updateCursorCounter();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 57 */     this.name.textboxKeyTyped(typedChar, keyCode);
/* 58 */     super.keyTyped(typedChar, keyCode);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 63 */     this.name.mouseClicked(mouseX, mouseY, mouseButton);
/* 64 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 69 */     drawDefaultBackground();
/* 70 */     int y = 1;
/* 71 */     if (this.loadedNames != null) {
/* 72 */       byte b; int i; String[] arrayOfString; for (i = (arrayOfString = this.loadedNames).length, b = 0; b < i; ) { String s2 = arrayOfString[b];
/* 73 */         drawCenteredString(this.fontRendererObj, "§7" + s2, this.width / 2, y + 10, -1);
/* 74 */         y += 10; b++; }
/*    */     
/*    */     } 
/* 77 */     this.name.drawTextBox();
/* 78 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gui\GuiNameHistory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
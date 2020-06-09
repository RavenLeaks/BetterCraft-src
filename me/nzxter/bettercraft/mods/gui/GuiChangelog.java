/*    */ package me.nzxter.bettercraft.mods.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiChangelog
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen before;
/*    */   
/*    */   public GuiChangelog(GuiScreen before) {
/* 14 */     this.before = before;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 19 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 20, "Back"));
/* 20 */     super.initGui();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 25 */     if (button.id == 0) {
/* 26 */       this.mc.displayGuiScreen(this.before);
/*    */     }
/* 28 */     super.actionPerformed(button);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 33 */     drawDefaultBackground();
/* 34 */     drawRect(20, 20, this.width - 20, this.height - 20, 1342177280);
/* 35 */     GlStateManager.scale(2.0F, 2.0F, 0.0F);
/* 36 */     drawString(this.fontRendererObj, "BetterCraft - Changelog", 12, 12, -1);
/* 37 */     GlStateManager.scale(1.0F, 1.0F, 0.0F);
/* 38 */     drawString(this.fontRendererObj, "§61.3.5", 25, 30, -1);
/* 39 */     GlStateManager.scale(0.5D, 0.5D, 0.0D);
/* 40 */     drawString(this.fontRendererObj, "§aNew Gui ", 30, 85, -1);
/* 41 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gui\GuiChangelog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
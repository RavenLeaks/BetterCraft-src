/*    */ package me.nzxter.bettercraft.mods.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.BetterCraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiCredits
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen before;
/*    */   
/*    */   public GuiCredits(GuiScreen before) {
/* 15 */     this.before = before;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 20 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 30, "Back"));
/* 21 */     super.initGui();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 26 */     if (button.id == 0) {
/* 27 */       this.mc.displayGuiScreen(this.before);
/*    */     }
/* 29 */     super.actionPerformed(button);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 34 */     drawDefaultBackground();
/* 35 */     drawRect(20, 20, this.width - 20, this.height - 40, 1342177280);
/* 36 */     GlStateManager.scale(2.0F, 2.0F, 0.0F);
/* 37 */     drawString(this.fontRendererObj, " §6BetterCraft §a" + BetterCraft.clientVersion + " §7by §4" + BetterCraft.clientAuthor, 25, 50, -1);
/* 38 */     GlStateManager.scale(0.5D, 0.5D, 0.0D);
/* 39 */     drawString(this.fontRendererObj, "§7This is not a §eHackclient §7! It is §6BetterCraft §7!", 110, 150, -1);
/* 40 */     GlStateManager.scale(2.0F, 2.0F, 0.0F);
/* 41 */     drawString(this.fontRendererObj, "§cSpecial Thanks", 25, 125, -1);
/* 42 */     GlStateManager.scale(0.5D, 0.5D, 0.0D);
/*    */     
/* 44 */     drawString(this.fontRendererObj, "§exVoiceSyntax§r - Help with code", 50, 270, -1);
/* 45 */     drawString(this.fontRendererObj, "§eHarakiridev§r - Help with code", 50, 280, -1);
/* 46 */     drawString(this.fontRendererObj, "§eCryptonicDev§r - Help with code", 50, 290, -1);
/* 47 */     drawString(this.fontRendererObj, "§eilluminator3§r - Help with code", 50, 300, -1);
/* 48 */     drawString(this.fontRendererObj, "§eKomischerBoy§r - Help with code", 50, 310, -1);
/* 49 */     drawString(this.fontRendererObj, "§eFlori2007§r - Help with code", 50, 320, -1);
/* 50 */     drawString(this.fontRendererObj, "§ePutzefurcht§r - Help with code", 50, 330, -1);
/*    */     
/* 52 */     drawString(this.fontRendererObj, "§eSkriptAPI§r - Test Client", 50, 350, -1);
/* 53 */     drawString(this.fontRendererObj, "§eRockyytvy§r - Test Client", 50, 360, -1);
/* 54 */     GlStateManager.scale(2.0F, 2.0F, 0.0F);
/* 55 */     drawString(this.fontRendererObj, "§cLicense", 37, 190, -1);
/* 56 */     GlStateManager.scale(0.5D, 0.5D, 0.0D);
/* 57 */     drawString(this.fontRendererObj, "§aMinecraft§r - Copyright Mojang AB", 40, 400, -1);
/* 58 */     drawString(this.fontRendererObj, "§bLabyStudio§r - For coding Labymod", 40, 410, -1);
/* 59 */     drawString(this.fontRendererObj, "§8Optifine Team§r - For coding Optifine", 40, 420, -1);
/*    */     
/* 61 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gui\GuiCredits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
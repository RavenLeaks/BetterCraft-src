/*    */ package me.nzxter.bettercraft.mods.fritzbox;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.mods.spoofer.GuiSpoofer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiFritzbox
/*    */   extends GuiScreen
/*    */ {
/* 22 */   private Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   private GuiScreen before;
/*    */   
/*    */   public GuiFritzbox(GuiScreen before) {
/* 27 */     this.before = before;
/*    */   }
/*    */   
/* 30 */   public static String renderText = "";
/*    */   
/*    */   public void initGui() {
/* 33 */     renderText = "";
/* 34 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 140, "Back"));
/* 35 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 115, "§6Reconnect"));
/*    */   }
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 39 */     int id = button.id;
/*    */     
/* 41 */     if (id == 0) {
/* 42 */       this.mc.displayGuiScreen(this.before);
/*    */     }
/*    */     
/* 45 */     if (id == 1) {
/* 46 */       FritzboxReconnector.reconnectFritzBox();
/* 47 */       renderText = "§aSuccessful";
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 56 */     drawDefaultBackground();
/* 57 */     GL11.glPushMatrix();
/* 58 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 59 */     GL11.glScaled(4.0D, 4.0D, 4.0D);
/* 60 */     GuiSpoofer.drawCenteredString(this.mc.fontRendererObj, renderText, this.width / 8, this.height / 4 - this.mc.fontRendererObj.FONT_HEIGHT, 0);
/* 61 */     GL11.glPopMatrix();
/*    */     
/* 63 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\fritzbox\GuiFritzbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
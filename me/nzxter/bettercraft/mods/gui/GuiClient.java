/*    */ package me.nzxter.bettercraft.mods.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.mods.checkhost.GuiCheckHost;
/*    */ import me.nzxter.bettercraft.mods.fritzbox.GuiFritzbox;
/*    */ import me.nzxter.bettercraft.mods.music.GuiMusic;
/*    */ import me.nzxter.bettercraft.mods.portscanner.GuiPortScanner;
/*    */ import me.nzxter.bettercraft.mods.proxy.GuiUseProxy;
/*    */ import me.nzxter.bettercraft.mods.rcon.GuiRconConnection;
/*    */ import me.nzxter.bettercraft.mods.shader.GuiShader;
/*    */ import me.nzxter.bettercraft.mods.spoofer.GuiSpoofer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiClient
/*    */   extends GuiScreen
/*    */ {
/* 22 */   private Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   private GuiScreen before;
/*    */   
/*    */   public GuiClient(GuiScreen before) {
/* 27 */     this.before = before;
/*    */   }
/*    */   
/*    */   public void initGui() {
/* 31 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 3 - 25, "Checkhost"));
/*    */     
/* 33 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 3, 98, 20, "Port Scanner"));
/* 34 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 2, this.height / 3, 98, 20, "Spoofer"));
/*    */     
/* 36 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 3 + 25, 98, 20, "Fritzbox"));
/* 37 */     this.buttonList.add(new GuiButton(5, this.width / 2 + 2, this.height / 3 + 25, 98, 20, "Music"));
/*    */     
/* 39 */     this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 3 + 50, 98, 20, "Proxy"));
/* 40 */     this.buttonList.add(new GuiButton(7, this.width / 2 + 2, this.height / 3 + 50, 98, 20, "Rcon"));
/*    */     
/* 42 */     this.buttonList.add(new GuiButton(8, this.width / 2 - 100, this.height / 3 + 75, "Shader"));
/*    */     
/* 44 */     this.buttonList.add(new GuiButton(9, this.width / 2 - 100, this.height / 3 + 120, 200, 20, "Back"));
/*    */   }
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 48 */     int id = button.id;
/*    */     
/* 50 */     if (id == 1) {
/* 51 */       this.mc.displayGuiScreen((GuiScreen)new GuiCheckHost(this));
/*    */     }
/*    */     
/* 54 */     if (id == 2) {
/* 55 */       this.mc.displayGuiScreen((GuiScreen)new GuiPortScanner(this));
/*    */     }
/*    */     
/* 58 */     if (id == 3) {
/* 59 */       this.mc.displayGuiScreen((GuiScreen)new GuiSpoofer(this));
/*    */     }
/*    */     
/* 62 */     if (id == 4) {
/* 63 */       this.mc.displayGuiScreen((GuiScreen)new GuiFritzbox(this));
/*    */     }
/*    */     
/* 66 */     if (id == 5) {
/* 67 */       this.mc.displayGuiScreen((GuiScreen)new GuiMusic(this));
/*    */     }
/*    */     
/* 70 */     if (id == 6) {
/* 71 */       this.mc.displayGuiScreen((GuiScreen)new GuiUseProxy(this));
/*    */     }
/*    */     
/* 74 */     if (id == 7) {
/* 75 */       this.mc.displayGuiScreen((GuiScreen)new GuiRconConnection(this));
/*    */     }
/*    */     
/* 78 */     if (id == 8) {
/* 79 */       this.mc.displayGuiScreen((GuiScreen)new GuiShader(this));
/*    */     }
/*    */     
/* 82 */     if (id == 9) {
/* 83 */       this.mc.displayGuiScreen(this.before);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 88 */     return true;
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 92 */     drawDefaultBackground();
/* 93 */     GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 94 */     GlStateManager.scale(0.5D, 0.5D, 1.0D);
/* 95 */     GlStateManager.scale(0.5D, 0.5D, 1.0D);
/*    */     
/* 97 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gui\GuiClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package me.nzxter.bettercraft.mods.proxy;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ 
/*     */ public final class GuiUseProxy
/*     */   extends GuiScreen
/*     */ {
/*     */   static GuiTextField ip;
/*     */   static GuiScreen before;
/*     */   private static boolean isRunning;
/*     */   private GuiButton button;
/*     */   private String status;
/*     */   
/*     */   public GuiUseProxy(GuiScreen before) {
/*  31 */     GuiUseProxy.before = before;
/*     */   }
/*     */ 
/*     */   
/*  35 */   public static String renderText = "";
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  40 */     switch (button.id) {
/*     */       case 1:
/*  42 */         this.mc.displayGuiScreen(before);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 0:
/*  47 */         if (!isRunning) {
/*  48 */           String[] split = ip.getText().split(":");
/*     */           
/*  50 */           if (split.length == 2) {
/*     */             
/*  52 */             ProxyManager.setProxy(ProxyManager.getProxyFromString(ip.getText()));
/*     */             
/*  54 */             this.status = "§6Proxy used " + ProxyManager.getProxy().address().toString();
/*     */             
/*  56 */             renderText = "§aSuccessful";
/*  57 */             isRunning = true;
/*  58 */             button.displayString = "§cDisconnect";
/*     */             break;
/*     */           } 
/*  61 */           this.status = "§cPlease use: <host>:<port>";
/*     */           break;
/*     */         } 
/*  64 */         isRunning = false;
/*  65 */         button.displayString = "§aConnect";
/*  66 */         ProxyManager.setProxy(null);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int x, int y, float z) {
/*  75 */     ScaledResolution scaledRes = new ScaledResolution(this.mc);
/*  76 */     drawDefaultBackground();
/*  77 */     GL11.glPushMatrix();
/*  78 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/*  79 */     GL11.glScaled(4.0D, 4.0D, 4.0D);
/*  80 */     drawCenteredString(this.mc.fontRendererObj, renderText, this.width / 8, this.height / 4 - this.mc.fontRendererObj.FONT_HEIGHT, 0);
/*  81 */     GL11.glPopMatrix();
/*  82 */     drawCenteredString(this.mc.fontRendererObj, this.status, this.width / 2, 20, -1);
/*  83 */     ip.drawTextBox();
/*  84 */     drawCenteredString(this.mc.fontRendererObj, "§7Proxy IP:Port", this.width / 2, 50, -1);
/*  85 */     super.drawScreen(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  91 */     renderText = "";
/*     */     
/*  93 */     this.buttonList.add(this.button = new GuiButton(0, this.width / 2 - 100, this.height / 3 + 40, 200, 20, !isRunning ? "§aConnect" : "§cDisconnect"));
/*  94 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 3 + 66, 200, 20, "Back"));
/*  95 */     ip = new GuiTextField(this.height, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  96 */     ip.setMaxStringLength(100);
/*  97 */     ip.setText("127.0.0.1:8080");
/*  98 */     this.status = "§6Waiting...";
/*     */     
/* 100 */     ip.setFocused(true);
/* 101 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char character, int key) {
/*     */     try {
/* 107 */       super.keyTyped(character, key);
/*     */     }
/* 109 */     catch (IOException e) {
/* 110 */       e.printStackTrace();
/*     */     } 
/* 112 */     if (character == '\r') {
/* 113 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/* 115 */     ip.textboxKeyTyped(character, key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int x, int y, int button) {
/*     */     try {
/* 121 */       super.mouseClicked(x, y, button);
/*     */     }
/* 123 */     catch (IOException e) {
/* 124 */       e.printStackTrace();
/*     */     } 
/* 126 */     ip.mouseClicked(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 131 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 136 */     ip.updateCursorCounter();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\proxy\GuiUseProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
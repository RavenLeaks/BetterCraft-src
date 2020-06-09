/*     */ package me.nzxter.bettercraft.mods.portscanner;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiPortScanner
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiTextField hostField;
/*     */   private GuiTextField minPortField;
/*     */   private GuiTextField maxPortField;
/*     */   private GuiTextField threadsField;
/*     */   private GuiButton buttonToggle;
/*     */   private boolean running;
/*  26 */   private String status = "§6Waiting...";
/*     */   private String host;
/*     */   private int currentPort;
/*     */   private int maxPort;
/*     */   private int minPort;
/*     */   private int checkedPort;
/*  32 */   private final List<Integer> ports = new ArrayList<>();
/*     */   
/*     */   private final GuiScreen before;
/*     */   
/*     */   public GuiPortScanner(GuiScreen before) {
/*  37 */     this.before = before;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     Keyboard.enableRepeatEvents(true);
/*  43 */     this.hostField = new GuiTextField(0, this.mc.fontRendererObj, this.width / 2 - 100, 40, 200, 20);
/*  44 */     this.hostField.setFocused(true);
/*  45 */     this.hostField.setMaxStringLength(2147483647);
/*  46 */     this.hostField.setText("127.0.0.1");
/*     */     
/*  48 */     this.minPortField = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, 80, 90, 20);
/*  49 */     this.minPortField.setMaxStringLength(5);
/*  50 */     this.minPortField.setText(String.valueOf(25500));
/*     */     
/*  52 */     this.maxPortField = new GuiTextField(2, this.mc.fontRendererObj, this.width / 2 + 10, 80, 90, 20);
/*  53 */     this.maxPortField.setMaxStringLength(5);
/*  54 */     this.maxPortField.setText(String.valueOf(25600));
/*     */     
/*  56 */     this.threadsField = new GuiTextField(3, this.mc.fontRendererObj, this.width / 2 - 100, 120, 200, 20);
/*  57 */     this.threadsField.setMaxStringLength(2147483647);
/*  58 */     this.threadsField.setText(String.valueOf(500));
/*     */     
/*  60 */     this.buttonToggle = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 115, this.running ? "Stop" : "Start");
/*  61 */     this.buttonList.add(this.buttonToggle);
/*  62 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 140, "Back"));
/*  63 */     super.initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  71 */     drawDefaultBackground();
/*  72 */     drawCenteredString(this.mc.fontRendererObj, "Server IP", this.width / 2, 30, 10526880);
/*  73 */     drawCenteredString(this.mc.fontRendererObj, "Port von", this.width / 2 - 55, 70, 10526880);
/*  74 */     drawCenteredString(this.mc.fontRendererObj, "Port bis", this.width / 2 + 55, 70, 10526880);
/*  75 */     drawCenteredString(this.mc.fontRendererObj, "Threads", this.width / 2, 110, 10526880);
/*     */     
/*  77 */     drawCenteredString(this.mc.fontRendererObj, this.running ? ("§6" + this.checkedPort + " §8/ §6" + this.maxPort) : ((this.status == null) ? "" : this.status), this.width / 2, this.height / 4 + 95, 16777215);
/*  78 */     this.buttonToggle.displayString = this.running ? "§cStop" : "§aStart";
/*  79 */     this.hostField.drawTextBox();
/*  80 */     this.minPortField.drawTextBox();
/*  81 */     this.maxPortField.drawTextBox();
/*  82 */     this.threadsField.drawTextBox();
/*  83 */     drawString(this.mc.fontRendererObj, "§6Open Ports:", 2, 2, Color.WHITE.hashCode());
/*  84 */     List<Integer> list = this.ports;
/*  85 */     synchronized (list) {
/*  86 */       int i = 12;
/*  87 */       for (Integer integer : this.ports) {
/*  88 */         drawString(this.mc.fontRendererObj, String.valueOf(integer), 2, i, Color.WHITE.hashCode());
/*  89 */         i += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     } 
/*  92 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  97 */     switch (button.id) {
/*     */       case 0:
/*  99 */         this.mc.displayGuiScreen(this.before);
/*     */         break;
/*     */       
/*     */       case 1:
/* 103 */         if (this.running) {
/* 104 */           this.running = false;
/*     */         } else {
/*     */           int threads;
/* 107 */           this.host = this.hostField.getText();
/* 108 */           if (this.host.isEmpty()) {
/* 109 */             this.status = "§cInvalid host";
/*     */             return;
/*     */           } 
/*     */           try {
/* 113 */             this.minPort = Integer.parseInt(this.minPortField.getText());
/*     */           }
/* 115 */           catch (NumberFormatException e) {
/* 116 */             this.status = "§cInvalid min port";
/*     */             return;
/*     */           } 
/*     */           try {
/* 120 */             this.maxPort = Integer.parseInt(this.maxPortField.getText());
/*     */           }
/* 122 */           catch (NumberFormatException e) {
/* 123 */             this.status = "§cInvalid max port";
/*     */             return;
/*     */           } 
/*     */           try {
/* 127 */             threads = Integer.parseInt(this.threadsField.getText());
/*     */           }
/* 129 */           catch (NumberFormatException e) {
/* 130 */             this.status = "§cInvalid threads";
/*     */             return;
/*     */           } 
/* 133 */           this.ports.clear();
/* 134 */           this.currentPort = this.minPort - 1;
/* 135 */           this.checkedPort = this.minPort;
/* 136 */           for (int i = 0; i < threads; i++) {
/* 137 */             (new Thread(() -> {
/*     */                   try {
/*     */                     while (this.running && this.currentPort < this.maxPort) {
/*     */                       int port = ++this.currentPort;
/*     */                       
/*     */                       try {
/*     */                         Socket socket = new Socket();
/*     */                         socket.connect(new InetSocketAddress(this.host, port), 500);
/*     */                         socket.close();
/*     */                         List<Integer> list = this.ports;
/*     */                         synchronized (list) {
/*     */                           if (!this.ports.contains(Integer.valueOf(port))) {
/*     */                             this.ports.add(Integer.valueOf(port));
/*     */                           }
/*     */                         } 
/* 152 */                       } catch (Exception exception) {}
/*     */                       
/*     */                       if (this.checkedPort >= port) {
/*     */                         continue;
/*     */                       }
/*     */                       this.checkedPort = port;
/*     */                     } 
/*     */                     this.running = false;
/*     */                     this.buttonToggle.displayString = "Start";
/* 161 */                   } catch (Exception e) {
/*     */                     this.status = "§a§l" + e.getClass().getSimpleName() + ": §c" + e.getMessage();
/*     */                   } 
/* 164 */                 })).start();
/*     */           } 
/* 166 */           this.running = true;
/*     */         } 
/* 168 */         this.buttonToggle.displayString = this.running ? "Stop" : "Start";
/*     */         break;
/*     */     } 
/* 171 */     super.actionPerformed(button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 176 */     if (1 == keyCode) {
/* 177 */       this.mc.displayGuiScreen(this.before);
/*     */       return;
/*     */     } 
/* 180 */     if (this.running) {
/*     */       return;
/*     */     }
/* 183 */     if (this.hostField.isFocused()) {
/* 184 */       this.hostField.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/* 186 */     if (this.minPortField.isFocused() && !Character.isLetter(typedChar)) {
/* 187 */       this.minPortField.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/* 189 */     if (this.maxPortField.isFocused() && !Character.isLetter(typedChar)) {
/* 190 */       this.maxPortField.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/* 192 */     if (this.threadsField.isFocused() && !Character.isLetter(typedChar)) {
/* 193 */       this.threadsField.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/* 195 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 200 */     this.hostField.mouseClicked(mouseX, mouseY, mouseButton);
/* 201 */     this.minPortField.mouseClicked(mouseX, mouseY, mouseButton);
/* 202 */     this.maxPortField.mouseClicked(mouseX, mouseY, mouseButton);
/* 203 */     this.threadsField.mouseClicked(mouseX, mouseY, mouseButton);
/* 204 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 209 */     this.hostField.updateCursorCounter();
/* 210 */     this.minPortField.updateCursorCounter();
/* 211 */     this.maxPortField.updateCursorCounter();
/* 212 */     this.threadsField.updateCursorCounter();
/* 213 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 218 */     Keyboard.enableRepeatEvents(false);
/* 219 */     this.running = false;
/* 220 */     super.onGuiClosed();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\portscanner\GuiPortScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
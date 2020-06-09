/*     */ package me.nzxter.bettercraft.mods.altmanager;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import me.nzxter.bettercraft.mods.mcleaks.GuiMcLeaksLogin;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiAltManager
/*     */   extends GuiScreen
/*     */ {
/*  31 */   private GuiScreen before = null;
/*     */   
/*     */   private GuiTextField searchField;
/*     */   
/*     */   private GuiTextField emailField;
/*     */   
/*     */   private GuiTextField passField;
/*     */   
/*     */   private boolean displayingLogin = false;
/*     */   private final JFileChooser fileChooser;
/*  41 */   private int selected = 0;
/*     */   
/*  43 */   private JFrame frame = new JFrame("FileChooser");
/*     */   
/*  45 */   private int scroll = 0;
/*     */   
/*     */   private boolean failed = false;
/*  48 */   private String failMessage = null;
/*     */   
/*     */   public GuiAltManager(GuiScreen before) {
/*  51 */     this.before = before;
/*  52 */     this.fileChooser = new JFileChooser();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  58 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 90, this.height - 44 - 5 + 26, 88, 20, "Login Selected"));
/*  59 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 0, this.height - 44 - 5 + 26, 88, 20, "Remove Selected"));
/*  60 */     this.buttonList.add(new GuiButton(2, 3, 3, 88, 20, "Direct Login"));
/*  61 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 90, this.height - 44 - 5 + 26, 48, 20, "Back"));
/*  62 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 140, this.height - 44 - 5 + 26, 48, 20, "Add Alt"));
/*  63 */     this.buttonList.add(new GuiButton(5, this.width - 135, 5, 133, 20, "Import from Accountlist"));
/*  64 */     this.buttonList.add(new GuiButton(6, this.width - 135, 28, 133, 20, "Export to Accountlist"));
/*     */     
/*  66 */     this.buttonList.add(new GuiButton(10, this.width / 2 + 1 + 100, this.height / 2, 48, 15, "Add"));
/*     */     
/*  68 */     this.buttonList.add(new GuiButton(11, this.width / 2 - 50, this.height - 80, 100, 20, "Okay"));
/*     */     
/*  70 */     this.buttonList.add(new GuiButton(12, 3, 26, 88, 20, "McLeaks"));
/*     */     
/*  72 */     ((GuiButton)this.buttonList.get(7)).visible = false;
/*  73 */     ((GuiButton)this.buttonList.get(8)).visible = false;
/*     */     
/*  75 */     this.searchField = new GuiTextField(7, this.fontRendererObj, this.width / 2 - 50, 50, 98, 15);
/*  76 */     this.searchField.setMaxStringLength(666);
/*  77 */     this.searchField.setFocused(true);
/*     */     
/*  79 */     this.emailField = new GuiTextField(8, this.fontRendererObj, this.width / 2 - 99 - 50, this.height / 2, 145, 15);
/*  80 */     this.emailField.setMaxStringLength(666);
/*     */     
/*  82 */     this.passField = new GuiTextField(9, this.fontRendererObj, this.width / 2 + 1, this.height / 2, 95, 15);
/*  83 */     this.passField.setMaxStringLength(666);
/*     */     
/*  85 */     if (this.displayingLogin) {
/*  86 */       displayLogin();
/*     */     } else {
/*  88 */       stopDisplayingLogin();
/*     */     } 
/*     */     
/*  91 */     if (this.failed) {
/*  92 */       startDisplayingFailed();
/*     */     } else {
/*  94 */       stopDisplayingFailed();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void displayLogin() {
/*  99 */     this.searchField.setFocused(false);
/* 100 */     this.displayingLogin = true;
/* 101 */     ((GuiButton)this.buttonList.get(7)).visible = true;
/* 102 */     for (GuiButton btn : this.buttonList) {
/* 103 */       btn.enabled = !(!btn.displayString.equals("Add") && !btn.displayString.equals("Back"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void stopDisplayingLogin() {
/* 108 */     this.searchField.setFocused(true);
/* 109 */     this.displayingLogin = false;
/* 110 */     ((GuiButton)this.buttonList.get(7)).visible = false;
/* 111 */     for (GuiButton btn : this.buttonList) {
/* 112 */       btn.enabled = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void startDisplayingFailed() {
/* 117 */     this.searchField.setFocused(false);
/* 118 */     this.failed = true;
/* 119 */     ((GuiButton)this.buttonList.get(8)).visible = true;
/* 120 */     for (GuiButton btn : this.buttonList) {
/* 121 */       btn.enabled = btn.displayString.equals("Okay");
/*     */     }
/*     */   }
/*     */   
/*     */   public void stopDisplayingFailed() {
/* 126 */     this.searchField.setFocused(true);
/* 127 */     this.failed = false;
/* 128 */     ((GuiButton)this.buttonList.get(8)).visible = false;
/* 129 */     for (GuiButton btn : this.buttonList) {
/* 130 */       btn.enabled = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 136 */     if (button.id == 2) {
/* 137 */       this.mc.displayGuiScreen(new GuiAltLogin(this.before));
/*     */     }
/* 139 */     if (button.id == 3) {
/* 140 */       if (this.displayingLogin) {
/* 141 */         stopDisplayingLogin();
/*     */       } else {
/* 143 */         this.mc.displayGuiScreen(this.before);
/*     */       } 
/*     */     }
/*     */     
/* 147 */     if (button.id == 4) {
/* 148 */       displayLogin();
/*     */     }
/*     */     
/* 151 */     if (button.id == 12) {
/* 152 */       this.mc.displayGuiScreen((GuiScreen)new GuiMcLeaksLogin(this.before));
/*     */     }
/*     */     
/* 155 */     if (button.id == 10) {
/* 156 */       Alt alt = new Alt(this.emailField.getText(), this.passField.getText());
/* 157 */       AltManager.addAlt(alt);
/* 158 */       stopDisplayingLogin();
/*     */     } 
/* 160 */     if (button.id == 11) {
/* 161 */       stopDisplayingFailed();
/*     */     }
/* 163 */     if (button.id == 1) {
/* 164 */       AltManager.getAlts().remove(AltManager.getAlts().get(this.selected));
/*     */     }
/* 166 */     if (button.id == 0) {
/* 167 */       Alt alt = AltManager.getAlts().get(this.selected);
/* 168 */       if (alt.cracked) {
/* 169 */         Login.changeName(alt.name);
/* 170 */         AltManager.loggedInName = ((Alt)AltManager.getAlts().get(this.selected)).name;
/*     */       } else {
/*     */         
/*     */         try {
/* 174 */           boolean changeIndex = Login.login(alt.email, alt.password);
/* 175 */           if (changeIndex) {
/* 176 */             AltManager.loggedInName = ((Alt)AltManager.getAlts().get(this.selected)).name;
/*     */           }
/* 178 */         } catch (Exception e) {
/* 179 */           e.printStackTrace();
/* 180 */           System.out.println("Login failed!");
/* 181 */           this.failMessage = e.getMessage();
/* 182 */           startDisplayingFailed();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     if (button.id == 5) {
/* 188 */       this.frame.setVisible(true);
/* 189 */       this.frame.setAlwaysOnTop(true);
/* 190 */       int result = this.fileChooser.showOpenDialog(this.frame);
/* 191 */       this.frame.setVisible(false);
/* 192 */       if (result == 0) {
/* 193 */         File file = this.fileChooser.getSelectedFile();
/* 194 */         if (file.getName().endsWith(".txt")) {
/*     */           try {
/* 196 */             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
/*     */             String line;
/* 198 */             while ((line = br.readLine()) != null) {
/* 199 */               String email = line.split(":")[0];
/* 200 */               String pass = null;
/* 201 */               if ((line.split(":")).length > 1) {
/* 202 */                 pass = line.split(":")[1];
/*     */               }
/* 204 */               if (pass != null && pass.isEmpty()) {
/* 205 */                 Alt alt1 = new Alt(email, null);
/* 206 */                 AltManager.addAlt(alt1);
/*     */                 continue;
/*     */               } 
/* 209 */               Alt alt = new Alt(email, pass);
/* 210 */               AltManager.addAlt(alt);
/*     */             } 
/*     */             
/* 213 */             br.close();
/* 214 */           } catch (Exception e) {
/* 215 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 220 */     if (button.id == 6) {
/* 221 */       this.frame.setVisible(true);
/* 222 */       this.frame.setAlwaysOnTop(true);
/* 223 */       int result = this.fileChooser.showSaveDialog(this.frame);
/* 224 */       this.frame.setVisible(false);
/* 225 */       if (result == 0) {
/* 226 */         File file = this.fileChooser.getSelectedFile();
/* 227 */         if (file.getName().endsWith(".txt")) {
/* 228 */           file.delete();
/*     */           try {
/* 230 */             PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
/* 231 */             for (Alt alt : AltManager.getAlts()) {
/* 232 */               if (alt.cracked) {
/* 233 */                 pw.println(alt.name);
/*     */                 continue;
/*     */               } 
/* 236 */               pw.println(String.valueOf(alt.email) + ":" + alt.password);
/*     */             } 
/*     */             
/* 239 */             pw.flush();
/* 240 */             pw.close();
/* 241 */           } catch (Exception e) {
/* 242 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 247 */     super.actionPerformed(button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 252 */     this.searchField.updateCursorCounter();
/* 253 */     this.emailField.updateCursorCounter();
/* 254 */     this.passField.updateCursorCounter();
/* 255 */     if (this.selected >= AltManager.getAlts().size()) {
/* 256 */       this.selected = AltManager.getAlts().size() - 1;
/*     */     }
/* 258 */     if (!this.displayingLogin) {
/* 259 */       this.emailField.setText("");
/* 260 */       this.passField.setText("");
/*     */     } 
/* 262 */     ((GuiButton)this.buttonList.get(0)).enabled = !AltManager.getAlts().isEmpty();
/* 263 */     ((GuiButton)this.buttonList.get(1)).enabled = !AltManager.getAlts().isEmpty();
/* 264 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 269 */     drawDefaultBackground();
/*     */     
/* 271 */     GlStateManager.scale(2.0F, 2.0F, 1.0F);
/* 272 */     drawCenteredString(this.fontRendererObj, "", this.width / 2 / 2, 2, -1);
/* 273 */     drawCenteredString(this.fontRendererObj, "§7Search", this.width / 2 / 2, 12, -1);
/* 274 */     GlStateManager.scale(0.5D, 0.5D, 1.0D);
/*     */     
/* 276 */     this.searchField.drawTextBox();
/*     */     
/* 278 */     int offsetY = 67;
/*     */     
/* 280 */     int alts = 0;
/* 281 */     for (Alt alt : AltManager.getAlts()) {
/* 282 */       if (alt.name.toLowerCase().indexOf(this.searchField.getText().toLowerCase()) == -1) {
/*     */         continue;
/*     */       }
/* 285 */       int left = this.width / 2 - 150;
/* 286 */       int right = this.width / 2 + 150;
/* 287 */       int top = offsetY + alts * 21 + this.scroll;
/* 288 */       int bottom = offsetY + alts * 21 + 20 + this.scroll;
/* 289 */       if (top < 51) {
/* 290 */         alts++;
/*     */         continue;
/*     */       } 
/* 293 */       boolean hover = (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom);
/* 294 */       drawRect(left, top, right, bottom, hover ? -1439686624 : -1440735200);
/* 295 */       drawHorizontalLine(left, right - 1, top, -1);
/* 296 */       drawHorizontalLine(left, right - 1, bottom - 1, -1);
/* 297 */       drawVerticalLine(left, top, bottom, -1);
/* 298 */       drawVerticalLine(right - 1, top, bottom, -1);
/* 299 */       String obfPass = "";
/* 300 */       if (!alt.cracked) {
/* 301 */         for (int i = 0; i < alt.password.length(); i++) {
/* 302 */           obfPass = String.valueOf(obfPass) + "*";
/*     */         }
/*     */       }
/* 305 */       if (AltManager.loggedInName != null && AltManager.loggedInName.equals(alt.name)) {
/* 306 */         drawString(this.fontRendererObj, "§2Logged in", right - this.fontRendererObj.getStringWidth("§2Logged in") - 2, bottom - 18, -1);
/*     */       }
/* 308 */       drawString(this.fontRendererObj, String.valueOf(alts + 1) + ".", left - this.fontRendererObj.getStringWidth(String.valueOf(alts + 1) + ".") - 2, top + 6, -1);
/* 309 */       drawString(this.fontRendererObj, alt.cracked ? ("§c" + alt.name) : ("§6" + alt.name + "§r:" + "§6" + obfPass), left + 6, top + 6, -1);
/* 310 */       if (alt.cracked) {
/* 311 */         drawString(this.fontRendererObj, "§cCracked", right - this.fontRendererObj.getStringWidth("§cCracked") - 2, bottom - 9, -1);
/*     */       } else {
/* 313 */         drawString(this.fontRendererObj, "§6Premium", right - this.fontRendererObj.getStringWidth("§6Premium") - 2, bottom - 9, -1);
/*     */       } 
/* 315 */       if (this.selected == alts) {
/* 316 */         drawRect(left, top, right, bottom, 452984831);
/*     */       }
/* 318 */       alts++;
/*     */     } 
/*     */     
/* 321 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 322 */     if (this.failed) {
/* 323 */       drawRect(0, 80, this.width, this.height - 40, -1392508928);
/* 324 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 325 */       drawCenteredString(this.fontRendererObj, "§cFailed to login!", this.width / 2 / 4, 22, -1);
/* 326 */       GlStateManager.scale(0.25D, 0.25D, 1.0D);
/* 327 */       drawCenteredString(this.fontRendererObj, "§7" + this.failMessage, this.width / 2, 130, -1);
/*     */       
/* 329 */       ((GuiButton)this.buttonList.get(8)).func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/*     */     } 
/* 331 */     if (this.displayingLogin) {
/* 332 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 333 */       GlStateManager.scale(0.25D, 0.25D, 1.0D);
/* 334 */       drawString(this.fontRendererObj, "Email/Username", this.width / 2 - 99 - 50, this.height / 2 - 11, -1);
/* 335 */       drawString(this.fontRendererObj, "Password", this.width / 2 + 1, this.height / 2 - 11, -1);
/*     */       
/* 337 */       this.emailField.drawTextBox();
/* 338 */       this.passField.drawTextBox();
/*     */       
/* 340 */       ((GuiButton)this.buttonList.get(7)).func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/* 341 */       ((GuiButton)this.buttonList.get(3)).func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 348 */     Keyboard.enableRepeatEvents(false);
/* 349 */     super.onGuiClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 354 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 355 */     if (this.displayingLogin) {
/* 356 */       this.emailField.mouseClicked(mouseX, mouseY, mouseButton);
/* 357 */       this.passField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/* 359 */     if (this.displayingLogin) {
/*     */       return;
/*     */     }
/* 362 */     this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 364 */     int offsetY = 67;
/*     */     
/* 366 */     int alts = 0;
/* 367 */     for (Alt alt : AltManager.getAlts()) {
/* 368 */       if (alt.name.toLowerCase().indexOf(this.searchField.getText().toLowerCase()) == -1) {
/*     */         continue;
/*     */       }
/* 371 */       int left = this.width / 2 - 150;
/* 372 */       int right = this.width / 2 + 150;
/* 373 */       int top = offsetY + alts * 21 + this.scroll;
/* 374 */       int bottom = offsetY + alts * 21 + 20 + this.scroll;
/* 375 */       boolean hover = (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom);
/* 376 */       drawRect(left, top, right, bottom, hover ? -1432338368 : -1435492320);
/* 377 */       if (hover) {
/* 378 */         this.selected = alts;
/*     */         break;
/*     */       } 
/* 381 */       alts++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 387 */     super.keyTyped(typedChar, keyCode);
/* 388 */     this.searchField.textboxKeyTyped(typedChar, keyCode);
/* 389 */     if (this.displayingLogin) {
/* 390 */       this.emailField.textboxKeyTyped(typedChar, keyCode);
/* 391 */       this.passField.textboxKeyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 396 */     super.handleMouseInput();
/* 397 */     if (this.displayingLogin) {
/*     */       return;
/*     */     }
/* 400 */     int i = Mouse.getEventDWheel();
/* 401 */     if (i != 0) {
/* 402 */       if (i > 1) {
/* 403 */         i = 1;
/*     */       }
/*     */       
/* 406 */       if (i < -1) {
/* 407 */         i = -1;
/*     */       }
/* 409 */       if (isCtrlKeyDown()) {
/* 410 */         i *= 210;
/*     */       } else {
/*     */         
/* 413 */         i *= 21;
/*     */       } 
/*     */ 
/*     */       
/* 417 */       this.scroll += i;
/*     */       
/* 419 */       if (this.scroll > 0) {
/* 420 */         this.scroll = 0;
/*     */       }
/*     */       
/* 423 */       if (this.scroll < -AltManager.getAlts().size() * 21 + 105)
/* 424 */         this.scroll = -AltManager.getAlts().size() * 21 + 105; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\GuiAltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
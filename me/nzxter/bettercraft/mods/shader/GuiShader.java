/*     */ package me.nzxter.bettercraft.mods.shader;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import me.nzxter.bettercraft.BetterCraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiShader
/*     */   extends GuiScreen
/*     */ {
/*     */   public static GuiScreen menu;
/*     */   public static boolean shader = false;
/*  25 */   public static String mode = "Shaders: §cOff";
/*     */   private GuiScreen before;
/*     */   
/*     */   public GuiShader(GuiScreen before) {
/*  29 */     this.before = before;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  34 */     Keyboard.enableRepeatEvents(true);
/*  35 */     this.buttonList.clear();
/*  36 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3, 98, 20, "Switch Shader"));
/*  37 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 3 + 66, 200, 20, "Back"));
/*  38 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 3, 98, 20, "Custom Shader"));
/*  39 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 3 + 5 + 20, mode));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  44 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  49 */     if (!button.enabled) {
/*     */       return;
/*     */     }
/*  52 */     if (button.id == 0) {
/*  53 */       if (!shader) {
/*     */         return;
/*     */       }
/*  56 */       DrawShader.Custom = null;
/*  57 */       DrawShader.switchShader();
/*     */     } else {
/*     */       
/*  60 */       if (button.id == 1) {
/*  61 */         this.mc.displayGuiScreen(this.before);
/*     */         return;
/*     */       } 
/*  64 */       if (button.id == 2) {
/*  65 */         (new Thread()
/*     */           {
/*     */             public void run() {
/*  68 */               final JFrame frm = new JFrame("Enter shader source.");
/*  69 */               frm.setSize(600, 500);
/*  70 */               frm.setLocationRelativeTo(null);
/*  71 */               JScrollPane scroll = new JScrollPane();
/*  72 */               final JTextArea sourceFld = new JTextArea();
/*  73 */               scroll.setViewportView(sourceFld);
/*  74 */               frm.getContentPane().add(scroll, "Center");
/*  75 */               JButton btnSave = new JButton("Save");
/*  76 */               btnSave.setFont(new Font("Segoe UI", 0, 15));
/*  77 */               btnSave.addActionListener(new ActionListener()
/*     */                   {
/*     */                     public void actionPerformed(ActionEvent e) {
/*  80 */                       BetterCraft.shaderSource = sourceFld.getText();
/*  81 */                       BetterCraft.INSTANCE.applyShader();
/*  82 */                       frm.setVisible(false);
/*     */                     }
/*     */                   });
/*  85 */               frm.getContentPane().add(btnSave, "South");
/*  86 */               frm.setVisible(true);
/*     */             }
/*  88 */           }).start();
/*     */         return;
/*     */       } 
/*  91 */       if (button.id != 4) {
/*     */         return;
/*     */       }
/*  94 */       if (!shader) {
/*  95 */         shader = true;
/*  96 */         mode = "Shaders: §aOn";
/*     */       } else {
/*     */         
/*  99 */         shader = false;
/* 100 */         mode = "Shaders: §cOff";
/*     */       } 
/* 102 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 108 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 113 */     drawDefaultBackground();
/* 114 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\shader\GuiShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
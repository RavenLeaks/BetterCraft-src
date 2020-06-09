/*     */ package me.nzxter.bettercraft.mods.music;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import javazoom.jl.player.Player;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ 
/*     */ 
/*     */ public class GuiMusic
/*     */   extends GuiScreen
/*     */ {
/*  30 */   private Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*     */   
/*     */   private GuiScreen before;
/*     */ 
/*     */   
/*     */   private String channelName;
/*     */ 
/*     */   
/*     */   private GuiTextField stream;
/*     */   
/*  41 */   private static int channelId = 2; public static String music_link;
/*     */   private static Player player;
/*     */   private static boolean isRunning;
/*  44 */   private static String URL = "http://streams.ilovemusic.de/iloveradio2.mp3"; private float currentVolume;
/*     */   
/*     */   public GuiMusic(GuiScreen before) {
/*  47 */     this.currentVolume = -40.0F;
/*     */     this.before = before;
/*     */   } private GuiButton button;
/*     */   public void initGui() {
/*  51 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 3 + 66, 200, 20, "Back"));
/*  52 */     this.buttonList.add(this.button = new GuiButton(1, this.width / 2 - 100, this.height / 3 + 40, 200, 20, !isRunning ? "§aStart" : "§cStop"));
/*     */     
/*  54 */     this.buttonList.add(this.button = new GuiButton(2, this.width / 2 + 107, this.height / 3 + 53, 20, 20, "§a+"));
/*  55 */     this.buttonList.add(this.button = new GuiButton(3, this.width / 2 - 127, this.height / 3 + 53, 20, 20, "§c-"));
/*  56 */     this.stream = new GuiTextField(this.height, this.mc.fontRendererObj, this.width / 2 - 120, 60, 240, 20);
/*  57 */     this.stream.setMaxStringLength(100);
/*  58 */     this.stream.setText(URL);
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  62 */     int id = button.id;
/*     */     
/*  64 */     if (id == 0) {
/*  65 */       this.mc.displayGuiScreen(this.before);
/*     */     }
/*     */     
/*  68 */     if (id == 1) {
/*  69 */       if (isRunning) {
/*  70 */         player.close();
/*  71 */         (new Thread(() -> playMusic(music_link))).stop();
/*  72 */         button.displayString = "§aStart";
/*  73 */         isRunning = false;
/*     */       } else {
/*  75 */         refreshLink(channelId);
/*  76 */         (new Thread(() -> playMusic(music_link))).start();
/*  77 */         button.displayString = "§cStop";
/*  78 */         isRunning = true;
/*     */       } 
/*     */     }
/*     */     
/*  82 */     if (id == 2) {
/*  83 */       this.currentVolume += 5.0F;
/*  84 */       if (this.currentVolume >= -10.0F) {
/*  85 */         this.currentVolume = -10.0F;
/*     */       }
/*  87 */       if (player != null) {
/*  88 */         player.setGain(this.currentVolume);
/*     */       }
/*     */     } 
/*  91 */     if (id == 3) {
/*  92 */       this.currentVolume -= 5.0F;
/*     */       
/*  94 */       if (this.currentVolume < -60.0F) {
/*  95 */         this.currentVolume = -60.0F;
/*     */       }
/*     */       
/*  98 */       if (player != null)
/*  99 */         player.setGain(this.currentVolume); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean doesGustreamauseGame() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char character, int key) throws IOException {
/*     */     try {
/* 110 */       super.keyTyped(character, key);
/*     */     }
/* 112 */     catch (IOException e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/* 115 */     if (character == '\r') {
/* 116 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/* 118 */     this.stream.textboxKeyTyped(character, key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int x, int y, int button) {
/*     */     try {
/* 124 */       super.mouseClicked(x, y, button);
/*     */     }
/* 126 */     catch (IOException e) {
/* 127 */       e.printStackTrace();
/*     */     } 
/* 129 */     this.stream.mouseClicked(x, y, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 134 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 139 */     this.stream.updateCursorCounter();
/* 140 */     refreshLink(channelId);
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 144 */     drawDefaultBackground();
/* 145 */     GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 146 */     GlStateManager.scale(0.5D, 0.5D, 1.0D);
/* 147 */     GlStateManager.scale(0.5D, 0.5D, 1.0D);
/*     */     
/* 149 */     Gui.drawCenteredString(this.mc.fontRendererObj, "§7URL", this.width / 2, 50, -1);
/* 150 */     this.stream.drawTextBox();
/*     */     
/* 152 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   private void playMusic(String url) {
/* 156 */     BufferedInputStream inputStream = null;
/*     */     try {
/* 158 */       inputStream = new BufferedInputStream((new URL(url)).openStream());
/* 159 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     try {
/* 163 */       this.currentVolume = -40.0F;
/*     */       
/* 165 */       player = new Player(inputStream);
/* 166 */       player.setGain(this.currentVolume);
/* 167 */       player.play();
/* 168 */     } catch (Exception ex) {
/* 169 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void refreshLink(int id) {
/* 174 */     URL = this.stream.getText().replace("https", "http");
/* 175 */     music_link = URL;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\music\GuiMusic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
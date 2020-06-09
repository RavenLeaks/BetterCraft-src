/*     */ package me.nzxter.bettercraft.mods.gui.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Keystrokes
/*     */   extends Gui
/*     */ {
/*  16 */   private static Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   public enum KeystrokesMode
/*     */   {
/*  20 */     WASD((String)new Keystrokes.Key[] { Keystrokes.Key.access$0(), Keystrokes.Key.access$1(), Keystrokes.Key.access$2(), Keystrokes.Key.access$3() }),
/*  21 */     WASD_MOUSE((String)new Keystrokes.Key[] { Keystrokes.Key.access$0(), Keystrokes.Key.access$1(), Keystrokes.Key.access$2(), Keystrokes.Key.access$3(), Keystrokes.Key.access$4(), Keystrokes.Key.access$5() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  26 */     private int width = 0;
/*  27 */     private int height = 0; private final Keystrokes.Key[] keys;
/*     */     
/*     */     KeystrokesMode(Keystrokes.Key... keysIn) {
/*  30 */       this.keys = keysIn; byte b; int i;
/*     */       Keystrokes.Key[] arrayOfKey;
/*  32 */       for (i = (arrayOfKey = this.keys).length, b = 0; b < i; ) { Keystrokes.Key key = arrayOfKey[b];
/*  33 */         this.width = Math.max(this.width, key.getX() + key.getWidth());
/*  34 */         this.height = Math.max(this.height, key.getY() + key.getHeight());
/*     */         b++; }
/*     */     
/*     */     }
/*     */     public int getHeight() {
/*  39 */       return this.height;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/*  43 */       return this.width;
/*     */     }
/*     */     
/*     */     public Keystrokes.Key[] getKeys() {
/*  47 */       return this.keys;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Key
/*     */   {
/*  53 */     private static final Key W = new Key("W", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18);
/*  54 */     private static final Key A = new Key("A", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18);
/*  55 */     private static final Key S = new Key("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18);
/*  56 */     private static final Key D = new Key("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18);
/*     */     
/*  58 */     private static final Key LMB = new Key("LMB", (Minecraft.getMinecraft()).gameSettings.keyBindAttack, 1, 41, 18, 18);
/*  59 */     private static final Key RMB = new Key("RMB", (Minecraft.getMinecraft()).gameSettings.keyBindUseItem, 32, 41, 18, 18);
/*     */     
/*     */     private String name;
/*     */     private KeyBinding keyBind;
/*     */     private int x;
/*     */     private int y;
/*     */     private int width;
/*     */     private int height;
/*     */     
/*     */     public Key(String name, KeyBinding keyBind, int x, int y, int width, int height) {
/*  69 */       this.name = name;
/*  70 */       this.keyBind = keyBind;
/*  71 */       this.x = x;
/*  72 */       this.y = y;
/*  73 */       this.width = width;
/*  74 */       this.height = height;
/*     */     }
/*     */     
/*     */     public boolean isDown() {
/*  78 */       return this.keyBind.isKeyDown();
/*     */     }
/*     */     
/*     */     public int getHeight() {
/*  82 */       return this.height;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  86 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/*  90 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getX() {
/*  94 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/*  98 */       return this.y;
/*     */     }
/*     */   }
/*     */   
/* 102 */   private static KeystrokesMode mode = KeystrokesMode.WASD_MOUSE;
/*     */   
/*     */   public void setMode(KeystrokesMode mode) {
/* 105 */     Keystrokes.mode = mode;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 109 */     return mode.getWidth();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 113 */     return mode.getHeight(); } public static void render() {
/*     */     byte b;
/*     */     int i;
/*     */     Key[] arrayOfKey;
/* 117 */     for (i = (arrayOfKey = mode.getKeys()).length, b = 0; b < i; ) { Key key = arrayOfKey[b];
/*     */       
/* 119 */       int textWidth = getStringWidth(key.getName());
/*     */ 
/*     */ 
/*     */       
/* 123 */       ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*     */       
/* 125 */       drawString(mc.fontRendererObj, key.getName(), 10 + key.getX() + key.getWidth() / 2 - textWidth / 2, 15 + key.getY() + key.getHeight() / 2 + 130, key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static int getStringWidth(String string) {
/* 131 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gu\\ui\Keystrokes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
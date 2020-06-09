/*     */ package me.nzxter.bettercraft.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SplashProgressUtils
/*     */ {
/*     */   private static final int MAX = 7;
/*  18 */   private static int PROGRESS = 0;
/*  19 */   private static String CURRENT = "";
/*     */   private static ResourceLocation splash;
/*     */   private static UnicodeFontRendererUtils ufr;
/*     */   
/*     */   public static void update() {
/*  24 */     if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
/*     */       return;
/*     */     }
/*  27 */     drawSplash(Minecraft.getMinecraft().getTextureManager());
/*     */   }
/*     */   
/*     */   public static void setProgress(int givenProgress, String givenText) {
/*  31 */     PROGRESS = givenProgress;
/*  32 */     CURRENT = givenText;
/*  33 */     update();
/*     */   }
/*     */   
/*     */   public static void drawSplash(TextureManager tm) {
/*  37 */     ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
/*  38 */     int scaleFactor = scaledResolution.getScaleFactor();
/*     */     
/*  40 */     Framebuffer framebuffer = new Framebuffer(ScaledResolution.getScaledWidth() * scaleFactor, ScaledResolution.getScaledHeight() * scaleFactor, true);
/*  41 */     framebuffer.bindFramebuffer(false);
/*     */     
/*  43 */     GlStateManager.matrixMode(5889);
/*  44 */     GlStateManager.loadIdentity();
/*  45 */     GlStateManager.ortho(0.0D, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/*  46 */     GlStateManager.matrixMode(5888);
/*  47 */     GlStateManager.loadIdentity();
/*  48 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*  49 */     GlStateManager.disableLighting();
/*  50 */     GlStateManager.disableFog();
/*  51 */     GlStateManager.disableDepth();
/*  52 */     GlStateManager.enableTexture2D();
/*     */     
/*  54 */     if (splash == null) {
/*  55 */       splash = new ResourceLocation("textures/gui/title/mojang.png");
/*     */     }
/*     */     
/*  58 */     tm.bindTexture(splash);
/*     */     
/*  60 */     GlStateManager.resetColor();
/*  61 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/*     */     
/*  63 */     Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, 1920, 1080, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 1920.0F, 1080.0F);
/*  64 */     drawProgress();
/*  65 */     framebuffer.unbindFramebuffer();
/*  66 */     framebuffer.framebufferRender(ScaledResolution.getScaledWidth() * scaleFactor, ScaledResolution.getScaledHeight() * scaleFactor);
/*     */     
/*  68 */     GlStateManager.enableAlpha();
/*  69 */     GlStateManager.alphaFunc(516, 0.1F);
/*     */     
/*  71 */     Minecraft.getMinecraft().updateDisplay();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void drawProgress() {
/*  76 */     if ((Minecraft.getMinecraft()).gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     if (ufr == null) {
/*  81 */       ufr = UnicodeFontRendererUtils.getFontOnPC("NONE", 20);
/*     */     }
/*     */     
/*  84 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*     */     
/*  86 */     double nProgress = PROGRESS;
/*  87 */     double calc = nProgress / 7.0D * ScaledResolution.getScaledWidth();
/*     */     
/*  89 */     Gui.drawRect(0, ScaledResolution.getScaledHeight() - 35, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), (new Color(0, 0, 0, 50)).getRGB());
/*     */     
/*  91 */     GlStateManager.resetColor();
/*  92 */     resetTextureState();
/*     */     
/*  94 */     ufr.drawString(CURRENT, 20.0F, (ScaledResolution.getScaledHeight() - 25), -1);
/*     */     
/*  96 */     String step = String.valueOf(PROGRESS) + "/" + '\007';
/*  97 */     ufr.drawString(step, (ScaledResolution.getScaledWidth() - 20) - ufr.getWidth(step), (ScaledResolution.getScaledHeight() - 25), -505290241);
/*     */     
/*  99 */     GlStateManager.resetColor();
/* 100 */     resetTextureState();
/*     */     
/* 102 */     Gui.drawRect(0, ScaledResolution.getScaledHeight() - 2, (int)calc, ScaledResolution.getScaledHeight(), (new Color(149, 201, 144)).getRGB());
/*     */     
/* 104 */     Gui.drawRect(0, ScaledResolution.getScaledHeight() - 2, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), (new Color(0, 0, 0, 10)).getRGB());
/*     */   }
/*     */   
/*     */   private static void resetTextureState() {
/* 108 */     (GlStateManager.textureState[GlStateManager.activeTextureUnit]).textureName = -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\SplashProgressUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
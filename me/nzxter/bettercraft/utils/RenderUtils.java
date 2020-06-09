/*     */ package me.nzxter.bettercraft.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
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
/*     */ public class RenderUtils
/*     */ {
/*  42 */   public static Minecraft mc = Minecraft.getMinecraft();
/*  43 */   private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
/*  44 */   private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */   
/*     */   public static DynamicTexture dynamicTexture;
/*     */   
/*     */   public static BufferedImage bufferedImage;
/*     */ 
/*     */   
/*     */   public static void downloadSkin() {
/*  52 */     dynamicTexture = null;
/*  53 */     bufferedImage = null;
/*     */     
/*     */     try {
/*  56 */       Minecraft.getMinecraft(); bufferedImage = getSkinBuffer("https://minotar.net/helm/" + Minecraft.getSession().getUsername() + "/55.png");
/*  57 */       dynamicTexture = new DynamicTexture(bufferedImage);
/*  58 */     } catch (Exception e) {
/*  59 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BufferedImage getSkinBuffer(String web) {
/*     */     try {
/*  65 */       return ImageIO.read((new URL(web)).openStream());
/*  66 */     } catch (Exception e) {
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawLine(double fromX, double fromY, double toX, double toY, Color color, float lineWidth) {
/*  73 */     GL11.glPushMatrix();
/*  74 */     GL11.glPushAttrib(1048575);
/*  75 */     GL11.glDisable(2929);
/*  76 */     GL11.glDisable(3553);
/*  77 */     GL11.glEnable(2848);
/*  78 */     GL11.glEnable(3042);
/*  79 */     GL11.glLineWidth(lineWidth);
/*  80 */     GL11.glBlendFunc(770, 771);
/*  81 */     Color c = color;
/*  82 */     GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
/*  83 */     GL11.glBegin(2);
/*  84 */     GL11.glVertex2d(fromX, fromY);
/*  85 */     GL11.glVertex2d(toX, toY);
/*  86 */     GL11.glEnd();
/*  87 */     GL11.glDisable(3042);
/*  88 */     GL11.glEnable(3553);
/*  89 */     GL11.glEnable(2929);
/*  90 */     GL11.glDisable(2848);
/*  91 */     GL11.glDisable(3042);
/*  92 */     GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/*  93 */     GL11.glPopAttrib();
/*  94 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static float[] RGBA(int color) {
/*  98 */     if ((color & 0xFC000000) == 0)
/*     */     {
/* 100 */       color |= 0xFF000000;
/*     */     }
/*     */     
/* 103 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 104 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 105 */     float blue = (color & 0xFF) / 255.0F;
/* 106 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 107 */     return new float[] { red, green, blue, alpha };
/*     */   }
/*     */   
/*     */   public static void drawMovedBackground(int mouseX, int mouseY, String backgroundLocation, boolean move) {
/* 111 */     ScaledResolution si = new ScaledResolution(mc);
/* 112 */     if (move) {
/* 113 */       mc.getTextureManager().bindTexture(new ResourceLocation(backgroundLocation));
/* 114 */       Gui.drawModalRectWithCustomSizedTexture(0 - mouseX / 40, 0 - mouseY / 40, 0.0F, 0.0F, ScaledResolution.getScaledWidth() + 30, 
/* 115 */           ScaledResolution.getScaledHeight() + 30, (ScaledResolution.getScaledWidth() + 30), (ScaledResolution.getScaledHeight() + 30));
/*     */     } else {
/* 117 */       mc.getTextureManager().bindTexture(new ResourceLocation(backgroundLocation));
/* 118 */       Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 
/* 119 */           ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
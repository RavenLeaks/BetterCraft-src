/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorUtils
/*    */ {
/*    */   public static Color rainbowEffect(long offset, float fade) {
/* 12 */     float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
/* 13 */     long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
/* 14 */     Color c = new Color((int)color);
/* 15 */     return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
/*    */   }
/*    */   
/*    */   public static int toARGB(int r, int g, int b, int a) {
/* 19 */     return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
/*    */   }
/*    */   
/*    */   public static void rainbowGL11() {
/* 23 */     float x = (float)(System.currentTimeMillis() % 15000L) / 4000.0F;
/* 24 */     float red = 0.5F + 0.5F * MathHelper.sin(x * 3.1415927F);
/* 25 */     float green = 0.5F + 0.5F * MathHelper.sin((x + 1.3333334F) * 3.1415927F);
/* 26 */     float blue = 0.5F + 0.5F * MathHelper.sin((x + 2.6666667F) * 3.1415927F);
/* 27 */     GL11.glColor4d(red, green, blue, 255.0D);
/*    */   }
/*    */   
/*    */   public static Color rainbowColor(long offset, int speed, float fade) {
/* 31 */     double millis = ((float)((System.currentTimeMillis() + offset) % 10000L / speed) / 10000.0F / speed);
/* 32 */     Color c = Color.getHSBColor((float)millis, 0.8F, 0.8F);
/* 33 */     return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, fade);
/*    */   }
/*    */   
/*    */   public static Color rainbowColor(long offset, float fade) {
/* 37 */     float huge = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
/* 38 */     long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(huge, 1.0F, 1.0F)), 16);
/* 39 */     Color c = new Color((int)color);
/* 40 */     return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, fade);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
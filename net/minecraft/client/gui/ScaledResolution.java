/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class ScaledResolution
/*    */ {
/*    */   private final double scaledWidthD;
/*    */   private final double scaledHeightD;
/*    */   private static int scaledWidth;
/*    */   private static int scaledHeight;
/*    */   private int scaleFactor;
/*    */   
/*    */   public ScaledResolution(Minecraft minecraftClient) {
/* 16 */     scaledWidth = minecraftClient.displayWidth;
/* 17 */     scaledHeight = minecraftClient.displayHeight;
/* 18 */     this.scaleFactor = 1;
/* 19 */     boolean flag = minecraftClient.isUnicode();
/* 20 */     int i = minecraftClient.gameSettings.guiScale;
/*    */     
/* 22 */     if (i == 0)
/*    */     {
/* 24 */       i = 1000;
/*    */     }
/*    */     
/* 27 */     while (this.scaleFactor < i && scaledWidth / (this.scaleFactor + 1) >= 320 && scaledHeight / (this.scaleFactor + 1) >= 240)
/*    */     {
/* 29 */       this.scaleFactor++;
/*    */     }
/*    */     
/* 32 */     if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1)
/*    */     {
/* 34 */       this.scaleFactor--;
/*    */     }
/*    */     
/* 37 */     this.scaledWidthD = scaledWidth / this.scaleFactor;
/* 38 */     this.scaledHeightD = scaledHeight / this.scaleFactor;
/* 39 */     scaledWidth = MathHelper.ceil(this.scaledWidthD);
/* 40 */     scaledHeight = MathHelper.ceil(this.scaledHeightD);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getScaledWidth() {
/* 45 */     return scaledWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getScaledHeight() {
/* 50 */     return scaledHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getScaledWidth_double() {
/* 55 */     return this.scaledWidthD;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getScaledHeight_double() {
/* 60 */     return this.scaledHeightD;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScaleFactor() {
/* 65 */     return this.scaleFactor;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ScaledResolution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
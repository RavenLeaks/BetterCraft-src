/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ImageBufferDownload
/*     */   implements IImageBuffer
/*     */ {
/*     */   private int[] imageData;
/*     */   private int imageWidth;
/*     */   private int imageHeight;
/*     */   
/*     */   @Nullable
/*     */   public BufferedImage parseUserSkin(BufferedImage image) {
/*  19 */     if (image == null)
/*     */     {
/*  21 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  25 */     this.imageWidth = 64;
/*  26 */     this.imageHeight = 64;
/*  27 */     int i = image.getWidth();
/*  28 */     int j = image.getHeight();
/*     */     
/*     */     int k;
/*  31 */     for (k = 1; this.imageWidth < i || this.imageHeight < j; k *= 2) {
/*     */       
/*  33 */       this.imageWidth *= 2;
/*  34 */       this.imageHeight *= 2;
/*     */     } 
/*     */     
/*  37 */     BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
/*  38 */     Graphics graphics = bufferedimage.getGraphics();
/*  39 */     graphics.drawImage(image, 0, 0, null);
/*  40 */     boolean flag = (image.getHeight() == 32 * k);
/*     */     
/*  42 */     if (flag) {
/*     */       
/*  44 */       graphics.setColor(new Color(0, 0, 0, 0));
/*  45 */       graphics.fillRect(0 * k, 32 * k, 64 * k, 32 * k);
/*  46 */       graphics.drawImage(bufferedimage, 24 * k, 48 * k, 20 * k, 52 * k, 4 * k, 16 * k, 8 * k, 20 * k, null);
/*  47 */       graphics.drawImage(bufferedimage, 28 * k, 48 * k, 24 * k, 52 * k, 8 * k, 16 * k, 12 * k, 20 * k, null);
/*  48 */       graphics.drawImage(bufferedimage, 20 * k, 52 * k, 16 * k, 64 * k, 8 * k, 20 * k, 12 * k, 32 * k, null);
/*  49 */       graphics.drawImage(bufferedimage, 24 * k, 52 * k, 20 * k, 64 * k, 4 * k, 20 * k, 8 * k, 32 * k, null);
/*  50 */       graphics.drawImage(bufferedimage, 28 * k, 52 * k, 24 * k, 64 * k, 0 * k, 20 * k, 4 * k, 32 * k, null);
/*  51 */       graphics.drawImage(bufferedimage, 32 * k, 52 * k, 28 * k, 64 * k, 12 * k, 20 * k, 16 * k, 32 * k, null);
/*  52 */       graphics.drawImage(bufferedimage, 40 * k, 48 * k, 36 * k, 52 * k, 44 * k, 16 * k, 48 * k, 20 * k, null);
/*  53 */       graphics.drawImage(bufferedimage, 44 * k, 48 * k, 40 * k, 52 * k, 48 * k, 16 * k, 52 * k, 20 * k, null);
/*  54 */       graphics.drawImage(bufferedimage, 36 * k, 52 * k, 32 * k, 64 * k, 48 * k, 20 * k, 52 * k, 32 * k, null);
/*  55 */       graphics.drawImage(bufferedimage, 40 * k, 52 * k, 36 * k, 64 * k, 44 * k, 20 * k, 48 * k, 32 * k, null);
/*  56 */       graphics.drawImage(bufferedimage, 44 * k, 52 * k, 40 * k, 64 * k, 40 * k, 20 * k, 44 * k, 32 * k, null);
/*  57 */       graphics.drawImage(bufferedimage, 48 * k, 52 * k, 44 * k, 64 * k, 52 * k, 20 * k, 56 * k, 32 * k, null);
/*     */     } 
/*     */     
/*  60 */     graphics.dispose();
/*  61 */     this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
/*  62 */     setAreaOpaque(0 * k, 0 * k, 32 * k, 16 * k);
/*     */     
/*  64 */     if (flag)
/*     */     {
/*  66 */       doTransparencyHack(32 * k, 0 * k, 64 * k, 32 * k);
/*     */     }
/*     */     
/*  69 */     setAreaOpaque(0 * k, 16 * k, 64 * k, 32 * k);
/*  70 */     setAreaOpaque(16 * k, 48 * k, 48 * k, 64 * k);
/*  71 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skinAvailable() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void doTransparencyHack(int p_189559_1_, int p_189559_2_, int p_189559_3_, int p_189559_4_) {
/*  81 */     for (int i = p_189559_1_; i < p_189559_3_; i++) {
/*     */       
/*  83 */       for (int j = p_189559_2_; j < p_189559_4_; j++) {
/*     */         
/*  85 */         int k = this.imageData[i + j * this.imageWidth];
/*     */         
/*  87 */         if ((k >> 24 & 0xFF) < 128) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  94 */     for (int l = p_189559_1_; l < p_189559_3_; l++) {
/*     */       
/*  96 */       for (int i1 = p_189559_2_; i1 < p_189559_4_; i1++)
/*     */       {
/*  98 */         this.imageData[l + i1 * this.imageWidth] = this.imageData[l + i1 * this.imageWidth] & 0xFFFFFF;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAreaOpaque(int x, int y, int width, int height) {
/* 108 */     for (int i = x; i < width; i++) {
/*     */       
/* 110 */       for (int j = y; j < height; j++)
/*     */       {
/* 112 */         this.imageData[i + j * this.imageWidth] = this.imageData[i + j * this.imageWidth] | 0xFF000000;
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ImageBufferDownload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
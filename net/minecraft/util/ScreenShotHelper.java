/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.IntBuffer;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ public class ScreenShotHelper
/*     */ {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*  31 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntBuffer pixelBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] pixelValues;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITextComponent saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer) {
/*  47 */     return saveScreenshot(gameDirectory, null, width, height, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITextComponent saveScreenshot(File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer) {
/*     */     try {
/*  58 */       File file1 = new File(gameDirectory, "screenshots");
/*  59 */       file1.mkdir();
/*  60 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  61 */       int i = (Config.getGameSettings()).guiScale;
/*  62 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/*  63 */       int j = scaledresolution.getScaleFactor();
/*  64 */       int k = Config.getScreenshotSize();
/*  65 */       boolean flag = (OpenGlHelper.isFramebufferEnabled() && k > 1);
/*     */       
/*  67 */       if (flag) {
/*     */         
/*  69 */         (Config.getGameSettings()).guiScale = j * k;
/*  70 */         resize(width * k, height * k);
/*  71 */         GlStateManager.pushMatrix();
/*  72 */         GlStateManager.clear(16640);
/*  73 */         minecraft.getFramebuffer().bindFramebuffer(true);
/*  74 */         minecraft.entityRenderer.updateCameraAndRender(minecraft.getRenderPartialTicks(), System.nanoTime());
/*     */       } 
/*     */       
/*  77 */       BufferedImage bufferedimage = createScreenshot(width, height, buffer);
/*     */       
/*  79 */       if (flag) {
/*     */         
/*  81 */         minecraft.getFramebuffer().unbindFramebuffer();
/*  82 */         GlStateManager.popMatrix();
/*  83 */         (Config.getGameSettings()).guiScale = i;
/*  84 */         resize(width, height);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  89 */       if (screenshotName == null) {
/*     */         
/*  91 */         file2 = getTimestampedPNGFileForDirectory(file1);
/*     */       }
/*     */       else {
/*     */         
/*  95 */         file2 = new File(file1, screenshotName);
/*     */       } 
/*     */       
/*  98 */       File file2 = file2.getCanonicalFile();
/*  99 */       Object object = null;
/*     */       
/* 101 */       if (Reflector.ForgeHooksClient_onScreenshot.exists()) {
/*     */         
/* 103 */         object = Reflector.call(Reflector.ForgeHooksClient_onScreenshot, new Object[] { bufferedimage, file2 });
/*     */         
/* 105 */         if (Reflector.callBoolean(object, Reflector.Event_isCanceled, new Object[0]))
/*     */         {
/* 107 */           return (ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getCancelMessage, new Object[0]);
/*     */         }
/*     */         
/* 110 */         file2 = (File)Reflector.call(object, Reflector.ScreenshotEvent_getScreenshotFile, new Object[0]);
/*     */       } 
/*     */       
/* 113 */       ImageIO.write(bufferedimage, "png", file2);
/* 114 */       TextComponentString textComponentString = new TextComponentString(file2.getName());
/* 115 */       textComponentString.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
/* 116 */       textComponentString.getStyle().setUnderlined(Boolean.valueOf(true));
/*     */       
/* 118 */       if (object != null) {
/*     */         
/* 120 */         ITextComponent itextcomponent1 = (ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getResultMessage, new Object[0]);
/*     */         
/* 122 */         if (itextcomponent1 != null)
/*     */         {
/* 124 */           return itextcomponent1;
/*     */         }
/*     */       } 
/*     */       
/* 128 */       return (ITextComponent)new TextComponentTranslation("screenshot.success", new Object[] { textComponentString });
/*     */     }
/* 130 */     catch (Exception exception1) {
/*     */       
/* 132 */       LOGGER.warn("Couldn't save screenshot", exception1);
/* 133 */       return (ITextComponent)new TextComponentTranslation("screenshot.failure", new Object[] { exception1.getMessage() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage createScreenshot(int width, int height, Framebuffer framebufferIn) {
/* 139 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 141 */       width = framebufferIn.framebufferTextureWidth;
/* 142 */       height = framebufferIn.framebufferTextureHeight;
/*     */     } 
/*     */     
/* 145 */     int i = width * height;
/*     */     
/* 147 */     if (pixelBuffer == null || pixelBuffer.capacity() < i) {
/*     */       
/* 149 */       pixelBuffer = BufferUtils.createIntBuffer(i);
/* 150 */       pixelValues = new int[i];
/*     */     } 
/*     */     
/* 153 */     GlStateManager.glPixelStorei(3333, 1);
/* 154 */     GlStateManager.glPixelStorei(3317, 1);
/* 155 */     pixelBuffer.clear();
/*     */     
/* 157 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 159 */       GlStateManager.bindTexture(framebufferIn.framebufferTexture);
/* 160 */       GlStateManager.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */     }
/*     */     else {
/*     */       
/* 164 */       GlStateManager.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
/*     */     } 
/*     */     
/* 167 */     pixelBuffer.get(pixelValues);
/* 168 */     TextureUtil.processPixelValues(pixelValues, width, height);
/* 169 */     BufferedImage bufferedimage = new BufferedImage(width, height, 1);
/* 170 */     bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
/* 171 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
/* 182 */     String s = DATE_FORMAT.format(new Date()).toString();
/* 183 */     int i = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 187 */       File file1 = new File(gameDirectory, String.valueOf(s) + ((i == 1) ? "" : ("_" + i)) + ".png");
/*     */       
/* 189 */       if (!file1.exists())
/*     */       {
/* 191 */         return file1;
/*     */       }
/*     */       
/* 194 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void resize(int p_resize_0_, int p_resize_1_) {
/* 200 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 201 */     minecraft.displayWidth = Math.max(1, p_resize_0_);
/* 202 */     minecraft.displayHeight = Math.max(1, p_resize_1_);
/*     */     
/* 204 */     if (minecraft.currentScreen != null) {
/*     */       
/* 206 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 207 */       minecraft.currentScreen.onResize(minecraft, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*     */     } 
/*     */     
/* 210 */     updateFramebufferSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateFramebufferSize() {
/* 215 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 216 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*     */     
/* 218 */     if (minecraft.entityRenderer != null)
/*     */     {
/* 220 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ScreenShotHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
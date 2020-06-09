/*    */ package optifine;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.IImageBuffer;
/*    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CapeUtils
/*    */ {
/*    */   public static void downloadCape(AbstractClientPlayer p_downloadCape_0_) {
/* 19 */     String s = p_downloadCape_0_.getNameClear();
/*    */     
/* 21 */     if (s != null && !s.isEmpty()) {
/*    */       
/* 23 */       String s1 = "http://s.optifine.net/capes/" + s + ".png";
/* 24 */       String s2 = FilenameUtils.getBaseName(s1);
/* 25 */       ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s2);
/* 26 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 27 */       ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*    */       
/* 29 */       if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
/*    */         
/* 31 */         ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
/*    */         
/* 33 */         if (threaddownloadimagedata.imageFound != null) {
/*    */           
/* 35 */           if (threaddownloadimagedata.imageFound.booleanValue())
/*    */           {
/* 37 */             p_downloadCape_0_.setLocationOfCape(resourcelocation);
/*    */           }
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */       
/* 44 */       CapeImageBuffer capeimagebuffer = new CapeImageBuffer(p_downloadCape_0_, resourcelocation);
/* 45 */       ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s1, null, (IImageBuffer)capeimagebuffer);
/* 46 */       threaddownloadimagedata1.pipeline = true;
/* 47 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static BufferedImage parseCape(BufferedImage p_parseCape_0_) {
/* 53 */     int i = 64;
/* 54 */     int j = 32;
/* 55 */     int k = p_parseCape_0_.getWidth();
/*    */     
/* 57 */     for (int l = p_parseCape_0_.getHeight(); i < k || j < l; j *= 2)
/*    */     {
/* 59 */       i *= 2;
/*    */     }
/*    */     
/* 62 */     BufferedImage bufferedimage = new BufferedImage(i, j, 2);
/* 63 */     Graphics graphics = bufferedimage.getGraphics();
/* 64 */     graphics.drawImage(p_parseCape_0_, 0, 0, null);
/* 65 */     graphics.dispose();
/* 66 */     return bufferedimage;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CapeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
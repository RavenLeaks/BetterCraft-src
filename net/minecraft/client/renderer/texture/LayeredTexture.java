/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LayeredTexture
/*    */   extends AbstractTexture
/*    */ {
/* 18 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   public final List<String> layeredTextureNames;
/*    */   
/*    */   public LayeredTexture(String... textureNames) {
/* 23 */     this.layeredTextureNames = Lists.newArrayList((Object[])textureNames);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 28 */     deleteGlTexture();
/* 29 */     BufferedImage bufferedimage = null;
/*    */     
/* 31 */     for (String s : this.layeredTextureNames) {
/*    */       
/* 33 */       IResource iresource = null;
/*    */ 
/*    */       
/*    */       try {
/* 37 */         if (s != null) {
/*    */           
/* 39 */           iresource = resourceManager.getResource(new ResourceLocation(s));
/* 40 */           BufferedImage bufferedimage1 = TextureUtil.readBufferedImage(iresource.getInputStream());
/*    */           
/* 42 */           if (bufferedimage == null)
/*    */           {
/* 44 */             bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
/*    */           }
/*    */           
/* 47 */           bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, null);
/*    */         } 
/*    */ 
/*    */         
/*    */         continue;
/* 52 */       } catch (IOException ioexception) {
/*    */         
/* 54 */         LOGGER.error("Couldn't load layered image", ioexception);
/*    */       }
/*    */       finally {
/*    */         
/* 58 */         IOUtils.closeQuietly((Closeable)iresource);
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 64 */     TextureUtil.uploadTextureImage(getGlTextureId(), bufferedimage);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\LayeredTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
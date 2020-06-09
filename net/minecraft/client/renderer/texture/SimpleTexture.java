/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import shadersmod.client.ShadersTex;
/*    */ 
/*    */ public class SimpleTexture
/*    */   extends AbstractTexture
/*    */ {
/* 19 */   private static final Logger LOG = LogManager.getLogger();
/*    */   
/*    */   protected final ResourceLocation textureLocation;
/*    */   
/*    */   public SimpleTexture(ResourceLocation textureResourceLocation) {
/* 24 */     this.textureLocation = textureResourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 29 */     deleteGlTexture();
/* 30 */     IResource iresource = null;
/*    */ 
/*    */     
/*    */     try {
/* 34 */       iresource = resourceManager.getResource(this.textureLocation);
/* 35 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
/* 36 */       boolean flag = false;
/* 37 */       boolean flag1 = false;
/*    */       
/* 39 */       if (iresource.hasMetadata()) {
/*    */         
/*    */         try {
/*    */           
/* 43 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*    */           
/* 45 */           if (texturemetadatasection != null)
/*    */           {
/* 47 */             flag = texturemetadatasection.getTextureBlur();
/* 48 */             flag1 = texturemetadatasection.getTextureClamp();
/*    */           }
/*    */         
/* 51 */         } catch (RuntimeException runtimeexception1) {
/*    */           
/* 53 */           LOG.warn("Failed reading metadata of: {}", this.textureLocation, runtimeexception1);
/*    */         } 
/*    */       }
/*    */       
/* 57 */       if (Config.isShaders())
/*    */       {
/* 59 */         ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, flag, flag1, resourceManager, this.textureLocation, getMultiTexID());
/*    */       }
/*    */       else
/*    */       {
/* 63 */         TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, flag, flag1);
/*    */       }
/*    */     
/*    */     } finally {
/*    */       
/* 68 */       IOUtils.closeQuietly((Closeable)iresource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\SimpleTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
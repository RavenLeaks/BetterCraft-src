/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.ShadersTex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   private final int[] dynamicTextureData;
/*    */   private final int width;
/*    */   private final int height;
/*    */   private boolean shadersInitialized;
/*    */   
/*    */   public DynamicTexture(BufferedImage bufferedImage) {
/* 22 */     this(bufferedImage.getWidth(), bufferedImage.getHeight());
/* 23 */     bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
/* 24 */     updateDynamicTexture();
/*    */   }
/*    */ 
/*    */   
/*    */   public DynamicTexture(int textureWidth, int textureHeight) {
/* 29 */     this.shadersInitialized = false;
/* 30 */     this.width = textureWidth;
/* 31 */     this.height = textureHeight;
/* 32 */     this.dynamicTextureData = new int[textureWidth * textureHeight * 3];
/*    */     
/* 34 */     if (Config.isShaders()) {
/*    */       
/* 36 */       ShadersTex.initDynamicTexture(getGlTextureId(), textureWidth, textureHeight, this);
/* 37 */       this.shadersInitialized = true;
/*    */     }
/*    */     else {
/*    */       
/* 41 */       TextureUtil.allocateTexture(getGlTextureId(), textureWidth, textureHeight);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {}
/*    */ 
/*    */   
/*    */   public void updateDynamicTexture() {
/* 51 */     if (Config.isShaders()) {
/*    */       
/* 53 */       if (!this.shadersInitialized) {
/*    */         
/* 55 */         ShadersTex.initDynamicTexture(getGlTextureId(), this.width, this.height, this);
/* 56 */         this.shadersInitialized = true;
/*    */       } 
/*    */       
/* 59 */       ShadersTex.updateDynamicTexture(getGlTextureId(), this.dynamicTextureData, this.width, this.height, this);
/*    */     }
/*    */     else {
/*    */       
/* 63 */       TextureUtil.uploadTexture(getGlTextureId(), this.dynamicTextureData, this.width, this.height);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getTextureData() {
/* 69 */     return this.dynamicTextureData;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\DynamicTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
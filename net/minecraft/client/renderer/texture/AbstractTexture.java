/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import shadersmod.client.MultiTexID;
/*    */ import shadersmod.client.ShadersTex;
/*    */ 
/*    */ public abstract class AbstractTexture
/*    */   implements ITextureObject {
/*  9 */   protected int glTextureId = -1;
/*    */   protected boolean blur;
/*    */   protected boolean mipmap;
/*    */   protected boolean blurLast;
/*    */   protected boolean mipmapLast;
/*    */   public MultiTexID multiTex;
/*    */   
/*    */   public void setBlurMipmapDirect(boolean blurIn, boolean mipmapIn) {
/*    */     int i, j;
/* 18 */     this.blur = blurIn;
/* 19 */     this.mipmap = mipmapIn;
/*    */ 
/*    */ 
/*    */     
/* 23 */     if (blurIn) {
/*    */       
/* 25 */       i = mipmapIn ? 9987 : 9729;
/* 26 */       j = 9729;
/*    */     }
/*    */     else {
/*    */       
/* 30 */       i = mipmapIn ? 9986 : 9728;
/* 31 */       j = 9728;
/*    */     } 
/*    */     
/* 34 */     GlStateManager.bindTexture(getGlTextureId());
/* 35 */     GlStateManager.glTexParameteri(3553, 10241, i);
/* 36 */     GlStateManager.glTexParameteri(3553, 10240, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlurMipmap(boolean blurIn, boolean mipmapIn) {
/* 41 */     this.blurLast = this.blur;
/* 42 */     this.mipmapLast = this.mipmap;
/* 43 */     setBlurMipmapDirect(blurIn, mipmapIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void restoreLastBlurMipmap() {
/* 48 */     setBlurMipmapDirect(this.blurLast, this.mipmapLast);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGlTextureId() {
/* 53 */     if (this.glTextureId == -1)
/*    */     {
/* 55 */       this.glTextureId = TextureUtil.glGenTextures();
/*    */     }
/*    */     
/* 58 */     return this.glTextureId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteGlTexture() {
/* 63 */     ShadersTex.deleteTextures(this, this.glTextureId);
/*    */     
/* 65 */     if (this.glTextureId != -1) {
/*    */       
/* 67 */       TextureUtil.deleteTexture(this.glTextureId);
/* 68 */       this.glTextureId = -1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public MultiTexID getMultiTexID() {
/* 74 */     return ShadersTex.getMultiTexID(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\AbstractTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
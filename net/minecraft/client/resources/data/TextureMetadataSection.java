/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ public class TextureMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final boolean textureBlur;
/*    */   private final boolean textureClamp;
/*    */   
/*    */   public TextureMetadataSection(boolean textureBlurIn, boolean textureClampIn) {
/* 10 */     this.textureBlur = textureBlurIn;
/* 11 */     this.textureClamp = textureClampIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getTextureBlur() {
/* 16 */     return this.textureBlur;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getTextureClamp() {
/* 21 */     return this.textureClamp;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\TextureMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
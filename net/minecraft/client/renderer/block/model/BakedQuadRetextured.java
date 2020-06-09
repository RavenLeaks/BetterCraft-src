/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ 
/*    */ public class BakedQuadRetextured
/*    */   extends BakedQuad
/*    */ {
/*    */   private final TextureAtlasSprite texture;
/*    */   private final TextureAtlasSprite spriteOld;
/*    */   
/*    */   public BakedQuadRetextured(BakedQuad quad, TextureAtlasSprite textureIn) {
/* 13 */     super(Arrays.copyOf(quad.getVertexData(), (quad.getVertexData()).length), quad.tintIndex, FaceBakery.getFacingFromVertexData(quad.getVertexData()), textureIn);
/* 14 */     this.texture = textureIn;
/* 15 */     this.format = quad.format;
/* 16 */     this.applyDiffuseLighting = quad.applyDiffuseLighting;
/* 17 */     this.spriteOld = quad.getSprite();
/* 18 */     remapQuad();
/* 19 */     fixVertexData();
/*    */   }
/*    */ 
/*    */   
/*    */   private void remapQuad() {
/* 24 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 26 */       int j = this.format.getIntegerSize() * i;
/* 27 */       int k = this.format.getUvOffsetById(0) / 4;
/* 28 */       this.vertexData[j + k] = Float.floatToRawIntBits(this.texture.getInterpolatedU(this.spriteOld.getUnInterpolatedU(Float.intBitsToFloat(this.vertexData[j + k]))));
/* 29 */       this.vertexData[j + k + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(this.spriteOld.getUnInterpolatedV(Float.intBitsToFloat(this.vertexData[j + k + 1]))));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BakedQuadRetextured.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
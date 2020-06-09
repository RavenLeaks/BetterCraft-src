/*    */ package com.TominoCZ.FBP.model;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FBPModelHelper
/*    */ {
/* 14 */   static int vertexes = 0;
/*    */   
/*    */   static boolean isAllCorruptedTexture = true;
/*    */ 
/*    */   
/*    */   public static boolean isModelValid(IBlockState state) {
/* 20 */     IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
/* 21 */       .getModelForState(state);
/*    */     
/* 23 */     TextureAtlasSprite s = model.getParticleTexture();
/*    */     
/* 25 */     if (s == null || s.getIconName().equals("missingno")) {
/* 26 */       return false;
/*    */     }
/* 28 */     vertexes = 0;
/*    */ 
/*    */     
/*    */     try {
/* 32 */       FBPModelTransformer.transform(model, state, 0L, new FBPModelTransformer.IVertexTransformer()
/*    */           {
/*    */             public float[] transform(BakedQuad quad, VertexFormatElement element, float... data)
/*    */             {
/* 36 */               if (element.getUsage() == VertexFormatElement.EnumUsage.POSITION) {
/* 37 */                 FBPModelHelper.vertexes++;
/*    */               }
/* 39 */               TextureAtlasSprite s = quad.getSprite();
/*    */               
/* 41 */               if (s != null && !s.getIconName().equals("missingno")) {
/* 42 */                 FBPModelHelper.isAllCorruptedTexture = false;
/*    */               }
/* 44 */               return data;
/*    */             }
/*    */           });
/* 47 */     } catch (Throwable throwable) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     return (vertexes >= 3 && !isAllCorruptedTexture);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\model\FBPModelHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
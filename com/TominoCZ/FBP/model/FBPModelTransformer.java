/*    */ package com.TominoCZ.FBP.model;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*    */ import net.minecraftforge.client.model.pipeline.LightUtil;
/*    */ import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FBPModelTransformer
/*    */ {
/*    */   public static FBPSimpleBakedModel transform(IBakedModel model, IBlockState state, long rand, IVertexTransformer transformer) {
/*    */     try {
/* 21 */       FBPSimpleBakedModel out = new FBPSimpleBakedModel(model);
/*    */       
/* 23 */       for (int i = 0; i <= 6; i++) {
/*    */         
/* 25 */         EnumFacing side = (i == 6) ? null : EnumFacing.getFront(i);
/*    */         
/* 27 */         for (BakedQuad quad : model.getQuads(state, side, rand))
/*    */         {
/* 29 */           out.addQuad(side, transform(quad, transformer));
/*    */         }
/*    */       } 
/*    */       
/* 33 */       return out;
/* 34 */     } catch (Throwable t) {
/*    */       
/* 36 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static BakedQuad transform(BakedQuad quad, IVertexTransformer transformer) {
/* 42 */     VertexFormat format = quad.getFormat();
/* 43 */     UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
/* 44 */     LightUtil.putBakedQuad(new VertexTransformerWrapper((IVertexConsumer)builder, quad, transformer), quad);
/* 45 */     return (BakedQuad)builder.build();
/*    */   }
/*    */   
/*    */   public static interface IVertexTransformer {
/*    */     float[] transform(BakedQuad param1BakedQuad, VertexFormatElement param1VertexFormatElement, float... param1VarArgs);
/*    */   }
/*    */   
/*    */   private static final class VertexTransformerWrapper implements IVertexConsumer {
/*    */     private final IVertexConsumer parent;
/*    */     private final BakedQuad parentQuad;
/*    */     
/*    */     public VertexTransformerWrapper(IVertexConsumer parent, BakedQuad parentQuad, FBPModelTransformer.IVertexTransformer transformer) {
/* 57 */       this.parent = parent;
/* 58 */       this.parentQuad = parentQuad;
/* 59 */       this.format = parent.getVertexFormat();
/* 60 */       this.transformer = transformer;
/*    */     }
/*    */     private final VertexFormat format;
/*    */     private final FBPModelTransformer.IVertexTransformer transformer;
/*    */     
/*    */     public VertexFormat getVertexFormat() {
/* 66 */       return this.format;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void setQuadTint(int tint) {
/* 72 */       this.parent.setQuadTint(tint);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void setQuadOrientation(EnumFacing orientation) {
/* 78 */       this.parent.setQuadOrientation(orientation);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void put(int elementId, float... data) {
/* 84 */       VertexFormatElement element = this.format.getElement(elementId);
/* 85 */       this.parent.put(elementId, this.transformer.transform(this.parentQuad, element, data));
/*    */     }
/*    */ 
/*    */     
/*    */     public void setQuadColored() {
/* 90 */       this.parent.setQuadColored();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\model\FBPModelTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
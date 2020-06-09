/*    */ package com.TominoCZ.FBP.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FBPVertexUtil
/*    */ {
/*    */   public static float[] calculateUV(Vector3f from, Vector3f to, EnumFacing facing1) {
/* 15 */     EnumFacing facing = facing1;
/* 16 */     if (facing == null)
/*    */     {
/* 18 */       if (from.y == to.y) {
/*    */         
/* 20 */         facing = EnumFacing.UP;
/* 21 */       } else if (from.x == to.x) {
/*    */         
/* 23 */         facing = EnumFacing.EAST;
/* 24 */       } else if (from.z == to.z) {
/*    */         
/* 26 */         facing = EnumFacing.SOUTH;
/*    */       } else {
/*    */         
/* 29 */         return null;
/*    */       } 
/*    */     }
/*    */     
/* 33 */     switch (facing) {
/*    */       
/*    */       case null:
/* 36 */         return new float[] { from.x, 16.0F - to.z, to.x, 16.0F - from.z };
/*    */       case UP:
/* 38 */         return new float[] { from.x, from.z, to.x, to.z };
/*    */       case NORTH:
/* 40 */         return new float[] { 16.0F - to.x, 16.0F - to.y, 16.0F - from.x, 16.0F - from.y };
/*    */       case SOUTH:
/* 42 */         return new float[] { from.x, 16.0F - to.y, to.x, 16.0F - from.y };
/*    */       case WEST:
/* 44 */         return new float[] { from.z, 16.0F - to.y, to.z, 16.0F - from.y };
/*    */       case EAST:
/* 46 */         return new float[] { 16.0F - to.z, 16.0F - to.y, 16.0F - from.z, 16.0F - from.y };
/*    */     } 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BakedQuad clone(BakedQuad quad) {
/* 54 */     return new BakedQuad(quad.getVertexData(), quad.getTintIndex(), quad.getFace(), quad.getSprite(), 
/* 55 */         quad.shouldApplyDiffuseLighting(), quad.getFormat());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int multiplyColor(int src, int dst) {
/* 60 */     int out = 0;
/* 61 */     for (int i = 0; i < 32; i += 8)
/*    */     {
/* 63 */       out |= ((src >> i & 0xFF) * (dst >> i & 0xFF) / 255 & 0xFF) << i;
/*    */     }
/* 65 */     return out;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BakedQuad recolorQuad(BakedQuad quad, int color) {
/* 70 */     int c = DefaultVertexFormats.BLOCK.getColorOffset() / 4;
/* 71 */     int v = DefaultVertexFormats.BLOCK.getNextOffset() / 4;
/* 72 */     int[] vertexData = quad.getVertexData();
/* 73 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 75 */       vertexData[v * i + c] = multiplyColor(vertexData[v * i + c], color);
/*    */     }
/* 77 */     return quad;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addRecoloredQuads(List<BakedQuad> src, int color, List<BakedQuad> target, EnumFacing facing) {
/* 82 */     for (BakedQuad quad : src) {
/*    */       
/* 84 */       BakedQuad quad1 = clone(quad);
/* 85 */       int c = DefaultVertexFormats.BLOCK.getColorOffset() / 4;
/* 86 */       int v = DefaultVertexFormats.BLOCK.getNextOffset() / 4;
/* 87 */       int[] vertexData = quad1.getVertexData();
/* 88 */       for (int i = 0; i < 4; i++)
/*    */       {
/* 90 */         vertexData[v * i + c] = multiplyColor(vertexData[v * i + c], color);
/*    */       }
/* 92 */       target.add(quad1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FB\\util\FBPVertexUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
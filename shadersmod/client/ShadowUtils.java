/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.renderer.ViewFrustum;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class ShadowUtils
/*    */ {
/*    */   public static Iterator<RenderChunk> makeShadowChunkIterator(WorldClient world, double partialTicks, Entity viewEntity, int renderDistanceChunks, ViewFrustum viewFrustum) {
/* 17 */     float f = Shaders.getShadowRenderDistance();
/*    */     
/* 19 */     if (f > 0.0F && f < ((renderDistanceChunks - 1) * 16)) {
/*    */       
/* 21 */       int i = MathHelper.ceil(f / 16.0F) + 1;
/* 22 */       float f6 = world.getCelestialAngleRadians((float)partialTicks);
/* 23 */       float f1 = Shaders.sunPathRotation * 0.017453292F;
/* 24 */       float f2 = (f6 > 1.5707964F && f6 < 4.712389F) ? (f6 + 3.1415927F) : f6;
/* 25 */       float f3 = -MathHelper.sin(f2);
/* 26 */       float f4 = MathHelper.cos(f2) * MathHelper.cos(f1);
/* 27 */       float f5 = -MathHelper.cos(f2) * MathHelper.sin(f1);
/* 28 */       BlockPos blockpos = new BlockPos(MathHelper.floor(viewEntity.posX) >> 4, MathHelper.floor(viewEntity.posY) >> 4, MathHelper.floor(viewEntity.posZ) >> 4);
/* 29 */       BlockPos blockpos1 = blockpos.add((-f3 * i), (-f4 * i), (-f5 * i));
/* 30 */       BlockPos blockpos2 = blockpos.add((f3 * renderDistanceChunks), (f4 * renderDistanceChunks), (f5 * renderDistanceChunks));
/* 31 */       IteratorRenderChunks iteratorrenderchunks = new IteratorRenderChunks(viewFrustum, blockpos1, blockpos2, i, i);
/* 32 */       return iteratorrenderchunks;
/*    */     } 
/*    */ 
/*    */     
/* 36 */     List<RenderChunk> list = Arrays.asList(viewFrustum.renderChunks);
/* 37 */     Iterator<RenderChunk> iterator = list.iterator();
/* 38 */     return iterator;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShadowUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
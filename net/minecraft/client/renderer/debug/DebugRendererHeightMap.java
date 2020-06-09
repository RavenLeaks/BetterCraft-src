/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class DebugRendererHeightMap
/*    */   implements DebugRenderer.IDebugRenderer {
/*    */   private final Minecraft minecraft;
/*    */   
/*    */   public DebugRendererHeightMap(Minecraft minecraftIn) {
/* 20 */     this.minecraft = minecraftIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float p_190060_1_, long p_190060_2_) {
/* 25 */     EntityPlayerSP entityPlayerSP = this.minecraft.player;
/* 26 */     WorldClient worldClient = this.minecraft.world;
/* 27 */     double d0 = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * p_190060_1_;
/* 28 */     double d1 = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * p_190060_1_;
/* 29 */     double d2 = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * p_190060_1_;
/* 30 */     GlStateManager.pushMatrix();
/* 31 */     GlStateManager.enableBlend();
/* 32 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 33 */     GlStateManager.disableTexture2D();
/* 34 */     BlockPos blockpos = new BlockPos(((EntityPlayer)entityPlayerSP).posX, 0.0D, ((EntityPlayer)entityPlayerSP).posZ);
/* 35 */     Iterable<BlockPos> iterable = BlockPos.getAllInBox(blockpos.add(-40, 0, -40), blockpos.add(40, 0, 40));
/* 36 */     Tessellator tessellator = Tessellator.getInstance();
/* 37 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 38 */     bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*    */     
/* 40 */     for (BlockPos blockpos1 : iterable) {
/*    */       
/* 42 */       int i = worldClient.getHeight(blockpos1.getX(), blockpos1.getZ());
/*    */       
/* 44 */       if (worldClient.getBlockState(blockpos1.add(0, i, 0).down()) == Blocks.AIR.getDefaultState()) {
/*    */         
/* 46 */         RenderGlobal.addChainedFilledBoxVertices(bufferbuilder, (blockpos1.getX() + 0.25F) - d0, i - d1, (blockpos1.getZ() + 0.25F) - d2, (blockpos1.getX() + 0.75F) - d0, i + 0.09375D - d1, (blockpos1.getZ() + 0.75F) - d2, 0.0F, 0.0F, 1.0F, 0.5F);
/*    */         
/*    */         continue;
/*    */       } 
/* 50 */       RenderGlobal.addChainedFilledBoxVertices(bufferbuilder, (blockpos1.getX() + 0.25F) - d0, i - d1, (blockpos1.getZ() + 0.25F) - d2, (blockpos1.getX() + 0.75F) - d0, i + 0.09375D - d1, (blockpos1.getZ() + 0.75F) - d2, 0.0F, 1.0F, 0.0F, 0.5F);
/*    */     } 
/*    */ 
/*    */     
/* 54 */     tessellator.draw();
/* 55 */     GlStateManager.enableTexture2D();
/* 56 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererHeightMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
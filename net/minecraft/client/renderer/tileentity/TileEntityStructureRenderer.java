/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityStructure;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityStructureRenderer extends TileEntitySpecialRenderer<TileEntityStructure> {
/*     */   public void func_192841_a(TileEntityStructure p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  19 */     if ((Minecraft.getMinecraft()).player.canUseCommandBlock() || (Minecraft.getMinecraft()).player.isSpectator()) {
/*     */       
/*  21 */       super.func_192841_a(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, p_192841_10_);
/*  22 */       BlockPos blockpos = p_192841_1_.getPosition();
/*  23 */       BlockPos blockpos1 = p_192841_1_.getStructureSize();
/*     */       
/*  25 */       if (blockpos1.getX() >= 1 && blockpos1.getY() >= 1 && blockpos1.getZ() >= 1)
/*     */       {
/*  27 */         if (p_192841_1_.getMode() == TileEntityStructure.Mode.SAVE || p_192841_1_.getMode() == TileEntityStructure.Mode.LOAD) {
/*     */           
/*  29 */           double d3, d4, d5, d7, d8, d10, d0 = 0.01D;
/*  30 */           double d1 = blockpos.getX();
/*  31 */           double d2 = blockpos.getZ();
/*  32 */           double d6 = p_192841_4_ + blockpos.getY() - 0.01D;
/*  33 */           double d9 = d6 + blockpos1.getY() + 0.02D;
/*     */ 
/*     */ 
/*     */           
/*  37 */           switch (p_192841_1_.getMirror()) {
/*     */             
/*     */             case LEFT_RIGHT:
/*  40 */               d3 = blockpos1.getX() + 0.02D;
/*  41 */               d4 = -(blockpos1.getZ() + 0.02D);
/*     */               break;
/*     */             
/*     */             case null:
/*  45 */               d3 = -(blockpos1.getX() + 0.02D);
/*  46 */               d4 = blockpos1.getZ() + 0.02D;
/*     */               break;
/*     */             
/*     */             default:
/*  50 */               d3 = blockpos1.getX() + 0.02D;
/*  51 */               d4 = blockpos1.getZ() + 0.02D;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  59 */           switch (p_192841_1_.getRotation()) {
/*     */             
/*     */             case CLOCKWISE_90:
/*  62 */               d5 = p_192841_2_ + ((d4 < 0.0D) ? (d1 - 0.01D) : (d1 + 1.0D + 0.01D));
/*  63 */               d7 = p_192841_6_ + ((d3 < 0.0D) ? (d2 + 1.0D + 0.01D) : (d2 - 0.01D));
/*  64 */               d8 = d5 - d4;
/*  65 */               d10 = d7 + d3;
/*     */               break;
/*     */             
/*     */             case null:
/*  69 */               d5 = p_192841_2_ + ((d3 < 0.0D) ? (d1 - 0.01D) : (d1 + 1.0D + 0.01D));
/*  70 */               d7 = p_192841_6_ + ((d4 < 0.0D) ? (d2 - 0.01D) : (d2 + 1.0D + 0.01D));
/*  71 */               d8 = d5 - d3;
/*  72 */               d10 = d7 - d4;
/*     */               break;
/*     */             
/*     */             case COUNTERCLOCKWISE_90:
/*  76 */               d5 = p_192841_2_ + ((d4 < 0.0D) ? (d1 + 1.0D + 0.01D) : (d1 - 0.01D));
/*  77 */               d7 = p_192841_6_ + ((d3 < 0.0D) ? (d2 - 0.01D) : (d2 + 1.0D + 0.01D));
/*  78 */               d8 = d5 + d4;
/*  79 */               d10 = d7 - d3;
/*     */               break;
/*     */             
/*     */             default:
/*  83 */               d5 = p_192841_2_ + ((d3 < 0.0D) ? (d1 + 1.0D + 0.01D) : (d1 - 0.01D));
/*  84 */               d7 = p_192841_6_ + ((d4 < 0.0D) ? (d2 + 1.0D + 0.01D) : (d2 - 0.01D));
/*  85 */               d8 = d5 + d3;
/*  86 */               d10 = d7 + d4;
/*     */               break;
/*     */           } 
/*  89 */           int i = 255;
/*  90 */           int j = 223;
/*  91 */           int k = 127;
/*  92 */           Tessellator tessellator = Tessellator.getInstance();
/*  93 */           BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  94 */           GlStateManager.disableFog();
/*  95 */           GlStateManager.disableLighting();
/*  96 */           GlStateManager.disableTexture2D();
/*  97 */           GlStateManager.enableBlend();
/*  98 */           GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  99 */           setLightmapDisabled(true);
/*     */           
/* 101 */           if (p_192841_1_.getMode() == TileEntityStructure.Mode.SAVE || p_192841_1_.showsBoundingBox())
/*     */           {
/* 103 */             renderBox(tessellator, bufferbuilder, d5, d6, d7, d8, d9, d10, 255, 223, 127);
/*     */           }
/*     */           
/* 106 */           if (p_192841_1_.getMode() == TileEntityStructure.Mode.SAVE && p_192841_1_.showsAir()) {
/*     */             
/* 108 */             renderInvisibleBlocks(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, blockpos, tessellator, bufferbuilder, true);
/* 109 */             renderInvisibleBlocks(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, blockpos, tessellator, bufferbuilder, false);
/*     */           } 
/*     */           
/* 112 */           setLightmapDisabled(false);
/* 113 */           GlStateManager.glLineWidth(1.0F);
/* 114 */           GlStateManager.enableLighting();
/* 115 */           GlStateManager.enableTexture2D();
/* 116 */           GlStateManager.enableDepth();
/* 117 */           GlStateManager.depthMask(true);
/* 118 */           GlStateManager.enableFog();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderInvisibleBlocks(TileEntityStructure p_190054_1_, double p_190054_2_, double p_190054_4_, double p_190054_6_, BlockPos p_190054_8_, Tessellator p_190054_9_, BufferBuilder p_190054_10_, boolean p_190054_11_) {
/* 126 */     GlStateManager.glLineWidth(p_190054_11_ ? 3.0F : 1.0F);
/* 127 */     p_190054_10_.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 128 */     World world = p_190054_1_.getWorld();
/* 129 */     BlockPos blockpos = p_190054_1_.getPos();
/* 130 */     BlockPos blockpos1 = blockpos.add((Vec3i)p_190054_8_);
/*     */     
/* 132 */     for (BlockPos blockpos2 : BlockPos.getAllInBox(blockpos1, blockpos1.add((Vec3i)p_190054_1_.getStructureSize()).add(-1, -1, -1))) {
/*     */       
/* 134 */       IBlockState iblockstate = world.getBlockState(blockpos2);
/* 135 */       boolean flag = (iblockstate == Blocks.AIR.getDefaultState());
/* 136 */       boolean flag1 = (iblockstate == Blocks.STRUCTURE_VOID.getDefaultState());
/*     */       
/* 138 */       if (flag || flag1) {
/*     */         
/* 140 */         float f = flag ? 0.05F : 0.0F;
/* 141 */         double d0 = ((blockpos2.getX() - blockpos.getX()) + 0.45F) + p_190054_2_ - f;
/* 142 */         double d1 = ((blockpos2.getY() - blockpos.getY()) + 0.45F) + p_190054_4_ - f;
/* 143 */         double d2 = ((blockpos2.getZ() - blockpos.getZ()) + 0.45F) + p_190054_6_ - f;
/* 144 */         double d3 = ((blockpos2.getX() - blockpos.getX()) + 0.55F) + p_190054_2_ + f;
/* 145 */         double d4 = ((blockpos2.getY() - blockpos.getY()) + 0.55F) + p_190054_4_ + f;
/* 146 */         double d5 = ((blockpos2.getZ() - blockpos.getZ()) + 0.55F) + p_190054_6_ + f;
/*     */         
/* 148 */         if (p_190054_11_) {
/*     */           
/* 150 */           RenderGlobal.drawBoundingBox(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F); continue;
/*     */         } 
/* 152 */         if (flag) {
/*     */           
/* 154 */           RenderGlobal.drawBoundingBox(p_190054_10_, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F);
/*     */           
/*     */           continue;
/*     */         } 
/* 158 */         RenderGlobal.drawBoundingBox(p_190054_10_, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     p_190054_9_.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderBox(Tessellator p_190055_1_, BufferBuilder p_190055_2_, double p_190055_3_, double p_190055_5_, double p_190055_7_, double p_190055_9_, double p_190055_11_, double p_190055_13_, int p_190055_15_, int p_190055_16_, int p_190055_17_) {
/* 168 */     GlStateManager.glLineWidth(2.0F);
/* 169 */     p_190055_2_.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 170 */     p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, 0.0F).endVertex();
/* 171 */     p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 172 */     p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_17_, p_190055_17_, p_190055_15_).endVertex();
/* 173 */     p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 174 */     p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 175 */     p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_7_).color(p_190055_17_, p_190055_17_, p_190055_16_, p_190055_15_).endVertex();
/* 176 */     p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_7_).color(p_190055_17_, p_190055_16_, p_190055_17_, p_190055_15_).endVertex();
/* 177 */     p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 178 */     p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 179 */     p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 180 */     p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 181 */     p_190055_2_.pos(p_190055_3_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 182 */     p_190055_2_.pos(p_190055_3_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 183 */     p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 184 */     p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_13_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 185 */     p_190055_2_.pos(p_190055_9_, p_190055_11_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 186 */     p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, p_190055_15_).endVertex();
/* 187 */     p_190055_2_.pos(p_190055_9_, p_190055_5_, p_190055_7_).color(p_190055_16_, p_190055_16_, p_190055_16_, 0.0F).endVertex();
/* 188 */     p_190055_1_.draw();
/* 189 */     GlStateManager.glLineWidth(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalRenderer(TileEntityStructure te) {
/* 194 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityStructureRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
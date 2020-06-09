/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DebugRendererSolidFace implements DebugRenderer.IDebugRenderer {
/*     */   private final Minecraft field_193851_a;
/*     */   
/*     */   public DebugRendererSolidFace(Minecraft p_i47478_1_) {
/*  24 */     this.field_193851_a = p_i47478_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_190060_1_, long p_190060_2_) {
/*  29 */     EntityPlayerSP entityPlayerSP = this.field_193851_a.player;
/*  30 */     double d0 = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * p_190060_1_;
/*  31 */     double d1 = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * p_190060_1_;
/*  32 */     double d2 = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * p_190060_1_;
/*  33 */     World world = this.field_193851_a.player.world;
/*  34 */     Iterable<BlockPos> iterable = BlockPos.func_191532_a(MathHelper.floor(((EntityPlayer)entityPlayerSP).posX - 6.0D), MathHelper.floor(((EntityPlayer)entityPlayerSP).posY - 6.0D), MathHelper.floor(((EntityPlayer)entityPlayerSP).posZ - 6.0D), MathHelper.floor(((EntityPlayer)entityPlayerSP).posX + 6.0D), MathHelper.floor(((EntityPlayer)entityPlayerSP).posY + 6.0D), MathHelper.floor(((EntityPlayer)entityPlayerSP).posZ + 6.0D));
/*  35 */     GlStateManager.enableBlend();
/*  36 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  37 */     GlStateManager.glLineWidth(2.0F);
/*  38 */     GlStateManager.disableTexture2D();
/*  39 */     GlStateManager.depthMask(false);
/*     */     
/*  41 */     for (BlockPos blockpos : iterable) {
/*     */       
/*  43 */       IBlockState iblockstate = world.getBlockState(blockpos);
/*     */       
/*  45 */       if (iblockstate.getBlock() != Blocks.AIR) {
/*     */         
/*  47 */         AxisAlignedBB axisalignedbb = iblockstate.getSelectedBoundingBox(world, blockpos).expandXyz(0.002D).offset(-d0, -d1, -d2);
/*  48 */         double d3 = axisalignedbb.minX;
/*  49 */         double d4 = axisalignedbb.minY;
/*  50 */         double d5 = axisalignedbb.minZ;
/*  51 */         double d6 = axisalignedbb.maxX;
/*  52 */         double d7 = axisalignedbb.maxY;
/*  53 */         double d8 = axisalignedbb.maxZ;
/*  54 */         float f = 1.0F;
/*  55 */         float f1 = 0.0F;
/*  56 */         float f2 = 0.0F;
/*  57 */         float f3 = 0.5F;
/*     */         
/*  59 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.WEST) == BlockFaceShape.SOLID) {
/*     */           
/*  61 */           Tessellator tessellator = Tessellator.getInstance();
/*  62 */           BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  63 */           bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  64 */           bufferbuilder.pos(d3, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  65 */           bufferbuilder.pos(d3, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  66 */           bufferbuilder.pos(d3, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  67 */           bufferbuilder.pos(d3, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  68 */           tessellator.draw();
/*     */         } 
/*     */         
/*  71 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.SOUTH) == BlockFaceShape.SOLID) {
/*     */           
/*  73 */           Tessellator tessellator1 = Tessellator.getInstance();
/*  74 */           BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
/*  75 */           bufferbuilder1.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  76 */           bufferbuilder1.pos(d3, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  77 */           bufferbuilder1.pos(d3, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  78 */           bufferbuilder1.pos(d6, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  79 */           bufferbuilder1.pos(d6, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  80 */           tessellator1.draw();
/*     */         } 
/*     */         
/*  83 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.EAST) == BlockFaceShape.SOLID) {
/*     */           
/*  85 */           Tessellator tessellator2 = Tessellator.getInstance();
/*  86 */           BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
/*  87 */           bufferbuilder2.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  88 */           bufferbuilder2.pos(d6, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  89 */           bufferbuilder2.pos(d6, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  90 */           bufferbuilder2.pos(d6, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  91 */           bufferbuilder2.pos(d6, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  92 */           tessellator2.draw();
/*     */         } 
/*     */         
/*  95 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.NORTH) == BlockFaceShape.SOLID) {
/*     */           
/*  97 */           Tessellator tessellator3 = Tessellator.getInstance();
/*  98 */           BufferBuilder bufferbuilder3 = tessellator3.getBuffer();
/*  99 */           bufferbuilder3.begin(5, DefaultVertexFormats.POSITION_COLOR);
/* 100 */           bufferbuilder3.pos(d6, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 101 */           bufferbuilder3.pos(d6, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 102 */           bufferbuilder3.pos(d3, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 103 */           bufferbuilder3.pos(d3, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 104 */           tessellator3.draw();
/*     */         } 
/*     */         
/* 107 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID) {
/*     */           
/* 109 */           Tessellator tessellator4 = Tessellator.getInstance();
/* 110 */           BufferBuilder bufferbuilder4 = tessellator4.getBuffer();
/* 111 */           bufferbuilder4.begin(5, DefaultVertexFormats.POSITION_COLOR);
/* 112 */           bufferbuilder4.pos(d3, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 113 */           bufferbuilder4.pos(d6, d4, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 114 */           bufferbuilder4.pos(d3, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 115 */           bufferbuilder4.pos(d6, d4, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 116 */           tessellator4.draw();
/*     */         } 
/*     */         
/* 119 */         if (iblockstate.func_193401_d((IBlockAccess)world, blockpos, EnumFacing.UP) == BlockFaceShape.SOLID) {
/*     */           
/* 121 */           Tessellator tessellator5 = Tessellator.getInstance();
/* 122 */           BufferBuilder bufferbuilder5 = tessellator5.getBuffer();
/* 123 */           bufferbuilder5.begin(5, DefaultVertexFormats.POSITION_COLOR);
/* 124 */           bufferbuilder5.pos(d3, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 125 */           bufferbuilder5.pos(d3, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 126 */           bufferbuilder5.pos(d6, d7, d5).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 127 */           bufferbuilder5.pos(d6, d7, d8).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 128 */           tessellator5.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     GlStateManager.depthMask(true);
/* 134 */     GlStateManager.enableTexture2D();
/* 135 */     GlStateManager.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererSolidFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
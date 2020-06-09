/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockPistonBase;
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityPiston;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston> {
/* 23 */   private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
/*    */ 
/*    */   
/*    */   public void func_192841_a(TileEntityPiston p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/* 27 */     BlockPos blockpos = p_192841_1_.getPos();
/* 28 */     IBlockState iblockstate = p_192841_1_.getPistonState();
/* 29 */     Block block = iblockstate.getBlock();
/*    */     
/* 31 */     if (iblockstate.getMaterial() != Material.AIR && p_192841_1_.getProgress(p_192841_8_) < 1.0F) {
/*    */       
/* 33 */       Tessellator tessellator = Tessellator.getInstance();
/* 34 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 35 */       bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 36 */       RenderHelper.disableStandardItemLighting();
/* 37 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 38 */       GlStateManager.enableBlend();
/* 39 */       GlStateManager.disableCull();
/*    */       
/* 41 */       if (Minecraft.isAmbientOcclusionEnabled()) {
/*    */         
/* 43 */         GlStateManager.shadeModel(7425);
/*    */       }
/*    */       else {
/*    */         
/* 47 */         GlStateManager.shadeModel(7424);
/*    */       } 
/*    */       
/* 50 */       bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
/* 51 */       bufferbuilder.setTranslation(p_192841_2_ - blockpos.getX() + p_192841_1_.getOffsetX(p_192841_8_), p_192841_4_ - blockpos.getY() + p_192841_1_.getOffsetY(p_192841_8_), p_192841_6_ - blockpos.getZ() + p_192841_1_.getOffsetZ(p_192841_8_));
/* 52 */       World world = getWorld();
/*    */       
/* 54 */       if (block == Blocks.PISTON_HEAD && p_192841_1_.getProgress(p_192841_8_) <= 0.25F) {
/*    */         
/* 56 */         iblockstate = iblockstate.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf(true));
/* 57 */         renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
/*    */       }
/* 59 */       else if (p_192841_1_.shouldPistonHeadBeRendered() && !p_192841_1_.isExtending()) {
/*    */         
/* 61 */         BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = (block == Blocks.STICKY_PISTON) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 62 */         IBlockState iblockstate1 = Blocks.PISTON_HEAD.getDefaultState().withProperty((IProperty)BlockPistonExtension.TYPE, (Comparable)blockpistonextension$enumpistontype).withProperty((IProperty)BlockPistonExtension.FACING, iblockstate.getValue((IProperty)BlockPistonBase.FACING));
/* 63 */         iblockstate1 = iblockstate1.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf((p_192841_1_.getProgress(p_192841_8_) >= 0.5F)));
/* 64 */         renderStateModel(blockpos, iblockstate1, bufferbuilder, world, true);
/* 65 */         bufferbuilder.setTranslation(p_192841_2_ - blockpos.getX(), p_192841_4_ - blockpos.getY(), p_192841_6_ - blockpos.getZ());
/* 66 */         iblockstate = iblockstate.withProperty((IProperty)BlockPistonBase.EXTENDED, Boolean.valueOf(true));
/* 67 */         renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
/*    */       }
/*    */       else {
/*    */         
/* 71 */         renderStateModel(blockpos, iblockstate, bufferbuilder, world, false);
/*    */       } 
/*    */       
/* 74 */       bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
/* 75 */       tessellator.draw();
/* 76 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean renderStateModel(BlockPos p_188186_1_, IBlockState p_188186_2_, BufferBuilder p_188186_3_, World p_188186_4_, boolean p_188186_5_) {
/* 82 */     return this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)p_188186_4_, this.blockRenderer.getModelForState(p_188186_2_), p_188186_2_, p_188186_1_, p_188186_3_, p_188186_5_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityPistonRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
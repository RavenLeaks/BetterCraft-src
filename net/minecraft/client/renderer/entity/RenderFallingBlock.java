/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RenderFallingBlock extends Render<EntityFallingBlock> {
/*    */   public RenderFallingBlock(RenderManager renderManagerIn) {
/* 22 */     super(renderManagerIn);
/* 23 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 31 */     if (entity.getBlock() != null) {
/*    */       
/* 33 */       IBlockState iblockstate = entity.getBlock();
/*    */       
/* 35 */       if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL) {
/*    */         
/* 37 */         World world = entity.getWorldObj();
/*    */         
/* 39 */         if (iblockstate != world.getBlockState(new BlockPos((Entity)entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*    */           
/* 41 */           bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 42 */           GlStateManager.pushMatrix();
/* 43 */           GlStateManager.disableLighting();
/* 44 */           Tessellator tessellator = Tessellator.getInstance();
/* 45 */           BufferBuilder bufferbuilder = tessellator.getBuffer();
/*    */           
/* 47 */           if (this.renderOutlines) {
/*    */             
/* 49 */             GlStateManager.enableColorMaterial();
/* 50 */             GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */           } 
/*    */           
/* 53 */           bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
/* 54 */           BlockPos blockpos = new BlockPos(entity.posX, (entity.getEntityBoundingBox()).maxY, entity.posZ);
/* 55 */           GlStateManager.translate((float)(x - blockpos.getX() - 0.5D), (float)(y - blockpos.getY()), (float)(z - blockpos.getZ() - 0.5D));
/* 56 */           BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 57 */           blockrendererdispatcher.getBlockModelRenderer().renderModel((IBlockAccess)world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, bufferbuilder, false, MathHelper.getPositionRandom((Vec3i)entity.getOrigin()));
/* 58 */           tessellator.draw();
/*    */           
/* 60 */           if (this.renderOutlines) {
/*    */             
/* 62 */             GlStateManager.disableOutlineMode();
/* 63 */             GlStateManager.disableColorMaterial();
/*    */           } 
/*    */           
/* 66 */           GlStateManager.enableLighting();
/* 67 */           GlStateManager.popMatrix();
/* 68 */           super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFallingBlock entity) {
/* 79 */     return TextureMap.LOCATION_BLOCKS_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
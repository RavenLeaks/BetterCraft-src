/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.SimpleBakedModel;
/*     */ import net.minecraft.client.renderer.color.BlockColors;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ public class BlockRendererDispatcher
/*     */   implements IResourceManagerReloadListener {
/*     */   private final BlockModelShapes blockModelShapes;
/*     */   private final BlockModelRenderer blockModelRenderer;
/*  22 */   private final ChestRenderer chestRenderer = new ChestRenderer();
/*     */   
/*     */   private final BlockFluidRenderer fluidRenderer;
/*     */   
/*     */   public BlockRendererDispatcher(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
/*  27 */     this.blockModelShapes = p_i46577_1_;
/*  28 */     this.blockModelRenderer = new BlockModelRenderer(p_i46577_2_);
/*  29 */     this.fluidRenderer = new BlockFluidRenderer(p_i46577_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelShapes getBlockModelShapes() {
/*  34 */     return this.blockModelShapes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBlockDamage(IBlockState state, BlockPos pos, TextureAtlasSprite texture, IBlockAccess blockAccess) {
/*  39 */     if (state.getRenderType() == EnumBlockRenderType.MODEL) {
/*     */       
/*  41 */       state = state.getActualState(blockAccess, pos);
/*  42 */       IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
/*  43 */       IBakedModel ibakedmodel1 = (new SimpleBakedModel.Builder(state, ibakedmodel, texture, pos)).makeBakedModel();
/*  44 */       this.blockModelRenderer.renderModel(blockAccess, ibakedmodel1, state, pos, Tessellator.getInstance().getBuffer(), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder worldRendererIn) {
/*     */     try {
/*  52 */       EnumBlockRenderType enumblockrendertype = state.getRenderType();
/*     */       
/*  54 */       if (enumblockrendertype == EnumBlockRenderType.INVISIBLE)
/*     */       {
/*  56 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  60 */       if (blockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
/*     */         
/*     */         try {
/*     */           
/*  64 */           state = state.getActualState(blockAccess, pos);
/*     */         }
/*  66 */         catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       switch (enumblockrendertype) {
/*     */         
/*     */         case MODEL:
/*  75 */           return this.blockModelRenderer.renderModel(blockAccess, getModelForState(state), state, pos, worldRendererIn, true);
/*     */         
/*     */         case null:
/*  78 */           return false;
/*     */         
/*     */         case LIQUID:
/*  81 */           return this.fluidRenderer.renderFluid(blockAccess, state, pos, worldRendererIn);
/*     */       } 
/*     */       
/*  84 */       return false;
/*     */ 
/*     */     
/*     */     }
/*  88 */     catch (Throwable throwable) {
/*     */       
/*  90 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
/*  91 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
/*  92 */       CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
/*  93 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelRenderer getBlockModelRenderer() {
/*  99 */     return this.blockModelRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getModelForState(IBlockState state) {
/* 104 */     return this.blockModelShapes.getModelForState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBlockBrightness(IBlockState state, float brightness) {
/* 110 */     EnumBlockRenderType enumblockrendertype = state.getRenderType();
/*     */     
/* 112 */     if (enumblockrendertype != EnumBlockRenderType.INVISIBLE) {
/*     */       IBakedModel ibakedmodel;
/* 114 */       switch (enumblockrendertype) {
/*     */         
/*     */         case MODEL:
/* 117 */           ibakedmodel = getModelForState(state);
/* 118 */           this.blockModelRenderer.renderModelBrightness(ibakedmodel, state, brightness, true);
/*     */           break;
/*     */         
/*     */         case null:
/* 122 */           this.chestRenderer.renderChestBrightness(state.getBlock(), brightness);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 131 */     this.fluidRenderer.initAtlasSprites();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BlockRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
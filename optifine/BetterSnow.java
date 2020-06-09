/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLever;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterSnow
/*     */ {
/*  29 */   private static IBakedModel modelSnowLayer = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  33 */     modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.SNOW_LAYER.getDefaultState());
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel getModelSnowLayer() {
/*  38 */     return modelSnowLayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState getStateSnowLayer() {
/*  43 */     return Blocks.SNOW_LAYER.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean shouldRender(IBlockAccess p_shouldRender_0_, IBlockState p_shouldRender_1_, BlockPos p_shouldRender_2_) {
/*  48 */     Block block = p_shouldRender_1_.getBlock();
/*  49 */     return !checkBlock(block, p_shouldRender_1_) ? false : hasSnowNeighbours(p_shouldRender_0_, p_shouldRender_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasSnowNeighbours(IBlockAccess p_hasSnowNeighbours_0_, BlockPos p_hasSnowNeighbours_1_) {
/*  54 */     Block block = Blocks.SNOW_LAYER;
/*  55 */     return (p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.north()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.south()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.west()).getBlock() != block && p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.east()).getBlock() != block) ? false : p_hasSnowNeighbours_0_.getBlockState(p_hasSnowNeighbours_1_.down()).isOpaqueCube();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkBlock(Block p_checkBlock_0_, IBlockState p_checkBlock_1_) {
/*  60 */     if (p_checkBlock_1_.isFullCube())
/*     */     {
/*  62 */       return false;
/*     */     }
/*  64 */     if (p_checkBlock_1_.isOpaqueCube())
/*     */     {
/*  66 */       return false;
/*     */     }
/*  68 */     if (p_checkBlock_0_ instanceof net.minecraft.block.BlockSnow)
/*     */     {
/*  70 */       return false;
/*     */     }
/*  72 */     if (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockBush) || (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockDoublePlant) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFlower) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockMushroom) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockSapling) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockTallGrass))) {
/*     */       
/*  74 */       if (!(p_checkBlock_0_ instanceof net.minecraft.block.BlockFence) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFenceGate) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockFlowerPot) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockPane) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockReed) && !(p_checkBlock_0_ instanceof net.minecraft.block.BlockWall)) {
/*     */         
/*  76 */         if (p_checkBlock_0_ instanceof net.minecraft.block.BlockRedstoneTorch && p_checkBlock_1_.getValue((IProperty)BlockTorch.FACING) == EnumFacing.UP)
/*     */         {
/*  78 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  82 */         if (p_checkBlock_0_ instanceof BlockLever) {
/*     */           
/*  84 */           Object object = p_checkBlock_1_.getValue((IProperty)BlockLever.FACING);
/*     */           
/*  86 */           if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z)
/*     */           {
/*  88 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  92 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  97 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BetterSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
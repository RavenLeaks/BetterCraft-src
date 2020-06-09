/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndGateway;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEndGateway
/*     */   extends BlockContainer
/*     */ {
/*     */   protected BlockEndGateway(Material p_i46687_1_) {
/*  24 */     super(p_i46687_1_);
/*  25 */     setLightLevel(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  33 */     return (TileEntity)new TileEntityEndGateway();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  38 */     IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
/*  39 */     Block block = iblockstate.getBlock();
/*  40 */     return (!iblockstate.isOpaqueCube() && block != Blocks.END_GATEWAY);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  46 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/*  72 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  74 */     if (tileentity instanceof TileEntityEndGateway) {
/*     */       
/*  76 */       int i = ((TileEntityEndGateway)tileentity).getParticleAmount();
/*     */       
/*  78 */       for (int j = 0; j < i; j++) {
/*     */         
/*  80 */         double d0 = (pos.getX() + rand.nextFloat());
/*  81 */         double d1 = (pos.getY() + rand.nextFloat());
/*  82 */         double d2 = (pos.getZ() + rand.nextFloat());
/*  83 */         double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
/*  84 */         double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
/*  85 */         double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
/*  86 */         int k = rand.nextInt(2) * 2 - 1;
/*     */         
/*  88 */         if (rand.nextBoolean()) {
/*     */           
/*  90 */           d2 = pos.getZ() + 0.5D + 0.25D * k;
/*  91 */           d5 = (rand.nextFloat() * 2.0F * k);
/*     */         }
/*     */         else {
/*     */           
/*  95 */           d0 = pos.getX() + 0.5D + 0.25D * k;
/*  96 */           d3 = (rand.nextFloat() * 2.0F * k);
/*     */         } 
/*     */         
/*  99 */         worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 106 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 114 */     return MapColor.BLACK;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 119 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockEndGateway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
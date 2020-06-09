/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDragonEgg extends Block {
/*  20 */   protected static final AxisAlignedBB DRAGON_EGG_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
/*     */ 
/*     */   
/*     */   public BlockDragonEgg() {
/*  24 */     super(Material.DRAGON_EGG, MapColor.BLACK);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  29 */     return DRAGON_EGG_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  37 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  47 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  52 */     checkFall(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkFall(World worldIn, BlockPos pos) {
/*  57 */     if (BlockFalling.canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
/*     */       
/*  59 */       int i = 32;
/*     */       
/*  61 */       if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
/*     */         
/*  63 */         worldIn.spawnEntityInWorld((Entity)new EntityFallingBlock(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), getDefaultState()));
/*     */       }
/*     */       else {
/*     */         
/*  67 */         worldIn.setBlockToAir(pos);
/*     */         
/*     */         BlockPos blockpos;
/*  70 */         for (blockpos = pos; BlockFalling.canFallThrough(worldIn.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  75 */         if (blockpos.getY() > 0)
/*     */         {
/*  77 */           worldIn.setBlockState(blockpos, getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  85 */     teleport(worldIn, pos);
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  91 */     teleport(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void teleport(World worldIn, BlockPos pos) {
/*  96 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  98 */     if (iblockstate.getBlock() == this)
/*     */     {
/* 100 */       for (int i = 0; i < 1000; i++) {
/*     */         
/* 102 */         BlockPos blockpos = pos.add(worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16), worldIn.rand.nextInt(8) - worldIn.rand.nextInt(8), worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16));
/*     */         
/* 104 */         if ((worldIn.getBlockState(blockpos).getBlock()).blockMaterial == Material.AIR) {
/*     */           
/* 106 */           if (worldIn.isRemote) {
/*     */             
/* 108 */             for (int j = 0; j < 128; j++)
/*     */             {
/* 110 */               double d0 = worldIn.rand.nextDouble();
/* 111 */               float f = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 112 */               float f1 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 113 */               float f2 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
/* 114 */               double d1 = blockpos.getX() + (pos.getX() - blockpos.getX()) * d0 + worldIn.rand.nextDouble() - 0.5D + 0.5D;
/* 115 */               double d2 = blockpos.getY() + (pos.getY() - blockpos.getY()) * d0 + worldIn.rand.nextDouble() - 0.5D;
/* 116 */               double d3 = blockpos.getZ() + (pos.getZ() - blockpos.getZ()) * d0 + worldIn.rand.nextDouble() - 0.5D + 0.5D;
/* 117 */               worldIn.spawnParticle(EnumParticleTypes.PORTAL, d1, d2, d3, f, f1, f2, new int[0]);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 122 */             worldIn.setBlockState(blockpos, iblockstate, 2);
/* 123 */             worldIn.setBlockToAir(pos);
/*     */           } 
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 137 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 160 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDragonEgg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
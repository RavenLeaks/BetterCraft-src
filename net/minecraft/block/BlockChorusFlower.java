/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockChorusFlower
/*     */   extends Block {
/*  27 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
/*     */ 
/*     */   
/*     */   protected BlockChorusFlower() {
/*  31 */     super(Material.PLANTS, MapColor.PURPLE);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  33 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  34 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  42 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  47 */     if (!canSurvive(worldIn, pos)) {
/*     */       
/*  49 */       worldIn.destroyBlock(pos, true);
/*     */     }
/*     */     else {
/*     */       
/*  53 */       BlockPos blockpos = pos.up();
/*     */       
/*  55 */       if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256) {
/*     */         
/*  57 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  59 */         if (i < 5 && rand.nextInt(1) == 0) {
/*     */           
/*  61 */           boolean flag = false;
/*  62 */           boolean flag1 = false;
/*  63 */           IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  64 */           Block block = iblockstate.getBlock();
/*     */           
/*  66 */           if (block == Blocks.END_STONE) {
/*     */             
/*  68 */             flag = true;
/*     */           }
/*  70 */           else if (block == Blocks.CHORUS_PLANT) {
/*     */             
/*  72 */             int j = 1;
/*     */             
/*  74 */             for (int k = 0; k < 4; k++) {
/*     */               
/*  76 */               Block block1 = worldIn.getBlockState(pos.down(j + 1)).getBlock();
/*     */               
/*  78 */               if (block1 != Blocks.CHORUS_PLANT) {
/*     */                 
/*  80 */                 if (block1 == Blocks.END_STONE)
/*     */                 {
/*  82 */                   flag1 = true;
/*     */                 }
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/*  88 */               j++;
/*     */             } 
/*     */             
/*  91 */             int i1 = 4;
/*     */             
/*  93 */             if (flag1)
/*     */             {
/*  95 */               i1++;
/*     */             }
/*     */             
/*  98 */             if (j < 2 || rand.nextInt(i1) >= j)
/*     */             {
/* 100 */               flag = true;
/*     */             }
/*     */           }
/* 103 */           else if (iblockstate.getMaterial() == Material.AIR) {
/*     */             
/* 105 */             flag = true;
/*     */           } 
/*     */           
/* 108 */           if (flag && areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null) && worldIn.isAirBlock(pos.up(2))) {
/*     */             
/* 110 */             worldIn.setBlockState(pos, Blocks.CHORUS_PLANT.getDefaultState(), 2);
/* 111 */             placeGrownFlower(worldIn, blockpos, i);
/*     */           }
/* 113 */           else if (i < 4) {
/*     */             
/* 115 */             int l = rand.nextInt(4);
/* 116 */             boolean flag2 = false;
/*     */             
/* 118 */             if (flag1)
/*     */             {
/* 120 */               l++;
/*     */             }
/*     */             
/* 123 */             for (int j1 = 0; j1 < l; j1++) {
/*     */               
/* 125 */               EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/* 126 */               BlockPos blockpos1 = pos.offset(enumfacing);
/*     */               
/* 128 */               if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
/*     */                 
/* 130 */                 placeGrownFlower(worldIn, blockpos1, i + 1);
/* 131 */                 flag2 = true;
/*     */               } 
/*     */             } 
/*     */             
/* 135 */             if (flag2)
/*     */             {
/* 137 */               worldIn.setBlockState(pos, Blocks.CHORUS_PLANT.getDefaultState(), 2);
/*     */             }
/*     */             else
/*     */             {
/* 141 */               placeDeadFlower(worldIn, pos);
/*     */             }
/*     */           
/* 144 */           } else if (i == 4) {
/*     */             
/* 146 */             placeDeadFlower(worldIn, pos);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeGrownFlower(World p_185602_1_, BlockPos p_185602_2_, int p_185602_3_) {
/* 155 */     p_185602_1_.setBlockState(p_185602_2_, getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(p_185602_3_)), 2);
/* 156 */     p_185602_1_.playEvent(1033, p_185602_2_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeDeadFlower(World p_185605_1_, BlockPos p_185605_2_) {
/* 161 */     p_185605_1_.setBlockState(p_185605_2_, getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(5)), 2);
/* 162 */     p_185605_1_.playEvent(1034, p_185605_2_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean areAllNeighborsEmpty(World p_185604_0_, BlockPos p_185604_1_, EnumFacing p_185604_2_) {
/* 167 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 169 */       if (enumfacing != p_185604_2_ && !p_185604_0_.isAirBlock(p_185604_1_.offset(enumfacing)))
/*     */       {
/* 171 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 193 */     return (super.canPlaceBlockAt(worldIn, pos) && canSurvive(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 203 */     if (!canSurvive(worldIn, pos))
/*     */     {
/* 205 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(World worldIn, BlockPos pos) {
/* 211 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 212 */     Block block = iblockstate.getBlock();
/*     */     
/* 214 */     if (block != Blocks.CHORUS_PLANT && block != Blocks.END_STONE) {
/*     */       
/* 216 */       if (iblockstate.getMaterial() == Material.AIR) {
/*     */         
/* 218 */         int i = 0;
/*     */         
/* 220 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */           
/* 222 */           IBlockState iblockstate1 = worldIn.getBlockState(pos.offset(enumfacing));
/* 223 */           Block block1 = iblockstate1.getBlock();
/*     */           
/* 225 */           if (block1 == Blocks.CHORUS_PLANT) {
/*     */             
/* 227 */             i++; continue;
/*     */           } 
/* 229 */           if (iblockstate1.getMaterial() != Material.AIR)
/*     */           {
/* 231 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 235 */         return (i == 1);
/*     */       } 
/*     */ 
/*     */       
/* 239 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 250 */     super.harvestBlock(worldIn, player, pos, state, te, stack);
/* 251 */     spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/* 256 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 261 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 269 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 277 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 282 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void generatePlant(World worldIn, BlockPos pos, Random rand, int p_185603_3_) {
/* 287 */     worldIn.setBlockState(pos, Blocks.CHORUS_PLANT.getDefaultState(), 2);
/* 288 */     growTreeRecursive(worldIn, pos, rand, pos, p_185603_3_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void growTreeRecursive(World worldIn, BlockPos p_185601_1_, Random rand, BlockPos p_185601_3_, int p_185601_4_, int p_185601_5_) {
/* 293 */     int i = rand.nextInt(4) + 1;
/*     */     
/* 295 */     if (p_185601_5_ == 0)
/*     */     {
/* 297 */       i++;
/*     */     }
/*     */     
/* 300 */     for (int j = 0; j < i; j++) {
/*     */       
/* 302 */       BlockPos blockpos = p_185601_1_.up(j + 1);
/*     */       
/* 304 */       if (!areAllNeighborsEmpty(worldIn, blockpos, (EnumFacing)null)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 309 */       worldIn.setBlockState(blockpos, Blocks.CHORUS_PLANT.getDefaultState(), 2);
/*     */     } 
/*     */     
/* 312 */     boolean flag = false;
/*     */     
/* 314 */     if (p_185601_5_ < 4) {
/*     */       
/* 316 */       int l = rand.nextInt(4);
/*     */       
/* 318 */       if (p_185601_5_ == 0)
/*     */       {
/* 320 */         l++;
/*     */       }
/*     */       
/* 323 */       for (int k = 0; k < l; k++) {
/*     */         
/* 325 */         EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/* 326 */         BlockPos blockpos1 = p_185601_1_.up(i).offset(enumfacing);
/*     */         
/* 328 */         if (Math.abs(blockpos1.getX() - p_185601_3_.getX()) < p_185601_4_ && Math.abs(blockpos1.getZ() - p_185601_3_.getZ()) < p_185601_4_ && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down()) && areAllNeighborsEmpty(worldIn, blockpos1, enumfacing.getOpposite())) {
/*     */           
/* 330 */           flag = true;
/* 331 */           worldIn.setBlockState(blockpos1, Blocks.CHORUS_PLANT.getDefaultState(), 2);
/* 332 */           growTreeRecursive(worldIn, blockpos1, rand, p_185601_3_, p_185601_4_, p_185601_5_ + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     if (!flag)
/*     */     {
/* 339 */       worldIn.setBlockState(p_185601_1_.up(i), Blocks.CHORUS_FLOWER.getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(5)), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 345 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockChorusFlower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCocoa;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTrees extends WorldGenAbstractTree {
/*  21 */   private static final IBlockState DEFAULT_TRUNK = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  22 */   private static final IBlockState DEFAULT_LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   private final int minTreeHeight;
/*     */ 
/*     */   
/*     */   private final boolean vinesGrow;
/*     */ 
/*     */   
/*     */   private final IBlockState metaWood;
/*     */ 
/*     */   
/*     */   private final IBlockState metaLeaves;
/*     */ 
/*     */   
/*     */   public WorldGenTrees(boolean p_i2027_1_) {
/*  38 */     this(p_i2027_1_, 4, DEFAULT_TRUNK, DEFAULT_LEAF, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenTrees(boolean p_i46446_1_, int p_i46446_2_, IBlockState p_i46446_3_, IBlockState p_i46446_4_, boolean p_i46446_5_) {
/*  43 */     super(p_i46446_1_);
/*  44 */     this.minTreeHeight = p_i46446_2_;
/*  45 */     this.metaWood = p_i46446_3_;
/*  46 */     this.metaLeaves = p_i46446_4_;
/*  47 */     this.vinesGrow = p_i46446_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  52 */     int i = rand.nextInt(3) + this.minTreeHeight;
/*  53 */     boolean flag = true;
/*     */     
/*  55 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  57 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  59 */         int k = 1;
/*     */         
/*  61 */         if (j == position.getY())
/*     */         {
/*  63 */           k = 0;
/*     */         }
/*     */         
/*  66 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  68 */           k = 2;
/*     */         }
/*     */         
/*  71 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  73 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  75 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  77 */             if (j >= 0 && j < 256) {
/*     */               
/*  79 */               if (!canGrowInto(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(l, j, i1)).getBlock()))
/*     */               {
/*  81 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  86 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       if (!flag)
/*     */       {
/*  94 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  98 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/* 100 */       if ((block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND) && position.getY() < 256 - i - 1) {
/*     */         
/* 102 */         setDirtAt(worldIn, position.down());
/* 103 */         int k2 = 3;
/* 104 */         int l2 = 0;
/*     */         
/* 106 */         for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; i3++) {
/*     */           
/* 108 */           int i4 = i3 - position.getY() + i;
/* 109 */           int j1 = 1 - i4 / 2;
/*     */           
/* 111 */           for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; k1++) {
/*     */             
/* 113 */             int l1 = k1 - position.getX();
/*     */             
/* 115 */             for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; i2++) {
/*     */               
/* 117 */               int j2 = i2 - position.getZ();
/*     */               
/* 119 */               if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || (rand.nextInt(2) != 0 && i4 != 0)) {
/*     */                 
/* 121 */                 BlockPos blockpos = new BlockPos(k1, i3, i2);
/* 122 */                 Material material = worldIn.getBlockState(blockpos).getMaterial();
/*     */                 
/* 124 */                 if (material == Material.AIR || material == Material.LEAVES || material == Material.VINE)
/*     */                 {
/* 126 */                   setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 133 */         for (int j3 = 0; j3 < i; j3++) {
/*     */           
/* 135 */           Material material1 = worldIn.getBlockState(position.up(j3)).getMaterial();
/*     */           
/* 137 */           if (material1 == Material.AIR || material1 == Material.LEAVES || material1 == Material.VINE) {
/*     */             
/* 139 */             setBlockAndNotifyAdequately(worldIn, position.up(j3), this.metaWood);
/*     */             
/* 141 */             if (this.vinesGrow && j3 > 0) {
/*     */               
/* 143 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, j3, 0)))
/*     */               {
/* 145 */                 addVine(worldIn, position.add(-1, j3, 0), BlockVine.EAST);
/*     */               }
/*     */               
/* 148 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, j3, 0)))
/*     */               {
/* 150 */                 addVine(worldIn, position.add(1, j3, 0), BlockVine.WEST);
/*     */               }
/*     */               
/* 153 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, -1)))
/*     */               {
/* 155 */                 addVine(worldIn, position.add(0, j3, -1), BlockVine.SOUTH);
/*     */               }
/*     */               
/* 158 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, 1)))
/*     */               {
/* 160 */                 addVine(worldIn, position.add(0, j3, 1), BlockVine.NORTH);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 166 */         if (this.vinesGrow) {
/*     */           
/* 168 */           for (int k3 = position.getY() - 3 + i; k3 <= position.getY() + i; k3++) {
/*     */             
/* 170 */             int j4 = k3 - position.getY() + i;
/* 171 */             int k4 = 2 - j4 / 2;
/* 172 */             BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */             
/* 174 */             for (int l4 = position.getX() - k4; l4 <= position.getX() + k4; l4++) {
/*     */               
/* 176 */               for (int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; i5++) {
/*     */                 
/* 178 */                 blockpos$mutableblockpos1.setPos(l4, k3, i5);
/*     */                 
/* 180 */                 if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getMaterial() == Material.LEAVES) {
/*     */                   
/* 182 */                   BlockPos blockpos2 = blockpos$mutableblockpos1.west();
/* 183 */                   BlockPos blockpos3 = blockpos$mutableblockpos1.east();
/* 184 */                   BlockPos blockpos4 = blockpos$mutableblockpos1.north();
/* 185 */                   BlockPos blockpos1 = blockpos$mutableblockpos1.south();
/*     */                   
/* 187 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getMaterial() == Material.AIR)
/*     */                   {
/* 189 */                     addHangingVine(worldIn, blockpos2, BlockVine.EAST);
/*     */                   }
/*     */                   
/* 192 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getMaterial() == Material.AIR)
/*     */                   {
/* 194 */                     addHangingVine(worldIn, blockpos3, BlockVine.WEST);
/*     */                   }
/*     */                   
/* 197 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getMaterial() == Material.AIR)
/*     */                   {
/* 199 */                     addHangingVine(worldIn, blockpos4, BlockVine.SOUTH);
/*     */                   }
/*     */                   
/* 202 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getMaterial() == Material.AIR)
/*     */                   {
/* 204 */                     addHangingVine(worldIn, blockpos1, BlockVine.NORTH);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 211 */           if (rand.nextInt(5) == 0 && i > 5)
/*     */           {
/* 213 */             for (int l3 = 0; l3 < 2; l3++) {
/*     */               
/* 215 */               for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */                 
/* 217 */                 if (rand.nextInt(4 - l3) == 0) {
/*     */                   
/* 219 */                   EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 220 */                   placeCocoa(worldIn, rand.nextInt(3), position.add(enumfacing1.getFrontOffsetX(), i - 5 + l3, enumfacing1.getFrontOffsetZ()), enumfacing);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 227 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 231 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void placeCocoa(World worldIn, int p_181652_2_, BlockPos pos, EnumFacing side) {
/* 243 */     setBlockAndNotifyAdequately(worldIn, pos, Blocks.COCOA.getDefaultState().withProperty((IProperty)BlockCocoa.AGE, Integer.valueOf(p_181652_2_)).withProperty((IProperty)BlockCocoa.FACING, (Comparable)side));
/*     */   }
/*     */ 
/*     */   
/*     */   private void addVine(World worldIn, BlockPos pos, PropertyBool prop) {
/* 248 */     setBlockAndNotifyAdequately(worldIn, pos, Blocks.VINE.getDefaultState().withProperty((IProperty)prop, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void addHangingVine(World worldIn, BlockPos pos, PropertyBool prop) {
/* 253 */     addVine(worldIn, pos, prop);
/* 254 */     int i = 4;
/*     */     
/* 256 */     for (BlockPos blockpos = pos.down(); worldIn.getBlockState(blockpos).getMaterial() == Material.AIR && i > 0; i--) {
/*     */       
/* 258 */       addVine(worldIn, blockpos, prop);
/* 259 */       blockpos = blockpos.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenTrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockLog;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenBigTree extends WorldGenAbstractTree {
/*     */   private Random rand;
/*     */   private World world;
/*  20 */   private BlockPos basePos = BlockPos.ORIGIN;
/*     */   int heightLimit;
/*     */   int height;
/*  23 */   double heightAttenuation = 0.618D;
/*  24 */   double branchSlope = 0.381D;
/*  25 */   double scaleWidth = 1.0D;
/*  26 */   double leafDensity = 1.0D;
/*  27 */   int trunkSize = 1;
/*  28 */   int heightLimitLimit = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   int leafDistanceLimit = 4;
/*     */   
/*     */   List<FoliageCoordinates> foliageCoords;
/*     */   
/*     */   public WorldGenBigTree(boolean notify) {
/*  38 */     super(notify);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeList() {
/*  46 */     this.height = (int)(this.heightLimit * this.heightAttenuation);
/*     */     
/*  48 */     if (this.height >= this.heightLimit)
/*     */     {
/*  50 */       this.height = this.heightLimit - 1;
/*     */     }
/*     */     
/*  53 */     int i = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
/*     */     
/*  55 */     if (i < 1)
/*     */     {
/*  57 */       i = 1;
/*     */     }
/*     */     
/*  60 */     int j = this.basePos.getY() + this.height;
/*  61 */     int k = this.heightLimit - this.leafDistanceLimit;
/*  62 */     this.foliageCoords = Lists.newArrayList();
/*  63 */     this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));
/*     */     
/*  65 */     for (; k >= 0; k--) {
/*     */       
/*  67 */       float f = layerSize(k);
/*     */       
/*  69 */       if (f >= 0.0F)
/*     */       {
/*  71 */         for (int l = 0; l < i; l++) {
/*     */           
/*  73 */           double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
/*  74 */           double d1 = (this.rand.nextFloat() * 2.0F) * Math.PI;
/*  75 */           double d2 = d0 * Math.sin(d1) + 0.5D;
/*  76 */           double d3 = d0 * Math.cos(d1) + 0.5D;
/*  77 */           BlockPos blockpos = this.basePos.add(d2, (k - 1), d3);
/*  78 */           BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
/*     */           
/*  80 */           if (checkBlockLine(blockpos, blockpos1) == -1) {
/*     */             
/*  82 */             int i1 = this.basePos.getX() - blockpos.getX();
/*  83 */             int j1 = this.basePos.getZ() - blockpos.getZ();
/*  84 */             double d4 = blockpos.getY() - Math.sqrt((i1 * i1 + j1 * j1)) * this.branchSlope;
/*  85 */             int k1 = (d4 > j) ? j : (int)d4;
/*  86 */             BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());
/*     */             
/*  88 */             if (checkBlockLine(blockpos2, blockpos) == -1)
/*     */             {
/*  90 */               this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void crosSection(BlockPos pos, float p_181631_2_, IBlockState p_181631_3_) {
/* 100 */     int i = (int)(p_181631_2_ + 0.618D);
/*     */     
/* 102 */     for (int j = -i; j <= i; j++) {
/*     */       
/* 104 */       for (int k = -i; k <= i; k++) {
/*     */         
/* 106 */         if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= (p_181631_2_ * p_181631_2_)) {
/*     */           
/* 108 */           BlockPos blockpos = pos.add(j, 0, k);
/* 109 */           Material material = this.world.getBlockState(blockpos).getMaterial();
/*     */           
/* 111 */           if (material == Material.AIR || material == Material.LEAVES)
/*     */           {
/* 113 */             setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float layerSize(int y) {
/* 125 */     if (y < this.heightLimit * 0.3F)
/*     */     {
/* 127 */       return -1.0F;
/*     */     }
/*     */ 
/*     */     
/* 131 */     float f = this.heightLimit / 2.0F;
/* 132 */     float f1 = f - y;
/* 133 */     float f2 = MathHelper.sqrt(f * f - f1 * f1);
/*     */     
/* 135 */     if (f1 == 0.0F) {
/*     */       
/* 137 */       f2 = f;
/*     */     }
/* 139 */     else if (Math.abs(f1) >= f) {
/*     */       
/* 141 */       return 0.0F;
/*     */     } 
/*     */     
/* 144 */     return f2 * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   float leafSize(int y) {
/* 150 */     if (y >= 0 && y < this.leafDistanceLimit)
/*     */     {
/* 152 */       return (y != 0 && y != this.leafDistanceLimit - 1) ? 3.0F : 2.0F;
/*     */     }
/*     */ 
/*     */     
/* 156 */     return -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNode(BlockPos pos) {
/* 165 */     for (int i = 0; i < this.leafDistanceLimit; i++)
/*     */     {
/* 167 */       crosSection(pos.up(i), leafSize(i), Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void limb(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_) {
/* 173 */     BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
/* 174 */     int i = getGreatestDistance(blockpos);
/* 175 */     float f = blockpos.getX() / i;
/* 176 */     float f1 = blockpos.getY() / i;
/* 177 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 179 */     for (int j = 0; j <= i; j++) {
/*     */       
/* 181 */       BlockPos blockpos1 = p_175937_1_.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/* 182 */       BlockLog.EnumAxis blocklog$enumaxis = getLogAxis(p_175937_1_, blockpos1);
/* 183 */       setBlockAndNotifyAdequately(this.world, blockpos1, p_175937_3_.getDefaultState().withProperty((IProperty)BlockLog.LOG_AXIS, (Comparable)blocklog$enumaxis));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getGreatestDistance(BlockPos posIn) {
/* 192 */     int i = MathHelper.abs(posIn.getX());
/* 193 */     int j = MathHelper.abs(posIn.getY());
/* 194 */     int k = MathHelper.abs(posIn.getZ());
/*     */     
/* 196 */     if (k > i && k > j)
/*     */     {
/* 198 */       return k;
/*     */     }
/*     */ 
/*     */     
/* 202 */     return (j > i) ? j : i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockLog.EnumAxis getLogAxis(BlockPos p_175938_1_, BlockPos p_175938_2_) {
/* 208 */     BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
/* 209 */     int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
/* 210 */     int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
/* 211 */     int k = Math.max(i, j);
/*     */     
/* 213 */     if (k > 0)
/*     */     {
/* 215 */       if (i == k) {
/*     */         
/* 217 */         blocklog$enumaxis = BlockLog.EnumAxis.X;
/*     */       }
/* 219 */       else if (j == k) {
/*     */         
/* 221 */         blocklog$enumaxis = BlockLog.EnumAxis.Z;
/*     */       } 
/*     */     }
/*     */     
/* 225 */     return blocklog$enumaxis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeaves() {
/* 233 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.foliageCoords)
/*     */     {
/* 235 */       generateLeafNode(worldgenbigtree$foliagecoordinates);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean leafNodeNeedsBase(int p_76493_1_) {
/* 244 */     return (p_76493_1_ >= this.heightLimit * 0.2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateTrunk() {
/* 253 */     BlockPos blockpos = this.basePos;
/* 254 */     BlockPos blockpos1 = this.basePos.up(this.height);
/* 255 */     Block block = Blocks.LOG;
/* 256 */     limb(blockpos, blockpos1, block);
/*     */     
/* 258 */     if (this.trunkSize == 2) {
/*     */       
/* 260 */       limb(blockpos.east(), blockpos1.east(), block);
/* 261 */       limb(blockpos.east().south(), blockpos1.east().south(), block);
/* 262 */       limb(blockpos.south(), blockpos1.south(), block);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeBases() {
/* 271 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.foliageCoords) {
/*     */       
/* 273 */       int i = worldgenbigtree$foliagecoordinates.getBranchBase();
/* 274 */       BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());
/*     */       
/* 276 */       if (!blockpos.equals(worldgenbigtree$foliagecoordinates) && leafNodeNeedsBase(i - this.basePos.getY()))
/*     */       {
/* 278 */         limb(blockpos, worldgenbigtree$foliagecoordinates, Blocks.LOG);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
/* 289 */     BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
/* 290 */     int i = getGreatestDistance(blockpos);
/* 291 */     float f = blockpos.getX() / i;
/* 292 */     float f1 = blockpos.getY() / i;
/* 293 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 295 */     if (i == 0)
/*     */     {
/* 297 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 301 */     for (int j = 0; j <= i; j++) {
/*     */       
/* 303 */       BlockPos blockpos1 = posOne.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/*     */       
/* 305 */       if (!canGrowInto(this.world.getBlockState(blockpos1).getBlock()))
/*     */       {
/* 307 */         return j;
/*     */       }
/*     */     } 
/*     */     
/* 311 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDecorationDefaults() {
/* 317 */     this.leafDistanceLimit = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 322 */     this.world = worldIn;
/* 323 */     this.basePos = position;
/* 324 */     this.rand = new Random(rand.nextLong());
/*     */     
/* 326 */     if (this.heightLimit == 0)
/*     */     {
/* 328 */       this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
/*     */     }
/*     */     
/* 331 */     if (!validTreeLocation())
/*     */     {
/* 333 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 337 */     generateLeafNodeList();
/* 338 */     generateLeaves();
/* 339 */     generateTrunk();
/* 340 */     generateLeafNodeBases();
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validTreeLocation() {
/* 351 */     Block block = this.world.getBlockState(this.basePos.down()).getBlock();
/*     */     
/* 353 */     if (block != Blocks.DIRT && block != Blocks.GRASS && block != Blocks.FARMLAND)
/*     */     {
/* 355 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 359 */     int i = checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));
/*     */     
/* 361 */     if (i == -1)
/*     */     {
/* 363 */       return true;
/*     */     }
/* 365 */     if (i < 6)
/*     */     {
/* 367 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 371 */     this.heightLimit = i;
/* 372 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static class FoliageCoordinates
/*     */     extends BlockPos
/*     */   {
/*     */     private final int branchBase;
/*     */ 
/*     */     
/*     */     public FoliageCoordinates(BlockPos pos, int p_i45635_2_) {
/* 383 */       super(pos.getX(), pos.getY(), pos.getZ());
/* 384 */       this.branchBase = p_i45635_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getBranchBase() {
/* 389 */       return this.branchBase;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenBigTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
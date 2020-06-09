/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFire
/*     */   extends Block
/*     */ {
/*  29 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*  30 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  31 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  32 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  33 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  34 */   public static final PropertyBool UPPER = PropertyBool.create("up");
/*  35 */   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
/*  36 */   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  44 */     return (!worldIn.getBlockState(pos.down()).isFullyOpaque() && !Blocks.FIRE.canCatchFire(worldIn, pos.down())) ? state.withProperty((IProperty)NORTH, Boolean.valueOf(canCatchFire(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canCatchFire(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canCatchFire(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canCatchFire(worldIn, pos.west()))).withProperty((IProperty)UPPER, Boolean.valueOf(canCatchFire(worldIn, pos.up()))) : getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockFire() {
/*  49 */     super(Material.FIRE);
/*  50 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)UPPER, Boolean.valueOf(false)));
/*  51 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  56 */     Blocks.FIRE.setFireInfo(Blocks.PLANKS, 5, 20);
/*  57 */     Blocks.FIRE.setFireInfo(Blocks.DOUBLE_WOODEN_SLAB, 5, 20);
/*  58 */     Blocks.FIRE.setFireInfo(Blocks.WOODEN_SLAB, 5, 20);
/*  59 */     Blocks.FIRE.setFireInfo(Blocks.OAK_FENCE_GATE, 5, 20);
/*  60 */     Blocks.FIRE.setFireInfo(Blocks.SPRUCE_FENCE_GATE, 5, 20);
/*  61 */     Blocks.FIRE.setFireInfo(Blocks.BIRCH_FENCE_GATE, 5, 20);
/*  62 */     Blocks.FIRE.setFireInfo(Blocks.JUNGLE_FENCE_GATE, 5, 20);
/*  63 */     Blocks.FIRE.setFireInfo(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
/*  64 */     Blocks.FIRE.setFireInfo(Blocks.ACACIA_FENCE_GATE, 5, 20);
/*  65 */     Blocks.FIRE.setFireInfo(Blocks.OAK_FENCE, 5, 20);
/*  66 */     Blocks.FIRE.setFireInfo(Blocks.SPRUCE_FENCE, 5, 20);
/*  67 */     Blocks.FIRE.setFireInfo(Blocks.BIRCH_FENCE, 5, 20);
/*  68 */     Blocks.FIRE.setFireInfo(Blocks.JUNGLE_FENCE, 5, 20);
/*  69 */     Blocks.FIRE.setFireInfo(Blocks.DARK_OAK_FENCE, 5, 20);
/*  70 */     Blocks.FIRE.setFireInfo(Blocks.ACACIA_FENCE, 5, 20);
/*  71 */     Blocks.FIRE.setFireInfo(Blocks.OAK_STAIRS, 5, 20);
/*  72 */     Blocks.FIRE.setFireInfo(Blocks.BIRCH_STAIRS, 5, 20);
/*  73 */     Blocks.FIRE.setFireInfo(Blocks.SPRUCE_STAIRS, 5, 20);
/*  74 */     Blocks.FIRE.setFireInfo(Blocks.JUNGLE_STAIRS, 5, 20);
/*  75 */     Blocks.FIRE.setFireInfo(Blocks.ACACIA_STAIRS, 5, 20);
/*  76 */     Blocks.FIRE.setFireInfo(Blocks.DARK_OAK_STAIRS, 5, 20);
/*  77 */     Blocks.FIRE.setFireInfo(Blocks.LOG, 5, 5);
/*  78 */     Blocks.FIRE.setFireInfo(Blocks.LOG2, 5, 5);
/*  79 */     Blocks.FIRE.setFireInfo(Blocks.LEAVES, 30, 60);
/*  80 */     Blocks.FIRE.setFireInfo(Blocks.LEAVES2, 30, 60);
/*  81 */     Blocks.FIRE.setFireInfo(Blocks.BOOKSHELF, 30, 20);
/*  82 */     Blocks.FIRE.setFireInfo(Blocks.TNT, 15, 100);
/*  83 */     Blocks.FIRE.setFireInfo(Blocks.TALLGRASS, 60, 100);
/*  84 */     Blocks.FIRE.setFireInfo(Blocks.DOUBLE_PLANT, 60, 100);
/*  85 */     Blocks.FIRE.setFireInfo(Blocks.YELLOW_FLOWER, 60, 100);
/*  86 */     Blocks.FIRE.setFireInfo(Blocks.RED_FLOWER, 60, 100);
/*  87 */     Blocks.FIRE.setFireInfo(Blocks.DEADBUSH, 60, 100);
/*  88 */     Blocks.FIRE.setFireInfo(Blocks.WOOL, 30, 60);
/*  89 */     Blocks.FIRE.setFireInfo(Blocks.VINE, 15, 100);
/*  90 */     Blocks.FIRE.setFireInfo(Blocks.COAL_BLOCK, 5, 5);
/*  91 */     Blocks.FIRE.setFireInfo(Blocks.HAY_BLOCK, 60, 20);
/*  92 */     Blocks.FIRE.setFireInfo(Blocks.CARPET, 60, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFireInfo(Block blockIn, int encouragement, int flammability) {
/*  97 */     this.encouragements.put(blockIn, Integer.valueOf(encouragement));
/*  98 */     this.flammabilities.put(blockIn, Integer.valueOf(flammability));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 104 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 133 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 138 */     if (worldIn.getGameRules().getBoolean("doFireTick")) {
/*     */       
/* 140 */       if (!canPlaceBlockAt(worldIn, pos))
/*     */       {
/* 142 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       
/* 145 */       Block block = worldIn.getBlockState(pos.down()).getBlock();
/* 146 */       boolean flag = !(block != Blocks.NETHERRACK && block != Blocks.MAGMA);
/*     */       
/* 148 */       if (worldIn.provider instanceof net.minecraft.world.WorldProviderEnd && block == Blocks.BEDROCK)
/*     */       {
/* 150 */         flag = true;
/*     */       }
/*     */       
/* 153 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/* 155 */       if (!flag && worldIn.isRaining() && canDie(worldIn, pos) && rand.nextFloat() < 0.2F + i * 0.03F) {
/*     */         
/* 157 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 161 */         if (i < 15) {
/*     */           
/* 163 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
/* 164 */           worldIn.setBlockState(pos, state, 4);
/*     */         } 
/*     */         
/* 167 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + rand.nextInt(10));
/*     */         
/* 169 */         if (!flag) {
/*     */           
/* 171 */           if (!canNeighborCatchFire(worldIn, pos)) {
/*     */             
/* 173 */             if (!worldIn.getBlockState(pos.down()).isFullyOpaque() || i > 3)
/*     */             {
/* 175 */               worldIn.setBlockToAir(pos);
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 181 */           if (!canCatchFire((IBlockAccess)worldIn, pos.down()) && i == 15 && rand.nextInt(4) == 0) {
/*     */             
/* 183 */             worldIn.setBlockToAir(pos);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 188 */         boolean flag1 = worldIn.isBlockinHighHumidity(pos);
/* 189 */         int j = 0;
/*     */         
/* 191 */         if (flag1)
/*     */         {
/* 193 */           j = -50;
/*     */         }
/*     */         
/* 196 */         catchOnFire(worldIn, pos.east(), 300 + j, rand, i);
/* 197 */         catchOnFire(worldIn, pos.west(), 300 + j, rand, i);
/* 198 */         catchOnFire(worldIn, pos.down(), 250 + j, rand, i);
/* 199 */         catchOnFire(worldIn, pos.up(), 250 + j, rand, i);
/* 200 */         catchOnFire(worldIn, pos.north(), 300 + j, rand, i);
/* 201 */         catchOnFire(worldIn, pos.south(), 300 + j, rand, i);
/*     */         
/* 203 */         for (int k = -1; k <= 1; k++) {
/*     */           
/* 205 */           for (int l = -1; l <= 1; l++) {
/*     */             
/* 207 */             for (int i1 = -1; i1 <= 4; i1++) {
/*     */               
/* 209 */               if (k != 0 || i1 != 0 || l != 0) {
/*     */                 
/* 211 */                 int j1 = 100;
/*     */                 
/* 213 */                 if (i1 > 1)
/*     */                 {
/* 215 */                   j1 += (i1 - 1) * 100;
/*     */                 }
/*     */                 
/* 218 */                 BlockPos blockpos = pos.add(k, i1, l);
/* 219 */                 int k1 = getNeighborEncouragement(worldIn, blockpos);
/*     */                 
/* 221 */                 if (k1 > 0) {
/*     */                   
/* 223 */                   int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);
/*     */                   
/* 225 */                   if (flag1)
/*     */                   {
/* 227 */                     l1 /= 2;
/*     */                   }
/*     */                   
/* 230 */                   if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !canDie(worldIn, blockpos))) {
/*     */                     
/* 232 */                     int i2 = i + rand.nextInt(5) / 4;
/*     */                     
/* 234 */                     if (i2 > 15)
/*     */                     {
/* 236 */                       i2 = 15;
/*     */                     }
/*     */                     
/* 239 */                     worldIn.setBlockState(blockpos, state.withProperty((IProperty)AGE, Integer.valueOf(i2)), 3);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDie(World worldIn, BlockPos pos) {
/* 252 */     return !(!worldIn.isRainingAt(pos) && !worldIn.isRainingAt(pos.west()) && !worldIn.isRainingAt(pos.east()) && !worldIn.isRainingAt(pos.north()) && !worldIn.isRainingAt(pos.south()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdates() {
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getFlammability(Block blockIn) {
/* 262 */     Integer integer = this.flammabilities.get(blockIn);
/* 263 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEncouragement(Block blockIn) {
/* 268 */     Integer integer = this.encouragements.get(blockIn);
/* 269 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void catchOnFire(World worldIn, BlockPos pos, int chance, Random random, int age) {
/* 274 */     int i = getFlammability(worldIn.getBlockState(pos).getBlock());
/*     */     
/* 276 */     if (random.nextInt(chance) < i) {
/*     */       
/* 278 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 280 */       if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
/*     */         
/* 282 */         int j = age + random.nextInt(5) / 4;
/*     */         
/* 284 */         if (j > 15)
/*     */         {
/* 286 */           j = 15;
/*     */         }
/*     */         
/* 289 */         worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(j)), 3);
/*     */       }
/*     */       else {
/*     */         
/* 293 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 296 */       if (iblockstate.getBlock() == Blocks.TNT)
/*     */       {
/* 298 */         Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true))); } 
/*     */     } 
/*     */   }
/*     */   private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 305 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 307 */       if (canCatchFire((IBlockAccess)worldIn, pos.offset(enumfacing)))
/*     */       {
/* 309 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 313 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNeighborEncouragement(World worldIn, BlockPos pos) {
/* 318 */     if (!worldIn.isAirBlock(pos))
/*     */     {
/* 320 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 324 */     int i = 0; byte b; int j;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 326 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 328 */       i = Math.max(getEncouragement(worldIn.getBlockState(pos.offset(enumfacing)).getBlock()), i);
/*     */       b++; }
/*     */     
/* 331 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 341 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
/* 349 */     return (getEncouragement(worldIn.getBlockState(pos).getBlock()) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 354 */     return !(!worldIn.getBlockState(pos.down()).isFullyOpaque() && !canNeighborCatchFire(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 364 */     if (!worldIn.getBlockState(pos.down()).isFullyOpaque() && !canNeighborCatchFire(worldIn, pos))
/*     */     {
/* 366 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 375 */     if (worldIn.provider.getDimensionType().getId() > 0 || !Blocks.PORTAL.trySpawnPortal(worldIn, pos))
/*     */     {
/* 377 */       if (!worldIn.getBlockState(pos.down()).isFullyOpaque() && !canNeighborCatchFire(worldIn, pos)) {
/*     */         
/* 379 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 383 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + worldIn.rand.nextInt(10));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 390 */     if (rand.nextInt(24) == 0)
/*     */     {
/* 392 */       worldIn.playSound((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
/*     */     }
/*     */     
/* 395 */     if (!worldIn.getBlockState(pos.down()).isFullyOpaque() && !Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.down())) {
/*     */       
/* 397 */       if (Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.west()))
/*     */       {
/* 399 */         for (int j = 0; j < 2; j++) {
/*     */           
/* 401 */           double d3 = pos.getX() + rand.nextDouble() * 0.10000000149011612D;
/* 402 */           double d8 = pos.getY() + rand.nextDouble();
/* 403 */           double d13 = pos.getZ() + rand.nextDouble();
/* 404 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 408 */       if (Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.east()))
/*     */       {
/* 410 */         for (int k = 0; k < 2; k++) {
/*     */           
/* 412 */           double d4 = (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 413 */           double d9 = pos.getY() + rand.nextDouble();
/* 414 */           double d14 = pos.getZ() + rand.nextDouble();
/* 415 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 419 */       if (Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.north()))
/*     */       {
/* 421 */         for (int l = 0; l < 2; l++) {
/*     */           
/* 423 */           double d5 = pos.getX() + rand.nextDouble();
/* 424 */           double d10 = pos.getY() + rand.nextDouble();
/* 425 */           double d15 = pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
/* 426 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 430 */       if (Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.south()))
/*     */       {
/* 432 */         for (int i1 = 0; i1 < 2; i1++) {
/*     */           
/* 434 */           double d6 = pos.getX() + rand.nextDouble();
/* 435 */           double d11 = pos.getY() + rand.nextDouble();
/* 436 */           double d16 = (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 437 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 441 */       if (Blocks.FIRE.canCatchFire((IBlockAccess)worldIn, pos.up()))
/*     */       {
/* 443 */         for (int j1 = 0; j1 < 2; j1++)
/*     */         {
/* 445 */           double d7 = pos.getX() + rand.nextDouble();
/* 446 */           double d12 = (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 447 */           double d17 = pos.getZ() + rand.nextDouble();
/* 448 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 454 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 456 */         double d0 = pos.getX() + rand.nextDouble();
/* 457 */         double d1 = pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
/* 458 */         double d2 = pos.getZ() + rand.nextDouble();
/* 459 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 469 */     return MapColor.TNT;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 474 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 482 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 490 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 495 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)UPPER });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 500 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
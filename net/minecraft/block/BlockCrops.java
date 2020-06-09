/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  21 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*  22 */   private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*     */ 
/*     */   
/*     */   protected BlockCrops() {
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)getAgeProperty(), Integer.valueOf(0)));
/*  27 */     setTickRandomly(true);
/*  28 */     setCreativeTab(null);
/*  29 */     setHardness(0.0F);
/*  30 */     setSoundType(SoundType.PLANT);
/*  31 */     disableStats();
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  36 */     return CROPS_AABB[((Integer)state.getValue((IProperty)getAgeProperty())).intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSustainBush(IBlockState state) {
/*  44 */     return (state.getBlock() == Blocks.FARMLAND);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PropertyInteger getAgeProperty() {
/*  49 */     return AGE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxAge() {
/*  54 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getAge(IBlockState state) {
/*  59 */     return ((Integer)state.getValue((IProperty)getAgeProperty())).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState withAge(int age) {
/*  64 */     return getDefaultState().withProperty((IProperty)getAgeProperty(), Integer.valueOf(age));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMaxAge(IBlockState state) {
/*  69 */     return (((Integer)state.getValue((IProperty)getAgeProperty())).intValue() >= getMaxAge());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  74 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  76 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  78 */       int i = getAge(state);
/*     */       
/*  80 */       if (i < getMaxAge()) {
/*     */         
/*  82 */         float f = getGrowthChance(this, worldIn, pos);
/*     */         
/*  84 */         if (rand.nextInt((int)(25.0F / f) + 1) == 0)
/*     */         {
/*  86 */           worldIn.setBlockState(pos, withAge(i + 1), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     int i = getAge(state) + getBonemealAgeIncrease(worldIn);
/*  95 */     int j = getMaxAge();
/*     */     
/*  97 */     if (i > j)
/*     */     {
/*  99 */       i = j;
/*     */     }
/*     */     
/* 102 */     worldIn.setBlockState(pos, withAge(i), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getBonemealAgeIncrease(World worldIn) {
/* 107 */     return MathHelper.getInt(worldIn.rand, 2, 5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
/* 112 */     float f = 1.0F;
/* 113 */     BlockPos blockpos = pos.down();
/*     */     
/* 115 */     for (int i = -1; i <= 1; i++) {
/*     */       
/* 117 */       for (int j = -1; j <= 1; j++) {
/*     */         
/* 119 */         float f1 = 0.0F;
/* 120 */         IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
/*     */         
/* 122 */         if (iblockstate.getBlock() == Blocks.FARMLAND) {
/*     */           
/* 124 */           f1 = 1.0F;
/*     */           
/* 126 */           if (((Integer)iblockstate.getValue((IProperty)BlockFarmland.MOISTURE)).intValue() > 0)
/*     */           {
/* 128 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/* 132 */         if (i != 0 || j != 0)
/*     */         {
/* 134 */           f1 /= 4.0F;
/*     */         }
/*     */         
/* 137 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     BlockPos blockpos1 = pos.north();
/* 142 */     BlockPos blockpos2 = pos.south();
/* 143 */     BlockPos blockpos3 = pos.west();
/* 144 */     BlockPos blockpos4 = pos.east();
/* 145 */     boolean flag = !(blockIn != worldIn.getBlockState(blockpos3).getBlock() && blockIn != worldIn.getBlockState(blockpos4).getBlock());
/* 146 */     boolean flag1 = !(blockIn != worldIn.getBlockState(blockpos1).getBlock() && blockIn != worldIn.getBlockState(blockpos2).getBlock());
/*     */     
/* 148 */     if (flag && flag1) {
/*     */       
/* 150 */       f /= 2.0F;
/*     */     }
/*     */     else {
/*     */       
/* 154 */       boolean flag2 = !(blockIn != worldIn.getBlockState(blockpos3.north()).getBlock() && blockIn != worldIn.getBlockState(blockpos4.north()).getBlock() && blockIn != worldIn.getBlockState(blockpos4.south()).getBlock() && blockIn != worldIn.getBlockState(blockpos3.south()).getBlock());
/*     */       
/* 156 */       if (flag2)
/*     */       {
/* 158 */         f /= 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 167 */     return ((worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && canSustainBush(worldIn.getBlockState(pos.down())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getSeed() {
/* 172 */     return Items.WHEAT_SEEDS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getCrop() {
/* 177 */     return Items.WHEAT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 185 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     
/* 187 */     if (!worldIn.isRemote) {
/*     */       
/* 189 */       int i = getAge(state);
/*     */       
/* 191 */       if (i >= getMaxAge()) {
/*     */         
/* 193 */         int j = 3 + fortune;
/*     */         
/* 195 */         for (int k = 0; k < j; k++) {
/*     */           
/* 197 */           if (worldIn.rand.nextInt(2 * getMaxAge()) <= i)
/*     */           {
/* 199 */             spawnAsEntity(worldIn, pos, new ItemStack(getSeed()));
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
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 211 */     return isMaxAge(state) ? getCrop() : getSeed();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 216 */     return new ItemStack(getSeed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 224 */     return !isMaxAge(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 234 */     grow(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 242 */     return withAge(meta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 250 */     return getAge(state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 255 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCrops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
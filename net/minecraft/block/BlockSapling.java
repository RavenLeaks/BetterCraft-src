/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBirchTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockSapling
/*     */   extends BlockBush implements IGrowable {
/*  30 */   public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
/*  31 */   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
/*  32 */   protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
/*     */ 
/*     */   
/*     */   protected BlockSapling() {
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.OAK).withProperty((IProperty)STAGE, Integer.valueOf(0)));
/*  37 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  42 */     return SAPLING_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  50 */     return I18n.translateToLocal(String.valueOf(getUnlocalizedName()) + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  55 */     if (!worldIn.isRemote) {
/*     */       
/*  57 */       super.updateTick(worldIn, pos, state, rand);
/*     */       
/*  59 */       if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
/*     */       {
/*  61 */         grow(worldIn, pos, state, rand);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  68 */     if (((Integer)state.getValue((IProperty)STAGE)).intValue() == 0) {
/*     */       
/*  70 */       worldIn.setBlockState(pos, state.cycleProperty((IProperty)STAGE), 4);
/*     */     }
/*     */     else {
/*     */       
/*  74 */       generateTree(worldIn, pos, state, rand);
/*     */     }  } public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenBirchTree worldGenBirchTree;
/*     */     WorldGenSavannaTree worldGenSavannaTree;
/*     */     WorldGenCanopyTree worldGenCanopyTree;
/*     */     IBlockState iblockstate, iblockstate1;
/*  80 */     WorldGenerator worldgenerator = (rand.nextInt(10) == 0) ? (WorldGenerator)new WorldGenBigTree(true) : (WorldGenerator)new WorldGenTrees(true);
/*  81 */     int i = 0;
/*  82 */     int j = 0;
/*  83 */     boolean flag = false;
/*     */     
/*  85 */     switch ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)) {
/*     */ 
/*     */       
/*     */       case SPRUCE:
/*  89 */         label68: for (i = 0; i >= -1; i--) {
/*     */           
/*  91 */           for (j = 0; j >= -1; j--) {
/*     */             
/*  93 */             if (isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.SPRUCE)) {
/*     */               
/*  95 */               WorldGenMegaPineTree worldGenMegaPineTree = new WorldGenMegaPineTree(false, rand.nextBoolean());
/*  96 */               flag = true;
/*     */               
/*     */               break label68;
/*     */             } 
/*     */           } 
/*     */         } 
/* 102 */         if (!flag) {
/*     */           
/* 104 */           i = 0;
/* 105 */           j = 0;
/* 106 */           WorldGenTaiga2 worldGenTaiga2 = new WorldGenTaiga2(true);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case BIRCH:
/* 112 */         worldGenBirchTree = new WorldGenBirchTree(true, false);
/*     */         break;
/*     */       
/*     */       case JUNGLE:
/* 116 */         iblockstate = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
/* 117 */         iblockstate1 = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */         
/* 120 */         label69: for (i = 0; i >= -1; i--) {
/*     */           
/* 122 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 124 */             if (isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.JUNGLE)) {
/*     */               
/* 126 */               WorldGenMegaJungle worldGenMegaJungle = new WorldGenMegaJungle(true, 10, 20, iblockstate, iblockstate1);
/* 127 */               flag = true;
/*     */               
/*     */               break label69;
/*     */             } 
/*     */           } 
/*     */         } 
/* 133 */         if (!flag) {
/*     */           
/* 135 */           i = 0;
/* 136 */           j = 0;
/* 137 */           WorldGenTrees worldGenTrees = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case null:
/* 143 */         worldGenSavannaTree = new WorldGenSavannaTree(true);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DARK_OAK:
/* 148 */         label70: for (i = 0; i >= -1; i--) {
/*     */           
/* 150 */           for (j = 0; j >= -1; j--) {
/*     */             
/* 152 */             if (isTwoByTwoOfType(worldIn, pos, i, j, BlockPlanks.EnumType.DARK_OAK)) {
/*     */               
/* 154 */               worldGenCanopyTree = new WorldGenCanopyTree(true);
/* 155 */               flag = true;
/*     */               
/*     */               break label70;
/*     */             } 
/*     */           } 
/*     */         } 
/* 161 */         if (!flag) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 169 */     IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
/*     */     
/* 171 */     if (flag) {
/*     */       
/* 173 */       worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
/* 174 */       worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
/* 175 */       worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
/* 176 */       worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
/*     */     }
/*     */     else {
/*     */       
/* 180 */       worldIn.setBlockState(pos, iblockstate2, 4);
/*     */     } 
/*     */     
/* 183 */     if (!worldGenCanopyTree.generate(worldIn, rand, pos.add(i, 0, j)))
/*     */     {
/* 185 */       if (flag) {
/*     */         
/* 187 */         worldIn.setBlockState(pos.add(i, 0, j), state, 4);
/* 188 */         worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
/* 189 */         worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
/* 190 */         worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
/*     */       }
/*     */       else {
/*     */         
/* 194 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTwoByTwoOfType(World worldIn, BlockPos pos, int p_181624_3_, int p_181624_4_, BlockPlanks.EnumType type) {
/* 201 */     return (isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_), type) && isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_), type) && isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_ + 1), type) && isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTypeAt(World worldIn, BlockPos pos, BlockPlanks.EnumType type) {
/* 209 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 210 */     return (iblockstate.getBlock() == this && iblockstate.getValue((IProperty)TYPE) == type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 219 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*     */     byte b;
/*     */     int i;
/*     */     BlockPlanks.EnumType[] arrayOfEnumType;
/* 227 */     for (i = (arrayOfEnumType = BlockPlanks.EnumType.values()).length, b = 0; b < i; ) { BlockPlanks.EnumType blockplanks$enumtype = arrayOfEnumType[b];
/*     */       
/* 229 */       tab.add(new ItemStack(this, 1, blockplanks$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 243 */     return (worldIn.rand.nextFloat() < 0.45D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 248 */     grow(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 256 */     return getDefaultState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.byMetadata(meta & 0x7)).withProperty((IProperty)STAGE, Integer.valueOf((meta & 0x8) >> 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 264 */     int i = 0;
/* 265 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/* 266 */     i |= ((Integer)state.getValue((IProperty)STAGE)).intValue() << 3;
/* 267 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 272 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)TYPE, (IProperty)STAGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
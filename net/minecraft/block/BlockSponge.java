/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSponge
/*     */   extends Block {
/*  25 */   public static final PropertyBool WET = PropertyBool.create("wet");
/*     */ 
/*     */   
/*     */   protected BlockSponge() {
/*  29 */     super(Material.SPONGE);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)WET, Boolean.valueOf(false)));
/*  31 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  39 */     return I18n.translateToLocal(String.valueOf(getUnlocalizedName()) + ".dry.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  48 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  56 */     tryAbsorb(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  66 */     tryAbsorb(worldIn, pos, state);
/*  67 */     super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tryAbsorb(World worldIn, BlockPos pos, IBlockState state) {
/*  72 */     if (!((Boolean)state.getValue((IProperty)WET)).booleanValue() && absorb(worldIn, pos)) {
/*     */       
/*  74 */       worldIn.setBlockState(pos, state.withProperty((IProperty)WET, Boolean.valueOf(true)), 2);
/*  75 */       worldIn.playEvent(2001, pos, Block.getIdFromBlock(Blocks.WATER));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean absorb(World worldIn, BlockPos pos) {
/*  81 */     Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
/*  82 */     List<BlockPos> list = Lists.newArrayList();
/*  83 */     queue.add(new Tuple(pos, Integer.valueOf(0)));
/*  84 */     int i = 0;
/*     */     
/*  86 */     while (!queue.isEmpty()) {
/*     */       
/*  88 */       Tuple<BlockPos, Integer> tuple = queue.poll();
/*  89 */       BlockPos blockpos = (BlockPos)tuple.getFirst();
/*  90 */       int j = ((Integer)tuple.getSecond()).intValue(); byte b; int k;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  92 */       for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  94 */         BlockPos blockpos1 = blockpos.offset(enumfacing);
/*     */         
/*  96 */         if (worldIn.getBlockState(blockpos1).getMaterial() == Material.WATER) {
/*     */           
/*  98 */           worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 2);
/*  99 */           list.add(blockpos1);
/* 100 */           i++;
/*     */           
/* 102 */           if (j < 6)
/*     */           {
/* 104 */             queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
/*     */           }
/*     */         } 
/*     */         b++; }
/*     */       
/* 109 */       if (i > 64) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 115 */     for (BlockPos blockpos2 : list)
/*     */     {
/* 117 */       worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.AIR, false);
/*     */     }
/*     */     
/* 120 */     return (i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 128 */     tab.add(new ItemStack(this, 1, 0));
/* 129 */     tab.add(new ItemStack(this, 1, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 137 */     return getDefaultState().withProperty((IProperty)WET, Boolean.valueOf(((meta & 0x1) == 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 145 */     return ((Boolean)state.getValue((IProperty)WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 150 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)WET });
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 155 */     if (((Boolean)stateIn.getValue((IProperty)WET)).booleanValue()) {
/*     */       
/* 157 */       EnumFacing enumfacing = EnumFacing.random(rand);
/*     */       
/* 159 */       if (enumfacing != EnumFacing.UP && !worldIn.getBlockState(pos.offset(enumfacing)).isFullyOpaque()) {
/*     */         
/* 161 */         double d0 = pos.getX();
/* 162 */         double d1 = pos.getY();
/* 163 */         double d2 = pos.getZ();
/*     */         
/* 165 */         if (enumfacing == EnumFacing.DOWN) {
/*     */           
/* 167 */           d1 -= 0.05D;
/* 168 */           d0 += rand.nextDouble();
/* 169 */           d2 += rand.nextDouble();
/*     */         }
/*     */         else {
/*     */           
/* 173 */           d1 += rand.nextDouble() * 0.8D;
/*     */           
/* 175 */           if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/*     */             
/* 177 */             d2 += rand.nextDouble();
/*     */             
/* 179 */             if (enumfacing == EnumFacing.EAST)
/*     */             {
/* 181 */               d0++;
/*     */             }
/*     */             else
/*     */             {
/* 185 */               d0 += 0.05D;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 190 */             d0 += rand.nextDouble();
/*     */             
/* 192 */             if (enumfacing == EnumFacing.SOUTH) {
/*     */               
/* 194 */               d2++;
/*     */             }
/*     */             else {
/*     */               
/* 198 */               d2 += 0.05D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 203 */         worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSponge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
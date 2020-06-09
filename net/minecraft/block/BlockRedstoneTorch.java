/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneTorch
/*     */   extends BlockTorch
/*     */ {
/*  24 */   private static final Map<World, List<Toggle>> toggles = Maps.newHashMap();
/*     */   
/*     */   private final boolean isOn;
/*     */   
/*     */   private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
/*  29 */     if (!toggles.containsKey(worldIn))
/*     */     {
/*  31 */       toggles.put(worldIn, Lists.newArrayList());
/*     */     }
/*     */     
/*  34 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/*  36 */     if (turnOff)
/*     */     {
/*  38 */       list.add(new Toggle(pos, worldIn.getTotalWorldTime()));
/*     */     }
/*     */     
/*  41 */     int i = 0;
/*     */     
/*  43 */     for (int j = 0; j < list.size(); j++) {
/*     */       
/*  45 */       Toggle blockredstonetorch$toggle = list.get(j);
/*     */       
/*  47 */       if (blockredstonetorch$toggle.pos.equals(pos)) {
/*     */         
/*  49 */         i++;
/*     */         
/*  51 */         if (i >= 8)
/*     */         {
/*  53 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRedstoneTorch(boolean isOn) {
/*  63 */     this.isOn = isOn;
/*  64 */     setTickRandomly(true);
/*  65 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  73 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  81 */     if (this.isOn) {
/*     */       byte b; int i; EnumFacing[] arrayOfEnumFacing;
/*  83 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  85 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  95 */     if (this.isOn) {
/*     */       byte b; int i; EnumFacing[] arrayOfEnumFacing;
/*  97 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  99 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 106 */     return (this.isOn && blockState.getValue((IProperty)FACING) != side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
/* 111 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 112 */     return worldIn.isSidePowered(pos.offset(enumfacing), enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 124 */     boolean flag = shouldBeOff(worldIn, pos, state);
/* 125 */     List<Toggle> list = toggles.get(worldIn);
/*     */     
/* 127 */     while (list != null && !list.isEmpty() && worldIn.getTotalWorldTime() - ((Toggle)list.get(0)).time > 60L)
/*     */     {
/* 129 */       list.remove(0);
/*     */     }
/*     */     
/* 132 */     if (this.isOn) {
/*     */       
/* 134 */       if (flag) {
/*     */         
/* 136 */         worldIn.setBlockState(pos, Blocks.UNLIT_REDSTONE_TORCH.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */         
/* 138 */         if (isBurnedOut(worldIn, pos, true))
/*     */         {
/* 140 */           worldIn.playSound(null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */           
/* 142 */           for (int i = 0; i < 5; i++) {
/*     */             
/* 144 */             double d0 = pos.getX() + rand.nextDouble() * 0.6D + 0.2D;
/* 145 */             double d1 = pos.getY() + rand.nextDouble() * 0.6D + 0.2D;
/* 146 */             double d2 = pos.getZ() + rand.nextDouble() * 0.6D + 0.2D;
/* 147 */             worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */           
/* 150 */           worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
/*     */         }
/*     */       
/*     */       } 
/* 154 */     } else if (!flag && !isBurnedOut(worldIn, pos, false)) {
/*     */       
/* 156 */       worldIn.setBlockState(pos, Blocks.REDSTONE_TORCH.getDefaultState().withProperty((IProperty)FACING, state.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 167 */     if (!onNeighborChangeInternal(worldIn, pos, state))
/*     */     {
/* 169 */       if (this.isOn == shouldBeOff(worldIn, pos, state))
/*     */       {
/* 171 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 178 */     return (side == EnumFacing.DOWN) ? blockState.getWeakPower(blockAccess, pos, side) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 186 */     return Item.getItemFromBlock(Blocks.REDSTONE_TORCH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 199 */     if (this.isOn) {
/*     */       
/* 201 */       double d0 = pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 202 */       double d1 = pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 203 */       double d2 = pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
/* 204 */       EnumFacing enumfacing = (EnumFacing)stateIn.getValue((IProperty)FACING);
/*     */       
/* 206 */       if (enumfacing.getAxis().isHorizontal()) {
/*     */         
/* 208 */         EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 209 */         double d3 = 0.27D;
/* 210 */         d0 += 0.27D * enumfacing1.getFrontOffsetX();
/* 211 */         d1 += 0.22D;
/* 212 */         d2 += 0.27D * enumfacing1.getFrontOffsetZ();
/*     */       } 
/*     */       
/* 215 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 221 */     return new ItemStack(Blocks.REDSTONE_TORCH);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 226 */     return !(other != Blocks.UNLIT_REDSTONE_TORCH && other != Blocks.REDSTONE_TORCH);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Toggle
/*     */   {
/*     */     BlockPos pos;
/*     */     long time;
/*     */     
/*     */     public Toggle(BlockPos pos, long time) {
/* 236 */       this.pos = pos;
/* 237 */       this.time = time;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
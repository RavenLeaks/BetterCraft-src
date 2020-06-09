/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockLeaves extends Block {
/*  20 */   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
/*  21 */   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
/*     */   
/*     */   protected boolean leavesFancy;
/*     */   int[] surroundings;
/*     */   
/*     */   public BlockLeaves() {
/*  27 */     super(Material.LEAVES);
/*  28 */     setTickRandomly(true);
/*  29 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  30 */     setHardness(0.2F);
/*  31 */     setLightOpacity(1);
/*  32 */     setSoundType(SoundType.PLANT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  40 */     int i = 1;
/*  41 */     int j = 2;
/*  42 */     int k = pos.getX();
/*  43 */     int l = pos.getY();
/*  44 */     int i1 = pos.getZ();
/*     */     
/*  46 */     if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2)))
/*     */     {
/*  48 */       for (int j1 = -1; j1 <= 1; j1++) {
/*     */         
/*  50 */         for (int k1 = -1; k1 <= 1; k1++) {
/*     */           
/*  52 */           for (int l1 = -1; l1 <= 1; l1++) {
/*     */             
/*  54 */             BlockPos blockpos = pos.add(j1, k1, l1);
/*  55 */             IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */             
/*  57 */             if (iblockstate.getMaterial() == Material.LEAVES && !((Boolean)iblockstate.getValue((IProperty)CHECK_DECAY)).booleanValue())
/*     */             {
/*  59 */               worldIn.setBlockState(blockpos, iblockstate.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(true)), 4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  69 */     if (!worldIn.isRemote)
/*     */     {
/*  71 */       if (((Boolean)state.getValue((IProperty)CHECK_DECAY)).booleanValue() && ((Boolean)state.getValue((IProperty)DECAYABLE)).booleanValue()) {
/*     */         
/*  73 */         int i = 4;
/*  74 */         int j = 5;
/*  75 */         int k = pos.getX();
/*  76 */         int l = pos.getY();
/*  77 */         int i1 = pos.getZ();
/*  78 */         int j1 = 32;
/*  79 */         int k1 = 1024;
/*  80 */         int l1 = 16;
/*     */         
/*  82 */         if (this.surroundings == null)
/*     */         {
/*  84 */           this.surroundings = new int[32768];
/*     */         }
/*     */         
/*  87 */         if (worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5))) {
/*     */           
/*  89 */           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */           
/*  91 */           for (int i2 = -4; i2 <= 4; i2++) {
/*     */             
/*  93 */             for (int j2 = -4; j2 <= 4; j2++) {
/*     */               
/*  95 */               for (int k2 = -4; k2 <= 4; k2++) {
/*     */                 
/*  97 */                 IBlockState iblockstate = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
/*  98 */                 Block block = iblockstate.getBlock();
/*     */                 
/* 100 */                 if (block != Blocks.LOG && block != Blocks.LOG2) {
/*     */                   
/* 102 */                   if (iblockstate.getMaterial() == Material.LEAVES)
/*     */                   {
/* 104 */                     this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
/*     */                   }
/*     */                   else
/*     */                   {
/* 108 */                     this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 113 */                   this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 119 */           for (int i3 = 1; i3 <= 4; i3++) {
/*     */             
/* 121 */             for (int j3 = -4; j3 <= 4; j3++) {
/*     */               
/* 123 */               for (int k3 = -4; k3 <= 4; k3++) {
/*     */                 
/* 125 */                 for (int l3 = -4; l3 <= 4; l3++) {
/*     */                   
/* 127 */                   if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
/*     */                     
/* 129 */                     if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
/*     */                     {
/* 131 */                       this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
/*     */                     }
/*     */                     
/* 134 */                     if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
/*     */                     {
/* 136 */                       this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
/*     */                     }
/*     */                     
/* 139 */                     if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2)
/*     */                     {
/* 141 */                       this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
/*     */                     }
/*     */                     
/* 144 */                     if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2)
/*     */                     {
/* 146 */                       this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
/*     */                     }
/*     */                     
/* 149 */                     if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 - 1] == -2)
/*     */                     {
/* 151 */                       this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 - 1] = i3;
/*     */                     }
/*     */                     
/* 154 */                     if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2)
/*     */                     {
/* 156 */                       this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 165 */         int l2 = this.surroundings[16912];
/*     */         
/* 167 */         if (l2 >= 0) {
/*     */           
/* 169 */           worldIn.setBlockState(pos, state.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(false)), 4);
/*     */         }
/*     */         else {
/*     */           
/* 173 */           destroy(worldIn, pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 181 */     if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isFullyOpaque() && rand.nextInt(15) == 1) {
/*     */       
/* 183 */       double d0 = (pos.getX() + rand.nextFloat());
/* 184 */       double d1 = pos.getY() - 0.05D;
/* 185 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 186 */       worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroy(World worldIn, BlockPos pos) {
/* 192 */     dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 193 */     worldIn.setBlockToAir(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 201 */     return (random.nextInt(20) == 0) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 209 */     return Item.getItemFromBlock(Blocks.SAPLING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 217 */     if (!worldIn.isRemote) {
/*     */       
/* 219 */       int i = getSaplingDropChance(state);
/*     */       
/* 221 */       if (fortune > 0) {
/*     */         
/* 223 */         i -= 2 << fortune;
/*     */         
/* 225 */         if (i < 10)
/*     */         {
/* 227 */           i = 10;
/*     */         }
/*     */       } 
/*     */       
/* 231 */       if (worldIn.rand.nextInt(i) == 0) {
/*     */         
/* 233 */         Item item = getItemDropped(state, worldIn.rand, fortune);
/* 234 */         spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*     */       } 
/*     */       
/* 237 */       i = 200;
/*     */       
/* 239 */       if (fortune > 0) {
/*     */         
/* 241 */         i -= 10 << fortune;
/*     */         
/* 243 */         if (i < 40)
/*     */         {
/* 245 */           i = 40;
/*     */         }
/*     */       } 
/*     */       
/* 249 */       dropApple(worldIn, pos, state, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {}
/*     */ 
/*     */   
/*     */   protected int getSaplingDropChance(IBlockState state) {
/* 259 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 267 */     return !this.leavesFancy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGraphicsLevel(boolean fancy) {
/* 275 */     this.leavesFancy = fancy;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 280 */     return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causesSuffocation(IBlockState p_176214_1_) {
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract BlockPlanks.EnumType getWoodType(int paramInt);
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 292 */     return (!this.leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this) ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
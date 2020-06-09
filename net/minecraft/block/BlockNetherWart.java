/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
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
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNetherWart
/*     */   extends BlockBush
/*     */ {
/*  22 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
/*  23 */   private static final AxisAlignedBB[] NETHER_WART_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D) };
/*     */ 
/*     */   
/*     */   protected BlockNetherWart() {
/*  27 */     super(Material.PLANTS, MapColor.RED);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  29 */     setTickRandomly(true);
/*  30 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  35 */     return NETHER_WART_AABB[((Integer)state.getValue((IProperty)AGE)).intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSustainBush(IBlockState state) {
/*  43 */     return (state.getBlock() == Blocks.SOUL_SAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     return canSustainBush(worldIn.getBlockState(pos.down()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  53 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */     
/*  55 */     if (i < 3 && rand.nextInt(10) == 0) {
/*     */       
/*  57 */       state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  58 */       worldIn.setBlockState(pos, state, 2);
/*     */     } 
/*     */     
/*  61 */     super.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  69 */     if (!worldIn.isRemote) {
/*     */       
/*  71 */       int i = 1;
/*     */       
/*  73 */       if (((Integer)state.getValue((IProperty)AGE)).intValue() >= 3) {
/*     */         
/*  75 */         i = 2 + worldIn.rand.nextInt(3);
/*     */         
/*  77 */         if (fortune > 0)
/*     */         {
/*  79 */           i += worldIn.rand.nextInt(fortune + 1);
/*     */         }
/*     */       } 
/*     */       
/*  83 */       for (int j = 0; j < i; j++)
/*     */       {
/*  85 */         spawnAsEntity(worldIn, pos, new ItemStack(Items.NETHER_WART));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  95 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 108 */     return new ItemStack(Items.NETHER_WART);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 116 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 124 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 129 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockNetherWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
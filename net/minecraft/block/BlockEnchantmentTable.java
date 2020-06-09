/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEnchantmentTable extends BlockContainer {
/*  26 */   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
/*     */ 
/*     */   
/*     */   protected BlockEnchantmentTable() {
/*  30 */     super(Material.ROCK, MapColor.RED);
/*  31 */     setLightOpacity(0);
/*  32 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  37 */     return AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/*  47 */     super.randomDisplayTick(stateIn, worldIn, pos, rand);
/*     */     
/*  49 */     for (int i = -2; i <= 2; i++) {
/*     */       
/*  51 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  53 */         if (i > -2 && i < 2 && j == -1)
/*     */         {
/*  55 */           j = 2;
/*     */         }
/*     */         
/*  58 */         if (rand.nextInt(16) == 0)
/*     */         {
/*  60 */           for (int k = 0; k <= 1; k++) {
/*     */             
/*  62 */             BlockPos blockpos = pos.add(i, k, j);
/*     */             
/*  64 */             if (worldIn.getBlockState(blockpos).getBlock() == Blocks.BOOKSHELF) {
/*     */               
/*  66 */               if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2))) {
/*     */                 break;
/*     */               }
/*     */ 
/*     */               
/*  71 */               worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, (i + rand.nextFloat()) - 0.5D, (k - rand.nextFloat() - 1.0F), (j + rand.nextFloat()) - 0.5D, new int[0]);
/*     */             } 
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
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  93 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 101 */     return (TileEntity)new TileEntityEnchantmentTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 106 */     if (worldIn.isRemote)
/*     */     {
/* 108 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 112 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 114 */     if (tileentity instanceof TileEntityEnchantmentTable)
/*     */     {
/* 116 */       playerIn.displayGui((IInteractionObject)tileentity);
/*     */     }
/*     */     
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 128 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/* 130 */     if (stack.hasDisplayName()) {
/*     */       
/* 132 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 134 */       if (tileentity instanceof TileEntityEnchantmentTable)
/*     */       {
/* 136 */         ((TileEntityEnchantmentTable)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 143 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityStructure;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStructure
/*     */   extends BlockContainer {
/*  23 */   public static final PropertyEnum<TileEntityStructure.Mode> MODE = PropertyEnum.create("mode", TileEntityStructure.Mode.class);
/*     */ 
/*     */   
/*     */   public BlockStructure() {
/*  27 */     super(Material.IRON, MapColor.SILVER);
/*  28 */     setDefaultState(this.blockState.getBaseState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  36 */     return (TileEntity)new TileEntityStructure();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  41 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  42 */     return (tileentity instanceof TileEntityStructure) ? ((TileEntityStructure)tileentity).usedBy(playerIn) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  50 */     if (!worldIn.isRemote) {
/*     */       
/*  52 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  54 */       if (tileentity instanceof TileEntityStructure) {
/*     */         
/*  56 */         TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
/*  57 */         tileentitystructure.createdBy(placer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  76 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  85 */     return getDefaultState().withProperty((IProperty)MODE, (Comparable)TileEntityStructure.Mode.DATA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  93 */     return getDefaultState().withProperty((IProperty)MODE, (Comparable)TileEntityStructure.Mode.getById(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 101 */     return ((TileEntityStructure.Mode)state.getValue((IProperty)MODE)).getModeId();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 106 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)MODE });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 116 */     if (!worldIn.isRemote) {
/*     */       
/* 118 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 120 */       if (tileentity instanceof TileEntityStructure) {
/*     */         
/* 122 */         TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
/* 123 */         boolean flag = worldIn.isBlockPowered(pos);
/* 124 */         boolean flag1 = tileentitystructure.isPowered();
/*     */         
/* 126 */         if (flag && !flag1) {
/*     */           
/* 128 */           tileentitystructure.setPowered(true);
/* 129 */           trigger(tileentitystructure);
/*     */         }
/* 131 */         else if (!flag && flag1) {
/*     */           
/* 133 */           tileentitystructure.setPowered(false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void trigger(TileEntityStructure p_189874_1_) {
/* 141 */     switch (p_189874_1_.getMode()) {
/*     */       
/*     */       case SAVE:
/* 144 */         p_189874_1_.save(false);
/*     */         break;
/*     */       
/*     */       case LOAD:
/* 148 */         p_189874_1_.load(false);
/*     */         break;
/*     */       
/*     */       case null:
/* 152 */         p_189874_1_.unloadStructure();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
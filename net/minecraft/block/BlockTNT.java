/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTNT
/*     */   extends Block {
/*  27 */   public static final PropertyBool EXPLODE = PropertyBool.create("explode");
/*     */ 
/*     */   
/*     */   public BlockTNT() {
/*  31 */     super(Material.TNT);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)EXPLODE, Boolean.valueOf(false)));
/*  33 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  41 */     super.onBlockAdded(worldIn, pos, state);
/*     */     
/*  43 */     if (worldIn.isBlockPowered(pos)) {
/*     */       
/*  45 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  46 */       worldIn.setBlockToAir(pos);
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
/*  57 */     if (worldIn.isBlockPowered(pos)) {
/*     */       
/*  59 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  60 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/*  69 */     if (!worldIn.isRemote) {
/*     */       
/*  71 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
/*  72 */       entitytntprimed.setFuse((short)(worldIn.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8));
/*  73 */       worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  82 */     explode(worldIn, pos, state, (EntityLivingBase)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
/*  87 */     if (!worldIn.isRemote)
/*     */     {
/*  89 */       if (((Boolean)state.getValue((IProperty)EXPLODE)).booleanValue()) {
/*     */         
/*  91 */         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), igniter);
/*  92 */         worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*  93 */         worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 100 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/*     */     
/* 102 */     if (!itemstack.func_190926_b() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
/*     */       
/* 104 */       explode(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (EntityLivingBase)playerIn);
/* 105 */       worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
/*     */       
/* 107 */       if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
/*     */         
/* 109 */         itemstack.damageItem(1, (EntityLivingBase)playerIn);
/*     */       }
/* 111 */       else if (!playerIn.capabilities.isCreativeMode) {
/*     */         
/* 113 */         itemstack.func_190918_g(1);
/*     */       } 
/*     */       
/* 116 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 129 */     if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
/*     */       
/* 131 */       EntityArrow entityarrow = (EntityArrow)entityIn;
/*     */       
/* 133 */       if (entityarrow.isBurning()) {
/*     */         
/* 135 */         explode(worldIn, pos, worldIn.getBlockState(pos).withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (entityarrow.shootingEntity instanceof EntityLivingBase) ? (EntityLivingBase)entityarrow.shootingEntity : null);
/* 136 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 154 */     return getDefaultState().withProperty((IProperty)EXPLODE, Boolean.valueOf(((meta & 0x1) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 162 */     return ((Boolean)state.getValue((IProperty)EXPLODE)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 167 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)EXPLODE });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockTNT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
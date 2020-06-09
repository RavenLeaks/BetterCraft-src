/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSlab
/*     */   extends ItemBlock {
/*     */   private final BlockSlab singleSlab;
/*     */   private final BlockSlab doubleSlab;
/*     */   
/*     */   public ItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
/*  26 */     super(block);
/*  27 */     this.singleSlab = singleSlab;
/*  28 */     this.doubleSlab = doubleSlab;
/*  29 */     setMaxDamage(0);
/*  30 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/*  39 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  48 */     return this.singleSlab.getUnlocalizedName(stack.getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  56 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  58 */     if (!itemstack.func_190926_b() && stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack)) {
/*     */       
/*  60 */       Comparable<?> comparable = this.singleSlab.getTypeForItem(itemstack);
/*  61 */       IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*     */       
/*  63 */       if (iblockstate.getBlock() == this.singleSlab) {
/*     */         
/*  65 */         IProperty<?> iproperty = this.singleSlab.getVariantProperty();
/*  66 */         Comparable<?> comparable1 = iblockstate.getValue(iproperty);
/*  67 */         BlockSlab.EnumBlockHalf blockslab$enumblockhalf = (BlockSlab.EnumBlockHalf)iblockstate.getValue((IProperty)BlockSlab.HALF);
/*     */         
/*  69 */         if (((hand == EnumFacing.UP && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM) || (hand == EnumFacing.DOWN && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP)) && comparable1 == comparable) {
/*     */           
/*  71 */           IBlockState iblockstate1 = makeState(iproperty, comparable1);
/*  72 */           AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox((IBlockAccess)playerIn, worldIn);
/*     */           
/*  74 */           if (axisalignedbb != Block.NULL_AABB && playerIn.checkNoEntityCollision(axisalignedbb.offset(worldIn)) && playerIn.setBlockState(worldIn, iblockstate1, 11)) {
/*     */             
/*  76 */             SoundType soundtype = this.doubleSlab.getSoundType();
/*  77 */             playerIn.playSound(stack, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/*  78 */             itemstack.func_190918_g(1);
/*     */             
/*  80 */             if (stack instanceof EntityPlayerMP)
/*     */             {
/*  82 */               CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*     */             }
/*     */           } 
/*     */           
/*  86 */           return EnumActionResult.SUCCESS;
/*     */         } 
/*     */       } 
/*     */       
/*  90 */       return tryPlace(stack, itemstack, playerIn, worldIn.offset(hand), comparable) ? EnumActionResult.SUCCESS : super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 100 */     BlockPos blockpos = pos;
/* 101 */     IProperty<?> iproperty = this.singleSlab.getVariantProperty();
/* 102 */     Comparable<?> comparable = this.singleSlab.getTypeForItem(stack);
/* 103 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 105 */     if (iblockstate.getBlock() == this.singleSlab) {
/*     */       
/* 107 */       boolean flag = (iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP);
/*     */       
/* 109 */       if (((side == EnumFacing.UP && !flag) || (side == EnumFacing.DOWN && flag)) && comparable == iblockstate.getValue(iproperty))
/*     */       {
/* 111 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     pos = pos.offset(side);
/* 116 */     IBlockState iblockstate1 = worldIn.getBlockState(pos);
/* 117 */     return (iblockstate1.getBlock() == this.singleSlab && comparable == iblockstate1.getValue(iproperty)) ? true : super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos, Object itemSlabType) {
/* 122 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 124 */     if (iblockstate.getBlock() == this.singleSlab) {
/*     */       
/* 126 */       Comparable<?> comparable = iblockstate.getValue(this.singleSlab.getVariantProperty());
/*     */       
/* 128 */       if (comparable == itemSlabType) {
/*     */         
/* 130 */         IBlockState iblockstate1 = makeState(this.singleSlab.getVariantProperty(), comparable);
/* 131 */         AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox((IBlockAccess)worldIn, pos);
/*     */         
/* 133 */         if (axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, iblockstate1, 11)) {
/*     */           
/* 135 */           SoundType soundtype = this.doubleSlab.getSoundType();
/* 136 */           worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/* 137 */           stack.func_190918_g(1);
/*     */         } 
/*     */         
/* 140 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends Comparable<T>> IBlockState makeState(IProperty<T> p_185055_1_, Comparable<?> p_185055_2_) {
/* 149 */     return this.doubleSlab.getDefaultState().withProperty(p_185055_1_, p_185055_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
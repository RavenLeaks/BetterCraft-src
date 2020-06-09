/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBed;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBed extends Item {
/*     */   public ItemBed() {
/*  27 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  28 */     setMaxDamage(0);
/*  29 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  37 */     if (playerIn.isRemote)
/*     */     {
/*  39 */       return EnumActionResult.SUCCESS;
/*     */     }
/*  41 */     if (hand != EnumFacing.UP)
/*     */     {
/*  43 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  47 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  48 */     Block block = iblockstate.getBlock();
/*  49 */     boolean flag = block.isReplaceable((IBlockAccess)playerIn, worldIn);
/*     */     
/*  51 */     if (!flag)
/*     */     {
/*  53 */       worldIn = worldIn.up();
/*     */     }
/*     */     
/*  56 */     int i = MathHelper.floor((stack.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*  57 */     EnumFacing enumfacing = EnumFacing.getHorizontal(i);
/*  58 */     BlockPos blockpos = worldIn.offset(enumfacing);
/*  59 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  61 */     if (stack.canPlayerEdit(worldIn, hand, itemstack) && stack.canPlayerEdit(blockpos, hand, itemstack)) {
/*     */       
/*  63 */       IBlockState iblockstate1 = playerIn.getBlockState(blockpos);
/*  64 */       boolean flag1 = iblockstate1.getBlock().isReplaceable((IBlockAccess)playerIn, blockpos);
/*  65 */       boolean flag2 = !(!flag && !playerIn.isAirBlock(worldIn));
/*  66 */       boolean flag3 = !(!flag1 && !playerIn.isAirBlock(blockpos));
/*     */       
/*  68 */       if (flag2 && flag3 && playerIn.getBlockState(worldIn.down()).isFullyOpaque() && playerIn.getBlockState(blockpos.down()).isFullyOpaque()) {
/*     */         
/*  70 */         IBlockState iblockstate2 = Blocks.BED.getDefaultState().withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty((IProperty)BlockBed.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.FOOT);
/*  71 */         playerIn.setBlockState(worldIn, iblockstate2, 10);
/*  72 */         playerIn.setBlockState(blockpos, iblockstate2.withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.HEAD), 10);
/*  73 */         SoundType soundtype = iblockstate2.getBlock().getSoundType();
/*  74 */         playerIn.playSound(null, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/*  75 */         TileEntity tileentity = playerIn.getTileEntity(blockpos);
/*     */         
/*  77 */         if (tileentity instanceof TileEntityBed)
/*     */         {
/*  79 */           ((TileEntityBed)tileentity).func_193051_a(itemstack);
/*     */         }
/*     */         
/*  82 */         TileEntity tileentity1 = playerIn.getTileEntity(worldIn);
/*     */         
/*  84 */         if (tileentity1 instanceof TileEntityBed)
/*     */         {
/*  86 */           ((TileEntityBed)tileentity1).func_193051_a(itemstack);
/*     */         }
/*     */         
/*  89 */         playerIn.notifyNeighborsRespectDebug(worldIn, block, false);
/*  90 */         playerIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);
/*     */         
/*  92 */         if (stack instanceof EntityPlayerMP)
/*     */         {
/*  94 */           CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*     */         }
/*     */         
/*  97 */         itemstack.func_190918_g(1);
/*  98 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */ 
/*     */       
/* 102 */       return EnumActionResult.FAIL;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 118 */     return String.valueOf(getUnlocalizedName()) + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 126 */     if (func_194125_a(itemIn))
/*     */     {
/* 128 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 130 */         tab.add(new ItemStack(this, 1, i));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
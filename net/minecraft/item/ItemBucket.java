/*     */ package net.minecraft.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBucket
/*     */   extends Item {
/*     */   private final Block containedBlock;
/*     */   
/*     */   public ItemBucket(Block containedBlockIn) {
/*  34 */     this.maxStackSize = 1;
/*  35 */     this.containedBlock = containedBlockIn;
/*  36 */     setCreativeTab(CreativeTabs.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  41 */     boolean flag = (this.containedBlock == Blocks.AIR);
/*  42 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*  43 */     RayTraceResult raytraceresult = rayTrace(itemStackIn, worldIn, flag);
/*     */     
/*  45 */     if (raytraceresult == null)
/*     */     {
/*  47 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*  49 */     if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
/*     */     {
/*  51 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */ 
/*     */     
/*  55 */     BlockPos blockpos = raytraceresult.getBlockPos();
/*     */     
/*  57 */     if (!itemStackIn.isBlockModifiable(worldIn, blockpos))
/*     */     {
/*  59 */       return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*  61 */     if (flag) {
/*     */       
/*  63 */       if (!worldIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
/*     */       {
/*  65 */         return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */ 
/*     */       
/*  69 */       IBlockState iblockstate = itemStackIn.getBlockState(blockpos);
/*  70 */       Material material = iblockstate.getMaterial();
/*     */       
/*  72 */       if (material == Material.WATER && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*     */         
/*  74 */         itemStackIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
/*  75 */         worldIn.addStat(StatList.getObjectUseStats(this));
/*  76 */         worldIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
/*  77 */         return new ActionResult(EnumActionResult.SUCCESS, fillBucket(itemstack, worldIn, Items.WATER_BUCKET));
/*     */       } 
/*  79 */       if (material == Material.LAVA && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*     */         
/*  81 */         worldIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
/*  82 */         itemStackIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
/*  83 */         worldIn.addStat(StatList.getObjectUseStats(this));
/*  84 */         return new ActionResult(EnumActionResult.SUCCESS, fillBucket(itemstack, worldIn, Items.LAVA_BUCKET));
/*     */       } 
/*     */ 
/*     */       
/*  88 */       return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     boolean flag1 = itemStackIn.getBlockState(blockpos).getBlock().isReplaceable((IBlockAccess)itemStackIn, blockpos);
/*  95 */     BlockPos blockpos1 = (flag1 && raytraceresult.sideHit == EnumFacing.UP) ? blockpos : blockpos.offset(raytraceresult.sideHit);
/*     */     
/*  97 */     if (!worldIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack))
/*     */     {
/*  99 */       return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/* 101 */     if (tryPlaceContainedLiquid(worldIn, itemStackIn, blockpos1)) {
/*     */       
/* 103 */       if (worldIn instanceof EntityPlayerMP)
/*     */       {
/* 105 */         CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)worldIn, blockpos1, itemstack);
/*     */       }
/*     */       
/* 108 */       worldIn.addStat(StatList.getObjectUseStats(this));
/* 109 */       return !worldIn.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(Items.BUCKET)) : new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 113 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket) {
/* 121 */     if (player.capabilities.isCreativeMode)
/*     */     {
/* 123 */       return emptyBuckets;
/*     */     }
/*     */ 
/*     */     
/* 127 */     emptyBuckets.func_190918_g(1);
/*     */     
/* 129 */     if (emptyBuckets.func_190926_b())
/*     */     {
/* 131 */       return new ItemStack(fullBucket);
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
/*     */     {
/* 137 */       player.dropItem(new ItemStack(fullBucket), false);
/*     */     }
/*     */     
/* 140 */     return emptyBuckets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn) {
/* 147 */     if (this.containedBlock == Blocks.AIR)
/*     */     {
/* 149 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 153 */     IBlockState iblockstate = worldIn.getBlockState(posIn);
/* 154 */     Material material = iblockstate.getMaterial();
/* 155 */     boolean flag = !material.isSolid();
/* 156 */     boolean flag1 = iblockstate.getBlock().isReplaceable((IBlockAccess)worldIn, posIn);
/*     */     
/* 158 */     if (!worldIn.isAirBlock(posIn) && !flag && !flag1)
/*     */     {
/* 160 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 164 */     if (worldIn.provider.doesWaterVaporize() && this.containedBlock == Blocks.FLOWING_WATER) {
/*     */       
/* 166 */       int l = posIn.getX();
/* 167 */       int i = posIn.getY();
/* 168 */       int j = posIn.getZ();
/* 169 */       worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */       
/* 171 */       for (int k = 0; k < 8; k++)
/*     */       {
/* 173 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, l + Math.random(), i + Math.random(), j + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 178 */       if (!worldIn.isRemote && (flag || flag1) && !material.isLiquid())
/*     */       {
/* 180 */         worldIn.destroyBlock(posIn, true);
/*     */       }
/*     */       
/* 183 */       SoundEvent soundevent = (this.containedBlock == Blocks.FLOWING_LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
/* 184 */       worldIn.playSound(player, posIn, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
/* 185 */       worldIn.setBlockState(posIn, this.containedBlock.getDefaultState(), 11);
/*     */     } 
/*     */     
/* 188 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBucket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
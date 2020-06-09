/*     */ package net.minecraft.item;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemDye extends Item {
/*  24 */   public static final int[] DYE_COLORS = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */ 
/*     */   
/*     */   public ItemDye() {
/*  28 */     setHasSubtypes(true);
/*  29 */     setMaxDamage(0);
/*  30 */     setCreativeTab(CreativeTabs.MATERIALS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  39 */     int i = stack.getMetadata();
/*  40 */     return String.valueOf(getUnlocalizedName()) + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  48 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  50 */     if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
/*     */     {
/*  52 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  56 */     EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
/*     */     
/*  58 */     if (enumdyecolor == EnumDyeColor.WHITE) {
/*     */       
/*  60 */       if (applyBonemeal(itemstack, playerIn, worldIn))
/*     */       {
/*  62 */         if (!playerIn.isRemote)
/*     */         {
/*  64 */           playerIn.playEvent(2005, worldIn, 0);
/*     */         }
/*     */         
/*  67 */         return EnumActionResult.SUCCESS;
/*     */       }
/*     */     
/*  70 */     } else if (enumdyecolor == EnumDyeColor.BROWN) {
/*     */       
/*  72 */       IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  73 */       Block block = iblockstate.getBlock();
/*     */       
/*  75 */       if (block == Blocks.LOG && iblockstate.getValue((IProperty)BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
/*     */         
/*  77 */         if (hand == EnumFacing.DOWN || hand == EnumFacing.UP)
/*     */         {
/*  79 */           return EnumActionResult.FAIL;
/*     */         }
/*     */         
/*  82 */         worldIn = worldIn.offset(hand);
/*     */         
/*  84 */         if (playerIn.isAirBlock(worldIn)) {
/*     */           
/*  86 */           IBlockState iblockstate1 = Blocks.COCOA.onBlockPlaced(playerIn, worldIn, hand, facing, hitX, hitY, 0, (EntityLivingBase)stack);
/*  87 */           playerIn.setBlockState(worldIn, iblockstate1, 10);
/*     */           
/*  89 */           if (!stack.capabilities.isCreativeMode)
/*     */           {
/*  91 */             itemstack.func_190918_g(1);
/*     */           }
/*     */           
/*  94 */           return EnumActionResult.SUCCESS;
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       return EnumActionResult.FAIL;
/*     */     } 
/*     */     
/* 101 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
/* 107 */     IBlockState iblockstate = worldIn.getBlockState(target);
/*     */     
/* 109 */     if (iblockstate.getBlock() instanceof IGrowable) {
/*     */       
/* 111 */       IGrowable igrowable = (IGrowable)iblockstate.getBlock();
/*     */       
/* 113 */       if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
/*     */         
/* 115 */         if (!worldIn.isRemote) {
/*     */           
/* 117 */           if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
/*     */           {
/* 119 */             igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
/*     */           }
/*     */           
/* 122 */           stack.func_190918_g(1);
/*     */         } 
/*     */         
/* 125 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
/* 134 */     if (amount == 0)
/*     */     {
/* 136 */       amount = 15;
/*     */     }
/*     */     
/* 139 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 141 */     if (iblockstate.getMaterial() != Material.AIR)
/*     */     {
/* 143 */       for (int i = 0; i < amount; i++) {
/*     */         
/* 145 */         double d0 = itemRand.nextGaussian() * 0.02D;
/* 146 */         double d1 = itemRand.nextGaussian() * 0.02D;
/* 147 */         double d2 = itemRand.nextGaussian() * 0.02D;
/* 148 */         worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (pos.getX() + itemRand.nextFloat()), pos.getY() + itemRand.nextFloat() * (iblockstate.getBoundingBox((IBlockAccess)worldIn, pos)).maxY, (pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
/* 158 */     if (target instanceof EntitySheep) {
/*     */       
/* 160 */       EntitySheep entitysheep = (EntitySheep)target;
/* 161 */       EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */       
/* 163 */       if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
/*     */         
/* 165 */         entitysheep.setFleeceColor(enumdyecolor);
/* 166 */         stack.func_190918_g(1);
/*     */       } 
/*     */       
/* 169 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 182 */     if (func_194125_a(itemIn))
/*     */     {
/* 184 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 186 */         tab.add(new ItemStack(this, 1, i));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemDye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
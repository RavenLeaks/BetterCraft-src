/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.BlockEndPortalFrame;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class ItemEnderEye extends Item {
/*     */   public ItemEnderEye() {
/*  29 */     setCreativeTab(CreativeTabs.MISC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  37 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  38 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  40 */     if (stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack) && iblockstate.getBlock() == Blocks.END_PORTAL_FRAME && !((Boolean)iblockstate.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*     */       
/*  42 */       if (playerIn.isRemote)
/*     */       {
/*  44 */         return EnumActionResult.SUCCESS;
/*     */       }
/*     */ 
/*     */       
/*  48 */       playerIn.setBlockState(worldIn, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
/*  49 */       playerIn.updateComparatorOutputLevel(worldIn, Blocks.END_PORTAL_FRAME);
/*  50 */       itemstack.func_190918_g(1);
/*     */       
/*  52 */       for (int i = 0; i < 16; i++) {
/*     */         
/*  54 */         double d0 = (worldIn.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  55 */         double d1 = (worldIn.getY() + 0.8125F);
/*  56 */         double d2 = (worldIn.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  57 */         double d3 = 0.0D;
/*  58 */         double d4 = 0.0D;
/*  59 */         double d5 = 0.0D;
/*  60 */         playerIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */       
/*  63 */       playerIn.playSound(null, worldIn, SoundEvents.field_193781_bp, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*  64 */       BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.getOrCreatePortalShape().match(playerIn, worldIn);
/*     */       
/*  66 */       if (blockpattern$patternhelper != null) {
/*     */         
/*  68 */         BlockPos blockpos = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);
/*     */         
/*  70 */         for (int j = 0; j < 3; j++) {
/*     */           
/*  72 */           for (int k = 0; k < 3; k++)
/*     */           {
/*  74 */             playerIn.setBlockState(blockpos.add(j, 0, k), Blocks.END_PORTAL.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */         
/*  78 */         playerIn.playBroadcastSound(1038, blockpos.add(1, 0, 1), 0);
/*     */       } 
/*     */       
/*  81 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  92 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*  93 */     RayTraceResult raytraceresult = rayTrace(itemStackIn, worldIn, false);
/*     */     
/*  95 */     if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && itemStackIn.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.END_PORTAL_FRAME)
/*     */     {
/*  97 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */ 
/*     */     
/* 101 */     worldIn.setActiveHand(playerIn);
/*     */     
/* 103 */     if (!itemStackIn.isRemote) {
/*     */       
/* 105 */       BlockPos blockpos = ((WorldServer)itemStackIn).getChunkProvider().getStrongholdGen(itemStackIn, "Stronghold", new BlockPos((Entity)worldIn), false);
/*     */       
/* 107 */       if (blockpos != null) {
/*     */         
/* 109 */         EntityEnderEye entityendereye = new EntityEnderEye(itemStackIn, worldIn.posX, worldIn.posY + (worldIn.height / 2.0F), worldIn.posZ);
/* 110 */         entityendereye.moveTowards(blockpos);
/* 111 */         itemStackIn.spawnEntityInWorld((Entity)entityendereye);
/*     */         
/* 113 */         if (worldIn instanceof EntityPlayerMP)
/*     */         {
/* 115 */           CriteriaTriggers.field_192132_l.func_192239_a((EntityPlayerMP)worldIn, blockpos);
/*     */         }
/*     */         
/* 118 */         itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 119 */         itemStackIn.playEvent(null, 1003, new BlockPos((Entity)worldIn), 0);
/*     */         
/* 121 */         if (!worldIn.capabilities.isCreativeMode)
/*     */         {
/* 123 */           itemstack.func_190918_g(1);
/*     */         }
/*     */         
/* 126 */         worldIn.addStat(StatList.getObjectUseStats(this));
/* 127 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemEnderEye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
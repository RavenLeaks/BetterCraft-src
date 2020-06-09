/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLilyPad
/*    */   extends ItemColored {
/*    */   public ItemLilyPad(Block block) {
/* 25 */     super(block, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 30 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 31 */     RayTraceResult raytraceresult = rayTrace(itemStackIn, worldIn, true);
/*    */     
/* 33 */     if (raytraceresult == null)
/*    */     {
/* 35 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */     }
/*    */ 
/*    */     
/* 39 */     if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
/*    */       
/* 41 */       BlockPos blockpos = raytraceresult.getBlockPos();
/*    */       
/* 43 */       if (!itemStackIn.isBlockModifiable(worldIn, blockpos) || !worldIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
/*    */       {
/* 45 */         return new ActionResult(EnumActionResult.FAIL, itemstack);
/*    */       }
/*    */       
/* 48 */       BlockPos blockpos1 = blockpos.up();
/* 49 */       IBlockState iblockstate = itemStackIn.getBlockState(blockpos);
/*    */       
/* 51 */       if (iblockstate.getMaterial() == Material.WATER && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0 && itemStackIn.isAirBlock(blockpos1)) {
/*    */         
/* 53 */         itemStackIn.setBlockState(blockpos1, Blocks.WATERLILY.getDefaultState(), 11);
/*    */         
/* 55 */         if (worldIn instanceof EntityPlayerMP)
/*    */         {
/* 57 */           CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)worldIn, blockpos1, itemstack);
/*    */         }
/*    */         
/* 60 */         if (!worldIn.capabilities.isCreativeMode)
/*    */         {
/* 62 */           itemstack.func_190918_g(1);
/*    */         }
/*    */         
/* 65 */         worldIn.addStat(StatList.getObjectUseStats(this));
/* 66 */         itemStackIn.playSound(worldIn, blockpos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
/* 67 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemLilyPad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
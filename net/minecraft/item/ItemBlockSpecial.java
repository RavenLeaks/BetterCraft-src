/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBlockSpecial extends Item {
/*    */   private final Block block;
/*    */   
/*    */   public ItemBlockSpecial(Block block) {
/* 25 */     this.block = block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 33 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/* 34 */     Block block = iblockstate.getBlock();
/*    */     
/* 36 */     if (block == Blocks.SNOW_LAYER && ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() < 1) {
/*    */       
/* 38 */       hand = EnumFacing.UP;
/*    */     }
/* 40 */     else if (!block.isReplaceable((IBlockAccess)playerIn, worldIn)) {
/*    */       
/* 42 */       worldIn = worldIn.offset(hand);
/*    */     } 
/*    */     
/* 45 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 47 */     if (!itemstack.func_190926_b() && stack.canPlayerEdit(worldIn, hand, itemstack) && playerIn.func_190527_a(this.block, worldIn, false, hand, null)) {
/*    */       
/* 49 */       IBlockState iblockstate1 = this.block.onBlockPlaced(playerIn, worldIn, hand, facing, hitX, hitY, 0, (EntityLivingBase)stack);
/*    */       
/* 51 */       if (!playerIn.setBlockState(worldIn, iblockstate1, 11))
/*    */       {
/* 53 */         return EnumActionResult.FAIL;
/*    */       }
/*    */ 
/*    */       
/* 57 */       iblockstate1 = playerIn.getBlockState(worldIn);
/*    */       
/* 59 */       if (iblockstate1.getBlock() == this.block) {
/*    */         
/* 61 */         ItemBlock.setTileEntityNBT(playerIn, stack, worldIn, itemstack);
/* 62 */         iblockstate1.getBlock().onBlockPlacedBy(playerIn, worldIn, iblockstate1, (EntityLivingBase)stack, itemstack);
/*    */         
/* 64 */         if (stack instanceof EntityPlayerMP)
/*    */         {
/* 66 */           CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*    */         }
/*    */       } 
/*    */       
/* 70 */       SoundType soundtype = this.block.getSoundType();
/* 71 */       playerIn.playSound(stack, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/* 72 */       itemstack.func_190918_g(1);
/* 73 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 78 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBlockSpecial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
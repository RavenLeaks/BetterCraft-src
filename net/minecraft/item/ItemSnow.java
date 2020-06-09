/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSnow extends ItemBlock {
/*    */   public ItemSnow(Block block) {
/* 22 */     super(block);
/* 23 */     setMaxDamage(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 31 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 33 */     if (!itemstack.func_190926_b() && stack.canPlayerEdit(worldIn, hand, itemstack)) {
/*    */       
/* 35 */       IBlockState iblockstate = playerIn.getBlockState(worldIn);
/* 36 */       Block block = iblockstate.getBlock();
/* 37 */       BlockPos blockpos = worldIn;
/*    */       
/* 39 */       if ((hand != EnumFacing.UP || block != this.block) && !block.isReplaceable((IBlockAccess)playerIn, worldIn)) {
/*    */         
/* 41 */         blockpos = worldIn.offset(hand);
/* 42 */         iblockstate = playerIn.getBlockState(blockpos);
/* 43 */         block = iblockstate.getBlock();
/*    */       } 
/*    */       
/* 46 */       if (block == this.block) {
/*    */         
/* 48 */         int i = ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue();
/*    */         
/* 50 */         if (i < 8) {
/*    */           
/* 52 */           IBlockState iblockstate1 = iblockstate.withProperty((IProperty)BlockSnow.LAYERS, Integer.valueOf(i + 1));
/* 53 */           AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox((IBlockAccess)playerIn, blockpos);
/*    */           
/* 55 */           if (axisalignedbb != Block.NULL_AABB && playerIn.checkNoEntityCollision(axisalignedbb.offset(blockpos)) && playerIn.setBlockState(blockpos, iblockstate1, 10)) {
/*    */             
/* 57 */             SoundType soundtype = this.block.getSoundType();
/* 58 */             playerIn.playSound(stack, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/*    */             
/* 60 */             if (stack instanceof EntityPlayerMP)
/*    */             {
/* 62 */               CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*    */             }
/*    */             
/* 65 */             itemstack.func_190918_g(1);
/* 66 */             return EnumActionResult.SUCCESS;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 71 */       return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY);
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 85 */     return damage;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
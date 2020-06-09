/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemDoor extends Item {
/*    */   private final Block block;
/*    */   
/*    */   public ItemDoor(Block block) {
/* 22 */     this.block = block;
/* 23 */     setCreativeTab(CreativeTabs.REDSTONE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 31 */     if (hand != EnumFacing.UP)
/*    */     {
/* 33 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 37 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/* 38 */     Block block = iblockstate.getBlock();
/*    */     
/* 40 */     if (!block.isReplaceable((IBlockAccess)playerIn, worldIn))
/*    */     {
/* 42 */       worldIn = worldIn.offset(hand);
/*    */     }
/*    */     
/* 45 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 47 */     if (stack.canPlayerEdit(worldIn, hand, itemstack) && this.block.canPlaceBlockAt(playerIn, worldIn)) {
/*    */       
/* 49 */       EnumFacing enumfacing = EnumFacing.fromAngle(stack.rotationYaw);
/* 50 */       int i = enumfacing.getFrontOffsetX();
/* 51 */       int j = enumfacing.getFrontOffsetZ();
/* 52 */       boolean flag = !((i >= 0 || hitY >= 0.5F) && (i <= 0 || hitY <= 0.5F) && (j >= 0 || facing <= 0.5F) && (j <= 0 || facing >= 0.5F));
/* 53 */       placeDoor(playerIn, worldIn, enumfacing, this.block, flag);
/* 54 */       SoundType soundtype = this.block.getSoundType();
/* 55 */       playerIn.playSound(stack, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/* 56 */       itemstack.func_190918_g(1);
/* 57 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 61 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
/* 68 */     BlockPos blockpos = pos.offset(facing.rotateY());
/* 69 */     BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
/* 70 */     int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
/* 71 */     int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
/* 72 */     boolean flag = !(worldIn.getBlockState(blockpos1).getBlock() != door && worldIn.getBlockState(blockpos1.up()).getBlock() != door);
/* 73 */     boolean flag1 = !(worldIn.getBlockState(blockpos).getBlock() != door && worldIn.getBlockState(blockpos.up()).getBlock() != door);
/*    */     
/* 75 */     if ((!flag || flag1) && j <= i) {
/*    */       
/* 77 */       if ((flag1 && !flag) || j < i)
/*    */       {
/* 79 */         isRightHinge = false;
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 84 */       isRightHinge = true;
/*    */     } 
/*    */     
/* 87 */     BlockPos blockpos2 = pos.up();
/* 88 */     boolean flag2 = !(!worldIn.isBlockPowered(pos) && !worldIn.isBlockPowered(blockpos2));
/* 89 */     IBlockState iblockstate = door.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)facing).withProperty((IProperty)BlockDoor.HINGE, isRightHinge ? (Comparable)BlockDoor.EnumHingePosition.RIGHT : (Comparable)BlockDoor.EnumHingePosition.LEFT).withProperty((IProperty)BlockDoor.POWERED, Boolean.valueOf(flag2)).withProperty((IProperty)BlockDoor.OPEN, Boolean.valueOf(flag2));
/* 90 */     worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.LOWER), 2);
/* 91 */     worldIn.setBlockState(blockpos2, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), 2);
/* 92 */     worldIn.notifyNeighborsOfStateChange(pos, door, false);
/* 93 */     worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
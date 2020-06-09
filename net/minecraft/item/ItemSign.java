/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.BlockStandingSign;
/*    */ import net.minecraft.block.BlockWallSign;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSign extends Item {
/*    */   public ItemSign() {
/* 24 */     this.maxStackSize = 16;
/* 25 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 33 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/* 34 */     boolean flag = iblockstate.getBlock().isReplaceable((IBlockAccess)playerIn, worldIn);
/*    */     
/* 36 */     if (hand != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || hand == EnumFacing.UP)) {
/*    */       
/* 38 */       worldIn = worldIn.offset(hand);
/* 39 */       ItemStack itemstack = stack.getHeldItem(pos);
/*    */       
/* 41 */       if (stack.canPlayerEdit(worldIn, hand, itemstack) && Blocks.STANDING_SIGN.canPlaceBlockAt(playerIn, worldIn)) {
/*    */         
/* 43 */         if (playerIn.isRemote)
/*    */         {
/* 45 */           return EnumActionResult.SUCCESS;
/*    */         }
/*    */ 
/*    */         
/* 49 */         worldIn = flag ? worldIn.down() : worldIn;
/*    */         
/* 51 */         if (hand == EnumFacing.UP) {
/*    */           
/* 53 */           int i = MathHelper.floor(((stack.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/* 54 */           playerIn.setBlockState(worldIn, Blocks.STANDING_SIGN.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 11);
/*    */         }
/*    */         else {
/*    */           
/* 58 */           playerIn.setBlockState(worldIn, Blocks.WALL_SIGN.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)hand), 11);
/*    */         } 
/*    */         
/* 61 */         TileEntity tileentity = playerIn.getTileEntity(worldIn);
/*    */         
/* 63 */         if (tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(playerIn, stack, worldIn, itemstack))
/*    */         {
/* 65 */           stack.openEditSign((TileEntitySign)tileentity);
/*    */         }
/*    */         
/* 68 */         if (stack instanceof EntityPlayerMP)
/*    */         {
/* 70 */           CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*    */         }
/*    */         
/* 73 */         itemstack.func_190918_g(1);
/* 74 */         return EnumActionResult.SUCCESS;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 79 */       return EnumActionResult.FAIL;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 84 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
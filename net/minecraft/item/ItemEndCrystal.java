/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProviderEnd;
/*    */ import net.minecraft.world.end.DragonFightManager;
/*    */ 
/*    */ public class ItemEndCrystal
/*    */   extends Item {
/*    */   public ItemEndCrystal() {
/* 23 */     setUnlocalizedName("end_crystal");
/* 24 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 32 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*    */     
/* 34 */     if (iblockstate.getBlock() != Blocks.OBSIDIAN && iblockstate.getBlock() != Blocks.BEDROCK)
/*    */     {
/* 36 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 40 */     BlockPos blockpos = worldIn.up();
/* 41 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 43 */     if (!stack.canPlayerEdit(blockpos, hand, itemstack))
/*    */     {
/* 45 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 49 */     BlockPos blockpos1 = blockpos.up();
/* 50 */     boolean flag = (!playerIn.isAirBlock(blockpos) && !playerIn.getBlockState(blockpos).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos));
/* 51 */     int i = flag | ((!playerIn.isAirBlock(blockpos1) && !playerIn.getBlockState(blockpos1).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos1)) ? 1 : 0);
/*    */     
/* 53 */     if (i != 0)
/*    */     {
/* 55 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 59 */     double d0 = blockpos.getX();
/* 60 */     double d1 = blockpos.getY();
/* 61 */     double d2 = blockpos.getZ();
/* 62 */     List<Entity> list = playerIn.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
/*    */     
/* 64 */     if (!list.isEmpty())
/*    */     {
/* 66 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 70 */     if (!playerIn.isRemote) {
/*    */       
/* 72 */       EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(playerIn, (worldIn.getX() + 0.5F), (worldIn.getY() + 1), (worldIn.getZ() + 0.5F));
/* 73 */       entityendercrystal.setShowBottom(false);
/* 74 */       playerIn.spawnEntityInWorld((Entity)entityendercrystal);
/*    */       
/* 76 */       if (playerIn.provider instanceof WorldProviderEnd) {
/*    */         
/* 78 */         DragonFightManager dragonfightmanager = ((WorldProviderEnd)playerIn.provider).getDragonFightManager();
/* 79 */         dragonfightmanager.respawnDragon();
/*    */       } 
/*    */     } 
/*    */     
/* 83 */     itemstack.func_190918_g(1);
/* 84 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 93 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemEndCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
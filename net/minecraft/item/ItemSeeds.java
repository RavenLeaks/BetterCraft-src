/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private final Block crops;
/*    */   private final Block soilBlockID;
/*    */   
/*    */   public ItemSeeds(Block crops, Block soil) {
/* 23 */     this.crops = crops;
/* 24 */     this.soilBlockID = soil;
/* 25 */     setCreativeTab(CreativeTabs.MATERIALS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 33 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 35 */     if (hand == EnumFacing.UP && stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack) && playerIn.getBlockState(worldIn).getBlock() == this.soilBlockID && playerIn.isAirBlock(worldIn.up())) {
/*    */       
/* 37 */       playerIn.setBlockState(worldIn.up(), this.crops.getDefaultState());
/*    */       
/* 39 */       if (stack instanceof EntityPlayerMP)
/*    */       {
/* 41 */         CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn.up(), itemstack);
/*    */       }
/*    */       
/* 44 */       itemstack.func_190918_g(1);
/* 45 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSeeds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
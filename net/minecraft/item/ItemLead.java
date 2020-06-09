/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLead
/*    */   extends Item
/*    */ {
/*    */   public ItemLead() {
/* 20 */     setCreativeTab(CreativeTabs.TOOLS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 28 */     Block block = playerIn.getBlockState(worldIn).getBlock();
/*    */     
/* 30 */     if (!(block instanceof net.minecraft.block.BlockFence))
/*    */     {
/* 32 */       return EnumActionResult.PASS;
/*    */     }
/*    */ 
/*    */     
/* 36 */     if (!playerIn.isRemote)
/*    */     {
/* 38 */       attachToFence(stack, playerIn, worldIn);
/*    */     }
/*    */     
/* 41 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean attachToFence(EntityPlayer player, World worldIn, BlockPos fence) {
/* 47 */     EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(worldIn, fence);
/* 48 */     boolean flag = false;
/* 49 */     double d0 = 7.0D;
/* 50 */     int i = fence.getX();
/* 51 */     int j = fence.getY();
/* 52 */     int k = fence.getZ();
/*    */     
/* 54 */     for (EntityLiving entityliving : worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(i - 7.0D, j - 7.0D, k - 7.0D, i + 7.0D, j + 7.0D, k + 7.0D))) {
/*    */       
/* 56 */       if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player) {
/*    */         
/* 58 */         if (entityleashknot == null)
/*    */         {
/* 60 */           entityleashknot = EntityLeashKnot.createKnot(worldIn, fence);
/*    */         }
/*    */         
/* 63 */         entityliving.setLeashedToEntity((Entity)entityleashknot, true);
/* 64 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemLead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
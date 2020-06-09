/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemEmptyMap
/*    */   extends ItemMapBase
/*    */ {
/*    */   protected ItemEmptyMap() {
/* 15 */     setCreativeTab(CreativeTabs.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 20 */     ItemStack itemstack = ItemMap.func_190906_a(itemStackIn, worldIn.posX, worldIn.posZ, (byte)0, true, false);
/* 21 */     ItemStack itemstack1 = worldIn.getHeldItem(playerIn);
/* 22 */     itemstack1.func_190918_g(1);
/*    */     
/* 24 */     if (itemstack1.func_190926_b())
/*    */     {
/* 26 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */     }
/*    */ 
/*    */     
/* 30 */     if (!worldIn.inventory.addItemStackToInventory(itemstack.copy()))
/*    */     {
/* 32 */       worldIn.dropItem(itemstack, false);
/*    */     }
/*    */     
/* 35 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 36 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack1);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemEmptyMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
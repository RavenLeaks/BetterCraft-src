/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemWritableBook
/*    */   extends Item
/*    */ {
/*    */   public ItemWritableBook() {
/* 16 */     setMaxStackSize(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 21 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 22 */     worldIn.openBook(itemstack, playerIn);
/* 23 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 24 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNBTValid(NBTTagCompound nbt) {
/* 32 */     if (nbt == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/* 36 */     if (!nbt.hasKey("pages", 9))
/*    */     {
/* 38 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 42 */     NBTTagList nbttaglist = nbt.getTagList("pages", 8);
/*    */     
/* 44 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */       
/* 46 */       String s = nbttaglist.getStringTagAt(i);
/*    */       
/* 48 */       if (s.length() > 32767)
/*    */       {
/* 50 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemWritableBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
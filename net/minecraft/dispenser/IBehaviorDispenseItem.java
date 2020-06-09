/*    */ package net.minecraft.dispenser;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public interface IBehaviorDispenseItem
/*    */ {
/*  7 */   public static final IBehaviorDispenseItem DEFAULT_BEHAVIOR = new IBehaviorDispenseItem()
/*    */     {
/*    */       public ItemStack dispense(IBlockSource source, ItemStack stack)
/*    */       {
/* 11 */         return stack;
/*    */       }
/*    */     };
/*    */   
/*    */   ItemStack dispense(IBlockSource paramIBlockSource, ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\dispenser\IBehaviorDispenseItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
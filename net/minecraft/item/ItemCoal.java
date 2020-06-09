/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ public class ItemCoal
/*    */   extends Item
/*    */ {
/*    */   public ItemCoal() {
/* 10 */     setHasSubtypes(true);
/* 11 */     setMaxDamage(0);
/* 12 */     setCreativeTab(CreativeTabs.MATERIALS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 21 */     return (stack.getMetadata() == 1) ? "item.charcoal" : "item.coal";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 29 */     if (func_194125_a(itemIn)) {
/*    */       
/* 31 */       tab.add(new ItemStack(this, 1, 0));
/* 32 */       tab.add(new ItemStack(this, 1, 1));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemCoal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
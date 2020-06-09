/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IRecipe
/*    */ {
/*    */   boolean matches(InventoryCrafting paramInventoryCrafting, World paramWorld);
/*    */   
/*    */   ItemStack getCraftingResult(InventoryCrafting paramInventoryCrafting);
/*    */   
/*    */   boolean func_194133_a(int paramInt1, int paramInt2);
/*    */   
/*    */   ItemStack getRecipeOutput();
/*    */   
/*    */   NonNullList<ItemStack> getRemainingItems(InventoryCrafting paramInventoryCrafting);
/*    */   
/*    */   default NonNullList<Ingredient> func_192400_c() {
/* 28 */     return NonNullList.func_191196_a();
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean func_192399_d() {
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   default String func_193358_e() {
/* 38 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\IRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
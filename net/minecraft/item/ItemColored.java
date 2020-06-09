/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemColored
/*    */   extends ItemBlock
/*    */ {
/*    */   private String[] subtypeNames;
/*    */   
/*    */   public ItemColored(Block block, boolean hasSubtypes) {
/* 11 */     super(block);
/*    */     
/* 13 */     if (hasSubtypes) {
/*    */       
/* 15 */       setMaxDamage(0);
/* 16 */       setHasSubtypes(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 26 */     return damage;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemColored setSubtypeNames(String[] names) {
/* 31 */     this.subtypeNames = names;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 41 */     if (this.subtypeNames == null)
/*    */     {
/* 43 */       return super.getUnlocalizedName(stack);
/*    */     }
/*    */ 
/*    */     
/* 47 */     int i = stack.getMetadata();
/* 48 */     return (i >= 0 && i < this.subtypeNames.length) ? (String.valueOf(super.getUnlocalizedName(stack)) + "." + this.subtypeNames[i]) : super.getUnlocalizedName(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemColored.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
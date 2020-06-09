/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemMultiTexture
/*    */   extends ItemBlock
/*    */ {
/*    */   protected final Block theBlock;
/*    */   protected final Mapper nameFunction;
/*    */   
/*    */   public ItemMultiTexture(Block p_i47262_1_, Block p_i47262_2_, Mapper p_i47262_3_) {
/* 12 */     super(p_i47262_1_);
/* 13 */     this.theBlock = p_i47262_2_;
/* 14 */     this.nameFunction = p_i47262_3_;
/* 15 */     setMaxDamage(0);
/* 16 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemMultiTexture(Block block, Block block2, String[] namesByMeta) {
/* 21 */     this(block, block2, new Mapper(namesByMeta)
/*    */         {
/*    */           public String apply(ItemStack p_apply_1_)
/*    */           {
/* 25 */             int i = p_apply_1_.getMetadata();
/*    */             
/* 27 */             if (i < 0 || i >= namesByMeta.length)
/*    */             {
/* 29 */               i = 0;
/*    */             }
/*    */             
/* 32 */             return namesByMeta[i];
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 43 */     return damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 52 */     return String.valueOf(getUnlocalizedName()) + "." + this.nameFunction.apply(stack);
/*    */   }
/*    */   
/*    */   public static interface Mapper {
/*    */     String apply(ItemStack param1ItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemMultiTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
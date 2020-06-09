/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemAir
/*    */   extends Item
/*    */ {
/*    */   private final Block field_190904_a;
/*    */   
/*    */   public ItemAir(Block p_i47264_1_) {
/* 15 */     this.field_190904_a = p_i47264_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 24 */     return this.field_190904_a.getUnlocalizedName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName() {
/* 32 */     return this.field_190904_a.getUnlocalizedName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 40 */     super.addInformation(stack, playerIn, tooltip, advanced);
/* 41 */     this.field_190904_a.func_190948_a(stack, playerIn, tooltip, advanced);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemAir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
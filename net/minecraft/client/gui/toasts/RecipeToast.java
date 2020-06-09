/*    */ package net.minecraft.client.gui.toasts;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ 
/*    */ public class RecipeToast
/*    */   implements IToast
/*    */ {
/* 14 */   private final List<ItemStack> field_193666_c = Lists.newArrayList();
/*    */   
/*    */   private long field_193667_d;
/*    */   private boolean field_193668_e;
/*    */   
/*    */   public RecipeToast(ItemStack p_i47489_1_) {
/* 20 */     this.field_193666_c.add(p_i47489_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
/* 25 */     if (this.field_193668_e) {
/*    */       
/* 27 */       this.field_193667_d = p_193653_2_;
/* 28 */       this.field_193668_e = false;
/*    */     } 
/*    */     
/* 31 */     if (this.field_193666_c.isEmpty())
/*    */     {
/* 33 */       return IToast.Visibility.HIDE;
/*    */     }
/*    */ 
/*    */     
/* 37 */     p_193653_1_.func_192989_b().getTextureManager().bindTexture(field_193654_a);
/* 38 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 39 */     p_193653_1_.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
/* 40 */     (p_193653_1_.func_192989_b()).fontRendererObj.drawString(I18n.format("recipe.toast.title", new Object[0]), 30, 7, -11534256);
/* 41 */     (p_193653_1_.func_192989_b()).fontRendererObj.drawString(I18n.format("recipe.toast.description", new Object[0]), 30, 18, -16777216);
/* 42 */     RenderHelper.enableGUIStandardItemLighting();
/* 43 */     p_193653_1_.func_192989_b().getRenderItem().renderItemAndEffectIntoGUI(null, this.field_193666_c.get((int)(p_193653_2_ / 5000L / this.field_193666_c.size() % this.field_193666_c.size())), 8, 8);
/* 44 */     return (p_193653_2_ - this.field_193667_d >= 5000L) ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_193664_a(ItemStack p_193664_1_) {
/* 50 */     if (this.field_193666_c.add(p_193664_1_))
/*    */     {
/* 52 */       this.field_193668_e = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_193665_a(GuiToast p_193665_0_, IRecipe p_193665_1_) {
/* 58 */     RecipeToast recipetoast = p_193665_0_.<RecipeToast>func_192990_a(RecipeToast.class, field_193655_b);
/*    */     
/* 60 */     if (recipetoast == null) {
/*    */       
/* 62 */       p_193665_0_.func_192988_a(new RecipeToast(p_193665_1_.getRecipeOutput()));
/*    */     }
/*    */     else {
/*    */       
/* 66 */       recipetoast.func_193664_a(p_193665_1_.getRecipeOutput());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\toasts\RecipeToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
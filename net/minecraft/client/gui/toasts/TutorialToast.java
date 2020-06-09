/*    */ package net.minecraft.client.gui.toasts;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class TutorialToast
/*    */   implements IToast {
/*    */   private final Icons field_193671_c;
/*    */   private final String field_193672_d;
/*    */   private final String field_193673_e;
/* 14 */   private IToast.Visibility field_193674_f = IToast.Visibility.SHOW;
/*    */   
/*    */   private long field_193675_g;
/*    */   private float field_193676_h;
/*    */   private float field_193677_i;
/*    */   private final boolean field_193678_j;
/*    */   
/*    */   public TutorialToast(Icons p_i47487_1_, ITextComponent p_i47487_2_, @Nullable ITextComponent p_i47487_3_, boolean p_i47487_4_) {
/* 22 */     this.field_193671_c = p_i47487_1_;
/* 23 */     this.field_193672_d = p_i47487_2_.getFormattedText();
/* 24 */     this.field_193673_e = (p_i47487_3_ == null) ? null : p_i47487_3_.getFormattedText();
/* 25 */     this.field_193678_j = p_i47487_4_;
/*    */   }
/*    */ 
/*    */   
/*    */   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
/* 30 */     p_193653_1_.func_192989_b().getTextureManager().bindTexture(field_193654_a);
/* 31 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 32 */     p_193653_1_.drawTexturedModalRect(0, 0, 0, 96, 160, 32);
/* 33 */     this.field_193671_c.func_193697_a(p_193653_1_, 6, 6);
/*    */     
/* 35 */     if (this.field_193673_e == null) {
/*    */       
/* 37 */       (p_193653_1_.func_192989_b()).fontRendererObj.drawString(this.field_193672_d, 30, 12, -11534256);
/*    */     }
/*    */     else {
/*    */       
/* 41 */       (p_193653_1_.func_192989_b()).fontRendererObj.drawString(this.field_193672_d, 30, 7, -11534256);
/* 42 */       (p_193653_1_.func_192989_b()).fontRendererObj.drawString(this.field_193673_e, 30, 18, -16777216);
/*    */     } 
/*    */     
/* 45 */     if (this.field_193678_j) {
/*    */       int i;
/* 47 */       Gui.drawRect(3, 28, 157, 29, -1);
/* 48 */       float f = (float)MathHelper.clampedLerp(this.field_193676_h, this.field_193677_i, ((float)(p_193653_2_ - this.field_193675_g) / 100.0F));
/*    */ 
/*    */       
/* 51 */       if (this.field_193677_i >= this.field_193676_h) {
/*    */         
/* 53 */         i = -16755456;
/*    */       }
/*    */       else {
/*    */         
/* 57 */         i = -11206656;
/*    */       } 
/*    */       
/* 60 */       Gui.drawRect(3, 28, (int)(3.0F + 154.0F * f), 29, i);
/* 61 */       this.field_193676_h = f;
/* 62 */       this.field_193675_g = p_193653_2_;
/*    */     } 
/*    */     
/* 65 */     return this.field_193674_f;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193670_a() {
/* 70 */     this.field_193674_f = IToast.Visibility.HIDE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193669_a(float p_193669_1_) {
/* 75 */     this.field_193677_i = p_193669_1_;
/*    */   }
/*    */   
/*    */   public enum Icons
/*    */   {
/* 80 */     MOVEMENT_KEYS(0, 0),
/* 81 */     MOUSE(1, 0),
/* 82 */     TREE(2, 0),
/* 83 */     RECIPE_BOOK(0, 1),
/* 84 */     WOODEN_PLANKS(1, 1);
/*    */     
/*    */     private final int field_193703_f;
/*    */     
/*    */     private final int field_193704_g;
/*    */     
/*    */     Icons(int p_i47576_3_, int p_i47576_4_) {
/* 91 */       this.field_193703_f = p_i47576_3_;
/* 92 */       this.field_193704_g = p_i47576_4_;
/*    */     }
/*    */ 
/*    */     
/*    */     public void func_193697_a(Gui p_193697_1_, int p_193697_2_, int p_193697_3_) {
/* 97 */       GlStateManager.enableBlend();
/* 98 */       p_193697_1_.drawTexturedModalRect(p_193697_2_, p_193697_3_, 176 + this.field_193703_f * 20, this.field_193704_g * 20, 20, 20);
/* 99 */       GlStateManager.enableBlend();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\toasts\TutorialToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.gui.toasts.IToast;
/*    */ import net.minecraft.client.gui.toasts.TutorialToast;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.GameType;
/*    */ 
/*    */ public class OpenInventoryStep implements ITutorialStep {
/* 10 */   private static final ITextComponent field_193281_a = (ITextComponent)new TextComponentTranslation("tutorial.open_inventory.title", new Object[0]);
/* 11 */   private static final ITextComponent field_193282_b = (ITextComponent)new TextComponentTranslation("tutorial.open_inventory.description", new Object[] { Tutorial.func_193291_a("inventory") });
/*    */   
/*    */   private final Tutorial field_193283_c;
/*    */   private TutorialToast field_193284_d;
/*    */   private int field_193285_e;
/*    */   
/*    */   public OpenInventoryStep(Tutorial p_i47580_1_) {
/* 18 */     this.field_193283_c = p_i47580_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193245_a() {
/* 23 */     this.field_193285_e++;
/*    */     
/* 25 */     if (this.field_193283_c.func_194072_f() != GameType.SURVIVAL) {
/*    */       
/* 27 */       this.field_193283_c.func_193292_a(TutorialSteps.NONE);
/*    */ 
/*    */     
/*    */     }
/* 31 */     else if (this.field_193285_e >= 600 && this.field_193284_d == null) {
/*    */       
/* 33 */       this.field_193284_d = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, field_193281_a, field_193282_b, false);
/* 34 */       this.field_193283_c.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193284_d);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_193248_b() {
/* 41 */     if (this.field_193284_d != null) {
/*    */       
/* 43 */       this.field_193284_d.func_193670_a();
/* 44 */       this.field_193284_d = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193251_c() {
/* 50 */     this.field_193283_c.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\OpenInventoryStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
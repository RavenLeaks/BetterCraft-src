/*    */ package net.minecraft.client.gui.toasts;
/*    */ 
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.client.audio.SoundHandler;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ public interface IToast {
/* 11 */   public static final ResourceLocation field_193654_a = new ResourceLocation("textures/gui/toasts.png");
/* 12 */   public static final Object field_193655_b = new Object();
/*    */ 
/*    */   
/*    */   Visibility func_193653_a(GuiToast paramGuiToast, long paramLong);
/*    */   
/*    */   default Object func_193652_b() {
/* 18 */     return field_193655_b;
/*    */   }
/*    */   
/*    */   public enum Visibility
/*    */   {
/* 23 */     SHOW((String)SoundEvents.field_194226_id),
/* 24 */     HIDE((String)SoundEvents.field_194227_ie);
/*    */     
/*    */     private final SoundEvent field_194170_c;
/*    */ 
/*    */     
/*    */     Visibility(SoundEvent p_i47607_3_) {
/* 30 */       this.field_194170_c = p_i47607_3_;
/*    */     }
/*    */ 
/*    */     
/*    */     public void func_194169_a(SoundHandler p_194169_1_) {
/* 35 */       p_194169_1_.playSound((ISound)PositionedSoundRecord.func_194007_a(this.field_194170_c, 1.0F, 1.0F));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\toasts\IToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
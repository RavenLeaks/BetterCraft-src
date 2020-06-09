/*    */ package net.minecraft.client.gui.toasts;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.DisplayInfo;
/*    */ import net.minecraft.advancements.FrameType;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class AdvancementToast
/*    */   implements IToast
/*    */ {
/*    */   private final Advancement field_193679_c;
/*    */   private boolean field_194168_d = false;
/*    */   
/*    */   public AdvancementToast(Advancement p_i47490_1_) {
/* 22 */     this.field_193679_c = p_i47490_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
/* 27 */     p_193653_1_.func_192989_b().getTextureManager().bindTexture(field_193654_a);
/* 28 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 29 */     DisplayInfo displayinfo = this.field_193679_c.func_192068_c();
/* 30 */     p_193653_1_.drawTexturedModalRect(0, 0, 0, 0, 160, 32);
/*    */     
/* 32 */     if (displayinfo != null) {
/*    */       
/* 34 */       List<String> list = (p_193653_1_.func_192989_b()).fontRendererObj.listFormattedStringToWidth(displayinfo.func_192297_a().getFormattedText(), 125);
/* 35 */       int i = (displayinfo.func_192291_d() == FrameType.CHALLENGE) ? 16746751 : 16776960;
/*    */       
/* 37 */       if (list.size() == 1) {
/*    */         
/* 39 */         (p_193653_1_.func_192989_b()).fontRendererObj.drawString(I18n.format("advancements.toast." + displayinfo.func_192291_d().func_192307_a(), new Object[0]), 30, 7, i | 0xFF000000);
/* 40 */         (p_193653_1_.func_192989_b()).fontRendererObj.drawString(displayinfo.func_192297_a().getFormattedText(), 30, 18, -1);
/*    */       }
/*    */       else {
/*    */         
/* 44 */         int j = 1500;
/* 45 */         float f = 300.0F;
/*    */         
/* 47 */         if (p_193653_2_ < 1500L) {
/*    */           
/* 49 */           int k = MathHelper.floor(MathHelper.clamp((float)(1500L - p_193653_2_) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 0x4000000;
/* 50 */           (p_193653_1_.func_192989_b()).fontRendererObj.drawString(I18n.format("advancements.toast." + displayinfo.func_192291_d().func_192307_a(), new Object[0]), 30, 11, i | k);
/*    */         }
/*    */         else {
/*    */           
/* 54 */           int i1 = MathHelper.floor(MathHelper.clamp((float)(p_193653_2_ - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 0x4000000;
/* 55 */           int l = 16 - list.size() * (p_193653_1_.func_192989_b()).fontRendererObj.FONT_HEIGHT / 2;
/*    */           
/* 57 */           for (String s : list) {
/*    */             
/* 59 */             (p_193653_1_.func_192989_b()).fontRendererObj.drawString(s, 30, l, 0xFFFFFF | i1);
/* 60 */             l += (p_193653_1_.func_192989_b()).fontRendererObj.FONT_HEIGHT;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 65 */       if (!this.field_194168_d && p_193653_2_ > 0L) {
/*    */         
/* 67 */         this.field_194168_d = true;
/*    */         
/* 69 */         if (displayinfo.func_192291_d() == FrameType.CHALLENGE)
/*    */         {
/* 71 */           p_193653_1_.func_192989_b().getSoundHandler().playSound((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_194228_if, 1.0F, 1.0F));
/*    */         }
/*    */       } 
/*    */       
/* 75 */       RenderHelper.enableGUIStandardItemLighting();
/* 76 */       p_193653_1_.func_192989_b().getRenderItem().renderItemAndEffectIntoGUI(null, displayinfo.func_192298_b(), 8, 8);
/* 77 */       return (p_193653_2_ >= 5000L) ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
/*    */     } 
/*    */ 
/*    */     
/* 81 */     return IToast.Visibility.HIDE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\toasts\AdvancementToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
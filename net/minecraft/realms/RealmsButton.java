/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiButtonRealmsProxy;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RealmsButton
/*    */ {
/* 10 */   protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
/*    */   
/*    */   private final GuiButtonRealmsProxy proxy;
/*    */   
/*    */   public RealmsButton(int buttonId, int x, int y, String text) {
/* 15 */     this.proxy = new GuiButtonRealmsProxy(this, buttonId, x, y, text);
/*    */   }
/*    */ 
/*    */   
/*    */   public RealmsButton(int buttonId, int x, int y, int widthIn, int heightIn, String text) {
/* 20 */     this.proxy = new GuiButtonRealmsProxy(this, buttonId, x, y, text, widthIn, heightIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiButton getProxy() {
/* 25 */     return (GuiButton)this.proxy;
/*    */   }
/*    */ 
/*    */   
/*    */   public int id() {
/* 30 */     return this.proxy.getId();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean active() {
/* 35 */     return this.proxy.getEnabled();
/*    */   }
/*    */ 
/*    */   
/*    */   public void active(boolean p_active_1_) {
/* 40 */     this.proxy.setEnabled(p_active_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void msg(String p_msg_1_) {
/* 45 */     this.proxy.setText(p_msg_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 50 */     return this.proxy.getButtonWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 55 */     return this.proxy.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public int y() {
/* 60 */     return this.proxy.getPositionY();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
/* 65 */     this.proxy.func_191745_a(Minecraft.getMinecraft(), p_render_1_, p_render_2_, p_render_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clicked(int p_clicked_1_, int p_clicked_2_) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void released(int p_released_1_, int p_released_2_) {}
/*    */ 
/*    */   
/*    */   public void blit(int p_blit_1_, int p_blit_2_, int p_blit_3_, int p_blit_4_, int p_blit_5_, int p_blit_6_) {
/* 78 */     this.proxy.drawTexturedModalRect(p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBg(int p_renderBg_1_, int p_renderBg_2_) {}
/*    */ 
/*    */   
/*    */   public int getYImage(boolean p_getYImage_1_) {
/* 87 */     return this.proxy.getYImage(p_getYImage_1_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
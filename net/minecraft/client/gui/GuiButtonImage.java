/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiButtonImage
/*    */   extends GuiButton
/*    */ {
/*    */   private final ResourceLocation field_191750_o;
/*    */   private final int field_191747_p;
/*    */   private final int field_191748_q;
/*    */   private final int field_191749_r;
/*    */   
/*    */   public GuiButtonImage(int p_i47392_1_, int p_i47392_2_, int p_i47392_3_, int p_i47392_4_, int p_i47392_5_, int p_i47392_6_, int p_i47392_7_, int p_i47392_8_, ResourceLocation p_i47392_9_) {
/* 16 */     super(p_i47392_1_, p_i47392_2_, p_i47392_3_, p_i47392_4_, p_i47392_5_, "");
/* 17 */     this.field_191747_p = p_i47392_6_;
/* 18 */     this.field_191748_q = p_i47392_7_;
/* 19 */     this.field_191749_r = p_i47392_8_;
/* 20 */     this.field_191750_o = p_i47392_9_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191746_c(int p_191746_1_, int p_191746_2_) {
/* 25 */     this.xPosition = p_191746_1_;
/* 26 */     this.yPosition = p_191746_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 31 */     if (this.visible) {
/*    */       
/* 33 */       this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 34 */       p_191745_1_.getTextureManager().bindTexture(this.field_191750_o);
/* 35 */       GlStateManager.disableDepth();
/* 36 */       int i = this.field_191747_p;
/* 37 */       int j = this.field_191748_q;
/*    */       
/* 39 */       if (this.hovered)
/*    */       {
/* 41 */         j += this.field_191749_r;
/*    */       }
/*    */       
/* 44 */       drawTexturedModalRect(this.xPosition, this.yPosition, i, j, this.width, this.height);
/* 45 */       GlStateManager.enableDepth();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiButtonImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
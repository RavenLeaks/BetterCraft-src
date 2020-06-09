/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiButtonToggle
/*    */   extends GuiButton
/*    */ {
/*    */   protected ResourceLocation field_191760_o;
/*    */   protected boolean field_191755_p;
/*    */   protected int field_191756_q;
/*    */   protected int field_191757_r;
/*    */   protected int field_191758_s;
/*    */   protected int field_191759_t;
/*    */   
/*    */   public GuiButtonToggle(int p_i47389_1_, int p_i47389_2_, int p_i47389_3_, int p_i47389_4_, int p_i47389_5_, boolean p_i47389_6_) {
/* 18 */     super(p_i47389_1_, p_i47389_2_, p_i47389_3_, p_i47389_4_, p_i47389_5_, "");
/* 19 */     this.field_191755_p = p_i47389_6_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191751_a(int p_191751_1_, int p_191751_2_, int p_191751_3_, int p_191751_4_, ResourceLocation p_191751_5_) {
/* 24 */     this.field_191756_q = p_191751_1_;
/* 25 */     this.field_191757_r = p_191751_2_;
/* 26 */     this.field_191758_s = p_191751_3_;
/* 27 */     this.field_191759_t = p_191751_4_;
/* 28 */     this.field_191760_o = p_191751_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191753_b(boolean p_191753_1_) {
/* 33 */     this.field_191755_p = p_191753_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_191754_c() {
/* 38 */     return this.field_191755_p;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191752_c(int p_191752_1_, int p_191752_2_) {
/* 43 */     this.xPosition = p_191752_1_;
/* 44 */     this.yPosition = p_191752_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 49 */     if (this.visible) {
/*    */       
/* 51 */       this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 52 */       p_191745_1_.getTextureManager().bindTexture(this.field_191760_o);
/* 53 */       GlStateManager.disableDepth();
/* 54 */       int i = this.field_191756_q;
/* 55 */       int j = this.field_191757_r;
/*    */       
/* 57 */       if (this.field_191755_p)
/*    */       {
/* 59 */         i += this.field_191758_s;
/*    */       }
/*    */       
/* 62 */       if (this.hovered)
/*    */       {
/* 64 */         j += this.field_191759_t;
/*    */       }
/*    */       
/* 67 */       drawTexturedModalRect(this.xPosition, this.yPosition, i, j, this.width, this.height);
/* 68 */       GlStateManager.enableDepth();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiButtonToggle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
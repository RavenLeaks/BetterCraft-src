/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiLabel
/*    */   extends Gui {
/* 11 */   protected int width = 200;
/* 12 */   protected int height = 20;
/*    */   
/*    */   public int x;
/*    */   public int y;
/*    */   private final List<String> labels;
/*    */   public int id;
/*    */   private boolean centered;
/*    */   public boolean visible = true;
/*    */   private boolean labelBgEnabled;
/*    */   private final int textColor;
/*    */   private int backColor;
/*    */   private int ulColor;
/*    */   private int brColor;
/*    */   private final FontRenderer fontRenderer;
/*    */   private int border;
/*    */   
/*    */   public GuiLabel(FontRenderer fontRendererObj, int p_i45540_2_, int p_i45540_3_, int p_i45540_4_, int p_i45540_5_, int p_i45540_6_, int p_i45540_7_) {
/* 29 */     this.fontRenderer = fontRendererObj;
/* 30 */     this.id = p_i45540_2_;
/* 31 */     this.x = p_i45540_3_;
/* 32 */     this.y = p_i45540_4_;
/* 33 */     this.width = p_i45540_5_;
/* 34 */     this.height = p_i45540_6_;
/* 35 */     this.labels = Lists.newArrayList();
/* 36 */     this.centered = false;
/* 37 */     this.labelBgEnabled = false;
/* 38 */     this.textColor = p_i45540_7_;
/* 39 */     this.backColor = -1;
/* 40 */     this.ulColor = -1;
/* 41 */     this.brColor = -1;
/* 42 */     this.border = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addLine(String p_175202_1_) {
/* 47 */     this.labels.add(I18n.format(p_175202_1_, new Object[0]));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiLabel setCentered() {
/* 55 */     this.centered = true;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawLabel(Minecraft mc, int mouseX, int mouseY) {
/* 61 */     if (this.visible) {
/*    */       
/* 63 */       GlStateManager.enableBlend();
/* 64 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 65 */       drawLabelBackground(mc, mouseX, mouseY);
/* 66 */       int i = this.y + this.height / 2 + this.border / 2;
/* 67 */       int j = i - this.labels.size() * 10 / 2;
/*    */       
/* 69 */       for (int k = 0; k < this.labels.size(); k++) {
/*    */         
/* 71 */         if (this.centered) {
/*    */           
/* 73 */           drawCenteredString(this.fontRenderer, this.labels.get(k), this.x + this.width / 2, j + k * 10, this.textColor);
/*    */         }
/*    */         else {
/*    */           
/* 77 */           drawString(this.fontRenderer, this.labels.get(k), this.x, j + k * 10, this.textColor);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawLabelBackground(Minecraft mcIn, int p_146160_2_, int p_146160_3_) {
/* 85 */     if (this.labelBgEnabled) {
/*    */       
/* 87 */       int i = this.width + this.border * 2;
/* 88 */       int j = this.height + this.border * 2;
/* 89 */       int k = this.x - this.border;
/* 90 */       int l = this.y - this.border;
/* 91 */       drawRect(k, l, k + i, l + j, this.backColor);
/* 92 */       drawHorizontalLine(k, k + i, l, this.ulColor);
/* 93 */       drawHorizontalLine(k, k + i, l + j, this.brColor);
/* 94 */       drawVerticalLine(k, l, l + j, this.ulColor);
/* 95 */       drawVerticalLine(k + i, l, l + j, this.brColor);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
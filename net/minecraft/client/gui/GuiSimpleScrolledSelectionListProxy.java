/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiSimpleScrolledSelectionListProxy
/*     */   extends GuiSlot
/*     */ {
/*     */   private final RealmsSimpleScrolledSelectionList realmsScrolledSelectionList;
/*     */   
/*     */   public GuiSimpleScrolledSelectionListProxy(RealmsSimpleScrolledSelectionList p_i45525_1_, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  17 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  18 */     this.realmsScrolledSelectionList = p_i45525_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  23 */     return this.realmsScrolledSelectionList.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/*  31 */     this.realmsScrolledSelectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  39 */     return this.realmsScrolledSelectionList.isSelectedItem(slotIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawBackground() {
/*  44 */     this.realmsScrolledSelectionList.renderBackground();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/*  49 */     this.realmsScrolledSelectionList.renderItem(p_192637_1_, p_192637_2_, p_192637_3_, p_192637_4_, p_192637_5_, p_192637_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  54 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseY() {
/*  59 */     return this.mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseX() {
/*  64 */     return this.mouseX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/*  72 */     return this.realmsScrolledSelectionList.getMaxPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  77 */     return this.realmsScrolledSelectionList.getScrollbarPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/*  82 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
/*  87 */     if (this.visible) {
/*     */       
/*  89 */       this.mouseX = mouseXIn;
/*  90 */       this.mouseY = mouseYIn;
/*  91 */       drawBackground();
/*  92 */       int i = getScrollBarX();
/*  93 */       int j = i + 6;
/*  94 */       bindAmountScrolled();
/*  95 */       GlStateManager.disableLighting();
/*  96 */       GlStateManager.disableFog();
/*  97 */       Tessellator tessellator = Tessellator.getInstance();
/*  98 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  99 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 100 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 102 */       if (this.hasListHeader)
/*     */       {
/* 104 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 107 */       func_192638_a(k, l, mouseXIn, mouseYIn, partialTicks);
/* 108 */       GlStateManager.disableDepth();
/* 109 */       overlayBackground(0, this.top, 255, 255);
/* 110 */       overlayBackground(this.bottom, this.height, 255, 255);
/* 111 */       GlStateManager.enableBlend();
/* 112 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/* 113 */       GlStateManager.disableAlpha();
/* 114 */       GlStateManager.shadeModel(7425);
/* 115 */       GlStateManager.disableTexture2D();
/* 116 */       int i1 = getMaxScroll();
/*     */       
/* 118 */       if (i1 > 0) {
/*     */         
/* 120 */         int j1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 121 */         j1 = MathHelper.clamp(j1, 32, this.bottom - this.top - 8);
/* 122 */         int k1 = (int)this.amountScrolled * (this.bottom - this.top - j1) / i1 + this.top;
/*     */         
/* 124 */         if (k1 < this.top)
/*     */         {
/* 126 */           k1 = this.top;
/*     */         }
/*     */         
/* 129 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 130 */         bufferbuilder.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 131 */         bufferbuilder.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 132 */         bufferbuilder.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 133 */         bufferbuilder.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 134 */         tessellator.draw();
/* 135 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 136 */         bufferbuilder.pos(i, (k1 + j1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 137 */         bufferbuilder.pos(j, (k1 + j1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 138 */         bufferbuilder.pos(j, k1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 139 */         bufferbuilder.pos(i, k1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 140 */         tessellator.draw();
/* 141 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 142 */         bufferbuilder.pos(i, (k1 + j1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 143 */         bufferbuilder.pos((j - 1), (k1 + j1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 144 */         bufferbuilder.pos((j - 1), k1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 145 */         bufferbuilder.pos(i, k1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 146 */         tessellator.draw();
/*     */       } 
/*     */       
/* 149 */       renderDecorations(mouseXIn, mouseYIn);
/* 150 */       GlStateManager.enableTexture2D();
/* 151 */       GlStateManager.shadeModel(7424);
/* 152 */       GlStateManager.enableAlpha();
/* 153 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSimpleScrolledSelectionListProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
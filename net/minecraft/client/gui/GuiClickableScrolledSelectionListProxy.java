/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.realms.RealmsClickableScrolledSelectionList;
/*     */ import net.minecraft.realms.Tezzelator;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiClickableScrolledSelectionListProxy
/*     */   extends GuiSlot
/*     */ {
/*     */   private final RealmsClickableScrolledSelectionList proxy;
/*     */   
/*     */   public GuiClickableScrolledSelectionListProxy(RealmsClickableScrolledSelectionList selectionList, int p_i45526_2_, int p_i45526_3_, int p_i45526_4_, int p_i45526_5_, int p_i45526_6_) {
/*  14 */     super(Minecraft.getMinecraft(), p_i45526_2_, p_i45526_3_, p_i45526_4_, p_i45526_5_, p_i45526_6_);
/*  15 */     this.proxy = selectionList;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  20 */     return this.proxy.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/*  28 */     this.proxy.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  36 */     return this.proxy.isSelectedItem(slotIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawBackground() {
/*  41 */     this.proxy.renderBackground();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/*  46 */     this.proxy.renderItem(p_192637_1_, p_192637_2_, p_192637_3_, p_192637_4_, p_192637_5_, p_192637_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int width() {
/*  51 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int mouseY() {
/*  56 */     return this.mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int mouseX() {
/*  61 */     return this.mouseX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/*  69 */     return this.proxy.getMaxPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  74 */     return this.proxy.getScrollbarPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/*  79 */     super.handleMouseInput();
/*     */     
/*  81 */     if (this.scrollMultiplier > 0.0F && Mouse.getEventButtonState())
/*     */     {
/*  83 */       this.proxy.customMouseEvent(this.top, this.bottom, this.headerPadding, this.amountScrolled, this.slotHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSelected(int p_178043_1_, int p_178043_2_, int p_178043_3_, Tezzelator p_178043_4_) {
/*  89 */     this.proxy.renderSelected(p_178043_1_, p_178043_2_, p_178043_3_, p_178043_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192638_a(int p_192638_1_, int p_192638_2_, int p_192638_3_, int p_192638_4_, float p_192638_5_) {
/*  94 */     int i = getSize();
/*     */     
/*  96 */     for (int j = 0; j < i; j++) {
/*     */       
/*  98 */       int k = p_192638_2_ + j * this.slotHeight + this.headerPadding;
/*  99 */       int l = this.slotHeight - 4;
/*     */       
/* 101 */       if (k > this.bottom || k + l < this.top)
/*     */       {
/* 103 */         func_192639_a(j, p_192638_1_, k, p_192638_5_);
/*     */       }
/*     */       
/* 106 */       if (this.showSelectionBox && isSelected(j))
/*     */       {
/* 108 */         renderSelected(this.width, k, l, Tezzelator.instance);
/*     */       }
/*     */       
/* 111 */       func_192637_a(j, p_192638_1_, k, l, p_192638_3_, p_192638_4_, p_192638_5_);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiClickableScrolledSelectionListProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
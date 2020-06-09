/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiSlot
/*     */ {
/*     */   protected final Minecraft mc;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   protected int right;
/*     */   protected int left;
/*     */   protected final int slotHeight;
/*     */   private int scrollUpButtonID;
/*     */   private int scrollDownButtonID;
/*     */   protected int mouseX;
/*     */   protected int mouseY;
/*     */   protected boolean centerListVertically = true;
/*  38 */   protected int initialClickY = -2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float scrollMultiplier;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amountScrolled;
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected int selectedElement = -1;
/*     */   
/*     */   protected long lastClicked;
/*     */   
/*     */   protected boolean visible = true;
/*     */   
/*     */   protected boolean showSelectionBox = true;
/*     */   
/*     */   protected boolean hasListHeader;
/*     */   
/*     */   protected int headerPadding;
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
/*  66 */     this.mc = mcIn;
/*  67 */     this.width = width;
/*  68 */     this.height = height;
/*  69 */     this.top = topIn;
/*  70 */     this.bottom = bottomIn;
/*  71 */     this.slotHeight = slotHeightIn;
/*  72 */     this.left = 0;
/*  73 */     this.right = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
/*  78 */     this.width = widthIn;
/*  79 */     this.height = heightIn;
/*  80 */     this.top = topIn;
/*  81 */     this.bottom = bottomIn;
/*  82 */     this.left = 0;
/*  83 */     this.right = widthIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193651_b(boolean p_193651_1_) {
/*  88 */     this.showSelectionBox = p_193651_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
/*  97 */     this.hasListHeader = hasListHeaderIn;
/*  98 */     this.headerPadding = headerPaddingIn;
/*     */     
/* 100 */     if (!hasListHeaderIn)
/*     */     {
/* 102 */       this.headerPadding = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isSelected(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 123 */     return getSize() * this.slotHeight + this.headerPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawBackground();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_192639_a(int p_192639_1_, int p_192639_2_, int p_192639_3_, float p_192639_4_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void func_192637_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clickedHeader(int p_148132_1_, int p_148132_2_) {}
/*     */ 
/*     */   
/*     */   protected void renderDecorations(int mouseXIn, int mouseYIn) {}
/*     */ 
/*     */   
/*     */   public int getSlotIndexFromScreenCoords(int posX, int posY) {
/* 151 */     int i = this.left + this.width / 2 - getListWidth() / 2;
/* 152 */     int j = this.left + this.width / 2 + getListWidth() / 2;
/* 153 */     int k = posY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 154 */     int l = k / this.slotHeight;
/* 155 */     return (posX < getScrollBarX() && posX >= i && posX <= j && l >= 0 && k >= 0 && l < getSize()) ? l : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
/* 163 */     this.scrollUpButtonID = scrollUpButtonIDIn;
/* 164 */     this.scrollDownButtonID = scrollDownButtonIDIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindAmountScrolled() {
/* 172 */     this.amountScrolled = MathHelper.clamp(this.amountScrolled, 0.0F, getMaxScroll());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxScroll() {
/* 177 */     return Math.max(0, getContentHeight() - this.bottom - this.top - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmountScrolled() {
/* 185 */     return (int)this.amountScrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
/* 190 */     return (p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollBy(int amount) {
/* 198 */     this.amountScrolled += amount;
/* 199 */     bindAmountScrolled();
/* 200 */     this.initialClickY = -2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(GuiButton button) {
/* 205 */     if (button.enabled)
/*     */     {
/* 207 */       if (button.id == this.scrollUpButtonID) {
/*     */         
/* 209 */         this.amountScrolled -= (this.slotHeight * 2 / 3);
/* 210 */         this.initialClickY = -2;
/* 211 */         bindAmountScrolled();
/*     */       }
/* 213 */       else if (button.id == this.scrollDownButtonID) {
/*     */         
/* 215 */         this.amountScrolled += (this.slotHeight * 2 / 3);
/* 216 */         this.initialClickY = -2;
/* 217 */         bindAmountScrolled();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
/* 224 */     if (this.visible) {
/*     */       
/* 226 */       this.mouseX = mouseXIn;
/* 227 */       this.mouseY = mouseYIn;
/*     */ 
/*     */       
/* 230 */       int i = getScrollBarX();
/* 231 */       int j = i + 6;
/* 232 */       bindAmountScrolled();
/* 233 */       GlStateManager.disableLighting();
/* 234 */       GlStateManager.disableFog();
/* 235 */       Tessellator tessellator = Tessellator.getInstance();
/* 236 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*     */       
/* 238 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 239 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 241 */       if (this.hasListHeader)
/*     */       {
/* 243 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 246 */       func_192638_a(k, l, mouseXIn, mouseYIn, partialTicks);
/* 247 */       GlStateManager.disableDepth();
/*     */ 
/*     */ 
/*     */       
/* 251 */       GlStateManager.enableBlend();
/* 252 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/* 253 */       GlStateManager.disableAlpha();
/* 254 */       GlStateManager.shadeModel(7425);
/* 255 */       GlStateManager.disableTexture2D();
/* 256 */       int i1 = 4;
/* 257 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 258 */       bufferbuilder.pos(this.left, (this.top + 4), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 259 */       bufferbuilder.pos(this.right, (this.top + 4), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 260 */       bufferbuilder.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 261 */       bufferbuilder.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 262 */       tessellator.draw();
/* 263 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 264 */       bufferbuilder.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 265 */       bufferbuilder.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 266 */       bufferbuilder.pos(this.right, (this.bottom - 4), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 267 */       bufferbuilder.pos(this.left, (this.bottom - 4), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 268 */       tessellator.draw();
/* 269 */       int j1 = getMaxScroll();
/*     */       
/* 271 */       if (j1 > 0) {
/*     */         
/* 273 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 274 */         k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8);
/* 275 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 277 */         if (l1 < this.top)
/*     */         {
/* 279 */           l1 = this.top;
/*     */         }
/*     */         
/* 282 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 283 */         bufferbuilder.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 284 */         bufferbuilder.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 285 */         bufferbuilder.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 286 */         bufferbuilder.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 287 */         tessellator.draw();
/* 288 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 289 */         bufferbuilder.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 290 */         bufferbuilder.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 291 */         bufferbuilder.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 292 */         bufferbuilder.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 293 */         tessellator.draw();
/* 294 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 295 */         bufferbuilder.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 296 */         bufferbuilder.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 297 */         bufferbuilder.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 298 */         bufferbuilder.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 299 */         tessellator.draw();
/*     */       } 
/*     */       
/* 302 */       renderDecorations(mouseXIn, mouseYIn);
/* 303 */       GlStateManager.enableTexture2D();
/* 304 */       GlStateManager.shadeModel(7424);
/* 305 */       GlStateManager.enableAlpha();
/* 306 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/* 312 */     if (isMouseYWithinSlotBounds(this.mouseY)) {
/*     */       
/* 314 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */         
/* 316 */         int i = (this.width - getListWidth()) / 2;
/* 317 */         int j = (this.width + getListWidth()) / 2;
/* 318 */         int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 319 */         int l = k / this.slotHeight;
/*     */         
/* 321 */         if (l < getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
/*     */           
/* 323 */           elementClicked(l, false, this.mouseX, this.mouseY);
/* 324 */           this.selectedElement = l;
/*     */         }
/* 326 */         else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
/*     */           
/* 328 */           clickedHeader(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
/*     */         } 
/*     */       } 
/*     */       
/* 332 */       if (Mouse.isButtonDown(0) && getEnabled()) {
/*     */         
/* 334 */         if (this.initialClickY != -1) {
/*     */           
/* 336 */           if (this.initialClickY >= 0)
/*     */           {
/* 338 */             this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
/* 339 */             this.initialClickY = this.mouseY;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 344 */           boolean flag1 = true;
/*     */           
/* 346 */           if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */             
/* 348 */             int j2 = (this.width - getListWidth()) / 2;
/* 349 */             int k2 = (this.width + getListWidth()) / 2;
/* 350 */             int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 351 */             int i1 = l2 / this.slotHeight;
/*     */             
/* 353 */             if (i1 < getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
/*     */               
/* 355 */               boolean flag = (i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L);
/* 356 */               elementClicked(i1, flag, this.mouseX, this.mouseY);
/* 357 */               this.selectedElement = i1;
/* 358 */               this.lastClicked = Minecraft.getSystemTime();
/*     */             }
/* 360 */             else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
/*     */               
/* 362 */               clickedHeader(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
/* 363 */               flag1 = false;
/*     */             } 
/*     */             
/* 366 */             int i3 = getScrollBarX();
/* 367 */             int j1 = i3 + 6;
/*     */             
/* 369 */             if (this.mouseX >= i3 && this.mouseX <= j1) {
/*     */               
/* 371 */               this.scrollMultiplier = -1.0F;
/* 372 */               int k1 = getMaxScroll();
/*     */               
/* 374 */               if (k1 < 1)
/*     */               {
/* 376 */                 k1 = 1;
/*     */               }
/*     */               
/* 379 */               int l1 = (int)(((this.bottom - this.top) * (this.bottom - this.top)) / getContentHeight());
/* 380 */               l1 = MathHelper.clamp(l1, 32, this.bottom - this.top - 8);
/* 381 */               this.scrollMultiplier /= (this.bottom - this.top - l1) / k1;
/*     */             }
/*     */             else {
/*     */               
/* 385 */               this.scrollMultiplier = 1.0F;
/*     */             } 
/*     */             
/* 388 */             if (flag1)
/*     */             {
/* 390 */               this.initialClickY = this.mouseY;
/*     */             }
/*     */             else
/*     */             {
/* 394 */               this.initialClickY = -2;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 399 */             this.initialClickY = -2;
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 405 */         this.initialClickY = -1;
/*     */       } 
/*     */       
/* 408 */       int i2 = Mouse.getEventDWheel();
/*     */       
/* 410 */       if (i2 != 0) {
/*     */         
/* 412 */         if (i2 > 0) {
/*     */           
/* 414 */           i2 = -1;
/*     */         }
/* 416 */         else if (i2 < 0) {
/*     */           
/* 418 */           i2 = 1;
/*     */         } 
/*     */         
/* 421 */         this.amountScrolled += (i2 * this.slotHeight / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledIn) {
/* 428 */     this.enabled = enabledIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnabled() {
/* 433 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 441 */     return 220;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192638_a(int p_192638_1_, int p_192638_2_, int p_192638_3_, int p_192638_4_, float p_192638_5_) {
/* 446 */     int i = getSize();
/* 447 */     Tessellator tessellator = Tessellator.getInstance();
/* 448 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*     */     
/* 450 */     for (int j = 0; j < i; j++) {
/*     */       
/* 452 */       int k = p_192638_2_ + j * this.slotHeight + this.headerPadding;
/* 453 */       int l = this.slotHeight - 4;
/*     */       
/* 455 */       if (k > this.bottom || k + l < this.top)
/*     */       {
/* 457 */         func_192639_a(j, p_192638_1_, k, p_192638_5_);
/*     */       }
/*     */       
/* 460 */       if (this.showSelectionBox && isSelected(j)) {
/*     */         
/* 462 */         int i1 = this.left + this.width / 2 - getListWidth() / 2;
/* 463 */         int j1 = this.left + this.width / 2 + getListWidth() / 2;
/* 464 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 465 */         GlStateManager.disableTexture2D();
/* 466 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 467 */         bufferbuilder.pos(i1, (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 468 */         bufferbuilder.pos(j1, (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 469 */         bufferbuilder.pos(j1, (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 470 */         bufferbuilder.pos(i1, (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 471 */         bufferbuilder.pos((i1 + 1), (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 472 */         bufferbuilder.pos((j1 - 1), (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 473 */         bufferbuilder.pos((j1 - 1), (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 474 */         bufferbuilder.pos((i1 + 1), (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 475 */         tessellator.draw();
/* 476 */         GlStateManager.enableTexture2D();
/*     */       } 
/*     */       
/* 479 */       if (k >= this.top - this.slotHeight && k <= this.bottom)
/*     */       {
/* 481 */         func_192637_a(j, p_192638_1_, k, l, p_192638_3_, p_192638_4_, p_192638_5_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 488 */     return this.width / 2 + 124;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
/* 496 */     Tessellator tessellator = Tessellator.getInstance();
/* 497 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 498 */     this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 499 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 500 */     float f = 32.0F;
/* 501 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 502 */     bufferbuilder.pos(this.left, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 503 */     bufferbuilder.pos((this.left + this.width), endY, 0.0D).tex((this.width / 32.0F), (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 504 */     bufferbuilder.pos((this.left + this.width), startY, 0.0D).tex((this.width / 32.0F), (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 505 */     bufferbuilder.pos(this.left, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 506 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotXBoundsFromLeft(int leftIn) {
/* 514 */     this.left = leftIn;
/* 515 */     this.right = leftIn + this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotHeight() {
/* 520 */     return this.slotHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawContainerBackground(Tessellator p_drawContainerBackground_1_) {
/* 525 */     BufferBuilder bufferbuilder = p_drawContainerBackground_1_.getBuffer();
/* 526 */     this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 527 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 528 */     float f = 32.0F;
/* 529 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 530 */     bufferbuilder.pos(this.left, this.bottom, 0.0D).tex((this.left / 32.0F), ((this.bottom + (int)this.amountScrolled) / 32.0F)).color(32, 32, 32, 255).endVertex();
/* 531 */     bufferbuilder.pos(this.right, this.bottom, 0.0D).tex((this.right / 32.0F), ((this.bottom + (int)this.amountScrolled) / 32.0F)).color(32, 32, 32, 255).endVertex();
/* 532 */     bufferbuilder.pos(this.right, this.top, 0.0D).tex((this.right / 32.0F), ((this.top + (int)this.amountScrolled) / 32.0F)).color(32, 32, 32, 255).endVertex();
/* 533 */     bufferbuilder.pos(this.left, this.top, 0.0D).tex((this.left / 32.0F), ((this.top + (int)this.amountScrolled) / 32.0F)).color(32, 32, 32, 255).endVertex();
/* 534 */     p_drawContainerBackground_1_.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
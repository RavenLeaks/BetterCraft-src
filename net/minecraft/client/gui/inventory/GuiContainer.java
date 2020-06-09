/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public abstract class GuiContainer extends GuiScreen {
/*  26 */   public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("textures/gui/container/inventory.png");
/*     */ 
/*     */   
/*  29 */   protected int xSize = 176;
/*     */ 
/*     */   
/*  32 */   protected int ySize = 166;
/*     */ 
/*     */ 
/*     */   
/*     */   public Container inventorySlots;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int guiLeft;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int guiTop;
/*     */ 
/*     */ 
/*     */   
/*     */   private Slot theSlot;
/*     */ 
/*     */   
/*     */   private Slot clickedSlot;
/*     */ 
/*     */   
/*     */   private boolean isRightMouseClick;
/*     */ 
/*     */   
/*  57 */   private ItemStack draggedStack = ItemStack.field_190927_a;
/*     */   
/*     */   private int touchUpX;
/*     */   
/*     */   private int touchUpY;
/*     */   private Slot returningStackDestSlot;
/*     */   private long returningStackTime;
/*  64 */   private ItemStack returningStack = ItemStack.field_190927_a;
/*     */   private Slot currentDragTargetSlot;
/*     */   private long dragItemDropDelay;
/*  67 */   protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
/*     */   protected boolean dragSplitting;
/*     */   private int dragSplittingLimit;
/*     */   private int dragSplittingButton;
/*     */   private boolean ignoreMouseUp;
/*     */   private int dragSplittingRemnant;
/*     */   private long lastClickTime;
/*     */   private Slot lastClickSlot;
/*     */   private int lastClickButton;
/*     */   private boolean doubleClick;
/*  77 */   private ItemStack shiftClickedSlot = ItemStack.field_190927_a;
/*     */ 
/*     */   
/*     */   public GuiContainer(Container inventorySlotsIn) {
/*  81 */     this.inventorySlots = inventorySlotsIn;
/*  82 */     this.ignoreMouseUp = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  91 */     super.initGui();
/*  92 */     this.mc.player.openContainer = this.inventorySlots;
/*  93 */     this.guiLeft = (this.width - this.xSize) / 2;
/*  94 */     this.guiTop = (this.height - this.ySize) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 102 */     int i = this.guiLeft;
/* 103 */     int j = this.guiTop;
/* 104 */     drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/* 105 */     GlStateManager.disableRescaleNormal();
/* 106 */     RenderHelper.disableStandardItemLighting();
/* 107 */     GlStateManager.disableLighting();
/* 108 */     GlStateManager.disableDepth();
/* 109 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 110 */     RenderHelper.enableGUIStandardItemLighting();
/* 111 */     GlStateManager.pushMatrix();
/* 112 */     GlStateManager.translate(i, j, 0.0F);
/* 113 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 114 */     GlStateManager.enableRescaleNormal();
/* 115 */     this.theSlot = null;
/* 116 */     int k = 240;
/* 117 */     int l = 240;
/* 118 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/* 119 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 121 */     for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); i1++) {
/*     */       
/* 123 */       Slot slot = this.inventorySlots.inventorySlots.get(i1);
/*     */       
/* 125 */       if (slot.canBeHovered())
/*     */       {
/* 127 */         drawSlot(slot);
/*     */       }
/*     */       
/* 130 */       if (isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered()) {
/*     */         
/* 132 */         this.theSlot = slot;
/* 133 */         GlStateManager.disableLighting();
/* 134 */         GlStateManager.disableDepth();
/* 135 */         int j1 = slot.xDisplayPosition;
/* 136 */         int k1 = slot.yDisplayPosition;
/* 137 */         GlStateManager.colorMask(true, true, true, false);
/* 138 */         drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
/* 139 */         GlStateManager.colorMask(true, true, true, true);
/* 140 */         GlStateManager.enableLighting();
/* 141 */         GlStateManager.enableDepth();
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     RenderHelper.disableStandardItemLighting();
/* 146 */     drawGuiContainerForegroundLayer(mouseX, mouseY);
/* 147 */     RenderHelper.enableGUIStandardItemLighting();
/* 148 */     InventoryPlayer inventoryplayer = this.mc.player.inventory;
/* 149 */     ItemStack itemstack = this.draggedStack.func_190926_b() ? inventoryplayer.getItemStack() : this.draggedStack;
/*     */     
/* 151 */     if (!itemstack.func_190926_b()) {
/*     */       
/* 153 */       int j2 = 8;
/* 154 */       int k2 = this.draggedStack.func_190926_b() ? 8 : 16;
/* 155 */       String s = null;
/*     */       
/* 157 */       if (!this.draggedStack.func_190926_b() && this.isRightMouseClick) {
/*     */         
/* 159 */         itemstack = itemstack.copy();
/* 160 */         itemstack.func_190920_e(MathHelper.ceil(itemstack.func_190916_E() / 2.0F));
/*     */       }
/* 162 */       else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
/*     */         
/* 164 */         itemstack = itemstack.copy();
/* 165 */         itemstack.func_190920_e(this.dragSplittingRemnant);
/*     */         
/* 167 */         if (itemstack.func_190926_b())
/*     */         {
/* 169 */           s = TextFormatting.YELLOW + "0";
/*     */         }
/*     */       } 
/*     */       
/* 173 */       drawItemStack(itemstack, mouseX - i - 8, mouseY - j - k2, s);
/*     */     } 
/*     */     
/* 176 */     if (!this.returningStack.func_190926_b()) {
/*     */       
/* 178 */       float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
/*     */       
/* 180 */       if (f >= 1.0F) {
/*     */         
/* 182 */         f = 1.0F;
/* 183 */         this.returningStack = ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 186 */       int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
/* 187 */       int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
/* 188 */       int l1 = this.touchUpX + (int)(l2 * f);
/* 189 */       int i2 = this.touchUpY + (int)(i3 * f);
/* 190 */       drawItemStack(this.returningStack, l1, i2, (String)null);
/*     */     } 
/*     */     
/* 193 */     GlStateManager.popMatrix();
/* 194 */     GlStateManager.enableLighting();
/* 195 */     GlStateManager.enableDepth();
/* 196 */     RenderHelper.enableStandardItemLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_191948_b(int p_191948_1_, int p_191948_2_) {
/* 201 */     if (this.mc.player.inventory.getItemStack().func_190926_b() && this.theSlot != null && this.theSlot.getHasStack())
/*     */     {
/* 203 */       renderToolTip(this.theSlot.getStack(), p_191948_1_, p_191948_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawItemStack(ItemStack stack, int x, int y, String altText) {
/* 214 */     GlStateManager.translate(0.0F, 0.0F, 32.0F);
/* 215 */     this.zLevel = 200.0F;
/* 216 */     this.itemRender.zLevel = 200.0F;
/* 217 */     this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
/* 218 */     this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, stack, x, y - (this.draggedStack.func_190926_b() ? 0 : 8), altText);
/* 219 */     this.zLevel = 0.0F;
/* 220 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawGuiContainerBackgroundLayer(float paramFloat, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSlot(Slot slotIn) {
/* 240 */     int i = slotIn.xDisplayPosition;
/* 241 */     int j = slotIn.yDisplayPosition;
/* 242 */     ItemStack itemstack = slotIn.getStack();
/* 243 */     boolean flag = false;
/* 244 */     boolean flag1 = (slotIn == this.clickedSlot && !this.draggedStack.func_190926_b() && !this.isRightMouseClick);
/* 245 */     ItemStack itemstack1 = this.mc.player.inventory.getItemStack();
/* 246 */     String s = null;
/*     */     
/* 248 */     if (slotIn == this.clickedSlot && !this.draggedStack.func_190926_b() && this.isRightMouseClick && !itemstack.func_190926_b()) {
/*     */       
/* 250 */       itemstack = itemstack.copy();
/* 251 */       itemstack.func_190920_e(itemstack.func_190916_E() / 2);
/*     */     }
/* 253 */     else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && !itemstack1.func_190926_b()) {
/*     */       
/* 255 */       if (this.dragSplittingSlots.size() == 1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 260 */       if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
/*     */         
/* 262 */         itemstack = itemstack1.copy();
/* 263 */         flag = true;
/* 264 */         Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack().func_190926_b() ? 0 : slotIn.getStack().func_190916_E());
/* 265 */         int k = Math.min(itemstack.getMaxStackSize(), slotIn.getItemStackLimit(itemstack));
/*     */         
/* 267 */         if (itemstack.func_190916_E() > k)
/*     */         {
/* 269 */           s = String.valueOf(TextFormatting.YELLOW.toString()) + k;
/* 270 */           itemstack.func_190920_e(k);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 275 */         this.dragSplittingSlots.remove(slotIn);
/* 276 */         updateDragSplitting();
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     this.zLevel = 100.0F;
/* 281 */     this.itemRender.zLevel = 100.0F;
/*     */     
/* 283 */     if (itemstack.func_190926_b() && slotIn.canBeHovered()) {
/*     */       
/* 285 */       String s1 = slotIn.getSlotTexture();
/*     */       
/* 287 */       if (s1 != null) {
/*     */         
/* 289 */         TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(s1);
/* 290 */         GlStateManager.disableLighting();
/* 291 */         this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 292 */         drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
/* 293 */         GlStateManager.enableLighting();
/* 294 */         flag1 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     if (!flag1) {
/*     */       
/* 300 */       if (flag)
/*     */       {
/* 302 */         drawRect(i, j, i + 16, j + 16, -2130706433);
/*     */       }
/*     */       
/* 305 */       GlStateManager.enableDepth();
/* 306 */       this.itemRender.renderItemAndEffectIntoGUI((EntityLivingBase)this.mc.player, itemstack, i, j);
/* 307 */       this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
/*     */     } 
/*     */     
/* 310 */     this.itemRender.zLevel = 0.0F;
/* 311 */     this.zLevel = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateDragSplitting() {
/* 316 */     ItemStack itemstack = this.mc.player.inventory.getItemStack();
/*     */     
/* 318 */     if (!itemstack.func_190926_b() && this.dragSplitting)
/*     */     {
/* 320 */       if (this.dragSplittingLimit == 2) {
/*     */         
/* 322 */         this.dragSplittingRemnant = itemstack.getMaxStackSize();
/*     */       }
/*     */       else {
/*     */         
/* 326 */         this.dragSplittingRemnant = itemstack.func_190916_E();
/*     */         
/* 328 */         for (Slot slot : this.dragSplittingSlots) {
/*     */           
/* 330 */           ItemStack itemstack1 = itemstack.copy();
/* 331 */           ItemStack itemstack2 = slot.getStack();
/* 332 */           int i = itemstack2.func_190926_b() ? 0 : itemstack2.func_190916_E();
/* 333 */           Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
/* 334 */           int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
/*     */           
/* 336 */           if (itemstack1.func_190916_E() > j)
/*     */           {
/* 338 */             itemstack1.func_190920_e(j);
/*     */           }
/*     */           
/* 341 */           this.dragSplittingRemnant -= itemstack1.func_190916_E() - i;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Slot getSlotAtPosition(int x, int y) {
/* 352 */     for (int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
/*     */       
/* 354 */       Slot slot = this.inventorySlots.inventorySlots.get(i);
/*     */       
/* 356 */       if (isMouseOverSlot(slot, x, y) && slot.canBeHovered())
/*     */       {
/* 358 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 362 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 370 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 371 */     boolean flag = (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100);
/* 372 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 373 */     long i = Minecraft.getSystemTime();
/* 374 */     this.doubleClick = (this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton);
/* 375 */     this.ignoreMouseUp = false;
/*     */     
/* 377 */     if (mouseButton == 0 || mouseButton == 1 || flag) {
/*     */       
/* 379 */       int j = this.guiLeft;
/* 380 */       int k = this.guiTop;
/* 381 */       boolean flag1 = func_193983_c(mouseX, mouseY, j, k);
/* 382 */       int l = -1;
/*     */       
/* 384 */       if (slot != null)
/*     */       {
/* 386 */         l = slot.slotNumber;
/*     */       }
/*     */       
/* 389 */       if (flag1)
/*     */       {
/* 391 */         l = -999;
/*     */       }
/*     */       
/* 394 */       if (this.mc.gameSettings.touchscreen && flag1 && this.mc.player.inventory.getItemStack().func_190926_b()) {
/*     */         
/* 396 */         this.mc.displayGuiScreen(null);
/*     */         
/*     */         return;
/*     */       } 
/* 400 */       if (l != -1)
/*     */       {
/* 402 */         if (this.mc.gameSettings.touchscreen) {
/*     */           
/* 404 */           if (slot != null && slot.getHasStack())
/*     */           {
/* 406 */             this.clickedSlot = slot;
/* 407 */             this.draggedStack = ItemStack.field_190927_a;
/* 408 */             this.isRightMouseClick = (mouseButton == 1);
/*     */           }
/*     */           else
/*     */           {
/* 412 */             this.clickedSlot = null;
/*     */           }
/*     */         
/* 415 */         } else if (!this.dragSplitting) {
/*     */           
/* 417 */           if (this.mc.player.inventory.getItemStack().func_190926_b()) {
/*     */             
/* 419 */             if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/*     */               
/* 421 */               handleMouseClick(slot, l, mouseButton, ClickType.CLONE);
/*     */             }
/*     */             else {
/*     */               
/* 425 */               boolean flag2 = (l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/* 426 */               ClickType clicktype = ClickType.PICKUP;
/*     */               
/* 428 */               if (flag2) {
/*     */                 
/* 430 */                 this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack().copy() : ItemStack.field_190927_a;
/* 431 */                 clicktype = ClickType.QUICK_MOVE;
/*     */               }
/* 433 */               else if (l == -999) {
/*     */                 
/* 435 */                 clicktype = ClickType.THROW;
/*     */               } 
/*     */               
/* 438 */               handleMouseClick(slot, l, mouseButton, clicktype);
/*     */             } 
/*     */             
/* 441 */             this.ignoreMouseUp = true;
/*     */           }
/*     */           else {
/*     */             
/* 445 */             this.dragSplitting = true;
/* 446 */             this.dragSplittingButton = mouseButton;
/* 447 */             this.dragSplittingSlots.clear();
/*     */             
/* 449 */             if (mouseButton == 0) {
/*     */               
/* 451 */               this.dragSplittingLimit = 0;
/*     */             }
/* 453 */             else if (mouseButton == 1) {
/*     */               
/* 455 */               this.dragSplittingLimit = 1;
/*     */             }
/* 457 */             else if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/*     */               
/* 459 */               this.dragSplittingLimit = 2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 466 */     this.lastClickSlot = slot;
/* 467 */     this.lastClickTime = i;
/* 468 */     this.lastClickButton = mouseButton;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
/* 473 */     return !(p_193983_1_ >= p_193983_3_ && p_193983_2_ >= p_193983_4_ && p_193983_1_ < p_193983_3_ + this.xSize && p_193983_2_ < p_193983_4_ + this.ySize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 482 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 483 */     ItemStack itemstack = this.mc.player.inventory.getItemStack();
/*     */     
/* 485 */     if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/*     */       
/* 487 */       if (clickedMouseButton == 0 || clickedMouseButton == 1)
/*     */       {
/* 489 */         if (this.draggedStack.func_190926_b()) {
/*     */           
/* 491 */           if (slot != this.clickedSlot && !this.clickedSlot.getStack().func_190926_b())
/*     */           {
/* 493 */             this.draggedStack = this.clickedSlot.getStack().copy();
/*     */           }
/*     */         }
/* 496 */         else if (this.draggedStack.func_190916_E() > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
/*     */           
/* 498 */           long i = Minecraft.getSystemTime();
/*     */           
/* 500 */           if (this.currentDragTargetSlot == slot) {
/*     */             
/* 502 */             if (i - this.dragItemDropDelay > 500L)
/*     */             {
/* 504 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
/* 505 */               handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
/* 506 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
/* 507 */               this.dragItemDropDelay = i + 750L;
/* 508 */               this.draggedStack.func_190918_g(1);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 513 */             this.currentDragTargetSlot = slot;
/* 514 */             this.dragItemDropDelay = i;
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 519 */     } else if (this.dragSplitting && slot != null && !itemstack.func_190926_b() && (itemstack.func_190916_E() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
/*     */       
/* 521 */       this.dragSplittingSlots.add(slot);
/* 522 */       updateDragSplitting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 531 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 532 */     int i = this.guiLeft;
/* 533 */     int j = this.guiTop;
/* 534 */     boolean flag = func_193983_c(mouseX, mouseY, i, j);
/* 535 */     int k = -1;
/*     */     
/* 537 */     if (slot != null)
/*     */     {
/* 539 */       k = slot.slotNumber;
/*     */     }
/*     */     
/* 542 */     if (flag)
/*     */     {
/* 544 */       k = -999;
/*     */     }
/*     */     
/* 547 */     if (this.doubleClick && slot != null && state == 0 && this.inventorySlots.canMergeSlot(ItemStack.field_190927_a, slot)) {
/*     */       
/* 549 */       if (isShiftKeyDown()) {
/*     */         
/* 551 */         if (!this.shiftClickedSlot.func_190926_b())
/*     */         {
/* 553 */           for (Slot slot2 : this.inventorySlots.inventorySlots)
/*     */           {
/* 555 */             if (slot2 != null && slot2.canTakeStack((EntityPlayer)this.mc.player) && slot2.getHasStack() && slot2.inventory == slot.inventory && Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true))
/*     */             {
/* 557 */               handleMouseClick(slot2, slot2.slotNumber, state, ClickType.QUICK_MOVE);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       } else {
/*     */         
/* 564 */         handleMouseClick(slot, k, state, ClickType.PICKUP_ALL);
/*     */       } 
/*     */       
/* 567 */       this.doubleClick = false;
/* 568 */       this.lastClickTime = 0L;
/*     */     }
/*     */     else {
/*     */       
/* 572 */       if (this.dragSplitting && this.dragSplittingButton != state) {
/*     */         
/* 574 */         this.dragSplitting = false;
/* 575 */         this.dragSplittingSlots.clear();
/* 576 */         this.ignoreMouseUp = true;
/*     */         
/*     */         return;
/*     */       } 
/* 580 */       if (this.ignoreMouseUp) {
/*     */         
/* 582 */         this.ignoreMouseUp = false;
/*     */         
/*     */         return;
/*     */       } 
/* 586 */       if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/*     */         
/* 588 */         if (state == 0 || state == 1)
/*     */         {
/* 590 */           if (this.draggedStack.func_190926_b() && slot != this.clickedSlot)
/*     */           {
/* 592 */             this.draggedStack = this.clickedSlot.getStack();
/*     */           }
/*     */           
/* 595 */           boolean flag2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
/*     */           
/* 597 */           if (k != -1 && !this.draggedStack.func_190926_b() && flag2) {
/*     */             
/* 599 */             handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
/* 600 */             handleMouseClick(slot, k, 0, ClickType.PICKUP);
/*     */             
/* 602 */             if (this.mc.player.inventory.getItemStack().func_190926_b())
/*     */             {
/* 604 */               this.returningStack = ItemStack.field_190927_a;
/*     */             }
/*     */             else
/*     */             {
/* 608 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
/* 609 */               this.touchUpX = mouseX - i;
/* 610 */               this.touchUpY = mouseY - j;
/* 611 */               this.returningStackDestSlot = this.clickedSlot;
/* 612 */               this.returningStack = this.draggedStack;
/* 613 */               this.returningStackTime = Minecraft.getSystemTime();
/*     */             }
/*     */           
/* 616 */           } else if (!this.draggedStack.func_190926_b()) {
/*     */             
/* 618 */             this.touchUpX = mouseX - i;
/* 619 */             this.touchUpY = mouseY - j;
/* 620 */             this.returningStackDestSlot = this.clickedSlot;
/* 621 */             this.returningStack = this.draggedStack;
/* 622 */             this.returningStackTime = Minecraft.getSystemTime();
/*     */           } 
/*     */           
/* 625 */           this.draggedStack = ItemStack.field_190927_a;
/* 626 */           this.clickedSlot = null;
/*     */         }
/*     */       
/* 629 */       } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
/*     */         
/* 631 */         handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
/*     */         
/* 633 */         for (Slot slot1 : this.dragSplittingSlots)
/*     */         {
/* 635 */           handleMouseClick(slot1, slot1.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
/*     */         }
/*     */         
/* 638 */         handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
/*     */       }
/* 640 */       else if (!this.mc.player.inventory.getItemStack().func_190926_b()) {
/*     */         
/* 642 */         if (state == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
/*     */           
/* 644 */           handleMouseClick(slot, k, state, ClickType.CLONE);
/*     */         }
/*     */         else {
/*     */           
/* 648 */           boolean flag1 = (k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*     */           
/* 650 */           if (flag1)
/*     */           {
/* 652 */             this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack().copy() : ItemStack.field_190927_a;
/*     */           }
/*     */           
/* 655 */           handleMouseClick(slot, k, state, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 660 */     if (this.mc.player.inventory.getItemStack().func_190926_b())
/*     */     {
/* 662 */       this.lastClickTime = 0L;
/*     */     }
/*     */     
/* 665 */     this.dragSplitting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
/* 673 */     return isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
/* 682 */     int i = this.guiLeft;
/* 683 */     int j = this.guiTop;
/* 684 */     pointX -= i;
/* 685 */     pointY -= j;
/* 686 */     return (pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
/* 694 */     if (slotIn != null)
/*     */     {
/* 696 */       slotId = slotIn.slotNumber;
/*     */     }
/*     */     
/* 699 */     this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, mouseButton, type, (EntityPlayer)this.mc.player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 708 */     if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
/*     */     {
/* 710 */       this.mc.player.closeScreen();
/*     */     }
/*     */     
/* 713 */     checkHotbarKeys(keyCode);
/*     */     
/* 715 */     if (this.theSlot != null && this.theSlot.getHasStack())
/*     */     {
/* 717 */       if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
/*     */         
/* 719 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, ClickType.CLONE);
/*     */       }
/* 721 */       else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
/*     */         
/* 723 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, ClickType.THROW);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkHotbarKeys(int keyCode) {
/* 735 */     if (this.mc.player.inventory.getItemStack().func_190926_b() && this.theSlot != null)
/*     */     {
/* 737 */       for (int i = 0; i < 9; i++) {
/*     */         
/* 739 */         if (keyCode == this.mc.gameSettings.keyBindsHotbar[i].getKeyCode()) {
/*     */           
/* 741 */           handleMouseClick(this.theSlot, this.theSlot.slotNumber, i, ClickType.SWAP);
/* 742 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 747 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 755 */     if (this.mc.player != null)
/*     */     {
/* 757 */       this.inventorySlots.onContainerClosed((EntityPlayer)this.mc.player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 766 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 774 */     super.updateScreen();
/*     */     
/* 776 */     if (!this.mc.player.isEntityAlive() || this.mc.player.isDead)
/*     */     {
/* 778 */       this.mc.player.closeScreen();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTextField
/*     */   extends Gui
/*     */ {
/*     */   private final int id;
/*     */   private final FontRenderer fontRendererInstance;
/*     */   public int xPosition;
/*     */   public int yPosition;
/*     */   private final int width;
/*     */   private final int height;
/*  25 */   private String text = "";
/*  26 */   private int maxStringLength = 32;
/*     */ 
/*     */   
/*     */   private int cursorCounter;
/*     */ 
/*     */   
/*     */   private boolean enableBackgroundDrawing = true;
/*     */ 
/*     */   
/*     */   private boolean canLoseFocus = true;
/*     */ 
/*     */   
/*     */   private boolean isFocused;
/*     */ 
/*     */   
/*     */   private boolean isEnabled = true;
/*     */ 
/*     */   
/*     */   private int lineScrollOffset;
/*     */ 
/*     */   
/*     */   private int cursorPosition;
/*     */ 
/*     */   
/*     */   private int selectionEnd;
/*     */ 
/*     */   
/*  53 */   private int enabledColor = 14737632;
/*  54 */   private int disabledColor = 7368816;
/*     */   
/*     */   private boolean visible = true;
/*     */   
/*     */   private GuiPageButtonList.GuiResponder guiResponder;
/*  59 */   private Predicate<String> validator = Predicates.alwaysTrue();
/*     */ 
/*     */   
/*     */   public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
/*  63 */     this.id = componentId;
/*  64 */     this.fontRendererInstance = fontrendererObj;
/*  65 */     this.xPosition = x;
/*  66 */     this.yPosition = y;
/*  67 */     this.width = par5Width;
/*  68 */     this.height = par6Height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGuiResponder(GuiPageButtonList.GuiResponder guiResponderIn) {
/*  76 */     this.guiResponder = guiResponderIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/*  84 */     this.cursorCounter++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String textIn) {
/*  92 */     if (this.validator.apply(textIn)) {
/*     */       
/*  94 */       if (textIn.length() > this.maxStringLength) {
/*     */         
/*  96 */         this.text = textIn.substring(0, this.maxStringLength);
/*     */       }
/*     */       else {
/*     */         
/* 100 */         this.text = textIn;
/*     */       } 
/*     */       
/* 103 */       setCursorPositionEnd();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 112 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedText() {
/* 120 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 121 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 122 */     return this.text.substring(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidator(Predicate<String> theValidator) {
/* 127 */     this.validator = theValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(String textToWrite) {
/*     */     int l;
/* 135 */     String s = "";
/* 136 */     String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
/* 137 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 138 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 139 */     int k = this.maxStringLength - this.text.length() - i - j;
/*     */     
/* 141 */     if (!this.text.isEmpty())
/*     */     {
/* 143 */       s = String.valueOf(s) + this.text.substring(0, i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (k < s1.length()) {
/*     */       
/* 150 */       s = String.valueOf(s) + s1.substring(0, k);
/* 151 */       l = k;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       s = String.valueOf(s) + s1;
/* 156 */       l = s1.length();
/*     */     } 
/*     */     
/* 159 */     if (!this.text.isEmpty() && j < this.text.length())
/*     */     {
/* 161 */       s = String.valueOf(s) + this.text.substring(j);
/*     */     }
/*     */     
/* 164 */     if (this.validator.apply(s)) {
/*     */       
/* 166 */       this.text = s;
/* 167 */       moveCursorBy(i - this.selectionEnd + l);
/* 168 */       func_190516_a(this.id, this.text);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190516_a(int p_190516_1_, String p_190516_2_) {
/* 174 */     if (this.guiResponder != null)
/*     */     {
/* 176 */       this.guiResponder.setEntryValue(p_190516_1_, p_190516_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteWords(int num) {
/* 186 */     if (!this.text.isEmpty())
/*     */     {
/* 188 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 190 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 194 */         deleteFromCursor(getNthWordFromCursor(num) - this.cursorPosition);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteFromCursor(int num) {
/* 205 */     if (!this.text.isEmpty())
/*     */     {
/* 207 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 209 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 213 */         boolean flag = (num < 0);
/* 214 */         int i = flag ? (this.cursorPosition + num) : this.cursorPosition;
/* 215 */         int j = flag ? this.cursorPosition : (this.cursorPosition + num);
/* 216 */         String s = "";
/*     */         
/* 218 */         if (i >= 0)
/*     */         {
/* 220 */           s = this.text.substring(0, i);
/*     */         }
/*     */         
/* 223 */         if (j < this.text.length())
/*     */         {
/* 225 */           s = String.valueOf(s) + this.text.substring(j);
/*     */         }
/*     */         
/* 228 */         if (this.validator.apply(s)) {
/*     */           
/* 230 */           this.text = s;
/*     */           
/* 232 */           if (flag)
/*     */           {
/* 234 */             moveCursorBy(num);
/*     */           }
/*     */           
/* 237 */           func_190516_a(this.id, this.text);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 245 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromCursor(int numWords) {
/* 253 */     return getNthWordFromPos(numWords, getCursorPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromPos(int n, int pos) {
/* 261 */     return getNthWordFromPosWS(n, pos, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
/* 269 */     int i = pos;
/* 270 */     boolean flag = (n < 0);
/* 271 */     int j = Math.abs(n);
/*     */     
/* 273 */     for (int k = 0; k < j; k++) {
/*     */       
/* 275 */       if (!flag) {
/*     */         
/* 277 */         int l = this.text.length();
/* 278 */         i = this.text.indexOf(' ', i);
/*     */         
/* 280 */         if (i == -1)
/*     */         {
/* 282 */           i = l;
/*     */         }
/*     */         else
/*     */         {
/* 286 */           while (skipWs && i < l && this.text.charAt(i) == ' ')
/*     */           {
/* 288 */             i++;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 294 */         while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ')
/*     */         {
/* 296 */           i--;
/*     */         }
/*     */         
/* 299 */         while (i > 0 && this.text.charAt(i - 1) != ' ')
/*     */         {
/* 301 */           i--;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveCursorBy(int num) {
/* 314 */     setCursorPosition(this.selectionEnd + num);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPosition(int pos) {
/* 322 */     this.cursorPosition = pos;
/* 323 */     int i = this.text.length();
/* 324 */     this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, i);
/* 325 */     setSelectionPos(this.cursorPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionZero() {
/* 333 */     setCursorPosition(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionEnd() {
/* 341 */     setCursorPosition(this.text.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean textboxKeyTyped(char typedChar, int keyCode) {
/* 349 */     if (!this.isFocused)
/*     */     {
/* 351 */       return false;
/*     */     }
/* 353 */     if (GuiScreen.isKeyComboCtrlA(keyCode)) {
/*     */       
/* 355 */       setCursorPositionEnd();
/* 356 */       setSelectionPos(0);
/* 357 */       return true;
/*     */     } 
/* 359 */     if (GuiScreen.isKeyComboCtrlC(keyCode)) {
/*     */       
/* 361 */       GuiScreen.setClipboardString(getSelectedText());
/* 362 */       return true;
/*     */     } 
/* 364 */     if (GuiScreen.isKeyComboCtrlV(keyCode)) {
/*     */       
/* 366 */       if (this.isEnabled)
/*     */       {
/* 368 */         writeText(GuiScreen.getClipboardString());
/*     */       }
/*     */       
/* 371 */       return true;
/*     */     } 
/* 373 */     if (GuiScreen.isKeyComboCtrlX(keyCode)) {
/*     */       
/* 375 */       GuiScreen.setClipboardString(getSelectedText());
/*     */       
/* 377 */       if (this.isEnabled)
/*     */       {
/* 379 */         writeText("");
/*     */       }
/*     */       
/* 382 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 386 */     switch (keyCode) {
/*     */       
/*     */       case 14:
/* 389 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 391 */           if (this.isEnabled)
/*     */           {
/* 393 */             deleteWords(-1);
/*     */           }
/*     */         }
/* 396 */         else if (this.isEnabled) {
/*     */           
/* 398 */           deleteFromCursor(-1);
/*     */         } 
/*     */         
/* 401 */         return true;
/*     */       
/*     */       case 199:
/* 404 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 406 */           setSelectionPos(0);
/*     */         }
/*     */         else {
/*     */           
/* 410 */           setCursorPositionZero();
/*     */         } 
/*     */         
/* 413 */         return true;
/*     */       
/*     */       case 203:
/* 416 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 418 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 420 */             setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
/*     */           }
/*     */           else
/*     */           {
/* 424 */             setSelectionPos(getSelectionEnd() - 1);
/*     */           }
/*     */         
/* 427 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 429 */           setCursorPosition(getNthWordFromCursor(-1));
/*     */         }
/*     */         else {
/*     */           
/* 433 */           moveCursorBy(-1);
/*     */         } 
/*     */         
/* 436 */         return true;
/*     */       
/*     */       case 205:
/* 439 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 441 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 443 */             setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
/*     */           }
/*     */           else
/*     */           {
/* 447 */             setSelectionPos(getSelectionEnd() + 1);
/*     */           }
/*     */         
/* 450 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 452 */           setCursorPosition(getNthWordFromCursor(1));
/*     */         }
/*     */         else {
/*     */           
/* 456 */           moveCursorBy(1);
/*     */         } 
/*     */         
/* 459 */         return true;
/*     */       
/*     */       case 207:
/* 462 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 464 */           setSelectionPos(this.text.length());
/*     */         }
/*     */         else {
/*     */           
/* 468 */           setCursorPositionEnd();
/*     */         } 
/*     */         
/* 471 */         return true;
/*     */       
/*     */       case 211:
/* 474 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 476 */           if (this.isEnabled)
/*     */           {
/* 478 */             deleteWords(1);
/*     */           }
/*     */         }
/* 481 */         else if (this.isEnabled) {
/*     */           
/* 483 */           deleteFromCursor(1);
/*     */         } 
/*     */         
/* 486 */         return true;
/*     */     } 
/*     */     
/* 489 */     if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
/*     */       
/* 491 */       if (this.isEnabled)
/*     */       {
/* 493 */         writeText(Character.toString(typedChar));
/*     */       }
/*     */       
/* 496 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 500 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 511 */     boolean flag = (mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height);
/*     */     
/* 513 */     if (this.canLoseFocus)
/*     */     {
/* 515 */       setFocused(flag);
/*     */     }
/*     */     
/* 518 */     if (this.isFocused && flag && mouseButton == 0) {
/*     */       
/* 520 */       int i = mouseX - this.xPosition;
/*     */       
/* 522 */       if (this.enableBackgroundDrawing)
/*     */       {
/* 524 */         i -= 4;
/*     */       }
/*     */       
/* 527 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 528 */       setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
/* 529 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 533 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextBox() {
/* 542 */     if (getVisible()) {
/*     */       
/* 544 */       if (getEnableBackgroundDrawing()) {
/*     */         
/* 546 */         drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
/* 547 */         drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
/*     */       } 
/*     */       
/* 550 */       int i = this.isEnabled ? this.enabledColor : this.disabledColor;
/* 551 */       int j = this.cursorPosition - this.lineScrollOffset;
/* 552 */       int k = this.selectionEnd - this.lineScrollOffset;
/* 553 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 554 */       boolean flag = (j >= 0 && j <= s.length());
/* 555 */       boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
/* 556 */       int l = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
/* 557 */       int i1 = this.enableBackgroundDrawing ? (this.yPosition + (this.height - 8) / 2) : this.yPosition;
/* 558 */       int j1 = l;
/*     */       
/* 560 */       if (k > s.length())
/*     */       {
/* 562 */         k = s.length();
/*     */       }
/*     */       
/* 565 */       if (!s.isEmpty()) {
/*     */         
/* 567 */         String s1 = flag ? s.substring(0, j) : s;
/* 568 */         j1 = this.fontRendererInstance.drawStringWithShadow(s1, l, i1, i);
/*     */       } 
/*     */       
/* 571 */       boolean flag2 = !(this.cursorPosition >= this.text.length() && this.text.length() < getMaxStringLength());
/* 572 */       int k1 = j1;
/*     */       
/* 574 */       if (!flag) {
/*     */         
/* 576 */         k1 = (j > 0) ? (l + this.width) : l;
/*     */       }
/* 578 */       else if (flag2) {
/*     */         
/* 580 */         k1 = j1 - 1;
/* 581 */         j1--;
/*     */       } 
/*     */       
/* 584 */       if (!s.isEmpty() && flag && j < s.length())
/*     */       {
/* 586 */         j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
/*     */       }
/*     */       
/* 589 */       if (flag1)
/*     */       {
/* 591 */         if (flag2) {
/*     */           
/* 593 */           Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
/*     */         }
/*     */         else {
/*     */           
/* 597 */           this.fontRendererInstance.drawStringWithShadow("<", k1, i1, i);
/*     */         } 
/*     */       }
/*     */       
/* 601 */       if (k != j) {
/*     */         
/* 603 */         int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
/* 604 */         drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawCursorVertical(int startX, int startY, int endX, int endY) {
/* 614 */     if (startX < endX) {
/*     */       
/* 616 */       int i = startX;
/* 617 */       startX = endX;
/* 618 */       endX = i;
/*     */     } 
/*     */     
/* 621 */     if (startY < endY) {
/*     */       
/* 623 */       int j = startY;
/* 624 */       startY = endY;
/* 625 */       endY = j;
/*     */     } 
/*     */     
/* 628 */     if (endX > this.xPosition + this.width)
/*     */     {
/* 630 */       endX = this.xPosition + this.width;
/*     */     }
/*     */     
/* 633 */     if (startX > this.xPosition + this.width)
/*     */     {
/* 635 */       startX = this.xPosition + this.width;
/*     */     }
/*     */     
/* 638 */     Tessellator tessellator = Tessellator.getInstance();
/* 639 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 640 */     GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
/* 641 */     GlStateManager.disableTexture2D();
/* 642 */     GlStateManager.enableColorLogic();
/* 643 */     GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
/* 644 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
/* 645 */     bufferbuilder.pos(startX, endY, 0.0D).endVertex();
/* 646 */     bufferbuilder.pos(endX, endY, 0.0D).endVertex();
/* 647 */     bufferbuilder.pos(endX, startY, 0.0D).endVertex();
/* 648 */     bufferbuilder.pos(startX, startY, 0.0D).endVertex();
/* 649 */     tessellator.draw();
/* 650 */     GlStateManager.disableColorLogic();
/* 651 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxStringLength(int length) {
/* 660 */     this.maxStringLength = length;
/*     */     
/* 662 */     if (this.text.length() > length)
/*     */     {
/* 664 */       this.text = this.text.substring(0, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStringLength() {
/* 673 */     return this.maxStringLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCursorPosition() {
/* 681 */     return this.cursorPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableBackgroundDrawing() {
/* 689 */     return this.enableBackgroundDrawing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
/* 697 */     this.enableBackgroundDrawing = enableBackgroundDrawingIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(int color) {
/* 705 */     this.enabledColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabledTextColour(int color) {
/* 713 */     this.disabledColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean isFocusedIn) {
/* 721 */     if (isFocusedIn && !this.isFocused)
/*     */     {
/* 723 */       this.cursorCounter = 0;
/*     */     }
/*     */     
/* 726 */     this.isFocused = isFocusedIn;
/*     */     
/* 728 */     if ((Minecraft.getMinecraft()).currentScreen != null)
/*     */     {
/* 730 */       (Minecraft.getMinecraft()).currentScreen.func_193975_a(isFocusedIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 739 */     return this.isFocused;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 747 */     this.isEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionEnd() {
/* 755 */     return this.selectionEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 763 */     return getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionPos(int position) {
/* 772 */     int i = this.text.length();
/*     */     
/* 774 */     if (position > i)
/*     */     {
/* 776 */       position = i;
/*     */     }
/*     */     
/* 779 */     if (position < 0)
/*     */     {
/* 781 */       position = 0;
/*     */     }
/*     */     
/* 784 */     this.selectionEnd = position;
/*     */     
/* 786 */     if (this.fontRendererInstance != null) {
/*     */       
/* 788 */       if (this.lineScrollOffset > i)
/*     */       {
/* 790 */         this.lineScrollOffset = i;
/*     */       }
/*     */       
/* 793 */       int j = getWidth();
/* 794 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
/* 795 */       int k = s.length() + this.lineScrollOffset;
/*     */       
/* 797 */       if (position == this.lineScrollOffset)
/*     */       {
/* 799 */         this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
/*     */       }
/*     */       
/* 802 */       if (position > k) {
/*     */         
/* 804 */         this.lineScrollOffset += position - k;
/*     */       }
/* 806 */       else if (position <= this.lineScrollOffset) {
/*     */         
/* 808 */         this.lineScrollOffset -= this.lineScrollOffset - position;
/*     */       } 
/*     */       
/* 811 */       this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanLoseFocus(boolean canLoseFocusIn) {
/* 820 */     this.canLoseFocus = canLoseFocusIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getVisible() {
/* 828 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean isVisible) {
/* 836 */     this.visible = isVisible;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiTextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
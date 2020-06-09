/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ public class GuiPageButtonList
/*     */   extends GuiListExtended {
/*  14 */   private final List<GuiEntry> entries = Lists.newArrayList();
/*  15 */   private final IntHashMap<Gui> componentMap = new IntHashMap();
/*  16 */   private final List<GuiTextField> editBoxes = Lists.newArrayList();
/*     */   
/*     */   private final GuiListEntry[][] pages;
/*     */   private int page;
/*     */   private final GuiResponder responder;
/*     */   private Gui focusedControl;
/*     */   
/*     */   public GuiPageButtonList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn, GuiResponder p_i45536_7_, GuiListEntry[]... p_i45536_8_) {
/*  24 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  25 */     this.responder = p_i45536_7_;
/*  26 */     this.pages = p_i45536_8_;
/*  27 */     this.centerListVertically = false;
/*  28 */     populateComponents();
/*  29 */     populateEntries();
/*     */   } private void populateComponents() {
/*     */     byte b;
/*     */     int i;
/*     */     GuiListEntry[][] arrayOfGuiListEntry;
/*  34 */     for (i = (arrayOfGuiListEntry = this.pages).length, b = 0; b < i; ) { GuiListEntry[] aguipagebuttonlist$guilistentry = arrayOfGuiListEntry[b];
/*     */       
/*  36 */       for (int j = 0; j < aguipagebuttonlist$guilistentry.length; j += 2) {
/*     */         
/*  38 */         GuiListEntry guipagebuttonlist$guilistentry = aguipagebuttonlist$guilistentry[j];
/*  39 */         GuiListEntry guipagebuttonlist$guilistentry1 = (j < aguipagebuttonlist$guilistentry.length - 1) ? aguipagebuttonlist$guilistentry[j + 1] : null;
/*  40 */         Gui gui = createEntry(guipagebuttonlist$guilistentry, 0, (guipagebuttonlist$guilistentry1 == null));
/*  41 */         Gui gui1 = createEntry(guipagebuttonlist$guilistentry1, 160, (guipagebuttonlist$guilistentry == null));
/*  42 */         GuiEntry guipagebuttonlist$guientry = new GuiEntry(gui, gui1);
/*  43 */         this.entries.add(guipagebuttonlist$guientry);
/*     */         
/*  45 */         if (guipagebuttonlist$guilistentry != null && gui != null) {
/*     */           
/*  47 */           this.componentMap.addKey(guipagebuttonlist$guilistentry.getId(), gui);
/*     */           
/*  49 */           if (gui instanceof GuiTextField)
/*     */           {
/*  51 */             this.editBoxes.add((GuiTextField)gui);
/*     */           }
/*     */         } 
/*     */         
/*  55 */         if (guipagebuttonlist$guilistentry1 != null && gui1 != null) {
/*     */           
/*  57 */           this.componentMap.addKey(guipagebuttonlist$guilistentry1.getId(), gui1);
/*     */           
/*  59 */           if (gui1 instanceof GuiTextField)
/*     */           {
/*  61 */             this.editBoxes.add((GuiTextField)gui1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private void populateEntries() {
/*  70 */     this.entries.clear();
/*     */     
/*  72 */     for (int i = 0; i < (this.pages[this.page]).length; i += 2) {
/*     */       
/*  74 */       GuiListEntry guipagebuttonlist$guilistentry = this.pages[this.page][i];
/*  75 */       GuiListEntry guipagebuttonlist$guilistentry1 = (i < (this.pages[this.page]).length - 1) ? this.pages[this.page][i + 1] : null;
/*  76 */       Gui gui = (Gui)this.componentMap.lookup(guipagebuttonlist$guilistentry.getId());
/*  77 */       Gui gui1 = (guipagebuttonlist$guilistentry1 != null) ? (Gui)this.componentMap.lookup(guipagebuttonlist$guilistentry1.getId()) : null;
/*  78 */       GuiEntry guipagebuttonlist$guientry = new GuiEntry(gui, gui1);
/*  79 */       this.entries.add(guipagebuttonlist$guientry);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPage(int p_181156_1_) {
/*  85 */     if (p_181156_1_ != this.page) {
/*     */       
/*  87 */       int i = this.page;
/*  88 */       this.page = p_181156_1_;
/*  89 */       populateEntries();
/*  90 */       markVisibility(i, p_181156_1_);
/*  91 */       this.amountScrolled = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPage() {
/*  97 */     return this.page;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPageCount() {
/* 102 */     return this.pages.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public Gui getFocusedControl() {
/* 107 */     return this.focusedControl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void previousPage() {
/* 112 */     if (this.page > 0)
/*     */     {
/* 114 */       setPage(this.page - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextPage() {
/* 120 */     if (this.page < this.pages.length - 1)
/*     */     {
/* 122 */       setPage(this.page + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Gui getComponent(int p_178061_1_) {
/* 128 */     return (Gui)this.componentMap.lookup(p_178061_1_);
/*     */   } private void markVisibility(int p_178060_1_, int p_178060_2_) {
/*     */     byte b;
/*     */     int i;
/*     */     GuiListEntry[] arrayOfGuiListEntry;
/* 133 */     for (i = (arrayOfGuiListEntry = this.pages[p_178060_1_]).length, b = 0; b < i; ) { GuiListEntry guipagebuttonlist$guilistentry = arrayOfGuiListEntry[b];
/*     */       
/* 135 */       if (guipagebuttonlist$guilistentry != null)
/*     */       {
/* 137 */         setComponentVisibility((Gui)this.componentMap.lookup(guipagebuttonlist$guilistentry.getId()), false);
/*     */       }
/*     */       b++; }
/*     */     
/* 141 */     for (i = (arrayOfGuiListEntry = this.pages[p_178060_2_]).length, b = 0; b < i; ) { GuiListEntry guipagebuttonlist$guilistentry1 = arrayOfGuiListEntry[b];
/*     */       
/* 143 */       if (guipagebuttonlist$guilistentry1 != null)
/*     */       {
/* 145 */         setComponentVisibility((Gui)this.componentMap.lookup(guipagebuttonlist$guilistentry1.getId()), true);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private void setComponentVisibility(Gui p_178066_1_, boolean p_178066_2_) {
/* 152 */     if (p_178066_1_ instanceof GuiButton) {
/*     */       
/* 154 */       ((GuiButton)p_178066_1_).visible = p_178066_2_;
/*     */     }
/* 156 */     else if (p_178066_1_ instanceof GuiTextField) {
/*     */       
/* 158 */       ((GuiTextField)p_178066_1_).setVisible(p_178066_2_);
/*     */     }
/* 160 */     else if (p_178066_1_ instanceof GuiLabel) {
/*     */       
/* 162 */       ((GuiLabel)p_178066_1_).visible = p_178066_2_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Gui createEntry(@Nullable GuiListEntry p_178058_1_, int p_178058_2_, boolean p_178058_3_) {
/* 169 */     if (p_178058_1_ instanceof GuiSlideEntry)
/*     */     {
/* 171 */       return createSlider(this.width / 2 - 155 + p_178058_2_, 0, (GuiSlideEntry)p_178058_1_);
/*     */     }
/* 173 */     if (p_178058_1_ instanceof GuiButtonEntry)
/*     */     {
/* 175 */       return createButton(this.width / 2 - 155 + p_178058_2_, 0, (GuiButtonEntry)p_178058_1_);
/*     */     }
/* 177 */     if (p_178058_1_ instanceof EditBoxEntry)
/*     */     {
/* 179 */       return createTextField(this.width / 2 - 155 + p_178058_2_, 0, (EditBoxEntry)p_178058_1_);
/*     */     }
/*     */ 
/*     */     
/* 183 */     return (p_178058_1_ instanceof GuiLabelEntry) ? createLabel(this.width / 2 - 155 + p_178058_2_, 0, (GuiLabelEntry)p_178058_1_, p_178058_3_) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean p_181155_1_) {
/* 189 */     for (GuiEntry guipagebuttonlist$guientry : this.entries) {
/*     */       
/* 191 */       if (guipagebuttonlist$guientry.component1 instanceof GuiButton)
/*     */       {
/* 193 */         ((GuiButton)guipagebuttonlist$guientry.component1).enabled = p_181155_1_;
/*     */       }
/*     */       
/* 196 */       if (guipagebuttonlist$guientry.component2 instanceof GuiButton)
/*     */       {
/* 198 */         ((GuiButton)guipagebuttonlist$guientry.component2).enabled = p_181155_1_;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
/* 205 */     boolean flag = super.mouseClicked(mouseX, mouseY, mouseEvent);
/* 206 */     int i = getSlotIndexFromScreenCoords(mouseX, mouseY);
/*     */     
/* 208 */     if (i >= 0) {
/*     */       
/* 210 */       GuiEntry guipagebuttonlist$guientry = getListEntry(i);
/*     */       
/* 212 */       if (this.focusedControl != guipagebuttonlist$guientry.focusedControl && this.focusedControl != null && this.focusedControl instanceof GuiTextField)
/*     */       {
/* 214 */         ((GuiTextField)this.focusedControl).setFocused(false);
/*     */       }
/*     */       
/* 217 */       this.focusedControl = guipagebuttonlist$guientry.focusedControl;
/*     */     } 
/*     */     
/* 220 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiSlider createSlider(int p_178067_1_, int p_178067_2_, GuiSlideEntry p_178067_3_) {
/* 225 */     GuiSlider guislider = new GuiSlider(this.responder, p_178067_3_.getId(), p_178067_1_, p_178067_2_, p_178067_3_.getCaption(), p_178067_3_.getMinValue(), p_178067_3_.getMaxValue(), p_178067_3_.getInitalValue(), p_178067_3_.getFormatter());
/* 226 */     guislider.visible = p_178067_3_.shouldStartVisible();
/* 227 */     return guislider;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiListButton createButton(int p_178065_1_, int p_178065_2_, GuiButtonEntry p_178065_3_) {
/* 232 */     GuiListButton guilistbutton = new GuiListButton(this.responder, p_178065_3_.getId(), p_178065_1_, p_178065_2_, p_178065_3_.getCaption(), p_178065_3_.getInitialValue());
/* 233 */     guilistbutton.visible = p_178065_3_.shouldStartVisible();
/* 234 */     return guilistbutton;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiTextField createTextField(int p_178068_1_, int p_178068_2_, EditBoxEntry p_178068_3_) {
/* 239 */     GuiTextField guitextfield = new GuiTextField(p_178068_3_.getId(), this.mc.fontRendererObj, p_178068_1_, p_178068_2_, 150, 20);
/* 240 */     guitextfield.setText(p_178068_3_.getCaption());
/* 241 */     guitextfield.setGuiResponder(this.responder);
/* 242 */     guitextfield.setVisible(p_178068_3_.shouldStartVisible());
/* 243 */     guitextfield.setValidator(p_178068_3_.getFilter());
/* 244 */     return guitextfield;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiLabel createLabel(int p_178063_1_, int p_178063_2_, GuiLabelEntry p_178063_3_, boolean p_178063_4_) {
/*     */     GuiLabel guilabel;
/* 251 */     if (p_178063_4_) {
/*     */       
/* 253 */       guilabel = new GuiLabel(this.mc.fontRendererObj, p_178063_3_.getId(), p_178063_1_, p_178063_2_, this.width - p_178063_1_ * 2, 20, -1);
/*     */     }
/*     */     else {
/*     */       
/* 257 */       guilabel = new GuiLabel(this.mc.fontRendererObj, p_178063_3_.getId(), p_178063_1_, p_178063_2_, 150, 20, -1);
/*     */     } 
/*     */     
/* 260 */     guilabel.visible = p_178063_3_.shouldStartVisible();
/* 261 */     guilabel.addLine(p_178063_3_.getCaption());
/* 262 */     guilabel.setCentered();
/* 263 */     return guilabel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onKeyPressed(char p_178062_1_, int p_178062_2_) {
/* 268 */     if (this.focusedControl instanceof GuiTextField) {
/*     */       
/* 270 */       GuiTextField guitextfield = (GuiTextField)this.focusedControl;
/*     */       
/* 272 */       if (!GuiScreen.isKeyComboCtrlV(p_178062_2_)) {
/*     */         
/* 274 */         if (p_178062_2_ == 15) {
/*     */           
/* 276 */           guitextfield.setFocused(false);
/* 277 */           int k = this.editBoxes.indexOf(this.focusedControl);
/*     */           
/* 279 */           if (GuiScreen.isShiftKeyDown()) {
/*     */             
/* 281 */             if (k == 0)
/*     */             {
/* 283 */               k = this.editBoxes.size() - 1;
/*     */             }
/*     */             else
/*     */             {
/* 287 */               k--;
/*     */             }
/*     */           
/* 290 */           } else if (k == this.editBoxes.size() - 1) {
/*     */             
/* 292 */             k = 0;
/*     */           }
/*     */           else {
/*     */             
/* 296 */             k++;
/*     */           } 
/*     */           
/* 299 */           this.focusedControl = this.editBoxes.get(k);
/* 300 */           guitextfield = (GuiTextField)this.focusedControl;
/* 301 */           guitextfield.setFocused(true);
/* 302 */           int l = guitextfield.yPosition + this.slotHeight;
/* 303 */           int i1 = guitextfield.yPosition;
/*     */           
/* 305 */           if (l > this.bottom)
/*     */           {
/* 307 */             this.amountScrolled += (l - this.bottom);
/*     */           }
/* 309 */           else if (i1 < this.top)
/*     */           {
/* 311 */             this.amountScrolled = i1;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 316 */           guitextfield.textboxKeyTyped(p_178062_1_, p_178062_2_);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 321 */         String s = GuiScreen.getClipboardString();
/* 322 */         String[] astring = s.split(";");
/* 323 */         int i = this.editBoxes.indexOf(this.focusedControl);
/* 324 */         int j = i; byte b; int k;
/*     */         String[] arrayOfString1;
/* 326 */         for (k = (arrayOfString1 = astring).length, b = 0; b < k; ) { String s1 = arrayOfString1[b];
/*     */           
/* 328 */           GuiTextField guitextfield1 = this.editBoxes.get(j);
/* 329 */           guitextfield1.setText(s1);
/* 330 */           guitextfield1.func_190516_a(guitextfield1.getId(), s1);
/*     */           
/* 332 */           if (j == this.editBoxes.size() - 1) {
/*     */             
/* 334 */             j = 0;
/*     */           }
/*     */           else {
/*     */             
/* 338 */             j++;
/*     */           } 
/*     */           
/* 341 */           if (j == i) {
/*     */             break;
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEntry getListEntry(int index) {
/* 355 */     return this.entries.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 360 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 368 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 373 */     return super.getScrollBarX() + 32;
/*     */   }
/*     */   
/*     */   public static class EditBoxEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final Predicate<String> filter;
/*     */     
/*     */     public EditBoxEntry(int p_i45534_1_, String p_i45534_2_, boolean p_i45534_3_, Predicate<String> p_i45534_4_) {
/* 382 */       super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
/* 383 */       this.filter = (Predicate<String>)MoreObjects.firstNonNull(p_i45534_4_, Predicates.alwaysTrue());
/*     */     }
/*     */ 
/*     */     
/*     */     public Predicate<String> getFilter() {
/* 388 */       return this.filter;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiButtonEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final boolean initialValue;
/*     */     
/*     */     public GuiButtonEntry(int p_i45535_1_, String p_i45535_2_, boolean p_i45535_3_, boolean p_i45535_4_) {
/* 398 */       super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
/* 399 */       this.initialValue = p_i45535_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getInitialValue() {
/* 404 */       return this.initialValue;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiEntry
/*     */     implements GuiListExtended.IGuiListEntry {
/* 410 */     private final Minecraft client = Minecraft.getMinecraft();
/*     */     
/*     */     private final Gui component1;
/*     */     private final Gui component2;
/*     */     private Gui focusedControl;
/*     */     
/*     */     public GuiEntry(@Nullable Gui p_i45533_1_, @Nullable Gui p_i45533_2_) {
/* 417 */       this.component1 = p_i45533_1_;
/* 418 */       this.component2 = p_i45533_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Gui getComponent1() {
/* 423 */       return this.component1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Gui getComponent2() {
/* 428 */       return this.component2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/* 433 */       func_192636_a(this.component1, p_192634_3_, p_192634_6_, p_192634_7_, false, p_192634_9_);
/* 434 */       func_192636_a(this.component2, p_192634_3_, p_192634_6_, p_192634_7_, false, p_192634_9_);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_192636_a(Gui p_192636_1_, int p_192636_2_, int p_192636_3_, int p_192636_4_, boolean p_192636_5_, float p_192636_6_) {
/* 439 */       if (p_192636_1_ != null)
/*     */       {
/* 441 */         if (p_192636_1_ instanceof GuiButton) {
/*     */           
/* 443 */           func_192635_a((GuiButton)p_192636_1_, p_192636_2_, p_192636_3_, p_192636_4_, p_192636_5_, p_192636_6_);
/*     */         }
/* 445 */         else if (p_192636_1_ instanceof GuiTextField) {
/*     */           
/* 447 */           renderTextField((GuiTextField)p_192636_1_, p_192636_2_, p_192636_5_);
/*     */         }
/* 449 */         else if (p_192636_1_ instanceof GuiLabel) {
/*     */           
/* 451 */           renderLabel((GuiLabel)p_192636_1_, p_192636_2_, p_192636_3_, p_192636_4_, p_192636_5_);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_192635_a(GuiButton p_192635_1_, int p_192635_2_, int p_192635_3_, int p_192635_4_, boolean p_192635_5_, float p_192635_6_) {
/* 458 */       p_192635_1_.yPosition = p_192635_2_;
/*     */       
/* 460 */       if (!p_192635_5_)
/*     */       {
/* 462 */         p_192635_1_.func_191745_a(this.client, p_192635_3_, p_192635_4_, p_192635_6_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void renderTextField(GuiTextField p_178027_1_, int p_178027_2_, boolean p_178027_3_) {
/* 468 */       p_178027_1_.yPosition = p_178027_2_;
/*     */       
/* 470 */       if (!p_178027_3_)
/*     */       {
/* 472 */         p_178027_1_.drawTextBox();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void renderLabel(GuiLabel p_178025_1_, int p_178025_2_, int p_178025_3_, int p_178025_4_, boolean p_178025_5_) {
/* 478 */       p_178025_1_.y = p_178025_2_;
/*     */       
/* 480 */       if (!p_178025_5_)
/*     */       {
/* 482 */         p_178025_1_.drawLabel(this.client, p_178025_3_, p_178025_4_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
/* 488 */       func_192636_a(this.component1, p_192633_3_, 0, 0, true, p_192633_4_);
/* 489 */       func_192636_a(this.component2, p_192633_3_, 0, 0, true, p_192633_4_);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 494 */       boolean flag = clickComponent(this.component1, mouseX, mouseY, mouseEvent);
/* 495 */       boolean flag1 = clickComponent(this.component2, mouseX, mouseY, mouseEvent);
/* 496 */       return !(!flag && !flag1);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean clickComponent(Gui p_178026_1_, int p_178026_2_, int p_178026_3_, int p_178026_4_) {
/* 501 */       if (p_178026_1_ == null)
/*     */       {
/* 503 */         return false;
/*     */       }
/* 505 */       if (p_178026_1_ instanceof GuiButton)
/*     */       {
/* 507 */         return clickButton((GuiButton)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
/*     */       }
/*     */ 
/*     */       
/* 511 */       if (p_178026_1_ instanceof GuiTextField)
/*     */       {
/* 513 */         clickTextField((GuiTextField)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
/*     */       }
/*     */       
/* 516 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean clickButton(GuiButton p_178023_1_, int p_178023_2_, int p_178023_3_, int p_178023_4_) {
/* 522 */       boolean flag = p_178023_1_.mousePressed(this.client, p_178023_2_, p_178023_3_);
/*     */       
/* 524 */       if (flag)
/*     */       {
/* 526 */         this.focusedControl = p_178023_1_;
/*     */       }
/*     */       
/* 529 */       return flag;
/*     */     }
/*     */ 
/*     */     
/*     */     private void clickTextField(GuiTextField p_178018_1_, int p_178018_2_, int p_178018_3_, int p_178018_4_) {
/* 534 */       p_178018_1_.mouseClicked(p_178018_2_, p_178018_3_, p_178018_4_);
/*     */       
/* 536 */       if (p_178018_1_.isFocused())
/*     */       {
/* 538 */         this.focusedControl = p_178018_1_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 544 */       releaseComponent(this.component1, x, y, mouseEvent);
/* 545 */       releaseComponent(this.component2, x, y, mouseEvent);
/*     */     }
/*     */ 
/*     */     
/*     */     private void releaseComponent(Gui p_178016_1_, int p_178016_2_, int p_178016_3_, int p_178016_4_) {
/* 550 */       if (p_178016_1_ != null)
/*     */       {
/* 552 */         if (p_178016_1_ instanceof GuiButton)
/*     */         {
/* 554 */           releaseButton((GuiButton)p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void releaseButton(GuiButton p_178019_1_, int p_178019_2_, int p_178019_3_, int p_178019_4_) {
/* 561 */       p_178019_1_.mouseReleased(p_178019_2_, p_178019_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiLabelEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     public GuiLabelEntry(int p_i45532_1_, String p_i45532_2_, boolean p_i45532_3_) {
/* 569 */       super(p_i45532_1_, p_i45532_2_, p_i45532_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GuiListEntry
/*     */   {
/*     */     private final int id;
/*     */     private final String caption;
/*     */     private final boolean startVisible;
/*     */     
/*     */     public GuiListEntry(int p_i45531_1_, String p_i45531_2_, boolean p_i45531_3_) {
/* 581 */       this.id = p_i45531_1_;
/* 582 */       this.caption = p_i45531_2_;
/* 583 */       this.startVisible = p_i45531_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 588 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCaption() {
/* 593 */       return this.caption;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldStartVisible() {
/* 598 */       return this.startVisible;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface GuiResponder
/*     */   {
/*     */     void setEntryValue(int param1Int, boolean param1Boolean);
/*     */     
/*     */     void setEntryValue(int param1Int, float param1Float);
/*     */     
/*     */     void setEntryValue(int param1Int, String param1String);
/*     */   }
/*     */   
/*     */   public static class GuiSlideEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final GuiSlider.FormatHelper formatter;
/*     */     private final float minValue;
/*     */     private final float maxValue;
/*     */     private final float initialValue;
/*     */     
/*     */     public GuiSlideEntry(int p_i45530_1_, String p_i45530_2_, boolean p_i45530_3_, GuiSlider.FormatHelper p_i45530_4_, float p_i45530_5_, float p_i45530_6_, float p_i45530_7_) {
/* 620 */       super(p_i45530_1_, p_i45530_2_, p_i45530_3_);
/* 621 */       this.formatter = p_i45530_4_;
/* 622 */       this.minValue = p_i45530_5_;
/* 623 */       this.maxValue = p_i45530_6_;
/* 624 */       this.initialValue = p_i45530_7_;
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiSlider.FormatHelper getFormatter() {
/* 629 */       return this.formatter;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getMinValue() {
/* 634 */       return this.minValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getMaxValue() {
/* 639 */       return this.maxValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getInitalValue() {
/* 644 */       return this.initialValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiPageButtonList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
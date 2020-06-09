/*     */ package net.minecraft.client.gui.achievement;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.IProgressMeter;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketClientStatus;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatCrafting;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.stats.StatisticsManager;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiStats extends GuiScreen implements IProgressMeter {
/*  35 */   protected String screenTitle = "Select world";
/*     */   
/*     */   protected GuiScreen parentScreen;
/*     */   
/*     */   private StatsGeneral generalStats;
/*     */   private StatsItem itemStats;
/*     */   private StatsBlock blockStats;
/*     */   private StatsMobsList mobStats;
/*     */   private final StatisticsManager stats;
/*     */   private GuiSlot displaySlot;
/*     */   private boolean doesGuiPauseGame = true;
/*     */   
/*     */   public GuiStats(GuiScreen p_i1071_1_, StatisticsManager p_i1071_2_) {
/*  48 */     this.parentScreen = p_i1071_1_;
/*  49 */     this.stats = p_i1071_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  58 */     this.screenTitle = I18n.format("gui.stats", new Object[0]);
/*  59 */     this.doesGuiPauseGame = true;
/*  60 */     this.mc.getConnection().sendPacket((Packet)new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  68 */     super.handleMouseInput();
/*     */     
/*  70 */     if (this.displaySlot != null)
/*     */     {
/*  72 */       this.displaySlot.handleMouseInput();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193028_a() {
/*  78 */     this.generalStats = new StatsGeneral(this.mc);
/*  79 */     this.generalStats.registerScrollButtons(1, 1);
/*  80 */     this.itemStats = new StatsItem(this.mc);
/*  81 */     this.itemStats.registerScrollButtons(1, 1);
/*  82 */     this.blockStats = new StatsBlock(this.mc);
/*  83 */     this.blockStats.registerScrollButtons(1, 1);
/*  84 */     this.mobStats = new StatsMobsList(this.mc);
/*  85 */     this.mobStats.registerScrollButtons(1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193029_f() {
/*  90 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  91 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 160, this.height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
/*  92 */     GuiButton guibutton = addButton(new GuiButton(2, this.width / 2 - 80, this.height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
/*  93 */     GuiButton guibutton1 = addButton(new GuiButton(3, this.width / 2, this.height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
/*  94 */     GuiButton guibutton2 = addButton(new GuiButton(4, this.width / 2 + 80, this.height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
/*     */     
/*  96 */     if (this.blockStats.getSize() == 0)
/*     */     {
/*  98 */       guibutton.enabled = false;
/*     */     }
/*     */     
/* 101 */     if (this.itemStats.getSize() == 0)
/*     */     {
/* 103 */       guibutton1.enabled = false;
/*     */     }
/*     */     
/* 106 */     if (this.mobStats.getSize() == 0)
/*     */     {
/* 108 */       guibutton2.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 117 */     if (button.enabled)
/*     */     {
/* 119 */       if (button.id == 0) {
/*     */         
/* 121 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 123 */       else if (button.id == 1) {
/*     */         
/* 125 */         this.displaySlot = this.generalStats;
/*     */       }
/* 127 */       else if (button.id == 3) {
/*     */         
/* 129 */         this.displaySlot = this.itemStats;
/*     */       }
/* 131 */       else if (button.id == 2) {
/*     */         
/* 133 */         this.displaySlot = this.blockStats;
/*     */       }
/* 135 */       else if (button.id == 4) {
/*     */         
/* 137 */         this.displaySlot = this.mobStats;
/*     */       }
/*     */       else {
/*     */         
/* 141 */         this.displaySlot.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 151 */     if (this.doesGuiPauseGame) {
/*     */       
/* 153 */       drawDefaultBackground();
/* 154 */       drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
/* 155 */       drawCenteredString(this.fontRendererObj, LOADING_STRINGS[(int)(Minecraft.getSystemTime() / 150L % LOADING_STRINGS.length)], this.width / 2, this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/*     */     }
/*     */     else {
/*     */       
/* 159 */       drawDefaultBackground();
/* 160 */       this.displaySlot.drawScreen(mouseX, mouseY, partialTicks);
/* 161 */       drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
/* 162 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193026_g() {
/* 168 */     if (this.doesGuiPauseGame) {
/*     */       
/* 170 */       func_193028_a();
/* 171 */       func_193029_f();
/* 172 */       this.displaySlot = this.generalStats;
/* 173 */       this.doesGuiPauseGame = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 182 */     return !this.doesGuiPauseGame;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawStatsScreen(int p_146521_1_, int p_146521_2_, Item p_146521_3_) {
/* 187 */     drawButtonBackground(p_146521_1_ + 1, p_146521_2_ + 1);
/* 188 */     GlStateManager.enableRescaleNormal();
/* 189 */     RenderHelper.enableGUIStandardItemLighting();
/* 190 */     this.itemRender.renderItemIntoGUI(p_146521_3_.func_190903_i(), p_146521_1_ + 2, p_146521_2_ + 2);
/* 191 */     RenderHelper.disableStandardItemLighting();
/* 192 */     GlStateManager.disableRescaleNormal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawButtonBackground(int p_146531_1_, int p_146531_2_) {
/* 200 */     drawSprite(p_146531_1_, p_146531_2_, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSprite(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_) {
/* 208 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 209 */     this.mc.getTextureManager().bindTexture(STAT_ICONS);
/* 210 */     float f = 0.0078125F;
/* 211 */     float f1 = 0.0078125F;
/* 212 */     int i = 18;
/* 213 */     int j = 18;
/* 214 */     Tessellator tessellator = Tessellator.getInstance();
/* 215 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 216 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 217 */     bufferbuilder.pos((p_146527_1_ + 0), (p_146527_2_ + 18), this.zLevel).tex(((p_146527_3_ + 0) * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex();
/* 218 */     bufferbuilder.pos((p_146527_1_ + 18), (p_146527_2_ + 18), this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex();
/* 219 */     bufferbuilder.pos((p_146527_1_ + 18), (p_146527_2_ + 0), this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), ((p_146527_4_ + 0) * 0.0078125F)).endVertex();
/* 220 */     bufferbuilder.pos((p_146527_1_ + 0), (p_146527_2_ + 0), this.zLevel).tex(((p_146527_3_ + 0) * 0.0078125F), ((p_146527_4_ + 0) * 0.0078125F)).endVertex();
/* 221 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   abstract class Stats
/*     */     extends GuiSlot {
/* 226 */     protected int headerPressed = -1;
/*     */     protected List<StatCrafting> statsHolder;
/*     */     protected Comparator<StatCrafting> statSorter;
/* 229 */     protected int sortColumn = -1;
/*     */     
/*     */     protected int sortOrder;
/*     */     
/*     */     protected Stats(Minecraft p_i47550_2_) {
/* 234 */       super(p_i47550_2_, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 20);
/* 235 */       func_193651_b(false);
/* 236 */       setHasListHeader(true, 20);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 245 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListWidth() {
/* 250 */       return 375;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 255 */       return this.width / 2 + 140;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 260 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
/* 265 */       if (!Mouse.isButtonDown(0))
/*     */       {
/* 267 */         this.headerPressed = -1;
/*     */       }
/*     */       
/* 270 */       if (this.headerPressed == 0) {
/*     */         
/* 272 */         GuiStats.this.drawSprite(insideLeft + 115 - 18, insideTop + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 276 */         GuiStats.this.drawSprite(insideLeft + 115 - 18, insideTop + 1, 0, 18);
/*     */       } 
/*     */       
/* 279 */       if (this.headerPressed == 1) {
/*     */         
/* 281 */         GuiStats.this.drawSprite(insideLeft + 165 - 18, insideTop + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 285 */         GuiStats.this.drawSprite(insideLeft + 165 - 18, insideTop + 1, 0, 18);
/*     */       } 
/*     */       
/* 288 */       if (this.headerPressed == 2) {
/*     */         
/* 290 */         GuiStats.this.drawSprite(insideLeft + 215 - 18, insideTop + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 294 */         GuiStats.this.drawSprite(insideLeft + 215 - 18, insideTop + 1, 0, 18);
/*     */       } 
/*     */       
/* 297 */       if (this.headerPressed == 3) {
/*     */         
/* 299 */         GuiStats.this.drawSprite(insideLeft + 265 - 18, insideTop + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 303 */         GuiStats.this.drawSprite(insideLeft + 265 - 18, insideTop + 1, 0, 18);
/*     */       } 
/*     */       
/* 306 */       if (this.headerPressed == 4) {
/*     */         
/* 308 */         GuiStats.this.drawSprite(insideLeft + 315 - 18, insideTop + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 312 */         GuiStats.this.drawSprite(insideLeft + 315 - 18, insideTop + 1, 0, 18);
/*     */       } 
/*     */       
/* 315 */       if (this.sortColumn != -1) {
/*     */         
/* 317 */         int i = 79;
/* 318 */         int j = 18;
/*     */         
/* 320 */         if (this.sortColumn == 1) {
/*     */           
/* 322 */           i = 129;
/*     */         }
/* 324 */         else if (this.sortColumn == 2) {
/*     */           
/* 326 */           i = 179;
/*     */         }
/* 328 */         else if (this.sortColumn == 3) {
/*     */           
/* 330 */           i = 229;
/*     */         }
/* 332 */         else if (this.sortColumn == 4) {
/*     */           
/* 334 */           i = 279;
/*     */         } 
/*     */         
/* 337 */         if (this.sortOrder == 1)
/*     */         {
/* 339 */           j = 36;
/*     */         }
/*     */         
/* 342 */         GuiStats.this.drawSprite(insideLeft + i, insideTop + 1, j, 0);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void clickedHeader(int p_148132_1_, int p_148132_2_) {
/* 348 */       this.headerPressed = -1;
/*     */       
/* 350 */       if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
/*     */         
/* 352 */         this.headerPressed = 0;
/*     */       }
/* 354 */       else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
/*     */         
/* 356 */         this.headerPressed = 1;
/*     */       }
/* 358 */       else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
/*     */         
/* 360 */         this.headerPressed = 2;
/*     */       }
/* 362 */       else if (p_148132_1_ >= 229 && p_148132_1_ < 265) {
/*     */         
/* 364 */         this.headerPressed = 3;
/*     */       }
/* 366 */       else if (p_148132_1_ >= 279 && p_148132_1_ < 315) {
/*     */         
/* 368 */         this.headerPressed = 4;
/*     */       } 
/*     */       
/* 371 */       if (this.headerPressed >= 0) {
/*     */         
/* 373 */         sortByColumn(this.headerPressed);
/* 374 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected final int getSize() {
/* 380 */       return this.statsHolder.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final StatCrafting getSlotStat(int p_148211_1_) {
/* 385 */       return this.statsHolder.get(p_148211_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract String getHeaderDescriptionId(int param1Int);
/*     */     
/*     */     protected void renderStat(StatBase p_148209_1_, int p_148209_2_, int p_148209_3_, boolean p_148209_4_) {
/* 392 */       if (p_148209_1_ != null) {
/*     */         
/* 394 */         String s = p_148209_1_.format(GuiStats.this.stats.readStat(p_148209_1_));
/* 395 */         GuiStats.drawString(GuiStats.this.fontRendererObj, s, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(s), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/*     */       }
/*     */       else {
/*     */         
/* 399 */         String s1 = "-";
/* 400 */         GuiStats.drawString(GuiStats.this.fontRendererObj, "-", p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth("-"), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderDecorations(int mouseXIn, int mouseYIn) {
/* 406 */       if (mouseYIn >= this.top && mouseYIn <= this.bottom) {
/*     */         
/* 408 */         int i = getSlotIndexFromScreenCoords(mouseXIn, mouseYIn);
/* 409 */         int j = (this.width - getListWidth()) / 2;
/*     */         
/* 411 */         if (i >= 0) {
/*     */           
/* 413 */           if (mouseXIn < j + 40 || mouseXIn > j + 40 + 20) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 418 */           StatCrafting statcrafting = getSlotStat(i);
/* 419 */           renderMouseHoverToolTip(statcrafting, mouseXIn, mouseYIn);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 425 */           if (mouseXIn >= j + 115 - 18 && mouseXIn <= j + 115) {
/*     */             
/* 427 */             s = getHeaderDescriptionId(0);
/*     */           }
/* 429 */           else if (mouseXIn >= j + 165 - 18 && mouseXIn <= j + 165) {
/*     */             
/* 431 */             s = getHeaderDescriptionId(1);
/*     */           }
/* 433 */           else if (mouseXIn >= j + 215 - 18 && mouseXIn <= j + 215) {
/*     */             
/* 435 */             s = getHeaderDescriptionId(2);
/*     */           }
/* 437 */           else if (mouseXIn >= j + 265 - 18 && mouseXIn <= j + 265) {
/*     */             
/* 439 */             s = getHeaderDescriptionId(3);
/*     */           }
/*     */           else {
/*     */             
/* 443 */             if (mouseXIn < j + 315 - 18 || mouseXIn > j + 315) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 448 */             s = getHeaderDescriptionId(4);
/*     */           } 
/*     */           
/* 451 */           String s = I18n.format(s, new Object[0]).trim();
/*     */           
/* 453 */           if (!s.isEmpty()) {
/*     */             
/* 455 */             int k = mouseXIn + 12;
/* 456 */             int l = mouseYIn - 12;
/* 457 */             int i1 = GuiStats.this.fontRendererObj.getStringWidth(s);
/* 458 */             GuiStats.this.drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, -1073741824, -1073741824);
/* 459 */             GuiStats.this.fontRendererObj.drawStringWithShadow(s, k, l, -1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderMouseHoverToolTip(StatCrafting p_148213_1_, int p_148213_2_, int p_148213_3_) {
/* 467 */       if (p_148213_1_ != null) {
/*     */         
/* 469 */         Item item = p_148213_1_.getItem();
/* 470 */         ItemStack itemstack = new ItemStack(item);
/* 471 */         String s = itemstack.getUnlocalizedName();
/* 472 */         String s1 = I18n.format(String.valueOf(s) + ".name", new Object[0]).trim();
/*     */         
/* 474 */         if (!s1.isEmpty()) {
/*     */           
/* 476 */           int i = p_148213_2_ + 12;
/* 477 */           int j = p_148213_3_ - 12;
/* 478 */           int k = GuiStats.this.fontRendererObj.getStringWidth(s1);
/* 479 */           GuiStats.this.drawGradientRect(i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
/* 480 */           GuiStats.this.fontRendererObj.drawStringWithShadow(s1, i, j, -1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void sortByColumn(int p_148212_1_) {
/* 487 */       if (p_148212_1_ != this.sortColumn) {
/*     */         
/* 489 */         this.sortColumn = p_148212_1_;
/* 490 */         this.sortOrder = -1;
/*     */       }
/* 492 */       else if (this.sortOrder == -1) {
/*     */         
/* 494 */         this.sortOrder = 1;
/*     */       }
/*     */       else {
/*     */         
/* 498 */         this.sortColumn = -1;
/* 499 */         this.sortOrder = 0;
/*     */       } 
/*     */       
/* 502 */       Collections.sort(this.statsHolder, this.statSorter);
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsBlock
/*     */     extends Stats
/*     */   {
/*     */     public StatsBlock(Minecraft p_i47554_2_) {
/* 510 */       super(p_i47554_2_);
/* 511 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 513 */       for (StatCrafting statcrafting : StatList.MINE_BLOCK_STATS) {
/*     */         
/* 515 */         boolean flag = false;
/* 516 */         Item item = statcrafting.getItem();
/*     */         
/* 518 */         if (GuiStats.this.stats.readStat((StatBase)statcrafting) > 0) {
/*     */           
/* 520 */           flag = true;
/*     */         }
/* 522 */         else if (StatList.getObjectUseStats(item) != null && GuiStats.this.stats.readStat(StatList.getObjectUseStats(item)) > 0) {
/*     */           
/* 524 */           flag = true;
/*     */         }
/* 526 */         else if (StatList.getCraftStats(item) != null && GuiStats.this.stats.readStat(StatList.getCraftStats(item)) > 0) {
/*     */           
/* 528 */           flag = true;
/*     */         }
/* 530 */         else if (StatList.getObjectsPickedUpStats(item) != null && GuiStats.this.stats.readStat(StatList.getObjectsPickedUpStats(item)) > 0) {
/*     */           
/* 532 */           flag = true;
/*     */         }
/* 534 */         else if (StatList.getDroppedObjectStats(item) != null && GuiStats.this.stats.readStat(StatList.getDroppedObjectStats(item)) > 0) {
/*     */           
/* 536 */           flag = true;
/*     */         } 
/*     */         
/* 539 */         if (flag)
/*     */         {
/* 541 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 545 */       this.statSorter = new Comparator<StatCrafting>()
/*     */         {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_)
/*     */           {
/* 549 */             Item item1 = p_compare_1_.getItem();
/* 550 */             Item item2 = p_compare_2_.getItem();
/* 551 */             StatBase statbase = null;
/* 552 */             StatBase statbase1 = null;
/*     */             
/* 554 */             if (GuiStats.StatsBlock.this.sortColumn == 2) {
/*     */               
/* 556 */               statbase = StatList.getBlockStats(Block.getBlockFromItem(item1));
/* 557 */               statbase1 = StatList.getBlockStats(Block.getBlockFromItem(item2));
/*     */             }
/* 559 */             else if (GuiStats.StatsBlock.this.sortColumn == 0) {
/*     */               
/* 561 */               statbase = StatList.getCraftStats(item1);
/* 562 */               statbase1 = StatList.getCraftStats(item2);
/*     */             }
/* 564 */             else if (GuiStats.StatsBlock.this.sortColumn == 1) {
/*     */               
/* 566 */               statbase = StatList.getObjectUseStats(item1);
/* 567 */               statbase1 = StatList.getObjectUseStats(item2);
/*     */             }
/* 569 */             else if (GuiStats.StatsBlock.this.sortColumn == 3) {
/*     */               
/* 571 */               statbase = StatList.getObjectsPickedUpStats(item1);
/* 572 */               statbase1 = StatList.getObjectsPickedUpStats(item2);
/*     */             }
/* 574 */             else if (GuiStats.StatsBlock.this.sortColumn == 4) {
/*     */               
/* 576 */               statbase = StatList.getDroppedObjectStats(item1);
/* 577 */               statbase1 = StatList.getDroppedObjectStats(item2);
/*     */             } 
/*     */             
/* 580 */             if (statbase != null || statbase1 != null) {
/*     */               
/* 582 */               if (statbase == null)
/*     */               {
/* 584 */                 return 1;
/*     */               }
/*     */               
/* 587 */               if (statbase1 == null)
/*     */               {
/* 589 */                 return -1;
/*     */               }
/*     */               
/* 592 */               int i = (GuiStats.StatsBlock.access$0(GuiStats.StatsBlock.this)).stats.readStat(statbase);
/* 593 */               int j = (GuiStats.StatsBlock.access$0(GuiStats.StatsBlock.this)).stats.readStat(statbase1);
/*     */               
/* 595 */               if (i != j)
/*     */               {
/* 597 */                 return (i - j) * GuiStats.StatsBlock.this.sortOrder;
/*     */               }
/*     */             } 
/*     */             
/* 601 */             return Item.getIdFromItem(item1) - Item.getIdFromItem(item2);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
/* 608 */       super.drawListHeader(insideLeft, insideTop, tessellatorIn);
/*     */       
/* 610 */       if (this.headerPressed == 0) {
/*     */         
/* 612 */         GuiStats.this.drawSprite(insideLeft + 115 - 18 + 1, insideTop + 1 + 1, 18, 18);
/*     */       }
/*     */       else {
/*     */         
/* 616 */         GuiStats.this.drawSprite(insideLeft + 115 - 18, insideTop + 1, 18, 18);
/*     */       } 
/*     */       
/* 619 */       if (this.headerPressed == 1) {
/*     */         
/* 621 */         GuiStats.this.drawSprite(insideLeft + 165 - 18 + 1, insideTop + 1 + 1, 36, 18);
/*     */       }
/*     */       else {
/*     */         
/* 625 */         GuiStats.this.drawSprite(insideLeft + 165 - 18, insideTop + 1, 36, 18);
/*     */       } 
/*     */       
/* 628 */       if (this.headerPressed == 2) {
/*     */         
/* 630 */         GuiStats.this.drawSprite(insideLeft + 215 - 18 + 1, insideTop + 1 + 1, 54, 18);
/*     */       }
/*     */       else {
/*     */         
/* 634 */         GuiStats.this.drawSprite(insideLeft + 215 - 18, insideTop + 1, 54, 18);
/*     */       } 
/*     */       
/* 637 */       if (this.headerPressed == 3) {
/*     */         
/* 639 */         GuiStats.this.drawSprite(insideLeft + 265 - 18 + 1, insideTop + 1 + 1, 90, 18);
/*     */       }
/*     */       else {
/*     */         
/* 643 */         GuiStats.this.drawSprite(insideLeft + 265 - 18, insideTop + 1, 90, 18);
/*     */       } 
/*     */       
/* 646 */       if (this.headerPressed == 4) {
/*     */         
/* 648 */         GuiStats.this.drawSprite(insideLeft + 315 - 18 + 1, insideTop + 1 + 1, 108, 18);
/*     */       }
/*     */       else {
/*     */         
/* 652 */         GuiStats.this.drawSprite(insideLeft + 315 - 18, insideTop + 1, 108, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 658 */       StatCrafting statcrafting = getSlotStat(p_192637_1_);
/* 659 */       Item item = statcrafting.getItem();
/* 660 */       GuiStats.this.drawStatsScreen(p_192637_2_ + 40, p_192637_3_, item);
/* 661 */       renderStat(StatList.getCraftStats(item), p_192637_2_ + 115, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 662 */       renderStat(StatList.getObjectUseStats(item), p_192637_2_ + 165, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 663 */       renderStat((StatBase)statcrafting, p_192637_2_ + 215, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 664 */       renderStat(StatList.getObjectsPickedUpStats(item), p_192637_2_ + 265, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 665 */       renderStat(StatList.getDroppedObjectStats(item), p_192637_2_ + 315, p_192637_3_, (p_192637_1_ % 2 == 0));
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getHeaderDescriptionId(int p_148210_1_) {
/* 670 */       if (p_148210_1_ == 0)
/*     */       {
/* 672 */         return "stat.crafted";
/*     */       }
/* 674 */       if (p_148210_1_ == 1)
/*     */       {
/* 676 */         return "stat.used";
/*     */       }
/* 678 */       if (p_148210_1_ == 3)
/*     */       {
/* 680 */         return "stat.pickup";
/*     */       }
/*     */ 
/*     */       
/* 684 */       return (p_148210_1_ == 4) ? "stat.dropped" : "stat.mined";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class StatsGeneral
/*     */     extends GuiSlot
/*     */   {
/*     */     public StatsGeneral(Minecraft p_i47553_2_) {
/* 693 */       super(p_i47553_2_, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
/* 694 */       func_193651_b(false);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 699 */       return StatList.BASIC_STATS.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 708 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 713 */       return getSize() * 10;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 718 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 723 */       StatBase statbase = StatList.BASIC_STATS.get(p_192637_1_);
/* 724 */       GuiStats.drawString(GuiStats.this.fontRendererObj, statbase.getStatName().getUnformattedText(), p_192637_2_ + 2, p_192637_3_ + 1, (p_192637_1_ % 2 == 0) ? 16777215 : 9474192);
/* 725 */       String s = statbase.format(GuiStats.this.stats.readStat(statbase));
/* 726 */       GuiStats.drawString(GuiStats.this.fontRendererObj, s, p_192637_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(s), p_192637_3_ + 1, (p_192637_1_ % 2 == 0) ? 16777215 : 9474192);
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsItem
/*     */     extends Stats
/*     */   {
/*     */     public StatsItem(Minecraft p_i47552_2_) {
/* 734 */       super(p_i47552_2_);
/* 735 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 737 */       for (StatCrafting statcrafting : StatList.USE_ITEM_STATS) {
/*     */         
/* 739 */         boolean flag = false;
/* 740 */         Item item = statcrafting.getItem();
/*     */         
/* 742 */         if (GuiStats.this.stats.readStat((StatBase)statcrafting) > 0) {
/*     */           
/* 744 */           flag = true;
/*     */         }
/* 746 */         else if (StatList.getObjectBreakStats(item) != null && GuiStats.this.stats.readStat(StatList.getObjectBreakStats(item)) > 0) {
/*     */           
/* 748 */           flag = true;
/*     */         }
/* 750 */         else if (StatList.getCraftStats(item) != null && GuiStats.this.stats.readStat(StatList.getCraftStats(item)) > 0) {
/*     */           
/* 752 */           flag = true;
/*     */         }
/* 754 */         else if (StatList.getObjectsPickedUpStats(item) != null && GuiStats.this.stats.readStat(StatList.getObjectsPickedUpStats(item)) > 0) {
/*     */           
/* 756 */           flag = true;
/*     */         }
/* 758 */         else if (StatList.getDroppedObjectStats(item) != null && GuiStats.this.stats.readStat(StatList.getDroppedObjectStats(item)) > 0) {
/*     */           
/* 760 */           flag = true;
/*     */         } 
/*     */         
/* 763 */         if (flag)
/*     */         {
/* 765 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 769 */       this.statSorter = new Comparator<StatCrafting>()
/*     */         {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_)
/*     */           {
/* 773 */             Item item1 = p_compare_1_.getItem();
/* 774 */             Item item2 = p_compare_2_.getItem();
/* 775 */             int i = Item.getIdFromItem(item1);
/* 776 */             int j = Item.getIdFromItem(item2);
/* 777 */             StatBase statbase = null;
/* 778 */             StatBase statbase1 = null;
/*     */             
/* 780 */             if (GuiStats.StatsItem.this.sortColumn == 0) {
/*     */               
/* 782 */               statbase = StatList.getObjectBreakStats(item1);
/* 783 */               statbase1 = StatList.getObjectBreakStats(item2);
/*     */             }
/* 785 */             else if (GuiStats.StatsItem.this.sortColumn == 1) {
/*     */               
/* 787 */               statbase = StatList.getCraftStats(item1);
/* 788 */               statbase1 = StatList.getCraftStats(item2);
/*     */             }
/* 790 */             else if (GuiStats.StatsItem.this.sortColumn == 2) {
/*     */               
/* 792 */               statbase = StatList.getObjectUseStats(item1);
/* 793 */               statbase1 = StatList.getObjectUseStats(item2);
/*     */             }
/* 795 */             else if (GuiStats.StatsItem.this.sortColumn == 3) {
/*     */               
/* 797 */               statbase = StatList.getObjectsPickedUpStats(item1);
/* 798 */               statbase1 = StatList.getObjectsPickedUpStats(item2);
/*     */             }
/* 800 */             else if (GuiStats.StatsItem.this.sortColumn == 4) {
/*     */               
/* 802 */               statbase = StatList.getDroppedObjectStats(item1);
/* 803 */               statbase1 = StatList.getDroppedObjectStats(item2);
/*     */             } 
/*     */             
/* 806 */             if (statbase != null || statbase1 != null) {
/*     */               
/* 808 */               if (statbase == null)
/*     */               {
/* 810 */                 return 1;
/*     */               }
/*     */               
/* 813 */               if (statbase1 == null)
/*     */               {
/* 815 */                 return -1;
/*     */               }
/*     */               
/* 818 */               int k = (GuiStats.StatsItem.access$0(GuiStats.StatsItem.this)).stats.readStat(statbase);
/* 819 */               int l = (GuiStats.StatsItem.access$0(GuiStats.StatsItem.this)).stats.readStat(statbase1);
/*     */               
/* 821 */               if (k != l)
/*     */               {
/* 823 */                 return (k - l) * GuiStats.StatsItem.this.sortOrder;
/*     */               }
/*     */             } 
/*     */             
/* 827 */             return i - j;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
/* 834 */       super.drawListHeader(insideLeft, insideTop, tessellatorIn);
/*     */       
/* 836 */       if (this.headerPressed == 0) {
/*     */         
/* 838 */         GuiStats.this.drawSprite(insideLeft + 115 - 18 + 1, insideTop + 1 + 1, 72, 18);
/*     */       }
/*     */       else {
/*     */         
/* 842 */         GuiStats.this.drawSprite(insideLeft + 115 - 18, insideTop + 1, 72, 18);
/*     */       } 
/*     */       
/* 845 */       if (this.headerPressed == 1) {
/*     */         
/* 847 */         GuiStats.this.drawSprite(insideLeft + 165 - 18 + 1, insideTop + 1 + 1, 18, 18);
/*     */       }
/*     */       else {
/*     */         
/* 851 */         GuiStats.this.drawSprite(insideLeft + 165 - 18, insideTop + 1, 18, 18);
/*     */       } 
/*     */       
/* 854 */       if (this.headerPressed == 2) {
/*     */         
/* 856 */         GuiStats.this.drawSprite(insideLeft + 215 - 18 + 1, insideTop + 1 + 1, 36, 18);
/*     */       }
/*     */       else {
/*     */         
/* 860 */         GuiStats.this.drawSprite(insideLeft + 215 - 18, insideTop + 1, 36, 18);
/*     */       } 
/*     */       
/* 863 */       if (this.headerPressed == 3) {
/*     */         
/* 865 */         GuiStats.this.drawSprite(insideLeft + 265 - 18 + 1, insideTop + 1 + 1, 90, 18);
/*     */       }
/*     */       else {
/*     */         
/* 869 */         GuiStats.this.drawSprite(insideLeft + 265 - 18, insideTop + 1, 90, 18);
/*     */       } 
/*     */       
/* 872 */       if (this.headerPressed == 4) {
/*     */         
/* 874 */         GuiStats.this.drawSprite(insideLeft + 315 - 18 + 1, insideTop + 1 + 1, 108, 18);
/*     */       }
/*     */       else {
/*     */         
/* 878 */         GuiStats.this.drawSprite(insideLeft + 315 - 18, insideTop + 1, 108, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 884 */       StatCrafting statcrafting = getSlotStat(p_192637_1_);
/* 885 */       Item item = statcrafting.getItem();
/* 886 */       GuiStats.this.drawStatsScreen(p_192637_2_ + 40, p_192637_3_, item);
/* 887 */       renderStat(StatList.getObjectBreakStats(item), p_192637_2_ + 115, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 888 */       renderStat(StatList.getCraftStats(item), p_192637_2_ + 165, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 889 */       renderStat((StatBase)statcrafting, p_192637_2_ + 215, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 890 */       renderStat(StatList.getObjectsPickedUpStats(item), p_192637_2_ + 265, p_192637_3_, (p_192637_1_ % 2 == 0));
/* 891 */       renderStat(StatList.getDroppedObjectStats(item), p_192637_2_ + 315, p_192637_3_, (p_192637_1_ % 2 == 0));
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getHeaderDescriptionId(int p_148210_1_) {
/* 896 */       if (p_148210_1_ == 1)
/*     */       {
/* 898 */         return "stat.crafted";
/*     */       }
/* 900 */       if (p_148210_1_ == 2)
/*     */       {
/* 902 */         return "stat.used";
/*     */       }
/* 904 */       if (p_148210_1_ == 3)
/*     */       {
/* 906 */         return "stat.pickup";
/*     */       }
/*     */ 
/*     */       
/* 910 */       return (p_148210_1_ == 4) ? "stat.dropped" : "stat.depleted";
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsMobsList
/*     */     extends GuiSlot
/*     */   {
/* 917 */     private final List<EntityList.EntityEggInfo> mobs = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public StatsMobsList(Minecraft p_i47551_2_) {
/* 921 */       super(p_i47551_2_, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, GuiStats.this.fontRendererObj.FONT_HEIGHT * 4);
/* 922 */       func_193651_b(false);
/*     */       
/* 924 */       for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.ENTITY_EGGS.values()) {
/*     */         
/* 926 */         if (GuiStats.this.stats.readStat(entitylist$entityegginfo.killEntityStat) > 0 || GuiStats.this.stats.readStat(entitylist$entityegginfo.entityKilledByStat) > 0)
/*     */         {
/* 928 */           this.mobs.add(entitylist$entityegginfo);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 935 */       return this.mobs.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 944 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 949 */       return getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 954 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 959 */       EntityList.EntityEggInfo entitylist$entityegginfo = this.mobs.get(p_192637_1_);
/* 960 */       String s = I18n.format("entity." + EntityList.func_191302_a(entitylist$entityegginfo.spawnedID) + ".name", new Object[0]);
/* 961 */       int i = GuiStats.this.stats.readStat(entitylist$entityegginfo.killEntityStat);
/* 962 */       int j = GuiStats.this.stats.readStat(entitylist$entityegginfo.entityKilledByStat);
/* 963 */       String s1 = I18n.format("stat.entityKills", new Object[] { Integer.valueOf(i), s });
/* 964 */       String s2 = I18n.format("stat.entityKilledBy", new Object[] { s, Integer.valueOf(j) });
/*     */       
/* 966 */       if (i == 0)
/*     */       {
/* 968 */         s1 = I18n.format("stat.entityKills.none", new Object[] { s });
/*     */       }
/*     */       
/* 971 */       if (j == 0)
/*     */       {
/* 973 */         s2 = I18n.format("stat.entityKilledBy.none", new Object[] { s });
/*     */       }
/*     */       
/* 976 */       GuiStats.drawString(GuiStats.this.fontRendererObj, s, p_192637_2_ + 2 - 10, p_192637_3_ + 1, 16777215);
/* 977 */       GuiStats.drawString(GuiStats.this.fontRendererObj, s1, p_192637_2_ + 2, p_192637_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, (i == 0) ? 6316128 : 9474192);
/* 978 */       GuiStats.drawString(GuiStats.this.fontRendererObj, s2, p_192637_2_ + 2, p_192637_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT * 2, (j == 0) ? 6316128 : 9474192);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\achievement\GuiStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
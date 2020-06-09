/*     */ package net.minecraft.client.gui;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonParseException;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemWrittenBook;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenBook extends GuiScreen {
/*  32 */   private static final Logger LOGGER = LogManager.getLogger();
/*  33 */   private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");
/*     */ 
/*     */   
/*     */   private final EntityPlayer editingPlayer;
/*     */ 
/*     */   
/*     */   private final ItemStack bookObj;
/*     */ 
/*     */   
/*     */   private final boolean bookIsUnsigned;
/*     */ 
/*     */   
/*     */   private boolean bookIsModified;
/*     */ 
/*     */   
/*     */   private boolean bookGettingSigned;
/*     */   
/*     */   private int updateCount;
/*     */   
/*  52 */   private final int bookImageWidth = 192;
/*  53 */   private final int bookImageHeight = 192;
/*  54 */   private int bookTotalPages = 1;
/*     */   private int currPage;
/*     */   private NBTTagList bookPages;
/*  57 */   private String bookTitle = "";
/*     */   private List<ITextComponent> cachedComponents;
/*  59 */   private int cachedPage = -1;
/*     */   
/*     */   private NextPageButton buttonNextPage;
/*     */   
/*     */   private NextPageButton buttonPreviousPage;
/*     */   
/*     */   private GuiButton buttonDone;
/*     */   private GuiButton buttonSign;
/*     */   private GuiButton buttonFinalize;
/*     */   private GuiButton buttonCancel;
/*     */   
/*     */   public GuiScreenBook(EntityPlayer player, ItemStack book, boolean isUnsigned) {
/*  71 */     this.editingPlayer = player;
/*  72 */     this.bookObj = book;
/*  73 */     this.bookIsUnsigned = isUnsigned;
/*     */     
/*  75 */     if (book.hasTagCompound()) {
/*     */       
/*  77 */       NBTTagCompound nbttagcompound = book.getTagCompound();
/*  78 */       this.bookPages = nbttagcompound.getTagList("pages", 8).copy();
/*  79 */       this.bookTotalPages = this.bookPages.tagCount();
/*     */       
/*  81 */       if (this.bookTotalPages < 1)
/*     */       {
/*  83 */         this.bookTotalPages = 1;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     if (this.bookPages == null && isUnsigned) {
/*     */       
/*  89 */       this.bookPages = new NBTTagList();
/*  90 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/*  91 */       this.bookTotalPages = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 100 */     super.updateScreen();
/* 101 */     this.updateCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 110 */     this.buttonList.clear();
/* 111 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 113 */     if (this.bookIsUnsigned) {
/*     */       
/* 115 */       this.buttonSign = addButton(new GuiButton(3, this.width / 2 - 100, 196, 98, 20, I18n.format("book.signButton", new Object[0])));
/* 116 */       this.buttonDone = addButton(new GuiButton(0, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.done", new Object[0])));
/* 117 */       this.buttonFinalize = addButton(new GuiButton(5, this.width / 2 - 100, 196, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
/* 118 */       this.buttonCancel = addButton(new GuiButton(4, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     }
/*     */     else {
/*     */       
/* 122 */       this.buttonDone = addButton(new GuiButton(0, this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done", new Object[0])));
/*     */     } 
/*     */     
/* 125 */     int i = (this.width - 192) / 2;
/* 126 */     int j = 2;
/* 127 */     this.buttonNextPage = addButton(new NextPageButton(1, i + 120, 156, true));
/* 128 */     this.buttonPreviousPage = addButton(new NextPageButton(2, i + 38, 156, false));
/* 129 */     updateButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 137 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateButtons() {
/* 142 */     this.buttonNextPage.visible = (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
/* 143 */     this.buttonPreviousPage.visible = (!this.bookGettingSigned && this.currPage > 0);
/* 144 */     this.buttonDone.visible = !(this.bookIsUnsigned && this.bookGettingSigned);
/*     */     
/* 146 */     if (this.bookIsUnsigned) {
/*     */       
/* 148 */       this.buttonSign.visible = !this.bookGettingSigned;
/* 149 */       this.buttonCancel.visible = this.bookGettingSigned;
/* 150 */       this.buttonFinalize.visible = this.bookGettingSigned;
/* 151 */       this.buttonFinalize.enabled = !this.bookTitle.trim().isEmpty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendBookToServer(boolean publish) throws IOException {
/* 157 */     if (this.bookIsUnsigned && this.bookIsModified)
/*     */     {
/* 159 */       if (this.bookPages != null) {
/*     */         
/* 161 */         while (this.bookPages.tagCount() > 1) {
/*     */           
/* 163 */           String s = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
/*     */           
/* 165 */           if (!s.isEmpty()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 170 */           this.bookPages.removeTag(this.bookPages.tagCount() - 1);
/*     */         } 
/*     */         
/* 173 */         if (this.bookObj.hasTagCompound()) {
/*     */           
/* 175 */           NBTTagCompound nbttagcompound = this.bookObj.getTagCompound();
/* 176 */           nbttagcompound.setTag("pages", (NBTBase)this.bookPages);
/*     */         }
/*     */         else {
/*     */           
/* 180 */           this.bookObj.setTagInfo("pages", (NBTBase)this.bookPages);
/*     */         } 
/*     */         
/* 183 */         String s1 = "MC|BEdit";
/*     */         
/* 185 */         if (publish) {
/*     */           
/* 187 */           s1 = "MC|BSign";
/* 188 */           this.bookObj.setTagInfo("author", (NBTBase)new NBTTagString(this.editingPlayer.getName()));
/* 189 */           this.bookObj.setTagInfo("title", (NBTBase)new NBTTagString(this.bookTitle.trim()));
/*     */         } 
/*     */         
/* 192 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 193 */         packetbuffer.writeItemStackToBuffer(this.bookObj);
/* 194 */         this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload(s1, packetbuffer));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 204 */     if (button.enabled) {
/*     */       
/* 206 */       if (button.id == 0) {
/*     */         
/* 208 */         this.mc.displayGuiScreen(null);
/* 209 */         sendBookToServer(false);
/*     */       }
/* 211 */       else if (button.id == 3 && this.bookIsUnsigned) {
/*     */         
/* 213 */         this.bookGettingSigned = true;
/*     */       }
/* 215 */       else if (button.id == 1) {
/*     */         
/* 217 */         if (this.currPage < this.bookTotalPages - 1)
/*     */         {
/* 219 */           this.currPage++;
/*     */         }
/* 221 */         else if (this.bookIsUnsigned)
/*     */         {
/* 223 */           addNewPage();
/*     */           
/* 225 */           if (this.currPage < this.bookTotalPages - 1)
/*     */           {
/* 227 */             this.currPage++;
/*     */           }
/*     */         }
/*     */       
/* 231 */       } else if (button.id == 2) {
/*     */         
/* 233 */         if (this.currPage > 0)
/*     */         {
/* 235 */           this.currPage--;
/*     */         }
/*     */       }
/* 238 */       else if (button.id == 5 && this.bookGettingSigned) {
/*     */         
/* 240 */         sendBookToServer(true);
/* 241 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 243 */       else if (button.id == 4 && this.bookGettingSigned) {
/*     */         
/* 245 */         this.bookGettingSigned = false;
/*     */       } 
/*     */       
/* 248 */       updateButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNewPage() {
/* 254 */     if (this.bookPages != null && this.bookPages.tagCount() < 50) {
/*     */       
/* 256 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/* 257 */       this.bookTotalPages++;
/* 258 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 268 */     super.keyTyped(typedChar, keyCode);
/*     */     
/* 270 */     if (this.bookIsUnsigned)
/*     */     {
/* 272 */       if (this.bookGettingSigned) {
/*     */         
/* 274 */         keyTypedInTitle(typedChar, keyCode);
/*     */       }
/*     */       else {
/*     */         
/* 278 */         keyTypedInBook(typedChar, keyCode);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInBook(char typedChar, int keyCode) {
/* 288 */     if (GuiScreen.isKeyComboCtrlV(keyCode)) {
/*     */       
/* 290 */       pageInsertIntoCurrent(GuiScreen.getClipboardString());
/*     */     } else {
/*     */       String s;
/*     */       
/* 294 */       switch (keyCode) {
/*     */         
/*     */         case 14:
/* 297 */           s = pageGetCurrent();
/*     */           
/* 299 */           if (!s.isEmpty())
/*     */           {
/* 301 */             pageSetCurrent(s.substring(0, s.length() - 1));
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case 28:
/*     */         case 156:
/* 308 */           pageInsertIntoCurrent("\n");
/*     */           return;
/*     */       } 
/*     */       
/* 312 */       if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
/*     */       {
/* 314 */         pageInsertIntoCurrent(Character.toString(typedChar));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInTitle(char p_146460_1_, int p_146460_2_) throws IOException {
/* 325 */     switch (p_146460_2_) {
/*     */       
/*     */       case 14:
/* 328 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 330 */           this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
/* 331 */           updateButtons();
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case 28:
/*     */       case 156:
/* 338 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 340 */           sendBookToServer(true);
/* 341 */           this.mc.displayGuiScreen(null);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 347 */     if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
/*     */       
/* 349 */       this.bookTitle = String.valueOf(this.bookTitle) + Character.toString(p_146460_1_);
/* 350 */       updateButtons();
/* 351 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pageGetCurrent() {
/* 361 */     return (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) ? this.bookPages.getStringTagAt(this.currPage) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageSetCurrent(String p_146457_1_) {
/* 369 */     if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
/*     */       
/* 371 */       this.bookPages.set(this.currPage, (NBTBase)new NBTTagString(p_146457_1_));
/* 372 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageInsertIntoCurrent(String p_146459_1_) {
/* 381 */     String s = pageGetCurrent();
/* 382 */     String s1 = String.valueOf(s) + p_146459_1_;
/* 383 */     int i = this.fontRendererObj.splitStringWidth(String.valueOf(s1) + TextFormatting.BLACK + "_", 118);
/*     */     
/* 385 */     if (i <= 128 && s1.length() < 256)
/*     */     {
/* 387 */       pageSetCurrent(s1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 396 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 397 */     this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
/* 398 */     int i = (this.width - 192) / 2;
/* 399 */     int j = 2;
/* 400 */     drawTexturedModalRect(i, 2, 0, 0, 192, 192);
/*     */     
/* 402 */     if (this.bookGettingSigned) {
/*     */       
/* 404 */       String s = this.bookTitle;
/*     */       
/* 406 */       if (this.bookIsUnsigned)
/*     */       {
/* 408 */         if (this.updateCount / 6 % 2 == 0) {
/*     */           
/* 410 */           s = String.valueOf(s) + TextFormatting.BLACK + "_";
/*     */         }
/*     */         else {
/*     */           
/* 414 */           s = String.valueOf(s) + TextFormatting.GRAY + "_";
/*     */         } 
/*     */       }
/*     */       
/* 418 */       String s1 = I18n.format("book.editTitle", new Object[0]);
/* 419 */       int k = this.fontRendererObj.getStringWidth(s1);
/* 420 */       this.fontRendererObj.drawString(s1, i + 36 + (116 - k) / 2, 34, 0);
/* 421 */       int l = this.fontRendererObj.getStringWidth(s);
/* 422 */       this.fontRendererObj.drawString(s, i + 36 + (116 - l) / 2, 50, 0);
/* 423 */       String s2 = I18n.format("book.byAuthor", new Object[] { this.editingPlayer.getName() });
/* 424 */       int i1 = this.fontRendererObj.getStringWidth(s2);
/* 425 */       this.fontRendererObj.drawString(TextFormatting.DARK_GRAY + s2, i + 36 + (116 - i1) / 2, 60, 0);
/* 426 */       String s3 = I18n.format("book.finalizeWarning", new Object[0]);
/* 427 */       this.fontRendererObj.drawSplitString(s3, i + 36, 82, 116, 0);
/*     */     }
/*     */     else {
/*     */       
/* 431 */       String s4 = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.currPage + 1), Integer.valueOf(this.bookTotalPages) });
/* 432 */       String s5 = "";
/*     */       
/* 434 */       if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
/*     */       {
/* 436 */         s5 = this.bookPages.getStringTagAt(this.currPage);
/*     */       }
/*     */       
/* 439 */       if (this.bookIsUnsigned) {
/*     */         
/* 441 */         if (this.fontRendererObj.getBidiFlag())
/*     */         {
/* 443 */           s5 = String.valueOf(s5) + "_";
/*     */         }
/* 445 */         else if (this.updateCount / 6 % 2 == 0)
/*     */         {
/* 447 */           s5 = String.valueOf(s5) + TextFormatting.BLACK + "_";
/*     */         }
/*     */         else
/*     */         {
/* 451 */           s5 = String.valueOf(s5) + TextFormatting.GRAY + "_";
/*     */         }
/*     */       
/* 454 */       } else if (this.cachedPage != this.currPage) {
/*     */         
/* 456 */         if (ItemWrittenBook.validBookTagContents(this.bookObj.getTagCompound())) {
/*     */           
/*     */           try
/*     */           {
/* 460 */             ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s5);
/* 461 */             this.cachedComponents = (itextcomponent != null) ? GuiUtilRenderComponents.splitText(itextcomponent, 116, this.fontRendererObj, true, true) : null;
/*     */           }
/* 463 */           catch (JsonParseException var13)
/*     */           {
/* 465 */             this.cachedComponents = null;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 470 */           TextComponentString textcomponentstring = new TextComponentString(TextFormatting.DARK_RED + "* Invalid book tag *");
/* 471 */           this.cachedComponents = Lists.newArrayList((Iterable)textcomponentstring);
/*     */         } 
/*     */         
/* 474 */         this.cachedPage = this.currPage;
/*     */       } 
/*     */       
/* 477 */       int j1 = this.fontRendererObj.getStringWidth(s4);
/* 478 */       this.fontRendererObj.drawString(s4, i - j1 + 192 - 44, 18, 0);
/*     */       
/* 480 */       if (this.cachedComponents == null) {
/*     */         
/* 482 */         this.fontRendererObj.drawSplitString(s5, i + 36, 34, 116, 0);
/*     */       }
/*     */       else {
/*     */         
/* 486 */         int k1 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.cachedComponents.size());
/*     */         
/* 488 */         for (int l1 = 0; l1 < k1; l1++) {
/*     */           
/* 490 */           ITextComponent itextcomponent2 = this.cachedComponents.get(l1);
/* 491 */           this.fontRendererObj.drawString(itextcomponent2.getUnformattedText(), i + 36, 34 + l1 * this.fontRendererObj.FONT_HEIGHT, 0);
/*     */         } 
/*     */         
/* 494 */         ITextComponent itextcomponent1 = getClickedComponentAt(mouseX, mouseY);
/*     */         
/* 496 */         if (itextcomponent1 != null)
/*     */         {
/* 498 */           handleComponentHover(itextcomponent1, mouseX, mouseY);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 503 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 511 */     if (mouseButton == 0) {
/*     */       
/* 513 */       ITextComponent itextcomponent = getClickedComponentAt(mouseX, mouseY);
/*     */       
/* 515 */       if (itextcomponent != null && handleComponentClick(itextcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 521 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleComponentClick(ITextComponent component) {
/* 529 */     ClickEvent clickevent = component.getStyle().getClickEvent();
/*     */     
/* 531 */     if (clickevent == null)
/*     */     {
/* 533 */       return false;
/*     */     }
/* 535 */     if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
/*     */       
/* 537 */       String s = clickevent.getValue();
/*     */ 
/*     */       
/*     */       try {
/* 541 */         int i = Integer.parseInt(s) - 1;
/*     */         
/* 543 */         if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
/*     */         {
/* 545 */           this.currPage = i;
/* 546 */           updateButtons();
/* 547 */           return true;
/*     */         }
/*     */       
/* 550 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 555 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 559 */     boolean flag = super.handleComponentClick(component);
/*     */     
/* 561 */     if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
/*     */     {
/* 563 */       this.mc.displayGuiScreen(null);
/*     */     }
/*     */     
/* 566 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ITextComponent getClickedComponentAt(int p_175385_1_, int p_175385_2_) {
/* 573 */     if (this.cachedComponents == null)
/*     */     {
/* 575 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 579 */     int i = p_175385_1_ - (this.width - 192) / 2 - 36;
/* 580 */     int j = p_175385_2_ - 2 - 16 - 16;
/*     */     
/* 582 */     if (i >= 0 && j >= 0) {
/*     */       
/* 584 */       int k = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.cachedComponents.size());
/*     */       
/* 586 */       if (i <= 116 && j < this.mc.fontRendererObj.FONT_HEIGHT * k + k) {
/*     */         
/* 588 */         int l = j / this.mc.fontRendererObj.FONT_HEIGHT;
/*     */         
/* 590 */         if (l >= 0 && l < this.cachedComponents.size()) {
/*     */           
/* 592 */           ITextComponent itextcomponent = this.cachedComponents.get(l);
/* 593 */           int i1 = 0;
/*     */           
/* 595 */           for (ITextComponent itextcomponent1 : itextcomponent) {
/*     */             
/* 597 */             if (itextcomponent1 instanceof TextComponentString) {
/*     */               
/* 599 */               i1 += this.mc.fontRendererObj.getStringWidth(((TextComponentString)itextcomponent1).getText());
/*     */               
/* 601 */               if (i1 > i)
/*     */               {
/* 603 */                 return itextcomponent1;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 609 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 613 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 618 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class NextPageButton
/*     */     extends GuiButton
/*     */   {
/*     */     private final boolean isForward;
/*     */ 
/*     */     
/*     */     public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_) {
/* 629 */       super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
/* 630 */       this.isForward = p_i46316_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 635 */       if (this.visible) {
/*     */         
/* 637 */         boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 638 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 639 */         p_191745_1_.getTextureManager().bindTexture(GuiScreenBook.BOOK_GUI_TEXTURES);
/* 640 */         int i = 0;
/* 641 */         int j = 192;
/*     */         
/* 643 */         if (flag)
/*     */         {
/* 645 */           i += 23;
/*     */         }
/*     */         
/* 648 */         if (!this.isForward)
/*     */         {
/* 650 */           j += 13;
/*     */         }
/*     */         
/* 653 */         drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
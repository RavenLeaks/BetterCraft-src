/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import me.nzxter.bettercraft.mods.shader.DrawShader;
/*     */ import me.nzxter.bettercraft.mods.shader.GuiShader;
/*     */ import me.nzxter.bettercraft.utils.RenderUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import net.minecraft.util.text.event.HoverEvent;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreen
/*     */   extends Gui
/*     */   implements GuiYesNoCallback
/*     */ {
/*  50 */   protected static final Logger LOGGER = LogManager.getLogger();
/*  51 */   private static final Set<String> PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  52 */   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
/*     */ 
/*     */   
/*     */   protected Minecraft mc;
/*     */ 
/*     */   
/*     */   protected RenderItem itemRender;
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */   
/*  67 */   protected List<GuiButton> buttonList = Lists.newArrayList();
/*  68 */   protected List<GuiLabel> labelList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean allowUserInput;
/*     */ 
/*     */   
/*     */   protected FontRenderer fontRendererObj;
/*     */ 
/*     */   
/*     */   protected GuiButton selectedButton;
/*     */ 
/*     */   
/*     */   private int eventButton;
/*     */   
/*     */   private long lastMouseEvent;
/*     */   
/*     */   private int touchValue;
/*     */   
/*     */   private URI clickedLinkURI;
/*     */   
/*     */   private boolean field_193977_u;
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  92 */     this.MouseXBackGround = mouseX;
/*  93 */     this.MouseYBackGround = mouseY;
/*     */ 
/*     */     
/*  96 */     for (int i = 0; i < this.buttonList.size(); i++)
/*     */     {
/*  98 */       ((GuiButton)this.buttonList.get(i)).func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/*     */     }
/*     */     
/* 101 */     for (int j = 0; j < this.labelList.size(); j++)
/*     */     {
/* 103 */       ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 113 */     if (keyCode == 1) {
/*     */       
/* 115 */       this.mc.displayGuiScreen(null);
/*     */       
/* 117 */       if (this.mc.currentScreen == null)
/*     */       {
/* 119 */         this.mc.setIngameFocus();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends GuiButton> T addButton(T p_189646_1_) {
/* 126 */     this.buttonList.add((GuiButton)p_189646_1_);
/* 127 */     return p_189646_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/* 137 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*     */       
/* 139 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
/*     */       {
/* 141 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/*     */     }
/* 144 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 157 */     if (!StringUtils.isEmpty(copyText)) {
/*     */       
/*     */       try {
/*     */         
/* 161 */         StringSelection stringselection = new StringSelection(copyText);
/* 162 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/*     */       }
/* 164 */       catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 173 */     drawHoveringText(func_191927_a(stack), x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> func_191927_a(ItemStack p_191927_1_) {
/* 178 */     List<String> list = p_191927_1_.getTooltip((EntityPlayer)this.mc.player, this.mc.gameSettings.advancedItemTooltips ? (ITooltipFlag)ITooltipFlag.TooltipFlags.ADVANCED : (ITooltipFlag)ITooltipFlag.TooltipFlags.NORMAL);
/*     */     
/* 180 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 182 */       if (i == 0) {
/*     */         
/* 184 */         list.set(i, (p_191927_1_.getRarity()).rarityColor + (String)list.get(i));
/*     */       }
/*     */       else {
/*     */         
/* 188 */         list.set(i, TextFormatting.GRAY + (String)list.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 201 */     drawHoveringText(Arrays.asList(new String[] { tabName }, ), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193975_a(boolean p_193975_1_) {
/* 206 */     this.field_193977_u = p_193975_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193976_p() {
/* 211 */     return this.field_193977_u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHoveringText(List<String> textLines, int x, int y) {
/* 219 */     if (!textLines.isEmpty()) {
/*     */       
/* 221 */       GlStateManager.disableRescaleNormal();
/* 222 */       RenderHelper.disableStandardItemLighting();
/* 223 */       GlStateManager.disableLighting();
/* 224 */       GlStateManager.disableDepth();
/* 225 */       int i = 0;
/*     */       
/* 227 */       for (String s : textLines) {
/*     */         
/* 229 */         int j = this.fontRendererObj.getStringWidth(s);
/*     */         
/* 231 */         if (j > i)
/*     */         {
/* 233 */           i = j;
/*     */         }
/*     */       } 
/*     */       
/* 237 */       int l1 = x + 12;
/* 238 */       int i2 = y - 12;
/* 239 */       int k = 8;
/*     */       
/* 241 */       if (textLines.size() > 1)
/*     */       {
/* 243 */         k += 2 + (textLines.size() - 1) * 10;
/*     */       }
/*     */       
/* 246 */       if (l1 + i > this.width)
/*     */       {
/* 248 */         l1 -= 28 + i;
/*     */       }
/*     */       
/* 251 */       if (i2 + k + 6 > this.height)
/*     */       {
/* 253 */         i2 = this.height - k - 6;
/*     */       }
/*     */       
/* 256 */       this.zLevel = 300.0F;
/* 257 */       this.itemRender.zLevel = 300.0F;
/* 258 */       int l = -267386864;
/* 259 */       drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
/* 260 */       drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
/* 261 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
/* 262 */       drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
/* 263 */       drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
/* 264 */       int i1 = 1347420415;
/* 265 */       int j1 = 1344798847;
/* 266 */       drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
/* 267 */       drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
/* 268 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
/* 269 */       drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);
/*     */       
/* 271 */       for (int k1 = 0; k1 < textLines.size(); k1++) {
/*     */         
/* 273 */         String s1 = textLines.get(k1);
/* 274 */         this.fontRendererObj.drawStringWithShadow(s1, l1, i2, -1);
/*     */         
/* 276 */         if (k1 == 0)
/*     */         {
/* 278 */           i2 += 2;
/*     */         }
/*     */         
/* 281 */         i2 += 10;
/*     */       } 
/*     */       
/* 284 */       this.zLevel = 0.0F;
/* 285 */       this.itemRender.zLevel = 0.0F;
/* 286 */       GlStateManager.enableLighting();
/* 287 */       GlStateManager.enableDepth();
/* 288 */       RenderHelper.enableStandardItemLighting();
/* 289 */       GlStateManager.enableRescaleNormal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComponentHover(ITextComponent component, int x, int y) {
/* 298 */     if (component != null && component.getStyle().getHoverEvent() != null) {
/*     */       
/* 300 */       HoverEvent hoverevent = component.getStyle().getHoverEvent();
/*     */       
/* 302 */       if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
/*     */         
/* 304 */         ItemStack itemstack = ItemStack.field_190927_a;
/*     */ 
/*     */         
/*     */         try {
/* 308 */           NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */           
/* 310 */           if (nBTTagCompound instanceof NBTTagCompound)
/*     */           {
/* 312 */             itemstack = new ItemStack(nBTTagCompound);
/*     */           }
/*     */         }
/* 315 */         catch (NBTException nBTException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 320 */         if (itemstack.func_190926_b())
/*     */         {
/* 322 */           drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Item!", x, y);
/*     */         }
/*     */         else
/*     */         {
/* 326 */           renderToolTip(itemstack, x, y);
/*     */         }
/*     */       
/* 329 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
/*     */         
/* 331 */         if (this.mc.gameSettings.advancedItemTooltips) {
/*     */           try
/*     */           {
/*     */             
/* 335 */             NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/* 336 */             List<String> list = Lists.newArrayList();
/* 337 */             list.add(nbttagcompound.getString("name"));
/*     */             
/* 339 */             if (nbttagcompound.hasKey("type", 8)) {
/*     */               
/* 341 */               String s = nbttagcompound.getString("type");
/* 342 */               list.add("Type: " + s);
/*     */             } 
/*     */             
/* 345 */             list.add(nbttagcompound.getString("id"));
/* 346 */             drawHoveringText(list, x, y);
/*     */           }
/* 348 */           catch (NBTException var8)
/*     */           {
/* 350 */             drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Entity!", x, y);
/*     */           }
/*     */         
/*     */         }
/* 354 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
/*     */         
/* 356 */         drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(hoverevent.getValue().getFormattedText(), Math.max(this.width / 2, 200)), x, y);
/*     */       } 
/*     */       
/* 359 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleComponentClick(ITextComponent component) {
/* 375 */     if (component == null)
/*     */     {
/* 377 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 381 */     ClickEvent clickevent = component.getStyle().getClickEvent();
/*     */     
/* 383 */     if (isShiftKeyDown()) {
/*     */       
/* 385 */       if (component.getStyle().getInsertion() != null)
/*     */       {
/* 387 */         setText(component.getStyle().getInsertion(), false);
/*     */       }
/*     */     }
/* 390 */     else if (clickevent != null) {
/*     */       
/* 392 */       if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
/*     */         
/* 394 */         if (!this.mc.gameSettings.chatLinks)
/*     */         {
/* 396 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 401 */           URI uri = new URI(clickevent.getValue());
/* 402 */           String s = uri.getScheme();
/*     */           
/* 404 */           if (s == null)
/*     */           {
/* 406 */             throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
/*     */           }
/*     */           
/* 409 */           if (!PROTOCOLS.contains(s.toLowerCase(Locale.ROOT)))
/*     */           {
/* 411 */             throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
/*     */           }
/*     */           
/* 414 */           if (this.mc.gameSettings.chatLinksPrompt)
/*     */           {
/* 416 */             this.clickedLinkURI = uri;
/* 417 */             this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
/*     */           }
/*     */           else
/*     */           {
/* 421 */             openWebLink(uri);
/*     */           }
/*     */         
/* 424 */         } catch (URISyntaxException urisyntaxexception) {
/*     */           
/* 426 */           LOGGER.error("Can't open url for {}", clickevent, urisyntaxexception);
/*     */         }
/*     */       
/* 429 */       } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
/*     */         
/* 431 */         URI uri1 = (new File(clickevent.getValue())).toURI();
/* 432 */         openWebLink(uri1);
/*     */       }
/* 434 */       else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/*     */         
/* 436 */         setText(clickevent.getValue(), true);
/*     */       }
/* 438 */       else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/*     */         
/* 440 */         sendChatMessage(clickevent.getValue(), false);
/*     */       }
/*     */       else {
/*     */         
/* 444 */         LOGGER.error("Don't know how to handle {}", clickevent);
/*     */       } 
/*     */       
/* 447 */       return true;
/*     */     } 
/*     */     
/* 450 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg) {
/* 459 */     sendChatMessage(msg, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg, boolean addToChat) {
/* 464 */     if (addToChat)
/*     */     {
/* 466 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*     */     }
/*     */     
/* 469 */     this.mc.player.sendChatMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 477 */     if (mouseButton == 0)
/*     */     {
/* 479 */       for (int i = 0; i < this.buttonList.size(); i++) {
/*     */         
/* 481 */         GuiButton guibutton = this.buttonList.get(i);
/*     */         
/* 483 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/*     */           
/* 485 */           this.selectedButton = guibutton;
/* 486 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 487 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 498 */     if (this.selectedButton != null && state == 0) {
/*     */       
/* 500 */       this.selectedButton.mouseReleased(mouseX, mouseY);
/* 501 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 526 */     this.mc = mc;
/* 527 */     this.itemRender = mc.getRenderItem();
/* 528 */     this.fontRendererObj = mc.fontRendererObj;
/* 529 */     this.width = width;
/* 530 */     this.height = height;
/* 531 */     this.buttonList.clear();
/* 532 */     initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGuiSize(int w, int h) {
/* 540 */     this.width = w;
/* 541 */     this.height = h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleInput() throws IOException {
/* 557 */     if (Mouse.isCreated())
/*     */     {
/* 559 */       while (Mouse.next())
/*     */       {
/* 561 */         handleMouseInput();
/*     */       }
/*     */     }
/*     */     
/* 565 */     if (Keyboard.isCreated())
/*     */     {
/* 567 */       while (Keyboard.next())
/*     */       {
/* 569 */         handleKeyboardInput();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 579 */     int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
/* 580 */     int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
/* 581 */     int k = Mouse.getEventButton();
/*     */     
/* 583 */     if (Mouse.getEventButtonState()) {
/*     */       
/* 585 */       if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 590 */       this.eventButton = k;
/* 591 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 592 */       mouseClicked(i, j, this.eventButton);
/*     */     }
/* 594 */     else if (k != -1) {
/*     */       
/* 596 */       if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 601 */       this.eventButton = -1;
/* 602 */       mouseReleased(i, j, k);
/*     */     }
/* 604 */     else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
/*     */       
/* 606 */       long l = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 607 */       mouseClickMove(i, j, this.eventButton, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 616 */     char c0 = Keyboard.getEventCharacter();
/*     */     
/* 618 */     if ((Keyboard.getEventKey() == 0 && c0 >= ' ') || Keyboard.getEventKeyState())
/*     */     {
/* 620 */       keyTyped(c0, Keyboard.getEventKey());
/*     */     }
/*     */     
/* 623 */     this.mc.dispatchKeypresses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 641 */   private int MouseXBackGround = 0;
/* 642 */   private int MouseYBackGround = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/* 650 */     if (this.mc.player == null) {
/* 651 */       if (!GuiShader.shader) {
/* 652 */         ScaledResolution s1 = new ScaledResolution(this.mc);
/* 653 */         this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/background.png"));
/* 654 */         Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*     */         
/* 656 */         RenderUtils.drawMovedBackground(this.MouseXBackGround, this.MouseYBackGround, "textures/gui/background.png", true);
/*     */         
/* 658 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 662 */       DrawShader.doShaderStuff();
/* 663 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       return;
/*     */     } 
/* 666 */     drawWorldBackground(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/* 671 */     if (this.mc.world != null) {
/* 672 */       drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
/*     */     } else {
/* 674 */       drawBackground(tint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int tint) {
/* 683 */     GlStateManager.disableLighting();
/* 684 */     GlStateManager.disableFog();
/* 685 */     Tessellator tessellator = Tessellator.getInstance();
/* 686 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 687 */     this.mc.getTextureManager().bindTexture(OPTIONS_BACKGROUND);
/* 688 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 689 */     float f = 32.0F;
/* 690 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 691 */     bufferbuilder.pos(0.0D, this.height, 0.0D).tex(0.0D, (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 692 */     bufferbuilder.pos(this.width, this.height, 0.0D).tex((this.width / 32.0F), (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 693 */     bufferbuilder.pos(this.width, 0.0D, 0.0D).tex((this.width / 32.0F), tint).color(64, 64, 64, 255).endVertex();
/* 694 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).color(64, 64, 64, 255).endVertex();
/* 695 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 703 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 708 */     if (id == 31102009) {
/*     */       
/* 710 */       if (result)
/*     */       {
/* 712 */         openWebLink(this.clickedLinkURI);
/*     */       }
/*     */       
/* 715 */       this.clickedLinkURI = null;
/* 716 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void openWebLink(URI url) {
/*     */     try {
/* 724 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 725 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 726 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { url });
/*     */     }
/* 728 */     catch (Throwable throwable1) {
/*     */       
/* 730 */       Throwable throwable = throwable1.getCause();
/* 731 */       LOGGER.error("Couldn't open link: {}", (throwable == null) ? "<UNKNOWN>" : throwable.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/* 740 */     if (Minecraft.IS_RUNNING_ON_MAC)
/*     */     {
/* 742 */       return !(!Keyboard.isKeyDown(219) && !Keyboard.isKeyDown(220));
/*     */     }
/*     */ 
/*     */     
/* 746 */     return !(!Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 755 */     return !(!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 763 */     return !(!Keyboard.isKeyDown(56) && !Keyboard.isKeyDown(184));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlX(int keyID) {
/* 768 */     return (keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlV(int keyID) {
/* 773 */     return (keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlC(int keyID) {
/* 778 */     return (keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlA(int keyID) {
/* 783 */     return (keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResize(Minecraft mcIn, int w, int h) {
/* 791 */     setWorldAndResolution(mcIn, w, h);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
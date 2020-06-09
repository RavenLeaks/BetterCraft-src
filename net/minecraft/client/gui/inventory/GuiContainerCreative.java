/*      */ package net.minecraft.client.gui.inventory;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.io.IOException;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import javax.annotation.Nullable;
/*      */ import me.nzxter.bettercraft.mods.creative.GuiItemCreator;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiButton;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiTextField;
/*      */ import net.minecraft.client.gui.achievement.GuiStats;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.CreativeSettings;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.HotbarSnapshot;
/*      */ import net.minecraft.client.util.ITooltipFlag;
/*      */ import net.minecraft.client.util.SearchTreeManager;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.ClickType;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ 
/*      */ public class GuiContainerCreative extends InventoryEffectRenderer {
/*   50 */   private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
/*   51 */   private static final InventoryBasic basicInventory = new InventoryBasic("tmp", true, 45);
/*      */ 
/*      */   
/*   54 */   private static int selectedTabIndex = CreativeTabs.BUILDING_BLOCKS.getTabIndex();
/*      */   
/*      */   private float currentScroll;
/*      */   
/*      */   private boolean isScrolling;
/*      */   
/*      */   private boolean wasClicking;
/*      */   
/*      */   private GuiTextField searchField;
/*      */   
/*      */   private List<Slot> originalSlots;
/*      */   
/*      */   private Slot destroyItemSlot;
/*      */   
/*      */   private boolean clearSearch;
/*      */   
/*      */   private CreativeCrafting listener;
/*      */ 
/*      */   
/*      */   public GuiContainerCreative(EntityPlayer player) {
/*   74 */     super(new ContainerCreative(player));
/*   75 */     player.openContainer = this.inventorySlots;
/*   76 */     this.allowUserInput = true;
/*   77 */     this.ySize = 136;
/*   78 */     this.xSize = 195;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateScreen() {
/*   86 */     if (!this.mc.playerController.isInCreativeMode())
/*      */     {
/*   88 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.player));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleMouseClick(@Nullable Slot slotIn, int slotId, int mouseButton, ClickType type) {
/*   97 */     this.clearSearch = true;
/*   98 */     boolean flag = (type == ClickType.QUICK_MOVE);
/*   99 */     type = (slotId == -999 && type == ClickType.PICKUP) ? ClickType.THROW : type;
/*      */     
/*  101 */     if (slotIn == null && selectedTabIndex != CreativeTabs.INVENTORY.getTabIndex() && type != ClickType.QUICK_CRAFT) {
/*      */       
/*  103 */       InventoryPlayer inventoryplayer1 = this.mc.player.inventory;
/*      */       
/*  105 */       if (!inventoryplayer1.getItemStack().func_190926_b()) {
/*      */         
/*  107 */         if (mouseButton == 0) {
/*      */           
/*  109 */           this.mc.player.dropItem(inventoryplayer1.getItemStack(), true);
/*  110 */           this.mc.playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
/*  111 */           inventoryplayer1.setItemStack(ItemStack.field_190927_a);
/*      */         } 
/*      */         
/*  114 */         if (mouseButton == 1)
/*      */         {
/*  116 */           ItemStack itemstack6 = inventoryplayer1.getItemStack().splitStack(1);
/*  117 */           this.mc.player.dropItem(itemstack6, true);
/*  118 */           this.mc.playerController.sendPacketDropItem(itemstack6);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  124 */       if (slotIn != null && !slotIn.canTakeStack((EntityPlayer)this.mc.player)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  129 */       if (slotIn == this.destroyItemSlot && flag) {
/*      */         
/*  131 */         for (int j = 0; j < this.mc.player.inventoryContainer.getInventory().size(); j++)
/*      */         {
/*  133 */           this.mc.playerController.sendSlotPacket(ItemStack.field_190927_a, j);
/*      */         }
/*      */       }
/*  136 */       else if (selectedTabIndex == CreativeTabs.INVENTORY.getTabIndex()) {
/*      */         
/*  138 */         if (slotIn == this.destroyItemSlot)
/*      */         {
/*  140 */           this.mc.player.inventory.setItemStack(ItemStack.field_190927_a);
/*      */         }
/*  142 */         else if (type == ClickType.THROW && slotIn != null && slotIn.getHasStack())
/*      */         {
/*  144 */           ItemStack itemstack = slotIn.decrStackSize((mouseButton == 0) ? 1 : slotIn.getStack().getMaxStackSize());
/*  145 */           ItemStack itemstack1 = slotIn.getStack();
/*  146 */           this.mc.player.dropItem(itemstack, true);
/*  147 */           this.mc.playerController.sendPacketDropItem(itemstack);
/*  148 */           this.mc.playerController.sendSlotPacket(itemstack1, ((CreativeSlot)slotIn).slot.slotNumber);
/*      */         }
/*  150 */         else if (type == ClickType.THROW && !this.mc.player.inventory.getItemStack().func_190926_b())
/*      */         {
/*  152 */           this.mc.player.dropItem(this.mc.player.inventory.getItemStack(), true);
/*  153 */           this.mc.playerController.sendPacketDropItem(this.mc.player.inventory.getItemStack());
/*  154 */           this.mc.player.inventory.setItemStack(ItemStack.field_190927_a);
/*      */         }
/*      */         else
/*      */         {
/*  158 */           this.mc.player.inventoryContainer.slotClick((slotIn == null) ? slotId : ((CreativeSlot)slotIn).slot.slotNumber, mouseButton, type, (EntityPlayer)this.mc.player);
/*  159 */           this.mc.player.inventoryContainer.detectAndSendChanges();
/*      */         }
/*      */       
/*  162 */       } else if (type != ClickType.QUICK_CRAFT && slotIn.inventory == basicInventory) {
/*      */         
/*  164 */         InventoryPlayer inventoryplayer = this.mc.player.inventory;
/*  165 */         ItemStack itemstack5 = inventoryplayer.getItemStack();
/*  166 */         ItemStack itemstack7 = slotIn.getStack();
/*      */         
/*  168 */         if (type == ClickType.SWAP) {
/*      */           
/*  170 */           if (!itemstack7.func_190926_b() && mouseButton >= 0 && mouseButton < 9) {
/*      */             
/*  172 */             ItemStack itemstack10 = itemstack7.copy();
/*  173 */             itemstack10.func_190920_e(itemstack10.getMaxStackSize());
/*  174 */             this.mc.player.inventory.setInventorySlotContents(mouseButton, itemstack10);
/*  175 */             this.mc.player.inventoryContainer.detectAndSendChanges();
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  181 */         if (type == ClickType.CLONE) {
/*      */           
/*  183 */           if (inventoryplayer.getItemStack().func_190926_b() && slotIn.getHasStack()) {
/*      */             
/*  185 */             ItemStack itemstack9 = slotIn.getStack().copy();
/*  186 */             itemstack9.func_190920_e(itemstack9.getMaxStackSize());
/*  187 */             inventoryplayer.setItemStack(itemstack9);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  193 */         if (type == ClickType.THROW) {
/*      */           
/*  195 */           if (!itemstack7.func_190926_b()) {
/*      */             
/*  197 */             ItemStack itemstack8 = itemstack7.copy();
/*  198 */             itemstack8.func_190920_e((mouseButton == 0) ? 1 : itemstack8.getMaxStackSize());
/*  199 */             this.mc.player.dropItem(itemstack8, true);
/*  200 */             this.mc.playerController.sendPacketDropItem(itemstack8);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  206 */         if (!itemstack5.func_190926_b() && !itemstack7.func_190926_b() && itemstack5.isItemEqual(itemstack7) && ItemStack.areItemStackTagsEqual(itemstack5, itemstack7)) {
/*      */           
/*  208 */           if (mouseButton == 0) {
/*      */             
/*  210 */             if (flag)
/*      */             {
/*  212 */               itemstack5.func_190920_e(itemstack5.getMaxStackSize());
/*      */             }
/*  214 */             else if (itemstack5.func_190916_E() < itemstack5.getMaxStackSize())
/*      */             {
/*  216 */               itemstack5.func_190917_f(1);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  221 */             itemstack5.func_190918_g(1);
/*      */           }
/*      */         
/*  224 */         } else if (!itemstack7.func_190926_b() && itemstack5.func_190926_b()) {
/*      */           
/*  226 */           inventoryplayer.setItemStack(itemstack7.copy());
/*  227 */           itemstack5 = inventoryplayer.getItemStack();
/*      */           
/*  229 */           if (flag)
/*      */           {
/*  231 */             itemstack5.func_190920_e(itemstack5.getMaxStackSize());
/*      */           }
/*      */         }
/*  234 */         else if (mouseButton == 0) {
/*      */           
/*  236 */           inventoryplayer.setItemStack(ItemStack.field_190927_a);
/*      */         }
/*      */         else {
/*      */           
/*  240 */           inventoryplayer.getItemStack().func_190918_g(1);
/*      */         }
/*      */       
/*  243 */       } else if (this.inventorySlots != null) {
/*      */         
/*  245 */         ItemStack itemstack3 = (slotIn == null) ? ItemStack.field_190927_a : this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
/*  246 */         this.inventorySlots.slotClick((slotIn == null) ? slotId : slotIn.slotNumber, mouseButton, type, (EntityPlayer)this.mc.player);
/*      */         
/*  248 */         if (Container.getDragEvent(mouseButton) == 2) {
/*      */           
/*  250 */           for (int k = 0; k < 9; k++)
/*      */           {
/*  252 */             this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + k).getStack(), 36 + k);
/*      */           }
/*      */         }
/*  255 */         else if (slotIn != null) {
/*      */           
/*  257 */           ItemStack itemstack4 = this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
/*  258 */           this.mc.playerController.sendSlotPacket(itemstack4, slotIn.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
/*  259 */           int i = 45 + mouseButton;
/*      */           
/*  261 */           if (type == ClickType.SWAP) {
/*      */             
/*  263 */             this.mc.playerController.sendSlotPacket(itemstack3, i - this.inventorySlots.inventorySlots.size() + 9 + 36);
/*      */           }
/*  265 */           else if (type == ClickType.THROW && !itemstack3.func_190926_b()) {
/*      */             
/*  267 */             ItemStack itemstack2 = itemstack3.copy();
/*  268 */             itemstack2.func_190920_e((mouseButton == 0) ? 1 : itemstack2.getMaxStackSize());
/*  269 */             this.mc.player.dropItem(itemstack2, true);
/*  270 */             this.mc.playerController.sendPacketDropItem(itemstack2);
/*      */           } 
/*      */           
/*  273 */           this.mc.player.inventoryContainer.detectAndSendChanges();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateActivePotionEffects() {
/*  281 */     int i = this.guiLeft;
/*  282 */     super.updateActivePotionEffects();
/*      */     
/*  284 */     if (this.searchField != null && this.guiLeft != i)
/*      */     {
/*  286 */       this.searchField.xPosition = this.guiLeft + 82;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initGui() {
/*  296 */     if (this.mc.playerController.isInCreativeMode()) {
/*      */       
/*  298 */       super.initGui();
/*  299 */       this.buttonList.clear();
/*      */ 
/*      */       
/*  302 */       this.buttonList.add(new GuiButton(2, this.guiLeft - 2, this.guiTop + 165, "Item Creator"));
/*      */ 
/*      */       
/*  305 */       Keyboard.enableRepeatEvents(true);
/*  306 */       this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 80, this.fontRendererObj.FONT_HEIGHT);
/*  307 */       this.searchField.setMaxStringLength(50);
/*  308 */       this.searchField.setEnableBackgroundDrawing(false);
/*  309 */       this.searchField.setVisible(false);
/*  310 */       this.searchField.setTextColor(16777215);
/*  311 */       int i = selectedTabIndex;
/*  312 */       selectedTabIndex = -1;
/*  313 */       setCurrentCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY[i]);
/*  314 */       this.listener = new CreativeCrafting(this.mc);
/*  315 */       this.mc.player.inventoryContainer.addListener(this.listener);
/*      */     }
/*      */     else {
/*      */       
/*  319 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.player));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onGuiClosed() {
/*  328 */     super.onGuiClosed();
/*      */     
/*  330 */     if (this.mc.player != null && this.mc.player.inventory != null)
/*      */     {
/*  332 */       this.mc.player.inventoryContainer.removeListener(this.listener);
/*      */     }
/*      */     
/*  335 */     Keyboard.enableRepeatEvents(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  344 */     if (selectedTabIndex != CreativeTabs.SEARCH.getTabIndex()) {
/*      */       
/*  346 */       if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat))
/*      */       {
/*  348 */         setCurrentCreativeTab(CreativeTabs.SEARCH);
/*      */       }
/*      */       else
/*      */       {
/*  352 */         super.keyTyped(typedChar, keyCode);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  357 */       if (this.clearSearch) {
/*      */         
/*  359 */         this.clearSearch = false;
/*  360 */         this.searchField.setText("");
/*      */       } 
/*      */       
/*  363 */       if (!checkHotbarKeys(keyCode))
/*      */       {
/*  365 */         if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
/*      */           
/*  367 */           updateCreativeSearch();
/*      */         }
/*      */         else {
/*      */           
/*  371 */           super.keyTyped(typedChar, keyCode);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateCreativeSearch() {
/*  379 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/*  380 */     guicontainercreative$containercreative.itemList.clear();
/*      */     
/*  382 */     if (this.searchField.getText().isEmpty()) {
/*      */       
/*  384 */       for (Item item : Item.REGISTRY)
/*      */       {
/*  386 */         item.getSubItems(CreativeTabs.SEARCH, guicontainercreative$containercreative.itemList);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  391 */       guicontainercreative$containercreative.itemList.addAll(this.mc.func_193987_a(SearchTreeManager.field_194011_a).func_194038_a(this.searchField.getText().toLowerCase(Locale.ROOT)));
/*      */     } 
/*      */     
/*  394 */     this.currentScroll = 0.0F;
/*  395 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  403 */     CreativeTabs creativetabs = CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex];
/*      */     
/*  405 */     if (creativetabs.drawInForegroundOfTab()) {
/*      */       
/*  407 */       GlStateManager.disableBlend();
/*  408 */       this.fontRendererObj.drawString(I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  417 */     if (mouseButton == 0) {
/*      */       
/*  419 */       int i = mouseX - this.guiLeft;
/*  420 */       int j = mouseY - this.guiTop; byte b; int k;
/*      */       CreativeTabs[] arrayOfCreativeTabs;
/*  422 */       for (k = (arrayOfCreativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY).length, b = 0; b < k; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/*      */         
/*  424 */         if (isMouseOverTab(creativetabs, i, j)) {
/*      */           return;
/*      */         }
/*      */         
/*      */         b++; }
/*      */     
/*      */     } 
/*  431 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*  439 */     if (state == 0) {
/*      */       
/*  441 */       int i = mouseX - this.guiLeft;
/*  442 */       int j = mouseY - this.guiTop; byte b; int k;
/*      */       CreativeTabs[] arrayOfCreativeTabs;
/*  444 */       for (k = (arrayOfCreativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY).length, b = 0; b < k; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/*      */         
/*  446 */         if (isMouseOverTab(creativetabs, i, j)) {
/*      */           
/*  448 */           setCurrentCreativeTab(creativetabs);
/*      */           return;
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*  454 */     super.mouseReleased(mouseX, mouseY, state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needsScrollBars() {
/*  462 */     return (selectedTabIndex != CreativeTabs.INVENTORY.getTabIndex() && CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).canScroll());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setCurrentCreativeTab(CreativeTabs tab) {
/*  470 */     int i = selectedTabIndex;
/*  471 */     selectedTabIndex = tab.getTabIndex();
/*  472 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/*  473 */     this.dragSplittingSlots.clear();
/*  474 */     guicontainercreative$containercreative.itemList.clear();
/*      */     
/*  476 */     if (tab == CreativeTabs.field_192395_m) {
/*      */       
/*  478 */       for (int j = 0; j < 9; j++) {
/*      */         
/*  480 */         HotbarSnapshot hotbarsnapshot = this.mc.field_191950_u.func_192563_a(j);
/*      */         
/*  482 */         if (hotbarsnapshot.isEmpty()) {
/*      */           
/*  484 */           for (int k = 0; k < 9; k++) {
/*      */             
/*  486 */             if (k == j)
/*      */             {
/*  488 */               ItemStack itemstack = new ItemStack(Items.PAPER);
/*  489 */               itemstack.func_190925_c("CustomCreativeLock");
/*  490 */               String s = GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindsHotbar[j].getKeyCode());
/*  491 */               String s1 = GameSettings.getKeyDisplayString(this.mc.gameSettings.field_193629_ap.getKeyCode());
/*  492 */               itemstack.setStackDisplayName((new TextComponentTranslation("inventory.hotbarInfo", new Object[] { s1, s })).getUnformattedText());
/*  493 */               guicontainercreative$containercreative.itemList.add(itemstack);
/*      */             }
/*      */             else
/*      */             {
/*  497 */               guicontainercreative$containercreative.itemList.add(ItemStack.field_190927_a);
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/*  503 */           guicontainercreative$containercreative.itemList.addAll((Collection)hotbarsnapshot);
/*      */         }
/*      */       
/*      */       } 
/*  507 */     } else if (tab != CreativeTabs.SEARCH) {
/*      */       
/*  509 */       tab.displayAllRelevantItems(guicontainercreative$containercreative.itemList);
/*      */     } 
/*      */     
/*  512 */     if (tab == CreativeTabs.INVENTORY) {
/*      */       
/*  514 */       Container container = this.mc.player.inventoryContainer;
/*      */       
/*  516 */       if (this.originalSlots == null)
/*      */       {
/*  518 */         this.originalSlots = guicontainercreative$containercreative.inventorySlots;
/*      */       }
/*      */       
/*  521 */       guicontainercreative$containercreative.inventorySlots = Lists.newArrayList();
/*      */       
/*  523 */       for (int l = 0; l < container.inventorySlots.size(); l++) {
/*      */         
/*  525 */         Slot slot = new CreativeSlot(container.inventorySlots.get(l), l);
/*  526 */         guicontainercreative$containercreative.inventorySlots.add(slot);
/*      */         
/*  528 */         if (l >= 5 && l < 9) {
/*      */           
/*  530 */           int j1 = l - 5;
/*  531 */           int l1 = j1 / 2;
/*  532 */           int j2 = j1 % 2;
/*  533 */           slot.xDisplayPosition = 54 + l1 * 54;
/*  534 */           slot.yDisplayPosition = 6 + j2 * 27;
/*      */         }
/*  536 */         else if (l >= 0 && l < 5) {
/*      */           
/*  538 */           slot.xDisplayPosition = -2000;
/*  539 */           slot.yDisplayPosition = -2000;
/*      */         }
/*  541 */         else if (l == 45) {
/*      */           
/*  543 */           slot.xDisplayPosition = 35;
/*  544 */           slot.yDisplayPosition = 20;
/*      */         }
/*  546 */         else if (l < container.inventorySlots.size()) {
/*      */           
/*  548 */           int i1 = l - 9;
/*  549 */           int k1 = i1 % 9;
/*  550 */           int i2 = i1 / 9;
/*  551 */           slot.xDisplayPosition = 9 + k1 * 18;
/*      */           
/*  553 */           if (l >= 36) {
/*      */             
/*  555 */             slot.yDisplayPosition = 112;
/*      */           }
/*      */           else {
/*      */             
/*  559 */             slot.yDisplayPosition = 54 + i2 * 18;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  564 */       this.destroyItemSlot = new Slot((IInventory)basicInventory, 0, 173, 112);
/*  565 */       guicontainercreative$containercreative.inventorySlots.add(this.destroyItemSlot);
/*      */     }
/*  567 */     else if (i == CreativeTabs.INVENTORY.getTabIndex()) {
/*      */       
/*  569 */       guicontainercreative$containercreative.inventorySlots = this.originalSlots;
/*  570 */       this.originalSlots = null;
/*      */     } 
/*      */     
/*  573 */     if (this.searchField != null)
/*      */     {
/*  575 */       if (tab == CreativeTabs.SEARCH) {
/*      */         
/*  577 */         this.searchField.setVisible(true);
/*  578 */         this.searchField.setCanLoseFocus(false);
/*  579 */         this.searchField.setFocused(true);
/*  580 */         this.searchField.setText("");
/*  581 */         updateCreativeSearch();
/*      */       }
/*      */       else {
/*      */         
/*  585 */         this.searchField.setVisible(false);
/*  586 */         this.searchField.setCanLoseFocus(true);
/*  587 */         this.searchField.setFocused(false);
/*      */       } 
/*      */     }
/*      */     
/*  591 */     this.currentScroll = 0.0F;
/*  592 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMouseInput() throws IOException {
/*  600 */     super.handleMouseInput();
/*  601 */     int i = Mouse.getEventDWheel();
/*      */     
/*  603 */     if (i != 0 && needsScrollBars()) {
/*      */       
/*  605 */       int j = (((ContainerCreative)this.inventorySlots).itemList.size() + 9 - 1) / 9 - 5;
/*      */       
/*  607 */       if (i > 0)
/*      */       {
/*  609 */         i = 1;
/*      */       }
/*      */       
/*  612 */       if (i < 0)
/*      */       {
/*  614 */         i = -1;
/*      */       }
/*      */       
/*  617 */       this.currentScroll = (float)(this.currentScroll - i / j);
/*  618 */       this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
/*  619 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  628 */     drawDefaultBackground();
/*  629 */     boolean flag = Mouse.isButtonDown(0);
/*  630 */     int i = this.guiLeft;
/*  631 */     int j = this.guiTop;
/*  632 */     int k = i + 175;
/*  633 */     int l = j + 18;
/*  634 */     int i1 = k + 14;
/*  635 */     int j1 = l + 112;
/*      */     
/*  637 */     if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
/*      */     {
/*  639 */       this.isScrolling = needsScrollBars();
/*      */     }
/*      */     
/*  642 */     if (!flag)
/*      */     {
/*  644 */       this.isScrolling = false;
/*      */     }
/*      */     
/*  647 */     this.wasClicking = flag;
/*      */     
/*  649 */     if (this.isScrolling) {
/*      */       
/*  651 */       this.currentScroll = ((mouseY - l) - 7.5F) / ((j1 - l) - 15.0F);
/*  652 */       this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
/*  653 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*      */     } 
/*      */     
/*  656 */     super.drawScreen(mouseX, mouseY, partialTicks); byte b; int m;
/*      */     CreativeTabs[] arrayOfCreativeTabs;
/*  658 */     for (m = (arrayOfCreativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY).length, b = 0; b < m; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/*      */       
/*  660 */       if (renderCreativeInventoryHoveringText(creativetabs, mouseX, mouseY)) {
/*      */         break;
/*      */       }
/*      */       
/*      */       b++; }
/*      */     
/*  666 */     if (this.destroyItemSlot != null && selectedTabIndex == CreativeTabs.INVENTORY.getTabIndex() && isPointInRegion(this.destroyItemSlot.xDisplayPosition, this.destroyItemSlot.yDisplayPosition, 16, 16, mouseX, mouseY))
/*      */     {
/*  668 */       drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), mouseX, mouseY);
/*      */     }
/*      */     
/*  671 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  672 */     GlStateManager.disableLighting();
/*  673 */     func_191948_b(mouseX, mouseY);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderToolTip(ItemStack stack, int x, int y) {
/*  678 */     if (selectedTabIndex == CreativeTabs.SEARCH.getTabIndex()) {
/*      */       
/*  680 */       List<String> list = stack.getTooltip((EntityPlayer)this.mc.player, this.mc.gameSettings.advancedItemTooltips ? (ITooltipFlag)ITooltipFlag.TooltipFlags.ADVANCED : (ITooltipFlag)ITooltipFlag.TooltipFlags.NORMAL);
/*  681 */       CreativeTabs creativetabs = stack.getItem().getCreativeTab();
/*      */       
/*  683 */       if (creativetabs == null && stack.getItem() == Items.ENCHANTED_BOOK) {
/*      */         
/*  685 */         Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
/*      */         
/*  687 */         if (map.size() == 1) {
/*      */           
/*  689 */           Enchantment enchantment = map.keySet().iterator().next(); byte b; int j;
/*      */           CreativeTabs[] arrayOfCreativeTabs;
/*  691 */           for (j = (arrayOfCreativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY).length, b = 0; b < j; ) { CreativeTabs creativetabs1 = arrayOfCreativeTabs[b];
/*      */             
/*  693 */             if (creativetabs1.hasRelevantEnchantmentType(enchantment.type)) {
/*      */               
/*  695 */               creativetabs = creativetabs1;
/*      */               break;
/*      */             } 
/*      */             b++; }
/*      */         
/*      */         } 
/*      */       } 
/*  702 */       if (creativetabs != null)
/*      */       {
/*  704 */         list.add(1, TextFormatting.BOLD + TextFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]));
/*      */       }
/*      */       
/*  707 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  709 */         if (i == 0) {
/*      */           
/*  711 */           list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*      */         }
/*      */         else {
/*      */           
/*  715 */           list.set(i, TextFormatting.GRAY + (String)list.get(i));
/*      */         } 
/*      */       } 
/*      */       
/*  719 */       drawHoveringText(list, x, y);
/*      */     }
/*      */     else {
/*      */       
/*  723 */       super.renderToolTip(stack, x, y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  732 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  733 */     RenderHelper.enableGUIStandardItemLighting();
/*  734 */     CreativeTabs creativetabs = CreativeTabs.CREATIVE_TAB_ARRAY[selectedTabIndex]; byte b; int m;
/*      */     CreativeTabs[] arrayOfCreativeTabs;
/*  736 */     for (m = (arrayOfCreativeTabs = CreativeTabs.CREATIVE_TAB_ARRAY).length, b = 0; b < m; ) { CreativeTabs creativetabs1 = arrayOfCreativeTabs[b];
/*      */       
/*  738 */       this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
/*      */       
/*  740 */       if (creativetabs1.getTabIndex() != selectedTabIndex)
/*      */       {
/*  742 */         drawTab(creativetabs1);
/*      */       }
/*      */       b++; }
/*      */     
/*  746 */     this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativetabs.getBackgroundImageName()));
/*  747 */     drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
/*  748 */     this.searchField.drawTextBox();
/*  749 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  750 */     int i = this.guiLeft + 175;
/*  751 */     int j = this.guiTop + 18;
/*  752 */     int k = j + 112;
/*  753 */     this.mc.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
/*      */     
/*  755 */     if (creativetabs.shouldHidePlayerInventory())
/*      */     {
/*  757 */       drawTexturedModalRect(i, j + (int)((k - j - 17) * this.currentScroll), 232 + (needsScrollBars() ? 0 : 12), 0, 12, 15);
/*      */     }
/*      */     
/*  760 */     drawTab(creativetabs);
/*      */     
/*  762 */     if (creativetabs == CreativeTabs.INVENTORY)
/*      */     {
/*  764 */       GuiInventory.drawEntityOnScreen(this.guiLeft + 88, this.guiTop + 45, 20, (this.guiLeft + 88 - mouseX), (this.guiTop + 45 - 30 - mouseY), (EntityLivingBase)this.mc.player);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMouseOverTab(CreativeTabs tab, int mouseX, int mouseY) {
/*  773 */     int i = tab.getTabColumn();
/*  774 */     int j = 28 * i;
/*  775 */     int k = 0;
/*      */     
/*  777 */     if (tab.func_192394_m()) {
/*      */       
/*  779 */       j = this.xSize - 28 * (6 - i) + 2;
/*      */     }
/*  781 */     else if (i > 0) {
/*      */       
/*  783 */       j += i;
/*      */     } 
/*      */     
/*  786 */     if (tab.isTabInFirstRow()) {
/*      */       
/*  788 */       k -= 32;
/*      */     }
/*      */     else {
/*      */       
/*  792 */       k += this.ySize;
/*      */     } 
/*      */     
/*  795 */     return (mouseX >= j && mouseX <= j + 28 && mouseY >= k && mouseY <= k + 32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean renderCreativeInventoryHoveringText(CreativeTabs tab, int mouseX, int mouseY) {
/*  804 */     int i = tab.getTabColumn();
/*  805 */     int j = 28 * i;
/*  806 */     int k = 0;
/*      */     
/*  808 */     if (tab.func_192394_m()) {
/*      */       
/*  810 */       j = this.xSize - 28 * (6 - i) + 2;
/*      */     }
/*  812 */     else if (i > 0) {
/*      */       
/*  814 */       j += i;
/*      */     } 
/*      */     
/*  817 */     if (tab.isTabInFirstRow()) {
/*      */       
/*  819 */       k -= 32;
/*      */     }
/*      */     else {
/*      */       
/*  823 */       k += this.ySize;
/*      */     } 
/*      */     
/*  826 */     if (isPointInRegion(j + 3, k + 3, 23, 27, mouseX, mouseY)) {
/*      */       
/*  828 */       drawCreativeTabHoveringText(I18n.format(tab.getTranslatedTabLabel(), new Object[0]), mouseX, mouseY);
/*  829 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  833 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawTab(CreativeTabs tab) {
/*  843 */     boolean flag = (tab.getTabIndex() == selectedTabIndex);
/*  844 */     boolean flag1 = tab.isTabInFirstRow();
/*  845 */     int i = tab.getTabColumn();
/*  846 */     int j = i * 28;
/*  847 */     int k = 0;
/*  848 */     int l = this.guiLeft + 28 * i;
/*  849 */     int i1 = this.guiTop;
/*  850 */     int j1 = 32;
/*      */     
/*  852 */     if (flag)
/*      */     {
/*  854 */       k += 32;
/*      */     }
/*      */     
/*  857 */     if (tab.func_192394_m()) {
/*      */       
/*  859 */       l = this.guiLeft + this.xSize - 28 * (6 - i);
/*      */     }
/*  861 */     else if (i > 0) {
/*      */       
/*  863 */       l += i;
/*      */     } 
/*      */     
/*  866 */     if (flag1) {
/*      */       
/*  868 */       i1 -= 28;
/*      */     }
/*      */     else {
/*      */       
/*  872 */       k += 64;
/*  873 */       i1 += this.ySize - 4;
/*      */     } 
/*      */     
/*  876 */     GlStateManager.disableLighting();
/*  877 */     drawTexturedModalRect(l, i1, j, k, 28, 32);
/*  878 */     this.zLevel = 100.0F;
/*  879 */     this.itemRender.zLevel = 100.0F;
/*  880 */     l += 6;
/*  881 */     i1 = i1 + 8 + (flag1 ? 1 : -1);
/*  882 */     GlStateManager.enableLighting();
/*  883 */     GlStateManager.enableRescaleNormal();
/*  884 */     ItemStack itemstack = tab.getIconItemStack();
/*  885 */     this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
/*  886 */     this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, l, i1);
/*  887 */     GlStateManager.disableLighting();
/*  888 */     this.itemRender.zLevel = 0.0F;
/*  889 */     this.zLevel = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void actionPerformed(GuiButton button) throws IOException {
/*  897 */     if (button.id == 1)
/*      */     {
/*  899 */       this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.player.getStatFileWriter()));
/*      */     }
/*      */ 
/*      */     
/*  903 */     if (button.id == 2) {
/*  904 */       this.mc.displayGuiScreen((GuiScreen)new GuiItemCreator((GuiScreen)this));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedTabIndex() {
/*  914 */     return selectedTabIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_192044_a(Minecraft p_192044_0_, int p_192044_1_, boolean p_192044_2_, boolean p_192044_3_) {
/*  919 */     EntityPlayerSP entityplayersp = p_192044_0_.player;
/*  920 */     CreativeSettings creativesettings = p_192044_0_.field_191950_u;
/*  921 */     HotbarSnapshot hotbarsnapshot = creativesettings.func_192563_a(p_192044_1_);
/*      */     
/*  923 */     if (p_192044_2_) {
/*      */       
/*  925 */       for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
/*      */         
/*  927 */         ItemStack itemstack = ((ItemStack)hotbarsnapshot.get(i)).copy();
/*  928 */         entityplayersp.inventory.setInventorySlotContents(i, itemstack);
/*  929 */         p_192044_0_.playerController.sendSlotPacket(itemstack, 36 + i);
/*      */       } 
/*      */       
/*  932 */       entityplayersp.inventoryContainer.detectAndSendChanges();
/*      */     }
/*  934 */     else if (p_192044_3_) {
/*      */       
/*  936 */       for (int j = 0; j < InventoryPlayer.getHotbarSize(); j++)
/*      */       {
/*  938 */         hotbarsnapshot.set(j, entityplayersp.inventory.getStackInSlot(j).copy());
/*      */       }
/*      */       
/*  941 */       String s = GameSettings.getKeyDisplayString(p_192044_0_.gameSettings.keyBindsHotbar[p_192044_1_].getKeyCode());
/*  942 */       String s1 = GameSettings.getKeyDisplayString(p_192044_0_.gameSettings.field_193630_aq.getKeyCode());
/*  943 */       p_192044_0_.ingameGUI.setRecordPlaying((ITextComponent)new TextComponentTranslation("inventory.hotbarSaved", new Object[] { s1, s }), false);
/*  944 */       creativesettings.func_192564_b();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static class ContainerCreative
/*      */     extends Container {
/*  950 */     public NonNullList<ItemStack> itemList = NonNullList.func_191196_a();
/*      */ 
/*      */     
/*      */     public ContainerCreative(EntityPlayer player) {
/*  954 */       InventoryPlayer inventoryplayer = player.inventory;
/*      */       
/*  956 */       for (int i = 0; i < 5; i++) {
/*      */         
/*  958 */         for (int j = 0; j < 9; j++)
/*      */         {
/*  960 */           addSlotToContainer(new GuiContainerCreative.LockedSlot((IInventory)GuiContainerCreative.basicInventory, i * 9 + j, 9 + j * 18, 18 + i * 18));
/*      */         }
/*      */       } 
/*      */       
/*  964 */       for (int k = 0; k < 9; k++)
/*      */       {
/*  966 */         addSlotToContainer(new Slot((IInventory)inventoryplayer, k, 9 + k * 18, 112));
/*      */       }
/*      */       
/*  969 */       scrollTo(0.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canInteractWith(EntityPlayer playerIn) {
/*  974 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void scrollTo(float p_148329_1_) {
/*  979 */       int i = (this.itemList.size() + 9 - 1) / 9 - 5;
/*  980 */       int j = (int)((p_148329_1_ * i) + 0.5D);
/*      */       
/*  982 */       if (j < 0)
/*      */       {
/*  984 */         j = 0;
/*      */       }
/*      */       
/*  987 */       for (int k = 0; k < 5; k++) {
/*      */         
/*  989 */         for (int l = 0; l < 9; l++) {
/*      */           
/*  991 */           int i1 = l + (k + j) * 9;
/*      */           
/*  993 */           if (i1 >= 0 && i1 < this.itemList.size()) {
/*      */             
/*  995 */             GuiContainerCreative.basicInventory.setInventorySlotContents(l + k * 9, (ItemStack)this.itemList.get(i1));
/*      */           }
/*      */           else {
/*      */             
/*  999 */             GuiContainerCreative.basicInventory.setInventorySlotContents(l + k * 9, ItemStack.field_190927_a);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canScroll() {
/* 1007 */       return (this.itemList.size() > 45);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 1012 */       if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size()) {
/*      */         
/* 1014 */         Slot slot = this.inventorySlots.get(index);
/*      */         
/* 1016 */         if (slot != null && slot.getHasStack())
/*      */         {
/* 1018 */           slot.putStack(ItemStack.field_190927_a);
/*      */         }
/*      */       } 
/*      */       
/* 1022 */       return ItemStack.field_190927_a;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 1027 */       return (slotIn.yDisplayPosition > 90);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canDragIntoSlot(Slot slotIn) {
/* 1032 */       return !(!(slotIn.inventory instanceof InventoryPlayer) && (slotIn.yDisplayPosition <= 90 || slotIn.xDisplayPosition > 162));
/*      */     }
/*      */   }
/*      */   
/*      */   class CreativeSlot
/*      */     extends Slot
/*      */   {
/*      */     private final Slot slot;
/*      */     
/*      */     public CreativeSlot(Slot p_i46313_2_, int p_i46313_3_) {
/* 1042 */       super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
/* 1043 */       this.slot = p_i46313_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/* 1048 */       this.slot.func_190901_a(p_190901_1_, p_190901_2_);
/* 1049 */       return p_190901_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isItemValid(ItemStack stack) {
/* 1054 */       return this.slot.isItemValid(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getStack() {
/* 1059 */       return this.slot.getStack();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getHasStack() {
/* 1064 */       return this.slot.getHasStack();
/*      */     }
/*      */ 
/*      */     
/*      */     public void putStack(ItemStack stack) {
/* 1069 */       this.slot.putStack(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSlotChanged() {
/* 1074 */       this.slot.onSlotChanged();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getSlotStackLimit() {
/* 1079 */       return this.slot.getSlotStackLimit();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getItemStackLimit(ItemStack stack) {
/* 1084 */       return this.slot.getItemStackLimit(stack);
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public String getSlotTexture() {
/* 1090 */       return this.slot.getSlotTexture();
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack decrStackSize(int amount) {
/* 1095 */       return this.slot.decrStackSize(amount);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isHere(IInventory inv, int slotIn) {
/* 1100 */       return this.slot.isHere(inv, slotIn);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canBeHovered() {
/* 1105 */       return this.slot.canBeHovered();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canTakeStack(EntityPlayer playerIn) {
/* 1110 */       return this.slot.canTakeStack(playerIn);
/*      */     }
/*      */   }
/*      */   
/*      */   static class LockedSlot
/*      */     extends Slot
/*      */   {
/*      */     public LockedSlot(IInventory p_i47453_1_, int p_i47453_2_, int p_i47453_3_, int p_i47453_4_) {
/* 1118 */       super(p_i47453_1_, p_i47453_2_, p_i47453_3_, p_i47453_4_);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canTakeStack(EntityPlayer playerIn) {
/* 1123 */       if (super.canTakeStack(playerIn) && getHasStack())
/*      */       {
/* 1125 */         return (getStack().getSubCompound("CustomCreativeLock") == null);
/*      */       }
/*      */ 
/*      */       
/* 1129 */       return !getHasStack();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
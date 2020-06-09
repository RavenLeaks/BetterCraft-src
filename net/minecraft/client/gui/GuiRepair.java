/*     */ package net.minecraft.client.gui;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerRepair;
/*     */ import net.minecraft.inventory.IContainerListener;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiRepair extends GuiContainer implements IContainerListener {
/*  25 */   private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation("textures/gui/container/anvil.png");
/*     */   
/*     */   private final ContainerRepair anvil;
/*     */   private GuiTextField nameField;
/*     */   private final InventoryPlayer playerInventory;
/*     */   
/*     */   public GuiRepair(InventoryPlayer inventoryIn, World worldIn) {
/*  32 */     super((Container)new ContainerRepair(inventoryIn, worldIn, (EntityPlayer)(Minecraft.getMinecraft()).player));
/*  33 */     this.playerInventory = inventoryIn;
/*  34 */     this.anvil = (ContainerRepair)this.inventorySlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  43 */     super.initGui();
/*  44 */     Keyboard.enableRepeatEvents(true);
/*  45 */     int i = (this.width - this.xSize) / 2;
/*  46 */     int j = (this.height - this.ySize) / 2;
/*  47 */     this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
/*  48 */     this.nameField.setTextColor(-1);
/*  49 */     this.nameField.setDisabledTextColour(-1);
/*  50 */     this.nameField.setEnableBackgroundDrawing(false);
/*  51 */     this.nameField.setMaxStringLength(35);
/*  52 */     this.inventorySlots.removeListener(this);
/*  53 */     this.inventorySlots.addListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  61 */     super.onGuiClosed();
/*  62 */     Keyboard.enableRepeatEvents(false);
/*  63 */     this.inventorySlots.removeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  71 */     GlStateManager.disableLighting();
/*  72 */     GlStateManager.disableBlend();
/*  73 */     this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
/*     */     
/*  75 */     if (this.anvil.maximumCost > 0) {
/*     */       
/*  77 */       int i = 8453920;
/*  78 */       boolean flag = true;
/*  79 */       String s = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(this.anvil.maximumCost) });
/*     */       
/*  81 */       if (this.anvil.maximumCost >= 40 && !this.mc.player.capabilities.isCreativeMode) {
/*     */         
/*  83 */         s = I18n.format("container.repair.expensive", new Object[0]);
/*  84 */         i = 16736352;
/*     */       }
/*  86 */       else if (!this.anvil.getSlot(2).getHasStack()) {
/*     */         
/*  88 */         flag = false;
/*     */       }
/*  90 */       else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
/*     */         
/*  92 */         i = 16736352;
/*     */       } 
/*     */       
/*  95 */       if (flag) {
/*     */         
/*  97 */         int j = 0xFF000000 | (i & 0xFCFCFC) >> 2 | i & 0xFF000000;
/*  98 */         int k = this.xSize - 8 - this.fontRendererObj.getStringWidth(s);
/*  99 */         int l = 67;
/*     */         
/* 101 */         if (this.fontRendererObj.getUnicodeFlag()) {
/*     */           
/* 103 */           drawRect(k - 3, 65, this.xSize - 7, 77, -16777216);
/* 104 */           drawRect(k - 2, 66, this.xSize - 8, 76, -12895429);
/*     */         }
/*     */         else {
/*     */           
/* 108 */           this.fontRendererObj.drawString(s, k, 68, j);
/* 109 */           this.fontRendererObj.drawString(s, k + 1, 67, j);
/* 110 */           this.fontRendererObj.drawString(s, k + 1, 68, j);
/*     */         } 
/*     */         
/* 113 */         this.fontRendererObj.drawString(s, k, 67, i);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 126 */     if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
/*     */       
/* 128 */       renameItem();
/*     */     }
/*     */     else {
/*     */       
/* 132 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renameItem() {
/* 138 */     String s = this.nameField.getText();
/* 139 */     Slot slot = this.anvil.getSlot(0);
/*     */     
/* 141 */     if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && s.equals(slot.getStack().getDisplayName()))
/*     */     {
/* 143 */       s = "";
/*     */     }
/*     */     
/* 146 */     this.anvil.updateItemName(s);
/* 147 */     this.mc.player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|ItemName", (new PacketBuffer(Unpooled.buffer())).writeString(s)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 155 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 156 */     this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 164 */     drawDefaultBackground();
/* 165 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 166 */     func_191948_b(mouseX, mouseY);
/* 167 */     GlStateManager.disableLighting();
/* 168 */     GlStateManager.disableBlend();
/* 169 */     this.nameField.drawTextBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 177 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 178 */     this.mc.getTextureManager().bindTexture(ANVIL_RESOURCE);
/* 179 */     int i = (this.width - this.xSize) / 2;
/* 180 */     int j = (this.height - this.ySize) / 2;
/* 181 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 182 */     drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
/*     */     
/* 184 */     if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack())
/*     */     {
/* 186 */       drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCraftingInventory(Container containerToSend, NonNullList<ItemStack> itemsList) {
/* 195 */     sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/* 204 */     if (slotInd == 0) {
/*     */       
/* 206 */       this.nameField.setText(stack.func_190926_b() ? "" : stack.getDisplayName());
/* 207 */       this.nameField.setEnabled(!stack.func_190926_b());
/*     */       
/* 209 */       if (!stack.func_190926_b())
/*     */       {
/* 211 */         renameItem();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {}
/*     */   
/*     */   public void sendAllWindowProperties(Container containerIn, IInventory inventory) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
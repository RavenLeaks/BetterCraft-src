/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiBeacon extends GuiContainer {
/*  28 */   private static final Logger LOGGER = LogManager.getLogger();
/*  29 */   private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation("textures/gui/container/beacon.png");
/*     */   
/*     */   private final IInventory tileBeacon;
/*     */   private ConfirmButton beaconConfirmButton;
/*     */   private boolean buttonsNotDrawn;
/*     */   
/*     */   public GuiBeacon(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
/*  36 */     super((Container)new ContainerBeacon((IInventory)playerInventory, tileBeaconIn));
/*  37 */     this.tileBeacon = tileBeaconIn;
/*  38 */     this.xSize = 230;
/*  39 */     this.ySize = 219;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  48 */     super.initGui();
/*  49 */     this.beaconConfirmButton = new ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107);
/*  50 */     this.buttonList.add(this.beaconConfirmButton);
/*  51 */     this.buttonList.add(new CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
/*  52 */     this.buttonsNotDrawn = true;
/*  53 */     this.beaconConfirmButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  61 */     super.updateScreen();
/*  62 */     int i = this.tileBeacon.getField(0);
/*  63 */     Potion potion = Potion.getPotionById(this.tileBeacon.getField(1));
/*  64 */     Potion potion1 = Potion.getPotionById(this.tileBeacon.getField(2));
/*     */     
/*  66 */     if (this.buttonsNotDrawn && i >= 0) {
/*     */       
/*  68 */       this.buttonsNotDrawn = false;
/*  69 */       int j = 100;
/*     */       
/*  71 */       for (int k = 0; k <= 2; k++) {
/*     */         
/*  73 */         int l = (TileEntityBeacon.EFFECTS_LIST[k]).length;
/*  74 */         int i1 = l * 22 + (l - 1) * 2;
/*     */         
/*  76 */         for (int j1 = 0; j1 < l; j1++) {
/*     */           
/*  78 */           Potion potion2 = TileEntityBeacon.EFFECTS_LIST[k][j1];
/*  79 */           PowerButton guibeacon$powerbutton = new PowerButton(j++, this.guiLeft + 76 + j1 * 24 - i1 / 2, this.guiTop + 22 + k * 25, potion2, k);
/*  80 */           this.buttonList.add(guibeacon$powerbutton);
/*     */           
/*  82 */           if (k >= i) {
/*     */             
/*  84 */             guibeacon$powerbutton.enabled = false;
/*     */           }
/*  86 */           else if (potion2 == potion) {
/*     */             
/*  88 */             guibeacon$powerbutton.setSelected(true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       int k1 = 3;
/*  94 */       int l1 = (TileEntityBeacon.EFFECTS_LIST[3]).length + 1;
/*  95 */       int i2 = l1 * 22 + (l1 - 1) * 2;
/*     */       
/*  97 */       for (int j2 = 0; j2 < l1 - 1; j2++) {
/*     */         
/*  99 */         Potion potion3 = TileEntityBeacon.EFFECTS_LIST[3][j2];
/* 100 */         PowerButton guibeacon$powerbutton2 = new PowerButton(j++, this.guiLeft + 167 + j2 * 24 - i2 / 2, this.guiTop + 47, potion3, 3);
/* 101 */         this.buttonList.add(guibeacon$powerbutton2);
/*     */         
/* 103 */         if (3 >= i) {
/*     */           
/* 105 */           guibeacon$powerbutton2.enabled = false;
/*     */         }
/* 107 */         else if (potion3 == potion1) {
/*     */           
/* 109 */           guibeacon$powerbutton2.setSelected(true);
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       if (potion != null) {
/*     */         
/* 115 */         PowerButton guibeacon$powerbutton1 = new PowerButton(j++, this.guiLeft + 167 + (l1 - 1) * 24 - i2 / 2, this.guiTop + 47, potion, 3);
/* 116 */         this.buttonList.add(guibeacon$powerbutton1);
/*     */         
/* 118 */         if (3 >= i) {
/*     */           
/* 120 */           guibeacon$powerbutton1.enabled = false;
/*     */         }
/* 122 */         else if (potion == potion1) {
/*     */           
/* 124 */           guibeacon$powerbutton1.setSelected(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     this.beaconConfirmButton.enabled = (!this.tileBeacon.getStackInSlot(0).func_190926_b() && potion != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 137 */     if (button.id == -2) {
/*     */       
/* 139 */       this.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(this.mc.player.openContainer.windowId));
/* 140 */       this.mc.displayGuiScreen(null);
/*     */     }
/* 142 */     else if (button.id == -1) {
/*     */       
/* 144 */       String s = "MC|Beacon";
/* 145 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 146 */       packetbuffer.writeInt(this.tileBeacon.getField(1));
/* 147 */       packetbuffer.writeInt(this.tileBeacon.getField(2));
/* 148 */       this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload("MC|Beacon", packetbuffer));
/* 149 */       this.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(this.mc.player.openContainer.windowId));
/* 150 */       this.mc.displayGuiScreen(null);
/*     */     }
/* 152 */     else if (button instanceof PowerButton) {
/*     */       
/* 154 */       PowerButton guibeacon$powerbutton = (PowerButton)button;
/*     */       
/* 156 */       if (guibeacon$powerbutton.isSelected()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 161 */       int i = Potion.getIdFromPotion(guibeacon$powerbutton.effect);
/*     */       
/* 163 */       if (guibeacon$powerbutton.tier < 3) {
/*     */         
/* 165 */         this.tileBeacon.setField(1, i);
/*     */       }
/*     */       else {
/*     */         
/* 169 */         this.tileBeacon.setField(2, i);
/*     */       } 
/*     */       
/* 172 */       this.buttonList.clear();
/* 173 */       initGui();
/* 174 */       updateScreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 183 */     drawDefaultBackground();
/* 184 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 185 */     func_191948_b(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 193 */     RenderHelper.disableStandardItemLighting();
/* 194 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
/* 195 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
/*     */     
/* 197 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/* 199 */       if (guibutton.isMouseOver()) {
/*     */         
/* 201 */         guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 206 */     RenderHelper.enableGUIStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 214 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 215 */     this.mc.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
/* 216 */     int i = (this.width - this.xSize) / 2;
/* 217 */     int j = (this.height - this.ySize) / 2;
/* 218 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 219 */     this.itemRender.zLevel = 100.0F;
/* 220 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.EMERALD), i + 42, j + 109);
/* 221 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.DIAMOND), i + 42 + 22, j + 109);
/* 222 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.GOLD_INGOT), i + 42 + 44, j + 109);
/* 223 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_INGOT), i + 42 + 66, j + 109);
/* 224 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */   
/*     */   static class Button
/*     */     extends GuiButton
/*     */   {
/*     */     private final ResourceLocation iconTexture;
/*     */     private final int iconX;
/*     */     private final int iconY;
/*     */     private boolean selected;
/*     */     
/*     */     protected Button(int buttonId, int x, int y, ResourceLocation iconTextureIn, int iconXIn, int iconYIn) {
/* 236 */       super(buttonId, x, y, 22, 22, "");
/* 237 */       this.iconTexture = iconTextureIn;
/* 238 */       this.iconX = iconXIn;
/* 239 */       this.iconY = iconYIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 244 */       if (this.visible) {
/*     */         
/* 246 */         p_191745_1_.getTextureManager().bindTexture(GuiBeacon.BEACON_GUI_TEXTURES);
/* 247 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 248 */         this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 249 */         int i = 219;
/* 250 */         int j = 0;
/*     */         
/* 252 */         if (!this.enabled) {
/*     */           
/* 254 */           j += this.width * 2;
/*     */         }
/* 256 */         else if (this.selected) {
/*     */           
/* 258 */           j += this.width * 1;
/*     */         }
/* 260 */         else if (this.hovered) {
/*     */           
/* 262 */           j += this.width * 3;
/*     */         } 
/*     */         
/* 265 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, 219, this.width, this.height);
/*     */         
/* 267 */         if (!GuiBeacon.BEACON_GUI_TEXTURES.equals(this.iconTexture))
/*     */         {
/* 269 */           p_191745_1_.getTextureManager().bindTexture(this.iconTexture);
/*     */         }
/*     */         
/* 272 */         drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.iconX, this.iconY, 18, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSelected() {
/* 278 */       return this.selected;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(boolean selectedIn) {
/* 283 */       this.selected = selectedIn;
/*     */     }
/*     */   }
/*     */   
/*     */   class CancelButton
/*     */     extends Button
/*     */   {
/*     */     public CancelButton(int buttonId, int x, int y) {
/* 291 */       super(buttonId, x, y, GuiBeacon.BEACON_GUI_TEXTURES, 112, 220);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 296 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class ConfirmButton
/*     */     extends Button
/*     */   {
/*     */     public ConfirmButton(int buttonId, int x, int y) {
/* 304 */       super(buttonId, x, y, GuiBeacon.BEACON_GUI_TEXTURES, 90, 220);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 309 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class PowerButton
/*     */     extends Button
/*     */   {
/*     */     private final Potion effect;
/*     */     private final int tier;
/*     */     
/*     */     public PowerButton(int buttonId, int x, int y, Potion effectIn, int tierIn) {
/* 320 */       super(buttonId, x, y, GuiContainer.INVENTORY_BACKGROUND, effectIn.getStatusIconIndex() % 8 * 18, 198 + effectIn.getStatusIconIndex() / 8 * 18);
/* 321 */       this.effect = effectIn;
/* 322 */       this.tier = tierIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 327 */       String s = I18n.format(this.effect.getName(), new Object[0]);
/*     */       
/* 329 */       if (this.tier >= 3 && this.effect != MobEffects.REGENERATION)
/*     */       {
/* 331 */         s = String.valueOf(s) + " II";
/*     */       }
/*     */       
/* 334 */       GuiBeacon.this.drawCreativeTabHoveringText(s, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.gui;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerMerchant;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiMerchant extends GuiContainer {
/*  26 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*  29 */   private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMerchant merchant;
/*     */ 
/*     */   
/*     */   private MerchantButton nextButton;
/*     */ 
/*     */   
/*     */   private MerchantButton previousButton;
/*     */ 
/*     */   
/*     */   private int selectedMerchantRecipe;
/*     */ 
/*     */   
/*     */   private final ITextComponent chatComponent;
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMerchant(InventoryPlayer p_i45500_1_, IMerchant p_i45500_2_, World worldIn) {
/*  50 */     super((Container)new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
/*  51 */     this.merchant = p_i45500_2_;
/*  52 */     this.chatComponent = p_i45500_2_.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  61 */     super.initGui();
/*  62 */     int i = (this.width - this.xSize) / 2;
/*  63 */     int j = (this.height - this.ySize) / 2;
/*  64 */     this.nextButton = (MerchantButton)addButton(new MerchantButton(1, i + 120 + 27, j + 24 - 1, true));
/*  65 */     this.previousButton = (MerchantButton)addButton(new MerchantButton(2, i + 36 - 19, j + 24 - 1, false));
/*  66 */     this.nextButton.enabled = false;
/*  67 */     this.previousButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  75 */     String s = this.chatComponent.getUnformattedText();
/*  76 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/*  77 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  85 */     super.updateScreen();
/*  86 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.player);
/*     */     
/*  88 */     if (merchantrecipelist != null) {
/*     */       
/*  90 */       this.nextButton.enabled = (this.selectedMerchantRecipe < merchantrecipelist.size() - 1);
/*  91 */       this.previousButton.enabled = (this.selectedMerchantRecipe > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 100 */     boolean flag = false;
/*     */     
/* 102 */     if (button == this.nextButton) {
/*     */       
/* 104 */       this.selectedMerchantRecipe++;
/* 105 */       MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.player);
/*     */       
/* 107 */       if (merchantrecipelist != null && this.selectedMerchantRecipe >= merchantrecipelist.size())
/*     */       {
/* 109 */         this.selectedMerchantRecipe = merchantrecipelist.size() - 1;
/*     */       }
/*     */       
/* 112 */       flag = true;
/*     */     }
/* 114 */     else if (button == this.previousButton) {
/*     */       
/* 116 */       this.selectedMerchantRecipe--;
/*     */       
/* 118 */       if (this.selectedMerchantRecipe < 0)
/*     */       {
/* 120 */         this.selectedMerchantRecipe = 0;
/*     */       }
/*     */       
/* 123 */       flag = true;
/*     */     } 
/*     */     
/* 126 */     if (flag) {
/*     */       
/* 128 */       ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
/* 129 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 130 */       packetbuffer.writeInt(this.selectedMerchantRecipe);
/* 131 */       this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload("MC|TrSel", packetbuffer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 140 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 141 */     this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 142 */     int i = (this.width - this.xSize) / 2;
/* 143 */     int j = (this.height - this.ySize) / 2;
/* 144 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 145 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.player);
/*     */     
/* 147 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/*     */       
/* 149 */       int k = this.selectedMerchantRecipe;
/*     */       
/* 151 */       if (k < 0 || k >= merchantrecipelist.size()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 156 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/*     */       
/* 158 */       if (merchantrecipe.isRecipeDisabled()) {
/*     */         
/* 160 */         this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 161 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 162 */         GlStateManager.disableLighting();
/* 163 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
/* 164 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 174 */     drawDefaultBackground();
/* 175 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 176 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.player);
/*     */     
/* 178 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/*     */       
/* 180 */       int i = (this.width - this.xSize) / 2;
/* 181 */       int j = (this.height - this.ySize) / 2;
/* 182 */       int k = this.selectedMerchantRecipe;
/* 183 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/* 184 */       ItemStack itemstack = merchantrecipe.getItemToBuy();
/* 185 */       ItemStack itemstack1 = merchantrecipe.getSecondItemToBuy();
/* 186 */       ItemStack itemstack2 = merchantrecipe.getItemToSell();
/* 187 */       GlStateManager.pushMatrix();
/* 188 */       RenderHelper.enableGUIStandardItemLighting();
/* 189 */       GlStateManager.disableLighting();
/* 190 */       GlStateManager.enableRescaleNormal();
/* 191 */       GlStateManager.enableColorMaterial();
/* 192 */       GlStateManager.enableLighting();
/* 193 */       this.itemRender.zLevel = 100.0F;
/* 194 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack, i + 36, j + 24);
/* 195 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, i + 36, j + 24);
/*     */       
/* 197 */       if (!itemstack1.func_190926_b()) {
/*     */         
/* 199 */         this.itemRender.renderItemAndEffectIntoGUI(itemstack1, i + 62, j + 24);
/* 200 */         this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack1, i + 62, j + 24);
/*     */       } 
/*     */       
/* 203 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack2, i + 120, j + 24);
/* 204 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack2, i + 120, j + 24);
/* 205 */       this.itemRender.zLevel = 0.0F;
/* 206 */       GlStateManager.disableLighting();
/*     */       
/* 208 */       if (isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && !itemstack.func_190926_b()) {
/*     */         
/* 210 */         renderToolTip(itemstack, mouseX, mouseY);
/*     */       }
/* 212 */       else if (!itemstack1.func_190926_b() && isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && !itemstack1.func_190926_b()) {
/*     */         
/* 214 */         renderToolTip(itemstack1, mouseX, mouseY);
/*     */       }
/* 216 */       else if (!itemstack2.func_190926_b() && isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && !itemstack2.func_190926_b()) {
/*     */         
/* 218 */         renderToolTip(itemstack2, mouseX, mouseY);
/*     */       }
/* 220 */       else if (merchantrecipe.isRecipeDisabled() && (isPointInRegion(83, 21, 28, 21, mouseX, mouseY) || isPointInRegion(83, 51, 28, 21, mouseX, mouseY))) {
/*     */         
/* 222 */         drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), mouseX, mouseY);
/*     */       } 
/*     */       
/* 225 */       GlStateManager.popMatrix();
/* 226 */       GlStateManager.enableLighting();
/* 227 */       GlStateManager.enableDepth();
/* 228 */       RenderHelper.enableStandardItemLighting();
/*     */     } 
/*     */     
/* 231 */     func_191948_b(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public IMerchant getMerchant() {
/* 236 */     return this.merchant;
/*     */   }
/*     */   
/*     */   static class MerchantButton
/*     */     extends GuiButton
/*     */   {
/*     */     private final boolean forward;
/*     */     
/*     */     public MerchantButton(int buttonID, int x, int y, boolean p_i1095_4_) {
/* 245 */       super(buttonID, x, y, 12, 19, "");
/* 246 */       this.forward = p_i1095_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 251 */       if (this.visible) {
/*     */         
/* 253 */         p_191745_1_.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
/* 254 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 255 */         boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 256 */         int i = 0;
/* 257 */         int j = 176;
/*     */         
/* 259 */         if (!this.enabled) {
/*     */           
/* 261 */           j += this.width * 2;
/*     */         }
/* 263 */         else if (flag) {
/*     */           
/* 265 */           j += this.width;
/*     */         } 
/*     */         
/* 268 */         if (!this.forward)
/*     */         {
/* 270 */           i += this.height;
/*     */         }
/*     */         
/* 273 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, i, this.width, this.height);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
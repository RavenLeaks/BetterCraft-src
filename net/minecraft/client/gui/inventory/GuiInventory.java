/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiButtonImage;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.recipebook.GuiRecipeBook;
/*     */ import net.minecraft.client.gui.recipebook.IRecipeShownListener;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.ContainerPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ 
/*     */ public class GuiInventory
/*     */   extends InventoryEffectRenderer
/*     */   implements IRecipeShownListener {
/*     */   private float oldMouseX;
/*     */   private float oldMouseY;
/*     */   private GuiButtonImage field_192048_z;
/*  29 */   private final GuiRecipeBook field_192045_A = new GuiRecipeBook();
/*     */   
/*     */   private boolean field_192046_B;
/*     */   private boolean field_194031_B;
/*     */   
/*     */   public GuiInventory(EntityPlayer player) {
/*  35 */     super(player.inventoryContainer);
/*  36 */     this.allowUserInput = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  44 */     if (this.mc.playerController.isInCreativeMode())
/*     */     {
/*  46 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.player));
/*     */     }
/*     */     
/*  49 */     this.field_192045_A.func_193957_d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  58 */     this.buttonList.clear();
/*     */     
/*  60 */     if (this.mc.playerController.isInCreativeMode()) {
/*     */       
/*  62 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.player));
/*     */     }
/*     */     else {
/*     */       
/*  66 */       super.initGui();
/*     */     } 
/*     */     
/*  69 */     this.field_192046_B = (this.width < 379);
/*  70 */     this.field_192045_A.func_194303_a(this.width, this.height, this.mc, this.field_192046_B, ((ContainerPlayer)this.inventorySlots).craftMatrix);
/*  71 */     this.guiLeft = this.field_192045_A.func_193011_a(this.field_192046_B, this.width, this.xSize);
/*  72 */     this.field_192048_z = new GuiButtonImage(10, this.guiLeft + 104, this.height / 2 - 22, 20, 18, 178, 0, 19, INVENTORY_BACKGROUND);
/*  73 */     this.buttonList.add(this.field_192048_z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  81 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 97, 8, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  89 */     drawDefaultBackground();
/*  90 */     this.hasActivePotionEffects = !this.field_192045_A.func_191878_b();
/*     */     
/*  92 */     if (this.field_192045_A.func_191878_b() && this.field_192046_B) {
/*     */       
/*  94 */       drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/*  95 */       this.field_192045_A.func_191861_a(mouseX, mouseY, partialTicks);
/*     */     }
/*     */     else {
/*     */       
/*  99 */       this.field_192045_A.func_191861_a(mouseX, mouseY, partialTicks);
/* 100 */       super.drawScreen(mouseX, mouseY, partialTicks);
/* 101 */       this.field_192045_A.func_191864_a(this.guiLeft, this.guiTop, false, partialTicks);
/*     */     } 
/*     */     
/* 104 */     func_191948_b(mouseX, mouseY);
/* 105 */     this.field_192045_A.func_191876_c(this.guiLeft, this.guiTop, mouseX, mouseY);
/* 106 */     this.oldMouseX = mouseX;
/* 107 */     this.oldMouseY = mouseY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 115 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 116 */     this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
/* 117 */     int i = this.guiLeft;
/* 118 */     int j = this.guiTop;
/* 119 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 120 */     drawEntityOnScreen(i + 51, j + 75, 30, (i + 51) - this.oldMouseX, (j + 75 - 50) - this.oldMouseY, (EntityLivingBase)this.mc.player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
/* 128 */     GlStateManager.enableColorMaterial();
/* 129 */     GlStateManager.pushMatrix();
/* 130 */     GlStateManager.translate(posX, posY, 50.0F);
/* 131 */     GlStateManager.scale(-scale, scale, scale);
/* 132 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 133 */     float f = ent.renderYawOffset;
/* 134 */     float f1 = ent.rotationYaw;
/* 135 */     float f2 = ent.rotationPitch;
/* 136 */     float f3 = ent.prevRotationYawHead;
/* 137 */     float f4 = ent.rotationYawHead;
/* 138 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/* 139 */     RenderHelper.enableStandardItemLighting();
/* 140 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 141 */     GlStateManager.rotate(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/* 142 */     ent.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
/* 143 */     ent.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
/* 144 */     ent.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
/* 145 */     ent.rotationYawHead = ent.rotationYaw;
/* 146 */     ent.prevRotationYawHead = ent.rotationYaw;
/* 147 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 148 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 149 */     rendermanager.setPlayerViewY(180.0F);
/* 150 */     rendermanager.setRenderShadow(false);
/* 151 */     rendermanager.doRenderEntity((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
/* 152 */     rendermanager.setRenderShadow(true);
/* 153 */     ent.renderYawOffset = f;
/* 154 */     ent.rotationYaw = f1;
/* 155 */     ent.rotationPitch = f2;
/* 156 */     ent.prevRotationYawHead = f3;
/* 157 */     ent.rotationYawHead = f4;
/* 158 */     GlStateManager.popMatrix();
/* 159 */     RenderHelper.disableStandardItemLighting();
/* 160 */     GlStateManager.disableRescaleNormal();
/* 161 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 162 */     GlStateManager.disableTexture2D();
/* 163 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
/* 172 */     return ((!this.field_192046_B || !this.field_192045_A.func_191878_b()) && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 180 */     if (!this.field_192045_A.func_191862_a(mouseX, mouseY, mouseButton))
/*     */     {
/* 182 */       if (!this.field_192046_B || !this.field_192045_A.func_191878_b())
/*     */       {
/* 184 */         super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 194 */     if (this.field_194031_B) {
/*     */       
/* 196 */       this.field_194031_B = false;
/*     */     }
/*     */     else {
/*     */       
/* 200 */       super.mouseReleased(mouseX, mouseY, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
/* 206 */     boolean flag = !(p_193983_1_ >= p_193983_3_ && p_193983_2_ >= p_193983_4_ && p_193983_1_ < p_193983_3_ + this.xSize && p_193983_2_ < p_193983_4_ + this.ySize);
/* 207 */     return (this.field_192045_A.func_193955_c(p_193983_1_, p_193983_2_, this.guiLeft, this.guiTop, this.xSize, this.ySize) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 215 */     if (button.id == 10) {
/*     */       
/* 217 */       this.field_192045_A.func_193014_a(this.field_192046_B, ((ContainerPlayer)this.inventorySlots).craftMatrix);
/* 218 */       this.field_192045_A.func_191866_a();
/* 219 */       this.guiLeft = this.field_192045_A.func_193011_a(this.field_192046_B, this.width, this.xSize);
/* 220 */       this.field_192048_z.func_191746_c(this.guiLeft + 104, this.height / 2 - 22);
/* 221 */       this.field_194031_B = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 231 */     if (!this.field_192045_A.func_191859_a(typedChar, keyCode))
/*     */     {
/* 233 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
/* 242 */     super.handleMouseClick(slotIn, slotId, mouseButton, type);
/* 243 */     this.field_192045_A.func_191874_a(slotIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192043_J_() {
/* 248 */     this.field_192045_A.func_193948_e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 256 */     this.field_192045_A.func_191871_c();
/* 257 */     super.onGuiClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiRecipeBook func_194310_f() {
/* 262 */     return this.field_192045_A;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
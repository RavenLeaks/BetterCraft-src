/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiChest extends GuiContainer {
/* 12 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*    */ 
/*    */   
/*    */   private final IInventory upperChestInventory;
/*    */   
/*    */   private final IInventory lowerChestInventory;
/*    */   
/*    */   private final int inventoryRows;
/*    */ 
/*    */   
/*    */   public GuiChest(IInventory upperInv, IInventory lowerInv) {
/* 23 */     super((Container)new ContainerChest(upperInv, lowerInv, (EntityPlayer)(Minecraft.getMinecraft()).player));
/* 24 */     this.upperChestInventory = upperInv;
/* 25 */     this.lowerChestInventory = lowerInv;
/* 26 */     this.allowUserInput = false;
/* 27 */     int i = 222;
/* 28 */     int j = 114;
/* 29 */     this.inventoryRows = lowerInv.getSizeInventory() / 9;
/* 30 */     this.ySize = 114 + this.inventoryRows * 18;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 38 */     drawDefaultBackground();
/* 39 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 40 */     func_191948_b(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 48 */     this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 49 */     this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 57 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 58 */     this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
/* 59 */     int i = (this.width - this.xSize) / 2;
/* 60 */     int j = (this.height - this.ySize) / 2;
/* 61 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
/* 62 */     drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
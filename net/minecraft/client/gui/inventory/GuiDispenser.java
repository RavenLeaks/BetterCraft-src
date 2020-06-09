/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerDispenser;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiDispenser extends GuiContainer {
/* 11 */   private static final ResourceLocation DISPENSER_GUI_TEXTURES = new ResourceLocation("textures/gui/container/dispenser.png");
/*    */ 
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */ 
/*    */   
/*    */   public IInventory dispenserInventory;
/*    */ 
/*    */   
/*    */   public GuiDispenser(InventoryPlayer playerInv, IInventory dispenserInv) {
/* 21 */     super((Container)new ContainerDispenser((IInventory)playerInv, dispenserInv));
/* 22 */     this.playerInventory = playerInv;
/* 23 */     this.dispenserInventory = dispenserInv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 31 */     drawDefaultBackground();
/* 32 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 33 */     func_191948_b(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 41 */     String s = this.dispenserInventory.getDisplayName().getUnformattedText();
/* 42 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 43 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 51 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 52 */     this.mc.getTextureManager().bindTexture(DISPENSER_GUI_TEXTURES);
/* 53 */     int i = (this.width - this.xSize) / 2;
/* 54 */     int j = (this.height - this.ySize) / 2;
/* 55 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
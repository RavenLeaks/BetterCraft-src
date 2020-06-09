/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerFurnace;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiFurnace extends GuiContainer {
/* 12 */   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */   
/*    */   private final IInventory tileFurnace;
/*    */ 
/*    */   
/*    */   public GuiFurnace(InventoryPlayer playerInv, IInventory furnaceInv) {
/* 20 */     super((Container)new ContainerFurnace(playerInv, furnaceInv));
/* 21 */     this.playerInventory = playerInv;
/* 22 */     this.tileFurnace = furnaceInv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 30 */     drawDefaultBackground();
/* 31 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 32 */     func_191948_b(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 40 */     String s = this.tileFurnace.getDisplayName().getUnformattedText();
/* 41 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 42 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 50 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 51 */     this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
/* 52 */     int i = (this.width - this.xSize) / 2;
/* 53 */     int j = (this.height - this.ySize) / 2;
/* 54 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */     
/* 56 */     if (TileEntityFurnace.isBurning(this.tileFurnace)) {
/*    */       
/* 58 */       int k = getBurnLeftScaled(13);
/* 59 */       drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
/*    */     } 
/*    */     
/* 62 */     int l = getCookProgressScaled(24);
/* 63 */     drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
/*    */   }
/*    */ 
/*    */   
/*    */   private int getCookProgressScaled(int pixels) {
/* 68 */     int i = this.tileFurnace.getField(2);
/* 69 */     int j = this.tileFurnace.getField(3);
/* 70 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   private int getBurnLeftScaled(int pixels) {
/* 75 */     int i = this.tileFurnace.getField(1);
/*    */     
/* 77 */     if (i == 0)
/*    */     {
/* 79 */       i = 200;
/*    */     }
/*    */     
/* 82 */     return this.tileFurnace.getField(0) * pixels / i;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
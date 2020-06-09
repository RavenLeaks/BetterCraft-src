/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerShulkerBox;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiShulkerBox extends GuiContainer {
/* 12 */   private static final ResourceLocation field_190778_u = new ResourceLocation("textures/gui/container/shulker_box.png");
/*    */   
/*    */   private final IInventory field_190779_v;
/*    */   private final InventoryPlayer field_190780_w;
/*    */   
/*    */   public GuiShulkerBox(InventoryPlayer p_i47233_1_, IInventory p_i47233_2_) {
/* 18 */     super((Container)new ContainerShulkerBox(p_i47233_1_, p_i47233_2_, (EntityPlayer)(Minecraft.getMinecraft()).player));
/* 19 */     this.field_190780_w = p_i47233_1_;
/* 20 */     this.field_190779_v = p_i47233_2_;
/* 21 */     this.ySize++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 29 */     drawDefaultBackground();
/* 30 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 31 */     func_191948_b(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 39 */     this.fontRendererObj.drawString(this.field_190779_v.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 40 */     this.fontRendererObj.drawString(this.field_190780_w.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 48 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 49 */     this.mc.getTextureManager().bindTexture(field_190778_u);
/* 50 */     int i = (this.width - this.xSize) / 2;
/* 51 */     int j = (this.height - this.ySize) / 2;
/* 52 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
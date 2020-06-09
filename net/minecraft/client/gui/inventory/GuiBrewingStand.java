/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerBrewingStand;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class GuiBrewingStand extends GuiContainer {
/* 12 */   private static final ResourceLocation BREWING_STAND_GUI_TEXTURES = new ResourceLocation("textures/gui/container/brewing_stand.png");
/* 13 */   private static final int[] BUBBLELENGTHS = new int[] { 29, 24, 20, 16, 11, 6 };
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */   
/*    */   private final IInventory tileBrewingStand;
/*    */ 
/*    */   
/*    */   public GuiBrewingStand(InventoryPlayer playerInv, IInventory p_i45506_2_) {
/* 21 */     super((Container)new ContainerBrewingStand(playerInv, p_i45506_2_));
/* 22 */     this.playerInventory = playerInv;
/* 23 */     this.tileBrewingStand = p_i45506_2_;
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
/* 41 */     String s = this.tileBrewingStand.getDisplayName().getUnformattedText();
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
/* 52 */     this.mc.getTextureManager().bindTexture(BREWING_STAND_GUI_TEXTURES);
/* 53 */     int i = (this.width - this.xSize) / 2;
/* 54 */     int j = (this.height - this.ySize) / 2;
/* 55 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 56 */     int k = this.tileBrewingStand.getField(1);
/* 57 */     int l = MathHelper.clamp((18 * k + 20 - 1) / 20, 0, 18);
/*    */     
/* 59 */     if (l > 0)
/*    */     {
/* 61 */       drawTexturedModalRect(i + 60, j + 44, 176, 29, l, 4);
/*    */     }
/*    */     
/* 64 */     int i1 = this.tileBrewingStand.getField(0);
/*    */     
/* 66 */     if (i1 > 0) {
/*    */       
/* 68 */       int j1 = (int)(28.0F * (1.0F - i1 / 400.0F));
/*    */       
/* 70 */       if (j1 > 0)
/*    */       {
/* 72 */         drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, j1);
/*    */       }
/*    */       
/* 75 */       j1 = BUBBLELENGTHS[i1 / 2 % 7];
/*    */       
/* 77 */       if (j1 > 0)
/*    */       {
/* 79 */         drawTexturedModalRect(i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
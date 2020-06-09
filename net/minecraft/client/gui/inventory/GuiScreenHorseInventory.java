/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.AbstractChestHorse;
/*    */ import net.minecraft.entity.passive.AbstractHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerHorseInventory;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiScreenHorseInventory extends GuiContainer {
/* 14 */   private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/horse.png");
/*    */ 
/*    */   
/*    */   private final IInventory playerInventory;
/*    */ 
/*    */   
/*    */   private final IInventory horseInventory;
/*    */ 
/*    */   
/*    */   private final AbstractHorse horseEntity;
/*    */ 
/*    */   
/*    */   private float mousePosx;
/*    */ 
/*    */   
/*    */   private float mousePosY;
/*    */ 
/*    */   
/*    */   public GuiScreenHorseInventory(IInventory playerInv, IInventory horseInv, AbstractHorse horse) {
/* 33 */     super((Container)new ContainerHorseInventory(playerInv, horseInv, horse, (EntityPlayer)(Minecraft.getMinecraft()).player));
/* 34 */     this.playerInventory = playerInv;
/* 35 */     this.horseInventory = horseInv;
/* 36 */     this.horseEntity = horse;
/* 37 */     this.allowUserInput = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 45 */     this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 46 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 54 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     this.mc.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
/* 56 */     int i = (this.width - this.xSize) / 2;
/* 57 */     int j = (this.height - this.ySize) / 2;
/* 58 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */     
/* 60 */     if (this.horseEntity instanceof AbstractChestHorse) {
/*    */       
/* 62 */       AbstractChestHorse abstractchesthorse = (AbstractChestHorse)this.horseEntity;
/*    */       
/* 64 */       if (abstractchesthorse.func_190695_dh())
/*    */       {
/* 66 */         drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, abstractchesthorse.func_190696_dl() * 18, 54);
/*    */       }
/*    */     } 
/*    */     
/* 70 */     if (this.horseEntity.func_190685_dA())
/*    */     {
/* 72 */       drawTexturedModalRect(i + 7, j + 35 - 18, 18, this.ySize + 54, 18, 18);
/*    */     }
/*    */     
/* 75 */     if (this.horseEntity.func_190677_dK())
/*    */     {
/* 77 */       if (this.horseEntity instanceof net.minecraft.entity.passive.EntityLlama) {
/*    */         
/* 79 */         drawTexturedModalRect(i + 7, j + 35, 36, this.ySize + 54, 18, 18);
/*    */       }
/*    */       else {
/*    */         
/* 83 */         drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
/*    */       } 
/*    */     }
/*    */     
/* 87 */     GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (i + 51) - this.mousePosx, (j + 75 - 50) - this.mousePosY, (EntityLivingBase)this.horseEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 95 */     drawDefaultBackground();
/* 96 */     this.mousePosx = mouseX;
/* 97 */     this.mousePosY = mouseY;
/* 98 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 99 */     func_191948_b(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiScreenHorseInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
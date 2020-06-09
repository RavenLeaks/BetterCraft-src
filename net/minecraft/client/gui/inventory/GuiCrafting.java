/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiButtonImage;
/*     */ import net.minecraft.client.gui.recipebook.GuiRecipeBook;
/*     */ import net.minecraft.client.gui.recipebook.IRecipeShownListener;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerWorkbench;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class GuiCrafting extends GuiContainer implements IRecipeShownListener {
/*  20 */   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
/*     */   
/*     */   private GuiButtonImage field_192049_w;
/*     */   private final GuiRecipeBook field_192050_x;
/*     */   private boolean field_193112_y;
/*     */   
/*     */   public GuiCrafting(InventoryPlayer playerInv, World worldIn) {
/*  27 */     this(playerInv, worldIn, BlockPos.ORIGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiCrafting(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
/*  32 */     super((Container)new ContainerWorkbench(playerInv, worldIn, blockPosition));
/*  33 */     this.field_192050_x = new GuiRecipeBook();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     super.initGui();
/*  43 */     this.field_193112_y = (this.width < 379);
/*  44 */     this.field_192050_x.func_194303_a(this.width, this.height, this.mc, this.field_193112_y, ((ContainerWorkbench)this.inventorySlots).craftMatrix);
/*  45 */     this.guiLeft = this.field_192050_x.func_193011_a(this.field_193112_y, this.width, this.xSize);
/*  46 */     this.field_192049_w = new GuiButtonImage(10, this.guiLeft + 5, this.height / 2 - 49, 20, 18, 0, 168, 19, CRAFTING_TABLE_GUI_TEXTURES);
/*  47 */     this.buttonList.add(this.field_192049_w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  55 */     super.updateScreen();
/*  56 */     this.field_192050_x.func_193957_d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  64 */     drawDefaultBackground();
/*     */     
/*  66 */     if (this.field_192050_x.func_191878_b() && this.field_193112_y) {
/*     */       
/*  68 */       drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/*  69 */       this.field_192050_x.func_191861_a(mouseX, mouseY, partialTicks);
/*     */     }
/*     */     else {
/*     */       
/*  73 */       this.field_192050_x.func_191861_a(mouseX, mouseY, partialTicks);
/*  74 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*  75 */       this.field_192050_x.func_191864_a(this.guiLeft, this.guiTop, true, partialTicks);
/*     */     } 
/*     */     
/*  78 */     func_191948_b(mouseX, mouseY);
/*  79 */     this.field_192050_x.func_191876_c(this.guiLeft, this.guiTop, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  87 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
/*  88 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  96 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  97 */     this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
/*  98 */     int i = this.guiLeft;
/*  99 */     int j = (this.height - this.ySize) / 2;
/* 100 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
/* 109 */     return ((!this.field_193112_y || !this.field_192050_x.func_191878_b()) && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 117 */     if (!this.field_192050_x.func_191862_a(mouseX, mouseY, mouseButton))
/*     */     {
/* 119 */       if (!this.field_193112_y || !this.field_192050_x.func_191878_b())
/*     */       {
/* 121 */         super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
/* 128 */     boolean flag = !(p_193983_1_ >= p_193983_3_ && p_193983_2_ >= p_193983_4_ && p_193983_1_ < p_193983_3_ + this.xSize && p_193983_2_ < p_193983_4_ + this.ySize);
/* 129 */     return (this.field_192050_x.func_193955_c(p_193983_1_, p_193983_2_, this.guiLeft, this.guiTop, this.xSize, this.ySize) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 137 */     if (button.id == 10) {
/*     */       
/* 139 */       this.field_192050_x.func_193014_a(this.field_193112_y, ((ContainerWorkbench)this.inventorySlots).craftMatrix);
/* 140 */       this.field_192050_x.func_191866_a();
/* 141 */       this.guiLeft = this.field_192050_x.func_193011_a(this.field_193112_y, this.width, this.xSize);
/* 142 */       this.field_192049_w.func_191746_c(this.guiLeft + 5, this.height / 2 - 49);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 152 */     if (!this.field_192050_x.func_191859_a(typedChar, keyCode))
/*     */     {
/* 154 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
/* 163 */     super.handleMouseClick(slotIn, slotId, mouseButton, type);
/* 164 */     this.field_192050_x.func_191874_a(slotIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192043_J_() {
/* 169 */     this.field_192050_x.func_193948_e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 177 */     this.field_192050_x.func_191871_c();
/* 178 */     super.onGuiClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiRecipeBook func_194310_f() {
/* 183 */     return this.field_192050_x;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
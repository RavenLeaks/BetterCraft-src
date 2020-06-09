/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.FlatLayerInfo;
/*     */ 
/*     */ public class GuiCreateFlatWorld
/*     */   extends GuiScreen {
/*     */   private final GuiCreateWorld createWorldGui;
/*  22 */   private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
/*     */ 
/*     */   
/*     */   private String flatWorldTitle;
/*     */ 
/*     */   
/*     */   private String materialText;
/*     */ 
/*     */   
/*     */   private String heightText;
/*     */ 
/*     */   
/*     */   private Details createFlatWorldListSlotGui;
/*     */ 
/*     */   
/*     */   private GuiButton addLayerButton;
/*     */   
/*     */   private GuiButton editLayerButton;
/*     */   
/*     */   private GuiButton removeLayerButton;
/*     */ 
/*     */   
/*     */   public GuiCreateFlatWorld(GuiCreateWorld createWorldGuiIn, String preset) {
/*  45 */     this.createWorldGui = createWorldGuiIn;
/*  46 */     setPreset(preset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreset() {
/*  54 */     return this.theFlatGeneratorInfo.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreset(String preset) {
/*  62 */     this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(preset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  71 */     this.buttonList.clear();
/*  72 */     this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
/*  73 */     this.materialText = I18n.format("createWorld.customize.flat.tile", new Object[0]);
/*  74 */     this.heightText = I18n.format("createWorld.customize.flat.height", new Object[0]);
/*  75 */     this.createFlatWorldListSlotGui = new Details();
/*  76 */     this.addLayerButton = addButton(new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)"));
/*  77 */     this.editLayerButton = addButton(new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)"));
/*  78 */     this.removeLayerButton = addButton(new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
/*  79 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  80 */     this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
/*  81 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  82 */     this.addLayerButton.visible = false;
/*  83 */     this.editLayerButton.visible = false;
/*  84 */     this.theFlatGeneratorInfo.updateLayers();
/*  85 */     onLayersChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  93 */     super.handleMouseInput();
/*  94 */     this.createFlatWorldListSlotGui.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 102 */     int i = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.selectedLayer - 1;
/*     */     
/* 104 */     if (button.id == 1) {
/*     */       
/* 106 */       this.mc.displayGuiScreen(this.createWorldGui);
/*     */     }
/* 108 */     else if (button.id == 0) {
/*     */       
/* 110 */       this.createWorldGui.chunkProviderSettingsJson = getPreset();
/* 111 */       this.mc.displayGuiScreen(this.createWorldGui);
/*     */     }
/* 113 */     else if (button.id == 5) {
/*     */       
/* 115 */       this.mc.displayGuiScreen(new GuiFlatPresets(this));
/*     */     }
/* 117 */     else if (button.id == 4 && hasSelectedLayer()) {
/*     */       
/* 119 */       this.theFlatGeneratorInfo.getFlatLayers().remove(i);
/* 120 */       this.createFlatWorldListSlotGui.selectedLayer = Math.min(this.createFlatWorldListSlotGui.selectedLayer, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
/*     */     } 
/*     */     
/* 123 */     this.theFlatGeneratorInfo.updateLayers();
/* 124 */     onLayersChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLayersChanged() {
/* 133 */     boolean flag = hasSelectedLayer();
/* 134 */     this.removeLayerButton.enabled = flag;
/* 135 */     this.editLayerButton.enabled = flag;
/* 136 */     this.editLayerButton.enabled = false;
/* 137 */     this.addLayerButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasSelectedLayer() {
/* 145 */     return (this.createFlatWorldListSlotGui.selectedLayer > -1 && this.createFlatWorldListSlotGui.selectedLayer < this.theFlatGeneratorInfo.getFlatLayers().size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 153 */     drawDefaultBackground();
/* 154 */     this.createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
/* 155 */     drawCenteredString(this.fontRendererObj, this.flatWorldTitle, this.width / 2, 8, 16777215);
/* 156 */     int i = this.width / 2 - 92 - 16;
/* 157 */     drawString(this.fontRendererObj, this.materialText, i, 32, 16777215);
/* 158 */     drawString(this.fontRendererObj, this.heightText, i + 2 + 213 - this.fontRendererObj.getStringWidth(this.heightText), 32, 16777215);
/* 159 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class Details
/*     */     extends GuiSlot {
/* 164 */     public int selectedLayer = -1;
/*     */ 
/*     */     
/*     */     public Details() {
/* 168 */       super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
/*     */     }
/*     */ 
/*     */     
/*     */     private void drawItem(int x, int z, ItemStack itemToDraw) {
/* 173 */       drawItemBackground(x + 1, z + 1);
/* 174 */       GlStateManager.enableRescaleNormal();
/*     */       
/* 176 */       if (!itemToDraw.func_190926_b()) {
/*     */         
/* 178 */         RenderHelper.enableGUIStandardItemLighting();
/* 179 */         GuiCreateFlatWorld.this.itemRender.renderItemIntoGUI(itemToDraw, x + 2, z + 2);
/* 180 */         RenderHelper.disableStandardItemLighting();
/*     */       } 
/*     */       
/* 183 */       GlStateManager.disableRescaleNormal();
/*     */     }
/*     */ 
/*     */     
/*     */     private void drawItemBackground(int x, int y) {
/* 188 */       drawItemBackground(x, y, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     private void drawItemBackground(int x, int z, int textureX, int textureY) {
/* 193 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 194 */       this.mc.getTextureManager().bindTexture(Gui.STAT_ICONS);
/* 195 */       float f = 0.0078125F;
/* 196 */       float f1 = 0.0078125F;
/* 197 */       int i = 18;
/* 198 */       int j = 18;
/* 199 */       Tessellator tessellator = Tessellator.getInstance();
/* 200 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 201 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 202 */       bufferbuilder.pos((x + 0), (z + 18), GuiCreateFlatWorld.this.zLevel).tex(((textureX + 0) * 0.0078125F), ((textureY + 18) * 0.0078125F)).endVertex();
/* 203 */       bufferbuilder.pos((x + 18), (z + 18), GuiCreateFlatWorld.this.zLevel).tex(((textureX + 18) * 0.0078125F), ((textureY + 18) * 0.0078125F)).endVertex();
/* 204 */       bufferbuilder.pos((x + 18), (z + 0), GuiCreateFlatWorld.this.zLevel).tex(((textureX + 18) * 0.0078125F), ((textureY + 0) * 0.0078125F)).endVertex();
/* 205 */       bufferbuilder.pos((x + 0), (z + 0), GuiCreateFlatWorld.this.zLevel).tex(((textureX + 0) * 0.0078125F), ((textureY + 0) * 0.0078125F)).endVertex();
/* 206 */       tessellator.draw();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 211 */       return GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 216 */       this.selectedLayer = slotIndex;
/* 217 */       GuiCreateFlatWorld.this.onLayersChanged();
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 222 */       return (slotIndex == this.selectedLayer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/*     */       String s1;
/* 231 */       FlatLayerInfo flatlayerinfo = GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().get(GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - p_192637_1_ - 1);
/* 232 */       IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
/* 233 */       Block block = iblockstate.getBlock();
/* 234 */       Item item = Item.getItemFromBlock(block);
/*     */       
/* 236 */       if (item == Items.field_190931_a)
/*     */       {
/* 238 */         if (block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
/*     */           
/* 240 */           if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
/*     */           {
/* 242 */             item = Items.LAVA_BUCKET;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 247 */           item = Items.WATER_BUCKET;
/*     */         } 
/*     */       }
/*     */       
/* 251 */       ItemStack itemstack = new ItemStack(item, 1, item.getHasSubtypes() ? block.getMetaFromState(iblockstate) : 0);
/* 252 */       String s = item.getItemStackDisplayName(itemstack);
/* 253 */       drawItem(p_192637_2_, p_192637_3_, itemstack);
/* 254 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s, p_192637_2_ + 18 + 5, p_192637_3_ + 3, 16777215);
/*     */ 
/*     */       
/* 257 */       if (p_192637_1_ == 0) {
/*     */         
/* 259 */         s1 = I18n.format("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       }
/* 261 */       else if (p_192637_1_ == GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - 1) {
/*     */         
/* 263 */         s1 = I18n.format("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       }
/*     */       else {
/*     */         
/* 267 */         s1 = I18n.format("createWorld.customize.flat.layer", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       } 
/*     */       
/* 270 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s1, p_192637_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(s1), p_192637_3_ + 3, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 275 */       return this.width - 70;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiCreateFlatWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
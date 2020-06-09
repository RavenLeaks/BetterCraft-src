/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.FlatLayerInfo;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiFlatPresets extends GuiScreen {
/*  28 */   private static final List<LayerItem> FLAT_WORLD_PRESETS = Lists.newArrayList();
/*     */   
/*     */   private final GuiCreateFlatWorld parentScreen;
/*     */   
/*     */   private String presetsTitle;
/*     */   
/*     */   private String presetsShare;
/*     */   private String listText;
/*     */   private ListSlot list;
/*     */   private GuiButton btnSelect;
/*     */   private GuiTextField export;
/*     */   
/*     */   public GuiFlatPresets(GuiCreateFlatWorld p_i46318_1_) {
/*  41 */     this.parentScreen = p_i46318_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  50 */     this.buttonList.clear();
/*  51 */     Keyboard.enableRepeatEvents(true);
/*  52 */     this.presetsTitle = I18n.format("createWorld.customize.presets.title", new Object[0]);
/*  53 */     this.presetsShare = I18n.format("createWorld.customize.presets.share", new Object[0]);
/*  54 */     this.listText = I18n.format("createWorld.customize.presets.list", new Object[0]);
/*  55 */     this.export = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
/*  56 */     this.list = new ListSlot();
/*  57 */     this.export.setMaxStringLength(1230);
/*  58 */     this.export.setText(this.parentScreen.getPreset());
/*  59 */     this.btnSelect = addButton(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
/*  60 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  61 */     updateButtonValidity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  69 */     super.handleMouseInput();
/*  70 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  78 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  86 */     this.export.mouseClicked(mouseX, mouseY, mouseButton);
/*  87 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  96 */     if (!this.export.textboxKeyTyped(typedChar, keyCode))
/*     */     {
/*  98 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 107 */     if (button.id == 0 && hasValidSelection()) {
/*     */       
/* 109 */       this.parentScreen.setPreset(this.export.getText());
/* 110 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     }
/* 112 */     else if (button.id == 1) {
/*     */       
/* 114 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 123 */     drawDefaultBackground();
/* 124 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 125 */     drawCenteredString(this.fontRendererObj, this.presetsTitle, this.width / 2, 8, 16777215);
/* 126 */     drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
/* 127 */     drawString(this.fontRendererObj, this.listText, 50, 70, 10526880);
/* 128 */     this.export.drawTextBox();
/* 129 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 137 */     this.export.updateCursorCounter();
/* 138 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtonValidity() {
/* 143 */     this.btnSelect.enabled = hasValidSelection();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasValidSelection() {
/* 148 */     return !((this.list.selected <= -1 || this.list.selected >= FLAT_WORLD_PRESETS.size()) && this.export.getText().length() <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void registerPreset(String name, Item icon, Biome biome, List<String> features, FlatLayerInfo... layers) {
/* 153 */     registerPreset(name, icon, 0, biome, features, layers);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void registerPreset(String name, Item icon, int iconMetadata, Biome biome, List<String> features, FlatLayerInfo... layers) {
/* 158 */     FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/*     */     
/* 160 */     for (int i = layers.length - 1; i >= 0; i--)
/*     */     {
/* 162 */       flatgeneratorinfo.getFlatLayers().add(layers[i]);
/*     */     }
/*     */     
/* 165 */     flatgeneratorinfo.setBiome(Biome.getIdForBiome(biome));
/* 166 */     flatgeneratorinfo.updateLayers();
/*     */     
/* 168 */     for (String s : features)
/*     */     {
/* 170 */       flatgeneratorinfo.getWorldFeatures().put(s, Maps.newHashMap());
/*     */     }
/*     */     
/* 173 */     FLAT_WORLD_PRESETS.add(new LayerItem(icon, iconMetadata, name, flatgeneratorinfo.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 178 */     registerPreset(I18n.format("createWorld.customize.preset.classic_flat", new Object[0]), Item.getItemFromBlock((Block)Blocks.GRASS), Biomes.PLAINS, Arrays.asList(new String[] { "village" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.GRASS), new FlatLayerInfo(2, Blocks.DIRT), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 179 */     registerPreset(I18n.format("createWorld.customize.preset.tunnelers_dream", new Object[0]), Item.getItemFromBlock(Blocks.STONE), Biomes.EXTREME_HILLS, Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.GRASS), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(230, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 180 */     registerPreset(I18n.format("createWorld.customize.preset.water_world", new Object[0]), Items.WATER_BUCKET, Biomes.DEEP_OCEAN, Arrays.asList(new String[] { "biome_1", "oceanmonument" }, ), new FlatLayerInfo[] { new FlatLayerInfo(90, (Block)Blocks.WATER), new FlatLayerInfo(5, (Block)Blocks.SAND), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(5, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 181 */     registerPreset(I18n.format("createWorld.customize.preset.overworld", new Object[0]), Item.getItemFromBlock((Block)Blocks.TALLGRASS), BlockTallGrass.EnumType.GRASS.getMeta(), Biomes.PLAINS, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.GRASS), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 182 */     registerPreset(I18n.format("createWorld.customize.preset.snowy_kingdom", new Object[0]), Item.getItemFromBlock(Blocks.SNOW_LAYER), Biomes.ICE_PLAINS, Arrays.asList(new String[] { "village", "biome_1" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.SNOW_LAYER), new FlatLayerInfo(1, (Block)Blocks.GRASS), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 183 */     registerPreset(I18n.format("createWorld.customize.preset.bottomless_pit", new Object[0]), Items.FEATHER, Biomes.PLAINS, Arrays.asList(new String[] { "village", "biome_1" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.GRASS), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(2, Blocks.COBBLESTONE) });
/* 184 */     registerPreset(I18n.format("createWorld.customize.preset.desert", new Object[0]), Item.getItemFromBlock((Block)Blocks.SAND), Biomes.DESERT, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }, ), new FlatLayerInfo[] { new FlatLayerInfo(8, (Block)Blocks.SAND), new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 185 */     registerPreset(I18n.format("createWorld.customize.preset.redstone_ready", new Object[0]), Items.REDSTONE, Biomes.DESERT, Collections.emptyList(), new FlatLayerInfo[] { new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/* 186 */     registerPreset(I18n.format("createWorld.customize.preset.the_void", new Object[0]), Item.getItemFromBlock(Blocks.BARRIER), Biomes.VOID, Arrays.asList(new String[] { "decoration" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.AIR) });
/*     */   }
/*     */ 
/*     */   
/*     */   static class LayerItem
/*     */   {
/*     */     public Item icon;
/*     */     public int iconMetadata;
/*     */     public String name;
/*     */     public String generatorInfo;
/*     */     
/*     */     public LayerItem(Item iconIn, int iconMetadataIn, String nameIn, String generatorInfoIn) {
/* 198 */       this.icon = iconIn;
/* 199 */       this.iconMetadata = iconMetadataIn;
/* 200 */       this.name = nameIn;
/* 201 */       this.generatorInfo = generatorInfoIn;
/*     */     }
/*     */   }
/*     */   
/*     */   class ListSlot
/*     */     extends GuiSlot {
/* 207 */     public int selected = -1;
/*     */ 
/*     */     
/*     */     public ListSlot() {
/* 211 */       super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
/*     */     }
/*     */ 
/*     */     
/*     */     private void renderIcon(int p_178054_1_, int p_178054_2_, Item icon, int iconMetadata) {
/* 216 */       blitSlotBg(p_178054_1_ + 1, p_178054_2_ + 1);
/* 217 */       GlStateManager.enableRescaleNormal();
/* 218 */       RenderHelper.enableGUIStandardItemLighting();
/* 219 */       GuiFlatPresets.this.itemRender.renderItemIntoGUI(new ItemStack(icon, 1, icon.getHasSubtypes() ? iconMetadata : 0), p_178054_1_ + 2, p_178054_2_ + 2);
/* 220 */       RenderHelper.disableStandardItemLighting();
/* 221 */       GlStateManager.disableRescaleNormal();
/*     */     }
/*     */ 
/*     */     
/*     */     private void blitSlotBg(int p_148173_1_, int p_148173_2_) {
/* 226 */       blitSlotIcon(p_148173_1_, p_148173_2_, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     private void blitSlotIcon(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
/* 231 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 232 */       this.mc.getTextureManager().bindTexture(Gui.STAT_ICONS);
/* 233 */       float f = 0.0078125F;
/* 234 */       float f1 = 0.0078125F;
/* 235 */       int i = 18;
/* 236 */       int j = 18;
/* 237 */       Tessellator tessellator = Tessellator.getInstance();
/* 238 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 239 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 240 */       bufferbuilder.pos((p_148171_1_ + 0), (p_148171_2_ + 18), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 0) * 0.0078125F), ((p_148171_4_ + 18) * 0.0078125F)).endVertex();
/* 241 */       bufferbuilder.pos((p_148171_1_ + 18), (p_148171_2_ + 18), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 18) * 0.0078125F), ((p_148171_4_ + 18) * 0.0078125F)).endVertex();
/* 242 */       bufferbuilder.pos((p_148171_1_ + 18), (p_148171_2_ + 0), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 18) * 0.0078125F), ((p_148171_4_ + 0) * 0.0078125F)).endVertex();
/* 243 */       bufferbuilder.pos((p_148171_1_ + 0), (p_148171_2_ + 0), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 0) * 0.0078125F), ((p_148171_4_ + 0) * 0.0078125F)).endVertex();
/* 244 */       tessellator.draw();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 249 */       return GuiFlatPresets.FLAT_WORLD_PRESETS.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 254 */       this.selected = slotIndex;
/* 255 */       GuiFlatPresets.this.updateButtonValidity();
/* 256 */       GuiFlatPresets.this.export.setText((GuiFlatPresets.FLAT_WORLD_PRESETS.get(GuiFlatPresets.this.list.selected)).generatorInfo);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 261 */       return (slotIndex == this.selected);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 270 */       GuiFlatPresets.LayerItem guiflatpresets$layeritem = GuiFlatPresets.FLAT_WORLD_PRESETS.get(p_192637_1_);
/* 271 */       renderIcon(p_192637_2_, p_192637_3_, guiflatpresets$layeritem.icon, guiflatpresets$layeritem.iconMetadata);
/* 272 */       GuiFlatPresets.this.fontRendererObj.drawString(guiflatpresets$layeritem.name, p_192637_2_ + 18 + 5, p_192637_3_ + 6, 16777215);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiFlatPresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
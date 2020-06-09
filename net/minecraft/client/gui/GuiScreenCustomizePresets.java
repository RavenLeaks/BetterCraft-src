/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.gen.ChunkGeneratorSettings;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenCustomizePresets
/*     */   extends GuiScreen {
/*  17 */   private static final List<Info> PRESETS = Lists.newArrayList();
/*     */   private ListPreset list;
/*     */   private GuiButton select;
/*     */   private GuiTextField export;
/*     */   private final GuiCustomizeWorldScreen parent;
/*  22 */   protected String title = "Customize World Presets";
/*     */   
/*     */   private String shareText;
/*     */   private String listText;
/*     */   
/*     */   public GuiScreenCustomizePresets(GuiCustomizeWorldScreen p_i45524_1_) {
/*  28 */     this.parent = p_i45524_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     this.buttonList.clear();
/*  38 */     Keyboard.enableRepeatEvents(true);
/*  39 */     this.title = I18n.format("createWorld.customize.custom.presets.title", new Object[0]);
/*  40 */     this.shareText = I18n.format("createWorld.customize.presets.share", new Object[0]);
/*  41 */     this.listText = I18n.format("createWorld.customize.presets.list", new Object[0]);
/*  42 */     this.export = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
/*  43 */     this.list = new ListPreset();
/*  44 */     this.export.setMaxStringLength(2000);
/*  45 */     this.export.setText(this.parent.saveValues());
/*  46 */     this.select = addButton(new GuiButton(0, this.width / 2 - 102, this.height - 27, 100, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
/*  47 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 3, this.height - 27, 100, 20, I18n.format("gui.cancel", new Object[0])));
/*  48 */     updateButtonValidity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  56 */     super.handleMouseInput();
/*  57 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  65 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  73 */     this.export.mouseClicked(mouseX, mouseY, mouseButton);
/*  74 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  83 */     if (!this.export.textboxKeyTyped(typedChar, keyCode))
/*     */     {
/*  85 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  94 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  97 */         this.parent.loadValues(this.export.getText());
/*  98 */         this.mc.displayGuiScreen(this.parent);
/*     */         break;
/*     */       
/*     */       case 1:
/* 102 */         this.mc.displayGuiScreen(this.parent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 111 */     drawDefaultBackground();
/* 112 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 113 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
/* 114 */     drawString(this.fontRendererObj, this.shareText, 50, 30, 10526880);
/* 115 */     drawString(this.fontRendererObj, this.listText, 50, 70, 10526880);
/* 116 */     this.export.drawTextBox();
/* 117 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 125 */     this.export.updateCursorCounter();
/* 126 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtonValidity() {
/* 131 */     this.select.enabled = hasValidSelection();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasValidSelection() {
/* 136 */     return !((this.list.selected <= -1 || this.list.selected >= PRESETS.size()) && this.export.getText().length() <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 141 */     ChunkGeneratorSettings.Factory chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
/* 142 */     ResourceLocation resourcelocation = new ResourceLocation("textures/gui/presets/water.png");
/* 143 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.waterWorld", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 144 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 145 */     resourcelocation = new ResourceLocation("textures/gui/presets/isles.png");
/* 146 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.isleLand", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 147 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 148 */     resourcelocation = new ResourceLocation("textures/gui/presets/delight.png");
/* 149 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.caveDelight", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 150 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 151 */     resourcelocation = new ResourceLocation("textures/gui/presets/madness.png");
/* 152 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.mountains", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 153 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
/* 154 */     resourcelocation = new ResourceLocation("textures/gui/presets/drought.png");
/* 155 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.drought", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 156 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
/* 157 */     resourcelocation = new ResourceLocation("textures/gui/presets/chaos.png");
/* 158 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.caveChaos", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/* 159 */     chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
/* 160 */     resourcelocation = new ResourceLocation("textures/gui/presets/luck.png");
/* 161 */     PRESETS.add(new Info(I18n.format("createWorld.customize.custom.preset.goodLuck", new Object[0]), resourcelocation, chunkgeneratorsettings$factory));
/*     */   }
/*     */ 
/*     */   
/*     */   static class Info
/*     */   {
/*     */     public String name;
/*     */     public ResourceLocation texture;
/*     */     public ChunkGeneratorSettings.Factory settings;
/*     */     
/*     */     public Info(String nameIn, ResourceLocation textureIn, ChunkGeneratorSettings.Factory settingsIn) {
/* 172 */       this.name = nameIn;
/* 173 */       this.texture = textureIn;
/* 174 */       this.settings = settingsIn;
/*     */     }
/*     */   }
/*     */   
/*     */   class ListPreset
/*     */     extends GuiSlot {
/* 180 */     public int selected = -1;
/*     */ 
/*     */     
/*     */     public ListPreset() {
/* 184 */       super(GuiScreenCustomizePresets.this.mc, GuiScreenCustomizePresets.this.width, GuiScreenCustomizePresets.this.height, 80, GuiScreenCustomizePresets.this.height - 32, 38);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 189 */       return GuiScreenCustomizePresets.PRESETS.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 194 */       this.selected = slotIndex;
/* 195 */       GuiScreenCustomizePresets.this.updateButtonValidity();
/* 196 */       GuiScreenCustomizePresets.this.export.setText((GuiScreenCustomizePresets.PRESETS.get(GuiScreenCustomizePresets.this.list.selected)).settings.toString());
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 201 */       return (slotIndex == this.selected);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     private void blitIcon(int p_178051_1_, int p_178051_2_, ResourceLocation p_178051_3_) {
/* 210 */       int i = p_178051_1_ + 5;
/* 211 */       GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ - 1, -2039584);
/* 212 */       GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ + 32, -6250336);
/* 213 */       GuiScreenCustomizePresets.this.drawVerticalLine(i - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
/* 214 */       GuiScreenCustomizePresets.this.drawVerticalLine(i + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
/* 215 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 216 */       this.mc.getTextureManager().bindTexture(p_178051_3_);
/* 217 */       int j = 32;
/* 218 */       int k = 32;
/* 219 */       Tessellator tessellator = Tessellator.getInstance();
/* 220 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 221 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 222 */       bufferbuilder.pos((i + 0), (p_178051_2_ + 32), 0.0D).tex(0.0D, 1.0D).endVertex();
/* 223 */       bufferbuilder.pos((i + 32), (p_178051_2_ + 32), 0.0D).tex(1.0D, 1.0D).endVertex();
/* 224 */       bufferbuilder.pos((i + 32), (p_178051_2_ + 0), 0.0D).tex(1.0D, 0.0D).endVertex();
/* 225 */       bufferbuilder.pos((i + 0), (p_178051_2_ + 0), 0.0D).tex(0.0D, 0.0D).endVertex();
/* 226 */       tessellator.draw();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 231 */       GuiScreenCustomizePresets.Info guiscreencustomizepresets$info = GuiScreenCustomizePresets.PRESETS.get(p_192637_1_);
/* 232 */       blitIcon(p_192637_2_, p_192637_3_, guiscreencustomizepresets$info.texture);
/* 233 */       GuiScreenCustomizePresets.this.fontRendererObj.drawString(guiscreencustomizepresets$info.name, p_192637_2_ + 32 + 10, p_192637_3_ + 14, 16777215);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenCustomizePresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
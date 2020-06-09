/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.primitives.Floats;
/*      */ import java.io.IOException;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.gen.ChunkGeneratorSettings;
/*      */ 
/*      */ public class GuiCustomizeWorldScreen
/*      */   extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
/*      */   private final GuiCreateWorld parent;
/*   21 */   protected String title = "Customize World Settings";
/*   22 */   protected String subtitle = "Page 1 of 3";
/*   23 */   protected String pageTitle = "Basic Settings"; private GuiPageButtonList list; private GuiButton done; private GuiButton randomize; private GuiButton defaults; private GuiButton previousPage; private GuiButton nextPage;
/*   24 */   protected String[] pageNames = new String[4];
/*      */   
/*      */   private GuiButton confirm;
/*      */   
/*      */   private GuiButton cancel;
/*      */   
/*      */   private GuiButton presets;
/*      */   
/*      */   private boolean settingsModified;
/*      */   
/*      */   private int confirmMode;
/*      */   private boolean confirmDismissed;
/*      */   
/*   37 */   private final Predicate<String> numberFilter = new Predicate<String>()
/*      */     {
/*      */       public boolean apply(@Nullable String p_apply_1_)
/*      */       {
/*   41 */         Float f = Floats.tryParse(p_apply_1_);
/*   42 */         return !(!p_apply_1_.isEmpty() && (f == null || !Floats.isFinite(f.floatValue()) || f.floatValue() < 0.0F));
/*      */       }
/*      */     };
/*   45 */   private final ChunkGeneratorSettings.Factory defaultSettings = new ChunkGeneratorSettings.Factory();
/*      */   
/*      */   private ChunkGeneratorSettings.Factory settings;
/*      */   
/*   49 */   private final Random random = new Random();
/*      */ 
/*      */   
/*      */   public GuiCustomizeWorldScreen(GuiScreen p_i45521_1_, String p_i45521_2_) {
/*   53 */     this.parent = (GuiCreateWorld)p_i45521_1_;
/*   54 */     loadValues(p_i45521_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initGui() {
/*   63 */     int i = 0;
/*   64 */     int j = 0;
/*      */     
/*   66 */     if (this.list != null) {
/*      */       
/*   68 */       i = this.list.getPage();
/*   69 */       j = this.list.getAmountScrolled();
/*      */     } 
/*      */     
/*   72 */     this.title = I18n.format("options.customizeTitle", new Object[0]);
/*   73 */     this.buttonList.clear();
/*   74 */     this.previousPage = addButton(new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev", new Object[0])));
/*   75 */     this.nextPage = addButton(new GuiButton(303, this.width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next", new Object[0])));
/*   76 */     this.defaults = addButton(new GuiButton(304, this.width / 2 - 187, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults", new Object[0])));
/*   77 */     this.randomize = addButton(new GuiButton(301, this.width / 2 - 92, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize", new Object[0])));
/*   78 */     this.presets = addButton(new GuiButton(305, this.width / 2 + 3, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets", new Object[0])));
/*   79 */     this.done = addButton(new GuiButton(300, this.width / 2 + 98, this.height - 27, 90, 20, I18n.format("gui.done", new Object[0])));
/*   80 */     this.defaults.enabled = this.settingsModified;
/*   81 */     this.confirm = new GuiButton(306, this.width / 2 - 55, 160, 50, 20, I18n.format("gui.yes", new Object[0]));
/*   82 */     this.confirm.visible = false;
/*   83 */     this.buttonList.add(this.confirm);
/*   84 */     this.cancel = new GuiButton(307, this.width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
/*   85 */     this.cancel.visible = false;
/*   86 */     this.buttonList.add(this.cancel);
/*      */     
/*   88 */     if (this.confirmMode != 0) {
/*      */       
/*   90 */       this.confirm.visible = true;
/*   91 */       this.cancel.visible = true;
/*      */     } 
/*      */     
/*   94 */     createPagedList();
/*      */     
/*   96 */     if (i != 0) {
/*      */       
/*   98 */       this.list.setPage(i);
/*   99 */       this.list.scrollBy(j);
/*  100 */       updatePageControls();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMouseInput() throws IOException {
/*  109 */     super.handleMouseInput();
/*  110 */     this.list.handleMouseInput();
/*      */   }
/*      */ 
/*      */   
/*      */   private void createPagedList() {
/*  115 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry = { new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0F, 255.0F, this.settings.seaLevel), new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves", new Object[0]), true, this.settings.useCaves), new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.settings.useStrongholds), new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages", new Object[0]), true, this.settings.useVillages), new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.settings.useMineShafts), new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples", new Object[0]), true, this.settings.useTemples), new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments", new Object[0]), true, this.settings.useMonuments), new GuiPageButtonList.GuiButtonEntry(211, I18n.format("createWorld.customize.custom.useMansions", new Object[0]), true, this.settings.field_191076_A), new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines", new Object[0]), true, this.settings.useRavines), new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons", new Object[0]), true, this.settings.useDungeons), new GuiPageButtonList.GuiSlideEntry(157, I18n.format("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0F, 100.0F, this.settings.dungeonChance), new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.settings.useWaterLakes), new GuiPageButtonList.GuiSlideEntry(158, I18n.format("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0F, 100.0F, this.settings.waterLakeChance), new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.settings.useLavaLakes), new GuiPageButtonList.GuiSlideEntry(159, I18n.format("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0F, 100.0F, this.settings.lavaLakeChance), new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.settings.useLavaOceans), new GuiPageButtonList.GuiSlideEntry(162, I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0F, 37.0F, this.settings.fixedBiome), new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0F, 8.0F, this.settings.biomeSize), new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0F, 5.0F, this.settings.riverSize) };
/*  116 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry1 = { new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.dirtSize), new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.dirtCount), new GuiPageButtonList.GuiSlideEntry(167, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.dirtMinHeight), new GuiPageButtonList.GuiSlideEntry(168, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.dirtMaxHeight), new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.gravelSize), new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.gravelCount), new GuiPageButtonList.GuiSlideEntry(171, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.gravelMinHeight), new GuiPageButtonList.GuiSlideEntry(172, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.gravelMaxHeight), new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.graniteSize), new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.graniteCount), new GuiPageButtonList.GuiSlideEntry(175, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.graniteMinHeight), new GuiPageButtonList.GuiSlideEntry(176, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.graniteMaxHeight), new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.dioriteSize), new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.dioriteCount), new GuiPageButtonList.GuiSlideEntry(179, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.dioriteMinHeight), new GuiPageButtonList.GuiSlideEntry(180, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.dioriteMaxHeight), new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.andesiteSize), new GuiPageButtonList.GuiSlideEntry(182, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.andesiteCount), new GuiPageButtonList.GuiSlideEntry(183, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.andesiteMinHeight), new GuiPageButtonList.GuiSlideEntry(184, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.andesiteMaxHeight), new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.coalSize), new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.coalCount), new GuiPageButtonList.GuiSlideEntry(187, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.coalMinHeight), new GuiPageButtonList.GuiSlideEntry(189, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.coalMaxHeight), new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.ironSize), new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.ironCount), new GuiPageButtonList.GuiSlideEntry(192, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.ironMinHeight), new GuiPageButtonList.GuiSlideEntry(193, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.ironMaxHeight), new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.goldSize), new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.goldCount), new GuiPageButtonList.GuiSlideEntry(196, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.goldMinHeight), new GuiPageButtonList.GuiSlideEntry(197, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.goldMaxHeight), new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.redstoneSize), new GuiPageButtonList.GuiSlideEntry(199, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.redstoneCount), new GuiPageButtonList.GuiSlideEntry(200, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.redstoneMinHeight), new GuiPageButtonList.GuiSlideEntry(201, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.redstoneMaxHeight), new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.diamondSize), new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.diamondCount), new GuiPageButtonList.GuiSlideEntry(204, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.diamondMinHeight), new GuiPageButtonList.GuiSlideEntry(205, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.settings.diamondMaxHeight), new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.settings.lapisSize), new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.settings.lapisCount), new GuiPageButtonList.GuiSlideEntry(208, I18n.format("createWorld.customize.custom.center", new Object[0]), false, this, 0.0F, 255.0F, this.settings.lapisCenterHeight), new GuiPageButtonList.GuiSlideEntry(209, I18n.format("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0F, 255.0F, this.settings.lapisSpread) };
/*  117 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry2 = { new GuiPageButtonList.GuiSlideEntry(100, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0F, 5000.0F, this.settings.mainNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(101, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0F, 5000.0F, this.settings.mainNoiseScaleY), new GuiPageButtonList.GuiSlideEntry(102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0F, 5000.0F, this.settings.mainNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(103, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0F, 2000.0F, this.settings.depthNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0F, 2000.0F, this.settings.depthNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01F, 20.0F, this.settings.depthNoiseScaleExponent), new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0F, 25.0F, this.settings.baseSize), new GuiPageButtonList.GuiSlideEntry(107, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0F, 6000.0F, this.settings.coordinateScale), new GuiPageButtonList.GuiSlideEntry(108, I18n.format("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0F, 6000.0F, this.settings.heightScale), new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01F, 50.0F, this.settings.stretchY), new GuiPageButtonList.GuiSlideEntry(110, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.settings.upperLimitScale), new GuiPageButtonList.GuiSlideEntry(111, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.settings.lowerLimitScale), new GuiPageButtonList.GuiSlideEntry(112, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0F, 20.0F, this.settings.biomeDepthWeight), new GuiPageButtonList.GuiSlideEntry(113, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0F, 20.0F, this.settings.biomeDepthOffset), new GuiPageButtonList.GuiSlideEntry(114, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0F, 20.0F, this.settings.biomeScaleWeight), new GuiPageButtonList.GuiSlideEntry(115, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0F, 20.0F, this.settings.biomeScaleOffset) };
/*  118 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry3 = { new GuiPageButtonList.GuiLabelEntry(400, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.mainNoiseScaleX) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(401, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.mainNoiseScaleY) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(402, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.mainNoiseScaleZ) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(403, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.depthNoiseScaleX) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(404, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.depthNoiseScaleZ) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(405, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.depthNoiseScaleExponent) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(406, String.valueOf(I18n.format("createWorld.customize.custom.baseSize", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.baseSize) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(407, String.valueOf(I18n.format("createWorld.customize.custom.coordinateScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.coordinateScale) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(408, String.valueOf(I18n.format("createWorld.customize.custom.heightScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.heightScale) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(409, String.valueOf(I18n.format("createWorld.customize.custom.stretchY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.stretchY) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(410, String.valueOf(I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.upperLimitScale) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(411, String.valueOf(I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", new Object[] { Float.valueOf(this.settings.lowerLimitScale) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(412, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.biomeDepthWeight) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(413, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.biomeDepthOffset) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(414, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.biomeScaleWeight) }), false, this.numberFilter), new GuiPageButtonList.GuiLabelEntry(415, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", new Object[] { Float.valueOf(this.settings.biomeScaleOffset) }), false, this.numberFilter) };
/*  119 */     this.list = new GuiPageButtonList(this.mc, this.width, this.height, 32, this.height - 32, 25, this, new GuiPageButtonList.GuiListEntry[][] { aguipagebuttonlist$guilistentry, aguipagebuttonlist$guilistentry1, aguipagebuttonlist$guilistentry2, aguipagebuttonlist$guilistentry3 });
/*      */     
/*  121 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  123 */       this.pageNames[i] = I18n.format("createWorld.customize.custom.page" + i, new Object[0]);
/*      */     }
/*      */     
/*  126 */     updatePageControls();
/*      */   }
/*      */ 
/*      */   
/*      */   public String saveValues() {
/*  131 */     return this.settings.toString().replace("\n", "");
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadValues(String p_175324_1_) {
/*  136 */     if (p_175324_1_ != null && !p_175324_1_.isEmpty()) {
/*      */       
/*  138 */       this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(p_175324_1_);
/*      */     }
/*      */     else {
/*      */       
/*  142 */       this.settings = new ChunkGeneratorSettings.Factory();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntryValue(int id, String value) {
/*  148 */     float f = 0.0F;
/*      */ 
/*      */     
/*      */     try {
/*  152 */       f = Float.parseFloat(value);
/*      */     }
/*  154 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  159 */     float f1 = 0.0F;
/*      */     
/*  161 */     switch (id) {
/*      */       
/*      */       case 132:
/*  164 */         this.settings.mainNoiseScaleX = MathHelper.clamp(f, 1.0F, 5000.0F);
/*  165 */         f1 = this.settings.mainNoiseScaleX;
/*      */         break;
/*      */       
/*      */       case 133:
/*  169 */         this.settings.mainNoiseScaleY = MathHelper.clamp(f, 1.0F, 5000.0F);
/*  170 */         f1 = this.settings.mainNoiseScaleY;
/*      */         break;
/*      */       
/*      */       case 134:
/*  174 */         this.settings.mainNoiseScaleZ = MathHelper.clamp(f, 1.0F, 5000.0F);
/*  175 */         f1 = this.settings.mainNoiseScaleZ;
/*      */         break;
/*      */       
/*      */       case 135:
/*  179 */         this.settings.depthNoiseScaleX = MathHelper.clamp(f, 1.0F, 2000.0F);
/*  180 */         f1 = this.settings.depthNoiseScaleX;
/*      */         break;
/*      */       
/*      */       case 136:
/*  184 */         this.settings.depthNoiseScaleZ = MathHelper.clamp(f, 1.0F, 2000.0F);
/*  185 */         f1 = this.settings.depthNoiseScaleZ;
/*      */         break;
/*      */       
/*      */       case 137:
/*  189 */         this.settings.depthNoiseScaleExponent = MathHelper.clamp(f, 0.01F, 20.0F);
/*  190 */         f1 = this.settings.depthNoiseScaleExponent;
/*      */         break;
/*      */       
/*      */       case 138:
/*  194 */         this.settings.baseSize = MathHelper.clamp(f, 1.0F, 25.0F);
/*  195 */         f1 = this.settings.baseSize;
/*      */         break;
/*      */       
/*      */       case 139:
/*  199 */         this.settings.coordinateScale = MathHelper.clamp(f, 1.0F, 6000.0F);
/*  200 */         f1 = this.settings.coordinateScale;
/*      */         break;
/*      */       
/*      */       case 140:
/*  204 */         this.settings.heightScale = MathHelper.clamp(f, 1.0F, 6000.0F);
/*  205 */         f1 = this.settings.heightScale;
/*      */         break;
/*      */       
/*      */       case 141:
/*  209 */         this.settings.stretchY = MathHelper.clamp(f, 0.01F, 50.0F);
/*  210 */         f1 = this.settings.stretchY;
/*      */         break;
/*      */       
/*      */       case 142:
/*  214 */         this.settings.upperLimitScale = MathHelper.clamp(f, 1.0F, 5000.0F);
/*  215 */         f1 = this.settings.upperLimitScale;
/*      */         break;
/*      */       
/*      */       case 143:
/*  219 */         this.settings.lowerLimitScale = MathHelper.clamp(f, 1.0F, 5000.0F);
/*  220 */         f1 = this.settings.lowerLimitScale;
/*      */         break;
/*      */       
/*      */       case 144:
/*  224 */         this.settings.biomeDepthWeight = MathHelper.clamp(f, 1.0F, 20.0F);
/*  225 */         f1 = this.settings.biomeDepthWeight;
/*      */         break;
/*      */       
/*      */       case 145:
/*  229 */         this.settings.biomeDepthOffset = MathHelper.clamp(f, 0.0F, 20.0F);
/*  230 */         f1 = this.settings.biomeDepthOffset;
/*      */         break;
/*      */       
/*      */       case 146:
/*  234 */         this.settings.biomeScaleWeight = MathHelper.clamp(f, 1.0F, 20.0F);
/*  235 */         f1 = this.settings.biomeScaleWeight;
/*      */         break;
/*      */       
/*      */       case 147:
/*  239 */         this.settings.biomeScaleOffset = MathHelper.clamp(f, 0.0F, 20.0F);
/*  240 */         f1 = this.settings.biomeScaleOffset;
/*      */         break;
/*      */     } 
/*  243 */     if (f1 != f && f != 0.0F)
/*      */     {
/*  245 */       ((GuiTextField)this.list.getComponent(id)).setText(getFormattedValue(id, f1));
/*      */     }
/*      */     
/*  248 */     ((GuiSlider)this.list.getComponent(id - 132 + 100)).setSliderValue(f1, false);
/*      */     
/*  250 */     if (!this.settings.equals(this.defaultSettings))
/*      */     {
/*  252 */       setSettingsModified(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSettingsModified(boolean p_181031_1_) {
/*  258 */     this.settingsModified = p_181031_1_;
/*  259 */     this.defaults.enabled = p_181031_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getText(int id, String name, float value) {
/*  264 */     return String.valueOf(name) + ": " + getFormattedValue(id, value);
/*      */   }
/*      */ 
/*      */   
/*      */   private String getFormattedValue(int p_175330_1_, float p_175330_2_) {
/*  269 */     switch (p_175330_1_) {
/*      */       
/*      */       case 100:
/*      */       case 101:
/*      */       case 102:
/*      */       case 103:
/*      */       case 104:
/*      */       case 107:
/*      */       case 108:
/*      */       case 110:
/*      */       case 111:
/*      */       case 132:
/*      */       case 133:
/*      */       case 134:
/*      */       case 135:
/*      */       case 136:
/*      */       case 139:
/*      */       case 140:
/*      */       case 142:
/*      */       case 143:
/*  289 */         return String.format("%5.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*      */       
/*      */       case 105:
/*      */       case 106:
/*      */       case 109:
/*      */       case 112:
/*      */       case 113:
/*      */       case 114:
/*      */       case 115:
/*      */       case 137:
/*      */       case 138:
/*      */       case 141:
/*      */       case 144:
/*      */       case 145:
/*      */       case 146:
/*      */       case 147:
/*  305 */         return String.format("%2.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  338 */         return String.format("%d", new Object[] { Integer.valueOf((int)p_175330_2_) });
/*      */       case 162:
/*      */         break;
/*  341 */     }  if (p_175330_2_ < 0.0F)
/*      */     {
/*  343 */       return I18n.format("gui.all", new Object[0]);
/*      */     }
/*  345 */     if ((int)p_175330_2_ >= Biome.getIdForBiome(Biomes.HELL)) {
/*      */       
/*  347 */       Biome biome1 = Biome.getBiomeForId((int)p_175330_2_ + 2);
/*  348 */       return (biome1 != null) ? biome1.getBiomeName() : "?";
/*      */     } 
/*      */ 
/*      */     
/*  352 */     Biome biome = Biome.getBiomeForId((int)p_175330_2_);
/*  353 */     return (biome != null) ? biome.getBiomeName() : "?";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntryValue(int id, boolean value) {
/*  360 */     switch (id) {
/*      */       
/*      */       case 148:
/*  363 */         this.settings.useCaves = value;
/*      */         break;
/*      */       
/*      */       case 149:
/*  367 */         this.settings.useDungeons = value;
/*      */         break;
/*      */       
/*      */       case 150:
/*  371 */         this.settings.useStrongholds = value;
/*      */         break;
/*      */       
/*      */       case 151:
/*  375 */         this.settings.useVillages = value;
/*      */         break;
/*      */       
/*      */       case 152:
/*  379 */         this.settings.useMineShafts = value;
/*      */         break;
/*      */       
/*      */       case 153:
/*  383 */         this.settings.useTemples = value;
/*      */         break;
/*      */       
/*      */       case 154:
/*  387 */         this.settings.useRavines = value;
/*      */         break;
/*      */       
/*      */       case 155:
/*  391 */         this.settings.useWaterLakes = value;
/*      */         break;
/*      */       
/*      */       case 156:
/*  395 */         this.settings.useLavaLakes = value;
/*      */         break;
/*      */       
/*      */       case 161:
/*  399 */         this.settings.useLavaOceans = value;
/*      */         break;
/*      */       
/*      */       case 210:
/*  403 */         this.settings.useMonuments = value;
/*      */         break;
/*      */       
/*      */       case 211:
/*  407 */         this.settings.field_191076_A = value;
/*      */         break;
/*      */     } 
/*  410 */     if (!this.settings.equals(this.defaultSettings))
/*      */     {
/*  412 */       setSettingsModified(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntryValue(int id, float value) {
/*  418 */     switch (id) {
/*      */       
/*      */       case 100:
/*  421 */         this.settings.mainNoiseScaleX = value;
/*      */         break;
/*      */       
/*      */       case 101:
/*  425 */         this.settings.mainNoiseScaleY = value;
/*      */         break;
/*      */       
/*      */       case 102:
/*  429 */         this.settings.mainNoiseScaleZ = value;
/*      */         break;
/*      */       
/*      */       case 103:
/*  433 */         this.settings.depthNoiseScaleX = value;
/*      */         break;
/*      */       
/*      */       case 104:
/*  437 */         this.settings.depthNoiseScaleZ = value;
/*      */         break;
/*      */       
/*      */       case 105:
/*  441 */         this.settings.depthNoiseScaleExponent = value;
/*      */         break;
/*      */       
/*      */       case 106:
/*  445 */         this.settings.baseSize = value;
/*      */         break;
/*      */       
/*      */       case 107:
/*  449 */         this.settings.coordinateScale = value;
/*      */         break;
/*      */       
/*      */       case 108:
/*  453 */         this.settings.heightScale = value;
/*      */         break;
/*      */       
/*      */       case 109:
/*  457 */         this.settings.stretchY = value;
/*      */         break;
/*      */       
/*      */       case 110:
/*  461 */         this.settings.upperLimitScale = value;
/*      */         break;
/*      */       
/*      */       case 111:
/*  465 */         this.settings.lowerLimitScale = value;
/*      */         break;
/*      */       
/*      */       case 112:
/*  469 */         this.settings.biomeDepthWeight = value;
/*      */         break;
/*      */       
/*      */       case 113:
/*  473 */         this.settings.biomeDepthOffset = value;
/*      */         break;
/*      */       
/*      */       case 114:
/*  477 */         this.settings.biomeScaleWeight = value;
/*      */         break;
/*      */       
/*      */       case 115:
/*  481 */         this.settings.biomeScaleOffset = value;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 157:
/*  530 */         this.settings.dungeonChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 158:
/*  534 */         this.settings.waterLakeChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 159:
/*  538 */         this.settings.lavaLakeChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 160:
/*  542 */         this.settings.seaLevel = (int)value;
/*      */         break;
/*      */       
/*      */       case 162:
/*  546 */         this.settings.fixedBiome = (int)value;
/*      */         break;
/*      */       
/*      */       case 163:
/*  550 */         this.settings.biomeSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 164:
/*  554 */         this.settings.riverSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 165:
/*  558 */         this.settings.dirtSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 166:
/*  562 */         this.settings.dirtCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 167:
/*  566 */         this.settings.dirtMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 168:
/*  570 */         this.settings.dirtMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 169:
/*  574 */         this.settings.gravelSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 170:
/*  578 */         this.settings.gravelCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 171:
/*  582 */         this.settings.gravelMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 172:
/*  586 */         this.settings.gravelMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 173:
/*  590 */         this.settings.graniteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 174:
/*  594 */         this.settings.graniteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 175:
/*  598 */         this.settings.graniteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 176:
/*  602 */         this.settings.graniteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 177:
/*  606 */         this.settings.dioriteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 178:
/*  610 */         this.settings.dioriteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 179:
/*  614 */         this.settings.dioriteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 180:
/*  618 */         this.settings.dioriteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 181:
/*  622 */         this.settings.andesiteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 182:
/*  626 */         this.settings.andesiteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 183:
/*  630 */         this.settings.andesiteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 184:
/*  634 */         this.settings.andesiteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 185:
/*  638 */         this.settings.coalSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 186:
/*  642 */         this.settings.coalCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 187:
/*  646 */         this.settings.coalMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 189:
/*  650 */         this.settings.coalMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 190:
/*  654 */         this.settings.ironSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 191:
/*  658 */         this.settings.ironCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 192:
/*  662 */         this.settings.ironMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 193:
/*  666 */         this.settings.ironMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 194:
/*  670 */         this.settings.goldSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 195:
/*  674 */         this.settings.goldCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 196:
/*  678 */         this.settings.goldMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 197:
/*  682 */         this.settings.goldMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 198:
/*  686 */         this.settings.redstoneSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 199:
/*  690 */         this.settings.redstoneCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 200:
/*  694 */         this.settings.redstoneMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 201:
/*  698 */         this.settings.redstoneMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 202:
/*  702 */         this.settings.diamondSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 203:
/*  706 */         this.settings.diamondCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 204:
/*  710 */         this.settings.diamondMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 205:
/*  714 */         this.settings.diamondMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 206:
/*  718 */         this.settings.lapisSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 207:
/*  722 */         this.settings.lapisCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 208:
/*  726 */         this.settings.lapisCenterHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 209:
/*  730 */         this.settings.lapisSpread = (int)value;
/*      */         break;
/*      */     } 
/*  733 */     if (id >= 100 && id < 116) {
/*      */       
/*  735 */       Gui gui = this.list.getComponent(id - 100 + 132);
/*      */       
/*  737 */       if (gui != null)
/*      */       {
/*  739 */         ((GuiTextField)gui).setText(getFormattedValue(id, value));
/*      */       }
/*      */     } 
/*      */     
/*  743 */     if (!this.settings.equals(this.defaultSettings))
/*      */     {
/*  745 */       setSettingsModified(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void actionPerformed(GuiButton button) throws IOException {
/*  754 */     if (button.enabled) {
/*      */       int i;
/*  756 */       switch (button.id) {
/*      */         
/*      */         case 300:
/*  759 */           this.parent.chunkProviderSettingsJson = this.settings.toString();
/*  760 */           this.mc.displayGuiScreen(this.parent);
/*      */           break;
/*      */         
/*      */         case 301:
/*  764 */           for (i = 0; i < this.list.getSize(); i++) {
/*      */             
/*  766 */             GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.list.getListEntry(i);
/*  767 */             Gui gui = guipagebuttonlist$guientry.getComponent1();
/*      */             
/*  769 */             if (gui instanceof GuiButton) {
/*      */               
/*  771 */               GuiButton guibutton = (GuiButton)gui;
/*      */               
/*  773 */               if (guibutton instanceof GuiSlider) {
/*      */                 
/*  775 */                 float f = ((GuiSlider)guibutton).getSliderPosition() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/*  776 */                 ((GuiSlider)guibutton).setSliderPosition(MathHelper.clamp(f, 0.0F, 1.0F));
/*      */               }
/*  778 */               else if (guibutton instanceof GuiListButton) {
/*      */                 
/*  780 */                 ((GuiListButton)guibutton).setValue(this.random.nextBoolean());
/*      */               } 
/*      */             } 
/*      */             
/*  784 */             Gui gui1 = guipagebuttonlist$guientry.getComponent2();
/*      */             
/*  786 */             if (gui1 instanceof GuiButton) {
/*      */               
/*  788 */               GuiButton guibutton1 = (GuiButton)gui1;
/*      */               
/*  790 */               if (guibutton1 instanceof GuiSlider) {
/*      */                 
/*  792 */                 float f1 = ((GuiSlider)guibutton1).getSliderPosition() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/*  793 */                 ((GuiSlider)guibutton1).setSliderPosition(MathHelper.clamp(f1, 0.0F, 1.0F));
/*      */               }
/*  795 */               else if (guibutton1 instanceof GuiListButton) {
/*      */                 
/*  797 */                 ((GuiListButton)guibutton1).setValue(this.random.nextBoolean());
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           return;
/*      */ 
/*      */         
/*      */         case 302:
/*  805 */           this.list.previousPage();
/*  806 */           updatePageControls();
/*      */           break;
/*      */         
/*      */         case 303:
/*  810 */           this.list.nextPage();
/*  811 */           updatePageControls();
/*      */           break;
/*      */         
/*      */         case 304:
/*  815 */           if (this.settingsModified)
/*      */           {
/*  817 */             enterConfirmation(304);
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case 305:
/*  823 */           this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
/*      */           break;
/*      */         
/*      */         case 306:
/*  827 */           exitConfirmation();
/*      */           break;
/*      */         
/*      */         case 307:
/*  831 */           this.confirmMode = 0;
/*  832 */           exitConfirmation();
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void restoreDefaults() {
/*  839 */     this.settings.setDefaults();
/*  840 */     createPagedList();
/*  841 */     setSettingsModified(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void enterConfirmation(int p_175322_1_) {
/*  846 */     this.confirmMode = p_175322_1_;
/*  847 */     setConfirmationControls(true);
/*      */   }
/*      */ 
/*      */   
/*      */   private void exitConfirmation() throws IOException {
/*  852 */     switch (this.confirmMode) {
/*      */       
/*      */       case 300:
/*  855 */         actionPerformed((GuiListButton)this.list.getComponent(300));
/*      */         break;
/*      */       
/*      */       case 304:
/*  859 */         restoreDefaults();
/*      */         break;
/*      */     } 
/*  862 */     this.confirmMode = 0;
/*  863 */     this.confirmDismissed = true;
/*  864 */     setConfirmationControls(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setConfirmationControls(boolean p_175329_1_) {
/*  869 */     this.confirm.visible = p_175329_1_;
/*  870 */     this.cancel.visible = p_175329_1_;
/*  871 */     this.randomize.enabled = !p_175329_1_;
/*  872 */     this.done.enabled = !p_175329_1_;
/*  873 */     this.previousPage.enabled = !p_175329_1_;
/*  874 */     this.nextPage.enabled = !p_175329_1_;
/*  875 */     this.defaults.enabled = (this.settingsModified && !p_175329_1_);
/*  876 */     this.presets.enabled = !p_175329_1_;
/*  877 */     this.list.setActive(!p_175329_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updatePageControls() {
/*  882 */     this.previousPage.enabled = (this.list.getPage() != 0);
/*  883 */     this.nextPage.enabled = (this.list.getPage() != this.list.getPageCount() - 1);
/*  884 */     this.subtitle = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.list.getPage() + 1), Integer.valueOf(this.list.getPageCount()) });
/*  885 */     this.pageTitle = this.pageNames[this.list.getPage()];
/*  886 */     this.randomize.enabled = (this.list.getPage() != this.list.getPageCount() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  895 */     super.keyTyped(typedChar, keyCode);
/*      */     
/*  897 */     if (this.confirmMode == 0) {
/*      */       
/*  899 */       switch (keyCode) {
/*      */         
/*      */         case 200:
/*  902 */           modifyFocusValue(1.0F);
/*      */           return;
/*      */         
/*      */         case 208:
/*  906 */           modifyFocusValue(-1.0F);
/*      */           return;
/*      */       } 
/*      */       
/*  910 */       this.list.onKeyPressed(typedChar, keyCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void modifyFocusValue(float p_175327_1_) {
/*  917 */     Gui gui = this.list.getFocusedControl();
/*      */     
/*  919 */     if (gui instanceof GuiTextField) {
/*      */       
/*  921 */       float f = p_175327_1_;
/*      */       
/*  923 */       if (GuiScreen.isShiftKeyDown()) {
/*      */         
/*  925 */         f = p_175327_1_ * 0.1F;
/*      */         
/*  927 */         if (GuiScreen.isCtrlKeyDown())
/*      */         {
/*  929 */           f *= 0.1F;
/*      */         }
/*      */       }
/*  932 */       else if (GuiScreen.isCtrlKeyDown()) {
/*      */         
/*  934 */         f = p_175327_1_ * 10.0F;
/*      */         
/*  936 */         if (GuiScreen.isAltKeyDown())
/*      */         {
/*  938 */           f *= 10.0F;
/*      */         }
/*      */       } 
/*      */       
/*  942 */       GuiTextField guitextfield = (GuiTextField)gui;
/*  943 */       Float f1 = Floats.tryParse(guitextfield.getText());
/*      */       
/*  945 */       if (f1 != null) {
/*      */         
/*  947 */         f1 = Float.valueOf(f1.floatValue() + f);
/*  948 */         int i = guitextfield.getId();
/*  949 */         String s = getFormattedValue(guitextfield.getId(), f1.floatValue());
/*  950 */         guitextfield.setText(s);
/*  951 */         setEntryValue(i, s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  961 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*      */     
/*  963 */     if (this.confirmMode == 0 && !this.confirmDismissed)
/*      */     {
/*  965 */       this.list.mouseClicked(mouseX, mouseY, mouseButton);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*  974 */     super.mouseReleased(mouseX, mouseY, state);
/*      */     
/*  976 */     if (this.confirmDismissed) {
/*      */       
/*  978 */       this.confirmDismissed = false;
/*      */     }
/*  980 */     else if (this.confirmMode == 0) {
/*      */       
/*  982 */       this.list.mouseReleased(mouseX, mouseY, state);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  991 */     drawDefaultBackground();
/*  992 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*  993 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 2, 16777215);
/*  994 */     drawCenteredString(this.fontRendererObj, this.subtitle, this.width / 2, 12, 16777215);
/*  995 */     drawCenteredString(this.fontRendererObj, this.pageTitle, this.width / 2, 22, 16777215);
/*  996 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*      */     
/*  998 */     if (this.confirmMode != 0) {
/*      */       
/* 1000 */       drawRect(0, 0, this.width, this.height, -2147483648);
/* 1001 */       drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 99, -2039584);
/* 1002 */       drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 185, -6250336);
/* 1003 */       drawVerticalLine(this.width / 2 - 91, 99, 185, -2039584);
/* 1004 */       drawVerticalLine(this.width / 2 + 90, 99, 185, -6250336);
/* 1005 */       float f = 85.0F;
/* 1006 */       float f1 = 180.0F;
/* 1007 */       GlStateManager.disableLighting();
/* 1008 */       GlStateManager.disableFog();
/* 1009 */       Tessellator tessellator = Tessellator.getInstance();
/* 1010 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1011 */       this.mc.getTextureManager().bindTexture(OPTIONS_BACKGROUND);
/* 1012 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1013 */       float f2 = 32.0F;
/* 1014 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1015 */       bufferbuilder.pos((this.width / 2 - 90), 185.0D, 0.0D).tex(0.0D, 2.65625D).color(64, 64, 64, 64).endVertex();
/* 1016 */       bufferbuilder.pos((this.width / 2 + 90), 185.0D, 0.0D).tex(5.625D, 2.65625D).color(64, 64, 64, 64).endVertex();
/* 1017 */       bufferbuilder.pos((this.width / 2 + 90), 100.0D, 0.0D).tex(5.625D, 0.0D).color(64, 64, 64, 64).endVertex();
/* 1018 */       bufferbuilder.pos((this.width / 2 - 90), 100.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 64).endVertex();
/* 1019 */       tessellator.draw();
/* 1020 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]), this.width / 2, 105, 16777215);
/* 1021 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1", new Object[0]), this.width / 2, 125, 16777215);
/* 1022 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2", new Object[0]), this.width / 2, 135, 16777215);
/* 1023 */       this.confirm.func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/* 1024 */       this.cancel.func_191745_a(this.mc, mouseX, mouseY, partialTicks);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiCustomizeWorldScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
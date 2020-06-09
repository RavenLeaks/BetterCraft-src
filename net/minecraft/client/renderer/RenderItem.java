/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockHugeMushroom;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockQuartz;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.BlockStoneSlabNew;
/*      */ import net.minecraft.block.BlockTallGrass;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*      */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*      */ import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
/*      */ import net.minecraft.client.renderer.block.model.ModelManager;
/*      */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*      */ import net.minecraft.client.renderer.color.ItemColors;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemFishFood;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.tileentity.TileEntityStructure;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.world.World;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.CustomItems;
/*      */ import optifine.Reflector;
/*      */ import optifine.ReflectorForge;
/*      */ import shadersmod.client.Shaders;
/*      */ import shadersmod.client.ShadersRender;
/*      */ 
/*      */ public class RenderItem
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*   69 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*      */   
/*      */   private boolean notRenderingEffectsInGUI = true;
/*      */   
/*      */   public float zLevel;
/*      */   
/*      */   private final ItemModelMesher itemModelMesher;
/*      */   
/*      */   private final TextureManager textureManager;
/*      */   private final ItemColors itemColors;
/*   79 */   private ResourceLocation modelLocation = null;
/*      */   private boolean renderItemGui = false;
/*   81 */   public ModelManager modelManager = null;
/*      */ 
/*      */   
/*      */   public RenderItem(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_) {
/*   85 */     this.textureManager = p_i46552_1_;
/*   86 */     this.modelManager = p_i46552_2_;
/*      */     
/*   88 */     if (Reflector.ItemModelMesherForge_Constructor.exists()) {
/*      */       
/*   90 */       this.itemModelMesher = (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, new Object[] { p_i46552_2_ });
/*      */     }
/*      */     else {
/*      */       
/*   94 */       this.itemModelMesher = new ItemModelMesher(p_i46552_2_);
/*      */     } 
/*      */     
/*   97 */     registerItems();
/*   98 */     this.itemColors = p_i46552_3_;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemModelMesher getItemModelMesher() {
/*  103 */     return this.itemModelMesher;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerItem(Item itm, int subType, String identifier) {
/*  108 */     this.itemModelMesher.register(itm, subType, new ModelResourceLocation(identifier, "inventory"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerBlock(Block blk, int subType, String identifier) {
/*  113 */     registerItem(Item.getItemFromBlock(blk), subType, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerBlock(Block blk, String identifier) {
/*  118 */     registerBlock(blk, 0, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerItem(Item itm, String identifier) {
/*  123 */     registerItem(itm, 0, identifier);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191961_a(IBakedModel p_191961_1_, ItemStack p_191961_2_) {
/*  128 */     func_191967_a(p_191961_1_, -1, p_191961_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191965_a(IBakedModel p_191965_1_, int p_191965_2_) {
/*  133 */     func_191967_a(p_191965_1_, p_191965_2_, ItemStack.field_190927_a);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191967_a(IBakedModel p_191967_1_, int p_191967_2_, ItemStack p_191967_3_) {
/*  138 */     Tessellator tessellator = Tessellator.getInstance();
/*  139 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  140 */     boolean flag = Minecraft.getMinecraft().getTextureMapBlocks().isTextureBound();
/*  141 */     boolean flag1 = (Config.isMultiTexture() && flag);
/*      */     
/*  143 */     if (flag1)
/*      */     {
/*  145 */       bufferbuilder.setBlockLayer(BlockRenderLayer.SOLID);
/*      */     }
/*      */     
/*  148 */     bufferbuilder.begin(7, DefaultVertexFormats.ITEM); byte b; int i;
/*      */     EnumFacing[] arrayOfEnumFacing;
/*  150 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */       
/*  152 */       func_191970_a(bufferbuilder, p_191967_1_.getQuads(null, enumfacing, 0L), p_191967_2_, p_191967_3_);
/*      */       b++; }
/*      */     
/*  155 */     func_191970_a(bufferbuilder, p_191967_1_.getQuads(null, null, 0L), p_191967_2_, p_191967_3_);
/*  156 */     tessellator.draw();
/*      */     
/*  158 */     if (flag1) {
/*      */       
/*  160 */       bufferbuilder.setBlockLayer(null);
/*  161 */       GlStateManager.bindCurrentTexture();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItem(ItemStack stack, IBakedModel model) {
/*  167 */     if (!stack.func_190926_b()) {
/*      */       
/*  169 */       GlStateManager.pushMatrix();
/*  170 */       GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*      */       
/*  172 */       if (model.isBuiltInRenderer()) {
/*      */         
/*  174 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  175 */         GlStateManager.enableRescaleNormal();
/*  176 */         TileEntityItemStackRenderer.instance.renderByItem(stack);
/*      */       }
/*      */       else {
/*      */         
/*  180 */         if (Config.isCustomItems()) {
/*      */           
/*  182 */           model = CustomItems.getCustomItemModel(stack, model, this.modelLocation, false);
/*  183 */           this.modelLocation = null;
/*      */         } 
/*      */         
/*  186 */         func_191961_a(model, stack);
/*      */         
/*  188 */         if (stack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomEffect(this, stack, model)))
/*      */         {
/*  190 */           func_191966_a(model);
/*      */         }
/*      */       } 
/*      */       
/*  194 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191966_a(IBakedModel p_191966_1_) {
/*  200 */     if (!Config.isCustomItems() || CustomItems.isUseGlint())
/*      */     {
/*  202 */       if (!Config.isShaders() || !Shaders.isShadowPass) {
/*      */         
/*  204 */         GlStateManager.depthMask(false);
/*  205 */         GlStateManager.depthFunc(514);
/*  206 */         GlStateManager.disableLighting();
/*  207 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
/*  208 */         this.textureManager.bindTexture(RES_ITEM_GLINT);
/*      */         
/*  210 */         if (Config.isShaders() && !this.renderItemGui)
/*      */         {
/*  212 */           ShadersRender.renderEnchantedGlintBegin();
/*      */         }
/*      */         
/*  215 */         GlStateManager.matrixMode(5890);
/*  216 */         GlStateManager.pushMatrix();
/*  217 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  218 */         float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  219 */         GlStateManager.translate(f, 0.0F, 0.0F);
/*  220 */         GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
/*  221 */         func_191965_a(p_191966_1_, -8372020);
/*  222 */         GlStateManager.popMatrix();
/*  223 */         GlStateManager.pushMatrix();
/*  224 */         GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  225 */         float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
/*  226 */         GlStateManager.translate(-f1, 0.0F, 0.0F);
/*  227 */         GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
/*  228 */         func_191965_a(p_191966_1_, -8372020);
/*  229 */         GlStateManager.popMatrix();
/*  230 */         GlStateManager.matrixMode(5888);
/*  231 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  232 */         GlStateManager.enableLighting();
/*  233 */         GlStateManager.depthFunc(515);
/*  234 */         GlStateManager.depthMask(true);
/*  235 */         this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*      */         
/*  237 */         if (Config.isShaders() && !this.renderItemGui)
/*      */         {
/*  239 */           ShadersRender.renderEnchantedGlintEnd();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void putQuadNormal(BufferBuilder renderer, BakedQuad quad) {
/*  247 */     Vec3i vec3i = quad.getFace().getDirectionVec();
/*  248 */     renderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191969_a(BufferBuilder p_191969_1_, BakedQuad p_191969_2_, int p_191969_3_) {
/*  253 */     if (p_191969_1_.isMultiTexture()) {
/*      */       
/*  255 */       p_191969_1_.addVertexData(p_191969_2_.getVertexDataSingle());
/*  256 */       p_191969_1_.putSprite(p_191969_2_.getSprite());
/*      */     }
/*      */     else {
/*      */       
/*  260 */       p_191969_1_.addVertexData(p_191969_2_.getVertexData());
/*      */     } 
/*      */     
/*  263 */     if (Reflector.ForgeHooksClient_putQuadColor.exists()) {
/*      */       
/*  265 */       Reflector.call(Reflector.ForgeHooksClient_putQuadColor, new Object[] { p_191969_1_, p_191969_2_, Integer.valueOf(p_191969_3_) });
/*      */     }
/*      */     else {
/*      */       
/*  269 */       p_191969_1_.putColor4(p_191969_3_);
/*      */     } 
/*      */     
/*  272 */     putQuadNormal(p_191969_1_, p_191969_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191970_a(BufferBuilder p_191970_1_, List<BakedQuad> p_191970_2_, int p_191970_3_, ItemStack p_191970_4_) {
/*  277 */     boolean flag = (p_191970_3_ == -1 && !p_191970_4_.func_190926_b());
/*  278 */     int i = 0;
/*      */     
/*  280 */     for (int j = p_191970_2_.size(); i < j; i++) {
/*      */       
/*  282 */       BakedQuad bakedquad = p_191970_2_.get(i);
/*  283 */       int k = p_191970_3_;
/*      */       
/*  285 */       if (flag && bakedquad.hasTintIndex()) {
/*      */         
/*  287 */         k = this.itemColors.getColorFromItemstack(p_191970_4_, bakedquad.getTintIndex());
/*      */         
/*  289 */         if (Config.isCustomColors())
/*      */         {
/*  291 */           k = CustomColors.getColorFromItemStack(p_191970_4_, bakedquad.getTintIndex(), k);
/*      */         }
/*      */         
/*  294 */         if (EntityRenderer.anaglyphEnable)
/*      */         {
/*  296 */           k = TextureUtil.anaglyphColor(k);
/*      */         }
/*      */         
/*  299 */         k |= 0xFF000000;
/*      */       } 
/*      */       
/*  302 */       func_191969_a(p_191970_1_, bakedquad, k);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldRenderItemIn3D(ItemStack stack) {
/*  308 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  309 */     return (ibakedmodel == null) ? false : ibakedmodel.isGui3d();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType) {
/*  314 */     if (!stack.func_190926_b()) {
/*      */       
/*  316 */       IBakedModel ibakedmodel = getItemModelWithOverrides(stack, null, null);
/*  317 */       renderItemModel(stack, ibakedmodel, cameraTransformType, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public IBakedModel getItemModelWithOverrides(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entitylivingbaseIn) {
/*  323 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  324 */     Item item = stack.getItem();
/*      */     
/*  326 */     if (Config.isCustomItems()) {
/*      */       
/*  328 */       if (item != null && item.hasCustomProperties())
/*      */       {
/*  330 */         this.modelLocation = ibakedmodel.getOverrides().applyOverride(stack, worldIn, entitylivingbaseIn);
/*      */       }
/*      */       
/*  333 */       IBakedModel ibakedmodel1 = CustomItems.getCustomItemModel(stack, ibakedmodel, this.modelLocation, true);
/*      */       
/*  335 */       if (ibakedmodel1 != ibakedmodel)
/*      */       {
/*  337 */         return ibakedmodel1;
/*      */       }
/*      */     } 
/*      */     
/*  341 */     if (Reflector.ForgeItemOverrideList_handleItemState.exists())
/*      */     {
/*  343 */       return (IBakedModel)Reflector.call(ibakedmodel.getOverrides(), Reflector.ForgeItemOverrideList_handleItemState, new Object[] { ibakedmodel, stack, worldIn, entitylivingbaseIn });
/*      */     }
/*  345 */     if (item != null && item.hasCustomProperties()) {
/*      */       
/*  347 */       ResourceLocation resourcelocation = ibakedmodel.getOverrides().applyOverride(stack, worldIn, entitylivingbaseIn);
/*  348 */       return (resourcelocation == null) ? ibakedmodel : this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation(resourcelocation, "inventory"));
/*      */     } 
/*      */ 
/*      */     
/*  352 */     return ibakedmodel;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItem(ItemStack stack, EntityLivingBase entitylivingbaseIn, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
/*  358 */     if (!stack.func_190926_b() && entitylivingbaseIn != null) {
/*      */       
/*  360 */       IBakedModel ibakedmodel = getItemModelWithOverrides(stack, entitylivingbaseIn.world, entitylivingbaseIn);
/*  361 */       renderItemModel(stack, ibakedmodel, transform, leftHanded);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderItemModel(ItemStack stack, IBakedModel bakedmodel, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
/*  367 */     if (!stack.func_190926_b()) {
/*      */       
/*  369 */       this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  370 */       this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/*  371 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  372 */       GlStateManager.enableRescaleNormal();
/*  373 */       GlStateManager.alphaFunc(516, 0.1F);
/*  374 */       GlStateManager.enableBlend();
/*  375 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  376 */       GlStateManager.pushMatrix();
/*      */       
/*  378 */       if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*      */         
/*  380 */         bakedmodel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { bakedmodel, transform, Boolean.valueOf(leftHanded) });
/*      */       }
/*      */       else {
/*      */         
/*  384 */         ItemCameraTransforms itemcameratransforms = bakedmodel.getItemCameraTransforms();
/*  385 */         ItemCameraTransforms.applyTransformSide(itemcameratransforms.getTransform(transform), leftHanded);
/*      */         
/*  387 */         if (isThereOneNegativeScale(itemcameratransforms.getTransform(transform)))
/*      */         {
/*  389 */           GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
/*      */         }
/*      */       } 
/*      */       
/*  393 */       CustomItems.setRenderOffHand(leftHanded);
/*  394 */       renderItem(stack, bakedmodel);
/*  395 */       CustomItems.setRenderOffHand(false);
/*  396 */       GlStateManager.cullFace(GlStateManager.CullFace.BACK);
/*  397 */       GlStateManager.popMatrix();
/*  398 */       GlStateManager.disableRescaleNormal();
/*  399 */       GlStateManager.disableBlend();
/*  400 */       this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  401 */       this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isThereOneNegativeScale(ItemTransformVec3f itemTranformVec) {
/*  410 */     return ((itemTranformVec.scale.x < 0.0F)) ^ ((itemTranformVec.scale.y < 0.0F)) ^ ((itemTranformVec.scale.z < 0.0F) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemIntoGUI(ItemStack stack, int x, int y) {
/*  415 */     func_191962_a(stack, x, y, getItemModelWithOverrides(stack, null, null));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_191962_a(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_, IBakedModel p_191962_4_) {
/*  420 */     this.renderItemGui = true;
/*  421 */     GlStateManager.pushMatrix();
/*  422 */     this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  423 */     this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/*  424 */     GlStateManager.enableRescaleNormal();
/*  425 */     GlStateManager.enableAlpha();
/*  426 */     GlStateManager.alphaFunc(516, 0.1F);
/*  427 */     GlStateManager.enableBlend();
/*  428 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  429 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  430 */     setupGuiTransform(p_191962_2_, p_191962_3_, p_191962_4_.isGui3d());
/*      */     
/*  432 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*      */       
/*  434 */       p_191962_4_ = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { p_191962_4_, ItemCameraTransforms.TransformType.GUI, Boolean.valueOf(false) });
/*      */     }
/*      */     else {
/*      */       
/*  438 */       p_191962_4_.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
/*      */     } 
/*      */     
/*  441 */     renderItem(p_191962_1_, p_191962_4_);
/*  442 */     GlStateManager.disableAlpha();
/*  443 */     GlStateManager.disableRescaleNormal();
/*  444 */     GlStateManager.disableLighting();
/*  445 */     GlStateManager.popMatrix();
/*  446 */     this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  447 */     this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/*  448 */     this.renderItemGui = false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d) {
/*  453 */     GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
/*  454 */     GlStateManager.translate(8.0F, 8.0F, 0.0F);
/*  455 */     GlStateManager.scale(1.0F, -1.0F, 1.0F);
/*  456 */     GlStateManager.scale(16.0F, 16.0F, 16.0F);
/*      */     
/*  458 */     if (isGui3d) {
/*      */       
/*  460 */       GlStateManager.enableLighting();
/*      */     }
/*      */     else {
/*      */       
/*  464 */       GlStateManager.disableLighting();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemAndEffectIntoGUI(ItemStack stack, int xPosition, int yPosition) {
/*  470 */     renderItemAndEffectIntoGUI((EntityLivingBase)(Minecraft.getMinecraft()).player, stack, xPosition, yPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemAndEffectIntoGUI(@Nullable EntityLivingBase p_184391_1_, final ItemStack p_184391_2_, int p_184391_3_, int p_184391_4_) {
/*  475 */     if (!p_184391_2_.func_190926_b()) {
/*      */       
/*  477 */       this.zLevel += 50.0F;
/*      */ 
/*      */       
/*      */       try {
/*  481 */         func_191962_a(p_184391_2_, p_184391_3_, p_184391_4_, getItemModelWithOverrides(p_184391_2_, null, p_184391_1_));
/*      */       }
/*  483 */       catch (Throwable throwable) {
/*      */         
/*  485 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
/*  486 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
/*  487 */         crashreportcategory.setDetail("Item Type", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  491 */                 return String.valueOf(p_184391_2_.getItem());
/*      */               }
/*      */             });
/*  494 */         crashreportcategory.setDetail("Item Aux", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  498 */                 return String.valueOf(p_184391_2_.getMetadata());
/*      */               }
/*      */             });
/*  501 */         crashreportcategory.setDetail("Item NBT", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  505 */                 return String.valueOf(p_184391_2_.getTagCompound());
/*      */               }
/*      */             });
/*  508 */         crashreportcategory.setDetail("Item Foil", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  512 */                 return String.valueOf(p_184391_2_.hasEffect());
/*      */               }
/*      */             });
/*  515 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  518 */       this.zLevel -= 50.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition) {
/*  524 */     renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text) {
/*  532 */     if (!stack.func_190926_b()) {
/*      */       
/*  534 */       if (stack.func_190916_E() != 1 || text != null) {
/*      */         
/*  536 */         String s = (text == null) ? String.valueOf(stack.func_190916_E()) : text;
/*  537 */         GlStateManager.disableLighting();
/*  538 */         GlStateManager.disableDepth();
/*  539 */         GlStateManager.disableBlend();
/*  540 */         fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
/*  541 */         GlStateManager.enableLighting();
/*  542 */         GlStateManager.enableDepth();
/*  543 */         GlStateManager.enableBlend();
/*      */       } 
/*      */       
/*  546 */       if (ReflectorForge.isItemDamaged(stack)) {
/*      */         
/*  548 */         GlStateManager.disableLighting();
/*  549 */         GlStateManager.disableDepth();
/*  550 */         GlStateManager.disableTexture2D();
/*  551 */         GlStateManager.disableAlpha();
/*  552 */         GlStateManager.disableBlend();
/*  553 */         Tessellator tessellator = Tessellator.getInstance();
/*  554 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  555 */         float f = stack.getItemDamage();
/*  556 */         float f1 = stack.getMaxDamage();
/*  557 */         float f2 = Math.max(0.0F, (f1 - f) / f1);
/*  558 */         int i = Math.round(13.0F - f * 13.0F / f1);
/*  559 */         int j = MathHelper.hsvToRGB(f2 / 3.0F, 1.0F, 1.0F);
/*      */         
/*  561 */         if (Reflector.ForgeItem_getDurabilityForDisplay.exists() && Reflector.ForgeItem_getRGBDurabilityForDisplay.exists()) {
/*      */           
/*  563 */           double d0 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack });
/*  564 */           int k = Reflector.callInt(stack.getItem(), Reflector.ForgeItem_getRGBDurabilityForDisplay, new Object[] { stack });
/*  565 */           i = Math.round(13.0F - (float)d0 * 13.0F);
/*  566 */           j = k;
/*      */         } 
/*      */         
/*  569 */         if (Config.isCustomColors())
/*      */         {
/*  571 */           j = CustomColors.getDurabilityColor(f2, j);
/*      */         }
/*      */         
/*  574 */         if (Reflector.ForgeItem_getDurabilityForDisplay.exists() && Reflector.ForgeItem_getRGBDurabilityForDisplay.exists()) {
/*      */           
/*  576 */           double d1 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack });
/*  577 */           int l = Reflector.callInt(stack.getItem(), Reflector.ForgeItem_getRGBDurabilityForDisplay, new Object[] { stack });
/*  578 */           i = Math.round(13.0F - (float)d1 * 13.0F);
/*  579 */           j = l;
/*      */         } 
/*      */         
/*  582 */         if (Config.isCustomColors())
/*      */         {
/*  584 */           j = CustomColors.getDurabilityColor(f2, j);
/*      */         }
/*      */         
/*  587 */         draw(bufferbuilder, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
/*  588 */         draw(bufferbuilder, xPosition + 2, yPosition + 13, i, 1, j >> 16 & 0xFF, j >> 8 & 0xFF, j & 0xFF, 255);
/*  589 */         GlStateManager.enableBlend();
/*  590 */         GlStateManager.enableAlpha();
/*  591 */         GlStateManager.enableTexture2D();
/*  592 */         GlStateManager.enableLighting();
/*  593 */         GlStateManager.enableDepth();
/*      */       } 
/*      */       
/*  596 */       EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).player;
/*  597 */       float f3 = (entityplayersp == null) ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());
/*      */       
/*  599 */       if (f3 > 0.0F) {
/*      */         
/*  601 */         GlStateManager.disableLighting();
/*  602 */         GlStateManager.disableDepth();
/*  603 */         GlStateManager.disableTexture2D();
/*  604 */         Tessellator tessellator1 = Tessellator.getInstance();
/*  605 */         BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
/*  606 */         draw(bufferbuilder1, xPosition, yPosition + MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 255, 255, 127);
/*  607 */         GlStateManager.enableTexture2D();
/*  608 */         GlStateManager.enableLighting();
/*  609 */         GlStateManager.enableDepth();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
/*  619 */     renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  620 */     renderer.pos((x + 0), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
/*  621 */     renderer.pos((x + 0), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  622 */     renderer.pos((x + width), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  623 */     renderer.pos((x + width), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
/*  624 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerItems() {
/*  629 */     registerBlock(Blocks.ANVIL, "anvil_intact");
/*  630 */     registerBlock(Blocks.ANVIL, 1, "anvil_slightly_damaged");
/*  631 */     registerBlock(Blocks.ANVIL, 2, "anvil_very_damaged");
/*  632 */     registerBlock(Blocks.CARPET, EnumDyeColor.BLACK.getMetadata(), "black_carpet");
/*  633 */     registerBlock(Blocks.CARPET, EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
/*  634 */     registerBlock(Blocks.CARPET, EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
/*  635 */     registerBlock(Blocks.CARPET, EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
/*  636 */     registerBlock(Blocks.CARPET, EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
/*  637 */     registerBlock(Blocks.CARPET, EnumDyeColor.GREEN.getMetadata(), "green_carpet");
/*  638 */     registerBlock(Blocks.CARPET, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
/*  639 */     registerBlock(Blocks.CARPET, EnumDyeColor.LIME.getMetadata(), "lime_carpet");
/*  640 */     registerBlock(Blocks.CARPET, EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
/*  641 */     registerBlock(Blocks.CARPET, EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
/*  642 */     registerBlock(Blocks.CARPET, EnumDyeColor.PINK.getMetadata(), "pink_carpet");
/*  643 */     registerBlock(Blocks.CARPET, EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
/*  644 */     registerBlock(Blocks.CARPET, EnumDyeColor.RED.getMetadata(), "red_carpet");
/*  645 */     registerBlock(Blocks.CARPET, EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
/*  646 */     registerBlock(Blocks.CARPET, EnumDyeColor.WHITE.getMetadata(), "white_carpet");
/*  647 */     registerBlock(Blocks.CARPET, EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
/*  648 */     registerBlock(Blocks.COBBLESTONE_WALL, BlockWall.EnumType.MOSSY.getMetadata(), "mossy_cobblestone_wall");
/*  649 */     registerBlock(Blocks.COBBLESTONE_WALL, BlockWall.EnumType.NORMAL.getMetadata(), "cobblestone_wall");
/*  650 */     registerBlock(Blocks.DIRT, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
/*  651 */     registerBlock(Blocks.DIRT, BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
/*  652 */     registerBlock(Blocks.DIRT, BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
/*  653 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.FERN.getMeta(), "double_fern");
/*  654 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.GRASS.getMeta(), "double_grass");
/*  655 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), "paeonia");
/*  656 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.ROSE.getMeta(), "double_rose");
/*  657 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), "sunflower");
/*  658 */     registerBlock((Block)Blocks.DOUBLE_PLANT, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), "syringa");
/*  659 */     registerBlock((Block)Blocks.LEAVES, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_leaves");
/*  660 */     registerBlock((Block)Blocks.LEAVES, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_leaves");
/*  661 */     registerBlock((Block)Blocks.LEAVES, BlockPlanks.EnumType.OAK.getMetadata(), "oak_leaves");
/*  662 */     registerBlock((Block)Blocks.LEAVES, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_leaves");
/*  663 */     registerBlock((Block)Blocks.LEAVES2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
/*  664 */     registerBlock((Block)Blocks.LEAVES2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
/*  665 */     registerBlock(Blocks.LOG, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_log");
/*  666 */     registerBlock(Blocks.LOG, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_log");
/*  667 */     registerBlock(Blocks.LOG, BlockPlanks.EnumType.OAK.getMetadata(), "oak_log");
/*  668 */     registerBlock(Blocks.LOG, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_log");
/*  669 */     registerBlock(Blocks.LOG2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_log");
/*  670 */     registerBlock(Blocks.LOG2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
/*  671 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), "chiseled_brick_monster_egg");
/*  672 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), "cobblestone_monster_egg");
/*  673 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), "cracked_brick_monster_egg");
/*  674 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), "mossy_brick_monster_egg");
/*  675 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.STONE.getMetadata(), "stone_monster_egg");
/*  676 */     registerBlock(Blocks.MONSTER_EGG, BlockSilverfish.EnumType.STONEBRICK.getMetadata(), "stone_brick_monster_egg");
/*  677 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_planks");
/*  678 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_planks");
/*  679 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
/*  680 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_planks");
/*  681 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.OAK.getMetadata(), "oak_planks");
/*  682 */     registerBlock(Blocks.PLANKS, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_planks");
/*  683 */     registerBlock(Blocks.PRISMARINE, BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
/*  684 */     registerBlock(Blocks.PRISMARINE, BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
/*  685 */     registerBlock(Blocks.PRISMARINE, BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
/*  686 */     registerBlock(Blocks.QUARTZ_BLOCK, BlockQuartz.EnumType.CHISELED.getMetadata(), "chiseled_quartz_block");
/*  687 */     registerBlock(Blocks.QUARTZ_BLOCK, BlockQuartz.EnumType.DEFAULT.getMetadata(), "quartz_block");
/*  688 */     registerBlock(Blocks.QUARTZ_BLOCK, BlockQuartz.EnumType.LINES_Y.getMetadata(), "quartz_column");
/*  689 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.ALLIUM.getMeta(), "allium");
/*  690 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
/*  691 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
/*  692 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), "orange_tulip");
/*  693 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
/*  694 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
/*  695 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY.getMeta(), "poppy");
/*  696 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
/*  697 */     registerBlock((Block)Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
/*  698 */     registerBlock((Block)Blocks.SAND, BlockSand.EnumType.RED_SAND.getMetadata(), "red_sand");
/*  699 */     registerBlock((Block)Blocks.SAND, BlockSand.EnumType.SAND.getMetadata(), "sand");
/*  700 */     registerBlock(Blocks.SANDSTONE, BlockSandStone.EnumType.CHISELED.getMetadata(), "chiseled_sandstone");
/*  701 */     registerBlock(Blocks.SANDSTONE, BlockSandStone.EnumType.DEFAULT.getMetadata(), "sandstone");
/*  702 */     registerBlock(Blocks.SANDSTONE, BlockSandStone.EnumType.SMOOTH.getMetadata(), "smooth_sandstone");
/*  703 */     registerBlock(Blocks.RED_SANDSTONE, BlockRedSandstone.EnumType.CHISELED.getMetadata(), "chiseled_red_sandstone");
/*  704 */     registerBlock(Blocks.RED_SANDSTONE, BlockRedSandstone.EnumType.DEFAULT.getMetadata(), "red_sandstone");
/*  705 */     registerBlock(Blocks.RED_SANDSTONE, BlockRedSandstone.EnumType.SMOOTH.getMetadata(), "smooth_red_sandstone");
/*  706 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_sapling");
/*  707 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_sapling");
/*  708 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
/*  709 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_sapling");
/*  710 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.OAK.getMetadata(), "oak_sapling");
/*  711 */     registerBlock(Blocks.SAPLING, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_sapling");
/*  712 */     registerBlock(Blocks.SPONGE, 0, "sponge");
/*  713 */     registerBlock(Blocks.SPONGE, 1, "sponge_wet");
/*  714 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
/*  715 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
/*  716 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
/*  717 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
/*  718 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
/*  719 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
/*  720 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass");
/*  721 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
/*  722 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
/*  723 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
/*  724 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
/*  725 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
/*  726 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.RED.getMetadata(), "red_stained_glass");
/*  727 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
/*  728 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
/*  729 */     registerBlock((Block)Blocks.STAINED_GLASS, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
/*  730 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass_pane");
/*  731 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass_pane");
/*  732 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass_pane");
/*  733 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass_pane");
/*  734 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass_pane");
/*  735 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass_pane");
/*  736 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass_pane");
/*  737 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass_pane");
/*  738 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass_pane");
/*  739 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass_pane");
/*  740 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass_pane");
/*  741 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass_pane");
/*  742 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.RED.getMetadata(), "red_stained_glass_pane");
/*  743 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass_pane");
/*  744 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass_pane");
/*  745 */     registerBlock((Block)Blocks.STAINED_GLASS_PANE, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass_pane");
/*  746 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.BLACK.getMetadata(), "black_stained_hardened_clay");
/*  747 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.BLUE.getMetadata(), "blue_stained_hardened_clay");
/*  748 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.BROWN.getMetadata(), "brown_stained_hardened_clay");
/*  749 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_hardened_clay");
/*  750 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.GRAY.getMetadata(), "gray_stained_hardened_clay");
/*  751 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.GREEN.getMetadata(), "green_stained_hardened_clay");
/*  752 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_hardened_clay");
/*  753 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.LIME.getMetadata(), "lime_stained_hardened_clay");
/*  754 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_hardened_clay");
/*  755 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_hardened_clay");
/*  756 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.PINK.getMetadata(), "pink_stained_hardened_clay");
/*  757 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_hardened_clay");
/*  758 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.RED.getMetadata(), "red_stained_hardened_clay");
/*  759 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.SILVER.getMetadata(), "silver_stained_hardened_clay");
/*  760 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.WHITE.getMetadata(), "white_stained_hardened_clay");
/*  761 */     registerBlock(Blocks.STAINED_HARDENED_CLAY, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_hardened_clay");
/*  762 */     registerBlock(Blocks.STONE, BlockStone.EnumType.ANDESITE.getMetadata(), "andesite");
/*  763 */     registerBlock(Blocks.STONE, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), "andesite_smooth");
/*  764 */     registerBlock(Blocks.STONE, BlockStone.EnumType.DIORITE.getMetadata(), "diorite");
/*  765 */     registerBlock(Blocks.STONE, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), "diorite_smooth");
/*  766 */     registerBlock(Blocks.STONE, BlockStone.EnumType.GRANITE.getMetadata(), "granite");
/*  767 */     registerBlock(Blocks.STONE, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), "granite_smooth");
/*  768 */     registerBlock(Blocks.STONE, BlockStone.EnumType.STONE.getMetadata(), "stone");
/*  769 */     registerBlock(Blocks.STONEBRICK, BlockStoneBrick.EnumType.CRACKED.getMetadata(), "cracked_stonebrick");
/*  770 */     registerBlock(Blocks.STONEBRICK, BlockStoneBrick.EnumType.DEFAULT.getMetadata(), "stonebrick");
/*  771 */     registerBlock(Blocks.STONEBRICK, BlockStoneBrick.EnumType.CHISELED.getMetadata(), "chiseled_stonebrick");
/*  772 */     registerBlock(Blocks.STONEBRICK, BlockStoneBrick.EnumType.MOSSY.getMetadata(), "mossy_stonebrick");
/*  773 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.BRICK.getMetadata(), "brick_slab");
/*  774 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), "cobblestone_slab");
/*  775 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.WOOD.getMetadata(), "old_wood_slab");
/*  776 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), "nether_brick_slab");
/*  777 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.QUARTZ.getMetadata(), "quartz_slab");
/*  778 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.SAND.getMetadata(), "sandstone_slab");
/*  779 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), "stone_brick_slab");
/*  780 */     registerBlock((Block)Blocks.STONE_SLAB, BlockStoneSlab.EnumType.STONE.getMetadata(), "stone_slab");
/*  781 */     registerBlock((Block)Blocks.STONE_SLAB2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), "red_sandstone_slab");
/*  782 */     registerBlock((Block)Blocks.TALLGRASS, BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), "dead_bush");
/*  783 */     registerBlock((Block)Blocks.TALLGRASS, BlockTallGrass.EnumType.FERN.getMeta(), "fern");
/*  784 */     registerBlock((Block)Blocks.TALLGRASS, BlockTallGrass.EnumType.GRASS.getMeta(), "tall_grass");
/*  785 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_slab");
/*  786 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_slab");
/*  787 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
/*  788 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_slab");
/*  789 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.OAK.getMetadata(), "oak_slab");
/*  790 */     registerBlock((Block)Blocks.WOODEN_SLAB, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_slab");
/*  791 */     registerBlock(Blocks.WOOL, EnumDyeColor.BLACK.getMetadata(), "black_wool");
/*  792 */     registerBlock(Blocks.WOOL, EnumDyeColor.BLUE.getMetadata(), "blue_wool");
/*  793 */     registerBlock(Blocks.WOOL, EnumDyeColor.BROWN.getMetadata(), "brown_wool");
/*  794 */     registerBlock(Blocks.WOOL, EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
/*  795 */     registerBlock(Blocks.WOOL, EnumDyeColor.GRAY.getMetadata(), "gray_wool");
/*  796 */     registerBlock(Blocks.WOOL, EnumDyeColor.GREEN.getMetadata(), "green_wool");
/*  797 */     registerBlock(Blocks.WOOL, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
/*  798 */     registerBlock(Blocks.WOOL, EnumDyeColor.LIME.getMetadata(), "lime_wool");
/*  799 */     registerBlock(Blocks.WOOL, EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
/*  800 */     registerBlock(Blocks.WOOL, EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
/*  801 */     registerBlock(Blocks.WOOL, EnumDyeColor.PINK.getMetadata(), "pink_wool");
/*  802 */     registerBlock(Blocks.WOOL, EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
/*  803 */     registerBlock(Blocks.WOOL, EnumDyeColor.RED.getMetadata(), "red_wool");
/*  804 */     registerBlock(Blocks.WOOL, EnumDyeColor.SILVER.getMetadata(), "silver_wool");
/*  805 */     registerBlock(Blocks.WOOL, EnumDyeColor.WHITE.getMetadata(), "white_wool");
/*  806 */     registerBlock(Blocks.WOOL, EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
/*  807 */     registerBlock(Blocks.FARMLAND, "farmland");
/*  808 */     registerBlock(Blocks.ACACIA_STAIRS, "acacia_stairs");
/*  809 */     registerBlock(Blocks.ACTIVATOR_RAIL, "activator_rail");
/*  810 */     registerBlock((Block)Blocks.BEACON, "beacon");
/*  811 */     registerBlock(Blocks.BEDROCK, "bedrock");
/*  812 */     registerBlock(Blocks.BIRCH_STAIRS, "birch_stairs");
/*  813 */     registerBlock(Blocks.BOOKSHELF, "bookshelf");
/*  814 */     registerBlock(Blocks.BRICK_BLOCK, "brick_block");
/*  815 */     registerBlock(Blocks.BRICK_BLOCK, "brick_block");
/*  816 */     registerBlock(Blocks.BRICK_STAIRS, "brick_stairs");
/*  817 */     registerBlock((Block)Blocks.BROWN_MUSHROOM, "brown_mushroom");
/*  818 */     registerBlock((Block)Blocks.CACTUS, "cactus");
/*  819 */     registerBlock(Blocks.CLAY, "clay");
/*  820 */     registerBlock(Blocks.COAL_BLOCK, "coal_block");
/*  821 */     registerBlock(Blocks.COAL_ORE, "coal_ore");
/*  822 */     registerBlock(Blocks.COBBLESTONE, "cobblestone");
/*  823 */     registerBlock(Blocks.CRAFTING_TABLE, "crafting_table");
/*  824 */     registerBlock(Blocks.DARK_OAK_STAIRS, "dark_oak_stairs");
/*  825 */     registerBlock((Block)Blocks.DAYLIGHT_DETECTOR, "daylight_detector");
/*  826 */     registerBlock((Block)Blocks.DEADBUSH, "dead_bush");
/*  827 */     registerBlock(Blocks.DETECTOR_RAIL, "detector_rail");
/*  828 */     registerBlock(Blocks.DIAMOND_BLOCK, "diamond_block");
/*  829 */     registerBlock(Blocks.DIAMOND_ORE, "diamond_ore");
/*  830 */     registerBlock(Blocks.DISPENSER, "dispenser");
/*  831 */     registerBlock(Blocks.DROPPER, "dropper");
/*  832 */     registerBlock(Blocks.EMERALD_BLOCK, "emerald_block");
/*  833 */     registerBlock(Blocks.EMERALD_ORE, "emerald_ore");
/*  834 */     registerBlock(Blocks.ENCHANTING_TABLE, "enchanting_table");
/*  835 */     registerBlock(Blocks.END_PORTAL_FRAME, "end_portal_frame");
/*  836 */     registerBlock(Blocks.END_STONE, "end_stone");
/*  837 */     registerBlock(Blocks.OAK_FENCE, "oak_fence");
/*  838 */     registerBlock(Blocks.SPRUCE_FENCE, "spruce_fence");
/*  839 */     registerBlock(Blocks.BIRCH_FENCE, "birch_fence");
/*  840 */     registerBlock(Blocks.JUNGLE_FENCE, "jungle_fence");
/*  841 */     registerBlock(Blocks.DARK_OAK_FENCE, "dark_oak_fence");
/*  842 */     registerBlock(Blocks.ACACIA_FENCE, "acacia_fence");
/*  843 */     registerBlock(Blocks.OAK_FENCE_GATE, "oak_fence_gate");
/*  844 */     registerBlock(Blocks.SPRUCE_FENCE_GATE, "spruce_fence_gate");
/*  845 */     registerBlock(Blocks.BIRCH_FENCE_GATE, "birch_fence_gate");
/*  846 */     registerBlock(Blocks.JUNGLE_FENCE_GATE, "jungle_fence_gate");
/*  847 */     registerBlock(Blocks.DARK_OAK_FENCE_GATE, "dark_oak_fence_gate");
/*  848 */     registerBlock(Blocks.ACACIA_FENCE_GATE, "acacia_fence_gate");
/*  849 */     registerBlock(Blocks.FURNACE, "furnace");
/*  850 */     registerBlock(Blocks.GLASS, "glass");
/*  851 */     registerBlock(Blocks.GLASS_PANE, "glass_pane");
/*  852 */     registerBlock(Blocks.GLOWSTONE, "glowstone");
/*  853 */     registerBlock(Blocks.GOLDEN_RAIL, "golden_rail");
/*  854 */     registerBlock(Blocks.GOLD_BLOCK, "gold_block");
/*  855 */     registerBlock(Blocks.GOLD_ORE, "gold_ore");
/*  856 */     registerBlock((Block)Blocks.GRASS, "grass");
/*  857 */     registerBlock(Blocks.GRASS_PATH, "grass_path");
/*  858 */     registerBlock(Blocks.GRAVEL, "gravel");
/*  859 */     registerBlock(Blocks.HARDENED_CLAY, "hardened_clay");
/*  860 */     registerBlock(Blocks.HAY_BLOCK, "hay_block");
/*  861 */     registerBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, "heavy_weighted_pressure_plate");
/*  862 */     registerBlock((Block)Blocks.HOPPER, "hopper");
/*  863 */     registerBlock(Blocks.ICE, "ice");
/*  864 */     registerBlock(Blocks.IRON_BARS, "iron_bars");
/*  865 */     registerBlock(Blocks.IRON_BLOCK, "iron_block");
/*  866 */     registerBlock(Blocks.IRON_ORE, "iron_ore");
/*  867 */     registerBlock(Blocks.IRON_TRAPDOOR, "iron_trapdoor");
/*  868 */     registerBlock(Blocks.JUKEBOX, "jukebox");
/*  869 */     registerBlock(Blocks.JUNGLE_STAIRS, "jungle_stairs");
/*  870 */     registerBlock(Blocks.LADDER, "ladder");
/*  871 */     registerBlock(Blocks.LAPIS_BLOCK, "lapis_block");
/*  872 */     registerBlock(Blocks.LAPIS_ORE, "lapis_ore");
/*  873 */     registerBlock(Blocks.LEVER, "lever");
/*  874 */     registerBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, "light_weighted_pressure_plate");
/*  875 */     registerBlock(Blocks.LIT_PUMPKIN, "lit_pumpkin");
/*  876 */     registerBlock(Blocks.MELON_BLOCK, "melon_block");
/*  877 */     registerBlock(Blocks.MOSSY_COBBLESTONE, "mossy_cobblestone");
/*  878 */     registerBlock((Block)Blocks.MYCELIUM, "mycelium");
/*  879 */     registerBlock(Blocks.NETHERRACK, "netherrack");
/*  880 */     registerBlock(Blocks.NETHER_BRICK, "nether_brick");
/*  881 */     registerBlock(Blocks.NETHER_BRICK_FENCE, "nether_brick_fence");
/*  882 */     registerBlock(Blocks.NETHER_BRICK_STAIRS, "nether_brick_stairs");
/*  883 */     registerBlock(Blocks.NOTEBLOCK, "noteblock");
/*  884 */     registerBlock(Blocks.OAK_STAIRS, "oak_stairs");
/*  885 */     registerBlock(Blocks.OBSIDIAN, "obsidian");
/*  886 */     registerBlock(Blocks.PACKED_ICE, "packed_ice");
/*  887 */     registerBlock((Block)Blocks.PISTON, "piston");
/*  888 */     registerBlock(Blocks.PUMPKIN, "pumpkin");
/*  889 */     registerBlock(Blocks.QUARTZ_ORE, "quartz_ore");
/*  890 */     registerBlock(Blocks.QUARTZ_STAIRS, "quartz_stairs");
/*  891 */     registerBlock(Blocks.RAIL, "rail");
/*  892 */     registerBlock(Blocks.REDSTONE_BLOCK, "redstone_block");
/*  893 */     registerBlock(Blocks.REDSTONE_LAMP, "redstone_lamp");
/*  894 */     registerBlock(Blocks.REDSTONE_ORE, "redstone_ore");
/*  895 */     registerBlock(Blocks.REDSTONE_TORCH, "redstone_torch");
/*  896 */     registerBlock((Block)Blocks.RED_MUSHROOM, "red_mushroom");
/*  897 */     registerBlock(Blocks.SANDSTONE_STAIRS, "sandstone_stairs");
/*  898 */     registerBlock(Blocks.RED_SANDSTONE_STAIRS, "red_sandstone_stairs");
/*  899 */     registerBlock(Blocks.SEA_LANTERN, "sea_lantern");
/*  900 */     registerBlock(Blocks.SLIME_BLOCK, "slime");
/*  901 */     registerBlock(Blocks.SNOW, "snow");
/*  902 */     registerBlock(Blocks.SNOW_LAYER, "snow_layer");
/*  903 */     registerBlock(Blocks.SOUL_SAND, "soul_sand");
/*  904 */     registerBlock(Blocks.SPRUCE_STAIRS, "spruce_stairs");
/*  905 */     registerBlock((Block)Blocks.STICKY_PISTON, "sticky_piston");
/*  906 */     registerBlock(Blocks.STONE_BRICK_STAIRS, "stone_brick_stairs");
/*  907 */     registerBlock(Blocks.STONE_BUTTON, "stone_button");
/*  908 */     registerBlock(Blocks.STONE_PRESSURE_PLATE, "stone_pressure_plate");
/*  909 */     registerBlock(Blocks.STONE_STAIRS, "stone_stairs");
/*  910 */     registerBlock(Blocks.TNT, "tnt");
/*  911 */     registerBlock(Blocks.TORCH, "torch");
/*  912 */     registerBlock(Blocks.TRAPDOOR, "trapdoor");
/*  913 */     registerBlock((Block)Blocks.TRIPWIRE_HOOK, "tripwire_hook");
/*  914 */     registerBlock(Blocks.VINE, "vine");
/*  915 */     registerBlock(Blocks.WATERLILY, "waterlily");
/*  916 */     registerBlock(Blocks.WEB, "web");
/*  917 */     registerBlock(Blocks.WOODEN_BUTTON, "wooden_button");
/*  918 */     registerBlock(Blocks.WOODEN_PRESSURE_PLATE, "wooden_pressure_plate");
/*  919 */     registerBlock((Block)Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION.getMeta(), "dandelion");
/*  920 */     registerBlock(Blocks.END_ROD, "end_rod");
/*  921 */     registerBlock(Blocks.CHORUS_PLANT, "chorus_plant");
/*  922 */     registerBlock(Blocks.CHORUS_FLOWER, "chorus_flower");
/*  923 */     registerBlock(Blocks.PURPUR_BLOCK, "purpur_block");
/*  924 */     registerBlock(Blocks.PURPUR_PILLAR, "purpur_pillar");
/*  925 */     registerBlock(Blocks.PURPUR_STAIRS, "purpur_stairs");
/*  926 */     registerBlock((Block)Blocks.PURPUR_SLAB, "purpur_slab");
/*  927 */     registerBlock((Block)Blocks.PURPUR_DOUBLE_SLAB, "purpur_double_slab");
/*  928 */     registerBlock(Blocks.END_BRICKS, "end_bricks");
/*  929 */     registerBlock(Blocks.MAGMA, "magma");
/*  930 */     registerBlock(Blocks.NETHER_WART_BLOCK, "nether_wart_block");
/*  931 */     registerBlock(Blocks.RED_NETHER_BRICK, "red_nether_brick");
/*  932 */     registerBlock(Blocks.BONE_BLOCK, "bone_block");
/*  933 */     registerBlock(Blocks.STRUCTURE_VOID, "structure_void");
/*  934 */     registerBlock(Blocks.field_190976_dk, "observer");
/*  935 */     registerBlock(Blocks.field_190977_dl, "white_shulker_box");
/*  936 */     registerBlock(Blocks.field_190978_dm, "orange_shulker_box");
/*  937 */     registerBlock(Blocks.field_190979_dn, "magenta_shulker_box");
/*  938 */     registerBlock(Blocks.field_190980_do, "light_blue_shulker_box");
/*  939 */     registerBlock(Blocks.field_190981_dp, "yellow_shulker_box");
/*  940 */     registerBlock(Blocks.field_190982_dq, "lime_shulker_box");
/*  941 */     registerBlock(Blocks.field_190983_dr, "pink_shulker_box");
/*  942 */     registerBlock(Blocks.field_190984_ds, "gray_shulker_box");
/*  943 */     registerBlock(Blocks.field_190985_dt, "silver_shulker_box");
/*  944 */     registerBlock(Blocks.field_190986_du, "cyan_shulker_box");
/*  945 */     registerBlock(Blocks.field_190987_dv, "purple_shulker_box");
/*  946 */     registerBlock(Blocks.field_190988_dw, "blue_shulker_box");
/*  947 */     registerBlock(Blocks.field_190989_dx, "brown_shulker_box");
/*  948 */     registerBlock(Blocks.field_190990_dy, "green_shulker_box");
/*  949 */     registerBlock(Blocks.field_190991_dz, "red_shulker_box");
/*  950 */     registerBlock(Blocks.field_190975_dA, "black_shulker_box");
/*  951 */     registerBlock(Blocks.field_192427_dB, "white_glazed_terracotta");
/*  952 */     registerBlock(Blocks.field_192428_dC, "orange_glazed_terracotta");
/*  953 */     registerBlock(Blocks.field_192429_dD, "magenta_glazed_terracotta");
/*  954 */     registerBlock(Blocks.field_192430_dE, "light_blue_glazed_terracotta");
/*  955 */     registerBlock(Blocks.field_192431_dF, "yellow_glazed_terracotta");
/*  956 */     registerBlock(Blocks.field_192432_dG, "lime_glazed_terracotta");
/*  957 */     registerBlock(Blocks.field_192433_dH, "pink_glazed_terracotta");
/*  958 */     registerBlock(Blocks.field_192434_dI, "gray_glazed_terracotta");
/*  959 */     registerBlock(Blocks.field_192435_dJ, "silver_glazed_terracotta");
/*  960 */     registerBlock(Blocks.field_192436_dK, "cyan_glazed_terracotta");
/*  961 */     registerBlock(Blocks.field_192437_dL, "purple_glazed_terracotta");
/*  962 */     registerBlock(Blocks.field_192438_dM, "blue_glazed_terracotta");
/*  963 */     registerBlock(Blocks.field_192439_dN, "brown_glazed_terracotta");
/*  964 */     registerBlock(Blocks.field_192440_dO, "green_glazed_terracotta");
/*  965 */     registerBlock(Blocks.field_192441_dP, "red_glazed_terracotta");
/*  966 */     registerBlock(Blocks.field_192442_dQ, "black_glazed_terracotta"); byte b; int i;
/*      */     EnumDyeColor[] arrayOfEnumDyeColor;
/*  968 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*      */       
/*  970 */       registerBlock(Blocks.field_192443_dR, enumdyecolor.getMetadata(), String.valueOf(enumdyecolor.func_192396_c()) + "_concrete");
/*  971 */       registerBlock(Blocks.field_192444_dS, enumdyecolor.getMetadata(), String.valueOf(enumdyecolor.func_192396_c()) + "_concrete_powder");
/*      */       b++; }
/*      */     
/*  974 */     registerBlock((Block)Blocks.CHEST, "chest");
/*  975 */     registerBlock(Blocks.TRAPPED_CHEST, "trapped_chest");
/*  976 */     registerBlock(Blocks.ENDER_CHEST, "ender_chest");
/*  977 */     registerItem(Items.IRON_SHOVEL, "iron_shovel");
/*  978 */     registerItem(Items.IRON_PICKAXE, "iron_pickaxe");
/*  979 */     registerItem(Items.IRON_AXE, "iron_axe");
/*  980 */     registerItem(Items.FLINT_AND_STEEL, "flint_and_steel");
/*  981 */     registerItem(Items.APPLE, "apple");
/*  982 */     registerItem((Item)Items.BOW, "bow");
/*  983 */     registerItem(Items.ARROW, "arrow");
/*  984 */     registerItem(Items.SPECTRAL_ARROW, "spectral_arrow");
/*  985 */     registerItem(Items.TIPPED_ARROW, "tipped_arrow");
/*  986 */     registerItem(Items.COAL, 0, "coal");
/*  987 */     registerItem(Items.COAL, 1, "charcoal");
/*  988 */     registerItem(Items.DIAMOND, "diamond");
/*  989 */     registerItem(Items.IRON_INGOT, "iron_ingot");
/*  990 */     registerItem(Items.GOLD_INGOT, "gold_ingot");
/*  991 */     registerItem(Items.IRON_SWORD, "iron_sword");
/*  992 */     registerItem(Items.WOODEN_SWORD, "wooden_sword");
/*  993 */     registerItem(Items.WOODEN_SHOVEL, "wooden_shovel");
/*  994 */     registerItem(Items.WOODEN_PICKAXE, "wooden_pickaxe");
/*  995 */     registerItem(Items.WOODEN_AXE, "wooden_axe");
/*  996 */     registerItem(Items.STONE_SWORD, "stone_sword");
/*  997 */     registerItem(Items.STONE_SHOVEL, "stone_shovel");
/*  998 */     registerItem(Items.STONE_PICKAXE, "stone_pickaxe");
/*  999 */     registerItem(Items.STONE_AXE, "stone_axe");
/* 1000 */     registerItem(Items.DIAMOND_SWORD, "diamond_sword");
/* 1001 */     registerItem(Items.DIAMOND_SHOVEL, "diamond_shovel");
/* 1002 */     registerItem(Items.DIAMOND_PICKAXE, "diamond_pickaxe");
/* 1003 */     registerItem(Items.DIAMOND_AXE, "diamond_axe");
/* 1004 */     registerItem(Items.STICK, "stick");
/* 1005 */     registerItem(Items.BOWL, "bowl");
/* 1006 */     registerItem(Items.MUSHROOM_STEW, "mushroom_stew");
/* 1007 */     registerItem(Items.GOLDEN_SWORD, "golden_sword");
/* 1008 */     registerItem(Items.GOLDEN_SHOVEL, "golden_shovel");
/* 1009 */     registerItem(Items.GOLDEN_PICKAXE, "golden_pickaxe");
/* 1010 */     registerItem(Items.GOLDEN_AXE, "golden_axe");
/* 1011 */     registerItem(Items.STRING, "string");
/* 1012 */     registerItem(Items.FEATHER, "feather");
/* 1013 */     registerItem(Items.GUNPOWDER, "gunpowder");
/* 1014 */     registerItem(Items.WOODEN_HOE, "wooden_hoe");
/* 1015 */     registerItem(Items.STONE_HOE, "stone_hoe");
/* 1016 */     registerItem(Items.IRON_HOE, "iron_hoe");
/* 1017 */     registerItem(Items.DIAMOND_HOE, "diamond_hoe");
/* 1018 */     registerItem(Items.GOLDEN_HOE, "golden_hoe");
/* 1019 */     registerItem(Items.WHEAT_SEEDS, "wheat_seeds");
/* 1020 */     registerItem(Items.WHEAT, "wheat");
/* 1021 */     registerItem(Items.BREAD, "bread");
/* 1022 */     registerItem((Item)Items.LEATHER_HELMET, "leather_helmet");
/* 1023 */     registerItem((Item)Items.LEATHER_CHESTPLATE, "leather_chestplate");
/* 1024 */     registerItem((Item)Items.LEATHER_LEGGINGS, "leather_leggings");
/* 1025 */     registerItem((Item)Items.LEATHER_BOOTS, "leather_boots");
/* 1026 */     registerItem((Item)Items.CHAINMAIL_HELMET, "chainmail_helmet");
/* 1027 */     registerItem((Item)Items.CHAINMAIL_CHESTPLATE, "chainmail_chestplate");
/* 1028 */     registerItem((Item)Items.CHAINMAIL_LEGGINGS, "chainmail_leggings");
/* 1029 */     registerItem((Item)Items.CHAINMAIL_BOOTS, "chainmail_boots");
/* 1030 */     registerItem((Item)Items.IRON_HELMET, "iron_helmet");
/* 1031 */     registerItem((Item)Items.IRON_CHESTPLATE, "iron_chestplate");
/* 1032 */     registerItem((Item)Items.IRON_LEGGINGS, "iron_leggings");
/* 1033 */     registerItem((Item)Items.IRON_BOOTS, "iron_boots");
/* 1034 */     registerItem((Item)Items.DIAMOND_HELMET, "diamond_helmet");
/* 1035 */     registerItem((Item)Items.DIAMOND_CHESTPLATE, "diamond_chestplate");
/* 1036 */     registerItem((Item)Items.DIAMOND_LEGGINGS, "diamond_leggings");
/* 1037 */     registerItem((Item)Items.DIAMOND_BOOTS, "diamond_boots");
/* 1038 */     registerItem((Item)Items.GOLDEN_HELMET, "golden_helmet");
/* 1039 */     registerItem((Item)Items.GOLDEN_CHESTPLATE, "golden_chestplate");
/* 1040 */     registerItem((Item)Items.GOLDEN_LEGGINGS, "golden_leggings");
/* 1041 */     registerItem((Item)Items.GOLDEN_BOOTS, "golden_boots");
/* 1042 */     registerItem(Items.FLINT, "flint");
/* 1043 */     registerItem(Items.PORKCHOP, "porkchop");
/* 1044 */     registerItem(Items.COOKED_PORKCHOP, "cooked_porkchop");
/* 1045 */     registerItem(Items.PAINTING, "painting");
/* 1046 */     registerItem(Items.GOLDEN_APPLE, "golden_apple");
/* 1047 */     registerItem(Items.GOLDEN_APPLE, 1, "golden_apple");
/* 1048 */     registerItem(Items.SIGN, "sign");
/* 1049 */     registerItem(Items.OAK_DOOR, "oak_door");
/* 1050 */     registerItem(Items.SPRUCE_DOOR, "spruce_door");
/* 1051 */     registerItem(Items.BIRCH_DOOR, "birch_door");
/* 1052 */     registerItem(Items.JUNGLE_DOOR, "jungle_door");
/* 1053 */     registerItem(Items.ACACIA_DOOR, "acacia_door");
/* 1054 */     registerItem(Items.DARK_OAK_DOOR, "dark_oak_door");
/* 1055 */     registerItem(Items.BUCKET, "bucket");
/* 1056 */     registerItem(Items.WATER_BUCKET, "water_bucket");
/* 1057 */     registerItem(Items.LAVA_BUCKET, "lava_bucket");
/* 1058 */     registerItem(Items.MINECART, "minecart");
/* 1059 */     registerItem(Items.SADDLE, "saddle");
/* 1060 */     registerItem(Items.IRON_DOOR, "iron_door");
/* 1061 */     registerItem(Items.REDSTONE, "redstone");
/* 1062 */     registerItem(Items.SNOWBALL, "snowball");
/* 1063 */     registerItem(Items.BOAT, "oak_boat");
/* 1064 */     registerItem(Items.SPRUCE_BOAT, "spruce_boat");
/* 1065 */     registerItem(Items.BIRCH_BOAT, "birch_boat");
/* 1066 */     registerItem(Items.JUNGLE_BOAT, "jungle_boat");
/* 1067 */     registerItem(Items.ACACIA_BOAT, "acacia_boat");
/* 1068 */     registerItem(Items.DARK_OAK_BOAT, "dark_oak_boat");
/* 1069 */     registerItem(Items.LEATHER, "leather");
/* 1070 */     registerItem(Items.MILK_BUCKET, "milk_bucket");
/* 1071 */     registerItem(Items.BRICK, "brick");
/* 1072 */     registerItem(Items.CLAY_BALL, "clay_ball");
/* 1073 */     registerItem(Items.REEDS, "reeds");
/* 1074 */     registerItem(Items.PAPER, "paper");
/* 1075 */     registerItem(Items.BOOK, "book");
/* 1076 */     registerItem(Items.SLIME_BALL, "slime_ball");
/* 1077 */     registerItem(Items.CHEST_MINECART, "chest_minecart");
/* 1078 */     registerItem(Items.FURNACE_MINECART, "furnace_minecart");
/* 1079 */     registerItem(Items.EGG, "egg");
/* 1080 */     registerItem(Items.COMPASS, "compass");
/* 1081 */     registerItem((Item)Items.FISHING_ROD, "fishing_rod");
/* 1082 */     registerItem(Items.CLOCK, "clock");
/* 1083 */     registerItem(Items.GLOWSTONE_DUST, "glowstone_dust");
/* 1084 */     registerItem(Items.FISH, ItemFishFood.FishType.COD.getMetadata(), "cod");
/* 1085 */     registerItem(Items.FISH, ItemFishFood.FishType.SALMON.getMetadata(), "salmon");
/* 1086 */     registerItem(Items.FISH, ItemFishFood.FishType.CLOWNFISH.getMetadata(), "clownfish");
/* 1087 */     registerItem(Items.FISH, ItemFishFood.FishType.PUFFERFISH.getMetadata(), "pufferfish");
/* 1088 */     registerItem(Items.COOKED_FISH, ItemFishFood.FishType.COD.getMetadata(), "cooked_cod");
/* 1089 */     registerItem(Items.COOKED_FISH, ItemFishFood.FishType.SALMON.getMetadata(), "cooked_salmon");
/* 1090 */     registerItem(Items.DYE, EnumDyeColor.BLACK.getDyeDamage(), "dye_black");
/* 1091 */     registerItem(Items.DYE, EnumDyeColor.RED.getDyeDamage(), "dye_red");
/* 1092 */     registerItem(Items.DYE, EnumDyeColor.GREEN.getDyeDamage(), "dye_green");
/* 1093 */     registerItem(Items.DYE, EnumDyeColor.BROWN.getDyeDamage(), "dye_brown");
/* 1094 */     registerItem(Items.DYE, EnumDyeColor.BLUE.getDyeDamage(), "dye_blue");
/* 1095 */     registerItem(Items.DYE, EnumDyeColor.PURPLE.getDyeDamage(), "dye_purple");
/* 1096 */     registerItem(Items.DYE, EnumDyeColor.CYAN.getDyeDamage(), "dye_cyan");
/* 1097 */     registerItem(Items.DYE, EnumDyeColor.SILVER.getDyeDamage(), "dye_silver");
/* 1098 */     registerItem(Items.DYE, EnumDyeColor.GRAY.getDyeDamage(), "dye_gray");
/* 1099 */     registerItem(Items.DYE, EnumDyeColor.PINK.getDyeDamage(), "dye_pink");
/* 1100 */     registerItem(Items.DYE, EnumDyeColor.LIME.getDyeDamage(), "dye_lime");
/* 1101 */     registerItem(Items.DYE, EnumDyeColor.YELLOW.getDyeDamage(), "dye_yellow");
/* 1102 */     registerItem(Items.DYE, EnumDyeColor.LIGHT_BLUE.getDyeDamage(), "dye_light_blue");
/* 1103 */     registerItem(Items.DYE, EnumDyeColor.MAGENTA.getDyeDamage(), "dye_magenta");
/* 1104 */     registerItem(Items.DYE, EnumDyeColor.ORANGE.getDyeDamage(), "dye_orange");
/* 1105 */     registerItem(Items.DYE, EnumDyeColor.WHITE.getDyeDamage(), "dye_white");
/* 1106 */     registerItem(Items.BONE, "bone");
/* 1107 */     registerItem(Items.SUGAR, "sugar");
/* 1108 */     registerItem(Items.CAKE, "cake");
/* 1109 */     registerItem(Items.REPEATER, "repeater");
/* 1110 */     registerItem(Items.COOKIE, "cookie");
/* 1111 */     registerItem((Item)Items.SHEARS, "shears");
/* 1112 */     registerItem(Items.MELON, "melon");
/* 1113 */     registerItem(Items.PUMPKIN_SEEDS, "pumpkin_seeds");
/* 1114 */     registerItem(Items.MELON_SEEDS, "melon_seeds");
/* 1115 */     registerItem(Items.BEEF, "beef");
/* 1116 */     registerItem(Items.COOKED_BEEF, "cooked_beef");
/* 1117 */     registerItem(Items.CHICKEN, "chicken");
/* 1118 */     registerItem(Items.COOKED_CHICKEN, "cooked_chicken");
/* 1119 */     registerItem(Items.RABBIT, "rabbit");
/* 1120 */     registerItem(Items.COOKED_RABBIT, "cooked_rabbit");
/* 1121 */     registerItem(Items.MUTTON, "mutton");
/* 1122 */     registerItem(Items.COOKED_MUTTON, "cooked_mutton");
/* 1123 */     registerItem(Items.RABBIT_FOOT, "rabbit_foot");
/* 1124 */     registerItem(Items.RABBIT_HIDE, "rabbit_hide");
/* 1125 */     registerItem(Items.RABBIT_STEW, "rabbit_stew");
/* 1126 */     registerItem(Items.ROTTEN_FLESH, "rotten_flesh");
/* 1127 */     registerItem(Items.ENDER_PEARL, "ender_pearl");
/* 1128 */     registerItem(Items.BLAZE_ROD, "blaze_rod");
/* 1129 */     registerItem(Items.GHAST_TEAR, "ghast_tear");
/* 1130 */     registerItem(Items.GOLD_NUGGET, "gold_nugget");
/* 1131 */     registerItem(Items.NETHER_WART, "nether_wart");
/* 1132 */     registerItem(Items.BEETROOT, "beetroot");
/* 1133 */     registerItem(Items.BEETROOT_SEEDS, "beetroot_seeds");
/* 1134 */     registerItem(Items.BEETROOT_SOUP, "beetroot_soup");
/* 1135 */     registerItem(Items.field_190929_cY, "totem");
/* 1136 */     registerItem((Item)Items.POTIONITEM, "bottle_drinkable");
/* 1137 */     registerItem((Item)Items.SPLASH_POTION, "bottle_splash");
/* 1138 */     registerItem((Item)Items.LINGERING_POTION, "bottle_lingering");
/* 1139 */     registerItem(Items.GLASS_BOTTLE, "glass_bottle");
/* 1140 */     registerItem(Items.DRAGON_BREATH, "dragon_breath");
/* 1141 */     registerItem(Items.SPIDER_EYE, "spider_eye");
/* 1142 */     registerItem(Items.FERMENTED_SPIDER_EYE, "fermented_spider_eye");
/* 1143 */     registerItem(Items.BLAZE_POWDER, "blaze_powder");
/* 1144 */     registerItem(Items.MAGMA_CREAM, "magma_cream");
/* 1145 */     registerItem(Items.BREWING_STAND, "brewing_stand");
/* 1146 */     registerItem(Items.CAULDRON, "cauldron");
/* 1147 */     registerItem(Items.ENDER_EYE, "ender_eye");
/* 1148 */     registerItem(Items.SPECKLED_MELON, "speckled_melon");
/* 1149 */     this.itemModelMesher.register(Items.SPAWN_EGG, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1153 */             return new ModelResourceLocation("spawn_egg", "inventory");
/*      */           }
/*      */         });
/* 1156 */     registerItem(Items.EXPERIENCE_BOTTLE, "experience_bottle");
/* 1157 */     registerItem(Items.FIRE_CHARGE, "fire_charge");
/* 1158 */     registerItem(Items.WRITABLE_BOOK, "writable_book");
/* 1159 */     registerItem(Items.EMERALD, "emerald");
/* 1160 */     registerItem(Items.ITEM_FRAME, "item_frame");
/* 1161 */     registerItem(Items.FLOWER_POT, "flower_pot");
/* 1162 */     registerItem(Items.CARROT, "carrot");
/* 1163 */     registerItem(Items.POTATO, "potato");
/* 1164 */     registerItem(Items.BAKED_POTATO, "baked_potato");
/* 1165 */     registerItem(Items.POISONOUS_POTATO, "poisonous_potato");
/* 1166 */     registerItem((Item)Items.MAP, "map");
/* 1167 */     registerItem(Items.GOLDEN_CARROT, "golden_carrot");
/* 1168 */     registerItem(Items.SKULL, 0, "skull_skeleton");
/* 1169 */     registerItem(Items.SKULL, 1, "skull_wither");
/* 1170 */     registerItem(Items.SKULL, 2, "skull_zombie");
/* 1171 */     registerItem(Items.SKULL, 3, "skull_char");
/* 1172 */     registerItem(Items.SKULL, 4, "skull_creeper");
/* 1173 */     registerItem(Items.SKULL, 5, "skull_dragon");
/* 1174 */     registerItem(Items.CARROT_ON_A_STICK, "carrot_on_a_stick");
/* 1175 */     registerItem(Items.NETHER_STAR, "nether_star");
/* 1176 */     registerItem(Items.END_CRYSTAL, "end_crystal");
/* 1177 */     registerItem(Items.PUMPKIN_PIE, "pumpkin_pie");
/* 1178 */     registerItem(Items.FIREWORK_CHARGE, "firework_charge");
/* 1179 */     registerItem(Items.COMPARATOR, "comparator");
/* 1180 */     registerItem(Items.NETHERBRICK, "netherbrick");
/* 1181 */     registerItem(Items.QUARTZ, "quartz");
/* 1182 */     registerItem(Items.TNT_MINECART, "tnt_minecart");
/* 1183 */     registerItem(Items.HOPPER_MINECART, "hopper_minecart");
/* 1184 */     registerItem((Item)Items.ARMOR_STAND, "armor_stand");
/* 1185 */     registerItem(Items.IRON_HORSE_ARMOR, "iron_horse_armor");
/* 1186 */     registerItem(Items.GOLDEN_HORSE_ARMOR, "golden_horse_armor");
/* 1187 */     registerItem(Items.DIAMOND_HORSE_ARMOR, "diamond_horse_armor");
/* 1188 */     registerItem(Items.LEAD, "lead");
/* 1189 */     registerItem(Items.NAME_TAG, "name_tag");
/* 1190 */     this.itemModelMesher.register(Items.BANNER, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1194 */             return new ModelResourceLocation("banner", "inventory");
/*      */           }
/*      */         });
/* 1197 */     this.itemModelMesher.register(Items.BED, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1201 */             return new ModelResourceLocation("bed", "inventory");
/*      */           }
/*      */         });
/* 1204 */     this.itemModelMesher.register(Items.SHIELD, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1208 */             return new ModelResourceLocation("shield", "inventory");
/*      */           }
/*      */         });
/* 1211 */     registerItem(Items.ELYTRA, "elytra");
/* 1212 */     registerItem(Items.CHORUS_FRUIT, "chorus_fruit");
/* 1213 */     registerItem(Items.CHORUS_FRUIT_POPPED, "chorus_fruit_popped");
/* 1214 */     registerItem(Items.field_190930_cZ, "shulker_shell");
/* 1215 */     registerItem(Items.field_191525_da, "iron_nugget");
/* 1216 */     registerItem(Items.RECORD_13, "record_13");
/* 1217 */     registerItem(Items.RECORD_CAT, "record_cat");
/* 1218 */     registerItem(Items.RECORD_BLOCKS, "record_blocks");
/* 1219 */     registerItem(Items.RECORD_CHIRP, "record_chirp");
/* 1220 */     registerItem(Items.RECORD_FAR, "record_far");
/* 1221 */     registerItem(Items.RECORD_MALL, "record_mall");
/* 1222 */     registerItem(Items.RECORD_MELLOHI, "record_mellohi");
/* 1223 */     registerItem(Items.RECORD_STAL, "record_stal");
/* 1224 */     registerItem(Items.RECORD_STRAD, "record_strad");
/* 1225 */     registerItem(Items.RECORD_WARD, "record_ward");
/* 1226 */     registerItem(Items.RECORD_11, "record_11");
/* 1227 */     registerItem(Items.RECORD_WAIT, "record_wait");
/* 1228 */     registerItem(Items.PRISMARINE_SHARD, "prismarine_shard");
/* 1229 */     registerItem(Items.PRISMARINE_CRYSTALS, "prismarine_crystals");
/* 1230 */     registerItem(Items.field_192397_db, "knowledge_book");
/* 1231 */     this.itemModelMesher.register(Items.ENCHANTED_BOOK, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1235 */             return new ModelResourceLocation("enchanted_book", "inventory");
/*      */           }
/*      */         });
/* 1238 */     this.itemModelMesher.register((Item)Items.FILLED_MAP, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack)
/*      */           {
/* 1242 */             return new ModelResourceLocation("filled_map", "inventory");
/*      */           }
/*      */         });
/* 1245 */     registerBlock(Blocks.COMMAND_BLOCK, "command_block");
/* 1246 */     registerItem(Items.FIREWORKS, "fireworks");
/* 1247 */     registerItem(Items.COMMAND_BLOCK_MINECART, "command_block_minecart");
/* 1248 */     registerBlock(Blocks.BARRIER, "barrier");
/* 1249 */     registerBlock(Blocks.MOB_SPAWNER, "mob_spawner");
/* 1250 */     registerItem(Items.WRITTEN_BOOK, "written_book");
/* 1251 */     registerBlock(Blocks.BROWN_MUSHROOM_BLOCK, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "brown_mushroom_block");
/* 1252 */     registerBlock(Blocks.RED_MUSHROOM_BLOCK, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "red_mushroom_block");
/* 1253 */     registerBlock(Blocks.DRAGON_EGG, "dragon_egg");
/* 1254 */     registerBlock(Blocks.REPEATING_COMMAND_BLOCK, "repeating_command_block");
/* 1255 */     registerBlock(Blocks.CHAIN_COMMAND_BLOCK, "chain_command_block");
/* 1256 */     registerBlock(Blocks.STRUCTURE_BLOCK, TileEntityStructure.Mode.SAVE.getModeId(), "structure_block");
/* 1257 */     registerBlock(Blocks.STRUCTURE_BLOCK, TileEntityStructure.Mode.LOAD.getModeId(), "structure_block");
/* 1258 */     registerBlock(Blocks.STRUCTURE_BLOCK, TileEntityStructure.Mode.CORNER.getModeId(), "structure_block");
/* 1259 */     registerBlock(Blocks.STRUCTURE_BLOCK, TileEntityStructure.Mode.DATA.getModeId(), "structure_block");
/*      */     
/* 1261 */     if (Reflector.ModelLoader_onRegisterItems.exists())
/*      */     {
/* 1263 */       Reflector.call(Reflector.ModelLoader_onRegisterItems, new Object[] { this.itemModelMesher });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 1269 */     this.itemModelMesher.rebuildCache();
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\RenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
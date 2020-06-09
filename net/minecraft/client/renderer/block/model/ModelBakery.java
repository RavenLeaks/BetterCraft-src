/*      */ package net.minecraft.client.renderer.block.model;
/*      */ 
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.io.Closeable;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Deque;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.renderer.BlockModelShapes;
/*      */ import net.minecraft.client.renderer.block.model.multipart.Multipart;
/*      */ import net.minecraft.client.renderer.block.model.multipart.Selector;
/*      */ import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
/*      */ import net.minecraft.client.renderer.texture.ITextureMapPopulator;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.registry.IRegistry;
/*      */ import net.minecraft.util.registry.RegistrySimple;
/*      */ import net.minecraftforge.common.model.ITransformation;
/*      */ import net.minecraftforge.common.model.TRSRTransformation;
/*      */ import net.minecraftforge.registries.IRegistryDelegate;
/*      */ import optifine.CustomItems;
/*      */ import optifine.Reflector;
/*      */ import optifine.StrUtils;
/*      */ import optifine.TextureUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ModelBakery
/*      */ {
/*   59 */   private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])new ResourceLocation[] { new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/water_overlay"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots"), new ResourceLocation("items/empty_armor_slot_shield"), new ResourceLocation("blocks/shulker_top_white"), new ResourceLocation("blocks/shulker_top_orange"), new ResourceLocation("blocks/shulker_top_magenta"), new ResourceLocation("blocks/shulker_top_light_blue"), new ResourceLocation("blocks/shulker_top_yellow"), new ResourceLocation("blocks/shulker_top_lime"), new ResourceLocation("blocks/shulker_top_pink"), new ResourceLocation("blocks/shulker_top_gray"), new ResourceLocation("blocks/shulker_top_silver"), new ResourceLocation("blocks/shulker_top_cyan"), new ResourceLocation("blocks/shulker_top_purple"), new ResourceLocation("blocks/shulker_top_blue"), new ResourceLocation("blocks/shulker_top_brown"), new ResourceLocation("blocks/shulker_top_green"), new ResourceLocation("blocks/shulker_top_red"), new ResourceLocation("blocks/shulker_top_black") });
/*   60 */   private static final Logger LOGGER = LogManager.getLogger();
/*   61 */   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
/*   62 */   private static final String MISSING_MODEL_MESH = "{    'textures': {       'particle': 'missingno',       'missingno': 'missingno'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}".replaceAll("'", "\"");
/*   63 */   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap();
/*   64 */   private static final Joiner JOINER = Joiner.on(" -> ");
/*      */   private final IResourceManager resourceManager;
/*   66 */   private final Map<ResourceLocation, TextureAtlasSprite> sprites = Maps.newHashMap();
/*   67 */   private final Map<ResourceLocation, ModelBlock> models = Maps.newLinkedHashMap();
/*   68 */   private final Map<ModelResourceLocation, VariantList> variants = Maps.newLinkedHashMap();
/*   69 */   private final Map<ModelBlockDefinition, Collection<ModelResourceLocation>> multipartVariantMap = Maps.newLinkedHashMap();
/*      */   private final TextureMap textureMap;
/*      */   private final BlockModelShapes blockModelShapes;
/*   72 */   private final FaceBakery faceBakery = new FaceBakery();
/*   73 */   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*   74 */   private final RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry = new RegistrySimple();
/*   75 */   private static final String EMPTY_MODEL_RAW = "{    'elements': [        {   'from': [0, 0, 0],            'to': [16, 16, 16],            'faces': {                'down': {'uv': [0, 0, 16, 16], 'texture': '' }            }        }    ]}".replaceAll("'", "\"");
/*   76 */   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize(EMPTY_MODEL_RAW);
/*   77 */   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize(EMPTY_MODEL_RAW);
/*   78 */   private final Map<String, ResourceLocation> itemLocations = Maps.newLinkedHashMap();
/*   79 */   private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions = Maps.newHashMap();
/*   80 */   private final Map<Item, List<String>> variantNames = Maps.newIdentityHashMap();
/*   81 */   private static Map<IRegistryDelegate<Item>, Set<String>> customVariantNames = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   public ModelBakery(IResourceManager resourceManagerIn, TextureMap textureMapIn, BlockModelShapes blockModelShapesIn) {
/*   85 */     this.resourceManager = resourceManagerIn;
/*   86 */     this.textureMap = textureMapIn;
/*   87 */     this.blockModelShapes = blockModelShapesIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
/*   92 */     loadBlocks();
/*   93 */     loadVariantItemModels();
/*   94 */     loadModelsCheck();
/*   95 */     loadSprites();
/*   96 */     makeItemModels();
/*   97 */     bakeBlockModels();
/*   98 */     bakeItemModels();
/*   99 */     return (IRegistry<ModelResourceLocation, IBakedModel>)this.bakedRegistry;
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadBlocks() {
/*  104 */     BlockStateMapper blockstatemapper = this.blockModelShapes.getBlockStateMapper();
/*      */     
/*  106 */     for (Block block : Block.REGISTRY) {
/*      */       
/*  108 */       for (ResourceLocation resourcelocation : blockstatemapper.getBlockstateLocations(block)) {
/*      */ 
/*      */         
/*      */         try {
/*  112 */           loadBlock(blockstatemapper, block, resourcelocation);
/*      */         }
/*  114 */         catch (Exception exception) {
/*      */           
/*  116 */           LOGGER.warn("Unable to load definition " + resourcelocation, exception);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void loadBlock(BlockStateMapper p_loadBlock_1_, Block p_loadBlock_2_, final ResourceLocation p_loadBlock_3_) {
/*  124 */     ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(p_loadBlock_3_);
/*  125 */     Map<IBlockState, ModelResourceLocation> map = p_loadBlock_1_.getVariants(p_loadBlock_2_);
/*      */     
/*  127 */     if (modelblockdefinition.hasMultipartData()) {
/*      */       
/*  129 */       Collection<ModelResourceLocation> collection = Sets.newHashSet(map.values());
/*  130 */       modelblockdefinition.getMultipartData().setStateContainer(p_loadBlock_2_.getBlockState());
/*  131 */       Collection<ModelResourceLocation> collection1 = this.multipartVariantMap.get(modelblockdefinition);
/*      */       
/*  133 */       if (collection1 == null)
/*      */       {
/*  135 */         collection1 = Lists.newArrayList();
/*      */       }
/*      */       
/*  138 */       collection1.addAll(Lists.newArrayList(Iterables.filter(collection, new Predicate<ModelResourceLocation>()
/*      */               {
/*      */                 public boolean apply(@Nullable ModelResourceLocation p_apply_1_)
/*      */                 {
/*  142 */                   return p_loadBlock_3_.equals(p_apply_1_);
/*      */                 }
/*      */               })));
/*  145 */       registerMultipartVariant(modelblockdefinition, collection1);
/*      */     } 
/*      */     
/*  148 */     for (Map.Entry<IBlockState, ModelResourceLocation> entry : map.entrySet()) {
/*      */       
/*  150 */       ModelResourceLocation modelresourcelocation = entry.getValue();
/*      */       
/*  152 */       if (p_loadBlock_3_.equals(modelresourcelocation)) {
/*      */         
/*      */         try {
/*      */           
/*  156 */           if (Reflector.ForgeItem_delegate.exists()) {
/*      */             
/*  158 */             registerVariant(modelblockdefinition, modelresourcelocation);
/*      */             
/*      */             continue;
/*      */           } 
/*  162 */           this.variants.put(modelresourcelocation, modelblockdefinition.getVariant(modelresourcelocation.getVariant()));
/*      */         
/*      */         }
/*  165 */         catch (RuntimeException runtimeexception) {
/*      */           
/*  167 */           if (!modelblockdefinition.hasMultipartData())
/*      */           {
/*  169 */             LOGGER.warn("Unable to load variant: " + modelresourcelocation.getVariant() + " from " + modelresourcelocation, runtimeexception);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadVariantItemModels() {
/*  178 */     this.variants.put(MODEL_MISSING, new VariantList(Lists.newArrayList((Object[])new Variant[] { new Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1) })));
/*  179 */     func_191401_d();
/*  180 */     loadVariantModels();
/*  181 */     loadMultipartVariantModels();
/*  182 */     loadItemModels();
/*  183 */     CustomItems.update();
/*  184 */     CustomItems.loadModels(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_191401_d() {
/*  189 */     ResourceLocation resourcelocation = new ResourceLocation("item_frame");
/*  190 */     ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(resourcelocation);
/*  191 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "normal"));
/*  192 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "map"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerVariant(ModelBlockDefinition blockstateDefinition, ModelResourceLocation location) {
/*      */     try {
/*  199 */       this.variants.put(location, blockstateDefinition.getVariant(location.getVariant()));
/*      */     }
/*  201 */     catch (RuntimeException var4) {
/*      */       
/*  203 */       if (!blockstateDefinition.hasMultipartData())
/*      */       {
/*  205 */         LOGGER.warn("Unable to load variant: {} from {}", location.getVariant(), location);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation location) {
/*  212 */     ResourceLocation resourcelocation = getBlockstateLocation(location);
/*  213 */     ModelBlockDefinition modelblockdefinition = this.blockDefinitions.get(resourcelocation);
/*      */     
/*  215 */     if (modelblockdefinition == null) {
/*      */       
/*  217 */       modelblockdefinition = loadMultipartMBD(location, resourcelocation);
/*  218 */       this.blockDefinitions.put(resourcelocation, modelblockdefinition);
/*      */     } 
/*      */     
/*  221 */     return modelblockdefinition;
/*      */   }
/*      */ 
/*      */   
/*      */   private ModelBlockDefinition loadMultipartMBD(ResourceLocation location, ResourceLocation fileIn) {
/*  226 */     List<ModelBlockDefinition> list = Lists.newArrayList();
/*      */ 
/*      */     
/*      */     try {
/*  230 */       for (IResource iresource : this.resourceManager.getAllResources(fileIn))
/*      */       {
/*  232 */         list.add(loadModelBlockDefinition(location, iresource));
/*      */       }
/*      */     }
/*  235 */     catch (IOException ioexception) {
/*      */       
/*  237 */       throw new RuntimeException("Encountered an exception when loading model definition of model " + fileIn, ioexception);
/*      */     } 
/*      */     
/*  240 */     return new ModelBlockDefinition(list);
/*      */   }
/*      */   
/*      */   private ModelBlockDefinition loadModelBlockDefinition(ResourceLocation location, IResource resource) {
/*      */     ModelBlockDefinition modelblockdefinition;
/*  245 */     InputStream inputstream = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  250 */       inputstream = resource.getInputStream();
/*      */       
/*  252 */       if (Reflector.ForgeModelBlockDefinition_parseFromReader2.exists())
/*      */       {
/*  254 */         modelblockdefinition = (ModelBlockDefinition)Reflector.call(Reflector.ForgeModelBlockDefinition_parseFromReader2, new Object[] { new InputStreamReader(inputstream, StandardCharsets.UTF_8), location });
/*      */       }
/*      */       else
/*      */       {
/*  258 */         modelblockdefinition = ModelBlockDefinition.parseFromReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
/*      */       }
/*      */     
/*  261 */     } catch (Exception exception) {
/*      */       
/*  263 */       throw new RuntimeException("Encountered an exception when loading model definition of '" + location + "' from: '" + resource.getResourceLocation() + "' in resourcepack: '" + resource.getResourcePackName() + "'", exception);
/*      */     }
/*      */     finally {
/*      */       
/*  267 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */     
/*  270 */     return modelblockdefinition;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getBlockstateLocation(ResourceLocation location) {
/*  275 */     return new ResourceLocation(location.getResourceDomain(), "blockstates/" + location.getResourcePath() + ".json");
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadVariantModels() {
/*  280 */     for (Map.Entry<ModelResourceLocation, VariantList> entry : this.variants.entrySet())
/*      */     {
/*  282 */       loadVariantList(entry.getKey(), entry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadMultipartVariantModels() {
/*  288 */     for (Map.Entry<ModelBlockDefinition, Collection<ModelResourceLocation>> entry : this.multipartVariantMap.entrySet()) {
/*      */       
/*  290 */       ModelResourceLocation modelresourcelocation = ((Collection<ModelResourceLocation>)entry.getValue()).iterator().next();
/*      */       
/*  292 */       for (VariantList variantlist : ((ModelBlockDefinition)entry.getKey()).getMultipartVariants())
/*      */       {
/*  294 */         loadVariantList(modelresourcelocation, variantlist);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadVariantList(ModelResourceLocation p_188638_1_, VariantList p_188638_2_) {
/*  301 */     for (Variant variant : p_188638_2_.getVariantList()) {
/*      */       
/*  303 */       ResourceLocation resourcelocation = variant.getModelLocation();
/*      */       
/*  305 */       if (this.models.get(resourcelocation) == null) {
/*      */         
/*      */         try {
/*      */           
/*  309 */           this.models.put(resourcelocation, loadModel(resourcelocation));
/*      */         }
/*  311 */         catch (Exception exception) {
/*      */           
/*  313 */           LOGGER.warn("Unable to load block model: '{}' for variant: '{}': {} ", resourcelocation, p_188638_1_, exception);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private ModelBlock loadModel(ResourceLocation location) throws IOException {
/*      */     ModelBlock modelblock1;
/*  321 */     Reader reader = null;
/*  322 */     IResource iresource = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     try { String s = location.getResourcePath();
/*      */       
/*  329 */       if ("builtin/generated".equals(s)) {
/*      */         
/*  331 */         ModelBlock modelblock4 = MODEL_GENERATED;
/*  332 */         return modelblock4;
/*      */       } 
/*      */       
/*  335 */       if (!"builtin/entity".equals(s))
/*      */       {
/*  337 */         if (s.startsWith("builtin/")) {
/*      */           
/*  339 */           String s2 = s.substring("builtin/".length());
/*  340 */           String s1 = BUILT_IN_MODELS.get(s2);
/*      */           
/*  342 */           if (s1 == null)
/*      */           {
/*  344 */             throw new FileNotFoundException(location.toString());
/*      */           }
/*      */           
/*  347 */           reader = new StringReader(s1);
/*      */         }
/*      */         else {
/*      */           
/*  351 */           location = getModelLocation(location);
/*  352 */           iresource = this.resourceManager.getResource(location);
/*  353 */           reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
/*      */         } 
/*      */         
/*  356 */         ModelBlock modelblock3 = ModelBlock.deserialize(reader);
/*  357 */         modelblock3.name = location.toString();
/*  358 */         String s3 = TextureUtils.getBasePath(location.getResourcePath());
/*  359 */         fixModelLocations(modelblock3, s3);
/*  360 */         ModelBlock modelblock2 = modelblock3;
/*  361 */         return modelblock2;
/*      */       
/*      */       }
/*      */       
/*      */        }
/*      */     
/*      */     finally
/*      */     
/*  369 */     { IOUtils.closeQuietly(reader);
/*  370 */       IOUtils.closeQuietly((Closeable)iresource); }  IOUtils.closeQuietly(reader); IOUtils.closeQuietly((Closeable)iresource);
/*      */ 
/*      */     
/*  373 */     return modelblock1;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getModelLocation(ResourceLocation location) {
/*  378 */     String s = location.getResourcePath();
/*      */     
/*  380 */     if (!s.startsWith("mcpatcher") && !s.startsWith("optifine"))
/*      */     {
/*  382 */       return new ResourceLocation(location.getResourceDomain(), "models/" + location.getResourcePath() + ".json");
/*      */     }
/*      */ 
/*      */     
/*  386 */     if (!s.endsWith(".json"))
/*      */     {
/*  388 */       location = new ResourceLocation(location.getResourceDomain(), String.valueOf(s) + ".json");
/*      */     }
/*      */     
/*  391 */     return location;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadItemModels() {
/*  397 */     registerVariantNames();
/*      */     
/*  399 */     for (Item item : Item.REGISTRY) {
/*      */       
/*  401 */       for (String s : getVariantNames(item)) {
/*      */         
/*  403 */         ResourceLocation resourcelocation = getItemLocation(s);
/*  404 */         ResourceLocation resourcelocation1 = (ResourceLocation)Item.REGISTRY.getNameForObject(item);
/*  405 */         loadItemModel(s, resourcelocation, resourcelocation1);
/*      */         
/*  407 */         if (item.hasCustomProperties()) {
/*      */           
/*  409 */           ModelBlock modelblock = this.models.get(resourcelocation);
/*      */           
/*  411 */           if (modelblock != null)
/*      */           {
/*  413 */             for (ResourceLocation resourcelocation2 : modelblock.getOverrideLocations())
/*      */             {
/*  415 */               loadItemModel(resourcelocation2.toString(), resourcelocation2, resourcelocation1);
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadItemModel(String variantName, ResourceLocation location, ResourceLocation itemName) {
/*  425 */     this.itemLocations.put(variantName, location);
/*      */     
/*  427 */     if (this.models.get(location) == null) {
/*      */       
/*      */       try {
/*      */         
/*  431 */         ModelBlock modelblock = loadModel(location);
/*  432 */         this.models.put(location, modelblock);
/*      */       }
/*  434 */       catch (Exception exception1) {
/*      */         
/*  436 */         LOGGER.warn("Unable to load item model: '{}' for item: '{}'", location, itemName);
/*  437 */         LOGGER.warn(String.valueOf(exception1.getClass().getName()) + ": " + exception1.getMessage());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerVariantNames() {
/*  444 */     this.variantNames.clear();
/*  445 */     this.variantNames.put(Item.getItemFromBlock(Blocks.STONE), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
/*  446 */     this.variantNames.put(Item.getItemFromBlock(Blocks.DIRT), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
/*  447 */     this.variantNames.put(Item.getItemFromBlock(Blocks.PLANKS), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
/*  448 */     this.variantNames.put(Item.getItemFromBlock(Blocks.SAPLING), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
/*  449 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.SAND), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
/*  450 */     this.variantNames.put(Item.getItemFromBlock(Blocks.LOG), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
/*  451 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.LEAVES), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
/*  452 */     this.variantNames.put(Item.getItemFromBlock(Blocks.SPONGE), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
/*  453 */     this.variantNames.put(Item.getItemFromBlock(Blocks.SANDSTONE), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
/*  454 */     this.variantNames.put(Item.getItemFromBlock(Blocks.RED_SANDSTONE), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
/*  455 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.TALLGRASS), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
/*  456 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.DEADBUSH), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
/*  457 */     this.variantNames.put(Item.getItemFromBlock(Blocks.WOOL), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
/*  458 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER), Lists.newArrayList((Object[])new String[] { "dandelion" }));
/*  459 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.RED_FLOWER), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
/*  460 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.STONE_SLAB), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
/*  461 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.STONE_SLAB2), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
/*  462 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.STAINED_GLASS), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
/*  463 */     this.variantNames.put(Item.getItemFromBlock(Blocks.MONSTER_EGG), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
/*  464 */     this.variantNames.put(Item.getItemFromBlock(Blocks.STONEBRICK), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
/*  465 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.WOODEN_SLAB), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
/*  466 */     this.variantNames.put(Item.getItemFromBlock(Blocks.COBBLESTONE_WALL), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
/*  467 */     this.variantNames.put(Item.getItemFromBlock(Blocks.ANVIL), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
/*  468 */     this.variantNames.put(Item.getItemFromBlock(Blocks.QUARTZ_BLOCK), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
/*  469 */     this.variantNames.put(Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
/*  470 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.STAINED_GLASS_PANE), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
/*  471 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.LEAVES2), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
/*  472 */     this.variantNames.put(Item.getItemFromBlock(Blocks.LOG2), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
/*  473 */     this.variantNames.put(Item.getItemFromBlock(Blocks.PRISMARINE), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
/*  474 */     this.variantNames.put(Item.getItemFromBlock(Blocks.CARPET), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
/*  475 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.DOUBLE_PLANT), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
/*  476 */     this.variantNames.put(Items.COAL, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
/*  477 */     this.variantNames.put(Items.FISH, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
/*  478 */     this.variantNames.put(Items.COOKED_FISH, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
/*  479 */     this.variantNames.put(Items.DYE, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
/*  480 */     this.variantNames.put(Items.POTIONITEM, Lists.newArrayList((Object[])new String[] { "bottle_drinkable" }));
/*  481 */     this.variantNames.put(Items.SKULL, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper", "skull_dragon" }));
/*  482 */     this.variantNames.put(Items.SPLASH_POTION, Lists.newArrayList((Object[])new String[] { "bottle_splash" }));
/*  483 */     this.variantNames.put(Items.LINGERING_POTION, Lists.newArrayList((Object[])new String[] { "bottle_lingering" }));
/*  484 */     this.variantNames.put(Item.getItemFromBlock(Blocks.field_192443_dR), Lists.newArrayList((Object[])new String[] { "black_concrete", "red_concrete", "green_concrete", "brown_concrete", "blue_concrete", "purple_concrete", "cyan_concrete", "silver_concrete", "gray_concrete", "pink_concrete", "lime_concrete", "yellow_concrete", "light_blue_concrete", "magenta_concrete", "orange_concrete", "white_concrete" }));
/*  485 */     this.variantNames.put(Item.getItemFromBlock(Blocks.field_192444_dS), Lists.newArrayList((Object[])new String[] { "black_concrete_powder", "red_concrete_powder", "green_concrete_powder", "brown_concrete_powder", "blue_concrete_powder", "purple_concrete_powder", "cyan_concrete_powder", "silver_concrete_powder", "gray_concrete_powder", "pink_concrete_powder", "lime_concrete_powder", "yellow_concrete_powder", "light_blue_concrete_powder", "magenta_concrete_powder", "orange_concrete_powder", "white_concrete_powder" }));
/*  486 */     this.variantNames.put(Item.getItemFromBlock(Blocks.AIR), Collections.emptyList());
/*  487 */     this.variantNames.put(Item.getItemFromBlock(Blocks.OAK_FENCE_GATE), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
/*  488 */     this.variantNames.put(Item.getItemFromBlock(Blocks.OAK_FENCE), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
/*  489 */     this.variantNames.put(Items.OAK_DOOR, Lists.newArrayList((Object[])new String[] { "oak_door" }));
/*  490 */     this.variantNames.put(Items.BOAT, Lists.newArrayList((Object[])new String[] { "oak_boat" }));
/*  491 */     this.variantNames.put(Items.field_190929_cY, Lists.newArrayList((Object[])new String[] { "totem" }));
/*      */     
/*  493 */     for (Map.Entry<IRegistryDelegate<Item>, Set<String>> entry : customVariantNames.entrySet())
/*      */     {
/*  495 */       this.variantNames.put((Item)((IRegistryDelegate)entry.getKey()).get(), Lists.newArrayList(((Set)entry.getValue()).iterator()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private List<String> getVariantNames(Item stack) {
/*  501 */     List<String> list = this.variantNames.get(stack);
/*      */     
/*  503 */     if (list == null)
/*      */     {
/*  505 */       list = Collections.singletonList(((ResourceLocation)Item.REGISTRY.getNameForObject(stack)).toString());
/*      */     }
/*      */     
/*  508 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getItemLocation(String location) {
/*  513 */     ResourceLocation resourcelocation = new ResourceLocation(location);
/*      */     
/*  515 */     if (Reflector.ForgeHooksClient.exists())
/*      */     {
/*  517 */       resourcelocation = new ResourceLocation(location.replaceAll("#.*", ""));
/*      */     }
/*      */     
/*  520 */     return new ResourceLocation(resourcelocation.getResourceDomain(), "item/" + resourcelocation.getResourcePath());
/*      */   }
/*      */ 
/*      */   
/*      */   private void bakeBlockModels() {
/*  525 */     for (ModelResourceLocation modelresourcelocation : this.variants.keySet()) {
/*      */       
/*  527 */       IBakedModel ibakedmodel = createRandomModelForVariantList(this.variants.get(modelresourcelocation), modelresourcelocation.toString());
/*      */       
/*  529 */       if (ibakedmodel != null)
/*      */       {
/*  531 */         this.bakedRegistry.putObject(modelresourcelocation, ibakedmodel);
/*      */       }
/*      */     } 
/*      */     
/*  535 */     for (Map.Entry<ModelBlockDefinition, Collection<ModelResourceLocation>> entry : this.multipartVariantMap.entrySet()) {
/*      */       
/*  537 */       ModelBlockDefinition modelblockdefinition = entry.getKey();
/*  538 */       Multipart multipart = modelblockdefinition.getMultipartData();
/*  539 */       String s = ((ResourceLocation)Block.REGISTRY.getNameForObject(multipart.getStateContainer().getBlock())).toString();
/*  540 */       MultipartBakedModel.Builder multipartbakedmodel$builder = new MultipartBakedModel.Builder();
/*      */       
/*  542 */       for (Selector selector : multipart.getSelectors()) {
/*      */         
/*  544 */         IBakedModel ibakedmodel1 = createRandomModelForVariantList(selector.getVariantList(), "selector of " + s);
/*      */         
/*  546 */         if (ibakedmodel1 != null)
/*      */         {
/*  548 */           multipartbakedmodel$builder.putModel(selector.getPredicate(multipart.getStateContainer()), ibakedmodel1);
/*      */         }
/*      */       } 
/*      */       
/*  552 */       IBakedModel ibakedmodel2 = multipartbakedmodel$builder.makeMultipartModel();
/*      */       
/*  554 */       for (ModelResourceLocation modelresourcelocation1 : entry.getValue()) {
/*      */         
/*  556 */         if (!modelblockdefinition.hasVariant(modelresourcelocation1.getVariant()))
/*      */         {
/*  558 */           this.bakedRegistry.putObject(modelresourcelocation1, ibakedmodel2);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private IBakedModel createRandomModelForVariantList(VariantList variantsIn, String modelLocation) {
/*  567 */     if (variantsIn.getVariantList().isEmpty())
/*      */     {
/*  569 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  573 */     WeightedBakedModel.Builder weightedbakedmodel$builder = new WeightedBakedModel.Builder();
/*  574 */     int i = 0;
/*      */     
/*  576 */     for (Variant variant : variantsIn.getVariantList()) {
/*      */       
/*  578 */       ModelBlock modelblock = this.models.get(variant.getModelLocation());
/*      */       
/*  580 */       if (modelblock != null && modelblock.isResolved()) {
/*      */         
/*  582 */         if (modelblock.getElements().isEmpty()) {
/*      */           
/*  584 */           LOGGER.warn("Missing elements for: {}", modelLocation);
/*      */           
/*      */           continue;
/*      */         } 
/*  588 */         IBakedModel ibakedmodel = bakeModel(modelblock, variant.getRotation(), variant.isUvLock());
/*      */         
/*  590 */         if (ibakedmodel != null) {
/*      */           
/*  592 */           i++;
/*  593 */           weightedbakedmodel$builder.add(ibakedmodel, variant.getWeight());
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  599 */       LOGGER.warn("Missing model for: {}", modelLocation);
/*      */     } 
/*      */ 
/*      */     
/*  603 */     IBakedModel ibakedmodel1 = null;
/*      */     
/*  605 */     if (i == 0) {
/*      */       
/*  607 */       LOGGER.warn("No weighted models for: {}", modelLocation);
/*      */     }
/*  609 */     else if (i == 1) {
/*      */       
/*  611 */       ibakedmodel1 = weightedbakedmodel$builder.first();
/*      */     }
/*      */     else {
/*      */       
/*  615 */       ibakedmodel1 = weightedbakedmodel$builder.build();
/*      */     } 
/*      */     
/*  618 */     return ibakedmodel1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void bakeItemModels() {
/*  624 */     for (Map.Entry<String, ResourceLocation> entry : this.itemLocations.entrySet()) {
/*      */       
/*  626 */       ResourceLocation resourcelocation = entry.getValue();
/*  627 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(entry.getKey(), "inventory");
/*      */       
/*  629 */       if (Reflector.ForgeHooksClient.exists())
/*      */       {
/*  631 */         modelresourcelocation = (ModelResourceLocation)Reflector.call(Reflector.ModelLoader_getInventoryVariant, new Object[] { entry.getKey() });
/*      */       }
/*      */       
/*  634 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*      */       
/*  636 */       if (modelblock != null && modelblock.isResolved()) {
/*      */         
/*  638 */         if (modelblock.getElements().isEmpty()) {
/*      */           
/*  640 */           LOGGER.warn("Missing elements for: {}", resourcelocation); continue;
/*      */         } 
/*  642 */         if (isCustomRenderer(modelblock)) {
/*      */           
/*  644 */           this.bakedRegistry.putObject(modelresourcelocation, new BuiltInModel(modelblock.getAllTransforms(), modelblock.createOverrides()));
/*      */           
/*      */           continue;
/*      */         } 
/*  648 */         IBakedModel ibakedmodel = bakeModel(modelblock, ModelRotation.X0_Y0, false);
/*      */         
/*  650 */         if (ibakedmodel != null)
/*      */         {
/*  652 */           this.bakedRegistry.putObject(modelresourcelocation, ibakedmodel);
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  658 */       LOGGER.warn("Missing model for: {}", resourcelocation);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<ResourceLocation> getVariantsTextureLocations() {
/*  665 */     Set<ResourceLocation> set = Sets.newHashSet();
/*  666 */     List<ModelResourceLocation> list = Lists.newArrayList(this.variants.keySet());
/*  667 */     Collections.sort(list, new Comparator<ModelResourceLocation>()
/*      */         {
/*      */           public int compare(ModelResourceLocation p_compare_1_, ModelResourceLocation p_compare_2_)
/*      */           {
/*  671 */             return p_compare_1_.toString().compareTo(p_compare_2_.toString());
/*      */           }
/*      */         });
/*      */     
/*  675 */     for (ModelResourceLocation modelresourcelocation : list) {
/*      */       
/*  677 */       VariantList variantlist = this.variants.get(modelresourcelocation);
/*      */       
/*  679 */       for (Variant variant : variantlist.getVariantList()) {
/*      */         
/*  681 */         ModelBlock modelblock = this.models.get(variant.getModelLocation());
/*      */         
/*  683 */         if (modelblock == null) {
/*      */           
/*  685 */           LOGGER.warn("Missing model for: {}", modelresourcelocation);
/*      */           
/*      */           continue;
/*      */         } 
/*  689 */         set.addAll(getTextureLocations(modelblock));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  694 */     for (ModelBlockDefinition modelblockdefinition : this.multipartVariantMap.keySet()) {
/*      */       
/*  696 */       for (VariantList variantlist1 : modelblockdefinition.getMultipartData().getVariants()) {
/*      */         
/*  698 */         for (Variant variant1 : variantlist1.getVariantList()) {
/*      */           
/*  700 */           ModelBlock modelblock1 = this.models.get(variant1.getModelLocation());
/*      */           
/*  702 */           if (modelblock1 == null) {
/*      */             
/*  704 */             LOGGER.warn("Missing model for: {}", Block.REGISTRY.getNameForObject(modelblockdefinition.getMultipartData().getStateContainer().getBlock()));
/*      */             
/*      */             continue;
/*      */           } 
/*  708 */           set.addAll(getTextureLocations(modelblock1));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  714 */     set.addAll(LOCATIONS_BUILTIN_TEXTURES);
/*  715 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IBakedModel bakeModel(ModelBlock modelBlockIn, ModelRotation modelRotationIn, boolean uvLocked) {
/*  722 */     TextureAtlasSprite textureatlassprite = this.sprites.get(new ResourceLocation(modelBlockIn.resolveTextureName("particle")));
/*  723 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(modelBlockIn, modelBlockIn.createOverrides())).setTexture(textureatlassprite);
/*      */     
/*  725 */     if (modelBlockIn.getElements().isEmpty())
/*      */     {
/*  727 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  731 */     for (BlockPart blockpart : modelBlockIn.getElements()) {
/*      */       
/*  733 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/*      */         
/*  735 */         BlockPartFace blockpartface = blockpart.mapFaces.get(enumfacing);
/*  736 */         TextureAtlasSprite textureatlassprite1 = this.sprites.get(new ResourceLocation(modelBlockIn.resolveTextureName(blockpartface.texture)));
/*      */         
/*  738 */         if (blockpartface.cullFace == null) {
/*      */           
/*  740 */           simplebakedmodel$builder.addGeneralQuad(makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
/*      */           
/*      */           continue;
/*      */         } 
/*  744 */         simplebakedmodel$builder.addFaceQuad(modelRotationIn.rotateFace(blockpartface.cullFace), makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  749 */     return simplebakedmodel$builder.makeBakedModel();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected IBakedModel bakeModel(ModelBlock p_bakeModel_1_, ITransformation p_bakeModel_2_, boolean p_bakeModel_3_) {
/*  755 */     TextureAtlasSprite textureatlassprite = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName("particle")));
/*  756 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(p_bakeModel_1_, p_bakeModel_1_.createOverrides())).setTexture(textureatlassprite);
/*      */     
/*  758 */     if (p_bakeModel_1_.getElements().isEmpty())
/*      */     {
/*  760 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  764 */     for (BlockPart blockpart : p_bakeModel_1_.getElements()) {
/*      */       
/*  766 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/*      */         
/*  768 */         BlockPartFace blockpartface = blockpart.mapFaces.get(enumfacing);
/*  769 */         TextureAtlasSprite textureatlassprite1 = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName(blockpartface.texture)));
/*  770 */         boolean flag = true;
/*      */         
/*  772 */         if (Reflector.ForgeHooksClient.exists())
/*      */         {
/*  774 */           flag = TRSRTransformation.isInteger(p_bakeModel_2_.getMatrix());
/*      */         }
/*      */         
/*  777 */         if (blockpartface.cullFace != null && flag) {
/*      */           
/*  779 */           simplebakedmodel$builder.addFaceQuad(p_bakeModel_2_.rotate(blockpartface.cullFace), makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_));
/*      */           
/*      */           continue;
/*      */         } 
/*  783 */         simplebakedmodel$builder.addGeneralQuad(makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  788 */     return simplebakedmodel$builder.makeBakedModel();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private BakedQuad makeBakedQuad(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, ModelRotation p_177589_5_, boolean p_177589_6_) {
/*  794 */     return Reflector.ForgeHooksClient.exists() ? makeBakedQuad(p_177589_1_, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_6_) : this.faceBakery.makeBakedQuad(p_177589_1_.positionFrom, p_177589_1_.positionTo, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.partRotation, p_177589_6_, p_177589_1_.shade);
/*      */   }
/*      */ 
/*      */   
/*      */   protected BakedQuad makeBakedQuad(BlockPart p_makeBakedQuad_1_, BlockPartFace p_makeBakedQuad_2_, TextureAtlasSprite p_makeBakedQuad_3_, EnumFacing p_makeBakedQuad_4_, ITransformation p_makeBakedQuad_5_, boolean p_makeBakedQuad_6_) {
/*  799 */     return this.faceBakery.makeBakedQuad(p_makeBakedQuad_1_.positionFrom, p_makeBakedQuad_1_.positionTo, p_makeBakedQuad_2_, p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_5_, p_makeBakedQuad_1_.partRotation, p_makeBakedQuad_6_, p_makeBakedQuad_1_.shade);
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadModelsCheck() {
/*  804 */     loadModels();
/*      */     
/*  806 */     for (ModelBlock modelblock : this.models.values())
/*      */     {
/*  808 */       modelblock.getParentFromMap(this.models);
/*      */     }
/*      */     
/*  811 */     ModelBlock.checkModelHierarchy(this.models);
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadModels() {
/*  816 */     Deque<ResourceLocation> deque = Queues.newArrayDeque();
/*  817 */     Set<ResourceLocation> set = Sets.newHashSet();
/*      */     
/*  819 */     for (ResourceLocation resourcelocation : this.models.keySet()) {
/*      */       
/*  821 */       set.add(resourcelocation);
/*  822 */       addModelParentLocation(deque, set, this.models.get(resourcelocation));
/*      */     } 
/*      */     
/*  825 */     while (!deque.isEmpty()) {
/*      */       
/*  827 */       ResourceLocation resourcelocation1 = deque.pop();
/*      */ 
/*      */       
/*      */       try {
/*  831 */         if (this.models.get(resourcelocation1) != null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  836 */         ModelBlock modelblock = loadModel(resourcelocation1);
/*  837 */         this.models.put(resourcelocation1, modelblock);
/*  838 */         addModelParentLocation(deque, set, modelblock);
/*      */       }
/*  840 */       catch (Exception var5) {
/*      */         
/*  842 */         LOGGER.warn("In parent chain: {}; unable to load model: '{}'", JOINER.join(getParentPath(resourcelocation1)), resourcelocation1);
/*      */       } 
/*      */       
/*  845 */       set.add(resourcelocation1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addModelParentLocation(Deque<ResourceLocation> p_188633_1_, Set<ResourceLocation> p_188633_2_, ModelBlock p_188633_3_) {
/*  851 */     ResourceLocation resourcelocation = p_188633_3_.getParentLocation();
/*      */     
/*  853 */     if (resourcelocation != null && !p_188633_2_.contains(resourcelocation))
/*      */     {
/*  855 */       p_188633_1_.add(resourcelocation);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private List<ResourceLocation> getParentPath(ResourceLocation p_177573_1_) {
/*  861 */     List<ResourceLocation> list = Lists.newArrayList((Object[])new ResourceLocation[] { p_177573_1_ });
/*  862 */     ResourceLocation resourcelocation = p_177573_1_;
/*      */     
/*  864 */     while ((resourcelocation = getParentLocation(resourcelocation)) != null)
/*      */     {
/*  866 */       list.add(0, resourcelocation);
/*      */     }
/*      */     
/*  869 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ResourceLocation getParentLocation(ResourceLocation p_177576_1_) {
/*  875 */     for (Map.Entry<ResourceLocation, ModelBlock> entry : this.models.entrySet()) {
/*      */       
/*  877 */       ModelBlock modelblock = entry.getValue();
/*      */       
/*  879 */       if (modelblock != null && p_177576_1_.equals(modelblock.getParentLocation()))
/*      */       {
/*  881 */         return entry.getKey();
/*      */       }
/*      */     } 
/*      */     
/*  885 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<ResourceLocation> getTextureLocations(ModelBlock p_177585_1_) {
/*  890 */     Set<ResourceLocation> set = Sets.newHashSet();
/*      */     
/*  892 */     for (BlockPart blockpart : p_177585_1_.getElements()) {
/*      */       
/*  894 */       for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*      */         
/*  896 */         ResourceLocation resourcelocation = new ResourceLocation(p_177585_1_.resolveTextureName(blockpartface.texture));
/*  897 */         set.add(resourcelocation);
/*      */       } 
/*      */     } 
/*      */     
/*  901 */     set.add(new ResourceLocation(p_177585_1_.resolveTextureName("particle")));
/*  902 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadSprites() {
/*  907 */     final Set<ResourceLocation> set = getVariantsTextureLocations();
/*  908 */     set.addAll(getItemsTextureLocations());
/*  909 */     set.remove(TextureMap.LOCATION_MISSING_TEXTURE);
/*  910 */     ITextureMapPopulator itexturemappopulator = new ITextureMapPopulator()
/*      */       {
/*      */         public void registerSprites(TextureMap textureMapIn)
/*      */         {
/*  914 */           for (ResourceLocation resourcelocation : set) {
/*      */             
/*  916 */             TextureAtlasSprite textureatlassprite = textureMapIn.registerSprite(resourcelocation);
/*  917 */             ModelBakery.this.sprites.put(resourcelocation, textureatlassprite);
/*      */           } 
/*      */         }
/*      */       };
/*  921 */     this.textureMap.loadSprites(this.resourceManager, itexturemappopulator);
/*  922 */     this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<ResourceLocation> getItemsTextureLocations() {
/*  927 */     Set<ResourceLocation> set = Sets.newHashSet();
/*      */     
/*  929 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*      */       
/*  931 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*      */       
/*  933 */       if (modelblock != null) {
/*      */         
/*  935 */         set.add(new ResourceLocation(modelblock.resolveTextureName("particle")));
/*      */         
/*  937 */         if (hasItemModel(modelblock)) {
/*      */           
/*  939 */           for (String s : ItemModelGenerator.LAYERS)
/*      */           {
/*  941 */             set.add(new ResourceLocation(modelblock.resolveTextureName(s))); } 
/*      */           continue;
/*      */         } 
/*  944 */         if (!isCustomRenderer(modelblock))
/*      */         {
/*  946 */           for (BlockPart blockpart : modelblock.getElements()) {
/*      */             
/*  948 */             for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*      */               
/*  950 */               ResourceLocation resourcelocation1 = new ResourceLocation(modelblock.resolveTextureName(blockpartface.texture));
/*  951 */               set.add(resourcelocation1);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  958 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean hasItemModel(@Nullable ModelBlock p_177581_1_) {
/*  963 */     if (p_177581_1_ == null)
/*      */     {
/*  965 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  969 */     return (p_177581_1_.getRootModel() == MODEL_GENERATED);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isCustomRenderer(@Nullable ModelBlock p_177587_1_) {
/*  975 */     if (p_177587_1_ == null)
/*      */     {
/*  977 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  981 */     ModelBlock modelblock = p_177587_1_.getRootModel();
/*  982 */     return (modelblock == MODEL_ENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void makeItemModels() {
/*  988 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*      */       
/*  990 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*      */       
/*  992 */       if (hasItemModel(modelblock)) {
/*      */         
/*  994 */         ModelBlock modelblock1 = makeItemModel(modelblock);
/*      */         
/*  996 */         if (modelblock1 != null)
/*      */         {
/*  998 */           modelblock1.name = resourcelocation.toString();
/*      */         }
/*      */         
/* 1001 */         this.models.put(resourcelocation, modelblock1); continue;
/*      */       } 
/* 1003 */       if (isCustomRenderer(modelblock))
/*      */       {
/* 1005 */         this.models.put(resourcelocation, modelblock);
/*      */       }
/*      */     } 
/*      */     
/* 1009 */     for (TextureAtlasSprite textureatlassprite : this.sprites.values()) {
/*      */       
/* 1011 */       if (!textureatlassprite.hasAnimationMetadata())
/*      */       {
/* 1013 */         textureatlassprite.clearFramesTextureData();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ModelBlock makeItemModel(ModelBlock p_177582_1_) {
/* 1020 */     return this.itemModelGenerator.makeItemModel(this.textureMap, p_177582_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ModelBlock getModelBlock(ResourceLocation p_getModelBlock_1_) {
/* 1025 */     ModelBlock modelblock = this.models.get(p_getModelBlock_1_);
/* 1026 */     return modelblock;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void fixModelLocations(ModelBlock p_fixModelLocations_0_, String p_fixModelLocations_1_) {
/* 1031 */     ResourceLocation resourcelocation = fixModelLocation(p_fixModelLocations_0_.parentLocation, p_fixModelLocations_1_);
/*      */     
/* 1033 */     if (resourcelocation != p_fixModelLocations_0_.parentLocation)
/*      */     {
/* 1035 */       p_fixModelLocations_0_.parentLocation = resourcelocation;
/*      */     }
/*      */     
/* 1038 */     if (p_fixModelLocations_0_.textures != null)
/*      */     {
/* 1040 */       for (Map.Entry<String, String> entry : p_fixModelLocations_0_.textures.entrySet()) {
/*      */         
/* 1042 */         String s = entry.getValue();
/* 1043 */         String s1 = fixResourcePath(s, p_fixModelLocations_1_);
/*      */         
/* 1045 */         if (s1 != s)
/*      */         {
/* 1047 */           entry.setValue(s1);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ResourceLocation fixModelLocation(ResourceLocation p_fixModelLocation_0_, String p_fixModelLocation_1_) {
/* 1055 */     if (p_fixModelLocation_0_ != null && p_fixModelLocation_1_ != null) {
/*      */       
/* 1057 */       if (!p_fixModelLocation_0_.getResourceDomain().equals("minecraft"))
/*      */       {
/* 1059 */         return p_fixModelLocation_0_;
/*      */       }
/*      */ 
/*      */       
/* 1063 */       String s = p_fixModelLocation_0_.getResourcePath();
/* 1064 */       String s1 = fixResourcePath(s, p_fixModelLocation_1_);
/*      */       
/* 1066 */       if (s1 != s)
/*      */       {
/* 1068 */         p_fixModelLocation_0_ = new ResourceLocation(p_fixModelLocation_0_.getResourceDomain(), s1);
/*      */       }
/*      */       
/* 1071 */       return p_fixModelLocation_0_;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1076 */     return p_fixModelLocation_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixResourcePath(String p_fixResourcePath_0_, String p_fixResourcePath_1_) {
/* 1082 */     p_fixResourcePath_0_ = TextureUtils.fixResourcePath(p_fixResourcePath_0_, p_fixResourcePath_1_);
/* 1083 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".json");
/* 1084 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".png");
/* 1085 */     return p_fixResourcePath_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerMultipartVariant(ModelBlockDefinition p_registerMultipartVariant_1_, Collection<ModelResourceLocation> p_registerMultipartVariant_2_) {
/* 1090 */     this.multipartVariantMap.put(p_registerMultipartVariant_1_, p_registerMultipartVariant_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerItemVariants(Item p_registerItemVariants_0_, ResourceLocation... p_registerItemVariants_1_) {
/* 1095 */     IRegistryDelegate<Item> iregistrydelegate = (IRegistryDelegate)Reflector.getFieldValue(p_registerItemVariants_0_, Reflector.ForgeItem_delegate);
/*      */     
/* 1097 */     if (!customVariantNames.containsKey(iregistrydelegate))
/*      */     {
/* 1099 */       customVariantNames.put(iregistrydelegate, Sets.newHashSet()); }  byte b;
/*      */     int i;
/*      */     ResourceLocation[] arrayOfResourceLocation;
/* 1102 */     for (i = (arrayOfResourceLocation = p_registerItemVariants_1_).length, b = 0; b < i; ) { ResourceLocation resourcelocation = arrayOfResourceLocation[b];
/*      */       
/* 1104 */       ((Set<String>)customVariantNames.get(iregistrydelegate)).add(resourcelocation.toString());
/*      */       b++; }
/*      */   
/*      */   }
/*      */   
/*      */   static {
/* 1110 */     BUILT_IN_MODELS.put("missing", MISSING_MODEL_MESH);
/* 1111 */     MODEL_GENERATED.name = "generation marker";
/* 1112 */     MODEL_ENTITY.name = "block entity marker";
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ModelBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
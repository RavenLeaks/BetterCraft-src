/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ModelBlock
/*     */ {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   @VisibleForTesting
/*  32 */   static final Gson SERIALIZER = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer()).create();
/*     */   private final List<BlockPart> elements;
/*     */   private final boolean gui3d;
/*     */   private final boolean ambientOcclusion;
/*     */   private final ItemCameraTransforms cameraTransforms;
/*     */   private final List<ItemOverride> overrides;
/*  38 */   public String name = "";
/*     */   
/*     */   @VisibleForTesting
/*     */   protected final Map<String, String> textures;
/*     */   @VisibleForTesting
/*     */   protected ModelBlock parent;
/*     */   @VisibleForTesting
/*     */   protected ResourceLocation parentLocation;
/*     */   
/*     */   public static ModelBlock deserialize(Reader readerIn) {
/*  48 */     return (ModelBlock)JsonUtils.gsonDeserialize(SERIALIZER, readerIn, ModelBlock.class, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelBlock deserialize(String jsonString) {
/*  53 */     return deserialize(new StringReader(jsonString));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlock(@Nullable ResourceLocation parentLocationIn, List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn, List<ItemOverride> overridesIn) {
/*  58 */     this.elements = elementsIn;
/*  59 */     this.ambientOcclusion = ambientOcclusionIn;
/*  60 */     this.gui3d = gui3dIn;
/*  61 */     this.textures = texturesIn;
/*  62 */     this.parentLocation = parentLocationIn;
/*  63 */     this.cameraTransforms = cameraTransformsIn;
/*  64 */     this.overrides = overridesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPart> getElements() {
/*  69 */     return (this.elements.isEmpty() && hasParent()) ? this.parent.getElements() : this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasParent() {
/*  74 */     return (this.parent != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  79 */     return hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  84 */     return this.gui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isResolved() {
/*  89 */     return !(this.parentLocation != null && (this.parent == null || !this.parent.isResolved()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void getParentFromMap(Map<ResourceLocation, ModelBlock> p_178299_1_) {
/*  94 */     if (this.parentLocation != null)
/*     */     {
/*  96 */       this.parent = p_178299_1_.get(this.parentLocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getOverrideLocations() {
/* 102 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 104 */     for (ItemOverride itemoverride : this.overrides)
/*     */     {
/* 106 */       set.add(itemoverride.getLocation());
/*     */     }
/*     */     
/* 109 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ItemOverride> getOverrides() {
/* 114 */     return this.overrides;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrideList createOverrides() {
/* 119 */     return this.overrides.isEmpty() ? ItemOverrideList.NONE : new ItemOverrideList(this.overrides);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTexturePresent(String textureName) {
/* 124 */     return !"missingno".equals(resolveTextureName(textureName));
/*     */   }
/*     */ 
/*     */   
/*     */   public String resolveTextureName(String textureName) {
/* 129 */     if (!startsWithHash(textureName))
/*     */     {
/* 131 */       textureName = String.valueOf('#') + textureName;
/*     */     }
/*     */     
/* 134 */     return resolveTextureName(textureName, new Bookkeep(this, null));
/*     */   }
/*     */ 
/*     */   
/*     */   private String resolveTextureName(String textureName, Bookkeep p_178302_2_) {
/* 139 */     if (startsWithHash(textureName)) {
/*     */       
/* 141 */       if (this == p_178302_2_.modelExt) {
/*     */         
/* 143 */         LOGGER.warn("Unable to resolve texture due to upward reference: {} in {}", textureName, this.name);
/* 144 */         return "missingno";
/*     */       } 
/*     */ 
/*     */       
/* 148 */       String s = this.textures.get(textureName.substring(1));
/*     */       
/* 150 */       if (s == null && hasParent())
/*     */       {
/* 152 */         s = this.parent.resolveTextureName(textureName, p_178302_2_);
/*     */       }
/*     */       
/* 155 */       p_178302_2_.modelExt = this;
/*     */       
/* 157 */       if (s != null && startsWithHash(s))
/*     */       {
/* 159 */         s = p_178302_2_.model.resolveTextureName(s, p_178302_2_);
/*     */       }
/*     */       
/* 162 */       return (s != null && !startsWithHash(s)) ? s : "missingno";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 167 */     return textureName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startsWithHash(String hash) {
/* 173 */     return (hash.charAt(0) == '#');
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getParentLocation() {
/* 179 */     return this.parentLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlock getRootModel() {
/* 184 */     return hasParent() ? this.parent.getRootModel() : this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getAllTransforms() {
/* 189 */     ItemTransformVec3f itemtransformvec3f = getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
/* 190 */     ItemTransformVec3f itemtransformvec3f1 = getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
/* 191 */     ItemTransformVec3f itemtransformvec3f2 = getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
/* 192 */     ItemTransformVec3f itemtransformvec3f3 = getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
/* 193 */     ItemTransformVec3f itemtransformvec3f4 = getTransform(ItemCameraTransforms.TransformType.HEAD);
/* 194 */     ItemTransformVec3f itemtransformvec3f5 = getTransform(ItemCameraTransforms.TransformType.GUI);
/* 195 */     ItemTransformVec3f itemtransformvec3f6 = getTransform(ItemCameraTransforms.TransformType.GROUND);
/* 196 */     ItemTransformVec3f itemtransformvec3f7 = getTransform(ItemCameraTransforms.TransformType.FIXED);
/* 197 */     return new ItemCameraTransforms(itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7);
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type) {
/* 202 */     return (this.parent != null && !this.cameraTransforms.hasCustomTransform(type)) ? this.parent.getTransform(type) : this.cameraTransforms.getTransform(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> p_178312_0_) {
/* 207 */     for (ModelBlock modelblock : p_178312_0_.values()) {
/*     */ 
/*     */       
/*     */       try {
/* 211 */         ModelBlock modelblock1 = modelblock.parent;
/*     */         
/* 213 */         for (ModelBlock modelblock2 = modelblock1.parent; modelblock1 != modelblock2; modelblock2 = modelblock2.parent.parent)
/*     */         {
/* 215 */           modelblock1 = modelblock1.parent;
/*     */         }
/*     */         
/* 218 */         throw new LoopException();
/*     */       }
/* 220 */       catch (NullPointerException nullPointerException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Bookkeep
/*     */   {
/*     */     public final ModelBlock model;
/*     */     
/*     */     public ModelBlock modelExt;
/*     */ 
/*     */     
/*     */     private Bookkeep(ModelBlock modelIn) {
/* 234 */       this.model = modelIn;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<ModelBlock>
/*     */   {
/*     */     public ModelBlock deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 242 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 243 */       List<BlockPart> list = getModelElements(p_deserialize_3_, jsonobject);
/* 244 */       String s = getParent(jsonobject);
/* 245 */       Map<String, String> map = getTextures(jsonobject);
/* 246 */       boolean flag = getAmbientOcclusionEnabled(jsonobject);
/* 247 */       ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.DEFAULT;
/*     */       
/* 249 */       if (jsonobject.has("display")) {
/*     */         
/* 251 */         JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
/* 252 */         itemcameratransforms = (ItemCameraTransforms)p_deserialize_3_.deserialize((JsonElement)jsonobject1, ItemCameraTransforms.class);
/*     */       } 
/*     */       
/* 255 */       List<ItemOverride> list1 = getItemOverrides(p_deserialize_3_, jsonobject);
/* 256 */       ResourceLocation resourcelocation = s.isEmpty() ? null : new ResourceLocation(s);
/* 257 */       return new ModelBlock(resourcelocation, list, map, flag, true, itemcameratransforms, list1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<ItemOverride> getItemOverrides(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 262 */       List<ItemOverride> list = Lists.newArrayList();
/*     */       
/* 264 */       if (object.has("overrides"))
/*     */       {
/* 266 */         for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "overrides"))
/*     */         {
/* 268 */           list.add((ItemOverride)deserializationContext.deserialize(jsonelement, ItemOverride.class));
/*     */         }
/*     */       }
/*     */       
/* 272 */       return list;
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<String, String> getTextures(JsonObject object) {
/* 277 */       Map<String, String> map = Maps.newHashMap();
/*     */       
/* 279 */       if (object.has("textures")) {
/*     */         
/* 281 */         JsonObject jsonobject = object.getAsJsonObject("textures");
/*     */         
/* 283 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */         {
/* 285 */           map.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
/*     */         }
/*     */       } 
/*     */       
/* 289 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     private String getParent(JsonObject object) {
/* 294 */       return JsonUtils.getString(object, "parent", "");
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean getAmbientOcclusionEnabled(JsonObject object) {
/* 299 */       return JsonUtils.getBoolean(object, "ambientocclusion", true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<BlockPart> getModelElements(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 304 */       List<BlockPart> list = Lists.newArrayList();
/*     */       
/* 306 */       if (object.has("elements"))
/*     */       {
/* 308 */         for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "elements"))
/*     */         {
/* 310 */           list.add((BlockPart)deserializationContext.deserialize(jsonelement, BlockPart.class));
/*     */         }
/*     */       }
/*     */       
/* 314 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LoopException extends RuntimeException {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ModelBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
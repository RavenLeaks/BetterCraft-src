/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.entity.model.anim.ModelVariableUpdater;
/*     */ import optifine.Config;
/*     */ import optifine.ConnectedParser;
/*     */ import optifine.Json;
/*     */ import optifine.PlayerItemParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomEntityModelParser
/*     */ {
/*     */   public static final String ENTITY = "entity";
/*     */   public static final String TEXTURE = "texture";
/*     */   public static final String SHADOW_SIZE = "shadowSize";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String ITEM_ANIMATIONS = "animations";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_MODEL = "model";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_PART = "part";
/*     */   public static final String MODEL_ATTACH = "attach";
/*     */   public static final String MODEL_INVERT_AXIS = "invertAxis";
/*     */   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
/*     */   public static final String MODEL_TRANSLATE = "translate";
/*     */   public static final String MODEL_ROTATE = "rotate";
/*     */   public static final String MODEL_SCALE = "scale";
/*     */   public static final String MODEL_BOXES = "boxes";
/*     */   public static final String MODEL_SPRITES = "sprites";
/*     */   public static final String MODEL_SUBMODEL = "submodel";
/*     */   public static final String MODEL_SUBMODELS = "submodels";
/*     */   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
/*     */   public static final String BOX_COORDINATES = "coordinates";
/*     */   public static final String BOX_SIZE_ADD = "sizeAdd";
/*     */   public static final String ENTITY_MODEL = "EntityModel";
/*     */   public static final String ENTITY_MODEL_PART = "EntityModelPart";
/*     */   
/*     */   public static CustomEntityRenderer parseEntityRender(JsonObject obj, String path) {
/*  58 */     ConnectedParser connectedparser = new ConnectedParser("CustomEntityModels");
/*  59 */     String s = connectedparser.parseName(path);
/*  60 */     String s1 = connectedparser.parseBasePath(path);
/*  61 */     String s2 = Json.getString(obj, "texture");
/*  62 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  63 */     float f = Json.getFloat(obj, "shadowSize", -1.0F);
/*  64 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  65 */     checkNull(jsonarray, "Missing models");
/*  66 */     Map<Object, Object> map = new HashMap<>();
/*  67 */     List<CustomModelRenderer> list = new ArrayList();
/*     */     
/*  69 */     for (int i = 0; i < jsonarray.size(); i++) {
/*     */       
/*  71 */       JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*  72 */       processBaseId(jsonobject, map);
/*  73 */       processExternalModel(jsonobject, map, s1);
/*  74 */       processId(jsonobject, map);
/*  75 */       CustomModelRenderer custommodelrenderer = parseCustomModelRenderer(jsonobject, aint, s1);
/*     */       
/*  77 */       if (custommodelrenderer != null)
/*     */       {
/*  79 */         list.add(custommodelrenderer);
/*     */       }
/*     */     } 
/*     */     
/*  83 */     CustomModelRenderer[] acustommodelrenderer = list.<CustomModelRenderer>toArray(new CustomModelRenderer[list.size()]);
/*  84 */     ResourceLocation resourcelocation = null;
/*     */     
/*  86 */     if (s2 != null)
/*     */     {
/*  88 */       resourcelocation = getResourceLocation(s1, s2, ".png");
/*     */     }
/*     */     
/*  91 */     CustomEntityRenderer customentityrenderer = new CustomEntityRenderer(s, s1, resourcelocation, acustommodelrenderer, f);
/*  92 */     return customentityrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processBaseId(JsonObject elem, Map mapModelJsons) {
/*  97 */     String s = Json.getString(elem, "baseId");
/*     */     
/*  99 */     if (s != null) {
/*     */       
/* 101 */       JsonObject jsonobject = (JsonObject)mapModelJsons.get(s);
/*     */       
/* 103 */       if (jsonobject == null) {
/*     */         
/* 105 */         Config.warn("BaseID not found: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 109 */         copyJsonElements(jsonobject, elem);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processExternalModel(JsonObject elem, Map mapModelJsons, String basePath) {
/* 116 */     String s = Json.getString(elem, "model");
/*     */     
/* 118 */     if (s != null) {
/*     */       
/* 120 */       ResourceLocation resourcelocation = getResourceLocation(basePath, s, ".jpm");
/*     */ 
/*     */       
/*     */       try {
/* 124 */         JsonObject jsonobject = loadJson(resourcelocation);
/*     */         
/* 126 */         if (jsonobject == null) {
/*     */           
/* 128 */           Config.warn("Model not found: " + resourcelocation);
/*     */           
/*     */           return;
/*     */         } 
/* 132 */         copyJsonElements(jsonobject, elem);
/*     */       }
/* 134 */       catch (IOException ioexception) {
/*     */         
/* 136 */         Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */       }
/* 138 */       catch (JsonParseException jsonparseexception) {
/*     */         
/* 140 */         Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/*     */       }
/* 142 */       catch (Exception exception) {
/*     */         
/* 144 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyJsonElements(JsonObject objFrom, JsonObject objTo) {
/* 151 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)objFrom.entrySet()) {
/*     */       
/* 153 */       if (!((String)entry.getKey()).equals("id") && !objTo.has(entry.getKey()))
/*     */       {
/* 155 */         objTo.add(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getResourceLocation(String basePath, String path, String extension) {
/* 162 */     if (!path.endsWith(extension))
/*     */     {
/* 164 */       path = String.valueOf(path) + extension;
/*     */     }
/*     */     
/* 167 */     if (!path.contains("/")) {
/*     */       
/* 169 */       path = String.valueOf(basePath) + "/" + path;
/*     */     }
/* 171 */     else if (path.startsWith("./")) {
/*     */       
/* 173 */       path = String.valueOf(basePath) + "/" + path.substring(2);
/*     */     }
/* 175 */     else if (path.startsWith("~/")) {
/*     */       
/* 177 */       path = "optifine/" + path.substring(2);
/*     */     } 
/*     */     
/* 180 */     return new ResourceLocation(path);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processId(JsonObject elem, Map<String, JsonObject> mapModelJsons) {
/* 185 */     String s = Json.getString(elem, "id");
/*     */     
/* 187 */     if (s != null)
/*     */     {
/* 189 */       if (s.length() < 1) {
/*     */         
/* 191 */         Config.warn("Empty model ID: " + s);
/*     */       }
/* 193 */       else if (mapModelJsons.containsKey(s)) {
/*     */         
/* 195 */         Config.warn("Duplicate model ID: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 199 */         mapModelJsons.put(s, elem);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static CustomModelRenderer parseCustomModelRenderer(JsonObject elem, int[] textureSize, String basePath) {
/* 206 */     String s = Json.getString(elem, "part");
/* 207 */     checkNull(s, "Model part not specified, missing \"replace\" or \"attachTo\".");
/* 208 */     boolean flag = Json.getBoolean(elem, "attach", false);
/* 209 */     ModelBase modelbase = new CustomEntityModel();
/*     */     
/* 211 */     if (textureSize != null) {
/*     */       
/* 213 */       modelbase.textureWidth = textureSize[0];
/* 214 */       modelbase.textureHeight = textureSize[1];
/*     */     } 
/*     */     
/* 217 */     ModelUpdater modelupdater = null;
/* 218 */     JsonArray jsonarray = (JsonArray)elem.get("animations");
/*     */     
/* 220 */     if (jsonarray != null) {
/*     */       
/* 222 */       List<ModelVariableUpdater> list = new ArrayList<>();
/*     */       
/* 224 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/* 226 */         JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*     */         
/* 228 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */           
/* 230 */           String s1 = entry.getKey();
/* 231 */           String s2 = ((JsonElement)entry.getValue()).getAsString();
/* 232 */           ModelVariableUpdater modelvariableupdater = new ModelVariableUpdater(s1, s2);
/* 233 */           list.add(modelvariableupdater);
/*     */         } 
/*     */       } 
/*     */       
/* 237 */       if (list.size() > 0) {
/*     */         
/* 239 */         ModelVariableUpdater[] amodelvariableupdater = list.<ModelVariableUpdater>toArray(new ModelVariableUpdater[list.size()]);
/* 240 */         modelupdater = new ModelUpdater(amodelvariableupdater);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, textureSize, basePath);
/* 245 */     CustomModelRenderer custommodelrenderer = new CustomModelRenderer(s, flag, modelrenderer, modelupdater);
/* 246 */     return custommodelrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 251 */     if (obj == null)
/*     */     {
/* 253 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject loadJson(ResourceLocation location) throws IOException, JsonParseException {
/* 259 */     InputStream inputstream = Config.getResourceStream(location);
/*     */     
/* 261 */     if (inputstream == null)
/*     */     {
/* 263 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 267 */     String s = Config.readInputStream(inputstream, "ASCII");
/* 268 */     inputstream.close();
/* 269 */     JsonParser jsonparser = new JsonParser();
/* 270 */     JsonObject jsonobject = (JsonObject)jsonparser.parse(s);
/* 271 */     return jsonobject;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\CustomEntityModelParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
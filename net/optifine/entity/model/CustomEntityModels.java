/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.IModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import optifine.Config;
/*     */ 
/*     */ public class CustomEntityModels {
/*     */   private static boolean active = false;
/*  25 */   private static Map<Class, Render> originalEntityRenderMap = null;
/*  26 */   private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  30 */     Map<Class<?>, Render> map = getEntityRenderMap();
/*  31 */     Map<Class<?>, TileEntitySpecialRenderer> map1 = getTileEntityRenderMap();
/*     */     
/*  33 */     if (map == null) {
/*     */       
/*  35 */       Config.warn("Entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*  37 */     else if (map1 == null) {
/*     */       
/*  39 */       Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*     */     else {
/*     */       
/*  43 */       active = false;
/*  44 */       map.clear();
/*  45 */       map1.clear();
/*  46 */       map.putAll((Map)originalEntityRenderMap);
/*  47 */       map1.putAll((Map)originalTileEntityRenderMap);
/*     */       
/*  49 */       if (Config.isCustomEntityModels()) {
/*     */         
/*  51 */         ResourceLocation[] aresourcelocation = getModelLocations();
/*     */         
/*  53 */         for (int i = 0; i < aresourcelocation.length; i++) {
/*     */           
/*  55 */           ResourceLocation resourcelocation = aresourcelocation[i];
/*  56 */           Config.dbg("CustomEntityModel: " + resourcelocation.getResourcePath());
/*  57 */           IEntityRenderer ientityrenderer = parseEntityRender(resourcelocation);
/*     */           
/*  59 */           if (ientityrenderer != null) {
/*     */             
/*  61 */             Class<?> oclass = ientityrenderer.getEntityClass();
/*     */             
/*  63 */             if (oclass != null) {
/*     */               
/*  65 */               if (ientityrenderer instanceof Render) {
/*     */                 
/*  67 */                 map.put(oclass, (Render)ientityrenderer);
/*     */               }
/*  69 */               else if (ientityrenderer instanceof TileEntitySpecialRenderer) {
/*     */                 
/*  71 */                 map1.put(oclass, (TileEntitySpecialRenderer)ientityrenderer);
/*     */               }
/*     */               else {
/*     */                 
/*  75 */                 Config.warn("Unknown renderer type: " + ientityrenderer.getClass().getName());
/*     */               } 
/*     */               
/*  78 */               active = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Class, Render> getEntityRenderMap() {
/*  88 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/*  89 */     Map<Class<?>, Render> map = rendermanager.getEntityRenderMap();
/*     */     
/*  91 */     if (map == null)
/*     */     {
/*  93 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  97 */     if (originalEntityRenderMap == null)
/*     */     {
/*  99 */       originalEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 102 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap() {
/* 108 */     Map<Class<?>, TileEntitySpecialRenderer> map = TileEntityRendererDispatcher.instance.mapSpecialRenderers;
/*     */     
/* 110 */     if (originalTileEntityRenderMap == null)
/*     */     {
/* 112 */       originalTileEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 115 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getModelLocations() {
/* 120 */     String s = "optifine/cem/";
/* 121 */     String s1 = ".jem";
/* 122 */     List<ResourceLocation> list = new ArrayList<>();
/* 123 */     String[] astring = CustomModelRegistry.getModelNames();
/*     */     
/* 125 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 127 */       String s2 = astring[i];
/* 128 */       String s3 = String.valueOf(s) + s2 + s1;
/* 129 */       ResourceLocation resourcelocation = new ResourceLocation(s3);
/*     */       
/* 131 */       if (Config.hasResource(resourcelocation))
/*     */       {
/* 133 */         list.add(resourcelocation);
/*     */       }
/*     */     } 
/*     */     
/* 137 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 138 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(ResourceLocation location) {
/*     */     try {
/* 145 */       JsonObject jsonobject = CustomEntityModelParser.loadJson(location);
/* 146 */       IEntityRenderer ientityrenderer = parseEntityRender(jsonobject, location.getResourcePath());
/* 147 */       return ientityrenderer;
/*     */     }
/* 149 */     catch (IOException ioexception) {
/*     */       
/* 151 */       Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 152 */       return null;
/*     */     }
/* 154 */     catch (JsonParseException jsonparseexception) {
/*     */       
/* 156 */       Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/* 157 */       return null;
/*     */     }
/* 159 */     catch (Exception exception) {
/*     */       
/* 161 */       exception.printStackTrace();
/* 162 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(JsonObject obj, String path) {
/* 168 */     CustomEntityRenderer customentityrenderer = CustomEntityModelParser.parseEntityRender(obj, path);
/* 169 */     String s = customentityrenderer.getName();
/* 170 */     ModelAdapter modeladapter = CustomModelRegistry.getModelAdapter(s);
/* 171 */     checkNull(modeladapter, "Entity not found: " + s);
/* 172 */     Class oclass = modeladapter.getEntityClass();
/* 173 */     checkNull(oclass, "Entity class not found: " + s);
/* 174 */     IEntityRenderer ientityrenderer = makeEntityRender(modeladapter, customentityrenderer);
/*     */     
/* 176 */     if (ientityrenderer == null)
/*     */     {
/* 178 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 182 */     ientityrenderer.setEntityClass(oclass);
/* 183 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer) {
/* 189 */     ResourceLocation resourcelocation = cer.getTextureLocation();
/* 190 */     CustomModelRenderer[] acustommodelrenderer = cer.getCustomModelRenderers();
/* 191 */     float f = cer.getShadowSize();
/*     */     
/* 193 */     if (f < 0.0F)
/*     */     {
/* 195 */       f = modelAdapter.getShadowSize();
/*     */     }
/*     */     
/* 198 */     ModelBase modelbase = modelAdapter.makeModel();
/*     */     
/* 200 */     if (modelbase == null)
/*     */     {
/* 202 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 206 */     ModelResolver modelresolver = new ModelResolver(modelAdapter, modelbase, acustommodelrenderer);
/*     */     
/* 208 */     if (!modifyModel(modelAdapter, modelbase, acustommodelrenderer, modelresolver))
/*     */     {
/* 210 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 214 */     IEntityRenderer ientityrenderer = modelAdapter.makeEntityRender(modelbase, f);
/*     */     
/* 216 */     if (ientityrenderer == null)
/*     */     {
/* 218 */       throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
/*     */     }
/*     */ 
/*     */     
/* 222 */     if (resourcelocation != null)
/*     */     {
/* 224 */       ientityrenderer.setLocationTextureCustom(resourcelocation);
/*     */     }
/*     */     
/* 227 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
/* 235 */     for (int i = 0; i < modelRenderers.length; i++) {
/*     */       
/* 237 */       CustomModelRenderer custommodelrenderer = modelRenderers[i];
/*     */       
/* 239 */       if (!modifyModel(modelAdapter, model, custommodelrenderer, mr))
/*     */       {
/* 241 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
/* 250 */     String s = customModelRenderer.getModelPart();
/* 251 */     ModelRenderer modelrenderer = modelAdapter.getModelRenderer(model, s);
/*     */     
/* 253 */     if (modelrenderer == null) {
/*     */       
/* 255 */       Config.warn("Model part not found: " + s + ", model: " + model);
/* 256 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 260 */     if (!customModelRenderer.isAttach()) {
/*     */       
/* 262 */       if (modelrenderer.cubeList != null)
/*     */       {
/* 264 */         modelrenderer.cubeList.clear();
/*     */       }
/*     */       
/* 267 */       if (modelrenderer.spriteList != null)
/*     */       {
/* 269 */         modelrenderer.spriteList.clear();
/*     */       }
/*     */       
/* 272 */       if (modelrenderer.childModels != null)
/*     */       {
/* 274 */         modelrenderer.childModels.clear();
/*     */       }
/*     */     } 
/*     */     
/* 278 */     modelrenderer.addChild(customModelRenderer.getModelRenderer());
/* 279 */     ModelUpdater modelupdater = customModelRenderer.getModelUpdater();
/*     */     
/* 281 */     if (modelupdater != null) {
/*     */       
/* 283 */       modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
/* 284 */       modelResolver.setPartModelRenderer(modelrenderer);
/*     */       
/* 286 */       if (!modelupdater.initialize((IModelResolver)modelResolver))
/*     */       {
/* 288 */         return false;
/*     */       }
/*     */       
/* 291 */       customModelRenderer.getModelRenderer().setModelUpdater(modelupdater);
/*     */     } 
/*     */     
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 300 */     if (obj == null)
/*     */     {
/* 302 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 308 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\CustomEntityModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
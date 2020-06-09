/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.multipart.Multipart;
/*     */ import net.minecraft.client.renderer.block.model.multipart.Selector;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ public class ModelBlockDefinition
/*     */ {
/*     */   @VisibleForTesting
/*  28 */   static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).registerTypeAdapter(VariantList.class, new VariantList.Deserializer()).registerTypeAdapter(Multipart.class, new Multipart.Deserializer()).registerTypeAdapter(Selector.class, new Selector.Deserializer()).create();
/*  29 */   private final Map<String, VariantList> mapVariants = Maps.newHashMap();
/*     */   
/*     */   private Multipart multipart;
/*     */   
/*     */   public static ModelBlockDefinition parseFromReader(Reader reader) {
/*  34 */     return (ModelBlockDefinition)JsonUtils.func_193839_a(GSON, reader, ModelBlockDefinition.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlockDefinition(Map<String, VariantList> variants, Multipart multipartIn) {
/*  39 */     this.multipart = multipartIn;
/*  40 */     this.mapVariants.putAll(variants);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBlockDefinition(List<ModelBlockDefinition> p_i46222_1_) {
/*  45 */     ModelBlockDefinition modelblockdefinition = null;
/*     */     
/*  47 */     for (ModelBlockDefinition modelblockdefinition1 : p_i46222_1_) {
/*     */       
/*  49 */       if (modelblockdefinition1.hasMultipartData()) {
/*     */         
/*  51 */         this.mapVariants.clear();
/*  52 */         modelblockdefinition = modelblockdefinition1;
/*     */       } 
/*     */       
/*  55 */       this.mapVariants.putAll(modelblockdefinition1.mapVariants);
/*     */     } 
/*     */     
/*  58 */     if (modelblockdefinition != null)
/*     */     {
/*  60 */       this.multipart = modelblockdefinition.multipart;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasVariant(String p_188000_1_) {
/*  66 */     return (this.mapVariants.get(p_188000_1_) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public VariantList getVariant(String p_188004_1_) {
/*  71 */     VariantList variantlist = this.mapVariants.get(p_188004_1_);
/*     */     
/*  73 */     if (variantlist == null)
/*     */     {
/*  75 */       throw new MissingVariantException();
/*     */     }
/*     */ 
/*     */     
/*  79 */     return variantlist;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  85 */     if (this == p_equals_1_)
/*     */     {
/*  87 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  91 */     if (p_equals_1_ instanceof ModelBlockDefinition) {
/*     */       
/*  93 */       ModelBlockDefinition modelblockdefinition = (ModelBlockDefinition)p_equals_1_;
/*     */       
/*  95 */       if (this.mapVariants.equals(modelblockdefinition.mapVariants))
/*     */       {
/*  97 */         return hasMultipartData() ? this.multipart.equals(modelblockdefinition.multipart) : (!modelblockdefinition.hasMultipartData());
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return 31 * this.mapVariants.hashCode() + (hasMultipartData() ? this.multipart.hashCode() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<VariantList> getMultipartVariants() {
/* 112 */     Set<VariantList> set = Sets.newHashSet(this.mapVariants.values());
/*     */     
/* 114 */     if (hasMultipartData())
/*     */     {
/* 116 */       set.addAll(this.multipart.getVariants());
/*     */     }
/*     */     
/* 119 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMultipartData() {
/* 124 */     return (this.multipart != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multipart getMultipartData() {
/* 129 */     return this.multipart;
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<ModelBlockDefinition>
/*     */   {
/*     */     public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 136 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 137 */       Map<String, VariantList> map = parseMapVariants(p_deserialize_3_, jsonobject);
/* 138 */       Multipart multipart = parseMultipart(p_deserialize_3_, jsonobject);
/*     */       
/* 140 */       if (!map.isEmpty() || (multipart != null && !multipart.getVariants().isEmpty()))
/*     */       {
/* 142 */         return new ModelBlockDefinition(map, multipart);
/*     */       }
/*     */ 
/*     */       
/* 146 */       throw new JsonParseException("Neither 'variants' nor 'multipart' found");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Map<String, VariantList> parseMapVariants(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 152 */       Map<String, VariantList> map = Maps.newHashMap();
/*     */       
/* 154 */       if (object.has("variants")) {
/*     */         
/* 156 */         JsonObject jsonobject = JsonUtils.getJsonObject(object, "variants");
/*     */         
/* 158 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */         {
/* 160 */           map.put(entry.getKey(), (VariantList)deserializationContext.deserialize(entry.getValue(), VariantList.class));
/*     */         }
/*     */       } 
/*     */       
/* 164 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Multipart parseMultipart(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 170 */       if (!object.has("multipart"))
/*     */       {
/* 172 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 176 */       JsonArray jsonarray = JsonUtils.getJsonArray(object, "multipart");
/* 177 */       return (Multipart)deserializationContext.deserialize((JsonElement)jsonarray, Multipart.class);
/*     */     }
/*     */   }
/*     */   
/*     */   public class MissingVariantException extends RuntimeException {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ModelBlockDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
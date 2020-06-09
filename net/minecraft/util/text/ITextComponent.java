/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.EnumTypeAdapterFactory;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ITextComponent
/*     */   extends Iterable<ITextComponent>
/*     */ {
/*     */   ITextComponent setStyle(Style paramStyle);
/*     */   
/*     */   Style getStyle();
/*     */   
/*     */   ITextComponent appendText(String paramString);
/*     */   
/*     */   ITextComponent appendSibling(ITextComponent paramITextComponent);
/*     */   
/*     */   String getUnformattedComponentText();
/*     */   
/*     */   String getUnformattedText();
/*     */   
/*     */   String getFormattedText();
/*     */   
/*     */   List<ITextComponent> getSiblings();
/*     */   
/*     */   ITextComponent createCopy();
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<ITextComponent>, JsonSerializer<ITextComponent>
/*     */   {
/*     */     private static final Gson GSON;
/*     */     
/*     */     public ITextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       ITextComponent itextcomponent;
/*  66 */       if (p_deserialize_1_.isJsonPrimitive())
/*     */       {
/*  68 */         return new TextComponentString(p_deserialize_1_.getAsString());
/*     */       }
/*  70 */       if (!p_deserialize_1_.isJsonObject()) {
/*     */         
/*  72 */         if (p_deserialize_1_.isJsonArray()) {
/*     */           
/*  74 */           JsonArray jsonarray1 = p_deserialize_1_.getAsJsonArray();
/*  75 */           ITextComponent itextcomponent1 = null;
/*     */           
/*  77 */           for (JsonElement jsonelement : jsonarray1) {
/*     */             
/*  79 */             ITextComponent itextcomponent2 = deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
/*     */             
/*  81 */             if (itextcomponent1 == null) {
/*     */               
/*  83 */               itextcomponent1 = itextcomponent2;
/*     */               
/*     */               continue;
/*     */             } 
/*  87 */             itextcomponent1.appendSibling(itextcomponent2);
/*     */           } 
/*     */ 
/*     */           
/*  91 */           return itextcomponent1;
/*     */         } 
/*     */ 
/*     */         
/*  95 */         throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 100 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */ 
/*     */       
/* 103 */       if (jsonobject.has("text")) {
/*     */         
/* 105 */         itextcomponent = new TextComponentString(jsonobject.get("text").getAsString());
/*     */       }
/* 107 */       else if (jsonobject.has("translate")) {
/*     */         
/* 109 */         String s = jsonobject.get("translate").getAsString();
/*     */         
/* 111 */         if (jsonobject.has("with"))
/*     */         {
/* 113 */           JsonArray jsonarray = jsonobject.getAsJsonArray("with");
/* 114 */           Object[] aobject = new Object[jsonarray.size()];
/*     */           
/* 116 */           for (int i = 0; i < aobject.length; i++) {
/*     */             
/* 118 */             aobject[i] = deserialize(jsonarray.get(i), p_deserialize_2_, p_deserialize_3_);
/*     */             
/* 120 */             if (aobject[i] instanceof TextComponentString) {
/*     */               
/* 122 */               TextComponentString textcomponentstring = (TextComponentString)aobject[i];
/*     */               
/* 124 */               if (textcomponentstring.getStyle().isEmpty() && textcomponentstring.getSiblings().isEmpty())
/*     */               {
/* 126 */                 aobject[i] = textcomponentstring.getText();
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 131 */           itextcomponent = new TextComponentTranslation(s, aobject);
/*     */         }
/*     */         else
/*     */         {
/* 135 */           itextcomponent = new TextComponentTranslation(s, new Object[0]);
/*     */         }
/*     */       
/* 138 */       } else if (jsonobject.has("score")) {
/*     */         
/* 140 */         JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
/*     */         
/* 142 */         if (!jsonobject1.has("name") || !jsonobject1.has("objective"))
/*     */         {
/* 144 */           throw new JsonParseException("A score component needs a least a name and an objective");
/*     */         }
/*     */         
/* 147 */         itextcomponent = new TextComponentScore(JsonUtils.getString(jsonobject1, "name"), JsonUtils.getString(jsonobject1, "objective"));
/*     */         
/* 149 */         if (jsonobject1.has("value"))
/*     */         {
/* 151 */           ((TextComponentScore)itextcomponent).setValue(JsonUtils.getString(jsonobject1, "value"));
/*     */         }
/*     */       }
/* 154 */       else if (jsonobject.has("selector")) {
/*     */         
/* 156 */         itextcomponent = new TextComponentSelector(JsonUtils.getString(jsonobject, "selector"));
/*     */       }
/*     */       else {
/*     */         
/* 160 */         if (!jsonobject.has("keybind"))
/*     */         {
/* 162 */           throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
/*     */         }
/*     */         
/* 165 */         itextcomponent = new TextComponentKeybind(JsonUtils.getString(jsonobject, "keybind"));
/*     */       } 
/*     */       
/* 168 */       if (jsonobject.has("extra")) {
/*     */         
/* 170 */         JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
/*     */         
/* 172 */         if (jsonarray2.size() <= 0)
/*     */         {
/* 174 */           throw new JsonParseException("Unexpected empty array of components");
/*     */         }
/*     */         
/* 177 */         for (int j = 0; j < jsonarray2.size(); j++)
/*     */         {
/* 179 */           itextcomponent.appendSibling(deserialize(jsonarray2.get(j), p_deserialize_2_, p_deserialize_3_));
/*     */         }
/*     */       } 
/*     */       
/* 183 */       itextcomponent.setStyle((Style)p_deserialize_3_.deserialize(p_deserialize_1_, Style.class));
/* 184 */       return itextcomponent;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void serializeChatStyle(Style style, JsonObject object, JsonSerializationContext ctx) {
/* 190 */       JsonElement jsonelement = ctx.serialize(style);
/*     */       
/* 192 */       if (jsonelement.isJsonObject()) {
/*     */         
/* 194 */         JsonObject jsonobject = (JsonObject)jsonelement;
/*     */         
/* 196 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*     */         {
/* 198 */           object.add(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ITextComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 205 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 207 */       if (!p_serialize_1_.getStyle().isEmpty())
/*     */       {
/* 209 */         serializeChatStyle(p_serialize_1_.getStyle(), jsonobject, p_serialize_3_);
/*     */       }
/*     */       
/* 212 */       if (!p_serialize_1_.getSiblings().isEmpty()) {
/*     */         
/* 214 */         JsonArray jsonarray = new JsonArray();
/*     */         
/* 216 */         for (ITextComponent itextcomponent : p_serialize_1_.getSiblings())
/*     */         {
/* 218 */           jsonarray.add(serialize(itextcomponent, itextcomponent.getClass(), p_serialize_3_));
/*     */         }
/*     */         
/* 221 */         jsonobject.add("extra", (JsonElement)jsonarray);
/*     */       } 
/*     */       
/* 224 */       if (p_serialize_1_ instanceof TextComponentString) {
/*     */         
/* 226 */         jsonobject.addProperty("text", ((TextComponentString)p_serialize_1_).getText());
/*     */       }
/* 228 */       else if (p_serialize_1_ instanceof TextComponentTranslation) {
/*     */         
/* 230 */         TextComponentTranslation textcomponenttranslation = (TextComponentTranslation)p_serialize_1_;
/* 231 */         jsonobject.addProperty("translate", textcomponenttranslation.getKey());
/*     */         
/* 233 */         if (textcomponenttranslation.getFormatArgs() != null && (textcomponenttranslation.getFormatArgs()).length > 0)
/*     */         {
/* 235 */           JsonArray jsonarray1 = new JsonArray(); byte b; int i;
/*     */           Object[] arrayOfObject;
/* 237 */           for (i = (arrayOfObject = textcomponenttranslation.getFormatArgs()).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/*     */             
/* 239 */             if (object instanceof ITextComponent) {
/*     */               
/* 241 */               jsonarray1.add(serialize((ITextComponent)object, object.getClass(), p_serialize_3_));
/*     */             }
/*     */             else {
/*     */               
/* 245 */               jsonarray1.add((JsonElement)new JsonPrimitive(String.valueOf(object)));
/*     */             } 
/*     */             b++; }
/*     */           
/* 249 */           jsonobject.add("with", (JsonElement)jsonarray1);
/*     */         }
/*     */       
/* 252 */       } else if (p_serialize_1_ instanceof TextComponentScore) {
/*     */         
/* 254 */         TextComponentScore textcomponentscore = (TextComponentScore)p_serialize_1_;
/* 255 */         JsonObject jsonobject1 = new JsonObject();
/* 256 */         jsonobject1.addProperty("name", textcomponentscore.getName());
/* 257 */         jsonobject1.addProperty("objective", textcomponentscore.getObjective());
/* 258 */         jsonobject1.addProperty("value", textcomponentscore.getUnformattedComponentText());
/* 259 */         jsonobject.add("score", (JsonElement)jsonobject1);
/*     */       }
/* 261 */       else if (p_serialize_1_ instanceof TextComponentSelector) {
/*     */         
/* 263 */         TextComponentSelector textcomponentselector = (TextComponentSelector)p_serialize_1_;
/* 264 */         jsonobject.addProperty("selector", textcomponentselector.getSelector());
/*     */       }
/*     */       else {
/*     */         
/* 268 */         if (!(p_serialize_1_ instanceof TextComponentKeybind))
/*     */         {
/* 270 */           throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
/*     */         }
/*     */         
/* 273 */         TextComponentKeybind textcomponentkeybind = (TextComponentKeybind)p_serialize_1_;
/* 274 */         jsonobject.addProperty("keybind", textcomponentkeybind.func_193633_h());
/*     */       } 
/*     */       
/* 277 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String componentToJson(ITextComponent component) {
/* 282 */       return GSON.toJson(component);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static ITextComponent jsonToComponent(String json) {
/* 288 */       return (ITextComponent)JsonUtils.gsonDeserialize(GSON, json, ITextComponent.class, false);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static ITextComponent fromJsonLenient(String json) {
/* 294 */       return (ITextComponent)JsonUtils.gsonDeserialize(GSON, json, ITextComponent.class, true);
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 299 */       GsonBuilder gsonbuilder = new GsonBuilder();
/* 300 */       gsonbuilder.registerTypeHierarchyAdapter(ITextComponent.class, new Serializer());
/* 301 */       gsonbuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
/* 302 */       gsonbuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
/* 303 */       GSON = gsonbuilder.create();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\ITextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
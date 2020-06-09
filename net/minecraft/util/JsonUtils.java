/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.Item;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonUtils
/*     */ {
/*     */   public static boolean isString(JsonObject json, String memberName) {
/*  27 */     return !isJsonPrimitive(json, memberName) ? false : json.getAsJsonPrimitive(memberName).isString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isString(JsonElement json) {
/*  35 */     return !json.isJsonPrimitive() ? false : json.getAsJsonPrimitive().isString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNumber(JsonElement json) {
/*  40 */     return !json.isJsonPrimitive() ? false : json.getAsJsonPrimitive().isNumber();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBoolean(JsonObject json, String memberName) {
/*  45 */     return !isJsonPrimitive(json, memberName) ? false : json.getAsJsonPrimitive(memberName).isBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonArray(JsonObject json, String memberName) {
/*  53 */     return !hasField(json, memberName) ? false : json.get(memberName).isJsonArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonPrimitive(JsonObject json, String memberName) {
/*  62 */     return !hasField(json, memberName) ? false : json.get(memberName).isJsonPrimitive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasField(JsonObject json, String memberName) {
/*  70 */     if (json == null)
/*     */     {
/*  72 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  76 */     return (json.get(memberName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonElement json, String memberName) {
/*  86 */     if (json.isJsonPrimitive())
/*     */     {
/*  88 */       return json.getAsString();
/*     */     }
/*     */ 
/*     */     
/*  92 */     throw new JsonSyntaxException("Expected " + memberName + " to be a string, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject json, String memberName) {
/* 101 */     if (json.has(memberName))
/*     */     {
/* 103 */       return getString(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 107 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject json, String memberName, String fallback) {
/* 117 */     return json.has(memberName) ? getString(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Item getItem(JsonElement json, String memberName) {
/* 122 */     if (json.isJsonPrimitive()) {
/*     */       
/* 124 */       String s = json.getAsString();
/* 125 */       Item item = Item.getByNameOrId(s);
/*     */       
/* 127 */       if (item == null)
/*     */       {
/* 129 */         throw new JsonSyntaxException("Expected " + memberName + " to be an item, was unknown string '" + s + "'");
/*     */       }
/*     */ 
/*     */       
/* 133 */       return item;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 138 */     throw new JsonSyntaxException("Expected " + memberName + " to be an item, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Item getItem(JsonObject json, String memberName) {
/* 144 */     if (json.has(memberName))
/*     */     {
/* 146 */       return getItem(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 150 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find an item");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonElement json, String memberName) {
/* 160 */     if (json.isJsonPrimitive())
/*     */     {
/* 162 */       return json.getAsBoolean();
/*     */     }
/*     */ 
/*     */     
/* 166 */     throw new JsonSyntaxException("Expected " + memberName + " to be a Boolean, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject json, String memberName) {
/* 175 */     if (json.has(memberName))
/*     */     {
/* 177 */       return getBoolean(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 181 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject json, String memberName, boolean fallback) {
/* 191 */     return json.has(memberName) ? getBoolean(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonElement json, String memberName) {
/* 200 */     if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber())
/*     */     {
/* 202 */       return json.getAsFloat();
/*     */     }
/*     */ 
/*     */     
/* 206 */     throw new JsonSyntaxException("Expected " + memberName + " to be a Float, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject json, String memberName) {
/* 215 */     if (json.has(memberName))
/*     */     {
/* 217 */       return getFloat(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 221 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Float");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject json, String memberName, float fallback) {
/* 231 */     return json.has(memberName) ? getFloat(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonElement json, String memberName) {
/* 240 */     if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber())
/*     */     {
/* 242 */       return json.getAsInt();
/*     */     }
/*     */ 
/*     */     
/* 246 */     throw new JsonSyntaxException("Expected " + memberName + " to be a Int, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject json, String memberName) {
/* 255 */     if (json.has(memberName))
/*     */     {
/* 257 */       return getInt(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 261 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a Int");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject json, String memberName, int fallback) {
/* 271 */     return json.has(memberName) ? getInt(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonElement json, String memberName) {
/* 280 */     if (json.isJsonObject())
/*     */     {
/* 282 */       return json.getAsJsonObject();
/*     */     }
/*     */ 
/*     */     
/* 286 */     throw new JsonSyntaxException("Expected " + memberName + " to be a JsonObject, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject json, String memberName) {
/* 292 */     if (json.has(memberName))
/*     */     {
/* 294 */       return getJsonObject(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 298 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a JsonObject");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject json, String memberName, JsonObject fallback) {
/* 308 */     return json.has(memberName) ? getJsonObject(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonElement json, String memberName) {
/* 317 */     if (json.isJsonArray())
/*     */     {
/* 319 */       return json.getAsJsonArray();
/*     */     }
/*     */ 
/*     */     
/* 323 */     throw new JsonSyntaxException("Expected " + memberName + " to be a JsonArray, was " + toString(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject json, String memberName) {
/* 332 */     if (json.has(memberName))
/*     */     {
/* 334 */       return getJsonArray(json.get(memberName), memberName);
/*     */     }
/*     */ 
/*     */     
/* 338 */     throw new JsonSyntaxException("Missing " + memberName + ", expected to find a JsonArray");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject json, String memberName, @Nullable JsonArray fallback) {
/* 348 */     return json.has(memberName) ? getJsonArray(json.get(memberName), memberName) : fallback;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T deserializeClass(@Nullable JsonElement json, String memberName, JsonDeserializationContext context, Class<? extends T> adapter) {
/* 353 */     if (json != null)
/*     */     {
/* 355 */       return (T)context.deserialize(json, adapter);
/*     */     }
/*     */ 
/*     */     
/* 359 */     throw new JsonSyntaxException("Missing " + memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T deserializeClass(JsonObject json, String memberName, JsonDeserializationContext context, Class<? extends T> adapter) {
/* 365 */     if (json.has(memberName))
/*     */     {
/* 367 */       return deserializeClass(json.get(memberName), memberName, context, adapter);
/*     */     }
/*     */ 
/*     */     
/* 371 */     throw new JsonSyntaxException("Missing " + memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T deserializeClass(JsonObject json, String memberName, T fallback, JsonDeserializationContext context, Class<? extends T> adapter) {
/* 377 */     return json.has(memberName) ? deserializeClass(json.get(memberName), memberName, context, adapter) : fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(JsonElement json) {
/* 385 */     String s = StringUtils.abbreviateMiddle(String.valueOf(json), "...", 10);
/*     */     
/* 387 */     if (json == null)
/*     */     {
/* 389 */       return "null (missing)";
/*     */     }
/* 391 */     if (json.isJsonNull())
/*     */     {
/* 393 */       return "null (json)";
/*     */     }
/* 395 */     if (json.isJsonArray())
/*     */     {
/* 397 */       return "an array (" + s + ")";
/*     */     }
/* 399 */     if (json.isJsonObject())
/*     */     {
/* 401 */       return "an object (" + s + ")";
/*     */     }
/*     */ 
/*     */     
/* 405 */     if (json.isJsonPrimitive()) {
/*     */       
/* 407 */       JsonPrimitive jsonprimitive = json.getAsJsonPrimitive();
/*     */       
/* 409 */       if (jsonprimitive.isNumber())
/*     */       {
/* 411 */         return "a number (" + s + ")";
/*     */       }
/*     */       
/* 414 */       if (jsonprimitive.isBoolean())
/*     */       {
/* 416 */         return "a boolean (" + s + ")";
/*     */       }
/*     */     } 
/*     */     
/* 420 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T gsonDeserialize(Gson gsonIn, Reader readerIn, Class<T> adapter, boolean lenient) {
/*     */     try {
/* 429 */       JsonReader jsonreader = new JsonReader(readerIn);
/* 430 */       jsonreader.setLenient(lenient);
/* 431 */       return (T)gsonIn.getAdapter(adapter).read(jsonreader);
/*     */     }
/* 433 */     catch (IOException ioexception) {
/*     */       
/* 435 */       throw new JsonParseException(ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T func_193838_a(Gson p_193838_0_, Reader p_193838_1_, Type p_193838_2_, boolean p_193838_3_) {
/*     */     try {
/* 444 */       JsonReader jsonreader = new JsonReader(p_193838_1_);
/* 445 */       jsonreader.setLenient(p_193838_3_);
/* 446 */       return (T)p_193838_0_.getAdapter(TypeToken.get(p_193838_2_)).read(jsonreader);
/*     */     }
/* 448 */     catch (IOException ioexception) {
/*     */       
/* 450 */       throw new JsonParseException(ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T func_193837_a(Gson p_193837_0_, String p_193837_1_, Type p_193837_2_, boolean p_193837_3_) {
/* 457 */     return func_193838_a(p_193837_0_, new StringReader(p_193837_1_), p_193837_2_, p_193837_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T gsonDeserialize(Gson gsonIn, String json, Class<T> adapter, boolean lenient) {
/* 463 */     return gsonDeserialize(gsonIn, new StringReader(json), adapter, lenient);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T func_193841_a(Gson p_193841_0_, Reader p_193841_1_, Type p_193841_2_) {
/* 469 */     return func_193838_a(p_193841_0_, p_193841_1_, p_193841_2_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T func_193840_a(Gson p_193840_0_, String p_193840_1_, Type p_193840_2_) {
/* 475 */     return func_193837_a(p_193840_0_, p_193840_1_, p_193840_2_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T func_193839_a(Gson p_193839_0_, Reader p_193839_1_, Class<T> p_193839_2_) {
/* 481 */     return gsonDeserialize(p_193839_0_, p_193839_1_, p_193839_2_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T gsonDeserialize(Gson gsonIn, String json, Class<T> adapter) {
/* 487 */     return gsonDeserialize(gsonIn, json, adapter, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\JsonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.world.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ 
/*     */ public class LootFunctionManager
/*     */ {
/*  20 */   private static final Map<ResourceLocation, LootFunction.Serializer<?>> NAME_TO_SERIALIZER_MAP = Maps.newHashMap();
/*  21 */   private static final Map<Class<? extends LootFunction>, LootFunction.Serializer<?>> CLASS_TO_SERIALIZER_MAP = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public static <T extends LootFunction> void registerFunction(LootFunction.Serializer<? extends T> p_186582_0_) {
/*  25 */     ResourceLocation resourcelocation = p_186582_0_.getFunctionName();
/*  26 */     Class<T> oclass = (Class)p_186582_0_.getFunctionClass();
/*     */     
/*  28 */     if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation))
/*     */     {
/*  30 */       throw new IllegalArgumentException("Can't re-register item function name " + resourcelocation);
/*     */     }
/*  32 */     if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass))
/*     */     {
/*  34 */       throw new IllegalArgumentException("Can't re-register item function class " + oclass.getName());
/*     */     }
/*     */ 
/*     */     
/*  38 */     NAME_TO_SERIALIZER_MAP.put(resourcelocation, p_186582_0_);
/*  39 */     CLASS_TO_SERIALIZER_MAP.put(oclass, p_186582_0_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LootFunction.Serializer<?> getSerializerForName(ResourceLocation location) {
/*  45 */     LootFunction.Serializer<?> serializer = NAME_TO_SERIALIZER_MAP.get(location);
/*     */     
/*  47 */     if (serializer == null)
/*     */     {
/*  49 */       throw new IllegalArgumentException("Unknown loot item function '" + location + "'");
/*     */     }
/*     */ 
/*     */     
/*  53 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends LootFunction> LootFunction.Serializer<T> getSerializerFor(T functionClass) {
/*  59 */     LootFunction.Serializer<T> serializer = (LootFunction.Serializer<T>)CLASS_TO_SERIALIZER_MAP.get(functionClass.getClass());
/*     */     
/*  61 */     if (serializer == null)
/*     */     {
/*  63 */       throw new IllegalArgumentException("Unknown loot item function " + functionClass);
/*     */     }
/*     */ 
/*     */     
/*  67 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  73 */     registerFunction(new SetCount.Serializer());
/*  74 */     registerFunction(new SetMetadata.Serializer());
/*  75 */     registerFunction(new EnchantWithLevels.Serializer());
/*  76 */     registerFunction(new EnchantRandomly.Serializer());
/*  77 */     registerFunction(new SetNBT.Serializer());
/*  78 */     registerFunction(new Smelt.Serializer());
/*  79 */     registerFunction(new LootingEnchantBonus.Serializer());
/*  80 */     registerFunction(new SetDamage.Serializer());
/*  81 */     registerFunction(new SetAttributes.Serializer());
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<LootFunction>, JsonSerializer<LootFunction> {
/*     */     public LootFunction deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       LootFunction.Serializer<?> serializer;
/*  88 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "function");
/*  89 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "function"));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  94 */         serializer = LootFunctionManager.getSerializerForName(resourcelocation);
/*     */       }
/*  96 */       catch (IllegalArgumentException var8) {
/*     */         
/*  98 */         throw new JsonSyntaxException("Unknown function '" + resourcelocation + "'");
/*     */       } 
/*     */       
/* 101 */       return (LootFunction)serializer.deserialize(jsonobject, p_deserialize_3_, (LootCondition[])JsonUtils.deserializeClass(jsonobject, "conditions", new LootCondition[0], p_deserialize_3_, LootCondition[].class));
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(LootFunction p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 106 */       LootFunction.Serializer<LootFunction> serializer = LootFunctionManager.getSerializerFor(p_serialize_1_);
/* 107 */       JsonObject jsonobject = new JsonObject();
/* 108 */       serializer.serialize(jsonobject, p_serialize_1_, p_serialize_3_);
/* 109 */       jsonobject.addProperty("function", serializer.getFunctionName().toString());
/*     */       
/* 111 */       if (p_serialize_1_.getConditions() != null && (p_serialize_1_.getConditions()).length > 0)
/*     */       {
/* 113 */         jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.getConditions()));
/*     */       }
/*     */       
/* 116 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\LootFunctionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
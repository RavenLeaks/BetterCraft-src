/*     */ package net.minecraft.world.storage.loot.conditions;
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
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ 
/*     */ public class LootConditionManager
/*     */ {
/*  22 */   private static final Map<ResourceLocation, LootCondition.Serializer<?>> NAME_TO_SERIALIZER_MAP = Maps.newHashMap();
/*  23 */   private static final Map<Class<? extends LootCondition>, LootCondition.Serializer<?>> CLASS_TO_SERIALIZER_MAP = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public static <T extends LootCondition> void registerCondition(LootCondition.Serializer<? extends T> condition) {
/*  27 */     ResourceLocation resourcelocation = condition.getLootTableLocation();
/*  28 */     Class<T> oclass = (Class)condition.getConditionClass();
/*     */     
/*  30 */     if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation))
/*     */     {
/*  32 */       throw new IllegalArgumentException("Can't re-register item condition name " + resourcelocation);
/*     */     }
/*  34 */     if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass))
/*     */     {
/*  36 */       throw new IllegalArgumentException("Can't re-register item condition class " + oclass.getName());
/*     */     }
/*     */ 
/*     */     
/*  40 */     NAME_TO_SERIALIZER_MAP.put(resourcelocation, condition);
/*  41 */     CLASS_TO_SERIALIZER_MAP.put(oclass, condition);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean testAllConditions(@Nullable LootCondition[] conditions, Random rand, LootContext context) {
/*  47 */     if (conditions == null)
/*     */     {
/*  49 */       return true; } 
/*     */     byte b;
/*     */     int i;
/*     */     LootCondition[] arrayOfLootCondition;
/*  53 */     for (i = (arrayOfLootCondition = conditions).length, b = 0; b < i; ) { LootCondition lootcondition = arrayOfLootCondition[b];
/*     */       
/*  55 */       if (!lootcondition.testCondition(rand, context))
/*     */       {
/*  57 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LootCondition.Serializer<?> getSerializerForName(ResourceLocation location) {
/*  67 */     LootCondition.Serializer<?> serializer = NAME_TO_SERIALIZER_MAP.get(location);
/*     */     
/*  69 */     if (serializer == null)
/*     */     {
/*  71 */       throw new IllegalArgumentException("Unknown loot item condition '" + location + "'");
/*     */     }
/*     */ 
/*     */     
/*  75 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends LootCondition> LootCondition.Serializer<T> getSerializerFor(T conditionClass) {
/*  81 */     LootCondition.Serializer<T> serializer = (LootCondition.Serializer<T>)CLASS_TO_SERIALIZER_MAP.get(conditionClass.getClass());
/*     */     
/*  83 */     if (serializer == null)
/*     */     {
/*  85 */       throw new IllegalArgumentException("Unknown loot item condition " + conditionClass);
/*     */     }
/*     */ 
/*     */     
/*  89 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  95 */     registerCondition(new RandomChance.Serializer());
/*  96 */     registerCondition(new RandomChanceWithLooting.Serializer());
/*  97 */     registerCondition(new EntityHasProperty.Serializer());
/*  98 */     registerCondition(new KilledByPlayer.Serializer());
/*  99 */     registerCondition(new EntityHasScore.Serializer());
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<LootCondition>, JsonSerializer<LootCondition> {
/*     */     public LootCondition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       LootCondition.Serializer<?> serializer;
/* 106 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "condition");
/* 107 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "condition"));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 112 */         serializer = LootConditionManager.getSerializerForName(resourcelocation);
/*     */       }
/* 114 */       catch (IllegalArgumentException var8) {
/*     */         
/* 116 */         throw new JsonSyntaxException("Unknown condition '" + resourcelocation + "'");
/*     */       } 
/*     */       
/* 119 */       return (LootCondition)serializer.deserialize(jsonobject, p_deserialize_3_);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(LootCondition p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 124 */       LootCondition.Serializer<LootCondition> serializer = LootConditionManager.getSerializerFor(p_serialize_1_);
/* 125 */       JsonObject jsonobject = new JsonObject();
/* 126 */       serializer.serialize(jsonobject, p_serialize_1_, p_serialize_3_);
/* 127 */       jsonobject.addProperty("condition", serializer.getLootTableLocation().toString());
/* 128 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\LootConditionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
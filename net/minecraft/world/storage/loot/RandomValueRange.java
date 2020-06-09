/*    */ package net.minecraft.world.storage.loot;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class RandomValueRange
/*    */ {
/*    */   private final float min;
/*    */   private final float max;
/*    */   
/*    */   public RandomValueRange(float minIn, float maxIn) {
/* 23 */     this.min = minIn;
/* 24 */     this.max = maxIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomValueRange(float value) {
/* 29 */     this.min = value;
/* 30 */     this.max = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMin() {
/* 35 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMax() {
/* 40 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public int generateInt(Random rand) {
/* 45 */     return MathHelper.getInt(rand, MathHelper.floor(this.min), MathHelper.floor(this.max));
/*    */   }
/*    */ 
/*    */   
/*    */   public float generateFloat(Random rand) {
/* 50 */     return MathHelper.nextFloat(rand, this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInRange(int value) {
/* 55 */     return (value <= this.max && value >= this.min);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     implements JsonDeserializer<RandomValueRange>, JsonSerializer<RandomValueRange>
/*    */   {
/*    */     public RandomValueRange deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 62 */       if (JsonUtils.isNumber(p_deserialize_1_))
/*    */       {
/* 64 */         return new RandomValueRange(JsonUtils.getFloat(p_deserialize_1_, "value"));
/*    */       }
/*    */ 
/*    */       
/* 68 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "value");
/* 69 */       float f = JsonUtils.getFloat(jsonobject, "min");
/* 70 */       float f1 = JsonUtils.getFloat(jsonobject, "max");
/* 71 */       return new RandomValueRange(f, f1);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public JsonElement serialize(RandomValueRange p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 77 */       if (p_serialize_1_.min == p_serialize_1_.max)
/*    */       {
/* 79 */         return (JsonElement)new JsonPrimitive(Float.valueOf(p_serialize_1_.min));
/*    */       }
/*    */ 
/*    */       
/* 83 */       JsonObject jsonobject = new JsonObject();
/* 84 */       jsonobject.addProperty("min", Float.valueOf(p_serialize_1_.min));
/* 85 */       jsonobject.addProperty("max", Float.valueOf(p_serialize_1_.max));
/* 86 */       return (JsonElement)jsonobject;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\RandomValueRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
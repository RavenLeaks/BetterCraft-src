/*    */ package net.minecraft.world.storage.loot.conditions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ 
/*    */ public class RandomChance
/*    */   implements LootCondition
/*    */ {
/*    */   private final float chance;
/*    */   
/*    */   public RandomChance(float chanceIn) {
/* 17 */     this.chance = chanceIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean testCondition(Random rand, LootContext context) {
/* 22 */     return (rand.nextFloat() < this.chance);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootCondition.Serializer<RandomChance>
/*    */   {
/*    */     protected Serializer() {
/* 29 */       super(new ResourceLocation("random_chance"), RandomChance.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject json, RandomChance value, JsonSerializationContext context) {
/* 34 */       json.addProperty("chance", Float.valueOf(value.chance));
/*    */     }
/*    */ 
/*    */     
/*    */     public RandomChance deserialize(JsonObject json, JsonDeserializationContext context) {
/* 39 */       return new RandomChance(JsonUtils.getFloat(json, "chance"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\RandomChance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.world.storage.loot.conditions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ 
/*    */ 
/*    */ public interface LootCondition
/*    */ {
/*    */   boolean testCondition(Random paramRandom, LootContext paramLootContext);
/*    */   
/*    */   public static abstract class Serializer<T extends LootCondition>
/*    */   {
/*    */     private final ResourceLocation lootTableLocation;
/*    */     private final Class<T> conditionClass;
/*    */     
/*    */     protected Serializer(ResourceLocation location, Class<T> clazz) {
/* 21 */       this.lootTableLocation = location;
/* 22 */       this.conditionClass = clazz;
/*    */     }
/*    */ 
/*    */     
/*    */     public ResourceLocation getLootTableLocation() {
/* 27 */       return this.lootTableLocation;
/*    */     }
/*    */ 
/*    */     
/*    */     public Class<T> getConditionClass() {
/* 32 */       return this.conditionClass;
/*    */     }
/*    */     
/*    */     public abstract void serialize(JsonObject param1JsonObject, T param1T, JsonSerializationContext param1JsonSerializationContext);
/*    */     
/*    */     public abstract T deserialize(JsonObject param1JsonObject, JsonDeserializationContext param1JsonDeserializationContext);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\LootCondition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
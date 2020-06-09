/*    */ package net.minecraft.world.storage.loot.conditions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ 
/*    */ public class RandomChanceWithLooting
/*    */   implements LootCondition
/*    */ {
/*    */   private final float chance;
/*    */   private final float lootingMultiplier;
/*    */   
/*    */   public RandomChanceWithLooting(float chanceIn, float lootingMultiplierIn) {
/* 20 */     this.chance = chanceIn;
/* 21 */     this.lootingMultiplier = lootingMultiplierIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean testCondition(Random rand, LootContext context) {
/* 26 */     int i = 0;
/*    */     
/* 28 */     if (context.getKiller() instanceof EntityLivingBase)
/*    */     {
/* 30 */       i = EnchantmentHelper.getLootingModifier((EntityLivingBase)context.getKiller());
/*    */     }
/*    */     
/* 33 */     return (rand.nextFloat() < this.chance + i * this.lootingMultiplier);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootCondition.Serializer<RandomChanceWithLooting>
/*    */   {
/*    */     protected Serializer() {
/* 40 */       super(new ResourceLocation("random_chance_with_looting"), RandomChanceWithLooting.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject json, RandomChanceWithLooting value, JsonSerializationContext context) {
/* 45 */       json.addProperty("chance", Float.valueOf(value.chance));
/* 46 */       json.addProperty("looting_multiplier", Float.valueOf(value.lootingMultiplier));
/*    */     }
/*    */ 
/*    */     
/*    */     public RandomChanceWithLooting deserialize(JsonObject json, JsonDeserializationContext context) {
/* 51 */       return new RandomChanceWithLooting(JsonUtils.getFloat(json, "chance"), JsonUtils.getFloat(json, "looting_multiplier"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\RandomChanceWithLooting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
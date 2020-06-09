/*    */ package net.minecraft.world.storage.loot.functions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.RandomValueRange;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ 
/*    */ public class EnchantWithLevels
/*    */   extends LootFunction
/*    */ {
/*    */   private final RandomValueRange randomLevel;
/*    */   private final boolean isTreasure;
/*    */   
/*    */   public EnchantWithLevels(LootCondition[] conditionsIn, RandomValueRange randomRange, boolean p_i46627_3_) {
/* 22 */     super(conditionsIn);
/* 23 */     this.randomLevel = randomRange;
/* 24 */     this.isTreasure = p_i46627_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 29 */     return EnchantmentHelper.addRandomEnchantment(rand, stack, this.randomLevel.generateInt(rand), this.isTreasure);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<EnchantWithLevels>
/*    */   {
/*    */     public Serializer() {
/* 36 */       super(new ResourceLocation("enchant_with_levels"), EnchantWithLevels.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, EnchantWithLevels functionClazz, JsonSerializationContext serializationContext) {
/* 41 */       object.add("levels", serializationContext.serialize(functionClazz.randomLevel));
/* 42 */       object.addProperty("treasure", Boolean.valueOf(functionClazz.isTreasure));
/*    */     }
/*    */ 
/*    */     
/*    */     public EnchantWithLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 47 */       RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.deserializeClass(object, "levels", deserializationContext, RandomValueRange.class);
/* 48 */       boolean flag = JsonUtils.getBoolean(object, "treasure", false);
/* 49 */       return new EnchantWithLevels(conditionsIn, randomvaluerange, flag);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\EnchantWithLevels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
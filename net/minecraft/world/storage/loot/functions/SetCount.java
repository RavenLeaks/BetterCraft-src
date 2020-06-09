/*    */ package net.minecraft.world.storage.loot.functions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.RandomValueRange;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ 
/*    */ public class SetCount
/*    */   extends LootFunction
/*    */ {
/*    */   private final RandomValueRange countRange;
/*    */   
/*    */   public SetCount(LootCondition[] conditionsIn, RandomValueRange countRangeIn) {
/* 20 */     super(conditionsIn);
/* 21 */     this.countRange = countRangeIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 26 */     stack.func_190920_e(this.countRange.generateInt(rand));
/* 27 */     return stack;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<SetCount>
/*    */   {
/*    */     protected Serializer() {
/* 34 */       super(new ResourceLocation("set_count"), SetCount.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, SetCount functionClazz, JsonSerializationContext serializationContext) {
/* 39 */       object.add("count", serializationContext.serialize(functionClazz.countRange));
/*    */     }
/*    */ 
/*    */     
/*    */     public SetCount deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 44 */       return new SetCount(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\SetCount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
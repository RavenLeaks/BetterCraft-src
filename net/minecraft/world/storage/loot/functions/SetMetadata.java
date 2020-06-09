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
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SetMetadata
/*    */   extends LootFunction {
/* 18 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private final RandomValueRange metaRange;
/*    */   
/*    */   public SetMetadata(LootCondition[] conditionsIn, RandomValueRange metaRangeIn) {
/* 23 */     super(conditionsIn);
/* 24 */     this.metaRange = metaRangeIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 29 */     if (stack.isItemStackDamageable()) {
/*    */       
/* 31 */       LOGGER.warn("Couldn't set data of loot item {}", stack);
/*    */     }
/*    */     else {
/*    */       
/* 35 */       stack.setItemDamage(this.metaRange.generateInt(rand));
/*    */     } 
/*    */     
/* 38 */     return stack;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<SetMetadata>
/*    */   {
/*    */     protected Serializer() {
/* 45 */       super(new ResourceLocation("set_data"), SetMetadata.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, SetMetadata functionClazz, JsonSerializationContext serializationContext) {
/* 50 */       object.add("data", serializationContext.serialize(functionClazz.metaRange));
/*    */     }
/*    */ 
/*    */     
/*    */     public SetMetadata deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 55 */       return new SetMetadata(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "data", deserializationContext, RandomValueRange.class));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\SetMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
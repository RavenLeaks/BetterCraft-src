/*    */ package net.minecraft.world.storage.loot.functions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.RandomValueRange;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SetDamage
/*    */   extends LootFunction {
/* 19 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private final RandomValueRange damageRange;
/*    */   
/*    */   public SetDamage(LootCondition[] conditionsIn, RandomValueRange damageRangeIn) {
/* 24 */     super(conditionsIn);
/* 25 */     this.damageRange = damageRangeIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 30 */     if (stack.isItemStackDamageable()) {
/*    */       
/* 32 */       float f = 1.0F - this.damageRange.generateFloat(rand);
/* 33 */       stack.setItemDamage(MathHelper.floor(f * stack.getMaxDamage()));
/*    */     }
/*    */     else {
/*    */       
/* 37 */       LOGGER.warn("Couldn't set damage of loot item {}", stack);
/*    */     } 
/*    */     
/* 40 */     return stack;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<SetDamage>
/*    */   {
/*    */     protected Serializer() {
/* 47 */       super(new ResourceLocation("set_damage"), SetDamage.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, SetDamage functionClazz, JsonSerializationContext serializationContext) {
/* 52 */       object.add("damage", serializationContext.serialize(functionClazz.damageRange));
/*    */     }
/*    */ 
/*    */     
/*    */     public SetDamage deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 57 */       return new SetDamage(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "damage", deserializationContext, RandomValueRange.class));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\SetDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
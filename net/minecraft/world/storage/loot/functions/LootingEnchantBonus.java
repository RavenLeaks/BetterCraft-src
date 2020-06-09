/*    */ package net.minecraft.world.storage.loot.functions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.RandomValueRange;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ 
/*    */ public class LootingEnchantBonus
/*    */   extends LootFunction
/*    */ {
/*    */   private final RandomValueRange count;
/*    */   private final int limit;
/*    */   
/*    */   public LootingEnchantBonus(LootCondition[] p_i47145_1_, RandomValueRange p_i47145_2_, int p_i47145_3_) {
/* 24 */     super(p_i47145_1_);
/* 25 */     this.count = p_i47145_2_;
/* 26 */     this.limit = p_i47145_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 31 */     Entity entity = context.getKiller();
/*    */     
/* 33 */     if (entity instanceof EntityLivingBase) {
/*    */       
/* 35 */       int i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
/*    */       
/* 37 */       if (i == 0)
/*    */       {
/* 39 */         return stack;
/*    */       }
/*    */       
/* 42 */       float f = i * this.count.generateFloat(rand);
/* 43 */       stack.func_190917_f(Math.round(f));
/*    */       
/* 45 */       if (this.limit != 0 && stack.func_190916_E() > this.limit)
/*    */       {
/* 47 */         stack.func_190920_e(this.limit);
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return stack;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<LootingEnchantBonus>
/*    */   {
/*    */     protected Serializer() {
/* 58 */       super(new ResourceLocation("looting_enchant"), LootingEnchantBonus.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, LootingEnchantBonus functionClazz, JsonSerializationContext serializationContext) {
/* 63 */       object.add("count", serializationContext.serialize(functionClazz.count));
/*    */       
/* 65 */       if (functionClazz.limit > 0)
/*    */       {
/* 67 */         object.add("limit", serializationContext.serialize(Integer.valueOf(functionClazz.limit)));
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public LootingEnchantBonus deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 73 */       int i = JsonUtils.getInt(object, "limit", 0);
/* 74 */       return new LootingEnchantBonus(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class), i);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\LootingEnchantBonus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
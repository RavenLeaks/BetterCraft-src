/*     */ package net.minecraft.world.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ import net.minecraft.world.storage.loot.conditions.LootConditionManager;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ public class LootPool
/*     */ {
/*     */   private final LootEntry[] lootEntries;
/*     */   private final LootCondition[] poolConditions;
/*     */   private final RandomValueRange rolls;
/*     */   private final RandomValueRange bonusRolls;
/*     */   
/*     */   public LootPool(LootEntry[] lootEntriesIn, LootCondition[] poolConditionsIn, RandomValueRange rollsIn, RandomValueRange bonusRollsIn) {
/*  31 */     this.lootEntries = lootEntriesIn;
/*  32 */     this.poolConditions = poolConditionsIn;
/*  33 */     this.rolls = rollsIn;
/*  34 */     this.bonusRolls = bonusRollsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createLootRoll(Collection<ItemStack> stacks, Random rand, LootContext context) {
/*  45 */     List<LootEntry> list = Lists.newArrayList();
/*  46 */     int i = 0; byte b; int j;
/*     */     LootEntry[] arrayOfLootEntry;
/*  48 */     for (j = (arrayOfLootEntry = this.lootEntries).length, b = 0; b < j; ) { LootEntry lootentry = arrayOfLootEntry[b];
/*     */       
/*  50 */       if (LootConditionManager.testAllConditions(lootentry.conditions, rand, context)) {
/*     */         
/*  52 */         int k = lootentry.getEffectiveWeight(context.getLuck());
/*     */         
/*  54 */         if (k > 0) {
/*     */           
/*  56 */           list.add(lootentry);
/*  57 */           i += k;
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/*  62 */     if (i != 0 && !list.isEmpty()) {
/*     */       
/*  64 */       int k = rand.nextInt(i);
/*     */       
/*  66 */       for (LootEntry lootentry1 : list) {
/*     */         
/*  68 */         k -= lootentry1.getEffectiveWeight(context.getLuck());
/*     */         
/*  70 */         if (k < 0) {
/*     */           
/*  72 */           lootentry1.addLoot(stacks, rand, context);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
/*  84 */     if (LootConditionManager.testAllConditions(this.poolConditions, rand, context)) {
/*     */       
/*  86 */       int i = this.rolls.generateInt(rand) + MathHelper.floor(this.bonusRolls.generateFloat(rand) * context.getLuck());
/*     */       
/*  88 */       for (int j = 0; j < i; j++)
/*     */       {
/*  90 */         createLootRoll(stacks, rand, context);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<LootPool>, JsonSerializer<LootPool>
/*     */   {
/*     */     public LootPool deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  99 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "loot pool");
/* 100 */       LootEntry[] alootentry = (LootEntry[])JsonUtils.deserializeClass(jsonobject, "entries", p_deserialize_3_, LootEntry[].class);
/* 101 */       LootCondition[] alootcondition = (LootCondition[])JsonUtils.deserializeClass(jsonobject, "conditions", new LootCondition[0], p_deserialize_3_, LootCondition[].class);
/* 102 */       RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.deserializeClass(jsonobject, "rolls", p_deserialize_3_, RandomValueRange.class);
/* 103 */       RandomValueRange randomvaluerange1 = (RandomValueRange)JsonUtils.deserializeClass(jsonobject, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), p_deserialize_3_, RandomValueRange.class);
/* 104 */       return new LootPool(alootentry, alootcondition, randomvaluerange, randomvaluerange1);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(LootPool p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 109 */       JsonObject jsonobject = new JsonObject();
/* 110 */       jsonobject.add("entries", p_serialize_3_.serialize(p_serialize_1_.lootEntries));
/* 111 */       jsonobject.add("rolls", p_serialize_3_.serialize(p_serialize_1_.rolls));
/*     */       
/* 113 */       if (p_serialize_1_.bonusRolls.getMin() != 0.0F && p_serialize_1_.bonusRolls.getMax() != 0.0F)
/*     */       {
/* 115 */         jsonobject.add("bonus_rolls", p_serialize_3_.serialize(p_serialize_1_.bonusRolls));
/*     */       }
/*     */       
/* 118 */       if (!ArrayUtils.isEmpty((Object[])p_serialize_1_.poolConditions))
/*     */       {
/* 120 */         jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.poolConditions));
/*     */       }
/*     */       
/* 123 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
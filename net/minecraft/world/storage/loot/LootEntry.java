/*     */ package net.minecraft.world.storage.loot;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.Random;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ 
/*     */ 
/*     */ public abstract class LootEntry
/*     */ {
/*     */   protected final int weight;
/*     */   protected final int quality;
/*     */   protected final LootCondition[] conditions;
/*     */   
/*     */   protected LootEntry(int weightIn, int qualityIn, LootCondition[] conditionsIn) {
/*  27 */     this.weight = weightIn;
/*  28 */     this.quality = qualityIn;
/*  29 */     this.conditions = conditionsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectiveWeight(float luck) {
/*  37 */     return Math.max(MathHelper.floor(this.weight + this.quality * luck), 0);
/*     */   }
/*     */   
/*     */   public abstract void addLoot(Collection<ItemStack> paramCollection, Random paramRandom, LootContext paramLootContext);
/*     */   
/*     */   protected abstract void serialize(JsonObject paramJsonObject, JsonSerializationContext paramJsonSerializationContext);
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<LootEntry>, JsonSerializer<LootEntry> {
/*     */     public LootEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       LootCondition[] alootcondition;
/*  48 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "loot item");
/*  49 */       String s = JsonUtils.getString(jsonobject, "type");
/*  50 */       int i = JsonUtils.getInt(jsonobject, "weight", 1);
/*  51 */       int j = JsonUtils.getInt(jsonobject, "quality", 0);
/*     */ 
/*     */       
/*  54 */       if (jsonobject.has("conditions")) {
/*     */         
/*  56 */         alootcondition = (LootCondition[])JsonUtils.deserializeClass(jsonobject, "conditions", p_deserialize_3_, LootCondition[].class);
/*     */       }
/*     */       else {
/*     */         
/*  60 */         alootcondition = new LootCondition[0];
/*     */       } 
/*     */       
/*  63 */       if ("item".equals(s))
/*     */       {
/*  65 */         return LootEntryItem.deserialize(jsonobject, p_deserialize_3_, i, j, alootcondition);
/*     */       }
/*  67 */       if ("loot_table".equals(s))
/*     */       {
/*  69 */         return LootEntryTable.deserialize(jsonobject, p_deserialize_3_, i, j, alootcondition);
/*     */       }
/*  71 */       if ("empty".equals(s))
/*     */       {
/*  73 */         return LootEntryEmpty.deserialize(jsonobject, p_deserialize_3_, i, j, alootcondition);
/*     */       }
/*     */ 
/*     */       
/*  77 */       throw new JsonSyntaxException("Unknown loot entry type '" + s + "'");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonElement serialize(LootEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/*  83 */       JsonObject jsonobject = new JsonObject();
/*  84 */       jsonobject.addProperty("weight", Integer.valueOf(p_serialize_1_.weight));
/*  85 */       jsonobject.addProperty("quality", Integer.valueOf(p_serialize_1_.quality));
/*     */       
/*  87 */       if (p_serialize_1_.conditions.length > 0)
/*     */       {
/*  89 */         jsonobject.add("conditions", p_serialize_3_.serialize(p_serialize_1_.conditions));
/*     */       }
/*     */       
/*  92 */       if (p_serialize_1_ instanceof LootEntryItem) {
/*     */         
/*  94 */         jsonobject.addProperty("type", "item");
/*     */       }
/*  96 */       else if (p_serialize_1_ instanceof LootEntryTable) {
/*     */         
/*  98 */         jsonobject.addProperty("type", "loot_table");
/*     */       }
/*     */       else {
/*     */         
/* 102 */         if (!(p_serialize_1_ instanceof LootEntryEmpty))
/*     */         {
/* 104 */           throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_);
/*     */         }
/*     */         
/* 107 */         jsonobject.addProperty("type", "empty");
/*     */       } 
/*     */       
/* 110 */       p_serialize_1_.serialize(jsonobject, p_serialize_3_);
/* 111 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
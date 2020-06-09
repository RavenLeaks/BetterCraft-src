/*    */ package net.minecraft.world.storage.loot;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Collection;
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ 
/*    */ public class LootEntryTable
/*    */   extends LootEntry
/*    */ {
/*    */   protected final ResourceLocation table;
/*    */   
/*    */   public LootEntryTable(ResourceLocation tableIn, int weightIn, int qualityIn, LootCondition[] conditionsIn) {
/* 19 */     super(weightIn, qualityIn, conditionsIn);
/* 20 */     this.table = tableIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
/* 25 */     LootTable loottable = context.getLootTableManager().getLootTableFromLocation(this.table);
/* 26 */     Collection<ItemStack> collection = loottable.generateLootForPools(rand, context);
/* 27 */     stacks.addAll(collection);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject json, JsonSerializationContext context) {
/* 32 */     json.addProperty("name", this.table.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public static LootEntryTable deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn) {
/* 37 */     ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(object, "name"));
/* 38 */     return new LootEntryTable(resourcelocation, weightIn, qualityIn, conditionsIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootEntryTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
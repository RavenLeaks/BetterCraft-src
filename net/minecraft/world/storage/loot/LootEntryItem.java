/*    */ package net.minecraft.world.storage.loot;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Collection;
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ import net.minecraft.world.storage.loot.conditions.LootConditionManager;
/*    */ import net.minecraft.world.storage.loot.functions.LootFunction;
/*    */ 
/*    */ public class LootEntryItem
/*    */   extends LootEntry
/*    */ {
/*    */   protected final Item item;
/*    */   protected final LootFunction[] functions;
/*    */   
/*    */   public LootEntryItem(Item itemIn, int weightIn, int qualityIn, LootFunction[] functionsIn, LootCondition[] conditionsIn) {
/* 23 */     super(weightIn, qualityIn, conditionsIn);
/* 24 */     this.item = itemIn;
/* 25 */     this.functions = functionsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
/* 30 */     ItemStack itemstack = new ItemStack(this.item); byte b; int i;
/*    */     LootFunction[] arrayOfLootFunction;
/* 32 */     for (i = (arrayOfLootFunction = this.functions).length, b = 0; b < i; ) { LootFunction lootfunction = arrayOfLootFunction[b];
/*    */       
/* 34 */       if (LootConditionManager.testAllConditions(lootfunction.getConditions(), rand, context))
/*    */       {
/* 36 */         itemstack = lootfunction.apply(itemstack, rand, context);
/*    */       }
/*    */       b++; }
/*    */     
/* 40 */     if (!itemstack.func_190926_b())
/*    */     {
/* 42 */       if (itemstack.func_190916_E() < this.item.getItemStackLimit()) {
/*    */         
/* 44 */         stacks.add(itemstack);
/*    */       }
/*    */       else {
/*    */         
/* 48 */         int j = itemstack.func_190916_E();
/*    */         
/* 50 */         while (j > 0) {
/*    */           
/* 52 */           ItemStack itemstack1 = itemstack.copy();
/* 53 */           itemstack1.func_190920_e(Math.min(itemstack.getMaxStackSize(), j));
/* 54 */           j -= itemstack1.func_190916_E();
/* 55 */           stacks.add(itemstack1);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject json, JsonSerializationContext context) {
/* 63 */     if (this.functions != null && this.functions.length > 0)
/*    */     {
/* 65 */       json.add("functions", context.serialize(this.functions));
/*    */     }
/*    */     
/* 68 */     ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.item);
/*    */     
/* 70 */     if (resourcelocation == null)
/*    */     {
/* 72 */       throw new IllegalArgumentException("Can't serialize unknown item " + this.item);
/*    */     }
/*    */ 
/*    */     
/* 76 */     json.addProperty("name", resourcelocation.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public static LootEntryItem deserialize(JsonObject object, JsonDeserializationContext deserializationContext, int weightIn, int qualityIn, LootCondition[] conditionsIn) {
/*    */     LootFunction[] alootfunction;
/* 82 */     Item item = JsonUtils.getItem(object, "name");
/*    */ 
/*    */     
/* 85 */     if (object.has("functions")) {
/*    */       
/* 87 */       alootfunction = (LootFunction[])JsonUtils.deserializeClass(object, "functions", deserializationContext, LootFunction[].class);
/*    */     }
/*    */     else {
/*    */       
/* 91 */       alootfunction = new LootFunction[0];
/*    */     } 
/*    */     
/* 94 */     return new LootEntryItem(item, weightIn, qualityIn, alootfunction, conditionsIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootEntryItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
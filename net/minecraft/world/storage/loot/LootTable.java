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
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LootTable
/*     */ {
/*  25 */   private static final Logger LOGGER = LogManager.getLogger();
/*  26 */   public static final LootTable EMPTY_LOOT_TABLE = new LootTable(new LootPool[0]);
/*     */   
/*     */   private final LootPool[] pools;
/*     */   
/*     */   public LootTable(LootPool[] poolsIn) {
/*  31 */     this.pools = poolsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> generateLootForPools(Random rand, LootContext context) {
/*  36 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  38 */     if (context.addLootTable(this)) {
/*     */       byte b; int i; LootPool[] arrayOfLootPool;
/*  40 */       for (i = (arrayOfLootPool = this.pools).length, b = 0; b < i; ) { LootPool lootpool = arrayOfLootPool[b];
/*     */         
/*  42 */         lootpool.generateLoot(list, rand, context);
/*     */         b++; }
/*     */       
/*  45 */       context.removeLootTable(this);
/*     */     }
/*     */     else {
/*     */       
/*  49 */       LOGGER.warn("Detected infinite loop in loot tables");
/*     */     } 
/*     */     
/*  52 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillInventory(IInventory inventory, Random rand, LootContext context) {
/*  57 */     List<ItemStack> list = generateLootForPools(rand, context);
/*  58 */     List<Integer> list1 = getEmptySlotsRandomized(inventory, rand);
/*  59 */     shuffleItems(list, list1.size(), rand);
/*     */     
/*  61 */     for (ItemStack itemstack : list) {
/*     */       
/*  63 */       if (list1.isEmpty()) {
/*     */         
/*  65 */         LOGGER.warn("Tried to over-fill a container");
/*     */         
/*     */         return;
/*     */       } 
/*  69 */       if (itemstack.func_190926_b()) {
/*     */         
/*  71 */         inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), ItemStack.field_190927_a);
/*     */         
/*     */         continue;
/*     */       } 
/*  75 */       inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void shuffleItems(List<ItemStack> stacks, int p_186463_2_, Random rand) {
/*  85 */     List<ItemStack> list = Lists.newArrayList();
/*  86 */     Iterator<ItemStack> iterator = stacks.iterator();
/*     */     
/*  88 */     while (iterator.hasNext()) {
/*     */       
/*  90 */       ItemStack itemstack = iterator.next();
/*     */       
/*  92 */       if (itemstack.func_190926_b()) {
/*     */         
/*  94 */         iterator.remove(); continue;
/*     */       } 
/*  96 */       if (itemstack.func_190916_E() > 1) {
/*     */         
/*  98 */         list.add(itemstack);
/*  99 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     p_186463_2_ -= stacks.size();
/*     */     
/* 105 */     while (p_186463_2_ > 0 && !list.isEmpty()) {
/*     */       
/* 107 */       ItemStack itemstack2 = list.remove(MathHelper.getInt(rand, 0, list.size() - 1));
/* 108 */       int i = MathHelper.getInt(rand, 1, itemstack2.func_190916_E() / 2);
/* 109 */       ItemStack itemstack1 = itemstack2.splitStack(i);
/*     */       
/* 111 */       if (itemstack2.func_190916_E() > 1 && rand.nextBoolean()) {
/*     */         
/* 113 */         list.add(itemstack2);
/*     */       }
/*     */       else {
/*     */         
/* 117 */         stacks.add(itemstack2);
/*     */       } 
/*     */       
/* 120 */       if (itemstack1.func_190916_E() > 1 && rand.nextBoolean()) {
/*     */         
/* 122 */         list.add(itemstack1);
/*     */         
/*     */         continue;
/*     */       } 
/* 126 */       stacks.add(itemstack1);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     stacks.addAll(list);
/* 131 */     Collections.shuffle(stacks, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Integer> getEmptySlotsRandomized(IInventory inventory, Random rand) {
/* 136 */     List<Integer> list = Lists.newArrayList();
/*     */     
/* 138 */     for (int i = 0; i < inventory.getSizeInventory(); i++) {
/*     */       
/* 140 */       if (inventory.getStackInSlot(i).func_190926_b())
/*     */       {
/* 142 */         list.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/*     */     
/* 146 */     Collections.shuffle(list, rand);
/* 147 */     return list;
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<LootTable>, JsonSerializer<LootTable>
/*     */   {
/*     */     public LootTable deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 154 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "loot table");
/* 155 */       LootPool[] alootpool = (LootPool[])JsonUtils.deserializeClass(jsonobject, "pools", new LootPool[0], p_deserialize_3_, LootPool[].class);
/* 156 */       return new LootTable(alootpool);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(LootTable p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 161 */       JsonObject jsonobject = new JsonObject();
/* 162 */       jsonobject.add("pools", p_serialize_3_.serialize(p_serialize_1_.pools));
/* 163 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
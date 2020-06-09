/*     */ package net.minecraft.world.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EnchantRandomly
/*     */   extends LootFunction {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final List<Enchantment> enchantments;
/*     */   
/*     */   public EnchantRandomly(LootCondition[] conditionsIn, @Nullable List<Enchantment> enchantmentsIn) {
/*  35 */     super(conditionsIn);
/*  36 */     this.enchantments = (enchantmentsIn == null) ? Collections.<Enchantment>emptyList() : enchantmentsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/*     */     Enchantment enchantment;
/*  43 */     if (this.enchantments.isEmpty()) {
/*     */       
/*  45 */       List<Enchantment> list = Lists.newArrayList();
/*     */       
/*  47 */       for (Enchantment enchantment1 : Enchantment.REGISTRY) {
/*     */         
/*  49 */         if (stack.getItem() == Items.BOOK || enchantment1.canApply(stack))
/*     */         {
/*  51 */           list.add(enchantment1);
/*     */         }
/*     */       } 
/*     */       
/*  55 */       if (list.isEmpty()) {
/*     */         
/*  57 */         LOGGER.warn("Couldn't find a compatible enchantment for {}", stack);
/*  58 */         return stack;
/*     */       } 
/*     */       
/*  61 */       enchantment = list.get(rand.nextInt(list.size()));
/*     */     }
/*     */     else {
/*     */       
/*  65 */       enchantment = this.enchantments.get(rand.nextInt(this.enchantments.size()));
/*     */     } 
/*     */     
/*  68 */     int i = MathHelper.getInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
/*     */     
/*  70 */     if (stack.getItem() == Items.BOOK) {
/*     */       
/*  72 */       stack = new ItemStack(Items.ENCHANTED_BOOK);
/*  73 */       ItemEnchantedBook.addEnchantment(stack, new EnchantmentData(enchantment, i));
/*     */     }
/*     */     else {
/*     */       
/*  77 */       stack.addEnchantment(enchantment, i);
/*     */     } 
/*     */     
/*  80 */     return stack;
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     extends LootFunction.Serializer<EnchantRandomly>
/*     */   {
/*     */     public Serializer() {
/*  87 */       super(new ResourceLocation("enchant_randomly"), EnchantRandomly.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public void serialize(JsonObject object, EnchantRandomly functionClazz, JsonSerializationContext serializationContext) {
/*  92 */       if (!functionClazz.enchantments.isEmpty()) {
/*     */         
/*  94 */         JsonArray jsonarray = new JsonArray();
/*     */         
/*  96 */         for (Enchantment enchantment : functionClazz.enchantments) {
/*     */           
/*  98 */           ResourceLocation resourcelocation = (ResourceLocation)Enchantment.REGISTRY.getNameForObject(enchantment);
/*     */           
/* 100 */           if (resourcelocation == null)
/*     */           {
/* 102 */             throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchantment);
/*     */           }
/*     */           
/* 105 */           jsonarray.add((JsonElement)new JsonPrimitive(resourcelocation.toString()));
/*     */         } 
/*     */         
/* 108 */         object.add("enchantments", (JsonElement)jsonarray);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public EnchantRandomly deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 114 */       List<Enchantment> list = Lists.newArrayList();
/*     */       
/* 116 */       if (object.has("enchantments"))
/*     */       {
/* 118 */         for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "enchantments")) {
/*     */           
/* 120 */           String s = JsonUtils.getString(jsonelement, "enchantment");
/* 121 */           Enchantment enchantment = (Enchantment)Enchantment.REGISTRY.getObject(new ResourceLocation(s));
/*     */           
/* 123 */           if (enchantment == null)
/*     */           {
/* 125 */             throw new JsonSyntaxException("Unknown enchantment '" + s + "'");
/*     */           }
/*     */           
/* 128 */           list.add(enchantment);
/*     */         } 
/*     */       }
/*     */       
/* 132 */       return new EnchantRandomly(conditionsIn, list);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\EnchantRandomly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
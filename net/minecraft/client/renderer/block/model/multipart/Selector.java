/*     */ package net.minecraft.client.renderer.block.model.multipart;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.VariantList;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ public class Selector
/*     */ {
/*     */   private final ICondition condition;
/*     */   private final VariantList variantList;
/*     */   
/*     */   public Selector(ICondition conditionIn, VariantList variantListIn) {
/*  28 */     if (conditionIn == null)
/*     */     {
/*  30 */       throw new IllegalArgumentException("Missing condition for selector");
/*     */     }
/*  32 */     if (variantListIn == null)
/*     */     {
/*  34 */       throw new IllegalArgumentException("Missing variant for selector");
/*     */     }
/*     */ 
/*     */     
/*  38 */     this.condition = conditionIn;
/*  39 */     this.variantList = variantListIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VariantList getVariantList() {
/*  45 */     return this.variantList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Predicate<IBlockState> getPredicate(BlockStateContainer state) {
/*  50 */     return this.condition.getPredicate(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  55 */     if (this == p_equals_1_)
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (p_equals_1_ instanceof Selector) {
/*     */       
/*  63 */       Selector selector = (Selector)p_equals_1_;
/*     */       
/*  65 */       if (this.condition.equals(selector.condition))
/*     */       {
/*  67 */         return this.variantList.equals(selector.variantList);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  77 */     return 31 * this.condition.hashCode() + this.variantList.hashCode();
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<Selector> {
/*  82 */     private static final Function<JsonElement, ICondition> FUNCTION_OR_AND = new Function<JsonElement, ICondition>()
/*     */       {
/*     */         @Nullable
/*     */         public ICondition apply(@Nullable JsonElement p_apply_1_)
/*     */         {
/*  87 */           return (p_apply_1_ == null) ? null : Selector.Deserializer.getOrAndCondition(p_apply_1_.getAsJsonObject());
/*     */         }
/*     */       };
/*  90 */     private static final Function<Map.Entry<String, JsonElement>, ICondition> FUNCTION_PROPERTY_VALUE = new Function<Map.Entry<String, JsonElement>, ICondition>()
/*     */       {
/*     */         @Nullable
/*     */         public ICondition apply(@Nullable Map.Entry<String, JsonElement> p_apply_1_)
/*     */         {
/*  95 */           return (p_apply_1_ == null) ? null : Selector.Deserializer.makePropertyValue(p_apply_1_);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     public Selector deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 101 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 102 */       return new Selector(getWhenCondition(jsonobject), (VariantList)p_deserialize_3_.deserialize(jsonobject.get("apply"), VariantList.class));
/*     */     }
/*     */ 
/*     */     
/*     */     private ICondition getWhenCondition(JsonObject json) {
/* 107 */       return json.has("when") ? getOrAndCondition(JsonUtils.getJsonObject(json, "when")) : ICondition.TRUE;
/*     */     }
/*     */ 
/*     */     
/*     */     @VisibleForTesting
/*     */     static ICondition getOrAndCondition(JsonObject json) {
/* 113 */       Set<Map.Entry<String, JsonElement>> set = json.entrySet();
/*     */       
/* 115 */       if (set.isEmpty())
/*     */       {
/* 117 */         throw new JsonParseException("No elements found in selector");
/*     */       }
/* 119 */       if (set.size() == 1) {
/*     */         
/* 121 */         if (json.has("OR"))
/*     */         {
/* 123 */           return new ConditionOr(Iterables.transform((Iterable)JsonUtils.getJsonArray(json, "OR"), FUNCTION_OR_AND));
/*     */         }
/*     */ 
/*     */         
/* 127 */         return json.has("AND") ? new ConditionAnd(Iterables.transform((Iterable)JsonUtils.getJsonArray(json, "AND"), FUNCTION_OR_AND)) : makePropertyValue(set.iterator().next());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 132 */       return new ConditionAnd(Iterables.transform(set, FUNCTION_PROPERTY_VALUE));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static ConditionPropertyValue makePropertyValue(Map.Entry<String, JsonElement> entry) {
/* 138 */       return new ConditionPropertyValue(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\multipart\Selector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
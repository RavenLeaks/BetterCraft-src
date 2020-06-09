/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class VariantList
/*    */ {
/*    */   private final List<Variant> variantList;
/*    */   
/*    */   public VariantList(List<Variant> variantListIn) {
/* 18 */     this.variantList = variantListIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Variant> getVariantList() {
/* 23 */     return this.variantList;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 28 */     if (this == p_equals_1_)
/*    */     {
/* 30 */       return true;
/*    */     }
/* 32 */     if (p_equals_1_ instanceof VariantList) {
/*    */       
/* 34 */       VariantList variantlist = (VariantList)p_equals_1_;
/* 35 */       return this.variantList.equals(variantlist.variantList);
/*    */     } 
/*    */ 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 45 */     return this.variantList.hashCode();
/*    */   }
/*    */   
/*    */   public static class Deserializer
/*    */     implements JsonDeserializer<VariantList>
/*    */   {
/*    */     public VariantList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 52 */       List<Variant> list = Lists.newArrayList();
/*    */       
/* 54 */       if (p_deserialize_1_.isJsonArray()) {
/*    */         
/* 56 */         JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();
/*    */         
/* 58 */         if (jsonarray.size() == 0)
/*    */         {
/* 60 */           throw new JsonParseException("Empty variant array");
/*    */         }
/*    */         
/* 63 */         for (JsonElement jsonelement : jsonarray)
/*    */         {
/* 65 */           list.add((Variant)p_deserialize_3_.deserialize(jsonelement, Variant.class));
/*    */         }
/*    */       }
/*    */       else {
/*    */         
/* 70 */         list.add((Variant)p_deserialize_3_.deserialize(p_deserialize_1_, Variant.class));
/*    */       } 
/*    */       
/* 73 */       return new VariantList(list);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\VariantList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
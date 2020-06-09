/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.IItemPropertyGetter;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemOverride
/*    */ {
/*    */   private final ResourceLocation location;
/*    */   private final Map<ResourceLocation, Float> mapResourceValues;
/*    */   
/*    */   public ItemOverride(ResourceLocation locationIn, Map<ResourceLocation, Float> propertyValues) {
/* 28 */     this.location = locationIn;
/* 29 */     this.mapResourceValues = propertyValues;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getLocation() {
/* 37 */     return this.location;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean matchesItemStack(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase livingEntity) {
/* 42 */     Item item = stack.getItem();
/*    */     
/* 44 */     for (Map.Entry<ResourceLocation, Float> entry : this.mapResourceValues.entrySet()) {
/*    */       
/* 46 */       IItemPropertyGetter iitempropertygetter = item.getPropertyGetter(entry.getKey());
/*    */       
/* 48 */       if (iitempropertygetter == null || iitempropertygetter.apply(stack, worldIn, livingEntity) < ((Float)entry.getValue()).floatValue())
/*    */       {
/* 50 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   static class Deserializer
/*    */     implements JsonDeserializer<ItemOverride>
/*    */   {
/*    */     public ItemOverride deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 61 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 62 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "model"));
/* 63 */       Map<ResourceLocation, Float> map = makeMapResourceValues(jsonobject);
/* 64 */       return new ItemOverride(resourcelocation, map);
/*    */     }
/*    */ 
/*    */     
/*    */     protected Map<ResourceLocation, Float> makeMapResourceValues(JsonObject p_188025_1_) {
/* 69 */       Map<ResourceLocation, Float> map = Maps.newLinkedHashMap();
/* 70 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_188025_1_, "predicate");
/*    */       
/* 72 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet())
/*    */       {
/* 74 */         map.put(new ResourceLocation(entry.getKey()), Float.valueOf(JsonUtils.getFloat(entry.getValue(), entry.getKey())));
/*    */       }
/*    */       
/* 77 */       return map;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ItemOverride.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
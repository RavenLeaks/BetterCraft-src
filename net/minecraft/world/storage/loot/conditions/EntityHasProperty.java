/*    */ package net.minecraft.world.storage.loot.conditions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.properties.EntityProperty;
/*    */ import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
/*    */ 
/*    */ public class EntityHasProperty
/*    */   implements LootCondition
/*    */ {
/*    */   private final EntityProperty[] properties;
/*    */   private final LootContext.EntityTarget target;
/*    */   
/*    */   public EntityHasProperty(EntityProperty[] propertiesIn, LootContext.EntityTarget targetIn) {
/* 24 */     this.properties = propertiesIn;
/* 25 */     this.target = targetIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean testCondition(Random rand, LootContext context) {
/* 30 */     Entity entity = context.getEntity(this.target);
/*    */     
/* 32 */     if (entity == null)
/*    */     {
/* 34 */       return false; } 
/*    */     byte b;
/*    */     int i;
/*    */     EntityProperty[] arrayOfEntityProperty;
/* 38 */     for (i = (arrayOfEntityProperty = this.properties).length, b = 0; b < i; ) { EntityProperty entityproperty = arrayOfEntityProperty[b];
/*    */       
/* 40 */       if (!entityproperty.testProperty(rand, entity))
/*    */       {
/* 42 */         return false;
/*    */       }
/*    */       b++; }
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Serializer
/*    */     extends LootCondition.Serializer<EntityHasProperty>
/*    */   {
/*    */     protected Serializer() {
/* 54 */       super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject json, EntityHasProperty value, JsonSerializationContext context) {
/* 59 */       JsonObject jsonobject = new JsonObject(); byte b; int i;
/*    */       EntityProperty[] arrayOfEntityProperty;
/* 61 */       for (i = (arrayOfEntityProperty = value.properties).length, b = 0; b < i; ) { EntityProperty entityproperty = arrayOfEntityProperty[b];
/*    */         
/* 63 */         EntityProperty.Serializer<EntityProperty> serializer = EntityPropertyManager.getSerializerFor(entityproperty);
/* 64 */         jsonobject.add(serializer.getName().toString(), serializer.serialize(entityproperty, context));
/*    */         b++; }
/*    */       
/* 67 */       json.add("properties", (JsonElement)jsonobject);
/* 68 */       json.add("entity", context.serialize(value.target));
/*    */     }
/*    */ 
/*    */     
/*    */     public EntityHasProperty deserialize(JsonObject json, JsonDeserializationContext context) {
/* 73 */       Set<Map.Entry<String, JsonElement>> set = JsonUtils.getJsonObject(json, "properties").entrySet();
/* 74 */       EntityProperty[] aentityproperty = new EntityProperty[set.size()];
/* 75 */       int i = 0;
/*    */       
/* 77 */       for (Map.Entry<String, JsonElement> entry : set)
/*    */       {
/* 79 */         aentityproperty[i++] = EntityPropertyManager.getSerializerForName(new ResourceLocation(entry.getKey())).deserialize(entry.getValue(), context);
/*    */       }
/*    */       
/* 82 */       return new EntityHasProperty(aentityproperty, (LootContext.EntityTarget)JsonUtils.deserializeClass(json, "entity", context, LootContext.EntityTarget.class));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\EntityHasProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
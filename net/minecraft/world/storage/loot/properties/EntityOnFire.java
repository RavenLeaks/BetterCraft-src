/*    */ package net.minecraft.world.storage.loot.properties;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EntityOnFire
/*    */   implements EntityProperty
/*    */ {
/*    */   private final boolean onFire;
/*    */   
/*    */   public EntityOnFire(boolean onFireIn) {
/* 18 */     this.onFire = onFireIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean testProperty(Random random, Entity entityIn) {
/* 23 */     return (entityIn.isBurning() == this.onFire);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends EntityProperty.Serializer<EntityOnFire>
/*    */   {
/*    */     protected Serializer() {
/* 30 */       super(new ResourceLocation("on_fire"), EntityOnFire.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public JsonElement serialize(EntityOnFire property, JsonSerializationContext serializationContext) {
/* 35 */       return (JsonElement)new JsonPrimitive(Boolean.valueOf(property.onFire));
/*    */     }
/*    */ 
/*    */     
/*    */     public EntityOnFire deserialize(JsonElement element, JsonDeserializationContext deserializationContext) {
/* 40 */       return new EntityOnFire(JsonUtils.getBoolean(element, "on_fire"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\properties\EntityOnFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
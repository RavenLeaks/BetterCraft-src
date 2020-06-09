/*    */ package net.minecraft.world.storage.loot.properties;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public interface EntityProperty
/*    */ {
/*    */   boolean testProperty(Random paramRandom, Entity paramEntity);
/*    */   
/*    */   public static abstract class Serializer<T extends EntityProperty>
/*    */   {
/*    */     private final ResourceLocation name;
/*    */     private final Class<T> propertyClass;
/*    */     
/*    */     protected Serializer(ResourceLocation nameIn, Class<T> propertyClassIn) {
/* 21 */       this.name = nameIn;
/* 22 */       this.propertyClass = propertyClassIn;
/*    */     }
/*    */ 
/*    */     
/*    */     public ResourceLocation getName() {
/* 27 */       return this.name;
/*    */     }
/*    */ 
/*    */     
/*    */     public Class<T> getPropertyClass() {
/* 32 */       return this.propertyClass;
/*    */     }
/*    */     
/*    */     public abstract JsonElement serialize(T param1T, JsonSerializationContext param1JsonSerializationContext);
/*    */     
/*    */     public abstract T deserialize(JsonElement param1JsonElement, JsonDeserializationContext param1JsonDeserializationContext);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\properties\EntityProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
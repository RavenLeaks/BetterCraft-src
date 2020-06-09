/*    */ package net.minecraft.world.storage.loot.properties;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EntityPropertyManager
/*    */ {
/*  9 */   private static final Map<ResourceLocation, EntityProperty.Serializer<?>> NAME_TO_SERIALIZER_MAP = Maps.newHashMap();
/* 10 */   private static final Map<Class<? extends EntityProperty>, EntityProperty.Serializer<?>> CLASS_TO_SERIALIZER_MAP = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public static <T extends EntityProperty> void registerProperty(EntityProperty.Serializer<? extends T> p_186644_0_) {
/* 14 */     ResourceLocation resourcelocation = p_186644_0_.getName();
/* 15 */     Class<T> oclass = (Class)p_186644_0_.getPropertyClass();
/*    */     
/* 17 */     if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation))
/*    */     {
/* 19 */       throw new IllegalArgumentException("Can't re-register entity property name " + resourcelocation);
/*    */     }
/* 21 */     if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass))
/*    */     {
/* 23 */       throw new IllegalArgumentException("Can't re-register entity property class " + oclass.getName());
/*    */     }
/*    */ 
/*    */     
/* 27 */     NAME_TO_SERIALIZER_MAP.put(resourcelocation, p_186644_0_);
/* 28 */     CLASS_TO_SERIALIZER_MAP.put(oclass, p_186644_0_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EntityProperty.Serializer<?> getSerializerForName(ResourceLocation p_186646_0_) {
/* 34 */     EntityProperty.Serializer<?> serializer = NAME_TO_SERIALIZER_MAP.get(p_186646_0_);
/*    */     
/* 36 */     if (serializer == null)
/*    */     {
/* 38 */       throw new IllegalArgumentException("Unknown loot entity property '" + p_186646_0_ + "'");
/*    */     }
/*    */ 
/*    */     
/* 42 */     return serializer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends EntityProperty> EntityProperty.Serializer<T> getSerializerFor(T property) {
/* 48 */     EntityProperty.Serializer<?> serializer = CLASS_TO_SERIALIZER_MAP.get(property.getClass());
/*    */     
/* 50 */     if (serializer == null)
/*    */     {
/* 52 */       throw new IllegalArgumentException("Unknown loot entity property " + property);
/*    */     }
/*    */ 
/*    */     
/* 56 */     return (EntityProperty.Serializer)serializer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 62 */     registerProperty(new EntityOnFire.Serializer());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\properties\EntityPropertyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
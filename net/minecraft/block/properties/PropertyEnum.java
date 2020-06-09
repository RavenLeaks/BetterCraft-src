/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Optional;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyEnum<T extends Enum<T> & IStringSerializable>
/*    */   extends PropertyHelper<T>
/*    */ {
/*    */   private final ImmutableSet<T> allowedValues;
/* 17 */   private final Map<String, T> nameToValue = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   protected PropertyEnum(String name, Class<T> valueClass, Collection<T> allowedValues) {
/* 21 */     super(name, valueClass);
/* 22 */     this.allowedValues = ImmutableSet.copyOf(allowedValues);
/*    */     
/* 24 */     for (Enum enum_ : allowedValues) {
/*    */       
/* 26 */       String s = ((IStringSerializable)enum_).getName();
/*    */       
/* 28 */       if (this.nameToValue.containsKey(s))
/*    */       {
/* 30 */         throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
/*    */       }
/*    */       
/* 33 */       this.nameToValue.put(s, (T)enum_);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<T> getAllowedValues() {
/* 39 */     return (Collection<T>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<T> parseValue(String value) {
/* 44 */     return Optional.fromNullable((Enum)this.nameToValue.get(value));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(T value) {
/* 52 */     return ((IStringSerializable)value).getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 57 */     if (this == p_equals_1_)
/*    */     {
/* 59 */       return true;
/*    */     }
/* 61 */     if (p_equals_1_ instanceof PropertyEnum && super.equals(p_equals_1_)) {
/*    */       
/* 63 */       PropertyEnum<?> propertyenum = (PropertyEnum)p_equals_1_;
/* 64 */       return (this.allowedValues.equals(propertyenum.allowedValues) && this.nameToValue.equals(propertyenum.nameToValue));
/*    */     } 
/*    */ 
/*    */     
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     int i = super.hashCode();
/* 75 */     i = 31 * i + this.allowedValues.hashCode();
/* 76 */     i = 31 * i + this.nameToValue.hashCode();
/* 77 */     return i;
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: invokestatic alwaysTrue : ()Lcom/google/common/base/Predicate;
/*    */     //   5: invokestatic create : (Ljava/lang/String;Ljava/lang/Class;Lcom/google/common/base/Predicate;)Lnet/minecraft/block/properties/PropertyEnum;
/*    */     //   8: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #82	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	9	0	name	Ljava/lang/String;
/*    */     //   0	9	1	clazz	Ljava/lang/Class;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	9	1	clazz	Ljava/lang/Class<TT;>;
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Predicate<T> filter) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: aload_1
/*    */     //   3: invokevirtual getEnumConstants : ()[Ljava/lang/Object;
/*    */     //   6: checkcast [Ljava/lang/Enum;
/*    */     //   9: invokestatic newArrayList : ([Ljava/lang/Object;)Ljava/util/ArrayList;
/*    */     //   12: aload_2
/*    */     //   13: invokestatic filter : (Ljava/util/Collection;Lcom/google/common/base/Predicate;)Ljava/util/Collection;
/*    */     //   16: invokestatic create : (Ljava/lang/String;Ljava/lang/Class;Ljava/util/Collection;)Lnet/minecraft/block/properties/PropertyEnum;
/*    */     //   19: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #87	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	20	0	name	Ljava/lang/String;
/*    */     //   0	20	1	clazz	Ljava/lang/Class;
/*    */     //   0	20	2	filter	Lcom/google/common/base/Predicate;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	20	1	clazz	Ljava/lang/Class<TT;>;
/*    */     //   0	20	2	filter	Lcom/google/common/base/Predicate<TT;>;
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Enum... values) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: aload_2
/*    */     //   3: invokestatic newArrayList : ([Ljava/lang/Object;)Ljava/util/ArrayList;
/*    */     //   6: invokestatic create : (Ljava/lang/String;Ljava/lang/Class;Ljava/util/Collection;)Lnet/minecraft/block/properties/PropertyEnum;
/*    */     //   9: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #92	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	10	0	name	Ljava/lang/String;
/*    */     //   0	10	1	clazz	Ljava/lang/Class;
/*    */     //   0	10	2	values	[Ljava/lang/Enum;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	10	1	clazz	Ljava/lang/Class<TT;>;
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Collection<T> values) {
/*    */     // Byte code:
/*    */     //   0: new net/minecraft/block/properties/PropertyEnum
/*    */     //   3: dup
/*    */     //   4: aload_0
/*    */     //   5: aload_1
/*    */     //   6: aload_2
/*    */     //   7: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Class;Ljava/util/Collection;)V
/*    */     //   10: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #97	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	11	0	name	Ljava/lang/String;
/*    */     //   0	11	1	clazz	Ljava/lang/Class;
/*    */     //   0	11	2	values	Ljava/util/Collection;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	11	1	clazz	Ljava/lang/Class<TT;>;
/*    */     //   0	11	2	values	Ljava/util/Collection<TT;>;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\properties\PropertyEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
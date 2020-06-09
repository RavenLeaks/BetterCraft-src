/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EnumTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   @Nullable
/*    */   public <T> TypeAdapter<T> create(Gson p_create_1_, TypeToken<T> p_create_2_) {
/* 21 */     Class<T> oclass = p_create_2_.getRawType();
/*    */     
/* 23 */     if (!oclass.isEnum())
/*    */     {
/* 25 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 29 */     final Map<String, T> map = Maps.newHashMap(); byte b; int i;
/*    */     T[] arrayOfT;
/* 31 */     for (i = (arrayOfT = oclass.getEnumConstants()).length, b = 0; b < i; ) { T t = arrayOfT[b];
/*    */       
/* 33 */       map.put(getName(t), t);
/*    */       b++; }
/*    */     
/* 36 */     return new TypeAdapter<T>()
/*    */       {
/*    */         public void write(JsonWriter p_write_1_, T p_write_2_) throws IOException
/*    */         {
/* 40 */           if (p_write_2_ == null) {
/*    */             
/* 42 */             p_write_1_.nullValue();
/*    */           }
/*    */           else {
/*    */             
/* 46 */             p_write_1_.value(EnumTypeAdapterFactory.this.getName(p_write_2_));
/*    */           } 
/*    */         }
/*    */         
/*    */         @Nullable
/*    */         public T read(JsonReader p_read_1_) throws IOException {
/* 52 */           if (p_read_1_.peek() == JsonToken.NULL) {
/*    */             
/* 54 */             p_read_1_.nextNull();
/* 55 */             return null;
/*    */           } 
/*    */ 
/*    */           
/* 59 */           return (T)map.get(p_read_1_.nextString());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String getName(Object objectIn) {
/* 68 */     return (objectIn instanceof Enum) ? ((Enum)objectIn).name().toLowerCase(Locale.ROOT) : objectIn.toString().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EnumTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class ResourceLocation
/*     */   implements Comparable<ResourceLocation> {
/*     */   protected final String resourceDomain;
/*     */   protected final String resourcePath;
/*     */   
/*     */   protected ResourceLocation(int unused, String... resourceName) {
/*  21 */     this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase(Locale.ROOT);
/*  22 */     this.resourcePath = resourceName[1].toLowerCase(Locale.ROOT);
/*  23 */     Validate.notNull(this.resourcePath);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation(String resourceName) {
/*  28 */     this(0, splitObjectName(resourceName));
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation(String resourceDomainIn, String resourcePathIn) {
/*  33 */     this(0, new String[] { resourceDomainIn, resourcePathIn });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String[] splitObjectName(String toSplit) {
/*  42 */     String[] astring = { "minecraft", toSplit };
/*  43 */     int i = toSplit.indexOf(':');
/*     */     
/*  45 */     if (i >= 0) {
/*     */       
/*  47 */       astring[1] = toSplit.substring(i + 1, toSplit.length());
/*     */       
/*  49 */       if (i > 1)
/*     */       {
/*  51 */         astring[0] = toSplit.substring(0, i);
/*     */       }
/*     */     } 
/*     */     
/*  55 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourcePath() {
/*  60 */     return this.resourcePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourceDomain() {
/*  65 */     return this.resourceDomain;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  70 */     return String.valueOf(this.resourceDomain) + ':' + this.resourcePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  75 */     if (this == p_equals_1_)
/*     */     {
/*  77 */       return true;
/*     */     }
/*  79 */     if (!(p_equals_1_ instanceof ResourceLocation))
/*     */     {
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  85 */     ResourceLocation resourcelocation = (ResourceLocation)p_equals_1_;
/*  86 */     return (this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ResourceLocation p_compareTo_1_) {
/*  97 */     int i = this.resourceDomain.compareTo(p_compareTo_1_.resourceDomain);
/*     */     
/*  99 */     if (i == 0)
/*     */     {
/* 101 */       i = this.resourcePath.compareTo(p_compareTo_1_.resourcePath);
/*     */     }
/*     */     
/* 104 */     return i;
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation>
/*     */   {
/*     */     public ResourceLocation deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 111 */       return new ResourceLocation(JsonUtils.getString(p_deserialize_1_, "location"));
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ResourceLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 116 */       return (JsonElement)new JsonPrimitive(p_serialize_1_.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
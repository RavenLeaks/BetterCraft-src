/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.Language;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class LanguageMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<LanguageMetadataSection>
/*    */ {
/*    */   public LanguageMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 18 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 19 */     Set<Language> set = Sets.newHashSet();
/*    */     
/* 21 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*    */       
/* 23 */       String s = entry.getKey();
/*    */       
/* 25 */       if (s.length() > 16)
/*    */       {
/* 27 */         throw new JsonParseException("Invalid language->'" + s + "': language code must not be more than " + '\020' + " characters long");
/*    */       }
/*    */       
/* 30 */       JsonObject jsonobject1 = JsonUtils.getJsonObject(entry.getValue(), "language");
/* 31 */       String s1 = JsonUtils.getString(jsonobject1, "region");
/* 32 */       String s2 = JsonUtils.getString(jsonobject1, "name");
/* 33 */       boolean flag = JsonUtils.getBoolean(jsonobject1, "bidirectional", false);
/*    */       
/* 35 */       if (s1.isEmpty())
/*    */       {
/* 37 */         throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
/*    */       }
/*    */       
/* 40 */       if (s2.isEmpty())
/*    */       {
/* 42 */         throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
/*    */       }
/*    */       
/* 45 */       if (!set.add(new Language(s, s1, s2, flag)))
/*    */       {
/* 47 */         throw new JsonParseException("Duplicate language->'" + s + "' defined");
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return new LanguageMetadataSection(set);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 59 */     return "language";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\LanguageMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
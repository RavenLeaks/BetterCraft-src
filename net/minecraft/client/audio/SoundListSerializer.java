/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class SoundListSerializer
/*    */   implements JsonDeserializer<SoundList>
/*    */ {
/*    */   public SoundList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 19 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "entry");
/* 20 */     boolean flag = JsonUtils.getBoolean(jsonobject, "replace", false);
/* 21 */     String s = JsonUtils.getString(jsonobject, "subtitle", null);
/* 22 */     List<Sound> list = deserializeSounds(jsonobject);
/* 23 */     return new SoundList(list, flag, s);
/*    */   }
/*    */ 
/*    */   
/*    */   private List<Sound> deserializeSounds(JsonObject object) {
/* 28 */     List<Sound> list = Lists.newArrayList();
/*    */     
/* 30 */     if (object.has("sounds")) {
/*    */       
/* 32 */       JsonArray jsonarray = JsonUtils.getJsonArray(object, "sounds");
/*    */       
/* 34 */       for (int i = 0; i < jsonarray.size(); i++) {
/*    */         
/* 36 */         JsonElement jsonelement = jsonarray.get(i);
/*    */         
/* 38 */         if (JsonUtils.isString(jsonelement)) {
/*    */           
/* 40 */           String s = JsonUtils.getString(jsonelement, "sound");
/* 41 */           list.add(new Sound(s, 1.0F, 1.0F, 1, Sound.Type.FILE, false));
/*    */         }
/*    */         else {
/*    */           
/* 45 */           list.add(deserializeSound(JsonUtils.getJsonObject(jsonelement, "sound")));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   private Sound deserializeSound(JsonObject object) {
/* 55 */     String s = JsonUtils.getString(object, "name");
/* 56 */     Sound.Type sound$type = deserializeType(object, Sound.Type.FILE);
/* 57 */     float f = JsonUtils.getFloat(object, "volume", 1.0F);
/* 58 */     Validate.isTrue((f > 0.0F), "Invalid volume", new Object[0]);
/* 59 */     float f1 = JsonUtils.getFloat(object, "pitch", 1.0F);
/* 60 */     Validate.isTrue((f1 > 0.0F), "Invalid pitch", new Object[0]);
/* 61 */     int i = JsonUtils.getInt(object, "weight", 1);
/* 62 */     Validate.isTrue((i > 0), "Invalid weight", new Object[0]);
/* 63 */     boolean flag = JsonUtils.getBoolean(object, "stream", false);
/* 64 */     return new Sound(s, f, f1, i, sound$type, flag);
/*    */   }
/*    */ 
/*    */   
/*    */   private Sound.Type deserializeType(JsonObject object, Sound.Type defaultValue) {
/* 69 */     Sound.Type sound$type = defaultValue;
/*    */     
/* 71 */     if (object.has("type")) {
/*    */       
/* 73 */       sound$type = Sound.Type.getByName(JsonUtils.getString(object, "type"));
/* 74 */       Validate.notNull(sound$type, "Invalid type", new Object[0]);
/*    */     } 
/*    */     
/* 77 */     return sound$type;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundListSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class TextureMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<TextureMetadataSection>
/*    */ {
/*    */   public TextureMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 14 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 15 */     boolean flag = JsonUtils.getBoolean(jsonobject, "blur", false);
/* 16 */     boolean flag1 = JsonUtils.getBoolean(jsonobject, "clamp", false);
/* 17 */     return new TextureMetadataSection(flag, flag1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 25 */     return "texture";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\TextureMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
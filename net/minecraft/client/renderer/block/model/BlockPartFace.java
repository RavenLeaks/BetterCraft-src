/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class BlockPartFace
/*    */ {
/* 15 */   public static final EnumFacing FACING_DEFAULT = null;
/*    */   
/*    */   public final EnumFacing cullFace;
/*    */   public final int tintIndex;
/*    */   public final String texture;
/*    */   public final BlockFaceUV blockFaceUV;
/*    */   
/*    */   public BlockPartFace(@Nullable EnumFacing cullFaceIn, int tintIndexIn, String textureIn, BlockFaceUV blockFaceUVIn) {
/* 23 */     this.cullFace = cullFaceIn;
/* 24 */     this.tintIndex = tintIndexIn;
/* 25 */     this.texture = textureIn;
/* 26 */     this.blockFaceUV = blockFaceUVIn;
/*    */   }
/*    */   
/*    */   static class Deserializer
/*    */     implements JsonDeserializer<BlockPartFace>
/*    */   {
/*    */     public BlockPartFace deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 33 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 34 */       EnumFacing enumfacing = parseCullFace(jsonobject);
/* 35 */       int i = parseTintIndex(jsonobject);
/* 36 */       String s = parseTexture(jsonobject);
/* 37 */       BlockFaceUV blockfaceuv = (BlockFaceUV)p_deserialize_3_.deserialize((JsonElement)jsonobject, BlockFaceUV.class);
/* 38 */       return new BlockPartFace(enumfacing, i, s, blockfaceuv);
/*    */     }
/*    */ 
/*    */     
/*    */     protected int parseTintIndex(JsonObject object) {
/* 43 */       return JsonUtils.getInt(object, "tintindex", -1);
/*    */     }
/*    */ 
/*    */     
/*    */     private String parseTexture(JsonObject object) {
/* 48 */       return JsonUtils.getString(object, "texture");
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     private EnumFacing parseCullFace(JsonObject object) {
/* 54 */       String s = JsonUtils.getString(object, "cullface", "");
/* 55 */       return EnumFacing.byName(s);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BlockPartFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
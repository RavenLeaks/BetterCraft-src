/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ public class BlockFaceUV
/*     */ {
/*     */   public float[] uvs;
/*     */   public final int rotation;
/*     */   
/*     */   public BlockFaceUV(@Nullable float[] uvsIn, int rotationIn) {
/*  20 */     this.uvs = uvsIn;
/*  21 */     this.rotation = rotationIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVertexU(int p_178348_1_) {
/*  26 */     if (this.uvs == null)
/*     */     {
/*  28 */       throw new NullPointerException("uvs");
/*     */     }
/*     */ 
/*     */     
/*  32 */     int i = getVertexRotated(p_178348_1_);
/*  33 */     return (i != 0 && i != 1) ? this.uvs[2] : this.uvs[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVertexV(int p_178346_1_) {
/*  39 */     if (this.uvs == null)
/*     */     {
/*  41 */       throw new NullPointerException("uvs");
/*     */     }
/*     */ 
/*     */     
/*  45 */     int i = getVertexRotated(p_178346_1_);
/*  46 */     return (i != 0 && i != 3) ? this.uvs[3] : this.uvs[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getVertexRotated(int p_178347_1_) {
/*  52 */     return (p_178347_1_ + this.rotation / 90) % 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVertexRotatedRev(int p_178345_1_) {
/*  57 */     return (p_178345_1_ + 4 - this.rotation / 90) % 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUvs(float[] uvsIn) {
/*  62 */     if (this.uvs == null)
/*     */     {
/*  64 */       this.uvs = uvsIn;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Deserializer
/*     */     implements JsonDeserializer<BlockFaceUV>
/*     */   {
/*     */     public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  72 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  73 */       float[] afloat = parseUV(jsonobject);
/*  74 */       int i = parseRotation(jsonobject);
/*  75 */       return new BlockFaceUV(afloat, i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int parseRotation(JsonObject object) {
/*  80 */       int i = JsonUtils.getInt(object, "rotation", 0);
/*     */       
/*  82 */       if (i >= 0 && i % 90 == 0 && i / 90 <= 3)
/*     */       {
/*  84 */         return i;
/*     */       }
/*     */ 
/*     */       
/*  88 */       throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private float[] parseUV(JsonObject object) {
/*  95 */       if (!object.has("uv"))
/*     */       {
/*  97 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 101 */       JsonArray jsonarray = JsonUtils.getJsonArray(object, "uv");
/*     */       
/* 103 */       if (jsonarray.size() != 4)
/*     */       {
/* 105 */         throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
/*     */       }
/*     */ 
/*     */       
/* 109 */       float[] afloat = new float[4];
/*     */       
/* 111 */       for (int i = 0; i < afloat.length; i++)
/*     */       {
/* 113 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), "uv[" + i + "]");
/*     */       }
/*     */       
/* 116 */       return afloat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BlockFaceUV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
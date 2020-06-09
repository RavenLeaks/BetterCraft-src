/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPart
/*     */ {
/*     */   public final Vector3f positionFrom;
/*     */   public final Vector3f positionTo;
/*     */   public final Map<EnumFacing, BlockPartFace> mapFaces;
/*     */   public final BlockPartRotation partRotation;
/*     */   public final boolean shade;
/*     */   
/*     */   public BlockPart(Vector3f positionFromIn, Vector3f positionToIn, Map<EnumFacing, BlockPartFace> mapFacesIn, @Nullable BlockPartRotation partRotationIn, boolean shadeIn) {
/*  30 */     this.positionFrom = positionFromIn;
/*  31 */     this.positionTo = positionToIn;
/*  32 */     this.mapFaces = mapFacesIn;
/*  33 */     this.partRotation = partRotationIn;
/*  34 */     this.shade = shadeIn;
/*  35 */     setDefaultUvs();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultUvs() {
/*  40 */     for (Map.Entry<EnumFacing, BlockPartFace> entry : this.mapFaces.entrySet()) {
/*     */       
/*  42 */       float[] afloat = getFaceUvs(entry.getKey());
/*  43 */       ((BlockPartFace)entry.getValue()).blockFaceUV.setUvs(afloat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getFaceUvs(EnumFacing facing) {
/*  49 */     switch (facing)
/*     */     
/*     */     { case null:
/*  52 */         return new float[] { this.positionFrom.x, 16.0F - this.positionTo.z, this.positionTo.x, 16.0F - this.positionFrom.z };
/*     */       case UP:
/*  54 */         return new float[] { this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z };
/*     */       
/*     */       default:
/*  57 */         return new float[] { 16.0F - this.positionTo.x, 16.0F - this.positionTo.y, 16.0F - this.positionFrom.x, 16.0F - this.positionFrom.y };
/*     */       case SOUTH:
/*  59 */         return new float[] { this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y };
/*     */       case WEST:
/*  61 */         return new float[] { this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y };
/*     */       case EAST:
/*  63 */         break; }  return new float[] { 16.0F - this.positionTo.z, 16.0F - this.positionTo.y, 16.0F - this.positionFrom.z, 16.0F - this.positionFrom.y };
/*     */   }
/*     */ 
/*     */   
/*     */   static class Deserializer
/*     */     implements JsonDeserializer<BlockPart>
/*     */   {
/*     */     public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  71 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  72 */       Vector3f vector3f = parsePositionFrom(jsonobject);
/*  73 */       Vector3f vector3f1 = parsePositionTo(jsonobject);
/*  74 */       BlockPartRotation blockpartrotation = parseRotation(jsonobject);
/*  75 */       Map<EnumFacing, BlockPartFace> map = parseFacesCheck(p_deserialize_3_, jsonobject);
/*     */       
/*  77 */       if (jsonobject.has("shade") && !JsonUtils.isBoolean(jsonobject, "shade"))
/*     */       {
/*  79 */         throw new JsonParseException("Expected shade to be a Boolean");
/*     */       }
/*     */ 
/*     */       
/*  83 */       boolean flag = JsonUtils.getBoolean(jsonobject, "shade", true);
/*  84 */       return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private BlockPartRotation parseRotation(JsonObject object) {
/*  91 */       BlockPartRotation blockpartrotation = null;
/*     */       
/*  93 */       if (object.has("rotation")) {
/*     */         
/*  95 */         JsonObject jsonobject = JsonUtils.getJsonObject(object, "rotation");
/*  96 */         Vector3f vector3f = parsePosition(jsonobject, "origin");
/*  97 */         vector3f.scale(0.0625F);
/*  98 */         EnumFacing.Axis enumfacing$axis = parseAxis(jsonobject);
/*  99 */         float f = parseAngle(jsonobject);
/* 100 */         boolean flag = JsonUtils.getBoolean(jsonobject, "rescale", false);
/* 101 */         blockpartrotation = new BlockPartRotation(vector3f, enumfacing$axis, f, flag);
/*     */       } 
/*     */       
/* 104 */       return blockpartrotation;
/*     */     }
/*     */ 
/*     */     
/*     */     private float parseAngle(JsonObject object) {
/* 109 */       float f = JsonUtils.getFloat(object, "angle");
/*     */       
/* 111 */       if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F)
/*     */       {
/* 113 */         throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
/*     */       }
/*     */ 
/*     */       
/* 117 */       return f;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private EnumFacing.Axis parseAxis(JsonObject object) {
/* 123 */       String s = JsonUtils.getString(object, "axis");
/* 124 */       EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.byName(s.toLowerCase(Locale.ROOT));
/*     */       
/* 126 */       if (enumfacing$axis == null)
/*     */       {
/* 128 */         throw new JsonParseException("Invalid rotation axis: " + s);
/*     */       }
/*     */ 
/*     */       
/* 132 */       return enumfacing$axis;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFacesCheck(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 138 */       Map<EnumFacing, BlockPartFace> map = parseFaces(deserializationContext, object);
/*     */       
/* 140 */       if (map.isEmpty())
/*     */       {
/* 142 */         throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
/*     */       }
/*     */ 
/*     */       
/* 146 */       return map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFaces(JsonDeserializationContext deserializationContext, JsonObject object) {
/* 152 */       Map<EnumFacing, BlockPartFace> map = Maps.newEnumMap(EnumFacing.class);
/* 153 */       JsonObject jsonobject = JsonUtils.getJsonObject(object, "faces");
/*     */       
/* 155 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */         
/* 157 */         EnumFacing enumfacing = parseEnumFacing(entry.getKey());
/* 158 */         map.put(enumfacing, (BlockPartFace)deserializationContext.deserialize(entry.getValue(), BlockPartFace.class));
/*     */       } 
/*     */       
/* 161 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     private EnumFacing parseEnumFacing(String name) {
/* 166 */       EnumFacing enumfacing = EnumFacing.byName(name);
/*     */       
/* 168 */       if (enumfacing == null)
/*     */       {
/* 170 */         throw new JsonParseException("Unknown facing: " + name);
/*     */       }
/*     */ 
/*     */       
/* 174 */       return enumfacing;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionTo(JsonObject object) {
/* 180 */       Vector3f vector3f = parsePosition(object, "to");
/*     */       
/* 182 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
/*     */       {
/* 184 */         return vector3f;
/*     */       }
/*     */ 
/*     */       
/* 188 */       throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionFrom(JsonObject object) {
/* 194 */       Vector3f vector3f = parsePosition(object, "from");
/*     */       
/* 196 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
/*     */       {
/* 198 */         return vector3f;
/*     */       }
/*     */ 
/*     */       
/* 202 */       throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePosition(JsonObject object, String memberName) {
/* 208 */       JsonArray jsonarray = JsonUtils.getJsonArray(object, memberName);
/*     */       
/* 210 */       if (jsonarray.size() != 3)
/*     */       {
/* 212 */         throw new JsonParseException("Expected 3 " + memberName + " values, found: " + jsonarray.size());
/*     */       }
/*     */ 
/*     */       
/* 216 */       float[] afloat = new float[3];
/*     */       
/* 218 */       for (int i = 0; i < afloat.length; i++)
/*     */       {
/* 220 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), String.valueOf(memberName) + "[" + i + "]");
/*     */       }
/*     */       
/* 223 */       return new Vector3f(afloat[0], afloat[1], afloat[2]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BlockPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
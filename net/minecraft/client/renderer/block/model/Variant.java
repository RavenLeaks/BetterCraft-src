/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class Variant
/*     */ {
/*     */   private final ResourceLocation modelLocation;
/*     */   private final ModelRotation rotation;
/*     */   private final boolean uvLock;
/*     */   private final int weight;
/*     */   
/*     */   public Variant(ResourceLocation modelLocationIn, ModelRotation rotationIn, boolean uvLockIn, int weightIn) {
/*  21 */     this.modelLocation = modelLocationIn;
/*  22 */     this.rotation = rotationIn;
/*  23 */     this.uvLock = uvLockIn;
/*  24 */     this.weight = weightIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getModelLocation() {
/*  29 */     return this.modelLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRotation getRotation() {
/*  34 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUvLock() {
/*  39 */     return this.uvLock;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeight() {
/*  44 */     return this.weight;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  49 */     return "Variant{modelLocation=" + this.modelLocation + ", rotation=" + this.rotation + ", uvLock=" + this.uvLock + ", weight=" + this.weight + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  54 */     if (this == p_equals_1_)
/*     */     {
/*  56 */       return true;
/*     */     }
/*  58 */     if (!(p_equals_1_ instanceof Variant))
/*     */     {
/*  60 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  64 */     Variant variant = (Variant)p_equals_1_;
/*  65 */     return (this.modelLocation.equals(variant.modelLocation) && this.rotation == variant.rotation && this.uvLock == variant.uvLock && this.weight == variant.weight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     int i = this.modelLocation.hashCode();
/*  72 */     i = 31 * i + this.rotation.hashCode();
/*  73 */     i = 31 * i + Boolean.valueOf(this.uvLock).hashCode();
/*  74 */     i = 31 * i + this.weight;
/*  75 */     return i;
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<Variant>
/*     */   {
/*     */     public Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  82 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  83 */       String s = getStringModel(jsonobject);
/*  84 */       ModelRotation modelrotation = parseModelRotation(jsonobject);
/*  85 */       boolean flag = parseUvLock(jsonobject);
/*  86 */       int i = parseWeight(jsonobject);
/*  87 */       return new Variant(getResourceLocationBlock(s), modelrotation, flag, i);
/*     */     }
/*     */ 
/*     */     
/*     */     private ResourceLocation getResourceLocationBlock(String p_188041_1_) {
/*  92 */       ResourceLocation resourcelocation = new ResourceLocation(p_188041_1_);
/*  93 */       resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
/*  94 */       return resourcelocation;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean parseUvLock(JsonObject json) {
/*  99 */       return JsonUtils.getBoolean(json, "uvlock", false);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ModelRotation parseModelRotation(JsonObject json) {
/* 104 */       int i = JsonUtils.getInt(json, "x", 0);
/* 105 */       int j = JsonUtils.getInt(json, "y", 0);
/* 106 */       ModelRotation modelrotation = ModelRotation.getModelRotation(i, j);
/*     */       
/* 108 */       if (modelrotation == null)
/*     */       {
/* 110 */         throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
/*     */       }
/*     */ 
/*     */       
/* 114 */       return modelrotation;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getStringModel(JsonObject json) {
/* 120 */       return JsonUtils.getString(json, "model");
/*     */     }
/*     */ 
/*     */     
/*     */     protected int parseWeight(JsonObject json) {
/* 125 */       int i = JsonUtils.getInt(json, "weight", 1);
/*     */       
/* 127 */       if (i < 1)
/*     */       {
/* 129 */         throw new JsonParseException("Invalid weight " + i + " found, expected integer >= 1");
/*     */       }
/*     */ 
/*     */       
/* 133 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\Variant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
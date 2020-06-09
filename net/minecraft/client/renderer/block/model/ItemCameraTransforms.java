/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.util.vector.Quaternion;
/*     */ 
/*     */ public class ItemCameraTransforms
/*     */ {
/*  15 */   public static final ItemCameraTransforms DEFAULT = new ItemCameraTransforms();
/*     */   
/*     */   public static float offsetTranslateX;
/*     */   public static float offsetTranslateY;
/*     */   public static float offsetTranslateZ;
/*     */   public static float offsetRotationX;
/*     */   public static float offsetRotationY;
/*     */   public static float offsetRotationZ;
/*     */   public static float offsetScaleX;
/*     */   public static float offsetScaleY;
/*     */   public static float offsetScaleZ;
/*     */   public final ItemTransformVec3f thirdperson_left;
/*     */   public final ItemTransformVec3f thirdperson_right;
/*     */   public final ItemTransformVec3f firstperson_left;
/*     */   public final ItemTransformVec3f firstperson_right;
/*     */   public final ItemTransformVec3f head;
/*     */   public final ItemTransformVec3f gui;
/*     */   public final ItemTransformVec3f ground;
/*     */   public final ItemTransformVec3f fixed;
/*     */   
/*     */   private ItemCameraTransforms() {
/*  36 */     this(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms(ItemCameraTransforms transforms) {
/*  41 */     this.thirdperson_left = transforms.thirdperson_left;
/*  42 */     this.thirdperson_right = transforms.thirdperson_right;
/*  43 */     this.firstperson_left = transforms.firstperson_left;
/*  44 */     this.firstperson_right = transforms.firstperson_right;
/*  45 */     this.head = transforms.head;
/*  46 */     this.gui = transforms.gui;
/*  47 */     this.ground = transforms.ground;
/*  48 */     this.fixed = transforms.fixed;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms(ItemTransformVec3f thirdperson_leftIn, ItemTransformVec3f thirdperson_rightIn, ItemTransformVec3f firstperson_leftIn, ItemTransformVec3f firstperson_rightIn, ItemTransformVec3f headIn, ItemTransformVec3f guiIn, ItemTransformVec3f groundIn, ItemTransformVec3f fixedIn) {
/*  53 */     this.thirdperson_left = thirdperson_leftIn;
/*  54 */     this.thirdperson_right = thirdperson_rightIn;
/*  55 */     this.firstperson_left = firstperson_leftIn;
/*  56 */     this.firstperson_right = firstperson_rightIn;
/*  57 */     this.head = headIn;
/*  58 */     this.gui = guiIn;
/*  59 */     this.ground = groundIn;
/*  60 */     this.fixed = fixedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyTransform(TransformType type) {
/*  65 */     applyTransformSide(getTransform(type), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyTransformSide(ItemTransformVec3f vec, boolean leftHand) {
/*  70 */     if (vec != ItemTransformVec3f.DEFAULT) {
/*     */       
/*  72 */       int i = leftHand ? -1 : 1;
/*  73 */       GlStateManager.translate(i * (offsetTranslateX + vec.translation.x), offsetTranslateY + vec.translation.y, offsetTranslateZ + vec.translation.z);
/*  74 */       float f = offsetRotationX + vec.rotation.x;
/*  75 */       float f1 = offsetRotationY + vec.rotation.y;
/*  76 */       float f2 = offsetRotationZ + vec.rotation.z;
/*     */       
/*  78 */       if (leftHand) {
/*     */         
/*  80 */         f1 = -f1;
/*  81 */         f2 = -f2;
/*     */       } 
/*     */       
/*  84 */       GlStateManager.rotate(makeQuaternion(f, f1, f2));
/*  85 */       GlStateManager.scale(offsetScaleX + vec.scale.x, offsetScaleY + vec.scale.y, offsetScaleZ + vec.scale.z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Quaternion makeQuaternion(float p_188035_0_, float p_188035_1_, float p_188035_2_) {
/*  91 */     float f = p_188035_0_ * 0.017453292F;
/*  92 */     float f1 = p_188035_1_ * 0.017453292F;
/*  93 */     float f2 = p_188035_2_ * 0.017453292F;
/*  94 */     float f3 = MathHelper.sin(0.5F * f);
/*  95 */     float f4 = MathHelper.cos(0.5F * f);
/*  96 */     float f5 = MathHelper.sin(0.5F * f1);
/*  97 */     float f6 = MathHelper.cos(0.5F * f1);
/*  98 */     float f7 = MathHelper.sin(0.5F * f2);
/*  99 */     float f8 = MathHelper.cos(0.5F * f2);
/* 100 */     return new Quaternion(f3 * f6 * f8 + f4 * f5 * f7, f4 * f5 * f8 - f3 * f6 * f7, f3 * f5 * f8 + f4 * f6 * f7, f4 * f6 * f8 - f3 * f5 * f7);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemTransformVec3f getTransform(TransformType type) {
/* 105 */     switch (type) {
/*     */       
/*     */       case THIRD_PERSON_LEFT_HAND:
/* 108 */         return this.thirdperson_left;
/*     */       
/*     */       case THIRD_PERSON_RIGHT_HAND:
/* 111 */         return this.thirdperson_right;
/*     */       
/*     */       case null:
/* 114 */         return this.firstperson_left;
/*     */       
/*     */       case FIRST_PERSON_RIGHT_HAND:
/* 117 */         return this.firstperson_right;
/*     */       
/*     */       case HEAD:
/* 120 */         return this.head;
/*     */       
/*     */       case GUI:
/* 123 */         return this.gui;
/*     */       
/*     */       case GROUND:
/* 126 */         return this.ground;
/*     */       
/*     */       case FIXED:
/* 129 */         return this.fixed;
/*     */     } 
/*     */     
/* 132 */     return ItemTransformVec3f.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomTransform(TransformType type) {
/* 138 */     return (getTransform(type) != ItemTransformVec3f.DEFAULT);
/*     */   }
/*     */   
/*     */   static class Deserializer
/*     */     implements JsonDeserializer<ItemCameraTransforms>
/*     */   {
/*     */     public ItemCameraTransforms deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 145 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 146 */       ItemTransformVec3f itemtransformvec3f = getTransform(p_deserialize_3_, jsonobject, "thirdperson_righthand");
/* 147 */       ItemTransformVec3f itemtransformvec3f1 = getTransform(p_deserialize_3_, jsonobject, "thirdperson_lefthand");
/*     */       
/* 149 */       if (itemtransformvec3f1 == ItemTransformVec3f.DEFAULT)
/*     */       {
/* 151 */         itemtransformvec3f1 = itemtransformvec3f;
/*     */       }
/*     */       
/* 154 */       ItemTransformVec3f itemtransformvec3f2 = getTransform(p_deserialize_3_, jsonobject, "firstperson_righthand");
/* 155 */       ItemTransformVec3f itemtransformvec3f3 = getTransform(p_deserialize_3_, jsonobject, "firstperson_lefthand");
/*     */       
/* 157 */       if (itemtransformvec3f3 == ItemTransformVec3f.DEFAULT)
/*     */       {
/* 159 */         itemtransformvec3f3 = itemtransformvec3f2;
/*     */       }
/*     */       
/* 162 */       ItemTransformVec3f itemtransformvec3f4 = getTransform(p_deserialize_3_, jsonobject, "head");
/* 163 */       ItemTransformVec3f itemtransformvec3f5 = getTransform(p_deserialize_3_, jsonobject, "gui");
/* 164 */       ItemTransformVec3f itemtransformvec3f6 = getTransform(p_deserialize_3_, jsonobject, "ground");
/* 165 */       ItemTransformVec3f itemtransformvec3f7 = getTransform(p_deserialize_3_, jsonobject, "fixed");
/* 166 */       return new ItemCameraTransforms(itemtransformvec3f1, itemtransformvec3f, itemtransformvec3f3, itemtransformvec3f2, itemtransformvec3f4, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7);
/*     */     }
/*     */ 
/*     */     
/*     */     private ItemTransformVec3f getTransform(JsonDeserializationContext p_181683_1_, JsonObject p_181683_2_, String p_181683_3_) {
/* 171 */       return p_181683_2_.has(p_181683_3_) ? (ItemTransformVec3f)p_181683_1_.deserialize(p_181683_2_.get(p_181683_3_), ItemTransformVec3f.class) : ItemTransformVec3f.DEFAULT;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TransformType
/*     */   {
/* 177 */     NONE,
/* 178 */     THIRD_PERSON_LEFT_HAND,
/* 179 */     THIRD_PERSON_RIGHT_HAND,
/* 180 */     FIRST_PERSON_LEFT_HAND,
/* 181 */     FIRST_PERSON_RIGHT_HAND,
/* 182 */     HEAD,
/* 183 */     GUI,
/* 184 */     GROUND,
/* 185 */     FIXED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ItemCameraTransforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
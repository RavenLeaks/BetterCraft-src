/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public abstract class ModelBase
/*    */ {
/*    */   public float swingProgress;
/*    */   public boolean isRiding;
/*    */   public boolean isChild = true;
/* 16 */   public List<ModelRenderer> boxList = Lists.newArrayList();
/* 17 */   private final Map<String, TextureOffset> modelTextureMap = Maps.newHashMap();
/* 18 */   public int textureWidth = 64;
/* 19 */   public int textureHeight = 32;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelRenderer getRandomModelBox(Random rand) {
/* 47 */     return this.boxList.get(rand.nextInt(this.boxList.size()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setTextureOffset(String partName, int x, int y) {
/* 52 */     this.modelTextureMap.put(partName, new TextureOffset(x, y));
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureOffset getTextureOffset(String partName) {
/* 57 */     return this.modelTextureMap.get(partName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
/* 66 */     dest.rotateAngleX = source.rotateAngleX;
/* 67 */     dest.rotateAngleY = source.rotateAngleY;
/* 68 */     dest.rotateAngleZ = source.rotateAngleZ;
/* 69 */     dest.rotationPointX = source.rotationPointX;
/* 70 */     dest.rotationPointY = source.rotationPointY;
/* 71 */     dest.rotationPointZ = source.rotationPointZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setModelAttributes(ModelBase model) {
/* 76 */     this.swingProgress = model.swingProgress;
/* 77 */     this.isRiding = model.isRiding;
/* 78 */     this.isChild = model.isChild;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
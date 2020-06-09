/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelWolf;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWolf extends RenderLiving<EntityWolf> {
/* 11 */   private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
/* 12 */   private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
/* 13 */   private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
/*    */ 
/*    */   
/*    */   public RenderWolf(RenderManager p_i47187_1_) {
/* 17 */     super(p_i47187_1_, (ModelBase)new ModelWolf(), 0.5F);
/* 18 */     addLayer(new LayerWolfCollar(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float handleRotationFloat(EntityWolf livingBase, float partialTicks) {
/* 26 */     return livingBase.getTailRotation();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 34 */     if (entity.isWolfWet()) {
/*    */       
/* 36 */       float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
/* 37 */       GlStateManager.color(f, f, f);
/*    */     } 
/*    */     
/* 40 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWolf entity) {
/* 48 */     if (entity.isTamed())
/*    */     {
/* 50 */       return TAMED_WOLF_TEXTURES;
/*    */     }
/*    */ 
/*    */     
/* 54 */     return entity.isAngry() ? ANRGY_WOLF_TEXTURES : WOLF_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
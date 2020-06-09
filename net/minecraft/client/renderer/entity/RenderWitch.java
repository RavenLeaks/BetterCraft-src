/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelWitch;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWitch extends RenderLiving<EntityWitch> {
/* 11 */   private static final ResourceLocation WITCH_TEXTURES = new ResourceLocation("textures/entity/witch.png");
/*    */ 
/*    */   
/*    */   public RenderWitch(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelWitch(0.0F), 0.5F);
/* 16 */     addLayer(new LayerHeldItemWitch(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelWitch getMainModel() {
/* 21 */     return (ModelWitch)super.getMainModel();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityWitch entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 29 */     ((ModelWitch)this.mainModel).holdingItem = !entity.getHeldItemMainhand().func_190926_b();
/* 30 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWitch entity) {
/* 38 */     return WITCH_TEXTURES;
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 43 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityWitch entitylivingbaseIn, float partialTickTime) {
/* 51 */     float f = 0.9375F;
/* 52 */     GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
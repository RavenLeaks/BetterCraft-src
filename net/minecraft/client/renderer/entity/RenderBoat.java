/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.IMultipassModel;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderBoat extends Render<EntityBoat> {
/* 13 */   private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[] { new ResourceLocation("textures/entity/boat/boat_oak.png"), new ResourceLocation("textures/entity/boat/boat_spruce.png"), new ResourceLocation("textures/entity/boat/boat_birch.png"), new ResourceLocation("textures/entity/boat/boat_jungle.png"), new ResourceLocation("textures/entity/boat/boat_acacia.png"), new ResourceLocation("textures/entity/boat/boat_darkoak.png") };
/*    */ 
/*    */   
/* 16 */   protected ModelBase modelBoat = (ModelBase)new ModelBoat();
/*    */ 
/*    */   
/*    */   public RenderBoat(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 29 */     GlStateManager.pushMatrix();
/* 30 */     setupTranslation(x, y, z);
/* 31 */     setupRotation(entity, entityYaw, partialTicks);
/* 32 */     bindEntityTexture(entity);
/*    */     
/* 34 */     if (this.renderOutlines) {
/*    */       
/* 36 */       GlStateManager.enableColorMaterial();
/* 37 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 40 */     this.modelBoat.render((Entity)entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/*    */     
/* 42 */     if (this.renderOutlines) {
/*    */       
/* 44 */       GlStateManager.disableOutlineMode();
/* 45 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 48 */     GlStateManager.popMatrix();
/* 49 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupRotation(EntityBoat p_188311_1_, float p_188311_2_, float p_188311_3_) {
/* 54 */     GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
/* 55 */     float f = p_188311_1_.getTimeSinceHit() - p_188311_3_;
/* 56 */     float f1 = p_188311_1_.getDamageTaken() - p_188311_3_;
/*    */     
/* 58 */     if (f1 < 0.0F)
/*    */     {
/* 60 */       f1 = 0.0F;
/*    */     }
/*    */     
/* 63 */     if (f > 0.0F)
/*    */     {
/* 65 */       GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * p_188311_1_.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 68 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_) {
/* 73 */     GlStateManager.translate((float)p_188309_1_, (float)p_188309_3_ + 0.375F, (float)p_188309_5_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBoat entity) {
/* 81 */     return BOAT_TEXTURES[entity.getBoatType().ordinal()];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMultipass() {
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderMultipass(EntityBoat p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_) {
/* 91 */     GlStateManager.pushMatrix();
/* 92 */     setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
/* 93 */     setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);
/* 94 */     bindEntityTexture(p_188300_1_);
/* 95 */     ((IMultipassModel)this.modelBoat).renderMultipass((Entity)p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 96 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelEvokerFangs;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEvokerFangs extends Render<EntityEvokerFangs> {
/* 10 */   private static final ResourceLocation field_191329_a = new ResourceLocation("textures/entity/illager/fangs.png");
/* 11 */   private final ModelEvokerFangs field_191330_f = new ModelEvokerFangs();
/*    */ 
/*    */   
/*    */   public RenderEvokerFangs(RenderManager p_i47208_1_) {
/* 15 */     super(p_i47208_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityEvokerFangs entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 23 */     float f = entity.func_190550_a(partialTicks);
/*    */     
/* 25 */     if (f != 0.0F) {
/*    */       
/* 27 */       float f1 = 2.0F;
/*    */       
/* 29 */       if (f > 0.9F)
/*    */       {
/* 31 */         f1 = (float)(f1 * (1.0D - f) / 0.10000000149011612D);
/*    */       }
/*    */       
/* 34 */       GlStateManager.pushMatrix();
/* 35 */       GlStateManager.disableCull();
/* 36 */       GlStateManager.enableAlpha();
/* 37 */       bindEntityTexture(entity);
/* 38 */       GlStateManager.translate((float)x, (float)y, (float)z);
/* 39 */       GlStateManager.rotate(90.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 40 */       GlStateManager.scale(-f1, -f1, f1);
/* 41 */       float f2 = 0.03125F;
/* 42 */       GlStateManager.translate(0.0F, -0.626F, 0.0F);
/* 43 */       this.field_191330_f.render((Entity)entity, f, 0.0F, 0.0F, entity.rotationYaw, entity.rotationPitch, 0.03125F);
/* 44 */       GlStateManager.popMatrix();
/* 45 */       GlStateManager.enableCull();
/* 46 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEvokerFangs entity) {
/* 55 */     return field_191329_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderEvokerFangs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
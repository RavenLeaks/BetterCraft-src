/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelOcelot;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderOcelot extends RenderLiving<EntityOcelot> {
/* 10 */   private static final ResourceLocation BLACK_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/black.png");
/* 11 */   private static final ResourceLocation OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/ocelot.png");
/* 12 */   private static final ResourceLocation RED_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/red.png");
/* 13 */   private static final ResourceLocation SIAMESE_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/siamese.png");
/*    */ 
/*    */   
/*    */   public RenderOcelot(RenderManager p_i47199_1_) {
/* 17 */     super(p_i47199_1_, (ModelBase)new ModelOcelot(), 0.4F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityOcelot entity) {
/* 25 */     switch (entity.getTameSkin()) {
/*    */ 
/*    */       
/*    */       default:
/* 29 */         return OCELOT_TEXTURES;
/*    */       
/*    */       case 1:
/* 32 */         return BLACK_OCELOT_TEXTURES;
/*    */       
/*    */       case 2:
/* 35 */         return RED_OCELOT_TEXTURES;
/*    */       case 3:
/*    */         break;
/* 38 */     }  return SIAMESE_OCELOT_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityOcelot entitylivingbaseIn, float partialTickTime) {
/* 47 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */     
/* 49 */     if (entitylivingbaseIn.isTamed())
/*    */     {
/* 51 */       GlStateManager.scale(0.8F, 0.8F, 0.8F);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderIronGolem extends RenderLiving<EntityIronGolem> {
/* 11 */   private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("textures/entity/iron_golem.png");
/*    */ 
/*    */   
/*    */   public RenderIronGolem(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelIronGolem(), 0.5F);
/* 16 */     addLayer(new LayerIronGolemFlower(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityIronGolem entity) {
/* 24 */     return IRON_GOLEM_TEXTURES;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityIronGolem entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 29 */     super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/*    */     
/* 31 */     if (entityLiving.limbSwingAmount >= 0.01D) {
/*    */       
/* 33 */       float f = 13.0F;
/* 34 */       float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
/* 35 */       float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
/* 36 */       GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
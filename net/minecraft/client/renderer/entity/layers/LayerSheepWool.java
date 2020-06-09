/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelSheep1;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import optifine.CustomColors;
/*    */ 
/*    */ public class LayerSheepWool implements LayerRenderer<EntitySheep> {
/* 14 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
/*    */   private final RenderSheep sheepRenderer;
/* 16 */   public ModelSheep1 sheepModel = new ModelSheep1();
/*    */ 
/*    */   
/*    */   public LayerSheepWool(RenderSheep sheepRendererIn) {
/* 20 */     this.sheepRenderer = sheepRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntitySheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 25 */     if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
/*    */       
/* 27 */       this.sheepRenderer.bindTexture(TEXTURE);
/*    */       
/* 29 */       if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag())) {
/*    */         
/* 31 */         int i1 = 25;
/* 32 */         int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
/* 33 */         int j = (EnumDyeColor.values()).length;
/* 34 */         int k = i % j;
/* 35 */         int l = (i + 1) % j;
/* 36 */         float f = ((entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
/* 37 */         float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
/* 38 */         float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
/*    */         
/* 40 */         if (Config.isCustomColors()) {
/*    */           
/* 42 */           afloat1 = CustomColors.getSheepColors(EnumDyeColor.byMetadata(k), afloat1);
/* 43 */           afloat2 = CustomColors.getSheepColors(EnumDyeColor.byMetadata(l), afloat2);
/*    */         } 
/*    */         
/* 46 */         GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
/*    */       }
/*    */       else {
/*    */         
/* 50 */         float[] afloat = EntitySheep.getDyeRgb(entitylivingbaseIn.getFleeceColor());
/*    */         
/* 52 */         if (Config.isCustomColors())
/*    */         {
/* 54 */           afloat = CustomColors.getSheepColors(entitylivingbaseIn.getFleeceColor(), afloat);
/*    */         }
/*    */         
/* 57 */         GlStateManager.color(afloat[0], afloat[1], afloat[2]);
/*    */       } 
/*    */       
/* 60 */       this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
/* 61 */       this.sheepModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
/* 62 */       this.sheepModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 68 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerSheepWool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
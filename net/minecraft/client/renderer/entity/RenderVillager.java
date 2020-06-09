/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelVillager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderVillager extends RenderLiving<EntityVillager> {
/* 11 */   private static final ResourceLocation VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/villager.png");
/* 12 */   private static final ResourceLocation FARMER_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/farmer.png");
/* 13 */   private static final ResourceLocation LIBRARIAN_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/librarian.png");
/* 14 */   private static final ResourceLocation PRIEST_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/priest.png");
/* 15 */   private static final ResourceLocation SMITH_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/smith.png");
/* 16 */   private static final ResourceLocation BUTCHER_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/villager/butcher.png");
/*    */ 
/*    */   
/*    */   public RenderVillager(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn, (ModelBase)new ModelVillager(0.0F), 0.5F);
/* 21 */     addLayer(new LayerCustomHead((getMainModel()).villagerHead));
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelVillager getMainModel() {
/* 26 */     return (ModelVillager)super.getMainModel();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityVillager entity) {
/* 34 */     switch (entity.getProfession()) {
/*    */       
/*    */       case 0:
/* 37 */         return FARMER_VILLAGER_TEXTURES;
/*    */       
/*    */       case 1:
/* 40 */         return LIBRARIAN_VILLAGER_TEXTURES;
/*    */       
/*    */       case 2:
/* 43 */         return PRIEST_VILLAGER_TEXTURES;
/*    */       
/*    */       case 3:
/* 46 */         return SMITH_VILLAGER_TEXTURES;
/*    */       
/*    */       case 4:
/* 49 */         return BUTCHER_VILLAGER_TEXTURES;
/*    */     } 
/*    */ 
/*    */     
/* 53 */     return VILLAGER_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityVillager entitylivingbaseIn, float partialTickTime) {
/* 62 */     float f = 0.9375F;
/*    */     
/* 64 */     if (entitylivingbaseIn.getGrowingAge() < 0) {
/*    */       
/* 66 */       f = (float)(f * 0.5D);
/* 67 */       this.shadowSize = 0.25F;
/*    */     }
/*    */     else {
/*    */       
/* 71 */       this.shadowSize = 0.5F;
/*    */     } 
/*    */     
/* 74 */     GlStateManager.scale(f, f, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
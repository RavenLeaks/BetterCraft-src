/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.AbstractSkeleton;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSkeleton extends RenderBiped<AbstractSkeleton> {
/* 12 */   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*    */ 
/*    */   
/*    */   public RenderSkeleton(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn, (ModelBiped)new ModelSkeleton(), 0.5F);
/* 17 */     addLayer(new LayerHeldItem(this));
/* 18 */     addLayer(new LayerBipedArmor(this)
/*    */         {
/*    */           protected void initArmor()
/*    */           {
/* 22 */             this.modelLeggings = (ModelBase)new ModelSkeleton(0.5F, true);
/* 23 */             this.modelArmor = (ModelBase)new ModelSkeleton(1.0F, true);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 30 */     GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(AbstractSkeleton entity) {
/* 38 */     return SKELETON_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
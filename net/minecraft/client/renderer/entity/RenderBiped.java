/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerElytra;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBiped<T extends EntityLiving> extends RenderLiving<T> {
/* 13 */   private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
/*    */ 
/*    */   
/*    */   public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
/* 17 */     super(renderManagerIn, (ModelBase)modelBipedIn, shadowSize);
/* 18 */     addLayer(new LayerCustomHead(modelBipedIn.bipedHead));
/* 19 */     addLayer(new LayerElytra(this));
/* 20 */     addLayer(new LayerHeldItem(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 28 */     return DEFAULT_RES_LOC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 33 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
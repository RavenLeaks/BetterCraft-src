/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderItem;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSnowball<T extends Entity>
/*    */   extends Render<T>
/*    */ {
/*    */   protected final Item item;
/*    */   private final RenderItem itemRenderer;
/*    */   
/*    */   public RenderSnowball(RenderManager renderManagerIn, Item itemIn, RenderItem itemRendererIn) {
/* 19 */     super(renderManagerIn);
/* 20 */     this.item = itemIn;
/* 21 */     this.itemRenderer = itemRendererIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 29 */     GlStateManager.pushMatrix();
/* 30 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 31 */     GlStateManager.enableRescaleNormal();
/* 32 */     GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 33 */     GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : true) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 34 */     GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 35 */     bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*    */     
/* 37 */     if (this.renderOutlines) {
/*    */       
/* 39 */       GlStateManager.enableColorMaterial();
/* 40 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*    */     } 
/*    */     
/* 43 */     this.itemRenderer.renderItem(getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
/*    */     
/* 45 */     if (this.renderOutlines) {
/*    */       
/* 47 */       GlStateManager.disableOutlineMode();
/* 48 */       GlStateManager.disableColorMaterial();
/*    */     } 
/*    */     
/* 51 */     GlStateManager.disableRescaleNormal();
/* 52 */     GlStateManager.popMatrix();
/* 53 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getStackToRender(T entityIn) {
/* 58 */     return new ItemStack(this.item);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity) {
/* 66 */     return TextureMap.LOCATION_BLOCKS_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
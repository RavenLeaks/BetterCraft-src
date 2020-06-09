/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityGiantZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderGiantZombie extends RenderLiving<EntityGiantZombie> {
/* 12 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*    */ 
/*    */   
/*    */   private final float scale;
/*    */ 
/*    */   
/*    */   public RenderGiantZombie(RenderManager p_i47206_1_, float p_i47206_2_) {
/* 19 */     super(p_i47206_1_, (ModelBase)new ModelZombie(), 0.5F * p_i47206_2_);
/* 20 */     this.scale = p_i47206_2_;
/* 21 */     addLayer(new LayerHeldItem(this));
/* 22 */     addLayer(new LayerBipedArmor(this)
/*    */         {
/*    */           protected void initArmor()
/*    */           {
/* 26 */             this.modelLeggings = (ModelBase)new ModelZombie(0.5F, true);
/* 27 */             this.modelArmor = (ModelBase)new ModelZombie(1.0F, true);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 34 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityGiantZombie entitylivingbaseIn, float partialTickTime) {
/* 42 */     GlStateManager.scale(this.scale, this.scale, this.scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityGiantZombie entity) {
/* 50 */     return ZOMBIE_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderGiantZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
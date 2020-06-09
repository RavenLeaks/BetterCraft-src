/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderZombie extends RenderBiped<EntityZombie> {
/* 10 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*    */ 
/*    */   
/*    */   public RenderZombie(RenderManager renderManagerIn) {
/* 14 */     super(renderManagerIn, (ModelBiped)new ModelZombie(), 0.5F);
/* 15 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
/*    */       {
/*    */         protected void initArmor()
/*    */         {
/* 19 */           this.modelLeggings = (ModelBase)new ModelZombie(0.5F, true);
/* 20 */           this.modelArmor = (ModelBase)new ModelZombie(1.0F, true);
/*    */         }
/*    */       };
/* 23 */     addLayer(layerbipedarmor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityZombie entity) {
/* 31 */     return ZOMBIE_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderman;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEnderman extends RenderLiving<EntityEnderman> {
/* 13 */   private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("textures/entity/enderman/enderman.png");
/* 14 */   private final Random rnd = new Random();
/*    */ 
/*    */   
/*    */   public RenderEnderman(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn, (ModelBase)new ModelEnderman(0.0F), 0.5F);
/* 19 */     addLayer(new LayerEndermanEyes(this));
/* 20 */     addLayer(new LayerHeldBlock(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelEnderman getMainModel() {
/* 25 */     return (ModelEnderman)super.getMainModel();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 33 */     IBlockState iblockstate = entity.getHeldBlockState();
/* 34 */     ModelEnderman modelenderman = getMainModel();
/* 35 */     modelenderman.isCarrying = (iblockstate != null);
/* 36 */     modelenderman.isAttacking = entity.isScreaming();
/*    */     
/* 38 */     if (entity.isScreaming()) {
/*    */       
/* 40 */       double d0 = 0.02D;
/* 41 */       x += this.rnd.nextGaussian() * 0.02D;
/* 42 */       z += this.rnd.nextGaussian() * 0.02D;
/*    */     } 
/*    */     
/* 45 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEnderman entity) {
/* 53 */     return ENDERMAN_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderMooshroom extends RenderLiving<EntityMooshroom> {
/* 10 */   private static final ResourceLocation MOOSHROOM_TEXTURES = new ResourceLocation("textures/entity/cow/mooshroom.png");
/*    */ 
/*    */   
/*    */   public RenderMooshroom(RenderManager p_i47200_1_) {
/* 14 */     super(p_i47200_1_, (ModelBase)new ModelCow(), 0.7F);
/* 15 */     addLayer(new LayerMooshroomMushroom(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelCow getMainModel() {
/* 20 */     return (ModelCow)super.getMainModel();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMooshroom entity) {
/* 28 */     return MOOSHROOM_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderCow extends RenderLiving<EntityCow> {
/*  9 */   private static final ResourceLocation COW_TEXTURES = new ResourceLocation("textures/entity/cow/cow.png");
/*    */ 
/*    */   
/*    */   public RenderCow(RenderManager p_i47210_1_) {
/* 13 */     super(p_i47210_1_, (ModelBase)new ModelCow(), 0.7F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCow entity) {
/* 21 */     return COW_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
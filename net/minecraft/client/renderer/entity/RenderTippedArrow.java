/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderTippedArrow extends RenderArrow<EntityTippedArrow> {
/*  8 */   public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");
/*  9 */   public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");
/*    */ 
/*    */   
/*    */   public RenderTippedArrow(RenderManager manager) {
/* 13 */     super(manager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityTippedArrow entity) {
/* 21 */     return (entity.getColor() > 0) ? RES_TIPPED_ARROW : RES_ARROW;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderTippedArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
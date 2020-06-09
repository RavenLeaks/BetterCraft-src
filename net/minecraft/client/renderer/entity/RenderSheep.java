/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSheep extends RenderLiving<EntitySheep> {
/* 10 */   private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation("textures/entity/sheep/sheep.png");
/*    */ 
/*    */   
/*    */   public RenderSheep(RenderManager p_i47195_1_) {
/* 14 */     super(p_i47195_1_, (ModelBase)new ModelSheep2(), 0.7F);
/* 15 */     addLayer(new LayerSheepWool(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySheep entity) {
/* 23 */     return SHEARED_SHEEP_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class LayerBipedArmor
/*    */   extends LayerArmorBase<ModelBiped> {
/*    */   public LayerBipedArmor(RenderLivingBase<?> rendererIn) {
/* 11 */     super(rendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initArmor() {
/* 16 */     this.modelLeggings = new ModelBiped(0.5F);
/* 17 */     this.modelArmor = new ModelBiped(1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
/* 23 */     setModelVisible(p_188359_1_);
/*    */     
/* 25 */     switch (slotIn) {
/*    */       
/*    */       case HEAD:
/* 28 */         p_188359_1_.bipedHead.showModel = true;
/* 29 */         p_188359_1_.bipedHeadwear.showModel = true;
/*    */         break;
/*    */       
/*    */       case null:
/* 33 */         p_188359_1_.bipedBody.showModel = true;
/* 34 */         p_188359_1_.bipedRightArm.showModel = true;
/* 35 */         p_188359_1_.bipedLeftArm.showModel = true;
/*    */         break;
/*    */       
/*    */       case LEGS:
/* 39 */         p_188359_1_.bipedBody.showModel = true;
/* 40 */         p_188359_1_.bipedRightLeg.showModel = true;
/* 41 */         p_188359_1_.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */       
/*    */       case FEET:
/* 45 */         p_188359_1_.bipedRightLeg.showModel = true;
/* 46 */         p_188359_1_.bipedLeftLeg.showModel = true;
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void setModelVisible(ModelBiped model) {
/* 52 */     model.setInvisible(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerBipedArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
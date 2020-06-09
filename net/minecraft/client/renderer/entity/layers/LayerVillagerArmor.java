/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ 
/*    */ public class LayerVillagerArmor
/*    */   extends LayerBipedArmor {
/*    */   public LayerVillagerArmor(RenderLivingBase<?> rendererIn) {
/* 10 */     super(rendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initArmor() {
/* 15 */     this.modelLeggings = (ModelBiped)new ModelZombieVillager(0.5F, 0.0F, true);
/* 16 */     this.modelArmor = (ModelBiped)new ModelZombieVillager(1.0F, 0.0F, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerVillagerArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
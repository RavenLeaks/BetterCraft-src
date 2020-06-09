/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelLlama;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerLlamaDecor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityLlama;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderLlama extends RenderLiving<EntityLlama> {
/* 10 */   private static final ResourceLocation[] field_191350_a = new ResourceLocation[] { new ResourceLocation("textures/entity/llama/llama_creamy.png"), new ResourceLocation("textures/entity/llama/llama_white.png"), new ResourceLocation("textures/entity/llama/llama_brown.png"), new ResourceLocation("textures/entity/llama/llama_gray.png") };
/*    */ 
/*    */   
/*    */   public RenderLlama(RenderManager p_i47203_1_) {
/* 14 */     super(p_i47203_1_, (ModelBase)new ModelLlama(0.0F), 0.7F);
/* 15 */     addLayer(new LayerLlamaDecor(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityLlama entity) {
/* 23 */     return field_191350_a[entity.func_190719_dM()];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderLlama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
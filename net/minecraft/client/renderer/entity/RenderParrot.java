/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityParrot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderParrot extends RenderLiving<EntityParrot> {
/* 10 */   public static final ResourceLocation[] field_192862_a = new ResourceLocation[] { new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_green.png"), new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_grey.png") };
/*    */ 
/*    */   
/*    */   public RenderParrot(RenderManager p_i47375_1_) {
/* 14 */     super(p_i47375_1_, (ModelBase)new ModelParrot(), 0.3F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityParrot entity) {
/* 22 */     return field_192862_a[entity.func_191998_ds()];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float handleRotationFloat(EntityParrot livingBase, float partialTicks) {
/* 30 */     return func_192861_b(livingBase, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   private float func_192861_b(EntityParrot p_192861_1_, float p_192861_2_) {
/* 35 */     float f = p_192861_1_.field_192011_bE + (p_192861_1_.field_192008_bB - p_192861_1_.field_192011_bE) * p_192861_2_;
/* 36 */     float f1 = p_192861_1_.field_192010_bD + (p_192861_1_.field_192009_bC - p_192861_1_.field_192010_bD) * p_192861_2_;
/* 37 */     return (MathHelper.sin(f) + 1.0F) * f1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderParrot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHorse;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.AbstractHorse;
/*    */ import net.minecraft.entity.passive.EntityDonkey;
/*    */ import net.minecraft.entity.passive.EntityMule;
/*    */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*    */ import net.minecraft.entity.passive.EntityZombieHorse;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderAbstractHorse extends RenderLiving<AbstractHorse> {
/* 16 */   private static final Map<Class<?>, ResourceLocation> field_191359_a = Maps.newHashMap();
/*    */   
/*    */   private final float field_191360_j;
/*    */   
/*    */   public RenderAbstractHorse(RenderManager p_i47212_1_) {
/* 21 */     this(p_i47212_1_, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderAbstractHorse(RenderManager p_i47213_1_, float p_i47213_2_) {
/* 26 */     super(p_i47213_1_, (ModelBase)new ModelHorse(), 0.75F);
/* 27 */     this.field_191360_j = p_i47213_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(AbstractHorse entitylivingbaseIn, float partialTickTime) {
/* 35 */     GlStateManager.scale(this.field_191360_j, this.field_191360_j, this.field_191360_j);
/* 36 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(AbstractHorse entity) {
/* 44 */     return field_191359_a.get(entity.getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 49 */     field_191359_a.put(EntityDonkey.class, new ResourceLocation("textures/entity/horse/donkey.png"));
/* 50 */     field_191359_a.put(EntityMule.class, new ResourceLocation("textures/entity/horse/mule.png"));
/* 51 */     field_191359_a.put(EntityZombieHorse.class, new ResourceLocation("textures/entity/horse/horse_zombie.png"));
/* 52 */     field_191359_a.put(EntitySkeletonHorse.class, new ResourceLocation("textures/entity/horse/horse_skeleton.png"));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderAbstractHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelArmorStandArmor;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerElytra;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderArmorStand
/*    */   extends RenderLivingBase<EntityArmorStand> {
/* 19 */   public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");
/*    */ 
/*    */   
/*    */   public RenderArmorStand(RenderManager manager) {
/* 23 */     super(manager, (ModelBase)new ModelArmorStand(), 0.0F);
/* 24 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
/*    */       {
/*    */         protected void initArmor()
/*    */         {
/* 28 */           this.modelLeggings = (ModelBase)new ModelArmorStandArmor(0.5F);
/* 29 */           this.modelArmor = (ModelBase)new ModelArmorStandArmor(1.0F);
/*    */         }
/*    */       };
/* 32 */     addLayer(layerbipedarmor);
/* 33 */     addLayer(new LayerHeldItem(this));
/* 34 */     addLayer(new LayerElytra(this));
/* 35 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityArmorStand entity) {
/* 43 */     return TEXTURE_ARMOR_STAND;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelArmorStand getMainModel() {
/* 48 */     return (ModelArmorStand)super.getMainModel();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityArmorStand entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 53 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/* 54 */     float f = (float)(entityLiving.world.getTotalWorldTime() - entityLiving.punchCooldown) + partialTicks;
/*    */     
/* 56 */     if (f < 5.0F)
/*    */     {
/* 58 */       GlStateManager.rotate(MathHelper.sin(f / 1.5F * 3.1415927F) * 3.0F, 0.0F, 1.0F, 0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canRenderName(EntityArmorStand entity) {
/* 64 */     return entity.getAlwaysRenderNameTag();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityArmorStand entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 72 */     if (entity.hasMarker())
/*    */     {
/* 74 */       this.renderMarker = true;
/*    */     }
/*    */     
/* 77 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */     
/* 79 */     if (entity.hasMarker())
/*    */     {
/* 81 */       this.renderMarker = false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
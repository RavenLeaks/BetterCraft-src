/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.model.ModelShulker;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class RenderShulker extends RenderLiving<EntityShulker> {
/*  16 */   public static final ResourceLocation[] SHULKER_ENDERGOLEM_TEXTURE = new ResourceLocation[] { new ResourceLocation("textures/entity/shulker/shulker_white.png"), new ResourceLocation("textures/entity/shulker/shulker_orange.png"), new ResourceLocation("textures/entity/shulker/shulker_magenta.png"), new ResourceLocation("textures/entity/shulker/shulker_light_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_yellow.png"), new ResourceLocation("textures/entity/shulker/shulker_lime.png"), new ResourceLocation("textures/entity/shulker/shulker_pink.png"), new ResourceLocation("textures/entity/shulker/shulker_gray.png"), new ResourceLocation("textures/entity/shulker/shulker_silver.png"), new ResourceLocation("textures/entity/shulker/shulker_cyan.png"), new ResourceLocation("textures/entity/shulker/shulker_purple.png"), new ResourceLocation("textures/entity/shulker/shulker_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_brown.png"), new ResourceLocation("textures/entity/shulker/shulker_green.png"), new ResourceLocation("textures/entity/shulker/shulker_red.png"), new ResourceLocation("textures/entity/shulker/shulker_black.png") };
/*     */ 
/*     */   
/*     */   public RenderShulker(RenderManager p_i47194_1_) {
/*  20 */     super(p_i47194_1_, (ModelBase)new ModelShulker(), 0.0F);
/*  21 */     addLayer(new HeadLayer(null));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelShulker getMainModel() {
/*  26 */     return (ModelShulker)super.getMainModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityShulker entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  34 */     int i = entity.getClientTeleportInterp();
/*     */     
/*  36 */     if (i > 0 && entity.isAttachedToBlock()) {
/*     */       
/*  38 */       BlockPos blockpos = entity.getAttachmentPos();
/*  39 */       BlockPos blockpos1 = entity.getOldAttachPos();
/*  40 */       double d0 = (i - partialTicks) / 6.0D;
/*  41 */       d0 *= d0;
/*  42 */       double d1 = (blockpos.getX() - blockpos1.getX()) * d0;
/*  43 */       double d2 = (blockpos.getY() - blockpos1.getY()) * d0;
/*  44 */       double d3 = (blockpos.getZ() - blockpos1.getZ()) * d0;
/*  45 */       super.doRender(entity, x - d1, y - d2, z - d3, entityYaw, partialTicks);
/*     */     }
/*     */     else {
/*     */       
/*  49 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(EntityShulker livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  55 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (livingEntity.getClientTeleportInterp() > 0 && livingEntity.isAttachedToBlock()) {
/*     */       
/*  63 */       BlockPos blockpos = livingEntity.getOldAttachPos();
/*  64 */       BlockPos blockpos1 = livingEntity.getAttachmentPos();
/*  65 */       Vec3d vec3d = new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*  66 */       Vec3d vec3d1 = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       
/*  68 */       if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord)))
/*     */       {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityShulker entity) {
/*  83 */     return SHULKER_ENDERGOLEM_TEXTURE[entity.func_190769_dn().getMetadata()];
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(EntityShulker entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/*  88 */     super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/*     */     
/*  90 */     switch (entityLiving.getAttachmentFacing()) {
/*     */       default:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case EAST:
/*  97 */         GlStateManager.translate(0.5F, 0.5F, 0.0F);
/*  98 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*  99 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */       
/*     */       case WEST:
/* 103 */         GlStateManager.translate(-0.5F, 0.5F, 0.0F);
/* 104 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 105 */         GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */       
/*     */       case NORTH:
/* 109 */         GlStateManager.translate(0.0F, 0.5F, -0.5F);
/* 110 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*     */ 
/*     */       
/*     */       case SOUTH:
/* 114 */         GlStateManager.translate(0.0F, 0.5F, 0.5F);
/* 115 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 116 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       case UP:
/*     */         break;
/*     */     } 
/* 120 */     GlStateManager.translate(0.0F, 1.0F, 0.0F);
/* 121 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(EntityShulker entitylivingbaseIn, float partialTickTime) {
/* 130 */     float f = 0.999F;
/* 131 */     GlStateManager.scale(0.999F, 0.999F, 0.999F);
/*     */   }
/*     */ 
/*     */   
/*     */   class HeadLayer
/*     */     implements LayerRenderer<EntityShulker>
/*     */   {
/*     */     private HeadLayer() {}
/*     */ 
/*     */     
/*     */     public void doRenderLayer(EntityShulker entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 142 */       GlStateManager.pushMatrix();
/*     */       
/* 144 */       switch (entitylivingbaseIn.getAttachmentFacing()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case EAST:
/* 151 */           GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 152 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 153 */           GlStateManager.translate(1.0F, -1.0F, 0.0F);
/* 154 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 158 */           GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/* 159 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 160 */           GlStateManager.translate(-1.0F, -1.0F, 0.0F);
/* 161 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 165 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 166 */           GlStateManager.translate(0.0F, -1.0F, -1.0F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 170 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 171 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 172 */           GlStateManager.translate(0.0F, -1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case UP:
/* 176 */           GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 177 */           GlStateManager.translate(0.0F, -2.0F, 0.0F);
/*     */           break;
/*     */       } 
/* 180 */       ModelRenderer modelrenderer = (RenderShulker.this.getMainModel()).head;
/* 181 */       modelrenderer.rotateAngleY = netHeadYaw * 0.017453292F;
/* 182 */       modelrenderer.rotateAngleX = headPitch * 0.017453292F;
/* 183 */       RenderShulker.this.bindTexture(RenderShulker.SHULKER_ENDERGOLEM_TEXTURE[entitylivingbaseIn.func_190769_dn().getMetadata()]);
/* 184 */       modelrenderer.render(scale);
/* 185 */       GlStateManager.popMatrix();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldCombineTextures() {
/* 190 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderShulker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
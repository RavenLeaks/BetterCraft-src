/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMinecart;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
/*  17 */   private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("textures/entity/minecart.png");
/*     */ 
/*     */   
/*  20 */   protected ModelBase modelMinecart = (ModelBase)new ModelMinecart();
/*     */ 
/*     */   
/*     */   public RenderMinecart(RenderManager renderManagerIn) {
/*  24 */     super(renderManagerIn);
/*  25 */     this.shadowSize = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  33 */     GlStateManager.pushMatrix();
/*  34 */     bindEntityTexture(entity);
/*  35 */     long i = entity.getEntityId() * 493286711L;
/*  36 */     i = i * i * 4392167121L + i * 98761L;
/*  37 */     float f = (((float)(i >> 16L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  38 */     float f1 = (((float)(i >> 20L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  39 */     float f2 = (((float)(i >> 24L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  40 */     GlStateManager.translate(f, f1, f2);
/*  41 */     double d0 = ((EntityMinecart)entity).lastTickPosX + (((EntityMinecart)entity).posX - ((EntityMinecart)entity).lastTickPosX) * partialTicks;
/*  42 */     double d1 = ((EntityMinecart)entity).lastTickPosY + (((EntityMinecart)entity).posY - ((EntityMinecart)entity).lastTickPosY) * partialTicks;
/*  43 */     double d2 = ((EntityMinecart)entity).lastTickPosZ + (((EntityMinecart)entity).posZ - ((EntityMinecart)entity).lastTickPosZ) * partialTicks;
/*  44 */     double d3 = 0.30000001192092896D;
/*  45 */     Vec3d vec3d = entity.getPos(d0, d1, d2);
/*  46 */     float f3 = ((EntityMinecart)entity).prevRotationPitch + (((EntityMinecart)entity).rotationPitch - ((EntityMinecart)entity).prevRotationPitch) * partialTicks;
/*     */     
/*  48 */     if (vec3d != null) {
/*     */       
/*  50 */       Vec3d vec3d1 = entity.getPosOffset(d0, d1, d2, 0.30000001192092896D);
/*  51 */       Vec3d vec3d2 = entity.getPosOffset(d0, d1, d2, -0.30000001192092896D);
/*     */       
/*  53 */       if (vec3d1 == null)
/*     */       {
/*  55 */         vec3d1 = vec3d;
/*     */       }
/*     */       
/*  58 */       if (vec3d2 == null)
/*     */       {
/*  60 */         vec3d2 = vec3d;
/*     */       }
/*     */       
/*  63 */       x += vec3d.xCoord - d0;
/*  64 */       y += (vec3d1.yCoord + vec3d2.yCoord) / 2.0D - d1;
/*  65 */       z += vec3d.zCoord - d2;
/*  66 */       Vec3d vec3d3 = vec3d2.addVector(-vec3d1.xCoord, -vec3d1.yCoord, -vec3d1.zCoord);
/*     */       
/*  68 */       if (vec3d3.lengthVector() != 0.0D) {
/*     */         
/*  70 */         vec3d3 = vec3d3.normalize();
/*  71 */         entityYaw = (float)(Math.atan2(vec3d3.zCoord, vec3d3.xCoord) * 180.0D / Math.PI);
/*  72 */         f3 = (float)(Math.atan(vec3d3.yCoord) * 73.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
/*  77 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  78 */     GlStateManager.rotate(-f3, 0.0F, 0.0F, 1.0F);
/*  79 */     float f5 = entity.getRollingAmplitude() - partialTicks;
/*  80 */     float f6 = entity.getDamage() - partialTicks;
/*     */     
/*  82 */     if (f6 < 0.0F)
/*     */     {
/*  84 */       f6 = 0.0F;
/*     */     }
/*     */     
/*  87 */     if (f5 > 0.0F)
/*     */     {
/*  89 */       GlStateManager.rotate(MathHelper.sin(f5) * f5 * f6 / 10.0F * entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*  92 */     int j = entity.getDisplayTileOffset();
/*     */     
/*  94 */     if (this.renderOutlines) {
/*     */       
/*  96 */       GlStateManager.enableColorMaterial();
/*  97 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */     } 
/*     */     
/* 100 */     IBlockState iblockstate = entity.getDisplayTile();
/*     */     
/* 102 */     if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*     */       
/* 104 */       GlStateManager.pushMatrix();
/* 105 */       bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 106 */       float f4 = 0.75F;
/* 107 */       GlStateManager.scale(0.75F, 0.75F, 0.75F);
/* 108 */       GlStateManager.translate(-0.5F, (j - 8) / 16.0F, 0.5F);
/* 109 */       renderCartContents(entity, partialTicks, iblockstate);
/* 110 */       GlStateManager.popMatrix();
/* 111 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 112 */       bindEntityTexture(entity);
/*     */     } 
/*     */     
/* 115 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 116 */     this.modelMinecart.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 117 */     GlStateManager.popMatrix();
/*     */     
/* 119 */     if (this.renderOutlines) {
/*     */       
/* 121 */       GlStateManager.disableOutlineMode();
/* 122 */       GlStateManager.disableColorMaterial();
/*     */     } 
/*     */     
/* 125 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(T entity) {
/* 133 */     return MINECART_TEXTURES;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderCartContents(T p_188319_1_, float partialTicks, IBlockState p_188319_3_) {
/* 138 */     GlStateManager.pushMatrix();
/* 139 */     Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(p_188319_3_, p_188319_1_.getBrightness());
/* 140 */     GlStateManager.popMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
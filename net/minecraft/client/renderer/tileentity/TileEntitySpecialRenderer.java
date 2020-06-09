/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.IEntityRenderer;
/*     */ 
/*     */ public abstract class TileEntitySpecialRenderer<T extends TileEntity>
/*     */   implements IEntityRenderer {
/*  18 */   protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] { new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png") };
/*     */   protected TileEntityRendererDispatcher rendererDispatcher;
/*  20 */   private Class tileEntityClass = null;
/*  21 */   private ResourceLocation locationTextureCustom = null;
/*     */ 
/*     */   
/*     */   public void func_192841_a(T p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  25 */     ITextComponent itextcomponent = p_192841_1_.getDisplayName();
/*     */     
/*  27 */     if (itextcomponent != null && this.rendererDispatcher.cameraHitResult != null && p_192841_1_.getPos().equals(this.rendererDispatcher.cameraHitResult.getBlockPos())) {
/*     */       
/*  29 */       setLightmapDisabled(true);
/*  30 */       drawNameplate(p_192841_1_, itextcomponent.getFormattedText(), p_192841_2_, p_192841_4_, p_192841_6_, 12);
/*  31 */       setLightmapDisabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLightmapDisabled(boolean disabled) {
/*  41 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*     */     
/*  43 */     if (disabled) {
/*     */       
/*  45 */       GlStateManager.disableTexture2D();
/*     */     }
/*     */     else {
/*     */       
/*  49 */       GlStateManager.enableTexture2D();
/*     */     } 
/*     */     
/*  52 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bindTexture(ResourceLocation location) {
/*  57 */     TextureManager texturemanager = this.rendererDispatcher.renderEngine;
/*     */     
/*  59 */     if (texturemanager != null)
/*     */     {
/*  61 */       texturemanager.bindTexture(location);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected World getWorld() {
/*  67 */     return this.rendererDispatcher.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/*  72 */     this.rendererDispatcher = rendererDispatcherIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/*  77 */     return this.rendererDispatcher.getFontRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalRenderer(T te) {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawNameplate(T te, String str, double x, double y, double z, int maxDistance) {
/*  87 */     Entity entity = this.rendererDispatcher.entity;
/*  88 */     double d0 = te.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */     
/*  90 */     if (d0 <= (maxDistance * maxDistance)) {
/*     */       
/*  92 */       float f = this.rendererDispatcher.entityYaw;
/*  93 */       float f1 = this.rendererDispatcher.entityPitch;
/*  94 */       boolean flag = false;
/*  95 */       EntityRenderer.drawNameplate(getFontRenderer(), entity, str, (float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F, 0, f, f1, false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTileEntityFast(T p_renderTileEntityFast_1_, double p_renderTileEntityFast_2_, double p_renderTileEntityFast_4_, double p_renderTileEntityFast_6_, float p_renderTileEntityFast_8_, int p_renderTileEntityFast_9_, float p_renderTileEntityFast_10_, BufferBuilder p_renderTileEntityFast_11_) {}
/*     */ 
/*     */   
/*     */   public Class getEntityClass() {
/* 105 */     return this.tileEntityClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 110 */     this.tileEntityClass = p_setEntityClass_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationTextureCustom() {
/* 115 */     return this.locationTextureCustom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 120 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntitySpecialRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
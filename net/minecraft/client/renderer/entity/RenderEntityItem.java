/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import me.nzxter.bettercraft.utils.ItemPhysicUtils;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ public class RenderEntityItem
/*     */   extends Render<EntityItem>
/*     */ {
/*     */   private final RenderItem itemRenderer;
/*  22 */   private final Random random = new Random();
/*     */ 
/*     */   
/*     */   public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
/*  26 */     super(renderManagerIn);
/*  27 */     this.itemRenderer = p_i46167_2_;
/*  28 */     this.shadowSize = 0.15F;
/*  29 */     this.shadowOpaque = 0.75F;
/*     */   }
/*     */ 
/*     */   
/*     */   private int transformModelCount(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
/*  34 */     ItemStack itemstack = itemIn.getEntityItem();
/*  35 */     Item item = itemstack.getItem();
/*     */     
/*  37 */     if (item == null)
/*     */     {
/*  39 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  43 */     boolean flag = p_177077_9_.isGui3d();
/*  44 */     int i = getModelCount(itemstack);
/*  45 */     float f = 0.25F;
/*  46 */     float f1 = MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
/*  47 */     float f2 = (p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND)).scale.y;
/*  48 */     GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
/*     */     
/*  50 */     if (flag || this.renderManager.options != null) {
/*     */       
/*  52 */       float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
/*  53 */       GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */     } 
/*     */     
/*  56 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  57 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getModelCount(ItemStack stack) {
/*  63 */     int i = 1;
/*     */     
/*  65 */     if (stack.func_190916_E() > 48) {
/*     */       
/*  67 */       i = 5;
/*     */     }
/*  69 */     else if (stack.func_190916_E() > 32) {
/*     */       
/*  71 */       i = 4;
/*     */     }
/*  73 */     else if (stack.func_190916_E() > 16) {
/*     */       
/*  75 */       i = 3;
/*     */     }
/*  77 */     else if (stack.func_190916_E() > 1) {
/*     */       
/*  79 */       i = 2;
/*     */     } 
/*     */     
/*  82 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  91 */     ItemPhysicUtils.doRender((Entity)entity, x, y, z, entityYaw, partialTicks);
/*     */ 
/*     */     
/*  94 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */   
/*     */   private void renderNormal(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  98 */     ItemStack itemstack = entity.getEntityItem();
/*  99 */     this.random.setSeed(187L);
/* 100 */     boolean flag = false;
/*     */     
/* 102 */     if (bindEntityTexture(entity)) {
/*     */       
/* 104 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
/* 105 */       flag = true;
/*     */     } 
/*     */     
/* 108 */     GlStateManager.enableRescaleNormal();
/* 109 */     GlStateManager.alphaFunc(516, 0.1F);
/* 110 */     GlStateManager.enableBlend();
/* 111 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 112 */     GlStateManager.pushMatrix();
/* 113 */     IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
/* 114 */     int i = transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
/*     */     
/* 116 */     for (int j = 0; j < i; j++) {
/*     */       
/* 118 */       if (ibakedmodel.isGui3d()) {
/*     */         
/* 120 */         GlStateManager.pushMatrix();
/*     */         
/* 122 */         if (j > 0) {
/*     */           
/* 124 */           float f = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 125 */           float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 126 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 127 */           GlStateManager.translate(f, f1, f2);
/*     */         } 
/*     */         
/* 130 */         GlStateManager.scale(0.2F, 0.2F, 0.2F);
/* 131 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 132 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 133 */         GlStateManager.popMatrix();
/*     */       }
/*     */       else {
/*     */         
/* 137 */         GlStateManager.pushMatrix();
/* 138 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 139 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 140 */         GlStateManager.popMatrix();
/* 141 */         float f3 = (ibakedmodel.getItemCameraTransforms()).ground.scale.x;
/* 142 */         float f4 = (ibakedmodel.getItemCameraTransforms()).ground.scale.y;
/* 143 */         float f5 = (ibakedmodel.getItemCameraTransforms()).ground.scale.z;
/* 144 */         GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     GlStateManager.popMatrix();
/* 149 */     GlStateManager.disableRescaleNormal();
/* 150 */     GlStateManager.disableBlend();
/* 151 */     bindEntityTexture(entity);
/*     */     
/* 153 */     if (flag)
/*     */     {
/* 155 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItem entity) {
/* 165 */     return TextureMap.LOCATION_BLOCKS_TEXTURE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderEntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
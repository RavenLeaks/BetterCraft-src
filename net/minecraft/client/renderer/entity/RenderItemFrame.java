/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ModelManager;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ 
/*     */ public class RenderItemFrame
/*     */   extends Render<EntityItemFrame> {
/*  28 */   private static final ResourceLocation MAP_BACKGROUND_TEXTURES = new ResourceLocation("textures/map/map_background.png");
/*  29 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  30 */   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
/*  31 */   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*     */   public RenderItemFrame(RenderManager renderManagerIn, RenderItem itemRendererIn) {
/*  36 */     super(renderManagerIn);
/*  37 */     this.itemRenderer = itemRendererIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*     */     IBakedModel ibakedmodel;
/*  45 */     GlStateManager.pushMatrix();
/*  46 */     BlockPos blockpos = entity.getHangingPosition();
/*  47 */     double d0 = blockpos.getX() - entity.posX + x;
/*  48 */     double d1 = blockpos.getY() - entity.posY + y;
/*  49 */     double d2 = blockpos.getZ() - entity.posZ + z;
/*  50 */     GlStateManager.translate(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D);
/*  51 */     GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  52 */     this.renderManager.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  53 */     BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/*  54 */     ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
/*     */ 
/*     */     
/*  57 */     if (entity.getDisplayedItem().getItem() instanceof net.minecraft.item.ItemMap) {
/*     */       
/*  59 */       ibakedmodel = modelmanager.getModel(this.mapModel);
/*     */     }
/*     */     else {
/*     */       
/*  63 */       ibakedmodel = modelmanager.getModel(this.itemFrameModel);
/*     */     } 
/*     */     
/*  66 */     GlStateManager.pushMatrix();
/*  67 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*     */     
/*  69 */     if (this.renderOutlines) {
/*     */       
/*  71 */       GlStateManager.enableColorMaterial();
/*  72 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */     } 
/*     */     
/*  75 */     blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  77 */     if (this.renderOutlines) {
/*     */       
/*  79 */       GlStateManager.disableOutlineMode();
/*  80 */       GlStateManager.disableColorMaterial();
/*     */     } 
/*     */     
/*  83 */     GlStateManager.popMatrix();
/*  84 */     GlStateManager.translate(0.0F, 0.0F, 0.4375F);
/*  85 */     renderItem(entity);
/*  86 */     GlStateManager.popMatrix();
/*  87 */     renderName(entity, x + (entity.facingDirection.getFrontOffsetX() * 0.3F), y - 0.25D, z + (entity.facingDirection.getFrontOffsetZ() * 0.3F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getEntityTexture(EntityItemFrame entity) {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItem(EntityItemFrame itemFrame) {
/* 102 */     ItemStack itemstack = itemFrame.getDisplayedItem();
/*     */     
/* 104 */     if (!itemstack.func_190926_b()) {
/*     */       
/* 106 */       if (!Config.zoomMode) {
/*     */         
/* 108 */         EntityPlayerSP entityPlayerSP = this.mc.player;
/* 109 */         double d0 = itemFrame.getDistanceSq(((Entity)entityPlayerSP).posX, ((Entity)entityPlayerSP).posY, ((Entity)entityPlayerSP).posZ);
/*     */         
/* 111 */         if (d0 > 4096.0D) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 117 */       GlStateManager.pushMatrix();
/* 118 */       GlStateManager.disableLighting();
/* 119 */       boolean flag = itemstack.getItem() instanceof net.minecraft.item.ItemMap;
/* 120 */       int i = flag ? (itemFrame.getRotation() % 4 * 2) : itemFrame.getRotation();
/* 121 */       GlStateManager.rotate(i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 123 */       if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, new Object[] { itemFrame, this }))
/*     */       {
/* 125 */         if (flag) {
/*     */           
/* 127 */           this.renderManager.renderEngine.bindTexture(MAP_BACKGROUND_TEXTURES);
/* 128 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 129 */           float f = 0.0078125F;
/* 130 */           GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
/* 131 */           GlStateManager.translate(-64.0F, -64.0F, 0.0F);
/* 132 */           MapData mapdata = ReflectorForge.getMapData(Items.FILLED_MAP, itemstack, itemFrame.world);
/* 133 */           GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */           
/* 135 */           if (mapdata != null)
/*     */           {
/* 137 */             this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, true);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 142 */           GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 143 */           GlStateManager.pushAttrib();
/* 144 */           RenderHelper.enableStandardItemLighting();
/* 145 */           this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED);
/* 146 */           RenderHelper.disableStandardItemLighting();
/* 147 */           GlStateManager.popAttrib();
/*     */         } 
/*     */       }
/*     */       
/* 151 */       GlStateManager.enableLighting();
/* 152 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(EntityItemFrame entity, double x, double y, double z) {
/* 158 */     if (Minecraft.isGuiEnabled() && !entity.getDisplayedItem().func_190926_b() && entity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entity) {
/*     */       
/* 160 */       double d0 = entity.getDistanceSqToEntity(this.renderManager.renderViewEntity);
/* 161 */       float f = entity.isSneaking() ? 32.0F : 64.0F;
/*     */       
/* 163 */       if (d0 < (f * f)) {
/*     */         
/* 165 */         String s = entity.getDisplayedItem().getDisplayName();
/* 166 */         renderLivingLabel(entity, s, x, y, z, 64);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
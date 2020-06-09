/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelShulker;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityBed;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.tileentity.TileEntityEndGateway;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.tileentity.TileEntityStructure;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ public class TileEntityRendererDispatcher
/*     */ {
/*  42 */   public final Map<Class, TileEntitySpecialRenderer> mapSpecialRenderers = Maps.newHashMap();
/*  43 */   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
/*     */   
/*     */   public FontRenderer fontRenderer;
/*     */   
/*     */   public static double staticPlayerX;
/*     */   
/*     */   public static double staticPlayerY;
/*     */   
/*     */   public static double staticPlayerZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   public Entity entity;
/*     */   public float entityYaw;
/*     */   public float entityPitch;
/*     */   public RayTraceResult cameraHitResult;
/*     */   public double entityX;
/*     */   public double entityY;
/*     */   public double entityZ;
/*     */   public TileEntity tileEntityRendered;
/*  64 */   private Tessellator batchBuffer = new Tessellator(2097152);
/*     */   
/*     */   private boolean drawingBatch = false;
/*     */   
/*     */   private TileEntityRendererDispatcher() {
/*  69 */     this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
/*  70 */     this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
/*  71 */     this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
/*  72 */     this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
/*  73 */     this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
/*  74 */     this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
/*  75 */     this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
/*  76 */     this.mapSpecialRenderers.put(TileEntityEndGateway.class, new TileEntityEndGatewayRenderer());
/*  77 */     this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
/*  78 */     this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
/*  79 */     this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
/*  80 */     this.mapSpecialRenderers.put(TileEntityStructure.class, new TileEntityStructureRenderer());
/*  81 */     this.mapSpecialRenderers.put(TileEntityShulkerBox.class, new TileEntityShulkerBoxRenderer(new ModelShulker()));
/*  82 */     this.mapSpecialRenderers.put(TileEntityBed.class, new TileEntityBedRenderer());
/*     */     
/*  84 */     for (TileEntitySpecialRenderer<?> tileentityspecialrenderer : this.mapSpecialRenderers.values())
/*     */     {
/*  86 */       tileentityspecialrenderer.setRendererDispatcher(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(Class<? extends TileEntity> teClass) {
/*  92 */     TileEntitySpecialRenderer<T> tileentityspecialrenderer = this.mapSpecialRenderers.get(teClass);
/*     */     
/*  94 */     if (tileentityspecialrenderer == null && teClass != TileEntity.class) {
/*     */       
/*  96 */       tileentityspecialrenderer = getSpecialRendererByClass((Class)teClass.getSuperclass());
/*  97 */       this.mapSpecialRenderers.put(teClass, tileentityspecialrenderer);
/*     */     } 
/*     */     
/* 100 */     return tileentityspecialrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(@Nullable TileEntity tileEntityIn) {
/* 106 */     return (tileEntityIn == null) ? null : getSpecialRendererByClass((Class)tileEntityIn.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepare(World p_190056_1_, TextureManager p_190056_2_, FontRenderer p_190056_3_, Entity p_190056_4_, RayTraceResult p_190056_5_, float p_190056_6_) {
/* 111 */     if (this.worldObj != p_190056_1_)
/*     */     {
/* 113 */       setWorld(p_190056_1_);
/*     */     }
/*     */     
/* 116 */     this.renderEngine = p_190056_2_;
/* 117 */     this.entity = p_190056_4_;
/* 118 */     this.fontRenderer = p_190056_3_;
/* 119 */     this.cameraHitResult = p_190056_5_;
/* 120 */     this.entityYaw = p_190056_4_.prevRotationYaw + (p_190056_4_.rotationYaw - p_190056_4_.prevRotationYaw) * p_190056_6_;
/* 121 */     this.entityPitch = p_190056_4_.prevRotationPitch + (p_190056_4_.rotationPitch - p_190056_4_.prevRotationPitch) * p_190056_6_;
/* 122 */     this.entityX = p_190056_4_.lastTickPosX + (p_190056_4_.posX - p_190056_4_.lastTickPosX) * p_190056_6_;
/* 123 */     this.entityY = p_190056_4_.lastTickPosY + (p_190056_4_.posY - p_190056_4_.lastTickPosY) * p_190056_6_;
/* 124 */     this.entityZ = p_190056_4_.lastTickPosZ + (p_190056_4_.posZ - p_190056_4_.lastTickPosZ) * p_190056_6_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTileEntity(TileEntity tileentityIn, float partialTicks, int destroyStage) {
/* 129 */     if (tileentityIn.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileentityIn.getMaxRenderDistanceSquared()) {
/*     */       
/* 131 */       RenderHelper.enableStandardItemLighting();
/* 132 */       boolean flag = true;
/*     */       
/* 134 */       if (Reflector.ForgeTileEntity_hasFastRenderer.exists())
/*     */       {
/* 136 */         flag = !(this.drawingBatch && Reflector.callBoolean(tileentityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0]));
/*     */       }
/*     */       
/* 139 */       if (flag) {
/*     */         
/* 141 */         int i = this.worldObj.getCombinedLight(tileentityIn.getPos(), 0);
/* 142 */         int j = i % 65536;
/* 143 */         int k = i / 65536;
/* 144 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 145 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 148 */       BlockPos blockpos = tileentityIn.getPos();
/* 149 */       func_192854_a(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks) {
/* 158 */     func_192855_a(tileEntityIn, x, y, z, partialTicks, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192855_a(TileEntity p_192855_1_, double p_192855_2_, double p_192855_4_, double p_192855_6_, float p_192855_8_, float p_192855_9_) {
/* 163 */     func_192854_a(p_192855_1_, p_192855_2_, p_192855_4_, p_192855_6_, p_192855_8_, -1, p_192855_9_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192854_a(TileEntity p_192854_1_, double p_192854_2_, double p_192854_4_, double p_192854_6_, float p_192854_8_, int p_192854_9_, float p_192854_10_) {
/* 168 */     TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = getSpecialRenderer(p_192854_1_);
/*     */     
/* 170 */     if (tileentityspecialrenderer != null) {
/*     */       
/*     */       try {
/*     */         
/* 174 */         this.tileEntityRendered = p_192854_1_;
/*     */         
/* 176 */         if (this.drawingBatch && Reflector.callBoolean(p_192854_1_, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0])) {
/*     */           
/* 178 */           tileentityspecialrenderer.renderTileEntityFast(p_192854_1_, p_192854_2_, p_192854_4_, p_192854_6_, p_192854_8_, p_192854_9_, p_192854_10_, this.batchBuffer.getBuffer());
/*     */         }
/*     */         else {
/*     */           
/* 182 */           tileentityspecialrenderer.func_192841_a(p_192854_1_, p_192854_2_, p_192854_4_, p_192854_6_, p_192854_8_, p_192854_9_, p_192854_10_);
/*     */         } 
/*     */         
/* 185 */         this.tileEntityRendered = null;
/*     */       }
/* 187 */       catch (Throwable throwable) {
/*     */         
/* 189 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
/* 190 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block Entity Details");
/* 191 */         p_192854_1_.addInfoToCrashReport(crashreportcategory);
/* 192 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(@Nullable World worldIn) {
/* 199 */     this.worldObj = worldIn;
/*     */     
/* 201 */     if (worldIn == null)
/*     */     {
/* 203 */       this.entity = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 209 */     return this.fontRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDrawBatch() {
/* 214 */     this.batchBuffer.getBuffer().begin(7, DefaultVertexFormats.BLOCK);
/* 215 */     this.drawingBatch = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBatch(int p_drawBatch_1_) {
/* 220 */     this.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 221 */     RenderHelper.disableStandardItemLighting();
/* 222 */     GlStateManager.blendFunc(770, 771);
/* 223 */     GlStateManager.enableBlend();
/* 224 */     GlStateManager.disableCull();
/*     */     
/* 226 */     if (Minecraft.isAmbientOcclusionEnabled()) {
/*     */       
/* 228 */       GlStateManager.shadeModel(7425);
/*     */     }
/*     */     else {
/*     */       
/* 232 */       GlStateManager.shadeModel(7424);
/*     */     } 
/*     */     
/* 235 */     if (p_drawBatch_1_ > 0)
/*     */     {
/* 237 */       this.batchBuffer.getBuffer().sortVertexData(0.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/* 240 */     this.batchBuffer.draw();
/* 241 */     RenderHelper.enableStandardItemLighting();
/* 242 */     this.drawingBatch = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
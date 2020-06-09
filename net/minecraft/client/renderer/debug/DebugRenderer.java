/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class DebugRenderer
/*     */ {
/*     */   public final IDebugRenderer debugRendererPathfinding;
/*     */   public final IDebugRenderer debugRendererWater;
/*     */   public final IDebugRenderer debugRendererChunkBorder;
/*     */   public final IDebugRenderer debugRendererHeightMap;
/*     */   public final IDebugRenderer field_191325_e;
/*     */   public final IDebugRenderer field_191557_f;
/*     */   public final IDebugRenderer field_193852_g;
/*     */   private boolean chunkBordersEnabled;
/*     */   private boolean pathfindingEnabled;
/*     */   private boolean waterEnabled;
/*     */   private boolean heightmapEnabled;
/*     */   private boolean field_191326_j;
/*     */   private boolean field_191558_l;
/*     */   private boolean field_193853_n;
/*     */   
/*     */   public DebugRenderer(Minecraft clientIn) {
/*  28 */     this.debugRendererPathfinding = new DebugRendererPathfinding(clientIn);
/*  29 */     this.debugRendererWater = new DebugRendererWater(clientIn);
/*  30 */     this.debugRendererChunkBorder = new DebugRendererChunkBorder(clientIn);
/*  31 */     this.debugRendererHeightMap = new DebugRendererHeightMap(clientIn);
/*  32 */     this.field_191325_e = new DebugRendererCollisionBox(clientIn);
/*  33 */     this.field_191557_f = new DebugRendererNeighborsUpdate(clientIn);
/*  34 */     this.field_193852_g = new DebugRendererSolidFace(clientIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender() {
/*  39 */     return !(!this.chunkBordersEnabled && !this.pathfindingEnabled && !this.waterEnabled && !this.heightmapEnabled && !this.field_191326_j && !this.field_191558_l && !this.field_193853_n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean toggleDebugScreen() {
/*  47 */     this.chunkBordersEnabled = !this.chunkBordersEnabled;
/*  48 */     return this.chunkBordersEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDebug(float partialTicks, long finishTimeNano) {
/*  53 */     if (this.pathfindingEnabled)
/*     */     {
/*  55 */       this.debugRendererPathfinding.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  58 */     if (this.chunkBordersEnabled && !Minecraft.getMinecraft().isReducedDebug())
/*     */     {
/*  60 */       this.debugRendererChunkBorder.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  63 */     if (this.waterEnabled)
/*     */     {
/*  65 */       this.debugRendererWater.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  68 */     if (this.heightmapEnabled)
/*     */     {
/*  70 */       this.debugRendererHeightMap.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  73 */     if (this.field_191326_j)
/*     */     {
/*  75 */       this.field_191325_e.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  78 */     if (this.field_191558_l)
/*     */     {
/*  80 */       this.field_191557_f.render(partialTicks, finishTimeNano);
/*     */     }
/*     */     
/*  83 */     if (this.field_193853_n)
/*     */     {
/*  85 */       this.field_193852_g.render(partialTicks, finishTimeNano);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_191556_a(String p_191556_0_, int p_191556_1_, int p_191556_2_, int p_191556_3_, float p_191556_4_, int p_191556_5_) {
/*  91 */     renderDebugText(p_191556_0_, p_191556_1_ + 0.5D, p_191556_2_ + 0.5D, p_191556_3_ + 0.5D, p_191556_4_, p_191556_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderDebugText(String str, double x, double y, double z, float partialTicks, int color) {
/*  96 */     Minecraft minecraft = Minecraft.getMinecraft();
/*     */     
/*  98 */     if (minecraft.player != null && minecraft.getRenderManager() != null && (minecraft.getRenderManager()).options != null) {
/*     */       
/* 100 */       FontRenderer fontrenderer = minecraft.fontRendererObj;
/* 101 */       EntityPlayerSP entityPlayerSP = minecraft.player;
/* 102 */       double d0 = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * partialTicks;
/* 103 */       double d1 = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * partialTicks;
/* 104 */       double d2 = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * partialTicks;
/* 105 */       GlStateManager.pushMatrix();
/* 106 */       GlStateManager.translate((float)(x - d0), (float)(y - d1) + 0.07F, (float)(z - d2));
/* 107 */       GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
/* 108 */       GlStateManager.scale(0.02F, -0.02F, 0.02F);
/* 109 */       RenderManager rendermanager = minecraft.getRenderManager();
/* 110 */       GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 111 */       GlStateManager.rotate(((rendermanager.options.thirdPersonView == 2) ? true : -1) * rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 112 */       GlStateManager.disableLighting();
/* 113 */       GlStateManager.enableTexture2D();
/* 114 */       GlStateManager.enableDepth();
/* 115 */       GlStateManager.depthMask(true);
/* 116 */       GlStateManager.scale(-1.0F, 1.0F, 1.0F);
/* 117 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
/* 118 */       GlStateManager.enableLighting();
/* 119 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 120 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface IDebugRenderer {
/*     */     void render(float param1Float, long param1Long);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
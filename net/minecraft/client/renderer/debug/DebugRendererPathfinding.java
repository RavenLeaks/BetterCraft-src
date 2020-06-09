/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class DebugRendererPathfinding
/*     */   implements DebugRenderer.IDebugRenderer {
/*     */   private final Minecraft minecraft;
/*  20 */   private final Map<Integer, Path> pathMap = Maps.newHashMap();
/*  21 */   private final Map<Integer, Float> pathMaxDistance = Maps.newHashMap();
/*  22 */   private final Map<Integer, Long> creationMap = Maps.newHashMap();
/*     */   
/*     */   private EntityPlayer player;
/*     */   private double xo;
/*     */   private double yo;
/*     */   private double zo;
/*     */   
/*     */   public DebugRendererPathfinding(Minecraft minecraftIn) {
/*  30 */     this.minecraft = minecraftIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPath(int p_188289_1_, Path p_188289_2_, float p_188289_3_) {
/*  35 */     this.pathMap.put(Integer.valueOf(p_188289_1_), p_188289_2_);
/*  36 */     this.creationMap.put(Integer.valueOf(p_188289_1_), Long.valueOf(System.currentTimeMillis()));
/*  37 */     this.pathMaxDistance.put(Integer.valueOf(p_188289_1_), Float.valueOf(p_188289_3_));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_190060_1_, long p_190060_2_) {
/*  42 */     if (!this.pathMap.isEmpty()) {
/*     */       
/*  44 */       long i = System.currentTimeMillis();
/*  45 */       this.player = (EntityPlayer)this.minecraft.player;
/*  46 */       this.xo = this.player.lastTickPosX + (this.player.posX - this.player.lastTickPosX) * p_190060_1_;
/*  47 */       this.yo = this.player.lastTickPosY + (this.player.posY - this.player.lastTickPosY) * p_190060_1_;
/*  48 */       this.zo = this.player.lastTickPosZ + (this.player.posZ - this.player.lastTickPosZ) * p_190060_1_;
/*  49 */       GlStateManager.pushMatrix();
/*  50 */       GlStateManager.enableBlend();
/*  51 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  52 */       GlStateManager.color(0.0F, 1.0F, 0.0F, 0.75F);
/*  53 */       GlStateManager.disableTexture2D();
/*  54 */       GlStateManager.glLineWidth(6.0F);
/*     */       
/*  56 */       for (Integer integer : this.pathMap.keySet()) {
/*     */         
/*  58 */         Path path = this.pathMap.get(integer);
/*  59 */         float f = ((Float)this.pathMaxDistance.get(integer)).floatValue();
/*  60 */         renderPathLine(p_190060_1_, path);
/*  61 */         PathPoint pathpoint = path.getTarget();
/*     */         
/*  63 */         if (addDistanceToPlayer(pathpoint) <= 40.0F) {
/*     */           
/*  65 */           RenderGlobal.renderFilledBox((new AxisAlignedBB((pathpoint.xCoord + 0.25F), (pathpoint.yCoord + 0.25F), pathpoint.zCoord + 0.25D, (pathpoint.xCoord + 0.75F), (pathpoint.yCoord + 0.75F), (pathpoint.zCoord + 0.75F))).offset(-this.xo, -this.yo, -this.zo), 0.0F, 1.0F, 0.0F, 0.5F);
/*     */           
/*  67 */           for (int k = 0; k < path.getCurrentPathLength(); k++) {
/*     */             
/*  69 */             PathPoint pathpoint1 = path.getPathPointFromIndex(k);
/*     */             
/*  71 */             if (addDistanceToPlayer(pathpoint1) <= 40.0F) {
/*     */               
/*  73 */               float f1 = (k == path.getCurrentPathIndex()) ? 1.0F : 0.0F;
/*  74 */               float f2 = (k == path.getCurrentPathIndex()) ? 0.0F : 1.0F;
/*  75 */               RenderGlobal.renderFilledBox((new AxisAlignedBB((pathpoint1.xCoord + 0.5F - f), (pathpoint1.yCoord + 0.01F * k), (pathpoint1.zCoord + 0.5F - f), (pathpoint1.xCoord + 0.5F + f), (pathpoint1.yCoord + 0.25F + 0.01F * k), (pathpoint1.zCoord + 0.5F + f))).offset(-this.xo, -this.yo, -this.zo), f1, 0.0F, f2, 0.5F);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  81 */       for (Integer integer1 : this.pathMap.keySet()) {
/*     */         
/*  83 */         Path path1 = this.pathMap.get(integer1); byte b1; int m;
/*     */         PathPoint[] arrayOfPathPoint;
/*  85 */         for (m = (arrayOfPathPoint = path1.getClosedSet()).length, b1 = 0; b1 < m; ) { PathPoint pathpoint3 = arrayOfPathPoint[b1];
/*     */           
/*  87 */           if (addDistanceToPlayer(pathpoint3) <= 40.0F) {
/*     */             
/*  89 */             DebugRenderer.renderDebugText(String.format("%s", new Object[] { pathpoint3.nodeType }), pathpoint3.xCoord + 0.5D, pathpoint3.yCoord + 0.75D, pathpoint3.zCoord + 0.5D, p_190060_1_, -65536);
/*  90 */             DebugRenderer.renderDebugText(String.format("%.2f", new Object[] { Float.valueOf(pathpoint3.costMalus) }), pathpoint3.xCoord + 0.5D, pathpoint3.yCoord + 0.25D, pathpoint3.zCoord + 0.5D, p_190060_1_, -65536);
/*     */           } 
/*     */           b1++; }
/*     */         
/*  94 */         for (m = (arrayOfPathPoint = path1.getOpenSet()).length, b1 = 0; b1 < m; ) { PathPoint pathpoint4 = arrayOfPathPoint[b1];
/*     */           
/*  96 */           if (addDistanceToPlayer(pathpoint4) <= 40.0F) {
/*     */             
/*  98 */             DebugRenderer.renderDebugText(String.format("%s", new Object[] { pathpoint4.nodeType }), pathpoint4.xCoord + 0.5D, pathpoint4.yCoord + 0.75D, pathpoint4.zCoord + 0.5D, p_190060_1_, -16776961);
/*  99 */             DebugRenderer.renderDebugText(String.format("%.2f", new Object[] { Float.valueOf(pathpoint4.costMalus) }), pathpoint4.xCoord + 0.5D, pathpoint4.yCoord + 0.25D, pathpoint4.zCoord + 0.5D, p_190060_1_, -16776961);
/*     */           } 
/*     */           b1++; }
/*     */         
/* 103 */         for (int k = 0; k < path1.getCurrentPathLength(); k++) {
/*     */           
/* 105 */           PathPoint pathpoint2 = path1.getPathPointFromIndex(k);
/*     */           
/* 107 */           if (addDistanceToPlayer(pathpoint2) <= 40.0F) {
/*     */             
/* 109 */             DebugRenderer.renderDebugText(String.format("%s", new Object[] { pathpoint2.nodeType }), pathpoint2.xCoord + 0.5D, pathpoint2.yCoord + 0.75D, pathpoint2.zCoord + 0.5D, p_190060_1_, -1);
/* 110 */             DebugRenderer.renderDebugText(String.format("%.2f", new Object[] { Float.valueOf(pathpoint2.costMalus) }), pathpoint2.xCoord + 0.5D, pathpoint2.yCoord + 0.25D, pathpoint2.zCoord + 0.5D, p_190060_1_, -1);
/*     */           } 
/*     */         } 
/*     */       }  byte b; int j;
/*     */       Integer[] arrayOfInteger;
/* 115 */       for (j = (arrayOfInteger = (Integer[])this.creationMap.keySet().toArray((Object[])new Integer[0])).length, b = 0; b < j; ) { Integer integer2 = arrayOfInteger[b];
/*     */         
/* 117 */         if (i - ((Long)this.creationMap.get(integer2)).longValue() > 20000L) {
/*     */           
/* 119 */           this.pathMap.remove(integer2);
/* 120 */           this.creationMap.remove(integer2);
/*     */         } 
/*     */         b++; }
/*     */       
/* 124 */       GlStateManager.enableTexture2D();
/* 125 */       GlStateManager.disableBlend();
/* 126 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPathLine(float p_190067_1_, Path p_190067_2_) {
/* 132 */     Tessellator tessellator = Tessellator.getInstance();
/* 133 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 134 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/* 136 */     for (int i = 0; i < p_190067_2_.getCurrentPathLength(); i++) {
/*     */       
/* 138 */       PathPoint pathpoint = p_190067_2_.getPathPointFromIndex(i);
/*     */       
/* 140 */       if (addDistanceToPlayer(pathpoint) <= 40.0F) {
/*     */         
/* 142 */         float f = i / p_190067_2_.getCurrentPathLength() * 0.33F;
/* 143 */         int j = (i == 0) ? 0 : MathHelper.hsvToRGB(f, 0.9F, 0.9F);
/* 144 */         int k = j >> 16 & 0xFF;
/* 145 */         int l = j >> 8 & 0xFF;
/* 146 */         int i1 = j & 0xFF;
/* 147 */         bufferbuilder.pos(pathpoint.xCoord - this.xo + 0.5D, pathpoint.yCoord - this.yo + 0.5D, pathpoint.zCoord - this.zo + 0.5D).color(k, l, i1, 255).endVertex();
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   private float addDistanceToPlayer(PathPoint p_190066_1_) {
/* 156 */     return (float)(Math.abs(p_190066_1_.xCoord - this.player.posX) + Math.abs(p_190066_1_.yCoord - this.player.posY) + Math.abs(p_190066_1_.zCoord - this.player.posZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererPathfinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
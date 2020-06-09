/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderLightningBolt
/*     */   extends Render<EntityLightningBolt> {
/*     */   public RenderLightningBolt(RenderManager renderManagerIn) {
/*  16 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityLightningBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  24 */     Tessellator tessellator = Tessellator.getInstance();
/*  25 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  26 */     GlStateManager.disableTexture2D();
/*  27 */     GlStateManager.disableLighting();
/*  28 */     GlStateManager.enableBlend();
/*  29 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
/*  30 */     double[] adouble = new double[8];
/*  31 */     double[] adouble1 = new double[8];
/*  32 */     double d0 = 0.0D;
/*  33 */     double d1 = 0.0D;
/*  34 */     Random random = new Random(entity.boltVertex);
/*     */     
/*  36 */     for (int i = 7; i >= 0; i--) {
/*     */       
/*  38 */       adouble[i] = d0;
/*  39 */       adouble1[i] = d1;
/*  40 */       d0 += (random.nextInt(11) - 5);
/*  41 */       d1 += (random.nextInt(11) - 5);
/*     */     } 
/*     */     
/*  44 */     for (int k1 = 0; k1 < 4; k1++) {
/*     */       
/*  46 */       Random random1 = new Random(entity.boltVertex);
/*     */       
/*  48 */       for (int j = 0; j < 3; j++) {
/*     */         
/*  50 */         int k = 7;
/*  51 */         int l = 0;
/*     */         
/*  53 */         if (j > 0)
/*     */         {
/*  55 */           k = 7 - j;
/*     */         }
/*     */         
/*  58 */         if (j > 0)
/*     */         {
/*  60 */           l = k - 2;
/*     */         }
/*     */         
/*  63 */         double d2 = adouble[k] - d0;
/*  64 */         double d3 = adouble1[k] - d1;
/*     */         
/*  66 */         for (int i1 = k; i1 >= l; i1--) {
/*     */           
/*  68 */           double d4 = d2;
/*  69 */           double d5 = d3;
/*     */           
/*  71 */           if (j == 0) {
/*     */             
/*  73 */             d2 += (random1.nextInt(11) - 5);
/*  74 */             d3 += (random1.nextInt(11) - 5);
/*     */           }
/*     */           else {
/*     */             
/*  78 */             d2 += (random1.nextInt(31) - 15);
/*  79 */             d3 += (random1.nextInt(31) - 15);
/*     */           } 
/*     */           
/*  82 */           bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  83 */           float f = 0.5F;
/*  84 */           float f1 = 0.45F;
/*  85 */           float f2 = 0.45F;
/*  86 */           float f3 = 0.5F;
/*  87 */           double d6 = 0.1D + k1 * 0.2D;
/*     */           
/*  89 */           if (j == 0)
/*     */           {
/*  91 */             d6 *= i1 * 0.1D + 1.0D;
/*     */           }
/*     */           
/*  94 */           double d7 = 0.1D + k1 * 0.2D;
/*     */           
/*  96 */           if (j == 0)
/*     */           {
/*  98 */             d7 *= (i1 - 1) * 0.1D + 1.0D;
/*     */           }
/*     */           
/* 101 */           for (int j1 = 0; j1 < 5; j1++) {
/*     */             
/* 103 */             double d8 = x + 0.5D - d6;
/* 104 */             double d9 = z + 0.5D - d6;
/*     */             
/* 106 */             if (j1 == 1 || j1 == 2)
/*     */             {
/* 108 */               d8 += d6 * 2.0D;
/*     */             }
/*     */             
/* 111 */             if (j1 == 2 || j1 == 3)
/*     */             {
/* 113 */               d9 += d6 * 2.0D;
/*     */             }
/*     */             
/* 116 */             double d10 = x + 0.5D - d7;
/* 117 */             double d11 = z + 0.5D - d7;
/*     */             
/* 119 */             if (j1 == 1 || j1 == 2)
/*     */             {
/* 121 */               d10 += d7 * 2.0D;
/*     */             }
/*     */             
/* 124 */             if (j1 == 2 || j1 == 3)
/*     */             {
/* 126 */               d11 += d7 * 2.0D;
/*     */             }
/*     */             
/* 129 */             bufferbuilder.pos(d10 + d2, y + (i1 * 16), d11 + d3).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/* 130 */             bufferbuilder.pos(d8 + d4, y + ((i1 + 1) * 16), d9 + d5).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/*     */           } 
/*     */           
/* 133 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     GlStateManager.disableBlend();
/* 139 */     GlStateManager.enableLighting();
/* 140 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getEntityTexture(EntityLightningBolt entity) {
/* 150 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
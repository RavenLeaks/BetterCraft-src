/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class DebugRendererChunkBorder
/*     */   implements DebugRenderer.IDebugRenderer {
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   public DebugRendererChunkBorder(Minecraft minecraftIn) {
/*  16 */     this.minecraft = minecraftIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_190060_1_, long p_190060_2_) {
/*  21 */     EntityPlayerSP entityPlayerSP = this.minecraft.player;
/*  22 */     Tessellator tessellator = Tessellator.getInstance();
/*  23 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  24 */     double d0 = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * p_190060_1_;
/*  25 */     double d1 = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * p_190060_1_;
/*  26 */     double d2 = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * p_190060_1_;
/*  27 */     double d3 = 0.0D - d1;
/*  28 */     double d4 = 256.0D - d1;
/*  29 */     GlStateManager.disableTexture2D();
/*  30 */     GlStateManager.disableBlend();
/*  31 */     double d5 = (((EntityPlayer)entityPlayerSP).chunkCoordX << 4) - d0;
/*  32 */     double d6 = (((EntityPlayer)entityPlayerSP).chunkCoordZ << 4) - d2;
/*  33 */     GlStateManager.glLineWidth(1.0F);
/*  34 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/*  36 */     for (int i = -16; i <= 32; i += 16) {
/*     */       
/*  38 */       for (int j = -16; j <= 32; j += 16) {
/*     */         
/*  40 */         bufferbuilder.pos(d5 + i, d3, d6 + j).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
/*  41 */         bufferbuilder.pos(d5 + i, d3, d6 + j).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  42 */         bufferbuilder.pos(d5 + i, d4, d6 + j).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  43 */         bufferbuilder.pos(d5 + i, d4, d6 + j).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
/*     */       } 
/*     */     } 
/*     */     
/*  47 */     for (int k = 2; k < 16; k += 2) {
/*     */       
/*  49 */       bufferbuilder.pos(d5 + k, d3, d6).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  50 */       bufferbuilder.pos(d5 + k, d3, d6).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  51 */       bufferbuilder.pos(d5 + k, d4, d6).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  52 */       bufferbuilder.pos(d5 + k, d4, d6).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  53 */       bufferbuilder.pos(d5 + k, d3, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  54 */       bufferbuilder.pos(d5 + k, d3, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  55 */       bufferbuilder.pos(d5 + k, d4, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  56 */       bufferbuilder.pos(d5 + k, d4, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */     
/*  59 */     for (int l = 2; l < 16; l += 2) {
/*     */       
/*  61 */       bufferbuilder.pos(d5, d3, d6 + l).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  62 */       bufferbuilder.pos(d5, d3, d6 + l).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  63 */       bufferbuilder.pos(d5, d4, d6 + l).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  64 */       bufferbuilder.pos(d5, d4, d6 + l).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  65 */       bufferbuilder.pos(d5 + 16.0D, d3, d6 + l).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  66 */       bufferbuilder.pos(d5 + 16.0D, d3, d6 + l).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  67 */       bufferbuilder.pos(d5 + 16.0D, d4, d6 + l).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  68 */       bufferbuilder.pos(d5 + 16.0D, d4, d6 + l).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */     
/*  71 */     for (int i1 = 0; i1 <= 256; i1 += 2) {
/*     */       
/*  73 */       double d7 = i1 - d1;
/*  74 */       bufferbuilder.pos(d5, d7, d6).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  75 */       bufferbuilder.pos(d5, d7, d6).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  76 */       bufferbuilder.pos(d5, d7, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  77 */       bufferbuilder.pos(d5 + 16.0D, d7, d6 + 16.0D).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  78 */       bufferbuilder.pos(d5 + 16.0D, d7, d6).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  79 */       bufferbuilder.pos(d5, d7, d6).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
/*  80 */       bufferbuilder.pos(d5, d7, d6).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */     
/*  83 */     tessellator.draw();
/*  84 */     GlStateManager.glLineWidth(2.0F);
/*  85 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/*  87 */     for (int j1 = 0; j1 <= 16; j1 += 16) {
/*     */       
/*  89 */       for (int l1 = 0; l1 <= 16; l1 += 16) {
/*     */         
/*  91 */         bufferbuilder.pos(d5 + j1, d3, d6 + l1).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*  92 */         bufferbuilder.pos(d5 + j1, d3, d6 + l1).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/*  93 */         bufferbuilder.pos(d5 + j1, d4, d6 + l1).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/*  94 */         bufferbuilder.pos(d5 + j1, d4, d6 + l1).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     for (int k1 = 0; k1 <= 256; k1 += 16) {
/*     */       
/* 100 */       double d8 = k1 - d1;
/* 101 */       bufferbuilder.pos(d5, d8, d6).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/* 102 */       bufferbuilder.pos(d5, d8, d6).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 103 */       bufferbuilder.pos(d5, d8, d6 + 16.0D).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 104 */       bufferbuilder.pos(d5 + 16.0D, d8, d6 + 16.0D).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 105 */       bufferbuilder.pos(d5 + 16.0D, d8, d6).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 106 */       bufferbuilder.pos(d5, d8, d6).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 107 */       bufferbuilder.pos(d5, d8, d6).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*     */     } 
/*     */     
/* 110 */     tessellator.draw();
/* 111 */     GlStateManager.glLineWidth(1.0F);
/* 112 */     GlStateManager.enableBlend();
/* 113 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererChunkBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
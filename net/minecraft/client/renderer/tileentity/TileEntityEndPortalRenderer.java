/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import shadersmod.client.ShadersRender;
/*     */ 
/*     */ public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
/*  19 */   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
/*  20 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*  21 */   private static final Random RANDOM = new Random(31100L);
/*  22 */   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
/*  23 */   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
/*  24 */   private final FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntityEndPortal p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  28 */     if (!Config.isShaders() || !ShadersRender.renderEndPortal(p_192841_1_, p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_9_, func_191287_c())) {
/*     */       
/*  30 */       GlStateManager.disableLighting();
/*  31 */       RANDOM.setSeed(31100L);
/*  32 */       GlStateManager.getFloat(2982, MODELVIEW);
/*  33 */       GlStateManager.getFloat(2983, PROJECTION);
/*  34 */       double d0 = p_192841_2_ * p_192841_2_ + p_192841_4_ * p_192841_4_ + p_192841_6_ * p_192841_6_;
/*  35 */       int i = func_191286_a(d0);
/*  36 */       float f = func_191287_c();
/*  37 */       boolean flag = false;
/*     */       
/*  39 */       for (int j = 0; j < i; j++) {
/*     */         
/*  41 */         GlStateManager.pushMatrix();
/*  42 */         float f1 = 2.0F / (18 - j);
/*     */         
/*  44 */         if (j == 0) {
/*     */           
/*  46 */           bindTexture(END_SKY_TEXTURE);
/*  47 */           f1 = 0.15F;
/*  48 */           GlStateManager.enableBlend();
/*  49 */           GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*     */         } 
/*     */         
/*  52 */         if (j >= 1) {
/*     */           
/*  54 */           bindTexture(END_PORTAL_TEXTURE);
/*  55 */           flag = true;
/*  56 */           (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/*     */         } 
/*     */         
/*  59 */         if (j == 1) {
/*     */           
/*  61 */           GlStateManager.enableBlend();
/*  62 */           GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/*     */         } 
/*     */         
/*  65 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9216);
/*  66 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9216);
/*  67 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9216);
/*  68 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9474, getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/*  69 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9474, getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/*  70 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9474, getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/*  71 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
/*  72 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
/*  73 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
/*  74 */         GlStateManager.popMatrix();
/*  75 */         GlStateManager.matrixMode(5890);
/*  76 */         GlStateManager.pushMatrix();
/*  77 */         GlStateManager.loadIdentity();
/*  78 */         GlStateManager.translate(0.5F, 0.5F, 0.0F);
/*  79 */         GlStateManager.scale(0.5F, 0.5F, 1.0F);
/*  80 */         float f2 = (j + 1);
/*  81 */         GlStateManager.translate(17.0F / f2, (2.0F + f2 / 1.5F) * (float)Minecraft.getSystemTime() % 800000.0F / 800000.0F, 0.0F);
/*  82 */         GlStateManager.rotate((f2 * f2 * 4321.0F + f2 * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
/*  83 */         GlStateManager.scale(4.5F - f2 / 4.0F, 4.5F - f2 / 4.0F, 1.0F);
/*  84 */         GlStateManager.multMatrix(PROJECTION);
/*  85 */         GlStateManager.multMatrix(MODELVIEW);
/*  86 */         Tessellator tessellator = Tessellator.getInstance();
/*  87 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  88 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  89 */         float f3 = (RANDOM.nextFloat() * 0.5F + 0.1F) * f1;
/*  90 */         float f4 = (RANDOM.nextFloat() * 0.5F + 0.4F) * f1;
/*  91 */         float f5 = (RANDOM.nextFloat() * 0.5F + 0.5F) * f1;
/*     */         
/*  93 */         if (p_192841_1_.shouldRenderFace(EnumFacing.SOUTH)) {
/*     */           
/*  95 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/*  96 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/*  97 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + 1.0D, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/*  98 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + 1.0D, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 101 */         if (p_192841_1_.shouldRenderFace(EnumFacing.NORTH)) {
/*     */           
/* 103 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + 1.0D, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 104 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + 1.0D, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 105 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 106 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 109 */         if (p_192841_1_.shouldRenderFace(EnumFacing.EAST)) {
/*     */           
/* 111 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + 1.0D, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 112 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + 1.0D, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 113 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 114 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 117 */         if (p_192841_1_.shouldRenderFace(EnumFacing.WEST)) {
/*     */           
/* 119 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 120 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 121 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + 1.0D, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 122 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + 1.0D, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 125 */         if (p_192841_1_.shouldRenderFace(EnumFacing.DOWN)) {
/*     */           
/* 127 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 128 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 129 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 130 */           bufferbuilder.pos(p_192841_2_, p_192841_4_, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 133 */         if (p_192841_1_.shouldRenderFace(EnumFacing.UP)) {
/*     */           
/* 135 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + f, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 136 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + f, p_192841_6_ + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
/* 137 */           bufferbuilder.pos(p_192841_2_ + 1.0D, p_192841_4_ + f, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/* 138 */           bufferbuilder.pos(p_192841_2_, p_192841_4_ + f, p_192841_6_).color(f3, f4, f5, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 141 */         tessellator.draw();
/* 142 */         GlStateManager.popMatrix();
/* 143 */         GlStateManager.matrixMode(5888);
/* 144 */         bindTexture(END_SKY_TEXTURE);
/*     */       } 
/*     */       
/* 147 */       GlStateManager.disableBlend();
/* 148 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
/* 149 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
/* 150 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
/* 151 */       GlStateManager.enableLighting();
/*     */       
/* 153 */       if (flag)
/*     */       {
/* 155 */         (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int func_191286_a(double p_191286_1_) {
/*     */     int i;
/* 164 */     if (p_191286_1_ > 36864.0D) {
/*     */       
/* 166 */       i = 1;
/*     */     }
/* 168 */     else if (p_191286_1_ > 25600.0D) {
/*     */       
/* 170 */       i = 3;
/*     */     }
/* 172 */     else if (p_191286_1_ > 16384.0D) {
/*     */       
/* 174 */       i = 5;
/*     */     }
/* 176 */     else if (p_191286_1_ > 9216.0D) {
/*     */       
/* 178 */       i = 7;
/*     */     }
/* 180 */     else if (p_191286_1_ > 4096.0D) {
/*     */       
/* 182 */       i = 9;
/*     */     }
/* 184 */     else if (p_191286_1_ > 1024.0D) {
/*     */       
/* 186 */       i = 11;
/*     */     }
/* 188 */     else if (p_191286_1_ > 576.0D) {
/*     */       
/* 190 */       i = 13;
/*     */     }
/* 192 */     else if (p_191286_1_ > 256.0D) {
/*     */       
/* 194 */       i = 14;
/*     */     }
/*     */     else {
/*     */       
/* 198 */       i = 15;
/*     */     } 
/*     */     
/* 201 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float func_191287_c() {
/* 206 */     return 0.75F;
/*     */   }
/*     */ 
/*     */   
/*     */   private FloatBuffer getBuffer(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
/* 211 */     this.buffer.clear();
/* 212 */     this.buffer.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
/* 213 */     this.buffer.flip();
/* 214 */     return this.buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityEndPortalRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
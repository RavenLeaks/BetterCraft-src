/*     */ package com.TominoCZ.FBP.util;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.vector.FBPVector3d;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPRenderUtil
/*     */ {
/*     */   public static void renderCubeShaded_S(BufferBuilder buf, Vec2f[] par, float f5, float f6, float f7, double scale, FBPVector3d rotVec, int j, int k, float r, float g, float b, float a, boolean cartoon) {
/*  27 */     Tessellator.getInstance().draw();
/*  28 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  29 */     buf.begin(7, FBP.POSITION_TEX_COLOR_LMAP_NORMAL);
/*     */ 
/*     */     
/*  32 */     Tessellator.getInstance().draw();
/*  33 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  34 */     buf.begin(7, FBP.POSITION_TEX_COLOR_LMAP_NORMAL);
/*     */ 
/*     */     
/*  37 */     GlStateManager.enableCull();
/*  38 */     RenderHelper.enableStandardItemLighting();
/*     */ 
/*     */     
/*  41 */     buf.setTranslation(f5, f6, f7);
/*     */     
/*  43 */     putCube_S(buf, par, scale, rotVec, j, k, r, g, b, a, FBP.cartoonMode);
/*     */     
/*  45 */     buf.setTranslation(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */     
/*  48 */     Tessellator.getInstance().draw();
/*  49 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  50 */     buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */     
/*  52 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderCubeShaded_WH(BufferBuilder buf, Vec2f[] par, float f5, float f6, float f7, double width, double height, FBPVector3d rotVec, int j, int k, float r, float g, float b, float a, boolean cartoon) {
/*  60 */     Tessellator.getInstance().draw();
/*  61 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  62 */     buf.begin(7, FBP.POSITION_TEX_COLOR_LMAP_NORMAL);
/*     */ 
/*     */     
/*  65 */     Tessellator.getInstance().draw();
/*  66 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  67 */     buf.begin(7, FBP.POSITION_TEX_COLOR_LMAP_NORMAL);
/*     */ 
/*     */     
/*  70 */     GlStateManager.enableCull();
/*  71 */     RenderHelper.enableStandardItemLighting();
/*     */ 
/*     */     
/*  74 */     buf.setTranslation(f5, f6, f7);
/*     */     
/*  76 */     putCube_WH(buf, par, width, height, rotVec, j, k, r, g, b, a, FBP.cartoonMode);
/*     */     
/*  78 */     buf.setTranslation(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */     
/*  81 */     Tessellator.getInstance().draw();
/*  82 */     (Minecraft.getMinecraft().getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  83 */     buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */     
/*  85 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void putCube_S(BufferBuilder worldRendererIn, Vec2f[] par, double scale, FBPVector3d rotVec, int j, int k, float r, float g, float b, float a, boolean cartoon) {
/*  91 */     float radsX = (float)Math.toRadians(rotVec.x);
/*  92 */     float radsY = (float)Math.toRadians(rotVec.y);
/*  93 */     float radsZ = (float)Math.toRadians(rotVec.z);
/*     */     
/*  95 */     for (int i = 0; i < FBP.CUBE.length; i += 4) {
/*     */       
/*  97 */       Vec3d v1 = FBP.CUBE[i];
/*  98 */       Vec3d v2 = FBP.CUBE[i + 1];
/*  99 */       Vec3d v3 = FBP.CUBE[i + 2];
/* 100 */       Vec3d v4 = FBP.CUBE[i + 3];
/*     */       
/* 102 */       v1 = rotatef_d(v1, radsX, radsY, radsZ);
/* 103 */       v2 = rotatef_d(v2, radsX, radsY, radsZ);
/* 104 */       v3 = rotatef_d(v3, radsX, radsY, radsZ);
/* 105 */       v4 = rotatef_d(v4, radsX, radsY, radsZ);
/*     */       
/* 107 */       Vec3d normal = rotatef_d(FBP.CUBE_NORMALS[i / 4], radsX, radsY, radsZ);
/*     */       
/* 109 */       if (!cartoon) {
/*     */         
/* 111 */         addVt_S(worldRendererIn, scale, v1, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 112 */         addVt_S(worldRendererIn, scale, v2, (par[1]).x, (par[1]).y, j, k, r, g, b, a, normal);
/* 113 */         addVt_S(worldRendererIn, scale, v3, (par[2]).x, (par[2]).y, j, k, r, g, b, a, normal);
/* 114 */         addVt_S(worldRendererIn, scale, v4, (par[3]).x, (par[3]).y, j, k, r, g, b, a, normal);
/*     */       } else {
/*     */         
/* 117 */         addVt_S(worldRendererIn, scale, v1, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 118 */         addVt_S(worldRendererIn, scale, v2, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 119 */         addVt_S(worldRendererIn, scale, v3, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 120 */         addVt_S(worldRendererIn, scale, v4, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void putCube_WH(BufferBuilder worldRendererIn, Vec2f[] par, double width, double height, FBPVector3d rotVec, int j, int k, float r, float g, float b, float a, boolean cartoon) {
/* 128 */     float radsX = (float)Math.toRadians(rotVec.x);
/* 129 */     float radsY = (float)Math.toRadians(rotVec.y);
/* 130 */     float radsZ = (float)Math.toRadians(rotVec.z);
/*     */     
/* 132 */     for (int i = 0; i < FBP.CUBE.length; i += 4) {
/*     */       
/* 134 */       Vec3d v1 = FBP.CUBE[i];
/* 135 */       Vec3d v2 = FBP.CUBE[i + 1];
/* 136 */       Vec3d v3 = FBP.CUBE[i + 2];
/* 137 */       Vec3d v4 = FBP.CUBE[i + 3];
/*     */       
/* 139 */       v1 = rotatef_d(v1, radsX, radsY, radsZ);
/* 140 */       v2 = rotatef_d(v2, radsX, radsY, radsZ);
/* 141 */       v3 = rotatef_d(v3, radsX, radsY, radsZ);
/* 142 */       v4 = rotatef_d(v4, radsX, radsY, radsZ);
/*     */       
/* 144 */       Vec3d normal = rotatef_d(FBP.CUBE_NORMALS[i / 4], radsX, radsY, radsZ);
/*     */       
/* 146 */       if (!cartoon) {
/*     */         
/* 148 */         addVt_WH(worldRendererIn, width, height, v1, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 149 */         addVt_WH(worldRendererIn, width, height, v2, (par[1]).x, (par[1]).y, j, k, r, g, b, a, normal);
/* 150 */         addVt_WH(worldRendererIn, width, height, v3, (par[2]).x, (par[2]).y, j, k, r, g, b, a, normal);
/* 151 */         addVt_WH(worldRendererIn, width, height, v4, (par[3]).x, (par[3]).y, j, k, r, g, b, a, normal);
/*     */       } else {
/*     */         
/* 154 */         addVt_WH(worldRendererIn, width, height, v1, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 155 */         addVt_WH(worldRendererIn, width, height, v2, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 156 */         addVt_WH(worldRendererIn, width, height, v3, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/* 157 */         addVt_WH(worldRendererIn, width, height, v4, (par[0]).x, (par[0]).y, j, k, r, g, b, a, normal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void addVt_S(BufferBuilder worldRendererIn, double scale, Vec3d pos, double u, double v, int j, int k, float r, float g, float b, float a, Vec3d n) {
/* 165 */     worldRendererIn.pos(pos.xCoord * scale, pos.yCoord * scale, pos.zCoord * scale).tex(u, v).color(r, g, b, a).lightmap(j, k)
/* 166 */       .normal((float)n.xCoord, (float)n.yCoord, (float)n.zCoord).endVertex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void addVt_WH(BufferBuilder worldRendererIn, double width, double height, Vec3d pos, double u, double v, int j, int k, float r, float g, float b, float a, Vec3d n) {
/* 172 */     worldRendererIn.pos(pos.xCoord * width, pos.yCoord * height, pos.zCoord * width).tex(u, v).color(r, g, b, a).lightmap(j, k)
/* 173 */       .normal((float)n.xCoord, (float)n.yCoord, (float)n.zCoord).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d rotatef_d(Vec3d vec, float AngleX, float AngleY, float AngleZ) {
/* 178 */     FBPVector3d sin = new FBPVector3d(MathHelper.sin(AngleX), MathHelper.sin(AngleY), MathHelper.sin(AngleZ));
/* 179 */     FBPVector3d cos = new FBPVector3d(MathHelper.cos(AngleX), MathHelper.cos(AngleY), MathHelper.cos(AngleZ));
/*     */     
/* 181 */     vec = new Vec3d(vec.xCoord, vec.yCoord * cos.x - vec.zCoord * sin.x, vec.yCoord * sin.x + vec.zCoord * cos.x);
/* 182 */     vec = new Vec3d(vec.xCoord * cos.z - vec.yCoord * sin.z, vec.xCoord * sin.z + vec.yCoord * cos.z, vec.zCoord);
/* 183 */     vec = new Vec3d(vec.xCoord * cos.y + vec.zCoord * sin.y, vec.yCoord, vec.xCoord * sin.y - vec.zCoord * cos.y);
/*     */     
/* 185 */     return vec;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vector3f rotatef_f(Vector3f pos, float AngleX, float AngleY, float AngleZ, EnumFacing facing) {
/* 190 */     FBPVector3d sin = new FBPVector3d(MathHelper.sin(AngleX), MathHelper.sin(AngleY), MathHelper.sin(AngleZ));
/* 191 */     FBPVector3d cos = new FBPVector3d(MathHelper.cos(AngleX), MathHelper.cos(AngleY), MathHelper.cos(AngleZ));
/*     */     
/* 193 */     FBPVector3d pos1 = new FBPVector3d(pos.x, pos.y, pos.z);
/*     */ 
/*     */     
/* 196 */     if (facing == EnumFacing.EAST) {
/*     */       
/* 198 */       pos1.x--;
/* 199 */     } else if (facing == EnumFacing.WEST) {
/*     */       
/* 201 */       pos1.x++;
/* 202 */     } else if (facing == EnumFacing.SOUTH) {
/*     */       
/* 204 */       pos1.z--;
/* 205 */       pos1.x--;
/*     */     } 
/*     */     
/* 208 */     FBPVector3d pos2 = new FBPVector3d(pos1.x, pos1.y * cos.x - pos1.z * sin.x, pos1.y * sin.x + pos1.z * cos.x);
/* 209 */     pos2 = new FBPVector3d(pos2.x * cos.z - pos2.y * sin.z, pos2.x * sin.z + pos2.y * cos.z, pos2.z);
/* 210 */     pos2 = new FBPVector3d(pos2.x * cos.y + pos2.z * sin.y, pos2.y, pos2.x * sin.y - pos2.z * cos.y);
/*     */     
/* 212 */     if (facing == EnumFacing.EAST) {
/*     */       
/* 214 */       pos2.x++;
/* 215 */     } else if (facing == EnumFacing.WEST) {
/*     */       
/* 217 */       pos2.x--;
/* 218 */     } else if (facing == EnumFacing.SOUTH) {
/*     */       
/* 220 */       pos2.z++;
/* 221 */       pos2.x++;
/*     */     } 
/*     */     
/* 224 */     return new Vector3f((float)pos2.x, (float)pos2.y, (float)pos2.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void markBlockForRender(BlockPos pos) {
/* 230 */     BlockPos bp1 = pos.add(1, 1, 1);
/* 231 */     BlockPos bp2 = pos.add(-1, -1, -1);
/*     */     
/* 233 */     (Minecraft.getMinecraft()).renderGlobal.markBlockRangeForRenderUpdate(bp1.getX(), bp1.getY(), bp1.getZ(), 
/* 234 */         bp2.getX(), bp2.getY(), bp2.getZ());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FB\\util\FBPRenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
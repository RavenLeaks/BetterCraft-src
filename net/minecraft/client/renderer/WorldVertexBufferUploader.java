/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import shadersmod.client.SVertexBuilder;
/*     */ 
/*     */ 
/*     */ public class WorldVertexBufferUploader
/*     */ {
/*     */   public void draw(BufferBuilder vertexBufferIn) {
/*  15 */     if (vertexBufferIn.getVertexCount() > 0) {
/*     */       
/*  17 */       VertexFormat vertexformat = vertexBufferIn.getVertexFormat();
/*  18 */       int i = vertexformat.getNextOffset();
/*  19 */       ByteBuffer bytebuffer = vertexBufferIn.getByteBuffer();
/*  20 */       List<VertexFormatElement> list = vertexformat.getElements();
/*  21 */       boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
/*  22 */       boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
/*     */       
/*  24 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  26 */         VertexFormatElement vertexformatelement = list.get(j);
/*  27 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/*     */         
/*  29 */         if (flag) {
/*     */           
/*  31 */           Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, new Object[] { vertexformat, Integer.valueOf(j), Integer.valueOf(i), bytebuffer });
/*     */         }
/*     */         else {
/*     */           
/*  35 */           int k = vertexformatelement.getType().getGlConstant();
/*  36 */           int l = vertexformatelement.getIndex();
/*  37 */           bytebuffer.position(vertexformat.getOffset(j));
/*     */           
/*  39 */           switch (vertexformatelement$enumusage) {
/*     */             
/*     */             case POSITION:
/*  42 */               GlStateManager.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  43 */               GlStateManager.glEnableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/*  47 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
/*  48 */               GlStateManager.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  49 */               GlStateManager.glEnableClientState(32888);
/*  50 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/*  54 */               GlStateManager.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  55 */               GlStateManager.glEnableClientState(32886);
/*     */               break;
/*     */             
/*     */             case NORMAL:
/*  59 */               GlStateManager.glNormalPointer(k, i, bytebuffer);
/*  60 */               GlStateManager.glEnableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*  65 */       if (vertexBufferIn.isMultiTexture()) {
/*     */         
/*  67 */         vertexBufferIn.drawMultiTexture();
/*     */       }
/*  69 */       else if (Config.isShaders()) {
/*     */         
/*  71 */         SVertexBuilder.drawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount(), vertexBufferIn);
/*     */       }
/*     */       else {
/*     */         
/*  75 */         GlStateManager.glDrawArrays(vertexBufferIn.getDrawMode(), 0, vertexBufferIn.getVertexCount());
/*     */       } 
/*     */       
/*  78 */       int j1 = 0;
/*     */       
/*  80 */       for (int k1 = list.size(); j1 < k1; j1++) {
/*     */         
/*  82 */         VertexFormatElement vertexformatelement1 = list.get(j1);
/*  83 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
/*     */         
/*  85 */         if (flag1) {
/*     */           
/*  87 */           Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, new Object[] { vertexformat, Integer.valueOf(j1), Integer.valueOf(i), bytebuffer });
/*     */         }
/*     */         else {
/*     */           
/*  91 */           int i1 = vertexformatelement1.getIndex();
/*     */           
/*  93 */           switch (vertexformatelement$enumusage1) {
/*     */             
/*     */             case POSITION:
/*  96 */               GlStateManager.glDisableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/* 100 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
/* 101 */               GlStateManager.glDisableClientState(32888);
/* 102 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/* 106 */               GlStateManager.glDisableClientState(32886);
/* 107 */               GlStateManager.resetColor();
/*     */               break;
/*     */             
/*     */             case NORMAL:
/* 111 */               GlStateManager.glDisableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 117 */     vertexBufferIn.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\WorldVertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ public class RenderHelper
/*    */ {
/*  9 */   private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
/* 10 */   private static final Vec3d LIGHT0_POS = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
/* 11 */   private static final Vec3d LIGHT1_POS = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void disableStandardItemLighting() {
/* 18 */     GlStateManager.disableLighting();
/* 19 */     GlStateManager.disableLight(0);
/* 20 */     GlStateManager.disableLight(1);
/* 21 */     GlStateManager.disableColorMaterial();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void enableStandardItemLighting() {
/* 29 */     GlStateManager.enableLighting();
/* 30 */     GlStateManager.enableLight(0);
/* 31 */     GlStateManager.enableLight(1);
/* 32 */     GlStateManager.enableColorMaterial();
/* 33 */     GlStateManager.colorMaterial(1032, 5634);
/* 34 */     GlStateManager.glLight(16384, 4611, setColorBuffer(LIGHT0_POS.xCoord, LIGHT0_POS.yCoord, LIGHT0_POS.zCoord, 0.0D));
/* 35 */     float f = 0.6F;
/* 36 */     GlStateManager.glLight(16384, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
/* 37 */     GlStateManager.glLight(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 38 */     GlStateManager.glLight(16384, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 39 */     GlStateManager.glLight(16385, 4611, setColorBuffer(LIGHT1_POS.xCoord, LIGHT1_POS.yCoord, LIGHT1_POS.zCoord, 0.0D));
/* 40 */     GlStateManager.glLight(16385, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
/* 41 */     GlStateManager.glLight(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 42 */     GlStateManager.glLight(16385, 4610, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 43 */     GlStateManager.shadeModel(7424);
/* 44 */     float f1 = 0.4F;
/* 45 */     GlStateManager.glLightModel(2899, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
/* 53 */     return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
/* 61 */     COLOR_BUFFER.clear();
/* 62 */     COLOR_BUFFER.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
/* 63 */     COLOR_BUFFER.flip();
/* 64 */     return COLOR_BUFFER;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void enableGUIStandardItemLighting() {
/* 72 */     GlStateManager.pushMatrix();
/* 73 */     GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
/* 74 */     GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
/* 75 */     enableStandardItemLighting();
/* 76 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\RenderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
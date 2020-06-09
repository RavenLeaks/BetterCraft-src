/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GLAllocation
/*    */ {
/*    */   public static synchronized int generateDisplayLists(int range) {
/* 17 */     int i = GlStateManager.glGenLists(range);
/*    */     
/* 19 */     if (i == 0) {
/*    */       
/* 21 */       int j = GlStateManager.glGetError();
/* 22 */       String s = "No error code reported";
/*    */       
/* 24 */       if (j != 0)
/*    */       {
/* 26 */         s = GLU.gluErrorString(j);
/*    */       }
/*    */       
/* 29 */       throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + range + ", GL error (" + j + "): " + s);
/*    */     } 
/*    */ 
/*    */     
/* 33 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list, int range) {
/* 39 */     GlStateManager.glDeleteLists(list, range);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list) {
/* 44 */     deleteDisplayLists(list, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ByteBuffer createDirectByteBuffer(int capacity) {
/* 52 */     return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntBuffer createDirectIntBuffer(int capacity) {
/* 60 */     return createDirectByteBuffer(capacity << 2).asIntBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FloatBuffer createDirectFloatBuffer(int capacity) {
/* 69 */     return createDirectByteBuffer(capacity << 2).asFloatBuffer();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\GLAllocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
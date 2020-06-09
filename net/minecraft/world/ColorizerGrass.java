/*    */ package net.minecraft.world;
/*    */ 
/*    */ 
/*    */ public class ColorizerGrass
/*    */ {
/*  6 */   private static int[] grassBuffer = new int[65536];
/*    */ 
/*    */   
/*    */   public static void setGrassBiomeColorizer(int[] grassBufferIn) {
/* 10 */     grassBuffer = grassBufferIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getGrassColor(double temperature, double humidity) {
/* 18 */     humidity *= temperature;
/* 19 */     int i = (int)((1.0D - temperature) * 255.0D);
/* 20 */     int j = (int)((1.0D - humidity) * 255.0D);
/* 21 */     int k = j << 8 | i;
/* 22 */     return (k > grassBuffer.length) ? -65281 : grassBuffer[k];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\ColorizerGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
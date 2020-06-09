/*    */ package net.minecraft.world;
/*    */ 
/*    */ 
/*    */ public class ColorizerFoliage
/*    */ {
/*  6 */   private static int[] foliageBuffer = new int[65536];
/*    */ 
/*    */   
/*    */   public static void setFoliageBiomeColorizer(int[] foliageBufferIn) {
/* 10 */     foliageBuffer = foliageBufferIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColor(double temperature, double humidity) {
/* 18 */     humidity *= temperature;
/* 19 */     int i = (int)((1.0D - temperature) * 255.0D);
/* 20 */     int j = (int)((1.0D - humidity) * 255.0D);
/* 21 */     return foliageBuffer[j << 8 | i];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColorPine() {
/* 29 */     return 6396257;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColorBirch() {
/* 37 */     return 8431445;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getFoliageColorBasic() {
/* 42 */     return 4764952;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\ColorizerFoliage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
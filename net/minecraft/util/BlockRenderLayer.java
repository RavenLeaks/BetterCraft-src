/*    */ package net.minecraft.util;
/*    */ 
/*    */ public enum BlockRenderLayer
/*    */ {
/*  5 */   SOLID("Solid"),
/*  6 */   CUTOUT_MIPPED("Mipped Cutout"),
/*  7 */   CUTOUT("Cutout"),
/*  8 */   TRANSLUCENT("Translucent");
/*    */   
/*    */   private final String layerName;
/*    */ 
/*    */   
/*    */   BlockRenderLayer(String layerNameIn) {
/* 14 */     this.layerName = layerNameIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return this.layerName;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\BlockRenderLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
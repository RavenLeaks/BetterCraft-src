/*    */ package com.TominoCZ.FBP.material;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ 
/*    */ 
/*    */ public class FBPMaterial
/*    */   extends Material
/*    */ {
/*    */   public FBPMaterial() {
/* 11 */     super(MapColor.AIR);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\material\FBPMaterial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
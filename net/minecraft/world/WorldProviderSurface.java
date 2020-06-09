/*    */ package net.minecraft.world;
/*    */ 
/*    */ public class WorldProviderSurface
/*    */   extends WorldProvider
/*    */ {
/*    */   public DimensionType getDimensionType() {
/*  7 */     return DimensionType.OVERWORLD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canDropChunk(int x, int z) {
/* 16 */     return !this.worldObj.isSpawnChunk(x, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldProviderSurface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
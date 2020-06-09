/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ public class WorldSavedDataCallableSave
/*    */   implements Runnable
/*    */ {
/*    */   private final WorldSavedData data;
/*    */   
/*    */   public WorldSavedDataCallableSave(WorldSavedData dataIn) {
/*  9 */     this.data = dataIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 14 */     this.data.markDirty();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\WorldSavedDataCallableSave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
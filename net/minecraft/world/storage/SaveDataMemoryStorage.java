/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SaveDataMemoryStorage
/*    */   extends MapStorage
/*    */ {
/*    */   public SaveDataMemoryStorage() {
/*  9 */     super(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public WorldSavedData getOrLoadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/* 20 */     return this.loadedDataMap.get(dataIdentifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(String dataIdentifier, WorldSavedData data) {
/* 28 */     this.loadedDataMap.put(dataIdentifier, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveAllData() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUniqueDataId(String key) {
/* 43 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\SaveDataMemoryStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
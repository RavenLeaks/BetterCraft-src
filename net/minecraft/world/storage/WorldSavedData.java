/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldSavedData
/*    */ {
/*    */   public final String mapName;
/*    */   private boolean dirty;
/*    */   
/*    */   public WorldSavedData(String name) {
/* 15 */     this.mapName = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract NBTTagCompound writeToNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */   
/*    */   public void markDirty() {
/* 30 */     setDirty(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirty(boolean isDirty) {
/* 38 */     this.dirty = isDirty;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDirty() {
/* 46 */     return this.dirty;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\WorldSavedData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
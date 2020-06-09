/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.storage.WorldSavedData;
/*    */ 
/*    */ public class MapGenStructureData extends WorldSavedData {
/*  8 */   private NBTTagCompound tagCompound = new NBTTagCompound();
/*    */ 
/*    */   
/*    */   public MapGenStructureData(String name) {
/* 12 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 20 */     this.tagCompound = nbt.getCompoundTag("Features");
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 25 */     compound.setTag("Features", (NBTBase)this.tagCompound);
/* 26 */     return compound;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeInstance(NBTTagCompound tagCompoundIn, int chunkX, int chunkZ) {
/* 35 */     this.tagCompound.setTag(formatChunkCoords(chunkX, chunkZ), (NBTBase)tagCompoundIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String formatChunkCoords(int chunkX, int chunkZ) {
/* 40 */     return "[" + chunkX + "," + chunkZ + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getTagCompound() {
/* 45 */     return this.tagCompound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenStructureData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
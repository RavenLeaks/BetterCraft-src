/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*    */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaveHandlerMP
/*    */   implements ISaveHandler
/*    */ {
/*    */   public WorldInfo loadWorldInfo() {
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkSessionLock() throws MinecraftException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfo(WorldInfo worldInformation) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IPlayerFileData getPlayerNBTManager() {
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getMapFileFromName(String mapName) {
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getWorldDirectory() {
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public TemplateManager getStructureTemplateManager() {
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\SaveHandlerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
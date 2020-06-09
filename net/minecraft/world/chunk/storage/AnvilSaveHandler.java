/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.storage.SaveHandler;
/*    */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnvilSaveHandler
/*    */   extends SaveHandler
/*    */ {
/*    */   public AnvilSaveHandler(File p_i46650_1_, String p_i46650_2_, boolean p_i46650_3_, DataFixer dataFixerIn) {
/* 18 */     super(p_i46650_1_, p_i46650_2_, p_i46650_3_, dataFixerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 26 */     File file1 = getWorldDirectory();
/*    */     
/* 28 */     if (provider instanceof net.minecraft.world.WorldProviderHell) {
/*    */       
/* 30 */       File file3 = new File(file1, "DIM-1");
/* 31 */       file3.mkdirs();
/* 32 */       return new AnvilChunkLoader(file3, this.dataFixer);
/*    */     } 
/* 34 */     if (provider instanceof net.minecraft.world.WorldProviderEnd) {
/*    */       
/* 36 */       File file2 = new File(file1, "DIM1");
/* 37 */       file2.mkdirs();
/* 38 */       return new AnvilChunkLoader(file2, this.dataFixer);
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return new AnvilChunkLoader(file1, this.dataFixer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, @Nullable NBTTagCompound tagCompound) {
/* 51 */     worldInformation.setSaveVersion(19133);
/* 52 */     super.saveWorldInfoWithPlayer(worldInformation, tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {
/*    */     try {
/* 62 */       ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
/*    */     }
/* 64 */     catch (InterruptedException interruptedexception) {
/*    */       
/* 66 */       interruptedexception.printStackTrace();
/*    */     } 
/*    */     
/* 69 */     RegionFileCache.clearRegionFileReferences();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\AnvilSaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
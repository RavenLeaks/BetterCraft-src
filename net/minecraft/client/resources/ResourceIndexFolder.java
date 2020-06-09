/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ResourceIndexFolder
/*    */   extends ResourceIndex
/*    */ {
/*    */   private final File baseDir;
/*    */   
/*    */   public ResourceIndexFolder(File folder) {
/* 12 */     this.baseDir = folder;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getFile(ResourceLocation location) {
/* 17 */     return new File(this.baseDir, location.toString().replace(':', '/'));
/*    */   }
/*    */ 
/*    */   
/*    */   public File getPackMcmeta() {
/* 22 */     return new File(this.baseDir, "pack.mcmeta");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourceIndexFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
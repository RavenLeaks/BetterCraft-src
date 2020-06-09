/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import optifine.StrUtils;
/*    */ 
/*    */ 
/*    */ public class ShaderPackFolder
/*    */   implements IShaderPack
/*    */ {
/*    */   protected File packFile;
/*    */   
/*    */   public ShaderPackFolder(String name, File file) {
/* 16 */     this.packFile = file;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 27 */       String s = StrUtils.removePrefixSuffix(resName, "/", "/");
/* 28 */       File file1 = new File(this.packFile, s);
/* 29 */       return !file1.exists() ? null : new BufferedInputStream(new FileInputStream(file1));
/*    */     }
/* 31 */     catch (Exception var4) {
/*    */       
/* 33 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 39 */     File file1 = new File(this.packFile, name.substring(1));
/*    */     
/* 41 */     if (!file1.exists())
/*    */     {
/* 43 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 47 */     return file1.isDirectory();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderPackFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
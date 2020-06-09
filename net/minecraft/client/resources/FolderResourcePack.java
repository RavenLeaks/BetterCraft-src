/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.CharMatcher;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.Util;
/*    */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*    */ 
/*    */ public class FolderResourcePack extends AbstractResourcePack {
/* 18 */   private static final boolean field_191386_b = (Util.getOSType() == Util.EnumOS.WINDOWS);
/* 19 */   private static final CharMatcher field_191387_c = CharMatcher.is('\\');
/*    */ 
/*    */   
/*    */   public FolderResourcePack(File resourcePackFileIn) {
/* 23 */     super(resourcePackFileIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static boolean func_191384_a(File p_191384_0_, String p_191384_1_) throws IOException {
/* 28 */     String s = p_191384_0_.getCanonicalPath();
/*    */     
/* 30 */     if (field_191386_b)
/*    */     {
/* 32 */       s = field_191387_c.replaceFrom(s, '/');
/*    */     }
/*    */     
/* 35 */     return s.endsWith(p_191384_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 40 */     File file1 = func_191385_d(name);
/*    */     
/* 42 */     if (file1 == null)
/*    */     {
/* 44 */       throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
/*    */     }
/*    */ 
/*    */     
/* 48 */     return new BufferedInputStream(new FileInputStream(file1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean hasResourceName(String name) {
/* 54 */     return (func_191385_d(name) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private File func_191385_d(String p_191385_1_) {
/*    */     try {
/* 62 */       File file1 = new File(this.resourcePackFile, p_191385_1_);
/*    */       
/* 64 */       if (file1.isFile() && func_191384_a(file1, p_191385_1_))
/*    */       {
/* 66 */         return file1;
/*    */       }
/*    */     }
/* 69 */     catch (IOException iOException) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 79 */     Set<String> set = Sets.newHashSet();
/* 80 */     File file1 = new File(this.resourcePackFile, "assets/");
/*    */     
/* 82 */     if (file1.isDirectory()) {
/*    */       byte b; int i; File[] arrayOfFile;
/* 84 */       for (i = (arrayOfFile = file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)).length, b = 0; b < i; ) { File file2 = arrayOfFile[b];
/*    */         
/* 86 */         String s = getRelativeName(file1, file2);
/*    */         
/* 88 */         if (s.equals(s.toLowerCase(Locale.ROOT))) {
/*    */           
/* 90 */           set.add(s.substring(0, s.length() - 1));
/*    */         }
/*    */         else {
/*    */           
/* 94 */           logNameNotLowercase(s);
/*    */         } 
/*    */         b++; }
/*    */     
/*    */     } 
/* 99 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\FolderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
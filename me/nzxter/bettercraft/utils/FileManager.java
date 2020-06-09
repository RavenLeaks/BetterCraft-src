/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.file.CopyOption;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.StandardCopyOption;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.json.simple.parser.JSONParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileManager
/*    */ {
/* 20 */   private static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static JSONParser parser;
/*    */   
/* 24 */   File mcDataDir = mc.mcDataDir;
/*    */   
/* 26 */   public static File altsDir = new File(new File("BetterCraft"), "alts.txt");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void createFiles() throws IOException {
/* 32 */     if (!Files.exists(Paths.get("BetterCraft", new String[0]), new java.nio.file.LinkOption[0])) {
/* 33 */       Files.createDirectory(Paths.get("BetterCraft", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*    */ 
/*    */       
/* 36 */       if (!Files.exists(Paths.get(altsDir.toURI()), new java.nio.file.LinkOption[0])) {
/* 37 */         Files.createFile(Paths.get(altsDir.toURI()), (FileAttribute<?>[])new FileAttribute[0]);
/*    */       }
/*    */       
/* 40 */       if (!Files.exists(Paths.get("BetterCraft/instantcrasher.exe", new String[0]), new java.nio.file.LinkOption[0])) {
/* 41 */         InputStream inputStream = FileManager.class.getResourceAsStream("/me/nzxter/bettercraft/mods/crasher/instantcrasher.exe");
/* 42 */         Files.copy(inputStream, Paths.get("BetterCraft/instantcrasher.exe", new String[0]), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\FileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
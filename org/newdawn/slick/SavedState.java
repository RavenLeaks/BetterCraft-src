/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import org.newdawn.slick.muffin.FileMuffin;
/*     */ import org.newdawn.slick.muffin.Muffin;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SavedState
/*     */ {
/*     */   private String fileName;
/*     */   private Muffin muffin;
/*  25 */   private HashMap numericData = new HashMap<>();
/*     */   
/*  27 */   private HashMap stringData = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SavedState(String fileName) throws SlickException {
/*  38 */     this.fileName = fileName;
/*     */     
/*  40 */     if (!isWebstartAvailable())
/*     */     {
/*     */ 
/*     */       
/*  44 */       this.muffin = (Muffin)new FileMuffin();
/*     */     }
/*     */     
/*     */     try {
/*  48 */       load();
/*  49 */     } catch (IOException e) {
/*  50 */       throw new SlickException("Failed to load state on startup", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumber(String nameOfField) {
/*  61 */     return getNumber(nameOfField, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumber(String nameOfField, double defaultValue) {
/*  72 */     Double value = (Double)this.numericData.get(nameOfField);
/*     */     
/*  74 */     if (value == null) {
/*  75 */       return defaultValue;
/*     */     }
/*     */     
/*  78 */     return value.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumber(String nameOfField, double value) {
/*  89 */     this.numericData.put(nameOfField, new Double(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String nameOfField) {
/*  99 */     return getString(nameOfField, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String nameOfField, String defaultValue) {
/* 110 */     String value = (String)this.stringData.get(nameOfField);
/*     */     
/* 112 */     if (value == null) {
/* 113 */       return defaultValue;
/*     */     }
/*     */     
/* 116 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String nameOfField, String value) {
/* 127 */     this.stringData.put(nameOfField, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() throws IOException {
/* 136 */     this.muffin.saveFile(this.numericData, String.valueOf(this.fileName) + "_Number");
/* 137 */     this.muffin.saveFile(this.stringData, String.valueOf(this.fileName) + "_String");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/* 146 */     this.numericData = this.muffin.loadFile(String.valueOf(this.fileName) + "_Number");
/* 147 */     this.stringData = this.muffin.loadFile(String.valueOf(this.fileName) + "_String");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 154 */     this.numericData.clear();
/* 155 */     this.stringData.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWebstartAvailable() {
/*     */     try {
/* 165 */       Class.forName("javax.jnlp.ServiceManager");
/*     */       
/* 167 */       Log.info("Webstart detected using Muffins");
/* 168 */     } catch (Exception e) {
/* 169 */       Log.info("Using Local File System");
/* 170 */       return false;
/*     */     } 
/* 172 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\SavedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class JsonException
/*    */   extends IOException
/*    */ {
/* 11 */   private final List<Entry> entries = Lists.newArrayList();
/*    */   
/*    */   private final String message;
/*    */   
/*    */   public JsonException(String messageIn) {
/* 16 */     this.entries.add(new Entry(null));
/* 17 */     this.message = messageIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonException(String messageIn, Throwable cause) {
/* 22 */     super(cause);
/* 23 */     this.entries.add(new Entry(null));
/* 24 */     this.message = messageIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void prependJsonKey(String p_151380_1_) {
/* 29 */     ((Entry)this.entries.get(0)).addJsonKey(p_151380_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFilenameAndFlush(String p_151381_1_) {
/* 34 */     (this.entries.get(0)).filename = p_151381_1_;
/* 35 */     this.entries.add(0, new Entry(null));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 40 */     return "Invalid " + this.entries.get(this.entries.size() - 1) + ": " + this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   public static JsonException forException(Exception p_151379_0_) {
/* 45 */     if (p_151379_0_ instanceof JsonException)
/*    */     {
/* 47 */       return (JsonException)p_151379_0_;
/*    */     }
/*    */ 
/*    */     
/* 51 */     String s = p_151379_0_.getMessage();
/*    */     
/* 53 */     if (p_151379_0_ instanceof java.io.FileNotFoundException)
/*    */     {
/* 55 */       s = "File not found";
/*    */     }
/*    */     
/* 58 */     return new JsonException(s, p_151379_0_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Entry
/*    */   {
/*    */     private String filename;
/*    */ 
/*    */     
/* 69 */     private final List<String> jsonKeys = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */     
/*    */     private void addJsonKey(String p_151373_1_) {
/* 74 */       this.jsonKeys.add(0, p_151373_1_);
/*    */     }
/*    */ 
/*    */     
/*    */     public String getJsonKeys() {
/* 79 */       return StringUtils.join(this.jsonKeys, "->");
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 84 */       if (this.filename != null)
/*    */       {
/* 86 */         return this.jsonKeys.isEmpty() ? this.filename : (String.valueOf(this.filename) + " " + getJsonKeys());
/*    */       }
/*    */ 
/*    */       
/* 90 */       return this.jsonKeys.isEmpty() ? "(Unknown file)" : ("(Unknown file) " + getJsonKeys());
/*    */     }
/*    */     
/*    */     private Entry() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\JsonException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
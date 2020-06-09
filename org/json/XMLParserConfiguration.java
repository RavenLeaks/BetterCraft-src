/*     */ package org.json;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLParserConfiguration
/*     */ {
/*  33 */   public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
/*     */   
/*  35 */   public static final XMLParserConfiguration KEEP_STRINGS = new XMLParserConfiguration(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean keepStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String cDataTagName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean convertNilAttributeToNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration() {
/*  57 */     this(false, "content", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration(boolean keepStrings) {
/*  66 */     this(keepStrings, "content", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration(String cDataTagName) {
/*  77 */     this(false, cDataTagName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration(boolean keepStrings, String cDataTagName) {
/*  88 */     this.keepStrings = keepStrings;
/*  89 */     this.cDataTagName = cDataTagName;
/*  90 */     this.convertNilAttributeToNull = false;
/*     */   }
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
/*     */   public XMLParserConfiguration(boolean keepStrings, String cDataTagName, boolean convertNilAttributeToNull) {
/* 103 */     this.keepStrings = keepStrings;
/* 104 */     this.cDataTagName = cDataTagName;
/* 105 */     this.convertNilAttributeToNull = convertNilAttributeToNull;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\XMLParserConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ShaderParser
/*     */ {
/*   8 */   public static Pattern PATTERN_UNIFORM = Pattern.compile("\\s*uniform\\s+\\w+\\s+(\\w+).*");
/*   9 */   public static Pattern PATTERN_COMMENT = Pattern.compile("\\s*/\\*\\s+([A-Z]+):(\\S+)\\s+\\*/.*");
/*  10 */   public static Pattern PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  11 */   public static Pattern PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
/*  12 */   public static Pattern PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
/*  13 */   public static Pattern PATTERN_COMPOSITE_FSH = Pattern.compile(".*composite[0-9]?\\.fsh");
/*  14 */   public static Pattern PATTERN_FINAL_FSH = Pattern.compile(".*final\\.fsh");
/*  15 */   public static Pattern PATTERN_DRAW_BUFFERS = Pattern.compile("[0-7N]*");
/*     */ 
/*     */   
/*     */   public static ShaderLine parseLine(String line) {
/*  19 */     Matcher matcher = PATTERN_UNIFORM.matcher(line);
/*     */     
/*  21 */     if (matcher.matches())
/*     */     {
/*  23 */       return new ShaderLine(1, matcher.group(1), "", line);
/*     */     }
/*     */ 
/*     */     
/*  27 */     Matcher matcher1 = PATTERN_COMMENT.matcher(line);
/*     */     
/*  29 */     if (matcher1.matches())
/*     */     {
/*  31 */       return new ShaderLine(2, matcher1.group(1), matcher1.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  35 */     Matcher matcher2 = PATTERN_CONST_INT.matcher(line);
/*     */     
/*  37 */     if (matcher2.matches())
/*     */     {
/*  39 */       return new ShaderLine(3, matcher2.group(1), matcher2.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  43 */     Matcher matcher3 = PATTERN_CONST_FLOAT.matcher(line);
/*     */     
/*  45 */     if (matcher3.matches())
/*     */     {
/*  47 */       return new ShaderLine(4, matcher3.group(1), matcher3.group(2), line);
/*     */     }
/*     */ 
/*     */     
/*  51 */     Matcher matcher4 = PATTERN_CONST_BOOL.matcher(line);
/*  52 */     return matcher4.matches() ? new ShaderLine(5, matcher4.group(1), matcher4.group(2), line) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndex(String uniform, String prefix, int minIndex, int maxIndex) {
/*  61 */     if (uniform.length() != prefix.length() + 1)
/*     */     {
/*  63 */       return -1;
/*     */     }
/*  65 */     if (!uniform.startsWith(prefix))
/*     */     {
/*  67 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  71 */     int i = uniform.charAt(prefix.length()) - 48;
/*  72 */     return (i >= minIndex && i <= maxIndex) ? i : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getShadowDepthIndex(String uniform) {
/*  78 */     byte b0 = -1;
/*     */     
/*  80 */     switch (uniform.hashCode()) {
/*     */       
/*     */       case -903579360:
/*  83 */         if (uniform.equals("shadow"))
/*     */         {
/*  85 */           b0 = 0;
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1235669239:
/*  91 */         if (uniform.equals("watershadow"))
/*     */         {
/*  93 */           b0 = 1;
/*     */         }
/*     */         break;
/*     */     } 
/*  97 */     switch (b0) {
/*     */       
/*     */       case 0:
/* 100 */         return 0;
/*     */       
/*     */       case 1:
/* 103 */         return 1;
/*     */     } 
/*     */     
/* 106 */     return getIndex(uniform, "shadowtex", 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getShadowColorIndex(String uniform) {
/* 112 */     byte b0 = -1;
/*     */     
/* 114 */     switch (uniform.hashCode()) {
/*     */       
/*     */       case -1560188349:
/* 117 */         if (uniform.equals("shadowcolor"))
/*     */         {
/* 119 */           b0 = 0;
/*     */         }
/*     */         break;
/*     */     } 
/* 123 */     switch (b0) {
/*     */       
/*     */       case 0:
/* 126 */         return 0;
/*     */     } 
/*     */     
/* 129 */     return getIndex(uniform, "shadowcolor", 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepthIndex(String uniform) {
/* 136 */     return getIndex(uniform, "depthtex", 0, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getColorIndex(String uniform) {
/* 141 */     int i = getIndex(uniform, "gaux", 1, 4);
/* 142 */     return (i > 0) ? (i + 3) : getIndex(uniform, "colortex", 4, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isComposite(String filename) {
/* 147 */     return PATTERN_COMPOSITE_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFinal(String filename) {
/* 152 */     return PATTERN_FINAL_FSH.matcher(filename).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidDrawBuffers(String str) {
/* 157 */     return PATTERN_DRAW_BUFFERS.matcher(str).matches();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
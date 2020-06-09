/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import optifine.Config;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ public class ShaderOptionVariable
/*     */   extends ShaderOption
/*     */ {
/*  12 */   private static final Pattern PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");
/*     */ 
/*     */   
/*     */   public ShaderOptionVariable(String name, String description, String value, String[] values, String path) {
/*  16 */     super(name, description, value, values, value, path);
/*  17 */     setVisible(((getValues()).length > 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  22 */     return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  27 */     String s = Shaders.translate("prefix." + getName(), "");
/*  28 */     String s1 = super.getValueText(val);
/*  29 */     String s2 = Shaders.translate("suffix." + getName(), "");
/*  30 */     String s3 = String.valueOf(s) + s1 + s2;
/*  31 */     return s3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  36 */     String s = val.toLowerCase();
/*  37 */     return (!s.equals("false") && !s.equals("off")) ? "§a" : "§c";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  42 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  44 */     if (!matcher.matches())
/*     */     {
/*  46 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  50 */     String s = matcher.group(1);
/*  51 */     return s.matches(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  57 */     Matcher matcher = PATTERN_VARIABLE.matcher(line);
/*     */     
/*  59 */     if (!matcher.matches())
/*     */     {
/*  61 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  65 */     String s = matcher.group(1);
/*  66 */     String s1 = matcher.group(2);
/*  67 */     String s2 = matcher.group(3);
/*  68 */     String s3 = StrUtils.getSegment(s2, "[", "]");
/*     */     
/*  70 */     if (s3 != null && s3.length() > 0)
/*     */     {
/*  72 */       s2 = s2.replace(s3, "").trim();
/*     */     }
/*     */     
/*  75 */     String[] astring = parseValues(s1, s3);
/*     */     
/*  77 */     if (s != null && s.length() > 0) {
/*     */       
/*  79 */       path = StrUtils.removePrefix(path, "/shaders/");
/*  80 */       ShaderOption shaderoption = new ShaderOptionVariable(s, s2, s1, astring, path);
/*  81 */       return shaderoption;
/*     */     } 
/*     */ 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] parseValues(String value, String valuesStr) {
/*  92 */     String[] astring = { value };
/*     */     
/*  94 */     if (valuesStr == null)
/*     */     {
/*  96 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 100 */     valuesStr = valuesStr.trim();
/* 101 */     valuesStr = StrUtils.removePrefix(valuesStr, "[");
/* 102 */     valuesStr = StrUtils.removeSuffix(valuesStr, "]");
/* 103 */     valuesStr = valuesStr.trim();
/*     */     
/* 105 */     if (valuesStr.length() <= 0)
/*     */     {
/* 107 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 111 */     String[] astring1 = Config.tokenize(valuesStr, " ");
/*     */     
/* 113 */     if (astring1.length <= 0)
/*     */     {
/* 115 */       return astring;
/*     */     }
/*     */ 
/*     */     
/* 119 */     if (!Arrays.<String>asList(astring1).contains(value))
/*     */     {
/* 121 */       astring1 = (String[])Config.addObjectToArray((Object[])astring1, value, 0);
/*     */     }
/*     */     
/* 124 */     return astring1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import optifine.StrUtils;
/*    */ 
/*    */ public class ShaderOptionVariableConst
/*    */   extends ShaderOptionVariable
/*    */ {
/* 10 */   private String type = null;
/* 11 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */   
/*    */   public ShaderOptionVariableConst(String name, String type, String description, String value, String[] values, String path) {
/* 15 */     super(name, description, value, values, path);
/* 16 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 21 */     return "const " + this.type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 26 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 28 */     if (!matcher.matches())
/*    */     {
/* 30 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 34 */     String s = matcher.group(2);
/* 35 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 41 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 43 */     if (!matcher.matches())
/*    */     {
/* 45 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 49 */     String s = matcher.group(1);
/* 50 */     String s1 = matcher.group(2);
/* 51 */     String s2 = matcher.group(3);
/* 52 */     String s3 = matcher.group(4);
/* 53 */     String s4 = StrUtils.getSegment(s3, "[", "]");
/*    */     
/* 55 */     if (s4 != null && s4.length() > 0)
/*    */     {
/* 57 */       s3 = s3.replace(s4, "").trim();
/*    */     }
/*    */     
/* 60 */     String[] astring = parseValues(s2, s4);
/*    */     
/* 62 */     if (s1 != null && s1.length() > 0) {
/*    */       
/* 64 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 65 */       ShaderOption shaderoption = new ShaderOptionVariableConst(s1, s, s3, s2, astring, path);
/* 66 */       return shaderoption;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionVariableConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
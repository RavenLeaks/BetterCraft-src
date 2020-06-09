/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import optifine.StrUtils;
/*    */ 
/*    */ public class ShaderOptionSwitchConst
/*    */   extends ShaderOptionSwitch
/*    */ {
/* 10 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */   
/*    */   public ShaderOptionSwitchConst(String name, String description, String value, String path) {
/* 14 */     super(name, description, value, path);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 19 */     return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 24 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 26 */     if (!matcher.matches())
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     String s = matcher.group(1);
/* 33 */     String s1 = matcher.group(2);
/* 34 */     String s2 = matcher.group(3);
/*    */     
/* 36 */     if (s != null && s.length() > 0) {
/*    */       
/* 38 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 39 */       ShaderOption shaderoption = new ShaderOptionSwitchConst(s, s2, s1, path);
/* 40 */       shaderoption.setVisible(false);
/* 41 */       return shaderoption;
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 52 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 54 */     if (!matcher.matches())
/*    */     {
/* 56 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 60 */     String s = matcher.group(1);
/* 61 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkUsed() {
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionSwitchConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
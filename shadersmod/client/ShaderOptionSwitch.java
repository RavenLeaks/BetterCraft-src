/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import optifine.Config;
/*     */ import optifine.Lang;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ public class ShaderOptionSwitch
/*     */   extends ShaderOption
/*     */ {
/*  12 */   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
/*  13 */   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
/*     */ 
/*     */   
/*     */   public ShaderOptionSwitch(String name, String description, String value, String path) {
/*  17 */     super(name, description, value, new String[] { "false", "true" }, value, path);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  22 */     return isTrue(getValue()) ? ("#define " + getName() + " // Shader option ON") : ("//#define " + getName() + " // Shader option OFF");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  27 */     String s = super.getValueText(val);
/*     */     
/*  29 */     if (s != val)
/*     */     {
/*  31 */       return s;
/*     */     }
/*     */ 
/*     */     
/*  35 */     return isTrue(val) ? Lang.getOn() : Lang.getOff();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  41 */     return isTrue(val) ? "§a" : "§c";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  46 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  48 */     if (!matcher.matches())
/*     */     {
/*  50 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  54 */     String s = matcher.group(1);
/*  55 */     String s1 = matcher.group(2);
/*  56 */     String s2 = matcher.group(3);
/*     */     
/*  58 */     if (s1 != null && s1.length() > 0) {
/*     */       
/*  60 */       boolean flag = Config.equals(s, "//");
/*  61 */       boolean flag1 = !flag;
/*  62 */       path = StrUtils.removePrefix(path, "/shaders/");
/*  63 */       ShaderOption shaderoption = new ShaderOptionSwitch(s1, s2, String.valueOf(flag1), path);
/*  64 */       return shaderoption;
/*     */     } 
/*     */ 
/*     */     
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  75 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  77 */     if (!matcher.matches())
/*     */     {
/*  79 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  83 */     String s = matcher.group(2);
/*  84 */     return s.matches(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/*  95 */     Matcher matcher = PATTERN_IFDEF.matcher(line);
/*     */     
/*  97 */     if (matcher.matches()) {
/*     */       
/*  99 */       String s = matcher.group(2);
/*     */       
/* 101 */       if (s.equals(getName()))
/*     */       {
/* 103 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(String val) {
/* 112 */     return Boolean.valueOf(val).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
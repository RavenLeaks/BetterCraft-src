/*     */ package shadersmod.client;
/*     */ 
/*     */ 
/*     */ public class ShaderLine
/*     */ {
/*     */   private int type;
/*     */   private String name;
/*     */   private String value;
/*     */   private String line;
/*     */   public static final int TYPE_UNIFORM = 1;
/*     */   public static final int TYPE_COMMENT = 2;
/*     */   public static final int TYPE_CONST_INT = 3;
/*     */   public static final int TYPE_CONST_FLOAT = 4;
/*     */   public static final int TYPE_CONST_BOOL = 5;
/*     */   
/*     */   public ShaderLine(int type, String name, String value, String line) {
/*  17 */     this.type = type;
/*  18 */     this.name = name;
/*  19 */     this.value = value;
/*  20 */     this.line = line;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/*  25 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  30 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  35 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUniform() {
/*  40 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUniform(String name) {
/*  45 */     return (isUniform() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComment() {
/*  50 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstInt() {
/*  55 */     return (this.type == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstFloat() {
/*  60 */     return (this.type == 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool() {
/*  65 */     return (this.type == 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComment(String name) {
/*  70 */     return (isComment() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComment(String name, String value) {
/*  75 */     return (isComment(name) && value.equals(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstInt(String name) {
/*  80 */     return (isConstInt() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstIntSuffix(String suffix) {
/*  85 */     return (isConstInt() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstFloat(String name) {
/*  90 */     return (isConstFloat() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name) {
/*  95 */     return (isConstBool() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix) {
/* 100 */     return (isConstBool() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix, boolean val) {
/* 105 */     return (isConstBoolSuffix(suffix) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2) {
/* 110 */     return !(!isConstBool(name1) && !isConstBool(name2));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3) {
/* 115 */     return !(!isConstBool(name1) && !isConstBool(name2) && !isConstBool(name3));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name, boolean val) {
/* 120 */     return (isConstBool(name) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, boolean val) {
/* 125 */     return (isConstBool(name1, name2) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3, boolean val) {
/* 130 */     return (isConstBool(name1, name2, name3) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueInt() {
/*     */     try {
/* 137 */       return Integer.parseInt(this.value);
/*     */     }
/* 139 */     catch (NumberFormatException var2) {
/*     */       
/* 141 */       throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValueFloat() {
/*     */     try {
/* 149 */       return Float.parseFloat(this.value);
/*     */     }
/* 151 */     catch (NumberFormatException var2) {
/*     */       
/* 153 */       throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getValueBool() {
/* 159 */     String s = this.value.toLowerCase();
/*     */     
/* 161 */     if (!s.equals("true") && !s.equals("false"))
/*     */     {
/* 163 */       throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
/*     */     }
/*     */ 
/*     */     
/* 167 */     return Boolean.valueOf(this.value).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import optifine.Config;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ 
/*     */ public abstract class ShaderOption
/*     */ {
/*  11 */   private String name = null;
/*  12 */   private String description = null;
/*  13 */   private String value = null;
/*  14 */   private String[] values = null;
/*  15 */   private String valueDefault = null;
/*  16 */   private String[] paths = null;
/*     */   
/*     */   private boolean enabled = true;
/*     */   private boolean visible = true;
/*     */   public static final String COLOR_GREEN = "§a";
/*     */   public static final String COLOR_RED = "§c";
/*     */   public static final String COLOR_BLUE = "§9";
/*     */   
/*     */   public ShaderOption(String name, String description, String value, String[] values, String valueDefault, String path) {
/*  25 */     this.name = name;
/*  26 */     this.description = description;
/*  27 */     this.value = value;
/*  28 */     this.values = values;
/*  29 */     this.valueDefault = valueDefault;
/*     */     
/*  31 */     if (path != null)
/*     */     {
/*  33 */       this.paths = new String[] { path };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  39 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  44 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionText() {
/*  49 */     String s = Config.normalize(this.description);
/*  50 */     s = StrUtils.removePrefix(s, "//");
/*  51 */     s = Shaders.translate("option." + getName() + ".comment", s);
/*  52 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/*  57 */     this.description = description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  62 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setValue(String value) {
/*  67 */     int i = getIndex(value, this.values);
/*     */     
/*  69 */     if (i < 0)
/*     */     {
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  75 */     this.value = value;
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueDefault() {
/*  82 */     return this.valueDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetValue() {
/*  87 */     this.value = this.valueDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  92 */     int i = getIndex(this.value, this.values);
/*     */     
/*  94 */     if (i >= 0) {
/*     */       
/*  96 */       i = (i + 1) % this.values.length;
/*  97 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void prevValue() {
/* 103 */     int i = getIndex(this.value, this.values);
/*     */     
/* 105 */     if (i >= 0) {
/*     */       
/* 107 */       i = (i - 1 + this.values.length) % this.values.length;
/* 108 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(String str, String[] strs) {
/* 114 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 116 */       String s = strs[i];
/*     */       
/* 118 */       if (s.equals(str))
/*     */       {
/* 120 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getPaths() {
/* 129 */     return this.paths;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPaths(String[] newPaths) {
/* 134 */     List<String> list = Arrays.asList(this.paths);
/*     */     
/* 136 */     for (int i = 0; i < newPaths.length; i++) {
/*     */       
/* 138 */       String s = newPaths[i];
/*     */       
/* 140 */       if (!list.contains(s))
/*     */       {
/* 142 */         this.paths = (String[])Config.addObjectToArray((Object[])this.paths, s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 149 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 154 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChanged() {
/* 159 */     return !Config.equals(this.value, this.valueDefault);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 164 */     return this.visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 169 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidValue(String val) {
/* 174 */     return (getIndex(val, this.values) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 179 */     return Shaders.translate("option." + this.name, this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 184 */     return Shaders.translate("value." + this.name + "." + val, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 189 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getValues() {
/* 214 */     return (String[])this.values.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIndexNormalized() {
/* 219 */     if (this.values.length <= 1)
/*     */     {
/* 221 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 225 */     int i = getIndex(this.value, this.values);
/*     */     
/* 227 */     if (i < 0)
/*     */     {
/* 229 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 233 */     float f = 1.0F * i / (this.values.length - 1.0F);
/* 234 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndexNormalized(float f) {
/* 241 */     if (this.values.length > 1) {
/*     */       
/* 243 */       f = Config.limit(f, 0.0F, 1.0F);
/* 244 */       int i = Math.round(f * (this.values.length - 1));
/* 245 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 251 */     return this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString((Object[])this.paths);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
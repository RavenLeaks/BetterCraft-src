/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import optifine.Lang;
/*     */ 
/*     */ public class ShaderOptionProfile
/*     */   extends ShaderOption
/*     */ {
/*  10 */   private ShaderProfile[] profiles = null;
/*  11 */   private ShaderOption[] options = null;
/*     */   
/*     */   private static final String NAME_PROFILE = "<profile>";
/*     */   private static final String VALUE_CUSTOM = "<custom>";
/*     */   
/*     */   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
/*  17 */     super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
/*  18 */     this.profiles = profiles;
/*  19 */     this.options = options;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  24 */     super.nextValue();
/*     */     
/*  26 */     if (getValue().equals("<custom>"))
/*     */     {
/*  28 */       super.nextValue();
/*     */     }
/*     */     
/*  31 */     applyProfileOptions();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProfile() {
/*  36 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  38 */     if (shaderprofile == null || !ShaderUtils.matchProfile(shaderprofile, this.options, false)) {
/*     */       
/*  40 */       String s = detectProfileName(this.profiles, this.options);
/*  41 */       setValue(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyProfileOptions() {
/*  47 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  49 */     if (shaderprofile != null) {
/*     */       
/*  51 */       String[] astring = shaderprofile.getOptions();
/*     */       
/*  53 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  55 */         String s = astring[i];
/*  56 */         ShaderOption shaderoption = getOption(s);
/*     */         
/*  58 */         if (shaderoption != null) {
/*     */           
/*  60 */           String s1 = shaderprofile.getValue(s);
/*  61 */           shaderoption.setValue(s1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderOption getOption(String name) {
/*  69 */     for (int i = 0; i < this.options.length; i++) {
/*     */       
/*  71 */       ShaderOption shaderoption = this.options[i];
/*     */       
/*  73 */       if (shaderoption.getName().equals(name))
/*     */       {
/*  75 */         return shaderoption;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderProfile getProfile(String name) {
/*  84 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/*  86 */       ShaderProfile shaderprofile = this.profiles[i];
/*     */       
/*  88 */       if (shaderprofile.getName().equals(name))
/*     */       {
/*  90 */         return shaderprofile;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameText() {
/*  99 */     return Lang.get("of.shaders.profile");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 104 */     return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 109 */     return val.equals("<custom>") ? "§c" : "§a";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
/* 114 */     return detectProfileName(profs, opts, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 119 */     ShaderProfile shaderprofile = ShaderUtils.detectProfile(profs, opts, def);
/* 120 */     return (shaderprofile == null) ? "<custom>" : shaderprofile.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getProfileNames(ShaderProfile[] profs) {
/* 125 */     List<String> list = new ArrayList<>();
/*     */     
/* 127 */     for (int i = 0; i < profs.length; i++) {
/*     */       
/* 129 */       ShaderProfile shaderprofile = profs[i];
/* 130 */       list.add(shaderprofile.getName());
/*     */     } 
/*     */     
/* 133 */     list.add("<custom>");
/* 134 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 135 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
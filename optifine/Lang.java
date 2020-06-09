/*     */ package optifine;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class Lang
/*     */ {
/*  19 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  20 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  24 */     Map map = I18n.getLocaleProperties();
/*  25 */     List<String> list = new ArrayList<>();
/*  26 */     String s = "optifine/lang/";
/*  27 */     String s1 = "en_us";
/*  28 */     String s2 = ".lang";
/*  29 */     list.add(String.valueOf(s) + s1 + s2);
/*     */     
/*  31 */     if (!(Config.getGameSettings()).language.equals(s1))
/*     */     {
/*  33 */       list.add(String.valueOf(s) + (Config.getGameSettings()).language + s2);
/*     */     }
/*     */     
/*  36 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  37 */     loadResources((IResourcePack)Config.getDefaultResourcePack(), astring, map);
/*  38 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  40 */     for (int i = 0; i < airesourcepack.length; i++) {
/*     */       
/*  42 */       IResourcePack iresourcepack = airesourcepack[i];
/*  43 */       loadResources(iresourcepack, astring, map);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadResources(IResourcePack p_loadResources_0_, String[] p_loadResources_1_, Map p_loadResources_2_) {
/*     */     try {
/*  51 */       for (int i = 0; i < p_loadResources_1_.length; i++) {
/*     */         
/*  53 */         String s = p_loadResources_1_[i];
/*  54 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  56 */         if (p_loadResources_0_.resourceExists(resourcelocation))
/*     */         {
/*  58 */           InputStream inputstream = p_loadResources_0_.getInputStream(resourcelocation);
/*     */           
/*  60 */           if (inputstream != null)
/*     */           {
/*  62 */             loadLocaleData(inputstream, p_loadResources_2_);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*  67 */     } catch (IOException ioexception) {
/*     */       
/*  69 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLocaleData(InputStream p_loadLocaleData_0_, Map<String, String> p_loadLocaleData_1_) throws IOException {
/*  75 */     for (String s : IOUtils.readLines(p_loadLocaleData_0_, Charsets.UTF_8)) {
/*     */       
/*  77 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */         
/*  79 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/*  81 */         if (astring != null && astring.length == 2) {
/*     */           
/*  83 */           String s1 = astring[0];
/*  84 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/*  85 */           p_loadLocaleData_1_.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String p_get_0_) {
/*  93 */     return I18n.format(p_get_0_, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(String p_get_0_, String p_get_1_) {
/*  98 */     String s = I18n.format(p_get_0_, new Object[0]);
/*  99 */     return (s != null && !s.equals(p_get_0_)) ? s : p_get_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOn() {
/* 104 */     return I18n.format("options.on", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOff() {
/* 109 */     return I18n.format("options.off", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFast() {
/* 114 */     return I18n.format("options.graphics.fast", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFancy() {
/* 119 */     return I18n.format("options.graphics.fancy", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDefault() {
/* 124 */     return I18n.format("generator.default", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\Lang.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
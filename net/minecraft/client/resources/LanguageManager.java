/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.SortedSet;
/*     */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.text.translation.LanguageMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LanguageManager
/*     */   implements IResourceManagerReloadListener {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final MetadataSerializer theMetadataSerializer;
/*     */   private String currentLanguage;
/*  21 */   protected static final Locale CURRENT_LOCALE = new Locale();
/*  22 */   private final Map<String, Language> languageMap = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public LanguageManager(MetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
/*  26 */     this.theMetadataSerializer = theMetadataSerializerIn;
/*  27 */     this.currentLanguage = currentLanguageIn;
/*  28 */     I18n.setLocale(CURRENT_LOCALE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseLanguageMetadata(List<IResourcePack> resourcesPacks) {
/*  33 */     this.languageMap.clear();
/*     */     
/*  35 */     for (IResourcePack iresourcepack : resourcesPacks) {
/*     */ 
/*     */       
/*     */       try {
/*  39 */         LanguageMetadataSection languagemetadatasection = iresourcepack.<LanguageMetadataSection>getPackMetadata(this.theMetadataSerializer, "language");
/*     */         
/*  41 */         if (languagemetadatasection != null)
/*     */         {
/*  43 */           for (Language language : languagemetadatasection.getLanguages())
/*     */           {
/*  45 */             if (!this.languageMap.containsKey(language.getLanguageCode()))
/*     */             {
/*  47 */               this.languageMap.put(language.getLanguageCode(), language);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*  52 */       } catch (RuntimeException runtimeexception) {
/*     */         
/*  54 */         LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.getPackName(), runtimeexception);
/*     */       }
/*  56 */       catch (IOException ioexception) {
/*     */         
/*  58 */         LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.getPackName(), ioexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  65 */     List<String> list = Lists.newArrayList((Object[])new String[] { "en_us" });
/*     */     
/*  67 */     if (!"en_us".equals(this.currentLanguage))
/*     */     {
/*  69 */       list.add(this.currentLanguage);
/*     */     }
/*     */     
/*  72 */     CURRENT_LOCALE.loadLocaleDataFiles(resourceManager, list);
/*  73 */     LanguageMap.replaceWith(CURRENT_LOCALE.properties);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrentLocaleUnicode() {
/*  78 */     return CURRENT_LOCALE.isUnicode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrentLanguageBidirectional() {
/*  83 */     return (getCurrentLanguage() != null && getCurrentLanguage().isBidirectional());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentLanguage(Language currentLanguageIn) {
/*  88 */     this.currentLanguage = currentLanguageIn.getLanguageCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Language getCurrentLanguage() {
/*  93 */     String s = this.languageMap.containsKey(this.currentLanguage) ? this.currentLanguage : "en_us";
/*  94 */     return this.languageMap.get(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public SortedSet<Language> getLanguages() {
/*  99 */     return Sets.newTreeSet(this.languageMap.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public Language func_191960_a(String p_191960_1_) {
/* 104 */     return this.languageMap.get(p_191960_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\LanguageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
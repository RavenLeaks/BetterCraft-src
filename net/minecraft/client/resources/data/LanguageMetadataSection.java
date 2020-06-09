/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.resources.Language;
/*    */ 
/*    */ public class LanguageMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final Collection<Language> languages;
/*    */   
/*    */   public LanguageMetadataSection(Collection<Language> languagesIn) {
/* 12 */     this.languages = languagesIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Language> getLanguages() {
/* 17 */     return this.languages;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\LanguageMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
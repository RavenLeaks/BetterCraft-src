/*     */ package wdl;
/*     */ 
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ public abstract class MessageTypeCategory
/*     */ {
/*     */   public final String internalName;
/*     */   
/*     */   public MessageTypeCategory(String internalName) {
/*  11 */     this.internalName = internalName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getDisplayName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  28 */     return "MessageTypeCategory [internalName=" + this.internalName + 
/*  29 */       ", displayName=" + getDisplayName() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  34 */     int prime = 31;
/*  35 */     int result = 1;
/*  36 */     result = 31 * result + (
/*  37 */       (this.internalName == null) ? 0 : this.internalName.hashCode());
/*  38 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  43 */     if (this == obj) {
/*  44 */       return true;
/*     */     }
/*  46 */     if (obj == null) {
/*  47 */       return false;
/*     */     }
/*  49 */     if (getClass() != obj.getClass()) {
/*  50 */       return false;
/*     */     }
/*  52 */     MessageTypeCategory other = (MessageTypeCategory)obj;
/*  53 */     if (this.internalName == null) {
/*  54 */       if (other.internalName != null) {
/*  55 */         return false;
/*     */       }
/*  57 */     } else if (!this.internalName.equals(other.internalName)) {
/*  58 */       return false;
/*     */     } 
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class I18nableMessageTypeCategory
/*     */     extends MessageTypeCategory
/*     */   {
/*     */     public final String i18nKey;
/*     */ 
/*     */     
/*     */     public I18nableMessageTypeCategory(String internalName, String i18nKey) {
/*  71 */       super(internalName);
/*  72 */       this.i18nKey = i18nKey;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDisplayName() {
/*  78 */       return I18n.format(this.i18nKey, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   static final MessageTypeCategory CORE_RECOMMENDED = new I18nableMessageTypeCategory("CORE_RECOMMENDED", 
/*  91 */       "wdl.messages.category.core_recommended");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   static final MessageTypeCategory CORE_DEBUG = new I18nableMessageTypeCategory("CORE_DEBUG", 
/* 100 */       "wdl.messages.category.core_debug");
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\MessageTypeCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ 
/*     */ public class TextComponentTranslation
/*     */   extends TextComponentBase {
/*     */   private final String key;
/*     */   private final Object[] formatArgs;
/*  18 */   private final Object syncLock = new Object();
/*  19 */   private long lastTranslationUpdateTimeInMilliseconds = -1L;
/*     */   @VisibleForTesting
/*  21 */   List<ITextComponent> children = Lists.newArrayList();
/*  22 */   public static final Pattern STRING_VARIABLE_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */ 
/*     */   
/*     */   public TextComponentTranslation(String translationKey, Object... args) {
/*  26 */     this.key = translationKey;
/*  27 */     this.formatArgs = args; byte b; int i;
/*     */     Object[] arrayOfObject;
/*  29 */     for (i = (arrayOfObject = args).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/*     */       
/*  31 */       if (object instanceof ITextComponent)
/*     */       {
/*  33 */         ((ITextComponent)object).getStyle().setParentStyle(getStyle());
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   synchronized void ensureInitialized() {
/*  45 */     synchronized (this.syncLock) {
/*     */       
/*  47 */       long i = I18n.getLastTranslationUpdateTimeInMilliseconds();
/*     */       
/*  49 */       if (i == this.lastTranslationUpdateTimeInMilliseconds) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  54 */       this.lastTranslationUpdateTimeInMilliseconds = i;
/*  55 */       this.children.clear();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  60 */       initializeFromFormat(I18n.translateToLocal(this.key));
/*     */     }
/*  62 */     catch (TextComponentTranslationFormatException textcomponenttranslationformatexception) {
/*     */       
/*  64 */       this.children.clear();
/*     */ 
/*     */       
/*     */       try {
/*  68 */         initializeFromFormat(I18n.translateToFallback(this.key));
/*     */       }
/*  70 */       catch (TextComponentTranslationFormatException var5) {
/*     */         
/*  72 */         throw textcomponenttranslationformatexception;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeFromFormat(String format) {
/*  82 */     boolean flag = false;
/*  83 */     Matcher matcher = STRING_VARIABLE_PATTERN.matcher(format);
/*  84 */     int i = 0;
/*  85 */     int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  91 */       for (; matcher.find(j); j = l) {
/*     */         
/*  93 */         int k = matcher.start();
/*  94 */         int l = matcher.end();
/*     */         
/*  96 */         if (k > j) {
/*     */           
/*  98 */           TextComponentString textcomponentstring = new TextComponentString(String.format(format.substring(j, k), new Object[0]));
/*  99 */           textcomponentstring.getStyle().setParentStyle(getStyle());
/* 100 */           this.children.add(textcomponentstring);
/*     */         } 
/*     */         
/* 103 */         String s2 = matcher.group(2);
/* 104 */         String s = format.substring(k, l);
/*     */         
/* 106 */         if ("%".equals(s2) && "%%".equals(s)) {
/*     */           
/* 108 */           TextComponentString textcomponentstring2 = new TextComponentString("%");
/* 109 */           textcomponentstring2.getStyle().setParentStyle(getStyle());
/* 110 */           this.children.add(textcomponentstring2);
/*     */         }
/*     */         else {
/*     */           
/* 114 */           if (!"s".equals(s2))
/*     */           {
/* 116 */             throw new TextComponentTranslationFormatException(this, "Unsupported format: '" + s + "'");
/*     */           }
/*     */           
/* 119 */           String s1 = matcher.group(1);
/* 120 */           int i1 = (s1 != null) ? (Integer.parseInt(s1) - 1) : i++;
/*     */           
/* 122 */           if (i1 < this.formatArgs.length)
/*     */           {
/* 124 */             this.children.add(getFormatArgumentAsComponent(i1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       if (j < format.length())
/*     */       {
/* 131 */         TextComponentString textcomponentstring1 = new TextComponentString(String.format(format.substring(j), new Object[0]));
/* 132 */         textcomponentstring1.getStyle().setParentStyle(getStyle());
/* 133 */         this.children.add(textcomponentstring1);
/*     */       }
/*     */     
/* 136 */     } catch (IllegalFormatException illegalformatexception) {
/*     */       
/* 138 */       throw new TextComponentTranslationFormatException(this, illegalformatexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ITextComponent getFormatArgumentAsComponent(int index) {
/*     */     ITextComponent itextcomponent;
/* 144 */     if (index >= this.formatArgs.length)
/*     */     {
/* 146 */       throw new TextComponentTranslationFormatException(this, index);
/*     */     }
/*     */ 
/*     */     
/* 150 */     Object object = this.formatArgs[index];
/*     */ 
/*     */     
/* 153 */     if (object instanceof ITextComponent) {
/*     */       
/* 155 */       itextcomponent = (ITextComponent)object;
/*     */     }
/*     */     else {
/*     */       
/* 159 */       itextcomponent = new TextComponentString((object == null) ? "null" : object.toString());
/* 160 */       itextcomponent.getStyle().setParentStyle(getStyle());
/*     */     } 
/*     */     
/* 163 */     return itextcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent setStyle(Style style) {
/* 169 */     super.setStyle(style); byte b; int i;
/*     */     Object[] arrayOfObject;
/* 171 */     for (i = (arrayOfObject = this.formatArgs).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/*     */       
/* 173 */       if (object instanceof ITextComponent)
/*     */       {
/* 175 */         ((ITextComponent)object).getStyle().setParentStyle(getStyle());
/*     */       }
/*     */       b++; }
/*     */     
/* 179 */     if (this.lastTranslationUpdateTimeInMilliseconds > -1L)
/*     */     {
/* 181 */       for (ITextComponent itextcomponent : this.children)
/*     */       {
/* 183 */         itextcomponent.getStyle().setParentStyle(style);
/*     */       }
/*     */     }
/*     */     
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ITextComponent> iterator() {
/* 192 */     ensureInitialized();
/* 193 */     return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnformattedComponentText() {
/* 202 */     ensureInitialized();
/* 203 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 205 */     for (ITextComponent itextcomponent : this.children)
/*     */     {
/* 207 */       stringbuilder.append(itextcomponent.getUnformattedComponentText());
/*     */     }
/*     */     
/* 210 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextComponentTranslation createCopy() {
/* 218 */     Object[] aobject = new Object[this.formatArgs.length];
/*     */     
/* 220 */     for (int i = 0; i < this.formatArgs.length; i++) {
/*     */       
/* 222 */       if (this.formatArgs[i] instanceof ITextComponent) {
/*     */         
/* 224 */         aobject[i] = ((ITextComponent)this.formatArgs[i]).createCopy();
/*     */       }
/*     */       else {
/*     */         
/* 228 */         aobject[i] = this.formatArgs[i];
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(this.key, aobject);
/* 233 */     textcomponenttranslation.setStyle(getStyle().createShallowCopy());
/*     */     
/* 235 */     for (ITextComponent itextcomponent : getSiblings())
/*     */     {
/* 237 */       textcomponenttranslation.appendSibling(itextcomponent.createCopy());
/*     */     }
/*     */     
/* 240 */     return textcomponenttranslation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 245 */     if (this == p_equals_1_)
/*     */     {
/* 247 */       return true;
/*     */     }
/* 249 */     if (!(p_equals_1_ instanceof TextComponentTranslation))
/*     */     {
/* 251 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 255 */     TextComponentTranslation textcomponenttranslation = (TextComponentTranslation)p_equals_1_;
/* 256 */     return (Arrays.equals(this.formatArgs, textcomponenttranslation.formatArgs) && this.key.equals(textcomponenttranslation.key) && super.equals(p_equals_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 262 */     int i = super.hashCode();
/* 263 */     i = 31 * i + this.key.hashCode();
/* 264 */     i = 31 * i + Arrays.hashCode(this.formatArgs);
/* 265 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 270 */     return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + getStyle() + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 275 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getFormatArgs() {
/* 280 */     return this.formatArgs;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentTranslation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
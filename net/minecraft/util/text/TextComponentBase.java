/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class TextComponentBase implements ITextComponent {
/*     */   public TextComponentBase() {
/*  12 */     this.siblings = Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ITextComponent> siblings;
/*     */   private Style style;
/*     */   
/*     */   public ITextComponent appendSibling(ITextComponent component) {
/*  20 */     component.getStyle().setParentStyle(getStyle());
/*  21 */     this.siblings.add(component);
/*  22 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ITextComponent> getSiblings() {
/*  27 */     return this.siblings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent appendText(String text) {
/*  35 */     return appendSibling(new TextComponentString(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent setStyle(Style style) {
/*  40 */     this.style = style;
/*     */     
/*  42 */     for (ITextComponent itextcomponent : this.siblings)
/*     */     {
/*  44 */       itextcomponent.getStyle().setParentStyle(getStyle());
/*     */     }
/*     */     
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Style getStyle() {
/*  52 */     if (this.style == null) {
/*     */       
/*  54 */       this.style = new Style();
/*     */       
/*  56 */       for (ITextComponent itextcomponent : this.siblings)
/*     */       {
/*  58 */         itextcomponent.getStyle().setParentStyle(this.style);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return this.style;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ITextComponent> iterator() {
/*  67 */     return Iterators.concat((Iterator)Iterators.forArray((Object[])new TextComponentBase[] { this }, ), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getUnformattedText() {
/*  75 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  77 */     for (ITextComponent itextcomponent : this)
/*     */     {
/*  79 */       stringbuilder.append(itextcomponent.getUnformattedComponentText());
/*     */     }
/*     */     
/*  82 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormattedText() {
/*  90 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  92 */     for (ITextComponent itextcomponent : this) {
/*     */       
/*  94 */       String s = itextcomponent.getUnformattedComponentText();
/*     */       
/*  96 */       if (!s.isEmpty()) {
/*     */         
/*  98 */         stringbuilder.append(itextcomponent.getStyle().getFormattingCode());
/*  99 */         stringbuilder.append(s);
/* 100 */         stringbuilder.append(TextFormatting.RESET);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterator<ITextComponent> createDeepCopyIterator(Iterable<ITextComponent> components) {
/* 109 */     Iterator<ITextComponent> iterator = Iterators.concat(Iterators.transform(components.iterator(), new Function<ITextComponent, Iterator<ITextComponent>>()
/*     */           {
/*     */             public Iterator<ITextComponent> apply(@Nullable ITextComponent p_apply_1_)
/*     */             {
/* 113 */               return p_apply_1_.iterator();
/*     */             }
/*     */           }));
/* 116 */     iterator = Iterators.transform(iterator, new Function<ITextComponent, ITextComponent>()
/*     */         {
/*     */           public ITextComponent apply(@Nullable ITextComponent p_apply_1_)
/*     */           {
/* 120 */             ITextComponent itextcomponent = p_apply_1_.createCopy();
/* 121 */             itextcomponent.setStyle(itextcomponent.getStyle().createDeepCopy());
/* 122 */             return itextcomponent;
/*     */           }
/*     */         });
/* 125 */     return iterator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 130 */     if (this == p_equals_1_)
/*     */     {
/* 132 */       return true;
/*     */     }
/* 134 */     if (!(p_equals_1_ instanceof TextComponentBase))
/*     */     {
/* 136 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 140 */     TextComponentBase textcomponentbase = (TextComponentBase)p_equals_1_;
/* 141 */     return (this.siblings.equals(textcomponentbase.siblings) && getStyle().equals(textcomponentbase.getStyle()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     return 31 * this.style.hashCode() + this.siblings.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
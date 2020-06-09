/*     */ package net.minecraft.util.text.event;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class HoverEvent
/*     */ {
/*     */   private final Action action;
/*     */   private final ITextComponent value;
/*     */   
/*     */   public HoverEvent(Action actionIn, ITextComponent valueIn) {
/*  14 */     this.action = actionIn;
/*  15 */     this.value = valueIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/*  23 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getValue() {
/*  32 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  37 */     if (this == p_equals_1_)
/*     */     {
/*  39 */       return true;
/*     */     }
/*  41 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  43 */       HoverEvent hoverevent = (HoverEvent)p_equals_1_;
/*     */       
/*  45 */       if (this.action != hoverevent.action)
/*     */       {
/*  47 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  51 */       if (this.value != null) {
/*     */         
/*  53 */         if (!this.value.equals(hoverevent.value))
/*     */         {
/*  55 */           return false;
/*     */         }
/*     */       }
/*  58 */       else if (hoverevent.value != null) {
/*     */         
/*  60 */         return false;
/*     */       } 
/*     */       
/*  63 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  74 */     return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  79 */     int i = this.action.hashCode();
/*  80 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/*  81 */     return i;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/*  86 */     SHOW_TEXT("show_text", true),
/*  87 */     SHOW_ITEM("show_item", true),
/*  88 */     SHOW_ENTITY("show_entity", true);
/*     */     
/*  90 */     private static final Map<String, Action> NAME_MAPPING = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean allowedInChat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String canonicalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Action[] arrayOfAction;
/* 116 */       for (i = (arrayOfAction = values()).length, b = 0; b < i; ) { Action hoverevent$action = arrayOfAction[b];
/*     */         
/* 118 */         NAME_MAPPING.put(hoverevent$action.getCanonicalName(), hoverevent$action);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     Action(String canonicalNameIn, boolean allowedInChatIn) {
/*     */       this.canonicalName = canonicalNameIn;
/*     */       this.allowedInChat = allowedInChatIn;
/*     */     }
/*     */     
/*     */     public boolean shouldAllowInChat() {
/*     */       return this.allowedInChat;
/*     */     }
/*     */     
/*     */     public String getCanonicalName() {
/*     */       return this.canonicalName;
/*     */     }
/*     */     
/*     */     public static Action getValueByCanonicalName(String canonicalNameIn) {
/*     */       return NAME_MAPPING.get(canonicalNameIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\event\HoverEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
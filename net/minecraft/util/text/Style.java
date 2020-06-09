/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import net.minecraft.util.text.event.HoverEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Style
/*     */ {
/*     */   private Style parentStyle;
/*     */   private TextFormatting color;
/*     */   private Boolean bold;
/*     */   private Boolean italic;
/*     */   private Boolean underlined;
/*     */   private Boolean strikethrough;
/*     */   private Boolean obfuscated;
/*     */   private ClickEvent clickEvent;
/*     */   private HoverEvent hoverEvent;
/*     */   private String insertion;
/*     */   
/*  35 */   private static final Style ROOT = new Style()
/*     */     {
/*     */       @Nullable
/*     */       public TextFormatting getColor()
/*     */       {
/*  40 */         return null;
/*     */       }
/*     */       
/*     */       public boolean getBold() {
/*  44 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getItalic() {
/*  48 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getStrikethrough() {
/*  52 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getUnderlined() {
/*  56 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getObfuscated() {
/*  60 */         return false;
/*     */       }
/*     */       
/*     */       @Nullable
/*     */       public ClickEvent getClickEvent() {
/*  65 */         return null;
/*     */       }
/*     */       
/*     */       @Nullable
/*     */       public HoverEvent getHoverEvent() {
/*  70 */         return null;
/*     */       }
/*     */       
/*     */       @Nullable
/*     */       public String getInsertion() {
/*  75 */         return null;
/*     */       }
/*     */       
/*     */       public Style setColor(TextFormatting color) {
/*  79 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setBold(Boolean boldIn) {
/*  83 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setItalic(Boolean italic) {
/*  87 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setStrikethrough(Boolean strikethrough) {
/*  91 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setUnderlined(Boolean underlined) {
/*  95 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setObfuscated(Boolean obfuscated) {
/*  99 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setClickEvent(ClickEvent event) {
/* 103 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setHoverEvent(HoverEvent event) {
/* 107 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Style setParentStyle(Style parent) {
/* 111 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public String toString() {
/* 115 */         return "Style.ROOT";
/*     */       }
/*     */       
/*     */       public Style createShallowCopy() {
/* 119 */         return this;
/*     */       }
/*     */       
/*     */       public Style createDeepCopy() {
/* 123 */         return this;
/*     */       }
/*     */       
/*     */       public String getFormattingCode() {
/* 127 */         return "";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TextFormatting getColor() {
/* 138 */     return (this.color == null) ? getParent().getColor() : this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBold() {
/* 146 */     return (this.bold == null) ? getParent().getBold() : this.bold.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getItalic() {
/* 154 */     return (this.italic == null) ? getParent().getItalic() : this.italic.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStrikethrough() {
/* 162 */     return (this.strikethrough == null) ? getParent().getStrikethrough() : this.strikethrough.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUnderlined() {
/* 170 */     return (this.underlined == null) ? getParent().getUnderlined() : this.underlined.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getObfuscated() {
/* 178 */     return (this.obfuscated == null) ? getParent().getObfuscated() : this.obfuscated.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 186 */     return (this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.clickEvent == null && this.hoverEvent == null && this.insertion == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ClickEvent getClickEvent() {
/* 196 */     return (this.clickEvent == null) ? getParent().getClickEvent() : this.clickEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public HoverEvent getHoverEvent() {
/* 206 */     return (this.hoverEvent == null) ? getParent().getHoverEvent() : this.hoverEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getInsertion() {
/* 216 */     return (this.insertion == null) ? getParent().getInsertion() : this.insertion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setColor(TextFormatting color) {
/* 225 */     this.color = color;
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setBold(Boolean boldIn) {
/* 235 */     this.bold = boldIn;
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setItalic(Boolean italic) {
/* 245 */     this.italic = italic;
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setStrikethrough(Boolean strikethrough) {
/* 255 */     this.strikethrough = strikethrough;
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setUnderlined(Boolean underlined) {
/* 265 */     this.underlined = underlined;
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setObfuscated(Boolean obfuscated) {
/* 275 */     this.obfuscated = obfuscated;
/* 276 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setClickEvent(ClickEvent event) {
/* 284 */     this.clickEvent = event;
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setHoverEvent(HoverEvent event) {
/* 293 */     this.hoverEvent = event;
/* 294 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setInsertion(String insertion) {
/* 302 */     this.insertion = insertion;
/* 303 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style setParentStyle(Style parent) {
/* 312 */     this.parentStyle = parent;
/* 313 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattingCode() {
/* 321 */     if (isEmpty())
/*     */     {
/* 323 */       return (this.parentStyle != null) ? this.parentStyle.getFormattingCode() : "";
/*     */     }
/*     */ 
/*     */     
/* 327 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 329 */     if (getColor() != null)
/*     */     {
/* 331 */       stringbuilder.append(getColor());
/*     */     }
/*     */     
/* 334 */     if (getBold())
/*     */     {
/* 336 */       stringbuilder.append(TextFormatting.BOLD);
/*     */     }
/*     */     
/* 339 */     if (getItalic())
/*     */     {
/* 341 */       stringbuilder.append(TextFormatting.ITALIC);
/*     */     }
/*     */     
/* 344 */     if (getUnderlined())
/*     */     {
/* 346 */       stringbuilder.append(TextFormatting.UNDERLINE);
/*     */     }
/*     */     
/* 349 */     if (getObfuscated())
/*     */     {
/* 351 */       stringbuilder.append(TextFormatting.OBFUSCATED);
/*     */     }
/*     */     
/* 354 */     if (getStrikethrough())
/*     */     {
/* 356 */       stringbuilder.append(TextFormatting.STRIKETHROUGH);
/*     */     }
/*     */     
/* 359 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Style getParent() {
/* 368 */     return (this.parentStyle == null) ? ROOT : this.parentStyle;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 373 */     return "Style{hasParent=" + ((this.parentStyle != null) ? 1 : 0) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + getClickEvent() + ", hoverEvent=" + getHoverEvent() + ", insertion=" + getInsertion() + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 378 */     if (this == p_equals_1_)
/*     */     {
/* 380 */       return true;
/*     */     }
/* 382 */     if (!(p_equals_1_ instanceof Style))
/*     */     {
/* 384 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     Style style = (Style)p_equals_1_;
/*     */     
/* 393 */     if (getBold() == style.getBold() && getColor() == style.getColor() && getItalic() == style.getItalic() && getObfuscated() == style.getObfuscated() && getStrikethrough() == style.getStrikethrough() && getUnderlined() == style.getUnderlined()) {
/*     */ 
/*     */ 
/*     */       
/* 397 */       if (getClickEvent() != null)
/*     */       
/* 399 */       { if (!getClickEvent().equals(style.getClickEvent()))
/*     */         
/*     */         { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 435 */           boolean flag = false;
/* 436 */           return flag; }  } else if (style.getClickEvent() != null) { return false; }  if (getHoverEvent() != null) { if (!getHoverEvent().equals(style.getHoverEvent()))
/*     */           return false;  } else if (style.getHoverEvent() != null) { return false; }
/* 438 */        if (getInsertion() != null) { if (getInsertion().equals(style.getInsertion())) { boolean flag = true;
/* 439 */           return flag; }
/*     */          }
/*     */       else if (style.getInsertion() == null)
/*     */       { return true; }
/*     */     
/*     */     } 
/* 445 */     return false; } public int hashCode() { int i = this.color.hashCode();
/* 446 */     i = 31 * i + this.bold.hashCode();
/* 447 */     i = 31 * i + this.italic.hashCode();
/* 448 */     i = 31 * i + this.underlined.hashCode();
/* 449 */     i = 31 * i + this.strikethrough.hashCode();
/* 450 */     i = 31 * i + this.obfuscated.hashCode();
/* 451 */     i = 31 * i + this.clickEvent.hashCode();
/* 452 */     i = 31 * i + this.hoverEvent.hashCode();
/* 453 */     i = 31 * i + this.insertion.hashCode();
/* 454 */     return i; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style createShallowCopy() {
/* 464 */     Style style = new Style();
/* 465 */     style.bold = this.bold;
/* 466 */     style.italic = this.italic;
/* 467 */     style.strikethrough = this.strikethrough;
/* 468 */     style.underlined = this.underlined;
/* 469 */     style.obfuscated = this.obfuscated;
/* 470 */     style.color = this.color;
/* 471 */     style.clickEvent = this.clickEvent;
/* 472 */     style.hoverEvent = this.hoverEvent;
/* 473 */     style.parentStyle = this.parentStyle;
/* 474 */     style.insertion = this.insertion;
/* 475 */     return style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style createDeepCopy() {
/* 484 */     Style style = new Style();
/* 485 */     style.setBold(Boolean.valueOf(getBold()));
/* 486 */     style.setItalic(Boolean.valueOf(getItalic()));
/* 487 */     style.setStrikethrough(Boolean.valueOf(getStrikethrough()));
/* 488 */     style.setUnderlined(Boolean.valueOf(getUnderlined()));
/* 489 */     style.setObfuscated(Boolean.valueOf(getObfuscated()));
/* 490 */     style.setColor(getColor());
/* 491 */     style.setClickEvent(getClickEvent());
/* 492 */     style.setHoverEvent(getHoverEvent());
/* 493 */     style.setInsertion(getInsertion());
/* 494 */     return style;
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<Style>, JsonSerializer<Style>
/*     */   {
/*     */     @Nullable
/*     */     public Style deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 502 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 504 */         Style style = new Style();
/* 505 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */         
/* 507 */         if (jsonobject == null)
/*     */         {
/* 509 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 513 */         if (jsonobject.has("bold"))
/*     */         {
/* 515 */           style.bold = Boolean.valueOf(jsonobject.get("bold").getAsBoolean());
/*     */         }
/*     */         
/* 518 */         if (jsonobject.has("italic"))
/*     */         {
/* 520 */           style.italic = Boolean.valueOf(jsonobject.get("italic").getAsBoolean());
/*     */         }
/*     */         
/* 523 */         if (jsonobject.has("underlined"))
/*     */         {
/* 525 */           style.underlined = Boolean.valueOf(jsonobject.get("underlined").getAsBoolean());
/*     */         }
/*     */         
/* 528 */         if (jsonobject.has("strikethrough"))
/*     */         {
/* 530 */           style.strikethrough = Boolean.valueOf(jsonobject.get("strikethrough").getAsBoolean());
/*     */         }
/*     */         
/* 533 */         if (jsonobject.has("obfuscated"))
/*     */         {
/* 535 */           style.obfuscated = Boolean.valueOf(jsonobject.get("obfuscated").getAsBoolean());
/*     */         }
/*     */         
/* 538 */         if (jsonobject.has("color"))
/*     */         {
/* 540 */           style.color = (TextFormatting)p_deserialize_3_.deserialize(jsonobject.get("color"), TextFormatting.class);
/*     */         }
/*     */         
/* 543 */         if (jsonobject.has("insertion"))
/*     */         {
/* 545 */           style.insertion = jsonobject.get("insertion").getAsString();
/*     */         }
/*     */         
/* 548 */         if (jsonobject.has("clickEvent")) {
/*     */           
/* 550 */           JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
/*     */           
/* 552 */           if (jsonobject1 != null) {
/*     */             
/* 554 */             JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
/* 555 */             ClickEvent.Action clickevent$action = (jsonprimitive == null) ? null : ClickEvent.Action.getValueByCanonicalName(jsonprimitive.getAsString());
/* 556 */             JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
/* 557 */             String s = (jsonprimitive1 == null) ? null : jsonprimitive1.getAsString();
/*     */             
/* 559 */             if (clickevent$action != null && s != null && clickevent$action.shouldAllowInChat())
/*     */             {
/* 561 */               style.clickEvent = new ClickEvent(clickevent$action, s);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 566 */         if (jsonobject.has("hoverEvent")) {
/*     */           
/* 568 */           JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
/*     */           
/* 570 */           if (jsonobject2 != null) {
/*     */             
/* 572 */             JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
/* 573 */             HoverEvent.Action hoverevent$action = (jsonprimitive2 == null) ? null : HoverEvent.Action.getValueByCanonicalName(jsonprimitive2.getAsString());
/* 574 */             ITextComponent itextcomponent = (ITextComponent)p_deserialize_3_.deserialize(jsonobject2.get("value"), ITextComponent.class);
/*     */             
/* 576 */             if (hoverevent$action != null && itextcomponent != null && hoverevent$action.shouldAllowInChat())
/*     */             {
/* 578 */               style.hoverEvent = new HoverEvent(hoverevent$action, itextcomponent);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 583 */         return style;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 588 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public JsonElement serialize(Style p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 595 */       if (p_serialize_1_.isEmpty())
/*     */       {
/* 597 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 601 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 603 */       if (p_serialize_1_.bold != null)
/*     */       {
/* 605 */         jsonobject.addProperty("bold", p_serialize_1_.bold);
/*     */       }
/*     */       
/* 608 */       if (p_serialize_1_.italic != null)
/*     */       {
/* 610 */         jsonobject.addProperty("italic", p_serialize_1_.italic);
/*     */       }
/*     */       
/* 613 */       if (p_serialize_1_.underlined != null)
/*     */       {
/* 615 */         jsonobject.addProperty("underlined", p_serialize_1_.underlined);
/*     */       }
/*     */       
/* 618 */       if (p_serialize_1_.strikethrough != null)
/*     */       {
/* 620 */         jsonobject.addProperty("strikethrough", p_serialize_1_.strikethrough);
/*     */       }
/*     */       
/* 623 */       if (p_serialize_1_.obfuscated != null)
/*     */       {
/* 625 */         jsonobject.addProperty("obfuscated", p_serialize_1_.obfuscated);
/*     */       }
/*     */       
/* 628 */       if (p_serialize_1_.color != null)
/*     */       {
/* 630 */         jsonobject.add("color", p_serialize_3_.serialize(p_serialize_1_.color));
/*     */       }
/*     */       
/* 633 */       if (p_serialize_1_.insertion != null)
/*     */       {
/* 635 */         jsonobject.add("insertion", p_serialize_3_.serialize(p_serialize_1_.insertion));
/*     */       }
/*     */       
/* 638 */       if (p_serialize_1_.clickEvent != null) {
/*     */         
/* 640 */         JsonObject jsonobject1 = new JsonObject();
/* 641 */         jsonobject1.addProperty("action", p_serialize_1_.clickEvent.getAction().getCanonicalName());
/* 642 */         jsonobject1.addProperty("value", p_serialize_1_.clickEvent.getValue());
/* 643 */         jsonobject.add("clickEvent", (JsonElement)jsonobject1);
/*     */       } 
/*     */       
/* 646 */       if (p_serialize_1_.hoverEvent != null) {
/*     */         
/* 648 */         JsonObject jsonobject2 = new JsonObject();
/* 649 */         jsonobject2.addProperty("action", p_serialize_1_.hoverEvent.getAction().getCanonicalName());
/* 650 */         jsonobject2.add("value", p_serialize_3_.serialize(p_serialize_1_.hoverEvent.getValue()));
/* 651 */         jsonobject.add("hoverEvent", (JsonElement)jsonobject2);
/*     */       } 
/*     */       
/* 654 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\Style.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
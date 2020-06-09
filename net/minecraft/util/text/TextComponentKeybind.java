/*    */ package net.minecraft.util.text;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextComponentKeybind
/*    */   extends TextComponentBase
/*    */ {
/*    */   public static Function<String, Supplier<String>> field_193637_b = p_193635_0_ -> ();
/*    */   private final String field_193638_c;
/*    */   private Supplier<String> field_193639_d;
/*    */   
/*    */   public TextComponentKeybind(String p_i47521_1_) {
/* 19 */     this.field_193638_c = p_i47521_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedComponentText() {
/* 28 */     if (this.field_193639_d == null)
/*    */     {
/* 30 */       this.field_193639_d = field_193637_b.apply(this.field_193638_c);
/*    */     }
/*    */     
/* 33 */     return this.field_193639_d.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TextComponentKeybind createCopy() {
/* 41 */     TextComponentKeybind textcomponentkeybind = new TextComponentKeybind(this.field_193638_c);
/* 42 */     textcomponentkeybind.setStyle(getStyle().createShallowCopy());
/*    */     
/* 44 */     for (ITextComponent itextcomponent : getSiblings())
/*    */     {
/* 46 */       textcomponentkeybind.appendSibling(itextcomponent.createCopy());
/*    */     }
/*    */     
/* 49 */     return textcomponentkeybind;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 54 */     if (this == p_equals_1_)
/*    */     {
/* 56 */       return true;
/*    */     }
/* 58 */     if (!(p_equals_1_ instanceof TextComponentKeybind))
/*    */     {
/* 60 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 64 */     TextComponentKeybind textcomponentkeybind = (TextComponentKeybind)p_equals_1_;
/* 65 */     return (this.field_193638_c.equals(textcomponentkeybind.field_193638_c) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "KeybindComponent{keybind='" + this.field_193638_c + '\'' + ", siblings=" + this.siblings + ", style=" + getStyle() + '}';
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_193633_h() {
/* 76 */     return this.field_193638_c;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentKeybind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
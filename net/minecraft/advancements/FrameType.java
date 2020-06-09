/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public enum FrameType
/*    */ {
/*  7 */   TASK("task", 0, TextFormatting.GREEN),
/*  8 */   CHALLENGE("challenge", 26, TextFormatting.DARK_PURPLE),
/*  9 */   GOAL("goal", 52, TextFormatting.GREEN);
/*    */   
/*    */   private final String field_192313_d;
/*    */   
/*    */   private final int field_192314_e;
/*    */   private final TextFormatting field_193230_f;
/*    */   
/*    */   FrameType(String p_i47585_3_, int p_i47585_4_, TextFormatting p_i47585_5_) {
/* 17 */     this.field_192313_d = p_i47585_3_;
/* 18 */     this.field_192314_e = p_i47585_4_;
/* 19 */     this.field_193230_f = p_i47585_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_192307_a() {
/* 24 */     return this.field_192313_d;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_192309_b() {
/* 29 */     return this.field_192314_e;
/*    */   } public static FrameType func_192308_a(String p_192308_0_) {
/*    */     byte b;
/*    */     int i;
/*    */     FrameType[] arrayOfFrameType;
/* 34 */     for (i = (arrayOfFrameType = values()).length, b = 0; b < i; ) { FrameType frametype = arrayOfFrameType[b];
/*    */       
/* 36 */       if (frametype.field_192313_d.equals(p_192308_0_))
/*    */       {
/* 38 */         return frametype;
/*    */       }
/*    */       b++; }
/*    */     
/* 42 */     throw new IllegalArgumentException("Unknown frame type '" + p_192308_0_ + "'");
/*    */   }
/*    */ 
/*    */   
/*    */   public TextFormatting func_193229_c() {
/* 47 */     return this.field_193230_f;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\FrameType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
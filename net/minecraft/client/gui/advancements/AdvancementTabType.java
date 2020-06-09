/*     */ package net.minecraft.client.gui.advancements;
/*     */ 
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ enum AdvancementTabType
/*     */ {
/*  10 */   ABOVE(0, 0, 28, 32, 8),
/*  11 */   BELOW(84, 0, 28, 32, 8),
/*  12 */   LEFT(0, 64, 32, 28, 5),
/*  13 */   RIGHT(96, 64, 32, 28, 5);
/*     */   
/*     */   public static final int field_192659_e;
/*     */   
/*     */   private final int field_192660_f;
/*     */   private final int field_192661_g;
/*     */   private final int field_192662_h;
/*     */   private final int field_192663_i;
/*     */   private final int field_192664_j;
/*     */   
/*     */   AdvancementTabType(int p_i47386_3_, int p_i47386_4_, int p_i47386_5_, int p_i47386_6_, int p_i47386_7_) {
/*  24 */     this.field_192660_f = p_i47386_3_;
/*  25 */     this.field_192661_g = p_i47386_4_;
/*  26 */     this.field_192662_h = p_i47386_5_;
/*  27 */     this.field_192663_i = p_i47386_6_;
/*  28 */     this.field_192664_j = p_i47386_7_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192650_a() {
/*  33 */     return this.field_192664_j;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192651_a(Gui p_192651_1_, int p_192651_2_, int p_192651_3_, boolean p_192651_4_, int p_192651_5_) {
/*  38 */     int i = this.field_192660_f;
/*     */     
/*  40 */     if (p_192651_5_ > 0)
/*     */     {
/*  42 */       i += this.field_192662_h;
/*     */     }
/*     */     
/*  45 */     if (p_192651_5_ == this.field_192664_j - 1)
/*     */     {
/*  47 */       i += this.field_192662_h;
/*     */     }
/*     */     
/*  50 */     int j = p_192651_4_ ? (this.field_192661_g + this.field_192663_i) : this.field_192661_g;
/*  51 */     p_192651_1_.drawTexturedModalRect(p_192651_2_ + func_192648_a(p_192651_5_), p_192651_3_ + func_192653_b(p_192651_5_), i, j, this.field_192662_h, this.field_192663_i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192652_a(int p_192652_1_, int p_192652_2_, int p_192652_3_, RenderItem p_192652_4_, ItemStack p_192652_5_) {
/*  56 */     int i = p_192652_1_ + func_192648_a(p_192652_3_);
/*  57 */     int j = p_192652_2_ + func_192653_b(p_192652_3_);
/*     */     
/*  59 */     switch (this) {
/*     */       
/*     */       case null:
/*  62 */         i += 6;
/*  63 */         j += 9;
/*     */         break;
/*     */       
/*     */       case BELOW:
/*  67 */         i += 6;
/*  68 */         j += 6;
/*     */         break;
/*     */       
/*     */       case LEFT:
/*  72 */         i += 10;
/*  73 */         j += 5;
/*     */         break;
/*     */       
/*     */       case RIGHT:
/*  77 */         i += 6;
/*  78 */         j += 5;
/*     */         break;
/*     */     } 
/*  81 */     p_192652_4_.renderItemAndEffectIntoGUI(null, p_192652_5_, i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192648_a(int p_192648_1_) {
/*  86 */     switch (this) {
/*     */       
/*     */       case null:
/*  89 */         return (this.field_192662_h + 4) * p_192648_1_;
/*     */       
/*     */       case BELOW:
/*  92 */         return (this.field_192662_h + 4) * p_192648_1_;
/*     */       
/*     */       case LEFT:
/*  95 */         return -this.field_192662_h + 4;
/*     */       
/*     */       case RIGHT:
/*  98 */         return 248;
/*     */     } 
/*     */     
/* 101 */     throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_192653_b(int p_192653_1_) {
/* 107 */     switch (this) {
/*     */       
/*     */       case null:
/* 110 */         return -this.field_192663_i + 4;
/*     */       
/*     */       case BELOW:
/* 113 */         return 136;
/*     */       
/*     */       case LEFT:
/* 116 */         return this.field_192663_i * p_192653_1_;
/*     */       
/*     */       case RIGHT:
/* 119 */         return this.field_192663_i * p_192653_1_;
/*     */     } 
/*     */     
/* 122 */     throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_192654_a(int p_192654_1_, int p_192654_2_, int p_192654_3_, int p_192654_4_, int p_192654_5_) {
/* 128 */     int i = p_192654_1_ + func_192648_a(p_192654_3_);
/* 129 */     int j = p_192654_2_ + func_192653_b(p_192654_3_);
/* 130 */     return (p_192654_4_ > i && p_192654_4_ < i + this.field_192662_h && p_192654_5_ > j && p_192654_5_ < j + this.field_192663_i);
/*     */   }
/*     */   
/*     */   static {
/* 134 */     int i = 0; byte b; int j;
/*     */     AdvancementTabType[] arrayOfAdvancementTabType;
/* 136 */     for (j = (arrayOfAdvancementTabType = values()).length, b = 0; b < j; ) { AdvancementTabType advancementtabtype = arrayOfAdvancementTabType[b];
/*     */       
/* 138 */       i += advancementtabtype.field_192664_j;
/*     */       b++; }
/*     */     
/* 141 */     field_192659_e = i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\advancements\AdvancementTabType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
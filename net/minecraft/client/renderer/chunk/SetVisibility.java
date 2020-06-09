/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class SetVisibility
/*     */ {
/*   8 */   private static final int COUNT_FACES = (EnumFacing.values()).length;
/*     */   
/*     */   private long bits;
/*     */   
/*     */   public void setManyVisible(Set<EnumFacing> facing) {
/*  13 */     for (EnumFacing enumfacing : facing) {
/*     */       
/*  15 */       for (EnumFacing enumfacing1 : facing)
/*     */       {
/*  17 */         setVisible(enumfacing, enumfacing1, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
/*  24 */     setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
/*  25 */     setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllVisible(boolean visible) {
/*  30 */     if (visible) {
/*     */       
/*  32 */       this.bits = -1L;
/*     */     }
/*     */     else {
/*     */       
/*  36 */       this.bits = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  42 */     return getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  47 */     StringBuilder stringbuilder = new StringBuilder();
/*  48 */     stringbuilder.append(' '); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  50 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  52 */       stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
/*     */       b++; }
/*     */     
/*  55 */     stringbuilder.append('\n');
/*     */     
/*  57 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing2 = arrayOfEnumFacing[b];
/*     */       
/*  59 */       stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0)); byte b1; int j;
/*     */       EnumFacing[] arrayOfEnumFacing1;
/*  61 */       for (j = (arrayOfEnumFacing1 = EnumFacing.values()).length, b1 = 0; b1 < j; ) { EnumFacing enumfacing1 = arrayOfEnumFacing1[b1];
/*     */         
/*  63 */         if (enumfacing2 == enumfacing1) {
/*     */           
/*  65 */           stringbuilder.append("  ");
/*     */         }
/*     */         else {
/*     */           
/*  69 */           boolean flag = isVisible(enumfacing2, enumfacing1);
/*  70 */           stringbuilder.append(' ').append(flag ? 89 : 110);
/*     */         } 
/*     */         b1++; }
/*     */       
/*  74 */       stringbuilder.append('\n');
/*     */       b++; }
/*     */     
/*  77 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getBit(int p_getBit_1_) {
/*  82 */     return ((this.bits & (1 << p_getBit_1_)) != 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBit(int p_setBit_1_, boolean p_setBit_2_) {
/*  87 */     if (p_setBit_2_) {
/*     */       
/*  89 */       setBit(p_setBit_1_);
/*     */     }
/*     */     else {
/*     */       
/*  93 */       clearBit(p_setBit_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBit(int p_setBit_1_) {
/*  99 */     this.bits |= (1 << p_setBit_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearBit(int p_clearBit_1_) {
/* 104 */     this.bits &= (1 << p_clearBit_1_ ^ 0xFFFFFFFF);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\SetVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.util;
/*     */ 
/*     */ public enum Rotation
/*     */ {
/*   5 */   NONE("rotate_0"),
/*   6 */   CLOCKWISE_90("rotate_90"),
/*   7 */   CLOCKWISE_180("rotate_180"),
/*   8 */   COUNTERCLOCKWISE_90("rotate_270"); private final String name;
/*     */   
/*     */   static {
/*  11 */     rotationNames = new String[(values()).length];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     int i = 0; byte b; int j;
/*     */     Rotation[] arrayOfRotation;
/* 121 */     for (j = (arrayOfRotation = values()).length, b = 0; b < j; ) { Rotation rotation = arrayOfRotation[b];
/*     */       
/* 123 */       rotationNames[i++] = rotation.name;
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static final String[] rotationNames;
/*     */   
/*     */   Rotation(String nameIn) {
/*     */     this.name = nameIn;
/*     */   }
/*     */   
/*     */   public Rotation add(Rotation rotation) {
/*     */     switch (rotation) {
/*     */       case null:
/*     */         switch (this) {
/*     */           case NONE:
/*     */             return CLOCKWISE_180;
/*     */           case CLOCKWISE_90:
/*     */             return COUNTERCLOCKWISE_90;
/*     */           case null:
/*     */             return NONE;
/*     */           case COUNTERCLOCKWISE_90:
/*     */             return CLOCKWISE_90;
/*     */         } 
/*     */       case COUNTERCLOCKWISE_90:
/*     */         switch (this) {
/*     */           case NONE:
/*     */             return COUNTERCLOCKWISE_90;
/*     */           case CLOCKWISE_90:
/*     */             return NONE;
/*     */           case null:
/*     */             return CLOCKWISE_90;
/*     */           case COUNTERCLOCKWISE_90:
/*     */             return CLOCKWISE_180;
/*     */         } 
/*     */       case CLOCKWISE_90:
/*     */         switch (this) {
/*     */           case NONE:
/*     */             return CLOCKWISE_90;
/*     */           case CLOCKWISE_90:
/*     */             return CLOCKWISE_180;
/*     */           case null:
/*     */             return COUNTERCLOCKWISE_90;
/*     */           case COUNTERCLOCKWISE_90:
/*     */             return NONE;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */     return this;
/*     */   }
/*     */   
/*     */   public EnumFacing rotate(EnumFacing facing) {
/*     */     if (facing.getAxis() == EnumFacing.Axis.Y)
/*     */       return facing; 
/*     */     switch (this) {
/*     */       case CLOCKWISE_90:
/*     */         return facing.rotateY();
/*     */       case null:
/*     */         return facing.getOpposite();
/*     */       case COUNTERCLOCKWISE_90:
/*     */         return facing.rotateYCCW();
/*     */     } 
/*     */     return facing;
/*     */   }
/*     */   
/*     */   public int rotate(int p_185833_1_, int p_185833_2_) {
/*     */     switch (this) {
/*     */       case CLOCKWISE_90:
/*     */         return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
/*     */       case null:
/*     */         return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
/*     */       case COUNTERCLOCKWISE_90:
/*     */         return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
/*     */     } 
/*     */     return p_185833_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\Rotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
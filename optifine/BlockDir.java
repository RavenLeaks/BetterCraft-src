/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public enum BlockDir
/*     */ {
/*   8 */   DOWN(EnumFacing.DOWN),
/*   9 */   UP(EnumFacing.UP),
/*  10 */   NORTH(EnumFacing.NORTH),
/*  11 */   SOUTH(EnumFacing.SOUTH),
/*  12 */   WEST(EnumFacing.WEST),
/*  13 */   EAST(EnumFacing.EAST),
/*  14 */   NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST),
/*  15 */   NORTH_EAST(EnumFacing.NORTH, EnumFacing.EAST),
/*  16 */   SOUTH_WEST(EnumFacing.SOUTH, EnumFacing.WEST),
/*  17 */   SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST),
/*  18 */   DOWN_NORTH(EnumFacing.DOWN, EnumFacing.NORTH),
/*  19 */   DOWN_SOUTH(EnumFacing.DOWN, EnumFacing.SOUTH),
/*  20 */   UP_NORTH(EnumFacing.UP, EnumFacing.NORTH),
/*  21 */   UP_SOUTH(EnumFacing.UP, EnumFacing.SOUTH),
/*  22 */   DOWN_WEST(EnumFacing.DOWN, EnumFacing.WEST),
/*  23 */   DOWN_EAST(EnumFacing.DOWN, EnumFacing.EAST),
/*  24 */   UP_WEST(EnumFacing.UP, EnumFacing.WEST),
/*  25 */   UP_EAST(EnumFacing.UP, EnumFacing.EAST);
/*     */   
/*     */   private EnumFacing facing1;
/*     */   
/*     */   private EnumFacing facing2;
/*     */   
/*     */   BlockDir(EnumFacing p_i12_3_) {
/*  32 */     this.facing1 = p_i12_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   BlockDir(EnumFacing p_i13_3_, EnumFacing p_i13_4_) {
/*  37 */     this.facing1 = p_i13_3_;
/*  38 */     this.facing2 = p_i13_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFacing1() {
/*  43 */     return this.facing1;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFacing2() {
/*  48 */     return this.facing2;
/*     */   }
/*     */ 
/*     */   
/*     */   BlockPos offset(BlockPos p_offset_1_) {
/*  53 */     p_offset_1_ = p_offset_1_.offset(this.facing1, 1);
/*     */     
/*  55 */     if (this.facing2 != null)
/*     */     {
/*  57 */       p_offset_1_ = p_offset_1_.offset(this.facing2, 1);
/*     */     }
/*     */     
/*  60 */     return p_offset_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffsetX() {
/*  65 */     int i = this.facing1.getFrontOffsetX();
/*     */     
/*  67 */     if (this.facing2 != null)
/*     */     {
/*  69 */       i += this.facing2.getFrontOffsetX();
/*     */     }
/*     */     
/*  72 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffsetY() {
/*  77 */     int i = this.facing1.getFrontOffsetY();
/*     */     
/*  79 */     if (this.facing2 != null)
/*     */     {
/*  81 */       i += this.facing2.getFrontOffsetY();
/*     */     }
/*     */     
/*  84 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffsetZ() {
/*  89 */     int i = this.facing1.getFrontOffsetZ();
/*     */     
/*  91 */     if (this.facing2 != null)
/*     */     {
/*  93 */       i += this.facing2.getFrontOffsetZ();
/*     */     }
/*     */     
/*  96 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDouble() {
/* 101 */     return (this.facing2 != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BlockDir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
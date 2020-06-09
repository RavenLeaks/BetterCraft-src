/*     */ package optifine;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class BlockPosM
/*     */   extends BlockPos
/*     */ {
/*     */   private int mx;
/*     */   private int my;
/*     */   private int mz;
/*     */   private int level;
/*     */   private BlockPosM[] facings;
/*     */   private boolean needsUpdate;
/*     */   
/*     */   public BlockPosM(int p_i16_1_, int p_i16_2_, int p_i16_3_) {
/*  20 */     this(p_i16_1_, p_i16_2_, p_i16_3_, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(double p_i17_1_, double p_i17_3_, double p_i17_5_) {
/*  25 */     this(MathHelper.floor(p_i17_1_), MathHelper.floor(p_i17_3_), MathHelper.floor(p_i17_5_));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM(int p_i18_1_, int p_i18_2_, int p_i18_3_, int p_i18_4_) {
/*  30 */     super(0, 0, 0);
/*  31 */     this.mx = p_i18_1_;
/*  32 */     this.my = p_i18_2_;
/*  33 */     this.mz = p_i18_3_;
/*  34 */     this.level = p_i18_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  42 */     return this.mx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  50 */     return this.my;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  58 */     return this.mz;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(int p_setXyz_1_, int p_setXyz_2_, int p_setXyz_3_) {
/*  63 */     this.mx = p_setXyz_1_;
/*  64 */     this.my = p_setXyz_2_;
/*  65 */     this.mz = p_setXyz_3_;
/*  66 */     this.needsUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXyz(double p_setXyz_1_, double p_setXyz_3_, double p_setXyz_5_) {
/*  71 */     setXyz(MathHelper.floor(p_setXyz_1_), MathHelper.floor(p_setXyz_3_), MathHelper.floor(p_setXyz_5_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/*  79 */     if (this.level <= 0)
/*     */     {
/*  81 */       return super.offset(facing, 1).toImmutable();
/*     */     }
/*     */ 
/*     */     
/*  85 */     if (this.facings == null)
/*     */     {
/*  87 */       this.facings = new BlockPosM[EnumFacing.VALUES.length];
/*     */     }
/*     */     
/*  90 */     if (this.needsUpdate)
/*     */     {
/*  92 */       update();
/*     */     }
/*     */     
/*  95 */     int i = facing.getIndex();
/*  96 */     BlockPosM blockposm = this.facings[i];
/*     */     
/*  98 */     if (blockposm == null) {
/*     */       
/* 100 */       int j = this.mx + facing.getFrontOffsetX();
/* 101 */       int k = this.my + facing.getFrontOffsetY();
/* 102 */       int l = this.mz + facing.getFrontOffsetZ();
/* 103 */       blockposm = new BlockPosM(j, k, l, this.level - 1);
/* 104 */       this.facings[i] = blockposm;
/*     */     } 
/*     */     
/* 107 */     return blockposm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 116 */     return (n == 1) ? offset(facing) : super.offset(facing, n).toImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   private void update() {
/* 121 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 123 */       BlockPosM blockposm = this.facings[i];
/*     */       
/* 125 */       if (blockposm != null) {
/*     */         
/* 127 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 128 */         int j = this.mx + enumfacing.getFrontOffsetX();
/* 129 */         int k = this.my + enumfacing.getFrontOffsetY();
/* 130 */         int l = this.mz + enumfacing.getFrontOffsetZ();
/* 131 */         blockposm.setXyz(j, k, l);
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     this.needsUpdate = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos toImmutable() {
/* 146 */     return new BlockPos(this.mx, this.my, this.mz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable getAllInBoxMutable(BlockPos p_getAllInBoxMutable_0_, BlockPos p_getAllInBoxMutable_1_) {
/* 151 */     final BlockPos blockpos = new BlockPos(Math.min(p_getAllInBoxMutable_0_.getX(), p_getAllInBoxMutable_1_.getX()), Math.min(p_getAllInBoxMutable_0_.getY(), p_getAllInBoxMutable_1_.getY()), Math.min(p_getAllInBoxMutable_0_.getZ(), p_getAllInBoxMutable_1_.getZ()));
/* 152 */     final BlockPos blockpos1 = new BlockPos(Math.max(p_getAllInBoxMutable_0_.getX(), p_getAllInBoxMutable_1_.getX()), Math.max(p_getAllInBoxMutable_0_.getY(), p_getAllInBoxMutable_1_.getY()), Math.max(p_getAllInBoxMutable_0_.getZ(), p_getAllInBoxMutable_1_.getZ()));
/* 153 */     return new Iterable()
/*     */       {
/*     */         public Iterator iterator()
/*     */         {
/* 157 */           return (Iterator)new AbstractIterator()
/*     */             {
/* 159 */               private BlockPosM theBlockPosM = null;
/*     */               
/*     */               protected BlockPosM computeNext0() {
/* 162 */                 if (this.theBlockPosM == null) {
/*     */                   
/* 164 */                   this.theBlockPosM = new BlockPosM(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 3);
/* 165 */                   return this.theBlockPosM;
/*     */                 } 
/* 167 */                 if (this.theBlockPosM.equals(blockpos1))
/*     */                 {
/* 169 */                   return (BlockPosM)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 173 */                 int i = this.theBlockPosM.getX();
/* 174 */                 int j = this.theBlockPosM.getY();
/* 175 */                 int k = this.theBlockPosM.getZ();
/*     */                 
/* 177 */                 if (i < blockpos1.getX()) {
/*     */                   
/* 179 */                   i++;
/*     */                 }
/* 181 */                 else if (j < blockpos1.getY()) {
/*     */                   
/* 183 */                   i = blockpos.getX();
/* 184 */                   j++;
/*     */                 }
/* 186 */                 else if (k < blockpos1.getZ()) {
/*     */                   
/* 188 */                   i = blockpos.getX();
/* 189 */                   j = blockpos.getY();
/* 190 */                   k++;
/*     */                 } 
/*     */                 
/* 193 */                 this.theBlockPosM.setXyz(i, j, k);
/* 194 */                 return this.theBlockPosM;
/*     */               }
/*     */ 
/*     */               
/*     */               protected Object computeNext() {
/* 199 */                 return computeNext0();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BlockPosM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
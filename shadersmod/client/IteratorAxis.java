/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import optifine.BlockPosM;
/*     */ 
/*     */ public class IteratorAxis
/*     */   implements Iterator<BlockPos>
/*     */ {
/*     */   private double yDelta;
/*     */   private double zDelta;
/*     */   private int xStart;
/*     */   private int xEnd;
/*     */   private double yStart;
/*     */   private double yEnd;
/*     */   private double zStart;
/*     */   private double zEnd;
/*     */   private int xNext;
/*     */   private double yNext;
/*     */   private double zNext;
/*  22 */   private BlockPosM pos = new BlockPosM(0, 0, 0);
/*     */   
/*     */   private boolean hasNext = false;
/*     */   
/*     */   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
/*  27 */     this.yDelta = yDelta;
/*  28 */     this.zDelta = zDelta;
/*  29 */     this.xStart = posStart.getX();
/*  30 */     this.xEnd = posEnd.getX();
/*  31 */     this.yStart = posStart.getY();
/*  32 */     this.yEnd = posEnd.getY() - 0.5D;
/*  33 */     this.zStart = posStart.getZ();
/*  34 */     this.zEnd = posEnd.getZ() - 0.5D;
/*  35 */     this.xNext = this.xStart;
/*  36 */     this.yNext = this.yStart;
/*  37 */     this.zNext = this.zStart;
/*  38 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  43 */     return this.hasNext;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos next() {
/*  48 */     if (!this.hasNext)
/*     */     {
/*  50 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*  54 */     this.pos.setXyz(this.xNext, this.yNext, this.zNext);
/*  55 */     nextPos();
/*  56 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*  57 */     return (BlockPos)this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextPos() {
/*  63 */     this.zNext++;
/*     */     
/*  65 */     if (this.zNext >= this.zEnd) {
/*     */       
/*  67 */       this.zNext = this.zStart;
/*  68 */       this.yNext++;
/*     */       
/*  70 */       if (this.yNext >= this.yEnd) {
/*     */         
/*  72 */         this.yNext = this.yStart;
/*  73 */         this.yStart += this.yDelta;
/*  74 */         this.yEnd += this.yDelta;
/*  75 */         this.yNext = this.yStart;
/*  76 */         this.zStart += this.zDelta;
/*  77 */         this.zEnd += this.zDelta;
/*  78 */         this.zNext = this.zStart;
/*  79 */         this.xNext++;
/*     */         
/*  81 */         if (this.xNext >= this.xEnd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/*  91 */     throw new RuntimeException("Not implemented");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  96 */     BlockPos blockpos = new BlockPos(-2, 10, 20);
/*  97 */     BlockPos blockpos1 = new BlockPos(2, 12, 22);
/*  98 */     double d0 = -0.5D;
/*  99 */     double d1 = 0.5D;
/* 100 */     IteratorAxis iteratoraxis = new IteratorAxis(blockpos, blockpos1, d0, d1);
/* 101 */     System.out.println("Start: " + blockpos + ", end: " + blockpos1 + ", yDelta: " + d0 + ", zDelta: " + d1);
/*     */     
/* 103 */     while (iteratoraxis.hasNext()) {
/*     */       
/* 105 */       BlockPos blockpos2 = iteratoraxis.next();
/* 106 */       System.out.println((String)blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\IteratorAxis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
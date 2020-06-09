/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import optifine.BlockPosM;
/*     */ 
/*     */ public class Iterator3d
/*     */   implements Iterator<BlockPos>
/*     */ {
/*     */   private IteratorAxis iteratorAxis;
/*  12 */   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
/*  13 */   private int axis = 0;
/*     */   
/*     */   private int kX;
/*     */   private int kY;
/*     */   private int kZ;
/*     */   private static final int AXIS_X = 0;
/*     */   private static final int AXIS_Y = 1;
/*     */   private static final int AXIS_Z = 2;
/*     */   
/*     */   public Iterator3d(BlockPos posStart, BlockPos posEnd, int width, int height) {
/*  23 */     boolean flag = (posStart.getX() > posEnd.getX());
/*  24 */     boolean flag1 = (posStart.getY() > posEnd.getY());
/*  25 */     boolean flag2 = (posStart.getZ() > posEnd.getZ());
/*  26 */     posStart = reverseCoord(posStart, flag, flag1, flag2);
/*  27 */     posEnd = reverseCoord(posEnd, flag, flag1, flag2);
/*  28 */     this.kX = flag ? -1 : 1;
/*  29 */     this.kY = flag1 ? -1 : 1;
/*  30 */     this.kZ = flag2 ? -1 : 1;
/*  31 */     Vec3d vec3d = new Vec3d((posEnd.getX() - posStart.getX()), (posEnd.getY() - posStart.getY()), (posEnd.getZ() - posStart.getZ()));
/*  32 */     Vec3d vec3d1 = vec3d.normalize();
/*  33 */     Vec3d vec3d2 = new Vec3d(1.0D, 0.0D, 0.0D);
/*  34 */     double d0 = vec3d1.dotProduct(vec3d2);
/*  35 */     double d1 = Math.abs(d0);
/*  36 */     Vec3d vec3d3 = new Vec3d(0.0D, 1.0D, 0.0D);
/*  37 */     double d2 = vec3d1.dotProduct(vec3d3);
/*  38 */     double d3 = Math.abs(d2);
/*  39 */     Vec3d vec3d4 = new Vec3d(0.0D, 0.0D, 1.0D);
/*  40 */     double d4 = vec3d1.dotProduct(vec3d4);
/*  41 */     double d5 = Math.abs(d4);
/*     */     
/*  43 */     if (d5 >= d3 && d5 >= d1) {
/*     */       
/*  45 */       this.axis = 2;
/*  46 */       BlockPos blockpos3 = new BlockPos(posStart.getZ(), posStart.getY() - width, posStart.getX() - height);
/*  47 */       BlockPos blockpos5 = new BlockPos(posEnd.getZ(), posStart.getY() + width + 1, posStart.getX() + height + 1);
/*  48 */       int k = posEnd.getZ() - posStart.getZ();
/*  49 */       double d9 = (posEnd.getY() - posStart.getY()) / 1.0D * k;
/*  50 */       double d11 = (posEnd.getX() - posStart.getX()) / 1.0D * k;
/*  51 */       this.iteratorAxis = new IteratorAxis(blockpos3, blockpos5, d9, d11);
/*     */     }
/*  53 */     else if (d3 >= d1 && d3 >= d5) {
/*     */       
/*  55 */       this.axis = 1;
/*  56 */       BlockPos blockpos2 = new BlockPos(posStart.getY(), posStart.getX() - width, posStart.getZ() - height);
/*  57 */       BlockPos blockpos4 = new BlockPos(posEnd.getY(), posStart.getX() + width + 1, posStart.getZ() + height + 1);
/*  58 */       int j = posEnd.getY() - posStart.getY();
/*  59 */       double d8 = (posEnd.getX() - posStart.getX()) / 1.0D * j;
/*  60 */       double d10 = (posEnd.getZ() - posStart.getZ()) / 1.0D * j;
/*  61 */       this.iteratorAxis = new IteratorAxis(blockpos2, blockpos4, d8, d10);
/*     */     }
/*     */     else {
/*     */       
/*  65 */       this.axis = 0;
/*  66 */       BlockPos blockpos = new BlockPos(posStart.getX(), posStart.getY() - width, posStart.getZ() - height);
/*  67 */       BlockPos blockpos1 = new BlockPos(posEnd.getX(), posStart.getY() + width + 1, posStart.getZ() + height + 1);
/*  68 */       int i = posEnd.getX() - posStart.getX();
/*  69 */       double d6 = (posEnd.getY() - posStart.getY()) / 1.0D * i;
/*  70 */       double d7 = (posEnd.getZ() - posStart.getZ()) / 1.0D * i;
/*  71 */       this.iteratorAxis = new IteratorAxis(blockpos, blockpos1, d6, d7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos reverseCoord(BlockPos pos, boolean revX, boolean revY, boolean revZ) {
/*  77 */     if (revX)
/*     */     {
/*  79 */       pos = new BlockPos(-pos.getX(), pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  82 */     if (revY)
/*     */     {
/*  84 */       pos = new BlockPos(pos.getX(), -pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  87 */     if (revZ)
/*     */     {
/*  89 */       pos = new BlockPos(pos.getX(), pos.getY(), -pos.getZ());
/*     */     }
/*     */     
/*  92 */     return pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  97 */     return this.iteratorAxis.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos next() {
/* 102 */     BlockPos blockpos = this.iteratorAxis.next();
/*     */     
/* 104 */     switch (this.axis) {
/*     */       
/*     */       case 0:
/* 107 */         this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/* 108 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 1:
/* 111 */         this.blockPos.setXyz(blockpos.getY() * this.kX, blockpos.getX() * this.kY, blockpos.getZ() * this.kZ);
/* 112 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 2:
/* 115 */         this.blockPos.setXyz(blockpos.getZ() * this.kX, blockpos.getY() * this.kY, blockpos.getX() * this.kZ);
/* 116 */         return (BlockPos)this.blockPos;
/*     */     } 
/*     */     
/* 119 */     this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/* 120 */     return (BlockPos)this.blockPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 126 */     throw new RuntimeException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 131 */     BlockPos blockpos = new BlockPos(10, 20, 30);
/* 132 */     BlockPos blockpos1 = new BlockPos(30, 40, 20);
/* 133 */     Iterator3d iterator3d = new Iterator3d(blockpos, blockpos1, 1, 1);
/*     */     
/* 135 */     while (iterator3d.hasNext()) {
/*     */       
/* 137 */       BlockPos blockpos2 = iterator3d.next();
/* 138 */       System.out.println((String)blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\Iterator3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Rotation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ @Immutable
/*     */ public class BlockPos
/*     */   extends Vec3i {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*  20 */   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
/*  21 */   private static final int NUM_X_BITS = 1 + MathHelper.log2(MathHelper.smallestEncompassingPowerOfTwo(30000000));
/*  22 */   private static final int NUM_Z_BITS = NUM_X_BITS;
/*  23 */   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
/*  24 */   private static final int Y_SHIFT = 0 + NUM_Z_BITS;
/*  25 */   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
/*  26 */   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
/*  27 */   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
/*  28 */   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
/*     */ 
/*     */   
/*     */   public BlockPos(int x, int y, int z) {
/*  32 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(double x, double y, double z) {
/*  37 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Entity source) {
/*  42 */     this(source.posX, source.posY, source.posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3d vec) {
/*  47 */     this(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos(Vec3i source) {
/*  52 */     this(source.getX(), source.getY(), source.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(double x, double y, double z) {
/*  60 */     return (x == 0.0D && y == 0.0D && z == 0.0D) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(int x, int y, int z) {
/*  68 */     return (x == 0 && y == 0 && z == 0) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(Vec3i vec) {
/*  76 */     return add(vec.getX(), vec.getY(), vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos subtract(Vec3i vec) {
/*  84 */     return add(-vec.getX(), -vec.getY(), -vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up() {
/*  92 */     return up(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up(int n) {
/* 100 */     return offset(EnumFacing.UP, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down() {
/* 108 */     return down(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down(int n) {
/* 116 */     return offset(EnumFacing.DOWN, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north() {
/* 124 */     return north(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north(int n) {
/* 132 */     return offset(EnumFacing.NORTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south() {
/* 140 */     return south(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south(int n) {
/* 148 */     return offset(EnumFacing.SOUTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west() {
/* 156 */     return west(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west(int n) {
/* 164 */     return offset(EnumFacing.WEST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east() {
/* 172 */     return east(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east(int n) {
/* 180 */     return offset(EnumFacing.EAST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/* 188 */     return offset(facing, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 196 */     return (n == 0) ? this : new BlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n, getZ() + facing.getFrontOffsetZ() * n);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos func_190942_a(Rotation p_190942_1_) {
/* 201 */     switch (p_190942_1_) {
/*     */ 
/*     */       
/*     */       default:
/* 205 */         return this;
/*     */       
/*     */       case CLOCKWISE_90:
/* 208 */         return new BlockPos(-getZ(), getY(), getX());
/*     */       
/*     */       case null:
/* 211 */         return new BlockPos(-getX(), getY(), -getZ());
/*     */       case COUNTERCLOCKWISE_90:
/*     */         break;
/* 214 */     }  return new BlockPos(getZ(), getY(), -getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos crossProduct(Vec3i vec) {
/* 223 */     return new BlockPos(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toLong() {
/* 231 */     return (getX() & X_MASK) << X_SHIFT | (getY() & Y_MASK) << Y_SHIFT | (getZ() & Z_MASK) << 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos fromLong(long serialized) {
/* 239 */     int i = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
/* 240 */     int j = (int)(serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
/* 241 */     int k = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
/* 242 */     return new BlockPos(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
/* 247 */     return func_191532_a(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()), Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> func_191532_a(final int p_191532_0_, final int p_191532_1_, final int p_191532_2_, final int p_191532_3_, final int p_191532_4_, final int p_191532_5_) {
/* 252 */     return new Iterable<BlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos> iterator()
/*     */         {
/* 256 */           return (Iterator<BlockPos>)new AbstractIterator<BlockPos>()
/*     */             {
/*     */               private boolean field_191534_b = true;
/*     */               private int field_191535_c;
/*     */               private int field_191536_d;
/*     */               private int field_191537_e;
/*     */               
/*     */               protected BlockPos computeNext() {
/* 264 */                 if (this.field_191534_b) {
/*     */                   
/* 266 */                   this.field_191534_b = false;
/* 267 */                   this.field_191535_c = p_191532_0_;
/* 268 */                   this.field_191536_d = p_191532_1_;
/* 269 */                   this.field_191537_e = p_191532_2_;
/* 270 */                   return new BlockPos(p_191532_0_, p_191532_1_, p_191532_2_);
/*     */                 } 
/* 272 */                 if (this.field_191535_c == p_191532_3_ && this.field_191536_d == p_191532_4_ && this.field_191537_e == p_191532_5_)
/*     */                 {
/* 274 */                   return (BlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 278 */                 if (this.field_191535_c < p_191532_3_) {
/*     */                   
/* 280 */                   this.field_191535_c++;
/*     */                 }
/* 282 */                 else if (this.field_191536_d < p_191532_4_) {
/*     */                   
/* 284 */                   this.field_191535_c = p_191532_0_;
/* 285 */                   this.field_191536_d++;
/*     */                 }
/* 287 */                 else if (this.field_191537_e < p_191532_5_) {
/*     */                   
/* 289 */                   this.field_191535_c = p_191532_0_;
/* 290 */                   this.field_191536_d = p_191532_1_;
/* 291 */                   this.field_191537_e++;
/*     */                 } 
/*     */                 
/* 294 */                 return new BlockPos(this.field_191535_c, this.field_191536_d, this.field_191537_e);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos toImmutable() {
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 315 */     return func_191531_b(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()), Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<MutableBlockPos> func_191531_b(final int p_191531_0_, final int p_191531_1_, final int p_191531_2_, final int p_191531_3_, final int p_191531_4_, final int p_191531_5_) {
/* 320 */     return new Iterable<MutableBlockPos>()
/*     */       {
/*     */         public Iterator<BlockPos.MutableBlockPos> iterator()
/*     */         {
/* 324 */           return (Iterator<BlockPos.MutableBlockPos>)new AbstractIterator<BlockPos.MutableBlockPos>()
/*     */             {
/*     */               private BlockPos.MutableBlockPos theBlockPos;
/*     */               
/*     */               protected BlockPos.MutableBlockPos computeNext() {
/* 329 */                 if (this.theBlockPos == null) {
/*     */                   
/* 331 */                   this.theBlockPos = new BlockPos.MutableBlockPos(p_191531_0_, p_191531_1_, p_191531_2_);
/* 332 */                   return this.theBlockPos;
/*     */                 } 
/* 334 */                 if (this.theBlockPos.x == p_191531_3_ && this.theBlockPos.y == p_191531_4_ && this.theBlockPos.z == p_191531_5_)
/*     */                 {
/* 336 */                   return (BlockPos.MutableBlockPos)endOfData();
/*     */                 }
/*     */ 
/*     */                 
/* 340 */                 if (this.theBlockPos.x < p_191531_3_) {
/*     */                   
/* 342 */                   this.theBlockPos.x++;
/*     */                 }
/* 344 */                 else if (this.theBlockPos.y < p_191531_4_) {
/*     */                   
/* 346 */                   this.theBlockPos.x = p_191531_0_;
/* 347 */                   this.theBlockPos.y++;
/*     */                 }
/* 349 */                 else if (this.theBlockPos.z < p_191531_5_) {
/*     */                   
/* 351 */                   this.theBlockPos.x = p_191531_0_;
/* 352 */                   this.theBlockPos.y = p_191531_1_;
/* 353 */                   this.theBlockPos.z++;
/*     */                 } 
/*     */                 
/* 356 */                 return this.theBlockPos;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MutableBlockPos
/*     */     extends BlockPos
/*     */   {
/*     */     protected int x;
/*     */     protected int y;
/*     */     protected int z;
/*     */     
/*     */     public MutableBlockPos() {
/* 372 */       this(0, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos(BlockPos pos) {
/* 377 */       this(pos.getX(), pos.getY(), pos.getZ());
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos(int x_, int y_, int z_) {
/* 382 */       super(0, 0, 0);
/* 383 */       this.x = x_;
/* 384 */       this.y = y_;
/* 385 */       this.z = z_;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos add(double x, double y, double z) {
/* 390 */       return super.add(x, y, z).toImmutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos add(int x, int y, int z) {
/* 395 */       return super.add(x, y, z).toImmutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos offset(EnumFacing facing, int n) {
/* 400 */       return super.offset(facing, n).toImmutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos func_190942_a(Rotation p_190942_1_) {
/* 405 */       return super.func_190942_a(p_190942_1_).toImmutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getX() {
/* 410 */       return this.x;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getY() {
/* 415 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getZ() {
/* 420 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setPos(int xIn, int yIn, int zIn) {
/* 425 */       this.x = xIn;
/* 426 */       this.y = yIn;
/* 427 */       this.z = zIn;
/* 428 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setPos(Entity entityIn) {
/* 433 */       return setPos(entityIn.posX, entityIn.posY, entityIn.posZ);
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setPos(double xIn, double yIn, double zIn) {
/* 438 */       return setPos(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setPos(Vec3i vec) {
/* 443 */       return setPos(vec.getX(), vec.getY(), vec.getZ());
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos move(EnumFacing facing) {
/* 448 */       return move(facing, 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos move(EnumFacing facing, int p_189534_2_) {
/* 453 */       return setPos(this.x + facing.getFrontOffsetX() * p_189534_2_, this.y + facing.getFrontOffsetY() * p_189534_2_, this.z + facing.getFrontOffsetZ() * p_189534_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setY(int yIn) {
/* 458 */       this.y = yIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos toImmutable() {
/* 463 */       return new BlockPos(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class PooledMutableBlockPos
/*     */     extends MutableBlockPos {
/*     */     private boolean released;
/* 470 */     private static final List<PooledMutableBlockPos> POOL = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     private PooledMutableBlockPos(int xIn, int yIn, int zIn) {
/* 474 */       super(xIn, yIn, zIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public static PooledMutableBlockPos retain() {
/* 479 */       return retain(0, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public static PooledMutableBlockPos retain(double xIn, double yIn, double zIn) {
/* 484 */       return retain(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
/*     */     }
/*     */ 
/*     */     
/*     */     public static PooledMutableBlockPos retain(Vec3i vec) {
/* 489 */       return retain(vec.getX(), vec.getY(), vec.getZ());
/*     */     }
/*     */ 
/*     */     
/*     */     public static PooledMutableBlockPos retain(int xIn, int yIn, int zIn) {
/* 494 */       synchronized (POOL) {
/*     */         
/* 496 */         if (!POOL.isEmpty()) {
/*     */           
/* 498 */           PooledMutableBlockPos blockpos$pooledmutableblockpos = POOL.remove(POOL.size() - 1);
/*     */           
/* 500 */           if (blockpos$pooledmutableblockpos != null && blockpos$pooledmutableblockpos.released) {
/*     */             
/* 502 */             blockpos$pooledmutableblockpos.released = false;
/* 503 */             blockpos$pooledmutableblockpos.setPos(xIn, yIn, zIn);
/* 504 */             return blockpos$pooledmutableblockpos;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 509 */       return new PooledMutableBlockPos(xIn, yIn, zIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public void release() {
/* 514 */       synchronized (POOL) {
/*     */         
/* 516 */         if (POOL.size() < 100)
/*     */         {
/* 518 */           POOL.add(this);
/*     */         }
/*     */         
/* 521 */         this.released = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos setPos(int xIn, int yIn, int zIn) {
/* 527 */       if (this.released) {
/*     */         
/* 529 */         BlockPos.LOGGER.error("PooledMutableBlockPosition modified after it was released.", new Throwable());
/* 530 */         this.released = false;
/*     */       } 
/*     */       
/* 533 */       return (PooledMutableBlockPos)super.setPos(xIn, yIn, zIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos setPos(Entity entityIn) {
/* 538 */       return (PooledMutableBlockPos)super.setPos(entityIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos setPos(double xIn, double yIn, double zIn) {
/* 543 */       return (PooledMutableBlockPos)super.setPos(xIn, yIn, zIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos setPos(Vec3i vec) {
/* 548 */       return (PooledMutableBlockPos)super.setPos(vec);
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos move(EnumFacing facing) {
/* 553 */       return (PooledMutableBlockPos)super.move(facing);
/*     */     }
/*     */ 
/*     */     
/*     */     public PooledMutableBlockPos move(EnumFacing facing, int p_189534_2_) {
/* 558 */       return (PooledMutableBlockPos)super.move(facing, p_189534_2_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\BlockPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
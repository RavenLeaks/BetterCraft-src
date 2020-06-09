/*     */ package net.minecraft.block.state.pattern;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockPattern
/*     */ {
/*     */   private final Predicate<BlockWorldState>[][][] blockMatches;
/*     */   private final int fingerLength;
/*     */   private final int thumbLength;
/*     */   private final int palmLength;
/*     */   
/*     */   public BlockPattern(Predicate[][][] predicatesIn) {
/*  23 */     this.blockMatches = (Predicate<BlockWorldState>[][][])predicatesIn;
/*  24 */     this.fingerLength = predicatesIn.length;
/*     */     
/*  26 */     if (this.fingerLength > 0) {
/*     */       
/*  28 */       this.thumbLength = (predicatesIn[0]).length;
/*     */       
/*  30 */       if (this.thumbLength > 0)
/*     */       {
/*  32 */         this.palmLength = (predicatesIn[0][0]).length;
/*     */       }
/*     */       else
/*     */       {
/*  36 */         this.palmLength = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  41 */       this.thumbLength = 0;
/*  42 */       this.palmLength = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFingerLength() {
/*  48 */     return this.fingerLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThumbLength() {
/*  53 */     return this.thumbLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPalmLength() {
/*  58 */     return this.palmLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PatternHelper checkPatternAt(BlockPos pos, EnumFacing finger, EnumFacing thumb, LoadingCache<BlockPos, BlockWorldState> lcache) {
/*  68 */     for (int i = 0; i < this.palmLength; i++) {
/*     */       
/*  70 */       for (int j = 0; j < this.thumbLength; j++) {
/*     */         
/*  72 */         for (int k = 0; k < this.fingerLength; k++) {
/*     */           
/*  74 */           if (!this.blockMatches[k][j][i].apply(lcache.getUnchecked(translateOffset(pos, finger, thumb, i, j, k))))
/*     */           {
/*  76 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return new PatternHelper(pos, finger, thumb, lcache, this.palmLength, this.thumbLength, this.fingerLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PatternHelper match(World worldIn, BlockPos pos) {
/*  93 */     LoadingCache<BlockPos, BlockWorldState> loadingcache = createLoadingCache(worldIn, false);
/*  94 */     int i = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
/*     */     
/*  96 */     for (BlockPos blockpos : BlockPos.getAllInBox(pos, pos.add(i - 1, i - 1, i - 1))) {
/*     */       byte b; int j; EnumFacing[] arrayOfEnumFacing;
/*  98 */       for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b]; byte b1; int k;
/*     */         EnumFacing[] arrayOfEnumFacing1;
/* 100 */         for (k = (arrayOfEnumFacing1 = EnumFacing.values()).length, b1 = 0; b1 < k; ) { EnumFacing enumfacing1 = arrayOfEnumFacing1[b1];
/*     */           
/* 102 */           if (enumfacing1 != enumfacing && enumfacing1 != enumfacing.getOpposite()) {
/*     */             
/* 104 */             PatternHelper blockpattern$patternhelper = checkPatternAt(blockpos, enumfacing, enumfacing1, loadingcache);
/*     */             
/* 106 */             if (blockpattern$patternhelper != null)
/*     */             {
/* 108 */               return blockpattern$patternhelper; } 
/*     */           } 
/*     */           b1++; }
/*     */         
/*     */         b++; }
/*     */     
/*     */     } 
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static LoadingCache<BlockPos, BlockWorldState> createLoadingCache(World worldIn, boolean forceLoadIn) {
/* 120 */     return CacheBuilder.newBuilder().build(new CacheLoader(worldIn, forceLoadIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static BlockPos translateOffset(BlockPos pos, EnumFacing finger, EnumFacing thumb, int palmOffset, int thumbOffset, int fingerOffset) {
/* 129 */     if (finger != thumb && finger != thumb.getOpposite()) {
/*     */       
/* 131 */       Vec3i vec3i = new Vec3i(finger.getFrontOffsetX(), finger.getFrontOffsetY(), finger.getFrontOffsetZ());
/* 132 */       Vec3i vec3i1 = new Vec3i(thumb.getFrontOffsetX(), thumb.getFrontOffsetY(), thumb.getFrontOffsetZ());
/* 133 */       Vec3i vec3i2 = vec3i.crossProduct(vec3i1);
/* 134 */       return pos.add(vec3i1.getX() * -thumbOffset + vec3i2.getX() * palmOffset + vec3i.getX() * fingerOffset, vec3i1.getY() * -thumbOffset + vec3i2.getY() * palmOffset + vec3i.getY() * fingerOffset, vec3i1.getZ() * -thumbOffset + vec3i2.getZ() * palmOffset + vec3i.getZ() * fingerOffset);
/*     */     } 
/*     */ 
/*     */     
/* 138 */     throw new IllegalArgumentException("Invalid forwards & up combination");
/*     */   }
/*     */ 
/*     */   
/*     */   static class CacheLoader
/*     */     extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState>
/*     */   {
/*     */     private final World world;
/*     */     private final boolean forceLoad;
/*     */     
/*     */     public CacheLoader(World worldIn, boolean forceLoadIn) {
/* 149 */       this.world = worldIn;
/* 150 */       this.forceLoad = forceLoadIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockWorldState load(BlockPos p_load_1_) throws Exception {
/* 155 */       return new BlockWorldState(this.world, p_load_1_, this.forceLoad);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PatternHelper
/*     */   {
/*     */     private final BlockPos frontTopLeft;
/*     */     private final EnumFacing forwards;
/*     */     private final EnumFacing up;
/*     */     private final LoadingCache<BlockPos, BlockWorldState> lcache;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int depth;
/*     */     
/*     */     public PatternHelper(BlockPos posIn, EnumFacing fingerIn, EnumFacing thumbIn, LoadingCache<BlockPos, BlockWorldState> lcacheIn, int p_i46378_5_, int p_i46378_6_, int p_i46378_7_) {
/* 171 */       this.frontTopLeft = posIn;
/* 172 */       this.forwards = fingerIn;
/* 173 */       this.up = thumbIn;
/* 174 */       this.lcache = lcacheIn;
/* 175 */       this.width = p_i46378_5_;
/* 176 */       this.height = p_i46378_6_;
/* 177 */       this.depth = p_i46378_7_;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getFrontTopLeft() {
/* 182 */       return this.frontTopLeft;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getForwards() {
/* 187 */       return this.forwards;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getUp() {
/* 192 */       return this.up;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 197 */       return this.width;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 202 */       return this.height;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockWorldState translateOffset(int palmOffset, int thumbOffset, int fingerOffset) {
/* 207 */       return (BlockWorldState)this.lcache.getUnchecked(BlockPattern.translateOffset(this.frontTopLeft, getForwards(), getUp(), palmOffset, thumbOffset, fingerOffset));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 212 */       return MoreObjects.toStringHelper(this).add("up", this.up).add("forwards", this.forwards).add("frontTopLeft", this.frontTopLeft).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\pattern\BlockPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
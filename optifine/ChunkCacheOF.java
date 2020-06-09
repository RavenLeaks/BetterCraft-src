/*     */ package optifine;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class ChunkCacheOF
/*     */   implements IBlockAccess {
/*     */   private ChunkCache chunkCache;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*  22 */   private static ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
/*  23 */   private static ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
/*     */   
/*     */   private static final int ARRAY_SIZE = 8000;
/*     */   
/*     */   public ChunkCacheOF(ChunkCache p_i22_1_, BlockPos p_i22_2_, int p_i22_3_) {
/*  28 */     this.chunkCache = p_i22_1_;
/*  29 */     this.posX = p_i22_2_.getX() - p_i22_3_;
/*  30 */     this.posY = p_i22_2_.getY() - p_i22_3_;
/*  31 */     this.posZ = p_i22_2_.getZ() - p_i22_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  36 */     if (this.combinedLights == null) {
/*     */       
/*  38 */       int k = this.chunkCache.getCombinedLight(pos, lightValue);
/*     */       
/*  40 */       if (Config.isDynamicLights() && !getBlockState(pos).isOpaqueCube())
/*     */       {
/*  42 */         k = DynamicLights.getCombinedLight(pos, k);
/*     */       }
/*     */       
/*  45 */       return k;
/*     */     } 
/*     */ 
/*     */     
/*  49 */     int i = getPositionIndex(pos);
/*     */     
/*  51 */     if (i >= 0 && i < this.combinedLights.length) {
/*     */       
/*  53 */       int j = this.combinedLights[i];
/*     */       
/*  55 */       if (j == -1) {
/*     */         
/*  57 */         j = this.chunkCache.getCombinedLight(pos, lightValue);
/*     */         
/*  59 */         if (Config.isDynamicLights() && !getBlockState(pos).isOpaqueCube())
/*     */         {
/*  61 */           j = DynamicLights.getCombinedLight(pos, j);
/*     */         }
/*     */         
/*  64 */         this.combinedLights[i] = j;
/*     */       } 
/*     */       
/*  67 */       return j;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     return this.chunkCache.getCombinedLight(pos, lightValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  78 */     if (this.blockStates == null)
/*     */     {
/*  80 */       return this.chunkCache.getBlockState(pos);
/*     */     }
/*     */ 
/*     */     
/*  84 */     int i = getPositionIndex(pos);
/*     */     
/*  86 */     if (i >= 0 && i < this.blockStates.length) {
/*     */       
/*  88 */       IBlockState iblockstate = this.blockStates[i];
/*     */       
/*  90 */       if (iblockstate == null) {
/*     */         
/*  92 */         iblockstate = this.chunkCache.getBlockState(pos);
/*  93 */         this.blockStates[i] = iblockstate;
/*     */       } 
/*     */       
/*  96 */       return iblockstate;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return this.chunkCache.getBlockState(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPositionIndex(BlockPos p_getPositionIndex_1_) {
/* 107 */     int i = p_getPositionIndex_1_.getX() - this.posX;
/* 108 */     int j = p_getPositionIndex_1_.getY() - this.posY;
/* 109 */     int k = p_getPositionIndex_1_.getZ() - this.posZ;
/* 110 */     return i * 400 + k * 20 + j;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderStart() {
/* 115 */     if (this.combinedLights == null)
/*     */     {
/* 117 */       this.combinedLights = (int[])cacheCombinedLights.allocate(8000);
/*     */     }
/*     */     
/* 120 */     Arrays.fill(this.combinedLights, -1);
/*     */     
/* 122 */     if (this.blockStates == null)
/*     */     {
/* 124 */       this.blockStates = (IBlockState[])cacheBlockStates.allocate(8000);
/*     */     }
/*     */     
/* 127 */     Arrays.fill((Object[])this.blockStates, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFinish() {
/* 132 */     cacheCombinedLights.free(this.combinedLights);
/* 133 */     this.combinedLights = null;
/* 134 */     cacheBlockStates.free(this.blockStates);
/* 135 */     this.blockStates = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 140 */     return this.chunkCache.extendedLevelsInChunkCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome getBiome(BlockPos pos) {
/* 145 */     return this.chunkCache.getBiome(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 150 */     return this.chunkCache.getStrongPower(pos, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/* 155 */     return this.chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos p_getTileEntity_1_, Chunk.EnumCreateEntityType p_getTileEntity_2_) {
/* 160 */     return this.chunkCache.getTileEntity(p_getTileEntity_1_, p_getTileEntity_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 165 */     return this.chunkCache.getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 174 */     return this.chunkCache.isAirBlock(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(BlockPos p_isSideSolid_1_, EnumFacing p_isSideSolid_2_, boolean p_isSideSolid_3_) {
/* 179 */     return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, new Object[] { p_isSideSolid_1_, p_isSideSolid_2_, Boolean.valueOf(p_isSideSolid_3_) });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ChunkCacheOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
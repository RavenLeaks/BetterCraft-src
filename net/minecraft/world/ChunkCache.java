/*     */ package net.minecraft.world;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCache
/*     */   implements IBlockAccess
/*     */ {
/*     */   protected int chunkX;
/*     */   protected int chunkZ;
/*     */   protected Chunk[][] chunkArray;
/*     */   protected boolean hasExtendedLevels;
/*     */   protected World worldObj;
/*     */   
/*     */   public ChunkCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  27 */     this.worldObj = worldIn;
/*  28 */     this.chunkX = posFromIn.getX() - subIn >> 4;
/*  29 */     this.chunkZ = posFromIn.getZ() - subIn >> 4;
/*  30 */     int i = posToIn.getX() + subIn >> 4;
/*  31 */     int j = posToIn.getZ() + subIn >> 4;
/*  32 */     this.chunkArray = new Chunk[i - this.chunkX + 1][j - this.chunkZ + 1];
/*  33 */     this.hasExtendedLevels = true;
/*     */     
/*  35 */     for (int k = this.chunkX; k <= i; k++) {
/*     */       
/*  37 */       for (int l = this.chunkZ; l <= j; l++)
/*     */       {
/*  39 */         this.chunkArray[k - this.chunkX][l - this.chunkZ] = worldIn.getChunkFromChunkCoords(k, l);
/*     */       }
/*     */     } 
/*     */     
/*  43 */     for (int i1 = posFromIn.getX() >> 4; i1 <= posToIn.getX() >> 4; i1++) {
/*     */       
/*  45 */       for (int j1 = posFromIn.getZ() >> 4; j1 <= posToIn.getZ() >> 4; j1++) {
/*     */         
/*  47 */         Chunk chunk = this.chunkArray[i1 - this.chunkX][j1 - this.chunkZ];
/*     */         
/*  49 */         if (chunk != null && !chunk.getAreLevelsEmpty(posFromIn.getY(), posToIn.getY()))
/*     */         {
/*  51 */           this.hasExtendedLevels = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedLevelsInChunkCache() {
/*  62 */     return this.hasExtendedLevels;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/*  68 */     return getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TileEntity getTileEntity(BlockPos p_190300_1_, Chunk.EnumCreateEntityType p_190300_2_) {
/*  74 */     int i = (p_190300_1_.getX() >> 4) - this.chunkX;
/*  75 */     int j = (p_190300_1_.getZ() >> 4) - this.chunkZ;
/*  76 */     return this.chunkArray[i][j].getTileEntity(p_190300_1_, p_190300_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  81 */     int i = getLightForExt(EnumSkyBlock.SKY, pos);
/*  82 */     int j = getLightForExt(EnumSkyBlock.BLOCK, pos);
/*     */     
/*  84 */     if (j < lightValue)
/*     */     {
/*  86 */       j = lightValue;
/*     */     }
/*     */     
/*  89 */     return i << 20 | j << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  94 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*     */       
/*  96 */       int i = (pos.getX() >> 4) - this.chunkX;
/*  97 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/*     */       
/*  99 */       if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < (this.chunkArray[i]).length) {
/*     */         
/* 101 */         Chunk chunk = this.chunkArray[i][j];
/*     */         
/* 103 */         if (chunk != null)
/*     */         {
/* 105 */           return chunk.getBlockState(pos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return Blocks.AIR.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome getBiome(BlockPos pos) {
/* 115 */     int i = (pos.getX() >> 4) - this.chunkX;
/* 116 */     int j = (pos.getZ() >> 4) - this.chunkZ;
/* 117 */     return this.chunkArray[i][j].getBiome(pos, this.worldObj.getBiomeProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getLightForExt(EnumSkyBlock type, BlockPos pos) {
/* 122 */     if (type == EnumSkyBlock.SKY && !this.worldObj.provider.func_191066_m())
/*     */     {
/* 124 */       return 0;
/*     */     }
/* 126 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*     */       
/* 128 */       if (getBlockState(pos).useNeighborBrightness()) {
/*     */         
/* 130 */         int l = 0; byte b; int k;
/*     */         EnumFacing[] arrayOfEnumFacing;
/* 132 */         for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */           
/* 134 */           int m = getLightFor(type, pos.offset(enumfacing));
/*     */           
/* 136 */           if (m > l)
/*     */           {
/* 138 */             l = m;
/*     */           }
/*     */           
/* 141 */           if (l >= 15)
/*     */           {
/* 143 */             return l;
/*     */           }
/*     */           b++; }
/*     */         
/* 147 */         return l;
/*     */       } 
/*     */ 
/*     */       
/* 151 */       int i = (pos.getX() >> 4) - this.chunkX;
/* 152 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/* 153 */       return this.chunkArray[i][j].getLightFor(type, pos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return type.defaultLightValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 168 */     return (getBlockState(pos).getMaterial() == Material.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightFor(EnumSkyBlock p_175628_1_, BlockPos pos) {
/* 173 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*     */       
/* 175 */       int i = (pos.getX() >> 4) - this.chunkX;
/* 176 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/* 177 */       return this.chunkArray[i][j].getLightFor(p_175628_1_, pos);
/*     */     } 
/*     */ 
/*     */     
/* 181 */     return p_175628_1_.defaultLightValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 187 */     return getBlockState(pos).getStrongPower(this, pos, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 192 */     return this.worldObj.getWorldType();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\ChunkCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntitySpawnPlacementRegistry;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.server.management.PlayerChunkMapEntry;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public final class WorldEntitySpawner {
/*  27 */   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0D, 2.0D);
/*  28 */   private final Set<ChunkPos> eligibleChunksForSpawning = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChunksForSpawning(WorldServer worldServerIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean spawnOnSetTickRate) {
/*  36 */     if (!spawnHostileMobs && !spawnPeacefulMobs)
/*     */     {
/*  38 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  42 */     this.eligibleChunksForSpawning.clear();
/*  43 */     int i = 0;
/*     */     
/*  45 */     for (EntityPlayer entityplayer : worldServerIn.playerEntities) {
/*     */       
/*  47 */       if (!entityplayer.isSpectator()) {
/*     */         
/*  49 */         int m = MathHelper.floor(entityplayer.posX / 16.0D);
/*  50 */         int k = MathHelper.floor(entityplayer.posZ / 16.0D);
/*  51 */         int l = 8;
/*     */         
/*  53 */         for (int i1 = -8; i1 <= 8; i1++) {
/*     */           
/*  55 */           for (int j1 = -8; j1 <= 8; j1++) {
/*     */             
/*  57 */             boolean flag = !(i1 != -8 && i1 != 8 && j1 != -8 && j1 != 8);
/*  58 */             ChunkPos chunkpos = new ChunkPos(i1 + m, j1 + k);
/*     */             
/*  60 */             if (!this.eligibleChunksForSpawning.contains(chunkpos)) {
/*     */               
/*  62 */               i++;
/*     */               
/*  64 */               if (!flag && worldServerIn.getWorldBorder().contains(chunkpos)) {
/*     */                 
/*  66 */                 PlayerChunkMapEntry playerchunkmapentry = worldServerIn.getPlayerChunkMap().getEntry(chunkpos.chunkXPos, chunkpos.chunkZPos);
/*     */                 
/*  68 */                 if (playerchunkmapentry != null && playerchunkmapentry.isSentToPlayers())
/*     */                 {
/*  70 */                   this.eligibleChunksForSpawning.add(chunkpos);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     int j4 = 0;
/*  80 */     BlockPos blockpos1 = worldServerIn.getSpawnPoint(); byte b; int j;
/*     */     EnumCreatureType[] arrayOfEnumCreatureType;
/*  82 */     for (j = (arrayOfEnumCreatureType = EnumCreatureType.values()).length, b = 0; b < j; ) { EnumCreatureType enumcreaturetype = arrayOfEnumCreatureType[b];
/*     */       
/*  84 */       if ((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || spawnOnSetTickRate)) {
/*     */         
/*  86 */         int k4 = worldServerIn.countEntities(enumcreaturetype.getCreatureClass());
/*  87 */         int l4 = enumcreaturetype.getMaxNumberOfCreature() * i / MOB_COUNT_DIV;
/*     */         
/*  89 */         if (k4 <= l4) {
/*     */           
/*  91 */           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */ 
/*     */           
/*  94 */           for (ChunkPos chunkpos1 : this.eligibleChunksForSpawning) {
/*     */             
/*  96 */             BlockPos blockpos = getRandomChunkPosition(worldServerIn, chunkpos1.chunkXPos, chunkpos1.chunkZPos);
/*  97 */             int k1 = blockpos.getX();
/*  98 */             int l1 = blockpos.getY();
/*  99 */             int i2 = blockpos.getZ();
/* 100 */             IBlockState iblockstate = worldServerIn.getBlockState(blockpos);
/*     */             
/* 102 */             if (!iblockstate.isNormalCube()) {
/*     */               
/* 104 */               int j2 = 0;
/*     */               int k2;
/* 106 */               label88: for (k2 = 0; k2 < 3; k2++) {
/*     */                 
/* 108 */                 int l2 = k1;
/* 109 */                 int i3 = l1;
/* 110 */                 int j3 = i2;
/* 111 */                 int k3 = 6;
/* 112 */                 Biome.SpawnListEntry biome$spawnlistentry = null;
/* 113 */                 IEntityLivingData ientitylivingdata = null;
/* 114 */                 int l3 = MathHelper.ceil(Math.random() * 4.0D);
/*     */                 
/* 116 */                 for (int i4 = 0; i4 < l3; i4++) {
/*     */                   
/* 118 */                   l2 += worldServerIn.rand.nextInt(6) - worldServerIn.rand.nextInt(6);
/* 119 */                   i3 += worldServerIn.rand.nextInt(1) - worldServerIn.rand.nextInt(1);
/* 120 */                   j3 += worldServerIn.rand.nextInt(6) - worldServerIn.rand.nextInt(6);
/* 121 */                   blockpos$mutableblockpos.setPos(l2, i3, j3);
/* 122 */                   float f = l2 + 0.5F;
/* 123 */                   float f1 = j3 + 0.5F;
/*     */                   
/* 125 */                   if (!worldServerIn.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0D) && blockpos1.distanceSq(f, i3, f1) >= 576.0D) {
/*     */                     
/* 127 */                     if (biome$spawnlistentry == null) {
/*     */                       
/* 129 */                       biome$spawnlistentry = worldServerIn.getSpawnListEntryForTypeAt(enumcreaturetype, (BlockPos)blockpos$mutableblockpos);
/*     */                       
/* 131 */                       if (biome$spawnlistentry == null) {
/*     */                         break;
/*     */                       }
/*     */                     } 
/*     */ 
/*     */                     
/* 137 */                     if (worldServerIn.canCreatureTypeSpawnHere(enumcreaturetype, biome$spawnlistentry, (BlockPos)blockpos$mutableblockpos) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biome$spawnlistentry.entityClass), worldServerIn, (BlockPos)blockpos$mutableblockpos)) {
/*     */                       EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */                       
/*     */                       try {
/* 143 */                         entityliving = biome$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldServerIn });
/*     */                       }
/* 145 */                       catch (Exception exception) {
/*     */                         
/* 147 */                         exception.printStackTrace();
/* 148 */                         return j4;
/*     */                       } 
/*     */                       
/* 151 */                       entityliving.setLocationAndAngles(f, i3, f1, worldServerIn.rand.nextFloat() * 360.0F, 0.0F);
/*     */                       
/* 153 */                       if (entityliving.getCanSpawnHere() && entityliving.isNotColliding()) {
/*     */                         
/* 155 */                         ientitylivingdata = entityliving.onInitialSpawn(worldServerIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/*     */                         
/* 157 */                         if (entityliving.isNotColliding()) {
/*     */                           
/* 159 */                           j2++;
/* 160 */                           worldServerIn.spawnEntityInWorld((Entity)entityliving);
/*     */                         }
/*     */                         else {
/*     */                           
/* 164 */                           entityliving.setDead();
/*     */                         } 
/*     */                         
/* 167 */                         if (j2 >= entityliving.getMaxSpawnedInChunk()) {
/*     */                           break label88;
/*     */                         }
/*     */                       } 
/*     */ 
/*     */                       
/* 173 */                       j4 += j2;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 184 */     return j4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
/* 190 */     Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
/* 191 */     int i = x * 16 + worldIn.rand.nextInt(16);
/* 192 */     int j = z * 16 + worldIn.rand.nextInt(16);
/* 193 */     int k = MathHelper.roundUp(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
/* 194 */     int l = worldIn.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 195 */     return new BlockPos(i, l, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidEmptySpawnBlock(IBlockState state) {
/* 200 */     if (state.isBlockNormalCube())
/*     */     {
/* 202 */       return false;
/*     */     }
/* 204 */     if (state.canProvidePower())
/*     */     {
/* 206 */       return false;
/*     */     }
/* 208 */     if (state.getMaterial().isLiquid())
/*     */     {
/* 210 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 214 */     return !BlockRailBase.isRailBlock(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos) {
/* 220 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 222 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 226 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 228 */     if (spawnPlacementTypeIn == EntityLiving.SpawnPlacementType.IN_WATER)
/*     */     {
/* 230 */       return (iblockstate.getMaterial() == Material.WATER && worldIn.getBlockState(pos.down()).getMaterial() == Material.WATER && !worldIn.getBlockState(pos.up()).isNormalCube());
/*     */     }
/*     */ 
/*     */     
/* 234 */     BlockPos blockpos = pos.down();
/*     */     
/* 236 */     if (!worldIn.getBlockState(blockpos).isFullyOpaque())
/*     */     {
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 242 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/* 243 */     boolean flag = (block != Blocks.BEDROCK && block != Blocks.BARRIER);
/* 244 */     return (flag && isValidEmptySpawnBlock(iblockstate) && isValidEmptySpawnBlock(worldIn.getBlockState(pos.up())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performWorldGenSpawning(World worldIn, Biome biomeIn, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random randomIn) {
/* 255 */     List<Biome.SpawnListEntry> list = biomeIn.getSpawnableList(EnumCreatureType.CREATURE);
/*     */     
/* 257 */     if (!list.isEmpty())
/*     */     {
/* 259 */       while (randomIn.nextFloat() < biomeIn.getSpawningChance()) {
/*     */         
/* 261 */         Biome.SpawnListEntry biome$spawnlistentry = (Biome.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
/* 262 */         int i = biome$spawnlistentry.minGroupCount + randomIn.nextInt(1 + biome$spawnlistentry.maxGroupCount - biome$spawnlistentry.minGroupCount);
/* 263 */         IEntityLivingData ientitylivingdata = null;
/* 264 */         int j = p_77191_2_ + randomIn.nextInt(p_77191_4_);
/* 265 */         int k = p_77191_3_ + randomIn.nextInt(p_77191_5_);
/* 266 */         int l = j;
/* 267 */         int i1 = k;
/*     */         
/* 269 */         for (int j1 = 0; j1 < i; j1++) {
/*     */           
/* 271 */           boolean flag = false;
/*     */           
/* 273 */           for (int k1 = 0; !flag && k1 < 4; k1++) {
/*     */             
/* 275 */             BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
/*     */             
/* 277 */             if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos)) {
/*     */               EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 283 */                 entityliving = biome$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */               }
/* 285 */               catch (Exception exception) {
/*     */                 
/* 287 */                 exception.printStackTrace();
/*     */               } 
/*     */ 
/*     */               
/* 291 */               entityliving.setLocationAndAngles((j + 0.5F), blockpos.getY(), (k + 0.5F), randomIn.nextFloat() * 360.0F, 0.0F);
/* 292 */               worldIn.spawnEntityInWorld((Entity)entityliving);
/* 293 */               ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/* 294 */               flag = true;
/*     */             } 
/*     */             
/* 297 */             j += randomIn.nextInt(5) - randomIn.nextInt(5);
/*     */             
/* 299 */             for (k += randomIn.nextInt(5) - randomIn.nextInt(5); j < p_77191_2_ || j >= p_77191_2_ + p_77191_4_ || k < p_77191_3_ || k >= p_77191_3_ + p_77191_4_; k = i1 + randomIn.nextInt(5) - randomIn.nextInt(5))
/*     */             {
/* 301 */               j = l + randomIn.nextInt(5) - randomIn.nextInt(5);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldEntitySpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
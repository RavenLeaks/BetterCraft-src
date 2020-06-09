/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class ChunkGeneratorDebug implements IChunkGenerator {
/*  19 */   private static final List<IBlockState> ALL_VALID_STATES = Lists.newArrayList();
/*     */   private static final int GRID_WIDTH;
/*     */   private static final int GRID_HEIGHT;
/*  22 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*  23 */   protected static final IBlockState BARRIER = Blocks.BARRIER.getDefaultState();
/*     */   
/*     */   private final World world;
/*     */   
/*     */   public ChunkGeneratorDebug(World worldIn) {
/*  28 */     this.world = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  33 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/*  35 */     for (int i = 0; i < 16; i++) {
/*     */       
/*  37 */       for (int j = 0; j < 16; j++) {
/*     */         
/*  39 */         int k = x * 16 + i;
/*  40 */         int l = z * 16 + j;
/*  41 */         chunkprimer.setBlockState(i, 60, j, BARRIER);
/*  42 */         IBlockState iblockstate = getBlockStateFor(k, l);
/*     */         
/*  44 */         if (iblockstate != null)
/*     */         {
/*  46 */           chunkprimer.setBlockState(i, 70, j, iblockstate);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  51 */     Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
/*  52 */     chunk.generateSkylightMap();
/*  53 */     Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
/*  54 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/*  56 */     for (int i1 = 0; i1 < abyte.length; i1++)
/*     */     {
/*  58 */       abyte[i1] = (byte)Biome.getIdForBiome(abiome[i1]);
/*     */     }
/*     */     
/*  61 */     chunk.generateSkylightMap();
/*  62 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState getBlockStateFor(int p_177461_0_, int p_177461_1_) {
/*  67 */     IBlockState iblockstate = AIR;
/*     */     
/*  69 */     if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
/*     */       
/*  71 */       p_177461_0_ /= 2;
/*  72 */       p_177461_1_ /= 2;
/*     */       
/*  74 */       if (p_177461_0_ <= GRID_WIDTH && p_177461_1_ <= GRID_HEIGHT) {
/*     */         
/*  76 */         int i = MathHelper.abs(p_177461_0_ * GRID_WIDTH + p_177461_1_);
/*     */         
/*  78 */         if (i < ALL_VALID_STATES.size())
/*     */         {
/*  80 */           iblockstate = ALL_VALID_STATES.get(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(int x, int z) {}
/*     */ 
/*     */   
/*     */   public boolean generateStructures(Chunk chunkIn, int x, int z) {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/*  99 */     Biome biome = this.world.getBiome(pos);
/* 100 */     return biome.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ 
/*     */   
/*     */   static {
/* 120 */     for (Block block : Block.REGISTRY)
/*     */     {
/* 122 */       ALL_VALID_STATES.addAll((Collection<? extends IBlockState>)block.getBlockState().getValidStates());
/*     */     }
/*     */     
/* 125 */     GRID_WIDTH = MathHelper.ceil(MathHelper.sqrt(ALL_VALID_STATES.size()));
/* 126 */     GRID_HEIGHT = MathHelper.ceil(ALL_VALID_STATES.size() / GRID_WIDTH);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
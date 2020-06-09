package net.minecraft.world.gen;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public interface IChunkGenerator {
  Chunk provideChunk(int paramInt1, int paramInt2);
  
  void populate(int paramInt1, int paramInt2);
  
  boolean generateStructures(Chunk paramChunk, int paramInt1, int paramInt2);
  
  List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType paramEnumCreatureType, BlockPos paramBlockPos);
  
  @Nullable
  BlockPos getStrongholdGen(World paramWorld, String paramString, BlockPos paramBlockPos, boolean paramBoolean);
  
  void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2);
  
  boolean func_193414_a(World paramWorld, String paramString, BlockPos paramBlockPos);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\IChunkGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
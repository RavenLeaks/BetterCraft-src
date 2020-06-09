package net.minecraft.world.chunk;

import javax.annotation.Nullable;

public interface IChunkProvider {
  @Nullable
  Chunk getLoadedChunk(int paramInt1, int paramInt2);
  
  Chunk provideChunk(int paramInt1, int paramInt2);
  
  boolean unloadQueuedChunks();
  
  String makeString();
  
  boolean func_191062_e(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\IChunkProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
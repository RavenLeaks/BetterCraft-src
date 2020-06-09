package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public interface IChunk {
  boolean hasBiomeData();
  
  int getChunkX();
  
  int getChunkZ();
  
  int getBitmask();
  
  ChunkSection[] getChunkSections();
  
  int[] getBiomeData();
  
  boolean isGroundUp();
  
  List<NBTTagCompound> getTileEntities();
  
  NBTTagCompound getHeightMap();
  
  void setHeightMap(NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\IChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
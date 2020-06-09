package wdl.api;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public interface ITileEntityImportationIdentifier extends IWDLMod {
  boolean shouldImportTileEntity(String paramString, BlockPos paramBlockPos, Block paramBlock, NBTTagCompound paramNBTTagCompound, Chunk paramChunk);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\ITileEntityImportationIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
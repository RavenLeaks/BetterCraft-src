package net.minecraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public interface IWorldEventListener {
  void notifyBlockUpdate(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState1, IBlockState paramIBlockState2, int paramInt);
  
  void notifyLightSet(BlockPos paramBlockPos);
  
  void markBlockRangeForRenderUpdate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  void playSoundToAllNearExcept(EntityPlayer paramEntityPlayer, SoundEvent paramSoundEvent, SoundCategory paramSoundCategory, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
  
  void playRecord(SoundEvent paramSoundEvent, BlockPos paramBlockPos);
  
  void spawnParticle(int paramInt, boolean paramBoolean, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int... paramVarArgs);
  
  void func_190570_a(int paramInt, boolean paramBoolean1, boolean paramBoolean2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int... paramVarArgs);
  
  void onEntityAdded(Entity paramEntity);
  
  void onEntityRemoved(Entity paramEntity);
  
  void broadcastSound(int paramInt1, BlockPos paramBlockPos, int paramInt2);
  
  void playEvent(EntityPlayer paramEntityPlayer, int paramInt1, BlockPos paramBlockPos, int paramInt2);
  
  void sendBlockBreakProgress(int paramInt1, BlockPos paramBlockPos, int paramInt2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\IWorldEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
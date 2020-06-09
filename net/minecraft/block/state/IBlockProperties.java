package net.minecraft.block.state;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockProperties {
  Material getMaterial();
  
  boolean isFullBlock();
  
  boolean canEntitySpawn(Entity paramEntity);
  
  int getLightOpacity();
  
  int getLightValue();
  
  boolean isTranslucent();
  
  boolean useNeighborBrightness();
  
  MapColor getMapColor(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  IBlockState withRotation(Rotation paramRotation);
  
  IBlockState withMirror(Mirror paramMirror);
  
  boolean isFullCube();
  
  boolean func_191057_i();
  
  EnumBlockRenderType getRenderType();
  
  int getPackedLightmapCoords(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  float getAmbientOcclusionLightValue();
  
  boolean isBlockNormalCube();
  
  boolean isNormalCube();
  
  boolean canProvidePower();
  
  int getWeakPower(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  boolean hasComparatorInputOverride();
  
  int getComparatorInputOverride(World paramWorld, BlockPos paramBlockPos);
  
  float getBlockHardness(World paramWorld, BlockPos paramBlockPos);
  
  float getPlayerRelativeBlockHardness(EntityPlayer paramEntityPlayer, World paramWorld, BlockPos paramBlockPos);
  
  int getStrongPower(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  EnumPushReaction getMobilityFlag();
  
  IBlockState getActualState(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  AxisAlignedBB getSelectedBoundingBox(World paramWorld, BlockPos paramBlockPos);
  
  boolean shouldSideBeRendered(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  boolean isOpaqueCube();
  
  @Nullable
  AxisAlignedBB getCollisionBoundingBox(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  void addCollisionBoxToList(World paramWorld, BlockPos paramBlockPos, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity, boolean paramBoolean);
  
  AxisAlignedBB getBoundingBox(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  RayTraceResult collisionRayTrace(World paramWorld, BlockPos paramBlockPos, Vec3d paramVec3d1, Vec3d paramVec3d2);
  
  boolean isFullyOpaque();
  
  Vec3d func_191059_e(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos);
  
  boolean func_191058_s();
  
  BlockFaceShape func_193401_d(IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\IBlockProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
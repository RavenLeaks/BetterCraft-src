package net.minecraft.client.renderer.block.model;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public interface IBakedModel {
  List<BakedQuad> getQuads(IBlockState paramIBlockState, EnumFacing paramEnumFacing, long paramLong);
  
  boolean isAmbientOcclusion();
  
  boolean isGui3d();
  
  boolean isBuiltInRenderer();
  
  TextureAtlasSprite getParticleTexture();
  
  ItemCameraTransforms getItemCameraTransforms();
  
  ItemOverrideList getOverrides();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\IBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
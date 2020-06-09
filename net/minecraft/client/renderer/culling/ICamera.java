package net.minecraft.client.renderer.culling;

import net.minecraft.util.math.AxisAlignedBB;

public interface ICamera {
  boolean isBoundingBoxInFrustum(AxisAlignedBB paramAxisAlignedBB);
  
  void setPosition(double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\culling\ICamera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.entity.ai.attributes;

import javax.annotation.Nullable;

public interface IAttribute {
  String getAttributeUnlocalizedName();
  
  double clampValue(double paramDouble);
  
  double getDefaultValue();
  
  boolean getShouldWatch();
  
  @Nullable
  IAttribute getParent();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\IAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
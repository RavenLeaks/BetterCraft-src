package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.Nullable;

public interface IAttributeInstance {
  IAttribute getAttribute();
  
  double getBaseValue();
  
  void setBaseValue(double paramDouble);
  
  Collection<AttributeModifier> getModifiersByOperation(int paramInt);
  
  Collection<AttributeModifier> getModifiers();
  
  boolean hasModifier(AttributeModifier paramAttributeModifier);
  
  @Nullable
  AttributeModifier getModifier(UUID paramUUID);
  
  void applyModifier(AttributeModifier paramAttributeModifier);
  
  void removeModifier(AttributeModifier paramAttributeModifier);
  
  void removeModifier(UUID paramUUID);
  
  void removeAllModifiers();
  
  double getAttributeValue();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\IAttributeInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
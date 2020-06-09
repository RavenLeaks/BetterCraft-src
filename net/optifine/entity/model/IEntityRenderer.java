package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer {
  Class getEntityClass();
  
  void setEntityClass(Class paramClass);
  
  ResourceLocation getLocationTextureCustom();
  
  void setLocationTextureCustom(ResourceLocation paramResourceLocation);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\IEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */